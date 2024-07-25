package com.opencars.netgo.Notifications.service;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.sales.proveedores.entity.SalesProviders;
import com.opencars.netgo.sales.proveedores.entity.StatesProveedores;
import com.opencars.netgo.sales.proveedores.service.SalesProvidersService;
import com.opencars.netgo.sales.proveedores.service.StatesProveedoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
public class SendMailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    SalesProvidersService salesProvidersService;

    @Autowired
    StatesProveedoresService statesProveedoresService;

    private Path pathpdfs = Paths.get("data/pdfs");

    public void sendMail(String from, String to, String subject, String body){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);

        javaMailSender.send(mailMessage);
    }

    public void sendMailWithFile(String from, String to, String subject, MultipartFile file) {

        try {
            Files.copy(file.getInputStream(),
                    pathpdfs.resolve(Objects.requireNonNull(file.getOriginalFilename())));

            Resource resourceObtained = this.getPDFByFilename(file.getOriginalFilename());

            if (resourceObtained.exists()){
                try{
                    MimeMessage message = javaMailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true);
                    helper.setFrom(from);
                    helper.setTo(to);
                    helper.setSubject(subject);
                    helper.setText("Estimado cliente: <br><br> En esta oportunidad, tenemos el agrado de hacerle llegar el peritaje de su vehículo, en formato digital. <br><br> Agradecemos su confianza, y le enviamos un cordial saludo. <br><br> El Equipo de Grupo Opencars. <br>", true);
                    helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), resourceObtained);
                    javaMailSender.send(message);
                    this.deletePDF(file.getOriginalFilename());
                }catch (MessagingException m){
                    System.out.println("Error en envío de correo: " + m.getMessage());
                }
            }

        } catch (IOException e) {
            Resource resourceObtained = this.getPDFByFilename(file.getOriginalFilename());

            if (resourceObtained.exists()){
                try{
                    MimeMessage message = javaMailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true);
                    helper.setFrom(from);
                    helper.setTo(to);
                    helper.setSubject(subject);
                    helper.setText("Estimado cliente: <br> En esta oportunidad, tenemos el agrado de hacerle llegar el peritaje de su vehículo, en formato digital. <br> Agradecemos su confianza, y le enviamos un cordial saludo. <br><br> El Equipo de Grupo Opencars. <br>", true);
                    helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), resourceObtained);
                    javaMailSender.send(message);
                    this.deletePDF(file.getOriginalFilename());
                }catch (MessagingException m){
                    System.out.println("Error en envío de correo: " + m.getMessage());
                }
            }
            throw new RuntimeException(e);
        }
    }

    public void sendMailWithFiles(long id, String op, Branch branch, String from, String to, String subject, List<MultipartFile> files) {

        LocalDateTime date = LocalDateTime.now();

        String mailCC = "";
        switch (branch.getBrandsCompany().getCompany().getName()){
            case "Fortecar":
                mailCC = "info-pagos@fortecar.com.ar";
                break;
            case "Granville":
                mailCC = "infopagos@granville.com.ar";
                break;
            case "Pampawagen":
                mailCC = "info.pagos@pampawagen.com";
                break;
        }

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setCc(mailCC);
            helper.setSubject(subject);
            helper.setText("Estimados, <br><br> Se adjuntan comprobantes de pagos emitidos por: " + branch.getBrandsCompany().getCompany().getName() + " S. A | CUIT: " + branch.getBrandsCompany().getCompany().getCuit() + "<br><br> El pago lo verá reflejado en su cuenta bancaria dentro de las próximas 24 horas. <br><br> Atte. <br><br> El Equipo de " + branch.getBrandsCompany().getCompany().getName() + " S. A <br>", true);

            for (MultipartFile file : files) {

                Resource resourceObtained = new ByteArrayResource(file.getBytes()) {
                    @Override
                    public String getFilename() {
                        return file.getOriginalFilename();
                    }
                };

                if (resourceObtained.exists()) {
                    helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), resourceObtained);
                }
            }

            javaMailSender.send(message);

            StatesProveedores stateUpdated = statesProveedoresService.getOne(6).get();
            SalesProviders compraUpdated = salesProvidersService.getOne(id).get();
            compraUpdated.setState(stateUpdated);
            compraUpdated.setOp(op);
            compraUpdated.setDateEnd(date);
            long diferenciaEnDias = ChronoUnit.DAYS.between(compraUpdated.getDateEmision(), compraUpdated.getDateEnd());
            compraUpdated.setResolution(String.valueOf(diferenciaEnDias));

            salesProvidersService.save(compraUpdated);

        } catch (MessagingException m) {
            System.out.println("Error en envío de correo: " + m.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Resource getPDFByFilename(String filename){
        try {
            Path pdf = pathpdfs.resolve(filename);
            Resource resource = new UrlResource(pdf.toUri());

            if(resource.exists() || resource.isReadable()){
                return resource;
            }else{
                throw new RuntimeException("No se puede leer el archivo ");
            }

        }catch (MalformedURLException e){
            throw new RuntimeException("Error malformed url: " + e.getMessage());
        }
    }

    private String deletePDF(String filename){

        Path pathFileDeleted = Paths.get("data/pdfs/" + filename);

        try {
            Boolean delete = Files.deleteIfExists(pathFileDeleted);
            return "Borrado";
        }catch (IOException e){
            e.printStackTrace();
            return "Error al borrar";
        }
    }
}

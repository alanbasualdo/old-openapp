package com.opencars.netgo.uploadfiles.services;

import com.opencars.netgo.dms.calidad.entity.ReportRegister;
import com.opencars.netgo.dms.calidad.service.ReportCalidadService;
import com.opencars.netgo.users.entity.User;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@Service
public class FileServiceImp implements FileService{

    @Autowired
    ReportCalidadService reportCalidadService;

    private Path pathrecibosglobal;
    private Path pathFileDeleted;
    private Path pathdownload;
    private final Path pathimgs = Paths.get("data/imgs"); //Imagenes estaticas

    @Override
    public void init() {}

    //guardar
    @Override
    public void save(MultipartFile file, String type, String id, String interno) {

        try {

            switch (type) {
                case "bienvenidas":
                case "imgs":
                case "finanzas":
                case "rentabilidades":
                case "cuentasporcobrar":
                case "gestionpda":
                    pathdownload = Paths.get("data/" + type + "/");
                    break;
                case "gestionlogistica":
                    pathdownload = Paths.get("data/" + type + "/");
                    break;
                case "tickets":
                    if (interno.equals("no")) {
                        pathdownload = Paths.get("data/tickets/" + id);
                    } else if (interno.equals("si")) {
                        pathdownload = Paths.get("data/tickets/" + id + "/interno");
                    }
                    break;
                default:
                    pathdownload = Paths.get("data/" + type + "/" + id);
                    break;
            }

            //Verificamos si el directorio existe, si no existe lo creamos
            if(!Files.isDirectory(pathdownload)){
                Files.createDirectory(pathdownload);
            }

            //Copy (que queremos copiar, a donde lo queremos copiar)
            Files.copy(file.getInputStream(),
                    pathdownload.resolve(file.getOriginalFilename()));

        } catch (IOException e) {

            throw new RuntimeException("No se puede guardar el archivo. Error " + e.getMessage());
        }
    }

    @Override
    public void createDirectory(String type, String id) {

        try {

            pathdownload = Paths.get("data/" + type + "/" + id);

            //Verificamos si el directorio existe, si no existe lo creamos
            if(!Files.isDirectory(pathdownload)) {
                Files.createDirectory(pathdownload);
            }


        } catch (IOException e) {

            throw new RuntimeException("No se puede crear el directorio" + e.getMessage());
        }
    }

    @Override
    public void createDirectoryPrincipal(String nameDirectory) {

        pathdownload = Paths.get("data/" + nameDirectory);

        try{

            //Verificamos si el directorio existe, si no existe lo creamos
            if(!Files.isDirectory(pathdownload)) {
                Files.createDirectory(pathdownload);
            }

        } catch (IOException e) {

            throw new RuntimeException("No se puede crear el directorio" + e.getMessage());
        }
    }

    //cambiar foto
    @Override
    public void change(MultipartFile file, String id, String type) {

        Path pathchanges1 = Paths.get("data/" + type + "/");

        Path pathchanges;

        switch (type) {
            case "finanzas":
                pathchanges = pathchanges1;
                this.registerReport(2);
                break;
            case "calidad":
                pathchanges = pathchanges1;
                this.registerReport(1);
                break;
            case "rentabilidades":
                pathchanges = pathchanges1;
                this.registerReport(3);
                break;
            case "gestionpda":
                pathchanges = pathchanges1;
                this.registerReport(4);
                break;
            case "cuentasporcobrar":
                pathchanges = pathchanges1;
                this.registerReport(5);
                break;
            case "gestionlogistica":
                pathchanges = pathchanges1;
                this.registerReport(6);
                break;
            default:
                pathchanges = Paths.get("data/" + type + "/" + id);
                break;
        }

        try {

            //Verificamos si el directorio existe, si no existe lo creamos
            if(!Files.isDirectory(pathchanges)){
                Files.createDirectory(pathchanges);
            }

            // Limpio el directorio
            FileUtils.cleanDirectory(pathchanges.toFile());

            //Copio la nueva imagen de perfil
            Files.copy(file.getInputStream(),
                    pathchanges.resolve(file.getOriginalFilename()));

        } catch (IOException e) {

            throw new RuntimeException("No se puede guardar el archivo. Error " + e.getMessage());
        }

    }

    private void registerReport(int id){

        LocalDateTime date = LocalDateTime.now();
        LocalDate shortdate = LocalDate.now();

        SortedSet<User> like = new TreeSet<>();
        ReportRegister registerReport = reportCalidadService.getByIdOptional(id).get();
        registerReport.setDate(date);
        registerReport.setShortdate(shortdate);
        registerReport.setLikes(like);
        registerReport.setViews(0);

        reportCalidadService.save(registerReport);
    }

    @Override
    public Resource load(String filename, String type, String cuil) {

        if (type.equals("calidad") || type.equals("finanzas") || type.equals("rentabilidades") || type.equals("cuentasporcobrar") || type.equals("gestionpda") || type.equals("gestionlogistica")){
            pathdownload = Paths.get("data/" + type + "/");
        }else{
            pathdownload = Paths.get("data/" + type + "/" + cuil);
        }

        try {
            Path file = pathdownload.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if(resource.exists() || resource.isReadable()){
                return resource;
            }else{
                throw new RuntimeException("No se puede leer el archivo.");
            }

        }catch (MalformedURLException e){
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public Stream<Path> loadImgsFromIdOrCUILDirectory(String type, String id){

        if (type.equals("finanzas") || type.equals("calidad") || type.equals("rentabilidades") || type.equals("cuentasporcobrar") || type.equals("gestionpda") || type.equals("gestionlogistica")){
            pathdownload = Paths.get("data/" + type + "/");
        }else{
            pathdownload = Paths.get("data/" + type + "/" + id);
        }

        try {
            return Files.walk(this.pathdownload, 1)
                    .filter(Files::isRegularFile);
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException("No se puede cargar la foto");
        }
    }

    @Override
    public Stream<Path> viewPrincipal(){

        try{
            pathrecibosglobal = Paths.get("data");

            return Files.walk(this.pathrecibosglobal, 1)
                    .filter(Files::isDirectory);

        }catch (RuntimeException | IOException e){
            throw new RuntimeException("No se pueden cargar los directorios ");
        }
    }

    @Override
    public Stream<Path> viewDirectories(String directory){

        try{
            pathrecibosglobal = Paths.get("data/" + directory);

            return Files.walk(this.pathrecibosglobal, 1)
                    .map(this.pathrecibosglobal::relativize);

        }catch (RuntimeException | IOException e){
            throw new RuntimeException("No se pueden cargar los directorios ");
        }
    }

    @Override
    public Stream<Path> loadFilesForDirectory(String directory, String subDirectory){

        try{

            pathrecibosglobal = Paths.get("data/" + directory + "/" + subDirectory);

            return Files.walk(this.pathrecibosglobal,
                    1 ).map(this.pathrecibosglobal::relativize);

        }catch (RuntimeException | IOException e){
            throw new RuntimeException("No se pueden cargar los archivos ");
        }
    }

    @Override
    public Stream<Path> loadFilesForInternalDirectory(String directory, String subDirectory, String internalDirectory){

        try{

            pathrecibosglobal = Paths.get("data/" + directory + "/" + subDirectory + "/" + internalDirectory);

            return Files.walk(this.pathrecibosglobal,
                    1 ).map(this.pathrecibosglobal::relativize);

        }catch (RuntimeException | IOException e){
            throw new RuntimeException("No se pueden cargar los archivos ");
        }
    }

    @Override
    public Stream<Path> loadDocumentsByTicketId(String id) {
        //Files.walk recorre las carpetas buscando los archivos

        try{
            Path pathtickets = Paths.get("data/tickets/" + id);

            // el 2 es la profundidad o nivel que queremos recorrer, en este caso buscamos dentro de los directorios de cada usuario
            // :: Referencias a metodos
            // Relativize sirve para crear una ruta relativa entre la ruta dada y esta ruta
            //Files::isRegularFile --> Filtramos por archivo, para que no cargue los nombres de los directorios

            return Files.walk(pathtickets,
                    1 ).filter(Files::isRegularFile).map(pathtickets::relativize);

        }catch (RuntimeException | IOException e){
            throw new RuntimeException("No se pueden cargar los archivos ");
        }
    }

    @Override
    public Resource loadFilesByFilename(String filename) {

        try {
            Path file = pathimgs.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if(resource.exists() || resource.isReadable()){
                return resource;
            }else{
                throw new RuntimeException("No se puede leer el archivo ");
            }

        }catch (MalformedURLException e){
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public String deleteInternalFile(String directory, String subdirectory, String internal){

        pathFileDeleted = Paths.get("data/" + directory + "/" + subdirectory + "/" + internal);

        try {
            Boolean delete = Files.deleteIfExists(this.pathFileDeleted);
            return "Borrado";
        }catch (IOException e){
            e.printStackTrace();
            return "Error Borrando ";
        }
    }

    @Override
    public String deleteDirectory(String directory){

        pathFileDeleted = Paths.get("data/" + directory);

        try {
            Boolean delete = Files.deleteIfExists(this.pathFileDeleted);
            return "Borrado";
        }catch (IOException e){
            e.printStackTrace();
            return "Error Borrando ";
        }
    }

    @Override
    public String deleteSubdirectory(String directory, String subdirectory){

        pathFileDeleted = Paths.get("data/" + directory + "/" + subdirectory);

        try {
            Boolean delete = Files.deleteIfExists(this.pathFileDeleted);
            return "Borrado";
        }catch (IOException e){
            e.printStackTrace();
            return "Error Borrando ";
        }
    }

    @Override
    public String deleteInternalFileOfSubdirectory(String directory, String subdirectory, String internalfile){

        pathFileDeleted = Paths.get("data/" + directory + "/" + subdirectory + "/" + internalfile);

        try {
            Boolean delete = Files.deleteIfExists(this.pathFileDeleted);
            return "Borrado";
        }catch (IOException e){
            e.printStackTrace();
            return "Error Borrando ";
        }
    }

    @Override
    public String deleteInternalFileLastLevel(String directory, String subdirectory, String internalfile, String file){

        pathFileDeleted = Paths.get("data/" + directory + "/" + subdirectory + "/" + internalfile + "/" + file);

        try {
            Boolean delete = Files.deleteIfExists(this.pathFileDeleted);
            return "Borrado";
        }catch (IOException e){
            e.printStackTrace();
            return "Error Borrando ";
        }
    }
}

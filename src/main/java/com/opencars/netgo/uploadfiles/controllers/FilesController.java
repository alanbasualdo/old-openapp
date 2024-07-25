package com.opencars.netgo.uploadfiles.controllers;

import com.opencars.netgo.auth.jwt.JwtTokenFilter;
import com.opencars.netgo.uploadfiles.messages.FileMessage;
import com.opencars.netgo.uploadfiles.models.DirectoryModel;
import com.opencars.netgo.uploadfiles.models.FileModel;
import com.opencars.netgo.uploadfiles.services.FileService;
import com.opencars.netgo.users.entity.User;
import com.opencars.netgo.users.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin()
@Api(tags = "Controlador de Manejo de Archivos")
public class FilesController {

    private Path pathdownload; //Descargas
    private final Path pathimgs = Paths.get("data/imgs"); //Imagenes
    private final ResourceLoader resourceLoader;

    //Inyectamos el servicio
    @Autowired
    FileService fileService;

    @Autowired
    UserService userService;

    public FilesController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    //Endpoint para cargar archivos
    @ApiOperation(value = "Carga de Archivos"
            ,notes = "Se carga un archivo y el sistema lo ubica en la carpeta correspondiente, según los parámetros recibidos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/upload")
    public ResponseEntity<FileMessage> uploadFiles(@RequestParam("files") MultipartFile file, @RequestParam("type")String typeLoad, @RequestParam("id")String id, @RequestParam("interno")String interno){
        String message = "";
        try{

            fileService.save(file, typeLoad, id, interno);

            message = String.valueOf(file.getOriginalFilename());
            return ResponseEntity.status(HttpStatus.OK).body(new FileMessage(message));
        }catch (Exception e){
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileMessage(message));
        }
    }

    @ApiOperation(value = "Carga de Archivos por lista"
            ,notes = "Se carga una lista de archivos y el sistema lo ubica en la carpeta correspondiente, según los parámetros recibidos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/upload/list")
    public ResponseEntity<FileMessage> uploadFilesArray(@RequestParam("files") MultipartFile[] files, @RequestParam("type")String typeLoad, @RequestParam("id")String id, @RequestParam("interno")String interno){
        String message = "";
        try{

            for (MultipartFile file : files) {
                fileService.save(file, typeLoad, id, interno);
            }

            message = "Todo salió bien";
            return ResponseEntity.status(HttpStatus.OK).body(new FileMessage(message));
        }catch (Exception e){
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileMessage(message));
        }
    }

    @ApiOperation(value = "Carga de Archivos por Cantidad"
            ,notes = "Se carga una lista de archivos y el sistema lo ubica en la carpeta correspondiente, según los parámetros recibidos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/upload/files")
    public ResponseEntity<?> uploadFiles(@RequestParam("files") MultipartFile[] files, @RequestParam("type")String typeLoad, @RequestParam("id")String id, @RequestParam("interno")String interno){
        String message = "";
        try{
            for (MultipartFile file : files) {
                fileService.save((MultipartFile) file.getResource().getFile(), typeLoad, id, interno);
            }
            message = "ok";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }catch (Exception e){
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    //Endpoint para crear directorio
    @ApiOperation(value = "Creación de un Directorio"
            ,notes = "Se crea un directorio para el tipo de archivo recibido por parámetro, en la carpeta correspondiente con el id recibido")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/createDirectory")
    public ResponseEntity<FileMessage> createDirectory(@RequestParam("type")String typeLoad, @RequestParam("id")String id){
        String message = "";
        try{

            fileService.createDirectory(typeLoad, id);

            message = "OK";
            return ResponseEntity.status(HttpStatus.OK).body(new FileMessage(message));

        }catch (Exception e){
            message = "Fallo al crear directorio";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileMessage(message));
        }
    }

    //Endpoint para crear directorio
    @ApiOperation(value = "Creación de un Directorio Principal"
            ,notes = "Se crea un directorio principal con el nombre enviado por parámetro, a nivel raíz de la carpeta de archivos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/createDirectoryPrincipal")
    public ResponseEntity<FileMessage> createDirectoryPrincipal(@RequestParam("name")String nameDirectory){
        String message = "";
        try{

            fileService.createDirectoryPrincipal(nameDirectory);

            message = "OK";
            return ResponseEntity.status(HttpStatus.OK).body(new FileMessage(message));

        }catch (Exception e){
            message = "Fallo al crear directorio";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileMessage(message));
        }
    }

    //Endpoint para obtener todos los documentos de un ticket
    @ApiOperation(value = "Documentos de un Ticket"
            ,notes = "Se obtiene un listado con todos los documentos vinculados a un ticket")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tickets/documents/{id}")
    public ResponseEntity<List<FileModel>> getDocumentsByTicketId(@PathVariable String id){

        List<FileModel> fileInfos = fileService.loadDocumentsByTicketId(id).map(path -> {
            String filename = path.getFileName().toString();
            return new FileModel(filename);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    //Endpoint para eliminar archivos de carpeta interna
    @ApiOperation(value = "Eliminación de archivos de Directorio Interno"
            ,notes = "Se elimina un archivo ubicado en un directorio interno")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/delete/{type}/{id}/interno/{filename:.+}")
    public ResponseEntity<FileMessage> deleteInternalFile(@PathVariable String type, @PathVariable String id, @PathVariable String filename) {
        String message = "";
        try {
            message = fileService.deleteInternalFile(type, id, filename);
            return ResponseEntity.status(HttpStatus.OK).body(new FileMessage(message));
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileMessage(message));
        }
    }

    //Endpoint to delete directory
    @ApiOperation(value = "Eliminación de Directorio"
            ,notes = "Se elimina un directorio del directorio principal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/delete/{directory}")
    public ResponseEntity<FileMessage> deleteDirectory(@PathVariable String directory) {
        String message = "";
        try {
            message = fileService.deleteDirectory(directory);
            return ResponseEntity.status(HttpStatus.OK).body(new FileMessage(message));
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileMessage(message));
        }
    }

    //Endpoint para eliminar subdirectorio
    @ApiOperation(value = "Eliminación de Subdirectorio"
            ,notes = "Se elimina un subdirectorio de un directorio")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT') or hasRole('ADMINFINANZAS') or hasRole('REPORTESFINANZAS') or hasRole('ADMINCALIDAD') or hasRole('ADMINPDA') or hasRole('ADMINLOGISTICA')")
    @GetMapping("/delete/directory/{directory}/subdirectory/{subdirectory}")
    public ResponseEntity<FileMessage> deleteSubDirectory(@PathVariable String directory, @PathVariable String subdirectory) {
        String message = "";
        try {
            message = fileService.deleteSubdirectory(directory, subdirectory);
            return ResponseEntity.status(HttpStatus.OK).body(new FileMessage(message));
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileMessage(message));
        }
    }

    //Endpoint para eliminar archivo de un subdirectorio
    @ApiOperation(value = "Eliminación de archivo de un subdirectorio"
            ,notes = "Se elimina un archivo de un subdirectorio")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/delete/directory/{directory}/subdirectory/{subdirectory}/{internalfile}")
    public ResponseEntity<FileMessage> deleteInterFileofSubdirectory(@PathVariable String directory, @PathVariable String subdirectory, @PathVariable String internalfile) {
        String message = "";
        try {
            message = fileService.deleteInternalFileOfSubdirectory(directory, subdirectory, internalfile);
            return ResponseEntity.status(HttpStatus.OK).body(new FileMessage(message));
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileMessage(message));
        }
    }

    //Endpoint para eliminar archivo de un directorio interno de un subdirectorio
    @ApiOperation(value = "Eliminación de archivo de un directorio interno de un subdirectorio"
            ,notes = "Se elimina un archivo de un directorio interno de un subdirectorio")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/delete/directory/{directory}/subdirectory/{subdirectory}/internal/{internal}")
    public ResponseEntity<FileMessage> deleteFileOfInterno(@PathVariable String directory, @PathVariable String subdirectory, @PathVariable String internal) {
        String message = "";
        try {
            message = fileService.deleteInternalFile(directory, subdirectory, internal);
            return ResponseEntity.status(HttpStatus.OK).body(new FileMessage(message));
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileMessage(message));
        }
    }

    //Endpoint para eliminar archivo de un interno
    @ApiOperation(value = "Eliminación de archivo de un interno de último nivel"
            ,notes = "Se elimina un archivo de un interno de último nivel")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/delete/directory/{directory}/subdirectory/{subdirectory}/internal/{internal}/file/{file}")
    public ResponseEntity<FileMessage> deleteFileOfInternoLastLevel(@PathVariable String directory, @PathVariable String subdirectory, @PathVariable String internal, @PathVariable String file) {
        String message = "";
        try {
            message = fileService.deleteInternalFileLastLevel(directory, subdirectory, internal, file);
            return ResponseEntity.status(HttpStatus.OK).body(new FileMessage(message));
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileMessage(message));
        }
    }

    //Endpoint para descargar archivos
    @ApiOperation(value = "Descarga de Imágenes"
            ,notes = "Se obtiene una imagen o un archivo a través de los parámetros configurados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @RequestMapping(path = "/download/{type}/{id}/{filename:.+}", method = RequestMethod.GET)
    public void download(HttpServletResponse response, @PathVariable String type, @PathVariable String id, @PathVariable String filename) throws Exception {

        if(type.equals("recibos")){
            if(userService.existsByUsername(JwtTokenFilter.usernameOnToken)){
                User user = userService.getByUsername(JwtTokenFilter.usernameOnToken).get();
                if(String.valueOf(user.getCuil()).equals(id)){
                    pathdownload = Paths.get("data/recibos/" + id);
                }
            }
        } else if (type.equals("bienvenidas") || type.equals("finanzas") || type.equals("calidad") || type.equals("rentabilidades") || type.equals("cuentasporcobrar") || type.equals("gestionpda") || type.equals("gestionlogistica")) {
            pathdownload = Paths.get("data/" + type + "/");
        }else{
            pathdownload = Paths.get("data/" + type + "/" + id);
        }

        try {
            //Busco si el archivo existe en el directorio real
            Path files = pathdownload.resolve(filename);
            Resource resource = new UrlResource(files.toUri());

            File file = new File(pathdownload + "/" + filename);

            // Llevando objeto de entrada
            FileInputStream fis = new FileInputStream(file);
            // Establecer el formato relevante
            response.setContentType("application/force-download");
            // Establecer el nombre y el encabezado del archivo descargado
            response.addHeader("Content-disposition", "attachment;fileName=" + filename);
            // Crear objeto de salida
            OutputStream os = response.getOutputStream();
            // operación normal
            byte[] buf = new byte[1024];
            int len = 0;
            while((len = fis.read(buf)) != -1) {
                os.write(buf, 0, len);
            }
            fis.close();

        }catch (MalformedURLException e){
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @ApiOperation(value = "Búsqueda y descarga de una foto dentro de una carpeta"
            ,notes = "Se obtiene una foto de a través del ID de la carpeta donde se encuentra")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @RequestMapping(path = "/download/{type}/{cuil}", method = RequestMethod.GET)
    public ResponseEntity downloadImgUser(@PathVariable String cuil, @PathVariable String type) throws Exception {

        if (type.equals("calidad") || type.equals("finanzas") || type.equals("rentabilidades") || type.equals("cuentasporcobrar")  || type.equals("gestionpda") || type.equals("gestionlogistica")){
            pathdownload = Paths.get("data/" + type + "/");
        }else{
            pathdownload = Paths.get("data/" + type + "/" + cuil);
        }

        //Busco la carpeta y traigo las fotos

        List<FileModel> img = fileService.loadImgsFromIdOrCUILDirectory(type, cuil).map(path -> {
            String filename = path.getFileName().toString();
            return new FileModel(filename);
        }).collect(Collectors.toList());

        //Selecciono la primera que encuentra, ya que cada carpeta de colaborador contiene una sola foto
        String filename = img.get(0).getName();

        //Verifico si el recurso existe en la carpeta y lo retorno (esto no debería dar error, ya que si existe)
        try {

            Resource file = fileService.load(filename, type, cuil);

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\""+ file.getFilename() + "\"").body(file);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    //Endpoint para descargar archivos de carpeta interna
    @ApiOperation(value = "Descarga de Imagen de Carpeta Interna"
            ,notes = "Se descarga una imagen ubicada en un directorio interno")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @RequestMapping(path = "/download/{type}/{id}/interno/{filename:.+}", method = RequestMethod.GET)
    public void downloadInternal(HttpServletResponse response, @PathVariable String type, @PathVariable String id, @PathVariable String filename) throws Exception {
        // Dirección del archivo, el entorno real se almacena en la base de datos
        //Creamos el path para el directorio por id

        if(type.equals("tickets")){
            pathdownload =  Paths.get("data/tickets/" + id + "/interno");
        }else{
            pathdownload = Paths.get("data/" + type + "/" + id);
        }

        try {
            //Busco si el archivo existe en el directorio real
            Path files = pathdownload.resolve(filename);
            Resource resource = new UrlResource(files.toUri());

            File file = new File(pathdownload + "/" + filename);

            // Llevando objeto de entrada
            FileInputStream fis = new FileInputStream(file);
            // Establecer el formato relevante
            response.setContentType("application/force-download");
            // Establecer el nombre y el encabezado del archivo descargado
            response.addHeader("Content-disposition", "attachment;fileName=" + filename);
            // Crear objeto de salida
            OutputStream os = response.getOutputStream();
            // operación normal
            byte[] buf = new byte[1024];
            int len = 0;
            while((len = fis.read(buf)) != -1) {
                os.write(buf, 0, len);
            }
            fis.close();

        }catch (MalformedURLException e){
            throw new RuntimeException("Error: " + e.getMessage());
        }

    }

    //Endpoint para cambiar foto de perfil
    @ApiOperation(value = "Actualización de Foto de Colaborador"
            ,notes = "Se actualiza la foto de perfil de un colaborador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/change")
    public ResponseEntity<FileMessage> uploadFiles(@RequestParam("img")MultipartFile file, @RequestParam("id")String id, @RequestParam("type")String type){
        String message = "";
        try{

            fileService.change(file, id, type);

            message = "El cambio se realizo correctamente";
            return ResponseEntity.status(HttpStatus.OK).body(new FileMessage(message));
        }catch (Exception e){
            message = "No se pudo actualizar el archivo";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileMessage(message));
        }
    }

    //Endpoint para mostrar imagenes estaticas
    @ApiOperation(value = "Muestra de Imagen"
            ,notes = "Se muestra una imagen")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @RequestMapping(path = "/show/{fileName:.+}", method = RequestMethod.GET)
    public ResponseEntity show(@PathVariable String fileName){

        try {

            //Prueba para descargar archivo desde nueva pestana
            Resource file = fileService.loadFilesByFilename(fileName);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\""+ file.getFilename() + "\"").body(file);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    /* Endpoints Carpetas de Archivos*/

    @ApiOperation(value = "Contenido del directorio principal"
            ,notes = "Se obtiene un listado con los directorios que contienen el principal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/principalDirectory")
    public ResponseEntity<List<DirectoryModel>> getPrincipal(){

        List<DirectoryModel> fileInfos = fileService.viewPrincipal().map(path -> {
            String filename = path.getFileName().toString();
            String type = "directory";
            return new DirectoryModel(filename, type);
        }).collect(Collectors.toList());

        //Elimino el primer elemento del arreglo, que es el directorio principal "recibos"
        fileInfos.remove(0);

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @ApiOperation(value = "Listado de Carpetas de un Directorio"
            ,notes = "Se obtiene un listado con las carpetas que contiene un directorio")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT') or hasRole('ADMINFINANZAS') or hasRole('REPORTESFINANZAS') or hasRole('ADMINCALIDAD') or hasRole('ADMINPDA') or hasRole('ADMINLOGISTICA')")
    @RequestMapping(path = "/directory", method = RequestMethod.GET)
    public ResponseEntity<List<DirectoryModel>> getDirectoryCont(String directory){

        List<DirectoryModel> fileInfos = fileService.viewDirectories(directory).map(path -> {
            String filename = path.getFileName().toString();

            pathdownload = Paths.get("data/" + directory);

            File pathToConsult = new File(pathdownload + "/" + filename);

            String type = "";
            if(pathToConsult.isDirectory()){
                type = "subdirectory";
            }else if (pathToConsult.isFile()){
                type = "fileSubDirectory";
            }
            return new DirectoryModel(filename, type);
        }).collect(Collectors.toList());

        //Elimino el primer elemento del arreglo, que es el directorio principal "recibos"
        fileInfos.remove(0);

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @ApiOperation(value = "Listado de Carpetas de un Subdirectorio"
            ,notes = "Se obtiene un listado con las carpetas que contiene un subdirectorio")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @RequestMapping(path = "/directory/subdirectory", method = RequestMethod.GET)
    public ResponseEntity<List<DirectoryModel>> getDirSubdirCont(String directory, String subDirectory){

        List<DirectoryModel> fileInfos = fileService.loadFilesForDirectory(directory, subDirectory).map(path -> {
            String filename = path.getFileName().toString();

            pathdownload = Paths.get("data/" + directory + "/" + subDirectory);

            File pathToConsult = new File(pathdownload + "/" + filename);

            String type = "";
            if(pathToConsult.isDirectory()){
                type = "folder";
            }else if (pathToConsult.isFile()){
                type = "file";
            }
            return new DirectoryModel(filename, type);
        }).collect(Collectors.toList());

        //Elimino el primer elemento del arreglo, que es el directorio principal "recibos"
        fileInfos.remove(0);

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @ApiOperation(value = "Listado de archivos que contiene un directorio interno"
            ,notes = "Se obtiene un listado con las carpetas que contiene un directorio")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @RequestMapping(path = "/directory/subdirectory/internal", method = RequestMethod.GET)
    public ResponseEntity<List<DirectoryModel>> getFilesInternalDirectory(String directory, String subDirectory, String internalDirectory){

        List<DirectoryModel> fileInfos = fileService.loadFilesForInternalDirectory(directory, subDirectory, internalDirectory).map(path -> {
            String filename = path.getFileName().toString();
            String type = "fileInterno";
            return new DirectoryModel(filename, type);
        }).collect(Collectors.toList());

        //Elimino el primer elemento del arreglo, que es el directorio principal "recibos"
        fileInfos.remove(0);

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }
}

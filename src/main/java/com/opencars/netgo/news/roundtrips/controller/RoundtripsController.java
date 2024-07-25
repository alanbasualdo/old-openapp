package com.opencars.netgo.news.roundtrips.controller;

import com.opencars.netgo.locations.entity.Company;
import com.opencars.netgo.locations.entity.Sector;
import com.opencars.netgo.locations.entity.SubSector;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.news.roundtrips.dto.RoundtripsList;
import com.opencars.netgo.news.roundtrips.dto.RoundtripsSummary;
import com.opencars.netgo.news.roundtrips.entity.Roundtrips;
import com.opencars.netgo.news.roundtrips.service.RoundtripsService;
import com.opencars.netgo.users.entity.User;
import com.opencars.netgo.users.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Circulares")
public class RoundtripsController {

    @Autowired
    RoundtripsService roundtripsService;

    @Autowired
    UserService userService;

    //Crear una circular
    @ApiOperation(value = "Creación de una circular"
            ,notes = "Se envía un objeto de tipo roundtrips a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCIRCULARES') or hasRole('ADMINMKT')")
    @PostMapping("/roundtrips")
    public ResponseEntity<?> create(@Valid @RequestBody Roundtrips roundtrips, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        if (roundtripsService.existsBytitle(roundtrips.getTitle()))
            return new ResponseEntity(new Msg("Ya existe una circular con ese título"), HttpStatus.BAD_REQUEST);

        String stringDate = LocalDate.now().toString();
        LocalDateTime date = LocalDateTime.now();
        LocalDate shortDate = LocalDate.now();

        String[] parts = stringDate.split("-");
        int part0 = Integer.parseInt(parts[0]); // yyyy
        int part1 = Integer.parseInt(parts[1]); // MM
        int part2 = Integer.parseInt(parts[2]); // dd

        String month = "";
        switch (part1){
            case 1: {
                month = "enero";
                break;
            }
            case 2: {
                month = "febrero";
                break;
            }
            case 3: {
                month = "marzo";
                break;
            }
            case 4: {
                month = "abril";
                break;
            }
            case 5: {
                month = "mayo";
                break;
            }
            case 6: {
                month = "junio";
                break;
            }
            case 7: {
                month = "julio";
                break;
            }
            case 8: {
                month = "agosto";
                break;
            }
            case 9: {
                month = "septiembre";
                break;
            }
            case 10: {
                month = "octubre";
                break;
            }
            case 11: {
                month = "noviembre";
                break;
            }
            case 12: {
                month = "diciembre";
                break;
            }
        }
        Roundtrips newRoundtrip =
                new Roundtrips(
                        roundtrips.getTitle(),
                        roundtrips.getDescription(),
                        roundtrips.getType(),
                        roundtrips.getSubSector(),
                        roundtrips.getBanner(),
                        roundtrips.getFooter(),
                        roundtrips.getAttacheds(),
                        roundtrips.getPublished(),
                        roundtrips.getAudience(),
                        roundtrips.getCompany(),
                        roundtrips.getCreatedBy()
                );
        String dateCurrent = part2 + " de " + month + " de " + part0;
        newRoundtrip.setStringDate(dateCurrent);
        newRoundtrip.setDate(date);
        newRoundtrip.setShortDate(shortDate);

        roundtripsService.save(newRoundtrip);
        Roundtrips roundtripCreated = roundtripsService.getByTitle(roundtrips.getTitle()).get();

        return new ResponseEntity(roundtripCreated, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Actualización de una Circular"
            ,notes = "Se actualiza una circular a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCIRCULARES') or hasRole('ADMINMKT')")
    @PutMapping("/roundtrips/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Roundtrips roundtrip){
        Roundtrips roundtripUpdated = roundtripsService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Roundtrip not found for this id :: " + id));

        String stringDate = LocalDate.now().toString();
        LocalDateTime date = LocalDateTime.now();
        LocalDate shortDate = LocalDate.now();

        String[] parts = stringDate.split("-");
        int part0 = Integer.parseInt(parts[0]); // yyyy
        int part1 = Integer.parseInt(parts[1]); // MM
        int part2 = Integer.parseInt(parts[2]); // dd

        String month = "";
        switch (part1){
            case 1: {
                month = "enero";
                break;
            }
            case 2: {
                month = "febrero";
                break;
            }
            case 3: {
                month = "marzo";
                break;
            }
            case 4: {
                month = "abril";
                break;
            }
            case 5: {
                month = "mayo";
                break;
            }
            case 6: {
                month = "junio";
                break;
            }
            case 7: {
                month = "julio";
                break;
            }
            case 8: {
                month = "agosto";
                break;
            }
            case 9: {
                month = "septiembre";
                break;
            }
            case 10: {
                month = "octubre";
                break;
            }
            case 11: {
                month = "noviembre";
                break;
            }
            case 12: {
                month = "diciembre";
                break;
            }
        }

        String dateCurrent = part2 + " de " + month + " de " + part0;

        roundtripUpdated.setTitle(roundtrip.getTitle());
        roundtripUpdated.setDescription(roundtrip.getDescription());
        roundtripUpdated.setType(roundtrip.getType());
        roundtripUpdated.setSubSector(roundtrip.getSubSector());
        roundtripUpdated.setDate(date);
        roundtripUpdated.setBanner(roundtrip.getBanner());
        roundtripUpdated.setFooter(roundtrip.getFooter());
        roundtripUpdated.setAttacheds(roundtrip.getAttacheds());
        roundtripUpdated.setStringDate(dateCurrent);
        roundtripUpdated.setPublished(roundtrip.getPublished());
        roundtripUpdated.setShortDate(shortDate);
        roundtripUpdated.setAudience(roundtrip.getAudience());
        roundtripUpdated.setCompany(roundtrip.getCompany());
        roundtripUpdated.setCreatedBy(roundtrip.getCreatedBy());

        roundtripsService.save(roundtripUpdated);

        return ResponseEntity.ok(roundtripUpdated);
    }

    @ApiOperation(value = "Lista de Circulares Por Área y Estado de Publicación"
            ,notes = "Se obtiene una lista de circulares por área y estado de publicación.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/roundtrips/{area}/state/{published}")
    public List<RoundtripsList> getRoundtripsList(@PathVariable("area") int area, @PathVariable("published") int published){

        List<RoundtripsList> list = roundtripsService.getByTypeAndPublishedState(area, published);

        return list;

    }

    @ApiOperation(value = "Lista de Circulares por coincidencia en el título"
            ,notes = "Se obtiene una lista de circulares por coincidencia en el título")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/roundtrips/title/{title}")
    public List<RoundtripsList> getRoundtripsByCoincidenceInTitle(@PathVariable("title") String title){

        List<RoundtripsList> list = roundtripsService.getByCoincidenceInTitle(title);

        return list;
    }

    @ApiOperation(value = "Lista de Circulares Por Subárea, Empresa y Estado de Publicación"
            ,notes = "Se obtiene una lista de circulares por subárea, empresa y estado de publicación.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/roundtrips/subsector/{subsector}/company/{company}/state/{published}")
    public List<RoundtripsList> getRoundtripsListBySubsectorAndCompany(@PathVariable("subsector") int subsector, @PathVariable("company") int company, @PathVariable("published") int published){

        List<RoundtripsList> list = roundtripsService.getBySubsectorAndCompanyAndPublishedState(subsector, company, published);

        return list;

    }

    @ApiOperation(value = "Lista de Todas las Circulares en estado pendiente"
            ,notes = "Se obtiene una lista de las circulares de todas las áreas en estado pendiente.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINMKT')")
    @GetMapping("/roundtrips/all/notpublished")
    public List<RoundtripsList> getRoundtripsList(){

        List<RoundtripsList> list = roundtripsService.getAllPendings();

        return list;

    }

    @ApiOperation(value = "Lista de circulares por creador"
            ,notes = "Se obtiene una lista de circulares por creador.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/roundtrips/createdby/{createdBy}")
    public List<RoundtripsList> getRoundtripsList(@PathVariable int createdBy){

        List<RoundtripsList> list = roundtripsService.getByCreator(createdBy);

        return list;

    }

    @ApiOperation(value = "Lista de Todas las Circulares por mes y año"
            ,notes = "Se obtiene una lista de las circulares de todas las áreas por mes y año.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/roundtrips/all/month/{month}/year/{year}")
    public List<RoundtripsList> getAllByMonthAndYear(@PathVariable("month") int month, @PathVariable("year") int year){

        List<RoundtripsList> list = roundtripsService.getAllByMonthAndYear(month, year);

        return list;

    }

    //Obtener circular por id
    @ApiOperation(value = "Circular por ID"
            ,notes = "Se obtiene una circular específica a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/roundtrips/id/{id}")
    public List<Roundtrips> getById(@PathVariable("id") int id){

        List<Roundtrips> list = roundtripsService.getById(id);

        return list;
    }

    @ApiOperation(value = "Lista Semanal de Circulares"
            ,notes = "Se obtiene una lista de circulares de los últimos 7 días")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/roundtrips/summary")
    public List<RoundtripsSummary> getRoundtripsForSummary(){

        List<RoundtripsSummary> list = roundtripsService.getRoundtripsSummary();

        return list;

    }

    @ApiOperation(value = "Contador de Circulares Actuales"
            ,notes = "Se otiene la cantidad de circulares actuales")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/roundtrips/current/count")
    public int getCountRoundtripsCurrents(){
        int count = roundtripsService.countRoundtripsByDate();
        return count;
    }

    @ApiOperation(value = "Contador de Circulares Actuales Por Área"
            ,notes = "Se otiene la cantidad de circulares actuales por área")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/roundtrips/current/area/{area}/count")
    public int getCountRoundtripsCurrentsByArea(@PathVariable("area") Sector area){
        int count = roundtripsService.countRoundtripsByDateAndSector(area);
        return count;
    }

    @ApiOperation(value = "Contador de Circulares Actuales Por Subárea"
            ,notes = "Se otiene la cantidad de circulares actuales por subárea")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/roundtrips/current/subarea/{subarea}/count")
    public int getCountRoundtripsCurrentsBySubsector(@PathVariable("subarea") SubSector subarea){
        int count = roundtripsService.countRoundtripsByDateAndSubSector(subarea);
        return count;
    }

    @ApiOperation(value = "Contador de Circulares Actuales Por Subárea y Empresa"
            ,notes = "Se otiene la cantidad de circulares actuales por subárea y empresa")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/roundtrips/current/subarea/{subarea}/company/{company}/count")
    public int getCountRoundtripsCurrentsBySubsectorAndCompany(@PathVariable("subarea") SubSector subarea, @PathVariable("company") Company company){
        int count = roundtripsService.countRoundtripsByDateAndSubSectorAndCompany(subarea, company);
        return count;
    }

    @ApiOperation(value = "Contador de Circulares por área, en Estado Pendiente"
            ,notes = "Se obtiene la cantidad de circulares por área que no han sido publicadas.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCIRCULARES') or hasRole('ADMINMKT')")
    @GetMapping("/roundtrips/count/area/{area}/notpublished")
    public int countRoundtripsNotPublishedByArea(@PathVariable("area") Sector area){
        int count = roundtripsService.countRoundtripsNotPublishedByArea(area);
        return count;
    }

    @ApiOperation(value = "Contador de Circulares en Estado Pendiente"
            ,notes = "Se obtiene la cantidad de circulares que no han sido publicadas.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINMKT')")
    @GetMapping("/roundtrips/count/notpublished")
    public int countAllRoundtripsNotPublished(){
        int count = roundtripsService.countAllRoundtripsNotPublished();
        return count;
    }

    @ApiOperation(value = "Eliminación de Circulares por ID"
            ,notes = "Se elimina una circular a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('ADMINCIRCULARES') or hasRole('ADMINMKT')")
    @DeleteMapping("/roundtrips/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            roundtripsService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Circular Eliminada", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Actualización de Likes"
            ,notes = "Se actualizan los likes de una circular.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/roundtrips/{id}/likes/user/{iduser}")
    public ResponseEntity<Roundtrips> updateLikes(@PathVariable("id") int id, @PathVariable("iduser") int iduser) {
       try {

            Roundtrips roundtrip = roundtripsService.getOne(id).get();

            SortedSet<User> likes = new TreeSet<>();
            roundtrip.getLikes().stream().forEach(data ->

                   likes.add(data)
            );

            List<User> listLikes = new ArrayList<>(likes);

            boolean contain = false;

            for (int i = 0; i < listLikes.size(); i++) {
                if (listLikes.get(i).getId() == iduser) {
                    listLikes.remove(i);
                    contain = true;
                }
            }

           SortedSet<User> usersSet = new TreeSet<>(listLikes);

           User userToAdd = this.userService.getOne(iduser).get();

            if(!contain){
                usersSet.add(userToAdd);
            }

            roundtrip.setLikes(usersSet);
            roundtripsService.save(roundtrip);
            return new ResponseEntity(new Msg("Likes actualizados", HttpStatus.OK.value()), HttpStatus.OK);

        } catch (Exception e) {
           return new ResponseEntity(new Msg("Error: " + e, HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Actualización de vistas de una circular"
            ,notes = "Se incrementa en uno, la cantidad de vistas de una circular.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/roundtrips/{id}/views")
    public ResponseEntity<Roundtrips> updateViews(@PathVariable("id") int id) {
        try {

            Roundtrips roundtrip = roundtripsService.getOne(id).get();

            int views = roundtrip.getViews();
            views = views + 1;
            roundtrip.setViews(views);
            roundtripsService.save(roundtrip);
            return new ResponseEntity<Roundtrips>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

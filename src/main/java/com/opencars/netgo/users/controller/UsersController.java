package com.opencars.netgo.users.controller;

import com.opencars.netgo.auth.entity.Rol;
import com.opencars.netgo.locations.entity.Sector;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.msgs.MsgInt;
import com.opencars.netgo.support.mobiles.controller.MobilesController;
import com.opencars.netgo.support.mobiles.entity.Mobiles;
import com.opencars.netgo.support.mobiles.service.MobilesService;
import com.opencars.netgo.support.pcs.controller.PcsController;
import com.opencars.netgo.support.pcs.entity.Pcs;
import com.opencars.netgo.support.pcs.service.PcsService;
import com.opencars.netgo.users.dto.*;
import com.opencars.netgo.users.entity.Employee;
import com.opencars.netgo.users.entity.Position;
import com.opencars.netgo.users.entity.User;
import com.opencars.netgo.users.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Usuarios")
public class UsersController {

    @Autowired
    UserService userService;

    @Autowired
    PcsService pcsService;

    @Autowired
    MobilesService mobilesService;

    @Autowired
    PcsController pcsController;

    @Autowired
    MobilesController mobilesController;

    @Autowired
    PasswordEncoder passwordEncoder;

    @ApiOperation(value = "Creación de un Usuario"
            ,notes = "Se envía un objeto de tipo user a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/users")
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Alguno de los datos no coinciden con el formato esperado"), HttpStatus.BAD_REQUEST);
        if (userService.existsByUsername(user.getUsername()))
            return new ResponseEntity(new Msg("Ese nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);
        if (userService.existsByMail(user.getMail()))
            return new ResponseEntity(new Msg("Ese email ya existe"), HttpStatus.BAD_REQUEST);

        String message = "";

        User newUser =
                new User(

                        user.getBirthDate(),
                        user.getBranch(),
                        user.getPayroll(),
                        user.getCuil(),
                        user.getEgressDate(),
                        user.getEnable(),
                        user.getEntryDate(),
                        user.getImgProfile(),
                        user.getMail(),
                        user.getName(),
                        passwordEncoder.encode(user.getPassword()),
                        user.getUsername(),
                        user.getPositions(),
                        user.getRoles(),
                        user.getGender(),
                        0

                );
        SortedSet<Rol> roles = new TreeSet<>(user.getRoles());
        newUser.setRoles(roles);

        try{
            userService.save(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Msg("Usuario Guardado", HttpStatus.CREATED.value()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de un Usuario"
            ,notes = "Se actualiza un usuario a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/users/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody User user){
       User userUpdated = userService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));;
        userUpdated.setBirthDate(user.getBirthDate());
        userUpdated.setBranch(user.getBranch());
        userUpdated.setPayroll(user.getPayroll());
        userUpdated.setCuil(user.getCuil());
        userUpdated.setEgressDate(user.getEgressDate());
        userUpdated.setEnable(user.getEnable());
        userUpdated.setEntryDate(user.getEntryDate());
        userUpdated.setImgProfile(user.getImgProfile());
        userUpdated.setMail(user.getMail());
        userUpdated.setName(user.getName());
        userUpdated.setPassword(user.getPassword());
        userUpdated.setUsername(user.getUsername());
        userUpdated.setPositions(user.getPositions());
        SortedSet<Rol> roles = new TreeSet<>();
        user.getRoles().stream().forEach(data ->
                roles.add(data)
        );
        userUpdated.setRoles(roles);
        userUpdated.setGender(user.getGender());
        userUpdated.setPasswordState(user.getPasswordState());
        userUpdated.setCbu(user.getCbu());
        userService.save(userUpdated);

        return ResponseEntity.ok(userUpdated);
    }

    @ApiOperation(value = "Lista de Usuarios Sin Paginación"
            ,notes = "Se obtiene una lista de usuarios sin paginar")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/notpaginated")
    public List<UsersData> getUsersNotPaginated(){

        List<UsersData> usersData = userService.getUsers();

        return usersData;
    }

    @ApiOperation(value = "Lista de Usuarios"
            ,notes = "Se obtiene una lista de usuarios paginada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT') or hasRole('VIEWPERMISSIONS') or hasRole('VIEWDLS')")
    @GetMapping("/users")
    public Page<User> getUsers(@PageableDefault(size = 10, page = 0) Pageable pageable){

        Page<User> list = userService.getAllUsers(pageable);

        return list;
    }

    @ApiOperation(value = "Actualización de Contraseña desde dentro de la plataforma"
            ,notes = "Se actualiza la contraseña de un usuario a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/users/{id}/password/{password}")
    public ResponseEntity<User> updatePassword(@PathVariable int id, @PathVariable String password) {
        try {
            User user = userService.getOne(id).get();
            user.setPassword(passwordEncoder.encode(password));
            user.setPasswordState(1);
            userService.save(user);
            return new ResponseEntity<User>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Actualización de Contraseña luego de tener una contraseña temporal"
            ,notes = "Se actualiza la contraseña de un usuario a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PatchMapping("/users/{id}/temp-password/{password}")
    public ResponseEntity<User> updatePasswordFromTemp(@PathVariable int id, @PathVariable String password) {
        try {
            User user = userService.getOne(id).get();
            user.setPassword(passwordEncoder.encode(password));
            user.setPasswordState(1);
            userService.save(user);
            return new ResponseEntity<User>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Actualización de Contraseña Temporal por parte del Administrador"
            ,notes = "Se actualiza la contraseña de un usuario de forma temporal, a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PatchMapping("/users/{id}/password/{password}/changebyadmin")
    public ResponseEntity<User> updatePasswordByAdmin(@PathVariable int id, @PathVariable String password) {
        try {
            User user = userService.getOne(id).get();
            user.setPassword(passwordEncoder.encode(password));
            user.setPasswordState(0);
            userService.save(user);
            return new ResponseEntity<User>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Desactivación del Usuario"
            ,notes = "Se actualiza el estado de un usuario a través de su ID a desactivado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('ADMINIT')")
    @PatchMapping(value = "/users/{id}/enable/{enable}")
    public ResponseEntity<User> disableUser(@PathVariable int id, @PathVariable int enable) {
        try {

            if(enable == 0){

                User user = userService.getOne(id).get();
                user.setEnable(enable);

                List<Pcs> list = pcsService.getByNameUser(user.getName());

                list.stream().forEach(data ->
                        pcsController.patchPc(data.getId(), "Devolucion")

                );

                List<Mobiles> listMobiles = mobilesService.getByNameUser(user.getName());

                listMobiles.stream().forEach(data ->
                        mobilesController.patchMobile(data.getId(), "Devolucion")

                );

                userService.save(user);
            }else if(enable == 1){
                User user = userService.getOne(id).get();
                user.setEnable(enable);
                userService.save(user);
            }

            return new ResponseEntity<User>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Actualización de Foto del Colaborador"
            ,notes = "Se actualiza el nombre de la foto que el usuario tiene asociada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('USER')")
    @PatchMapping(value = "/users/{id}/img/{imgProfile}")
    public ResponseEntity<User> changeImgProfile(@PathVariable int id, @PathVariable String imgProfile) {
        try {
            User user = userService.getOne(id).get();
            user.setImgProfile(imgProfile);
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Usuario por ID"
            ,notes = "Se obtiene un usuario a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") int id){
        if(!userService.existsById(id))
            return new ResponseEntity(new Msg("no existe"), HttpStatus.NOT_FOUND);
        User user = userService.getOne(id).get();
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @ApiOperation(value = "Usuario por correo"
            ,notes = "Se obtiene un usuario a través de su correo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/mail/{mail}")
    public ResponseEntity<User> getByMail(@PathVariable("mail") String mail){
        if(!userService.existsByMail(mail))
            return new ResponseEntity(new Msg("no existe"), HttpStatus.NOT_FOUND);
        User user = userService.getByMail(mail).get();
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @ApiOperation(value = "Existencia de usuario por cuil"
            ,notes = "Se consulta si un usuario está creado, a través de su cuil")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe")})
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/cuil/{cuil}")
    public boolean existByCuil(@PathVariable("cuil") String cuil) {
        if (!userService.existsByCuil(cuil)){
            return false;
        }else{
            return true;
        }
    }

    @ApiOperation(value = "Obtención de Gerente General"
            ,notes = "Se obtienen los datos del Gerente General")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe")})
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/generalmanager")
    public ResponseEntity<User> getGeneralManager() {

        try {
            User generalmanager = userService.getGeneralManager().get();
            return ResponseEntity.ok(generalmanager);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiOperation(value = "Obtención de Organigrama por Área"
            ,notes = "Se obtiene el organigrama de un área")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe")})
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/organigrama/{sector}")
    public ResponseEntity<Employee> getOrganigramaBySector(@PathVariable("sector") Sector sector) {

        return this.userService.getOrganigramaBySector(sector);

    }

    @ApiOperation(value = "Obtención de Segundas Líneas - Organigrama"
            ,notes = "Se obtienen las segundas líneas de un organigrama")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe")})
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/organigrama/secondslines")
    public ResponseEntity<Employee> getSecondsLines() {

        return this.userService.getSecondsLines();

    }

    @ApiOperation(value = "Obtener id de usuario por cuil"
            ,notes = "Se obtiene el id de un usuario a través de su cuil")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe")})
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/id/cuil/{cuil}")
    public ResponseEntity<?> getUserIdByCuil(@PathVariable("cuil") String cuil) {
        if (userService.existsByCuil(cuil)){
            User user = this.userService.getByCuil(cuil).get();
            return ResponseEntity.status(HttpStatus.OK).body(new MsgInt("Usuario existe", HttpStatus.OK.value(), user.getId()));
        }else{
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg("No existe usuario con ese cuil. Se deberá realizar la carga manual de la foto de perfil.", HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Información de Usuario por Nombre de Usuario"
            ,notes = "Se obtienen la información de un usuario a través del nombre de usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path = "/users/logged", method = RequestMethod.GET)
    public ResponseEntity<User> getByUsername(String username){
        if(!userService.existsByUsername(username))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        User user = userService.getByUsername(username).get();

        return new ResponseEntity(user, HttpStatus.OK);
    }

    @ApiOperation(value = "Id de Usuario por Nombre de Usuario"
            ,notes = "Se obtienen el id de un usuario a través del nombre de usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe")})
    @RequestMapping(path = "/users/id", method = RequestMethod.GET)
    public ResponseEntity<UserId> getUserIdByUsername(String username){
        if(!userService.existsByUsername(username))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        UserId userId = userService.getUserIdByUsername(username).get();

        return new ResponseEntity(userId, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Puestos de Colaboradores"
            ,notes = "Se obtiene una lista con los distintos tipos de puestos de los colaboradores")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path = "/users/position", method = RequestMethod.GET)
    public List<User> getByPosition(@PathVariable Position position){
        List<User> list = userService.getByPosition(position);
        return list;
    }

    @ApiOperation(value = "Lista de Usuarios por Área"
            ,notes = "Se obtiene una lista con los usuarios correspondientes a un área pasado por parámetro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path = "/users/sector/{sector}", method = RequestMethod.GET)
    public List<User> getByPosition(@PathVariable Sector sector){
        List<User> list = userService.getUsersBySector(sector);
        return list;
    }

    @ApiOperation(value = "Lista de Cumpleaños"
            ,notes = "Se obtiene una lista con los cumpleaños del día corriente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/birthday")
    public ResponseEntity<User> getBirthdays(){
        List<Birthday> list = userService.getBirthdays();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Cumpleaños Semanal"
            ,notes = "Se obtiene una lista con los cumpleaños de la semana")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/summary/birthday")
    public List<Birthday> getBirthdaysForSummary(){
        List<Birthday> list = userService.getBirthdaysForSummary();
        Collections.sort(list, new SortByDate());
        return list;
    }

    static class SortByDate implements Comparator<Birthday> {
        @Override
        public int compare(Birthday a, Birthday b) {
            return b.getDate().compareTo(a.getDate());
        }
    }

    @ApiOperation(value = "Contador de Cumpleaños"
            ,notes = "Se obtiene la cantidad de cumpleaños del día actual")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/birthday/count")
    public int getCountBirthdays(){
        List<Birthday> list = userService.getBirthdays();
        int count = list.size();
        return count;
    }

    @ApiOperation(value = "Lista de Usuarios por Nombre"
            ,notes = "Se obtiene una lista de usuarios por coincidencia en el nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/name/{name}")
    public List<User> getUsersByName(@PathVariable("name") String name){

        List<User> list = userService.getByName(name);

        return list;
    }

    @ApiOperation(value = "Lista de Usuarios Completos Activos por Nombre"
            ,notes = "Se obtiene una lista de usuarios completos activos por coincidencia en el nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/enableds/name/{name}")
    public List<User> getUsersCompletesEnabledsByName(@PathVariable("name") String name){

        List<User> list = userService.getUsersEnabledsByName(name);

        return list;
    }

    @ApiOperation(value = "Lista de Usuarios Activos por Nombre"
            ,notes = "Se obtiene una lista de usuarios activos por coincidencia en el nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/enableds/coincidence/name/{name}")
    public List<UserNames> getUsersEnabledsByName(@PathVariable("name") String name){

        List<UserNames> list = userService.getUsersNamesEnabledsByNames(name);

        return list;
    }

    @ApiOperation(value = "Lista de Usuarios Por Posición y coincidencia en el Nombre"
            ,notes = "Se obtiene una lista de usuarios por posición y por coincidencia en el nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/name/{name}/position/{position}")
    public List<User> getUsersByPositionAndNameCoincidence(@PathVariable("name") String name, @PathVariable("position") Position position){

        List<User> list = userService.getUsersByPositionAndNameCoincidence(name, position);

        return list;
    }

    @ApiOperation(value = "Lista de Usuarios por rol"
            ,notes = "Se obtiene una lista de usuarios por un rol específico")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT') or hasRole('ADMINCCHH')")
    @GetMapping("/users/roles/{role}")
    public List<User> getUsersByRole(@PathVariable("role") Rol role){
        List<User> users = userService.getByRole(role);
        return users;
    }

}

package com.opencars.netgo.auth.controller;

import com.opencars.netgo.auth.dto.ActivesBranch;
import com.opencars.netgo.auth.dto.JwtDto;
import com.opencars.netgo.auth.dto.ListsSessions;
import com.opencars.netgo.auth.dto.LoginUser;
import com.opencars.netgo.auth.entity.Sesiones;
import com.opencars.netgo.auth.jwt.JwtProvider;
import com.opencars.netgo.auth.service.SesionesService;
import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.users.dto.UsersConnections;
import com.opencars.netgo.users.entity.User;
import com.opencars.netgo.users.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
@Api(tags = "Controlador de Autenticación")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    SesionesService sesionesService;

    @Autowired
    UserService userService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${jwt.expiration}")
    private int expiration;

    @ApiOperation(value = "Pre Login"
            ,notes = "Se envía usuario y contraseña a través de un payload para verificar el estado de cambio de contraseña")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PostMapping("/prelogin")
    public int prelogin(@Valid @RequestBody LoginUser loginUser){

        User user = userService.getByUsername(loginUser.getUsername()).get();

        boolean isPasswordMatch = passwordEncoder.matches(loginUser.getPassword(), user.getPassword());

        if (user.getEnable() == 1){

            if(user.getUsername().equals(loginUser.getUsername()) && isPasswordMatch && user.getPasswordState() == 0){
                //Si el usuario tiene que cambiar la clave, retorna 0
                return 0;
            }else if(user.getUsername().equals(loginUser.getUsername()) && isPasswordMatch && user.getPasswordState() == 1){
                //Si no es necesario cambiar la clave, retorna 1
                return 1;
            }else{
                //Si los datos de inicio de sesión no son correctos, retorna 2
                return 2;
            }
        }else{
            //si el usuario no está habilitado retorna 3
            return 3;
        }
    }

    @ApiOperation(value = "Inicio de sesión"
            ,notes = "Se envía usuario y contraseña a través de un payload, y token de navegador por parámetro, devuelve como resultado un objeto de tipo UserMain")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PostMapping("/login/tokenfb/{tokenfb}")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUser loginUser, @PathVariable("tokenfb") String tokenfb, BindingResult bindingResult) throws IOException {

        Date date = new Date();
        LocalDate today = LocalDate.now();

        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);

        User user = userService.getByUsername(loginUser.getUsername()).get();

        //Si el usuario está habilitado
        if (user.getEnable() == 1){

            //Genero el token JWT
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities() );

            List<Sesiones> userSessions = sesionesService.getByColaborator(user);

            //Si no tiene ninguna sesión
            if (userSessions.size() == 0){

                //Creo una sesión nueva, y la guardo
                Sesiones sesion = new Sesiones(user, jwt, date, new Date(new Date().getTime() + expiration * 1000), "Activo", tokenfb, today);
                sesionesService.save(sesion);

            //Si tiene sesiones
            }else {
                //Traigo las sesiones inactivas
                List<Sesiones> inactivesSessions = sesionesService.getByColaboratorAndActive(user, "Inactivo");

                //Traigo las sesiones inactivas si es que tiene, y las elimino
                if (inactivesSessions.size() > 0) {
                    for (Sesiones inactives : inactivesSessions) {
                        sesionesService.deleteById(inactives.getId());
                    }

                    //Creo una sesión nueva, y la guardo
                    Sesiones sesion = new Sesiones(user, jwt, date, new Date(new Date().getTime() + expiration * 1000), "Activo", tokenfb, today);
                    sesionesService.save(sesion);

                } else {
                    //Creo una sesión nueva, y la guardo
                    Sesiones sesion = new Sesiones(user, jwt, date, new Date(new Date().getTime() + expiration * 1000), "Activo", tokenfb, today);
                    sesionesService.save(sesion);
                }
            }

            return new ResponseEntity(jwtDto, HttpStatus.OK);

        }else{
            return new ResponseEntity(new Msg("Usuario desactivado"), HttpStatus.valueOf(HttpServletResponse.SC_FORBIDDEN));
        }
    }

    @ApiOperation(value = "Cierre de sesión"
            ,notes = "Se envía usuario y contraseña a través de un payload y si los datos son válidos, se actualiza la información de la sesión")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PostMapping("/logout/tokenfb/{tokenfb}")
    public ResponseEntity<Msg> logout(@Valid @RequestBody LoginUser loginUser, @PathVariable("tokenfb") String tokenfb, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);

        Date date = new Date();

        User user = userService.getByUsername(loginUser.getUsername()).get();

        boolean passwordMatch = passwordEncoder.matches(loginUser.getPassword(), user.getPassword());

        if (user.getEnable() == 1) {
            if (user.getUsername().equals(loginUser.getUsername()) && passwordMatch) {

                List<Sesiones> userSessions = sesionesService.getByColaborator(user);

                for (Sesiones sesion : userSessions) {

                    if (userSessions.size() > 1) {

                        if (Objects.equals(sesion.getNavToken(), tokenfb)) {

                            sesionesService.deleteById(sesion.getId());
                        }
                    } else {
                        Sesiones sesionUpdated = sesionesService.getOne(sesion.getId()).get();

                        sesionUpdated.setEnd(date);
                        sesionUpdated.setActive("Inactivo");
                        sesionesService.save(sesionUpdated);
                    }
                }

                return new ResponseEntity(new Msg("Sesión Cerrada", HttpStatus.OK.value()), HttpStatus.OK);

            }
        }

        return new ResponseEntity(new Msg("Usuario no autorizado para realizar la acción", HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
    }

    @ApiOperation(value = "Contador de Usuarios Activos"
            ,notes = "Se obtiene la cantidad de usuarios activos en la plataforma")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/actives")
    public int countActiveUsers(){
        int count = sesionesService.countActiveUsers();
        return count;
    }

    @ApiOperation(value = "Contador de Usuarios Inactivos"
            ,notes = "Se obtiene la cantidad de usuarios inactivos en la plataforma")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/inactives")
    public int countInactiveUsers(){
        int count = sesionesService.countInactiveUsers();
        return count;
    }

    @ApiOperation(value = "Contador de Usuarios en línea por sucursal en el día actual"
            ,notes = "Se obtiene la cantidad de usuarios activos en la plataforma en el día corriente, por sucursal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/actives/branch/{branch}")
    public ActivesBranch countActiveUsersForBranch(@PathVariable("branch") Branch branch){
        ActivesBranch activesBranch = sesionesService.countUsersOnlineForDayAndBranch(branch);
        return activesBranch;
    }

    @ApiOperation(value = "Lista de Sesiones de Usuarios Activos"
            ,notes = "Se obtiene una lista de sesiones de usuarios activos sin paginar")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/actives")
    public List<Sesiones> getActivesUsers(){

        List<Sesiones> sesiones = sesionesService.getActives();

        return sesiones;
    }

    @ApiOperation(value = "Lista de Sesiones"
            ,notes = "Se obtiene una lista de sesiones de usuarios sin paginar")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/sessions")
    public ResponseEntity<ListsSessions> getSessions(){

        ResponseEntity<ListsSessions> sesiones = sesionesService.getAllSessionsOrderByDate();
        return sesiones;
    }

    @ApiOperation(value = "Lista de Sesiones por Sucursal"
            ,notes = "Se obtiene una lista de sesiones de usuarios por sucursal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/sessions/branch/{branch}")
    public List<UsersConnections> getSessionsByBranch(@PathVariable("branch") Branch branch){

        List<UsersConnections> sesiones = sesionesService.getSessionsByBranch(branch);
        return sesiones;
    }

    @ApiOperation(value = "Lista de Sesiones por Nombre de Usuario"
            ,notes = "Se obtiene una lista de sesiones de usuario por coincidencia en el nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/users/sessions/{name}")
    public List<UsersConnections> getUsersByName(@PathVariable("name") String name){

        List<UsersConnections> list = sesionesService.getSessionByCoincidenceInNameAndActive(name);

        return list;
    }

}

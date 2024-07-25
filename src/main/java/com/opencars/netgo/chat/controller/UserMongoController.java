package com.opencars.netgo.chat.controller;

import com.opencars.netgo.chat.entity.UserMongo;
import com.opencars.netgo.chat.service.UserMongoService;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.users.entity.Position;
import com.opencars.netgo.users.entity.User;
import com.opencars.netgo.users.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/api")
public class UserMongoController {

    @Autowired
    UserMongoService userMongoService;

    @Autowired
    UserService userService;

    public int create(UserMongo userMongo){

        UserMongo newUser =
                new UserMongo(
                        userMongo.getIdIntranet(),
                        userMongo.getName(),
                        userMongo.getEmail(),
                        userMongo.getPassword(),
                        userMongo.getCuil(),
                        userMongo.getRole(),
                        userMongo.getLastSeen()
                );

        try{
            userMongoService.save(newUser);
            return HttpStatus.OK.value();
        }catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
    }

    @ApiOperation(value = "Obtener id de mongo del usuario y crear usuario en mongo db si no existe, por id de Intranet"
            ,notes = "Se obtiene el id de mongo de un colaborador, si no existe en la base de datos de mongo, se crea, a través de su ID de la Intranet")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @GetMapping("/usersmongo/intranet/{id}/password/{password}")
    public ResponseEntity<?> getByIdIntranet(@PathVariable("id") int id, @PathVariable("password") String password){
        try{
            //Si existe el usuario de mongo, obtengo id
            if(userMongoService.existsByIdIntranet(id)){

                UserMongo us = userMongoService.getByIdIntranet(id).get();

                return ResponseEntity.status(HttpStatus.OK).body(new Msg(us.getId(), HttpStatus.OK.value()));

                //Si no existe lo creo y envío el id
            }else{

                User user = userService.getOne(id).get();
                StringBuilder role = new StringBuilder();
                if (user.getPositions().size() == 1){
                    role = new StringBuilder(user.getPositions().first().getPosition().getName());
                }else{
                    int count = 0;
                    for (Position p: user.getPositions()
                    ) {
                        count = count + 1;
                        if (user.getPositions().size() == count){
                            role.append(p.getPosition().getName());
                        }else{
                            role.append(p.getPosition().getName()).append(" | ");
                        }
                    }
                }

                UserMongo newUser = new UserMongo(
                        user.getId(),
                        user.getName(),
                        user.getMail(),
                        BCrypt.hashpw(password, BCrypt.gensalt()),
                        user.getCuil(),
                        role.toString(),
                        new Date()
                );

                int statusCode = this.create(newUser);

                if (statusCode == 200){
                    UserMongo us = userMongoService.getByIdIntranet(id).get();
                    return ResponseEntity.status(HttpStatus.OK).body(new Msg(us.getId(), HttpStatus.OK.value()));
                }else{
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error", HttpStatus.INTERNAL_SERVER_ERROR.value()));
                }
            }
        }catch (Exception e){
            String message = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}

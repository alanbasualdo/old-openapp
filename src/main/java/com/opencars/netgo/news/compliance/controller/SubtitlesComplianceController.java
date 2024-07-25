package com.opencars.netgo.news.compliance.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.news.compliance.entity.Subtitle;
import com.opencars.netgo.news.compliance.service.SubtitleComplianceService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Subtítulos de Compliance")
public class SubtitlesComplianceController {

    @Autowired
    SubtitleComplianceService subtitleComplianceService;

    @ApiOperation(value = "Creación de un Subítulo de manual de Compliance"
            ,notes = "Se envía un objeto de tipo subtitle a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCCHH')")
    @PostMapping("/compliance/subtitle")
    public ResponseEntity<?> create(@Valid @RequestBody Subtitle subtitles){
        Subtitle newSubtitle =
                new Subtitle(

                        subtitles.getSubtitle(),
                        subtitles.getNumber()

                );
        subtitleComplianceService.save(newSubtitle);

        return new ResponseEntity(new Msg("Subtítulo Guardado"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Actualización de un Subtítulo del Manual de Compliance"
            ,notes = "Se actualiza un subtítulo a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCCHH')")
    @PutMapping("/compliance/subtitle/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Subtitle subtitle){
        Subtitle subtitleUpdated = subtitleComplianceService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Title not found for this id :: " + id));;
        subtitleUpdated.setSubtitle(subtitle.getSubtitle());
        subtitleUpdated.setNumber(subtitle.getNumber());

        subtitleComplianceService.save(subtitleUpdated);

        return ResponseEntity.ok(subtitleUpdated);
    }

    @ApiOperation(value = "Lista de Subtítulos de Manual de Compliance"
            ,notes = "Se obtiene una lista de los subtítulos del manual de compliance")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCCHH')")
    @GetMapping("/compliance/subtitles")
    public List<Subtitle> getSubtitlesCompliance(){

        List<Subtitle> list = subtitleComplianceService.getAll();

        return list;
    }

}

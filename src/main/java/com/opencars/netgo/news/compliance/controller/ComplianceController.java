package com.opencars.netgo.news.compliance.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.news.compliance.entity.Compliance;
import com.opencars.netgo.news.compliance.service.ComplianceService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Compliance")
public class ComplianceController {

    @Autowired
    ComplianceService complianceService;


    @ApiOperation(value = "Creación de una página de manual de Compliance"
            ,notes = "Se envía un objeto de compliance user a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCCHH')")
    @PostMapping("/compliance")
    public ResponseEntity<?> create(@Valid @RequestBody Compliance compliance){

        String message = "";

        Compliance newCompliance =
                new Compliance(

                        compliance.getTitle(),
                        compliance.getSubtitle(),
                        compliance.getInternalsubtitle(),
                        compliance.getText()

                );

        try{
            complianceService.save(newCompliance);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Msg("Página Guardada", HttpStatus.CREATED.value()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }

    }

    @ApiOperation(value = "Actualización de una Página del Manual de Compliance"
            ,notes = "Se actualiza una página a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCCHH')")
    @PutMapping("/compliance/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Compliance compliance){
        Compliance complianceUpdated = complianceService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Page not found for this id :: " + id));;
        complianceUpdated.setTitle(compliance.getTitle());
        complianceUpdated.setSubtitle(compliance.getSubtitle());
        complianceUpdated.setInternalsubtitle(compliance.getInternalsubtitle());
        complianceUpdated.setText(compliance.getText());

        complianceService.save(complianceUpdated);

        return ResponseEntity.ok(complianceUpdated);
    }

    @ApiOperation(value = "Lista de Compliance"
            ,notes = "Se obtiene una lista paginada del manual de compliance")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/compliance")
    public Page<Compliance> getCompliance(@PageableDefault(size = 10, page = 0) Pageable pageable){

        Page<Compliance> list = complianceService.getAll(pageable);

        return list;
    }

    @ApiOperation(value = "Lista de Compliance sin paginar"
            ,notes = "Se obtiene una lista del manual de compliance")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/compliance/notpaginated")
    public List<Compliance> getComplianceNotPaginates(){

        List<Compliance> list = complianceService.getAllNotPaginated();

        return list;
    }

    @ApiOperation(value = "Lista de Compliance para Vista"
            ,notes = "Se obtiene una lista paginada de uno en uno, del manual de compliance")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/compliance/view")
    public Page<Compliance> getComplianceView(@PageableDefault(size = 1, page = 0) Pageable pageable){

        Page<Compliance> list = complianceService.getAll(pageable);

        return list;
    }

    @ApiOperation(value = "Lista de Páginas de Manual de Compliance por Título"
            ,notes = "Se obtiene una lista de páginas por coincidencia en el título")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCCHH')")
    @GetMapping("/compliance/title/{title}")
    public List<Compliance> getPagesByTitleCoincidence(@PathVariable("title") String title){

        List<Compliance> list = complianceService.getByTitleCoincidence(title);

        return list;
    }
}

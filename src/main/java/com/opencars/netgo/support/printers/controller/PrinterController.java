package com.opencars.netgo.support.printers.controller;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.printers.entity.Printer;
import com.opencars.netgo.support.printers.service.PrinterService;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/printers")
@CrossOrigin
@Api(tags = "Controlador de Impresoras")
public class PrinterController {

    @Autowired
    PrinterService printerService;

    @ApiOperation(value = "Creación de una Impresora"
            ,notes = "Se envía un objeto de tipo Printer a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Printer printer, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);

        Printer newPrinter =
                new Printer(

                        printer.getModel(),
                        printer.getBranch(),
                        printer.getSector(),
                        printer.getIp(),
                        printer.getCode(),
                        printer.getAssignedTo(),
                        printer.getUser(),
                        printer.isOnJson(),
                        printer.isOwn(),
                        printer.getProvider(),
                        printer.getHostname(),
                        printer.getInvoicedTo(),
                        printer.getToner(),
                        printer.getProviderToner(),
                        printer.getState(),
                        printer.getTypeDevice()

                );

        printerService.save(newPrinter);
        return new ResponseEntity(newPrinter, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Actualización de una Impresora"
            ,notes = "Se actualiza la información de una impresora")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/printer/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Printer printer){
        Printer printerUpdated = printerService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Printer not found for this id :: " + id));;
        printerUpdated.setModel(printer.getModel());
        printerUpdated.setBranch(printer.getBranch());
        printerUpdated.setSector(printer.getSector());
        printerUpdated.setIp(printer.getIp());
        printerUpdated.setCode(printer.getCode());
        printerUpdated.setAssignedTo(printer.getAssignedTo());
        printerUpdated.setUser(printer.getUser());
        printerUpdated.setOnJson(printer.isOnJson());
        printerUpdated.setOwn(printer.isOwn());
        printerUpdated.setProvider(printer.getProvider());
        printerUpdated.setHostname(printer.getHostname());
        printerUpdated.setInvoicedTo(printer.getInvoicedTo());
        printerUpdated.setToner(printer.getToner());
        printerUpdated.setProviderToner(printer.getProviderToner());
        printerUpdated.setState(printer.getState());
        printerUpdated.setTypeDevice(printer.getTypeDevice());

        printerService.save(printerUpdated);

        return ResponseEntity.ok(printerUpdated);
    }

    @ApiOperation(value = "Lista de Impresoras y Escáners"
            ,notes = "Se obtiene una lista paginada de impresoras y escáners")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/list")
    public Page<Printer> getList(@PageableDefault(size = 10, page = 0) Pageable pageable){

        Page<Printer> list = printerService.getList(pageable);

        return list;
    }

    @ApiOperation(value = "Lista de Impresoras y Escáners por estado"
            ,notes = "Se obtiene una lista de impresoras y escáners por estado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/list/state/{state}")
    public Page<Printer> getPrintersByState(@PathVariable("state") String state, @PageableDefault(size = 20, page = 0) Pageable pageable){
        Page<Printer> list = printerService.getByState(state, pageable);
        return list;
    }

    @ApiOperation(value = "Contador Totalidad de Impresoras"
            ,notes = "Se obtiene la cantidad total de impresoras")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/total/count")
    public int getCountTotalPrinters(){
        int count = printerService.getCountTotalPrinters();
        return count;
    }

    @ApiOperation(value = "Contador Totalidad de Escáners"
            ,notes = "Se obtiene la cantidad total de escáners")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/total/scanners/count")
    public int getCountTotalScanners(){
        int count = printerService.getCountTotalScanners();
        return count;
    }


    @ApiOperation(value = "Contador Totalidad de Impresoras por Sucursal"
            ,notes = "Se obtiene la cantidad total de impresoras por sucursal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/total/count/branch/{branch}")
    public int getCountTotalPrinters(@PathVariable("branch") Branch branch){
        int count = printerService.getCountTotalPrintersByBranch(branch.getId());
        return count;
    }

    @ApiOperation(value = "Contador Totalidad de Escáners por Sucursal"
            ,notes = "Se obtiene la cantidad total de escáners por sucursal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/total/scanners/count/branch/{branch}")
    public int getCountTotalScanners(@PathVariable("branch") Branch branch){
        int count = printerService.getCountTotalScannersByBranch(branch.getId());
        return count;
    }

    @ApiOperation(value = "Contador Totalidad de Impresoras Asignadas"
            ,notes = "Se obtiene la cantidad total de impresoras asignadas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/assigneds/count")
    public int getCountAssignedsPrinters(){
        int count = printerService.getCountAssignedsPrinters();
        return count;
    }

    @ApiOperation(value = "Contador Totalidad de Escáners Asignados"
            ,notes = "Se obtiene la cantidad total de escáners asignados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/assigneds/scanners/count")
    public int getCountAssignedsScanners(){
        int count = printerService.getCountAssignedsScanners();
        return count;
    }

    @ApiOperation(value = "Contador Totalidad de Impresoras Asignadas por Sucursal"
            ,notes = "Se obtiene la cantidad total de impresoras asignadas por sucursal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/assigneds/count/branch/{branch}")
    public int getCountAssignedsPrinters(@PathVariable("branch") Branch branch){
        int count = printerService.getCountAssignedsPrintersByBranch(branch.getId());
        return count;
    }

    @ApiOperation(value = "Contador Totalidad de Escáners Asignados por Sucursal"
            ,notes = "Se obtiene la cantidad total de escáners asignados por sucursal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/assigneds/scanners/count/branch/{branch}")
    public int getCountAssignedsScanners(@PathVariable("branch") Branch branch){
        int count = printerService.getCountAssignedsScannersByBranch(branch.getId());
        return count;
    }

    @ApiOperation(value = "Contador Totalidad de Impresoras en Stock"
            ,notes = "Se obtiene la cantidad total de impresoras en stock")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/stock/count")
    public int getCountStockPrinters(){
        int count = printerService.getCountStockPrinters();
        return count;
    }

    @ApiOperation(value = "Contador Totalidad de Escáners en Stock"
            ,notes = "Se obtiene la cantidad total de escáners en stock")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/stock/scanners/count")
    public int getCountStockScanners(){
        int count = printerService.getCountStockScanners();
        return count;
    }

    @ApiOperation(value = "Contador Totalidad de Impresoras en Stock por Sucursal"
            ,notes = "Se obtiene la cantidad total de impresoras en stock por sucursal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/stock/count/branch/{branch}")
    public int getCountStockPrinters(Branch branch){
        int count = printerService.getCountStockPrintersByBranch(branch.getId());
        return count;
    }

    @ApiOperation(value = "Contador Totalidad de Escáners en Stock por Sucursal"
            ,notes = "Se obtiene la cantidad total de escáners en stock por sucursal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/stock/scanners/count/branch/{branch}")
    public int getCountStockScanners(Branch branch){
        int count = printerService.getCountStockScannersByBranch(branch.getId());
        return count;
    }

    @ApiOperation(value = "Lista de Impresoras por Sucursal"
            ,notes = "Se obtiene una lista de Impresoras por sucursal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/branch/{branch}")
    public List<Printer> getPrintersByBranch(@PathVariable("branch") Branch branch){

        List<Printer> printers = printerService.getPrintersByBranch(branch);
        return printers;
    }

    @ApiOperation(value = "Eliminación de un dispositivo por ID"
            ,notes = "Se elimina un dispositivo a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('ADMINIT')")
    @DeleteMapping("/printer/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            printerService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Dispositivo Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

}

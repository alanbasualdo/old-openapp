package com.opencars.netgo.dms.quoter.controller;

import com.google.api.client.util.Base64;
import com.opencars.netgo.dms.quoter.dto.*;
import com.opencars.netgo.dms.quoter.entity.BrandInfoAuto;
import com.opencars.netgo.dms.quoter.entity.Cotization;
import com.opencars.netgo.dms.quoter.entity.ModelsInfoAuto;
import com.opencars.netgo.dms.quoter.entity.Series;
import com.opencars.netgo.dms.quoter.service.*;
import com.opencars.netgo.msgs.MsgInt;
import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin()
@Api(tags = "Controlador de API de InfoAuto")
public class InfoautoController {

    @Autowired
    MileageYearsService mileageYearsService;

    @Autowired
    MileageService mileageService;

    @Autowired
    TypeOfCarsService typeOfCarsService;

    @Autowired
    YearsPercentsService yearsPercentsService;

    @Autowired
    TypeOfSaleService typeOfSaleService;

    @Autowired
    SeriesService seriesService;

    @Autowired
    CotizationService cotizationService;

    @Autowired
    BrandInfoAutoService brandInfoAutoService;

    @Autowired
    ModelsCarsService modelsCarsService;

    @Autowired
    ModelsInfoAutoService modelsInfoAutoService;

    private String apiInfoauto = "https://api.infoauto.com.ar/cars";
    private String userInfoauto = "tdinucci@fortecar.com.ar";
    private String passInfoauto = "7pEJ6srspPF97NZ4";

    HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }

    HttpHeaders createHeadersBearer(){
        return new HttpHeaders() {{
            String token = loginInfoauto();
            String authHeader = "Bearer " + token;
            set( "Authorization", authHeader );
            add("Accept-Encoding", "gzip");
            add("Content-Encoding", "gzip");
            add("Content-Type", "application/json");
            add("Accept", "application/json");
            add("Content-Length","0");
        }};
    }

    private String loginInfoauto(){
        String uri = apiInfoauto + "/auth/login";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TokenInfoauto> tokenInfoauto = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(createHeaders(userInfoauto, passInfoauto)), TokenInfoauto.class);
        return tokenInfoauto.getBody().getAccess_token();
    }

    @ApiOperation(value = "Lista Completa de Marcas de Autos"
            ,notes = "Se obtiene una lista completa con las marcas de autos.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/infoauto/brands/list")
    public List<?> brandsComplete(){
        List<BrandInfoAuto> list = brandInfoAutoService.list();
        return list;
    }

    @ApiOperation(value = "Lista Completa de Modelos de Autos"
            ,notes = "Se obtiene una lista completa con los modelos de autos.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/infoauto/models/list")
    public List<?> modelsComplete(){
        List<ModelsInfoAuto> list = modelsInfoAutoService.list();
        return list;
    }

    @ApiOperation(value = "Lista de Patentes por coincidencia"
            ,notes = "Se obtiene una lista de patentes por coincidencia")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cotizations/patents/{patent}")
    public List<?> getPatentsByCoincidence(@PathVariable("patent") String patent){
        List<String> list = cotizationService.getDistinctPatents(patent);
        return list;
    }

    public List<?> brandsCompleteInternal(){
        List<BrandInfoAuto> list = brandInfoAutoService.list();
        return list;
    }

    public List<BrandInfoAuto> getBrands(){
        String uri = apiInfoauto + "/pub/brands/download/";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        BrandsInfoAuto[] response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(createHeadersBearer()), BrandsInfoAuto[].class).getBody();
        List<BrandInfoAuto> listNames = Arrays.asList(response).stream().map(b ->
                new BrandInfoAuto(
                        b.getId(),
                        b.getName()
                )
        ).collect(Collectors.toList());
        return listNames;
    }

    public void getModelsOfInfoAutoAndSave(){

        List<Series> listSeries = seriesService.list();
        // Obtener todos los modelos de cada serie y guardarlos
        for (Series serie : listSeries) {
            List<ModelsInfoAutoComplete> list = this.modelsBySerie(serie.getBrand().getCodia(), serie.getCodia());
            for (ModelsInfoAutoComplete model : list) {
                ModelsInfoAuto m = new ModelsInfoAuto(
                        model.getCodia(),
                        model.getDescription()
                );
                try{
                    if (!modelsInfoAutoService.existsByCodia(m.getCodia())){
                        modelsInfoAutoService.save(m);
                    }
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }

            }

            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @ApiOperation(value = "Obtener fecha de última actualización de InfoAuto"
            ,notes = "Se obtiene la fecha de la última actualización de la API de InfoAuto.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/infoauto/update/datetime")
    public UpdateDatetime updateDatetime(){
        String uri = apiInfoauto + "/pub/datetime";
        RestTemplate restTemplate = new RestTemplate();
        UpdateDatetime response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(createHeadersBearer()), UpdateDatetime.class).getBody();
        return response;
    }

    public UpdateDatetime updateDatetimeInternal(){
        String uri = apiInfoauto + "/pub/datetime";
        RestTemplate restTemplate = new RestTemplate();
        UpdateDatetime response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(createHeadersBearer()), UpdateDatetime.class).getBody();
        return response;
    }

    @ApiOperation(value = "Modelos de autos por marca y serie"
            ,notes = "Se obtiene una lista de modelos de autos a través de una marca y serie.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/brand/{brand}/group/{group}/models")
    public List<ModelsInfoAutoComplete> modelsBySerie(@PathVariable("brand") int brand, @PathVariable("group") int group){
        String uri = apiInfoauto + "/pub/brands/" + brand + "/groups/" + group + "/models/?page_size=100";
        RestTemplate restTemplate = new RestTemplate();
        ModelsInfoAutoComplete[] models = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(createHeadersBearer()), ModelsInfoAutoComplete[].class).getBody();

        return Arrays.asList(models);
    }

    @ApiOperation(value = "Modelos de autos por coincidencia modelo o marca"
            ,notes = "Se obtiene una lista de modelos de autos por coincidencia en el modelo o marca.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/search/{coincidence}")
    public List<ModelsInfoAuto> modelsByModelOrBrand(@PathVariable("coincidence") String coincidence){

        List<ModelsInfoAuto> list = modelsInfoAutoService.searchByCoincidence(coincidence);

        return list;
    }

    @ApiOperation(value = "Series de autos por marca"
            ,notes = "Se obtiene una lista de series de autos a través de una marca.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/brands/{brand}/groups")
    public List<?> seriesByBrand(@PathVariable("brand") int brand){
        String uri = apiInfoauto + "/pub/brands/" + brand + "/groups/?page_size=100";
        RestTemplate restTemplate = new RestTemplate();
        Object[] series = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(createHeadersBearer()), Object[].class).getBody();

        return Arrays.asList(series);
    }

    @ApiOperation(value = "Modelos de autos por marca y serie paginados"
            ,notes = "Se obtiene una lista de modelos de autos a través de una marca y serie.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/paginated/brand/{brand}/group/{group}/models/page/{page}")
    public List<?> getPaginatedModelsBySerie(@PathVariable("brand") int brand, @PathVariable("group") int group, @PathVariable("page") int page){

        String uri = apiInfoauto + "/pub/brands/" + brand + "/groups/" + group + "/models/?page_size=100&page=" + page;

        RestTemplate restTemplate = new RestTemplate();

        Object[] result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(createHeadersBearer()), Object[].class).getBody();

        return Arrays.asList(result);

    }

    @ApiOperation(value = "Precio de un auto"
            ,notes = "Se obtiene un precio de un auto por código del vehículo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/codia/{codia}/prices")
    public List<?> priceByCodia(@PathVariable("codia") int codia){
        String uri = apiInfoauto + "/pub/models/" + codia + "/prices/";
        RestTemplate restTemplate = new RestTemplate();
        Object[] listPrices = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(createHeadersBearer()), Object[].class).getBody();

        return Arrays.asList(listPrices);
    }

    @ApiOperation(value = "Obtener Cotización Final para VO"
            ,notes = "Se obtiene la cotización final para un VO")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cars/cotization")
    public ResponseEntity<?> getCotization(@RequestParam("typeSale") int typeSale, @RequestParam("typeCar") int typeCar, @RequestParam("serie") int serie, @RequestParam("priceMagazine") long priceMagazine, @RequestParam("year") int year, @RequestParam("km") long km, @RequestParam("model") String model){

       LocalDateTime dateCurrent = LocalDateTime.now(); // fecha actual
       int yearCurrent = LocalDate.now().getYear(); // año en curso
       long averageKm = km / (yearCurrent - year); //promedio de kms
       String obs;

       if(year > 2013 && km <= 141000){

           if (averageKm < 60000){

               float percentForKmAverage = (float)this.mileageService.getKmRange(averageKm).get().getPercent() / 100;
               float priceToTakeForDiscounts = (float)priceMagazine * percentForKmAverage; //valor con descuento por promedio de kilometraje
               float percentByTypeOfSale = (float)this.typeOfSaleService.getOne(typeSale).get().getPercent() / 100; //porcentaje por tipo de venta
               float percentByCarType = (float)this.typeOfCarsService.getOne(typeCar).get().getPercent() / 100;
               float percentForYear = (float)this.yearsPercentsService.getByYear(year).get().getPercent() / 100; //porcentaje por año del auto
               float percentForYearAndKm = (float)this.mileageYearsService.getPercentForYearsAndKm(year, km).get().getPercent() / 100; //porcentaje por año y kilometraje
               float percentForSerie = (float)this.seriesService.getOne(serie).get().getPercent() / 100; //porcentaje por serie

               float percentForModel;
               try {
                   percentForModel = (float)this.modelsCarsService.getPercentForModel(model).get().getPercent() / 100; //porcentaje por modelo
               } catch (NoSuchElementException e) {
                   percentForModel = 0.0f;
               }

               float finalValue = Math.round(
                       (priceToTakeForDiscounts -
                               (priceToTakeForDiscounts * percentForSerie) -
                               (priceToTakeForDiscounts * percentByTypeOfSale) -
                               (priceToTakeForDiscounts * percentForYear) -
                               (priceToTakeForDiscounts * percentForYearAndKm) -
                               (priceToTakeForDiscounts * percentByCarType) -
                               (priceToTakeForDiscounts * percentForModel)) /
                               10000) * 10000L;

               obs = "El valor corresponde al precio de toma según cotizador.";
               CotizationVO cotizationVO = new CotizationVO(
                       dateCurrent,
                       priceMagazine,
                       finalValue,
                       averageKm,
                       percentForKmAverage * 100,
                       priceMagazine * percentForKmAverage,
                       priceToTakeForDiscounts * percentByTypeOfSale,
                       percentByTypeOfSale * 100,
                       priceToTakeForDiscounts * percentByCarType,
                       percentByCarType * 100,
                       priceToTakeForDiscounts * percentForYear,
                       percentForYear * 100,
                       priceToTakeForDiscounts * percentForYearAndKm,
                       percentForYearAndKm * 100,
                       priceToTakeForDiscounts * percentForSerie,
                       percentForSerie * 100,
                       priceToTakeForDiscounts * percentForModel,
                       percentForModel * 100,
                       HttpStatus.OK.value(),
                       obs
               );

               return new ResponseEntity<> (cotizationVO, HttpStatus.OK);

           }else{
               obs = "El valor observado corresponde al precio de revista.\nDebe cotizarse con Jefe de Sucursal.";
               NoCotizationVO noCotizationVO = new NoCotizationVO(
                       dateCurrent,
                       priceMagazine,
                       averageKm,
                       obs,
                       HttpStatus.EXPECTATION_FAILED.value()

               );

               return new ResponseEntity<> (noCotizationVO, HttpStatus.OK);
           }

       }else{
           obs = "El valor observado corresponde al precio de revista.\nDebe cotizarse con Jefe de Sucursal.";
           NoCotizationVO noCotizationVO = new NoCotizationVO(
                   dateCurrent,
                   priceMagazine,
                   averageKm,
                   obs,
                   HttpStatus.EXPECTATION_FAILED.value()

           );

           return new ResponseEntity<> (noCotizationVO, HttpStatus.OK);
       }

    }

    @ApiOperation(value = "Guardar Cotización"
            ,notes = "Se guarda una cotización final de un VO")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/cars/cotization/create")
    public ResponseEntity<?> create(@Valid @RequestBody Cotization cotization, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MsgInt("Alguno de los datos no coinciden con el formato esperado", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        String message = "";
        int yearCurrent = LocalDate.now().getYear();

        try{

            Cotization newCotization = new Cotization(
                    cotization.getBrand(),
                    cotization.getModel(),
                    cotization.getYear(),
                    cotization.getPriceFinal(),
                    cotization.getKm(),
                    cotization.getClient(),
                    cotization.getTypeOperation(),
                    cotization.getTypeOfCar(),
                    cotization.getQuotedBy(),
                    cotization.getPatent(),
                    cotization.getObservation()
            );

            long averageKm = cotization.getKm() / (yearCurrent - cotization.getYear());
            newCotization.setKmAverage(averageKm);

            cotizationService.save(newCotization);

            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgInt("Cotización Guardada", HttpStatus.CREATED.value()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Lista de Cotizaciones"
            ,notes = "Se obtiene una lista de cotizaciones, paginada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cars/cotizations/list")
    public Page<Cotization> getCotizations(@PageableDefault(size = 20, page = 0, sort = "dateToOrder", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Cotization> cotizations = cotizationService.list(pageable);
        return cotizations;
    }

    @ApiOperation(value = "Lista de Cotizaciones por cliente"
            ,notes = "Se obtiene una lista de cotizaciones por coincidencia en el nombre de cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("cars/cotizations/client/{name}")
    public List<Cotization> getCotizationsByClient(@PathVariable("name") String name){

        List<Cotization> list = cotizationService.getByClient(name);

        return list;
    }

    @ApiOperation(value = "Cotizaciones por unidad"
            ,notes = "Se obtiene una lista de cotizaciones correspondientes a una unidad")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("cars/cotizations/model/{model}")
    public List<Cotization> getCotizatiosByModel(@PathVariable("model") String model){

        List<Cotization> list = cotizationService.getByModel(model);

        return list;
    }

    @ApiOperation(value = "Cotizaciones por Cotizante"
            ,notes = "Se obtiene una lista de cotizaciones correspondientes a un usuario cotizador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("cars/cotizations/quotedby/{quotedby}")
    public List<Cotization> getExpertisesByDomain(@PathVariable("quotedby") User quotedby){

        List<Cotization> list = cotizationService.getByQuotedBy(quotedby);

        return list;
    }

}

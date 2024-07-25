package com.opencars.netgo.sales.viaticos.service;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.locations.entity.Company;
import com.opencars.netgo.locations.entity.SubSector;
import com.opencars.netgo.locations.service.BranchService;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.sales.proveedores.entity.SalesProviders;
import com.opencars.netgo.sales.viaticos.dto.*;
import com.opencars.netgo.sales.viaticos.entity.*;
import com.opencars.netgo.sales.viaticos.repository.ViaticosRepository;
import com.opencars.netgo.support.tickets.dto.*;
import com.opencars.netgo.users.entity.User;
import com.opencars.netgo.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

import static com.opencars.netgo.support.tickets.service.TicketsService.*;

@Service
@Transactional
public class ViaticosService {

    private int viaticosLoadeds;
    private int viaticosNotLoaded;
    private int viaticosRejecteds;
    private int totalForAnalyst;
    private int viaticosAuthorizeds;
    private int totalForAuthorizer;
    private int viaticosNotAuthorizeds;
    private int viaticosNotPaids;
    private int viaticosPaids;
    private int totalForTreasurers;
    private long amountNotPaid;
    private long amountPaid;
    private long totalAmountForTreasurer;

    @Autowired
    ViaticosRepository viaticosRepository;

    @Autowired
    TableContableService tableContableService;

    @Autowired
    TableTreasurersService tableTreasurersService;

    @Autowired
    AuthorizerSuperiorService authorizerSuperiorService;

    @Autowired
    AuthorizerToSuperiorService authorizerToSuperiorService;

    @Autowired
    BranchService branchService;

    @Autowired
    ConceptsComprasService conceptsComprasService;

    @Autowired
    UserService userService;

    public Optional<Viaticos> getOne(long id){
        return viaticosRepository.findById(id);
    }

    public void deleteById(long id){
        viaticosRepository.deleteById(id);
    }

    public List<Viaticos> getByNameColaboratorAndAuthorizer(String name, User authorizer){
        return viaticosRepository.findByNameColaboratorAndAuthorizer(name, authorizer);
    }

    public List<Viaticos> getByNameColaboratorAndAnalyst(String name, User analyst){
        return viaticosRepository.findByNameColaboratorAndAnalyst(name, analyst);
    }

    public List<Viaticos> getByNameColaboratorAndTreasurer(String name, User treasurer){
        return viaticosRepository.findByNameColaboratorAndTreasurer(name, treasurer);
    }

    public List<Viaticos> getByRef(String ref){
        return viaticosRepository.findByRef(ref);
    }

    public List<ReportFinallyColaborators> getReportColaborators(){

        LocalDate dateCurrent = LocalDate.now();

        List<ReportColaborators> listColaborators = viaticosRepository.findColaboratorsReport();
        List<ReportColaborators> listMin90 = viaticosRepository.findColaboratorsMin90Days(dateCurrent);
        List<ReportColaborators> listRange60to90 = viaticosRepository.findColaboratorsAmountsByRangeDays(dateCurrent, 61, 90 );
        List<ReportColaborators> listRange30to60 = viaticosRepository.findColaboratorsAmountsByRangeDays(dateCurrent, 31, 60 );
        List<ReportColaborators> listRange0to30 = viaticosRepository.findColaboratorsAmountsByRangeDays(dateCurrent, 0, 30 );
        List<ReportFinallyColaborators> listFinally = new ArrayList<>();

        for (ReportColaborators colaborator: listColaborators){

            ReportFinallyColaborators resultFinally = new ReportFinallyColaborators();

            resultFinally.setColaborator(colaborator.getColaborator());

            for (ReportColaborators listMax90: listMin90) {
                if (colaborator.getColaborator() == listMax90.getColaborator()) {
                    resultFinally.setAmountMax90days(listMax90.getAmount());
                    break;
                }
            }

            for (ReportColaborators listRangeto90: listRange60to90) {
                if (colaborator.getColaborator() == listRangeto90.getColaborator()) {
                    resultFinally.setAmount90days(listRangeto90.getAmount());
                    break;
                }
            }

            for (ReportColaborators listRangeto60: listRange30to60) {
                if (colaborator.getColaborator() == listRangeto60.getColaborator()) {
                    resultFinally.setAmount60days(listRangeto60.getAmount());
                    break;
                }
            }

            for (ReportColaborators listRangeto30: listRange0to30) {
                if (colaborator.getColaborator() == listRangeto30.getColaborator()) {
                    resultFinally.setAmount30days(listRangeto30.getAmount());
                    break;
                }
            }

            resultFinally.setAmountTotal(colaborator.getAmount());

            listFinally.add(resultFinally);

        }

        return listFinally;

    }

    public List<Viaticos> getAllPendingsForClose(){
        List<Viaticos> list = viaticosRepository.findAllPendingsForClose();
        return list;
    }

    public List<Viaticos> getByBranch(Branch branch){
        return viaticosRepository.findByBranch(branch);
    }

    public Page<Viaticos> getByColaborator(User colaborator, Pageable pageable){
        return viaticosRepository.findByColaborator(colaborator, pageable);
    }

    public Page<Viaticos> getByAnalystsPendings(Pageable pageable){
        return viaticosRepository.findByAnalystsPendings(pageable);
    }

    public Page<Viaticos> getByTreasurersPendings(Pageable pageable){
        return viaticosRepository.findByTreasurersPendings(pageable);
    }

    public Page<Viaticos> getByAuthorizerAndState(User authorizer, StatesCompras state, Pageable pageable){
        return viaticosRepository.findByAuthorizerAndState(authorizer, state, pageable);
    }

    public Page<Viaticos> getByAnalystAndState(User analyst, StatesCompras state, Pageable pageable){
        if(state.getId() == 5){
            return viaticosRepository.findByAnalystRejecteds(analyst, pageable);
        }else{
            return viaticosRepository.findByAnalystAndState(analyst, state, pageable);
        }
    }

    public Msg passViaticosPendingsToOtherAnalyst(long id, User analyst) {

        String message = "";
        try {

            Viaticos viaticoUpdated = this.getOne(id).get();
            viaticoUpdated.setAnalyst(analyst);

            this.save(viaticoUpdated);

            return new Msg("Registro Guardado", HttpStatus.OK.value());


        } catch (Exception e) {
            message = "Error: " + e;
            return new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public Msg passViaticosPendingsToOtherAuthorizer(long id, User authorizer) {

        String message = "";
        try {

            Viaticos viaticoUpdated = this.getOne(id).get();
            viaticoUpdated.setAuthorizer(authorizer);

            this.save(viaticoUpdated);

            return new Msg("Registro Guardado", HttpStatus.OK.value());


        } catch (Exception e) {
            message = "Error: " + e;
            return new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public Msg passViaticosPendingsToOtherTreasurer(long id, User treasurer) {

        String message = "";
        try {

            Viaticos viaticoUpdated = this.getOne(id).get();
            viaticoUpdated.setTreasurer(treasurer);

            this.save(viaticoUpdated);

            return new Msg("Registro Guardado", HttpStatus.OK.value());


        } catch (Exception e) {
            message = "Error: " + e;
            return new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public List<Viaticos> getListByAnalystAndStateNotPostedAndBranch(User analyst, Branch branch){
        return viaticosRepository.findListByAnalystAndStateNotPostedAndBranch(analyst, branch);
    }

    public List<Viaticos> getListByAuthorizerPendings(User authorizer){
        return viaticosRepository.findListByAuthorizerPendings(authorizer);
    }

    public List<Viaticos> getListByTreasurerAndBranchAndStateNotPaid(User treasurer, Branch branch){
        return viaticosRepository.findListByTreasurerAndStateNotPaidAndBranch(treasurer, branch);
    }

    public Page<Viaticos> getByTreasurerAndState(User treasurer, StatesCompras state, Pageable pageable){
        return viaticosRepository.findByTreasurerAndState(treasurer, state, pageable);
    }

    public Page<Viaticos> getAllNotRejectedAndAuthorizeds(User authorizer, Pageable pageable){
        return viaticosRepository.findAllNotRejectedAndAuthorizeds(authorizer, pageable);
    }

    public Page<Viaticos> getAllNotRejectedAndLoaded(User analyst, Pageable pageable){
        return viaticosRepository.findAllNotRejectedAndLoaded(analyst, pageable);
    }

    public Page<Viaticos> getByCompanyAndState(Branch branch, StatesCompras state, Pageable pageable){
        return viaticosRepository.findByBranchAndState(branch, state, pageable);
    }

    public List<User> listTreasurers(){
        List<User> listTreasurers = viaticosRepository.findTreasurersActivatedAndDeactivated();
        return listTreasurers;
    }

    public List<User> listAnalysts(){
        List<User> listAnalysts = viaticosRepository.findAnalystsActivatedAndDeactivated();
        return listAnalysts;
    }

    public List<User> listAuthorizers(){
        List<User> listAuthorizers = new ArrayList<>();
        List<User> listAuthorizersLines3 = userService.getLines3();
        List<User> listAuthorizersLines3Final = new ArrayList<>();
        List<User> listAuthorizersLines4 = userService.getLines4();
        List<User> listAuthorizersLines4Final = new ArrayList<>();

        //Verifico que el usuario tenga gente a cargo, si es asi lo agrego a la lista final de autorizantes, si no tiene gente a cargo no autoriza
        for (User chief: listAuthorizersLines4){

            List<Branch> branchsLines4 = new ArrayList<>();
            List<SubSector> subSectors4 = new ArrayList<>();

            chief.getPositions().forEach(p -> {
                branchsLines4.addAll(p.getBranchs());
                subSectors4.add(p.getSubSector());
            });

            for (SubSector subSector: subSectors4){
                if(subSector.getName().equals("Postventa")){
                    for (Branch branch: branchsLines4){
                        List<User> peopleLine5;
                        peopleLine5 = userService.getPeopleInChargeForGarajeChief(subSector, branch);
                        if (peopleLine5.size() > 0){
                            if (!listAuthorizersLines4Final.contains(chief)){
                                listAuthorizersLines4Final.add(chief);
                            }
                        }
                    }
                }else if(subSector.getName().equals("Comercial") || subSector.getName().equals("Comercial-PDA") ){
                    for (Branch branch: branchsLines4){
                        List<User> peopleLine5;
                        peopleLine5 = userService.getPeopleInChargeForComercialSupervisor(branch);
                        if (peopleLine5.size() > 0){
                            if (!listAuthorizersLines4Final.contains(chief)){
                                listAuthorizersLines4Final.add(chief);
                            }
                        }
                    }
                }else{
                    List<User> peopleLine5;
                    peopleLine5 = userService.getPeopleInChargeForSubsectorSupervisor(subSector);
                    if (peopleLine5.size() > 0){
                        if (!listAuthorizersLines4Final.contains(chief)){
                            listAuthorizersLines4Final.add(chief);
                        }
                    }
                }
            }
        }

        //Verifico que el usuario tenga gente a cargo, si es asi lo agrego a la lista final de autorizantes, si no tiene gente a cargo no autoriza
        for (User chief: listAuthorizersLines3){

            List<Branch> branchsLines3 = new ArrayList<>();
            List<SubSector> subSectors = new ArrayList<>();

            chief.getPositions().forEach(p -> {
                branchsLines3.addAll(p.getBranchs());
                subSectors.add(p.getSubSector());
            });

            for (SubSector subSector: subSectors){
                if (subSector.getName().equals("Comercial") || subSector.getName().equals("Comercial-PDA")){
                    for (Branch branch: branchsLines3){
                        List<User> peopleLine4;
                        peopleLine4 = userService.getPeopleInChargeForComercialChief(subSector, branch);
                        if (peopleLine4.size() > 0){
                            if (!listAuthorizersLines3Final.contains(chief)){
                                listAuthorizersLines3Final.add(chief);
                            }
                        }
                    }
                }else  if(subSector.getName().equals("Postventa")){
                    for (Branch branch: branchsLines3){
                        List<User> peopleLine4;
                        peopleLine4 = userService.getPeopleInChargeForPostventaChief(subSector, branch.getBrandsCompany().getCompany().getId());
                        if (peopleLine4.size() > 0){
                            if (!listAuthorizersLines3Final.contains(chief)){
                                listAuthorizersLines3Final.add(chief);
                            }
                        }
                    }
                }else{
                    List<User> peopleLine4;
                    peopleLine4 = userService.getPeopleInChargeForSubsectorChief(subSector);
                    if (peopleLine4.size() > 0){
                        if (!listAuthorizersLines3Final.contains(chief)){
                            listAuthorizersLines3Final.add(chief);
                        }
                    }
                }
            }
        }

        List<User> listAuthorizersSectors = userService.getLines2();
        List<User> listAuthorizersSectorsFinal = new ArrayList<>();

        for (User line2: listAuthorizersSectors){
            List<Branch> branchsLines2 = new ArrayList<>();
            List<SubSector> subSectors2 = new ArrayList<>();

            line2.getPositions().forEach(p -> {
                branchsLines2.addAll(p.getBranchs());
                subSectors2.add(p.getSubSector());
            });

            for (SubSector subSector: subSectors2){
                if (subSector.getName().equals("Comercial")){
                    for (Branch branch: branchsLines2){
                        List<User> peopleLine3;
                        peopleLine3 = userService.getPeopleInChargeForComercialManager(subSector.getSector());
                        if (peopleLine3.size() > 0){
                            if (!listAuthorizersSectorsFinal.contains(line2)){
                                listAuthorizersSectorsFinal.add(line2);
                            }
                        }
                    }
                }else{
                    List<User> peopleLine3;
                    peopleLine3 = userService.getPeopleInChargeForSectorManager(subSector.getSector());
                    if (peopleLine3.size() > 0){
                        if (!listAuthorizersSectorsFinal.contains(line2)){
                            listAuthorizersSectorsFinal.add(line2);
                        }
                    }
                }
            }
        }

        List<User> listAuthorizersSuperior = authorizerSuperiorService.listAuthorizers();
        List<User> listAuthorizerToSuperior = authorizerToSuperiorService.listAuthorizers();

        listAuthorizers.addAll(listAuthorizersLines3Final);
        listAuthorizers.addAll(listAuthorizersLines4Final);
        listAuthorizers.addAll(listAuthorizersSectorsFinal);
        listAuthorizers.addAll(listAuthorizersSuperior);
        listAuthorizers.addAll(listAuthorizerToSuperior);

        List<User> listaLimpia = new ArrayList<>();

        //Forma número 1 (Uso de Maps).
        Map<Integer, User> mapAnalyst = new HashMap<>(listAuthorizers.size());

        //Aquí está la magia
        for(User p : listAuthorizers) {
            mapAnalyst.put(p.getId(), p);
        }

        //Agrego cada elemento del map a una nueva lista y muestro cada elemento.
        for(Map.Entry<Integer, User> p : mapAnalyst.entrySet()) {
            listaLimpia.add(p.getValue());
        }

        return listaLimpia;
    }

    public List<CardAnalysts> getPerformanceAnalysts() {

        List<CardAnalysts> listCards = new ArrayList<>();
        List<Viaticos> listViaticosClosedForAnalyst;

        List<User> listaLimpia = listAnalysts();

        for (User analyst : listaLimpia) {

            int[] arrayResolvedVsTotal = new int[3];
            String[] arrayLabelsTotal = new String[3];
            Data[] arrayTotalResolvedVsTotal = new Data[1];

            long totalTimeResolution = 0;
            long averageForTicket = 0;

            String timeAverageResolution = "";

            listViaticosClosedForAnalyst = viaticosRepository.findResolvedsByAnalyst(analyst);
            Duration duration = Duration.ofSeconds(0);
            for (Viaticos viatico : listViaticosClosedForAnalyst) {
                if(viatico.getState().getId() == 5){
                    if(viatico.getRejectedBy() == analyst){
                        duration = Duration.between(viatico.getDateAuthorized(), viatico.getDateEnd());
                    }
                }else{
                    duration = Duration.between(viatico.getDateAuthorized(), viatico.getDateLoaded());
                }
                totalTimeResolution = totalTimeResolution + duration.getSeconds();

            }

            if(listViaticosClosedForAnalyst.size() > 0) {
                averageForTicket = totalTimeResolution / listViaticosClosedForAnalyst.size();

                long days = averageForTicket / DAYS;
                long hours = ((averageForTicket % DAYS) / SECONDS_PER_HOUR);
                long minutes = ((averageForTicket % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
                long secs = (averageForTicket % SECONDS_PER_MINUTE);

                String day = "";
                if(days == 0){
                    timeAverageResolution = hours + "hs " + minutes + "min " + secs + "segs";
                }else if(days == 1){
                    day = "día ";
                    timeAverageResolution = days + day + hours + "hs " + minutes + "min " + secs + "segs";
                }else if(days > 1){
                    day = "días ";
                    timeAverageResolution = days + day + hours + "hs " + minutes + "min " + secs + "segs";
                }

            }else{
                timeAverageResolution = "No aplica";
            }

            totalForAnalyst= viaticosRepository.countAllViaticosByAnalyst(analyst);
            viaticosNotLoaded = viaticosRepository.countViaticosByAnalystNotManageds(analyst);
            viaticosLoadeds = viaticosRepository.countViaticosByAnalystLoades(analyst);
            viaticosRejecteds = viaticosRepository.countViaticosByAnalystRejecteds(analyst);

            arrayResolvedVsTotal[0] = viaticosNotLoaded;
            arrayResolvedVsTotal[1] = viaticosLoadeds;
            arrayResolvedVsTotal[2] = viaticosRejecteds;

            DecimalFormat df = new DecimalFormat("#.##");

            if(viaticosNotLoaded > 0){
                float NotManaged = (float)viaticosNotLoaded / (float)totalForAnalyst * 100;
                arrayLabelsTotal[0] = df.format(NotManaged) + "%";
            }else {
                arrayLabelsTotal[0] = "0%";
            }

            if (viaticosLoadeds > 0) {
                float loadeds = (float)viaticosLoadeds / (float)totalForAnalyst * 100;
                arrayLabelsTotal[1] = df.format(loadeds) + "%";
            }else{
                arrayLabelsTotal[1] = "0%";
            }

            if (viaticosRejecteds > 0){
                float rejecteds = (float)viaticosRejecteds / (float)totalForAnalyst * 100;
                arrayLabelsTotal[2] = df.format(rejecteds) + "%";
            }else {
                arrayLabelsTotal[2] = "0%";
            }

            arrayTotalResolvedVsTotal[0] = new Data(arrayResolvedVsTotal);

            List<Branch> branchsForAnalyst = this.tableContableService.getBranchsForAnalyst(analyst);

            CardAnalysts cardAnalysts = new CardAnalysts(
                    analyst,
                    totalForAnalyst,
                    viaticosNotLoaded,
                    viaticosLoadeds,
                    viaticosRejecteds,
                    timeAverageResolution,
                    arrayTotalResolvedVsTotal,
                    arrayLabelsTotal,
                    branchsForAnalyst
            );

            listCards.add(cardAnalysts);

        }

        return listCards;

    }

    public List<CardAuthorizers> getPerformanceAuthorizers() {

        List<CardAuthorizers> listCards = new ArrayList<>();
        List<Viaticos> listViaticosClosedForAuthorizers;

        List<User> listaLimpia = listAuthorizers();

        for (User authorizer : listaLimpia) {

            int[] arrayResolvedVsTotal = new int[3];
            String[] arrayLabelsTotal = new String[3];
            Data[] arrayTotalResolvedVsTotal = new Data[1];

            long totalTimeResolution = 0;
            long averageForTicket = 0;

            String timeAverageResolution = "";

            listViaticosClosedForAuthorizers = viaticosRepository.findResolvedsByAuthorizer(authorizer);
            Duration duration = Duration.ofSeconds(0);
            for (Viaticos viatico : listViaticosClosedForAuthorizers) {

                if(viatico.getState().getId() == 2 || viatico.getState().getId() == 3 || viatico.getState().getId() == 4){
                    duration = Duration.between(viatico.getDateInit(), viatico.getDateAuthorized());
                }else{
                    if(viatico.getRejectedBy() == authorizer){
                        duration = Duration.between(viatico.getDateInit(), viatico.getDateEnd());
                    }
                }

                totalTimeResolution = totalTimeResolution + duration.getSeconds();

            }

            if(listViaticosClosedForAuthorizers.size() > 0) {
                averageForTicket = totalTimeResolution / listViaticosClosedForAuthorizers.size();

                long days = averageForTicket / DAYS;
                long hours = ((averageForTicket % DAYS) / SECONDS_PER_HOUR);
                long minutes = ((averageForTicket % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
                long secs = (averageForTicket % SECONDS_PER_MINUTE);

                String day = "";
                if(days == 0){
                    timeAverageResolution = hours + "hs " + minutes + "min " + secs + "segs";
                }else if(days == 1){
                    day = "día ";
                    timeAverageResolution = days + day + hours + "hs " + minutes + "min " + secs + "segs";
                }else if(days > 1){
                    day = "días ";
                    timeAverageResolution = days + day + hours + "hs " + minutes + "min " + secs + "segs";
                }

            }else{
                timeAverageResolution = "No aplica";
            }

            totalForAuthorizer= viaticosRepository.countAllViaticosByAuthorizer(authorizer);
            viaticosNotAuthorizeds = viaticosRepository.countViaticosByAuthorizerNotAuthorizeds(authorizer);
            viaticosAuthorizeds = viaticosRepository.countViaticosByAuthorizedsAuthorizeds(authorizer);
            viaticosRejecteds = viaticosRepository.countViaticosByAuthorizerRejecteds(authorizer);

            arrayResolvedVsTotal[0] = viaticosNotAuthorizeds;
            arrayResolvedVsTotal[1] = viaticosAuthorizeds;
            arrayResolvedVsTotal[2] = viaticosRejecteds;

            DecimalFormat df = new DecimalFormat("#.##");

            if(viaticosNotAuthorizeds > 0){
                float NotManaged = (float)viaticosNotAuthorizeds / (float)totalForAuthorizer * 100;
                arrayLabelsTotal[0] = df.format(NotManaged) + "%";
            }else {
                arrayLabelsTotal[0] = "0%";
            }

            if (viaticosAuthorizeds > 0) {
                float loadeds = (float)viaticosAuthorizeds / (float)totalForAuthorizer * 100;
                arrayLabelsTotal[1] = df.format(loadeds) + "%";
            }else{
                arrayLabelsTotal[1] = "0%";
            }

            if (viaticosRejecteds > 0){
                float rejecteds = (float)viaticosRejecteds / (float)totalForAuthorizer * 100;
                arrayLabelsTotal[2] = df.format(rejecteds) + "%";
            }else {
                arrayLabelsTotal[2] = "0%";
            }

            arrayTotalResolvedVsTotal[0] = new Data(arrayResolvedVsTotal);

            List<TypeAuthorizer> typesAuthorizations = new ArrayList<>();

            List<User> listChiefs4 = userService.getLines4();
            if (listChiefs4.contains(authorizer)){
                typesAuthorizations.add(new TypeAuthorizer("Autorizante de área/subárea por monto", true));
            }

            List<User> listChiefs = userService.getLines3();
            if (listChiefs.contains(authorizer)){
                typesAuthorizations.add(new TypeAuthorizer("Autorizante de área por monto", true));
            }

            List<User> listManagers = userService.getLines2();
            if (listManagers.contains(authorizer)){
                typesAuthorizations.add(new TypeAuthorizer("Autorizante de área por monto.", true));
            }

            if (this.authorizerSuperiorService.isSuperior(authorizer)){
                typesAuthorizations.add(new TypeAuthorizer("Autorizante de gerencias y jefaturas.", true));
            }

            if (this.authorizerToSuperiorService.existsByColaborator(authorizer)){
                typesAuthorizations.add(new TypeAuthorizer("Autorizante de Gerente General.", true));
            }

            CardAuthorizers cardAuthorizers = new CardAuthorizers(
                    authorizer,
                    totalForAuthorizer,
                    viaticosNotAuthorizeds,
                    viaticosAuthorizeds,
                    viaticosRejecteds,
                    timeAverageResolution,
                    arrayTotalResolvedVsTotal,
                    arrayLabelsTotal,
                    typesAuthorizations
            );

            listCards.add(cardAuthorizers);

        }

        return listCards;

    }

    public List<CardTreasurers> getPerformanceTreasurers() {

        List<CardTreasurers> listCards = new ArrayList<>();
        List<Viaticos> listViaticosResolvedsForTreasurer;

        List<User> listaLimpia = listTreasurers();

        for (User treasurer : listaLimpia) {

            int[] arrayResolvedVsTotal = new int[2];
            String[] arrayLabelsTotal = new String[2];
            Data[] arrayTotalResolvedVsTotal = new Data[1];

            long[] arrayAmount = new long[2];
            String[] arrayLabelsAmount = new String[2];
            DataLong[] arrayTotalAmount = new DataLong[1];
            String amountNotPaidStr;
            String amountPaidStr;

            long totalTimeResolution = 0;
            long averageForTicket = 0;

            String timeAverageResolution = "";

            //Obtengo los viaticos que ya pago el tesorero
            listViaticosResolvedsForTreasurer = viaticosRepository.findResolvedsByTreasurer(treasurer);

            for (Viaticos viatico : listViaticosResolvedsForTreasurer) {

                Duration duration = Duration.between(viatico.getDateLoaded(), viatico.getDateEnd());

                totalTimeResolution = totalTimeResolution + duration.getSeconds();

            }

            if(listViaticosResolvedsForTreasurer.size() > 0) {
                averageForTicket = totalTimeResolution / listViaticosResolvedsForTreasurer.size();

                long days = averageForTicket / DAYS;
                long hours = ((averageForTicket % DAYS) / SECONDS_PER_HOUR);
                long minutes = ((averageForTicket % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
                long secs = (averageForTicket % SECONDS_PER_MINUTE);

                String day = "";
                if(days == 0){
                    timeAverageResolution = hours + "hs " + minutes + "min " + secs + "segs";
                }else if(days == 1){
                    day = "día ";
                    timeAverageResolution = days + day + hours + "hs " + minutes + "min " + secs + "segs";
                }else if(days > 1){
                    day = "días ";
                    timeAverageResolution = days + day + hours + "hs " + minutes + "min " + secs + "segs";
                }

            }else{
                timeAverageResolution = "No aplica";
            }

            totalForTreasurers= viaticosRepository.countAllViaticosByTreasurer(treasurer);
            viaticosNotPaids = viaticosRepository.countViaticosByTreasurerNotPaids(treasurer);
            viaticosPaids = viaticosRepository.countViaticosByTreasurerPaids(treasurer);

            if(viaticosRepository.sumaAmountForTreasurer(treasurer) != null){
                totalAmountForTreasurer = viaticosRepository.sumaAmountForTreasurer(treasurer);
            }else{
                totalAmountForTreasurer = 0;
            }

            if(viaticosRepository.sumNotPaidsForTreasurer(treasurer) != null){
                amountNotPaid = viaticosRepository.sumNotPaidsForTreasurer(treasurer);
            }else{
                amountNotPaid = 0;
            }

            if(viaticosRepository.sumPaidsForTreasurer(treasurer) != null){
                amountPaid = viaticosRepository.sumPaidsForTreasurer(treasurer);
            }else{
                amountPaid = 0;
            }

            arrayResolvedVsTotal[0] = viaticosNotPaids;
            arrayResolvedVsTotal[1] = viaticosPaids;
            arrayAmount[0] = amountNotPaid;
            arrayAmount[1] = amountPaid;

            DecimalFormat df = new DecimalFormat("#.##");

            if(viaticosNotPaids > 0){
                float notPaids = (float)viaticosNotPaids / (float)totalForTreasurers * 100;
                arrayLabelsTotal[0] = df.format(notPaids) + "%";
            }else {
                arrayLabelsTotal[0] = "0%";
            }

            if (viaticosPaids > 0) {
                float paids = (float)viaticosPaids / (float)totalForTreasurers * 100;
                arrayLabelsTotal[1] = df.format(paids) + "%";
            }else{
                arrayLabelsTotal[1] = "0%";
            }

            if(amountNotPaid > 0){
                float notPaids = (float)amountNotPaid / (float)totalAmountForTreasurer * 100;
                arrayLabelsAmount[0] = df.format(notPaids) + "%";
            }else {
                arrayLabelsAmount[0] = "0%";
            }

            if (amountPaid > 0) {
                float paids = (float)amountPaid / (float)totalAmountForTreasurer * 100;
                arrayLabelsAmount[1] = df.format(paids) + "%";
            }else{
                arrayLabelsAmount[1] = "0%";
            }

            String amountTotal = df.format(totalAmountForTreasurer);
            amountNotPaidStr = "$ " + df.format(amountNotPaid);
            amountPaidStr = "$ " + df.format(amountPaid);

            arrayTotalAmount[0] = new DataLong(arrayAmount);
            arrayTotalResolvedVsTotal[0] = new Data(arrayResolvedVsTotal);

            List<Branch> branchsForTreasurer = this.tableTreasurersService.getBranchsForTreasurer(treasurer);

            CardTreasurers cardTreasurers = new CardTreasurers(
                    treasurer,
                    totalForTreasurers,
                    viaticosNotPaids,
                    viaticosPaids,
                    timeAverageResolution,
                    arrayTotalResolvedVsTotal,
                    arrayLabelsTotal,
                    arrayTotalAmount,
                    arrayLabelsAmount,
                    amountNotPaidStr,
                    amountPaidStr,
                    amountTotal,
                    branchsForTreasurer

            );

            listCards.add(cardTreasurers);

        }

        return listCards;

    }

    public ReportGraphic getReportTotalTimePendings(){

        int[] arrayTicketsState = new int[3];
        String[] arrayLabels = new String[3];
        DataReport[] arrayData = new DataReport[1];

        int countNotAuthorized = viaticosRepository.countAllPendingsForAuthorize();
        int countNotCharge = viaticosRepository.countAllPendingsForCharge();
        int countNotPaid = viaticosRepository.countAllPendingsForPaid();

        arrayTicketsState[0] = countNotAuthorized;
        arrayTicketsState[1] = countNotCharge;
        arrayTicketsState[2] = countNotPaid;

        arrayLabels[0] = "Pendientes de Autorización";
        arrayLabels[1] = "Pendientes de Contabilización";
        arrayLabels[2] = "Pendientes de Pago";

        arrayData[0] = new DataReport(arrayTicketsState, "");

        return new ReportGraphic(arrayData, arrayLabels);

    }

    public ReportGraphic getReportForBranchs(){

        List<Branch> listBranchs = branchService.getAll();

        int[] arrayTicketsNotAuthorizeds = new int[listBranchs.size()];
        int[] arrayTicketsNotCharge = new int[listBranchs.size()];
        int[] arrayTicketsNotPaids = new int[listBranchs.size()];
        String[] arrayLabels = new String[listBranchs.size()];
        DataReport[] arrayData = new DataReport[3];

        for(int i = 0; i < listBranchs.size(); i++){
            int countPendingAuthorize = viaticosRepository.countAllPendingsForAuthorizeForBranch(listBranchs.get(i));
            int countPendingCharge = viaticosRepository.countAllPendingsForChargeForBranch(listBranchs.get(i));
            int countPendingPaid = viaticosRepository.countAllPendingsForPaidForBranch(listBranchs.get(i));

            arrayTicketsNotAuthorizeds[i] = countPendingAuthorize;
            arrayTicketsNotCharge[i] = countPendingCharge;
            arrayTicketsNotPaids[i] = countPendingPaid;
            arrayLabels[i] = listBranchs.get(i).getBrandsCompany().getCompany().getName() + " - " + listBranchs.get(i).getCity() + " [" + listBranchs.get(i).getBrandsCompany().getName() + "]";
        }

        arrayData[0] = new DataReport(arrayTicketsNotAuthorizeds, "Pendientes de Autorización");
        arrayData[1] = new DataReport(arrayTicketsNotCharge, "Pendientes de Contabilización");
        arrayData[2] = new DataReport(arrayTicketsNotPaids, "Pendientes de Pago");

        return new ReportGraphic(arrayData, arrayLabels);

    }

    public LineReportLong getReportBranchsAmounts(){

        List<Branch> listBranchs = branchService.getAll();

        long[] arrayBranchsNotPaids = new long[listBranchs.size()];
        long[] arrayBranchsPaids = new long[listBranchs.size()];
        long[] arrayBranchsTotal = new long[listBranchs.size()];
        String[] arrayLabels = new String[listBranchs.size()];
        DataLineLong[] arrayData = new DataLineLong[3];

        for(int i = 0; i < listBranchs.size(); i++){

            long amountNotPaid;
            if(viaticosRepository.sumaAmountForBranchNotPaid(listBranchs.get(i)) != null){
                amountNotPaid = viaticosRepository.sumaAmountForBranchNotPaid(listBranchs.get(i));
            }else{
                amountNotPaid = 0;
            }

            long amountPaid;
            if(viaticosRepository.sumaAmountForBranchPaid(listBranchs.get(i)) != null){
                amountPaid = viaticosRepository.sumaAmountForBranchPaid(listBranchs.get(i));
            }else{
                amountPaid = 0;
            }

            long amountTotal;
            if(viaticosRepository.sumaAmountTotalForBranch(listBranchs.get(i)) != null){
                amountTotal = viaticosRepository.sumaAmountTotalForBranch(listBranchs.get(i));
            }else{
                amountTotal = 0;
            }

            arrayBranchsNotPaids[i] = amountNotPaid;
            arrayBranchsPaids[i] = amountPaid;
            arrayBranchsTotal[i] = amountTotal;
            arrayLabels[i] = listBranchs.get(i).getBrandsCompany().getCompany().getName() + " - " + listBranchs.get(i).getCity() + " [" + listBranchs.get(i).getBrandsCompany().getName() + "]";
        }

        arrayData[0] = new DataLineLong(arrayBranchsNotPaids, "Pendiente de Pago", true, 0.5, "black", "rgba(255,0,0,0.3)");
        arrayData[1] = new DataLineLong(arrayBranchsPaids, "Pagado", true, 0.5, "black", "rgba(255,0,0,0.3)");
        arrayData[2] = new DataLineLong(arrayBranchsTotal, "Total", true, 0.5, "black", "rgba(255,0,0,0.3)");

        return new LineReportLong(arrayData, arrayLabels);

    }

    public ReportGraphicLong getReportForConcepts(){

        List<ConceptsCompras> listConcepts = conceptsComprasService.list();

        long[] arrayConcepts = new long[listConcepts.size()];
        String[] arrayLabels = new String[listConcepts.size()];
        DataReportLong[] arrayData = new DataReportLong[1];

        for(int i = 0; i < listConcepts.size(); i++){

            long amount;
            if(viaticosRepository.sumaAmountForConcept(listConcepts.get(i)) != null){
                amount = viaticosRepository.sumaAmountForConcept(listConcepts.get(i));
            }else{
                amount = 0;
            }

            arrayConcepts[i] = amount;
            arrayLabels[i] = listConcepts.get(i).getConcept();
        }

        arrayData[0] = new DataReportLong(arrayConcepts, "Monto");

        return new ReportGraphicLong(arrayData, arrayLabels);

    }

    public ReportMultipleLong getReportForBranchParametrized(Branch branch, int month, int year){

        List<ConceptsCompras> listConcepts = conceptsComprasService.list();

        long[] arrayConcepts = new long[2];
        long[] arrayConcepts2 = new long[listConcepts.size()];
        String[] arrayLabels = new String[2];
        String[] arrayLabelsConcepts = new String[listConcepts.size()];
        DataReportLong[] arrayData = new DataReportLong[1];
        DataReportLong[] arrayDataConcepts = new DataReportLong[1];

        long amountPaid;
        if(month != 0 && year != 0){
            if(viaticosRepository.sumPaidBranch(branch, month, year) != null){
                amountPaid = viaticosRepository.sumPaidBranch(branch, month, year);
            }else{
                amountPaid = 0;
            }
        }else if(month == 0 && year != 0){
            if(viaticosRepository.sumPaidBranchYear(branch, year) != null){
                amountPaid = viaticosRepository.sumPaidBranchYear(branch, year);
            }else{
                amountPaid = 0;
            }
        }else{
            if(viaticosRepository.sumAllPaidBranch(branch) != null){
                amountPaid = viaticosRepository.sumAllPaidBranch(branch);
            }else{
                amountPaid = 0;
            }
        }

        long amountNotPaid;
        if(month != 0 && year != 0){
            if(viaticosRepository.sumNotPaidBranch(branch, month, year) != null){
                amountNotPaid = viaticosRepository.sumNotPaidBranch(branch, month, year);
            }else{
                amountNotPaid = 0;
            }
        }else if(month == 0 && year != 0){
            if(viaticosRepository.sumNotPaidBranchYear(branch, year) != null){
                amountNotPaid = viaticosRepository.sumNotPaidBranchYear(branch, year);
            }else{
                amountNotPaid = 0;
            }
        }else{
            if(viaticosRepository.sumAllNotPaidBranch(branch) != null){
                amountNotPaid = viaticosRepository.sumAllNotPaidBranch(branch);
            }else{
                amountNotPaid = 0;
            }
        }

        arrayConcepts[0] = amountNotPaid;
        arrayConcepts[1] = amountPaid;
        arrayLabels[0] = "Pendiente: $" + amountNotPaid;
        arrayLabels[1] = "Abonado: $" + amountPaid;

        arrayData[0] = new DataReportLong(arrayConcepts, "Monto");

        for(int i = 0; i < listConcepts.size(); i++){

            long amount;
            if(month != 0 && year != 0) {
                if(viaticosRepository.sumaAmountConceptForBranchAndTime(listConcepts.get(i), branch, month, year) != null){
                    amount = viaticosRepository.sumaAmountConceptForBranchAndTime(listConcepts.get(i), branch, month, year);
                }else{
                    amount = 0;
                }
            }else if(month == 0 && year != 0) {
                if(viaticosRepository.sumaAmountConceptForBranchYear(listConcepts.get(i), branch, year) != null){
                    amount = viaticosRepository.sumaAmountConceptForBranchYear(listConcepts.get(i), branch, year);
                }else{
                    amount = 0;
                }
            }else{
                if(viaticosRepository.sumAllAmountConceptForBranch(listConcepts.get(i), branch) != null){
                    amount = viaticosRepository.sumAllAmountConceptForBranch(listConcepts.get(i), branch);
                }else{
                    amount = 0;
                }
            }

            arrayConcepts2[i] = amount;
            arrayLabelsConcepts[i] = listConcepts.get(i).getConcept();
        }

        arrayDataConcepts[0] = new DataReportLong(arrayConcepts2, "Monto");

        return new ReportMultipleLong(arrayData, arrayLabels, arrayDataConcepts, arrayLabelsConcepts);

    }

    public ReportMultipleLong getReportForCompanyParametrized(Company company, int month, int year){

        List<ConceptsCompras> listConcepts = conceptsComprasService.list();

        long[] arrayConcepts = new long[2];
        long[] arrayConcepts2 = new long[listConcepts.size()];
        String[] arrayLabels = new String[2];
        String[] arrayLabelsConcepts = new String[listConcepts.size()];
        DataReportLong[] arrayData = new DataReportLong[1];
        DataReportLong[] arrayDataConcepts = new DataReportLong[1];

        long amountPaid;
        if(month != 0 && year != 0){
            if(viaticosRepository.sumPaidCompany(company, month, year) != null){
                amountPaid = viaticosRepository.sumPaidCompany(company, month, year);
            }else{
                amountPaid = 0;
            }
        } else if(month == 0 && year != 0){
            if(viaticosRepository.sumPaidCompanyYear(company, year) != null){
                amountPaid = viaticosRepository.sumPaidCompanyYear(company, year);
            }else{
                amountPaid = 0;
            }
        }else{
            if(viaticosRepository.sumAllPaidCompany(company) != null){
                amountPaid = viaticosRepository.sumAllPaidCompany(company);
            }else{
                amountPaid = 0;
            }
        }

        long amountNotPaid;
        if(month != 0 && year != 0){
            if(viaticosRepository.sumNotPaidCompany(company, month, year) != null){
                amountNotPaid = viaticosRepository.sumNotPaidCompany(company, month, year);
            }else{
                amountNotPaid = 0;
            }
        }else if(month == 0 && year != 0){
            if(viaticosRepository.sumNotPaidCompanyYear(company, year) != null){
                amountNotPaid = viaticosRepository.sumNotPaidCompanyYear(company, year);
            }else{
                amountNotPaid = 0;
            }
        }else{
            if(viaticosRepository.sumAllNotPaidCompany(company) != null){
                amountNotPaid = viaticosRepository.sumAllNotPaidCompany(company);
            }else{
                amountNotPaid = 0;
            }
        }

        arrayConcepts[0] = amountNotPaid;
        arrayConcepts[1] = amountPaid;
        arrayLabels[0] = "Pendiente: $" + amountNotPaid;
        arrayLabels[1] = "Abonado: $" + amountPaid;

        arrayData[0] = new DataReportLong(arrayConcepts, "Monto");

        for(int i = 0; i < listConcepts.size(); i++){

            long amount;
            if(month != 0 && year != 0) {
                if(viaticosRepository.sumaAmountConceptForCompanyAndTime(listConcepts.get(i), company, month, year) != null){
                    amount = viaticosRepository.sumaAmountConceptForCompanyAndTime(listConcepts.get(i), company, month, year);
                }else{
                    amount = 0;
                }
            }else if(month == 0 && year != 0) {
                if(viaticosRepository.sumaAmountConceptForCompanyYear(listConcepts.get(i), company, year) != null){
                    amount = viaticosRepository.sumaAmountConceptForCompanyYear(listConcepts.get(i), company, year);
                }else{
                    amount = 0;
                }
            }else{
                if(viaticosRepository.sumAllAmountConceptForCompany(listConcepts.get(i), company) != null){
                    amount = viaticosRepository.sumAllAmountConceptForCompany(listConcepts.get(i), company);
                }else{
                    amount = 0;
                }
            }

            arrayConcepts2[i] = amount;
            arrayLabelsConcepts[i] = listConcepts.get(i).getConcept();
        }

        arrayDataConcepts[0] = new DataReportLong(arrayConcepts2, "Monto");

        return new ReportMultipleLong(arrayData, arrayLabels, arrayDataConcepts, arrayLabelsConcepts);

    }

    public ReportMultipleLong getReportForColaboratorParametrized(User colaborator, int month, int year){

        List<ConceptsCompras> listConcepts = conceptsComprasService.list();

        long[] arrayConcepts = new long[2];
        long[] arrayConcepts2 = new long[listConcepts.size()];
        String[] arrayLabels = new String[2];
        String[] arrayLabelsConcepts = new String[listConcepts.size()];
        DataReportLong[] arrayData = new DataReportLong[1];
        DataReportLong[] arrayDataConcepts = new DataReportLong[1];

        long amountPaid;
        if(month != 0 && year != 0){
            if(viaticosRepository.sumPaidColaborator(colaborator, month, year) != null){
                amountPaid = viaticosRepository.sumPaidColaborator(colaborator, month, year);
            }else{
                amountPaid = 0;
            }
        }else if(month == 0 && year != 0){
            if(viaticosRepository.sumPaidColaboratorYear(colaborator, year) != null){
                amountPaid = viaticosRepository.sumPaidColaboratorYear(colaborator, year);
            }else{
                amountPaid = 0;
            }
        }else{
            if(viaticosRepository.sumAllPaidColaborator(colaborator) != null){
                amountPaid = viaticosRepository.sumAllPaidColaborator(colaborator);
            }else{
                amountPaid = 0;
            }
        }

        long amountNotPaid;
        if(month != 0 && year != 0) {
            if(viaticosRepository.sumNotPaidColaborator(colaborator, month, year) != null){
                amountNotPaid = viaticosRepository.sumNotPaidColaborator(colaborator, month, year);
            }else{
                amountNotPaid = 0;
            }
        }else if(month == 0 && year != 0){
            if(viaticosRepository.sumNotPaidColaboratorYear(colaborator, year) != null){
                amountNotPaid = viaticosRepository.sumNotPaidColaboratorYear(colaborator, year);
            }else{
                amountNotPaid = 0;
            }
        }else{
            if(viaticosRepository.sumAllNotPaidColaborator(colaborator) != null){
                amountNotPaid = viaticosRepository.sumAllNotPaidColaborator(colaborator);
            }else{
                amountNotPaid = 0;
            }
        }

        arrayConcepts[0] = amountNotPaid;
        arrayConcepts[1] = amountPaid;
        arrayLabels[0] = "Pendiente: $" + amountNotPaid;
        arrayLabels[1] = "Abonado: $" + amountPaid;

        arrayData[0] = new DataReportLong(arrayConcepts, "Monto");

        for(int i = 0; i < listConcepts.size(); i++){

            long amount;
            if(month != 0 && year != 0) {
                if(viaticosRepository.sumaAmountConceptForColaboratorAndTime(listConcepts.get(i), colaborator, month, year) != null){
                    amount = viaticosRepository.sumaAmountConceptForColaboratorAndTime(listConcepts.get(i), colaborator, month, year);
                }else{
                    amount = 0;
                }
            }else  if(month == 0 && year != 0) {
                if(viaticosRepository.sumaAmountConceptForColaboratorYear(listConcepts.get(i), colaborator, year) != null){
                    amount = viaticosRepository.sumaAmountConceptForColaboratorYear(listConcepts.get(i), colaborator, year);
                }else{
                    amount = 0;
                }
            }else{
                if(viaticosRepository.sumaAllAmountConceptForColaborator(listConcepts.get(i), colaborator) != null){
                    amount = viaticosRepository.sumaAllAmountConceptForColaborator(listConcepts.get(i), colaborator);
                }else{
                    amount = 0;
                }
            }

            arrayConcepts2[i] = amount;
            arrayLabelsConcepts[i] = listConcepts.get(i).getConcept();
        }

        arrayDataConcepts[0] = new DataReportLong(arrayConcepts2, "Monto");

        return new ReportMultipleLong(arrayData, arrayLabels, arrayDataConcepts, arrayLabelsConcepts);

    }

    public User getAuthorizerForAmountAndSubsector(int lineUser, SubSector subSector, double amount, Branch branch){

       int userId;
       User authorizer = null;

        if (amount > 0 && amount < 30000){
            if (lineUser == 5){
                if (subSector.getName().equals("Postventa")){
                    userId = this.userService.getUsersByLineAndSubsectorAndCity(4, subSector, branch.getCity()).get(0);
                    authorizer = this.userService.getOne(userId).get();
                }else{
                    userId = this.userService.getUsersByLineAndSubsector(4, subSector).get(0);
                    authorizer = this.userService.getOne(userId).get();
                }
            }else if (lineUser == 4) {

                if (subSector.getName().equals("Postventa")) {
                    userId = this.userService.getUsersByLineAndSubsectorAndCompany(3, subSector, branch.getBrandsCompany().getCompany().getId()).get(0);
                }else if (subSector.getSector().getName().equals("Marketing")){
                    userId = this.userService.getUsersByLineAndSector(3, subSector.getSector()).get(0).getId();
                }else{
                    userId = this.userService.getUsersByLineAndSubsector(3, subSector).get(0);
                }

                authorizer = this.userService.getOne(userId).get();

            }else if (lineUser == 3){
                authorizer = this.userService.getUsersByLineAndSectorToAuthorizations(2, subSector.getSector()).get(0);
            }
        }else if(amount >= 30000){
            authorizer = this.userService.getUsersByLineAndSectorToAuthorizations(2, subSector.getSector()).get(0);
        }

       return authorizer;
    }

    public User getAuthorizerForAmountAndSubsectorComercialOrPDAorMaestranza(int lineUser, SubSector subSector, double amount, Branch branch){

        int userId;
        User authorizer = null;

        if (subSector.getName().equals("Comercial")){
            if (amount > 0 && amount < 30000){
                if (lineUser == 5){
                    userId = this.userService.getUsersByLineSubSectorAndBranch(4, subSector, branch).get(0);
                    authorizer = this.userService.getOne(userId).get();
                }else if(lineUser == 4){
                    userId = this.userService.getUsersByLineSubSectorAndBranch(3, subSector, branch).get(0);
                    authorizer = this.userService.getOne(userId).get();
                }else if (lineUser == 3){
                    authorizer = this.userService.getUsersByLineAndSectorToAuthorizations(2, subSector.getSector()).get(0);
                }
            }else if(amount >= 30000){
                authorizer = this.userService.getUsersByLineAndSectorToAuthorizations(2, subSector.getSector()).get(0);
            }
        }else if (subSector.getName().equals("Comercial-PDA")){
            if (amount > 0 && amount < 30000){
               if (lineUser == 5){
                    userId = this.userService.getUsersByLineSubSectorAndBranch(4, subSector, branch).get(0);
                    authorizer = this.userService.getOne(userId).get();
                }else if (lineUser == 4){
                    //userId = this.userService.getUsersIdsByLineAndSector(3, subSector.getSector()).get(0);
                    userId = this.userService.getUsersByLineSubSectorAndBranch(3, subSector, branch).get(0);
                    authorizer = this.userService.getOne(userId).get();
                }else if (lineUser == 3){
                    authorizer = this.userService.getUsersByLineAndSectorToAuthorizations(2, subSector.getSector()).get(0);
                }
            }else if(amount >= 30000){
                authorizer = this.userService.getUsersByLineAndSectorToAuthorizations(2, subSector.getSector()).get(0);
            }
        }

        return authorizer;
    }

    public Long getSumAmountMonthlyForColaborator(User user){

        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();

        Long sumaMonthly = this.viaticosRepository.sumAmountMonthlyForColaborator(user, month, year);

        return sumaMonthly;
    }

    public void save(Viaticos viatico){
        viaticosRepository.save(viatico);
    }

}

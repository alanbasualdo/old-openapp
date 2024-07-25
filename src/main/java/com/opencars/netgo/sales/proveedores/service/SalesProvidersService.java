package com.opencars.netgo.sales.proveedores.service;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.sales.proveedores.dto.*;
import com.opencars.netgo.sales.proveedores.entity.SalesProviders;
import com.opencars.netgo.sales.proveedores.entity.StatesProveedores;
import com.opencars.netgo.sales.proveedores.repository.SalesProvidersRepository;
import com.opencars.netgo.sales.viaticos.dto.TypeAuthorizer;
import com.opencars.netgo.support.tickets.dto.Data;
import com.opencars.netgo.support.tickets.dto.DataLong;
import com.opencars.netgo.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static com.opencars.netgo.support.tickets.service.TicketsService.*;
import static com.opencars.netgo.support.tickets.service.TicketsService.SECONDS_PER_MINUTE;

@Service
@Transactional
public class SalesProvidersService {

    @Autowired
    TableTreasurersProvidersService tableTreasurersProvidersService;

    private int totalForAnalyst;
    private int salesAuthorizeds;
    private int totalForAuthorizer;
    private int salesNotAuthorizeds;
    private int salesPaids;
    private int totalForTreasurers;
    private long amountNotPaidNotDefeated;
    private long amountNotPaidDefeated;
    private long amountPaid;
    private long totalAmountForTreasurer;
    private int salesLoadeds;
    private int salesNotLoaded;
    private int salesRejecteds;
    private int salesNotPaidsNotDefeated;
    private int salesNotPaidsDefeated;

    @Autowired
    SalesProvidersRepository salesProvidersRepository;

    @Autowired
    TableContableProvidersService tableContableProvidersService;


    public Optional<SalesProviders> getOne(long id){
        return salesProvidersRepository.findById(id);
    }

    public Page<SalesProviders> getAll(Specification<SalesProviders> spec, Pageable pageable) {
        return salesProvidersRepository.findAll(spec, pageable);
    }

    public void deleteById(long id){
        salesProvidersRepository.deleteById(id);
    }

    public List<SalesProviders> getPendingsForTreasurer(User treasurer, Branch branch){
        return salesProvidersRepository.findPendingsForTreasurers(treasurer, branch);
    }

    public List<User> getAuthorizersSector(){
        return salesProvidersRepository.findAuthorizersSector();
    }

    public List<User> getAuthorizersAccount(){
        return salesProvidersRepository.findAuthorizersAccount();
    }

    public List<User> getAuthorizersMaxAmount(){
        return salesProvidersRepository.findAuthorizersMaxAmount();
    }

    public List<SalesProviders> getPendingsForAnalyst(User analyst, Branch branch){
        return salesProvidersRepository.findPendingsForAnalyst(analyst, branch);
    }

    public Page<SalesProviders> getByColaborator(User colaborator, Pageable pageable){
        return salesProvidersRepository.findByColaborator(colaborator, pageable);
    }

    public Page<SalesProviders> getByProviderAndColaborator(long provider, int colaborator, Pageable pageable){
        return salesProvidersRepository.findByProviderAndColaborator(provider, colaborator, pageable);
    }

    public Page<SalesProviders> getByBranchAndColaborator(int branch, int colaborator, Pageable pageable){
        return salesProvidersRepository.findByBranchAndColaborator(branch, colaborator, pageable);
    }

    public Page<SalesProviders> getByProvider(long provider, Pageable pageable){
        return salesProvidersRepository.findByProvider(provider, pageable);
    }

    public Page<SalesProviders> getByBranch(int branch, Pageable pageable){
        return salesProvidersRepository.findByBranch(branch, pageable);
    }

    public Page<SalesProviders> getByProviderAndAuthorizer(long provider, int authorizer, Pageable pageable){
        return salesProvidersRepository.findByProviderAndAuthorizer(provider, authorizer, pageable);
    }

    public Page<SalesProviders> getByBranchAndAuthorizer(int branch, int authorizer, Pageable pageable){
        return salesProvidersRepository.findByBranchAndAuthorizer(branch, authorizer, pageable);
    }

    public Page<SalesProviders> getByProviderAndAnalyst(long provider, int analyst, Pageable pageable){
        return salesProvidersRepository.findByProviderAndAnalyst(provider, analyst, pageable);
    }

    public Page<SalesProviders> getByBranchAndAnalyst(int branch, int analyst, Pageable pageable){
        return salesProvidersRepository.findByBranchAndAnalyst(branch, analyst, pageable);
    }

    public Page<SalesProviders> getByProviderAndTreasurer(long provider, int treasurer, Pageable pageable){
        return salesProvidersRepository.findByProviderAndTreasurer(provider, treasurer, pageable);
    }

    public Page<SalesProviders> getByBranchAndTreasurer(int branch, int treasurer, Pageable pageable){
        return salesProvidersRepository.findByBranchAndTreasurer(branch, treasurer, pageable);
    }

    public Msg passSalesPendingsToOtherAnalyst(long id, User analyst) {

        String message = "";
        try {

            SalesProviders saleUpdated = this.getOne(id).get();
            saleUpdated.setAnalyst(analyst);

            this.save(saleUpdated);

            return new Msg("Registro Guardado", HttpStatus.OK.value());


        } catch (Exception e) {
            message = "Error: " + e;
            return new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public Msg passSalesPendingsToOtherTreasurer(long id, User treasurer) {

        String message = "";
        try {

            SalesProviders saleUpdated = this.getOne(id).get();
            saleUpdated.setTreasurer(treasurer);

            this.save(saleUpdated);

            return new Msg("Registro Guardado", HttpStatus.OK.value());


        } catch (Exception e) {
            message = "Error: " + e;
            return new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public Page<SalesProviders> getByAuthorizerPendings(User authorizer, Pageable pageable){
        return salesProvidersRepository.findByAuthorizerPendings(authorizer, pageable);
    }

    public List<SalesProviders> getListByAuthorizerPendings(User authorizer){
        return salesProvidersRepository.findListByAuthorizerPendings(authorizer);
    }

    public Page<SalesProviders> getByRejectedBy(User authorizer, Pageable pageable){
        return salesProvidersRepository.findByRejectedBy(authorizer, pageable);
    }

    public Page<SalesProviders> getUnloadsAnalyst(User analyst, Pageable pageable){
        return salesProvidersRepository.findUnloadsByAnalyst(analyst, pageable);
    }

    public Page<SalesProviders> getAllDataPaidReprogramed(Pageable pageable){
        return salesProvidersRepository.findAllDataPaidReprogramed(pageable);
    }

    public Page<SalesProviders> getAllDataPaidAuthorizeds(Pageable pageable){
        return salesProvidersRepository.findAllDataPaidAuthorizeds(pageable);
    }

    public Page<SalesProviders> getAllDataPaidUnauthorizeds(Pageable pageable){
        return salesProvidersRepository.findAllDataPaidUnauthorizeds(pageable);
    }

    public Page<SalesProviders> getAllNotRejectedAndLoaded(User analyst, Pageable pageable){
        return salesProvidersRepository.findAllNotRejectedAndLoaded(analyst, pageable);
    }

    public Page<SalesProviders> getByTreasurerAndState(User treasurer, StatesProveedores state, Pageable pageable){
        return salesProvidersRepository.findByTreasurerAndState(treasurer, state, pageable);
    }

    public List<User> listAnalysts(){
        List<User> listAnalysts = salesProvidersRepository.findAnalystsActivatedAndDeactivated();
        return listAnalysts;
    }

    public List<User> listTreasurers(){
        List<User> listTreasurers = salesProvidersRepository.findTreasurersActivatedAndDeactivated();
        return listTreasurers;
    }

    public List<SalesToExport> getAllPendingsForClose(){
        List<SalesToExport> list = salesProvidersRepository.findAllPendingsForClose();
        return list;
    }

    // Get closed sales
    public List<SalesToExport> getAllClosedSales(){
        List<SalesToExport> list = salesProvidersRepository.findAllClosedSales();
        return list;
    }

    public Page<SalesProviders> getByTreasurersPendings(Pageable pageable){
        return salesProvidersRepository.findByTreasurersPendings(pageable);
    }

    public Msg passSalesPendingsToOtherAuthorizer(long id, User authorizer) {

        String message = "";
        try {

            SalesProviders saleUpdated = this.getOne(id).get();

            if (saleUpdated.getAuthorizerSector().getId() == authorizer.getId()){
                saleUpdated.setAuthorizerSector(authorizer);
            }

            if (saleUpdated.getAuthorizerAccount().getId() == authorizer.getId()){
                saleUpdated.setAuthorizerAccount(authorizer);
            }

            if (saleUpdated.getAuthorizerMaxAmount().getId() == authorizer.getId()){
                saleUpdated.setAuthorizerMaxAmount(authorizer);
            }

            this.save(saleUpdated);

            return new Msg("Registro Guardado", HttpStatus.OK.value());


        } catch (Exception e) {
            message = "Error: " + e;
            return new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public List<CardAnalystsProviders> getPerformanceAnalysts() {

        List<CardAnalystsProviders> listCards = new ArrayList<>();
        List<SalesProviders> listSalesClosedForAnalyst;

        List<User> listaLimpia = listAnalysts();

        for (User analyst : listaLimpia) {

            int[] arrayResolvedVsTotal = new int[3];
            String[] arrayLabelsTotal = new String[3];
            Data[] arrayTotalResolvedVsTotal = new Data[1];

            long totalTimeResolution = 0;
            long averageForTicket = 0;

            String timeAverageResolution = "";

            listSalesClosedForAnalyst = salesProvidersRepository.findResolvedsByAnalyst(analyst);
            Duration duration = Duration.ofSeconds(0);
            for (SalesProviders sale : listSalesClosedForAnalyst) {
                if(sale.getState().getId() == 7){
                    if(sale.getRejectedBy() == analyst){
                        if (sale.getDateAuthorizedMaxAmount() != null){
                            duration = Duration.between(sale.getDateAuthorizedMaxAmount(), sale.getDateEnd());
                        }else if (sale.getDateAuthorizedAccount() != null){
                            duration = Duration.between(sale.getDateAuthorizedAccount(), sale.getDateEnd());
                        }else if (sale.getDateAuthorizedSector() != null){
                            duration = Duration.between(sale.getDateAuthorizedSector(), sale.getDateEnd());
                        }
                    }
                }else{
                    if (sale.getDateAuthorizedMaxAmount() != null){
                        duration = Duration.between(sale.getDateAuthorizedMaxAmount(), sale.getDateEnd());
                    }else if (sale.getDateAuthorizedAccount() != null){
                        duration = Duration.between(sale.getDateAuthorizedAccount(), sale.getDateEnd());
                    }else if (sale.getDateAuthorizedSector() != null){
                        duration = Duration.between(sale.getDateAuthorizedSector(), sale.getDateEnd());
                    }
                }
                totalTimeResolution = totalTimeResolution + duration.getSeconds();

            }

            if(listSalesClosedForAnalyst.size() > 0) {
                averageForTicket = totalTimeResolution / listSalesClosedForAnalyst.size();

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

            totalForAnalyst= salesProvidersRepository.countAllSalesByAnalyst(analyst);
            salesNotLoaded = salesProvidersRepository.countSalesByAnalystNotManageds(analyst);
            salesLoadeds = salesProvidersRepository.countSalesByAnalystLoades(analyst);
            salesRejecteds = salesProvidersRepository.countSalesByAnalystRejecteds(analyst);

            arrayResolvedVsTotal[0] = salesNotLoaded;
            arrayResolvedVsTotal[1] = salesLoadeds;
            arrayResolvedVsTotal[2] = salesRejecteds;

            DecimalFormat df = new DecimalFormat("#.##");

            if(salesNotLoaded > 0){
                float NotManaged = (float)salesNotLoaded / (float)totalForAnalyst * 100;
                arrayLabelsTotal[0] = df.format(NotManaged) + "%";
            }else {
                arrayLabelsTotal[0] = "0%";
            }

            if (salesLoadeds > 0) {
                float loadeds = (float)salesLoadeds / (float)totalForAnalyst * 100;
                arrayLabelsTotal[1] = df.format(loadeds) + "%";
            }else{
                arrayLabelsTotal[1] = "0%";
            }

            if (salesRejecteds > 0){
                float rejecteds = (float) salesRejecteds / (float)totalForAnalyst * 100;
                arrayLabelsTotal[2] = df.format(rejecteds) + "%";
            }else {
                arrayLabelsTotal[2] = "0%";
            }

            arrayTotalResolvedVsTotal[0] = new Data(arrayResolvedVsTotal);

            List<Branch> branchsForAnalyst = this.tableContableProvidersService.getBranchsForAnalyst(analyst);

            CardAnalystsProviders cardAnalysts = new CardAnalystsProviders(
                    analyst,
                    totalForAnalyst,
                    salesNotLoaded,
                    salesLoadeds,
                    salesRejecteds,
                    timeAverageResolution,
                    arrayTotalResolvedVsTotal,
                    arrayLabelsTotal,
                    branchsForAnalyst
            );

            listCards.add(cardAnalysts);

        }

        return listCards;

    }

    public List<CardTreasurersProviders> getPerformanceTreasurers() {

        List<CardTreasurersProviders> listCards = new ArrayList<>();
        List<SalesProviders> listSalesResolvedsForTreasurer;

        List<User> listaLimpia = listTreasurers();

        for (User treasurer : listaLimpia) {

            int[] arrayResolvedVsTotal = new int[3];
            String[] arrayLabelsTotal = new String[3];
            Data[] arrayTotalResolvedVsTotal = new Data[1];

            long[] arrayAmount = new long[3];
            String[] arrayLabelsAmount = new String[3];
            DataLong[] arrayTotalAmount = new DataLong[1];
            String amountNotPaidNotDefeatedStr;
            String amountNotPaidDefeatedStr;
            String amountPaidStr;

            long totalTimeResolution = 0;
            long averageForTicket = 0;

            String timeAverageResolution = "";

            //Obtengo las compras que ya pago el tesorero
            listSalesResolvedsForTreasurer = salesProvidersRepository.findResolvedsByTreasurer(treasurer);

            for (SalesProviders sale : listSalesResolvedsForTreasurer) {

                Duration duration = Duration.between(sale.getDateLoaded(), sale.getDateEnd());

                totalTimeResolution = totalTimeResolution + duration.getSeconds();

            }

            if(listSalesResolvedsForTreasurer.size() > 0) {
                averageForTicket = totalTimeResolution / listSalesResolvedsForTreasurer.size();

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

            LocalDate date = LocalDate.now();
            totalForTreasurers= salesProvidersRepository.countAllSalesByTreasurer(treasurer);
            salesNotPaidsNotDefeated = salesProvidersRepository.countSalesByTreasurerNotPaidsNotDefeated(treasurer, date); //no vencidos
            salesNotPaidsDefeated = salesProvidersRepository.countSalesByTreasurerNotPaidsDefeated(treasurer, date); //vencidos
            salesPaids = salesProvidersRepository.countSalesByTreasurerPaids(treasurer); //pagos

            if(salesProvidersRepository.sumaAmountForTreasurer(treasurer) != null){
                totalAmountForTreasurer = salesProvidersRepository.sumaAmountForTreasurer(treasurer);
            }else{
                totalAmountForTreasurer = 0;
            }

            if(salesProvidersRepository.sumNotPaidsNotDefeatedForTreasurer(treasurer, date) != null){
                amountNotPaidNotDefeated = salesProvidersRepository.sumNotPaidsNotDefeatedForTreasurer(treasurer, date);
            }else{
                amountNotPaidNotDefeated = 0;
            }

            if(salesProvidersRepository.sumNotPaidsDefeatedForTreasurer(treasurer, date) != null){
                amountNotPaidDefeated = salesProvidersRepository.sumNotPaidsDefeatedForTreasurer(treasurer, date);
            }else{
                amountNotPaidDefeated = 0;
            }

            if(salesProvidersRepository.sumPaidsForTreasurer(treasurer) != null){
                amountPaid = salesProvidersRepository.sumPaidsForTreasurer(treasurer);
            }else{
                amountPaid = 0;
            }

            arrayResolvedVsTotal[0] = salesNotPaidsNotDefeated;
            arrayResolvedVsTotal[1] = salesNotPaidsDefeated;
            arrayResolvedVsTotal[2] = salesPaids;
            arrayAmount[0] = amountNotPaidNotDefeated;
            arrayAmount[1] = amountNotPaidDefeated;
            arrayAmount[2] = amountPaid;

            DecimalFormat df = new DecimalFormat("#.##");

            if(salesNotPaidsNotDefeated > 0){
                float notPaids = (float)salesNotPaidsNotDefeated / (float)totalForTreasurers * 100;
                arrayLabelsTotal[0] = df.format(notPaids) + "%";
            }else {
                arrayLabelsTotal[0] = "0%";
            }

            if(salesNotPaidsDefeated > 0){
                float notPaids = (float)salesNotPaidsDefeated / (float)totalForTreasurers * 100;
                arrayLabelsTotal[1] = df.format(notPaids) + "%";
            }else {
                arrayLabelsTotal[1] = "0%";
            }

            if (salesPaids > 0) {
                float paids = (float)salesPaids / (float)totalForTreasurers * 100;
                arrayLabelsTotal[2] = df.format(paids) + "%";
            }else{
                arrayLabelsTotal[2] = "0%";
            }

            if(amountNotPaidNotDefeated > 0){
                float notPaids = (float)amountNotPaidNotDefeated / (float)totalAmountForTreasurer * 100;
                arrayLabelsAmount[0] = df.format(notPaids) + "%";
            }else {
                arrayLabelsAmount[0] = "0%";
            }

            if(amountNotPaidDefeated > 0){
                float notPaids = (float)amountNotPaidDefeated / (float)totalAmountForTreasurer * 100;
                arrayLabelsAmount[1] = df.format(notPaids) + "%";
            }else {
                arrayLabelsAmount[1] = "0%";
            }

            if (amountPaid > 0) {
                float paids = (float)amountPaid / (float)totalAmountForTreasurer * 100;
                arrayLabelsAmount[2] = df.format(paids) + "%";
            }else{
                arrayLabelsAmount[2] = "0%";
            }

            String amountTotal = df.format(totalAmountForTreasurer);
            amountNotPaidNotDefeatedStr = "$ " + df.format(amountNotPaidNotDefeated);
            amountNotPaidDefeatedStr = "$ " + df.format(amountNotPaidDefeated);
            amountPaidStr = "$ " + df.format(amountPaid);

            arrayTotalAmount[0] = new DataLong(arrayAmount);
            arrayTotalResolvedVsTotal[0] = new Data(arrayResolvedVsTotal);

            List<Branch> branchsForTreasurer = this.tableTreasurersProvidersService.getBranchsForTreasurer(treasurer);

            CardTreasurersProviders cardTreasurers = new CardTreasurersProviders(
                    treasurer,
                    totalForTreasurers,
                    salesNotPaidsNotDefeated,
                    salesNotPaidsDefeated,
                    salesPaids,
                    timeAverageResolution,
                    arrayTotalResolvedVsTotal,
                    arrayLabelsTotal,
                    arrayTotalAmount,
                    arrayLabelsAmount,
                    amountNotPaidNotDefeatedStr,
                    amountNotPaidDefeatedStr,
                    amountPaidStr,
                    amountTotal,
                    branchsForTreasurer

            );

            listCards.add(cardTreasurers);

        }

        return listCards;

    }

    public List<User> listAuthorizers(){
        List<User> listAuthorizersArray = new ArrayList<>();
        List<User> listAuthorizersSectors = this.getAuthorizersSector();
        List<User> listAuthorizersAccount = this.getAuthorizersAccount();
        List<User> listAuthorizersMaxAmount = this.getAuthorizersMaxAmount();

        if (listAuthorizersSectors.size() > 0){
            listAuthorizersArray.addAll(listAuthorizersSectors);
        }

        if (listAuthorizersAccount.size() > 0){
            listAuthorizersArray.addAll(listAuthorizersAccount);
        }

        if (listAuthorizersMaxAmount.size() > 0){
            listAuthorizersArray.addAll(listAuthorizersMaxAmount);
        }

        List<User> listaLimpia = new ArrayList<>();

        //Forma número 1 (Uso de Maps).
        Map<Integer, User> mapAnalyst = new HashMap<>(listAuthorizersArray.size());

        //Aquí está la magia
        for(User p : listAuthorizersArray) {
            mapAnalyst.put(p.getId(), p);
        }

        //Agrego cada elemento del map a una nueva lista y muestro cada elemento.
        for(Map.Entry<Integer, User> p : mapAnalyst.entrySet()) {
            listaLimpia.add(p.getValue());
        }

        return listaLimpia;
    }

    public List<CardAuthorizersProviders> getPerformanceAuthorizers() {

        List<CardAuthorizersProviders> listCards = new ArrayList<>();
        List<SalesProviders> listSalesClosedForAuthorizers;

        List<User> listaLimpia = listAuthorizers();

        for (User authorizer : listaLimpia) {

            int[] arrayResolvedVsTotal = new int[3];
            String[] arrayLabelsTotal = new String[3];
            Data[] arrayTotalResolvedVsTotal = new Data[1];

            long totalTimeResolution = 0;
            long averageForTicket = 0;

            String timeAverageResolution = "";

            listSalesClosedForAuthorizers = salesProvidersRepository.findResolvedsByAuthorizer(authorizer);
            Duration duration = Duration.ofSeconds(0);
            for (SalesProviders sale : listSalesClosedForAuthorizers) {
                if(sale.getState().getId() == 3 || sale.getState().getId() == 4 || sale.getState().getId() == 5 || sale.getState().getId() == 6){
                    if(authorizer == sale.getAuthorizerSector()){
                        duration = Duration.between(sale.getDateInit(), sale.getDateAuthorizedSector());
                    }else if (authorizer == sale.getAuthorizerAccount()){
                        duration = Duration.between(sale.getDateInit(), sale.getDateAuthorizedAccount());
                    }else if (authorizer == sale.getAuthorizerMaxAmount()){
                        duration = Duration.between(sale.getDateInit(), sale.getDateAuthorizedMaxAmount());
                    }
                }else{
                    if(sale.getRejectedBy() == authorizer){
                        duration = Duration.between(sale.getDateInit(), sale.getDateEnd());
                    }
                }

                totalTimeResolution = totalTimeResolution + duration.getSeconds();

            }

            if(listSalesClosedForAuthorizers.size() > 0) {
                averageForTicket = totalTimeResolution / listSalesClosedForAuthorizers.size();

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

            totalForAuthorizer= salesProvidersRepository.countAllSalesByAuthorizer(authorizer);
            salesNotAuthorizeds = salesProvidersRepository.countSalesByAuthorizerNotAuthorizeds(authorizer);
            salesAuthorizeds = salesProvidersRepository.countSalesByAuthorizedsAuthorizeds(authorizer);
            salesRejecteds = salesProvidersRepository.countSalesByAuthorizerRejecteds(authorizer);

            arrayResolvedVsTotal[0] = salesNotAuthorizeds;
            arrayResolvedVsTotal[1] = salesAuthorizeds;
            arrayResolvedVsTotal[2] = salesRejecteds;

            DecimalFormat df = new DecimalFormat("#.##");

            if(salesNotAuthorizeds > 0){
                float NotManaged = (float)salesNotAuthorizeds / (float)totalForAuthorizer * 100;
                arrayLabelsTotal[0] = df.format(NotManaged) + "%";
            }else {
                arrayLabelsTotal[0] = "0%";
            }

            if (salesAuthorizeds > 0) {
                float loadeds = (float)salesAuthorizeds / (float)totalForAuthorizer * 100;
                arrayLabelsTotal[1] = df.format(loadeds) + "%";
            }else{
                arrayLabelsTotal[1] = "0%";
            }

            if (salesRejecteds > 0){
                float rejecteds = (float)salesRejecteds / (float)totalForAuthorizer * 100;
                arrayLabelsTotal[2] = df.format(rejecteds) + "%";
            }else {
                arrayLabelsTotal[2] = "0%";
            }

            arrayTotalResolvedVsTotal[0] = new Data(arrayResolvedVsTotal);

            List<TypeAuthorizer> typesAuthorizations = new ArrayList<>();

            List<User> listAuthSectors = this.getAuthorizersSector();
            if (listAuthSectors.contains(authorizer)){
                typesAuthorizations.add(new TypeAuthorizer("Autorizante designado de concepto por monto", true));
            }

            List<User> listAuthAccount = this.getAuthorizersAccount();
            if (listAuthAccount.contains(authorizer)){
                typesAuthorizations.add(new TypeAuthorizer("Autorizante de concepto", true));
            }

            List<User> listAuthMaxAmount = this.getAuthorizersMaxAmount();
            if (listAuthMaxAmount.contains(authorizer)){
                typesAuthorizations.add(new TypeAuthorizer("Autorizante por monto Extraordinario", true));
            }

            CardAuthorizersProviders cardAuthorizers = new CardAuthorizersProviders(
                    authorizer,
                    totalForAuthorizer,
                    salesNotAuthorizeds,
                    salesAuthorizeds,
                    salesRejecteds,
                    timeAverageResolution,
                    arrayTotalResolvedVsTotal,
                    arrayLabelsTotal,
                    typesAuthorizations
            );

            listCards.add(cardAuthorizers);

        }

        return listCards;

    }

    public LocalDate calculateLastThursday(LocalDate expiration) {
        LocalDate dateLimit = expiration;
        LocalDate lastThursday = dateLimit.with(TemporalAdjusters.previous(DayOfWeek.THURSDAY));
        if (dateLimit.getDayOfWeek() == DayOfWeek.THURSDAY ) {
            lastThursday = lastThursday.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
        }
        return lastThursday;
    }

    public LocalDate lastThursdayBeforeDay10(LocalDate dateEmision) {
        LocalDate dateLimit = dateEmision.withDayOfMonth(10);
        LocalDate lastThursday = dateLimit.with(TemporalAdjusters.previous(DayOfWeek.THURSDAY));
        if (lastThursday.isAfter(dateLimit)) {
            lastThursday = lastThursday.with(TemporalAdjusters.previous(DayOfWeek.THURSDAY));
        }
        return lastThursday;
    }

    public List<ReportFinalProviders> generateReport() {
        List<ReportProviders> reportData = salesProvidersRepository.findProvidersReport();

        Map<Integer, ReportFinalProviders> reportMap = new HashMap<>();

        for (ReportProviders report : reportData) {
            if (!reportMap.containsKey(report.getIdColaborator())) {
                ReportFinalProviders reportFinal = new ReportFinalProviders(report.getColaborator(), report.getIdColaborator(), new ConceptChargeds[0], 0, 0L);
                reportMap.put(report.getIdColaborator(), reportFinal);
            }

            ReportFinalProviders existingReportFinal = reportMap.get(report.getIdColaborator());

            ConceptChargeds[] conceptChargedsArray = existingReportFinal.getConceptChargeds();
            List<ConceptChargeds> conceptChargedsList = new ArrayList<>(Arrays.asList(conceptChargedsArray));
            conceptChargedsList.add(new ConceptChargeds(report.getSubConcept(), report.getIdSubconcept(), report.getInvoiceCount(), report.getInvoiceSum(), report.getRejecteds(), report.getNotRejecteds()));

            existingReportFinal.setConceptChargeds(conceptChargedsList.toArray(new ConceptChargeds[0]));

            // Sumar el monto al amountTotal usando el valor de report.getInvoiceSum()
            // y otros campos relevantes de ConceptChargeds
            ConceptChargeds conceptCharged = conceptChargedsList.get(conceptChargedsList.size() - 1);
            existingReportFinal.setAmountTotal(existingReportFinal.getAmountTotal() + conceptCharged.getSum());
            existingReportFinal.setTotalInvoices(existingReportFinal.getTotalInvoices() + report.getInvoiceCount());
        }

        return new ArrayList<>(reportMap.values());
    }

    public List<ReportFinalProviders> generateReportByProvider(long provider) {
        List<ReportProviders> reportData = salesProvidersRepository.findProvidersReportByProvider(provider);

        Map<Integer, ReportFinalProviders> reportMap = new HashMap<>();

        for (ReportProviders report : reportData) {
            if (!reportMap.containsKey(report.getIdColaborator())) {
                ReportFinalProviders reportFinal = new ReportFinalProviders(report.getColaborator(), report.getIdColaborator(), new ConceptChargeds[0], 0, 0L);
                reportMap.put(report.getIdColaborator(), reportFinal);
            }

            ReportFinalProviders existingReportFinal = reportMap.get(report.getIdColaborator());

            ConceptChargeds[] conceptChargedsArray = existingReportFinal.getConceptChargeds();
            List<ConceptChargeds> conceptChargedsList = new ArrayList<>(Arrays.asList(conceptChargedsArray));
            conceptChargedsList.add(new ConceptChargeds(report.getSubConcept(), report.getIdSubconcept(), report.getInvoiceCount(), report.getInvoiceSum(), report.getRejecteds(), report.getNotRejecteds()));

            existingReportFinal.setConceptChargeds(conceptChargedsList.toArray(new ConceptChargeds[0]));

            // Sumar el monto al amountTotal usando el valor de report.getInvoiceSum()
            // y otros campos relevantes de ConceptChargeds
            ConceptChargeds conceptCharged = conceptChargedsList.get(conceptChargedsList.size() - 1);
            existingReportFinal.setAmountTotal(existingReportFinal.getAmountTotal() + conceptCharged.getSum());
            existingReportFinal.setTotalInvoices(existingReportFinal.getTotalInvoices() + report.getInvoiceCount());
        }

        return new ArrayList<>(reportMap.values());
    }

    public List<ReportFinalProviders> generateReportFilterByDate(LocalDateTime dateInit, LocalDateTime dateEnd) {
        List<ReportProviders> reportData = salesProvidersRepository.findProvidersReportByDateRange(dateInit, dateEnd);

        Map<Integer, ReportFinalProviders> reportMap = new HashMap<>();

        for (ReportProviders report : reportData) {
            if (!reportMap.containsKey(report.getIdColaborator())) {
                ReportFinalProviders reportFinal = new ReportFinalProviders(report.getColaborator(), report.getIdColaborator(), new ConceptChargeds[0], 0, 0L);
                reportMap.put(report.getIdColaborator(), reportFinal);
            }

            ReportFinalProviders existingReportFinal = reportMap.get(report.getIdColaborator());

            ConceptChargeds[] conceptChargedsArray = existingReportFinal.getConceptChargeds();
            List<ConceptChargeds> conceptChargedsList = new ArrayList<>(Arrays.asList(conceptChargedsArray));
            conceptChargedsList.add(new ConceptChargeds(report.getSubConcept(), report.getIdSubconcept(), report.getInvoiceCount(), report.getInvoiceSum(), report.getRejecteds(), report.getNotRejecteds()));

            existingReportFinal.setConceptChargeds(conceptChargedsList.toArray(new ConceptChargeds[0]));

            // Sumar el monto al amountTotal usando el valor de report.getInvoiceSum()
            // y otros campos relevantes de ConceptChargeds
            ConceptChargeds conceptCharged = conceptChargedsList.get(conceptChargedsList.size() - 1);
            existingReportFinal.setAmountTotal(existingReportFinal.getAmountTotal() + conceptCharged.getSum());
            existingReportFinal.setTotalInvoices(existingReportFinal.getTotalInvoices() + report.getInvoiceCount());
        }

        return new ArrayList<>(reportMap.values());
    }

    public List<ReportFinalProviders> generateReportFilterByDateAndProvider(LocalDateTime dateInit, LocalDateTime dateEnd, long provider) {
        List<ReportProviders> reportData = salesProvidersRepository.findProvidersReportByDateRangeAndProvider(dateInit, dateEnd, provider);

        Map<Integer, ReportFinalProviders> reportMap = new HashMap<>();

        for (ReportProviders report : reportData) {
            if (!reportMap.containsKey(report.getIdColaborator())) {
                ReportFinalProviders reportFinal = new ReportFinalProviders(report.getColaborator(), report.getIdColaborator(), new ConceptChargeds[0], 0, 0L);
                reportMap.put(report.getIdColaborator(), reportFinal);
            }

            ReportFinalProviders existingReportFinal = reportMap.get(report.getIdColaborator());

            ConceptChargeds[] conceptChargedsArray = existingReportFinal.getConceptChargeds();
            List<ConceptChargeds> conceptChargedsList = new ArrayList<>(Arrays.asList(conceptChargedsArray));
            conceptChargedsList.add(new ConceptChargeds(report.getSubConcept(), report.getIdSubconcept(), report.getInvoiceCount(), report.getInvoiceSum(), report.getRejecteds(), report.getNotRejecteds()));

            existingReportFinal.setConceptChargeds(conceptChargedsList.toArray(new ConceptChargeds[0]));

            // Sumar el monto al amountTotal usando el valor de report.getInvoiceSum()
            // y otros campos relevantes de ConceptChargeds
            ConceptChargeds conceptCharged = conceptChargedsList.get(conceptChargedsList.size() - 1);
            existingReportFinal.setAmountTotal(existingReportFinal.getAmountTotal() + conceptCharged.getSum());
            existingReportFinal.setTotalInvoices(existingReportFinal.getTotalInvoices() + report.getInvoiceCount());
        }

        return new ArrayList<>(reportMap.values());
    }

    public List<ReportFinalProviders> getByCoincidenceInAmount(double amount) {
        List<ReportProviders> reportData = salesProvidersRepository.findByCoincidenceInAmount(amount);

        Map<Integer, ReportFinalProviders> reportMap = new HashMap<>();

        for (ReportProviders report : reportData) {
            if (!reportMap.containsKey(report.getIdColaborator())) {
                ReportFinalProviders reportFinal = new ReportFinalProviders(report.getColaborator(), report.getIdColaborator(), new ConceptChargeds[0], 0, 0L);
                reportMap.put(report.getIdColaborator(), reportFinal);
            }

            ReportFinalProviders existingReportFinal = reportMap.get(report.getIdColaborator());

            ConceptChargeds[] conceptChargedsArray = existingReportFinal.getConceptChargeds();
            List<ConceptChargeds> conceptChargedsList = new ArrayList<>(Arrays.asList(conceptChargedsArray));
            conceptChargedsList.add(new ConceptChargeds(report.getSubConcept(), report.getIdSubconcept(), report.getInvoiceCount(), report.getInvoiceSum(), report.getRejecteds(), report.getNotRejecteds()));

            existingReportFinal.setConceptChargeds(conceptChargedsList.toArray(new ConceptChargeds[0]));

            // Sumar el monto al amountTotal usando el valor de report.getInvoiceSum()
            // y otros campos relevantes de ConceptChargeds
            ConceptChargeds conceptCharged = conceptChargedsList.get(conceptChargedsList.size() - 1);
            existingReportFinal.setAmountTotal(existingReportFinal.getAmountTotal() + conceptCharged.getSum());
            existingReportFinal.setTotalInvoices(existingReportFinal.getTotalInvoices() + report.getInvoiceCount());
        }

        return new ArrayList<>(reportMap.values());
    }

    public List<ReportFinalProviders> getByCoincidenceInAmountAndProvider(double amount, long provider) {
        List<ReportProviders> reportData = salesProvidersRepository.findByCoincidenceInAmountAndProvider(amount, provider);

        Map<Integer, ReportFinalProviders> reportMap = new HashMap<>();

        for (ReportProviders report : reportData) {
            if (!reportMap.containsKey(report.getIdColaborator())) {
                ReportFinalProviders reportFinal = new ReportFinalProviders(report.getColaborator(), report.getIdColaborator(), new ConceptChargeds[0], 0, 0L);
                reportMap.put(report.getIdColaborator(), reportFinal);
            }

            ReportFinalProviders existingReportFinal = reportMap.get(report.getIdColaborator());

            ConceptChargeds[] conceptChargedsArray = existingReportFinal.getConceptChargeds();
            List<ConceptChargeds> conceptChargedsList = new ArrayList<>(Arrays.asList(conceptChargedsArray));
            conceptChargedsList.add(new ConceptChargeds(report.getSubConcept(), report.getIdSubconcept(), report.getInvoiceCount(), report.getInvoiceSum(), report.getRejecteds(), report.getNotRejecteds()));

            existingReportFinal.setConceptChargeds(conceptChargedsList.toArray(new ConceptChargeds[0]));

            // Sumar el monto al amountTotal usando el valor de report.getInvoiceSum()
            // y otros campos relevantes de ConceptChargeds
            ConceptChargeds conceptCharged = conceptChargedsList.get(conceptChargedsList.size() - 1);
            existingReportFinal.setAmountTotal(existingReportFinal.getAmountTotal() + conceptCharged.getSum());
            existingReportFinal.setTotalInvoices(existingReportFinal.getTotalInvoices() + report.getInvoiceCount());
        }

        return new ArrayList<>(reportMap.values());
    }

    public List<SalesFilter> getByColaboratorAndConcept(int colaborator, int concept){
        return salesProvidersRepository.findByColaboratorAndConcept(colaborator, concept);
    }

    public List<SalesFilter> getByColaboratorAndConceptAndProvider(int colaborator, int concept, long provider){
        return salesProvidersRepository.findByColaboratorAndConceptAndProvider(colaborator, concept, provider);
    }

    public List<SalesFilter> getByColaboratorAndConceptByDateRange(int colaborator, int concept, LocalDateTime dateInit, LocalDateTime dateEnd){
        return salesProvidersRepository.findByColaboratorAndConceptByDateRange(colaborator, concept, dateInit, dateEnd);
    }

    public List<SalesFilter> getByColaboratorAndConceptAndProviderByDateRange(int colaborator, int concept, LocalDateTime dateInit, LocalDateTime dateEnd, long provider){
        return salesProvidersRepository.findByColaboratorAndConceptAndProviderByDateRange(colaborator, concept, dateInit, dateEnd, provider);
    }

    public List<SalesFilter> getByColaboratorAndConceptAndAmount(int colaborator, int concept, double amount){
        return salesProvidersRepository.findByColaboratorAndConceptAndAmount(colaborator, concept, amount);
    }

    public List<SalesFilter> getByColaboratorAndConceptAndAmountAndProvider(int colaborator, int concept, double amount, long provider){
        return salesProvidersRepository.findByColaboratorAndConceptAndAmountAndProvider(colaborator, concept, amount, provider);
    }

    public void save(SalesProviders salesProviders){
        salesProvidersRepository.save(salesProviders);
    }
}

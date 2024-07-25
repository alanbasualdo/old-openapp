package com.opencars.netgo.support.tickets.service;

import com.opencars.netgo.support.tickets.dto.*;
import com.opencars.netgo.support.tickets.entity.Tickets;
import com.opencars.netgo.support.tickets.entity.TicketsCategories;
import com.opencars.netgo.support.tickets.entity.TicketsStates;
import com.opencars.netgo.support.tickets.entity.TicketsSubcategories;
import com.opencars.netgo.support.tickets.repository.TicketsRepository;
import com.opencars.netgo.users.entity.User;
import com.opencars.netgo.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


@Service
@Transactional
public class TicketsService {

    @Autowired
    TicketsRepository ticketsRepository;

    @Autowired
    UserService userService;

    @Autowired
    TicketsCategoriesService ticketsCategoriesService;

    @Autowired
    TicketsSubCategoryService ticketsSubCategoryService;

    @Autowired
    TicketsStatesService ticketsStatesService;

    public static final int MINUTES_PER_HOUR = 60;
    public static final int SECONDS_PER_MINUTE = 60;
    public static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

    public static final int DAYS = SECONDS_PER_HOUR * 24;

    private int ticketsAssigned;
    private int ticketsOthers;
    private int totalTicketsForCategory;
    private int ticketsResolved;
    private int ticketsInProgress;
    private int ticketsOnHold;
    private int ticketsNotManaged;

    private String months[] = {"Enero","Febrero","Marzo","Abril","Mayo",
            "Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};

    private String manageTickets[] = {"No gestionados", "Gestionados", "Total"};

    public boolean existsById(Long id){
        return ticketsRepository.existsById(id);
    }

    public Optional<Tickets> getOne(Long id){
        return ticketsRepository.findById(id);
    }

    public Page<TicketsResume> getByCategoryAndSubcategory(TicketsCategories category, TicketsSubcategories subcategory, Pageable pageable){
        return ticketsRepository.findByCategoryAndSubcategory(category.getId(), subcategory.getId(), pageable);
    }

    public Page<TicketsResume> getByCategorySubcategoryAndState(TicketsCategories category, TicketsSubcategories subcategory, TicketsStates state, Pageable pageable){
        return ticketsRepository.findByCategoryAndSubcategoryAndState(category.getId(), subcategory.getId(), state.getId(), pageable);
    }

    public Page<TicketsResume> getByObserver(User observer, Pageable pageable){
        return ticketsRepository.findByObserver(observer, pageable);
    }

    public Page<TicketsResume> getTicketsSupportIT(TicketsCategories category, Pageable pageable){
        return ticketsRepository.findTicketsSupportIT(category.getId(), pageable);
    }

    public Page<TicketsResume> getTicketsSupportITByState(TicketsCategories category, TicketsStates state, Pageable pageable){
        return ticketsRepository.findTicketsSupportITAndState(category.getId(), state.getId(), pageable);
    }

    public Page<TicketsResume> getByApplicant(User applicant, Pageable pageable){
        return ticketsRepository.findByApplicant(applicant.getId(), pageable);
    }

    public int countTicketsByCategorySubcategoryAndState(TicketsCategories category, TicketsSubcategories subcategory, TicketsStates state){

        int count = ticketsRepository.countTicketsByCategorySubcategoryAndState(category, subcategory, state);

        return count;

    }

    public int countTicketsIT(TicketsCategories category, TicketsStates state){

        int count = ticketsRepository.countTicketsIT(category, state);

        return count;

    }

    public int countTicketsObserverByState(User observer, TicketsStates state){

        int count = ticketsRepository.countTicketsObserverByState(observer, state);

        return count;

    }

    public int countTicketsByCategoryAndTechnician(TicketsCategories category, User technician){

        int count = ticketsRepository.countTicketsByCategoryAndTechnician(category, technician);

        return count;

    }

    public int countTicketsApplicantByState(User applicant, TicketsStates state){

        int count = ticketsRepository.countTicketsApplicantByState(applicant, state);

        return count;

    }

    public int countTicketsByCategory(TicketsCategories category){

        int count = ticketsRepository.countTicketsByCategory(category);

        return count;

    }

    public int countTicketsByCategoryStateAndTechnician(TicketsCategories category, User technician, TicketsStates state){

        int count = ticketsRepository.countTicketsByCategoryStateAndTechnician(category, technician, state);

        return count;

    }

    public List<TicketsResume> getByCategoryAndCoincidenceInApplicantName(TicketsCategories category, User applicant) {
        return ticketsRepository.findByCategoryAndApplicantCoincidence(category.getId(), applicant.getId());
    }

    public List<TicketsResume> getByIdCoincidence(TicketsCategories category, Long id) {
        return ticketsRepository.findByIdCoincidence(category.getId(), id);
    }

    public List<TicketsResume> getByTechnician(User resolved) {
        return ticketsRepository.findByTechnician(resolved.getId());
    }

    public List<CardTechnician> getPerformanceTechniciansByCategory(TicketsCategories category) {

        List<User> listTechs = userService.getTechniciansByCategory(category.getRol().getRolName());
        List<CardTechnician> listCards = new ArrayList<>();
        List<TicketsResume> listTicketsClosedForTechnician;

        for (User tech : listTechs) {

            int[] arrayTicketsForState = new int[manageTickets.length];
            int[] arrayResolvedVsTotal = new int[manageTickets.length];
            String[] arrayLabelsMyManagement = new String[manageTickets.length];
            String[] arrayLabelsTotal = new String[manageTickets.length];
            Data[] arrayData = new Data[1];
            Data[] arrayTotalResolvedVsTotal = new Data[1];

            long totalTimeResolution = 0;
            long averageForTicket;

            String timeAverageResolution = "";

            TicketsStates stateClosed = ticketsStatesService.getOne(4).get();
            TicketsStates stateInProgress = ticketsStatesService.getOne(2).get();
            TicketsStates stateOnHold = ticketsStatesService.getOne(3).get();

            listTicketsClosedForTechnician = ticketsRepository.findTicketsByCategoryTechnicianAndState(tech.getId(), category.getId(), stateClosed.getId());

            for (TicketsResume tickets : listTicketsClosedForTechnician) {

                Duration duration = Duration.between(tickets.getOpeningDate(), tickets.getClosingDate());

                totalTimeResolution = totalTimeResolution + duration.getSeconds();

            }

            if(listTicketsClosedForTechnician.size() > 0) {
                averageForTicket = totalTimeResolution / listTicketsClosedForTechnician.size();

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

            ticketsAssigned = ticketsRepository.countTicketsByCategoryAndTechnician(category, tech);
            ticketsOthers= ticketsRepository.countTicketsByCategoryAndOthersTechnicians(category, tech);
            totalTicketsForCategory = ticketsRepository.countTicketsByCategory(category);
            ticketsResolved = ticketsRepository.countTicketsByCategoryStateAndTechnician(category, tech, stateClosed);
            ticketsInProgress = ticketsRepository.countTicketsByCategoryStateAndTechnician(category, tech, stateInProgress);
            ticketsOnHold = ticketsRepository.countTicketsByCategoryStateAndTechnician(category, tech, stateOnHold);
            ticketsNotManaged = ticketsRepository.countTicketsByCategoryNotManaged(category);

            arrayTicketsForState[0] = ticketsInProgress;
            arrayTicketsForState[1] = ticketsOnHold;
            arrayTicketsForState[2] = ticketsResolved;

            arrayResolvedVsTotal[0] = ticketsAssigned;
            arrayResolvedVsTotal[1] = ticketsOthers;
            arrayResolvedVsTotal[2] = ticketsNotManaged;

            DecimalFormat df = new DecimalFormat("#.##");

            if (ticketsAssigned > 0){
                float myManagement = (float)ticketsAssigned / (float)totalTicketsForCategory * 100;
                arrayLabelsMyManagement[0] = df.format(myManagement) + "%";
            }else{
                arrayLabelsMyManagement[0] = "0%";
            }

            if (ticketsOthers > 0){
                float totalOthers = (float)ticketsOthers / (float)totalTicketsForCategory * 100;
                arrayLabelsMyManagement[1] = df.format(totalOthers) + "%";
            }else{
                arrayLabelsMyManagement[1] = "0%";
            }

            if (ticketsNotManaged > 0){
                float totalNotManaged = (float)ticketsNotManaged / (float)totalTicketsForCategory * 100;
                arrayLabelsMyManagement[2] = df.format(totalNotManaged) + "%";
            }else{
                arrayLabelsMyManagement[2] = "0%";
            }

            if(ticketsInProgress > 0){
                float myProgress = (float)ticketsInProgress / (float)ticketsAssigned * 100;
                arrayLabelsTotal[0] = df.format(myProgress) + "%";
            }else {
                arrayLabelsTotal[0] = "0%";
            }

            if (ticketsOnHold > 0) {
                float myOnHold = (float)ticketsOnHold / (float)ticketsAssigned * 100;
                arrayLabelsTotal[1] = df.format(myOnHold) + "%";
            }else{
                arrayLabelsTotal[1] = "0%";
            }

            if (ticketsResolved > 0){
                float myResolved = (float)ticketsResolved / (float)ticketsAssigned * 100;
                arrayLabelsTotal[2] = df.format(myResolved) + "%";
            }else {
                arrayLabelsTotal[2] = "0%";
            }

            arrayData[0] = new Data(arrayTicketsForState);
            arrayTotalResolvedVsTotal[0] = new Data(arrayResolvedVsTotal);

            CardTechnician cardTechnician = new CardTechnician(
                    tech,
                    ticketsAssigned,
                    totalTicketsForCategory,
                    ticketsResolved,
                    ticketsInProgress,
                    ticketsOnHold,
                    timeAverageResolution,
                    arrayData,
                    arrayTotalResolvedVsTotal,
                    arrayLabelsMyManagement,
                    arrayLabelsTotal,
                    ticketsOthers,
                    ticketsNotManaged
            );

            listCards.add(cardTechnician);

        }

        return listCards;

    }

    public List<CardTechniciansResume> getAllTechnicians(){
        List<TicketsCategories> categories = ticketsCategoriesService.getAll();
        List<CardTechniciansResume> listTechs = new ArrayList<>();
        for (TicketsCategories category: categories){
            List<User> list = userService.getTechniciansByCategory(category.getRol().getRolName());
            for (User user: list){
                CardTechniciansResume tech = new CardTechniciansResume(user, category);
                if (!listTechs.contains(tech)){
                    listTechs.add(tech);
                }
            }
        }
        return listTechs;
    }

    public ReportGraphic getReportTotalTime(){

        DataReport[] arrayData = new DataReport[manageTickets.length];

        List<TicketsCategories> categories = ticketsCategoriesService.getAll();
        int[] arrayTicketsByCategory = new int[categories.size()];
        int[] arrayTicketsNotManaged = new int[categories.size()];
        int[] arrayTicketsManageds = new int[categories.size()];
        String[] arrayLabels = new String[categories.size()];

        for(int i = 0; i < categories.size(); i++){
            arrayTicketsByCategory[i] = ticketsRepository.countTicketsByCategory(categories.get(i));
            arrayTicketsNotManaged[i] = ticketsRepository.countTicketsByCategoryNotManaged(categories.get(i));
            arrayTicketsManageds[i] = ticketsRepository.countTicketsByCategoryManageds(categories.get(i));
            arrayLabels[i] = categories.get(i).getCategory();
        }

        arrayData[0] = new DataReport(arrayTicketsNotManaged, manageTickets[0]);
        arrayData[1] = new DataReport(arrayTicketsManageds, manageTickets[1]);
        arrayData[2] = new DataReport(arrayTicketsByCategory, manageTickets[2]);

        return new ReportGraphic(arrayData, arrayLabels);

    }

    public ReportGraphic getReportLastMonth(){

        DataReport[] arrayData = new DataReport[manageTickets.length];

        LocalDateTime date = LocalDateTime.now();

        List<TicketsCategories> categories = ticketsCategoriesService.getAll();
        int[] arrayTicketsByCategory = new int[categories.size()];
        int[] arrayTicketsNotManaged = new int[categories.size()];
        int[] arrayTicketsManageds = new int[categories.size()];
        String[] arrayLabels = new String[categories.size()];

        int month;
        int year;
        if (date.getMonthValue() == 1){
            month = 12;
            year = date.minusYears(1).getYear();
        }else{
            month = date.getMonthValue() - 1;
            year = date.getYear();
        }

        System.out.println("Mes " + month);
        System.out.println("Año " + year);

        for(int i = 0; i < categories.size(); i++) {
            arrayTicketsByCategory[i] = ticketsRepository.countTicketsLastMonth(categories.get(i), month, year);
            arrayTicketsNotManaged[i] = ticketsRepository.countTicketsLastMonthNotManageds(categories.get(i), month, year);
            arrayTicketsManageds[i] = ticketsRepository.countTicketsLastMonthManageds(categories.get(i), month, year);
            arrayLabels[i] = categories.get(i).getCategory();
        }

        arrayData[0] = new DataReport(arrayTicketsNotManaged, manageTickets[0]);
        arrayData[1] = new DataReport(arrayTicketsManageds, manageTickets[1]);
        arrayData[2] = new DataReport(arrayTicketsByCategory, manageTickets[2]);

        return new ReportGraphic(arrayData, arrayLabels);

    }

    public ReportGraphic getReportCurrentDay(){

        DataReport[] arrayData = new DataReport[manageTickets.length];

        LocalDateTime date = LocalDateTime.now();

        List<TicketsCategories> categories = ticketsCategoriesService.getAll();

        int[] arrayTicketsByCategory = new int[categories.size()];
        int[] arrayTicketsNotManaged = new int[categories.size()];
        int[] arrayTicketsManageds = new int[categories.size()];
        String[] arrayLabels = new String[categories.size()];

        for(int i = 0; i < categories.size(); i++) {
            arrayTicketsByCategory[i] = ticketsRepository.countTicketsCurrentDay(categories.get(i), date.getDayOfMonth(),date.getMonthValue(), date.getYear());
            arrayTicketsNotManaged[i] = ticketsRepository.countTicketsCurrentDayNotManaged(categories.get(i), date.getDayOfMonth(),date.getMonthValue(), date.getYear());
            arrayTicketsManageds[i] = ticketsRepository.countTicketsCurrentDayManageds(categories.get(i), date.getDayOfMonth(),date.getMonthValue(), date.getYear());
            arrayLabels[i] = categories.get(i).getCategory();
        }

        arrayData[0] = new DataReport(arrayTicketsNotManaged, manageTickets[0]);
        arrayData[1] = new DataReport(arrayTicketsManageds, manageTickets[1]);
        arrayData[2] = new DataReport(arrayTicketsByCategory, manageTickets[2]);

        return new ReportGraphic(arrayData, arrayLabels);

    }

    public ReportGraphic getReportByCategory(TicketsCategories category){

        DataReport[] arrayData = new DataReport[1];

        List<TicketsSubcategories> subcategories = this.ticketsSubCategoryService.getByCategory(category);

        int[] arrayTicketsBySubcategory = new int[subcategories.size()];
        String[] arrayLabels = new String[subcategories.size()];

        for (int i = 0; i < subcategories.size(); i++){
            arrayTicketsBySubcategory[i] = ticketsRepository.countTicketsByCategorAndSubcategory(category, subcategories.get(i));
            arrayLabels[i] = subcategories.get(i).getSubCategory();
        }

        arrayData[0] = new DataReport(arrayTicketsBySubcategory, "Total");

        return new ReportGraphic(arrayData, arrayLabels);

    }

    public LineReport getReportMonthlyByCategory(TicketsCategories category){

        int[] arrayTicketsForMonths2022 = new int[months.length];
        int[] arrayTicketsForMonths = new int[months.length];
        String[] arrayLabels = months;
        DataLineReport[] arrayData = new DataLineReport[2];

        LocalDateTime date = LocalDateTime.now();

        for (int i = 0; i < 12; i++){
            arrayTicketsForMonths2022[i] = ticketsRepository.countTicketsByCategorAndMonthsOfCurrentYear(category,  i + 1, date.minusYears(1).getYear());
        }

        for (int i = 0; i < 12; i++){
            arrayTicketsForMonths[i] = ticketsRepository.countTicketsByCategorAndMonthsOfCurrentYear(category,  i + 1, date.getYear());
        }

        arrayData[0] = new DataLineReport(arrayTicketsForMonths2022, "2022", true, 0.5, "black", "rgba(255,0,0,0.3)");
        arrayData[1] = new DataLineReport(arrayTicketsForMonths, "2023", true, 0.5, "black", "rgba(255,0,0,0.3)");

        return new LineReport(arrayData, arrayLabels);

    }

    public void deleteById(long id){
        ticketsRepository.deleteById(id);
    }

    public void save(Tickets tickets){
        ticketsRepository.save(tickets);
    }
}

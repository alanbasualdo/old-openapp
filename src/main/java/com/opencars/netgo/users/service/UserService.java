package com.opencars.netgo.users.service;

import com.opencars.netgo.auth.dto.ListsSessions;
import com.opencars.netgo.auth.entity.Rol;
import com.opencars.netgo.auth.entity.Sesiones;
import com.opencars.netgo.auth.repository.RolRepository;
import com.opencars.netgo.auth.repository.SesionesRepository;
import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.locations.entity.Sector;
import com.opencars.netgo.locations.entity.SubSector;
import com.opencars.netgo.locations.service.SectorService;
import com.opencars.netgo.locations.service.SubSectorService;
import com.opencars.netgo.support.mobiles.service.MobilesService;
import com.opencars.netgo.users.dto.*;
import com.opencars.netgo.users.entity.*;
import com.opencars.netgo.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RolRepository rolRepository;

    @Autowired
    SesionesRepository sesionesRepository;

    @Autowired
    SubSectorService subSectorService;

    @Autowired
    SectorService sectorService;

    @Autowired
    MobilesService mobilesService;

    private String positions;

    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean existsByCuil(String cuil){
        return userRepository.existsByCuil(cuil);
    }

    public Optional<User> getByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public List<User> getTechniciansByCategory(String rolName){
        Rol roleTech = rolRepository.findByRolName(rolName).get();
        return userRepository.findByRolesContains(roleTech);
    }

    public Optional<UserId> getUserIdByUsername(String username){
        return userRepository.findUserIdByUsername(username);
    }

    public Optional<User> getByCuil(String cuil){
        return userRepository.findByCuil(cuil);
    }

    public List<User> getByPosition(Position position){
        return userRepository.findUserByPosition(position);
    }

    public boolean existsById(int id){
        return userRepository.existsById(id);
    }

    public Optional<User> getOne(int id){
        return userRepository.findById(id);
    }

    public Optional<User> getGeneralManager(){
        return userRepository.findGeneralManager();
    }

    public List<User> getUsersByLineAndSector(int line, Sector sector){
        return userRepository.findUsersByLineAndSector(line, sector.getId());
    }

    public List<Integer> getUsersIdsByLineAndSector(int line, Sector sector){
        return userRepository.findUsersIdsByLineAndSector(line, sector.getId());
    }

    public List<User> getUsersByLineAndSectorToAuthorizations(int line, Sector sector){
        return userRepository.findUsersByLineAndSectorToAuthorizations(line, sector.getId());
    }

    public List<User> getUsersByLine(int line){
        return userRepository.findUsersByLine(line);
    }

    public List<User> getManagers(){
        List<Integer> listIds = userRepository.findManagers();
        List<User> listManagers = new ArrayList<>();
        for (Integer userId: listIds){
            User us = this.getOne(userId).get();
            listManagers.add(us);
        }
        return listManagers;
    }

    public List<User> getLines2(){
        List<Integer> listIds = userRepository.findLines2();
        List<User> listManagers = new ArrayList<>();
        for (Integer userId: listIds){
            User us = this.getOne(userId).get();
            listManagers.add(us);
        }
        return listManagers;
    }

    public List<User> getLines3(){
        List<Integer> listIds = userRepository.findLines3();
        List<User> listManagers = new ArrayList<>();
        for (Integer userId: listIds){
            User us = this.getOne(userId).get();
            listManagers.add(us);
        }
        return listManagers;
    }

    public List<User> getLines4(){
        List<Integer> listIds = userRepository.findLines4();
        List<User> listManagers = new ArrayList<>();
        for (Integer userId: listIds){
            User us = this.getOne(userId).get();
            listManagers.add(us);
        }
        return listManagers;
    }

    public List<Integer> getUsersByLineSubSectorAndBranch(int line, SubSector subSector, Branch branch){
        return userRepository.findUsersByLineSubSectorAndBranch(line, subSector.getId(), branch.getId());
    }

    public List<Integer> getUsersByLineAndSubsector(int line, SubSector subSector){
        return userRepository.findUsersByLineAndSubsector(line, subSector.getId());
    }

    public List<Integer> getUsersByLineAndSubsectorAndCompany(int line, SubSector subSector, int company){
        return userRepository.findUsersByLineAndSubsectorAndCompany(line, subSector.getId(), company);
    }

    public List<Integer> getUsersByLineAndSubsectorAndCity(int line, SubSector subSector, String city){
        return userRepository.findUsersByLineAndSubsectorAndCity(line, subSector.getId(), city);
    }

    public boolean existsByMail(String mail){
        return userRepository.existsByMail(mail);
    }

    public Optional<User> getByMail(String mail){
        return userRepository.findByMail(mail);
    }

    public List<Birthday> getBirthdays(){

        //Obtengo Mes y Dia actual
        String date = LocalDate.now().toString();

        String[] parts = date.split("-");
        String month = parts[1]; // MM
        String day = parts[2]; // dd
        int yearCurrent = LocalDate.now().getYear();

        String formatBirthday = day + "." + month;
        String dateBirthday = day + "/" + month + "/" + yearCurrent;

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fecha = LocalDate.parse(dateBirthday, formato);

        List<User> list = userRepository.findAllByBirthdaysCurrents(Integer.parseInt(day), Integer.parseInt(month));
        List<Birthday> listBirthdays = new ArrayList<>();

        list.stream().forEach(data -> {

            listBirthdays.add(
                    new Birthday(
                            data.getId(),
                            data.getName(),
                            data.getImgProfile(),
                            data.getBranch().getCity(),
                            data.getBranch().getBrandsCompany().getCompany().getName(),
                            formatBirthday,
                            data.getCuil(),
                            fecha
                    )
            );
        });

        return listBirthdays;

    }

    public List<Birthday> getBirthdaysForSummary(){

        LocalDate dateInit = LocalDate.now().minusDays(7);
        LocalDate dateEnd = LocalDate.now();
        int yearCurrent = dateEnd.getYear();

        List<User> list = userRepository.findAllByBirthdayMonthRange(dateInit.getMonthValue(), dateEnd.getMonthValue());
        List<Birthday> listBirthdays = new ArrayList<>();

        list.forEach(data -> {

            String date = data.getBirthDate().toString();
            String[] parts = date.split("-");
            String month = parts[1]; // MM
            String day = parts[2]; // dd

            String formatBirthday = day + "." + month;
            String dateBirthday = day + "/" + month + "/" + yearCurrent;

            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fecha = LocalDate.parse(dateBirthday, formato);

            if(fecha.isAfter(dateInit) && fecha.isBefore(dateEnd) || fecha.equals(dateEnd)){
                listBirthdays.add(
                        new Birthday(
                                data.getId(),
                                data.getName(),
                                data.getImgProfile(),
                                data.getBranch().getCity(),
                                data.getBranch().getBrandsCompany().getCompany().getName(),
                                formatBirthday,
                                data.getCuil(),
                                fecha
                        )
                );
            }
        });

        return listBirthdays;

    }

    public Page<User> getAllUsers(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public List<User> getUsersBySector(Sector sector){
        return userRepository.findBySector(sector);
    }

    public List<UsersData> getUsers(){
        return userRepository.findUsers();
    }

    public ResponseEntity<ListsSessions> getUsersConnections(){

        List<User> list = userRepository.usersEnables();
        List<UsersConnections> listConnectionsActives = new ArrayList<>();
        List<UsersConnections> listConnectionsInactives = new ArrayList<>();
        ListsSessions listsSessions = new ListsSessions();

        for (User user : list) {

            String sucursal = "";
            Date end = new Date();
            String active = "";
            int sesiones = 0;
            List<Sesiones> userSessions = sesionesRepository.findByColaborator(user);
            if (userSessions.size() > 0) {
                sesiones = 0;
                for (Sesiones sesion : userSessions) {
                    if (Objects.equals(sesion.getActive(), "Activo")) {
                        active = "Activo";
                        sesiones = sesiones + 1;
                    } else {
                        active = "Inactivo";
                    }
                    end = sesion.getEnd();
                }
            } else {
                active = "Inactivo";
                end = null;
            }

            sucursal = user.getBranch().getBrandsCompany().getCompany().getName() + " - " + user.getBranch().getCity() + " [" + user.getBranch().getBrandsCompany().getName() + "]";

            SortedSet<Position> userPositions = user.getPositions();
            this.positions = "";
            userPositions.stream().forEach(p -> {
                this.positions = this.positions + " | " + p.getPosition().getName();
            });

            if (active.equals("Activo")) {
                listConnectionsActives.add(new UsersConnections(end, active, user.getName(), user.getCuil(), sucursal, this.positions, sesiones, user.getImgProfile()));
            } else {
                listConnectionsInactives.add(new UsersConnections(end, active, user.getName(), user.getCuil(), sucursal, this.positions, sesiones, user.getImgProfile()));
            }

            listsSessions = new ListsSessions(listConnectionsActives, listConnectionsInactives);

        }

        return new ResponseEntity(listsSessions, HttpStatus.OK);
    }

    public List<UsersConnections> getUsersConnectionsByBranch(Branch branch){

        List<Sesiones> list = sesionesRepository.findActivesByBranch(branch);
        List<UsersConnections> listConnections = new ArrayList<>();

        for (Sesiones sesion : list) {

            String sucursal = "";
            Date end = new Date();
            String active = "";
            int sesiones = 0;
            List<Sesiones> userSessions = sesionesRepository.findByColaborator(sesion.getColaborator());
            if (userSessions.size() > 0) {
                sesiones = 0;
                for (Sesiones value : userSessions) {
                    if (Objects.equals(value.getActive(), "Activo")) {
                        active = "Activo";
                        sesiones = sesiones + 1;
                    } else {
                        active = "Inactivo";
                    }
                    end = value.getEnd();
                }
            } else {
                active = "Inactivo";
                end = null;
            }

            sucursal = sesion.getColaborator().getBranch().getBrandsCompany().getCompany().getName() + " - " + sesion.getColaborator().getBranch().getCity() + " [" + sesion.getColaborator().getBranch().getBrandsCompany().getName() + "]";

            SortedSet<Position> userPositions = sesion.getColaborator().getPositions();
            this.positions = "";
            userPositions.stream().forEach(p -> {
                this.positions = this.positions + " | " + p.getPosition().getName();
            });

            listConnections.add(new UsersConnections(end, active, sesion.getColaborator().getName(), sesion.getColaborator().getCuil(), sucursal, this.positions, sesiones, sesion.getColaborator().getImgProfile()));

        }

        return listConnections;
    }

    public ResponseEntity<Employee> getSecondsLines() {

        String urlImage = "https://api.opencars.com.ar/api/download/usuarios/";
        String cssClass = "ngx-org-ceo";
        User generalMan = this.getGeneralManager().get();
        //Para todos los sectores, agrego el gerente general
        String mobile = "";
        if(!this.mobilesService.getByUserId(generalMan.getId()).isEmpty()){
            mobile = this.mobilesService.getByUserId(generalMan.getId()).get(0).getLine().getLine();
        }
        Employee generalManager = new Employee(generalMan.getName(), cssClass, urlImage + generalMan.getCuil(), generalMan.getPositions().first().getPosition().getName(), generalMan.getMail(), generalMan.getBranch(), mobile);
        List<User> line2 = this.getUsersByLine(2);
        List<User> listaLimpia = new ArrayList<>();

        //Forma número 1 (Uso de Maps).
        Map<Integer, User> mapAnalyst = new HashMap<>(line2.size());

        //Aquí está la magia
        for(User p : line2) {
            mapAnalyst.put(p.getId(), p);
        }
        //Agrego cada elemento del map a una nueva lista y muestro cada elemento.
        for(Map.Entry<Integer, User> p : mapAnalyst.entrySet()) {
            listaLimpia.add(p.getValue());
        }

        //Ordenar listaLimpia por nombre de Employee
        Collections.sort(listaLimpia, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u1.getName().compareTo(u2.getName());
            }
        });

        for(User u : listaLimpia) {
            AtomicReference<String> positionLine2 = new AtomicReference<>("");
            u.getPositions().forEach(p -> {
                String positionName = p.getPosition().getName();
                if (!positionLine2.toString().contains(positionName)) {
                    positionLine2.set(positionLine2 + " | " + p.getPosition().getName());
                }
            });

            String mobile12 = "";
            if(!this.mobilesService.getByUserId(u.getId()).isEmpty()){
                mobile12 = this.mobilesService.getByUserId(u.getId()).get(0).getLine().getLine();
            }
            Employee userLine2 = new Employee(u.getName(), cssClass, urlImage + u.getCuil(), positionLine2.toString(), u.getMail(), u.getBranch(), mobile12);
            generalManager.addChilds(userLine2);
        }

        return ResponseEntity.ok().body(generalManager);

    }

    public List<User> getByName(String name){
        return userRepository.findUserByCoincidence(name);
    }

    public ResponseEntity<Employee> getOrganigramaBySector(Sector sector){

        String urlImage = "https://api.opencars.com.ar/api/download/usuarios/";
        String cssClass = "ngx-org-ceo";

        /*Este código construye un organigrama de un sector específico
        mediante la creación de objetos de tipo Employee y agregando subordinados a cada uno.
        El gerente general es agregado al primer nivel del organigrama.
        Luego, se buscan los usuarios en línea 2 y se les agrega como subordinados del gerente general.
        Para cada usuario de línea 2, se buscan las sucursales a cargo y los usuarios a cargo en línea 3.
        Estos usuarios en línea 3 son agregados como subordinados de los usuarios de línea 2.
        Finalmente, se buscan los usuarios en línea 4
        y se les agrega como subordinados de los usuarios en línea 3.
        Los métodos getUsersByLineAndSector,
        getPeopleInChargeForComercialManager,
        getPeopleInChargeForComercialChief,
        y getPeopleInChargeForSubsectorChief
        se utilizan para recuperar los usuarios en las diferentes líneas y roles. */
        Sector sectorComplete = sectorService.getOne(sector.getId()).get();
        User generalMan = this.getGeneralManager().get();
        //Para todos los sectores, agrego el gerente general
        String mobile = "";
        if(!this.mobilesService.getByUserId(generalMan.getId()).isEmpty()){
            mobile = this.mobilesService.getByUserId(generalMan.getId()).get(0).getLine().getLine();
        }
        Employee generalManager = new Employee(generalMan.getName(), cssClass, urlImage + generalMan.getCuil(), generalMan.getPositions().first().getPosition().getName(), generalMan.getMail(), generalMan.getBranch(), mobile);

        //Para comercial
        List<User> line2 = this.getUsersByLineAndSector(2, sector);
        if (sectorComplete.getName().equals("Comercial")){
            for (User user : line2) {

                List<Branch> branchsUser = new ArrayList<>();
                List<SubSector> subSectorsLine2 = new ArrayList<>();
                List<User> peopleInCharge = new ArrayList<>();
                AtomicReference<String> positionLine2 = new AtomicReference<>("");
                user.getPositions().forEach(p -> {
                    branchsUser.addAll(p.getBranchs());
                    if (p.getSubSector().getSector().getId() == sector.getId()){
                        subSectorsLine2.add(p.getSubSector());
                        String positionName = p.getPosition().getName();
                        if (!positionLine2.toString().contains(positionName)) {
                            positionLine2.set(positionLine2 + " | " + p.getPosition().getName());
                        }
                    }
                });

                String mobile1 = "";
                if(!this.mobilesService.getByUserId(user.getId()).isEmpty()){
                    mobile1 = this.mobilesService.getByUserId(user.getId()).get(0).getLine().getLine();
                }
                Employee userLine2 = new Employee(user.getName(), cssClass, urlImage + user.getCuil(), positionLine2.toString(), user.getMail(), user.getBranch(), mobile1);
                generalManager.addChilds(userLine2);

                for (SubSector subSector: subSectorsLine2) {
                    if (subSector.getSector() == sectorComplete) {
                        if (subSector.getName().equals("Comercial")) {

                            List<User> people = this.getPeopleInChargeForComercialManager(sector);
                            for (User peopleIn : people){
                                if (!peopleInCharge.contains(peopleIn)){
                                    peopleInCharge.add(peopleIn);
                                }
                            }

                            for (User inCharge: peopleInCharge){

                                List<Branch> branchsLines2 = new ArrayList<>();
                                List<User> peopleInChargeLine3 = new ArrayList<>();
                                List<SubSector> subSectorsLine3 = new ArrayList<>();
                                AtomicReference<String> positionLine3 = new AtomicReference<>("");
                                inCharge.getPositions().forEach(p -> {
                                    branchsLines2.addAll(p.getBranchs());
                                    subSectorsLine3.add(p.getSubSector());
                                    if (p.getSubSector().getSector().getId() == sector.getId()){
                                        String positionName = p.getPosition().getName();
                                        if (!positionLine3.toString().contains(positionName)) {
                                            positionLine3.set(positionLine3 + " | " + p.getPosition().getName());
                                        }
                                    }
                                });

                                String mobile2 = "";
                                if(!this.mobilesService.getByUserId(inCharge.getId()).isEmpty()){
                                    mobile2 = this.mobilesService.getByUserId(inCharge.getId()).get(0).getLine().getLine();
                                }
                                Employee userLine3 = new Employee(inCharge.getName(), cssClass, urlImage + inCharge.getCuil(), positionLine3.toString(), inCharge.getMail(), inCharge.getBranch(), mobile2);

                                for (SubSector subSectorLine3: subSectorsLine3){
                                    if (subSectorLine3.getSector() == sectorComplete){
                                        if (subSectorLine3.getName().equals("Comercial")) {
                                            for (Branch branch: branchsLines2){
                                                List<User> peopleLine4 = this.getPeopleInChargeForComercialChief(subSectorLine3, branch);
                                                for (User peopleIn : peopleLine4){
                                                    if (!peopleInChargeLine3.contains(peopleIn)){
                                                        peopleInChargeLine3.add(peopleIn);
                                                    }
                                                }
                                            }
                                        }else{
                                            List<User> peopleLine4 = this.getPeopleInChargeForSubsectorChief(subSectorLine3);
                                            for (User peopleIn : peopleLine4){
                                                if (!peopleInChargeLine3.contains(peopleIn)){
                                                    peopleInChargeLine3.add(peopleIn);
                                                }
                                            }
                                        }
                                    }
                                }

                                for (User inChargeLine3: peopleInChargeLine3){
                                    AtomicReference<String> positionLine4 = new AtomicReference<>("");

                                    List<LineSubsector> positionsLine4 = new ArrayList<>();
                                    List<Branch> branchsLine4 = new ArrayList<>();

                                    inChargeLine3.getPositions().forEach(p -> {
                                        if (p.getSubSector().getSector().getId() == sector.getId() && p.getLinea() == 4){
                                            String positionName = p.getPosition().getName();
                                            if (!positionLine4.toString().contains(positionName)) {
                                                positionLine4.set(positionLine4 + " | " + p.getPosition().getName());
                                            }
                                        }
                                        if (p.getLinea() == 4 && p.getSubSector().getSector().getId() == sector.getId()){
                                            branchsLine4.addAll(p.getBranchs());
                                            positionsLine4.add(new LineSubsector(p.getLinea(), p.getSubSector()));
                                        }
                                    });

                                    String mobile3 = "";
                                    if(!this.mobilesService.getByUserId(inChargeLine3.getId()).isEmpty()){
                                        mobile3 = this.mobilesService.getByUserId(inChargeLine3.getId()).get(0).getLine().getLine();
                                    }
                                    Employee userLine4 = new Employee(inChargeLine3.getName(), cssClass, urlImage + inChargeLine3.getCuil(), positionLine4.toString(), inChargeLine3.getMail(), inChargeLine3.getBranch(), mobile3);
                                    userLine3.addChilds(userLine4);

                                    //recorro los subsectores
                                    for (LineSubsector lineSubSector: positionsLine4) {
                                        //si es igual al que se está consultando
                                        if ((lineSubSector.getSubSector().getSector() == sectorComplete)) {
                                            List<User> peopleInChargeLine4 = new ArrayList<>();
                                            List<User> peopleLine5 = new ArrayList<>();
                                            for (Branch branch : branchsLine4) {
                                                peopleLine5.addAll(this.getPeopleInChargeForComercialSupervisor(branch));
                                            }

                                            for (User peopleIn : peopleLine5){
                                                if (!peopleInChargeLine4.contains(peopleIn)){
                                                    peopleInChargeLine4.add(peopleIn);
                                                }
                                            }

                                            for (User inChargeLine4: peopleInChargeLine4) {
                                                AtomicReference<String> positionLine5 = new AtomicReference<>("");
                                                inChargeLine4.getPositions().forEach(p -> {
                                                    if (p.getSubSector().getSector().getId() == sector.getId()){
                                                        String positionName = p.getPosition().getName();
                                                        if (!positionLine5.toString().contains(positionName)) {
                                                            positionLine5.set(positionLine5 + " | " + p.getPosition().getName());
                                                        }
                                                    }
                                                });

                                                String mobile4 = "";
                                                if(!this.mobilesService.getByUserId(inChargeLine4.getId()).isEmpty()){
                                                    mobile4 = this.mobilesService.getByUserId(inChargeLine4.getId()).get(0).getLine().getLine();
                                                }
                                                Employee userLine5 = new Employee(inChargeLine4.getName(), cssClass, urlImage + inChargeLine4.getCuil(), positionLine5.toString(), inChargeLine4.getMail(), inChargeLine4.getBranch(), mobile4);
                                                userLine4.addChilds(userLine5);
                                            }
                                        }
                                    }
                                }

                                userLine2.addChilds(userLine3);
                            }

                        }
                    }
                }

            }
        }else if(sectorComplete.getName().equals("Capital Humano")){
            for (User user : line2) {
                List<Branch> branchsUser = new ArrayList<>();
                List<User> peopleInCharge = new ArrayList<>();
                AtomicReference<String> positionLine2 = new AtomicReference<>("");
                user.getPositions().forEach(p -> {
                    branchsUser.addAll(p.getBranchs());
                    if (p.getSubSector().getSector().getId() == sector.getId()) {
                        String positionName = p.getPosition().getName();
                        if (!positionLine2.toString().contains(positionName)) {
                            positionLine2.set(positionLine2 + " | " + p.getPosition().getName());
                        }
                    }
                });

                String mobile5 = "";
                if(!this.mobilesService.getByUserId(user.getId()).isEmpty()){
                    mobile5 = this.mobilesService.getByUserId(user.getId()).get(0).getLine().getLine();
                }
                Employee userLine2 = new Employee(user.getName(), cssClass, urlImage + user.getCuil(), positionLine2.toString(), user.getMail(), user.getBranch(), mobile5);
                generalManager.addChilds(userLine2);

                //Obtengo todos los jefes de sucursales segun las sucursales que tiene asignadas el gerente de cchh
                for (Branch branch : branchsUser) {
                    List<User> people = this.getChiefsForCCHHManager(branch);
                    for (User peopleIn : people) {
                        if (!peopleInCharge.contains(peopleIn)) {
                            peopleInCharge.add(peopleIn);
                        }
                    }
                }

                //por cada jefe de sucursal
                for (User inCharge: peopleInCharge) {

                    List<Branch> branchsLines2 = new ArrayList<>();
                    List<User> peopleInChargeLine3 = new ArrayList<>();
                    List<SubSector> subSectors = new ArrayList<>();
                    AtomicReference<String> positionLine3 = new AtomicReference<>("");
                    inCharge.getPositions().forEach(p -> {
                        branchsLines2.addAll(p.getBranchs());
                        subSectors.add(p.getSubSector());
                        if (p.getSubSector().getSector().getName().equals("Comercial")) {
                            String positionName = p.getPosition().getName();
                            if (!positionLine3.toString().contains(positionName)) {
                                positionLine3.set(positionLine3 + " | " + p.getPosition().getName());
                            }
                        }
                    });

                    String mobile6 = "";
                    if(!this.mobilesService.getByUserId(inCharge.getId()).isEmpty()){
                        mobile6 = this.mobilesService.getByUserId(inCharge.getId()).get(0).getLine().getLine();
                    }
                    Employee userLine3 = new Employee(inCharge.getName(), cssClass, urlImage + inCharge.getCuil(), positionLine3.toString(), inCharge.getMail(), inCharge.getBranch(), mobile6);

                    for (SubSector subSector : subSectors) {
                        if (subSector.getSector().getName().equals("Comercial")) {
                            for (Branch branch: branchsLines2){
                                List<User> peopleLine4 = this.getPeopleInChargeForComercialChief(subSectorService.getByName("Maestranza").get(), branch);
                                for (User peopleIn : peopleLine4){
                                    if (!peopleInChargeLine3.contains(peopleIn)){
                                        peopleInChargeLine3.add(peopleIn);
                                    }
                                }
                            }
                        }
                    }

                    for (User inChargeLine3: peopleInChargeLine3){
                        AtomicReference<String> positionLine4 = new AtomicReference<>("");
                        inChargeLine3.getPositions().forEach(p -> {
                            if (p.getSubSector().getSector().getId() == sector.getId()){
                                String positionName = p.getPosition().getName();
                                if (!positionLine4.toString().contains(positionName)) {
                                    positionLine4.set(positionLine4 + " | " + p.getPosition().getName());
                                }
                            }
                        });

                        String mobile7 = "";
                        if(!this.mobilesService.getByUserId(inChargeLine3.getId()).isEmpty()){
                            mobile7 = this.mobilesService.getByUserId(inChargeLine3.getId()).get(0).getLine().getLine();
                        }
                        Employee userLine4 = new Employee(inChargeLine3.getName(), cssClass, urlImage + inChargeLine3.getCuil(), positionLine4.toString(), inChargeLine3.getMail(), inChargeLine3.getBranch(), mobile7);
                        userLine3.addChilds(userLine4);
                    }

                    userLine2.addChilds(userLine3);
                }
            }
        }else{
            for (User user : line2) {
                List<User> peopleInCharge = new ArrayList<>();
                AtomicReference<String> positionLine2 = new AtomicReference<>("");
                List<SubSector> subSectorsLine2 = new ArrayList<>();
                user.getPositions().forEach(p -> {
                    if (p.getSubSector().getSector().getId() == sector.getId()){
                        subSectorsLine2.add(p.getSubSector());
                        String positionName = p.getPosition().getName();
                        if (!positionLine2.toString().contains(positionName)) {
                            positionLine2.set(positionLine2 + " | " + p.getPosition().getName());
                        }
                    }
                });

                String mobile8 = "";
                if(!this.mobilesService.getByUserId(user.getId()).isEmpty()){
                    mobile8 = this.mobilesService.getByUserId(user.getId()).get(0).getLine().getLine();
                }
                Employee userLine2 = new Employee(user.getName(), cssClass, urlImage + user.getCuil(), positionLine2.toString(), user.getMail(), user.getBranch(), mobile8);
                generalManager.addChilds(userLine2);

                for (SubSector subSector: subSectorsLine2) {
                    if (subSector.getSector() == sectorComplete) {
                        if (!subSector.getName().equals("Transversal")) {

                            List<User> people = this.getPeopleInChargeForSectorManager(sector);
                            for (User peopleIn : people) {
                                if (!peopleInCharge.contains(peopleIn)) {
                                    peopleInCharge.add(peopleIn);
                                }
                            }

                            for (User inCharge: peopleInCharge){

                                List<User> peopleInChargeLine3 = new ArrayList<>();
                                List<SubSector> subSectorsLine3 = new ArrayList<>();
                                List<Branch> branchs = new ArrayList<>();
                                AtomicReference<String> positionLine3 = new AtomicReference<>("");
                                inCharge.getPositions().forEach(p -> {
                                    subSectorsLine3.add(p.getSubSector());
                                    branchs.addAll(p.getBranchs());
                                    if (p.getSubSector().getSector().getId() == sector.getId() && p.getLinea() == 3){
                                        String positionName = p.getPosition().getName();
                                        if (!positionLine3.toString().contains(positionName)) {
                                            positionLine3.set(positionLine3 + " | " + p.getPosition().getName());
                                        }
                                    }
                                });

                                String mobile9 = "";
                                if(!this.mobilesService.getByUserId(inCharge.getId()).isEmpty()){
                                    mobile9 = this.mobilesService.getByUserId(inCharge.getId()).get(0).getLine().getLine();
                                }
                                Employee userLine3 = new Employee(inCharge.getName(), cssClass, urlImage + inCharge.getCuil(), positionLine3.toString(), inCharge.getMail(), inCharge.getBranch(), mobile9);

                                for (SubSector subSectorLine3: subSectorsLine3){
                                    if (subSectorLine3.getSector() == sectorComplete){
                                        List<User> peopleLine4 = new ArrayList<>();

                                        if(subSectorLine3.getName().equals("Postventa")) {
                                            for (Branch branch : branchs) {
                                                peopleLine4.addAll(this.getPeopleInChargeForPostventaChief(subSectorLine3, branch.getBrandsCompany().getCompany().getId()));
                                                peopleLine4.addAll(this.getPeopleInChargeForPostventaChief(subSectorService.getByName("Garantías").get(), branch.getBrandsCompany().getCompany().getId()));
                                            }
                                        }else if (subSectorLine3.getName().equals("Comercial-PDA")){
                                                for (Branch branch: branchs){
                                                    peopleLine4.addAll(this.getPeopleInChargeForComercialChief(subSector, branch));
                                                }
                                        }else if (subSectorLine3.getSector().getName().equals("Contabilidad")){
                                            peopleLine4.addAll(this.getPeopleInChargeForSubsectorChief(subSectorLine3));
                                        }else if (subSectorLine3.getSector().getName().equals("Marketing")) {
                                            peopleLine4.addAll(this.getLine4ForSector(subSectorLine3.getSector()));
                                        }else if(subSectorLine3.getName().equals("Tesorería")){
                                            peopleLine4.addAll(this.getPeopleInChargeForSubsectorChief(subSectorService.getByName("Análisis de Tesorería").get()));
                                            peopleLine4.addAll(this.getPeopleInChargeForSubsectorChief(subSectorLine3));
                                        }else{
                                            peopleLine4.addAll(this.getPeopleInChargeForSubsectorChief(subSectorLine3));
                                        }

                                        for (User peopleIn : peopleLine4){
                                            if (!peopleInChargeLine3.contains(peopleIn)){
                                                peopleInChargeLine3.add(peopleIn);
                                            }
                                        }
                                    }
                                }

                                for (User inChargeLine3: peopleInChargeLine3){
                                    AtomicReference<String> positionLine4 = new AtomicReference<>("");
                                    //Por cada colaborador de linea 4, obtengo subsectores y sucursales
                                    List<LineSubsector> positionSubSectorsLine4 = new ArrayList<>();
                                    List<Branch> branchsLine4 = new ArrayList<>();

                                    inChargeLine3.getPositions().forEach(p -> {
                                        if (p.getSubSector().getSector().getId() == sector.getId() && p.getLinea() == 4){
                                            String positionName = p.getPosition().getName();
                                            if (!positionLine4.toString().contains(positionName)) {
                                                positionLine4.set(positionLine4 + " | " + p.getPosition().getName());
                                            }
                                        }
                                        if (p.getLinea() == 4 && p.getSubSector().getSector().getId() == sector.getId()){
                                            branchsLine4.addAll(p.getBranchs());
                                            positionSubSectorsLine4.add(new LineSubsector(p.getLinea(), p.getSubSector()));
                                        }
                                    });
                                    String mobile10 = "";
                                    if(!this.mobilesService.getByUserId(inChargeLine3.getId()).isEmpty()){
                                        mobile10 = this.mobilesService.getByUserId(inChargeLine3.getId()).get(0).getLine().getLine();
                                    }
                                    Employee userLine4 = new Employee(inChargeLine3.getName(), cssClass, urlImage + inChargeLine3.getCuil(), positionLine4.toString(), inChargeLine3.getMail(), inChargeLine3.getBranch(), mobile10);
                                    userLine3.addChilds(userLine4);

                                    boolean postventaProcessed = false;

                                    //recorro los subsectores
                                    for (LineSubsector lineSubSector: positionSubSectorsLine4) {
                                        //si es igual al que se está consultando
                                        if ((lineSubSector.getSubSector().getSector() == sectorComplete)) {
                                            List<User> peopleInChargeLine4 = new ArrayList<>();
                                            List<User> peopleLine5 = new ArrayList<>();
                                            if (lineSubSector.getSubSector().getName().equals("Postventa") && !postventaProcessed) {
                                                for (Branch branch : branchsLine4) {
                                                    peopleLine5.addAll(this.getPeopleInChargeForGarajeChief(lineSubSector.getSubSector(), branch));
                                                }
                                                postventaProcessed = true;
                                            }else if (!lineSubSector.getSubSector().getName().equals("Postventa") && !lineSubSector.getSubSector().getName().equals("Comercial-PDA")){
                                                peopleLine5.addAll(this.getPeopleInChargeForSubsectorSupervisor(lineSubSector.getSubSector()));
                                            }else if(lineSubSector.getSubSector().getName().equals("Comercial-PDA")){
                                                for (Branch branch : branchsLine4) {
                                                    peopleLine5.addAll(this.getPeopleInChargeForComercialPDASupervisor(branch));
                                                    //peopleLine5.addAll(this.getPeopleInChargeForPDAChief(lineSubSector.getSubSector(), branch));
                                                }
                                            }

                                            for (User peopleIn : peopleLine5){
                                                if (!peopleInChargeLine4.contains(peopleIn)){
                                                    peopleInChargeLine4.add(peopleIn);
                                                }
                                            }

                                            for (User inChargeLine4: peopleInChargeLine4) {
                                                AtomicReference<String> positionLine5 = new AtomicReference<>("");
                                                //List<LineSubsector> positionSubSectorsLine5 = new ArrayList<>();
                                                //List<Branch> branchsLine5 = new ArrayList<>();
                                                inChargeLine4.getPositions().forEach(p -> {
                                                    if (p.getSubSector().getSector().getId() == sector.getId()){
                                                        String positionName = p.getPosition().getName();
                                                        if (!positionLine5.toString().contains(positionName)) {
                                                            positionLine5.set(positionLine5 + " | " + p.getPosition().getName());
                                                        }
                                                    }
                                                    /*if (p.getLinea() == 5 && p.getSubSector().getSector().getId() == sector.getId()){
                                                        branchsLine5.addAll(p.getBranchs());
                                                        positionSubSectorsLine5.add(new LineSubsector(p.getLinea(), p.getSubSector()));
                                                    }*/
                                                });

                                                String mobile11 = "";
                                                if(!this.mobilesService.getByUserId(inChargeLine4.getId()).isEmpty()){
                                                    mobile11 = this.mobilesService.getByUserId(inChargeLine4.getId()).get(0).getLine().getLine();
                                                }
                                                Employee userLine5 = new Employee(inChargeLine4.getName(), cssClass, urlImage + inChargeLine4.getCuil(), positionLine5.toString(), inChargeLine4.getMail(), inChargeLine4.getBranch(), mobile11);
                                                userLine4.addChilds(userLine5);


                                                /*
                                                //recorro los subsectores
                                                for (LineSubsector lineSubSector5: positionSubSectorsLine5) {
                                                    //si es igual al que se está consultando
                                                    if ((lineSubSector5.getSubSector().getSector() == sectorComplete)) {
                                                        List<User> peopleInChargeLine5 = new ArrayList<>();
                                                        List<User> peopleLine6 = new ArrayList<>();
                                                        if(lineSubSector5.getSubSector().getName().equals("Comercial-PDA")){
                                                            for (Branch branch : branchsLine5) {
                                                                peopleLine6.addAll(this.getPeopleInChargeForComercialPDASupervisor(lineSubSector5.getSubSector(), branch));
                                                            }
                                                        }

                                                        for (User peopleIn : peopleLine6){
                                                            if (!peopleInChargeLine5.contains(peopleIn)){
                                                                peopleInChargeLine5.add(peopleIn);
                                                            }
                                                        }

                                                        for (User inChargeLine5: peopleInChargeLine5) {
                                                            AtomicReference<String> positionLine6 = new AtomicReference<>("");
                                                            inChargeLine5.getPositions().forEach(p -> {
                                                                if (p.getSubSector().getSector().getId() == sector.getId()){
                                                                    String positionName = p.getPosition().getName();
                                                                    if (!positionLine6.toString().contains(positionName)) {
                                                                        positionLine6.set(positionLine6 + " | " + p.getPosition().getName());
                                                                    }
                                                                }
                                                            });

                                                            String mobile12 = "";
                                                            if(!this.mobilesService.getByUserId(inChargeLine5.getId()).isEmpty()){
                                                                mobile12 = this.mobilesService.getByUserId(inChargeLine5.getId()).get(0).getLine().getLine();
                                                            }
                                                            Employee userLine6 = new Employee(inChargeLine5.getName(), cssClass, urlImage + inChargeLine5.getCuil(), positionLine6.toString(), inChargeLine5.getMail(), inChargeLine5.getBranch(), mobile12);
                                                            userLine5.addChilds(userLine6);
                                                        }
                                                    }
                                                }*/
                                            }
                                        }
                                    }
                                }
                                userLine2.addChilds(userLine3);
                            }
                        }
                    }
                }

            }
        }

        return ResponseEntity.ok().body(generalManager);

    }

    private User getUserForOrganizationChart(User user){
        User userToReturn = new User(
                user.getBranch(),
                user.getCuil(),
                user.getMail(),
                user.getName(),
                user.getPositions(),
                user.getImgProfile()
        );

        return userToReturn;
    }

    public List<User> getPeopleInChargeForComercialManager(Sector sector){
        List<User> line3Final = this.getUsersByLineAndSector(3, sector);
        return line3Final;
    }

    public List<User> getChiefsForCCHHManager(Branch branch){
        List<User> line3Final = new ArrayList<>();
        List<Integer> line3ComercialToCCHH = this.getUsersByLineSubSectorAndBranch(3, subSectorService.getByName("Comercial").get(), branch);
        for (Integer idUser: line3ComercialToCCHH){
            User us = this.userRepository.getById(idUser);
            line3Final.add(us);
        }
        return line3Final;
    }

    public List<User> getPeopleInChargeForComercialChief(SubSector subSector, Branch branch){
        List<User> line4Final = new ArrayList<>();
        List<Integer> line4Comercial = this.getUsersByLineSubSectorAndBranch(4, subSector, branch);
        for (Integer idUser: line4Comercial){
            User us = this.userRepository.getById(idUser);
            line4Final.add(us);
        }
        return line4Final;
    }

    public List<User> getPeopleInChargeForPDAManager(Sector sector){
        List<User> line4Final = new ArrayList<>();
        List<Integer> line4PDA = this.getUsersIdsByLineAndSector(4, sector);
        for (Integer idUser: line4PDA){
            User us = this.userRepository.getById(idUser);
            line4Final.add(us);
        }
        return line4Final;
    }

    public List<User> getPeopleInChargeForPDAChief(SubSector subSector, Branch branch){
        List<User> line5Final = new ArrayList<>();
        List<Integer> line5PDA = this.getUsersByLineSubSectorAndBranch(5, subSector, branch);
        for (Integer idUser: line5PDA){
            User us = this.userRepository.getById(idUser);
            line5Final.add(us);
        }
        return line5Final;
    }

    public List<User> getPeopleInChargeForGarajeChief(SubSector subSector, Branch branch){
        List<User> line5Final = new ArrayList<>();
        List<Integer> line5Garaje = this.getUsersByLineSubSectorAndBranch(5, subSector, branch);
        for (Integer idUser: line5Garaje){
            User us = this.userRepository.getById(idUser);
            line5Final.add(us);
        }
        return line5Final;
    }

    public List<User> getPeopleInChargeForComercialSupervisor(Branch branch){
        List<User> line5Final = new ArrayList<>();
        List<Integer> line5Comercial = this.getUsersByLineSubSectorAndBranch(5, subSectorService.getByName("Comercial").get(), branch);
        for (Integer idUser: line5Comercial){
            User us = this.userRepository.getById(idUser);
            line5Final.add(us);
        }
        return line5Final;
    }

    public List<User> getPeopleInChargeForComercialPDASupervisor(Branch branch){
        List<User> line5Final = new ArrayList<>();
        List<Integer> line5Comercial = this.getUsersByLineSubSectorAndBranch(5, subSectorService.getByName("Comercial-PDA").get(), branch);
        //List<Integer> line5Comercial = this.getUsersByLineSubSectorAndBranch(6, subSector, branch);
        for (Integer idUser: line5Comercial){
            User us = this.userRepository.getById(idUser);
            line5Final.add(us);
        }
        return line5Final;
    }


    public List<User> getPeopleInChargeForSectorManager(Sector sector){
        List<User> line3 = this.getUsersByLineAndSector(3, sector);
        return line3;
    }

    public List<User> getLine4ForSector(Sector sector){
        List<User> line4 = this.getUsersByLineAndSector(4, sector);
        return line4;
    }

    public List<User> getPeopleInChargeForSubsectorChief(SubSector subSector){
        List<User> line4Final = new ArrayList<>();
        List<Integer> line4Subsector = this.getUsersByLineAndSubsector(4, subSector);
        for (Integer idUser: line4Subsector){
            User us = this.userRepository.getById(idUser);
            line4Final.add(us);
        }
        return line4Final;
    }

    public List<User> getPeopleInChargeForSubsectorSupervisor(SubSector subSector){
        List<User> line5Final = new ArrayList<>();
        List<Integer> line5Subsector = this.getUsersByLineAndSubsector(5, subSector);
        for (Integer idUser: line5Subsector){
            User us = this.userRepository.getById(idUser);
            line5Final.add(us);
        }
        return line5Final;
    }

    public List<User> getPeopleInChargeForPostventaChief(SubSector subSector, int company){
        List<User> line4Final = new ArrayList<>();
        List<Integer> line4Postventa = this.getUsersByLineAndSubsectorAndCompany(4, subSector, company);
        for (Integer idUser: line4Postventa){
            User us = this.userRepository.getById(idUser);
            line4Final.add(us);
        }
        return line4Final;
    }

    public List<User> getUsersEnabledsByName(String name){
        return userRepository.findUsersEnabledsByCoincidence(name);
    }

    public List<UserNames> getUsersNamesEnabledsByNames(String name){
        return userRepository.findUsersNamesEnabledsByCoincidence(name);
    }

    public List<User> getUsersByPositionAndNameCoincidence(String name, Position position){
        return userRepository.findUserByPositionAndCoincidenceInName(name, position);
    }

    public List<User> getByRole(Rol role){
        return userRepository.findByRolesContains(role);
    }

    public void save(User user){
        userRepository.save(user);
    }

}

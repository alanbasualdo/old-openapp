package com.opencars.netgo.auth.service;

import com.opencars.netgo.auth.dto.ActivesBranch;
import com.opencars.netgo.auth.dto.ListsSessions;
import com.opencars.netgo.auth.entity.LogDailyUse;
import com.opencars.netgo.auth.entity.Sesiones;
import com.opencars.netgo.auth.repository.LogDailyUseRepository;
import com.opencars.netgo.auth.repository.SesionesRepository;
import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.locations.repository.BranchRepository;
import com.opencars.netgo.users.dto.UsersConnections;
import com.opencars.netgo.users.entity.Position;
import com.opencars.netgo.users.entity.User;
import com.opencars.netgo.users.repository.UserRepository;
import com.opencars.netgo.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class SesionesService {

    @Autowired
    SesionesRepository sesionesRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    LogDailyUseRepository logDailyUseRepository;

    private String positions;

    public void deleteById(int id){
        sesionesRepository.deleteById(id);
    }

    public int countActiveUsers(){

        int cant = sesionesRepository.countActives();

        return cant;
    }

    public int countInactiveUsers(){

        int countTotalUsers = userRepository.countUsersEnableds();
        int cantActives = sesionesRepository.countActives();
        int countInactives = countTotalUsers - cantActives;

        return countInactives;

    }

    public ActivesBranch countUsersOnlineForDayAndBranch(Branch branch){

        Branch branchCurrent = branchRepository.findById(branch.getId()).get();

        String branchReceipt = branchCurrent.getBrandsCompany().getCompany().getName() + " - " + branchCurrent.getCity() + " [" + branchCurrent.getBrandsCompany().getName() + "]";

        LocalDate today = LocalDate.now();
        int countActives = 0;
        if (sesionesRepository.countActivesForBranchInCurrentDay(branchCurrent, today) != null){
            countActives = sesionesRepository.countActivesForBranchInCurrentDay(branchCurrent, today);
        }
        int countTotal = 0;
        if (userRepository.countUsersEnablesByBranch(branch) != null){
            countTotal = userRepository.countUsersEnablesByBranch(branch);
        }
        ActivesBranch activesBranch = new ActivesBranch(countActives, countTotal, branchReceipt);

        return activesBranch;
    }

    public LogDailyUse countOnlinesForLog(Branch branch){

        LocalDate today = LocalDate.now();

        int countActives = sesionesRepository.countActivesForBranchInCurrentDay(branch, today);
        int countTotal = userRepository.countUsersEnablesByBranch(branch);

        LogDailyUse logDailyUse = new LogDailyUse(countActives, countTotal, branch, today);

        return logDailyUse;
    }

    public Optional<Sesiones> getOne(int id){
        return sesionesRepository.findById(id);
    }
    public Optional<Sesiones> getByToken(String token){
        return sesionesRepository.findByToken(token);
    }

    public List<Sesiones> getActives(){
        return sesionesRepository.findActives();
    }

    public ResponseEntity<ListsSessions> getAllSessionsOrderByDate(){
        return userService.getUsersConnections();
    }

    public List<UsersConnections> getSessionsByBranch(Branch branch){
        return userService.getUsersConnectionsByBranch(branch);
    }

    public List<Sesiones> getByColaborator(User colaborator){
        return sesionesRepository.findByColaborator(colaborator);
    }

    public List<Sesiones> getByColaboratorAndActive(User colaborator, String state){
        return sesionesRepository.findByColaboratorAndActive(colaborator, state);
    }

    public List<UsersConnections> getSessionByCoincidenceInNameAndActive(String name) {

        List<Sesiones> list = sesionesRepository.findByUsernameLike(name);

        List<UsersConnections> listConnections = new ArrayList<>();

        for (Sesiones sesion: list) {

            String sucursal = "";
            Date end = new Date();
            String active = "";
            int countActivesByColaborator = 0;

            List<Sesiones> listUserSessions = sesionesRepository.findByColaborator(sesion.getColaborator());
            countActivesByColaborator = sesionesRepository.countActivesByColaborator(sesion.getColaborator());
            if (listUserSessions.size() > 0) {
                for (Sesiones connections: listUserSessions) {
                    if (Objects.equals(connections.getActive(), "Activo")) {
                        active = "Activo";
                    } else {
                        active = "Inactivo";
                    }
                    end = connections.getEnd();
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

            listConnections.add(new UsersConnections(end, active, sesion.getColaborator().getName(), sesion.getColaborator().getCuil(), sucursal, this.positions, countActivesByColaborator, sesion.getColaborator().getImgProfile()));

        }

        return listConnections;
    }

    public List<Sesiones> getAll(){
        return sesionesRepository.findAll();
    }

    public void changeDropSessionsToInactive(){

        List<Sesiones> sesiones = sesionesRepository.findActives();

        Date date = new Date();

        //Recorro las sesiones activas
        for (Sesiones sesion : sesiones) {

            //Fecha de caída de la sesión
            Date dateEnd = sesion.getEnd();

            //Obtengo todas las sesiones del usuario
            List<Sesiones> userSessions = sesionesRepository.findByColaborator(sesion.getColaborator());

            //Verifico si el tiempo del token expiró, es decir si es una sesión que dejaron abierta y se cayó
            if (date.after(dateEnd)) {

                Sesiones sessionToDelete;

                //Si el usuario tiene más de una sesión, la elimino, si no actualizo la sesión a inactiva
                if (userSessions.size() > 1) {
                    this.deleteById(sesion.getId());

                } else {
                    Sesiones sesionUpdated = this.getOne(sesion.getId()).get();

                    sesionUpdated.setEnd(date);
                    sesionUpdated.setActive("Inactivo");
                    this.save(sesionUpdated);
                }
            }
        }
    }

    public void save(Sesiones sesion){
        sesionesRepository.save(sesion);
    }

}

package com.opencars.netgo.benefitcars.service;

import com.opencars.netgo.benefitcars.entity.BenefitCars;
import com.opencars.netgo.benefitcars.repository.BenefitCarsRepository;
import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.locations.entity.Sector;
import com.opencars.netgo.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BenefitCarsService {

    @Autowired
    BenefitCarsRepository benefitCarsRepository;

   /* @Autowired
    AuthorizersBranchsRepository authorizersBranchsRepository;

    @Autowired
    AuthorizersSectorsRepository authorizersSectorsRepository;*/

    public boolean existsById(long id){
        return benefitCarsRepository.existsById(id);
    }

    public Optional<BenefitCars> getOne(long id){
        return benefitCarsRepository.findById(id);
    }

    public List<BenefitCars> getAllForVOManager(){
        return benefitCarsRepository.findAllForVOManager();
    }

    public List<BenefitCars> getAllForGeneralManager(){
        return benefitCarsRepository.findAllForGeneralManager();
    }

    public List<BenefitCars> getAllForCCHHManager(){
        return benefitCarsRepository.findAllForCCHHManager();
    }

    public List<BenefitCars> getAllForPresident(){
        return benefitCarsRepository.findAllForPresident();
    }

    public List<BenefitCars> getAllForRRLL(){
        return benefitCarsRepository.findAllForRRLL();
    }

    public List<BenefitCars> getByColaborator(User colaborator){
        return benefitCarsRepository.findByColaboratorOrderByIdDesc(colaborator);
    }

    public List<BenefitCars> getByBranchManager(User colaborator){

        List<BenefitCars> listBenefits = new ArrayList<>();

       /* List<Branch> listBranchs = authorizersBranchsRepository.findBranchAuthorizedByBranchManager(colaborator);

        for (Branch branch: listBranchs){
            listBenefits.addAll(benefitCarsRepository.findByBranchManager(branch));
        }*/

        return listBenefits;
    }

    public List<BenefitCars> getByAreaManager(User colaborator){

        List<BenefitCars> listBenefits = new ArrayList<>();

       /* List<Sector> listSectors = authorizersSectorsRepository.findSectorAuthorizedByAreaManager(colaborator);

        System.out.println("Tamaño de lista " + listSectors.size());

        for (Sector sector: listSectors){
            listBenefits.addAll(benefitCarsRepository.findBySectorManager(sector));
        }*/

        return listBenefits;
    }

    public boolean userGotBenefitInLastYear(User colaborator){

        LocalDate date = LocalDate.now();

        boolean gotBenefit;

        Optional<BenefitCars> benefit =  this.benefitCarsRepository.findBenefitCardGrantedForColaboratorOnLastYear(colaborator, date);

        gotBenefit = benefit.isPresent();

        return gotBenefit;
    }

    public boolean userRequestBenefitInLastYear(User colaborator){

        LocalDate date = LocalDate.now();

        boolean requestBenefit;

        Optional<BenefitCars> benefit =  this.benefitCarsRepository.findBenefitCardRequestForColaboratorOnLastYear(colaborator, date);

        requestBenefit = benefit.isPresent();

        return requestBenefit;
    }

    public void save(BenefitCars benefitCars){
        benefitCarsRepository.save(benefitCars);
    }

}

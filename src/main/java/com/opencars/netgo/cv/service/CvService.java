package com.opencars.netgo.cv.service;

import com.opencars.netgo.cv.dto.CVsDTO;
import com.opencars.netgo.cv.dto.CountCVs;
import com.opencars.netgo.cv.entity.*;
import com.opencars.netgo.cv.repository.*;
import com.opencars.netgo.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CvService {

    @Autowired
    CvRepository cvRepository;

    @Autowired
    EducationRepository educationRepository;

    @Autowired
    ExperienceRepository experienceRepository;

    @Autowired
    CertificationsRepository certificationsRepository;

    @Autowired
    HobbiesRepository hobbiesRepository;

    @Autowired
    UserRepository userRepository;

    public boolean existsById(int id){
        return cvRepository.existsById(id);
    }

    public boolean existsByColaborator(int colaborator){
        return cvRepository.existsByColaborator(colaborator);
    }

    public Optional<Cv> getOne(int id){
        return cvRepository.findById(id);
    }

    public Optional<Cv> getByColaborator(int colaborator){
        return cvRepository.findByColaborator(colaborator);
    }

    public List<Cv> getAll(){
        return cvRepository.findAll();
    }

    public void save(Cv cv){
        cvRepository.save(cv);
    }

    public List<CVsDTO> getByCoincidence(String coincidence){

        List<Cv> listCVs = this.cvRepository.findByCoincidence(coincidence);
        List<Education> listEd = this.educationRepository.findByCoincidence(coincidence);
        List<Certifications> listCert = this.certificationsRepository.findByCoincidence(coincidence);
        List<Experience> listExp = this.experienceRepository.findByCoincidence(coincidence);
        List<Hobbies> listHobbies = this.hobbiesRepository.findByCoincidence(coincidence);
        List<CVsDTO> listCvsFinal = new ArrayList<>();

        for (Cv listCV : listCVs) {

            CVsDTO cv = new CVsDTO();
            cv.setColaborator(listCV.getColaborator());

            for (Education ed: listEd){
                for (Education educationColaborator: listCV.getEducation()){
                    if (ed.getId() == educationColaborator.getId()){
                        cv.setCheckTitle(true);
                    }
                }
            }

            for (Certifications cert: listCert){
                for (Certifications certsColaborator: listCV.getCertifications()){
                    if (cert.getId() == certsColaborator.getId()){
                        cv.setCheckCertification(true);
                    }
                }
            }

            for (Experience exp: listExp){
                for (Experience experienceColaborator: listCV.getExperience()){
                    if (exp.getId() == experienceColaborator.getId()){
                        cv.setCheckExperience(true);
                    }
                }
            }

            for (Hobbies hob: listHobbies){
                for (Hobbies hobbiesColaborator: listCV.getHobbies()){
                    if (hob.getId() == hobbiesColaborator.getId()){
                        cv.setCheckHobbies(true);
                    }
                }
            }

            listCvsFinal.add(cv);

        }

        return listCvsFinal;
    }

    public CountCVs countCVs() {

        int countTotalEnabled = userRepository.countUsersEnableds();
        int countCvsCreated = cvRepository.countCVsCreatedsWithUsersEnableds();
        int countNotCreated = countTotalEnabled - countCvsCreated;

        CountCVs countCVs = new CountCVs(
                "CREADOS",
                "PENDIENTES",
                countCvsCreated,
                countNotCreated
        );

        return countCVs;
    }
}

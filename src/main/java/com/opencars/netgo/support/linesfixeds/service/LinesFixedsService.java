package com.opencars.netgo.support.linesfixeds.service;

import com.opencars.netgo.support.linesfixeds.entity.LinesFixeds;
import com.opencars.netgo.support.linesfixeds.repository.LinesFixedsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LinesFixedsService {

    @Autowired
    LinesFixedsRepository linesFixedsRepository;
    public boolean existsById(int id){
        return linesFixedsRepository.existsById(id);
    }
    public Optional<LinesFixeds> getOne(int id){
        return linesFixedsRepository.findById(id);
    }
    public Page<LinesFixeds> getAll(Pageable pageable){
        return linesFixedsRepository.findAll(pageable);
    }
    public void save(LinesFixeds linesFixeds){
        linesFixedsRepository.save(linesFixeds);
    }
    public List<LinesFixeds> getByCoincidence(String linesFixeds){
        return linesFixedsRepository.findByCoincidence(linesFixeds);
    }
    public List<LinesFixeds> getByCoincidenceInUse(String use){
        return linesFixedsRepository.findByCoincidenceInUse(use);
    }
    public List<LinesFixeds> getByCoincidenceInUseAndType(String use, String type){
        return linesFixedsRepository.findByCoincidenceInUseAndType(use, type);
    }
    public List<LinesFixeds> getByCoincidenceInType(String type){
        return linesFixedsRepository.findByType(type);
    }
    public List<LinesFixeds> getByBranch(int id){
        return linesFixedsRepository.findByBranch(id);
    }
    public List<LinesFixeds> getByBranchAndType(int id, String type){
        return linesFixedsRepository.findByBranchAndType(id, type);
    }
}

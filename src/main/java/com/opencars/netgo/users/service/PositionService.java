package com.opencars.netgo.users.service;

import com.opencars.netgo.locations.entity.SubSector;
import com.opencars.netgo.users.entity.Position;
import com.opencars.netgo.users.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PositionService {

    @Autowired
    PositionRepository positionRepository;

    public boolean existsById(int id){
        return positionRepository.existsById(id);
    }

    public boolean existsByPosition(String position){
        return positionRepository.existsByPosition(position);
    }

    public Optional<Position> getOne(int id){
        return positionRepository.findById(id);
    }

    public List<Position> getBySubsector(SubSector subSector){
        return positionRepository.findBySubSector(subSector);
    }

    public List<Integer> getLines(){
        return positionRepository.findLines();
    }

    public List<Position> getAll(){
        return positionRepository.findAllOrderByChargeName();
    }

    public List<Position> getByName(String name){
        return positionRepository.findByCoincidenceInName(name);
    }

    public Page<Position> getAllPosition(Pageable pageable){
        return positionRepository.findAll(pageable);
    }

    public void deleteById(int id){
        positionRepository.deleteById(id);
    }

    public void save(Position position){
        positionRepository.save(position);
    }

}

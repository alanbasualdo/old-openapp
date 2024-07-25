package com.opencars.netgo.news.searchs.service;

import com.opencars.netgo.news.searchs.dto.ListPositions;
import com.opencars.netgo.news.searchs.entity.Searchs;
import com.opencars.netgo.news.searchs.repository.SearchsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class SearchsService {

    @Autowired
    SearchsRepository searchsRepository;

    public List<Searchs> getById(int id){
        return searchsRepository.findById(id);
    }

    public Optional<Searchs> getOne(int id){
        return searchsRepository.getById(id);
    }

    public void save(Searchs search){
        searchsRepository.save(search);
    }

    public List<Searchs> getActives(){

        List<Searchs> list = searchsRepository.findByStates("Activa");

        return list;

    }

    public List<Searchs> getInactives(){

        List<Searchs> list = searchsRepository.findByStates("Inactiva");

        return list;

    }

    public int countByState(String state){

        int count = searchsRepository.countSearchsByState(state);

        return count;

    }

    public List<ListPositions> getPositionsActives(){

        List<ListPositions> list = searchsRepository.findAllActives();

        return list;

    }

    public void deleteById(int id){
        searchsRepository.deleteById(id);
    }

}

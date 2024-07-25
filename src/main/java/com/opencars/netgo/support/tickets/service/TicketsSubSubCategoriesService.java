package com.opencars.netgo.support.tickets.service;

import com.opencars.netgo.support.tickets.entity.TicketsSubSubCategories;
import com.opencars.netgo.support.tickets.entity.TicketsSubcategories;
import com.opencars.netgo.support.tickets.repository.TicketsSubSubCategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TicketsSubSubCategoriesService {

    @Autowired
    TicketsSubSubCategoriesRepository ticketsSubSubCategoriesRepository;

    public boolean existsById(int id){
        return ticketsSubSubCategoriesRepository.existsById(id);
    }

    public Optional<TicketsSubSubCategories> getOne(int id){
        return ticketsSubSubCategoriesRepository.findById(id);
    }

    public void save(TicketsSubSubCategories subcategory){
        ticketsSubSubCategoriesRepository.save(subcategory);
    }

    public List<TicketsSubSubCategories> getByName(String name){
        return ticketsSubSubCategoriesRepository.findByCoincidenceInName(name);
    }

    public List<TicketsSubSubCategories> getBySubcategory(TicketsSubcategories subcategory){
        return ticketsSubSubCategoriesRepository.findBySubcategory(subcategory);
    }

    public List<TicketsSubSubCategories> getAll(){ return ticketsSubSubCategoriesRepository.findAll(); }

}

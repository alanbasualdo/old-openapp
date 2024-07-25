package com.opencars.netgo.support.tickets.service;

import com.opencars.netgo.support.tickets.entity.TicketsCategories;
import com.opencars.netgo.support.tickets.entity.TicketsSubcategories;
import com.opencars.netgo.support.tickets.repository.TicketsSubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TicketsSubCategoryService {

    @Autowired
    TicketsSubCategoryRepository ticketsSubCategoryRepository;

    public boolean existsById(int id){
        return ticketsSubCategoryRepository.existsById(id);
    }

    public Optional<TicketsSubcategories> getOne(int id){
        return ticketsSubCategoryRepository.findById(id);
    }

    public void save(TicketsSubcategories subcategory){
        ticketsSubCategoryRepository.save(subcategory);
    }

    public List<TicketsSubcategories> getByName(String name){
        return ticketsSubCategoryRepository.findByCoincidenceInName(name);
    }

    public List<TicketsSubcategories> getByCategory(TicketsCategories category){
        return ticketsSubCategoryRepository.findByCategory(category);
    }

    public List<TicketsSubcategories> getAll(){ return ticketsSubCategoryRepository.findAll(); }
}

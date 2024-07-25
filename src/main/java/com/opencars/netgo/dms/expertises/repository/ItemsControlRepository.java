package com.opencars.netgo.dms.expertises.repository;

import com.opencars.netgo.dms.expertises.entity.ItemsControl;
import com.opencars.netgo.dms.quoter.entity.TypeOfCars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsControlRepository extends JpaRepository<ItemsControl, Integer> {

    @Query("SELECT r.price FROM ItemsControl r where r.item = :item and r.typeOfCars = :typeOfCar")
    Long findPriceByItemAndTypeOfCars(String item, TypeOfCars typeOfCar);
}

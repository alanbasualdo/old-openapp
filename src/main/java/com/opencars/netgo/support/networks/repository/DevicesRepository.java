package com.opencars.netgo.support.networks.repository;

import com.opencars.netgo.support.networks.entity.Devices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DevicesRepository extends JpaRepository<Devices, Integer> {

    @Query("SELECT r FROM Devices r where r.name like %:device%")
    List<Devices> findByCoincidence(String device);
}

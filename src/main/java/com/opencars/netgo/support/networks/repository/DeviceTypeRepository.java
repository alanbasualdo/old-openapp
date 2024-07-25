package com.opencars.netgo.support.networks.repository;

import com.opencars.netgo.support.networks.entity.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceTypeRepository extends JpaRepository<DeviceType, Integer> {

    @Query("SELECT r FROM DeviceType r where r.type like %:type%")
    List<DeviceType> findByCoincidence (String type);

}

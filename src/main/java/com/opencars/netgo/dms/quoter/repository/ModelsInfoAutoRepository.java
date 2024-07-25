package com.opencars.netgo.dms.quoter.repository;

import com.opencars.netgo.dms.quoter.entity.ModelsInfoAuto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelsInfoAutoRepository extends JpaRepository<ModelsInfoAuto, Long> {

    @Query("SELECT r FROM ModelsInfoAuto r where r.description like %:model%")
    List<ModelsInfoAuto> findByCoincidence(String model);

    boolean existsByCodia(int codia);
}

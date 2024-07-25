package com.opencars.netgo.dms.providers.repository;

import com.opencars.netgo.dms.providers.entity.ProvidersSales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvidersSalesRepository extends JpaRepository<ProvidersSales, Long> {

    boolean existsByTelefono(String telefono);
    boolean existsByCorreo(String correo);
    boolean existsByCuit(Long cuit);
    @Query("SELECT r FROM ProvidersSales r where r.nombre like %:name%")
    List<ProvidersSales> findProviderByCoincidence(String name);
    @Query("SELECT r FROM ProvidersSales r WHERE CAST(r.cuit AS string) LIKE CONCAT('%', :cuit, '%')")
    List<ProvidersSales> findProviderByCoincidenceInCuit(long cuit);
    Page<ProvidersSales> findAllByOrderByIdDesc(Pageable pageable);
}

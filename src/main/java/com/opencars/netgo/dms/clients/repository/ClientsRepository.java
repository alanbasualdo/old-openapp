package com.opencars.netgo.dms.clients.repository;

import com.opencars.netgo.dms.clients.entity.Clients;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientsRepository extends JpaRepository<Clients, Long> {

    boolean existsByTelefono(String telefono);
    boolean existsByCorreo(String correo);
    boolean existsByDocumento(Long documento);
    @Query("SELECT r FROM Clients r where r.nombre like %:name%")
    List<Clients> findClientByCoincidence(String name);
    Page<Clients> findAllByOrderByIdDesc(Pageable pageable);
}

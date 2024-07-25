package com.opencars.netgo.auth.repository;

import com.opencars.netgo.auth.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

    @Query("SELECT r FROM Rol r where r.rolName like %:name% and r.type = :type")
    List<Rol> findRolByCoincidenceInNameAndType(String name, String type);

    @Query("SELECT r FROM Rol r where r.rolName like %:name%")
    List<Rol> findRolByCoincidenceInName(String name);

    @Query("SELECT r FROM Rol r where r.type = :type")
    List<Rol> findAllByType(String type);

    @Query("SELECT r FROM Rol r where r.rolName = :rolName")
    Optional<Rol> findByRolName(String rolName);

}

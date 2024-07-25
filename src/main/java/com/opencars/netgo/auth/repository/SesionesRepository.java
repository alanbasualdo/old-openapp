package com.opencars.netgo.auth.repository;

import com.opencars.netgo.auth.entity.Sesiones;
import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SesionesRepository extends JpaRepository<Sesiones, Integer> {

    List<Sesiones> findByColaborator(User colaborator);
    List<Sesiones> findByColaboratorAndActive(User colaborator, String state);
    Optional<Sesiones> findByToken(String token);
    @Query("SELECT r FROM Sesiones r WHERE r.active = 'Activo' ORDER BY r.init DESC")
    List<Sesiones> findActives();
    @Query("SELECT r FROM Sesiones r WHERE r.active = 'Activo' AND r.colaborator = :colaborator")
    List<Sesiones> findActivesByColaborator(User colaborator);
    @Query("SELECT r FROM Sesiones r where r.colaborator.branch = :branch AND r.active = 'Activo' AND r.colaborator.enable = 1 GROUP BY r.colaborator.id ORDER BY r.today DESC")
    List<Sesiones> findActivesByBranch(Branch branch);
    @Query("SELECT COUNT(DISTINCT r.colaborator.id) FROM Sesiones r WHERE r.active = 'Activo'")
    Integer countActives();
    @Query("SELECT COUNT(r) FROM Sesiones r WHERE r.active = 'Activo' AND r.colaborator = :colaborator")
    Integer countActivesByColaborator(User colaborator);
    @Query("SELECT COUNT(DISTINCT r.colaborator.id) FROM Sesiones r WHERE r.active = 'Activo' AND r.colaborator.branch = :branch AND r.today = :today")
    Integer countActivesForBranchInCurrentDay(Branch branch, LocalDate today);
    @Query("SELECT r FROM Sesiones r WHERE r.colaborator.name like %:name% and r.colaborator.enable = 1 GROUP BY r.colaborator")
    List<Sesiones> findByUsernameLike(String name);

}

package com.opencars.netgo.users.repository;

import com.opencars.netgo.locations.entity.SubSector;
import com.opencars.netgo.users.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

    List<Position> findBySubSector(SubSector subSector);

    boolean existsByPosition(String position);
    @Query("SELECT r FROM Position r order by r.position.name ASC")
    List<Position> findAllOrderByChargeName();

    @Query("SELECT r.linea FROM Position r where r.linea != 1 group by r.linea order by r.id desc")
    List<Integer> findLines();

    @Query("SELECT r FROM Position r INNER JOIN Charge c ON r.position = c.id where c.name like %:name%")
    List<Position> findByCoincidenceInName(String name);

}

package com.opencars.netgo.support.servers.repository;

import com.opencars.netgo.support.servers.entity.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerRepository extends JpaRepository<Server, Integer> {

    @Query("SELECT r FROM Server r where r.name like %:name%")
    List<Server> findByCoincidenceInName(String name);
}

package com.opencars.netgo.support.videosecurity.repository;

import com.opencars.netgo.support.videosecurity.entity.Videosecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideosecurityRepository extends JpaRepository<Videosecurity, Integer> {

    @Query("SELECT r FROM Videosecurity r where r.branch.id = :branch")
    List<Videosecurity> findByBranch(int branch);
}

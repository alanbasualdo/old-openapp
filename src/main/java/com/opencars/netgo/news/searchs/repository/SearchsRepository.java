package com.opencars.netgo.news.searchs.repository;

import com.opencars.netgo.news.searchs.dto.ListPositions;
import com.opencars.netgo.news.searchs.entity.Searchs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SearchsRepository extends JpaRepository<Searchs, Integer> {

    List<Searchs> findById(int id);
    @Query("SELECT new com.opencars.netgo.news.searchs.dto.ListPositions(r.id, r.position) FROM Searchs r where r.state = 'Activa' ORDER BY r.date DESC")
    List<ListPositions> findAllActives();
    @Query("SELECT r FROM Searchs r where r.id = :id")
    Optional<Searchs> getById(int id);
    @Query("SELECT r FROM Searchs r where r.state = :state ORDER BY r.date DESC")
    List<Searchs> findByStates(String state);

    @Query("SELECT COUNT(r) FROM Searchs r where r.state = :state")
    int countSearchsByState(String state);
}

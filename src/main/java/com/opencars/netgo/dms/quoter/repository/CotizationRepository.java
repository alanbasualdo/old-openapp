package com.opencars.netgo.dms.quoter.repository;

import com.opencars.netgo.dms.quoter.entity.Cotization;
import com.opencars.netgo.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CotizationRepository extends JpaRepository<Cotization, Long> {

    @Query("SELECT r FROM Cotization r where r.client like %:nameClient%")
    List<Cotization> findByNameClient (String nameClient);
    @Query("SELECT DISTINCT r.patent FROM Cotization r where r.patent like %:patent%")
    List<String> findDistinctPatents (String patent);
    @Query("SELECT r FROM Cotization r where r.model like %:model%")
    List<Cotization> findByUnit(String model);
    @Query("SELECT r FROM Cotization r where r.quotedBy = :quotedBy")
    List<Cotization> findByQuotedBy(User quotedBy);
    String query = "SELECT * FROM cotization where patent = :domain order by date_to_order DESC LIMIT 1";
    @Query(value = query, nativeQuery = true)
    Optional<Cotization> findLastCotizationForDomain(String domain);
}

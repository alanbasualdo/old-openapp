package com.opencars.netgo.sales.proveedores.repository;

import com.opencars.netgo.sales.proveedores.entity.Accounts;
import com.opencars.netgo.sales.proveedores.entity.SubcateorizedConcepts;
import com.opencars.netgo.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Integer> {

    @Query("SELECT r FROM Accounts r where r.concept = :concept")
    Optional<Accounts> findAuthorizerForConcept(SubcateorizedConcepts concept);

    boolean existsByOwner(User owner);

}

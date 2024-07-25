package com.opencars.netgo.sales.proveedores.repository;

import com.opencars.netgo.sales.proveedores.entity.AuthorizersAmounts;
import com.opencars.netgo.sales.proveedores.entity.SubcateorizedConcepts;
import com.opencars.netgo.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorizersAmountsRepository extends JpaRepository<AuthorizersAmounts, Integer> {

    @Query("SELECT r FROM AuthorizersAmounts r where :amount BETWEEN r.initAmount AND r.endAmount and r.concept = :concept")
    Optional<AuthorizersAmounts> findAuthorizerForSubConceptAndAmount(double amount, SubcateorizedConcepts concept);

    boolean existsByAuthorizer(User authorizer);
}

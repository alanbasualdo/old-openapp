package com.opencars.netgo.sales.proveedores.repository;

import com.opencars.netgo.sales.proveedores.entity.TableAuthorizer;
import com.opencars.netgo.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TableAuthorizerRepository extends JpaRepository<TableAuthorizer, Integer> {

    @Query("SELECT r FROM TableAuthorizer r where :amount BETWEEN r.initAmount AND r.endAmount")
    Optional<TableAuthorizer> findAuthorizerForAmount(double amount);

    @Query("SELECT MIN(r.initAmount) FROM TableAuthorizer r")
    Double findAmountMin();

    boolean existsByAuthorizer(User authorizer);

}

package com.opencars.netgo.sales.viaticos.repository;

import com.opencars.netgo.sales.viaticos.entity.AuthorizerToSuperior2;
import com.opencars.netgo.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorizerToSuperior2Repository extends JpaRepository<AuthorizerToSuperior2, Integer> {

    boolean existsByColaborator(User authorizer);

    @Query("SELECT r.colaborator FROM AuthorizerToSuperior2 r")
    List<User> findColaborators();
}

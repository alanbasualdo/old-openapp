package com.opencars.netgo.sales.viaticos.repository;

import com.opencars.netgo.sales.viaticos.entity.AuthorizerToSuperior;
import com.opencars.netgo.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorizerToSuperiorRepository extends JpaRepository<AuthorizerToSuperior, Integer> {

    boolean existsByColaborator(User authorizer);

    @Query("SELECT r.colaborator FROM AuthorizerToSuperior r")
    List<User> findColaborators();
}

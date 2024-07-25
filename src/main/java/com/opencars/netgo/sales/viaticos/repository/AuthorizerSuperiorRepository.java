package com.opencars.netgo.sales.viaticos.repository;

import com.opencars.netgo.sales.viaticos.entity.AuthorizerSuperior;
import com.opencars.netgo.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizerSuperiorRepository extends JpaRepository<AuthorizerSuperior, Integer> {

    boolean existsByColaborator(User colaborator);

}

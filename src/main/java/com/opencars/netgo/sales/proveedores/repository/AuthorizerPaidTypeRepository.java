package com.opencars.netgo.sales.proveedores.repository;

import com.opencars.netgo.sales.proveedores.entity.AuthorizerPaidType;
import com.opencars.netgo.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizerPaidTypeRepository extends JpaRepository<AuthorizerPaidType, Integer> {

    boolean existsByColaborator(User authorizer);
}

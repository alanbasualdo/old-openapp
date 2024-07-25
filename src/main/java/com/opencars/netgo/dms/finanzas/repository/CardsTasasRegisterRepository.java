package com.opencars.netgo.dms.finanzas.repository;

import com.opencars.netgo.dms.finanzas.entity.CardsTasasRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardsTasasRegisterRepository extends JpaRepository<CardsTasasRegister, Integer> {
}

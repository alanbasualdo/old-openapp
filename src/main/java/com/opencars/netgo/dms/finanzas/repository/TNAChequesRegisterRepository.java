package com.opencars.netgo.dms.finanzas.repository;

import com.opencars.netgo.dms.finanzas.entity.TNAChequesRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TNAChequesRegisterRepository extends JpaRepository<TNAChequesRegister, Integer> {
}

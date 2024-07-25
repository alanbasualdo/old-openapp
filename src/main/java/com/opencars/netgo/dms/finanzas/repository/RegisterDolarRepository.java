package com.opencars.netgo.dms.finanzas.repository;

import com.opencars.netgo.dms.finanzas.entity.DolarRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterDolarRepository extends JpaRepository<DolarRegister, Long> {

}

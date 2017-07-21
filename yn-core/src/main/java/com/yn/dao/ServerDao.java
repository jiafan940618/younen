package com.yn.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.Server;

public interface ServerDao extends JpaRepository<Server, Long>, JpaSpecificationExecutor<Server> {
    @Modifying
    @Query("update Server set del=1,delDtm=(now()) where id = :id")
    void delete(@Param("id") Long id);
    
    @Transactional
    @Modifying
    @Query("update Server set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);
    
    @Query("SELECT COUNT(*) FROM Server u WHERE u.createDtm>=?1 AND u.createDtm<?2 AND u.del=0")
    long countNum(Date startDtm, Date endDtm);
}

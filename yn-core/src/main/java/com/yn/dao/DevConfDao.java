package com.yn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.DevConf;

public interface DevConfDao extends JpaRepository<DevConf, Long>, JpaSpecificationExecutor<DevConf> {
    @Modifying
    @Query("update DevConf set del=1,delDtm=(now()) where rowId = :rowId")
    void delete(@Param("rowId") Long rowId);
    
    @Transactional
    @Modifying
    @Query("update DevConf set del=1,delDtm=(now()) where rowId in (:rowIds)")
	void deleteBatch(@Param("rowIds") List<Long> rowIds);
}

package com.yn.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.Station;

public interface StationDao extends JpaRepository<Station, Long>, JpaSpecificationExecutor<Station> {
    @Modifying
    @Query("update Station set del=1,delDtm=(now()) where id = :id")
    void delete(@Param("id") Long id);
    
    @Transactional
    @Modifying
    @Query("update Station set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);
    
    @Query("select COALESCE(sum(s.nowKw),0) from Station s WHERE s.del=0")
    double sumNowKw();
    
    @Query("select COALESCE(sum(s.nowKw),0) from Station s WHERE s.serverId=?1 AND s.del=0")
    double sumNowKw(Long serverId);
    
    @Query("select s.id from Station s WHERE s.del=0")
    List<Long> findId();
    
    @Query("select s.id from Station s WHERE s.serverId=?1 AND s.del=0")
    List<Long> findId(Long serverId);
    
    @Query("SELECT COUNT(*) FROM Station s WHERE s.createDtm>=?1 AND s.createDtm<?2 AND s.del=0")
    long countNum(Date startDtm, Date endDtm);
    
    @Query("SELECT COUNT(*) FROM Station s WHERE s.createDtm>=?1 AND s.createDtm<?2 AND s.serverId=?3 AND s.del=0")
    long countNum(Date startDtm, Date endDtm, Long serverId);
    
    @Query("select COALESCE(sum(s.capacity),0) from Station s WHERE s.del=0")
    double sumCapacity();
    
    @Query("select COALESCE(sum(s.capacity),0) from Station s WHERE s.serverId=?1 AND s.del=0")
    double sumCapacity(Long serverId);
}

package com.yn.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.TemStation;

public interface TemStationDao extends JpaRepository<TemStation, Long>, JpaSpecificationExecutor<TemStation> {
    @Modifying
    @Query("update TemStation set del=1,delDtm=(now()) where id = :id")
    void delete(@Param("id") Long id);
    
    @Transactional
    @Modifying
    @Query("update TemStation set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);
    
    @Query("select COALESCE(sum(t.kwh),0) from TemStation t WHERE t.type=?1 AND t.del=0")
    double sumKwh(Integer type);
    
    @Query("select COALESCE(sum(t.kwh),0) from TemStation t WHERE t.type=?1 AND t.serverId=?2 AND t.del=0")
    double sumKwh(Integer type, Long serverId);
    
    @Query("select COALESCE(sum(t.kwh),0) from TemStation t WHERE t.createDtm>=?1 AND t.createDtm<?2 AND t.type=?3 AND t.del=0")
    double sumKwh(Date startDtm, Date endDtm, Integer type);
    
    @Query("select COALESCE(sum(t.kwh),0) from TemStation t WHERE t.createDtm>=?1 AND t.createDtm<?2 AND t.type=?3 AND t.serverId=?4 AND t.del=0")
    double sumKwh(Date startDtm, Date endDtm, Integer type, Long serverId);
    
    @Query("select COALESCE(sum(t.kwh),0) from TemStation t WHERE t.createDtm>=?1 AND t.createDtm<?2 AND t.type=?3 AND t.stationId=?4 AND t.del=0")
    double sumKwhByStationId(Date startDtm, Date endDtm, Integer type, Long stationId);

    @Query("select o.dAddr from TemStation o where o.stationId = ?1 and o.type = ?2 and o.del = 0")
    Set<Long> findDAddr(Long stationId, Integer type);

    List<TemStation> findByTypeAndCreateDtmBetween(Integer type, Date start, Date end);

    List<TemStation> findByServerIdAndTypeAndCreateDtmBetween(Long serverId, Integer type, Date start, Date end);

    List<TemStation> findByStationIdAndTypeAndCreateDtmBetween(Long stationId, Integer type, Date start, Date end);
}

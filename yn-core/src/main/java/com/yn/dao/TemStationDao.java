package com.yn.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
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
    
    @Query("select COALESCE(sum(t.kwh),0) from TemStation t WHERE t.dAddr like CONCAT(?1,'%') AND t.del=0")
    double sumKwh(Long dAddr);
    
    @Query("select COALESCE(sum(t.kwh),0) from TemStation t WHERE t.dAddr like CONCAT(?1,'%') AND t.serverId=?2 AND t.del=0")
    double sumKwh(Long dAddr, Long serverId);
    
    @Query("select COALESCE(sum(t.kwh),0) from TemStation t WHERE t.createDtm>=?1 AND t.createDtm<?2 AND t.dAddr like CONCAT(?3,'%')  AND t.del=0")
    double sumKwh(Date startDtm, Date endDtm, Long dAddr);
    
    @Query("select COALESCE(sum(t.kwh),0) from TemStation t WHERE t.createDtm>=?1 AND t.createDtm<?2 AND t.dAddr like CONCAT(?3,'%') AND t.serverId=?4 AND t.del=0")
    double sumKwh(Date startDtm, Date endDtm, Long dAddr, Long serverId);
    
    @Query("select COALESCE(sum(t.kwh),0) from TemStation t WHERE t.createDtm>=?1 AND t.createDtm<?2 AND t.dAddr like CONCAT(?3,'%') AND t.stationId=?4 AND t.del=0")
    double sumKwhByStationId(Date startDtm, Date endDtm, Long dAddr, Long stationId);

    @Query("select o.dAddr from TemStation o where o.stationId = ?1 and o.dAddr like CONCAT(?2,'%') and o.del = 0")
    Set<Long> findDAddr(Long stationId, Long dAddr);
    
    @Query(value="select * from tem_station as t WHERE t.create_dtm>=?2 AND t.create_dtm<?3 AND t.d_addr like CONCAT(?1,'%')  AND t.del=0",nativeQuery=true)
    List<TemStation> findByDAddrAndCreateDtmBetween(Long dAddr, Date start, Date end);
    
    @Query(value="select * from tem_station as t WHERE t.create_dtm>=?3 AND t.create_dtm<?4 AND t.d_addr like CONCAT(?2,'%') AND t.server_id=?1 AND t.del=0",nativeQuery=true)
    List<TemStation> findByServerIdAndDAddrAndCreateDtmBetween(Long serverId, Long dAddr, Date start, Date end);
    
    @Query(value="select * from tem_station as t WHERE t.create_dtm>=?3 AND t.create_dtm<?4 AND t.d_addr like CONCAT(?2,'%') AND t.station_id=?1 AND t.del=0",nativeQuery=true)
    List<TemStation> findByStationIdAndDAddrAndCreateDtmBetween(Long stationId, Long dAddr, Date start, Date end);

    @Query("SELECT DATE_FORMAT(create_dtm,'%Y-%m') AS create_dtm, SUM(kwh) AS kwh FROM TemStation t WHERE t.stationId=?1 AND t.dAddr like CONCAT(1,'%') GROUP BY DATE_FORMAT(create_dtm,'%Y-%m')ORDER BY create_dtm ASC")
    List<Map<Object,Object>> sumMonthKwh(Long stationId);
}

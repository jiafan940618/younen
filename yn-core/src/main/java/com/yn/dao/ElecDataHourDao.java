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

import com.yn.model.ElecDataHour;

public interface ElecDataHourDao extends JpaRepository<ElecDataHour, Long>, JpaSpecificationExecutor<ElecDataHour> {
    @Modifying
    @Query("update ElecDataHour set del=1,delDtm=(now()) where id = :id")
    void delete(@Param("id") Long id);
    
    @Transactional
    @Modifying
    @Query("update ElecDataHour set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);
    

    @Query("select COALESCE(sum(t.kwh),0) from ElecDataHour t WHERE t.type =?1 AND t.del=0")
    double sumKwh(Integer type);
    
//    @Query("select COALESCE(sum(t.kwh),0) from ElecDataHour t WHERE  t.type =?1 AND t.serverId=?2 AND t.del=0")
//    double sumKwh(Integer type, Long serverId);
    
    
    @Query("select COALESCE(sum(t.kwh),0) from ElecDataHour t WHERE t.createDtm>=?1 AND t.createDtm<?2 AND  t.type =?3  AND t.del=0")
    double sumKwh(Date startDtm, Date endDtm, Integer type);
    
//    @Query("select COALESCE(sum(t.kwh),0) from ElecDataHour t WHERE t.createDtm>=?1 AND t.createDtm<?2 AND t.type =?3 AND t.serverId=?4 AND t.del=0")
//    double sumKwh(Date startDtm, Date endDtm,Integer type, Long serverId);
    
    @Query("select COALESCE(sum(t.kwh),0) from ElecDataHour t WHERE t.createDtm>=?1 AND t.createDtm<?2 AND t.type=?3 AND t.ammeterCode=?4 AND t.del=0")
    double sumKwhByAmmeterCode(Date startDtm, Date endDtm, Integer type, String ammeterCode);

    @Query(value="SELECT edh.d_addr FROM elec_data_hour edh INNER JOIN `ammeter_record` ar ON edh.ammeter_code = ar.c_addr WHERE  ar.station_id=?1 AND edh.type=?2 and edh.del = 0",nativeQuery=true)
    Set<Long> findDAddr(Long stationId, Integer type);
    
    @Query(value="select * from elec_data_hour as t WHERE t.create_dtm>=?2 AND t.create_dtm<?3 AND t.type =?1  AND t.del=0",nativeQuery=true)
    List<ElecDataHour> findByTypeAndCreateDtmBetween(Integer type, Date start, Date end);
    
//    @Query(value="select * from elec_data_hour as t WHERE t.create_dtm>=?3 AND t.create_dtm<?4 AND t.type =?2 AND t.server_id=?1 AND t.del=0",nativeQuery=true)
//    List<ElecDataHour> findByServerIdAndTypeAndCreateDtmBetween(Long serverId, Integer type, Date start, Date end);
    
    @Query(value="select * from elec_data_hour as t WHERE t.create_dtm>=?3 AND t.create_dtm<?4 AND t.type =?2 AND t.ammeter_code in (?1) AND t.del=0",nativeQuery=true)
    List<ElecDataHour> findByAmmeterCodeAndTypeAndCreateDtmBetween(String ammeterCode, Integer type, Date start, Date end);

    @Query("SELECT DATE_FORMAT(create_dtm,'%Y-%m') AS create_dtm, SUM(kwh) AS kwh FROM ElecDataHour t WHERE t.ammeterCode in(?1) AND t.type =1 GROUP BY DATE_FORMAT(create_dtm,'%Y-%m')ORDER BY create_dtm ASC")
    List<Map<Object,Object>> sumMonthKwh(String ammeterCode);
    
    @Query(value="select * from elec_data_hour as t WHERE t.create_dtm>=?3 AND t.create_dtm<?4 AND t.type =?2 AND t.ammeter_code in (?1) AND t.del=0",nativeQuery=true)
    List<ElecDataHour> findByAmmeterCodes(List<Long> ammeterCodes, Integer type, Date start, Date end); 
    
    @Query(value="select COALESCE(sum(t.kwh),0) from elec_data_hour as t WHERE t.create_dtm>=?1 AND t.create_dtm<?2 AND t.type=?3 AND t.ammeter_code in (?4) AND t.del=0",nativeQuery=true)
    double sumKwhByAmmeterCodes(Date startDtm, Date endDtm, Integer type, List<Long> ammeterCodes);
}

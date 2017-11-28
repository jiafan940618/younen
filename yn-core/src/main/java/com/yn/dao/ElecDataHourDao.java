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

    @Query(value="SELECT  d_addr FROM elec_data_day WHERE ammeter_code IN(SELECT c_addr FROM ammeter WHERE station_id=?1 AND del=0) AND del=0 AND type=?2 GROUP BY d_addr",nativeQuery=true)
    Set<Long> findDAddr(Long stationId, Integer type);
    
    @Query(value="select * from elec_data_hour as t WHERE t.create_dtm>=?2 AND t.create_dtm<?3 AND t.type =?1  AND t.del=0",nativeQuery=true)
    List<ElecDataHour> findByTypeAndCreateDtmBetween(Integer type, Date start, Date end);
    
//    @Query(value="select * from elec_data_hour as t WHERE t.create_dtm>=?3 AND t.create_dtm<?4 AND t.type =?2 AND t.server_id=?1 AND t.del=0",nativeQuery=true)
//    List<ElecDataHour> findByServerIdAndTypeAndCreateDtmBetween(Long serverId, Integer type, Date start, Date end);
    
    @Query(value="select * from elec_data_hour as t WHERE t.create_dtm>=?3 AND t.create_dtm<?4 AND t.type =?2 AND t.ammeter_code in (?1) AND t.del=0",nativeQuery=true)
    List<ElecDataHour> findByAmmeterCodeAndTypeAndCreateDtmBetween(String ammeterCode, Integer type, Date start, Date end);

    @Query("SELECT DATE_FORMAT(create_dtm,'%Y-%m') AS create_dtm, SUM(kwh) AS kwh FROM ElecDataHour t WHERE t.ammeterCode in(?1) AND t.type =1 GROUP BY DATE_FORMAT(create_dtm,'%Y-%m')ORDER BY create_dtm ASC")
    List<Map<Object,Object>> sumMonthKwh(String ammeterCode);
    
//    @Query(value="select * from elec_data_hour as t WHERE t.record_time>=?3 AND t.record_time<?4 AND t.type =?2 AND t.ammeter_code in (?1) AND t.del=0",nativeQuery=true)
//    List<ElecDataHour> findByAmmeterCodes(List<Long> ammeterCodes, Integer type, String start, String end);  
    
//    @Query(value="select t.record_time,SUM(t.kwh),SUM(t.kw) from elec_data_day as t WHERE t.record_time>=?3 AND t.record_time<=?4 "
//    		+ "AND t.type =?2 AND t.ammeter_code in (?1) AND t.del=0 group by DATE_FORMAT(create_dtm,'%Y')",nativeQuery=true)
//    Object[] findByYears(List<Long> ammeterCodes, Integer type, String start, String end);
    @Query(value="select t.record_time,SUM(t.kwh),SUM(t.kw) from elec_data_hour as t WHERE t.record_time>=?3 AND t.record_time<?4 "
    		+ "AND t.type =?2 AND t.ammeter_code in (?1) AND t.del=0 group by t.record_time",nativeQuery=true)
    Object[]  findByAmmeterCodes(List<Long> ammeterCodes, Integer type, String start, String end); 
    
    @Query(value="select COALESCE(sum(t.kwh),0) from elec_data_hour as t WHERE t.record_time>=?1 AND t.record_time<=?2 AND t.type=?3 AND t.ammeter_code in (?4) AND t.del=0",nativeQuery=true)
    double sumKwhByAmmeterCodes(String startDtm, String endDtm, Integer type, List<Long> ammeterCodes);
}

package com.yn.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.ElecDataDay;
import com.yn.model.ElecDataHour;
import com.yn.model.ElecDataMonth;
import com.yn.model.ElecDataYear;

public interface ElecDataDayDao extends JpaRepository<ElecDataDay, Long>, JpaSpecificationExecutor<ElecDataDay> {
    @Modifying
    @Query("update ElecDataDay set del=1,delDtm=(now()) where id = :id")
    void delete(@Param("id") Long id);
    
    @Transactional
    @Modifying
    @Query("update ElecDataDay set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);
    
    @Query(value="SELECT DATE_FORMAT(create_dtm,'%Y-%m') AS create_dtm, "
    		+ "SUM(kwh) AS kwh FROM elec_data_day t WHERE"
    		+ " t.create_dtm is not null AND t.ammeter_code in (?1) AND "
    		+ "t.type =1 GROUP BY DATE_FORMAT(create_dtm,'%Y-%m') ORDER BY record_time ASC",nativeQuery=true)
    List<Object[]> sumMonthKwh(List<Long> ammeterCodes);
    
    @Query("SELECT DATE_FORMAT(create_dtm,:dateFormat) AS create_dtm, SUM(kwh) AS kwh FROM ElecDataDay t "
    		+ "WHERE t.createDtm is not null AND t.ammeterCode in (:ammeterCodes) AND t.type =1 AND "
    		+ "t.createDtm LIKE CONCAT('%',:dateStr,'%') GROUP BY DATE_FORMAT(create_dtm,:dateFormat)ORDER BY create_dtm ASC")
    List<Object[]> sumKwh(@Param("ammeterCodes")List<Long> ammeterCodes,@Param("dateFormat")String dateFormat,@Param("dateStr")String dateStr);
    
    @Query(value="SELECT DATE_FORMAT(create_dtm,'%Y-%m') AS create_dtm, SUM(kwh) AS kwh FROM elec_data_day t "
    		+ "WHERE t.create_dtm is not null AND t.ammeter_code in (?1) AND t.type=?2 GROUP BY "
    		+ "DATE_FORMAT(create_dtm,'%Y-%m')ORDER BY create_dtm ASC",nativeQuery=true)
    List<Object[]> workUseCount(List<Long> ammeterCodes,Integer type);
    
    @Query(value="select t.record_time,SUM(t.kwh),SUM(t.kw) from elec_data_day as t WHERE t.record_time>=?3 AND t.record_time<=?4 "
    		+ "AND t.type =?2 AND t.ammeter_code in (?1) AND t.del=0 group by t.record_time",nativeQuery=true)
    Object[] findByDays(List<Long> ammeterCodes, Integer type, String start, String end);
    
    @Query(value="select t.record_time,SUM(t.kwh),SUM(t.kw) from elec_data_day as t WHERE t.record_time>=?3 AND t.record_time<=?4 "
    		+ "AND t.type =?2 AND t.ammeter_code in (?1) AND t.del=0 group by DATE_FORMAT(create_dtm,'%Y-%m')",nativeQuery=true)
    Object[] findByMonths(List<Long> ammeterCodes, Integer type, String start, String end);
    
    @Query(value="select t.record_time,SUM(t.kwh),SUM(t.kw) from elec_data_day as t WHERE t.record_time>=?3 AND t.record_time<=?4 "
    		+ "AND t.type =?2 AND t.ammeter_code in (?1) AND t.del=0 group by DATE_FORMAT(create_dtm,'%Y')",nativeQuery=true)
    Object[] findByYears(List<Long> ammeterCodes, Integer type, String start, String end);
    
    @Query(value="select COALESCE(sum(t.kwh),0) from elec_data_day as t WHERE t.type=?1 AND t.ammeter_code in (?2) AND t.del=0",nativeQuery=true)
    double sumKwhByHistory(Integer type, List<Long> ammeterCodes);

    @Query(value="select COALESCE(sum(t.kwh),0) from elec_data_day as t WHERE t.record_time>=?1 AND t.record_time<=?2 AND t.type=?3 AND t.ammeter_code in (?4) AND t.del=0",nativeQuery=true)
    double sumKwhByDays(String start, String end, Integer type, List<Long> ammeterCodes);

}

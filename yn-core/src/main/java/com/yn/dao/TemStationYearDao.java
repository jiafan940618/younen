package com.yn.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.TemStationYear;

public interface TemStationYearDao extends JpaRepository<TemStationYear, Long>, JpaSpecificationExecutor<TemStationYear> {
    @Modifying
    @Query("update TemStationYear set del=1,delDtm=(now()) where id = :id")
    void delete(@Param("id") Long id);
    
    @Transactional
    @Modifying
    @Query("update TemStationYear set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);
    
    @Query("SELECT DATE_FORMAT(create_dtm,'%Y-%m') AS create_dtm, SUM(kwh) AS kwh FROM TemStationYear t WHERE t.createDtm is not null AND t.stationId=?1 AND type=1 GROUP BY DATE_FORMAT(create_dtm,'%Y-%m')ORDER BY create_dtm ASC")
    List<Map<Object,Object>> sumMonthKwh(Long stationId);
    
    @Query("SELECT DATE_FORMAT(create_dtm,:dateFormat) AS create_dtm, SUM(kwh) AS kwh FROM TemStationYear t WHERE t.createDtm is not null AND t.stationId=:stationId AND type=1 AND t.createDtm LIKE CONCAT('%',:dateStr,'%') GROUP BY DATE_FORMAT(create_dtm,:dateFormat)ORDER BY create_dtm ASC")
    List<Map<Object,Object>> sumKwh(@Param("stationId")Long stationId,@Param("dateFormat")String dateFormat,@Param("dateStr")String dateStr);
    
    @Query("SELECT DATE_FORMAT(create_dtm,'%Y-%m') AS create_dtm, SUM(kwh) AS kwh FROM TemStationYear t WHERE t.createDtm is not null AND t.stationId=?1 AND type=?2 GROUP BY DATE_FORMAT(create_dtm,'%Y-%m')ORDER BY create_dtm ASC")
    List<Map<Object,Object>> workUseCount(Long stationId,Integer type);
}

package com.yn.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.ElecDataDay;

public interface ElecDataDayDao extends JpaRepository<ElecDataDay, Long>, JpaSpecificationExecutor<ElecDataDay> {
    @Modifying
    @Query("update ElecDataDay set del=1,delDtm=(now()) where id = :id")
    void delete(@Param("id") Long id);
    
    @Transactional
    @Modifying
    @Query("update ElecDataDay set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);
    
    @Query("SELECT DATE_FORMAT(create_dtm,'%Y-%m') AS create_dtm, SUM(kwh) AS kwh FROM ElecDataDay t WHERE t.createDtm is not null AND t.stationId=?1 AND t.dAddr like CONCAT(1,'%') GROUP BY DATE_FORMAT(create_dtm,'%Y-%m')ORDER BY create_dtm ASC")
    List<Map<Object,Object>> sumMonthKwh(Long stationId);
    
    @Query("SELECT DATE_FORMAT(create_dtm,:dateFormat) AS create_dtm, SUM(kwh) AS kwh FROM ElecDataDay t WHERE t.createDtm is not null AND t.stationId=:stationId AND t.dAddr like CONCAT(1,'%') AND t.createDtm LIKE CONCAT('%',:dateStr,'%') GROUP BY DATE_FORMAT(create_dtm,:dateFormat)ORDER BY create_dtm ASC")
    List<Map<Object,Object>> sumKwh(@Param("stationId")Long stationId,@Param("dateFormat")String dateFormat,@Param("dateStr")String dateStr);
    
    @Query("SELECT DATE_FORMAT(create_dtm,'%Y-%m') AS create_dtm, SUM(kwh) AS kwh FROM ElecDataDay t WHERE t.createDtm is not null AND t.stationId=?1 AND t.dAddr like CONCAT(?2,'%') GROUP BY DATE_FORMAT(create_dtm,'%Y-%m')ORDER BY create_dtm ASC")
    List<Map<Object,Object>> workUseCount(Long stationId,Long dAddr);
}

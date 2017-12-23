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
    		+ " t.create_dtm is not null AND t.del=0 AND t.ammeter_code in (?1) AND "
    		+ "t.type =1 GROUP BY DATE_FORMAT(create_dtm,'%Y-%m') ORDER BY record_time ASC",nativeQuery=true)
    List<Object[]> sumMonthKwh(List<Long> ammeterCodes);
    
    @Query("SELECT DATE_FORMAT(create_dtm,:dateFormat) AS create_dtm, SUM(kwh) AS kwh FROM ElecDataDay t "
    		+ "WHERE t.createDtm is not null AND t.ammeterCode in (:ammeterCodes) AND t.type =1 AND t.del=0 AND "
    		+ "t.createDtm LIKE CONCAT('%',:dateStr,'%') GROUP BY DATE_FORMAT(create_dtm,:dateFormat)ORDER BY create_dtm ASC")
    List<Object[]> sumKwh(@Param("ammeterCodes")List<Long> ammeterCodes,@Param("dateFormat")String dateFormat,@Param("dateStr")String dateStr);
    
    @Query(value="SELECT DATE_FORMAT(create_dtm,'%Y-%m') AS create_dtm, SUM(kwh) AS kwh FROM elec_data_day t "
    		+ "WHERE t.create_dtm is not null AND t.ammeter_code in (?1) AND t.del=0 AND t.type=?2 GROUP BY "
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
    Double sumKwhByHistory(Integer type, List<Long> ammeterCodes);

    @Query(value="select COALESCE(sum(t.kwh),0) from elec_data_day as t WHERE t.record_time>=?1 AND t.record_time<=?2 AND t.type=?3 AND t.ammeter_code in (?4) AND t.del=0",nativeQuery=true)
    Double sumKwhByDays(String start, String end, Integer type, List<Long> ammeterCodes);

    @Query(value="SELECT DATE_FORMAT(create_dtm,?2) AS create_dtm, SUM(kwh) AS kwh FROM elec_data_day as t "
    		+ "WHERE t.create_dtm is not null AND t.ammeter_code in (?1) AND t.type =?4 AND t.del=0 AND "
    		+ "t.create_dtm LIKE CONCAT('%',?3,'%') GROUP BY DATE_FORMAT(create_dtm,?2)ORDER BY create_dtm ASC",nativeQuery=true)
    List<Object[]> oneKwh(@Param("ammeterCodes")List<Long> ammeterCodes,@Param("dateFormat")String dateFormat,@Param("dateStr")String dateStr,@Param("type")Integer type);
    
    @Query(value="SELECT  d_addr FROM elec_data_day WHERE ammeter_code IN(SELECT c_addr FROM ammeter WHERE station_id=?1 AND del=0) AND del=0 AND d_addr like CONCAT(?2,'%')",nativeQuery=true)
    Set<Long> findDAddr(Long stationId, Integer type);
    
//  @Query("select o.dAddr from ElecDataHour o where o.stationId = ?1 and o.dAddr like CONCAT(?2,'%') and o.del = 0")

//  Set<Long> findDAddr(Long stationId, Long dAddr);
    @Query(value="select COALESCE(sum(t.kwh),0) from elec_data_day as t WHERE t.record_time>=?1 AND t.record_time<=?2 AND t.type=1 AND t.ammeter_code in (?3) AND t.del=0",nativeQuery=true)
    Double sumKwh(String start, String end, List<Long> ammeterCodes);
    
    @Query(value="select COALESCE(sum(t.kwh),0) from elec_data_day as t WHERE t.record_time>=?1 AND t.record_time<=?2 AND t.type=1 AND t.del=0",nativeQuery=true)
    Double sumKwhAll(String start, String end);

    /**
     * 
        * @Title: findByAmmeterCode
        * @Description: TODO(根据电表码去查每日的统计信息)
        * @param @param getcAddr
        * @param @return    参数
        * @return ElecDataDay    返回类型
        * @throws
     */
	List<ElecDataDay> findByAmmeterCode(String cAddr);
	
	/**
	 * 
	    * @Title: findByAmmeterCodeAndDAddrAndRecordTimeBetween
	    * @Description: TODO(顾名思义)
	    * @param @param cAddr 电表码
	    * @param @param dAddr 用发电类型
	    * @param @param startTime 统计的起始时间
	    * @param @param endTime 统计的结束时间
	    * @param @return    参数
	    * @return ElecDataDay    返回类型
	    * @throws
	 */
	List<ElecDataDay> findByAmmeterCodeAndDAddrInAndRecordTimeBetween(String cAddr,List<Long> dAddr,String startTime,String endTime);
	
}

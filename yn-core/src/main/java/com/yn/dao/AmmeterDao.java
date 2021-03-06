package com.yn.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.Ammeter;
import com.yn.vo.UserVo;

public interface AmmeterDao extends JpaRepository<Ammeter, Long>, JpaSpecificationExecutor<Ammeter> {
	@Modifying
	@Query("update Ammeter set del=1,delDtm=(now()) where id = :id")
	void delete(@Param("id") Long id);

	@Transactional
	@Modifying
	@Query("update Ammeter set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);

	@Query("SELECT COUNT(*) FROM Ammeter u WHERE u.createDtm>=?1 AND u.createDtm<?2 AND u.del=0")
	long countNum(Date startDtm, Date endDtm);
	/**

	 * 通过stationId找到电表

	 * 

	 */
	@Query(value="select * from ammeter as a where a.station_id=?1 AND a.del=0 ",nativeQuery=true)
    List<Ammeter> findByStationId(Long stationId);
	
    @Query(value="SELECT a.init_kwh,a.work_total_kwh,a.work_total_tm  FROM station s  LEFT JOIN ammeter a ON s.id = a.station_id WHERE s.user_id = :#{#userVo.id} AND s.del = 0",nativeQuery=true)
    List<Object> findByUserId(@Param("userVo") UserVo userVo);
    
    @Query(value="SELECT a.init_kwh,a.work_total_kwh,a.work_total_tm FROM station s  LEFT JOIN ammeter a ON s.id = a.station_id WHERE s.id = :stationId AND s.del = 0 ",nativeQuery=true)
    List<Object> findBynewStationId(@Param("stationId") Long stationId);
    
	@Query(value="select a.c_addr from ammeter as a where a.station_id=?1 AND a.del=0",nativeQuery=true)
    List<Long> selectAmmeterCode(Long stationId);
	
    @Query(value="select COALESCE(sum(s.now_kw),0) from ammeter as s WHERE s.del=0",nativeQuery=true)
    Double sumNowKw();
    
    @Query(value="select COALESCE(sum(s.now_kw),0) from ammeter as s WHERE s.station_id in (?1) AND s.del=0",nativeQuery=true)
    Double sumNowKwByStationIds(List<Long> stationIds);
    
    @Query(value="select COALESCE(sum(s.work_total_kwh),0) from ammeter as s WHERE s.station_id in (?1) AND s.del=0",nativeQuery=true)
    Double sumWorkTotalKwhByStationIds(List<Long> stationIds);
    
    @Query(value="select COALESCE(sum(s.work_total_kwh),0) from ammeter as s WHERE  s.del=0",nativeQuery=true)
    Double sumWorkTotalKwh();
    
    @Query(value="select COALESCE(sum(s.init_kwh),0) from ammeter as s WHERE s.station_id in (?1) AND s.del=0",nativeQuery=true)
    Double sumInitKwhByStationIds(List<Long> stationIds);
    
    @Query(value="select COALESCE(sum(s.init_kwh),0) from ammeter as s WHERE  s.del=0",nativeQuery=true)
    Double sumInitKwh();
    
    Ammeter findByCAddr(String caddr);
    
    @Query(value="select a.c_addr from ammeter as a WHERE a.del=0",nativeQuery=true)
    List<Long> selectAllAmmeter();
    
    @Query(value="select COALESCE(sum(s.now_kw),0) from ammeter as s WHERE s.del=0 and s.station_id=?1",nativeQuery=true)
    Double nowKw(Long stationId);
    
    @Query(value="select COALESCE(sum(s.work_total_kwh),0) from ammeter as s WHERE s.del=0 and s.station_id=?1",nativeQuery=true)
    Double workTotalkwh(Long stationId);
    
    @Query(value="select COALESCE(sum(s.init_kwh),0) from ammeter as s WHERE s.del=0 and s.station_id=?1",nativeQuery=true)
    Double initTotalkwh(Long stationId);
    
    @Query(value="select a.c_addr from ammeter as a where a.station_id in(?1) AND a.del=0",nativeQuery=true)
    List<Long> selectAmmeterCodeByStationIds(List<Long> stationIds);
    
    @Query(value="SELECT SUM(init_kwh)+SUM(work_total_kwh) FROM ammeter WHERE del=0 AND station_id "
    		+ "IN(SELECT id FROM station WHERE server_id=?1 AND del=0);",nativeQuery=true)
    Double sumKwh(Long serverId);
    
    @Query(value="SELECT SUM(init_kwh)+SUM(work_total_kwh) FROM ammeter WHERE del=0",nativeQuery=true)
    Double sumAllKwh();
    
	@Query(value="select a.c_addr from ammeter as a where a.station_id=?1 AND a.del=0",nativeQuery=true)
    Long selectAmmeterByStationId(Long stationId);
	
	@Query(value="SELECT MIN(a.create_dtm) FROM ammeter as a WHERE a.station_id=?1 AND a.del=0",nativeQuery=true)
    Date selectCreateDtm(Long stationId);
	

}


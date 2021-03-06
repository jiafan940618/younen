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

import com.yn.model.Station;

public interface StationDao extends JpaRepository<Station, Long>, JpaSpecificationExecutor<Station> {
	@Modifying
	@Query("update Station set del=1,delDtm=(now()) where id = :id")
	void delete(@Param("id") Long id);

	@Transactional
	@Modifying
	@Query("update Station set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);

	// @Query("select COALESCE(sum(s.nowKw),0) from Station s WHERE s.del=0")
	// double sumNowKw();

	// @Query("select COALESCE(sum(s.nowKw),0) from Station s WHERE
	// s.serverId=?1 AND s.del=0")
	// double sumNowKw(Long serverId);
	//

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

	@Query(value = "select * from station as s where s.user_id=?1 and s.status in(1,2) AND s.del=0", nativeQuery = true)
	List<Station> findByUserId(long userid);

	@Query(value = "select * from station as s where s.status in(1,2) AND s.del=0", nativeQuery = true)
	List<Station> findAllStation();

	@Query("SELECT DATE_FORMAT(create_dtm,'%Y-%m') AS create_dtm, SUM(capacity) AS capacity FROM Station t WHERE t.createDtm is not null AND t.id=?1 AND t.del=0 GROUP BY DATE_FORMAT(create_dtm,'%Y-%m')ORDER BY create_dtm ASC")
	List<Map<Object, Object>> findUserCapacity(Long stationId);

	@Query(value = "SELECT t2.province_text AS provinceName, COUNT(*) AS stationNum FROM station t1 INNER JOIN province t2 "
			+ "ON t1.province_id = t2.id where t1.del = 0 and t1.status =1 GROUP BY t1.province_id", nativeQuery = true)
	Object[] stationFenbu();

	@Query(value = "SELECT t2.province_text AS provinceName, COUNT(*) AS stationNum FROM station t1 INNER JOIN province t2 "
			+ "ON t1.province_id = t2.id where t1.del = 0 and t1.status = 1 and t1.server_id=?1 GROUP BY t1.province_id ORDER BY t2.id ASC", nativeQuery = true)
	Object[] stationFenbuById(Long id);

	/** 根据userid查询出电站的信息 */
	@Query("select new Station(id,stationName,userId,capacity,status,stationCode) from Station s WHERE s.userId = :userId and s.del=0 ")
	List<Station> getstation(@Param("userId") Long userId);

	@Query("select new Station(id,stationName,userId,capacity,status,stationCode) from Station s WHERE s.orderId = :orderId and s.del=0")
	Station FindByStationCode(@Param("orderId") Long orderId);


	/**
	 * 按时间查询装机并网总量
	 */

	@Query("SELECT DATE_FORMAT(create_dtm,:dateFormat) AS create_dtm, SUM(capacity) AS capacity FROM Station t WHERE t.createDtm is not null AND t.del=0 AND t.createDtm LIKE CONCAT('%',:dateStr,'%') AND t.id=:stationId GROUP BY DATE_FORMAT(create_dtm,:dateFormat)ORDER BY create_dtm ASC")
	List<Map<Object, Object>> numCapacity(@Param("stationId") Long stationId, @Param("dateFormat") String dateFormat,
			@Param("dateStr") String dateStr);

	/**
	 * select s.id,s.station_name,s.user_id,s.capacity,s.status,
	 * s.station_code,a.init_kwh,a.work_total_kwh,a.work_total_tm from station s
	 * LEFT JOIN ammeter a on s.id = a.station_id WHERE s.user_id = 2 and
	 * s.del=0
	 */
	/** 移动端的我的电站 */
	@Query(value = "select  s.id,s.station_name,s.user_id,s.capacity,s.status,"
			+ "  s.station_code,a.init_kwh,a.work_total_kwh,a.work_total_tm,u.user_name,a.now_kw"
			+ " from station s "
			+ " LEFT JOIN user u on u.id = s.user_id "
			+ " LEFT JOIN ammeter a on s.id = a.station_id   WHERE s.user_id = :userId and s.del=0", nativeQuery = true)
	List<Object> findByUserIdS(@Param("userId") Long userId);
	
	@Query(value = "select  s.id,s.station_name,s.user_id,s.capacity,s.status,"
			+ "  s.station_code,a.init_kwh,a.work_total_kwh,a.work_total_tm,u.user_name,a.now_kw"
			+ " from station s "
			+ " LEFT JOIN user u on u.id = s.user_id "
			+ " LEFT JOIN ammeter a on s.id = a.station_id   WHERE s.id in (:ids) and s.del=0", nativeQuery = true)
	List<Object> findByList(@Param("ids") List<Long> ids);
	
	@Query("select s.capacity from Station s WHERE s.id=?1 AND s.del=0")
	double findCapacity(Long stationId);
	
	@Query("select s from Station s WHERE  s.del=0")
	List<Station> getselStation();
	
	@Query("select v from Station v where v.id in (stationIds) AND v.del=0")
	List<Station> findByStation(String stationIds);
	
	@Query(value = "select id from station  where  del=0", nativeQuery = true)
	List<Integer> FindByStationId();
	
	@Query("select s.id from Station s WHERE s.del=0")
	List<Long> findAllStationId();

	@Query(value="SELECT s.capacity,a.init_kwh,a.work_total_kwh,s.id,a.now_kw,a.work_total_tm " +
			" FROM station s LEFT JOIN  ammeter a ON s.id  = a.station_id WHERE s.status <>0 AND a.del = 0 AND s.del =0 AND s.user_id = ?1", nativeQuery = true)
	Object findByNewUserId(Long userId);

	/**
	 * 
	    * @Title: findByLinkMan
	    * @Description: TODO(输入用户名，查找他的电站)
	    * @param @param linkMan
	    * @param @return    参数
	    * @return Station    返回类型
	    * @throws
	 */
	List<Station> findByLinkMan(String linkMan);
	

}

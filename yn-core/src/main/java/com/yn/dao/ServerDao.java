package com.yn.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.Server;

public interface ServerDao extends JpaRepository<Server, Long>, JpaSpecificationExecutor<Server> {
	@Modifying
	@Query("update Server set del=1,delDtm=(now()) where id = :id")
	void delete(@Param("id") Long id);

	@Transactional
	@Modifying
	@Query("update Server set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);

	@Query("SELECT COUNT(*) FROM Server u WHERE u.createDtm>=?1 AND u.createDtm<?2 AND u.del=0")
	long countNum(Date startDtm, Date endDtm);

	Page<Server> findByServerCityIdsLikeAndDel(String ids, int del, Pageable pageable);
	

	    @Query("select new Server(companyName,factorage)  from Server  where id = :serverid")
	    Server findServer(@Param("serverid") Long serverid);
	    
	    
	    @Query(value="SELECT"
	    		+ "  DISTINCT(e.id), e.company_name,o.type,o.conversion_efficiency,o.quality_assurance,o.board_year,e.company_logo  FROM server e "
		+ " LEFT JOIN new_server_plan p ON e.id = p.`server_id`"+
		 " LEFT JOIN solar_panel o ON  p.`batteryboard_id` = o.id  WHERE e.del=0 AND p.planid=1 LIMIT ?1,?2 ",nativeQuery=true)
	  //  List<Object> findObject(@Param("start") Integer start,@Param("limit") Integer limit);
	    List<Object> findObject(Integer start, Integer limit);

	    
	    @Query(value="SELECT "
	    		+ " DISTINCT(s.id),s.company_name,o.type,o.conversion_efficiency,o.quality_assurance,o.board_year,s.company_logo  FROM server s "+
	   		 " LEFT JOIN new_server_plan p ON s.id = p.`server_id`"+
	   		 " LEFT JOIN solar_panel o ON  p.`batteryboard_id` = o.id  WHERE s.del=0 AND p.planid=1 and s.`server_city_text`=:cityName LIMIT :start,:limit",nativeQuery=true)
	   	    List<Object> findtwoObject(@Param("cityName") String CityName,@Param("start") Integer start,@Param("limit") Integer limit);
	     
	    /** 查询资质*/
	    @Query(value=" SELECT DISTINCT(e.id),t.img_url,t.text  FROM server e"+
	    		" LEFT JOIN qualifications_server q ON q.`server_id` =e.`id` "+
	    		" LEFT JOIN qualifications t ON  t.id= q.qualifications_id WHERE e.del=0  and e.id in (:ids)",nativeQuery=true)
	    List<Object> findquatwoObject(@Param("ids") List<Long> ids);
	    
	    
	     /** 查询资质*/
	    @Query(value=" SELECT DISTINCT(e.id),t.img_url,t.text  FROM server e"+
	    		" LEFT JOIN qualifications_server q ON q.`server_id` =e.`id` "+
	    		" LEFT JOIN qualifications t ON  t.id= q.qualifications_id WHERE e.del=0 and e.`server_city_text`=:cityName and e.id in (:ids)",nativeQuery=true)
	   	    List<Object> findquaObject(@Param("ids") List<Long> ids);
	    
	  /** 查存储总页数*/
	    @Query(value="SELECT  COUNT(1) FROM Server e WHERE e.del=0 ")
		  //  List<Object> findObject(@Param("start") Integer start,@Param("limit") Integer limit);
		    Long findCount();
	    
	    /** 查存储总页数*/
	    @Query("SELECT  COUNT(1) FROM Server e WHERE e.del=0 AND e.serverCityText = :cityName  ")
		  //  List<Object> findObject(@Param("start") Integer start,@Param("limit") Integer limit);
	    Long findcityCount(@Param("cityName") String cityName);
	      
	    
	    
	
}

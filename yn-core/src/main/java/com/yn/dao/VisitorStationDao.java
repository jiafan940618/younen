package com.yn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.yn.model.Station;
import com.yn.model.VisitorStation;



public interface VisitorStationDao  extends JpaRepository<VisitorStation, Long>, JpaSpecificationExecutor<VisitorStation>{
	
	/*@Query("select v from VisitorStation v where v.stationIds in ()")
	List<Station> findCapacity(Long stationId);*/
	
	@Query("select v from VisitorStation v where v.userId =?1 ")
	VisitorStation findVisitorStation(Long userId);
	
	
}

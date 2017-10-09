package com.yn.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yn.model.Weather;

public interface WeatherDao extends JpaRepository<Weather, Long>, JpaSpecificationExecutor<Weather> {

	Weather findByCity(@Param("city") String city);
	
	
	@Query(value="SELECT w FROM Weather WHERE createDtm LIKE  :#{#weather.createDtm}%  AND city  LIKE :#{#weather.city} ",nativeQuery=true)
	Weather findBycityandcreate(@Param("weather") Weather weather);
	
	
}

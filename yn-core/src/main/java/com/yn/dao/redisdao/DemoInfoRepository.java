package com.yn.dao.redisdao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.City;
import com.yn.model.Weather;

/**
* DemoInfo持久化类
*/
public interface DemoInfoRepository extends CrudRepository<Weather,Long>{
	
	
    @Query(value="select w.create_dtm,w.city,w.w_day,w.w_night,w.uv,w.id from weather w where w.city =:city and w.create_dtm like :createTime ",nativeQuery=true)
	//@Query(value="select w from weather w where w.city =:city and w.create_dtm like :createTime ",nativeQuery=true)
    Object findbydate(@Param("city") String city,@Param("createTime") String createTime);
	
	
	
	

}

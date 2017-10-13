package com.yn.service.redisService;


import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.Logger;
import com.yn.dao.redisdao.DemoInfoRepository;
import com.yn.model.City;
import com.yn.model.Weather;

/**
 * DemoInfo数据处理类
 * */
@Service
public class DemoInfoService {
	
	@Autowired
	private DemoInfoRepository demoInfoRepository;
	
	@Autowired
    private RedisTemplate<String,String> redisTemplate;
	
	 public Weather findOne(Long id) {
	        return demoInfoRepository.findOne(id);
	    }

    public void test(){

           ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();

           valueOperations.set("mykey4", "random1="+Math.random());

           System.out.println(valueOperations.get("mykey4"));

    }
    
    @Cacheable(value="weather",key="#city") //缓存
  public  Weather findbydate(String city,String createTime){

	  System.err.println("DemoInfoServiceImpl.findbydate=========从数据库中进行获取的");
	  
	  createTime = createTime+"%";
	  
	  Weather weather = new Weather();
	  
	 Object object = demoInfoRepository.findbydate(city,createTime);
	 Object[] obj = (Object[])object;
	 
	 Date createDtm = (Date)obj[0];
	 String city01 =(String)obj[1];
	 String wday =(String)obj[2];
	 String wnight =(String)obj[3];
	 Integer wuv =(Integer)obj[4];
	 
	 weather.setCreateDtm(createDtm);
	 weather.setCity(city01);
	 weather.setwDay(wday);
	 weather.setwNight(wnight);
	 weather.setUv(wuv);
 
	 
	return weather;
   }
    
    

    //keyGenerator="myKeyGenerator"

    @Cacheable(value="weather") //缓存,这里没有指定key.
    public Weather findById(long id) {

           System.err.println("DemoInfoServiceImpl.findById()=========从数据库中进行获取的....id="+id);

           return demoInfoRepository.findOne(id);

    }

   

    @CacheEvict(value="weather",key="#city")
    public void deleteFromCache(String city) {

           System.out.println("DemoInfoServiceImpl.delete().从缓存中删除.");

    }

}

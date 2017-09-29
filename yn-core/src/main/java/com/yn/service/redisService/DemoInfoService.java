package com.yn.service.redisService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.yn.dao.redisdao.DemoInfoRepository;
import com.yn.model.City;

/**
 * DemoInfo数据处理类
 * */
@Service
public class DemoInfoService {
	
	@Autowired
	private DemoInfoRepository demoInfoRepository;
	
	@Autowired
    private RedisTemplate<String,String> redisTemplate;
	

    public void test(){

           ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();

           valueOperations.set("mykey4", "random1="+Math.random());

           System.out.println(valueOperations.get("mykey4"));

    }

   

    //keyGenerator="myKeyGenerator"

    @Cacheable(value="city") //缓存,这里没有指定key.

    public City findById(long id) {

           System.err.println("DemoInfoServiceImpl.findById()=========从数据库中进行获取的....id="+id);

           return demoInfoRepository.findOne(id);

    }

   

    @CacheEvict(value="city")
    public void deleteFromCache(long id) {

           System.out.println("DemoInfoServiceImpl.delete().从缓存中删除.");

    }
	
	
	
	
	
	
	
	

}

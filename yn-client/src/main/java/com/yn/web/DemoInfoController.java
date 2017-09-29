package com.yn.web;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yn.model.City;
import com.yn.service.redisService.DemoInfoService;

@Controller
@RequestMapping("/client/demo")
public class DemoInfoController {
	
	@Autowired
	 private ValueOperations<String, Object> valueOperations;
	@Autowired
    DemoInfoService demoInfoService;
	
	@ResponseBody
	@RequestMapping("/test")
    public  String test(){

		City loaded = demoInfoService.findById(1);

		System.out.println("loaded="+loaded);
	
		City cached = demoInfoService.findById(1);

        System.out.println("cached="+cached);

        loaded = demoInfoService.findById(2);

        System.out.println("loaded2="+loaded);
        
      /** 根据系统时间来确定*/
        
       

        return "ok";

    }
	
	

}

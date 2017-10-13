package com.yn.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yn.model.City;
import com.yn.model.Weather;
import com.yn.service.redisService.DemoInfoService;

@Controller 
@RequestMapping("/client/demo")
public class DemoInfoController {
	
	@Autowired
    DemoInfoService demoInfoService;
	
	@ResponseBody
	@RequestMapping("/test")
    public  String test(){
	
		
		Weather loaded = demoInfoService.findById(3l);

		System.out.println("loaded="+loaded);
	
		Weather cached = demoInfoService.findById(3l);

        System.out.println("cached="+cached);

        loaded = demoInfoService.findById(5l);

        System.out.println("loaded2="+loaded);
        
      /** 根据系统时间来确定*/
        
        
        
        Date date = new Date();
        
        SimpleDateFormat formart = new SimpleDateFormat("yyyy-MM-dd");
        
        String dateTime =formart.format(date);
        
        
       

        return "ok";

    }
	
	

}

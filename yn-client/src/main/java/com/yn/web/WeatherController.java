package com.yn.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.model.Weather;
import com.yn.service.WeatherService;
import com.yn.service.redisService.DemoInfoService;
import com.yn.utils.GetIP;
import com.yn.vo.WeatherVo;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/client/weather")
public class WeatherController {

	private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);
	@Autowired
	WeatherService weatherService;
	
	@Autowired
	DemoInfoService demoInfoService;
	
	

	@RequestMapping(value = "/getWeather", method = { RequestMethod.GET })
	public Object getWeather(HttpServletRequest request) {
		String ip = GetIP.getRemoteAddress(request);
		//ip = "120.76.98.74";

		String cityJson = weatherService.getCity(ip);
		String city = weatherService.jsonToCity(cityJson);

		Weather weather = weatherService.getWeather(city);
		return ResultVOUtil.success(weather);
	}
	
	@ResponseBody
	@RequestMapping("/findWeather")
    public  Object Weather(WeatherVo weatherVo){
	//	weatherVo.setCity("东莞");
      /** 根据系统时间来确定*/
        logger.info("-- --- ---- - -- - - ------传递的城市为："+weatherVo.getCity());
     
        Date date = new Date();
        
        SimpleDateFormat formart = new SimpleDateFormat("yyyy-MM-dd");
        
       String dateTime =formart.format(date);
       logger.info("-- --- ---- - -- - - ------当前时间为："+dateTime);
        
        Weather weather  = demoInfoService.findbydate(weatherVo.getCity(),dateTime);
        System.out.println("weather -->"+weather);
      
        return ResultVOUtil.success(weather);

    }
	
	
	
	
}

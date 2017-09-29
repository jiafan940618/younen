package com.yn.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yn.model.Weather;
import com.yn.service.WeatherService;
import com.yn.utils.GetIP;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/client/weather")
public class WeatherController {

	@Autowired
	WeatherService weatherService;

	@RequestMapping(value = "/getWeather", method = { RequestMethod.GET })
	public Object getWeather(HttpServletRequest request) {
		String ip = GetIP.getRemoteAddress(request);
		//ip = "120.76.98.74";

		String cityJson = weatherService.getCity(ip);
		String city = weatherService.jsonToCity(cityJson);

		Weather weather = weatherService.getWeather(city);
		return ResultVOUtil.success(weather);
	}
}

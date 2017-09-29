package com.yn.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yn.dao.WeatherDao;
import com.yn.model.Weather;
import com.yn.utils.HttpUtil;
import com.yn.utils.JsonUtil;

import net.sf.json.JSONArray;

/**
 * 
 * @author Allen
 *
 */
@Service
public class WeatherService {
	@Autowired
	WeatherDao weatherDao;

	// final private String JUHEAPPKEY = "72daa5ecfd27138c92f4eb4d257979fd";
	final private String HEFENGAPPKEY = "8f68a8f55d1e4f259caa3de36cdf16fa";
	// final private String JUHEURL = "http://apis.juhe.cn/ip/ip2addr";
	final private String HEFENGURL = "https://free-api.heweather.com/v5/weather";
	final private String TAOBAOURL = "http://ip.taobao.com/service/getIpInfo.php";

	public String getCity(String ip) {
		// String requestUrl = JUHEURL + "?ip=" + ip + "&key=" + JUHEAPPKEY;
		String requestUrl = TAOBAOURL + "?ip=" + ip;
		String cityJson = HttpUtil.doGet(requestUrl);
		System.out.println(cityJson);
		return cityJson;
	}

	public Weather getWeather(String city) {
		// 1.查看数据库中是否存在该城市的数据
		// 2.存在则直接返回；
		// 3.不存在则先查询，再保存再返回

		Weather weather=weatherDao.findByCity(city);
		if (weather==null){
			String requestUrl = HEFENGURL + "?city=" + city + "&key=" + HEFENGAPPKEY;
			String weatherJson = HttpUtil.doGet(requestUrl);
			weather=jsonToWeather(weatherJson);
			weatherDao.save(weather);
		}
		return weather;
	}

	@SuppressWarnings("unchecked")
	public String jsonToCity(String cityJson) {
		Map<String, Object> map = JsonUtil.parseJSON2Map(cityJson);
		// Map<String, Object> result = (Map<String, Object>) map.get("result");
		// String city = result.get("area").toString();
		Map<String, Object> result = (Map<String, Object>) map.get("data");
		String city = result.get("city").toString();
		return city;
	}

	@SuppressWarnings("unchecked")
	public Weather jsonToWeather(String weatherJson) {
		Map<String, Object> map = JsonUtil.parseJSON2Map(weatherJson);
		JSONArray heWeather5Array = JSONArray.fromObject(map.get("HeWeather5"));
		Map<String, Object> heWeather5Map = (Map<String, Object>) heWeather5Array.get(0);

		Map<String, Object> basicMap = (Map<String, Object>) heWeather5Map.get("basic");
		String city = basicMap.get("city").toString();

		Map<String, Object> aqiMap = (Map<String, Object>) heWeather5Map.get("aqi");
		Map<String, Object> cityAqiMap = (Map<String, Object>) aqiMap.get("city");
		Integer pm25 = Integer.valueOf(cityAqiMap.get("pm25").toString());

		JSONArray dailyForecastArray = JSONArray.fromObject(heWeather5Map.get("daily_forecast"));
		Map<String, Object> dailyForecastMap = (Map<String, Object>) dailyForecastArray.get(0);
		Integer uv = Integer.valueOf(dailyForecastMap.get("uv").toString());

		Map<String, Object> condMap = (Map<String, Object>) dailyForecastMap.get("cond");
		String wDay = condMap.get("txt_d").toString();
		String wNight = condMap.get("txt_n").toString();

		Map<String, Object> temperatureMap = (Map<String, Object>) dailyForecastMap.get("tmp");
		Integer tMax = Integer.valueOf(temperatureMap.get("max").toString());
		Integer tMin = Integer.valueOf(temperatureMap.get("min").toString());

		Weather weather = new Weather();
		weather.setCity(city);
		weather.setPm25(pm25);
		weather.setUv(uv);
		weather.settMax(tMax);
		weather.settMin(tMin);
		weather.setwDay(wDay);
		weather.setwNight(wNight);

		return weather;
	}

}

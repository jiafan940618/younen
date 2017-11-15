package com.yn.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.service.SystemConfigService;
import com.yn.vo.ResultData;

@RestController
@RequestMapping("/guangfu/systemConfig")
public class SystemConfig02Controller {
	    @Autowired
	    SystemConfigService systemConfigService;
	
	    @ResponseBody
		@RequestMapping(value = "/updateAndroid")
		public ResultData<Map<String, String>> deleteBatch(HttpServletRequest request, HttpServletResponse response,
				HttpSession httpSession) {
	    	ResultData<Map<String, String>> resultData = new ResultData<Map<String, String>>();
	    	
			Map<String, String> map = systemConfigService.getnewlist();
			
			map.put("android_version_code", map.get("android_version_code"));
			map.put("android_version_name", map.get("android_version_name"));
			map.put("android_update_url", map.get("android_update_url"));
			map.put("android_update_info", map.get("android_update_info"));
			
			resultData.setData(map);
			
			return resultData;
		}
	

}

package com.yn.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yn.service.SystemConfigService;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/client/download")
public class DownloadController {

	@Autowired
	SystemConfigService systemConfigService;

	final private String ANDROID_UPDATE_URL = "android_update_url";

	@RequestMapping(value = "/getAppUrl", method = { RequestMethod.GET })
	public Object getAppUrl() {
		String appUrl = systemConfigService.get(ANDROID_UPDATE_URL);
		Map<String, String> result = new HashMap<>();
		result.put("appUrl", appUrl);
		return ResultVOUtil.success(result);
	}
}

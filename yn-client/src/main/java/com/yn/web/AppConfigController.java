package com.yn.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.service.SystemConfigService;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/client/appConfig")
public class AppConfigController {
	private String IS_SYSTEM_MAINTAIN = "is_system_maintain";
	private String SYSTEM_MAINTAIN_NOTICE = "system_maintain_notice";

	@Autowired
	SystemConfigService systemConfigService;

	@RequestMapping(value = "/isSystemMaintain", method = { RequestMethod.POST })
	@ResponseBody
	public Object isSystemMaintain() {
		String is_system_maintain = systemConfigService.get(IS_SYSTEM_MAINTAIN);
		String system_maintain_notice = systemConfigService.get(SYSTEM_MAINTAIN_NOTICE);
		Map<String, String> result = new HashMap<>();
		result.put(IS_SYSTEM_MAINTAIN, is_system_maintain);
		result.put(SYSTEM_MAINTAIN_NOTICE, system_maintain_notice);
		return ResultVOUtil.success(result);
	}
}

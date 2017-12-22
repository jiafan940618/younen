package com.yn.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.yn.service.JPushService;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/server/jpush")
public class JPushController {
	@Autowired
	JPushService jPushService;
	
	@RequestMapping(value = "/Allpush", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object pushAll() {
		
		String alert="我五五开从来没有开挂之全网推送";
        jPushService.JPushAll(alert);
        return ResultVOUtil.success(alert);
    }
	@RequestMapping(value = "/pushByAlias", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object pushByAlias() {  
		String alert="我五五开从来没有开挂之不是别名推送";
        jPushService.JPushByAlias(alert, "88");
        return ResultVOUtil.success(alert);
    }
	@RequestMapping(value = "/pushByQuite", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object pushByQuite() { 
		String alert="我五五开从来没有开挂之静默推送";
        jPushService.JPushAndroidByQuite(alert, "88");
        return ResultVOUtil.success(alert);
    }
}

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
		
		String alert="";
        jPushService.JPushAll(alert);
        return ResultVOUtil.success(alert);
    }
	@RequestMapping(value = "/pushByAlias", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object pushByAlias() {
		String alias2="0078449ec56c321318edc1f8e9b46820" ;   
		String alert="我五五开从来没有开挂之不是静默推送";
		String  alias="170976fa8a851585fb8";
        jPushService.JPushByAlias(alert, "88");
        return ResultVOUtil.success(alert);
    }
	@RequestMapping(value = "/pushByQuite", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object pushByQuite() {
		String alias2="0078449ec56c321318edc1f8e9b46820" ;   
		String alert="我五五开从来没有开挂";
		String  alias="170976fa8a851585fb8";
        jPushService.JPushAndroidByQuite(alert, "88");
        return ResultVOUtil.success(alert);
    }
}

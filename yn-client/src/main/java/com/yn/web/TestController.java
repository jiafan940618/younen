package com.yn.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.model.NewServerPlan;
import com.yn.service.DevideService;
import com.yn.service.NewServerPlanService;
import com.yn.service.SystemConfigService;
import com.yn.utils.ResultData;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/client/test")
public class TestController {
	
	@Autowired
	private NewServerPlanService planService;
	
	 @RequestMapping(value = "/dotest")
	 @ResponseBody
	    public ResultData<Object> newTest() {
		 
		 NewServerPlan serverPlan = new NewServerPlan (); 
		 serverPlan.setServerId(1L);
		 
	List<NewServerPlan>	 list = planService.findAll(serverPlan);
		 
			return ResultVOUtil.success(list);
	    }
	
	
	

}

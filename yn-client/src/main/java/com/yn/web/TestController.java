package com.yn.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.model.Apolegamy;
import com.yn.model.NewServerPlan;
import com.yn.model.Server;
import com.yn.service.ApolegamyService;
import com.yn.service.DevideService;
import com.yn.service.NewServerPlanService;
import com.yn.service.ServerService;
import com.yn.service.SystemConfigService;
import com.yn.utils.ResultData;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/client/test")
public class TestController {
	
	@Autowired
	private ServerService planService;
	
	  @Autowired
	ApolegamyService apolegamyService;
	
	 @RequestMapping(value = "/dotest")
	 @ResponseBody
	    public ResultData<Object> newTest() {
		 
		/* NewServerPlan serverPlan = new NewServerPlan (); 
		 serverPlan.setServerId(1L);*/
		 
		/* NewServerPlan	plan = planService.findOne(1L);
		 
		System.out.println(plan.getInverter().getBrandName()+"---- --"+plan.getInverter().getModel()
				+" -- -- "+plan.getSolarPanel().getBrandName()+"-- -- "+plan.getSolarPanel().getModel());*/
		 Server server = new Server();
		 server.setId(1l);
		 
		List<Server> list = planService.findAll(server);
		 

			return ResultVOUtil.success(list);
	    }
	
	
	

}

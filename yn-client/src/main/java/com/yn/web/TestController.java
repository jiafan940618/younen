package com.yn.web;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.yn.model.Decideinfo;
import com.yn.model.NewServerPlan;
import com.yn.service.DecideinfoService;
import com.yn.service.NewServerPlanService;
import com.yn.service.redisService.DemoInfoService;
import com.yn.utils.PropertyUtils;
import com.yn.vo.UserVo;
import com.yn.vo.re.ResultVOUtil;

@Controller
@RequestMapping("/client/test")
public class TestController {

	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	private String merchantId =PropertyUtils.getProperty("merchantId");
	private String BankAccountNo = PropertyUtils.getProperty("BankAccountNo");
	private String notifyUrl =PropertyUtils.getProperty("notifyUrl");
	
	@Autowired
    DecideinfoService  decideinfoService;
	
	@Autowired
	NewServerPlanService newserverPlanService;
	@Autowired
	DemoInfoService  demoInfoService;
	
	       @RequestMapping("/dotest")  
	       public String helloJsp(){  
	         
	    	logger.info("--- ---- ---- ----  --- merchantId: "+merchantId);
	    	logger.info("--- ---- ---- ----  --- BankAccountNo: "+BankAccountNo); 
	    	logger.info("--- ---- ---- ----  --- notifyUrl: "+notifyUrl); 
	    	 
	              return "NewFile";  
	       } 
	       
	       
	       @RequestMapping("/test")  
	       public String helloJsp001(){  
	          
	    	   NewServerPlan findOne = newserverPlanService.findOne(1l);
	    	   System.out.println(findOne);

	              return "NewFile";  
	       } 
	         
	       
	       
	       
	
	 @RequestMapping(value = "/dotest02")
	 @ResponseBody
	    public Object someTest(HttpSession session) {
		 
		System.out.println("进入测试!");

			return ResultVOUtil.success(null);
	    }
	
}

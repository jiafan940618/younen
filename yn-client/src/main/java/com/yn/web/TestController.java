package com.yn.web;


import java.util.HashMap;
import java.util.List;
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
import com.yn.model.Order;
import com.yn.model.Page;
import com.yn.model.Server;
import com.yn.service.DecideinfoService;
import com.yn.service.NewServerPlanService;
import com.yn.service.OrderService;
import com.yn.service.ServerService;
import com.yn.service.redisService.DemoInfoService;
import com.yn.utils.PropertyUtils;
import com.yn.vo.UserVo;
import com.yn.vo.re.ResultVOUtil;

@Controller
@RequestMapping("/client/test")
public class TestController {

	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
/*	private String merchantId =PropertyUtils.getProperty("merchantId");
	private String BankAccountNo = PropertyUtils.getProperty("BankAccountNo");
	private String notifyUrl =PropertyUtils.getProperty("notifyUrl");*/
	
	@Autowired
    DecideinfoService  decideinfoService;
	@Autowired
	ServerService  serverService;
	@Autowired
	OrderService orderService;
	@Autowired
	NewServerPlanService newserverPlanService;
	@Autowired
	DemoInfoService  demoInfoService;
	
	       @RequestMapping("/dotest") 
	       @ResponseBody
	       public Object helloJsp01(){  
	         
	    	   Page<Order> page = new  Page<Order>();
	    	   page.setUserId(3L);
	    	   page.setStatus(9);
	    	   page.setIndex(1);
	    	   
	    	   List<Order> list =   orderService.findBystatus(page);
	    	   for (Order order : list) {
	    		   logger.info("-- ---- --- --- ---- --- order.getId()"+order.getId());
	    		   logger.info("-- ---- --- --- ---- --- order.getServerName()"+order.getServerName());
	    		   logger.info("-- ---- --- --- ---- --- order.getOrderCode()"+order.getOrderCode());
	    		   logger.info("-- ---- --- --- ---- --- order.getCapacity()"+order.getCapacity());
	    	   }
	    	 
	            return ResultVOUtil.success(list);  
	       } 
	       
	       @ResponseBody
	       @RequestMapping("/test")  
	       public Object helloJsp001(){  
	          
	    	   Order order = new Order();
	    	   order.setId(1L);
	    	   
	    	   Order order2 =   orderService.selectOrderSta(order);
	    	   
	              return ResultVOUtil.success(order2);  
	       } 
	         
	       
	       
	       
	
	 @RequestMapping(value = "/dotest02")
	 @ResponseBody
	    public Object someTest(HttpSession session) {
		 
		System.out.println("进入测试!");

			return ResultVOUtil.success(null);
	    }
	
}

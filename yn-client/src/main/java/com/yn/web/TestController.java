package com.yn.web;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.dao.mapper.ServerMapper;
import com.yn.model.BillOrder;
import com.yn.model.Server;
import com.yn.model.User;
import com.yn.model.newPage;
import com.yn.service.BillOrderService;
import com.yn.service.ServerService;
import com.yn.service.SolarPanelSerice;
import com.yn.service.UserService;
import com.yn.utils.PhoneFormatCheckUtils;
import com.yn.utils.PropertyUtils;
import com.yn.utils.ResultData;
import com.yn.vo.QualificationsVo;
import com.yn.vo.SolarPanelVo;
import com.yn.vo.re.ResultVOUtil;

@Controller
@RequestMapping("/client/test")
public class TestController {

	
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	@Autowired
	SolarPanelSerice solarService;

	@Autowired
	BillOrderService orderService;
	
	@Autowired
   UserService userService;
	
	 @RequestMapping(value = "/dotest")
	 @ResponseBody
	    public Object  newTest() {
		
		Boolean istrue = PhoneFormatCheckUtils.isPhoneLegal("13613027433");
		
		if(!istrue){
			logger.info(" ----- ----- ----- 请输入正确的电话号码");
			return ResultVOUtil.error(777, "请输入正确的电话号码");
		}
		   
			return ResultVOUtil.success(null);
	    }
	
	 @RequestMapping(value = "/dotest02")
	 @ResponseBody
	    public Object someTest() {
		 String outTradeNo="2017071014160552761"; 
	
		BillOrder order = orderService.findByTradeNo(outTradeNo);
		

			return ResultVOUtil.success(order);
	    }
	 
	 /* Server server = new Server();
	 server.setServerCityText("深圳市");
	 server.setServerCityIds("199");
	 
	 Pageable pageable = new PageRequest(0, 20, Direction.DESC, "id");
	
Page<Server> page =	serverService.findAll(server, pageable);

System.out.println(page.getTotalPages());

List<Server> list = page.getContent();
Set<NewServerPlan> doset =  new  HashSet<NewServerPlan>();

for (Server server2 : list) {
	
	Set<NewServerPlan> set = server2.getNewServerPlan();
	

	int i=0;
	 for (NewServerPlan newServerPlan : set) {
		 if(i==0){
			 doset.add(newServerPlan);
			 server2.setNewServerPlan(null);
			 server2.setNewServerPlan(doset);
			 System.out.println(newServerPlan.getId()+" -- -- "+newServerPlan.getInverterId());
			 break;
		 }
	}

}*/

}

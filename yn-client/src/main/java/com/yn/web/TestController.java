package com.yn.web;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.model.Apolegamy;
import com.yn.service.ApolegamyOrderService;
import com.yn.service.ApolegamyService;
import com.yn.service.BillOrderService;
import com.yn.service.OrderService;
import com.yn.service.StationService;
import com.yn.vo.re.ResultVOUtil;

@Controller
@RequestMapping("/client/test")
public class TestController {

	
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	@Autowired
	StationService stationService;
	
	@Autowired
	BillOrderService orderService;
	
	@Autowired
	OrderService ordService;
	@Autowired
	ApolegamyOrderService APOservice;

	 @Autowired
	ApolegamyService apolegamyService;
	 @ResponseBody
	 @RequestMapping(value = "/dotest")
	    public Object  newTest(HttpSession session) {
		 
		 String ids ="0";
		 List<Long> listids =APOservice.Transformation(ids);
		 
		 List<Apolegamy> list = apolegamyService.findAll(listids);
		 Double apoPrice = 0.0;
		 
			for (Apolegamy apolegamy : list) {
				apoPrice += apolegamy.getPrice();
			}
		 
			return ResultVOUtil.success(list);
	    }
	
	 @RequestMapping(value = "/dotest02")
	 @ResponseBody
	    public Object someTest(HttpSession session) {
		 
		 List<Long> list =new ArrayList<Long>();
			
			Object  object= session.getAttribute("list");
			if(object instanceof Integer){
				
				Integer num01	=(Integer)object;

			}else if(object instanceof List){
			
				list =(List<Long>)object;
			}

			return ResultVOUtil.success(null);
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

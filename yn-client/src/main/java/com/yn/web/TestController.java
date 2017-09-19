package com.yn.web;

import java.math.BigDecimal;
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

import com.yn.kftService.PyOrderService;
import com.yn.model.Order;
import com.yn.model.Wallet;
import com.yn.service.ApolegamyOrderService;
import com.yn.service.ApolegamyService;
import com.yn.service.BillOrderService;
import com.yn.service.OrderService;
import com.yn.service.StationService;
import com.yn.service.WalletService;
import com.yn.utils.Constant;
import com.yn.vo.BillOrderVo;
import com.yn.vo.re.ResultVOUtil;

@Controller
@RequestMapping("/client/test")
public class TestController {

	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private BillOrderService billorderService;
	@Autowired 
	PyOrderService pyOrderService;
	@Autowired
	OrderService orderService;
	
	 @ResponseBody
	 @RequestMapping(value = "/dotest")
	    public String  newTest(HttpSession session) {
		 
		System.out.println("进入页面！");
		 
			return "NewFile";
	    }
	
	 @RequestMapping(value = "/dotest02")
	 @ResponseBody
	    public Object someTest(HttpSession session) {
		 
		 String orderCode =	"zoti170918161576";
		 
		  logger.info("---- ---- ---- ------ ----- 保存的订单号为："+orderCode);
		  Order orderSize =orderService.finByOrderCode(orderCode);
		  if(null == orderSize){
			  System.out.println("对象为空");
			  return ResultVOUtil.success("对象为空");
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

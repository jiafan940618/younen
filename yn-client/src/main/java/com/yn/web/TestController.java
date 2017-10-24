package com.yn.web;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yn.model.BankCard;
import com.yn.model.BillOrder;
import com.yn.model.Page;
import com.yn.model.Recharge;
import com.yn.model.TransactionRecord;
import com.yn.model.Wallet;
import com.yn.service.BankCardService;
import com.yn.service.BillOrderService;
import com.yn.service.BillWithdrawalsService;
import com.yn.service.ConstructionService;
import com.yn.service.NewServerPlanService;
import com.yn.service.OrderService;
import com.yn.service.ServerService;
import com.yn.service.StationService;
import com.yn.service.TransactionRecordService;
import com.yn.service.WalletService;
import com.yn.service.kftService.RechargeService;
import com.yn.vo.RechargeVo;
import com.yn.vo.re.ResultVOUtil;

@Controller
@RequestMapping("/client/test")
public class TestController {

	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	ConstructionService constructionService;
	@Autowired
	TransactionRecordService transactionRecordService;
	@Autowired
	private RechargeService rechargeService;
	@Autowired
	WalletService  walletService;
	@Autowired
	BillOrderService  billOrderService;
	
	@Autowired
	BankCardService bankCardService;
	@Autowired
	StationService  stationService;
	
	@Autowired
	OrderService orderService;
	@Autowired
	NewServerPlanService newserverPlanService;
	@Autowired
	BillWithdrawalsService  billWithdrawalsService;
	
	
	
	       @RequestMapping("/dotest") 
	       @ResponseBody
	       public Object helloJsp01(){  
	    	   
	    	   Page<TransactionRecord> page = new Page<TransactionRecord>();
	    	   page.setUserId(2L);
	    	   
	    
	    	    int total =	transactionRecordService.FindBynewNum(page);
	    	   

	            return ResultVOUtil.success(total);  
	       } 
	       
	       @ResponseBody
	       @RequestMapping("/test")  
	       public Object helloJsp001(RechargeVo rechargeVo){
	    	
	    	   
	              return ResultVOUtil.success(null);  
	       } 
	         
	       
	       
	       
	
	 @RequestMapping(value = "/dotest02")
	 @ResponseBody
	    public Object someTest(HttpSession session) {
		 
		System.out.println("进入测试!");

			return ResultVOUtil.success(null);
	    }
	
}

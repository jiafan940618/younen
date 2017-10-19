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

import com.yn.model.BillOrder;
import com.yn.model.BillWithdrawals;
import com.yn.model.Page;
import com.yn.model.Recharge;
import com.yn.model.Station;
import com.yn.model.TransactionRecord;
import com.yn.model.Wallet;
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
import com.yn.vo.StationVo;
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
	ServerService  serverService;
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
	    
	    	   BillOrder   billOrder = new BillOrder();
	    	    billOrder.setOrderId(556l);
				billOrder.setUserId(2l);
				billOrder.setMoney(0.01);
				billOrder.setTradeNo("eogn171019198341");
		    	billOrder.setPayWay(4);
		    	billOrder.setStatus(1);
		    	billOrder.setDel(0);
		    	
		    	billOrderService.save(billOrder);
	    	  
	    	   

	            return ResultVOUtil.success(null);  
	       } 
	       
	       @ResponseBody
	       @RequestMapping("/test")  
	       public Object helloJsp001(RechargeVo rechargeVo){
	    	   BigDecimal Money  = new BigDecimal(1000.0);
	    	   
	    	 
	    	   
	    	   Recharge recharge = new Recharge();
				recharge.setWalletId(1L);
				recharge.setMoney(Money.doubleValue());
				recharge.setRechargeCode(serverService.getOrderCode(1l));
				recharge.setPayWay(4);
				recharge.setDel(0);
				recharge.setStatus(1);
				rechargeService.save(recharge);
				
				logger.info("---- ----- ----- ---- RechargeCode "+recharge.getRechargeCode());
				logger.info("---- ----- ----- ---- Money "+recharge.getMoney());
				
				Recharge NEWrecharge = new Recharge();
				NEWrecharge.setRechargeCode(recharge.getRechargeCode());
				NEWrecharge.setStatus(0);
            	
            	rechargeService.updateRecharge(NEWrecharge);
            	
            	/** 根据订单号查询金额 */
            	RechargeVo NEWrechargeVo = rechargeService.findRecharge(NEWrecharge);
            	
            	BigDecimal addMoney = NEWrechargeVo.getMoney().add(NEWrechargeVo.getTotalmoney());
            	
            	 /** 在钱包哪里添加充值订单号*/
            	Wallet wallet = new Wallet();
            	wallet.setMoney(addMoney);
            	wallet.setId(NEWrechargeVo.getWalletId());
            	 /** 修改用户的钱包金额*/	                	
            	walletService.updatePrice(wallet);
	    	  
	    	   
	              return ResultVOUtil.success(null);  
	       } 
	         
	       
	       
	       
	
	 @RequestMapping(value = "/dotest02")
	 @ResponseBody
	    public Object someTest(HttpSession session) {
		 
		System.out.println("进入测试!");

			return ResultVOUtil.success(null);
	    }
	
}

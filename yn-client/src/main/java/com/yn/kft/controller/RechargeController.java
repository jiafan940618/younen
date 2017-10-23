package com.yn.kft.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.model.BillOrder;
import com.yn.model.Recharge;
import com.yn.model.Wallet;
import com.yn.service.BankCardService;
import com.yn.service.BillOrderService;
import com.yn.service.OrderService;
import com.yn.service.ServerService;
import com.yn.service.TransactionRecordService;
import com.yn.service.WalletService;
import com.yn.service.kftService.RechargeService;
import com.yn.service.kftService.SignService;
import com.yn.utils.CashierSignUtil;
import com.yn.utils.Constant;
import com.yn.vo.RechargeVo;
import com.yn.vo.re.ResultVOUtil;

/**
 * 充值的接口,并添加充值记录表recharge
 * 
 * */
	

@RestController
@RequestMapping(value="/client/recharge")
public class RechargeController {
	private static final Logger logger = LoggerFactory.getLogger(RechargeController.class);
	@Autowired
	TransactionRecordService transactionRecordService;
	@Autowired
	private ServerService serverService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private BillOrderService billorderService;
	@Autowired
	private RechargeService rechargeService;
	@Autowired
	private SignService signService;
	@Autowired
	BankCardService bankCardService;
	@Autowired
	WalletService walletService;

	 /** pc端*/
		@ResponseBody
		@RequestMapping(value="/rechargeOnline")
		/** 传过来的参数为 payWay,userId,money*/
		public Object doOnline(HttpServletRequest request,HttpSession session,RechargeVo rechargeVo){

			/*** [支付方式]{0:手动录入,1:余额支付,2:微信,3:支付宝,4:银联,5:快付通}'*/
			/*rechargeVo.setUserId(1L);
			rechargeVo.setPayWay(2);
			rechargeVo.setMoney(new BigDecimal("4920"));*/

			Wallet wallet = walletService.findWalletByUser(rechargeVo.getUserId());
			
			rechargeVo.setWalletId(wallet.getId());
			
			/** 手机端是微信app支付*/  /** wxApp*/
			/** 手机端是支付宝app支付*/  /** alipayApp*/
			rechargeVo.setRechargeCode(serverService.getOrderCode(rechargeVo.getWalletId()));
			
			logger.info("--- ---- ---- ---- ----- ---- --- 支付的类型："+rechargeVo.getPayWay());
			logger.info("--- ---- ---- --- --- -- --  传递的订单号为："+rechargeVo.getRechargeCode());
			logger.info("--- ---- ---- --- --- -- --  传递的金额为："+rechargeVo.getMoney());


			session.setAttribute("rechargeCode", rechargeVo.getRechargeCode());
		
			 if(rechargeVo.getPayWay()==3 || rechargeVo.getPayWay()==2  ){
				
				BigDecimal xmoney = BigDecimal.valueOf(100);
				DecimalFormat   df   =new DecimalFormat("#");
				
				System.out.println(df.format(rechargeVo.getMoney().multiply(xmoney)));
				rechargeVo.setMoney(new BigDecimal(df.format(rechargeVo.getMoney().multiply(xmoney))));
				logger.info("--- ---- ---- ---- ----- ---- --- 进入方法->：");
					
				try {
					rechargeService.init();
				} catch (Exception e) {
					e.printStackTrace();
				}

				return rechargeService.getMap(request, rechargeVo);
				 
			}else if(rechargeVo.getPayWay()==4){//等于4是银联支付
				
				BigDecimal xmoney = BigDecimal.valueOf(100);
				DecimalFormat   df   =new DecimalFormat("#");
				
				System.out.println(df.format(rechargeVo.getMoney().multiply(xmoney)));
				rechargeVo.setMoney(new BigDecimal(df.format(rechargeVo.getMoney().multiply(xmoney))));
				
				return rechargeService.findSign(rechargeVo); 
			}

			return ResultVOUtil.error(777, Constant.PAY_WAY_NULL);
		}
	
		/** 接收快付通响应返回结果的接口*/
		@ResponseBody
		@RequestMapping(value="/doresult")
		public Object getSignresult(HttpServletRequest request,HttpServletResponse response){
			
			 Map<String, Object> resultMap = new HashMap<String, Object>();
			logger.info("=============================================================================");
			logger.info("进入测试响应后台contrller ----- ------ --- --- --- ----- !");
			
			logger.info("传过来的sign为：----- ----- ----- ------"+request.getParameter("signatureInfo"));
			logger.info("传过来的orderNo为：----- ----- ----- ------"+request.getParameter("orderNo"));
			logger.info("传过来的status为：----- ----- ----- ------"+request.getParameter("status"));
			logger.info("传过来的settlementAmount为：----- ----- ----- ------"+request.getParameter("settlementAmount"));
			logger.info("传过来的language为：----- ----- ----- ------"+request.getParameter("language"));
			logger.info("传过来的callerIp为：----- ----- ----- ------"+request.getParameter("callerIp"));
			logger.info("传过来的signatureAlgorithm为：----- ----- ----- ------"+request.getParameter("signatureAlgorithm"));
			logger.info("传过来的errorCode为：----- ----- ----- ------"+request.getParameter("errorCode"));
			logger.info("传过来的failureDetails为：----- ----- ----- ------"+request.getParameter("failureDetails"));
			logger.info("传过来的channelNo为：----- ----- ----- ------"+request.getParameter("channelNo"));
			logger.info("传过来的statusDesc为：----- ----- ----- ------"+request.getParameter("reconStatus"));
			
			 Map<String, String> map = new HashMap<String, String>();
			 
			 map.put("settlementAmount", request.getParameter("settlementAmount"));
			/* map.put("signatureInfo", request.getParameter("signatureInfo"));*/
			 map.put("orderNo", request.getParameter("orderNo"));
			 map.put("status", request.getParameter("status"));
			 map.put("language", request.getParameter("language"));
			/* map.put("signatureAlgorithm", request.getParameter("signatureAlgorithm"));*/
			 map.put("channelNo", request.getParameter("channelNo"));
			/* map.put("failureDetails", request.getParameter("failureDetails"));
			 map.put("errorCode", request.getParameter("errorCode"));*/
			 
			 map.put("callerIp", request.getParameter("callerIp"));
			 map.put("reconStatus", request.getParameter("reconStatus"));
			
			 String orderNo =(String) request.getParameter("orderNo");

             String status =(String)request.getParameter("status");
             String amount =(String) request.getParameter("settlementAmount");
             String failureDetails = request.getParameter("failureDetails");	
             String errorCode = request.getParameter("errorCode");
             String pfxPath=null;
 			try {
 				 File directory = new File("");// 参数为空
 				pfxPath = directory.getCanonicalPath()+"/privateKey/20KFT.cer";
 				 System.out.println("项目路径为：-- --- -- -- - - - - - -"+pfxPath);
 			} catch (IOException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
            
           
             Boolean result = CashierSignUtil.verifySign_2(pfxPath, map, request.getParameter("signatureInfo"));
	   
		               if(result){
		                		
		                		if(request.getParameter("status").equals("1")){
				                	logger.info("-- --- ---- -- --- - - - - - - - - --订单号为： "+orderNo);
				                	logger.info("-- --- ---- -- --- - - - - - - - - --金额为： "+amount);
				                	
				                	Recharge recharge = new Recharge();
				                	recharge.setRechargeCode(orderNo);
				                	recharge.setStatus(0);
				                	
				                	rechargeService.updateRecharge(recharge);
				                	
				                	/** 根据订单号查询金额 */
				                	RechargeVo rechargeVo = rechargeService.findRecharge(recharge);
				                	
				                	BigDecimal addMoney = rechargeVo.getMoney().add(rechargeVo.getTotalmoney());
				                	
				                	 /** 在钱包哪里添加充值订单号*/
				                	Wallet wallet = new Wallet();
				                	wallet.setMoney(addMoney);
				                	wallet.setId(rechargeVo.getWalletId());
				                	 /** 修改用户的钱包金额*/	                	
				                	walletService.updatePrice(wallet);
	
				                	transactionRecordService.InsertBillAll(recharge);
				                	
				                	return "SUCCESS";
				                	
			                	}else if(status.equals("2")){
			                		
			                		Recharge recharge =rechargeService.findByRecode(orderNo);
			                		recharge.setRemark(errorCode+":"+failureDetails);
			                		rechargeService.save(recharge);
			                		
			                		transactionRecordService.InsertBillAll(recharge);
			                		
			                		return ResultVOUtil.error(777, "抱歉,充值失败,详请咨询客服!");
			                	}else if(status.equals("3")){
			                		Recharge recharge =rechargeService.findByRecode(orderNo);
			                		recharge.setRemark(errorCode+":"+failureDetails);
			                		rechargeService.save(recharge);
			                		
			                		transactionRecordService.InsertBillAll(recharge);
			                		
			                		return ResultVOUtil.error(777, "抱歉,充值失败,详请咨询客服!");
			                	}else if(status.equals("4")){
			                		Recharge recharge =rechargeService.findByRecode(orderNo);
			                		recharge.setRemark(errorCode+":"+failureDetails);
			                		rechargeService.save(recharge);
			                		
			                		transactionRecordService.InsertBillAll(recharge);
			                		return ResultVOUtil.error(777, "抱歉,充值失败,详请咨询客服!");
			                	}else if(status.equals("5")){
			                		Recharge recharge =rechargeService.findByRecode(orderNo);
			                		recharge.setRemark(errorCode+":"+failureDetails);
			                		rechargeService.save(recharge);
			                		
			                		transactionRecordService.InsertBillAll(recharge);
			                		return ResultVOUtil.error(777, "抱歉,充值失败,详请咨询客服!");
			                	}else if(status.equals("6")){
			                		Recharge recharge =rechargeService.findByRecode(orderNo);
			                		recharge.setRemark(errorCode+":"+failureDetails);
			                		rechargeService.save(recharge);
			                		
			                		transactionRecordService.InsertBillAll(recharge);
			                		return ResultVOUtil.error(777, "抱歉,充值失败,详请咨询客服!");
			                }else{
			                	Recharge recharge =rechargeService.findByRecode(orderNo);
		                		recharge.setRemark(errorCode+":"+failureDetails);
		                		rechargeService.save(recharge);
		                		transactionRecordService.InsertBillAll(recharge);
		                		
			                		return ResultVOUtil.error(777, "抱歉,充值失败,详请咨询客服!");
			                	}
		               }else{
		            	   Recharge recharge =rechargeService.findByRecode(orderNo);
	                		recharge.setRemark(errorCode+":"+failureDetails);
	                		rechargeService.save(recharge);
		            	   
	                		transactionRecordService.InsertBillAll(recharge);
	                		
		            	   logger.info("====================== ================== 验签失败!");
		            	   return ResultVOUtil.error(777, "充值未成功!");
		               }   		
			          

		}
	
		/** 定时查询订单号*/
		@ResponseBody
		@RequestMapping(value="/selectSta")
		public Object getorderCode(HttpSession session){
			String tradeNo =(String) session.getAttribute("rechargeCode");
			Map<String, Integer> map =  new HashMap<String, Integer>();
			logger.info("拿到的订单号为：--- ---- -- - -- - - -- - - - -"+tradeNo);
			
			Recharge recharge =rechargeService.findByRecode(tradeNo);
			
			if(null != recharge){

				map.put("status", recharge.getStatus());
			}
			return	ResultVOUtil.success(map);
		}
		
		/** 银联响应接口*/
		@ResponseBody
		@RequestMapping(value="/doSucRep")
		public Object getBoby(HttpServletRequest request) throws IOException{

			 Map<String, String> resultMap = new HashMap<String, String>();
			System.out.println(" ==== ==== ===============================================================================");
			System.out.println("---------- ------ -- ----- 进入后台响应");
			 File directory = new File("");// 参数为空
			 String pfxPath=null;
			try {
				pfxPath = directory.getCanonicalPath()+"/privateKey/20KFT.cer";
				 System.out.println("项目路径为：-- --- -- -- - - - - - -"+pfxPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
			 
			 
			String orderNo = request.getParameter("orderNo");
			String amount = request.getParameter("amount");
			logger.info("-- --- ---- -- --- - - - - - - - - --订单号为： "+orderNo);
        	logger.info("-- --- ---- -- --- - - - - - - - - --金额为： "+amount); 
        	logger.info("-- --- ---- -- --- - - - - - - - - --status为： "+request.getParameter("status")); 
        	logger.info("-- --- ---- -- --- - - - - - - - - --statusDesc为： "+request.getParameter("statusDesc")); 
        	logger.info("-- --- ---- -- --- - - - - - - - - --callerIp为： "+request.getParameter("callerIp")); 
        	logger.info("-- --- ---- -- --- - - - - - - - - --language为： "+ request.getParameter("language")); 
        	if(request.getParameter("status").equals("1")){
        	
			 Map<String, String> parameters = new HashMap<String, String>();
				parameters.put("amount", request.getParameter("amount"));
				parameters.put("orderNo", request.getParameter("orderNo"));
				parameters.put("status", request.getParameter("status"));
				parameters.put("statusDesc",request.getParameter("statusDesc"));
				parameters.put("callerIp", request.getParameter("callerIp"));
				parameters.put("language", request.getParameter("language"));
				
			//	BillOrder billOrder =  billorderService.findByTradeNoandstatus(request.getParameter("orderNo"));
				boolean verify_sign=CashierSignUtil.verifySign_2(pfxPath, parameters, request.getParameter("signatureInfo"));
				if(verify_sign){
					
					Recharge recharge = new Recharge();
                	recharge.setRechargeCode(orderNo);
                	recharge.setStatus(0);
                	
                	rechargeService.updateRecharge(recharge);
                	
                	/** 根据订单号查询金额 */
                	RechargeVo rechargeVo = rechargeService.findRecharge(recharge);
                	
                	BigDecimal addMoney = rechargeVo.getMoney().add(rechargeVo.getTotalmoney());
                	
                	 /** 在钱包哪里添加充值订单号*/
                	Wallet wallet = new Wallet();
                	wallet.setMoney(addMoney);
                	wallet.setId(rechargeVo.getWalletId());
                	 /** 修改用户的钱包金额*/	                	
                	walletService.updatePrice(wallet);
					
                	recharge =	rechargeService.findByRecode(orderNo);
                	transactionRecordService.InsertBillAll(recharge);
                	
                	resultMap.put("status", "1");
                	resultMap.put("message", "");
                	resultMap.put("orderNo",orderNo);

                	return resultMap;
				}
        	}
        		logger.info("---- --------- ------------ -------- "+request.getParameter("message"));
        		resultMap.put("message","充值失败!"+ request.getParameter("message"));
        		
        		Recharge recharge =rechargeService.findByRecode(orderNo);
        		recharge.setRemark(request.getParameter("message"));
        		rechargeService.save(recharge);
        		
     
            	transactionRecordService.InsertBillAll(recharge);
        		
        		
			System.out.println("---------- ------ -- ----- 结束后台响应");
			System.out.println(" ==== ==== ===============================================================================");
			
			return resultMap;
		}
	
	
	

}

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
import com.yn.service.BillOrderService;
import com.yn.service.BillWithdrawalsService;
import com.yn.service.OrderService;
import com.yn.service.ServerService;
import com.yn.service.TransactionRecordService;
import com.yn.service.kftService.KFTpayService;
import com.yn.service.kftService.PyOrderService;
import com.yn.service.kftService.SignService;
import com.yn.utils.CashierSignUtil;
import com.yn.utils.Constant;
import com.yn.vo.BillOrderVo;
import com.yn.vo.BillWithdrawalsVo;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping(value="/client/sign")
public class SignController {
	
	private static final Logger logger = LoggerFactory.getLogger(SignController.class);
	@Autowired
	private ServerService serverService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private BillOrderService billorderService;
	@Autowired
	private PyOrderService pyOrderService;
	@Autowired
	BillWithdrawalsService billWithdrawalsService;
	@Autowired
	TransactionRecordService transactionRecordService;
	@Autowired
	KFTpayService kFTpayService;
	@Autowired
	SignService signService;

	
		//http://2e93431d.ngrok.io/client/sign/payonline
       //http://localhost/younen/html/project/online_apply.html
	 /** pc端*/
		@ResponseBody
		@RequestMapping(value="/payonline")
		/** 传过来的参数为 payWay,channel,userId,balancePrice,money*/
		public Object doOnline(HttpServletRequest request,HttpSession session,BillOrderVo billOrderVo){
			/** pc端支付宝支付为二维码支付*/ /** alipayQR*/
			/** pc端微信支付为二维码支付*/  /** wxPubQR*/
			/*** [支付方式]{0:手动录入,1:余额支付,2:微信,3:支付宝,4:银联,5:快付通}'*/

			/*billOrderVo.setMoney(new BigDecimal("0.01"));
			billOrderVo.setPayWay(4);
			billOrderVo.setUserId(3L);
			billOrderVo.setOrderId(1l);*/
			/** 手机端是微信app支付*/  /** wxApp*/
			/** 手机端是支付宝app支付*/  /** alipayApp*/
			billOrderVo.setTradeNo(serverService.getOrderCode(billOrderVo.getUserId()));
			
			
			logger.info("--- ---- ---- ---- ----- ---- --- 支付的类型："+billOrderVo.getPayWay());
			logger.info("--- ---- ---- --- --- -- --  传递的订单号为："+billOrderVo.getTradeNo());
			logger.info("--- ---- ---- --- --- -- --  传递的金额为："+billOrderVo.getMoney());

			/*String description = billOrderVo.getOrderId().toString()+","+billOrderVo.getUserId();
		
			billOrderVo.setDescription(description);*/


			session.setAttribute("tradeNo", billOrderVo.getTradeNo());
			 //等于1是余额支付
			if(billOrderVo.getPayWay()==1){
				logger.info("--- ---- ---- ---- ----- ---- --- 进入方法->1：");
				return pyOrderService.payBalance(billOrderVo);
				 //等于3是支付宝支付//等于2是微信支付	
			}else if(billOrderVo.getPayWay()==3 || billOrderVo.getPayWay()==2){
				
				BigDecimal xmoney = BigDecimal.valueOf(100);
				DecimalFormat   df   =new DecimalFormat("#");
				
				System.out.println(df.format(billOrderVo.getMoney().multiply(xmoney)));
				billOrderVo.setMoney(new BigDecimal(df.format(billOrderVo.getMoney().multiply(xmoney))));
				logger.info("--- ---- ---- ---- ----- ---- --- 进入方法->2：");
					
				try {
					pyOrderService.init();
				} catch (Exception e) {
					e.printStackTrace();
				}

				return pyOrderService.getMap(request, billOrderVo);
				 
			}else if(billOrderVo.getPayWay()==4){//等于4是银联支付
				logger.info("--- ---- ---- ---- ----- ---- --- 进入方法->4：");
				BigDecimal xmoney = BigDecimal.valueOf(100);
				DecimalFormat   df   =new DecimalFormat("#");
				
				System.out.println(df.format(billOrderVo.getMoney().multiply(xmoney)));
				billOrderVo.setMoney(new BigDecimal(df.format(billOrderVo.getMoney().multiply(xmoney))));
				
				
				BillOrder billOrder = new BillOrder();
				
				System.out.println("----- --- ----- ------- ---getOrderId :"+billOrderVo.getOrderId());
				System.out.println("----- --- ----- ------- ---getUserId: "+billOrderVo.getUserId());
				System.out.println("----- --- ----- ------- ---getMoney: "+billOrderVo.getMoney().doubleValue()/100);
				System.out.println("----- --- ----- ------- ---getTradeNo: "+billOrderVo.getTradeNo());
				System.out.println("----- --- ----- ------- ---getOrderId: "+billOrderVo.getOrderId());
				System.out.println("----- --- ----- ------- ---getPayWay: "+billOrderVo.getPayWay());
				
					
					billOrder.setOrderId(billOrderVo.getOrderId());
					billOrder.setUserId(billOrderVo.getUserId());
					billOrder.setMoney(billOrderVo.getMoney().doubleValue()/100);
					billOrder.setTradeNo(billOrderVo.getTradeNo());
			    	billOrder.setPayWay(billOrderVo.getPayWay());
			    	billOrder.setStatus(1);
			    	//billOrder.setDel(0);
			    	
			    	billorderService.save(billOrder);
				
				return signService.findSign(billOrderVo); 
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
		                		
		                		if(status.equals("1")){
				                	logger.info("-- --- ---- -- --- - - - - - - - - --订单号为： "+orderNo);
				                	logger.info("-- --- ---- -- --- - - - - - - - - --金额为： "+amount);
				                	
				                	/** 修改订单记录状态*/
				                	billorderService.updateOrder(orderNo);
				                	/** 修改订单金额,及3步走，支付状态*/
				                	orderService.UpdateOrStatus(orderNo,Double.valueOf(amount)/100 );
				                	
				                	BillOrder billOrder =  billorderService.findByTradeNoandstatus(orderNo);
				                	
				                	transactionRecordService.InsertBillAll(billOrder);
				                	 /** 不在这里修改状态*/
				                //orderService.givePrice(orderService.FindByTradeNo(orderNo));
				                	
				                	logger.info("---- ----- ------ ---- 添加订单记录结束 0002");

				                	return "SUCCESS";
				                	
			                	}else if(resultMap.get("status").equals("2")){
			                		BillOrder billOrder =  billorderService.findByTradeNoandstatus(orderNo);
			                		billOrder.setRemark(errorCode+":"+failureDetails);
			                		billorderService.save(billOrder);
				                	transactionRecordService.InsertBillAll(billOrder);
			                		
			                		return ResultVOUtil.error(777, "抱歉,支付失败，详情请咨询客服!");
			                	}else if(resultMap.get("status").equals("3")){
			                	
				                	
			                		BillOrder billOrder =  billorderService.findByTradeNoandstatus(orderNo);
			                		billOrder.setRemark(errorCode+":"+failureDetails);
			                		billorderService.save(billOrder);
			                		transactionRecordService.InsertBillAll(billOrder);
			                		return ResultVOUtil.error(777, "抱歉,支付失败，详情请咨询客服!");
			                	}else if(resultMap.get("status").equals("4")){
			                		BillOrder billOrder =  billorderService.findByTradeNoandstatus(orderNo);
			                		billOrder.setRemark(errorCode+":"+failureDetails);
			                		billorderService.save(billOrder);
			                		transactionRecordService.InsertBillAll(billOrder);
			                		return ResultVOUtil.error(777, "抱歉,支付失败，详情请咨询客服!");
			                	}else if(resultMap.get("status").equals("5")){
			                		BillOrder billOrder =  billorderService.findByTradeNoandstatus(orderNo);
			                		billOrder.setRemark(errorCode+":"+failureDetails);
			                		billorderService.save(billOrder);
			                		transactionRecordService.InsertBillAll(billOrder);
			                		return ResultVOUtil.error(777, "抱歉,支付失败，详情请咨询客服!");
			                	}else if(resultMap.get("status").equals("6")){
			                		BillOrder billOrder =  billorderService.findByTradeNoandstatus(orderNo);
			                		billOrder.setRemark(errorCode+":"+failureDetails);
			                		billorderService.save(billOrder);
			                		transactionRecordService.InsertBillAll(billOrder);
			                		return ResultVOUtil.error(777, "抱歉,支付失败，详情请咨询客服!");
			                }else{
			                	BillOrder billOrder =  billorderService.findByTradeNoandstatus(orderNo);
		                		billOrder.setRemark(errorCode+":"+failureDetails);
		                		billorderService.save(billOrder);
		                		transactionRecordService.InsertBillAll(billOrder);
			                		return ResultVOUtil.error(777, "抱歉,支付失败，详情请咨询客服!");
			                	}
		               }else{
		            	   BillOrder billOrder =  billorderService.findByTradeNoandstatus(orderNo);
	                		billOrder.setRemark(errorCode+":"+failureDetails);
	                		billorderService.save(billOrder);
	                		transactionRecordService.InsertBillAll(billOrder);
		            	   logger.info("====================== ================== 验签失败!");
		            	   return ResultVOUtil.error(777, "支付未成功!");
		               }   		
			          

		}
	
		/** 定时查询订单号*/
		@ResponseBody
		@RequestMapping(value="/selectSta")
		public Object getorderCode(HttpSession session){
			String tradeNo =(String) session.getAttribute("tradeNo");
			Map<String, Integer> map =  new HashMap<String, Integer>();
			logger.info("拿到的订单号为：--- ---- -- - -- - - -- - - - -"+tradeNo);
			
			BillOrder billOrder = billorderService.findByTradeNo(tradeNo);
			
			if(null != billOrder){

				map.put("status", billOrder.getStatus());
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
				
		
				boolean verify_sign=CashierSignUtil.verifySign_2(pfxPath, parameters, request.getParameter("signatureInfo"));
				if(verify_sign){
					
					logger.info("-- --- ---- -- --- - - - - - - - - --订单号为： "+orderNo);
                	logger.info("-- --- ---- -- --- - - - - - - - - --金额为： "+amount);
                	
                	/** 修改订单记录状态*/
                	billorderService.updateOrder(orderNo);
                	/** */
                	orderService.UpdateOrStatus(orderNo,Double.valueOf(amount)*0.01 );

                	BillOrder billOrder =  billorderService.findByTradeNoandstatus(orderNo);
                	
                	transactionRecordService.InsertBillAll(billOrder);
                	 /** 不在这里修改状态*/
                	//orderService.givePrice(orderService.FindByTradeNo(orderNo));
                	
                	logger.info("---- ----- ------ ---- 添加订单记录结束 0002");
                	resultMap.put("status", "1");
                	resultMap.put("message", "");
                	resultMap.put("orderNo",orderNo);

                	return resultMap;
				}
        	}
        		logger.info("---- --------- ------------ -------- "+request.getParameter("message"));
        		
        		 BillOrder billOrder =  billorderService.findByTradeNoandstatus(orderNo);
         		 billOrder.setRemark(request.getParameter("message"));
         		 billorderService.save(billOrder);
        		
         		transactionRecordService.InsertBillAll(billOrder);
        		resultMap.put("message","支付失败!"+ request.getParameter("message"));
        		
			System.out.println("---------- ------ -- ----- 结束后台响应");
			System.out.println(" ==== ==== ===============================================================================");
			
			return resultMap;
		}
		
		/*** 提现的接口 */
	  @ResponseBody
		@RequestMapping(value="/bankRefund")
		public  Object getRefund(BillWithdrawalsVo billWithdrawalsVo){
			//666640755.96
		  //21000000000773
		 /* billWithdrawalsVo.setTreatyId("21000000000773");
		  billWithdrawalsVo.setMoney(10000.0);*/
		  
		 // billWithdrawalsVo.getUserId();
		  
	      Double money = billWithdrawalsVo.getMoney();
	  	  logger.info("---- --------- ------------ -------- 金额："+money);
	  
		  logger.info("---- --------- ------------ -------- 协议号："+billWithdrawalsVo.getTreatyId());
		  
		  BillWithdrawalsVo   billWithdrawals =billWithdrawalsService.selWithdrawal(billWithdrawalsVo.getTreatyId());
		  
		  if(billWithdrawals.getMoney() - billWithdrawalsVo.getMoney() >= 0){
			  billWithdrawals.setMoney(money);

			  billWithdrawals.setTradeNo(serverService.getOrderCode(billWithdrawals.getUserId()));
		  }else{
			  
			  return ResultVOUtil.error(777, "抱歉,你的余额不足!");
		  }
		  
		  /** 将查询出来的行号替换成treatyType*/
		  
		 
		  
		  try {
			  kFTpayService.init();
			  
			kFTpayService.payToBankAccount(billWithdrawals);
			
			
		  }catch (Exception e) {
			
			e.printStackTrace();
		}
			
			return	ResultVOUtil.success(null);

		}
		
		
}

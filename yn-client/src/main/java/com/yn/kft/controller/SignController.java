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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.model.BillOrder;
import com.yn.service.BankCardService;
import com.yn.service.BillOrderService;
import com.yn.service.OrderService;
import com.yn.service.ServerService;
import com.yn.service.kftService.PyOrderService;
import com.yn.service.kftService.SignService;
import com.yn.utils.CashierSignUtil;
import com.yn.utils.Constant;

import com.yn.vo.BillOrderVo;
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
	private SignService signService;
	@Autowired
	BankCardService bankCardService;

	

	
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
			billOrderVo.setOrderId(1L);
			billOrderVo.setPayWay(4);
			billOrderVo.setUserId(3L);
		

			/** 手机端是微信app支付*/  /** wxApp*/
			/** 手机端是支付宝app支付*/  /** alipayApp*/
			billOrderVo.setTradeNo(serverService.getOrderCode(billOrderVo.getOrderId()));
			
			logger.info("--- ---- ---- ---- ----- ---- --- 支付的类型："+billOrderVo.getPayWay());
			logger.info("--- ---- ---- --- --- -- --  传递的订单号为："+billOrderVo.getTradeNo());
			logger.info("--- ---- ---- --- --- -- --  传递的金额为："+billOrderVo.getMoney());

			String description = billOrderVo.getOrderId().toString()+","+billOrderVo.getUserId();
		
			billOrderVo.setDescription(description);


			session.setAttribute("tradeNo", billOrderVo.getTradeNo());
			 //等于1是余额支付
			if(billOrderVo.getPayWay()==1){
				logger.info("--- ---- ---- ---- ----- ---- --- 进入方法->1：");
				return pyOrderService.payBalance(billOrderVo);
				 //等于3是支付宝支付	
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
				logger.info("--- ---- ---- ---- ----- ---- --- 进入方法->2："+billOrderVo.getChannel());
			

				return pyOrderService.getMap(request, billOrderVo);
				 //等于2是微信支付
			}else if(billOrderVo.getPayWay()==4){//等于4是银联支付
				logger.info("--- ---- ---- ---- ----- ---- --- 进入方法->4："+billOrderVo.getChannel());
				BigDecimal xmoney = BigDecimal.valueOf(100);
				DecimalFormat   df   =new DecimalFormat("#");
				
				System.out.println(df.format(billOrderVo.getMoney().multiply(xmoney)));
				billOrderVo.setMoney(new BigDecimal(df.format(billOrderVo.getMoney().multiply(xmoney))));
				
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
			//settlementAmount
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
				                	
				                	/** 修改订单记录状态*/
				                	billorderService.updateOrder(orderNo);
				                	/** 修改订单金额,及3步走，支付状态*/
				                	orderService.UpdateOrStatus(orderNo,Double.valueOf(amount) );
				                	
				                	BillOrder billOrder =  billorderService.findByTradeNoandstatus(orderNo);
				                	 /** 查询订单改变订单进度*/
				                	orderService.givePrice(orderService.FindByTradeNo(orderNo));
				                	
				                	logger.info("---- ----- ------ ---- 添加订单记录结束 0002");

				                	return "SUCCESS";
				                	
			                	}else if(resultMap.get("status").equals("2")){
			                		
			                		return ResultVOUtil.error(777, "支付未成功!");
			                	}else if(resultMap.get("status").equals("3")){
			                		BillOrder billOrder01 = new BillOrder();
				                	
				                	billOrder01.setStatus(2);
				                	billorderService.newsave(billOrder01);
			                		return ResultVOUtil.error(777, "已冲正!");
			                	}else if(resultMap.get("status").equals("4")){
			                		return ResultVOUtil.error(777, "订单已超时!");
			                	}else if(resultMap.get("status").equals("5")){
			                		return ResultVOUtil.error(777, "异常成功!");
			                	}else if(resultMap.get("status").equals("6")){
			                		return ResultVOUtil.error(777, "已撤销!");
			                }else{
			                		return ResultVOUtil.error(777, "支付失败!");
			                	}
		               }else{
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
				
				BillOrder billOrder =  billorderService.findByTradeNoandstatus(request.getParameter("orderNo"));
				boolean verify_sign=CashierSignUtil.verifySign_2(pfxPath, parameters, request.getParameter("signatureInfo"));
				if(verify_sign){
					
					logger.info("-- --- ---- -- --- - - - - - - - - --订单号为： "+orderNo);
                	logger.info("-- --- ---- -- --- - - - - - - - - --金额为： "+amount);
                	
                	/** 修改订单记录状态*/
                	billorderService.updateOrder(orderNo);
                	/** 修改订单金额,及3步走，支付状态*/
                	orderService.UpdateOrStatus(orderNo,Double.valueOf(amount) );

                	 /** 查询订单改变订单进度*/
                	orderService.givePrice(orderService.FindByTradeNo(orderNo));
                	
                	logger.info("---- ----- ------ ---- 添加订单记录结束 0002");
                	resultMap.put("status", "1");
                	resultMap.put("message", "");
                	resultMap.put("orderNo",orderNo);

                	return resultMap;
				}
        	}
        		logger.info("---- --------- ------------ -------- "+request.getParameter("message"));
        		resultMap.put("message","支付失败!"+ request.getParameter("message"));
        		
			System.out.println("---------- ------ -- ----- 结束后台响应");
			System.out.println(" ==== ==== ===============================================================================");
			
			return resultMap;
		}
		
		/*** 银联支付前端响应接口 */
		/*@ResponseBody
		@RequestMapping(value="/bankPay")
		public  Object getBank(HttpServletRequest request){
			logger.info("===== =========== ========== ====="+request.getParameter("status"));
			String tradeNo =(String) request.getParameter("orderNo");
			Map<String, Integer> map =  new HashMap<String, Integer>();
			logger.info("拿到的订单号为：--- ---- -- - -- - - -- - - - -"+tradeNo);
			
			BillOrder billOrder = billorderService.findByTradeNo(tradeNo);
			
			if(null != billOrder){
				logger.info("订单的状态为：--- ---- -- - -- - - -- - - - -"+billOrder.getStatus());

				map.put("status", billOrder.getStatus());
			}
			return	ResultVOUtil.success(map);

		}*/
		
		
}

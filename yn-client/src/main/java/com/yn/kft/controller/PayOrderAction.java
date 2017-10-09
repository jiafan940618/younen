package com.yn.kft.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
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
import com.alibaba.fastjson.JSON;
import com.yn.kftService.PyOrderService;
import com.yn.kftService.TestOrderService;
import com.yn.model.BillOrder;
import com.yn.service.BillOrderService;
import com.yn.service.OrderService;
import com.yn.service.ServerService;
import com.yn.utils.Constant;
import com.yn.utils.HttpsClientUtil;
import com.yn.utils.PropertyUtils;
import com.yn.utils.SignUtil;
import com.yn.vo.BillOrderVo;
import com.yn.vo.re.ResultVOUtil;

//http://3ad9d9c5.ngrok.io/client/payOrder/createPay
@Controller
@RequestMapping(value="/client/payOrder")
public class PayOrderAction {
	
	private static final Logger logger = LoggerFactory.getLogger(PayOrderAction.class);
	@Autowired
	private ServerService serverService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private BillOrderService billorderService;
	@Autowired
	private TestOrderService pyOrderService;

	private String key;
	private String requestUrl;
	private String mchId;
	
	public  PayOrderAction(){
		mchId = PropertyUtils.getProperty("mchId");
		key = PropertyUtils.getProperty("key");
		 requestUrl = PropertyUtils.getProperty("requestUrl");
	}

	//https://qr.alipay.com/bax006707avarcn0xceg00a9
	 //@RequestParam(value="outTradeNo") String outTradeNo,@RequestParam(value="channel") String channel
	 /** 参数传一个订单号查询数据最后填充至map集合*/
//	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping(value = "/createPay")
	public Object createPay(HttpServletRequest request,HttpSession session) throws Exception {  
		/** pc端支付宝支付为二维码支付*/ /** alipayQR*/
		/** pc端微信支付为二维码支付*/  /** wxPubQR*/
		/*** [支付方式]{0:手动录入,1:余额支付,2:微信,3:支付宝,4:银联,5:快付通}'*/
		BillOrderVo billOrderVo = new BillOrderVo();
		billOrderVo.setOrderId(1L);
		billOrderVo.setChannel("wxPubQR");
		billOrderVo.setPayWay(2);
		billOrderVo.setUserId(3L);
		BigDecimal money = BigDecimal.valueOf(0.01);
		billOrderVo.setMoney(money);
		billOrderVo.setKey(key);
		billOrderVo.setRequestUrl(requestUrl);
		billOrderVo.setMchId(mchId);
		/** 手机端是微信app支付*/  /** wxApp*/
		/** 手机端是支付宝app支付*/  /** alipayApp*/
		billOrderVo.setTradeNo(serverService.getOrderCode(billOrderVo.getOrderId()));
		logger.info("--- ---- ---- ---- ----- ---- --- requestUrl："+billOrderVo.getRequestUrl());
		logger.info("--- ---- ---- ---- ----- ---- --- mchId："+billOrderVo.getMchId());
		logger.info("--- ---- ---- ---- ----- ---- --- key："+billOrderVo.getKey());
		logger.info("--- ---- ---- ---- ----- ---- --- 支付的类型："+billOrderVo.getPayWay());
		logger.info("--- --- --- --- --- ---- --- 传递的支付方式为："+billOrderVo.getChannel());
		logger.info("--- ---- ---- --- --- -- --  传递的订单号为："+billOrderVo.getTradeNo());
		
		String description = billOrderVo.getOrderId().toString()+","+billOrderVo.getUserId();
	
		billOrderVo.setDescription(description);

		session.setAttribute("tradeNo", billOrderVo.getTradeNo());
		 //等于1是余额支付
		if(billOrderVo.getPayWay()==1){
			logger.info("--- ---- ---- ---- ----- ---- --- 进入方法1：");
			return pyOrderService.payBalance(billOrderVo);
			 //等于3是支付宝支付	
		}else if(billOrderVo.getPayWay()==3 || billOrderVo.getPayWay()==2){
			logger.info("--- ---- ---- ---- ----- ---- --- 进入方法2："+billOrderVo.getChannel());
		
			return pyOrderService.getMap(request, billOrderVo);
			 //等于2是微信支付
		}else if(billOrderVo.getPayWay()==4){//等于4是银联支付
			
			return ""; 
		}else if(billOrderVo.getPayWay()==5){//等于5是快付通支付
			
			return ""; 
		}

		return ResultVOUtil.error(777, Constant.PAY_WAY_NULL);
	}
	
	
	/** 接收快付通响应返回结果的接口*/
	@ResponseBody
	@RequestMapping(value="/doresult")
	public Object getSignresult(HttpServletRequest request){
		
		 Map<String, Object> resultMap = new HashMap<String, Object>();
		
	logger.info("=============================================================================");
		
		logger.info("进入测试响应后台contrller ----- ------ --- --- --- ----- !");
		
		 try {
	            InputStream inStream = request.getInputStream();
	            int _buffer_size = 1024;
	            
	            if (inStream != null) {
	                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	                byte[] tempBytes = new byte[_buffer_size];
	                int count = -1;
	                while ((count = inStream.read(tempBytes, 0, _buffer_size)) != -1) {
	                    outStream.write(tempBytes, 0, count);
	                }
	                tempBytes = null;
	                outStream.flush();
	                //将流转换成字符串
	                String result = new String(outStream.toByteArray(), "UTF-8");
	                
	                System.out.println(result);
	                //将字符串解析成XML
	                resultMap = (Map<String, Object>)JSON.parse(result);
	                //将XML格式转化成MAP格式数据
	                if(SignUtil.validSign01(resultMap, key)){  
	                
	                String channel =(String) resultMap.get("outTradeNo");
	                String outTradeNo =(String) resultMap.get("outTradeNo");
	                String description =(String) resultMap.get("postscript");
	                String[] desrp =	description.split(",");
	                Long orderId =Long.valueOf(desrp[0]);
	                Long userId =Long.valueOf(desrp[1]);
	                String status =(String)resultMap.get("status");
	                BigDecimal amount =(BigDecimal) resultMap.get("amount");
	                
	                BillOrder billorder = 	 billorderService.findByTradeNo(outTradeNo);
	        		
	       		 if(null == billorder){
	               
	                	if(resultMap.get("resultCode").equals("0") && resultMap.get("returnCode").equals("0")){
		                	if(resultMap.get("status").equals("02")){
		                		
		                	logger.info("-- --- ---- -- --- - - - - - - - - --orderId为： "+orderId);
		                	logger.info("-- --- ---- -- --- - - - - - - - - --userId为： "+userId);
		                	logger.info("-- --- ---- -- --- - - - - - - - - --订单号为： "+outTradeNo);
		                	logger.info("-- --- ---- -- --- - - - - - - - - --金额为： "+amount);
		                	
		                	/** 修改订单记录状态*/
		                	BillOrder billOrderVo = new BillOrder();
		                	billOrderVo.setOrderId(orderId);
		                	billOrderVo.setUserId(userId);
		                	billOrderVo.setMoney(amount.doubleValue());
		                	billOrderVo.setTradeNo(outTradeNo);
		                	Integer payWay =billorderService.changeChannel(channel);
		                	billOrderVo.setPayWay(payWay);
		                	billOrderVo.setStatus(0);
		                	billorderService.newsave(billOrderVo);
		                	
		                	/** 修改订单金额,及3步走，支付状态*/
		                	orderService.UpdateOrStatus(outTradeNo, amount.doubleValue());

		                	 /** 查询订单改变订单进度*/
		                	orderService.checkUpdateOrderStatus(orderService.findOne(orderId));
		                	
		                	logger.info("---- ----- ------ ---- 添加订单记录结束 0002");

		                	return "SUCCESS";
		                	
	                	}else if(resultMap.get("status").equals("01")){
	                		BillOrder billOrderVo = new BillOrder();
		                	billOrderVo.setOrderId(orderId);
		                	billOrderVo.setUserId(userId);
		                	billOrderVo.setMoney(amount.doubleValue());
		                	billOrderVo.setTradeNo(outTradeNo);
		                	Integer payWay =billorderService.changeChannel(channel);
		                	billOrderVo.setPayWay(payWay);
		                	billOrderVo.setStatus(Integer.parseInt(status));
		                	billorderService.save(billOrderVo);
	                		
	                		return ResultVOUtil.error(777, "支付未成功!");
	                	}else if(resultMap.get("status").equals("03")){
	                		BillOrder billOrderVo = new BillOrder();
		                	billOrderVo.setOrderId(orderId);
		                	billOrderVo.setUserId(userId);
		                	billOrderVo.setMoney(amount.doubleValue());
		                	billOrderVo.setTradeNo(outTradeNo);
		                	Integer payWay =billorderService.changeChannel(channel);
		                	billOrderVo.setPayWay(payWay);
		                	billOrderVo.setStatus(2);
		                	billorderService.newsave(billOrderVo);
	                		return ResultVOUtil.error(777, "已冲正!");
	                	}else if(resultMap.get("status").equals("04")){
	                		return ResultVOUtil.error(777, "订单已超时!");
	                	}else if(resultMap.get("status").equals("05")){
	                		return ResultVOUtil.error(777, "转入退款!");
	                	}else if(resultMap.get("status").equals("06")){
	                		return ResultVOUtil.error(777, "已撤销!");
	                	}else{
	                		return ResultVOUtil.error(777, "支付失败!");
	                	}
		                	
	                }else{
	                	return ResultVOUtil.error(777, "支付失败!");
	                }
	              }else{
	            		logger.info("-- --- ---- -- --- - - - - - - - - -- 订单号无效！");
	            	  return ResultVOUtil.error(777, "订单号无效!"); 
	              }
	                
	            }else{
	            	logger.info("-- --- ---- -- --- - - - - - - - - -- 响应的流为空！");
	
	            	return ResultVOUtil.error(777, "支付失败!");
	            }
	       
	            //通知微信支付系统接收到信息
	            }else{
	            	logger.info("======== ===== ==== ==== ===== ====   订单已支付");
	            	return ResultVOUtil.error(777, "订单已支付!");
	            }
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	        //如果失败返回错误，微信会再次发送支付信息
		logger.info("支付失败!----响应后台contrller ----- ------ --- --- --- ----- !");

		logger.info("=============================================================================");
		
		return ResultVOUtil.error(777, "支付失败!");
	}
	

}

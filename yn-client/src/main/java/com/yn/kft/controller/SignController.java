package com.yn.kft.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.yn.kftService.PyOrderService;
import com.yn.model.BillOrder;
import com.yn.service.BillOrderService;
import com.yn.utils.Constant;
import com.yn.utils.HttpsClientUtil;
import com.yn.utils.RequestUtils;
import com.yn.utils.SignUtil;
import com.yn.vo.BillOrderVo;
import com.yn.vo.re.ResultVOUtil;
import com.yn.web.OrderController;


@RestController
@RequestMapping(value="/client/sign")
public class SignController {
	
	private static final Logger logger = LoggerFactory.getLogger(SignController.class);
	
	@Autowired
	private BillOrderService orderService;
	@Autowired
	private PyOrderService pyOrderService;

	 /** pc端*/
		@ResponseBody
		@RequestMapping(value="/payonline")
		/** 传过来的参数为 payWay,channel,userId,balancePrice,money*/
		public Object doOnline(HttpServletRequest request,BillOrderVo billOrderVo){
			/** pc端支付宝支付为二维码支付*/ /** alipayQR*/
			/** pc端微信支付为二维码支付*/  /** wxPubQR*/
			/*** [支付方式]{0:手动录入,1:余额支付,2:微信,3:支付宝,4:银联,5:快付通}'*/
			/*BillOrderVo billOrderVo = new BillOrderVo();
			billOrderVo.setChannel("alipayQR");
			billOrderVo.setPayWay(2);
			billOrderVo.setTradeNo("2017071014160552756");*/
			/** 手机端是微信app支付*/  /** wxApp*/
			/** 手机端是支付宝app支付*/  /** alipayApp*/
			logger.info("--- ---- ---- ---- ----- ---- --- 支付的类型："+billOrderVo.getPayWay());
			logger.info("--- --- --- --- --- ---- --- 传递的支付方式为："+billOrderVo.getChannel());
			logger.info("--- ---- ---- --- --- -- --  传递的订单号为："+billOrderVo.getTradeNo());
			

			orderService.findByTradeNo(billOrderVo.getTradeNo());
			
			billOrderVo.setPayWay(2);
			//String type ="2";
			 //等于1是余额支付
			if(billOrderVo.getPayWay()==1){
				logger.info("--- ---- ---- ---- ----- ---- --- 进入方法1：");
				return pyOrderService.payBalance(billOrderVo);
				 //等于2是支付宝支付	
			}else if(billOrderVo.getPayWay()==2){
				logger.info("--- ---- ---- ---- ----- ---- --- 进入方法2："+billOrderVo.getChannel());
				
				return pyOrderService.getMap(request, billOrderVo.getTradeNo(), billOrderVo.getChannel());
				 //等于3是微信支付
			}else if(billOrderVo.getPayWay()==3){
				logger.info("--- ---- ---- ---- ----- ---- --- 进入方法3："+billOrderVo.getChannel());
				
				return pyOrderService.getMap(request, billOrderVo.getTradeNo(), billOrderVo.getChannel());
			}else if(billOrderVo.getPayWay()==4){//等于4是银联支付
				
				 //等于4是快付通支付
			}else if(billOrderVo.getPayWay()==5){
				
				return ""; 
			}

			
			return ResultVOUtil.error(777, Constant.PAY_WAY_NULL);
		}
	
		
		@ResponseBody
		@RequestMapping(value="/doresult")
		public Object getSignresult(HttpServletRequest request){
			
			
			Map<String, String> map = RequestUtils.getRequestMapValue(request.getParameterMap());
			
			
			return request;
		}
		/** 接收快付通响应返回结果的接口*/
		@ResponseBody
		@RequestMapping(value="/result")
		public Object getSignpay(HttpServletRequest request){
			String queryType="1";
			String outTradeNo ="";
			
			/** 只是测试数据，根据实际情况而定*/ 
			Map<String, String> map = RequestUtils.getRequestMapValue(request.getParameterMap());
			  map.put("tradeType","cs.trade.single.query");
		      map.put("version", "1.0");
		       /** 测试数据 mchid*/
		      map.put("mchId", "000010000000000024");
		      map.put("queryType", queryType); 
		      map.put("outTradeNo", outTradeNo);
			
			//过滤空值或null
			Map<String, String> filterMap = SignUtil.paraFilter(map);
			//拼接
			String toSign = SignUtil.createLinkString(filterMap);
			/** 生成签名sign 测试key：7f957562b40e4b0eaf5bc9ed4d1a78ca
			 * 生产：key：详见conf.properties
			 ** */
			String sign = SignUtil.genSign("7f957562b40e4b0eaf5bc9ed4d1a78ca", toSign);
			filterMap.put("sign", sign);
			//转为json串
			String postStr = JSON.toJSONString(filterMap);
			/** 发送请求 测试requestUrl：http://test.kftpay.com.cn:3080/cloud/cloudplatform/api/trade.html 
			 * 生产url：https://jhpay.kftpay.com.cn/cloud/cloudplatform/api/trade.html
			 * */
			String returnStr = HttpsClientUtil.sendRequest(
					"http://test.kftpay.com.cn:3080/cloud/cloudplatform/api/trade.html",
					postStr,"application/json");
			
			//------------------------------------------------------//
			//解析返回串
			Map<String, String> returnMap = (Map<String, String>)JSON.parse(returnStr);

			//验签
			if(SignUtil.validSign(filterMap, "7f957562b40e4b0eaf5bc9ed4d1a78ca")){
				  /****最终都是返回map集合  与判断returnCode 与resultCode 有区别****/
				String returnCode = returnMap.get("returnCode");
				String resultCode = returnMap.get("resultCode");
				if(returnCode.equals("0")&&resultCode.equals("0")){
					//请根据实际情况操作 更新数据到对应的表，或显示到页面
					request.setAttribute("returnMap", returnMap);
					
				}else{
					//请根据实际情况操作 更新数据到对应的表，或显示到页面
					request.setAttribute("returnMap", returnMap);
				}
			}else {
				//do nothing
			}

			return ResultVOUtil.success(null);
		}
		

}

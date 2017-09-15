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

import com.yn.kftService.PyOrderService;
import com.yn.model.BillOrder;
import com.yn.service.BillOrderService;
import com.yn.utils.Constant;
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
		/** 传过来的参数为 payWay,channel,userId*/
		public Object doOnline(HttpServletRequest request){
			/** pc端支付宝支付为二维码支付*/ /** alipayQR*/
			/** pc端微信支付为二维码支付*/  /** wxPubQR*/
			/*** [支付方式]{0:手动录入,1:余额支付,2:微信,3:支付宝,4:银联,5:快付通}'*/
			BillOrderVo billOrderVo = new BillOrderVo();
			billOrderVo.setChannel("alipayQR");
			billOrderVo.setPayWay(2);
			billOrderVo.setTradeNo("2017071014160552756");
			
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
				
				return "forward:/client/sign/dosign";
				 //等于2是支付宝支付	
			}else if(billOrderVo.getPayWay()==2){
				
				
				return pyOrderService.getMap(request, billOrderVo.getTradeNo(), billOrderVo.getChannel());
				 //等于3是微信支付
			}else if(billOrderVo.getPayWay()==3){
				
				return pyOrderService.getMap(request, billOrderVo.getTradeNo(), billOrderVo.getChannel());
			}else if(billOrderVo.getPayWay()==4){//等于4是银联支付
				
				 //等于4是快付通支付
			}else if(billOrderVo.getPayWay()==5){
				
				return ""; 
			}

			
			return ResultVOUtil.error(777, Constant.PAY_WAY_NULL);
		}
	

		/** 接收快付通响应返回结果的接口*/
		@ResponseBody
		@RequestMapping(value="/result")
		public Object getSignpay(){
			
			
			
			return ResultVOUtil.success(null);
		}
		

}

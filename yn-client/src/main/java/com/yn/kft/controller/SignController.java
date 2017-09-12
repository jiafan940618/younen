package com.yn.kft.controller;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.soofa.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lycheepay.gateway.client.security.KeystoreSignProvider;
import com.lycheepay.gateway.client.security.SignProvider;
import com.yn.kftentity.OrderPay;
import com.yn.service.OrderService;
import com.yn.utils.CashierSignUtil;



@Controller
@RequestMapping(value="/sign")
public class SignController {
	
	@Resource
	private OrderService orderService;
	
	private final static String charset = "UTF-8";

	private static Map<String, String> param = null;
	private static String signatureInfo = "";
	
	
	@RequestMapping(value="/online")
	public String doOnline(){
		
		System.out.println("进入测试页面！");
		
		return "queryIndex";
	}
	
	@RequestMapping(value="/dosign")
	public String getSucRep(@RequestParam(value="outTradeNo") String orderNo ,HttpServletRequest request){
		
		Charset encoding = Charset.forName(charset);
		
	OrderPay orderPay =	new OrderPay();
		
		
try {
			String pfxPath="F://Java.01//Eclipse//工作专用//工作文档//测试环境证书//";
			String  psw="123456";
			SignProvider signProvider = new KeystoreSignProvider("PKCS12",pfxPath+"pfx.pfx",
					psw.toCharArray(), null, psw.toCharArray());
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("productNo", "1FA00AAA");
			parameters.put("service", "proxy_onlineBank_direct_service");
			parameters.put("version", "1.0.0-TEST");
			parameters.put("language", "BG");
			parameters.put("signatureAlgorithm", "RSA");
			 /** 2014030600048235*/
			parameters.put("merchantId", "2014030600048235");
			parameters.put("callerIp", "218.17.35.123");
			  /** 同步通知结果，后期测试*/
			//parameters.put("returnUrl", "http://10.36.160.29:8080/cashierDemo/returnUrl.jsp");
			parameters.put("notifyAddr", "http://192.168.0.66:8080/guangfu/sign/doSuncRep");
			
			parameters.put("customerType", orderPay.getCustomertype());
			//订单号
			parameters.put("orderNo", orderPay.getOuttradeno());
			parameters.put("tradeTime", "2016-12-08 15:00:00");
			parameters.put("payPurpose", "1");
			parameters.put("currency",  orderPay.getCurrency());
			parameters.put("tradeName", orderPay.getBody());
			parameters.put("subject", orderPay.getSubject());
			parameters.put("description", orderPay.getDescription());
			parameters.put("amount", orderPay.getAmount().toString());
			//parameters.put("singlePrice", "");
			//parameters.put("quantity", "");
			//parameters.put("payerCustName", "");
			 /** 添加时注意添加银行的类型*/
			parameters.put("bankType",orderPay.getBanktype());
			parameters.put("timeout", "5m");
			//parameters.put("showUrl", "");
			//parameters.put("merchantLogoUrl", "");
			parameters.put("cashierStyle", "1");
		
			Map<String, String> signParameters = CashierSignUtil.signParameters(signProvider,
					parameters, null);
			signatureInfo = signParameters.get("signatureInfo");
			System.out.println("商户POST请求后加签的密文:"+signatureInfo);
			
			String prestr = CashierSignUtil.createLinkString(signParameters);
			
			String encodeBase64String = signProvider.sign(prestr.getBytes(charset),
					encoding);
			parameters.put("signatureInfo", signatureInfo);
		
			request.setAttribute("map", parameters);
			
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		return "online-pay";	
	}
	
	
	 /** 异步回调*/
	
	@RequestMapping(value="/doSuncRep")
	public String getQuery(HttpServletRequest request){
		String pfxPath="F://Java.01//Eclipse//工作专用//工作文档//测试环境证书//20KFT.cer";
		
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("signatureInfo",request.getParameter("signatureInfo"));
			parameters.put("amount",request.getParameter("amount"));
			parameters.put("orderNo",request.getParameter("orderNo"));
			parameters.put("status", request.getParameter("status"));
			parameters.put("statusDesc",request.getParameter("statusDesc"));
			parameters.put("callerIp",request.getParameter("callerIp"));
			parameters.put("language",request.getParameter("language"));
			parameters.put("signatureAlgorithm",request.getParameter("signatureAlgorithm"));
			
	   
	        
			boolean verify_sign=CashierSignUtil.verifySign_2(pfxPath, parameters, request.getParameter("signatureInfo"));
		
			  if(verify_sign)
			   {
				   System.out.println("验签成功，具体接收参数如下：");
				 /*  System.out.println("amount="+notify_amount+",orderNo="
				   +notify_orderNo+",status="+notify_status+",statusDesc="+notify_statusDesc+
						   ",callerIp="+notify_callerIp+",language="
						   		+ ""+notify_language+",signatureInfo="
						   +notify_signatureInfo+",signatureAlgorithm"+notfy_signatureAlgorithm);*/
			   }else {
				  System.out.println("验签失败，请核对参数的准确性！");
			}      	
		return "queryIndex";
	}
	
	
	
	

}

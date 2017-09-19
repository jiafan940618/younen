package com.yn.kftService;

/*import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import com.lycheepay.gateway.client.security.KeystoreSignProvider;
import com.lycheepay.gateway.client.security.SignProvider;
import com.yn.kftentity.OrderPay;*/

public class SignService {
	
	/*private final static String charset = "UTF-8";

	private static Map<String, String> param = null;
	private static String signatureInfo = "";
	
	public void  findSign(){
		
		OrderPay orderPay  = new OrderPay();
		
		Charset encoding = Charset.forName(charset);
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
			 *//** 2014030600048235*//*
			parameters.put("merchantId", "2014030600048235");
			parameters.put("callerIp", "218.17.35.123");
			  *//** 同步通知结果，后期测试*//*
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
			 *//** 添加时注意添加银行的类型*//*
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
			
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}*/

}

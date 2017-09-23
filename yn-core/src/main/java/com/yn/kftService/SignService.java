package com.yn.kftService;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lycheepay.gateway.client.security.KeystoreSignProvider;
import com.lycheepay.gateway.client.security.SignProvider;
import com.yn.kftentity.OrderPay;
import com.yn.utils.CashierSignUtil;
import com.yn.vo.BillOrderVo;
import com.yn.vo.re.ResultVOUtil;

@Service
public class SignService {
	
	private final static String charset = "UTF-8";

	private static Map<String, String> param = null;
	private static String signatureInfo = "";
	
	//request
	public static Object  findSign(BillOrderVo billOrderVo){
		OrderPay orderPay  = new OrderPay();
		BigDecimal amount = BigDecimal.valueOf(100.0);
		orderPay.setAmount(billOrderVo.getMoney());
		  /** 银行卡编号*/
		orderPay.setBanktype("1051000");
		orderPay.setBody("测试商品0110");
		orderPay.setSubject("测试商品002");
		orderPay.setDescription(billOrderVo.getDescription());
		Map<String, String> parameters = new HashMap<String, String>();
		
		Charset encoding = Charset.forName(charset);

		 File directory = new File("");// 参数为空
		 String pfxPath=null;
		try {
			pfxPath = directory.getCanonicalPath();
			 System.out.println("项目路径为：-- --- -- -- - - - - - -"+pfxPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {

			String  psw="123456";
		SignProvider signProvider = new KeystoreSignProvider("PKCS12",pfxPath+"\\privateKey\\pfx.pfx",
					psw.toCharArray(), null, psw.toCharArray());
		
			parameters.put("productNo", "1FA00AAA");
			parameters.put("service", "proxy_onlineBank_direct_service");
			parameters.put("version", "1.0.0-TEST");
			parameters.put("language", "BG");
			parameters.put("signatureAlgorithm", "RSA");
			//** 2014030600048235*//*
			parameters.put("merchantId", "2014030600048235");
			parameters.put("callerIp", "218.17.35.123");
			//** 页面同步通知地址,同步通知结果，后期测试*//*
			//parameters.put("returnUrl", "http://10.36.160.29:8080/cashierDemo/returnUrl.jsp");
			 /** 后台通知地址*/
			parameters.put("notifyAddr", "http://localhost/client/sign/doresult");
			
			parameters.put("customerType", "1");
			//订单号
			parameters.put("orderNo", orderPay.getOuttradeno());
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String tradeTime = formatter.format(date);

			parameters.put("tradeTime", tradeTime);
			parameters.put("payPurpose", "1");
			parameters.put("currency",  "CNY");
			parameters.put("tradeName", orderPay.getBody());
			parameters.put("subject", orderPay.getSubject());
			parameters.put("description", orderPay.getDescription());
			parameters.put("amount", orderPay.getAmount().toString());
			//parameters.put("singlePrice", "");
			//parameters.put("quantity", "");
			//parameters.put("payerCustName", "");
			//** 添加时注意添加银行的类型*//*
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
	
		
		return ResultVOUtil.success(parameters);
	}
	
	
	public static void main(String[] args) {
	//	findSign();
	}

}

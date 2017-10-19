package com.yn.service.kftService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jcraft.jsch.Logger;
import com.lycheepay.gateway.client.security.KeystoreSignProvider;
import com.lycheepay.gateway.client.security.SignProvider;
import com.yn.kftentity.OrderPay;
import com.yn.model.BillOrder;
import com.yn.service.BillOrderService;
import com.yn.utils.CashierSignUtil;
import com.yn.utils.PropertyUtils;
import com.yn.vo.BillOrderVo;
import com.yn.vo.re.ResultVOUtil;

@Service
public class SignService {
	
	private final  static String charset = "UTF-8";

	@Autowired
	private static BillOrderService billorderService;
	private static Map<String, String> param = null;
	private static String signatureInfo = "";
	private String merchantId =PropertyUtils.getProperty("merchantId");
	private String notifyUrl ="http://test.u-en.cn/client/sign/doSucRep";
	private String merchantIps =PropertyUtils.getProperty("callerIp");
	private String callerIp =PropertyUtils.getProperty("callerIp");
	private String version =PropertyUtils.getProperty("version");
	
	
	//request
	public  Object  findSign(BillOrderVo billOrderVo){
		 //** 银行卡编号*//*
		 billOrderVo.setBankType("1051000");
		 billOrderVo.setBoby("交易名称0002");
		billOrderVo.setSubject("优能光伏");
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

			parameters.put("productNo", "2ACB0BAA");
			parameters.put("service", "proxy_onlineBank_direct_service");
			parameters.put("version", version);
			parameters.put("language", "BG");
			parameters.put("signatureAlgorithm", "RSA");
			/** 2014030600048235*/
			parameters.put("merchantId", merchantId);
			parameters.put("callerIp", callerIp);
			 /** 后台通知地址*/

			parameters.put("notifyAddr", "http://test.u-en.cn/client/sign/doSucRep");

			
			parameters.put("customerType", "1");
			//订单号
			parameters.put("orderNo", billOrderVo.getTradeNo());
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String tradeTime = formatter.format(date);

			parameters.put("tradeTime", tradeTime);
			parameters.put("payPurpose", "1");
			parameters.put("currency",  "CNY");
			parameters.put("tradeName", billOrderVo.getBoby());
			parameters.put("subject", billOrderVo.getSubject());
			parameters.put("description", "优能光伏");
			parameters.put("amount", billOrderVo.getMoney().toString());
			//parameters.put("singlePrice", "");
			//parameters.put("quantity", "");
			//parameters.put("payerCustName", "");
			//** 添加时注意添加银行的类型*//*
			parameters.put("bankType",billOrderVo.getBankType());
			parameters.put("timeout", "5m");
			parameters.put("cashierStyle", "1");
		
			String signatureInfo =	CashierSignUtil.sign(pfxPath+"/privateKey/pfx.pfx", "123456",parameters);
			
	
			
			parameters.put("signatureInfo", signatureInfo);
			
			
			
		} catch (Exception e) {
		
			e.printStackTrace();
		}

		return ResultVOUtil.success(parameters);
	}
	
	public String getresult(HttpServletRequest request){
		
		 String result = null;
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
             result  = new String(outStream.toByteArray(), "UTF-8");
         }
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		return result;
	}
	
	
}

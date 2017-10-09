package com.yn.kftService;
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

import com.lycheepay.gateway.client.security.KeystoreSignProvider;
import com.lycheepay.gateway.client.security.SignProvider;
import com.yn.kftentity.OrderPay;
import com.yn.model.BillOrder;
import com.yn.service.BillOrderService;
import com.yn.utils.CashierSignUtil;
import com.yn.vo.BillOrderVo;
import com.yn.vo.re.ResultVOUtil;

@Service
public class SignService {
	
	private final static String charset = "UTF-8";

	@Autowired
	private static BillOrderService billorderService;
	private static Map<String, String> param = null;
	private static String signatureInfo = "";
	
	//request
	public static Object  findSign(BillOrderVo billOrderVo){
		 //** 银行卡编号*//*
		 billOrderVo.setBankType("1051000");
		 billOrderVo.setBoby("交易名称0002");
		billOrderVo.setSubject("商品名称0001");
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

			parameters.put("productNo", "1FA00AAA");
			parameters.put("service", "proxy_onlineBank_direct_service");
			parameters.put("version", "1.0.0-TEST");
			parameters.put("language", "BG");
			parameters.put("signatureAlgorithm", "RSA");
			//** 2014030600048235*//*
			parameters.put("merchantId", "2014030600048235");
			parameters.put("callerIp", "192.168.0.104");
			//** 页面同步通知地址,同步通知结果，后期测试*//*
			//parameters.put("returnUrl", "http://10.36.160.29:8080/cashierDemo/returnUrl.jsp");
			 /** 后台通知地址*/

			parameters.put("notifyAddr", "http://client.u-en.cn/client/sign/doresult");

			
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
			parameters.put("description", billOrderVo.getDescription());
			parameters.put("amount", billOrderVo.getMoney().toString());
			//parameters.put("singlePrice", "");
			//parameters.put("quantity", "");
			//parameters.put("payerCustName", "");
			//** 添加时注意添加银行的类型*//*
			parameters.put("bankType",billOrderVo.getBankType());
			parameters.put("timeout", "5m");
			//parameters.put("showUrl", "");
			//parameters.put("merchantLogoUrl", "");
			parameters.put("cashierStyle", "1");
		
			String signatureInfo =	CashierSignUtil.sign(pfxPath+"\\privateKey\\pfx.pfx", "123456",parameters);
			
			System.out.println(signatureInfo);
			
			parameters.put("signatureInfo", signatureInfo);
			
			BillOrder billOrder = new BillOrder();
			billOrder.setOrderId(billOrderVo.getOrderId());
			billOrder.setUserId(billOrderVo.getUserId());
			billOrder.setMoney(billOrderVo.getMoney().doubleValue());
			billOrder.setTradeNo(billOrderVo.getTradeNo());
	    	billOrder.setPayWay(billOrderVo.getPayWay());
	    	billOrder.setStatus(1);
	    	billorderService.newsave(billOrder);
			
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

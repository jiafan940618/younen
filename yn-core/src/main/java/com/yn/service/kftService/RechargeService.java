package com.yn.service.kftService;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lycheepay.gateway.client.GatewayClientException;
import com.lycheepay.gateway.client.InitiativePayService;
import com.lycheepay.gateway.client.dto.initiativepay.ActiveScanPayReqDTO;
import com.lycheepay.gateway.client.dto.initiativepay.ActiveScanPayRespDTO;
import com.lycheepay.gateway.client.security.KeystoreSignProvider;
import com.lycheepay.gateway.client.security.SignProvider;
import com.yn.dao.RechargeDao;
import com.yn.model.Recharge;
import com.yn.service.OrderService;
import com.yn.service.WalletService;
import com.yn.utils.BeanCopy;
import com.yn.utils.CashierSignUtil;

import com.yn.utils.PropertyUtils;

import com.yn.vo.RechargeVo;
import com.yn.vo.re.ResultVOUtil;

@Service
public class RechargeService {
	
	 private static final Logger logger = LoggerFactory.getLogger(PyOrderService.class);
	 
	 
		InitiativePayService service;
		
		@Autowired
		RechargeDao rechargeDao;
		@Autowired
		private  RechargeService rechargeService;
		@Autowired 
		PyOrderService pyOrderService;
		@Autowired
		WalletService walletService;
		
		public static final String WX_BANK_NO = "0000001";
		public static final String ZFB_BANK_NO = "0000002";
		public static final String YL_BANK_NO = "0000003";

		static	String terminalIp = "192.168.0.104";
		static String currency = "CNY";
		static 	String tradeName = "商品描述001";
		static String remark = "remark";
		static String operatorId = "operatorId";
		static	String storeId = "storeId";
		static	String terminalId = "49000002";
		private String merchantId =PropertyUtils.getProperty("merchantId");
		private String notifyUrl ="http://test.u-en.cn/client/recharge/doresult";
		private String merchantIps =PropertyUtils.getProperty("merchantIp");

	  public Recharge findOne(Long id) {
	        return rechargeDao.findOne(id);
	    }

	    public void save(Recharge recharge) {
	        if(recharge.getId()!=null){
	        	Recharge one = rechargeDao.findOne(recharge.getId());
	            try {
	                BeanCopy.beanCopy(recharge,one);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            rechargeDao.save(one);
	        }else {
	        	rechargeDao.save(recharge);
	        }
	    }

	 public   RechargeVo findRecharge(Recharge recharge){
		 
		Object[] objct  = (Object[])rechargeDao.findRecharge(recharge);
		
		RechargeVo rechargeVo = new RechargeVo();
		
		//rechargeVo.setId(id);
		Integer walletId = (Integer) objct[0];
		BigDecimal w_money = (BigDecimal) objct[1];
		BigDecimal r_money = (BigDecimal) objct[2];
		
		rechargeVo.setWalltId(walletId.longValue());
		rechargeVo.setTotalmoney(w_money);
		rechargeVo.setMoney(r_money);
		 
		return rechargeVo;	
	  }
	    
	    
	  public  void updateRecharge(Recharge recharge){
	    	
		  rechargeDao.updateRecharge(recharge);
	    }
	    
	    
		public  void init() throws Exception {
			 File directory = new File("");// 参数为空
			 String pfxPath=null;
				try {
					pfxPath = directory.getCanonicalPath();
					logger.info("项目路径为：-- --- -- -- - - - - - -"+(pfxPath+"/privateKey/pfx.pfx"));
				} catch (IOException e) {
					
					e.printStackTrace();
				}
		
			// 初始化证书
			String merchantIp = merchantIps;
			// 证书类型、证书路径、证书密码、别名、证书容器密码
			SignProvider keystoreSignProvider = new KeystoreSignProvider("PKCS12",pfxPath+"/privateKey/pfx.pfx", "123456".toCharArray(), null,
					"123456".toCharArray());
			// 签名提供者、商户服务器IP(callerIp)、下载文件路径(暂时没用)
			service = new InitiativePayService(keystoreSignProvider, merchantIp, "zh_CN", "c:/zip"); 
			service.setHttpPort(6443);
			// 设置的交易连接超时时间要大于0小于2分钟,单位:秒.0表示不超时,一直等待,默认30秒
			service.setConnectionTimeoutSeconds(1 * 60);
			// 设置的交易响应超时时间,要大于0小于10分钟,单位:秒.0表示不超时,一直等待,默认5分钟,ps：对应获取对账文件这个应该设长一点时间
			service.setResponseTimeoutSeconds(10 * 60);
		}

		public Object getMap(HttpServletRequest request,RechargeVo rechargeVo){
			ActiveScanPayReqDTO reqDTO = new ActiveScanPayReqDTO();
			reqDTO.setReqNo(String.valueOf(System.currentTimeMillis()));//请求编号
			reqDTO.setService("kpp_zdsm_pay");//接口名称，固定不变
			reqDTO.setVersion("1.0.0-IEST");//接口版本号，测试:1.0.0-IEST,生产:1.0.0-PRD
			reqDTO.setMerchantId(merchantId);//替换成快付通提供的商户ID，测试生产不一样
			// reqDTO.setSecMerchantId(secMerchantId)//二级商户ID 可空
			reqDTO.setOrderNo(rechargeVo.getRechargeCode());//交易编号 
			reqDTO.setTerminalIp(terminalIp);//APP和网页支付提交用户端ip，主扫支付填调用付API的机器IP
			reqDTO.setNotifyUrl(notifyUrl);//必须可直接访问的url，不能携带参数
			reqDTO.setAmount(rechargeVo.getMoney().toString());//此次交易的具体金额,单位:分,不支持小数点
			reqDTO.setCurrency(currency);//币种
			reqDTO.setTradeName(tradeName);//商品描述,简要概括此次交易的内容.可能会在用户App上显示
			reqDTO.setRemark(remark);//商品详情 可空
			
				Date currentTime = new Date();
			   SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			   String dateString = formatter.format(currentTime);
			
			reqDTO.setTradeTime(dateString);;//商户方交易时间 注意此时间取值一般为商户方系统时间而非快付通生成此时间 20120927185643
			
			/** [支付方式]{1:微信,2:支付宝,3:银联,4:快付通}*/
			if(rechargeVo.getPayWay() == 1){
				reqDTO.setBankNo(WX_BANK_NO);//支付渠道   微信渠道:0000001 支付宝渠道：0000002 银联：0000003 
			}else if(rechargeVo.getPayWay() == 2){
				reqDTO.setBankNo(ZFB_BANK_NO);
			}else if(rechargeVo.getPayWay() == 3){
				reqDTO.setBankNo(YL_BANK_NO);
			}
			reqDTO.setOperatorId(operatorId);//商户操作员编号 可空
			reqDTO.setStoreId(storeId);//商户门店编号 可空
			reqDTO.setIsS0("0");//是否是S0支付是否是S0支付，1：是；0：否。默认否。如果是S0支付，金额会实时付给商户。需经快付通审核通过后才可开展此业务。如果无此业务权限，此参数为1，则返回失败。 可空 
			ActiveScanPayRespDTO resp = new ActiveScanPayRespDTO();
			
			/** 保存订单*/
			Recharge recharge = new Recharge();
			recharge.setWalltId(rechargeVo.getWalltId());
			recharge.setMoney(rechargeVo.getMoney().doubleValue()*0.01);
			recharge.setRechargeCode(rechargeVo.getRechargeCode());
			recharge.setPayWay(rechargeVo.getPayWay());
			recharge.setDel(0);
			recharge.setStatus(1);
			rechargeService.save(recharge);
	    	

	    	try {
				resp = service.activeScanPay(reqDTO);
				System.out.println("主扫支付响应：" + JSON.toJSONString(resp));
				System.out.println("主扫支付响应----- ---- CodeUrl：" + resp.getCodeUrl());
				System.out.println("主扫支付响应----- ---- Status：" + resp.getStatus());
				if(Integer.parseInt(resp.getStatus()) != 7){
					logger.info("------ ----- ----- ------错误码："+resp.getErrorCode());
					logger.info("------ ----- ----- ------错误信息："+resp.getFailureDetails());
					return ResultVOUtil.error(777,resp.getFailureDetails());
				}
	    	} catch (GatewayClientException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}	
				
			return  ResultVOUtil.success(resp);
		}
		
		public  Object  findSign(RechargeVo rechargeVo){
			 //** 银行卡编号*//*
			rechargeVo.setBankType("1051000");
			rechargeVo.setBoby("充值记录交易名称0002");
			rechargeVo.setSubject("充值记录商品名称0001");
			rechargeVo.setDescription("充值记录");
			
			Map<String, String> parameters = new HashMap<String, String>();
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
				/** 2014030600048235*/
				parameters.put("merchantId", "2014030600048235");
				parameters.put("callerIp", "120.76.98.74");
				 /** 后台通知地址*/

				parameters.put("notifyAddr", "http://test.u-en.cn/client/recharge/doSucRep");

				
				parameters.put("customerType", "1");
				//订单号
				parameters.put("orderNo", rechargeVo.getRechargeCode());
				
				Date date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				String tradeTime = formatter.format(date);

				parameters.put("tradeTime", tradeTime);
				parameters.put("payPurpose", "1");
				parameters.put("currency",  "CNY");
				parameters.put("tradeName", rechargeVo.getBoby());
				parameters.put("subject", rechargeVo.getSubject());
				parameters.put("description", rechargeVo.getDescription());
				parameters.put("amount", rechargeVo.getMoney().toString());
				
				//** 添加时注意添加银行的类型*//*
				parameters.put("bankType",rechargeVo.getBankType());
				parameters.put("timeout", "5m");
				
				parameters.put("cashierStyle", "1");
			
				String signatureInfo =	CashierSignUtil.sign(pfxPath+"/privateKey/pfx.pfx", "123456",parameters);
				
				System.out.println(signatureInfo);
				
				parameters.put("signatureInfo", signatureInfo);
				
				Recharge recharge = new Recharge();
				recharge.setWalltId(rechargeVo.getWalltId());
				recharge.setMoney(rechargeVo.getMoney().doubleValue()*0.01);
				recharge.setRechargeCode(rechargeVo.getRechargeCode());
				recharge.setPayWay(rechargeVo.getPayWay());
				recharge.setStatus(1);
				rechargeService.save(recharge);
				
				
			} catch (Exception e) {
			
				e.printStackTrace();
			}

			return ResultVOUtil.success(parameters);
		}
}	
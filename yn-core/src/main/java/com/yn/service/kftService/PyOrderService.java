package com.yn.service.kftService;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.lycheepay.gateway.client.GatewayClientException;
import com.lycheepay.gateway.client.InitiativePayService;
import com.lycheepay.gateway.client.dto.initiativepay.ActiveScanPayReqDTO;
import com.lycheepay.gateway.client.dto.initiativepay.ActiveScanPayRespDTO;
import com.lycheepay.gateway.client.security.KeystoreSignProvider;
import com.lycheepay.gateway.client.security.SignProvider;
import com.yn.model.BillOrder;
import com.yn.model.Wallet;
import com.yn.service.BillOrderService;
import com.yn.service.OrderService;
import com.yn.service.WalletService;
import com.yn.utils.Constant;
import com.yn.utils.PropertyUtils;
import com.yn.vo.BillOrderVo;
import com.yn.vo.re.ResultVOUtil;

 
@Service
public class PyOrderService {
	
	private static final Logger logger = LoggerFactory.getLogger(PyOrderService.class);
	InitiativePayService service;


	@Autowired
	private  BillOrderService billOrderService;
	@Autowired
	private OrderService orderService;
	@Autowired 
	PyOrderService pyOrderService;
	@Autowired
	WalletService walletService;
	
	public static final String WX_BANK_NO = "0000001";
	public static final String ZFB_BANK_NO = "0000002";
	public static final String YL_BANK_NO = "0000003";

	
	static	String terminalIp = "192.168.0.104";
//	static	String notifyUrl = "http://b85ba525.ngrok.io/client/sign/doresult";
	static String currency = "CNY";
	static 	String tradeName = "商品描述001";
	static String remark = "remark";
	static String operatorId = "operatorId";
	static	String storeId = "storeId";
	static	String terminalId = "49000002";
	private String merchantId =PropertyUtils.getProperty("merchantId");
	private String notifyUrl =PropertyUtils.getProperty("notifyUrl");
	private String merchantIps =PropertyUtils.getProperty("merchantIp");
	
	
	
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

	public Object getMap(HttpServletRequest request,BillOrderVo billOrderVo){
		ActiveScanPayReqDTO reqDTO = new ActiveScanPayReqDTO();
		reqDTO.setReqNo("kft"+String.valueOf(System.currentTimeMillis()));//请求编号
		reqDTO.setService("kpp_zdsm_pay");//接口名称，固定不变
		reqDTO.setVersion("1.0.0-IEST");//接口版本号，测试:1.0.0-IEST,生产:1.0.0-PRD
		reqDTO.setMerchantId(merchantId);//替换成快付通提供的商户ID，测试生产不一样
		
		// reqDTO.setSecMerchantId(secMerchantId)//二级商户ID 可空
		reqDTO.setOrderNo(billOrderVo.getTradeNo());//交易编号 
		reqDTO.setTerminalIp(terminalIp);//APP和网页支付提交用户端ip，主扫支付填调用付API的机器IP
		reqDTO.setNotifyUrl(notifyUrl);//必须可直接访问的url，不能携带参数
		reqDTO.setAmount(billOrderVo.getMoney().toString());//此次交易的具体金额,单位:分,不支持小数点
		reqDTO.setCurrency(currency);//币种
		reqDTO.setTradeName(tradeName);//商品描述,简要概括此次交易的内容.可能会在用户App上显示
		reqDTO.setRemark(remark);//商品详情 可空
		
			Date currentTime = new Date();
		   SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		   String dateString = formatter.format(currentTime);
		
		reqDTO.setTradeTime(dateString);;//商户方交易时间 注意此时间取值一般为商户方系统时间而非快付通生成此时间 20120927185643
		if(billOrderVo.getPayWay() == 2){
			reqDTO.setBankNo(WX_BANK_NO);//支付渠道   微信渠道:0000001 支付宝渠道：0000002 银联：0000003 
		}else if(billOrderVo.getPayWay() == 3){
			reqDTO.setBankNo(ZFB_BANK_NO);
		}else if(billOrderVo.getPayWay() == 4){
			reqDTO.setBankNo(YL_BANK_NO);
		}
		reqDTO.setOperatorId(operatorId);//商户操作员编号 可空
		reqDTO.setStoreId(storeId);//商户门店编号 可空
		reqDTO.setIsS0("0");//是否是S0支付是否是S0支付，1：是；0：否。默认否。如果是S0支付，金额会实时付给商户。需经快付通审核通过后才可开展此业务。如果无此业务权限，此参数为1，则返回失败。 可空 
		//reqDTO.setIsGuarantee("0");//是否担保交易,1:是，0:否
		//reqDTO.setIsSplit("0");//是否分账交易,1:是，0：否 ，
		//分账详情，如果是否分账交易为是，该字段为必填，格式如下:
		//reqDTO.setSplitInfo("[{\"merchantId\":\"2017072600081986\",\"amount\":\"1\",\"remark\":\"有线电视费\"},{\"merchantId\":\"2017073100082105\",\"amount\":\"1\",\"remark\":\"宽带费\"}]");
		ActiveScanPayRespDTO resp = new ActiveScanPayRespDTO();
		
		/** 保存订单*/
		BillOrder billOrder = new BillOrder();
		billOrder.setOrderId(billOrderVo.getOrderId());
		billOrder.setUserId(billOrderVo.getUserId());
		billOrder.setMoney(billOrderVo.getMoney().doubleValue()*0.01);
		billOrder.setTradeNo(billOrderVo.getTradeNo());
    	billOrder.setPayWay(billOrderVo.getPayWay());
    	billOrder.setStatus(1);
    	billOrder.setDel(0);
    	billOrderService.newsave(billOrder);
    	

    	try {
			resp = service.activeScanPay(reqDTO);
			System.out.println("主扫支付响应：" + JSON.toJSONString(resp));
			System.out.println("主扫支付响应----- ---- CodeUrl：" + resp.getCodeUrl());
			System.out.println("主扫支付响应----- ---- Status：" + resp.getStatus());
			if(Integer.parseInt(resp.getStatus()) != 7){
				logger.info("------ ----- ----- ------错误码："+resp.getErrorCode());
				logger.info("------ ----- ----- ------错误信息："+resp.getFailureDetails());
				billOrder.setRemark(resp.getErrorCode()+":"+resp.getFailureDetails());
				
				billOrderService.save(billOrder);
				
				return ResultVOUtil.error(777,"抱歉,支付失败，详情，请咨询客服!");
			}
    	} catch (GatewayClientException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}	
			
		return  ResultVOUtil.success(resp);
	}
	
	
	
	/** 处理优能余额支付*/
	public Object payBalance(BillOrderVo billOrderVo){
		
		Wallet wallet =	walletService.findWalletByUser(billOrderVo.getUserId());
		/** 余额*/
		BigDecimal balancePrice =	wallet.getMoney();
	   
		 /** 余额与支付的钱比较*/
		if(balancePrice.compareTo(billOrderVo.getMoney()) == -1){
			
			
			return ResultVOUtil.error(777,Constant.MONEY_LITTLE);
		}
	 
		BigDecimal money =balancePrice.subtract(billOrderVo.getMoney());

		wallet.setMoney(money);
		
		walletService.updatePrice(wallet);
		
		BillOrder billOrder = new BillOrder();
		billOrder.setOrderId(billOrderVo.getOrderId());
		billOrder.setUserId(billOrderVo.getUserId());
		billOrder.setMoney(billOrderVo.getMoney().doubleValue());
		logger.info("-- --- ----保存的金额为："+billOrderVo.getMoney().doubleValue());		
		billOrder.setTradeNo(billOrderVo.getTradeNo());
    	billOrder.setPayWay(billOrderVo.getPayWay());
    	billOrder.setStatus(0);
    	billOrder.setDel(0);
    	billOrderService.newsave(billOrder);
		
		/** 修改订单金额,及3步走，支付状态*/
    	orderService.UpdateOrStatus(billOrderVo.getTradeNo(), billOrderVo.getMoney().doubleValue());

    	 /** 查询订单改变订单进度*/
    	//orderService.givePrice(orderService.FindByTradeNo(billOrderVo.getTradeNo()));
    	//orderService.checkUpdateOrderStatus(orderService.findOne(billOrderVo.getOrderId()));
		return ResultVOUtil.success("支付成功！");
	}

}

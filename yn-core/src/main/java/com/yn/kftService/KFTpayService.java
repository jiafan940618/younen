package com.yn.kftService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.lycheepay.gateway.client.GBPService;
import com.lycheepay.gateway.client.GatewayClientException;
import com.lycheepay.gateway.client.KftService;
import com.lycheepay.gateway.client.dto.gbp.CancelTreatyDTO;
import com.lycheepay.gateway.client.dto.gbp.CancelTreatyResultDTO;
import com.lycheepay.gateway.client.dto.gbp.SearchTreatyDTO;
import com.lycheepay.gateway.client.dto.gbp.SearchTreatyResultDTO;
import com.lycheepay.gateway.client.dto.gbp.TreatyApplyDTO;
import com.lycheepay.gateway.client.dto.gbp.TreatyApplyResultDTO;
import com.lycheepay.gateway.client.dto.gbp.TreatyCollectDTO;
import com.lycheepay.gateway.client.dto.gbp.TreatyCollectResultDTO;
import com.lycheepay.gateway.client.dto.gbp.TreatyConfirmDTO;
import com.lycheepay.gateway.client.dto.gbp.TreatyConfirmResultDTO;
import com.lycheepay.gateway.client.security.KeystoreSignProvider;
import com.lycheepay.gateway.client.security.SignProvider;
import com.yn.model.BankCard;
import com.yn.service.BankCardService;
import com.yn.vo.BillOrderVo;

@Service

public class KFTpayService {
	
	private static final Logger logger = LoggerFactory.getLogger(KFTpayService.class);

	@Autowired
	private  BankCardService bankCardService;
	

	private static KftService service;
	private static GBPService gbpService;
   //2017092300132528
	//初始化KftService
	public static void init() throws Exception {
		//初始化证书
		//证书类型、证书路径、证书密码、别名、证书容器密码
		 String pfxPath=null;
			try {
				 File directory = new File("");// 参数为空
				pfxPath = directory.getCanonicalPath()+"\\privateKey\\pfx.pfx";
				 System.out.println("项目路径为：-- --- -- -- - - - - - -"+pfxPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		SignProvider keystoreSignProvider = new KeystoreSignProvider("PKCS12", pfxPath, "123456".toCharArray(), null,"123456".toCharArray());
		//签名提供者、商户服务器IP(callerIp)、下载文件路径(暂时没用)
		service = new KftService(keystoreSignProvider, "192.168.0.104", "zh_CN", "c:/zip");
		service.setHttpPort(6443);////测试环境端口6443,生产环境端口8443,不设置默认8443
		service.setConnectionTimeoutSeconds(1*60);//设置的交易连接超时时间要大于0小于2分钟,单位:秒.0表示不超时,一直等待,默认30秒
		service.setResponseTimeoutSeconds(10*60);//设置的交易响应超时时间,要大于0小于10分钟,单位:秒.0表示不超时,一直等待,默认5分钟,ps：对应获取对账文件这个应该设长一点时间
		//gbpService,cbcsService超时时间同理设置
		gbpService = new GBPService(keystoreSignProvider, "192.168.0.104", "zh_CN", "c:/zip");
		gbpService.setHttpPort(6443);////测试环境端口6443,生产环境端口8443,不设置默认8443
		gbpService.setConnectionTimeoutSeconds(1*60);
		gbpService.setResponseTimeoutSeconds(10*60);
		
	}
	
	//快捷协议代扣协议申请(快捷协议代扣步骤1)
		public  void treatyCollectApply(BillOrderVo billOrderVo) throws GatewayClientException {
		BankCard bankCard =	bankCardService.selectBank(billOrderVo.getUserId());


			TreatyApplyDTO dto = new TreatyApplyDTO();
			dto.setService("gbp_treaty_collect_apply");//接口名称，固定不变
			dto.setVersion("1.0.0-IEST");//接口版本号，测试:1.0.0-IEST,生产:1.0.0-PRD
			dto.setMerchantId("2014030600048235");//替换成快付通提供的商户ID，测试生产不一样

			dto.setOrderNo(billOrderVo.getTradeNo());//订单号同一个商户必须保证唯一
			dto.setTreatyType("11");//11借计卡扣款  12信用卡扣款
			dto.setNote("协议说明");//协议简要说明，可空
			Date date = new Date(); 
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			
			dto.setStartDate(dateFormat.format(date));//协议生效日期,日期格式yyyyMMdd
			dto.setEndDate(Integer.parseInt(dateFormat.format(date))+30000+"");//协议失效日期,日期格式yyyyMMdd
			dto.setHolderName(bankCard.getRealName());//持卡人真实姓名
			dto.setBankType("1051000");//银行卡行别，测试环境只支持建行卡
			dto.setBankCardType("1");//0存折 1借记 2贷记
			dto.setBankCardNo(bankCard.getBankNum());//银行卡号
			dto.setMobileNo(bankCard.getPhone());//银行预留手机号
			dto.setCertificateType("0");//持卡人证件类型，0身份证
			dto.setCertificateNo(bankCard.getIdCardNum());//证件号码

//			dto.setCustCardValidDate("信用卡有效期");//可空，信用卡扣款时必填
//			dto.setCustCardCvv2("信用卡cvv2");//可空，信用卡扣款时必填
			System.out.println("请求信息为：" + dto.toString());
			TreatyApplyResultDTO result = gbpService.treatyCollectApply(dto);//发往快付通验证并返回结果
			logger.info("------- ----- ------- ---- ------ ----- 订单编号："+result.getOrderNo());
			logger.info("------- ----- ------- ---- ------ ----- smsSeq： "+result.getSmsSeq());
			logger.info("------- ----- ------- ---- ------ -----请求编号为： "+result.getReqNo());
			logger.info("------- ----- ------- ---- ------ ----- 状态为："+result.getStatus());
			System.out.println("响应信息为:" + result.toString());
		}
		
		//快捷协议代扣协议确定(快捷协议代扣步骤2)
		public static void confirmTreatyCollectApply(TreatyApplyResultDTO resultdto,BankCard bankCard) throws GatewayClientException {

			TreatyConfirmDTO dto = new TreatyConfirmDTO();
			dto.setService("gbp_confirm_treaty_collect_apply");//接口名称，固定不变
			dto.setVersion("1.0.0-IEST");//接口版本号，测试:1.0.0-IEST,生产:1.0.0-PRD
			dto.setMerchantId("2014030600048235");//替换成快付通提供的商户ID，测试生产不一样
			dto.setOrderNo(resultdto.getOrderNo());//同协议代扣申请订单号一致
			dto.setSmsSeq(resultdto.getSmsSeq());//协议代扣申请返回的短信流水号
			dto.setAuthCode("145420");
			dto.setHolderName(bankCard.getRealName());//持卡人姓名，与申请时一致
			dto.setBankCardNo(bankCard.getIdCardNum());//银行卡号，与申请时一致

			System.out.println("请求信息为：" + dto.toString());
			TreatyConfirmResultDTO result = gbpService.confirmTreatyCollectApply(dto);//发往快付通验证并返回结果
			System.out.println("响应信息为:" + result.toString());
		}
		//orderNo=2017082109302989566, status=1, treatyId=20170923035725
		//快捷协议代扣(协议代扣步骤3)

		public static void treatyCollect(TreatyConfirmResultDTO resultdto,BankCard bankCard) throws GatewayClientException {

			TreatyCollectDTO dto = new TreatyCollectDTO();
			dto.setService("gbp_treaty_collect");//接口名称，固定不变
			dto.setVersion("1.0.0-IEST");//接口版本号，测试:1.0.0-IEST,生产:1.0.0-PRD
			dto.setMerchantId("2014030600048235");//替换成快付通提供的商户ID，测试生产不一样
			dto.setProductNo("2ACB0BBA");//替换成快付通提供的产品编号，测试生产不一样
			dto.setOrderNo(resultdto.getOrderNo());//订单号同一个商户必须保证唯一
			dto.setTreatyNo(resultdto.getTreatyId());//协议代扣申请确认返回的协议号
			dto.setAmount("100");//此次交易的具体金额,单位:分,不支持小数点
			dto.setCurrency("CNY");//快付通定义的扣费币种,详情请看文档
			dto.setHolderName(bankCard.getRealName());//持卡人姓名，与申请时一致
			dto.setBankType("1051000");//客户银行账户行别;快付通定义的行别号,详情请看文档
			dto.setBankCardNo(bankCard.getIdCardNum());//银行卡号，与申请时一致，本次交易中,从客户的哪张卡上扣钱

			dto.setMerchantBankAccountNo("商户对公账号");//商户用于收款的银行账户,资金不落地模式时必填（重要参数）
			System.out.println("请求信息为：" + dto.toString());
			TreatyCollectResultDTO result = gbpService.treatyCollect(dto);//发往快付通验证并返回结果
			System.out.println("响应信息为:" + result.toString());
		}
		
		
		//快捷协议代扣协议查询
		public static void queryTreatyInfo() throws GatewayClientException {
			SearchTreatyDTO dto = new SearchTreatyDTO();
			dto.setService("gbp_query_treaty_info");//接口名称，固定不变
			dto.setVersion("1.0.0-IEST");//接口版本号，测试:1.0.0-IEST,生产:1.0.0-PRD
			dto.setMerchantId("2014030600048235");//替换成快付通提供的商户ID，测试生产不一样
			dto.setOrderNo("201706230000002");//同协议代扣申请订单号一致
			dto.setTreatyNo("20170923035725");//协议代扣申请确认返回的协议号
			System.out.println("请求信息为：" + dto.toString());
			SearchTreatyResultDTO result = gbpService.queryTreatyInfo(dto);//发往快付通验证并返回结果
			System.out.println("响应信息为:" + result.toString());
		}
		
		//快捷协议代扣协议解除
		public static  void cancelTreatyInfo() throws GatewayClientException {
			CancelTreatyDTO dto = new CancelTreatyDTO();
			dto.setService("gbp_cancel_treaty_info");//接口名称，固定不变
			dto.setVersion("1.0.0-IEST");//接口版本号，测试:1.0.0-IEST,生产:1.0.0-PRD
			dto.setMerchantId("2014030600048235");//替换成快付通提供的商户ID，测试生产不一样
			dto.setOrderNo("2017082109302989566");//同协议代扣申请订单号一致
			dto.setTreatyNo("20170923035725");//协议代扣申请确认返回的协议号
			System.out.println("请求信息为：" + dto.toString());
			CancelTreatyResultDTO result = gbpService.cancelTreatyInfo(dto);//发往快付通验证并返回结果
			System.out.println("响应信息为:" + result.toString());
		}

		
		public static void main(String[] args) {
			try {
				init();
				//treatyCollectApply();
				
			//	confirmTreatyCollectApply();
				 
				//treatyCollect();
				queryTreatyInfo();
				
				//cancelTreatyInfo();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}

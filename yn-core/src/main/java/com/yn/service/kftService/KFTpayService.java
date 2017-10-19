package com.yn.service.kftService;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lycheepay.gateway.client.GBPService;
import com.lycheepay.gateway.client.GatewayClientException;
import com.lycheepay.gateway.client.KftService;
import com.lycheepay.gateway.client.dto.AccountBalanceChangeRecordRequestDTO;
import com.lycheepay.gateway.client.dto.AccountBalanceChangeRecordResultDTO;
import com.lycheepay.gateway.client.dto.PayToBankAccountDTO;
import com.lycheepay.gateway.client.dto.QueryBatchFileResultDTO;
import com.lycheepay.gateway.client.dto.QueryReconResultRequestDTO;
import com.lycheepay.gateway.client.dto.QueryTradeRecordDTO;
import com.lycheepay.gateway.client.dto.QueryTradeRecordResultDTO;
import com.lycheepay.gateway.client.dto.TradeResultDTO;
import com.lycheepay.gateway.client.dto.QueryTradeRecordDTO.Criteria;
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
import com.lycheepay.gateway.client.dto.gebp.CapitalAccountQueryDTO;
import com.lycheepay.gateway.client.dto.gebp.CapitalAccountQueryResultDTO;
import com.lycheepay.gateway.client.security.KeystoreSignProvider;
import com.lycheepay.gateway.client.security.SignProvider;
import com.yn.model.BankCard;
import com.yn.model.BillOrder;
import com.yn.model.BillWithdrawals;
import com.yn.model.Recharge;
import com.yn.model.Wallet;
import com.yn.service.BankCardService;
import com.yn.service.BillOrderService;
import com.yn.service.BillWithdrawalsService;
import com.yn.service.OrderService;
import com.yn.service.TransactionRecordService;
import com.yn.service.WalletService;
import com.yn.utils.PropertyUtils;
import com.yn.vo.BankCardVo;
import com.yn.vo.BillOrderVo;
import com.yn.vo.BillRefundVo;
import com.yn.vo.BillWithdrawalsVo;
import com.yn.vo.RechargeVo;
import com.yn.vo.re.ResultVOUtil;

@Service

public class KFTpayService {
	
	private static final Logger logger = LoggerFactory.getLogger(KFTpayService.class);

	private String merchantId =PropertyUtils.getProperty("merchantId");
	private String BankAccountNo = PropertyUtils.getProperty("BankAccountNo");
	@Autowired
	TransactionRecordService transactionRecordService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private  BankCardService bankCardService;
	@Autowired
	private BillOrderService billOrderService;
	@Autowired
	private RechargeService rechargeService;
	//walletService
	@Autowired
	private WalletService walletService;
	@Autowired
	BillWithdrawalsService billWithdrawalsService;
	

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
				pfxPath = directory.getCanonicalPath()+"/privateKey/pfx.pfx";
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
		public  TreatyApplyResultDTO treatyCollectApply(BankCardVo bankCardVo) throws GatewayClientException {
			BankCard bankCard =	 new BankCard();


			TreatyApplyDTO dto = new TreatyApplyDTO();
			dto.setService("gbp_treaty_collect_apply");//接口名称，固定不变
			dto.setVersion("1.0.0-IEST");//接口版本号，测试:1.0.0-IEST,生产:1.0.0-PRD
			dto.setMerchantId(merchantId);//替换成快付通提供的商户ID，测试生产不一样

			dto.setOrderNo(bankCardVo.getOrderNo());//订单号同一个商户必须保证唯一
			dto.setTreatyType(bankCardVo.getTreatyType());//11借计卡扣款  12信用卡扣款
			dto.setNote("协议说明");//协议简要说明，可空
			Date date = new Date(); 
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			
			dto.setStartDate(dateFormat.format(date));//协议生效日期,日期格式yyyyMMdd
			dto.setEndDate(Integer.parseInt(dateFormat.format(date))+30000+"");//协议失效日期,日期格式yyyyMMdd
			dto.setHolderName(bankCardVo.getRealName());//持卡人真实姓名
			dto.setBankType(bankCardVo.getBankNo());//银行卡行别，测试环境只支持建行卡
			if(bankCardVo.getTreatyType().equals("11")){
				dto.setBankCardType("1");//0存折 1借记 2贷记
			}else if(bankCardVo.getTreatyType().equals("12")){
				dto.setBankCardType("2");//0存折 1借记 2贷记
			}
			
			
			dto.setBankCardNo(bankCardVo.getBankCardNum());//银行卡号
			dto.setMobileNo(bankCardVo.getPhone());//银行预留手机号
			dto.setCertificateType("0");//持卡人证件类型，0身份证
			dto.setCertificateNo(bankCardVo.getIdCardNum());//证件号码

//			dto.setCustCardValidDate("信用卡有效期");//可空，信用卡扣款时必填
//			dto.setCustCardCvv2("信用卡cvv2");//可空，信用卡扣款时必填
			System.out.println("请求信息为：" + dto.toString());
			TreatyApplyResultDTO result = gbpService.treatyCollectApply(dto);//发往快付通验证并返回结果
			logger.info("------- ----- ------- ---- ------ ----- 订单编号："+result.getOrderNo());
			logger.info("------- ----- ------- ---- ------ ----- smsSeq： "+result.getSmsSeq());
			logger.info("------- ----- ------- ---- ------ -----请求编号为： "+result.getReqNo());
			logger.info("------- ----- ------- ---- ------ ----- 状态为："+result.getStatus());
			System.out.println("响应信息为:" + result.toString());
			return result;
		}
		
		//[orderNo=8oy215064771236274921, smsSeq=2017092700132967, status=1, failureDetails=null, errorCode=null]
		//快捷协议代扣协议确定(快捷协议代扣步骤2)
		public  TreatyConfirmResultDTO confirmTreatyCollectApply(TreatyApplyResultDTO resultdto,BankCardVo bankCardVo) throws GatewayClientException {

			TreatyConfirmDTO dto = new TreatyConfirmDTO();
			dto.setService("gbp_confirm_treaty_collect_apply");//接口名称，固定不变
			dto.setVersion("1.0.0-IEST");//接口版本号，测试:1.0.0-IEST,生产:1.0.0-PRD
			dto.setMerchantId(merchantId);//替换成快付通提供的商户ID，测试生产不一样
			dto.setOrderNo(resultdto.getOrderNo());//同协议代扣申请订单号一致
			dto.setSmsSeq(resultdto.getSmsSeq());//协议代扣申请返回的短信流水号
			dto.setAuthCode("145420");
			dto.setHolderName(bankCardVo.getRealName());//持卡人姓名，与申请时一致
			dto.setBankCardNo(bankCardVo.getBankCardNum());//银行卡号，与申请时一致

			System.out.println("请求信息为：" + dto.toString());
			TreatyConfirmResultDTO result = gbpService.confirmTreatyCollectApply(dto);//发往快付通验证并返回结果
			System.out.println("响应信息为:" + result.toString());
			
			return result;
		}
		//orderNo=2017082109302989566, status=1, treatyId=20170923035725
		//快捷协议代扣(协议代扣步骤3)

		public Object treatyCollect(BillOrderVo billOrderVo,BankCard bankCard) throws GatewayClientException {

			TreatyCollectDTO dto = new TreatyCollectDTO();
			dto.setService("gbp_treaty_collect");//接口名称，固定不变
			dto.setVersion("1.0.0-IEST");//接口版本号，测试:1.0.0-IEST,生产:1.0.0-PRD
			dto.setMerchantId(merchantId);//替换成快付通提供的商户ID，测试生产不一样
			dto.setProductNo("2ACB0BBA");//替换成快付通提供的产品编号，测试生产不一样
			dto.setOrderNo(billOrderVo.getTradeNo());//订单号同一个商户必须保证唯一
			dto.setTreatyNo(bankCard.getTreatyId());//协议代扣申请确认返回的协议号
			dto.setAmount(billOrderVo.getMoney().toString());//此次交易的具体金额,单位:分,不支持小数点
			dto.setCurrency("CNY");//快付通定义的扣费币种,详情请看文档
			dto.setHolderName(bankCard.getRealName());//持卡人姓名，与申请时一致
			dto.setBankType(bankCard.getOrderNo());//客户银行账户行别;快付通定义的行别号,详情请看文档
			dto.setBankCardNo(bankCard.getBankCardNum());//银行卡号，与申请时一致，本次交易中,从客户的哪张卡上扣钱

			dto.setMerchantBankAccountNo(BankAccountNo);//商户用于收款的银行账户,资金不落地模式时必填（重要参数）
			System.out.println("请求信息为：" + dto.toString());
			TreatyCollectResultDTO result= gbpService.treatyCollect(dto);
		//发往快付通验证并返回结果
			System.out.println("响应信息为:" + result.toString());
			
			BillOrder billOrder = new BillOrder();
			billOrder.setOrderId(billOrderVo.getOrderId());
			billOrder.setUserId(billOrderVo.getUserId());
			billOrder.setMoney(billOrderVo.getMoney().doubleValue());
			billOrder.setTradeNo(billOrderVo.getTradeNo());
	    	billOrder.setPayWay(5);
	  

			if(result.getStatus()==1){
				billOrder.setStatus(0);
				billOrderService.newsave(billOrder);
				
				
				/** 修改订单记录状态*/
				billOrderService.updateOrder(billOrder.getTradeNo());
            	/** 修改订单金额,及3步走，支付状态*/
            	//orderService.UpdateOrStatus(billOrder.getTradeNo(),billOrderVo.getMoney().doubleValue());

            	 /** 不在这里修改状态*/
            //orderService.givePrice(orderService.FindByTradeNo(billOrder.getTradeNo()));
				
            	transactionRecordService.InsertBillAll(billOrder);
				 return ResultVOUtil.success("支付成功!");
			}else{
				logger.info("============ ============= ============== ========="+result.getFailureDetails());
				billOrder.setRemark(result.getErrorCode()+":"+result.getFailureDetails());
				billOrder.setStatus(1);
				billOrderService.save(billOrder);

				transactionRecordService.InsertBillAll(billOrder);
				
			  return ResultVOUtil.error(777, result.getFailureDetails());
			}	
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
		public   Object cancelTreatyInfo(BankCard bankCard) throws GatewayClientException {
			CancelTreatyDTO dto = new CancelTreatyDTO();
			dto.setService("gbp_cancel_treaty_info");//接口名称，固定不变
			dto.setVersion("1.0.0-IEST");//接口版本号，测试:1.0.0-IEST,生产:1.0.0-PRD
			dto.setMerchantId(merchantId);//替换成快付通提供的商户ID，测试生产不一样
			dto.setOrderNo(bankCard.getOrderNo());//同协议代扣申请订单号一致
			dto.setTreatyNo(bankCard.getTreatyId());//协议代扣申请确认返回的协议号
			System.out.println("请求信息为：" + dto.toString());
			CancelTreatyResultDTO result = gbpService.cancelTreatyInfo(dto);//发往快付通验证并返回结果
			System.out.println("响应信息为:" + result.toString());
			if(result.getStatus()==1){
				bankCardService.delete(bankCard.getId());
				
				return ResultVOUtil.success("解绑成功!");
			}else{
				
				return ResultVOUtil.error(777, result.getFailureDetails());
			}

		}
		
		/*** 用于充值的*/
		public Object rechargeCollect(RechargeVo rechargeVo,BankCard bankCard) throws GatewayClientException {

			TreatyCollectDTO dto = new TreatyCollectDTO();
			dto.setService("gbp_treaty_collect");//接口名称，固定不变
			dto.setVersion("1.0.0-IEST");//接口版本号，测试:1.0.0-IEST,生产:1.0.0-PRD
			dto.setMerchantId("2017062300091037");//替换成快付通提供的商户ID，测试生产不一样
			dto.setProductNo("2ACB0BBA");//替换成快付通提供的产品编号，测试生产不一样
			dto.setOrderNo(rechargeVo.getRechargeCode());//订单号同一个商户必须保证唯一
			dto.setTreatyNo(bankCard.getTreatyId());//协议代扣申请确认返回的协议号
			dto.setAmount(rechargeVo.getMoney().toString());//此次交易的具体金额,单位:分,不支持小数点
			dto.setCurrency("CNY");//快付通定义的扣费币种,详情请看文档
			dto.setHolderName(bankCard.getRealName());//持卡人姓名，与申请时一致
			dto.setBankType(bankCard.getOrderNo());//客户银行账户行别;快付通定义的行别号,详情请看文档
			dto.setBankCardNo(bankCard.getBankCardNum());//银行卡号，与申请时一致，本次交易中,从客户的哪张卡上扣钱

			dto.setMerchantBankAccountNo(BankAccountNo);//商户用于收款的银行账户,资金不落地模式时必填（重要参数）
			System.out.println("请求信息为：" + dto.toString());
			TreatyCollectResultDTO result= gbpService.treatyCollect(dto);
		//发往快付通验证并返回结果
			System.out.println("响应信息为:" + result.toString());
			
			Recharge recharge = new Recharge();
			recharge.setWalletId(rechargeVo.getWalletId());
			recharge.setMoney(rechargeVo.getMoney().doubleValue()*0.01);
			recharge.setRechargeCode(rechargeVo.getRechargeCode());
			recharge.setPayWay(rechargeVo.getPayWay());
			recharge.setDel(0);
			recharge.setStatus(1);
			rechargeService.save(recharge);
	  

			if(result.getStatus()==1){
				
				Recharge recharge01 = new Recharge();
            	recharge.setRechargeCode(rechargeVo.getRechargeCode());
            	recharge.setStatus(0);
            	
            	rechargeService.updateRecharge(recharge01);
            	
            	/** 根据订单号查询金额 */
            	RechargeVo rechargeVo01 = rechargeService.findRecharge(recharge01);
            	
            	BigDecimal addMoney = rechargeVo.getMoney().add(rechargeVo01.getTotalmoney());
            	
            	 /** 在钱包哪里添加充值订单号*/
            	Wallet wallet = new Wallet();
            	wallet.setMoney(addMoney);
            	wallet.setId(rechargeVo01.getWalletId());
            	 /** 修改用户的钱包金额*/	                	
            	walletService.updatePrice(wallet);
            	
            	transactionRecordService.InsertBillAll(recharge);
				
				 return ResultVOUtil.success("充值成功!");
			}else{
				logger.info("============ ============= ============== ========="+result.getFailureDetails());
				recharge.setRemark(result.getErrorCode()+":"+result.getFailureDetails());
				recharge.setStatus(1);
				rechargeService.save(recharge);

				transactionRecordService.InsertBillAll(recharge);
				
			  return ResultVOUtil.error(777, result.getFailureDetails());
			}	
		}
		
		public  Object payToBankAccount(BillWithdrawalsVo billWithdrawalsVo) throws GatewayClientException {
			PayToBankAccountDTO dto = new PayToBankAccountDTO();
			dto.setService("gbp_pay");//接口名称，固定不变
			dto.setVersion("1.0.0-IEST");//接口版本号，测试:1.0.0-IEST,生产:1.0.0-PRD
			dto.setMerchantId(merchantId);//替换成快付通提供的商户ID，测试生产不一样
			dto.setProductNo("2BA00BBA");//替换成快付通提供的产品编号，测试生产不一样
			dto.setOrderNo(billWithdrawalsVo.getTradeNo());//订单号同一个商户必须保证唯一
			dto.setTradeName("单笔付款测试交易");//简要概括此次交易的内容
			dto.setMerchantBankAccountNo("商户对公账号");//商户用于付款的银行账户,资金不落地模式时必填（重要参数）		

			dto.setTradeTime(new Date());//交易时间,注意此时间取值一般为商户方系统时间而非快付通生成此时间
			dto.setAmount("60");//交易金额，单位：分，不支持小数点
			dto.setCurrency("CNY");//快付通定义的扣费币种,详情请看文档
			dto.setCustBankNo(billWithdrawalsVo.getBankNo());//客户银行帐户银行别，测试环境只支持：中、农、建
			dto.setCustBankAccountNo(billWithdrawalsVo.getBankCardNum());//本次交易中,付款到客户哪张卡
			dto.setCustName(billWithdrawalsVo.getRealName());//客户银行卡户名
			dto.setRemark("单笔收款");//商户可额外填写付款方备注信息,此信息会传给银行,会在银行的账单信息中显示(具体如何显示取决于银行方,快付通不保证银行肯定能显示)
			System.out.println("请求信息为:" + dto.toString());
			TradeResultDTO result = service.payToBankAccount(dto);//发往快付通验证并返回结果
			System.out.println("响应信息为:" + result.toString());
			
			//TradeResultDTO [orderNo=8o6615079499770435677, status=1, errorCode=, failureDetails=]
			
			BillWithdrawals billWithdrawals = new BillWithdrawals();
			billWithdrawals.setStatus(0);
			billWithdrawals.setDel(0);
			billWithdrawals.setWalletId(billWithdrawalsVo.getWalletId());
			billWithdrawals.setUserId(billWithdrawalsVo.getUserId());
			billWithdrawals.setTradeNo(billWithdrawalsVo.getTradeNo());
			billWithdrawals.setMoney(billWithdrawalsVo.getMoney());
			billWithdrawals.setBankName(billWithdrawalsVo.getBankName());
			billWithdrawals.setBankCardNum(billWithdrawalsVo.getBankCardNum());
			billWithdrawals.setRealName(billWithdrawalsVo.getRealName());
			billWithdrawals.setPhone(billWithdrawalsVo.getPhone());
			
			billWithdrawalsService.save(billWithdrawals);
			
			
			if(result.getStatus() == 1){
				billWithdrawals.setStatus(1);
				
				Wallet wallet  = walletService.findWalletByUser(billWithdrawals.getUserId());
				 //subtract
				BigDecimal money = wallet.getMoney().subtract(BigDecimal.valueOf(billWithdrawals.getMoney()));
				
				wallet.setMoney(money);
				
				walletService.updatePrice(wallet);
				
				transactionRecordService.InsertBillAll(billWithdrawals);
				
				return ResultVOUtil.success("提现成功!");
			}else{
				billWithdrawals.setStatus(2);
				billWithdrawals.setRemark(result.getFailureDetails());
				
				transactionRecordService.InsertBillAll(billWithdrawals);
				billWithdrawalsService.save(billWithdrawals);
				 return ResultVOUtil.error(777, "抱歉,提现失败,详情请咨询客服!");	
			}	
		}
	
	
		//查询交易结果
		public void queryTradeRecord() throws GatewayClientException {
			QueryTradeRecordDTO dto = new QueryTradeRecordDTO();
			dto.setService("trade_record_query");//接口名称，固定不变
			dto.setVersion("1.0.0-IEST");//接口版本号，测试:1.0.0-IEST,生产:1.0.0-PRD
			dto.setMerchantId("商户ID");//替换成快付通提供的商户ID，测试生产不一样
			dto.setProductNo("查询接口的产品编号");//替换成快付通提供的产品编号，测试生产不一样
			dto.addCriteria(dto.new Criteria("原交易的产品编号", "", "原交易订单号"))//第二个参数为空
					.addCriteria(dto.new Criteria("原交易的产品编号", "", "原交易订单号"));//一次可查询多个订单,最多200条
			System.out.println("请求信息为:" + dto.toString());
			QueryTradeRecordResultDTO result = service.queryTradeRecord(dto);//发往快付通验证并返回结果
			System.out.println("响应信息为:" + result.toString());
		}
		
		//查询帐户余额
		public void queryCapitalAccounts() throws GatewayClientException {
			CapitalAccountQueryDTO dto = new CapitalAccountQueryDTO();
			dto.setService("query_available_balance");//接口名称，固定不变
			dto.setVersion("1.0.0-IEST");//接口版本号，测试:1.0.0-IEST,生产:1.0.0-PRD
			dto.setMerchantId("商户ID");//替换成快付通提供的商户ID，测试生产不一样
			dto.setProductNo("产品编号");//替换成快付通提供的产品编号，测试生产不一样
			System.out.println("请求信息为:" + dto.toString());
			CapitalAccountQueryResultDTO result = service.queryCapitalAccounts(dto);//发往快付通验证并返回结果
			System.out.println("响应信息为:" + result.toString());
		}

		//查询资金变动明细
		public  void accountBalanceChangeRecordQuery() throws GatewayClientException{
			AccountBalanceChangeRecordRequestDTO dto = new AccountBalanceChangeRecordRequestDTO();
			dto.setService("capital_account_balance_change_query");//接口名称，固定不变
			dto.setVersion("1.0.0-IEST");//接口版本号，测试:1.0.0-IEST,生产:1.0.0-PRD
			dto.setMerchantId("商户ID");//替换成快付通提供的商户ID，测试生产不一样
			dto.setProductNo("查询接口的产品编号");//替换成快付通提供的产品编号，测试生产不一样
			dto.setTradeDate("20160309");//查询日期格式由YYYYMMDD只能查询今天以前的资金变动
			File file = new File("D:\\result.json");//指定资金变动明细文件存放路径
			dto.setDownloadFile(file);
			System.out.println("请求信息为:" + dto.toString());
			AccountBalanceChangeRecordResultDTO result = service.queryAccountBalanceChangeRecords(dto);//发往快付通验证并返回结果
			System.out.println("响应信息为:" + result.toString());
		}
		
		//查询对帐文件
		public void queryReconResult() throws GatewayClientException, IOException {
			QueryReconResultRequestDTO dto = new QueryReconResultRequestDTO();
			dto.setService("recon_result_query");//接口名称，固定不变
			dto.setVersion("1.0.0-IEST");//接口版本号，测试:1.0.0-IEST,生产:1.0.0-PRD
			dto.setMerchantId("商户ID");//替换成快付通提供的商户ID，测试生产不一样
			dto.setProductNo("查询对账接口的产品编号");//替换成快付通提供的产品编号，测试生产不一样
			dto.setQueryCriteria("原交易产品编号,查询起始日期,查询结束日期");//"2ACCCCC,20120101,20140101"
			File file = new File("D:\\result.json");//指定对账文件存放路径
			dto.setDownloadFile(file);
			System.out.println("请求信息为:" + dto.toString());
			QueryBatchFileResultDTO result = service.queryReconResult(dto);//发往快付通验证并返回结果
			System.out.println("响应信息为:" + result.toString());
		}
		
		
	
		
		

		
	/*	public static void main(String[] args) {
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
		}*/
}

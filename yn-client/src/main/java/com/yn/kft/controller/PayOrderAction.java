package com.yn.kft.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lycheepay.gateway.client.dto.gbp.TreatyApplyResultDTO;
import com.lycheepay.gateway.client.dto.gbp.TreatyConfirmResultDTO;
import com.yn.model.BankCard;
import com.yn.model.BankCode;
import com.yn.service.BankCardService;
import com.yn.service.BankCodeService;
import com.yn.service.ServerService;
import com.yn.service.kftService.CheckBankCard;
import com.yn.service.kftService.IdcardUtil;
import com.yn.service.kftService.KFTpayService;
import com.yn.utils.BeanCopy;
import com.yn.utils.Constant;
import com.yn.utils.PhoneFormatCheckUtils;
import com.yn.vo.BankCardVo;
import com.yn.vo.BillOrderVo;
import com.yn.vo.RechargeVo;
import com.yn.vo.re.ResultVOUtil;

//http://3ad9d9c5.ngrok.io/client/payOrder/createPay

/**
 * 提示
 * 
 * */
@Controller
@RequestMapping(value="/client/payOrder")
public class PayOrderAction {
	
	private static final Logger logger = LoggerFactory.getLogger(PayOrderAction.class);
	@Autowired
	CheckBankCard checkBankcard;
	@Autowired
	IdcardUtil idcardUtil;
	@Autowired
	private BankCodeService bankCodeService;
	@Autowired
	private BankCardService bankCardService;
	@Autowired
	private KFTpayService kftpayService;
	@Autowired
	private ServerService serverService;
	
	/** 得先判断该用户是否绑定银行卡*/
	/** 使用快通支付*/
	@ResponseBody
	@RequestMapping(value="/bind")
	public  Object getBindIng(BillOrderVo billOrderVo){
		
		List<BankCardVo> list = new ArrayList<BankCardVo>();

	    if(null!=billOrderVo.getUserId()){
			
			 list =bankCardService.selectBank(billOrderVo.getUserId());
			
				if(list.size()==0){
					logger.info("----- ----- ------- ------ ----- ------ 该用户没有绑定银行卡");
					return ResultVOUtil.error(777, Constant.BANK_CODE_ERROR);
				}
				return ResultVOUtil.success(list);
		}

		return ResultVOUtil.success(null);
	}
	
	@ResponseBody
	@RequestMapping(value="/bindIng")
	public  Object getdIng(BillOrderVo billOrderVo,BankCardVo bankCardVo){
		/** 测试数据*/
		billOrderVo.setTradeNo(serverService.getOrderCode(billOrderVo.getOrderId()));
		/*billOrderVo.setOrderId(1L);
		billOrderVo.setUserId(3L);
		BigDecimal xmoney = BigDecimal.valueOf(100);
		billOrderVo.setMoney(xmoney);
		bankCardVo.setTreatyId("20170927035820");*/
		logger.info("======= ========= ======== =======传递的OrderId:"+billOrderVo.getUserId());
		logger.info("======= ========= ======== =======传递的UserId:"+billOrderVo.getUserId());
		logger.info("======= ========= ======== =======传递的TradeNo:"+billOrderVo.getTradeNo());
		logger.info("======= ========= ======== =======传递的money:"+billOrderVo.getMoney());
		
		logger.info("======= ========= ======== =======传递的协议号TreatyId:"+bankCardVo.getTreatyId());
		
		
		logger.info("----- ----- ------- ------ ---- 协议号为："+bankCardVo.getTreatyId());
		 if (null!=bankCardVo.getTreatyId() && !bankCardVo.getTreatyId().equals("") ){
		    	
		    	try {
		    		kftpayService.init();
		    		
		    		BankCard bankCard=bankCardService.findByTreatyId(bankCardVo.getTreatyId());	
		    		
		    		return	kftpayService.treatyCollect(billOrderVo,bankCard);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }

		logger.info("=========== =============== =========== 没有该协议号!");
		
		return ResultVOUtil.error(777, "没有协议号!");
	}
	
	/*bankCardVo.setBankId(5);
	bankCardVo.setUserId(3L);
	bankCardVo.setBankCardNum("6217902000000879543");
	bankCardVo.setTreatyType("11");
	bankCardVo.setRealName("不知道001");
	bankCardVo.setPhone("13124733745");
	bankCardVo.setIdCardNum("500382199401185412");
	bankCardVo.setBankNo("1051000");
	bankCardVo.setType(1);*/
		
	/** 绑定银行卡，接收俩个方法*/
	@ResponseBody
	@RequestMapping(value="/bindingCard")
	public  Object getBindcard(BankCardVo bankCardVo){
		
		/*bankCardVo.setBankCardNum("6212262201023557228");
		bankCardVo.setBankId(5);
		bankCardVo.setBankNo("1051000");
		bankCardVo.setIdCardNum("410526199307147372");
		bankCardVo.setPhone("18317829893");
		bankCardVo.setRealName("bankCardVo");
		bankCardVo.setTreatyType("11"); 
		
		bankCardVo.setUserId(1L);*/
		/** 测试数据*/
		logger.info("======= ========= ======== =======传递的用户id:userId:"+bankCardVo.getUserId());
		logger.info("======= ========= ======== =======传递的银行卡号:bankCardNum:"+bankCardVo.getBankCardNum());
		logger.info("======= ========= ======== =======传递的卡类型(11、借计卡扣款  12、信用卡扣款):treatyType:"+bankCardVo.getTreatyType());
		logger.info("======= ========= ======== =======传递的真实姓名:realName:"+bankCardVo.getRealName());
		logger.info("======= ========= ======== =======传递的电话号码:phone:"+bankCardVo.getPhone());
		logger.info("======= ========= ======== =======传递的身份证号:idCardNum:"+bankCardVo.getIdCardNum());
		logger.info("======= ========= ======== =======传递的银行的行号:bankNo:"+bankCardVo.getBankNo());
		logger.info("======= ========= ======== =======传递的用户选择的银行id:bankId:"+bankCardVo.getBankId());
		logger.info("======= ========= ======== =======传递的类型(1、个人账户):Type:"+bankCardVo.getType());
		
		
		if(null == bankCardVo.getBankCardNum() || bankCardVo.getBankCardNum().equals("") ){
			return ResultVOUtil.error(777, "银行卡号不能为空!");//Constant
		}
		if(null == bankCardVo.getTreatyType() || bankCardVo.getTreatyType().equals("") ){
			return ResultVOUtil.error(777, "请选择信用卡或银行卡!");//Constant
		}
		if(null == bankCardVo.getRealName() || bankCardVo.getRealName().equals("") ){
			return ResultVOUtil.error(777, "真实姓名不能为空!");//Constant
		}
		if(null == bankCardVo.getPhone() || bankCardVo.getPhone().equals("") ){
			return ResultVOUtil.error(777, Constant.PHONE_NULL);//Constant
		}
		if(null == bankCardVo.getIdCardNum() || bankCardVo.getIdCardNum().equals("") ){
			return ResultVOUtil.error(777, "身份证号不能为空!");//Constant
		}
		if(null == bankCardVo.getBankNo() || bankCardVo.getBankNo().equals("") ){
			return ResultVOUtil.error(777, "请选择银行!");//Constant
		}
		if(!idcardUtil.isIdcard(bankCardVo.getIdCardNum())){
			
			return ResultVOUtil.error(777, "抱歉,您的身份证号有误!");
		}
		if(!checkBankcard.checkBankCard(bankCardVo.getBankCardNum())){
			
			return ResultVOUtil.error(777, "抱歉,银行卡号有误,请确定您的卡号是否正确!");
		}
		if(!PhoneFormatCheckUtils.isPhoneLegal(bankCardVo.getPhone())){
			
			return ResultVOUtil.error(777, "抱歉,您的手机号有误!");
		}
		
		
	String orderNo = serverService.getOrderNo(bankCardVo.getUserId());
		logger.info("======= ========= ======== =======传递的orderNo:"+orderNo);
	bankCardVo.setOrderNo(orderNo);
		try {
			kftpayService.init();
			
			TreatyApplyResultDTO resultdto = kftpayService.treatyCollectApply(bankCardVo);
			
			if(resultdto.getStatus()==2){
				logger.info("========= =========== ========== ========="+resultdto.getErrorCode()+":"+resultdto.getFailureDetails());
				
				return ResultVOUtil.error(777, "出现错误,请联系客服,错误提示:"+resultdto.getErrorCode()+":"+resultdto.getFailureDetails());
			}
			
			if(resultdto.getStatus()!=1){
				logger.info("========= =========== ========== ========="+ resultdto.getErrorCode()+":"+resultdto.getFailureDetails());
				

				return ResultVOUtil.error(777, "出现错误,请联系客服,错误提示:"+resultdto.getErrorCode()+":"+resultdto.getFailureDetails());
			}

			TreatyConfirmResultDTO configdto=	kftpayService.confirmTreatyCollectApply(resultdto, bankCardVo);
			
			if(configdto.getStatus()!=1){
				logger.info("========= =========== ========== ========="+ configdto.getFailureDetails());
				
				return ResultVOUtil.error(777, "出现错误,请联系客服,错误提示:"+resultdto.getErrorCode()+":"+resultdto.getFailureDetails());
			}else{
				bankCardVo.setTreatyId(configdto.getTreatyId());
				
				BankCard bankCard = new BankCard();
			    BeanCopy.copyProperties(bankCardVo, bankCard);
				
			    bankCardService.save(bankCard);
				return ResultVOUtil.success("绑定银行卡成功!");
			}
   
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResultVOUtil.error(777, "绑定银行卡失败!");
	
		
	}
	

	
	/** 有一个用户的协议查询，判断是否有这个协议*/
	
	
	/** 解除绑定,软删除*/
	
	@ResponseBody
	@RequestMapping(value="/selectCard")
	public Object getdelete(BankCardVo bankCardVo){
		logger.info("----- ----- ------- ------ ---- 协议号为："+bankCardVo.getTreatyId());
		
	
	 if (null!=bankCardVo.getTreatyId() || bankCardVo.getTreatyId().equals("") ){
		 BankCard bankCard= bankCardService.findByBankcard(bankCardVo.getTreatyId());
			
			Object object =null;
		try {
			kftpayService.init();
			object=	kftpayService.cancelTreatyInfo(bankCard);
			
			return object;
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	return ResultVOUtil.error(777, "没有协议号,解绑失败!");
		
	}
	
	
	
	/** 根据userid查找绑定银行卡*/
	@ResponseBody
	@RequestMapping(value="/selectbank")
	public Object getbank(BillOrderVo billOrderVo){
		logger.info("---------- ------------ --------查找的userId:"+billOrderVo.getUserId());
		
		List<BankCardVo> list =bankCardService.selectBank(billOrderVo.getUserId());
	
		return ResultVOUtil.success(list);
	}	
	/*** 展示银行*/
	@ResponseBody
	@RequestMapping(value="/selectCode")
	public Object getBankCode(BankCode bankCode){
		
		List<BankCode> list = bankCodeService.findAll(bankCode);
		
		return ResultVOUtil.success(list);
	}
	
	/** Recharge充值*/
	@ResponseBody
	@RequestMapping(value="/RechargeIng")
	public  Object RechargedIng(RechargeVo rechargeVo,BankCardVo bankCardVo){
	/** 测试数据*/
		rechargeVo.setRechargeCode(serverService.getOrderCode(rechargeVo.getWalletId()));
		/*billOrderVo.setOrderId(1L);
		billOrderVo.setUserId(3L);
		BigDecimal xmoney = BigDecimal.valueOf(100);
		billOrderVo.setMoney(xmoney);
		bankCardVo.setTreatyId("20170927035820");*/
		//logger.info("======= ========= ======== =======传递的OrderId:"+billOrderVo.getUserId());
		logger.info("======= ========= ======== =======传递的WalletId():"+rechargeVo.getWalletId());
		logger.info("======= ========= ======== =======传递的TradeNo:"+rechargeVo.getRechargeCode());
		logger.info("======= ========= ======== =======传递的money:"+rechargeVo.getMoney());
		
		logger.info("======= ========= ======== =======传递的协议号TreatyId:"+bankCardVo.getTreatyId());
		
		
		logger.info("----- ----- ------- ------ ---- 协议号为："+bankCardVo.getTreatyId());
		 if (null!=bankCardVo.getTreatyId() && !bankCardVo.getTreatyId().equals("") ){
		    	
		    	try {
		    		kftpayService.init();
		    		
		    		BankCard bankCard=bankCardService.findByTreatyId(bankCardVo.getTreatyId());	
		    		
		    		return	kftpayService.rechargeCollect(rechargeVo, bankCard);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }

		logger.info("=========== =============== =========== 没有该协议号!");
		
		return ResultVOUtil.error(777, "没有协议号!");
	}
	
	
	
	
	

}

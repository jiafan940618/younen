package com.yn.kft.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lycheepay.gateway.client.GatewayClientException;
import com.lycheepay.gateway.client.KftBaseService;
import com.lycheepay.gateway.client.dto.gbp.TreatyApplyResultDTO;
import com.lycheepay.gateway.client.dto.gbp.TreatyConfirmResultDTO;
import com.yn.kftService.KFTpayService;
import com.yn.model.BankCard;
import com.yn.model.City;
import com.yn.service.BankCardService;
import com.yn.service.ServerService;
import com.yn.utils.BeanCopy;
import com.yn.utils.Constant;
import com.yn.vo.BankCardVo;
import com.yn.vo.BillOrderVo;
import com.yn.vo.re.ResultVOUtil;

//http://3ad9d9c5.ngrok.io/client/payOrder/createPay
@Controller
@RequestMapping(value="/client/payOrder")
public class PayOrderAction {
	
	private static final Logger logger = LoggerFactory.getLogger(PayOrderAction.class);
	
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
	public  Object getBindIng(BillOrderVo billOrderVo,BankCardVo bankCardVo){
		logger.info("----- ----- ------- ------ ----");
		List<BankCard> list = new ArrayList<BankCard>();
		
    if(null!=bankCardVo.getUserId()){
		
		 list =bankCardService.selectBank(bankCardVo.getUserId());
		
			if(list.size()==0){
				logger.info("----- ----- ------- ------ ----- ------ 该用户没有绑定银行卡");
				return ResultVOUtil.error(777, Constant.BANK_CODE_ERROR);
			}
			return ResultVOUtil.success(list);
	}
	
    if (null!=bankCardVo.getTreatyId() || bankCardVo.getTreatyId().equals("") ){
    	
    	try {
    		kftpayService.init();
    		
    		BankCard bankCard=bankCardService.findByTreatyId(bankCardVo.getTreatyId());	
    		
			kftpayService.treatyCollect(billOrderVo,bankCard);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

		return ResultVOUtil.success(null);
	}
	
	/** 绑定银行卡，接收俩个方法*/
	@ResponseBody
	@RequestMapping(value="/bindingCard")
	public  Object getBindcard(BankCardVo bankCardVo){
		bankCardVo.setUserId(3L);
		bankCardVo.setBankCardNum("62179020000008778945");
		bankCardVo.setTreatyType("11");
		bankCardVo.setRealName("不知道");
		bankCardVo.setPhone("13124733745");
		bankCardVo.setIdCardNum("500382199401185412");
		bankCardVo.setBankNo("1051000");
		bankCardVo.setType(0);
		
	String orderNo = serverService.getOrderNo(bankCardVo.getUserId());
		
	bankCardVo.setOrderNo(orderNo);
		try {
			kftpayService.init();
			
			TreatyApplyResultDTO resultdto = kftpayService.treatyCollectApply(bankCardVo);
			
			if(resultdto.getStatus()==2){
				logger.info("========= =========== ========== ========= 该协议号已存在，银行卡已绑定");
				
				return ResultVOUtil.error(777, "该银行卡已绑定!");
			}
			
			if(resultdto.getStatus()!=1){
				logger.info("========= =========== ========== ========="+ resultdto.getFailureDetails());
				
				return ResultVOUtil.error(777, resultdto.getFailureDetails());
			}

			TreatyConfirmResultDTO configdto=	kftpayService.confirmTreatyCollectApply(resultdto, bankCardVo);
			//[orderNo=8oud15064778614372975, status=1, treatyId=20170927035821, failureDetails=null, errorCode=null]
			if(configdto.getStatus()!=1){
				logger.info("========= =========== ========== ========="+ configdto.getFailureDetails());
				
				return ResultVOUtil.error(777, configdto.getFailureDetails());
			}
			
			bankCardVo.setTreatyId(configdto.getTreatyId());
			
			BankCard bankCard = new BankCard();
		    BeanCopy.copyProperties(bankCardVo, bankCard);
			
		    bankCardService.save(bankCard);
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return ResultVOUtil.success(null);
	}
	

	
	/** 有一个用户的协议查询，判断是否有这个协议*/
	
	
	/** 解除绑定,软删除*/
	
	@ResponseBody
	@RequestMapping(value="/selectCard")
	public Object getdelete(BankCardVo bankCardVo){
		
	BankCard bankCard= bankCardService.findByBankcard(bankCardVo.getTreatyId());
	
	Object object =null;
		try {
			kftpayService.init();
			kftpayService.cancelTreatyInfo(bankCard);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bankCardService;
	}
	
	
	
	
	
	
	
	
	
	

}

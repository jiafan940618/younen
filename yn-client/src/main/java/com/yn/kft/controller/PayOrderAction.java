package com.yn.kft.controller;

import java.math.BigDecimal;
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
import com.yn.kftService.KFTpayService;
import com.yn.model.BankCard;
import com.yn.model.BankCode;
import com.yn.service.BankCardService;
import com.yn.service.BankCodeService;
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
		
		List<BankCard> list = new ArrayList<BankCard>();

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
		logger.info("======= ========= ======== =======传递的amount:"+billOrderVo.getMoney());
		
		logger.info("======= ========= ======== =======传递的协议号TreatyId:"+bankCardVo.getTreatyId());
		
		
		logger.info("----- ----- ------- ------ ---- 协议号为："+bankCardVo.getTreatyId());
		 if (null!=bankCardVo.getTreatyId() || bankCardVo.getTreatyId().equals("") ){
		    	
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
	
	
	
	/** 绑定银行卡，接收俩个方法*/
	@ResponseBody
	@RequestMapping(value="/bindingCard")
	public  Object getBindcard(BankCardVo bankCardVo){
		/** 测试数据*/
		/*bankCardVo.setBankId(5);
		bankCardVo.setUserId(3L);
		bankCardVo.setBankCardNum("6217902000000879543");
		bankCardVo.setTreatyType("11");
		bankCardVo.setRealName("不知道001");
		bankCardVo.setPhone("13124733745");
		bankCardVo.setIdCardNum("500382199401185412");
		bankCardVo.setBankNo("1051000");
		bankCardVo.setType(1);*/
		logger.info("======= ========= ======== =======传递的UserId:"+bankCardVo.getUserId());
		logger.info("======= ========= ======== =======传递的BankCardNum:"+bankCardVo.getBankCardNum());
		logger.info("======= ========= ======== =======传递的TreatyType:"+bankCardVo.getTreatyType());
		logger.info("======= ========= ======== =======传递的RealName:"+bankCardVo.getRealName());
		logger.info("======= ========= ======== =======传递的Phone:"+bankCardVo.getPhone());
		logger.info("======= ========= ======== =======传递的IdCardNum:"+bankCardVo.getIdCardNum());
		logger.info("======= ========= ======== =======传递的BankNo:"+bankCardVo.getBankNo());
		logger.info("======= ========= ======== =======传递的bankId:"+bankCardVo.getBankId());
		logger.info("======= ========= ======== =======传递的Type:"+bankCardVo.getType());
		
	String orderNo = serverService.getOrderNo(bankCardVo.getUserId());
		logger.info("======= ========= ======== =======传递的orderNo:"+orderNo);
	bankCardVo.setOrderNo(orderNo);
		try {
			kftpayService.init();
			
			TreatyApplyResultDTO resultdto = kftpayService.treatyCollectApply(bankCardVo);
			
			if(resultdto.getStatus()==2){
				logger.info("========= =========== ========== ========="+resultdto.getFailureDetails());
				
				return ResultVOUtil.error(777, resultdto.getFailureDetails());
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
	
		return ResultVOUtil.success("绑定银行卡成功!");
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
	
	/*** 展示银行*/
	@ResponseBody
	@RequestMapping(value="/selectCode")
	public Object getBankCode(BankCode bankCode){
		
		List<BankCode> list = bankCodeService.findAll(bankCode);
		
		return ResultVOUtil.success(list);
	}
	
	
	
	
	
	
	

}

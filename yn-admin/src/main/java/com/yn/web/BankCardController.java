package com.yn.web;

import com.yn.vo.re.ResultVOUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lycheepay.gateway.client.dto.gbp.TreatyApplyResultDTO;
import com.lycheepay.gateway.client.dto.gbp.TreatyConfirmResultDTO;
import com.yn.model.BankCard;
import com.yn.service.BankCardService;
import com.yn.service.ServerService;
import com.yn.service.kftService.CheckBankCard;
import com.yn.service.kftService.IdcardUtil;
import com.yn.service.kftService.KFTpayService;
import com.yn.utils.BeanCopy;
import com.yn.utils.Constant;
import com.yn.utils.PhoneFormatCheckUtils;
import com.yn.vo.BankCardVo;

@RestController
@RequestMapping("/server/bankCard")
public class BankCardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BankCardController.class);
	
	@Autowired
	private KFTpayService kftpayService;
    @Autowired
    BankCardService bankCardService;
    @Autowired
	private ServerService serverService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        BankCard findOne = bankCardService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody BankCardVo bankCardVo) {
        BankCard bankCard = new BankCard();
        BeanCopy.copyProperties(bankCardVo, bankCard);
        bankCardService.save(bankCard);
        return ResultVOUtil.success(bankCard);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        bankCardService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(BankCardVo bankCardVo) {
        BankCard bankCard = new BankCard();
        BeanCopy.copyProperties(bankCardVo, bankCard);
        BankCard findOne = bankCardService.findOne(bankCard);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(BankCardVo bankCardVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        BankCard bankCard = new BankCard();
        BeanCopy.copyProperties(bankCardVo, bankCard);
        Page<BankCard> findAll = bankCardService.findAll(bankCard, pageable);
        return ResultVOUtil.success(findAll);
    }
    
    /** 绑定银行卡*/
    @RequestMapping(value = "/findBankCard", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object insertBankCard(BankCardVo bankCardVo){
    	logger.info("======= ========= ======== =======传递的用户id:userId:"+bankCardVo.getUserId());
		logger.info("======= ========= ======== =======传递的银行卡号:bankCardNum:"+bankCardVo.getBankCardNum());
		logger.info("======= ========= ======== =======传递的卡类型(11、借计卡扣款  12、信用卡扣款):treatyType:"+bankCardVo.getTreatyType());
		logger.info("======= ========= ======== =======传递的真实姓名:realName:"+bankCardVo.getRealName());
		logger.info("======= ========= ======== =======传递的电话号码:phone:"+bankCardVo.getPhone());
		logger.info("======= ========= ======== =======传递的身份证号:idCardNum:"+bankCardVo.getIdCardNum());
		logger.info("======= ========= ======== =======传递的银行的行号:bankNo:"+bankCardVo.getBankNo());
		logger.info("======= ========= ======== =======传递的用户选择的银行id:bankId:"+bankCardVo.getBankId());
		logger.info("======= ========= ======== =======传递的类型(1、个人账户):Type:"+bankCardVo.getType());
		
		BankCard newbankCard =	bankCardService.findBybank(bankCardVo.getBankCardNum());
		
		if(null != newbankCard){
			return ResultVOUtil.error(777, "该银行卡已绑定!");
		}
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
		if(!IdcardUtil.isIdcard(bankCardVo.getIdCardNum())){
			
			return ResultVOUtil.error(777, "抱歉,您的身份证号有误!");
		}
		if(!CheckBankCard.checkBankCard(bankCardVo.getBankCardNum())){
			
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
				logger.info("========= =========== ========== ========="+resultdto.getErrorCode());
				
				return ResultVOUtil.error(777, "出现错误,请联系客服,错误提示:"+resultdto.getErrorCode());
			}
			
			if(resultdto.getStatus()!=1){
				logger.info("========= =========== ========== ========="+ resultdto.getErrorCode());
				

				return ResultVOUtil.error(777, "出现错误,请联系客服,错误提示:"+resultdto.getErrorCode());
			}

			TreatyConfirmResultDTO configdto=	kftpayService.confirmTreatyCollectApply(resultdto, bankCardVo);
			
			if(configdto.getStatus()!=1){
				logger.info("========= =========== ========== ========="+ configdto.getFailureDetails());
				
				return ResultVOUtil.error(777, "出现错误,请联系客服,错误提示:"+resultdto.getErrorCode());
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
    
    
    
    
}

package com.yn.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.yn.model.BillRefund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.yn.dao.BankCardDao;
import com.yn.model.BankCard;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;
import com.yn.vo.BankCardVo;

@Service
public class BankCardService {
	@Autowired
	BankCardDao bankCardDao;

	public BankCard findOne(Long id) {
		return bankCardDao.findOne(id);
	}

	public void save(BankCard bankCard) {
		if (bankCard.getId() != null) {
			BankCard one = bankCardDao.findOne(bankCard.getId());
			try {
				BeanCopy.beanCopy(bankCard, one);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			bankCardDao.save(one);
		} else {
			bankCardDao.save(bankCard);
		}
		System.out.println();
	}
	
	public BankCard findBybank(String bankCardNum){
		
		return bankCardDao.findBybank(bankCardNum);	
	}

	public void delete(Long id) {
		bankCardDao.delete(id);
	}

	public void deleteBatch(List<Long> id) {
		bankCardDao.deleteBatch(id);
	}

	public BankCard findOne(BankCard bankCard) {
		Specification<BankCard> spec = RepositoryUtil.getSpecification(bankCard);
		BankCard findOne = bankCardDao.findOne(spec);
		return findOne;
	}

	public List<BankCard> findAll(List<Long> list) {
		return bankCardDao.findAll(list);
	}

	public Page<BankCard> findAll(BankCard bankCard, Pageable pageable) {
		Specification<BankCard> spec = RepositoryUtil.getSpecification(bankCard);
		Page<BankCard> findAll = bankCardDao.findAll(spec, pageable);
		return findAll;
	}

	public List<BankCard> findAll(BankCard bankCard) {
		Specification<BankCard> spec = RepositoryUtil.getSpecification(bankCard);
		return bankCardDao.findAll(spec);
	} 
	
	public List<BankCardVo> selectBank(Long userId){
		List<BankCardVo> Banklist = new LinkedList<BankCardVo>();
		
	List<Object> list =	bankCardDao.selectBank(userId); 
		 for (Object object : list) {
			Object[] obj =(Object[])object;
		
			BankCardVo bankCardVo = new BankCardVo();
			
			String treatyId =(String)obj[0];
			String bankName =(String)obj[1];
			String bankCardNum =(String)obj[2];
			String phone =(String)obj[3];
			String imgUrl =(String)obj[4];
			
			bankCardVo.setTreatyId(treatyId);
			bankCardVo.setBankName(bankName);
			bankCardVo.setImg_url(imgUrl);
			int length =bankCardNum.length();
			
			bankCardNum = bankCardNum.substring(0, 4)+"******"+bankCardNum.substring(length-4, length);
			
			bankCardVo.setPhone(phone);
			bankCardVo.setBankCardNum(bankCardNum);
	
			
			Banklist.add(bankCardVo);
		}
		
		return Banklist;
				
	 }
	
	public BankCard findByBankcard(String treatyIds){
		
		return bankCardDao.findByTreatyId02(treatyIds);
	}
	
	public BankCard findByTreatyId(String treatyIds){
		
		Object object = bankCardDao.findByTreatyId(treatyIds);
		Object[] obj = (Object[])object;
		BankCard bankCard = new BankCard();
		
		String realName =(String)obj[0];
		String bankCardNum =(String)obj[1];
		String treatyId =(String)obj[2];
		String bankNo =(String)obj[3];
		
		bankCard.setRealName(realName);
		bankCard.setBankCardNum(bankCardNum);
		bankCard.setTreatyId(treatyId);
		bankCard.setOrderNo(bankNo);
		
		return bankCard;	
		
	}


    //bank_card_num,b.account_name,b.real_name,o.had_pay_price,b.phone,o.order_code
	public BillRefund getBank(Long id){

		BillRefund billRefund = new BillRefund();

		Object object = bankCardDao.getBank(id);

		Object[] obj = (Object[])object;

		String bankCardNum = (String)obj[0];
		String accountName = (String)obj[1];
		String realName = (String)obj[2];
		BigDecimal hadPayPrice = (BigDecimal)obj[3];
		String phone = (String)obj[4];
		String orderCode = (String)obj[5];
		Integer userId =(Integer)obj[6];
		Integer serverId =(Integer)obj[7];


		billRefund.setBankCardNum(bankCardNum);
		billRefund.setBankName(accountName);
		billRefund.setRealName(realName);
		billRefund.setMoney(hadPayPrice.doubleValue());
		billRefund.setPhone(phone);
		billRefund.setTradeNo(orderCode);
		billRefund.setUserId(userId.longValue());
		billRefund.setServerId(serverId.longValue());

		return billRefund;
	}

}

package com.yn.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
			
			bankCardVo.setTreatyId(treatyId);
			bankCardVo.setBankName(bankName);
			
			int length =bankCardNum.length();
			
			bankCardNum = bankCardNum.substring(0, 4)+"******"+bankCardNum.substring(length-4, length);
			
			
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
	

	
	
}

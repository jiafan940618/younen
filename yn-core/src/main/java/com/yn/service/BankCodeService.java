package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.BankCodeDao;
import com.yn.model.BankCard;
import com.yn.model.BankCode;
import com.yn.utils.RepositoryUtil;

@Service
public class BankCodeService {

	
	@Autowired
	BankCodeDao bankCodeDao;
	
	public BankCode findOne(Long id) {
		
		return bankCodeDao.findOne(id);
	}
	
	public List<BankCode> findAll(BankCode bankCode) {
		Specification<BankCode> spec = RepositoryUtil.getSpecification(bankCode);

		return bankCodeDao.findAll(spec);
	}
	
	
}

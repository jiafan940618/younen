package com.yn.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yn.model.BankCode;

public interface BankCodeDao extends JpaRepository<BankCode, Long>, JpaSpecificationExecutor<BankCode> {
	
	

}

package com.yn.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yn.model.TransactionRecord;

public interface TransactionRecordDao extends JpaRepository<TransactionRecord, Long>, JpaSpecificationExecutor<TransactionRecord>{
	
	
	
	
}

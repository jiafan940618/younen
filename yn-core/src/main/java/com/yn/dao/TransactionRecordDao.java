package com.yn.dao;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.yn.model.TransactionRecord;

public interface TransactionRecordDao extends JpaRepository<TransactionRecord, Long>, JpaSpecificationExecutor<TransactionRecord>{
	
	
	@Query(" SELECT COUNT(1) FROM TransactionRecord WHERE userId =:userId AND del = 0 ")
	int FindByNum(@Param("userId") Long userId);
	
	
	
}

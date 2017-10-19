package com.yn.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.TransactionRecord;

public interface TransactionRecordDao extends JpaRepository<TransactionRecord, Long>, JpaSpecificationExecutor<TransactionRecord>{
	
	@Transactional
    @Modifying
	@Query(" SELECT COUNT(1) FROM TransactionRecord WHERE userId = AND del = 0 ")
	int FindByNum(@Param("userId") Long userId);
	
	
	
}

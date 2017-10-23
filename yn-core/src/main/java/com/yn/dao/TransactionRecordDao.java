package com.yn.dao;

import org.springframework.data.repository.query.Param;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.yn.model.TransactionRecord;

public interface TransactionRecordDao extends JpaRepository<TransactionRecord, Long>, JpaSpecificationExecutor<TransactionRecord>{
	
	
	@Query(" SELECT COUNT(1) FROM TransactionRecord WHERE userId =:userId AND del = 0 ")
	int FindByNum(@Param("userId") Long userId);
	
	//select id,del,create_dtm,pay_way,user_id,type,status,money,remark,order_no from transaction_record where user_id = #{userId} and del = 0*/
	
	@Query("select id,del,createDtm,payWay,userId,type,status,money,remark,orderNo from TransactionRecord where userId = :userId and del = 0 ")
	List<TransactionRecord> FindByTransactionRecord(@Param("userId") Long userId);
	
	
	
	
	
	
}

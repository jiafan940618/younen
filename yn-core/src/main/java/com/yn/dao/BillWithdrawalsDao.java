package com.yn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.BillWithdrawals;

public interface BillWithdrawalsDao extends JpaRepository<BillWithdrawals, Long>, JpaSpecificationExecutor<BillWithdrawals> {
    @Modifying
    @Query("update BillWithdrawals set del=1,delDtm=(now()) where id = :id")
    void delete(@Param("id") Long id);
    
    @Transactional
    @Modifying
    @Query("update BillWithdrawals set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);
    
   /* SELECT b.phone,b.real_name,b.user_id,c.bank_no,c.bank_name,w.id w_id  FROM bank_card b
    LEFT JOIN bank_code c ON c.id = b.bank_id 
    LEFT JOIN wallet w ON w.user_id = b.user_id
    
    WHERE b.treaty_id = '21000000000773' AND b.del =0*/
    @Query(value = "SELECT b.phone,b.real_name,b.bank_card_num,b.user_id,c.bank_no,c.bank_name,w.id w_id,w.money  FROM bank_card b "+
    " LEFT JOIN bank_code c ON c.id = b.bank_id "+
    " LEFT JOIN wallet w ON w.user_id = b.user_id"+
   " WHERE b.treaty_id = :treatyId AND b.del =0",nativeQuery=true)
    
   	Object selWithdrawal(@Param("treatyId") String treatyId);
    
    @Query("select  b from BillWithdrawals b where b.tradeNo = :orderNo and b.del=0 ")
    BillWithdrawals findByOrderNo(@Param("orderNo") String orderNo);
    
    
    
    
}

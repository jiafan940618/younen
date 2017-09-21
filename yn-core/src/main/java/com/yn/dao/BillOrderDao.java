package com.yn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.BillOrder;

public interface BillOrderDao extends JpaRepository<BillOrder, Long>, JpaSpecificationExecutor<BillOrder> {
    @Modifying
    @Query("update BillOrder set del=1,delDtm=(now()) where id = :id")
    void delete(@Param("id") Long id);
    
    @Transactional
    @Modifying
    @Query("update BillOrder set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);
    
  
    @Query(" select b from BillOrder b where  b.tradeNo = :tradeNo ")
    BillOrder findByTradeNo(@Param("tradeNo") String tradeNo );
    
    @Query(" select b from BillOrder b where  b.tradeNo = :tradeNo and b.status = 1")
    BillOrder findByTradeNoandstatus(@Param("tradeNo") String tradeNo );
    
    @Query(" select b from BillOrder b where b.status = 0 and   b.orderId = :orderId ")
    List<BillOrder> findByOrderId(@Param("orderId") Long orderId );
    
    @Query(" update  BillOrder b set b.status = 0 where b.tradeNo = :tradeNo ")
    void updateOrder(@Param("tradeNo") String tradeNo );
    
    
    
    
}

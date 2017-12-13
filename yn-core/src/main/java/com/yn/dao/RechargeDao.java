package com.yn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.yn.model.Recharge;

public interface RechargeDao extends JpaRepository<Recharge, Long>, JpaSpecificationExecutor<Recharge>{
	
	 @Modifying
	    @Query("update Recharge set del=1,delDtm=(now()) where id = :id")
	    void delete(@Param("id") Long id);
	    
	    @Transactional
	    @Modifying
	    @Query("update Recharge set del=1,delDtm=(now()) where id in (:ids)")
		void deleteBatch(@Param("ids") List<Long> ids);
	
	@Transactional
	@Modifying
	@Query("update Recharge set status =:#{#recharge.status},del = 0 where tradeNo = :#{#recharge.rechargeCode}")
	void updateRecharge(@Param("recharge") Recharge recharge);
	
	@Query(value="SELECT w.`id`,w.`money` w_money,r.`money` r_money "+
		"FROM recharge r LEFT JOIN wallet w ON w.id = r.`wallet_id` WHERE r.`trade_no` = :#{#recharge.rechargeCode} AND r.del = 0",nativeQuery=true)
		Object findRecharge(@Param("recharge") Recharge recharge);
	
	@Query("SELECT r from   Recharge r where r.tradeNo = :orderNo and r.del = 0")
	Recharge findByRecode(@Param("orderNo") String orderNo);
	
}

package com.yn.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.Order;
import com.yn.model.Recharge;

public interface RechargeDao extends JpaRepository<Recharge, Long>, JpaSpecificationExecutor<Recharge>{
	@Transactional
	@Modifying
	@Query("update Recharge set status =:#{#recharge.status} where rechargeCode = :#{#recharge.rechargeCode}")
	void updateRecharge(@Param("recharge") Recharge recharge);
	
	@Query(value="SELECT w.`id`,w.`money` w_money,r.`money` r_money"+
		"FROM recharge r LEFT JOIN wallet w ON w.id = r.`wallet_id` WHERE r.`recharge_code` = :#{#recharge.rechargeCode} AND r.del = 0",nativeQuery=true)
		Object findRecharge(@Param("recharge") Recharge recharge);
	
}

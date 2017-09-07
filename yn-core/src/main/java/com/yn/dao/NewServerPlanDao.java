package com.yn.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.NewServerPlan;
import com.yn.model.ServerPlan;
import com.yn.model.User;
import com.yn.vo.NewServerPlanVo;

public interface NewServerPlanDao  extends JpaRepository<NewServerPlan, Long>, JpaSpecificationExecutor<NewServerPlan>{
	
	
	 @Transactional
	    @Modifying
	    @Query("select new NewServerPlan(serverId,minPurchase,unitPrice) from  NewServerPlan where id = :#{#newserverPlan.id} ")
	    NewServerPlan selectOne(NewServerPlanVo newserverPlan);
	

}

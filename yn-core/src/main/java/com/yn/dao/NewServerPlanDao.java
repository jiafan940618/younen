package com.yn.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yn.model.NewServerPlan;
import com.yn.model.User;

public interface NewServerPlanDao  extends JpaRepository<NewServerPlan, Long>, JpaSpecificationExecutor<NewServerPlan>{
	
	

}

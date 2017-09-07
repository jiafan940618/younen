package com.yn.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yn.model.Order;
import com.yn.model.ProductionDetail;

public interface ProductionDao  extends JpaRepository<ProductionDetail, Long>, JpaSpecificationExecutor<ProductionDetail>{
	
	

}

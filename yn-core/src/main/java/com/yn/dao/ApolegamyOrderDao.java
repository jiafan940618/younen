package com.yn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.Apolegamy;
import com.yn.model.ApolegamyOrder;

public interface ApolegamyOrderDao   extends JpaRepository<ApolegamyOrder, Long>, JpaSpecificationExecutor<ApolegamyOrder>{
	
	 @Modifying
	    @Query("update ApolegamyOrder set del=1,delDtm=(now()) where id = :id")
	    void delete(@Param("id") Long id);
	    
	    @Transactional
	    @Modifying
	    @Query("update ApolegamyOrder set del=1,delDtm=(now()) where id in (:ids)")
		void deleteBatch(@Param("ids") List<Long> ids);
	
	
	
       
}


package com.yn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.City;
import com.yn.model.Construction;

public interface ConstructionDao extends JpaRepository<Construction, Long>, JpaSpecificationExecutor<Construction>{
	
	    @Transactional
	    @Modifying
	    @Query("select c from  Construction c  where c.type = :type and c.del = 0  and c.identification = 0 ")
	    List<Construction> findbyType(@Param("type") Integer type);
	    

	    @Query("SELECT c FROM Construction c WHERE c.del = 0  and c.identification = 0  ORDER BY c.type  ")
	    List<Construction> findbyStruction();
	    
	    @Query("SELECT c FROM Construction c WHERE c.del = 0  and c.identification = 1  ORDER BY c.type  ")
	    List<Construction> findbyident();
	    
	
	

}

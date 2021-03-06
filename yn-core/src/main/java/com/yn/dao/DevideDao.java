package com.yn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.Devide;

public interface DevideDao extends JpaRepository<Devide, Long>, JpaSpecificationExecutor<Devide> {
    @Modifying
    @Query("update Devide set del=1,delDtm=(now()) where id = :id")
    void delete(@Param("id") Long id);
    
    @Transactional
    @Modifying
    @Query("update Devide set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);
    
    @Modifying
    @Query("select   new Devide(id,devideModel,devideBrand) from  Devide  where parentId = :#{#deviceType.parentId}  and devideModel is Not null")
	List<Devide> selectBatch(@Param("deviceType") Devide deviceType);
    
    @Modifying
    @Query("SELECT  new Devide(id,devideBrand) from  Devide WHERE parentId =:#{#deviceType.parentId} and devideModel is null ")
	List<Devide> selectDevice(@Param("deviceType") Devide deviceType);
}

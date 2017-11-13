package com.yn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.SystemConfig;

public interface SystemConfigDao extends JpaRepository<SystemConfig, Long>, JpaSpecificationExecutor<SystemConfig> {
    @Modifying
    @Query("update SystemConfig set del=1,delDtm=(now()) where id = :id")
    void delete(@Param("id") Long id);
    
    @Transactional
    @Modifying
    @Query("update SystemConfig set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);
    
    SystemConfig findByPropertyKey(String propertyKey);
     //2,3,4,15,16,17,29,30,31
    @Query("SELECT s FROM SystemConfig s WHERE s.id IN (2,3,4,15,16,17,29,30,31)")
    List<SystemConfig> getlist();
    
    @Query("SELECT s FROM SystemConfig s WHERE s.propertyKey IN ('android_version_code','android_version_name','android_update_url','android_update_info')")
    List<SystemConfig> getnewlist();
    
    
}

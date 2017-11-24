package com.yn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.Apolegamy;

public interface ApolegamyDao extends JpaRepository<Apolegamy, Long>, JpaSpecificationExecutor<Apolegamy> {
    @Modifying
    @Query("update Apolegamy set del=1,delDtm=(now()) where id = :id")
    void delete(@Param("id") Long id);
    
    @Transactional
    @Modifying
    @Query("update Apolegamy set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);
    
    @Query(value = " select"
    		+ " m.`id`,m.`apolegamy_name`,m.`img_url`,m.`price`,m.`unit`,m.`TYPE`,m.`icon_url` "
    		+ "from apolegamy_server a LEFT JOIN apolegamy m ON m.id =a.`apolegamy_id`"
    		+ "  where a.del=0 and a.server_id=?1" ,nativeQuery=true)
    
    	List<Object> selectApo(Long serverid);
    

}

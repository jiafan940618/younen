package com.yn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.Qualifications;

public interface QualificationsDao extends JpaRepository<Qualifications, Long>, JpaSpecificationExecutor<Qualifications> {
    @Modifying
    @Query("update Qualifications set del=1,delDtm=(now()) where id = :id")
    void delete(@Param("id") Long id);
    
    @Transactional
    @Modifying
    @Query("update Qualifications set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);
    

    @Query(value="SELECT"
    		+ " q.id,q.img_url,q.text FROM qualifications q "
    		+ "LEFT JOIN  qualifications_server s ON s.qualifications_id = q.id WHERE s.server_id =:serverId AND s.del = 0",nativeQuery=true)
   	List<Object> FindByServerId(@Param("serverId") Long serverId);
    
}

package com.yn.dao;

import com.yn.model.QualificationsServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QualificationsServerDao extends JpaRepository<QualificationsServer, Long>, JpaSpecificationExecutor<QualificationsServer> {
    @Modifying
    @Query("update QualificationsServer set del=1,delDtm=(now()) where id = :id")
    void delete(@Param("id") Long id);
    
    @Transactional
    @Modifying
    @Query("update QualificationsServer set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);
    
    
    @Query(value = " select "
    		+ "q.img_url,q.text "
    		+ "from qualifications_server s "
    		+ "left join qualifications q on s.qualifications_id = q.id"
    		+ " where s.del=0 and s.server_id=?1" ,nativeQuery=true)
    
    	List<Object> selectQualif(Long serverid);
}

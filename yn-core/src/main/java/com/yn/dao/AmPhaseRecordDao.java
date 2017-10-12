package com.yn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.AmPhaseRecord;

public interface AmPhaseRecordDao extends JpaRepository<AmPhaseRecord, Long>, JpaSpecificationExecutor<AmPhaseRecord> {
    @Modifying
    @Query("update AmPhaseRecord set del=1,delDtm=(now()) where id = :id")
    void delete(@Param("id") Long id);
    
    @Transactional
    @Modifying
    @Query("update AmPhaseRecord set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);
    
    /**
     * 
     * @param date : 171009
     */
    @Transactional
    @Modifying
    @Query(value="delete FROM  am_phase_record WHERE am_phase_record_id LIKE  CONCAT('%',:amPhaseRecordId,'%') ", nativeQuery = true)
	void deleteAmPhaseRecordByIdLike(@Param("amPhaseRecordId") String amPhaseRecordId);
}

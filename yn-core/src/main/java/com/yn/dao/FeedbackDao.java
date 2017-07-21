package com.yn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.Feedback;

public interface FeedbackDao extends JpaRepository<Feedback, Long>, JpaSpecificationExecutor<Feedback> {
    @Modifying
    @Query("update Feedback set del=1,delDtm=(now()) where id = :id")
    void delete(@Param("id") Long id);
    
    @Transactional
    @Modifying
    @Query("update Feedback set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);
    
    @Query("SELECT COUNT(*) FROM Feedback f WHERE f.zlevel=1 AND f.status=0 AND f.del=0")
    long countUnconfirmed();
}

package com.yn.dao;

import com.yn.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NoticeDao extends JpaRepository<Notice, Long>, JpaSpecificationExecutor<Notice> {
    @Modifying
    @Query("update Notice set del=1,delDtm=(now()) where id = :id")
    void delete(@Param("id") Long id);
    
    @Transactional
    @Modifying
    @Query("update Notice set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);

    long countByUserIdAndTypeAndIsRead(Long userId, Integer type, Integer isRead);
}

package com.yn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.Banner;

public interface BannerDao extends JpaRepository<Banner, Long>, JpaSpecificationExecutor<Banner> {
    @Modifying
    @Query("update Banner set del=1,delDtm=(now()) where id = :id")
    void delete(@Param("id") Long id);
    
    @Transactional
    @Modifying
    @Query("update Banner set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);
    
    @Modifying
    @Query("select new Banner(id,imgUrl,linkUrl)  from Banner  where del = 0 and isShow = 0")
    List<Banner> selectBanner();
}

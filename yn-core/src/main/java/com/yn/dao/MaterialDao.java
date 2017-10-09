package com.yn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.Material;

public interface MaterialDao extends JpaRepository<Material, Long>, JpaSpecificationExecutor<Material> {
	@Modifying
	@Query("update Material set del=1,delDtm=(now()) where id = :id")
	void delete(@Param("id") Long id);

	@Transactional
	@Modifying
	@Query("update Material set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);
}

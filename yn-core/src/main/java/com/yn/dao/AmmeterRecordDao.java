package com.yn.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.AmmeterRecord;

public interface AmmeterRecordDao extends JpaRepository<AmmeterRecord, Long>, JpaSpecificationExecutor<AmmeterRecord> {
	@Modifying
	@Query("update AmmeterRecord set del=1,delDtm=(now()) where id = :id")
	void delete(@Param("id") Long id);

	@Transactional
	@Modifying
	@Query("update AmmeterRecord set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);

	@Query("select o.dAddr from AmmeterRecord o where o.stationId = ?1 and o.type = ?2 and o.del = 0")
	Set<Long> findDAddr(Long stationId, Integer type);
}

package com.yn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.Inverter;
import com.yn.model.Page;

public interface InverterDao extends JpaRepository<Inverter, Long>, JpaSpecificationExecutor<Inverter> {
	@Modifying
	@Query("update Inverter set del=1,delDtm=(now()) where id = :id")
	void delete(@Param("id") Long id);

	@Transactional
	@Modifying
	@Query("update Inverter set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);
	
	
	@Query(value="SELECT DISTINCT(brand_id),brand_name FROM inverter ORDER BY brand_id ASC ",nativeQuery=true)
	List<Object> getinverter();
	
	@Query(value="SELECT * FROM inverter i where i.del=0 LIMIT :#{#page.start},:#{#page.limit} ",nativeQuery=true)
	List<Inverter> FindByall(@Param("page") Page page);
	
	@Query("SELECT count(1) FROM Inverter i where i.del=0  ")
	int FindByconut();
	
	
	@Transactional
	@Modifying
	@Query(" select new Inverter(id,model,qualityAssurance) from Inverter i where i.brandId = :brandId and i.del =0 ")
	List<Inverter> selectInverter(@Param("brandId") Integer brandId);
	
}

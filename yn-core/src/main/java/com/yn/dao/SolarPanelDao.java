package com.yn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.Page;
import com.yn.model.SolarPanel;

public interface SolarPanelDao extends JpaRepository<SolarPanel, Long>, JpaSpecificationExecutor<SolarPanel> {
	@Modifying
	@Query("update SolarPanel set del=1,delDtm=(now()) where id = :id")
	void delete(@Param("id") Long id);

	@Transactional
	@Modifying
	@Query("update SolarPanel set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);
	
	@Query(value="SELECT DISTINCT(brand_id),brand_name FROM solar_panel ORDER BY brand_id ASC ",nativeQuery=true)
	List<Object> getsolar();
	
	@Query(value="SELECT * FROM solar_panel  where del =0 LIMIT :#{#page.start},:#{#page.limit} ",nativeQuery=true)
	List<SolarPanel> FindByall(Page page);
	
	@Query("SELECT count(1) FROM SolarPanel  where del=0  ")
	int FindByconut();
	
	@Transactional
	@Modifying
	@Query(" select new SolarPanel(id,model,conversionEfficiency,powerGeneration,qualityAssurance) from SolarPanel s  where s.brandId = :brandId and s.del =0 ")
	List<SolarPanel> selectSolarPanel(@Param("brandId") Integer brandId);
	
	
}

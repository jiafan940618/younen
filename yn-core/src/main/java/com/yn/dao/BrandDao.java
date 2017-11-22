package com.yn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.Brand;
import com.yn.model.Inverter;
import com.yn.model.SolarPanel;

public interface BrandDao extends JpaRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {
	@Modifying
	@Query("update Brand set del=1,delDtm=(now()) where id = :id")
	void delete(@Param("id") Long id);

	@Transactional
	@Modifying
	@Query("update Brand set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);
	
	 @Modifying
	    @Query("SELECT b from Brand b where b.type = :type and b.del = 0 ")
		List<Brand> selectBrand(@Param("type") String type);
	 
	 //SELECT DISTINCT(model),brand_name FROM inverter WHERE brand_id =7 AND del =0;
	 
	 @Query("SELECT new Inverter(id,model,brandName) FROM Inverter WHERE brandId =:id AND del =0")
	 List<Inverter>  getInverter(@Param("id") Integer id);
	 
	 @Query("SELECT new SolarPanel(id,model,brandName) FROM SolarPanel WHERE brandId =:id AND del =0")
	 List<SolarPanel>  getSolarPanel(@Param("id") Integer id);
	 
	
}

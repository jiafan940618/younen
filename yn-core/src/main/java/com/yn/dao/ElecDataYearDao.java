package com.yn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.yn.model.ElecDataYear;

public interface ElecDataYearDao extends JpaRepository<ElecDataYear, Long>, JpaSpecificationExecutor<ElecDataYear> {
 
    @Query(value="select * from elec_data_year as t WHERE "
    		+ "t.type =?2 AND t.ammeter_code in (?1) AND t.del=0",nativeQuery=true)
    List<ElecDataYear> findByYear(List<Long> ammeterCodes, Integer type);
    
    @Query(value="select COALESCE(sum(t.kwh),0) from elec_data_year as t WHERE t.type=?1 AND t.ammeter_code in (?2) AND t.del=0",nativeQuery=true)
    double sumKwhByYear(Integer type, List<Long> ammeterCodes);
}

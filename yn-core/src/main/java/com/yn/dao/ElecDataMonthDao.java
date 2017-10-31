package com.yn.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.yn.model.ElecDataMonth;

public interface ElecDataMonthDao extends JpaRepository<ElecDataMonth, Long>, JpaSpecificationExecutor<ElecDataMonth> {

   @Query(value="select * from elec_data_month as t WHERE t.create_dtm>=?3 AND t.create_dtm<?4 "
	    		+ "AND t.type =?2 AND t.ammeter_code in (?1) AND t.del=0",nativeQuery=true)
   List<ElecDataMonth> findByMonths(List<Long> ammeterCodes, Integer type, Date start, Date end);
	    
   @Query(value="select COALESCE(sum(t.kwh),0) from elec_data_month as t WHERE t.create_dtm>=?1 AND t.create_dtm<?2 AND t.type=?3 AND t.ammeter_code in (?4) AND t.del=0",nativeQuery=true)
   double sumKwhByMonths(Date startDtm, Date endDtm, Integer type, List<Long> ammeterCodes);
	
}

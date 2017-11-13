package com.yn.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.yn.model.ElecDataMonth;

public interface ElecDataMonthDao extends JpaRepository<ElecDataMonth, Long>, JpaSpecificationExecutor<ElecDataMonth> {

   @Query(value="select * from elec_data_month as t WHERE t.record_time>=?3 AND t.record_time<?4 "
	    		+ "AND t.type =?2 AND t.ammeter_code in (?1) AND t.del=0",nativeQuery=true)
   List<ElecDataMonth> findByMonths(List<Long> ammeterCodes, Integer type, String start, String end);
	    
   @Query(value="select COALESCE(sum(t.kwh),0) from elec_data_month as t WHERE t.record_time>=?1 AND t.record_time<?2 AND t.type=?3 AND t.ammeter_code in (?4) AND t.del=0",nativeQuery=true)
   double sumKwhByMonths(String startDtm, String endDtm, Integer type, List<Long> ammeterCodes);
	
}

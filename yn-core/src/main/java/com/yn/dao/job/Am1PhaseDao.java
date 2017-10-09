package com.yn.dao.job;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yn.model.Am1Phase;
import com.yn.model.Am3Phase;

public interface Am1PhaseDao {
	
	//List<Am1Phase> findAll(String am1Sql);

	List<Am1Phase> findAllAm1Phase(@Param("sqlNowData") String sqlNowData, @Param("startDtm") String startDtm, @Param("endDtm") String endDtm);

}

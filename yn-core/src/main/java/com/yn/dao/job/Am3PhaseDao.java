package com.yn.dao.job;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yn.model.Am1Phase;
import com.yn.model.Am3Phase;

public interface Am3PhaseDao {
	

	List<Am3Phase> findAllAm3Phase(@Param("sqlNowData") String sqlNowData, @Param("startDtm") String startDtm, @Param("endDtm") String endDtm);

}

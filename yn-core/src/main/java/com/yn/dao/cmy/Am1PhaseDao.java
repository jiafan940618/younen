package com.yn.dao.cmy;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yn.model.Am1Phase;

public interface Am1PhaseDao {
	

	List<Am1Phase> findAllAm1Phase(@Param("sqlNowData") String sqlNowData, @Param("startDtm") String startDtm, @Param("endDtm") String endDtm);

}

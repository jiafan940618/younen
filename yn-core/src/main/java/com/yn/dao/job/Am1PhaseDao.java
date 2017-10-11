package com.yn.dao.job;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yn.model.Am1Phase;


public interface Am1PhaseDao {
	

	List<Am1Phase> findAllAm1Phase(@Param("sqlNowData") String sqlNowData, @Param("startDtm") String startDtm, @Param("endDtm") String endDtm);

	/**
	 * 删除数据
	 * @param sqlNowDate
	 * @param date
	 */
	/*void deleteAm1PhaseByDate(@Param("sqlNowDate") String sqlNowDate, @Param("date") String date);*/
}

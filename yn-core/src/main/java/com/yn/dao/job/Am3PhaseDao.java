package com.yn.dao.job;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yn.model.Am3Phase;

public interface Am3PhaseDao {
	
	List<Am3Phase> findAllAm3Phase(@Param("sqlNowData") String sqlNowData, @Param("startDtm") String startDtm, @Param("endDtm") String endDtm);

	/**
	 * 删除数据
	 * delete from  am_3phase_2017_10_10 WHERE meter_time LIKE '171010%';
	 * @param sqlNowDate
	 * @param date
	 */
	/*void deleteAm3PhaseByDate(@Param("sqlNowDate") String sqlNowDate, @Param("date") String date);*/
}

package com.yn.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yn.dao.job.Am1PhaseDao;
import com.yn.dao.job.Am3PhaseDao;
import com.yn.model.Am1Phase;
import com.yn.model.Am3Phase;
import com.yn.utils.DateUtil;

@Service
public class AmPhaseService {

	@Autowired
	Am1PhaseDao am1PhaseDao;

	@Autowired
	Am3PhaseDao am3PhaseDao;
	
	
	public List<Am1Phase> findAllAm1Phase() {
		String am1TableName = DateUtil.formatDate(new Date(), "yyyy_MM_dd"); // am_1phase_2017_07_13
		Date[] thisHourSpace = DateUtil.thisHourSpace();
		String startDtm = DateUtil.formatDate(thisHourSpace[0], DateUtil.yyMMddHHmmss);
		String endDtm = DateUtil.formatDate(thisHourSpace[1], DateUtil.yyMMddHHmmss);
		String am1Sql = "select * from am_1phase_" + am1TableName +
				" where meter_time >= " + startDtm + " and meter_time  <= "
				+ endDtm;
		System.err.println(am1Sql);
		return am1PhaseDao.findAllAm1Phase(am1TableName,startDtm,endDtm);
	}
	
	public List<Am3Phase> findAllAm3Phase() {
		String am1TableName = DateUtil.formatDate(new Date(), "yyyy_MM_dd"); // am_3phase_2017_07_13
		Date[] thisHourSpace = DateUtil.thisHourSpace();
		String startDtm = DateUtil.formatDate(thisHourSpace[0], DateUtil.yyMMddHHmmss);
		String endDtm = DateUtil.formatDate(thisHourSpace[1], DateUtil.yyMMddHHmmss);
		String am1Sql = "select * from am_3phase_" + am1TableName +
				" where meter_time >= " + startDtm + " and meter_time  <= "
				+ endDtm;
		System.err.println(am1Sql);
		return am3PhaseDao.findAllAm3Phase(am1TableName,startDtm,endDtm);
	}
}

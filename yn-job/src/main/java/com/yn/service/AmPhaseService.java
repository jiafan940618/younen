package com.yn.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itrus.util.DateUtils;
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

	@Autowired
	AmPhaseRecordService amPhaseRecordService;

	public List<Am1Phase> findAllAm1Phase() {
		String am1TableName = DateUtil.formatDate(new Date(), "yyyy_MM_dd"); // am_1phase_2017_07_13
		Date[] thisHourSpace = DateUtil.thisHourSpace();
		String startDtm = DateUtil.formatDate(thisHourSpace[0], DateUtil.yyMMddHHmmss);
		String endDtm = DateUtil.formatDate(thisHourSpace[1], DateUtil.yyMMddHHmmss);
		String am1Sql = "select * from am_1phase_" + am1TableName + " where meter_time >= " + startDtm
				+ " and meter_time  <= " + endDtm;
		System.err.println(am1Sql);
		return am1PhaseDao.findAllAm1Phase(am1TableName, startDtm, endDtm);
	}

	public List<Am3Phase> findAllAm3Phase() {
		String am1TableName = DateUtil.formatDate(new Date(), "yyyy_MM_dd"); // am_3phase_2017_07_13
		Date[] thisHourSpace = DateUtil.thisHourSpace();
		String startDtm = DateUtil.formatDate(thisHourSpace[0], DateUtil.yyMMddHHmmss);
		String endDtm = DateUtil.formatDate(thisHourSpace[1], DateUtil.yyMMddHHmmss);
		String am1Sql = "select * from am_3phase_" + am1TableName + " where meter_time >= " + startDtm
				+ " and meter_time  <= " + endDtm;
		System.err.println(am1Sql);
		return am3PhaseDao.findAllAm3Phase(am1TableName, startDtm, endDtm);
	}

	// **/*/*/*/*/*/*/****/*/*/*/*/*/*//*删除数据*\\*\*\*\*\*\*\*\*\*******\******\\\\\\**\\
	/**
	 * 1 biao
	 * 
	 * @param date
	 *            2017-10-10
	 * @return
	 * @throws ParseException
	 */
	/*
	 * public boolean deleteAm1PhaseByDate(String date) throws ParseException {
	 * try { String sqlNowDate = parseDate(date);// 171010 String startDate =
	 * string2Date(date);// 2017_10_10
	 * //am1PhaseDao.deleteAm1PhaseByDate(sqlNowDate, startDate); return true; }
	 * catch (Exception e) { e.printStackTrace(); return false; } }
	 */
	/**
	 * 3 biao
	 * 
	 * @param date
	 *            2017-10-10
	 * @return
	 * @throws ParseException
	 */
	/*
	 * public boolean deleteAm3PhaseByDate(String date) { try { String
	 * sqlNowDate = parseDate(date);// 171010 String startDate =
	 * string2Date(date);// 2017_10_10
	 * //am3PhaseDao.deleteAm3PhaseByDate(sqlNowDate, startDate); return true; }
	 * catch (Exception e) { e.printStackTrace(); return false; } }
	 */

	// -------------------------------------\\
	/**
	 * 删除记录表里的数据，是新表。
	 * 
	 * @return
	 */
	public boolean sd(String date) {
		return false;
	}

	/**
	 * 格式如下：
	 * 
	 * @param 2017-10-10 20171010
	 * @return 171010/1710100000000
	 * @throws ParseException
	 */
	private String parseDate(String date) throws ParseException {
		SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd");
		Date parse = s.parse(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String format = sdf.format(parse);
		Date thisHourSpace = sdf.parse(format);
		String startDtm = DateUtil.formatDate(thisHourSpace, DateUtil.yyMMddHHmmss).substring(0, 6);
		return startDtm;
	}

	/**
	 * 格式如下：
	 * 
	 * @param 20171010
	 * @return 2017_10_10
	 * @throws ParseException
	 */
	private String string2Date(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy_MM_dd");
		Date ss = sdf.parse(date);
		String format = sdf1.format(ss);
		return format;
	}
	

	public List<Am1Phase> findAllAm1PhaseByDate(String date) throws ParseException {
		String am1TableName = string2Date(date);// 2017_10_10
		String startDtm = parseDate(date) + "000";
		String endDtm = parseDate(date) + "9999999999";
		String am1Sql = "select * from am_1phase_" + am1TableName + " where meter_time >= " + startDtm
				+ " and meter_time  <= " + endDtm;
		System.err.println(am1Sql);
		return am1PhaseDao.findAllAm1Phase(am1TableName, startDtm, endDtm);
	}

	public List<Am3Phase> findAllAm3PhaseByDate(String date) throws ParseException {
		String am1TableName = string2Date(date);// 2017_10_10
		String startDtm = parseDate(date) + "000";
		String endDtm = parseDate(date) + "9999999999";
		String am1Sql = "select * from am_3phase_" + am1TableName + " where meter_time >= " + startDtm
				+ " and meter_time  <= " + endDtm;
		System.err.println(am1Sql);
		return am3PhaseDao.findAllAm3Phase(am1TableName, startDtm, endDtm);
	}

}

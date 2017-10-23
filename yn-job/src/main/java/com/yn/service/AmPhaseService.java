package com.yn.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		Date parse = null;
		String startDtm = null;
		if(date.length()==12){
			parse = s.parse(date.substring(0, 8));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
			String format = sdf.format(parse);
			Date thisHourSpace = sdf.parse(format);
			System.err.println(format);
			startDtm = DateUtil.formatDate(thisHourSpace, "yyyyMMddHHmmss").substring(0, 6);
		}else{
			parse = s.parse(date);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String format = sdf.format(parse);
			System.out.println(format);
			Date thisHourSpace = sdf.parse(format);
			startDtm = DateUtil.formatDate(thisHourSpace, "yyyyMMddHHmmss").substring(0,8);
		}
		System.out.println(startDtm);
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
		Date ss = null;
		if(date.length()==12){
			ss = sdf.parse(date.substring(0, 8));
		}else{
			ss = sdf.parse(date);
		}
		String format = sdf1.format(ss);
		return format;
	}
	

	public List<Am1Phase> findAllAm1PhaseByDate(String date) {
		String am1TableName = null;;
		String startDtm =null ;
		String endDtm = null;
		System.err.println(date);
		try {
			am1TableName = string2Date(date);
			startDtm = parseDate(date) + "000";
			if(date.length()==12){
				endDtm = parseDate(date) +date.substring(8, date.length())+ "9999999";
			}else{
				endDtm = parseDate(date) + "9999999999";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}// 2017_10_10
		String am1Sql = "select * from am_1phase_" + am1TableName + " where meter_time >= " + startDtm
				+ " and meter_time  <= " + endDtm;
		System.err.println(am1Sql);
		List<Am1Phase> allAm1Phase = null;;
		try {
			allAm1Phase = am1PhaseDao.findAllAm1Phase(am1TableName, startDtm, endDtm);
		} catch (Exception e) {
			return null;
		}
		return allAm1Phase;
	}

	public List<Am3Phase> findAllAm3PhaseByDate(String date) {
		String am1TableName = null;
		String startDtm = null;
		String endDtm = null;
		System.err.println(date);
		try {
			am1TableName = string2Date(date);
			startDtm = parseDate(date) + "000";
			if(date.length()==12){
				endDtm = parseDate(date) +date.substring(8, date.length())+ "9999999";
			}else{
				endDtm = parseDate(date) + "9999999999";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String am1Sql = "select * from am_3phase_" + am1TableName + " where meter_time >= " + startDtm
				+ " and meter_time  <= " + endDtm;
		System.err.println(am1Sql);
		 List<Am3Phase> findAllAm3Phase = null;
		 try {
			 findAllAm3Phase = am3PhaseDao.findAllAm3Phase(am1TableName, startDtm, endDtm);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		return findAllAm3Phase;
	}

}

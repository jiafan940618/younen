package com.yn.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itrus.util.DateUtils;
import com.yn.model.AmPhaseRecord;
import com.yn.service.AmPhaseService;
import com.yn.utils.DateUtil;

public class TestDemo {

	public static void main(String [] as) throws ParseException {
/*		String date = "2017-10-01";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date thisHourSpace = sdf.parse(date);
		String startDtm = DateUtil.formatDate(thisHourSpace, DateUtil.yyMMddHHmmss).substring(0, 6);
		String endDtm = DateUtil.formatDate(thisHourSpace, DateUtil.yyMMddHHmmss).substring(0, 6);
		System.out.println(startDtm);
		System.out.println(endDtm);*/
		// AmPhaseService aps = new AmPhaseService();
		// aps.findAllAm1Phase();
		// aps.findAllAm3Phase();
		//sd();
		String date = "2017-10-10";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy_MM_dd");
		Date ss = sdf.parse(date);
		String format = sdf1.format(ss);
		System.out.println(format);
	}
	public static void sd() throws ParseException{
		String date = "2017-10-10";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date thisHourSpace = sdf.parse(date);
		String startDtm = DateUtil.formatDate(thisHourSpace, DateUtil.yyMMddHHmmss).substring(0, 6);
		System.out.println(startDtm);
	}
}

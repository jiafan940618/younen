package com.yn.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.yn.model.AmPhaseRecord;
import com.yn.service.AmPhaseService;
import com.yn.utils.DateUtil;

public class TestDemo {

	public void t() throws ParseException {
		String s = "2017-10-01";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date thisHourSpace = sdf.parse(s);
		String startDtm = DateUtil.formatDate(thisHourSpace, DateUtil.yyMMddHHmmss);
		String endDtm = DateUtil.formatDate(thisHourSpace, DateUtil.yyMMddHHmmss);
		System.out.println(startDtm);
		System.out.println(endDtm);
		//AmPhaseService aps = new AmPhaseService();
		//aps.findAllAm1Phase();
		//aps.findAllAm3Phase();
	}
}

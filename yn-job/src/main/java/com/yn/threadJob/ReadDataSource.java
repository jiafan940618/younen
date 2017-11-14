package com.yn.threadJob;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yn.dao.AmmeterDao;
import com.yn.dao.mapper.AmmeterMapper;
import com.yn.model.Am1Phase;
import com.yn.model.Am3Phase;
import com.yn.model.AmPhaseRecord;
import com.yn.service.AmPhaseRecordService;
import com.yn.service.AmPhaseService;
import com.yn.service.AmmeterRecordService;
import com.yn.service.AmmeterService;
import com.yn.service.AmmeterStatusCodeService;
import com.yn.vo.re.ResultVOUtil;

@Controller
@RequestMapping("/client/rds")
public class ReadDataSource extends Thread {
	@Autowired
	AmPhaseRecordService amPhaseRecordService;
	@Autowired
	AmmeterService ammeterService;
	@Autowired
	AmmeterDao ammeterDao;
	@Autowired
	AmmeterMapper ammeterMapper;
	@Autowired
	AmmeterStatusCodeService ammeterStatusCodeService;
	@Autowired
	AmmeterRecordService ammeterRecordService;
	@Autowired
	AmPhaseService am1PhaseService;
	@Autowired
	ReadDataSource readDataSource;

	// 年月日
	int year = 2017, month = 0, day = 0;

	@SuppressWarnings("static-access")
	private static int countNowDaysInMonth(int month) {
		return LocalDate.now().withMonth(month).now().getDayOfMonth();
	}

	private static int countDaysInMonth(int month) {
		return LocalDate.now().withMonth(month).lengthOfMonth();
	}

	private static String parseDate(int y, int m, int d) {
		String date = "";
		String day = "";
		String month = "";
		if (d <= 9) {
			day = "0" + d;
		} else {
			day = d + "";
		}
		if (m <= 9) {
			month = "0" + m;
		} else {
			month = m + "";
		}
		date = y + "_" + month + "_" + day;
		return date;
	}

	@RequestMapping("/runThread2Insert")
	@ResponseBody
	public Object runThread2Insert() throws InterruptedException {
		// 开始时间
		Long begin = new Date().getTime();
		readDataSource.start();
		// 结束时间
		Long end = new Date().getTime();
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		// 耗时
		System.out.println("数据插入花费时间 : " + (end - begin) / 1000 + " s" + "  插入完成");
		System.out.println("数据插入花费时间 : " + (end - begin) + " ms" + "  插入完成");
		jsonResult.put("needTime4S", (end - begin) / 1000);
		jsonResult.put("needTime4MS", (end - begin));
		return ResultVOUtil.success();
	}

	@Override
	public void run() {
		// 开始时间
		Long begin = new Date().getTime();
		String m = "";
		if (month <= 9) {
			m = "0" + month;
		}
		String date = parseDate(year, month, countDaysInMonth(month));// 2017_11_11
		String selectDate = "";
		Calendar cal = Calendar.getInstance();
		int nowYear = cal.get(Calendar.YEAR);// 当前年
		int nowMonth = cal.get(Calendar.MONTH) + 1;// 当前月
		int days = 0;
		if (year == nowYear) {// 本年
			if (month < nowMonth) {// 是本月就获取本月1号到今天的总天数，不是就获取当月的总天数。
				days = ReadDataSource.countDaysInMonth(month);// 获取总天数。
			} else {
				days = ReadDataSource.countNowDaysInMonth(month);
			}
		} else if (year > nowYear) {// 明年
		} else if (year < nowYear) {// 去年
		}
		for (int i = 1; i <= days; i++) {
			selectDate = year + "" + m + "" + i;// 20101111
			date = parseDate(year, month, i);// 2017_11_11
			amPhaseRecordService.dropTmpTable(date);
			System.out.println("存在am_phase_record_" + date + "表就删除！");
			List<Am1Phase> am1Phases = null;
			List<Am3Phase> am3Phases = null;
			am1Phases = am1PhaseService.findAllAm1PhaseByDate(selectDate);
			am3Phases = am1PhaseService.findAllAm3PhaseByDate(selectDate);

			if (am1Phases.size() > 0) {
				for (Am1Phase am1Phase : am1Phases) {
					AmPhaseRecord amPhaseRecordR = new AmPhaseRecord();
					amPhaseRecordR.setRowId(am1Phase.getRowId());
					amPhaseRecordR.setcAddr(am1Phase.getcAddr());
					amPhaseRecordR.setiAddr(am1Phase.getiAddr());
					amPhaseRecordR.setdAddr(am1Phase.getdAddr());
					amPhaseRecordR.setdType(am1Phase.getdType());
					amPhaseRecordR.setwAddr(am1Phase.getwAddr());
					amPhaseRecordR.setMeterTime(am1Phase.getMeterTime());
					amPhaseRecordR.setDate(date);
					AmPhaseRecord findOne = amPhaseRecordService.findOneByMapper(amPhaseRecordR);
					if (findOne == null) {
						try {
							AmPhaseRecord amPhaseRecord = new AmPhaseRecord();
							BeanUtils.copyProperties(am1Phase, amPhaseRecord);
							amPhaseRecord.setAmPhaseRecordId(
									"am1Phase" + am1Phase.getMeterTime().toString() + am1Phase.getRowId().toString());
							amPhaseRecord.setDate(date);
							amPhaseRecordService.saveByMapper(amPhaseRecord);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}
				}
			}
			if (am3Phases.size() > 0) {
				for (Am3Phase am3Phase : am3Phases) {
					AmPhaseRecord amPhaseRecordR = new AmPhaseRecord();
					amPhaseRecordR.setRowId(am3Phase.getRowId());
					amPhaseRecordR.setcAddr(am3Phase.getcAddr());
					amPhaseRecordR.setiAddr(am3Phase.getiAddr());
					amPhaseRecordR.setdAddr(am3Phase.getdAddr());
					amPhaseRecordR.setdType(am3Phase.getdType());
					amPhaseRecordR.setwAddr(am3Phase.getwAddr());
					amPhaseRecordR.setMeterTime(am3Phase.getMeterTime());
					amPhaseRecordR.setDate(date);
					AmPhaseRecord findOne = amPhaseRecordService.findOneByMapper(amPhaseRecordR);
					if (findOne == null) {
						try {
							AmPhaseRecord amPhaseRecord = new AmPhaseRecord();
							BeanUtils.copyProperties(am3Phase, amPhaseRecord);
							amPhaseRecord.setAmPhaseRecordId(
									"am3Phase" + am3Phase.getMeterTime().toString() + am3Phase.getRowId().toString());
							amPhaseRecord.setDate(date);
							amPhaseRecordService.saveByMapper(amPhaseRecord);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}
				}
			}
		}
		// 结束时间
		Long end = new Date().getTime();
		// 耗时
		System.out.println("数据插入花费时间 : " + (end - begin) / 1000 + " s" + "  插入完成");
		System.out.println("数据插入花费时间 : " + (end - begin) + " ms" + "  插入完成");
	}
}

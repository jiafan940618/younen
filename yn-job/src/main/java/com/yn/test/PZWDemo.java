package com.yn.test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yn.dao.PzwDataSourceMapper;
import com.yn.dao.PzwMapper;
import com.yn.model.AmPhaseRecord;
import com.yn.model.Pzw;
import com.yn.model.PzwDataSource;
import com.yn.service.AmPhaseRecordService;
import com.yn.service.AmmeterRecordService;
import com.yn.service.AmmeterService;
import com.yn.service.StationService;
import com.yn.utils.DateUtil;
import com.yn.vo.re.ResultVOUtil;

@Controller
@RequestMapping("/pzw")
public class PZWDemo {

	@Autowired
	private PzwMapper pzwMapper;

	@Autowired
	private PzwDataSourceMapper pzwDataSourceMapper;

	@Autowired
	private AmPhaseRecordService amPhaseRecordService;

	@Autowired
	AmmeterService ammeterService;

	@Autowired
	StationService stationService;

	@Autowired
	AmmeterRecordService ammeterRecordService;

	private final static Integer CADDR = 16020105;

	/**
	 * 
	 * @Title: countDaysInMonth @Description: TODO(返回指定月份的总天数) @param @param
	 *         month 传入的月份 @param @return 参数 指定月份的天数 @return int 返回类型 @throws
	 */
	private static int countDaysInMonth(int month) {
		return LocalDate.now().withMonth(month).lengthOfMonth();
	}

	/**
	 * 
	 * @Title: countNowDaysInMonth @Description: TODO(获取指定月份的当前日期的天数)
	 *         eg：2017/10/10-->10 @param @param month 月份 @param @return 参数
	 *         当前月份的当前天数 @return int 返回类型 @throws
	 */
	private static int countNowDaysInMonth(int month) {
		return LocalDate.now().withMonth(month).now().getDayOfMonth();
	}

	/**
	 * 
	 * @Title: parseDate @Description: TODO(返回一个具体的日期) eg：20170631 @param @param
	 *         y 年 @param @param m 月 @param @param d 日 @param @return 参数
	 *         日期 @return String 返回类型 @throws
	 */
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

	/**
	 * 
	 * @Title: simulationByMonth @Description:
	 *         TODO(植入指定年份指定月份的数据。如果不指定是某一天的话，那么默认就是从本月1号到今天。如果指定则按照执行指定日期的数据。) @param @param
	 *         year @param @param month @param @return 参数 @return Object
	 *         返回类型 @throws
	 *         url：http://localhost:40404/pzw/importData/2016/6?day=17
	 *         url：http://localhost:40404/pzw/importData/2016/6
	 */
	@RequestMapping("/importData/{year}/{month}")
	public @ResponseBody Object simulationByYearMonth(@PathVariable("year") int year, @PathVariable("month") int month,
			@RequestParam(value = "day", required = false, defaultValue = "-1") int day) {
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		String selectDate = "";
		String date = "";
		String m = "";
		if (month <= 9) {
			m = "0" + month;
		} else {
			m = month + "";
		}
		if (day == -1) {
			Calendar cal = Calendar.getInstance();
			int nowYear = cal.get(Calendar.YEAR);// 当前年
			int nowMonth = cal.get(Calendar.MONTH) + 1;// 当前月
			int days = 0;
			if (year == nowYear) {// 本年
				if (month < nowMonth) {// 是本月就获取本月1号到今天的总天数，不是就获取当月的总天数。
					days = PZWDemo.countDaysInMonth(month);// 获取总天数。
				} else {
					days = PZWDemo.countNowDaysInMonth(month);
				}
			} else if (year > nowYear) {// 明年
				return ResultVOUtil.error(-1, "当前年是：" + nowYear + "，而您输入的是：" + year);
			} else if (year < nowYear) {// 去年
				days = PZWDemo.countDaysInMonthByYear(year, month);
			}
			System.out.println("您输入的是：" + year + "年" + m + "月，从1号到当年当月共生存了：" + days + "天了，保持到32天即可吃鸡。");
			for (int i = 1; i <= days; i++) {
				selectDate = year + "" + m + "" + ((i <= 9) ? "0" + i : i + "");// 20101111
				System.out.println(selectDate);
				date = parseDate(year, month, i);// 2017_11_11
				Map<String, Object> map = this.job(date);
				jsonResult.put("map" + i, map);
			}
		} else {
			System.out.println("您输入的时间是" + year + "年" + month + "月" + day + "号。");
			selectDate = year + "" + m + "" + (day <= 9 ? "0" + day : day);// 20101111
			date = parseDate(year, month, day);// 2017_11_11
			Map<String, Object> map = this.job(date);
			jsonResult.put("map", map);
		}
		return ResultVOUtil.success(jsonResult);
	}

	/**
	 * 
	    * @Title: countDaysInMonthByYear
	    * @Description: TODO(返回指定年月指定月份的总天数。)
	    * @param @param year 年
	    * @param @param month 月
	    * @param @return    参数
	    * @return int    返回类型 总天数
	    * @throws
	 */
	private static int countDaysInMonthByYear(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(year, month, 0);
		int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
		return dayOfMonth;
	}

	public Map<String, Object> job(String date) {
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		// 用电
		List<AmPhaseRecord> amPhaseRecords4Use = amPhaseRecordService.findAll(date, CADDR, 2);
		// 发电
		List<AmPhaseRecord> amPhaseRecords4Prod = amPhaseRecordService.findAll(date, CADDR, 1);

		// 用电
		System.out.println("amPhaseRecords4Use.size()::" + amPhaseRecords4Use.size());
		if (amPhaseRecords4Use != null && amPhaseRecords4Use.size() > 0) {
			jsonResult.put("amPhaseRecords4Use.size() ", amPhaseRecords4Use.size());
			for (AmPhaseRecord amPhaseRecord : amPhaseRecords4Use) {
				// Date recordDate =
				// DateUtil.parseString(amPhaseRecord.getMeterTime().toString(),
				// DateUtil.yyMMddHHmmss);
				// String recordTime = DateUtil.formatDate(recordDate,
				// "yyyy年MM月dd日 HH时mm分ss秒 E");
				// Pzw pzw = new Pzw();
				// pzw.setAmPhaseRecordId(amPhaseRecord.getAmPhaseRecordId());
				// pzw.setKwhTotal(BigDecimal.valueOf(amPhaseRecord.getKwhTotal()));
				// pzw.setRecordTime(recordTime);
				// pzw.setType(2);
				// int insert = pzwMapper.insert(pzw);
				PzwDataSource record = new PzwDataSource();

				record.setAmPhaseRecordId(amPhaseRecord.getAmPhaseRecordId());
				record.setAbVolt(
						amPhaseRecord.getAbVolt() == null ? null : BigDecimal.valueOf(amPhaseRecord.getAbVolt()));
				record.setaCurrent(
						amPhaseRecord.getaCurrent() == null ? null : BigDecimal.valueOf(amPhaseRecord.getaCurrent()));
				record.setaKva(amPhaseRecord.getaKva() == null ? null : BigDecimal.valueOf(amPhaseRecord.getaKva()));
				record.setaKvar(amPhaseRecord.getKvar() == null ? null : BigDecimal.valueOf(amPhaseRecord.getKvar()));
				record.setaKw(amPhaseRecord.getaKw() == null ? null : BigDecimal.valueOf(amPhaseRecord.getaKw()));
				record.setaKwhTotal(
						amPhaseRecord.getaKwhTotal() == null ? null : BigDecimal.valueOf(amPhaseRecord.getaKwhTotal()));
				record.setaPowerFactor(amPhaseRecord.getPowerFactor() == null ? null
						: BigDecimal.valueOf(amPhaseRecord.getPowerFactor()));
				record.setaVolt(amPhaseRecord.getaVolt() == null ? null : BigDecimal.valueOf(amPhaseRecord.getaVolt()));
				record.setbCurrent(
						amPhaseRecord.getbCurrent() == null ? null : BigDecimal.valueOf(amPhaseRecord.getbCurrent()));
				record.setBcVolt(
						amPhaseRecord.getBcVolt() == null ? null : BigDecimal.valueOf(amPhaseRecord.getBcVolt()));
				record.setbKva(amPhaseRecord.getbKva() == null ? null : BigDecimal.valueOf(amPhaseRecord.getbKva()));
				record.setbKvar(amPhaseRecord.getbKvar() == null ? null : BigDecimal.valueOf(amPhaseRecord.getbKvar()));
				record.setbKw(amPhaseRecord.getbKw() == null ? null : BigDecimal.valueOf(amPhaseRecord.getbKw()));
				record.setbKwhTotal(
						amPhaseRecord.getbKwhTotal() == null ? null : BigDecimal.valueOf(amPhaseRecord.getbKwhTotal()));
				record.setbPowerFactor(amPhaseRecord.getbPowerFactor() == null ? null
						: BigDecimal.valueOf(amPhaseRecord.getbPowerFactor()));
				record.setbVolt(amPhaseRecord.getbVolt() == null ? null : BigDecimal.valueOf(amPhaseRecord.getbVolt()));
				record.setcAddr(amPhaseRecord.getcAddr() == null ? null : amPhaseRecord.getcAddr());
				record.setCaVolt(
						amPhaseRecord.getCaVolt() == null ? null : BigDecimal.valueOf(amPhaseRecord.getCaVolt()));
				record.setcCurrent(
						amPhaseRecord.getcCurrent() == null ? null : BigDecimal.valueOf(amPhaseRecord.getcCurrent()));
				record.setcKva(amPhaseRecord.getcKva() == null ? null : BigDecimal.valueOf(amPhaseRecord.getcKva()));
				record.setcKvar(amPhaseRecord.getcKvar() == null ? null : BigDecimal.valueOf(amPhaseRecord.getcKvar()));
				record.setcKw(amPhaseRecord.getcKw() == null ? null : BigDecimal.valueOf(amPhaseRecord.getcKw()));
				record.setcKwhTotal(
						amPhaseRecord.getcKwhTotal() == null ? null : BigDecimal.valueOf(amPhaseRecord.getcKwhTotal()));
				record.setcPowerFactor(amPhaseRecord.getcPowerFactor() == null ? null
						: BigDecimal.valueOf(amPhaseRecord.getcPowerFactor()));
				record.setCurrent(
						amPhaseRecord.getCurrent() == null ? null : BigDecimal.valueOf(amPhaseRecord.getCurrent()));
				record.setCurrentChange(amPhaseRecord.getCurrentChange());
				record.setCurrentZero(amPhaseRecord.getCurrentZero() == null ? null
						: BigDecimal.valueOf(amPhaseRecord.getCurrentZero()));
				record.setcVolt(
						amPhaseRecord.getCurrent() == null ? null : BigDecimal.valueOf(amPhaseRecord.getcVolt()));
				record.setdAddr(amPhaseRecord.getdAddr());
				record.setDealt(amPhaseRecord.getDealt() == null ? null : amPhaseRecord.getDealt());
				record.setdType(amPhaseRecord.getdType());
				record.setFrequency(
						amPhaseRecord.getFrequency() == null ? null : BigDecimal.valueOf(amPhaseRecord.getFrequency()));
				record.setiAddr(amPhaseRecord.getiAddr());
				record.setKva(amPhaseRecord.getKva() == null ? null : BigDecimal.valueOf(amPhaseRecord.getKva()));
				record.setKvar(amPhaseRecord.getKvar() == null ? null : BigDecimal.valueOf(amPhaseRecord.getKvar()));
				record.setKvarh1(
						amPhaseRecord.getKvarh1() == null ? null : BigDecimal.valueOf(amPhaseRecord.getKvarh1()));
				record.setKvarh2(
						amPhaseRecord.getKvarh2() == null ? null : BigDecimal.valueOf(amPhaseRecord.getKvarh2()));
				record.setKw(amPhaseRecord.getKw() == null ? null : BigDecimal.valueOf(amPhaseRecord.getKw()));
				record.setKwh(amPhaseRecord.getKwh() == null ? null : BigDecimal.valueOf(amPhaseRecord.getKwh()));
				record.setKwhRev(
						amPhaseRecord.getKwhRev() == null ? null : BigDecimal.valueOf(amPhaseRecord.getKwhRev()));
				record.setKwhTotal(
						amPhaseRecord.getKwhTotal() == null ? null : BigDecimal.valueOf(amPhaseRecord.getKwhTotal()));
				record.setMeterState(amPhaseRecord.getMeterState());
				record.setMeterTime(amPhaseRecord.getMeterTime());
				record.setPowerFactor(amPhaseRecord.getPowerFactor() == null ? null
						: BigDecimal.valueOf(amPhaseRecord.getPowerFactor()));
				record.setRowId(amPhaseRecord.getRowId());
				record.setVolt(amPhaseRecord.getVolt() == null ? null : BigDecimal.valueOf(amPhaseRecord.getVolt()));
				record.setVoltChange(amPhaseRecord.getVoltChange());
				record.setwAddr(amPhaseRecord.getwAddr());

				record.setAmPhaseRecordId(amPhaseRecord.getAmPhaseRecordId());
				int insert = pzwDataSourceMapper.insert(record);
				if (insert > 0) {
					System.out.println(
							"PZWDemo-->新增成功！-->" + new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
				} else {
					System.err.println(
							"PZWDemo-->新增失败！-->" + new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
					System.exit(1);
				}
			}
		}
		// 发电
		System.out.println("amPhaseRecords4Prod.size()::" + amPhaseRecords4Prod.size());
		if (amPhaseRecords4Prod != null && amPhaseRecords4Prod.size() > 0) {
			for (AmPhaseRecord amPhaseRecord : amPhaseRecords4Prod) {
				// System.out.println(amPhaseRecord.getMeterTime());
				// Date recordDate =
				// DateUtil.parseString(amPhaseRecord.getMeterTime().toString(),
				// DateUtil.yyMMddHHmmss);
				// String recordTime = DateUtil.formatDate(recordDate,
				// "yyyy年MM月dd日 HH时mm分ss秒 E");
				// Pzw pzw = new Pzw();
				// pzw.setAmPhaseRecordId(amPhaseRecord.getAmPhaseRecordId());
				// pzw.setKwhTotal(BigDecimal.valueOf(amPhaseRecord.getKwhTotal()));
				// pzw.setRecordTime(recordTime);
				// pzw.setType(1);
				// int insert = pzwMapper.insert(pzw);
				PzwDataSource record = new PzwDataSource();

				record.setAmPhaseRecordId(amPhaseRecord.getAmPhaseRecordId());
				record.setAbVolt(
						amPhaseRecord.getAbVolt() == null ? null : BigDecimal.valueOf(amPhaseRecord.getAbVolt()));
				record.setaCurrent(
						amPhaseRecord.getaCurrent() == null ? null : BigDecimal.valueOf(amPhaseRecord.getaCurrent()));
				record.setaKva(amPhaseRecord.getaKva() == null ? null : BigDecimal.valueOf(amPhaseRecord.getaKva()));
				record.setaKvar(amPhaseRecord.getKvar() == null ? null : BigDecimal.valueOf(amPhaseRecord.getKvar()));
				record.setaKw(amPhaseRecord.getaKw() == null ? null : BigDecimal.valueOf(amPhaseRecord.getaKw()));
				record.setaKwhTotal(
						amPhaseRecord.getaKwhTotal() == null ? null : BigDecimal.valueOf(amPhaseRecord.getaKwhTotal()));
				record.setaPowerFactor(amPhaseRecord.getPowerFactor() == null ? null
						: BigDecimal.valueOf(amPhaseRecord.getPowerFactor()));
				record.setaVolt(amPhaseRecord.getaVolt() == null ? null : BigDecimal.valueOf(amPhaseRecord.getaVolt()));
				record.setbCurrent(
						amPhaseRecord.getbCurrent() == null ? null : BigDecimal.valueOf(amPhaseRecord.getbCurrent()));
				record.setBcVolt(
						amPhaseRecord.getBcVolt() == null ? null : BigDecimal.valueOf(amPhaseRecord.getBcVolt()));
				record.setbKva(amPhaseRecord.getbKva() == null ? null : BigDecimal.valueOf(amPhaseRecord.getbKva()));
				record.setbKvar(amPhaseRecord.getbKvar() == null ? null : BigDecimal.valueOf(amPhaseRecord.getbKvar()));
				record.setbKw(amPhaseRecord.getbKw() == null ? null : BigDecimal.valueOf(amPhaseRecord.getbKw()));
				record.setbKwhTotal(
						amPhaseRecord.getbKwhTotal() == null ? null : BigDecimal.valueOf(amPhaseRecord.getbKwhTotal()));
				record.setbPowerFactor(amPhaseRecord.getbPowerFactor() == null ? null
						: BigDecimal.valueOf(amPhaseRecord.getbPowerFactor()));
				record.setbVolt(amPhaseRecord.getbVolt() == null ? null : BigDecimal.valueOf(amPhaseRecord.getbVolt()));
				record.setcAddr(amPhaseRecord.getcAddr() == null ? null : amPhaseRecord.getcAddr());
				record.setCaVolt(
						amPhaseRecord.getCaVolt() == null ? null : BigDecimal.valueOf(amPhaseRecord.getCaVolt()));
				record.setcCurrent(
						amPhaseRecord.getcCurrent() == null ? null : BigDecimal.valueOf(amPhaseRecord.getcCurrent()));
				record.setcKva(amPhaseRecord.getcKva() == null ? null : BigDecimal.valueOf(amPhaseRecord.getcKva()));
				record.setcKvar(amPhaseRecord.getcKvar() == null ? null : BigDecimal.valueOf(amPhaseRecord.getcKvar()));
				record.setcKw(amPhaseRecord.getcKw() == null ? null : BigDecimal.valueOf(amPhaseRecord.getcKw()));
				record.setcKwhTotal(
						amPhaseRecord.getcKwhTotal() == null ? null : BigDecimal.valueOf(amPhaseRecord.getcKwhTotal()));
				record.setcPowerFactor(amPhaseRecord.getcPowerFactor() == null ? null
						: BigDecimal.valueOf(amPhaseRecord.getcPowerFactor()));
				record.setCurrent(
						amPhaseRecord.getCurrent() == null ? null : BigDecimal.valueOf(amPhaseRecord.getCurrent()));
				record.setCurrentChange(amPhaseRecord.getCurrentChange());
				record.setCurrentZero(amPhaseRecord.getCurrentZero() == null ? null
						: BigDecimal.valueOf(amPhaseRecord.getCurrentZero()));
				record.setcVolt(amPhaseRecord.getcVolt() == null ? null : BigDecimal.valueOf(amPhaseRecord.getcVolt()));
				record.setdAddr(amPhaseRecord.getdAddr());
				record.setDealt(amPhaseRecord.getDealt() == null ? null : amPhaseRecord.getDealt());
				record.setdType(amPhaseRecord.getdType());
				record.setFrequency(
						amPhaseRecord.getFrequency() == null ? null : BigDecimal.valueOf(amPhaseRecord.getFrequency()));
				record.setiAddr(amPhaseRecord.getiAddr());
				record.setKva(amPhaseRecord.getKva() == null ? null : BigDecimal.valueOf(amPhaseRecord.getKva()));
				record.setKvar(amPhaseRecord.getKvar() == null ? null : BigDecimal.valueOf(amPhaseRecord.getKvar()));
				record.setKvarh1(
						amPhaseRecord.getKvarh1() == null ? null : BigDecimal.valueOf(amPhaseRecord.getKvarh1()));
				record.setKvarh2(
						amPhaseRecord.getKvarh2() == null ? null : BigDecimal.valueOf(amPhaseRecord.getKvarh2()));
				record.setKw(amPhaseRecord.getKw() == null ? null : BigDecimal.valueOf(amPhaseRecord.getKw()));
				record.setKwh(amPhaseRecord.getKwh() == null ? null : BigDecimal.valueOf(amPhaseRecord.getKwh()));
				record.setKwhRev(
						amPhaseRecord.getKwhRev() == null ? null : BigDecimal.valueOf(amPhaseRecord.getKwhRev()));
				record.setKwhTotal(
						amPhaseRecord.getKwhTotal() == null ? null : BigDecimal.valueOf(amPhaseRecord.getKwhTotal()));
				record.setMeterState(amPhaseRecord.getMeterState());
				record.setMeterTime(amPhaseRecord.getMeterTime());
				record.setPowerFactor(amPhaseRecord.getPowerFactor() == null ? null
						: BigDecimal.valueOf(amPhaseRecord.getPowerFactor()));
				record.setRowId(amPhaseRecord.getRowId());
				record.setVolt(amPhaseRecord.getVolt() == null ? null : BigDecimal.valueOf(amPhaseRecord.getVolt()));
				record.setVoltChange(amPhaseRecord.getVoltChange());
				record.setwAddr(amPhaseRecord.getwAddr());

				int insert = pzwDataSourceMapper.insert(record);
				if (insert > 0) {
					System.out.println(
							"PZWDemo-->新增成功！-->" + new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
				} else {
					System.err.println(
							"PZWDemo-->新增失败！-->" + new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
					System.exit(1);
				}
			}
		}
		return jsonResult;
	}

	@RequestMapping("/handleData/{year}/{month}")
	public @ResponseBody Object handleData(@PathVariable("year") int year, @PathVariable("month") int month,
			@RequestParam(value = "day", required = false, defaultValue = "-1") int day) {
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		String selectDate = "";
		String date = "";
		String m = "";
		if (month <= 9) {
			m = "0" + month;
		} else {
			m = month + "";
		}
		if (day == -1) {
			Calendar cal = Calendar.getInstance();
			int nowYear = cal.get(Calendar.YEAR);// 当前年
			int nowMonth = cal.get(Calendar.MONTH) + 1;// 当前月
			int days = 0;
			if (year == nowYear) {// 本年
				if (month < nowMonth) {// 是本月就获取本月1号到今天的总天数，不是就获取当月的总天数。
					days = PZWDemo.countDaysInMonth(month);// 获取总天数。
				} else {
					days = PZWDemo.countNowDaysInMonth(month);
				}
			} else if (year > nowYear) {// 明年
				return ResultVOUtil.error(-1, "当前年是：" + nowYear + "，而您输入的是：" + year);
			} else if (year < nowYear) {// 去年
				days = PZWDemo.countDaysInMonthByYear(year, month);
			}
			System.out.println("您输入的是：" + year + "年" + m + "月，从1号到当年当月共生存了：" + days + "天了，保持到32天即可吃鸡。");
			for (int i = 1; i <= days; i++) {
				selectDate = year + "" + m + "" + ((i <= 9) ? "0" + i : i + "");// 20101111
				selectDate = selectDate.substring(2, selectDate.length());// 101111
				System.out.println(selectDate);
				date = parseDate(year, month, i);// 2017_11_11
				// 170113000100
				Map<String, Object> map = this.handleData4Day(selectDate);
				jsonResult.put("map" + i, map);
			}
		} else {
			System.out.println("您输入的时间是" + year + "年" + month + "月" + day + "号。");
			selectDate = year + "" + m + "" + (day <= 9 ? "0" + day : day);// 20101111
			date = parseDate(year, month, day);// 2017_11_11
			selectDate = selectDate.substring(2, selectDate.length());// 101111
			System.out.println(selectDate);
			Map<String, Object> map = this.handleData4Day(selectDate);
			jsonResult.put("map", map);
		}
		return ResultVOUtil.success(jsonResult);
	}

	public Map<String, Object> handleData4Day(String selectDate) {
		Pzw pzw = new Pzw();
		pzw.setType(1);
		pzw.setAmmeterCode(CADDR.toString());
		Date recordDate = DateUtil.parseString(selectDate, "yyMMdd");
		String recordTime = DateUtil.formatDate(recordDate, "yyyy年MM月dd日");
		pzw.setRecordTime(recordTime);
		PzwDataSource example = pzwDataSourceMapper.selectAllByDate(selectDate, "desc");
		PzwDataSource example1 = pzwDataSourceMapper.selectAllByDate(selectDate, "asc");
		Double totalKwhMax = -2.0;
		Double totalKwhMin = -1.0;
		if (example != null) {
			totalKwhMax = Double.valueOf(example.getKwhTotal() == null ? "0.00" : example.getKwhTotal().toString());
		}
		if (example1 != null) {
			totalKwhMin = Double.valueOf(example1.getKwhTotal() == null ? "0.00" : example1.getKwhTotal().toString());
		}
		Double totalKwh = totalKwhMax - totalKwhMin;
		pzw.setKwhTotal(BigDecimal.valueOf(totalKwh));
		int insert = pzwMapper.insert(pzw);
		String chooseDate = recordTime.substring(0, 8);
		Double sumKwh = pzwMapper.findSumKwh(pzw, chooseDate);
		if(sumKwh==null){
			sumKwh=-1.00;
		}
		pzw.setMonthMaxKwh(BigDecimal.valueOf(sumKwh));
		pzwMapper.update(pzw, chooseDate);
		if (insert > 0) {
			System.out
					.println("PZWDemo-->新增成功！-->" + new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
		} else {
			System.err
					.println("PZWDemo-->新增失败！-->" + new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
			System.exit(1);
		}
		return null;
	}

}

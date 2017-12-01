package com.yn.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yn.dao.PatchDataRecordMapper;
import com.yn.model.Am1Phase;
import com.yn.model.Am3Phase;
import com.yn.model.AmPhaseRecord;
import com.yn.model.Ammeter;
import com.yn.model.PatchDataRecord;
import com.yn.service.AmPhaseRecordService;
import com.yn.service.AmPhaseService;
import com.yn.service.AmmeterRecordService;
import com.yn.service.AmmeterService;
import com.yn.service.StationService;
import com.yn.utils.DateUtil;
import com.yn.vo.re.ResultVOUtil;

@Controller
@RequestMapping("/client/aprJob")
public class AmPhaseRecordJobController {

	@Autowired
	private AmPhaseService amPhaseService;

	@Autowired
	private AmPhaseRecordService amPhaseRecordService;

	@Autowired
	AmmeterService ammeterService;

	@Autowired
	StationService stationService;

	@Autowired
	AmmeterRecordService ammeterRecordService;

	@Autowired
	PatchDataRecordMapper patchDataRecordMapper;
	

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
	@SuppressWarnings("static-access")
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
	 *         url：http://localhost:40403/client/aprJob/simulationByYearMonth/2017/10?day=17
	 *         url：http://localhost:40403/client/aprJob/simulationByYearMonth/2017/10
	 */
	@RequestMapping("/simulationByYearMonth/{year}/{month}")
	// @RequestMapping("/job")
	public @ResponseBody Object simulationByYearMonth(@PathVariable("year") int year, @PathVariable("month") int month,
			@RequestParam(value = "day", required = false, defaultValue = "-1") int day) {
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		// int days =
		// AmPhaseRecordJobController.countDaysInMonth(month);//获取总天数。
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
					days = AmPhaseRecordJobController.countDaysInMonth(month);// 获取总天数。
				} else {
					days = AmPhaseRecordJobController.countNowDaysInMonth(month);
				}
			} else if (year > nowYear) {// 明年
				return ResultVOUtil.error(-1, "当前年是：" + nowYear + "，而您输入的是：" + year);
			} else if (year < nowYear) {// 去年
				days = AmPhaseRecordJobController.countDaysInMonthByYear(year, month);
			}
			System.out.println("您输入的是：" + year + "年" + m + "月，从1号到当年当月共生存了：" + days + "天了，保持到32天即可吃鸡。");
			for (int i = 1; i <= days; i++) {
				selectDate = year + "" + m + "" + ((i <= 9) ? "0" + i : i + "");// 20101111
				System.out.println(selectDate);
				date = parseDate(year, month, i);// 2017_11_11
				amPhaseRecordService.dropTmpTable(date);
				System.out.println("存在am_phase_record_" + date + "表就删除！");
				Map<String, Object> map = this.job(selectDate, date);
				jsonResult.put("map" + i, map);
			}
		} else {
			System.out.println("您输入的时间是" + year + "年" + month + "月" + day + "号。");
			selectDate = year + "" + m + "" + (day <= 9 ? "0" + day : day);// 20101111
			date = parseDate(year, month, day);// 2017_11_11
//			amPhaseRecordService.dropTmpTable(date);
			System.out.println("存在am_phase_record_" + date + "表就删除！");
			Map<String, Object> map = this.job(selectDate, date);
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

	public Map<String, Object> job(String selectDate, String date) {
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		List<Am1Phase> am1Phases = null;
		List<Am3Phase> am3Phases = null;
		am1Phases = amPhaseService.findAllAm1PhaseByDate(selectDate);
		am3Phases = amPhaseService.findAllAm3PhaseByDate(selectDate);
		String checkDate = DateUtil.formatDate(new Date(), "yyMMdd");
		if (am1Phases != null && am1Phases.size() > 0) {
			jsonResult.put("am1Phases.size() ", am1Phases.size());
			for (Am1Phase am1Phase : am1Phases) {
				AmPhaseRecord amPhaseRecordR = new AmPhaseRecord();
				amPhaseRecordR.setRowId(am1Phase.getRowId());
				amPhaseRecordR.setcAddr(am1Phase.getcAddr());
				amPhaseRecordR.setiAddr(am1Phase.getiAddr());
				amPhaseRecordR.setdAddr(am1Phase.getdAddr());
				amPhaseRecordR.setdType(am1Phase.getdType());
				amPhaseRecordR.setwAddr(0);
				amPhaseRecordR.setMeterTime(am1Phase.getMeterTime());
				amPhaseRecordR.setDate(date);
				AmPhaseRecord findOne = amPhaseRecordService.findOneByMapper(amPhaseRecordR);
				if (findOne == null) {
					try {
						AmPhaseRecord amPhaseRecord = new AmPhaseRecord();
						BeanUtils.copyProperties(am1Phase, amPhaseRecord);
						amPhaseRecord.setAmPhaseRecordId(
								"am1Phase" + am1Phase.getMeterTime().toString() + am1Phase.getRowId().toString());
						Long checkMeterTime = am1Phase.getMeterTime();
						String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyMMddHHmmss").parse(am1Phase.getMeterTime().toString()));
						amPhaseRecord.setRecordTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format));
						amPhaseRecord.setType(am1Phase.getdAddr().toString().indexOf("1")==0?1:2);
						// 判断数据是不是当天的。
						if (!checkDate.equals(String.valueOf(checkMeterTime).substring(0, 6))) {
							amPhaseRecord.setDate(new SimpleDateFormat("yyMMddHHssmm").format(
									new SimpleDateFormat("yyMMddHHssmm").parse(String.valueOf(checkMeterTime))));
							PatchDataRecord patchDataRecord = new PatchDataRecord();
							BeanUtils.copyProperties(amPhaseRecord, patchDataRecord);
							patchDataRecord.setDealt(0);
							patchDataRecord.setCreateDtm(new Date());
							// 存临时表。
							patchDataRecordMapper.insert(patchDataRecord);
							continue;
						}
						amPhaseRecord.setDealt(0);
						amPhaseRecord.setDate(date);
						amPhaseRecordService.saveByMapper(amPhaseRecord);
						System.out.println("AmPhaseRecordJobController--> am1Phase::"
								+ amPhaseRecord.getAmPhaseRecordId() + "新增成功！-->"
								+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			} 
		}
		if (am3Phases != null && am3Phases.size() > 0) {
			jsonResult.put("am3Phases.size() ", am3Phases.size());
			for (Am3Phase am3Phase : am3Phases) {
				AmPhaseRecord amPhaseRecordR = new AmPhaseRecord();
				amPhaseRecordR.setRowId(am3Phase.getRowId());
				amPhaseRecordR.setcAddr(am3Phase.getcAddr());
				amPhaseRecordR.setiAddr(am3Phase.getiAddr());
				amPhaseRecordR.setdAddr(am3Phase.getdAddr());
				amPhaseRecordR.setdType(am3Phase.getdType());
				amPhaseRecordR.setwAddr(0);
				amPhaseRecordR.setMeterTime(am3Phase.getMeterTime());
				// AmPhaseRecord findOne =
				// amPhaseRecordService.findOne(amPhaseRecordR);
				amPhaseRecordR.setDate(date);
				AmPhaseRecord findOne = amPhaseRecordService.findOneByMapper(amPhaseRecordR);
				if (findOne == null) {
					try {
						AmPhaseRecord amPhaseRecord = new AmPhaseRecord();
						BeanUtils.copyProperties(am3Phase, amPhaseRecord);
						amPhaseRecord.setAmPhaseRecordId(
								"am3Phase" + am3Phase.getMeterTime().toString() + am3Phase.getRowId().toString());
						Long checkMeterTime = am3Phase.getMeterTime();
						String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyMMddHHmmss").parse(am3Phase.getMeterTime().toString()));
						amPhaseRecord.setRecordTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format));
						amPhaseRecord.setType(am3Phase.getdAddr().toString().indexOf("1")==0?1:2);
						// 判断数据是不是当天的。
						if (!checkDate.equals(String.valueOf(checkMeterTime).substring(0, 6))) {
							amPhaseRecord.setDate(new SimpleDateFormat("yyMMddHHssmm").format(
									new SimpleDateFormat("yyMMddHHssmm").parse(String.valueOf(checkMeterTime))));
							PatchDataRecord patchDataRecord = new PatchDataRecord();
							BeanUtils.copyProperties(amPhaseRecord, patchDataRecord);
							patchDataRecord.setCreateDtm(new Date());
							patchDataRecord.setDealt(0);
							// 存临时表。
							patchDataRecordMapper.insert(patchDataRecord);
							//amPhaseRecordMapper.deleteByPrimaryKey(amPhaseRecord.getAmPhaseRecordId());
							continue;
						}
						amPhaseRecord.setDate(date);
						amPhaseRecord.setDealt(0);
						amPhaseRecordService.saveByMapper(amPhaseRecord);
						System.out.println("AmPhaseRecordJobController--> am3Phase::"
								+ amPhaseRecord.getAmPhaseRecordId() + "新增成功！-->"
								+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}
		}
		return jsonResult;
	}

}

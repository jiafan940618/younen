package com.yn.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.yn.dao.AmPhaseRecordDao;
import com.yn.dao.AmmeterDao;
import com.yn.dao.StationDao;
import com.yn.dao.mapper.AmmeterMapper;
import com.yn.dao.mapper.StationMapper;
import com.yn.model.AmPhaseRecord;
import com.yn.model.Ammeter;
import com.yn.model.AmmeterRecord;
import com.yn.model.AmmeterStatusCode;
import com.yn.model.ElecDataDay;
import com.yn.model.ElecDataHour;
import com.yn.model.Station;
import com.yn.service.AmPhaseRecordService;
import com.yn.service.AmmeterRecordService;
import com.yn.service.AmmeterService;
import com.yn.service.AmmeterStatusCodeService;
import com.yn.service.ElecDataDayService;
import com.yn.service.ElecDataHourService;
import com.yn.service.StationService;
import com.yn.utils.DateUtil;
import com.yn.vo.re.ResultVOUtil;

/** 
 * 修改电表/电站信息 保存电表工作状态记录
 */
@Controller
@RequestMapping("/client/aJob")
public class AmmeterJobController {
	@Autowired
	AmmeterService ammeterService;
	@Autowired
	AmmeterRecordService ammeterRecordService;
	@Autowired
	AmPhaseRecordService amPhaseRecordService;
	@Autowired
	AmPhaseRecordDao amPhaseRecordDao;
	@Autowired
	AmmeterDao ammeterDao;
	@Autowired
	AmmeterStatusCodeService ammeterStatusCodeService;
	@Autowired
	StationService stationService;
	@Autowired
	StationDao stationDao;
	@Autowired
	StationMapper stationMapper;
	@Autowired
	ElecDataDayService elecDataDayService;
	@Autowired
	ElecDataHourService elecDataHourService;
	@Autowired
	AmmeterMapper ammeterMapper;

	/**
	 * 
	    * @Title: job 模拟AmmeterJob的job方法
	    * @Description: TODO(更新电表、电站、每时、日的记录。)
	    * @param @param year 年
	    * @param @param month 月
	    * @param @param day 日
	    * @param @return    参数 最终格式： yyyy_MM_dd
	    * @return Object    返回类型
	    * Eg:
	    * 	http://localhost:40403/client/aJob/simulationAGAndS/2016/6?day=24
	    * 	http://localhost:40403/client/aJob/simulationAGAndS/2016/6
	    * @throws
	 */
	@RequestMapping("/simulationAGAndS/{year}/{month}")
	public @ResponseBody Object job(@PathVariable("year") int year, @PathVariable("month") int month,
			@RequestParam(value = "day", required = false, defaultValue = "-1") int day) {
		String date = "";
		try {
			if (day == -1) {
				int days = DateUtil.getDaysByYearMonth(year, month);
				for (int i = 1; i <= days; i++) {
					date = DateUtil.parseDate(year, month, i);
					System.out.println("AmmeterJobController-->job::run");
					List<Ammeter> findAll = ammeterService.findAll(new Ammeter());
					for (Ammeter ammeter : findAll) {
						AmPhaseRecord aprR = new AmPhaseRecord();
						aprR.setcAddr(Integer.parseInt(ammeter.getcAddr()));
						aprR.setdType(ammeter.getdType());
						aprR.setiAddr(ammeter.getiAddr());
						aprR.setDealt(0);
						aprR.setDate(date);
						List<AmPhaseRecord> amPhaseRecords = amPhaseRecordService.findAllByMapper(aprR);
						for (AmPhaseRecord apr : amPhaseRecords) {
							apr.setDealt(1); // 已经处理
							apr.setDate(date);
							AmPhaseRecord amPhaseRecord = amPhaseRecordService.selectByPrimaryKey(apr);
							apr.setDate(date);
							if (amPhaseRecord == null) {
								amPhaseRecordService.saveByMapper(apr);
							} else {
								int keySelective = amPhaseRecordService.updateByPrimaryKeySelective(apr);
							}
							// 保存电表记录。
							saveAmmeterRecord(ammeter, apr);
							// 更新电表和电站。
							updateAmmeterAndStation(ammeter, apr, date);
							System.out.println("AmmeterJobController--> 更新电表和电站更新成功！-->"
									+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
						}
						if (amPhaseRecords.size() == 0) {
							ammeter.setNowKw(0D);
							Ammeter findOne = ammeterDao.findOne(ammeter.getId());
							if (findOne != null) {
								int selective = ammeterMapper.updateByPrimaryKeySelective(ammeter);
								System.out.println("AmmeterJob--> ammeter更新成功！-->"
										+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
							} else {
								int insert = ammeterMapper.insert(ammeter);
								System.out.println("AmmeterJob--> ammeter新增成功！-->"
										+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
							}
						}
					}
				}
			} else {
				date = DateUtil.parseDate(year, month, day);
				System.out.println("AmmeterJobController-->job::run");
				List<Ammeter> findAll = ammeterService.findAll(new Ammeter());
				for (Ammeter ammeter : findAll) {
					AmPhaseRecord aprR = new AmPhaseRecord();
					aprR.setcAddr(Integer.parseInt(ammeter.getcAddr()));
					aprR.setdType(ammeter.getdType());
					aprR.setiAddr(ammeter.getiAddr());
					aprR.setDealt(0);
					aprR.setDate(date);
					List<AmPhaseRecord> amPhaseRecords = amPhaseRecordService.findAllByMapper(aprR);
					for (AmPhaseRecord apr : amPhaseRecords) {
						apr.setDealt(1); // 已经处理
						apr.setDate(date);
						AmPhaseRecord amPhaseRecord = amPhaseRecordService.selectByPrimaryKey(apr);
						apr.setDate(date);
						if (amPhaseRecord == null) {
							amPhaseRecordService.saveByMapper(apr);
						} else {
							int keySelective = amPhaseRecordService.updateByPrimaryKeySelective(apr);
						}
						// 保存电表记录。
						saveAmmeterRecord(ammeter, apr);
						// 更新电表和电站。
						updateAmmeterAndStation(ammeter, apr, date);
						System.out.println("AmmeterJobController--> 更新电表和电站更新成功！-->"
								+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
					}
					if (amPhaseRecords.size() == 0) {
						ammeter.setNowKw(0D);
						Ammeter findOne = ammeterDao.findOne(ammeter.getId());
						if (findOne != null) {
							int selective = ammeterMapper.updateByPrimaryKeySelective(ammeter);
							System.out.println("AmmeterJob--> ammeter更新成功！-->"
									+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
						} else {
							int insert = ammeterMapper.insert(ammeter);
							System.out.println("AmmeterJob--> ammeter新增成功！-->"
									+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Exception：：-->" + new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return ResultVOUtil.success();
	}

	/**
	 * 保存电表状态记录
	 *
	 * @param ammeter
	 * @param meterTime
	 */
	private void saveAmmeterRecord(Ammeter ammeter, AmPhaseRecord apr) {
		AmmeterRecord ammeterRecord = new AmmeterRecord();
		ammeterRecord.setcAddr(ammeter.getcAddr());
		ammeterRecord.setdType(ammeter.getdType());
		ammeterRecord.setRecordDtm(DateUtil.parseString(apr.getMeterTime().toString(), DateUtil.yyMMddHHmmss));
		if (ammeter.getStation() != null) {
			ammeterRecord.setStationId(ammeter.getStationId());
			ammeterRecord.setStationCode(ammeter.getStation().getStationCode());
		}
		Long dAddr = apr.getdAddr();
		CharSequence subSequence = dAddr.toString().subSequence(0, 1);
		if (subSequence.equals("1")) {
			ammeterRecord.setType(1);
		} else if (subSequence.equals("2")) {
			ammeterRecord.setType(2);
		}
		ammeterRecord.setCreateDtm(new Date());
		ammeterRecord.setdAddr(apr.getdAddr());
		ammeterRecord.setwAddr(apr.getwAddr());
		ammeterRecord.setStatusCode(ammeter.getStatusCode());
		ammeterRecordService.saveByMapper(ammeterRecord);
	}

	/**
	 * 更新电表 和 电站
	 *
	 * @param ammeter
	 * @param apr
	 */
	private void updateAmmeterAndStation(Ammeter ammeter, AmPhaseRecord apr, String date) {
		// 更新电表信息
		String statusCode = apr.getMeterState();
		AmmeterStatusCode ammeterStatusCode = ammeterStatusCodeService.findByStatusCode(statusCode);
		if (ammeterStatusCode != null) {
			ammeter.setStatus(ammeterStatusCode.getIsNormal());
		}
		if (ammeter.getWorkDtm() == null) {
			ammeter.setWorkDtm(new Date());
		}
		Double kwhTol = getKwhTol(apr, date);
		ammeter.setStatusCode(statusCode);
		ammeter.setNowKw(apr.getKw());
		ammeter.setWorkTotalTm(ammeter.getWorkTotalTm() + 10);
		ammeter.setWorkTotalKwh(ammeter.getWorkTotalKwh() + kwhTol);
		Ammeter findOne = ammeterDao.findOne(ammeter.getId());
//		AmPhaseRecord ap = apr;
//		ap.setcAddr(Integer.parseInt(ammeter.getcAddr()));
//		Double kwhTotal = 0.0;
//		// 统计发电用电的
//		for (long i = 1; i <= 2; i++) {
//			ap.setdAddr(i);
//			AmPhaseRecord a = amPhaseRecordService.findByMapper4InitKwh(ap);
//			if (a != null) {
//				kwhTotal += a.getKwhTotal();
//			}
//		}
//		ammeter.setInitKwh(kwhTotal);
		if (findOne != null) {
			ammeterMapper.updateByPrimaryKeySelective(ammeter);
			System.out.println("AmmeterJobController--> ammeter更新成功！-->"
					+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
		} else {
			ammeterMapper.insert(ammeter);
			System.out.println("AmmeterJob--> ammeter新增成功！-->"
					+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
		}

//		Long stationId = ammeter.getStationId();
//		if (stationId != null) {
//			Station station = stationService.findOne(stationId);
//			System.err.println(ammeter.getNowKw());
//			if (station == null) {
//				stationMapper.insert(station);
//				System.out.println("AmmeterJobController--> station新增成功！-->"
//						+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
//			} else {
//				stationMapper.updateByPrimaryKeySelective(station);
//				System.out.println("AmmeterJobController--> station更新成功！-->"
//						+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
//			}
//
//		}
		// 更新电站每小时 和 每天 的发电/用电
		saveTemStation(ammeter, apr, kwhTol);
	}

	/**
	 * 更新电站每小时 和 每天 的发电/用电
	 *
	 * @param station
	 * @param ammeter
	 * @param apr
	 * @param tolKwh
	 */
	private void saveTemStation( Ammeter ammeter, AmPhaseRecord apr, Double tolKwh) {
		Date meterTime = DateUtil.parseString(apr.getMeterTime().toString(), DateUtil.yyMMddHHmmss);
		String temStationYearRecordTime = DateUtil.formatDate(meterTime, "yyyy-MM-dd");
		
		String formatDate = DateUtil.formatDate(meterTime, "yyyy-MM-dd HH:mm:ss");
		Date date1 =  null;
		try {
			date1 = DateUtil.formatString(formatDate, "yyyy-MM-dd HH");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date date = new Date(date1.getTime() - 600000);//10分钟前的时间
		String temStationRecordTime =  DateUtil.formatDate(date, "yyyy-MM-dd HH:mm");

		String cAddr = ammeter.getcAddr();
		Long dAddr = apr.getdAddr();
		Integer dType = ammeter.getdType();
		Integer wAddr = apr.getwAddr();
		String ammeterCode = ammeter.getcAddr();
		CharSequence subSequence = dAddr.toString().subSequence(0, 1);

		// 每小时的
		ElecDataHour temStationR = new ElecDataHour();
		temStationR.setDevConfCode(cAddr);
		temStationR.setdType(dType);
		temStationR.setwAddr(wAddr);
		temStationR.setAmmeterCode(ammeterCode);
		temStationR.setRecordTime(temStationRecordTime);
		ElecDataHour temStation = elecDataHourService.findOne(temStationR);
		if (temStation == null) {
			ElecDataHour newTemStation = new ElecDataHour();
			newTemStation.setDevConfCode(cAddr);
			newTemStation.setdType(dType);
			newTemStation.setwAddr(wAddr);
			newTemStation.setAmmeterCode(ammeterCode);
			newTemStation.setKw(apr.getKw());
			newTemStation.setKwh(tolKwh);
			newTemStation.setRecordTime(temStationRecordTime);
			if (subSequence.equals("1")) {
				newTemStation.setType(1);
			} else if (subSequence.equals("2")) {
				newTemStation.setType(2);
			}
			elecDataHourService.saveByMapper(newTemStation);
			System.out.println("AmmeterJobController--> temStation更新成功！-->"
					+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
		} else {
			if (subSequence.equals("1")) {
				temStation.setType(1);
			} else if (subSequence.equals("2")) {
				temStation.setType(2);
			}
			temStation.setKw(apr.getKw());
			temStation.setKwh(temStation.getKwh() + tolKwh);
			elecDataHourService.saveByMapper(temStation);
			System.out.println("AmmeterJobController--> temStation更新成功！-->"
					+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
		}

		// 每天的
		ElecDataDay temStationYearR = new ElecDataDay();
		temStationYearR.setDevConfCode(cAddr);
		temStationYearR.setdType(dType);
		temStationYearR.setwAddr(wAddr);
		temStationYearR.setAmmeterCode(ammeterCode);
		temStationYearR.setRecordTime(temStationYearRecordTime);
		ElecDataDay temStationYear = elecDataDayService.findOne(temStationYearR);
		if (temStationYear == null) {
			ElecDataDay newTemStationYear = new ElecDataDay();
			newTemStationYear.setDevConfCode(cAddr);
			newTemStationYear.setdType(dType);
			newTemStationYear.setwAddr(wAddr);
			newTemStationYear.setAmmeterCode(ammeterCode);
			newTemStationYear.setKw(apr.getKw());
			newTemStationYear.setKwh(tolKwh);
			newTemStationYear.setRecordTime(temStationYearRecordTime);
			if (subSequence.equals("1")) {
				newTemStationYear.setType(1);
			} else if (subSequence.equals("2")) {
				newTemStationYear.setType(2);
			}
			elecDataDayService.saveByMapper(newTemStationYear);
			System.out.println("AmmeterJobController--> temStationYear新增成功！-->"
					+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
		} else {
			if (subSequence.equals("1")) {
				temStationYear.setType(1);
			} else if (subSequence.equals("2")) {
				temStationYear.setType(2);
			}
			temStationYear.setKw(apr.getKw());
			temStationYear.setKwh(temStationYear.getKwh() + tolKwh);
			elecDataDayService.saveByMapper(temStationYear);
			System.out.println("AmmeterJob--> temStationYear更新成功！-->"
					+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
		}
		// }
	}

	/**
	 * 获取发/用电
	 *
	 * @param apr
	 */
	private Double getKwhTol(AmPhaseRecord apr, String date) {
		Double kwhTol = 0d;

		Long meterTime = apr.getMeterTime();
		long lastMeterTime = getLastMeterTime(meterTime);
		AmPhaseRecord amPhaseRecordR = new AmPhaseRecord();
		amPhaseRecordR.setcAddr(apr.getcAddr());
		amPhaseRecordR.setdAddr(apr.getdAddr());
		amPhaseRecordR.setdType(apr.getdType());
		amPhaseRecordR.setwAddr(apr.getwAddr());
		amPhaseRecordR.setMeterTime(lastMeterTime);
		// String date = DateUtil.formatDate(new Date(), "yyyy_MM_dd");
		amPhaseRecordR.setDate(date);
		AmPhaseRecord lastAmPhaseRecord = amPhaseRecordService.findOneByMapper(amPhaseRecordR);
		if (lastAmPhaseRecord != null) {
			if (lastAmPhaseRecord.getKwhTotal() != null && apr.getKwhTotal() != null)
				kwhTol = apr.getKwhTotal() - lastAmPhaseRecord.getKwhTotal(); // 10分钟内发/用电
		}
		return kwhTol;
	}

	/**
	 * @param meterTime
	 * @return
	 */
	private long getLastMeterTime(Long meterTime) {
		Date meterTimeDtm = DateUtil.parseString(meterTime.toString(), DateUtil.yyMMddHHmmss);
		Calendar cal = Calendar.getInstance();
		cal.setTime(meterTimeDtm);
		cal.add(Calendar.MINUTE, -10);
		String formatDate = DateUtil.formatDate(cal.getTime(), DateUtil.yyMMddHHmmss);
		return Long.valueOf(formatDate);
	}

}
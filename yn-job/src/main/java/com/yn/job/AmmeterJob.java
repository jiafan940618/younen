package com.yn.job;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yn.dao.AmPhaseRecordDao;
import com.yn.dao.AmmeterDao;
import com.yn.dao.AmmeterRecordDao;
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

/**
 * 修改电表/电站信息 保存电表工作状态记录
 */
@Component
public class AmmeterJob {
	@Autowired
	AmmeterService ammeterService;
	@Autowired
	AmmeterRecordService ammeterRecordService;
	@Autowired
	AmmeterRecordDao ammeterRecordDao;
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
	ElecDataHourService elecDataHourService;
	@Autowired
	ElecDataDayService elecDataDayService;
	@Autowired
	AmmeterMapper ammeterMapper;

	private static PrintStream mytxt;
	private static PrintStream out;

	public AmmeterJob() {
		try {
//			mytxt = new PrintStream("/opt/springbootproject/ynJob/log/ammeterJobLog.log");
			mytxt = new PrintStream("./ammeterJobLog.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// private String date = "2016_01_01";//yyyy_MM_dd -->用于导入数据使用,指定导入的时间。

	@Scheduled(fixedDelay = 10 * 1000)
	private void job() {
		// 设置日志文件输出路径。
		out = System.out;
		System.setOut(mytxt);
		System.out.println("AmmeterJob文档执行的日期是：" + new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
		try {
			System.out.println("AmmeterJob-->job::run");
			String date = DateUtil.formatDate(new Date(), "yyyy_MM_dd");
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
					saveAmmeterRecord(ammeter,apr);
					// 更新电表和电站。
					updateAmmeterAndStation(ammeter, apr);
					System.out.println("AmmeterJob--> 更新电表和电站更新成功！-->"
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
		} catch (Exception e) {
			System.out.println("Exception：：-->" + new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		System.setOut(out);
		System.out.println("AmmeterJob日志保存完毕。");
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
	private void updateAmmeterAndStation(Ammeter ammeter, AmPhaseRecord apr) {
		// 更新电表信息
		String statusCode = apr.getMeterState();
		AmmeterStatusCode ammeterStatusCode = ammeterStatusCodeService.findByStatusCode(statusCode);
		if (ammeterStatusCode != null) {
			ammeter.setStatus(ammeterStatusCode.getIsNormal());
		}
		if (ammeter.getWorkDtm() == null) {
			ammeter.setWorkDtm(new Date());
		}
		Double kwhTol = getKwhTol(apr);
		ammeter.setStatusCode(statusCode);
		ammeter.setNowKw(apr.getKw());
		ammeter.setWorkTotalTm(ammeter.getWorkTotalTm() + 10);
		ammeter.setWorkTotalKwh(ammeter.getWorkTotalKwh() + kwhTol);
		Ammeter findOne = ammeterDao.findOne(ammeter.getId());
		AmPhaseRecord ap = apr;
		ap.setcAddr(Integer.parseInt(ammeter.getcAddr()));
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
			System.out.println("AmmeterJob--> ammeter更新成功！-->"
					+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
		} else {
			ammeterMapper.insert(ammeter);
			System.out.println("AmmeterJob--> ammeter新增成功！-->"
					+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
		}
		// 更新电站每小时 和 每天 的发电/用电
		saveTemStation(ammeter, apr, kwhTol);
	}

	/**
	 * 更新电站每小时 和 每天 的发电/用电
	 *
	 * @param ammeter
	 * @param apr
	 * @param tolKwh
	 */
	private void saveTemStation(Ammeter ammeter, AmPhaseRecord apr, Double tolKwh) {
		Date meterTime = DateUtil.parseString(apr.getMeterTime().toString(), DateUtil.yyMMddHHmmss);
		String temStationYearRecordTime = DateUtil.formatDate(meterTime, "yyyy-MM-dd");

		String formatDate = DateUtil.formatDate(meterTime, "yyyy-MM-dd HH:mm:ss");
		Date date1 = null;
		try {
			date1 = DateUtil.formatString(formatDate, "yyyy-MM-dd HH");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date date = new Date(date1.getTime() - 600000);// 10分钟前的时间
		String temStationRecordTime = DateUtil.formatDate(date, "yyyy-MM-dd HH:mm");

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
			newTemStation.setdAddr(dAddr);
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
			System.out.println("AmmeterJob--> temStation更新成功！-->"
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
			System.out.println("AmmeterJob--> temStation更新成功！-->"
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
			newTemStationYear.setdAddr(dAddr);
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
			System.out.println("AmmeterJob--> temStationYear新增成功！-->"
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
	private Double getKwhTol(AmPhaseRecord apr) {
		Double kwhTol = 0d;

		Long meterTime = apr.getMeterTime();
		long lastMeterTime = getLastMeterTime(meterTime);
		AmPhaseRecord amPhaseRecordR = new AmPhaseRecord();
		amPhaseRecordR.setcAddr(apr.getcAddr());
		amPhaseRecordR.setdAddr(apr.getdAddr());
		amPhaseRecordR.setdType(apr.getdType());
		amPhaseRecordR.setwAddr(apr.getwAddr());
		amPhaseRecordR.setMeterTime(lastMeterTime);
		String date = DateUtil.formatDate(new Date(), "yyyy_MM_dd");
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

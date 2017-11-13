package com.yn.job;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yn.dao.AmPhaseRecordDao;
import com.yn.dao.AmmeterDao;
import com.yn.dao.PatchDataRecordMapper;
import com.yn.dao.StationDao;
import com.yn.dao.TaskExecuteRecordMapper;
import com.yn.dao.mapper.AmPhaseRecordMapper;
import com.yn.dao.mapper.AmmeterMapper;
import com.yn.dao.mapper.ElecDataHourMapper;
import com.yn.dao.mapper.StationMapper;
import com.yn.model.AmPhaseRecord;
import com.yn.model.Ammeter;
import com.yn.model.AmmeterRecord;
import com.yn.model.AmmeterStatusCode;
import com.yn.model.ElecDataDay;
import com.yn.model.ElecDataHour;
import com.yn.model.ElecDataMonth;
import com.yn.model.ElecDataYear;
import com.yn.model.PatchDataRecord;
import com.yn.model.Station;
import com.yn.service.AmPhaseRecordService;
import com.yn.service.AmPhaseService;
import com.yn.service.AmmeterRecordService;
import com.yn.service.AmmeterService;
import com.yn.service.AmmeterStatusCodeService;
import com.yn.service.ElecDataDayService;
import com.yn.service.ElecDataHourService;
import com.yn.service.ElecDataMonthService;
import com.yn.service.ElecDataYearService;
import com.yn.service.StationService;
import com.yn.utils.DateUtil;

/**
 * 
    * @ClassName: PatchDataRecordJob
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author lzyqssn
    * @date 2017年10月27日
    *
 */
@Component
public class PatchDataRecordJob {

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
	TaskExecuteRecordMapper taskExecuteRecordMapper;
	@Autowired
	PatchDataRecordMapper patchDataRecordMapper;
	@Autowired
	ElecDataHourMapper elecDataHourMapper;
	@Autowired
	AmPhaseRecordMapper amPhaseRecordMapper;
	@Autowired
	AmPhaseRecordDao amPhaseRecordDao;
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
	ElecDataMonthService elecDataMonthService;
	@Autowired
	ElecDataYearService elecDataYearService;

	private static PrintStream mytxt;
	private static PrintStream out;

	public PatchDataRecordJob() {
		try {
			// mytxt = new
			// PrintStream("/opt/springbootproject/ynJob/log/patchDataRecordJobLog.log");
			mytxt = new PrintStream("./patchDataRecordJobLog.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	    * @Title: job
	    * @Description: TODO(每天执行一次)
	    * @param @throws ParseException    参数
	    * @return void    返回类型
	    * @throws
	 */
	@Scheduled(cron = "0 0 0 * * ? ")
//	@Scheduled(fixedDelay = 25 * 1000)
	@Transactional
	private void job() throws ParseException {
		// 设置日志文件输出路径。
		out = System.out;
		System.setOut(mytxt);
		System.out.println(
				"PatchDataRecordJob文档执行的日期是：" + new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
		List<PatchDataRecord> list = patchDataRecordMapper.selectByExample(null);
		if (list.size() > 0) {
			System.out.println("紊乱的数据的总条数：" + list.size());
			// 处理数据。
			for (PatchDataRecord patchDataRecord : list) {
				String meterTime = patchDataRecord.getMeterTime().toString();
				if (StringUtils.isBlank(meterTime)) {
					continue;
				}
				String date4Temp = new SimpleDateFormat("yyyy_MM_dd")
						.format(new SimpleDateFormat("yyMMddHHssmm").parse(meterTime));
				patchDataRecord.setDate(date4Temp);
				AmPhaseRecord amPhaseRecord1 = new AmPhaseRecord();
				BeanUtils.copyProperties(patchDataRecord, amPhaseRecord1);
				amPhaseRecord1.setDate(date4Temp);
				amPhaseRecordMapper.createTmpTable(amPhaseRecord1);
				String meterTimeCode = amPhaseRecord1.getMeterTime().toString();
				String date = new SimpleDateFormat("yyyy_MM_dd")
						.format(new SimpleDateFormat("yyMMddHHssmm").parse(meterTimeCode));
				AmPhaseRecord key = amPhaseRecordService.selectByPrimaryKey(amPhaseRecord1);
				if (key == null) {
					amPhaseRecordService.saveByMapper(amPhaseRecord1);
				} else {
					amPhaseRecordService.updateByPrimaryKey(amPhaseRecord1);
				}
				List<Ammeter> findAll = ammeterService.findAll(new Ammeter());
				for (Ammeter ammeter : findAll) {
					AmPhaseRecord aprR = new AmPhaseRecord();
					aprR.setcAddr(Integer.parseInt(ammeter.getcAddr()));
					aprR.setdType(ammeter.getdType());
					aprR.setiAddr(ammeter.getiAddr());
					aprR.setDealt(0);
					// 添加查询日期
					aprR.setDate(date);
					List<AmPhaseRecord> amPhaseRecords = amPhaseRecordService.findAllByMapper2(aprR);
					for (AmPhaseRecord apr : amPhaseRecords) {
						apr.setDealt(1); // 已经处理
						apr.setDate(date);
						AmPhaseRecord amPhaseRecord = amPhaseRecordService.selectByPrimaryKey(apr);
						apr.setDate(date);
						if (amPhaseRecord == null) {
							amPhaseRecordService.saveByMapper(apr);
						} else {
							amPhaseRecordService.updateByPrimaryKeySelective(apr);
						}
						saveAmmeterRecord(ammeter, apr.getMeterTime());
						updateAmmeterAndStation(ammeter, apr);
					}
					if (amPhaseRecords.size() == 0) {
						ammeter.setNowKw(0D);
						Ammeter findOne = ammeterDao.findOne(ammeter.getId());
						if (findOne != null) {
							ammeterMapper.updateByPrimaryKeySelective(ammeter);
						} else {
							ammeterMapper.insert(ammeter);
						}
					}
				}
			}
			System.out.println("开始整理临时表中的数据。");
			doUpdateMonthYear();
			System.out.println("整理完成。");
			System.out.println("清空临时表中的数据。");
//			patchDataRecordMapper.truncateTable();
		}
		System.setOut(out);
		System.out.println("PatchDataRecordJob日志保存完毕。");
	}

	/**
	 * 保存电表状态记录
	 *
	 * @param ammeter
	 * @param meterTime
	 */
	private void saveAmmeterRecord(Ammeter ammeter, Long meterTime) {
		AmmeterRecord ammeterRecord = new AmmeterRecord();
		ammeterRecord.setcAddr(ammeter.getcAddr());
		ammeterRecord.setdType(ammeter.getdType());
		ammeterRecord.setRecordDtm(DateUtil.parseString(meterTime.toString(), DateUtil.yyMMddHHmmss));
		if (ammeter.getStation() != null) {
			ammeterRecord.setStationId(ammeter.getStationId());
			ammeterRecord.setStationCode(ammeter.getStation().getStationCode());
		}
		ammeterRecord.setStatusCode(ammeter.getStatusCode());
		ammeterRecordService.saveByMapper(ammeterRecord);
	}

	/**
	 * 更新电表 和 电站
	 *
	 * @param ammeter
	 * @param apr
	 * @throws ParseException 
	 */
	private void updateAmmeterAndStation(Ammeter ammeter, AmPhaseRecord apr) throws ParseException {
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
		if (findOne != null) {
			ammeterMapper.updateByPrimaryKeySelective(ammeter);
		} else {
			ammeterMapper.insert(ammeter);
		}

		Long stationId = ammeter.getStationId();
		if (stationId != null) {
			Station station = stationService.findOne(stationId);
			System.err.println(ammeter.getNowKw());
			stationMapper.updateByPrimaryKeySelective(station);
			saveTemStation(station, ammeter, apr, kwhTol);
		}
	}

	/**
	 * 更新电站每小时的发电/用电
	 *
	 * @param station
	 * @param ammeter
	 * @param apr
	 * @param tolKwh
	 */
	private void saveTemStation(Station station, Ammeter ammeter, AmPhaseRecord apr, Double tolKwh) {
		Date meterTime = DateUtil.parseString(apr.getMeterTime().toString(), DateUtil.yyMMddHHmmss);
		String temStationRecordTime = DateUtil.formatDate(meterTime, "yyyyMMddHH");
		String cAddr = ammeter.getcAddr();
		Long dAddr = apr.getdAddr();
		Integer dType = ammeter.getdType();
		Integer wAddr = apr.getwAddr();
		String ammeterCode = ammeter.getcAddr();
		CharSequence subSequence = dAddr.toString().subSequence(0, 1);

		// 每小时的。
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
		} else {
			if (subSequence.equals("1")) {
				temStation.setType(1);
			} else if (subSequence.equals("2")) {
				temStation.setType(2);
			}
			temStation.setKw(apr.getKw());
			temStation.setKwh(temStation.getKwh() + tolKwh);
			elecDataHourService.saveByMapper(temStation);
		}
	}

	private void doUpdateMonthYear() throws ParseException {
		List<PatchDataRecord> list = patchDataRecordMapper.selectByExample(null);
		for (PatchDataRecord patchDataRecord : list) {
			String recordTime = DateUtil.formatDate(
					DateUtil.formatString(patchDataRecord.getMeterTime().toString(), "yyMMddHHssmm"), "yyyy-MM-dd HH");
			String cAddr = patchDataRecord.getcAddr().toString();
			Long dAddr = patchDataRecord.getdAddr();
			Integer dType = patchDataRecord.getdType();
			Integer wAddr = patchDataRecord.getwAddr();
			String ammeterCode = patchDataRecord.getcAddr().toString();
			CharSequence subSequence = dAddr.toString().subSequence(0, 1);
			Double tolKwh = 0D;
			Double tolKw = 0D;
			// 每天的。
			ElecDataHour edh = new ElecDataHour();
			edh.setRecordTime(recordTime);
			edh.setAmmeterCode(ammeterCode);
			List<ElecDataHour> byExample = elecDataHourService.selectByExample(edh);
			if (byExample.size() > 0) {
				for (ElecDataHour elecDataHour : byExample) {
					tolKw += patchDataRecord.getKw().doubleValue();// + elecDataHour.getKw().doubleValue();
					tolKwh += patchDataRecord.getKw().doubleValue() + elecDataHour.getKwh().doubleValue();
				}
			} else {
				tolKwh = patchDataRecord.getKwh();
				tolKw = patchDataRecord.getKw();
			}
			ElecDataDay elecDataDay = new ElecDataDay();
			elecDataDay.setdType(dType);
			elecDataDay.setwAddr(wAddr);
			elecDataDay.setAmmeterCode(ammeterCode);
			elecDataDay.setRecordTime(recordTime);
			ElecDataDay temStationYear = elecDataDayService.findOne(elecDataDay);
			if (temStationYear == null) {
				ElecDataDay newTemStationYear = new ElecDataDay();
				newTemStationYear.setDevConfCode(cAddr);
				newTemStationYear.setwAddr(wAddr);
				newTemStationYear.setAmmeterCode(ammeterCode);
				newTemStationYear.setKw(tolKw);
				newTemStationYear.setKwh(tolKwh);
				newTemStationYear.setRecordTime(recordTime);
				if (subSequence.equals("1")) {
					newTemStationYear.setType(1);
				} else if (subSequence.equals("2")) {
					newTemStationYear.setType(2);
				}
				elecDataDayService.saveByMapper(newTemStationYear);
			} else {
				if (subSequence.equals("1")) {
					temStationYear.setType(1);
				} else if (subSequence.equals("2")) {
					temStationYear.setType(2);
				}
				temStationYear.setKw(patchDataRecord.getKw());
				temStationYear.setKwh(temStationYear.getKwh() + tolKwh);
				elecDataDayService.saveByMapper(temStationYear);
			}
			tolKwh = 0D;
			tolKw = 0D;
			// 每个月的。
			String monthRecord = DateUtil.formatDate(
					DateUtil.formatString(patchDataRecord.getMeterTime().toString(), "yyMMddHHssmm"), "yyyyMM");
			List<ElecDataHour> byExample2 = elecDataHourService.selectByExample(edh);
			if (byExample2.size() > 0) {
				for (ElecDataHour elecDataHour : byExample2) {
					ElecDataMonth elecDataMonth = new ElecDataMonth();
					elecDataMonth.setAmmeterCode(ammeterCode);
					elecDataMonth.setRecordTime(monthRecord);
					elecDataMonth.setdAddr(elecDataHour.getdAddr() == null ? 0 : elecDataHour.getdAddr().intValue());
					List<ElecDataMonth> condition = elecDataMonthService.findByCondition(elecDataMonth);
					// */*/*/*/**//
					if (condition.size() > 0) {
						for (ElecDataMonth elecDataMonth2 : condition) {
							Integer dAddr1 = elecDataMonth2.getdAddr();
							CharSequence subSequence1 = dAddr1.toString().subSequence(0, 1);
							if (subSequence1.equals("1")) {
								elecDataMonth.setType(1);// 用电
							} else if (subSequence1.equals("2")) {
								elecDataMonth.setType(2);// 发电
							}
							tolKw += patchDataRecord.getKw().doubleValue();// + elecDataHour.getKw().doubleValue();
							tolKwh += patchDataRecord.getKw().doubleValue() + elecDataHour.getKwh().doubleValue();
						}
						elecDataMonth.setKw(BigDecimal.valueOf(tolKw));
						elecDataMonth.setKwh(BigDecimal.valueOf(tolKwh));
						boolean falg = elecDataMonthService.updateByExampleSelective(elecDataMonth);
						if (falg) {
							System.out.println("修改月成功！：：" + elecDataMonth.getAmmeterCode());
						} else {
							System.out.println("修改月异常或失败！：：" + elecDataMonth.getAmmeterCode());
						}
					} else {
						Long dAddr1 = elecDataHour.getdAddr();
						CharSequence subSequence1 = dAddr1.toString().subSequence(0, 1);
						ElecDataMonth edm = new ElecDataMonth();
						edm.setAmmeterCode(ammeterCode);
						edm.setRecordTime(monthRecord);
						edm.setKw(BigDecimal.valueOf(elecDataHour.getKw()));
						edm.setKwh(BigDecimal.valueOf(elecDataHour.getKwh()));
						edm.setdAddr(elecDataHour.getdAddr().intValue());
						edm.setDevConfCode(elecDataHour.getDevConfCode());
						edm.setdType(elecDataHour.getdType());
						edm.setwAddr(elecDataHour.getwAddr());
						if (subSequence1.equals("1")) {
							edm.setType(1);// 用电
						} else if (subSequence1.equals("2")) {
							edm.setType(2);// 发电
						}
						edm.setwAddr(elecDataHour.getwAddr());
						boolean b = elecDataMonthService.saveByMapper(edm);
						if (b) {
							System.out.println("保存月成功！：：" + edm.getAmmeterCode());
						} else {
							System.out.println("报存月失败或异常！：：" + edm.getAmmeterCode());
						}
					}
					// 删除那一个月的数据、
					// elecDataMonthService.deleteByRecordTime(elecDataMonth);
				}
			}
			tolKwh = 0D;
			tolKw = 0D;
			// 年的。
			String yearRecord = DateUtil.formatDate(
					DateUtil.formatString(patchDataRecord.getMeterTime().toString(), "yyMMddHHssmm"), "yyyy");
			List<ElecDataHour> byExample3 = elecDataHourService.selectByExample(edh);
			for (ElecDataHour elecDataHour2 : byExample3) {
				ElecDataYear elecDataYear = new ElecDataYear();
				elecDataYear.setRecordTime(yearRecord);
				elecDataYear.setAmmeterCode(ammeterCode);
				elecDataYear.setdAddr(elecDataHour2.getdAddr() == null ? 0 : elecDataHour2.getdAddr().intValue());
				List<ElecDataYear> condition = elecDataYearService.findByCondition(elecDataYear);
				Double totalKw = 0d;
				Double totalKwh = 0d;
				if (condition.size() > 0) {
					for (ElecDataYear elecDataYear2 : condition) {
						Integer dAddr1 = elecDataYear2.getdAddr();
						CharSequence subSequence1 = dAddr1.toString().subSequence(0, 1);
						if (subSequence1.equals("1")) {
							elecDataYear2.setType(1);// 用电
						} else if (subSequence1.equals("2")) {
							elecDataYear2.setType(2);// 发电
						}
						totalKw += elecDataHour2.getKw().doubleValue() ;//+ elecDataYear2.getKw().doubleValue();
						totalKwh += elecDataHour2.getKw().doubleValue() + elecDataYear2.getKwh().doubleValue();

					}
					elecDataYear.setKw(BigDecimal.valueOf(totalKw));
					elecDataYear.setKwh(BigDecimal.valueOf(totalKwh));
					boolean falg = elecDataYearService.updateByExampleSelective(elecDataYear);
					if (falg)
						System.out.println("修改成功！：：" + elecDataYear.getAmmeterCode());
					else
						System.out.println("修改异常或失败！：：" + elecDataYear.getAmmeterCode());
				} else {
					Long dAddr1 = elecDataHour2.getdAddr();
					CharSequence subSequence1 = dAddr1.toString().subSequence(0, 1);
					ElecDataYear edy = new ElecDataYear();
					edy.setAmmeterCode(ammeterCode);
					edy.setRecordTime(yearRecord);
					edy.setKw(BigDecimal.valueOf(elecDataHour2.getKw()));
					edy.setKwh(BigDecimal.valueOf(elecDataHour2.getKwh()));
					edy.setdAddr(elecDataHour2.getdAddr().intValue());
					edy.setDevConfCode(elecDataHour2.getDevConfCode());
					edy.setdType(elecDataHour2.getdType());
					if (subSequence1.equals("1")) {
						edy.setType(1);// 用电
					} else if (subSequence1.equals("2")) {
						edy.setType(2);// 发电
					}
					edy.setwAddr(elecDataHour2.getwAddr());
					boolean b = elecDataYearService.saveByMapper(edy);
					if (b)
						System.out.println("保存年成功！：：" + edy.getAmmeterCode());
					else
						System.out.println("保存年异常或失败！：：" + edy.getAmmeterCode());
				}
			}
		}
	}

	/**
	 * 获取发/用电
	 *
	 * @param apr
	 * @throws ParseException 
	 */
	private Double getKwhTol(AmPhaseRecord apr) throws ParseException {
		Double kwhTol = 0d;

		Long meterTime = apr.getMeterTime();
		long lastMeterTime = getLastMeterTime(meterTime);
		AmPhaseRecord amPhaseRecordR = new AmPhaseRecord();
		amPhaseRecordR.setcAddr(apr.getcAddr());
		amPhaseRecordR.setdAddr(apr.getdAddr());
		amPhaseRecordR.setdType(apr.getdType());
		amPhaseRecordR.setwAddr(apr.getwAddr());
		amPhaseRecordR.setMeterTime(lastMeterTime);
		String date = DateUtil.formatDate(DateUtil.formatString(apr.getMeterTime().toString(), "yyMMddHHssmm"),
				"yyyy_MM_dd");
		amPhaseRecordR.setDate(date);
		AmPhaseRecord lastAmPhaseRecord = amPhaseRecordService.findOneByMapper(amPhaseRecordR);
		if (lastAmPhaseRecord != null) {
			if (lastAmPhaseRecord.getKwhTotal() > 0) {
				//现在和前10分钟数据一致说明没发电。
				if(lastAmPhaseRecord.getKwhTotal()==apr.getKwhTotal()){
					return 0.00;
				}
				kwhTol = apr.getKwhTotal() - lastAmPhaseRecord.getKwhTotal(); // 10分钟内发/用电
			} else {
				kwhTol = apr.getKwhTotal() - ammeterDao.findByCAddr(apr.getcAddr().toString()).getWorkTotalKwh();
			}
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

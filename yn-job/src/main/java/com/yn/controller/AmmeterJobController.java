package com.yn.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	// @Autowired
	// AmmeterRecordDao ammeterRecordDao;
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
	 * 模拟AmmeterJob的job方法
	 * 	只是为了模拟，并没有实际用途、
	 * @return
	 */
//	@RequestMapping("/simulationAGAndS")
	//@RequestMapping("/job")
	public /*@ResponseBody*/ Object job() {
		Map<String,Object> jsonResult = new HashMap<String,Object>();
		try {
			System.out.println("AmmeterJob-->job::run");
			String date = DateUtil.formatDate(new Date(), "yyyy_MM_dd");
			List<Ammeter> findAll = ammeterService.findAll(new Ammeter());
			jsonResult.put("ammeterAll", findAll);
			for (Ammeter ammeter : findAll) {
				AmPhaseRecord aprR = new AmPhaseRecord();
				aprR.setcAddr(Integer.parseInt(ammeter.getcAddr()));
				aprR.setdAddr(ammeter.getdAddr());
				aprR.setdType(ammeter.getdType());
				aprR.setiAddr(ammeter.getiAddr());
				aprR.setDealt(0);
				//List<AmPhaseRecord> amPhaseRecords = amPhaseRecordService.findAll(aprR);
				List<AmPhaseRecord> amPhaseRecords = amPhaseRecordService.findAllByMapper(aprR);
				for (AmPhaseRecord apr : amPhaseRecords) {
					apr.setDealt(1); // 已经处理
					// amPhaseRecordDao.save(apr);
					AmPhaseRecord amPhaseRecord = amPhaseRecordService.selectByPrimaryKey(apr.getAmPhaseRecordId(),date);
					if (amPhaseRecord == null) {
						apr.setDate(date);
						amPhaseRecordService.saveByMapper(apr);
					} else {
						apr.setDate(date);
						int keySelective = amPhaseRecordService.updateByPrimaryKeySelective(apr);
					}
					saveAmmeterRecord(ammeter, apr.getMeterTime());
					updateAmmeterAndStation(ammeter, apr);
					System.out.println("AmmeterJob--> 更新电表和电站更新成功！-->"
							+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
				}
				if (amPhaseRecords.size() == 0) {
					ammeter.setNowKw(0D);
					// ammeterDao.save(ammeter);
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
		return ResultVOUtil.success(jsonResult);
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
		ammeterRecord.setdAddr(ammeter.getdAddr());
		ammeterRecord.setdType(ammeter.getdType());
		ammeterRecord.setRecordDtm(DateUtil.parseString(meterTime.toString(), DateUtil.yyMMddHHmmss));
		// if (ammeter.getStation() != null) {
		// ammeterRecord.setStationId(ammeter.getStationId());
		// ammeterRecord.setStationCode(ammeter.getStation().getStationCode());
		// }
		ammeterRecord.setStatusCode(ammeter.getStatusCode());
		ammeterRecord.setType(ammeter.getType());
		// ammeterRecordService.save(ammeterRecord);
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
		ammeter.setWorkTotaTm(ammeter.getWorkTotaTm() + 10);
		ammeter.setWorkTotaKwh(ammeter.getWorkTotaKwh() + kwhTol);
		// ammeterDao.save(ammeter);
		// ammeterMapper.insert(ammeter);
		Ammeter findOne = ammeterDao.findOne(ammeter.getId());
		if (findOne != null) {
			ammeterMapper.updateByPrimaryKeySelective(ammeter);
			System.out.println("AmmeterJob--> ammeter更新成功！-->"
					+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
		} else {
			ammeterMapper.insert(ammeter);
			System.out.println("AmmeterJob--> ammeter新增成功！-->"
					+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
		}

		Long stationId = ammeter.getStationId();
		if (stationId != null) {
			Station station = stationService.findOne(stationId);
//			if (station.getWorkDtm() == null) {
//				station.setWorkDtm(new Date());
//			}
//			station.setWorkTotaTm(station.getWorkTotaTm() + 10);
//			if (ammeter.getType() == 1) {
//				station.setElectricityGenerationTol(station.getElectricityGenerationTol() + kwhTol);
//			} else if (ammeter.getType() == 2) {
//				station.setElectricityUseTol(station.getElectricityUseTol() + kwhTol);
//			}
			System.err.println(ammeter.getNowKw());
			// stationDao.save(station);
			// stationMapper.insert(station);
			Station one = stationDao.findOne(station.getId());
			if (one != null) {
				// ammeterMapper.updateByPrimaryKeySelective(ammeter);
				stationMapper.updateByPrimaryKeySelective(station);
				System.out.println("AmmeterJob--> station更新成功！-->"
						+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
			} else {
				stationMapper.insert(station);
				System.out.println("AmmeterJob--> station新增成功！-->"
						+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
			}
			saveTemStation(station, ammeter, apr, kwhTol);
		}
	}


	/**
	 * 更新电站每小时 和 每天 的发电/用电
	 *
	 * @param station
	 * @param ammeter
	 * @param apr
	 * @param tolKwh
	 */
	private void saveTemStation(Station station, Ammeter ammeter, AmPhaseRecord apr, Double tolKwh) {
		Date meterTime = DateUtil.parseString(apr.getMeterTime().toString(), DateUtil.yyMMddHHmmss);
		String temStationRecordTime = DateUtil.formatDate(meterTime, "yyyyMMddHH");
		String temStationYearRecordTime = DateUtil.formatDate(meterTime, "yyyyMMdd");

		if (ammeter.getStationId() != null) {
			String cAddr = ammeter.getcAddr();
			Long dAddr = ammeter.getdAddr();
			Integer dType = ammeter.getdType();
			Integer wAddr = apr.getwAddr();
			/*Long stationId = station.getId();
			String stationCode = station.getStationCode();*/
			Long serverId = station.getServerId();
			Integer type = ammeter.getType();
			String ammeterCode = ammeter.getcAddr();

			// 每小时的
			ElecDataHour temStationR = new ElecDataHour();
			temStationR.setDevConfCode(cAddr);
			temStationR.setdAddr(dAddr);
			temStationR.setdType(dType);
			temStationR.setwAddr(wAddr);
//			temStationR.setStationId(stationId);
//			temStationR.setStationCode(stationCode);
			temStationR.setAmmeterCode(ammeterCode);
			temStationR.setServerId(serverId);
//			temStationR.setType(type);
			temStationR.setRecordTime(temStationRecordTime);
			ElecDataHour temStation = elecDataHourService.findOne(temStationR);
			if (temStation == null) {
				ElecDataHour newTemStation = new ElecDataHour();
				newTemStation.setDevConfCode(cAddr);
				newTemStation.setdAddr(dAddr);
				newTemStation.setdType(dType);
				newTemStation.setwAddr(wAddr);
				/*newTemStation.setStationId(stationId);
				newTemStation.setStationCode(stationCode);*/
				newTemStation.setAmmeterCode(ammeterCode);
				newTemStation.setServerId(serverId);
//				newTemStation.setType(type);
				newTemStation.setKw(apr.getKw());
				newTemStation.setKwh(tolKwh);
				newTemStation.setRecordTime(temStationRecordTime);
				// temStationService.save(newTemStation);
				elecDataHourService.saveByMapper(newTemStation);
				System.out.println("AmmeterJob--> temStation更新成功！-->"
						+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
			} else {
				temStation.setKw(apr.getKw());
				temStation.setKwh(temStation.getKwh() + tolKwh);
				// temStationService.save(temStation);;
				elecDataHourService.saveByMapper(temStation);
				System.out.println("AmmeterJob--> temStation更新成功！-->"
						+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
			}

			// 每天的
			ElecDataDay temStationYearR = new ElecDataDay();
			temStationYearR.setDevConfCode(cAddr);
			temStationYearR.setdAddr(dAddr);
			temStationYearR.setdType(dType);
			temStationYearR.setwAddr(wAddr);
			/*temStationYearR.setStationId(stationId);
			temStationYearR.setStationCode(stationCode);*/
			temStationYearR.setServerId(serverId);
			temStationYearR.setAmmeterCode(ammeterCode);
//			temStationYearR.setType(type);
			temStationYearR.setRecordTime(temStationYearRecordTime);
			ElecDataDay temStationYear = elecDataDayService.findOne(temStationYearR);
			if (temStationYear == null) {
				ElecDataDay newTemStationYear = new ElecDataDay();
				newTemStationYear.setDevConfCode(cAddr);
				newTemStationYear.setdAddr(dAddr);
				newTemStationYear.setdType(dType);
				newTemStationYear.setwAddr(wAddr);
				/*newTemStationYear.setStationId(stationId);
				newTemStationYear.setStationCode(stationCode)*/;
				newTemStationYear.setServerId(serverId);
				newTemStationYear.setAmmeterCode(ammeterCode);
//				newTemStationYear.setType(type);
				newTemStationYear.setKw(apr.getKw());
				newTemStationYear.setKwh(tolKwh);
				newTemStationYear.setRecordTime(temStationYearRecordTime);
				// temStationYearService.save(newTemStationYear);
				elecDataDayService.saveByMapper(newTemStationYear);
				System.out.println("AmmeterJob--> temStationYear新增成功！-->"
						+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
			} else {
				temStationYear.setKw(apr.getKw());
				temStationYear.setKwh(temStationYear.getKwh() + tolKwh);
				// temStationYearService.save(temStationYear);
				elecDataDayService.saveByMapper(temStationYear);
				System.out.println("AmmeterJob--> temStationYear更新成功！-->"
						+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
			}
		}
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
		AmPhaseRecord lastAmPhaseRecord = amPhaseRecordService.findOne(amPhaseRecordR);
		if (lastAmPhaseRecord != null) {
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

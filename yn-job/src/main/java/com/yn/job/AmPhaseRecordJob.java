package com.yn.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yn.dao.AmmeterDao;
import com.yn.dao.mapper.AmmeterMapper;
import com.yn.model.Am1Phase;
import com.yn.model.Am3Phase;
import com.yn.model.AmPhaseRecord;
import com.yn.model.Ammeter;
import com.yn.model.AmmeterRecord;
import com.yn.model.AmmeterStatusCode;
import com.yn.service.AmPhaseRecordService;
import com.yn.service.AmPhaseService;
import com.yn.service.AmmeterRecordService;
import com.yn.service.AmmeterService;
import com.yn.service.AmmeterStatusCodeService;
import com.yn.utils.DateUtil;

/**
 * 采集原始数据
 */
/**
 * 用途：用于youneng里面旧表的数据读出来，但插入到younen的新表里去。
 * 
 * @author {lzyqssn} <2017年9月28日-下午4:56:15>
 */
@Component
public class AmPhaseRecordJob {
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

	/**
	 * 采集amPhase数据
	 */
	// @Scheduled(fixedDelay = 25 * 1000)
	@Scheduled(cron = "0/25 * * * * ? ")
	private void collectAmPhaseRecord() throws Exception {
		// AmPhaseRecord amPhaseRecord1 = new AmPhaseRecord();
		// amPhaseRecord1.setAmPhaseRecordId("3214321234");
		// amPhaseRecordService.save(amPhaseRecord1);
		// 把当前小时内的所有数据查找出来
		// Date[] thisHourSpace = DateUtil.thisHourSpace();
		// String startDtm = DateUtil.formatDate(thisHourSpace[0],
		// DateUtil.yyMMddHHmmss);
		// String endDtm = DateUtil.formatDate(thisHourSpace[1],
		// DateUtil.yyMMddHHmmss);
		// String am1TableName = "am_1phase_" + DateUtil.formatDate(new Date(),
		// "yyyy_MM_dd"); // am_1phase_2017_07_13
		// String am1TableName = DateUtil.formatDate(new Date(), "yyyy_MM_dd");
		// String am1Sql = "select * from " + am1TableName + " where
		// meter_time>=" + startDtm + " and meter_time<="
		// + endDtm;
		// String am3TableName = "am_3phase_" + DateUtil.formatDate(new Date(),
		// "yyyy_MM_dd"); // am_3phase_2017_07_13
		// String am3TableName = DateUtil.formatDate(new Date(), "yyyy_MM_dd");
		// String am3Sql = "select * from " + am3TableName + " where
		// meter_time>=" + startDtm + " and meter_time<="
		// + endDtm;
		// List<Am1Phase> am1Phases = getAm1Phase(am1Sql);
		// List<Am3Phase> am3Phases = getAm3Phase(am3Sql);
		List<Am1Phase> am1Phases = am1PhaseService.findAllAm1Phase();
		List<Am3Phase> am3Phases = am1PhaseService.findAllAm3Phase();

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
				AmPhaseRecord findOne = amPhaseRecordService.findOne(amPhaseRecordR);
				if (findOne == null) {
					AmPhaseRecord amPhaseRecord = new AmPhaseRecord();
					BeanUtils.copyProperties(am1Phase, amPhaseRecord);
					amPhaseRecord.setAmPhaseRecordId(
							"am1Phase" + am1Phase.getMeterTime().toString() + am1Phase.getRowId().toString());
					amPhaseRecordService.saveByMapper(amPhaseRecord);
					System.out.println("AmPhaseRecordJob--> am1Phase::"+amPhaseRecord.getAmPhaseRecordId()+"新增成功！-->"+new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
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
				AmPhaseRecord findOne = amPhaseRecordService.findOne(amPhaseRecordR);
				if (findOne == null) {
					AmPhaseRecord amPhaseRecord = new AmPhaseRecord();
					BeanUtils.copyProperties(am3Phase, amPhaseRecord);
					amPhaseRecord.setAmPhaseRecordId(
							"am3Phase" + am3Phase.getMeterTime().toString() + am3Phase.getRowId().toString());
					// amPhaseRecordService.save(amPhaseRecord);//springdata jpa
					// --> 执行返回select语句。保存不失败，但数据库没有数据。
					amPhaseRecordService.saveByMapper(amPhaseRecord);
					System.out.println("AmPhaseRecordJob--> am3Phase::"+amPhaseRecord.getAmPhaseRecordId()+"新增成功！-->"+new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
				}
			}
		}
	}

	/**
	 * 更新电表的信息 和 插入电表的工作状态记录
	 *
	 * @param amPhaseRecords
	 */
	public void dealAmmeter(List<AmPhaseRecord> amPhaseRecords) {
		for (AmPhaseRecord apr : amPhaseRecords) {
			Long meterTime = apr.getMeterTime();
			Ammeter ammeterR = new Ammeter();
			ammeterR.setcAddr(apr.getcAddr().toString());
			ammeterR.setdAddr(apr.getdAddr());
			ammeterR.setdType(apr.getdType());
			ammeterR.setiAddr(apr.getiAddr());
			Ammeter ammeter = ammeterService.findOne(ammeterR);
			if (ammeter != null) {
				// 更新电表信息
				String statusCode = apr.getMeterState();
				AmmeterStatusCode ammeterStatusCode = ammeterStatusCodeService.findByStatusCode(statusCode);
				if (ammeterStatusCode != null) {
					ammeter.setStatus(ammeterStatusCode.getIsNormal());
				}
				if (ammeter.getWorkDtm() == null) {
					ammeter.setWorkDtm(new Date());
				}

				ammeter.setNowKw(apr.getKw());
				ammeter.setWorkTotaTm(ammeter.getWorkTotaTm() + 10);
				// ammeterDao.save(ammeter);
				if(ammeterDao.findOne(ammeter.getId())!=null){
					ammeterMapper.updateByPrimaryKeySelective(ammeter);
				}else{
					ammeterMapper.insert(ammeter);
				}

				// 插入电表状态记录
				AmmeterRecord ammeterRecord = new AmmeterRecord();
				ammeterRecord.setcAddr(ammeter.getcAddr());
				ammeterRecord.setdAddr(ammeter.getdAddr());
				ammeterRecord.setdType(ammeter.getdType());
				ammeterRecord.setRecordDtm(DateUtil.parseString(meterTime.toString(), DateUtil.yyMMddHHmmss));
				if (ammeter.getStation() != null) {
					ammeterRecord.setStationId(ammeter.getStationId());
					ammeterRecord.setStationCode(ammeter.getStation().getStationCode());
				}
				ammeterRecord.setStatusCode(statusCode);
				ammeterRecord.setType(ammeter.getType());
				// ammeterRecordService.save(ammeterRecord);
				ammeterRecordService.saveByMapper(ammeterRecord);
			}
		}
	}

}

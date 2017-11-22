package com.yn.job;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yn.dao.AmmeterDao;
import com.yn.dao.PatchDataRecordMapper;
import com.yn.dao.TaskExecuteRecordMapper;
import com.yn.dao.mapper.AmPhaseRecordMapper;
import com.yn.dao.mapper.AmmeterMapper;
import com.yn.dao.mapper.ElecDataHourMapper;
import com.yn.model.Am1Phase;
import com.yn.model.Am3Phase;
import com.yn.model.AmPhaseRecord;
import com.yn.model.Ammeter;
import com.yn.model.AmmeterRecord;
import com.yn.model.AmmeterStatusCode;
import com.yn.model.PatchDataRecord;
import com.yn.model.TaskExecuteRecord;
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
	@Autowired
	TaskExecuteRecordMapper taskExecuteRecordMapper;
	@Autowired
	PatchDataRecordMapper patchDataRecordMapper;
	@Autowired
	ElecDataHourMapper elecDataHourMapper;
	@Autowired
	AmPhaseRecordMapper amPhaseRecordMapper;

	private static PrintStream mytxt;
	private static PrintStream out;

	public AmPhaseRecordJob() {
		try {
//			mytxt = new PrintStream(new FileOutputStream(new File("/opt/ynJob/log/AmPhaseRecordJob.log"),true));
			mytxt = new PrintStream("./elecDataMonthJobLog.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 采集amPhase数据
	 */
	@Scheduled(fixedDelay = 25 * 1000)
	private void collectAmPhaseRecord() throws Exception {
		// 设置日志文件输出路径。
		out = System.out;
		System.setOut(mytxt);
		System.out.println("AmPhaseRecordJob文档执行的日期是：" + new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
		// TODO 建议后期换成拦截器、过滤器处理。eg:@ExceptionHandler
		TaskExecuteRecord taskExecuteRecord = new TaskExecuteRecord();
		taskExecuteRecord.setStatus("失败");
		taskExecuteRecord.setEndDate(Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
		taskExecuteRecord.setJobName(this.getClass().getSimpleName());
		taskExecuteRecordMapper.insert(taskExecuteRecord);
		try {
			List<Am1Phase> am1Phases = am1PhaseService.findAllAm1Phase();
			List<Am3Phase> am3Phases = am1PhaseService.findAllAm3Phase();
			String date = DateUtil.formatDate(new Date(), "yyyy_MM_dd");
			String checkDate = DateUtil.formatDate(new Date(), "yyMMdd");
			// date = "2017_10_04";
			// checkDate = "171004";
			if (am1Phases.size() > 0) {
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
								// amPhaseRecordMapper.deleteByPrimaryKey(amPhaseRecord.getAmPhaseRecordId());
								continue;
							}
							amPhaseRecord.setDealt(0);
							amPhaseRecord.setDate(date);
							amPhaseRecordService.saveByMapper(amPhaseRecord);
							System.out.println("AmPhaseRecordJob--> am1Phase::" + amPhaseRecord.getAmPhaseRecordId()
									+ "新增成功！-->" + new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
						} catch (Exception e) {
							taskExecuteRecord.setStatus(e.getMessage());
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
					amPhaseRecordR.setwAddr(0);
					amPhaseRecordR.setMeterTime(am3Phase.getMeterTime());
					amPhaseRecordR.setDate(date);
					AmPhaseRecord findOne = amPhaseRecordService.findOneByMapper(amPhaseRecordR);
					if (findOne == null) {
						try {
							AmPhaseRecord amPhaseRecord = new AmPhaseRecord();
							BeanUtils.copyProperties(am3Phase, amPhaseRecord);
							amPhaseRecord.setAmPhaseRecordId(
									"am3Phase" + am3Phase.getMeterTime().toString() + am3Phase.getRowId().toString());
							Long checkMeterTime = am3Phase.getMeterTime();
							// 判断数据是不是当天的。
							if (!checkDate.equals(String.valueOf(checkMeterTime).substring(0, 6))) {
								amPhaseRecord.setDate(new SimpleDateFormat("yyMMddHHssmm").format(
										new SimpleDateFormat("yyMMddHHssmm").parse(String.valueOf(checkMeterTime))));
								PatchDataRecord patchDataRecord = new PatchDataRecord();
								BeanUtils.copyProperties(amPhaseRecord, patchDataRecord);
								patchDataRecord.setCreateDtm(new Date());
								// 存临时表。
								patchDataRecordMapper.insert(patchDataRecord);
								// amPhaseRecordMapper.deleteByPrimaryKey(amPhaseRecord.getAmPhaseRecordId());
								continue;
							}
							amPhaseRecord.setDate(date);
							amPhaseRecord.setDealt(0);
							amPhaseRecordService.saveByMapper(amPhaseRecord);
							System.out.println("AmPhaseRecordJob--> am3Phase::" + amPhaseRecord.getAmPhaseRecordId()
									+ "新增成功！-->" + new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
						} catch (Exception e) {
							System.out.println(e.getMessage());
							taskExecuteRecord.setStatus(e.getMessage());
						}
					}
				}
			}
			taskExecuteRecord.setStatus("正常");
		} catch (Exception e) {
			taskExecuteRecord.setStatus(e.getMessage());
			e.printStackTrace();
		}
		taskExecuteRecordMapper.updateByPrimaryKey(taskExecuteRecord);
		System.out.println("日志记录：" + taskExecuteRecord.getStatus());
		System.setOut(out);
		System.out.println("AmPhaseRecordJob日志保存完毕。");
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
				ammeter.setWorkTotalTm(ammeter.getWorkTotalTm() + 10);
				if (ammeterDao.findOne(ammeter.getId()) != null) {
					ammeterMapper.updateByPrimaryKeySelective(ammeter);
				} else {
					ammeterMapper.insert(ammeter);
				}
				// 插入电表状态记录
				AmmeterRecord ammeterRecord = new AmmeterRecord();
				ammeterRecord.setdAddr(apr.getdAddr());
				ammeterRecord.setcAddr(ammeter.getcAddr());
				ammeterRecord.setdType(ammeter.getdType());
				ammeterRecord.setRecordDtm(DateUtil.parseString(meterTime.toString(), DateUtil.yyMMddHHmmss));
				if (ammeter.getStation() != null) {
					ammeterRecord.setStationId(ammeter.getStationId());
					ammeterRecord.setStationCode(ammeter.getStation().getStationCode());
				}
				ammeterRecord.setStatusCode(statusCode);
				ammeterRecordService.saveByMapper(ammeterRecord);
			}
		}
	}

}

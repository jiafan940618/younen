package com.yn.job;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
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
			mytxt = new PrintStream(new FileOutputStream(new File("/opt/ynJob/log/AmmeterJob.log"), true));
//			 mytxt = new PrintStream("./AmmeterJob.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


	@Scheduled(fixedDelay = 25 * 1000)
	private void job() {
		String date = DateUtil.formatDate(new Date(), "yyyy_MM_dd");// yyyy_MM_dd
		// 设置日志文件输出路径。
		out = System.out;
		System.setOut(mytxt);
		System.out.println("AmmeterJob文档执行的日期是：" + new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
		try {
			System.out.println("AmmeterJob-->job::run");
			List<Ammeter> findAll = ammeterService.findAll(new Ammeter());
			for (Ammeter ammeter : findAll) {
				AmPhaseRecord aprR = new AmPhaseRecord();
				aprR.setcAddr(Integer.parseInt(ammeter.getcAddr()));
				aprR.setdType(ammeter.getdType());
				aprR.setiAddr(ammeter.getiAddr());
				aprR.setDealt(0);
				aprR.setDate(date);
				aprR.setwAddr(0);
				List<AmPhaseRecord> amPhaseRecords = amPhaseRecordService.findAllByMapper(aprR);
				// List<AmPhaseRecord> amPhaseRecords =
				// amPhaseRecordService.findAllByMapperAndCurrenttime(aprR);
				for (AmPhaseRecord apr : amPhaseRecords) {
					String msg = apr.getMeterState();
					apr.setDate(date);
					// 保存电表记录。
					saveAmmeterRecord(ammeter, apr);
					// 更新电表和电站。
					updateAmmeterAndStation(ammeter, apr);
					System.out.println("AmmeterJob--> 更新电表和电站更新成功！-->"
							+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
					// 已经处理
					apr.setDealt(1);
					//由于在更新电表的时候需要传入时间参数，使用了meterState字段作为参数传入。
					apr.setMeterState(msg);
					amPhaseRecordService.updateByPrimaryKeySelective(apr);
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
		ammeterRecord.setdAddr(apr.getdAddr());
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
		Double totalKw = 0d;
		ammeter.setStatusCode(statusCode);
		AmPhaseRecord apr1 = apr;
		if(apr1.getdAddr()==1){
			apr1.setdAddr(11L);
			AmPhaseRecord theDaddr = amPhaseRecordService.findOneByMapper4Daddr(apr1);
			if(theDaddr!=null){
				totalKw = apr1.getKw() + theDaddr.getKw();
			}else{
				totalKw = apr1.getKw();
			}
			apr.setdAddr(1L);
		}else if(apr1.getdAddr()==11){
			apr1.setdAddr(1L);
			AmPhaseRecord theDaddr = amPhaseRecordService.findOneByMapper4Daddr(apr1);
			if(theDaddr!=null){
				totalKw = apr1.getKw() + theDaddr.getKw();
			}else{
				totalKw = apr1.getKw();
			}
			apr.setdAddr(11L);
		}/*else if(apr1.getdAddr()==2){
			totalKw = apr1.getKw();
		}*/
		if(apr1.getdAddr()!=2){
			System.out.println("更新电表的Kw:" + totalKw);
			ammeter.setWorkTotalTm(ammeter.getWorkTotalTm() + 10);
			ammeter.setWorkTotalKwh(ammeter.getWorkTotalKwh() + kwhTol);
			ammeter.setNowKw(totalKw);
		}
		ammeter.setUpdateDtm(new Date());
		ammeterMapper.updateByPrimaryKeySelective(ammeter);
		// 更新电站 每天 的发电/用电
		saveTemStation(ammeter, apr, kwhTol);
	}

	/**
	 * 更新电站 每天 的发电/用电
	 *
	 * @param ammeter
	 * @param apr
	 * @param tolKwh
	 */
	private void saveTemStation(Ammeter ammeter, AmPhaseRecord apr, Double tolKwh) {
		Date meterTime = DateUtil.parseString(apr.getMeterTime().toString(), DateUtil.yyMMddHHmmss);
		String temStationYearRecordTime = DateUtil.formatDate(meterTime, "yyyy-MM-dd");
		String cAddr = ammeter.getcAddr();
		Long dAddr = apr.getdAddr();
		Integer dType = ammeter.getdType();
		Integer wAddr = 0;
		String ammeterCode = ammeter.getcAddr();
		if (dAddr == null) {
			return;
		}
		CharSequence subSequence = dAddr.toString().subSequence(0, 1);
		// 每天的
		ElecDataDay temStationYearR = new ElecDataDay();
		temStationYearR.setDevConfCode(cAddr);
		temStationYearR.setdType(dType);
		temStationYearR.setwAddr(wAddr);
		temStationYearR.setdAddr(dAddr);
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
		} else {
			if (subSequence.equals("1")) {
				temStationYear.setType(1);
			} else if (subSequence.equals("2")) {
				temStationYear.setType(2);
			}
			temStationYear.setKw(apr.getKw());
			temStationYear.setKwh(temStationYear.getKwh() + tolKwh);
			elecDataDayService.saveByMapper(temStationYear);
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
		amPhaseRecordR.setwAddr(0);
		amPhaseRecordR.setMeterTime(lastMeterTime);
		// 半夜和凌晨不发电。
		 String date = DateUtil.formatDate(new Date(), "yyyy_MM_dd");
		amPhaseRecordR.setDate(date);
		AmPhaseRecord lastAmPhaseRecord = amPhaseRecordService.findOneByMapper(amPhaseRecordR);
		if (lastAmPhaseRecord != null) {
			if (lastAmPhaseRecord.getKwhTotal() >= 0.0) {
				System.out.println(
						"当前kwhTotal：" + apr.getKwhTotal() + "\t10分钟前的kwhTotal：" + lastAmPhaseRecord.getKwhTotal());
				// 现在和前10分钟数据一致说明没发电。
				if (lastAmPhaseRecord.getKwhTotal() == apr.getKwhTotal()) {
					return 0.00;
				}
				kwhTol = apr.getKwhTotal() - lastAmPhaseRecord.getKwhTotal(); // 10分钟内发/用电
			}
		}
		if (kwhTol < 0) {
			System.out.println("kwhTol小于0");
			kwhTol = 0.0d;
			// System.exit(0);
		}
		System.out.println("电表码:" + apr.getcAddr() + "\tmeterTime:" + apr.getMeterTime() + "\t用发电类型：" + apr.getdAddr()
				+ "\tkwhTol:" + kwhTol);
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

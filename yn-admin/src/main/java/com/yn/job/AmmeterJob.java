package com.yn.job;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.yn.model.*;
import com.yn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yn.dao.AmPhaseRecordDao;
import com.yn.dao.AmmeterDao;
import com.yn.dao.AmmeterRecordDao;
import com.yn.dao.StationDao;
import com.yn.utils.DateUtil;


/**
 * 修改电表/电站信息
 * 保存电表工作状态记录
 */
//@Component
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
    ElecDataHourService elecDataHourService;
    @Autowired
    ElecDataDayService elecDataDayService;

    @Scheduled(fixedDelay = 10 * 1000)
    @Transactional
    private void job() {
        List<Ammeter> findAll = ammeterService.findAll(new Ammeter());
        for (Ammeter ammeter : findAll) {
            AmPhaseRecord aprR = new AmPhaseRecord();
            aprR.setcAddr(Integer.parseInt(ammeter.getcAddr()));
           // aprR.setdAddr(ammeter.getdAddr());
            aprR.setdType(ammeter.getdType());
            aprR.setiAddr(ammeter.getiAddr());
            aprR.setDealt(0);
            List<AmPhaseRecord> amPhaseRecords = amPhaseRecordService.findAll(aprR);
            for (AmPhaseRecord apr : amPhaseRecords) {
                apr.setDealt(1); // 已经处理
                amPhaseRecordDao.save(apr);

                saveAmmeterRecord(ammeter, apr.getMeterTime());
                updateAmmeterAndStation(ammeter, apr);
            }

            if (amPhaseRecords.size() == 0) {
                ammeter.setNowKw(0D);
                ammeterDao.save(ammeter);
            }
        }
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
        //ammeterRecord.setType(ammeter.getType());
        ammeterRecordService.save(ammeterRecord);
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
       // ammeter.setWorkTotaTm(ammeter.getWorkTotaTm() + 10);
       // ammeter.setWorkTotaKwh(ammeter.getWorkTotaKwh() + kwhTol);
        ammeterDao.save(ammeter);

        Long stationId = ammeter.getStationId();
        if (stationId != null) {
            Station station = stationService.findOne(stationId);
//            if (station.getWorkDtm() == null) {
//                station.setWorkDtm(new Date());
//            }
//            station.setWorkTotaTm(station.getWorkTotaTm() + 10);
//            if (ammeter.getType() == 1) {
//                station.setElectricityGenerationTol(station.getElectricityGenerationTol() + kwhTol);
//            } else if (ammeter.getType() == 2) {
//                station.setElectricityUseTol(station.getElectricityUseTol() + kwhTol);
//            }
            stationDao.save(station);
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
        String temStationRecordTime = DateUtil.formatDate(meterTime, "yyyyMMdd HH");
        String temStationYearRecordTime = DateUtil.formatDate(meterTime, "yyyyMMdd");

        if (ammeter.getStationId() != null) {
            String cAddr = ammeter.getcAddr();
            Long dAddr = apr.getdAddr();
            Integer dType = ammeter.getdType();
            Integer wAddr = apr.getwAddr();
            Long stationId = station.getId();
            String ammeterCode = ammeter.getcAddr();
            Long serverId = station.getServerId();
           // Integer type = ammeter.getType();
            CharSequence subSequence = dAddr.toString().subSequence(0, 1);

            // 每小时的
            ElecDataHour temStationR = new ElecDataHour();
            temStationR.setDevConfCode(cAddr);
            temStationR.setdAddr(dAddr);
            temStationR.setdType(dType);
            temStationR.setwAddr(wAddr);
//            temStationR.setStationId(stationId);
//            temStationR.setStationCode(stationCode);
            temStationR.setAmmeterCode(ammeterCode);
            //temStationR.setServerId(serverId);
           // temStationR.setType(type);
            temStationR.setRecordTime(temStationRecordTime);
            ElecDataHour temStation = elecDataHourService.findOne(temStationR);
            if (temStation == null) {
            	ElecDataHour newTemStation = new ElecDataHour();
                newTemStation.setDevConfCode(cAddr);
                newTemStation.setdAddr(dAddr);
                newTemStation.setdType(dType);
                newTemStation.setwAddr(wAddr);
//                newTemStation.setStationId(stationId);
//                newTemStation.setStationCode(stationCode);
                newTemStation.setKw(apr.getKw());
                newTemStation.setKwh(tolKwh);
                newTemStation.setRecordTime(temStationRecordTime);
                if(subSequence.equals("1")){
					newTemStation.setType(1);
				}else if(subSequence.equals("2")){
					newTemStation.setType(2);
				}
                elecDataHourService.save(newTemStation);
            } else {
                temStation.setKw(apr.getKw());
                temStation.setKwh(temStation.getKwh() + tolKwh);
                elecDataHourService.save(temStation);
            }

            // 每天的
            ElecDataDay temStationYearR = new ElecDataDay();
            temStationYearR.setDevConfCode(cAddr);
            temStationYearR.setdAddr(dAddr);
            temStationYearR.setdType(dType);
            temStationYearR.setwAddr(wAddr);
            /*temStationYearR.setStationId(stationId);
            temStationYearR.setStationCode(stationCode);*/
            temStationYearR.setAmmeterCode(ammeterCode);
            temStationYearR.setRecordTime(temStationYearRecordTime);
            ElecDataDay temStationYear = elecDataDayService.findOne(temStationYearR);
            if (temStationYear == null) {
            	ElecDataDay newTemStationYear = new ElecDataDay();
                newTemStationYear.setDevConfCode(cAddr);
                newTemStationYear.setdAddr(dAddr);
                newTemStationYear.setdType(dType);
                newTemStationYear.setwAddr(wAddr);
                /*newTemStationYear.setStationId(stationId);
                newTemStationYear.setStationCode(stationCode);*/
                newTemStationYear.setKw(apr.getKw());
                newTemStationYear.setKwh(tolKwh);
                newTemStationYear.setRecordTime(temStationYearRecordTime);
                if(subSequence.equals("1")){
					newTemStationYear.setType(1);
				}else if(subSequence.equals("2")){
					newTemStationYear.setType(2);
				}
                elecDataDayService.save(newTemStationYear);
            } else {
                temStationYear.setKw(apr.getKw());
                temStationYear.setKwh(temStationYear.getKwh() + tolKwh);
                elecDataDayService.save(temStationYear);
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

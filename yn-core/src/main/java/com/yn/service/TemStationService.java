package com.yn.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.TemStationDao;
import com.yn.domain.EachHourTemStation;
import com.yn.model.TemStation;
import com.yn.utils.BeanCopy;
import com.yn.utils.DateUtil;
import com.yn.utils.RepositoryUtil;

@Service
public class TemStationService {
    @Autowired
    TemStationDao temStationDao;

    public TemStation findOne(Long id) {
        return temStationDao.findOne(id);
    }

    public void save(TemStation temStation) {
        if(temStation.getId()!=null){
        	TemStation one = temStationDao.findOne(temStation.getId());
            try {
                BeanCopy.beanCopy(temStation,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            temStationDao.save(one);
        }else {
            temStationDao.save(temStation);
        }
    }

    public void delete(Long id) {
        temStationDao.delete(id);
    }
    
    public void deleteBatch(List<Long> id) {
		temStationDao.deleteBatch(id);
	}

    public TemStation findOne(TemStation temStation) {
        Specification<TemStation> spec = RepositoryUtil.getSpecification(temStation);
        TemStation findOne = temStationDao.findOne(spec);
        return findOne;
    }

    public List<TemStation> findAll(List<Long> list) {
        return temStationDao.findAll(list);
    }

    public Page<TemStation> findAll(TemStation temStation, Pageable pageable) {
        Specification<TemStation> spec = RepositoryUtil.getSpecification(temStation);
        Page<TemStation> findAll = temStationDao.findAll(spec, pageable);
        return findAll;
    }

    public List<TemStation> findAll(TemStation temStation) {
        Specification<TemStation> spec = RepositoryUtil.getSpecification(temStation);
        return temStationDao.findAll(spec);
    }
    
    /**
     * 查找今日每个时刻所有电站的发电量
     * @return
     */
    public List<EachHourTemStation> getTodayKwh(Long serverId) {
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    	List<Date> todayEachHourBegin = DateUtil.getTodayEachHourBegin();
    	List<Date> todayEachHourBegin2 = DateUtil.getTodayEachHourBegin2();
    	
    	List<EachHourTemStation> ts = new ArrayList<>();
    	for (int i = 0; i < todayEachHourBegin.size(); i++) {
    		Date startDtm = todayEachHourBegin.get(i);
    		Date endDtm = todayEachHourBegin2.get(i);
    		EachHourTemStation eachHourTemStation = new EachHourTemStation();
    		eachHourTemStation.setTime(startDtm);
    		eachHourTemStation.setTimeStr(sdf.format(startDtm));
    		if (serverId == null) {
    			double sumKwh = temStationDao.sumKwh(startDtm, endDtm, 1);
    			eachHourTemStation.setKwh(sumKwh);
			} else {
				double sumKwh = temStationDao.sumKwh(startDtm, endDtm, 1, serverId);
				eachHourTemStation.setKwh(sumKwh);
			}
    		ts.add(eachHourTemStation);
		}
    	
    	return ts;
    }
    
    /**
     * 根据电站id查找今日每个时刻的发电量/用电量
     * @return
     */
    public List<EachHourTemStation> getTodayKwhByStationId(Long stationId, Integer type) {
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    	List<Date> todayEachHourBegin = DateUtil.getTodayEachHourBegin();
    	List<Date> todayEachHourBegin2 = DateUtil.getTodayEachHourBegin2();
    	
    	List<EachHourTemStation> ts = new ArrayList<>();
    	for (int i = 0; i < todayEachHourBegin.size(); i++) {
    		Date startDtm = todayEachHourBegin.get(i);
    		Date endDtm = todayEachHourBegin2.get(i);
    		double sumKwh = temStationDao.sumKwhByStationId(startDtm, endDtm, type, stationId);
    		EachHourTemStation eachHourTemStation = new EachHourTemStation();
    		eachHourTemStation.setTime(startDtm);
    		eachHourTemStation.setTimeStr(sdf.format(startDtm));
			eachHourTemStation.setKwh(sumKwh);
    		ts.add(eachHourTemStation);
		}
    	
    	return ts;
    }
    
    /**
     * 今日发电/用电
     * @param stationId
     * @param type
     * @return
     */
    public double todayKwh(Long stationId, Integer type) {
    	Date[] todaySpace = DateUtil.getTodaySpace();
		double todayKwh = temStationDao.sumKwhByStationId(todaySpace[0], todaySpace[1], type, stationId);
		return todayKwh;
	}
    
    /**
     * 昨日发电/用电
     * @param stationId
     * @param type
     * @return
     */
    public double yesterdayKwh(Long stationId, Integer type) {
    	Date[] yesterdaySpace = DateUtil.getYesterdaySpace();
		double yesterdayKwh = temStationDao.sumKwhByStationId(yesterdaySpace[0], yesterdaySpace[1], type, stationId);
		return yesterdayKwh;
	}
    
    /**
     * 当月发电/用电
     * @param stationId
     * @param type
     * @return
     */
    public double thisMonthKwh(Long stationId, Integer type) {
    	Date[] thisMonthSpace = DateUtil.getThisMonthSpace();
		double thisMonthKwh = temStationDao.sumKwhByStationId(thisMonthSpace[0], thisMonthSpace[1], type, stationId);
		return thisMonthKwh;
	}
    
    /**
     * 上月发电/用电
     * @param stationId
     * @param type
     * @return
     */
    public double lastMonthKwh(Long stationId, Integer type) {
    	Date[] lastMonthSpace = DateUtil.getLastMonthSpace();
		double lastMonthKwh = temStationDao.sumKwhByStationId(lastMonthSpace[0], lastMonthSpace[1], type, stationId);
		return lastMonthKwh;
	}
    
    /**
     * 当年发电/用电
     * @param stationId
     * @param type
     * @return
     */
    public double thisYearKwh(Long stationId, Integer type) {
    	Date[] thisYearSpace = DateUtil.getThisYearSpace();
		double thisYearKwh = temStationDao.sumKwhByStationId(thisYearSpace[0], thisYearSpace[1], type, stationId);
		return thisYearKwh;
	}
    
    /**
     * 去年发电/用电
     * @param stationId
     * @param type
     * @return
     */
    public double lastYearKwh(Long stationId, Integer type) {
    	Date[] lastYearSpace = DateUtil.getLastYearSpace();
		double lastYearKwh = temStationDao.sumKwhByStationId(lastYearSpace[0], lastYearSpace[1], type, stationId);
		return lastYearKwh;
	}
	
    
}

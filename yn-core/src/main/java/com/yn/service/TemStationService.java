package com.yn.service;

import com.yn.dao.TemStationDao;
import com.yn.domain.EachHourTemStation;
import com.yn.enums.AmmeterTypeEnum;
import com.yn.model.Station;
import com.yn.model.TemStation;
import com.yn.utils.BeanCopy;
import com.yn.utils.DateUtil;
import com.yn.utils.ObjToMap;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TemStationService {
    @Autowired
    TemStationDao temStationDao;

    public TemStation findOne(Long id) {
        return temStationDao.findOne(id);
    }

    public void save(TemStation temStation) {
        if (temStation.getId() != null) {
            TemStation one = temStationDao.findOne(temStation.getId());
            try {
                BeanCopy.beanCopy(temStation, one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            temStationDao.save(one);
        } else {
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
        Specification<TemStation> spec = getSpecification(temStation);
        TemStation findOne = temStationDao.findOne(spec);
        return findOne;
    }

    public List<TemStation> findAll(List<Long> list) {
        return temStationDao.findAll(list);
    }

    public Page<TemStation> findAll(TemStation temStation, Pageable pageable) {
        Specification<TemStation> spec = getSpecification(temStation);
        Page<TemStation> findAll = temStationDao.findAll(spec, pageable);
        return findAll;
    }

    public List<TemStation> findAll(TemStation temStation) {
        Specification<TemStation> spec = getSpecification(temStation);
        return temStationDao.findAll(spec);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Specification<TemStation> getSpecification(TemStation temStation) {
        temStation.setDel(0);
        Map<String, Object> objectMap = ObjToMap.getObjectMap(temStation);
        return (Root<TemStation> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

            Predicate conjunction = cb.conjunction();
            List<Expression<Boolean>> expressions = conjunction.getExpressions();
            Iterator<Map.Entry<String, Object>> iterator = objectMap.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                if (!entry.getKey().equals("query") && !entry.getKey().equals("queryStartDtm") && !entry.getKey().equals("queryEndDtm")) {
                    Object value = entry.getValue();
                    if (value instanceof Map) {
                        Iterator<Map.Entry<String, Object>> iterator1 = ((Map) value).entrySet().iterator();
                        while (iterator1.hasNext()) {
                            Map.Entry<String, Object> entry1 = iterator1.next();
                            expressions.add(cb.equal(root.get(entry.getKey()).get(entry1.getKey()), entry1.getValue()));
                        }
                    } else {
                        expressions.add(cb.equal(root.get(entry.getKey()), value));
                    }
                }
            }

            // 根据xxx来查询
            String queryStr = temStation.getQuery();
            if (!StringUtils.isEmpty(queryStr)) {
//				Predicate[] predicates = new Predicate[2];
//				predicates[0] = cb.like(root.get("title"), "%" + queryStr + "%");
//				predicates[1] = cb.like(root.get("author"), "%" + queryStr + "%");
//				expressions.add(cb.or(predicates));
            }

            // 根据日期筛选
            String queryStartDtm = temStation.getQueryStartDtm();
            String queryEndDtm = temStation.getQueryEndDtm();
            if (!StringUtils.isEmpty(queryStartDtm)) {
                expressions.add(cb.greaterThanOrEqualTo(root.get("createDtm"), DateUtil.parseString(queryStartDtm, DateUtil.yyyy_MM_dd_HHmmss)));
            }
            if (!StringUtils.isEmpty(queryEndDtm)) {
                expressions.add(cb.lessThan(root.get("createDtm"), DateUtil.parseString(queryEndDtm, DateUtil.yyyy_MM_dd_HHmmss)));
            }

            return conjunction;
        };
    }


    /**
     * 查找今日每个时刻所有电站的发电 / 用电
     *
     * @return
     */
    public List<EachHourTemStation> getTodayKwh(Long serverId, Integer type) {

        Date[] todaySpace = DateUtil.getTodaySpace();
        Date start = todaySpace[0];
        Date end = todaySpace[1];


        List<TemStation> temStationList = new ArrayList<>();
        if (serverId == null) {
            temStationList = temStationDao.findByTypeAndCreateDtmBetween(type, start, end);
        }
        if (serverId != null) {
            temStationList = temStationDao.findByServerIdAndTypeAndCreateDtmBetween(serverId, type, start, end);
        }


        List<EachHourTemStation> eachHourTemStationList = groupByEachHourInOneDay(temStationList);


        return eachHourTemStationList;
    }


    /**
     * 根据电站id查找今日每个时刻的发电量/用电量
     *
     * @return
     */
    public List<EachHourTemStation> getTodayKwhByStationId(Long stationId, Integer type) {

        Date[] todaySpace = DateUtil.getTodaySpace();
        Date start = todaySpace[0];
        Date end = todaySpace[1];


        List<TemStation> temStationList = temStationDao.findByStationIdAndTypeAndCreateDtmBetween(stationId, type, start, end);


        List<EachHourTemStation> eachHourTemStationList = groupByEachHourInOneDay(temStationList);


        return eachHourTemStationList;
    }


    /**
     * 计算某一天内每个小时的 发电/用电
     * @param temStationList
     * @return
     */
    public List<EachHourTemStation> groupByEachHourInOneDay(List<TemStation> temStationList) {
        List<EachHourTemStation> eachHourTemStationList = new ArrayList<>();


        List<Date> todayEachHourBegin = DateUtil.getTodayEachHourBegin();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:00");
        for (Date each : todayEachHourBegin) {
            String timeStr = sdf.format(each);
            EachHourTemStation eachHourTemStation = new EachHourTemStation();
            eachHourTemStation.setTimeStr(timeStr);
            eachHourTemStation.setKw(0D);
            eachHourTemStation.setKwh(0D);


            for (TemStation ts : temStationList) {
                if (sdf.format(ts.getCreateDtm()).equals(timeStr)) {
                    eachHourTemStation.setKw(eachHourTemStation.getKw() + ts.getKw());
                    eachHourTemStation.setKwh(eachHourTemStation.getKwh() + ts.getKwh());
                }
            }


            eachHourTemStationList.add(eachHourTemStation);
        }

        return eachHourTemStationList;
    }


    /**
     * 今日发电/用电
     *
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
     *
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
     *
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
     *
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
     *
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
     *
     * @param stationId
     * @param type
     * @return
     */
    public double lastYearKwh(Long stationId, Integer type) {
        Date[] lastYearSpace = DateUtil.getLastYearSpace();
        double lastYearKwh = temStationDao.sumKwhByStationId(lastYearSpace[0], lastYearSpace[1], type, stationId);
        return lastYearKwh;
    }

    /**
     * 用户发电量
     *
     * @param stations
     * @return
     */
	public Map<Object, Object> monthKwh(List<Station> stations){
    	Map<Object, Object> objectMap = new TreeMap<Object, Object>();
    	Map<Object, Object> linkHashMap=new LinkedHashMap<>();
    	List<Map<Object, Object>> lists=new ArrayList<>();
    	
    	for (Station station : stations) {
    		List<Map<Object, Object>> list=temStationDao.sumMonthKwh(station.getId());
           if (!list.isEmpty()) {
	          lists.addAll(list);
			}
    			
    	}
    	for(Map<Object, Object> map : lists) {
    		if (!objectMap.containsKey(map.get("create_dtm"))) {
    			
    			objectMap.put(map.get("create_dtm"), map.get("kwh"));
			}else{
				double kwh=(double)objectMap.get(map.get("create_dtm"))+(double)map.get("kwh");
				objectMap.put(map.get("create_dtm"), (Object)kwh);
			}
    		
    	}
    	Object[] key = objectMap.keySet().toArray();
    	Arrays.sort(key);
    	for (int i = 0; i < key.length; i++) { 
    		linkHashMap.put(key[i], objectMap.get(key[i]));
        	}
    	
    	return objectMap;
    }

}

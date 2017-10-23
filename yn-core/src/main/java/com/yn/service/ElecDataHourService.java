package com.yn.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yn.dao.ElecDataHourDao;
import com.yn.dao.mapper.ElecDataHourMapper;
import com.yn.model.ElecDataHour;
import com.yn.model.Station;
import com.yn.utils.BeanCopy;
import com.yn.utils.DateUtil;
import com.yn.utils.ObjToMap;

@Service
public class ElecDataHourService {
    @Autowired
    ElecDataHourDao elecDataHourDao;
    @Autowired
    ElecDataHourMapper elecDataHourMapper;

    public ElecDataHour findOne(Long id) {
        return elecDataHourDao.findOne(id);
    }

    public void save(ElecDataHour elecDataHour) {
        if (elecDataHour.getId() != null) {
        	ElecDataHour one = elecDataHourDao.findOne(elecDataHour.getId());
            try {
                BeanCopy.beanCopy(elecDataHour, one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            elecDataHourDao.save(one);
        } else {
            elecDataHourDao.save(elecDataHour);
        }
    }
    public void saveByMapper(ElecDataHour elecDataHour) {
        if (elecDataHour.getId() != null) {
        	ElecDataHour one = elecDataHourDao.findOne(elecDataHour.getId());
            try {
                BeanCopy.beanCopy(elecDataHour, one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            elecDataHourMapper.updateByPrimaryKeySelective(elecDataHour);
        } else {
        	elecDataHourMapper.insert(elecDataHour);
        }
    }

    public void delete(Long id) {
        elecDataHourDao.delete(id);
    }

    public void deleteBatch(List<Long> id) {
        elecDataHourDao.deleteBatch(id);
    }

    public ElecDataHour findOne(ElecDataHour elecDataHour) {
        Specification<ElecDataHour> spec = getSpecification(elecDataHour);
        ElecDataHour findOne = elecDataHourDao.findOne(spec);
        return findOne;
    }

    public List<ElecDataHour> findAll(List<Long> list) {
        return elecDataHourDao.findAll(list);
    }

    public Page<ElecDataHour> findAll(ElecDataHour ElecDataHour, Pageable pageable) {
        Specification<ElecDataHour> spec = getSpecification(ElecDataHour);
        Page<ElecDataHour> findAll = elecDataHourDao.findAll(spec, pageable);
        return findAll;
    }

    public List<ElecDataHour> findAll(ElecDataHour ElecDataHour) {
        Specification<ElecDataHour> spec = getSpecification(ElecDataHour);
        return elecDataHourDao.findAll(spec);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Specification<ElecDataHour> getSpecification(ElecDataHour ElecDataHour) {
        ElecDataHour.setDel(0);
        Map<String, Object> objectMap = ObjToMap.getObjectMap(ElecDataHour);
        return (Root<ElecDataHour> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

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
            String queryStr = ElecDataHour.getQuery();
            if (!StringUtils.isEmpty(queryStr)) {
//				Predicate[] predicates = new Predicate[2];
//				predicates[0] = cb.like(root.get("title"), "%" + queryStr + "%");
//				predicates[1] = cb.like(root.get("author"), "%" + queryStr + "%");
//				expressions.add(cb.or(predicates));
            }

            // 根据日期筛选
            String queryStartDtm = ElecDataHour.getQueryStartDtm();
            String queryEndDtm = ElecDataHour.getQueryEndDtm();
            Long  dAddr= ElecDataHour.getdAddr();
            if (!StringUtils.isEmpty(dAddr)) {
            	expressions.add(cb.like(root.get("dAddr"), dAddr+"%"));
            }
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
    public List<ElecDataHour> getTodayKwh(Long serverId, Long  dAddr) {

        Date[] todaySpace = DateUtil.getTodaySpace();
        Date start = todaySpace[0];
        Date end = todaySpace[1];


        List<ElecDataHour> ElecDataHourList = new ArrayList<>();
        if (serverId == null) {
            ElecDataHourList = elecDataHourDao.findByDAddrAndCreateDtmBetween(dAddr, start, end);
        }
        if (serverId != null) {
            ElecDataHourList = elecDataHourDao.findByServerIdAndDAddrAndCreateDtmBetween(serverId, dAddr, start, end);
        }


        List<ElecDataHour> eachHourElecDataHourList = groupByEachHourInOneDay(ElecDataHourList);


        return eachHourElecDataHourList;
    }


    /**
     * 根据电站id查找今日每个时刻的发电量/用电量
     *
     * @return
     */
//    public List<ElecDataHour> getTodayKwhByStationId(Long stationId, Long  dAddr) {
//
//        Date[] todaySpace = DateUtil.getTodaySpace();
//        Date start = todaySpace[0];
//        Date end = todaySpace[1];
//
//
//        List<ElecDataHour> ElecDataHourList = elecDataHourDao.findByStationIdAndDAddrAndCreateDtmBetween(stationId, dAddr, start, end);
//
//
//        List<ElecDataHour> eachHourElecDataHourList = groupByEachHourInOneDay(ElecDataHourList);
//
//
//        return eachHourElecDataHourList;
//    }


    /**
     * 计算某一天内每个小时的 发电/用电
     * @param ElecDataHourList
     * @return
     */
    public List<ElecDataHour> groupByEachHourInOneDay(List<ElecDataHour> ElecDataHourList) {
        List<ElecDataHour> eachHourElecDataHourList = new ArrayList<>();


        List<Date> todayEachHourBegin = DateUtil.getTodayEachHourBegin();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:00");
        for (Date each : todayEachHourBegin) {
            String timeStr = sdf.format(each);
            ElecDataHour eachHourElecDataHour = new ElecDataHour();
//            eachHourElecDataHour.setTimeStr(timeStr);
            eachHourElecDataHour.setKw(0D);
            eachHourElecDataHour.setKwh(0D);


            for (ElecDataHour ts : ElecDataHourList) {
                if (sdf.format(ts.getCreateDtm()).equals(timeStr)) {
                    eachHourElecDataHour.setKw(eachHourElecDataHour.getKw() + ts.getKw());
                    eachHourElecDataHour.setKwh(eachHourElecDataHour.getKwh() + ts.getKwh());
                }
            }


            eachHourElecDataHourList.add(eachHourElecDataHour);
        }

        return eachHourElecDataHourList;
    }


    /**
     * 今日发电/用电
     *
     * @param stationId
     * @param type
     * @return
     */
//    public double todayKwh(Long stationId, Long dAddr) {
//        Date[] todaySpace = DateUtil.getTodaySpace();
//        double todayKwh = elecDataHourDao.sumKwhByStationId(todaySpace[0], todaySpace[1], dAddr, stationId);
//        return todayKwh;
//    }

    /**
     * 昨日发电/用电
     *
     * @param stationId
     * @param type
     * @return
     */
//    public double yesterdayKwh(Long stationId, Long dAddr) {
//        Date[] yesterdaySpace = DateUtil.getYesterdaySpace();
//        double yesterdayKwh = elecDataHourDao.sumKwhByStationId(yesterdaySpace[0], yesterdaySpace[1], dAddr, stationId);
//        return yesterdayKwh;
//    }

    /**
     * 当月发电/用电
     *
     * @param stationId
     * @param type
     * @return
     */
//    public double thisMonthKwh(Long stationId, Long dAddr) {
//        Date[] thisMonthSpace = DateUtil.getThisMonthSpace();
//        double thisMonthKwh = elecDataHourDao.sumKwhByStationId(thisMonthSpace[0], thisMonthSpace[1], dAddr, stationId);
//        return thisMonthKwh;
//    }

    /**
     * 上月发电/用电
     *
     * @param stationId
     * @param type
     * @return
     */
//    public double lastMonthKwh(Long stationId, Long dAddr) {
//        Date[] lastMonthSpace = DateUtil.getLastMonthSpace();
//        double lastMonthKwh = elecDataHourDao.sumKwhByStationId(lastMonthSpace[0], lastMonthSpace[1], dAddr, stationId);
//        return lastMonthKwh;
//    }

    /**
     * 当年发电/用电
     *
     * @param stationId
     * @param type
     * @return
     */
//    public double thisYearKwh(Long stationId, Long dAddr) {
//        Date[] thisYearSpace = DateUtil.getThisYearSpace();
//        double thisYearKwh = elecDataHourDao.sumKwhByStationId(thisYearSpace[0], thisYearSpace[1], dAddr, stationId);
//        return thisYearKwh;
//    }

    /**
     * 去年发电/用电
     *
     * @param stationId
     * @param type
     * @return
     */
//    public double lastYearKwh(Long stationId, Long dAddr) {
//        Date[] lastYearSpace = DateUtil.getLastYearSpace();
//        double lastYearKwh = elecDataHourDao.sumKwhByStationId(lastYearSpace[0], lastYearSpace[1], dAddr, stationId);
//        return lastYearKwh;
//    }

    /**
     * 用户每月发电量
     *
     * @param stations
     * @return
     */
	public List<Map<Object,Object>> monthKwh(List<Station> stations){
    	Map<Object, Object> objectMap = new TreeMap<Object, Object>();
    	Map<Object, Object> linkHashMap=new LinkedHashMap<>();
    	List<Map<Object, Object>> lists=new ArrayList<>();
    	List<Map<Object, Object>> listsMap=new ArrayList<>();
    	for (Station station : stations) {
    		List<Map<Object, Object>> list=elecDataHourDao.sumMonthKwh(station.getId());
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
    	for (int i = 0; i < key.length; i++) { 
    		Map<Object, Object> listMap=new LinkedHashMap<>();
    		linkHashMap.put(key[i], objectMap.get(key[i]));
    		listMap.put("createDtm", key[i]);
    		listMap.put("capacity", objectMap.get(key[i]));
    		listsMap.add(listMap);
        	}
    	return listsMap;
    }
	
	/**
	 * 获取当前时间的发电/用电总量
	 */
//	public Map<String, Object> getNowToalKwh(Long stationId, Long dAddr, Date date) {
//
//		Date start = date;
//
//		Date end = new Date();
//
//		List<ElecDataHour> ElecDataHourList = elecDataHourDao.findByStationIdAndDAddrAndCreateDtmBetween(stationId, dAddr,
//				start, end);
//		Double toalKwh = 0D;
//		Double kw = 0D;
//		Map<String, Object> map = new HashMap<>();
//		for (ElecDataHour ElecDataHour : ElecDataHourList) {
//			toalKwh += ElecDataHour.getKwh();
//			kw=ElecDataHour.getKw();
//			
//		}
//		map.put("toalKwh", NumberUtil.accurateToTwoDecimal(toalKwh));
//		 
//		map.put("kw",NumberUtil.accurateToTwoDecimal(kw) );
//		map.put("todayKwh",NumberUtil.accurateToTwoDecimal(todayKwh(stationId, dAddr)));
//
//		return map;
//
//	}
	
	/**
	 * 当前一个小时内的发电、用电量
	 */
//	public List<Map<String, Object>> oneHourKwh(Long stationId, Long dAddr) {
//		List<Map<String, Object>> list=new ArrayList<>();
//		Date time  = new Date();
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 12);
//		SimpleDateFormat dFormat=new SimpleDateFormat("HHmm");
//		List<ElecDataHour> ElecDataHourList = elecDataHourDao.findByStationIdAndDAddrAndCreateDtmBetween(stationId, dAddr,
//				calendar.getTime(), time);
//		
//		for (ElecDataHour ElecDataHour : ElecDataHourList) {
//			Map<String, Object> map = new HashMap<>();
//			map.put("time", dFormat.format(ElecDataHour.getCreateDtm()));
//			map.put("kw", NumberUtil.accurateToTwoDecimal(ElecDataHour.getKw()));
//			list.add(map);
//		}
//		return list;
//		
//	}

	public List<ElecDataHour> findByMapper(ElecDataHour elecDataHour) {
		List<ElecDataHour> list=elecDataHourMapper.selectByQuery(elecDataHour);
        return list;
    }

}

package com.yn.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

import com.yn.dao.ElecDataDayDao;
import com.yn.dao.mapper.ElecDataDayMapper;
import com.yn.model.ElecDataDay;
import com.yn.model.Station;
import com.yn.utils.BeanCopy;
import com.yn.utils.DateUtil;
import com.yn.utils.NumberUtil;
import com.yn.utils.ObjToMap;
import com.yn.utils.RepositoryUtil;


@Service
public class ElecDataDayService {
    @Autowired
    ElecDataDayDao elecDataDayDao;
    @Autowired
    ElecDataDayMapper elecDataDayMapper;
    @Autowired
    ElecDataHourService elecDataHourService;

    public ElecDataDay findOne(Long id) {
        return elecDataDayDao.findOne(id);
    }

    public void save(ElecDataDay elecDataDay) {
        if(elecDataDay.getId()!=null){
        	ElecDataDay one = elecDataDayDao.findOne(elecDataDay.getId());
            try {
                BeanCopy.beanCopy(elecDataDay,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            elecDataDayDao.save(one);
        }else {
        	elecDataDayDao.save(elecDataDay);
        }
    } 
    public void saveByMapper(ElecDataDay elecDataDay) {
        if(elecDataDay.getId()!=null){
        	ElecDataDay one = elecDataDayDao.findOne(elecDataDay.getId());
            try {
                BeanCopy.beanCopy(elecDataDay,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            elecDataDayMapper.updateByPrimaryKeySelective(one);
        }else {
        	elecDataDayMapper.insert(elecDataDay);
        }
    }

    public void delete(Long id) {
    	elecDataDayDao.delete(id);
    }
    
    public void deleteBatch(List<Long> id) {
    	elecDataDayDao.deleteBatch(id);
	}

    public ElecDataDay findOne(ElecDataDay elecDataDay) {
        Specification<ElecDataDay> spec = RepositoryUtil.getSpecification(elecDataDay);
        ElecDataDay findOne = elecDataDayDao.findOne(spec);
        return findOne;
    }

    public List<ElecDataDay> findAll(List<Long> list) {
        return elecDataDayDao.findAll(list);
    }

    public Page<ElecDataDay> findAll(ElecDataDay elecDataDay, Pageable pageable) {
        Specification<ElecDataDay> spec = RepositoryUtil.getSpecification(elecDataDay);
        Page<ElecDataDay> findAll = elecDataDayDao.findAll(spec, pageable);
        return findAll;
    }

    public List<ElecDataDay> findAll(ElecDataDay elecDataDay) {
        Specification<ElecDataDay> spec = RepositoryUtil.getSpecification(elecDataDay);
        return elecDataDayDao.findAll(spec);
    }
    
   public ElecDataDay findHuanbao(Map<String, Object> map){
	   
		return elecDataDayMapper.findHuanbao(map);	
    }
    
    
    
    /**
     * 用户每月发电量
     *
     * @param stations
     * @return
     */
//	public List<Map<Object,Object>> monthKwh(List<Station> stations){
//    	Map<Object, Object> objectMap = new TreeMap<Object, Object>();
//    	Map<Object, Object> linkHashMap=new LinkedHashMap<>();
//    	List<Map<Object, Object>> lists=new ArrayList<>();
//    	List<Map<Object, Object>> listsMap=new ArrayList<>();
//    	for (Station station : stations) {
//    		List<Map<Object, Object>> list=elecDataDayDao.sumMonthKwh(station.getId());
//           if (!list.isEmpty()) {
//	          lists.addAll(list);
//			}
//    			
//    	}
//    	for(Map<Object, Object> map : lists) {
//    		if (!objectMap.containsKey(map.get("create_dtm"))) {
//    			
//    			objectMap.put(map.get("create_dtm"), map.get("kwh"));
//			}else{
//				double kwh=(double)objectMap.get(map.get("create_dtm"))+(double)map.get("kwh");
//				objectMap.put(map.get("create_dtm"), (Object)kwh);
//			}
//    		
//    	}
//    	Object[] key = objectMap.keySet().toArray();
//    	for (int i = 0; i < key.length; i++) { 
//    		Map<Object, Object> listMap=new LinkedHashMap<>();
//    		linkHashMap.put(key[i], objectMap.get(key[i]));
//    		listMap.put("createDtm", key[i]);
//    		listMap.put("kwh", NumberUtil.accurateToTwoDecimal((Double)objectMap.get(key[i])));
//    		listsMap.add(listMap);
//        	}
//    	return listsMap;
//    }
	
	/**
     * 用户每月发电量
     *
     * @param stations
     * @return
     */
//	public List<Map<Object,Object>> numKwh(List<Station> stations ,Integer type ,String dateStr){
//    	Map<Object, Object> objectMap = new TreeMap<Object, Object>();
//    	Map<Object, Object> linkHashMap=new LinkedHashMap<>();
//    	List<Map<Object, Object>> lists=new ArrayList<>();
//    	List<Map<Object, Object>> listsMap=new ArrayList<>();
//    	
//    	   String dateFormat="";
//       	if (type == 0) {
//       		 dateFormat="%Y";
//   		} else if (type == 1) {
//   			dateFormat="%Y-%m";
//   		} else if (type == 2) {
//   			dateFormat="%Y-%m-%d";	
//   		} 
//    	for (Station station : stations) {
//    		List<Map<Object, Object>> list=elecDataDayDao.sumKwh(station.getId(),dateFormat,dateStr);
//           if (!list.isEmpty()) {
//	          lists.addAll(list);
//			}
//    			
//    	}
//    	for(Map<Object, Object> map : lists) {
//    		if (!objectMap.containsKey(map.get("create_dtm"))) {
//    			
//    			objectMap.put(map.get("create_dtm"), map.get("kwh"));
//			}else{
//				double kwh=(double)objectMap.get(map.get("create_dtm"))+(double)map.get("kwh");
//				objectMap.put(map.get("create_dtm"), (Object)kwh);
//			}
//    		
//    	}
//    	Object[] key = objectMap.keySet().toArray();
//    	for (int i = 0; i < key.length; i++) { 
//    		Map<Object, Object> listMap=new LinkedHashMap<>();
//    		linkHashMap.put(key[i], objectMap.get(key[i]));
//    		listMap.put("createDtm", key[i]);
//    		listMap.put("kwh", NumberUtil.accurateToTwoDecimal((Double)objectMap.get(key[i])));
//    		listsMap.add(listMap);
//        	}
//    	
//    	
//    	return listsMap;
//    }
//	
	/**
	 * 用户发电/用电统计图
	 */
//	public Map<String,Object> workUseCount(Long stationId ,Long dAddr) {
//		
//		List<Map<Object, Object>> lists=elecDataDayDao.workUseCount(stationId, dAddr);
//		Map<Object, Object> linkHashMap=new LinkedHashMap<>();
//		List<Map<Object, Object>> listsMap=new ArrayList<>();
//		Map<Object, Object> objectMap = new TreeMap<Object, Object>();
//		for(Map<Object, Object> map : lists) {
//    		if (!objectMap.containsKey(map.get("create_dtm"))) {
//    			
//    			objectMap.put(map.get("create_dtm"), map.get("kwh"));
//			}else{
//				double kwh=(double)objectMap.get(map.get("create_dtm"))+(double)map.get("kwh");
//				objectMap.put(map.get("create_dtm"), (Object)kwh);
//			}
//    		
//    	}
//		Object[] key = objectMap.keySet().toArray();
//    	for (int i = 0; i < key.length; i++) { 
//    		Map<Object, Object> listMap=new LinkedHashMap<>();
//    		linkHashMap.put(key[i], objectMap.get(key[i]));
//    		listMap.put("createDtm", key[i]);
//    		listMap.put("kwh", NumberUtil.accurateToTwoDecimal((Double)objectMap.get(key[i])));
//    		listsMap.add(listMap);
//        	}
//    	
//    	
//    	Map<String, Object> map=new HashMap<>();
//    	map.put("workUseCount", listsMap);
//    	map.put("thisYearKwh",NumberUtil.accurateToTwoDecimal(elecDataHourService.thisYearKwh(stationId, dAddr)) );
//    	map.put("thisMonthKwh", NumberUtil.accurateToTwoDecimal(elecDataHourService.thisMonthKwh(stationId, dAddr)));
//    	map.put("lastYearKwh",NumberUtil.accurateToTwoDecimal(elecDataHourService.lastYearKwh(stationId, dAddr)) );
//    	map.put("lastMonthKwh", NumberUtil.accurateToTwoDecimal(elecDataHourService.lastMonthKwh(stationId, dAddr)));
//    	map.put("todayKwh", NumberUtil.accurateToTwoDecimal(elecDataHourService.elecDataHourService(stationId, dAddr)));
//    	map.put("yesterdayKwh", NumberUtil.accurateToTwoDecimal(elecDataHourService.yesterdayKwh(stationId, dAddr)));
//    	return map;
//	}
	
	 public Page<ElecDataDay> listCount(ElecDataDay temStationYear, Pageable pageable) {
		 Specification<ElecDataDay> spec = getSpecification(temStationYear);
	        Page<ElecDataDay> findAll = elecDataDayDao.findAll(spec, pageable);
	        
	        return findAll;
	    }
	
	 @SuppressWarnings({"unchecked", "rawtypes"})
	    public static Specification<ElecDataDay> getSpecification(ElecDataDay temStationYear) {
		 temStationYear.setDel(0);
	        Map<String, Object> objectMap = ObjToMap.getObjectMap(temStationYear);
	        return (Root<ElecDataDay> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

	            Predicate conjunction = cb.conjunction();
	            List<Expression<Boolean>> expressions = conjunction.getExpressions();
	            Iterator<Entry<String, Object>> iterator = objectMap.entrySet().iterator();

	            while (iterator.hasNext()) {
	                Entry<String, Object> entry = iterator.next();
	                if (!entry.getKey().equals("query") && !entry.getKey().equals("queryStartDtm") && !entry.getKey().equals("queryEndDtm")) {
	                    Object value = entry.getValue();
	                    if (value instanceof Map) {
	                        Iterator<Entry<String, Object>> iterator1 = ((Map) value).entrySet().iterator();
	                        while (iterator1.hasNext()) {
	                            Entry<String, Object> entry1 = iterator1.next();
	                            expressions.add(cb.equal(root.get(entry.getKey()).get(entry1.getKey()), entry1.getValue()));
	                        }
	                    } else {
	                        expressions.add(cb.equal(root.get(entry.getKey()), value));
	                    }
	                }
	            }

	            // 根据日期筛选
	            String queryStartDtm = temStationYear.getQueryStartDtm();
	            String queryEndDtm = temStationYear.getQueryEndDtm();
	            Long  dAddr= temStationYear.getdAddr();
	            if (!StringUtils.isEmpty(dAddr)) {
	            	expressions.add(cb.like(root.get("dAddr"), dAddr+"%"));
	            }
	            if (!StringUtils.isEmpty(queryStartDtm)) {
	                expressions.add(cb.greaterThanOrEqualTo(root.get("createDtm"), DateUtil.parseString(queryStartDtm, DateUtil.yyyy_MM_dd)));
	            }
	            if (!StringUtils.isEmpty(queryEndDtm)) {
	                expressions.add(cb.lessThan(root.get("createDtm"), DateUtil.parseString(queryEndDtm, DateUtil.yyyy_MM_dd)));
	            }

	            return conjunction;
	        };
	    }

	 
	 public List<ElecDataDay> findByMapper(ElecDataDay elecDataDay) {
			
			List<ElecDataDay> list=elecDataDayMapper.selectByQuery(elecDataDay);
	        return list;
	    }
}

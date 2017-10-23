package com.yn.service;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.yn.dao.TemStationYearDao;
import com.yn.dao.mapper.TemStationYearMapper;
import com.yn.model.Station;
import com.yn.model.TemStation;
import com.yn.model.TemStationYear;
import com.yn.utils.BeanCopy;
import com.yn.utils.NumberUtil;
import com.yn.utils.ObjToMap;
import com.yn.utils.RepositoryUtil;
import com.yn.utils.DateUtil;


@Service
public class TemStationYearService {
    @Autowired
    TemStationYearDao temStationYearDao;
    @Autowired
    TemStationYearMapper temStationYearMapper;
    @Autowired
    TemStationService temStationService;

    public TemStationYear findOne(Long id) {
        return temStationYearDao.findOne(id);
    }

    public void save(TemStationYear temStationYear) {
        if(temStationYear.getId()!=null){
        	TemStationYear one = temStationYearDao.findOne(temStationYear.getId());
            try {
                BeanCopy.beanCopy(temStationYear,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            temStationYearDao.save(one);
        }else {
            temStationYearDao.save(temStationYear);
        }
    } 
    public void saveByMapper(TemStationYear temStationYear) {
        if(temStationYear.getId()!=null){
        	TemStationYear one = temStationYearDao.findOne(temStationYear.getId());
            try {
                BeanCopy.beanCopy(temStationYear,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            temStationYearMapper.updateByPrimaryKeySelective(one);
        }else {
        	temStationYearMapper.insert(temStationYear);
        }
    }

    public void delete(Long id) {
        temStationYearDao.delete(id);
    }
    
    public void deleteBatch(List<Long> id) {
		temStationYearDao.deleteBatch(id);
	}

    public TemStationYear findOne(TemStationYear temStationYear) {
        Specification<TemStationYear> spec = RepositoryUtil.getSpecification(temStationYear);
        TemStationYear findOne = temStationYearDao.findOne(spec);
        return findOne;
    }

    public List<TemStationYear> findAll(List<Long> list) {
        return temStationYearDao.findAll(list);
    }

    public Page<TemStationYear> findAll(TemStationYear temStationYear, Pageable pageable) {
        Specification<TemStationYear> spec = RepositoryUtil.getSpecification(temStationYear);
        Page<TemStationYear> findAll = temStationYearDao.findAll(spec, pageable);
        return findAll;
    }

    public List<TemStationYear> findAll(TemStationYear temStationYear) {
        Specification<TemStationYear> spec = RepositoryUtil.getSpecification(temStationYear);
        return temStationYearDao.findAll(spec);
    }
    
   public TemStationYear findHuanbao(Map<String, Object> map){
		return temStationYearMapper.findHuanbao(map);	
    }
    
    
    
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
    		List<Map<Object, Object>> list=temStationYearDao.sumMonthKwh(station.getId());
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
    		listMap.put("kwh", NumberUtil.accurateToTwoDecimal((Double)objectMap.get(key[i])));
    		listsMap.add(listMap);
        	}
    	return listsMap;
    }
	
	/**
     * 用户每月发电量
     *
     * @param stations
     * @return
     */
	public List<Map<Object,Object>> numKwh(List<Station> stations ,Integer type ,String dateStr){
    	Map<Object, Object> objectMap = new TreeMap<Object, Object>();
    	Map<Object, Object> linkHashMap=new LinkedHashMap<>();
    	List<Map<Object, Object>> lists=new ArrayList<>();
    	List<Map<Object, Object>> listsMap=new ArrayList<>();
    	
    	   String dateFormat="";
       	if (type == 0) {
       		 dateFormat="%Y";
   		} else if (type == 1) {
   			dateFormat="%Y-%m";
   		} else if (type == 2) {
   			dateFormat="%Y-%m-%d";	
   		} 
    	for (Station station : stations) {
    		List<Map<Object, Object>> list=temStationYearDao.sumKwh(station.getId(),dateFormat,dateStr);
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
    		listMap.put("kwh", NumberUtil.accurateToTwoDecimal((Double)objectMap.get(key[i])));
    		listsMap.add(listMap);
        	}
    	
    	
    	return listsMap;
    }
	
	/**
	 * 用户发电/用电统计图
	 */
	public Map<String,Object> workUseCount(Long stationId ,Long dAddr) {
		
		List<Map<Object, Object>> lists=temStationYearDao.workUseCount(stationId, dAddr);
		Map<Object, Object> linkHashMap=new LinkedHashMap<>();
		List<Map<Object, Object>> listsMap=new ArrayList<>();
		Map<Object, Object> objectMap = new TreeMap<Object, Object>();
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
    		listMap.put("kwh", NumberUtil.accurateToTwoDecimal((Double)objectMap.get(key[i])));
    		listsMap.add(listMap);
        	}
    	
    	
    	Map<String, Object> map=new HashMap<>();
    	map.put("workUseCount", listsMap);
    	map.put("thisYearKwh",NumberUtil.accurateToTwoDecimal(temStationService.thisYearKwh(stationId, dAddr)) );
    	map.put("thisMonthKwh", NumberUtil.accurateToTwoDecimal(temStationService.thisMonthKwh(stationId, dAddr)));
    	map.put("lastYearKwh",NumberUtil.accurateToTwoDecimal(temStationService.lastYearKwh(stationId, dAddr)) );
    	map.put("lastMonthKwh", NumberUtil.accurateToTwoDecimal(temStationService.lastMonthKwh(stationId, dAddr)));
    	map.put("todayKwh", NumberUtil.accurateToTwoDecimal(temStationService.todayKwh(stationId, dAddr)));
    	map.put("yesterdayKwh", NumberUtil.accurateToTwoDecimal(temStationService.yesterdayKwh(stationId, dAddr)));
    	
    	return map;
		
	}
	
	 public Page<TemStationYear> listCount(TemStationYear temStationYear, Pageable pageable) {
		 Specification<TemStationYear> spec = getSpecification(temStationYear);
	        Page<TemStationYear> findAll = temStationYearDao.findAll(spec, pageable);
	        
	        return findAll;
	    }
	
	 @SuppressWarnings({"unchecked", "rawtypes"})
	    public static Specification<TemStationYear> getSpecification(TemStationYear temStationYear) {
		 temStationYear.setDel(0);
	        Map<String, Object> objectMap = ObjToMap.getObjectMap(temStationYear);
	        return (Root<TemStationYear> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

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
	 
	 
	 public List<TemStationYear> findByMapper(TemStationYear temStationYear) {
			
			List<TemStationYear> list=temStationYearMapper.selectByQuery(temStationYear);
	        return list;
	    }
}

package com.yn.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yn.dao.SystemConfigDao;
import com.yn.model.SystemConfig;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class SystemConfigService {
    @Autowired
    SystemConfigDao systemConfigDao;

    public SystemConfig findOne(Long id) {
    	
        return systemConfigDao.findOne(id);
    }
    public SystemConfig findByPropertyKey(String propertyKey) {
		return systemConfigDao.findByPropertyKey(propertyKey);
	}
    
    public String get(String propertyKey) {
    	SystemConfig systemConfig = systemConfigDao.findByPropertyKey(propertyKey);
		return systemConfig.getPropertyValue();
	}
    
    /**
     * 转换成数组
     * @param propertyKey
     * @param class1
     * @return
     */
    public <T> List<T> getArray(String key,Class<T> class1) {
    	SystemConfig systemConfig = systemConfigDao.findByPropertyKey(key);
    	if (systemConfig==null) {
			return new ArrayList<>();
		}
    	List<T> parseArray = JSON.parseArray(systemConfig.getPropertyValue(), class1);
		return parseArray;
	}
    
    /**
     * 转换成数组
     * @param propertyKey
     * @param class1
     * @return
     */
    public <T> List<T> getArray(Class<T> class1) {
		return getArray(class1.getName(), class1);
	}

    public void save(SystemConfig systemConfig) {
        if(systemConfig.getId()!=null){
        	SystemConfig one = systemConfigDao.findOne(systemConfig.getId());
            try {
                BeanCopy.beanCopy(systemConfig,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            systemConfigDao.save(one);
        }else {
            systemConfigDao.save(systemConfig);
        }
    }

    public void delete(Long id) {
        systemConfigDao.delete(id);
    }
    
    public void deleteBatch(List<Long> id) {
		systemConfigDao.deleteBatch(id);
	}

    public SystemConfig findOne(SystemConfig systemConfig) {
        Specification<SystemConfig> spec = RepositoryUtil.getSpecification(systemConfig);
        SystemConfig findOne = systemConfigDao.findOne(spec);
        return findOne;
    }

    public List<SystemConfig> findAll(List<Long> list) {
        return systemConfigDao.findAll(list);
    }

    public Page<SystemConfig> findAll(SystemConfig systemConfig, Pageable pageable) {
        Specification<SystemConfig> spec = RepositoryUtil.getSpecification(systemConfig);
        Page<SystemConfig> findAll = systemConfigDao.findAll(spec, pageable);
        return findAll;
    }

    public List<SystemConfig> findAll(SystemConfig systemConfig) {
        Specification<SystemConfig> spec = RepositoryUtil.getSpecification(systemConfig);
        return systemConfigDao.findAll(spec);
    }
   
   public  Map<String,String> getlist(){
	   List<SystemConfig> list = systemConfigDao.getlist();
	   Map<String,String> map = new HashMap<String,String>(); 
		map.put("sqm_electric", list.get(0).getPropertyValue());
		map.put("watt_price", list.get(1).getPropertyValue());
		map.put("area_capacity_per", list.get(2).getPropertyValue());
		map.put("country_subsidy", list.get(3).getPropertyValue());
		map.put("country_subsidy_year", list.get(4).getPropertyValue());
		map.put("damping_rate", list.get(5).getPropertyValue());
		map.put("plant_trees_prm", list.get(6).getPropertyValue());
		map.put("CO2_prm", list.get(7).getPropertyValue());
		map.put("SO_prm", list.get(8).getPropertyValue());
       map.put("unlsubsidy", list.get(9).getPropertyValue());
    	
    	return map;
    }
   public  Map<String,String> getnewlist(){
	   List<SystemConfig> list = systemConfigDao.getnewlist();
	   Map<String,String> map = new HashMap<String,String>(); 
		map.put("android_version_code", list.get(0).getPropertyValue());
		map.put("android_version_name", list.get(1).getPropertyValue());
		map.put("android_update_url", list.get(2).getPropertyValue());
		map.put("android_update_info", list.get(3).getPropertyValue());
		
    	
    	return map;
    }
   
   

}

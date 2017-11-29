package com.yn.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import com.yn.dao.SolarPanelDao;
import com.yn.model.News;
import com.yn.model.SolarPanel;
import com.yn.utils.BeanCopy;
import com.yn.utils.DateUtil;
import com.yn.utils.ObjToMap;
import com.yn.utils.RepositoryUtil;

@Service
public class SolarPanelService {
	@Autowired
	SolarPanelDao solarPanelDao;

	public SolarPanel findOne(Long id) {
		return solarPanelDao.findOne(id);
	}
	
	public List<SolarPanel> FindByall(com.yn.model.Page page){
		
		
		return solarPanelDao.FindByall(page);
	}
	
	public	int FindByconut(){
		return solarPanelDao.FindByconut();
	}

	public void save(SolarPanel solarPanel) {
		if (solarPanel.getId() != null) {
			SolarPanel one = solarPanelDao.findOne(solarPanel.getId());
			try {
				BeanCopy.beanCopy(solarPanel, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			solarPanelDao.save(one);
		} else {
			solarPanelDao.save(solarPanel);
		}
	}

	public void delete(Long id) {
		solarPanelDao.delete(id);
	}

	public void deleteBatch(List<Long> id) {
		solarPanelDao.deleteBatch(id);
	}

	public SolarPanel findOne(SolarPanel solarPanel) {
		Specification<SolarPanel> spec = RepositoryUtil.getSpecification(solarPanel);
		SolarPanel findOne = solarPanelDao.findOne(spec);
		return findOne;
	}

	public List<SolarPanel> findAll(List<Long> list) {
		return solarPanelDao.findAll(list);
	}

	public Page<SolarPanel> findAll(SolarPanel solarPanel, Pageable pageable) {
		Specification<SolarPanel> spec = getSpecification(solarPanel);
		Page<SolarPanel> findAll = solarPanelDao.findAll(spec, pageable);
		return findAll;
	}

	public List<SolarPanel> findAll(SolarPanel solarPanel) {
		Specification<SolarPanel> spec = getSpecification(solarPanel);
		return solarPanelDao.findAll(spec);
	}
	
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	    public static Specification<SolarPanel> getSpecification(SolarPanel solarPanel) {
		 solarPanel.setDel(0);
	        Map<String, Object> objectMap = ObjToMap.getObjectMap(solarPanel);
	        return (Root<SolarPanel> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

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
	                        expressions.add(cb.equal(root.get(entry.getKey()), entry.getValue()));
	                    }
	                }
	            }

	            return conjunction;
	        };
	    }
	
	
}

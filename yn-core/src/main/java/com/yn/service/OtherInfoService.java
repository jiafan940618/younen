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

import com.yn.dao.OtherInfoDao;
import com.yn.model.OtherInfo;
import com.yn.utils.BeanCopy;
import com.yn.utils.ObjToMap;
import com.yn.utils.RepositoryUtil;

@Service
public class OtherInfoService {

	@Autowired
	OtherInfoDao otherInfodao;
	
	public OtherInfo findOne(Long id) {
		return otherInfodao.findOne(id);
	}

	public void save(OtherInfo solarPanel) {
		if (solarPanel.getId() != null) {
			OtherInfo one = otherInfodao.findOne(solarPanel.getId());
			try {
				BeanCopy.beanCopy(solarPanel, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			otherInfodao.save(one);
		} else {
			otherInfodao.save(solarPanel);
		}
	}

	public void delete(Long id) {
		otherInfodao.delete(id);
	}

	public void deleteBatch(List<Long> id) {
		otherInfodao.deleteBatch(id);
	}

	public OtherInfo findOne(OtherInfo solarPanel) {
		Specification<OtherInfo> spec = RepositoryUtil.getSpecification(solarPanel);
		OtherInfo findOne = otherInfodao.findOne(spec);
		return findOne;
	}

	public List<OtherInfo> findAll(List<Long> list) {
		return otherInfodao.findAll(list);
	}

	public Page<OtherInfo> findAll(OtherInfo solarPanel, Pageable pageable) {
		Specification<OtherInfo> spec = getSpecification(solarPanel);
		Page<OtherInfo> findAll = otherInfodao.findAll(spec, pageable);
		return findAll;
	}

	public List<OtherInfo> findAll(OtherInfo solarPanel) {
		Specification<OtherInfo> spec = getSpecification(solarPanel);
		return otherInfodao.findAll(spec);
	}
	
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	    public static Specification<OtherInfo> getSpecification(OtherInfo solarPanel) {
		 solarPanel.setDel(0);
	        Map<String, Object> objectMap = ObjToMap.getObjectMap(solarPanel);
	        return (Root<OtherInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

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

package com.yn.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yn.dao.AmmeterDao;
import com.yn.model.Ammeter;
import com.yn.utils.BeanCopy;
import com.yn.utils.DateUtil;
import com.yn.utils.ObjToMap;

@Service
public class AmmeterService {
	@Autowired
	AmmeterDao ammeterDao;

	public Ammeter findOne(Long id) {
		return ammeterDao.findOne(id);
	}

	public void save(Ammeter ammeter) {
		if (ammeter.getId() != null) {
			Ammeter one = ammeterDao.findOne(ammeter.getId());
			try {
				BeanCopy.beanCopy(ammeter, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ammeterDao.save(one);
		} else {
			ammeterDao.save(ammeter);
		}
		System.out.println();
	}

	public void delete(Long id) {
		ammeterDao.delete(id);
	}

	public void deleteBatch(List<Long> id) {
		ammeterDao.deleteBatch(id);
	}

	public Ammeter findOne(Ammeter ammeter) {
		Specification<Ammeter> spec = getSpecification(ammeter);
		Ammeter findOne = ammeterDao.findOne(spec);
		return findOne;
	}

	public List<Ammeter> findAll(List<Long> list) {
		return ammeterDao.findAll(list);
	}

	public Page<Ammeter> findAll(Ammeter ammeter, Pageable pageable) {
		Specification<Ammeter> spec = getSpecification(ammeter);
		Page<Ammeter> findAll = ammeterDao.findAll(spec, pageable);
		return findAll;
	}

	public List<Ammeter> findAll(Ammeter ammeter) {
		Specification<Ammeter> spec = getSpecification(ammeter);
		return ammeterDao.findAll(spec);
	} 
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Specification<Ammeter> getSpecification(Ammeter ammeter) {
		ammeter.setDel(0);
		Map<String, Object> objectMap = ObjToMap.getObjectMap(ammeter);
		return (Root<Ammeter> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

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

			// 根据采集器码，城市，电站码，电站名
			if (!StringUtils.isEmpty(ammeter.getQuery())) {
				Predicate[] predicates = new Predicate[4];
				predicates[0] = cb.like(root.get("cAddr"), "%" + ammeter.getQuery() + "%");
				predicates[1] = cb.like(root.get("cityText"), "%" + ammeter.getQuery() + "%");
				predicates[2] = cb.like(root.join("station", JoinType.LEFT).get("stationCode"), "%" + ammeter.getQuery() + "%");
				predicates[3] = cb.like(root.join("station", JoinType.LEFT).get("stationName"), "%" + ammeter.getQuery() + "%");
				expressions.add(cb.or(predicates));
			}
			
			// 根据日期筛选
			String queryStartDtm = ammeter.getQueryStartDtm();
			String queryEndDtm = ammeter.getQueryEndDtm();
			if (!StringUtils.isEmpty(queryStartDtm)) {
				expressions.add(cb.greaterThanOrEqualTo(root.get("createDtm"), DateUtil.parseString(queryStartDtm, DateUtil.yyyy_MM_dd_HHmmss)));
			}
			if (!StringUtils.isEmpty(queryEndDtm)) {
                expressions.add(cb.lessThan(root.get("createDtm"), DateUtil.parseString(queryEndDtm, DateUtil.yyyy_MM_dd_HHmmss)));
			}

			return conjunction;
		};
	}
	
}

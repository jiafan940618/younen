package com.yn.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import com.yn.dao.RechargeDao;
import com.yn.model.Recharge;
import com.yn.utils.BeanCopy;
import com.yn.utils.DateUtil;
import com.yn.utils.ObjToMap;

@Service
public class FRechargeService {
	
	@Autowired
	RechargeDao rechargeDao;

	public Recharge findOne(Long id) {
		return rechargeDao.findOne(id);
	}

	public void save(Recharge recharge) {
		if (recharge.getId() != null) {
			Recharge one = rechargeDao.findOne(recharge.getId());
			try {
				BeanCopy.beanCopy(recharge, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			rechargeDao.save(one);
		} else {
			rechargeDao.save(recharge);
		}	
	   }

	public void delete(Long id) {
		rechargeDao.delete(id);
	}

	public void deleteBatch(List<Long> id) {
		rechargeDao.deleteBatch(id);
	}

	public Recharge findOne(Recharge recharge) {
		Specification<Recharge> spec = getSpecification(recharge);
		Recharge findOne = rechargeDao.findOne(spec);
		return findOne;
	}

	public List<Recharge> findAll(List<Long> list) {
		return rechargeDao.findAll(list);
	}

	public Page<Recharge> findAll(Recharge billRecharge, Pageable pageable) {
		Specification<Recharge> spec = getSpecification(billRecharge);
		Page<Recharge> findAll = rechargeDao.findAll(spec, pageable);
		return findAll;
	}

	public List<Recharge> findAll(Recharge billRecharge) {
		Specification<Recharge> spec = getSpecification(billRecharge);
		return rechargeDao.findAll(spec);
	} 
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Specification<Recharge> getSpecification(Recharge billRecharge) {
		billRecharge.setDel(0);
		Map<String, Object> objectMap = ObjToMap.getObjectMap(billRecharge);
		return (Root<Recharge> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

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

			// 根据交易单号，用户
			String queryStr = billRecharge.getQuery();
			if (!StringUtils.isEmpty(queryStr)) {
				Predicate[] predicates = new Predicate[2];
				predicates[0] = cb.like(root.get("tradeNo"), "%" + queryStr + "%");
				predicates[1] = cb.like(root.get("user").get("userName"), "%" + queryStr + "%");
				expressions.add(cb.or(predicates));
			}
			
			// 根据日期筛选
			String queryStartDtm = billRecharge.getQueryStartDtm();
			String queryEndDtm = billRecharge.getQueryEndDtm();
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

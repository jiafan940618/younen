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

import com.yn.dao.BillRechargeDao;
import com.yn.model.BillRecharge;
import com.yn.utils.BeanCopy;
import com.yn.utils.DateUtil;
import com.yn.utils.ObjToMap;

@Service
public class BillRechargeService {
	@Autowired
	BillRechargeDao billRechargeDao;

	public BillRecharge findOne(Long id) {
		return billRechargeDao.findOne(id);
	}

	public void save(BillRecharge billRecharge) {
		if (billRecharge.getId() != null) {
			BillRecharge one = billRechargeDao.findOne(billRecharge.getId());
			try {
				BeanCopy.beanCopy(billRecharge, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			billRechargeDao.save(one);
		} else {
			billRechargeDao.save(billRecharge);
		}
		System.out.println();
	}

	public void delete(Long id) {
		billRechargeDao.delete(id);
	}

	public void deleteBatch(List<Long> id) {
		billRechargeDao.deleteBatch(id);
	}

	public BillRecharge findOne(BillRecharge billRecharge) {
		Specification<BillRecharge> spec = getSpecification(billRecharge);
		BillRecharge findOne = billRechargeDao.findOne(spec);
		return findOne;
	}

	public List<BillRecharge> findAll(List<Long> list) {
		return billRechargeDao.findAll(list);
	}

	public Page<BillRecharge> findAll(BillRecharge billRecharge, Pageable pageable) {
		Specification<BillRecharge> spec = getSpecification(billRecharge);
		Page<BillRecharge> findAll = billRechargeDao.findAll(spec, pageable);
		return findAll;
	}

	public List<BillRecharge> findAll(BillRecharge billRecharge) {
		Specification<BillRecharge> spec = getSpecification(billRecharge);
		return billRechargeDao.findAll(spec);
	} 
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Specification<BillRecharge> getSpecification(BillRecharge billRecharge) {
		billRecharge.setDel(0);
		Map<String, Object> objectMap = ObjToMap.getObjectMap(billRecharge);
		return (Root<BillRecharge> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

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

			// 根据交易单号，用户名
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

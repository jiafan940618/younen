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

import com.yn.dao.BillRefundDao;
import com.yn.model.BillRefund;
import com.yn.utils.BeanCopy;
import com.yn.utils.DateUtil;
import com.yn.utils.ObjToMap;

@Service
public class BillRefundService {
	@Autowired
	BillRefundDao billRefundDao;

	public BillRefund findOne(Long id) {
		return billRefundDao.findOne(id);
	}

	public void save(BillRefund billRefund) {
		if (billRefund.getId() != null) {
			BillRefund one = billRefundDao.findOne(billRefund.getId());
			try {
				BeanCopy.beanCopy(billRefund, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			billRefundDao.save(one);
		} else {
			billRefundDao.save(billRefund);
		}
		System.out.println();
	}

	public void delete(Long id) {
		billRefundDao.delete(id);
	}

	public void deleteBatch(List<Long> id) {
		billRefundDao.deleteBatch(id);
	}

	public BillRefund findOne(BillRefund billRefund) {
		Specification<BillRefund> spec = getSpecification(billRefund);
		BillRefund findOne = billRefundDao.findOne(spec);
		return findOne;
	}

	public List<BillRefund> findAll(List<Long> list) {
		return billRefundDao.findAll(list);
	}

	public Page<BillRefund> findAll(BillRefund billRefund, Pageable pageable) {
		Specification<BillRefund> spec = getSpecification(billRefund);
		Page<BillRefund> findAll = billRefundDao.findAll(spec, pageable);
		return findAll;
	}

	public List<BillRefund> findAll(BillRefund billRefund) {
		Specification<BillRefund> spec = getSpecification(billRefund);
		return billRefundDao.findAll(spec);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Specification<BillRefund> getSpecification(BillRefund billRefund) {
		billRefund.setDel(0);
		Map<String, Object> objectMap = ObjToMap.getObjectMap(billRefund);
		return (Root<BillRefund> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

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
			String queryStr = billRefund.getQuery();
			if (!StringUtils.isEmpty(queryStr)) {
				Predicate[] predicates = new Predicate[2];
				predicates[0] = cb.like(root.get("tradeNo"), "%" + queryStr + "%");
				predicates[1] = cb.like(root.get("user").get("userName"), "%" + queryStr + "%");
				expressions.add(cb.or(predicates));
			}
			
			// 根据日期筛选
			String queryStartDtm = billRefund.getQueryStartDtm();
			String queryEndDtm = billRefund.getQueryEndDtm();
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

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
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yn.dao.BillOrderDao;
import com.yn.model.BillOrder;
import com.yn.utils.BeanCopy;
import com.yn.utils.DateUtil;
import com.yn.utils.ObjToMap;
import com.yn.utils.RepositoryUtil;
import com.yn.utils.StringUtil;

@Service
public class BillOrderService {
	@Autowired
	BillOrderDao billOrderDao;

	public BillOrder findOne(Long id) {
		return billOrderDao.findOne(id);
	}

	public void save(BillOrder billOrder) {
		if (billOrder.getId() != null) {
			BillOrder one = billOrderDao.findOne(billOrder.getId());
			try {
				BeanCopy.beanCopy(billOrder, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			billOrderDao.save(one);
		} else {
			billOrderDao.save(billOrder);
		}
		System.out.println();
	}

	public void delete(Long id) {
		billOrderDao.delete(id);
	}
	
	 public	BillOrder findByTradeNo(String tradeNo){
		 
		return billOrderDao.findByTradeNo(tradeNo); 
	 }
		
	
	public void deleteBatch(List<Long> id) {
		billOrderDao.deleteBatch(id);
	}

	public BillOrder findOne(BillOrder billOrder) {
		Specification<BillOrder> spec = RepositoryUtil.getSpecification(billOrder);
		BillOrder findOne = billOrderDao.findOne(spec);
		return findOne;
	}

	public List<BillOrder> findAll(List<Long> list) {
		return billOrderDao.findAll(list);
	}

	public Page<BillOrder> findAll(BillOrder billOrder, String orderStatus, Pageable pageable) {
		Specification<BillOrder> spec = getSpecification(billOrder, orderStatus);
		Page<BillOrder> findAll = billOrderDao.findAll(spec, pageable);
		return findAll;
	}

	public List<BillOrder> findAll(BillOrder billOrder) {
		Specification<BillOrder> spec = RepositoryUtil.getSpecification(billOrder);
		return billOrderDao.findAll(spec);
	} 
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Specification<BillOrder> getSpecification(BillOrder billOrder, String orderStatus) {
		billOrder.setDel(0);
		Map<String, Object> objectMap = ObjToMap.getObjectMap(billOrder);
		return (Root<BillOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

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
			
			// 根据订单的状态
			List<Integer> parse2IntList = StringUtil.parse2IntList(orderStatus);
			if (!StringUtil.isEmpty(parse2IntList)) {
				int orderStatusSize = parse2IntList.size();
				Predicate[] predicates = new Predicate[orderStatusSize];
				for (int i = 0; i < orderStatusSize; i++) {
					predicates[i] = cb.equal(root.get("order").get("status"), parse2IntList.get(i));
				}
				expressions.add(cb.or(predicates));
			}

			// 根据订单号，用户名
			String queryStr = billOrder.getQuery();
			if (!StringUtils.isEmpty(queryStr)) {
				Predicate[] predicates = new Predicate[2];
				predicates[0] = cb.like(root.get("tradeNo"), "%" + queryStr + "%");
				predicates[1] = cb.like(root.get("user").get("userName"), "%" + queryStr + "%");
				expressions.add(cb.or(predicates));
			}
			
			// 根据日期筛选
			String queryStartDtm = billOrder.getQueryStartDtm();
			String queryEndDtm = billOrder.getQueryEndDtm();
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

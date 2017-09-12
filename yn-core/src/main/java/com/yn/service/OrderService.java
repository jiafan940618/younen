package com.yn.service;

import com.yn.dao.OrderDao;
import com.yn.dao.mapper.OrderMapper;
import com.yn.domain.OrderDetailAccounts;
import com.yn.enums.NoticeEnum;
import com.yn.enums.OrderEnum;
import com.yn.model.Order;
import com.yn.utils.BeanCopy;
import com.yn.utils.DateUtil;
import com.yn.utils.ObjToMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Service
public class OrderService {

	@Autowired
	protected OrderDao orderDao;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private OrderMapper mapper;

	public Order findOne(Long id) {
		return orderDao.findOne(id);
	}

	/**
	 * 验证订单的状态是否更新成功！
	 * 
	 * @param order
	 * @return
	 */
	//@Transactional(propagation=Propagation.REQUIRED)
	public boolean checkUpdateOrderStatus(Order order){
		return mapper.updateByCondition(order)>0?true:false;
	}

	/*
	 * public Order findOrder(Order order) { return orderDao.findOrder(order); }
	 */

	public void save(Order order) {
		if (order.getId() != null) {
			Order one = orderDao.findOne(order.getId());
			try {
				BeanCopy.beanCopy(order, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			orderDao.save(one);
		} else {
			orderDao.save(order);
		}
	}

	public void delete(Long id) {
		orderDao.delete(id);

		// 删除未读信息
		noticeService.delete(NoticeEnum.NEW_ORDER.getCode(), id);
	}

	public void deleteBatch(List<Long> id) {
		orderDao.deleteBatch(id);
	}

	public Order findOne(Order order) {
		Specification<Order> spec = getSpecification(order);
		Order findOne = orderDao.findOne(spec);
		return findOne;
	}

	public List<Order> findAll(List<Long> list) {
		return orderDao.findAll(list);
	}

	public Page<Order> findAll(Order order, Pageable pageable) {
		Specification<Order> spec = getSpecification(order);
		Page<Order> findAll = orderDao.findAll(spec, pageable);
		return findAll;
	}

	public List<Order> findAll(Order order) {
		Specification<Order> spec = getSpecification(order);
		return orderDao.findAll(spec);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Specification<Order> getSpecification(Order order) {
		order.setDel(0);
		Map<String, Object> objectMap = ObjToMap.getObjectMap(order);
		return (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

			Predicate conjunction = cb.conjunction();
			List<Expression<Boolean>> expressions = conjunction.getExpressions();
			Iterator<Entry<String, Object>> iterator = objectMap.entrySet().iterator();

			while (iterator.hasNext()) {
				Entry<String, Object> entry = iterator.next();
				if (!entry.getKey().equals("query") && !entry.getKey().equals("queryStartDtm")
						&& !entry.getKey().equals("queryEndDtm")) {
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

			// 根据订单号，联系人，联系人手机号，服务商名称
			if (!StringUtils.isEmpty(order.getQuery())) {
				Predicate[] predicates = new Predicate[5];
				predicates[0] = cb.like(root.get("orderCode"), "%" + order.getQuery() + "%");
				predicates[1] = cb.like(root.get("linkMan"), "%" + order.getQuery() + "%");
				predicates[2] = cb.like(root.get("linkPhone"), "%" + order.getQuery() + "%");
				predicates[3] = cb.like(root.get("addressText"), "%" + order.getQuery() + "%");
				predicates[4] = cb.like(root.get("server").get("companyName"), "%" + order.getQuery() + "%");
				expressions.add(cb.or(predicates));
			}

			// 根据日期筛选
			String queryStartDtm = order.getQueryStartDtm();
			String queryEndDtm = order.getQueryEndDtm();
			if (!StringUtils.isEmpty(queryStartDtm)) {
				expressions.add(cb.greaterThanOrEqualTo(root.get("createDtm"),
						DateUtil.parseString(queryStartDtm, DateUtil.yyyy_MM_dd_HHmmss)));
			}
			if (!StringUtils.isEmpty(queryEndDtm)) {
				expressions.add(cb.lessThan(root.get("createDtm"),
						DateUtil.parseString(queryEndDtm, DateUtil.yyyy_MM_dd_HHmmss)));
			}

			return conjunction;
		};
	}

	/**
	 * 账目明细
	 *
	 * @param serverId
	 * @return
	 */
	public OrderDetailAccounts detailAccounts(Long serverId) {
		Order orderR = new Order();
		orderR.setServerId(serverId);
		List<Order> findAll = findAll(orderR);

		OrderDetailAccounts oda = new OrderDetailAccounts();
		oda.setOrderNum(findAll.size());

		for (Order order : findAll) {
			Double totalPrice = order.getTotalPrice();
			oda.setPriceTol(oda.getPriceTol() + totalPrice);
			if (order.getStatus().equals(OrderEnum.STATUS_APPLY.getCode())) {
				// 申请中项目
				oda.setApplyingPriceTol(oda.getApplyingPriceTol() + totalPrice);
			} else if (order.getStatus().equals(OrderEnum.STATUS_BUILD.getCode())) {
				// 建设中项目
				oda.setBuildingPriceTol(oda.getBuildingPriceTol() + totalPrice);
			} else if (order.getStatus().equals(OrderEnum.STATUS_GRIDCONNECTED_APPLY.getCode())) {
				// 申请并网发电项目
				oda.setGridConnectedingPriceTol(oda.getGridConnectedingPriceTol() + totalPrice);
			} else if (order.getStatus().equals(OrderEnum.STATUS_GRIDCONNECTED.getCode())) {
				// 并网发电中项目
				oda.setGridConnectedPriceTol(oda.getGridConnectedPriceTol() + totalPrice);
			}
			// 优能服务费
			oda.setFactoragePriceTol(oda.getFactoragePriceTol() + order.getFactoragePrice());
			// 优能选配项目
			oda.setYnApolegamyPriceTol(oda.getYnApolegamyPriceTol() + order.getYnApolegamyPrice());
			// 服务商选配项目
			oda.setServerApolegamyPriceTol(oda.getServerApolegamyPriceTol() + order.getServerApolegamyPrice());
			// 已支付金额
			oda.setHadPayPriceTol(oda.getHadPayPriceTol() + order.getHadPayPrice());
			// 未支付金额
			oda.setNotPayPriceTol(oda.getNotPayPriceTol() + (order.getTotalPrice() - order.getHadPayPrice()));
		}

		// 营业利润 = 优能服务费 + 优能选配项目金额
		oda.setProfitTol(oda.getFactoragePriceTol() + oda.getYnApolegamyPriceTol());

		return oda;
	}

}

package com.yn.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yn.dao.BankCardDao;
import com.yn.dao.BillOrderDao;
import com.yn.dao.OrderDao;
import com.yn.dao.mapper.BillOrderMapper;
import com.yn.dao.mapper.OrderMapper;
import com.yn.model.BankCard;
import com.yn.model.BillOrder;
import com.yn.model.Order;
import com.yn.utils.BeanCopy;
import com.yn.utils.DateUtil;
import com.yn.utils.ObjToMap;
import com.yn.utils.RepositoryUtil;
import com.yn.utils.StringUtil;
import com.yn.vo.BillOrderVo;

@Service
public class BillOrderService {
	
	private static final Logger logger = LoggerFactory.getLogger(BillOrderService.class);
	
	@Autowired
	BillOrderDao billOrderDao;
	@Autowired 
	ServerPlanService serverPlanService;
	@Autowired 
	BillOrderMapper mapper;
	@Autowired
	BankCardDao bankCardDao ;
	@Autowired 
	OrderMapper ordermapper;

	private static DecimalFormat df = new DecimalFormat("0.00");
	private static DecimalFormat df1 = new DecimalFormat("0000");
	private static Random rd = new Random();
    private  static	SimpleDateFormat format = new SimpleDateFormat("yyMMddHH");
	/** 自定义进制(0,1没有加入,容易与o,l混淆) */
	private static final char[] r = new char[] { 'q', 'w', 'e', '8', 'a', 's', '2', 'd', 'z', 'x', '9', 'c', '7', 'p',
			'5', 'i', 'k', '3', 'm', 'j', 'u', 'f', 'r', '4', 'v', 'y', 'l', 't', 'n', '6', 'b', 'g', 'h' };
	/** (不能与自定义进制有重复) */
	private static final char b = 'o';
	/** 进制长度 */
	private static final int binLen = r.length;
	
	public BillOrder findOne(Long id) {
		return billOrderDao.findOne(id);
	}
	
	public void newsave(BillOrder billOrder) {
		billOrderDao.save(billOrder);
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
	}
	
	public void UpdateBillorder(BillOrderVo billorderVo){
		logger.info("---- ----- ------ ---- 开始修改订单记录   00001");
		
		mapper.UpdateBillorder(billorderVo);
	}
	
	public BillOrder findByTradeNoandstatus(String tradeNo ){
		
		return billOrderDao.findByTradeNoandstatus(tradeNo);	
	}
	
	public void updateOrder(String tradeNo){

		billOrderDao.updateOrder(tradeNo);
	}
	

	public void delete(Long id) {
		billOrderDao.delete(id);
	}
	
	 public	BillOrder findByTradeNo(String tradeNo){
		 
		return billOrderDao.findByTradeNo(tradeNo); 
	 }
		
	 public	List<BillOrder> findByOrderId(Long OrderId){
		 
			return billOrderDao.findByOrderId(OrderId); 
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
	
	 /** 根据订单号,拿到用户id找到银行卡*/
	
	public void getbankCard01(String tradeNo){
		
		BillOrder billOrder =	billOrderDao.findByTradeNo(tradeNo);
		
		BankCard bankCard = new BankCard();
		//BankCard bankCard =bankCardDao.selectBank(billOrder.getUserId());
		
		//bankCard.getBankNum();
	
	}
	
	/** 传一个对象 BillOrder 保存到交易记录表中*/
	
	public void saveBillOrder(BillOrder billOrder){
		
		String orderCode = serverPlanService.toSerialCode(billOrder.getUserId(), 4) + format.format(System.currentTimeMillis()) + df1.format(rd.nextInt(9999));
		
		billOrder.setTradeNo(orderCode);
		 /** 第一次进来交易为失败*/
		billOrder.setStatus(1);
		
		save(billOrder);
	}
	
	public List<String> getSay(List<BillOrder> billOrder){
		List<String> list = new LinkedList<String>();
		
		String billsay ="";
		
		if(null == billOrder || billOrder.size() == 0){
			billsay = "无";
			list.add(billsay);
		}else{
			int i =1;
			
			for (BillOrder billOrder2 : billOrder) {
				billsay = billOrder2.getCreateDtm()+"    "+"第"+i+"次支付"+billOrder2.getMoney()+"元";
				
				list.add(billsay);
				
				i++;
			}
			
		}
		return list;
	}
	
	/** 根据支付的金额改变订单的状态 status*/
	
	public void ChangeBillSta(Order order){
		
		Double speed = order.getHadPayPrice()/ order.getTotalPrice();
		/*DecimalFormat decimalFormat = new DecimalFormat("0.0000");
		speed = Double.parseDouble(decimalFormat.format(speed)) * 100;*/
		Integer status = 0;
		if(speed > 0 && speed < 0.3){
			status = 0;
		}else if(speed > 0.3 && speed < 0.6 || speed == 0.3){	
			status = 1;
		}else if(speed > 0.6 && speed < 1 || speed == 0.6){
			status = 2;
		}else if(speed == 1){
			status = 3;
		}
		
		order.setStatus(status);

		ordermapper.UpdateOrderStatus(order);
	}
	
	/** 实现添加支付记录 表*/
	public Integer changeChannel(String channel){
		Integer payWay =0;
		if(channel.equals("wxPubQR")){
			payWay =2;
			
		}else if(channel.equals("alipayQR")){
			payWay =3;
		}
		return payWay;
	
	}
	
	
	
	
}

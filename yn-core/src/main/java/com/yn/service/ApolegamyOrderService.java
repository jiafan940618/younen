package com.yn.service;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.yn.dao.ApolegamyOrderDao;
import com.yn.model.ApolegamyOrder;
import com.yn.model.Order;
import com.yn.model.OrderPlan;
import com.yn.model.Station;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class ApolegamyOrderService {
	
	private static final Logger logger = LoggerFactory.getLogger(ApolegamyOrderService.class);
	
	@Autowired
	ApolegamyOrderDao apolegamyOrderdao;

	public ApolegamyOrder findOne(Long id) {

		return apolegamyOrderdao.findOne(id);
	}
	
	public void delete(Long id){
		
		apolegamyOrderdao.delete(id);
	 }
	
	
	
	public ApolegamyOrder findOne(ApolegamyOrder apo) {
		 Specification<ApolegamyOrder> spec = RepositoryUtil.getSpecification(apo);
		 ApolegamyOrder findOne = apolegamyOrderdao.findOne(spec);
		return findOne;	
	}
	

	public void save(ApolegamyOrder apolegamyOrder) {
		if (apolegamyOrder.getId() != null) {
			logger.info("进入另一个方法！---- ---- ----- --- ");
			ApolegamyOrder one = apolegamyOrderdao.findOne(apolegamyOrder.getId());
			try {
				BeanCopy.beanCopy(apolegamyOrder, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			apolegamyOrderdao.save(one);
		} else {
			
			logger.info("添加数据！--- ---- --- ---- ----");
			
			apolegamyOrderdao.save(apolegamyOrder);
		}   
	}  

	public void getapole(Order order, List<Long> list) {

		ApolegamyOrder apolegamyOrder = new ApolegamyOrder();
		String ids = "";
		if (list.size() == 0) {
			ids = "0";
		}else{
			for (Long long1 : list) {
				ids += long1.toString() + ",";
			}
		}
		logger.info("拿到的项目串为：---- ---- ----- ---- ---"+ids);
		logger.info("拿到的钱为：---- ---- ---- ---- ------ --"+order.getYnApolegamyPrice());
		apolegamyOrder.setApoIds(ids);
		apolegamyOrder.setOrderId(order.getId());
		apolegamyOrder.setPrice(order.getYnApolegamyPrice());
		apolegamyOrder.setType(0);
		
		save(apolegamyOrder);
	}

	public List<Long> Transformation(String ids) {

		String[] apoId = ids.split(",");

		List<Long> list = new LinkedList<Long>();

		for (int i = 0; i < apoId.length; i++) {
			list.add(Long.valueOf(apoId[i]));
		}

		return list;
	}

}
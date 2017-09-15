<<<<<<< HEAD
package com.yn.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yn.dao.ApolegamyOrderDao;
import com.yn.model.ApolegamyOrder;
import com.yn.model.Order;
import com.yn.utils.BeanCopy;

@Service
public class ApolegamyOrderService {

	@Autowired
	ApolegamyOrderDao apolegamyOrderdao;

	public ApolegamyOrder findOne(Long id) {

		return apolegamyOrderdao.findOne(id);
	}

	public void save(ApolegamyOrder apolegamyOrder) {
		if (apolegamyOrder.getId() != null) {
			ApolegamyOrder one = apolegamyOrderdao.findOne(apolegamyOrder.getId());
			try {
				BeanCopy.beanCopy(apolegamyOrder, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			apolegamyOrderdao.save(one);
		} else {
			apolegamyOrderdao.save(apolegamyOrder);
		}   
	}  

	public void getapole(Order order, List<Long> list) {

		ApolegamyOrder apolegamyOrder = new ApolegamyOrder();
		String ids = "";
		if (list.size() == 0) {
			ids = "0";
		}

		for (Long long1 : list) {
			ids += long1.toString() + ",";
		}
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
=======
package com.yn.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yn.dao.ApolegamyOrderDao;
import com.yn.model.ApolegamyOrder;
import com.yn.model.ApolegamyServer;
import com.yn.model.Order;
import com.yn.utils.BeanCopy;

@Service
public class ApolegamyOrderService {
	
	@Autowired
	 ApolegamyOrderDao apolegamyOrderdao;
	
	 public ApolegamyOrder findOne(Long id) {
		 
	        return apolegamyOrderdao.findOne(id);
	    }

	    public void save(ApolegamyOrder apolegamyOrder) {
	        if(apolegamyOrder.getId()!=null){
	        	ApolegamyOrder one = apolegamyOrderdao.findOne(apolegamyOrder.getId());
	            try {
	                BeanCopy.beanCopy(apolegamyOrder,one);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            apolegamyOrderdao.save(one);
	        }else {
	        	apolegamyOrderdao.save(apolegamyOrder);
	        }
	    }
	
	public void getapole(Order order,List<Long> list){
		
		ApolegamyOrder apolegamyOrder = new ApolegamyOrder();
		String ids ="";
		if(list.size()==0){
			ids ="0";
		}
		
		for (Long long1 : list) {
			 ids +=long1.toString()+",";
		}
		apolegamyOrder.setApoIds(ids);
		apolegamyOrder.setOrderId(order.getId());
		apolegamyOrder.setPrice(order.getYnApolegamyPrice());
		apolegamyOrder.setType(0);
		
		save(apolegamyOrder);
	}
	
	
	public List<Long> Transformation(String ids){
		
		String[] apoId = ids.split(",");
		
		List<Long> list = new LinkedList<Long>();
		
		 for (int i = 0; i < apoId.length; i++) {
			list.add(Long.valueOf(apoId[i]));
		}
		
		
		return list;
	}

}
>>>>>>> 22e57ef2fced0f82f71dbff66c49bfe22126fad1

package com.yn.service;

import com.yn.dao.OrderPlanDao;
import com.yn.model.OrderPlan;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderPlanService {
    @Autowired
    OrderPlanDao orderPlanDao;

    public OrderPlan findOne(Long id) {
        return orderPlanDao.findOne(id);
    }

    public void save(OrderPlan orderPlan) {
        if(orderPlan.getId()!=null){
        	OrderPlan one = orderPlanDao.findOne(orderPlan.getId());
            try {
                BeanCopy.beanCopy(orderPlan,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            orderPlanDao.save(one);
        }else {
            orderPlanDao.save(orderPlan);
        }
    }

    public void delete(Long id) {
        orderPlanDao.delete(id);
    }
    
    public void deleteBatch(List<Long> id) {
		orderPlanDao.deleteBatch(id);
	}

    public OrderPlan findOne(OrderPlan orderPlan) {
        Specification<OrderPlan> spec = RepositoryUtil.getSpecification(orderPlan);
        OrderPlan findOne = orderPlanDao.findOne(spec);
        return findOne;
    }

    public List<OrderPlan> findAll(List<Long> list) {
        return orderPlanDao.findAll(list);
    }

    public Page<OrderPlan> findAll(OrderPlan orderPlan, Pageable pageable) {
        Specification<OrderPlan> spec = RepositoryUtil.getSpecification(orderPlan);
        Page<OrderPlan> findAll = orderPlanDao.findAll(spec, pageable);
        return findAll;
    }

    public List<OrderPlan> findAll(OrderPlan orderPlan) {
        Specification<OrderPlan> spec = RepositoryUtil.getSpecification(orderPlan);
        return orderPlanDao.findAll(spec);
    }
}

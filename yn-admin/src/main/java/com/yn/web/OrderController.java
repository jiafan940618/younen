package com.yn.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.dao.OrderDao;
import com.yn.dao.OrderPlanDao;
import com.yn.domain.OrderDetailAccounts;
import com.yn.model.Order;
import com.yn.service.OrderService;
import com.yn.service.ServerPlanService;
import com.yn.utils.BeanCopy;
import com.yn.vo.OrderVo;
import com.yn.vo.re.ResultDataVoUtil;

@RestController
@RequestMapping("/server/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    ServerPlanService serverPlanService;
    @Autowired
    OrderDao orderDao;
    @Autowired
    OrderPlanDao orderPlanDao;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        Order findOne = orderService.findOne(id);
        return ResultDataVoUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody OrderVo orderVo) {
        Order order = new Order();
        BeanCopy.copyProperties(orderVo, order);
        orderService.save(order);
        return ResultDataVoUtil.success(order);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        orderService.delete(id);
        return ResultDataVoUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(OrderVo orderVo) {
        Order order = new Order();
        BeanCopy.copyProperties(orderVo, order);
        Order findOne = orderService.findOne(order);
        return ResultDataVoUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(OrderVo orderVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Order order = new Order();
        BeanCopy.copyProperties(orderVo, order);
        Page<Order> findAll = orderService.findAll(order, pageable);
        return ResultDataVoUtil.success(findAll);
    }
    
    /**
     * 账目明细
     * @return
     */
    @RequestMapping(value = "/detailAccounts", method = {RequestMethod.POST})
    @ResponseBody
    public Object detailAccounts(Long serverId) {
        Order orderR = new Order();
        orderR.setServerId(serverId);
        List<Order> findAll = orderService.findAll(orderR);
        
        OrderDetailAccounts oda = new OrderDetailAccounts();
        oda.setOrderNum(findAll.size());
        
        for (Order order : findAll) {
        	Double totalPrice = order.getTotalPrice();
        	oda.setPriceTol(oda.getPriceTol() + totalPrice);
        	if (order.getStatus() == 0) {
        		oda.setApplyingPriceTol(oda.getApplyingPriceTol() + totalPrice);
			} else if (order.getStatus() == 1) {
				oda.setBuildingPriceTol(oda.getBuildingPriceTol() + totalPrice);
			} else if (order.getStatus() == 2) {
				oda.setGridConnectedingPriceTol(oda.getGridConnectedingPriceTol() + totalPrice);
			} else if (order.getStatus() == 3) {
				oda.setGridConnectedPriceTol(oda.getGridConnectedPriceTol() + totalPrice);
			}
        	oda.setFactoragePriceTol(oda.getFactoragePriceTol() + order.getFactoragePrice());
        	oda.setApolegamyPriceTol(oda.getApolegamyPriceTol() + order.getApolegamyPrice());
        	oda.setHadPayPriceTol(oda.getHadPayPriceTol() + order.getHadPayPrice());
		}
        
        return ResultDataVoUtil.success(oda);
    }
    
}

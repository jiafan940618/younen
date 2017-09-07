package com.yn.web;

import javax.servlet.http.HttpSession;

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
import com.yn.model.Order;
import com.yn.model.Wallet;
import com.yn.service.OrderService;
import com.yn.service.ServerPlanService;
import com.yn.service.WalletService;
import com.yn.utils.BeanCopy;
import com.yn.utils.ResultData;
import com.yn.vo.OrderVo;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/client/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    ServerPlanService serverPlanService;
    @Autowired
    OrderDao orderDao;
    @Autowired
    OrderPlanDao orderPlanDao;
    @Autowired
    WalletService walletService;
    
    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        Order findOne = orderService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody OrderVo orderVo) {
        Order order = new Order();
        BeanCopy.copyProperties(orderVo, order);
        orderService.save(order);
        return ResultVOUtil.success(order);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        orderService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(OrderVo orderVo) {
        Order order = new Order();
        BeanCopy.copyProperties(orderVo, order);
        Order findOne = orderService.findOne(order);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(OrderVo orderVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Order order = new Order();
        BeanCopy.copyProperties(orderVo, order);
        Page<Order> findAll = orderService.findAll(order, pageable);
        return ResultVOUtil.success(findAll);
    }
    
     /** 订单详情*/
    @ResponseBody
    @RequestMapping(value = "/seeOrder")
    public Object LookOrder(HttpSession session) {
    	
    	Order order =(Order)session.getAttribute("order");
    	
        return ResultVOUtil.success(order);
    }
    
     /** 线上支付,第一步*/
    @ResponseBody
    @RequestMapping(value = "/orderPrice")
    public ResultData<Object> findOrderprice(OrderVo orderVo) {
    	
    	
    Order order = orderService.findOne(orderVo.getId());
    
    Wallet wallet = walletService.findOne(orderVo.getUserId());
    	
    	
        return ResultVOUtil.success(null);
    }
  
    
    
    
}

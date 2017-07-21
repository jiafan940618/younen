package com.yn.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.dao.BillOrderDao;
import com.yn.dao.OrderDao;
import com.yn.model.BillOrder;
import com.yn.model.Order;
import com.yn.model.User;
import com.yn.service.BillOrderService;
import com.yn.service.OrderService;
import com.yn.session.SessionCache;
import com.yn.utils.BeanCopy;
import com.yn.utils.StringUtil;
import com.yn.vo.BillOrderVo;
import com.yn.vo.re.ResultDataVoUtil;

@RestController
@RequestMapping("/client/billOrder")
public class BillOrderController {
    @Autowired
    BillOrderService billOrderService;
    @Autowired
    BillOrderDao billOrderDao;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDao orderDao;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        BillOrder findOne = billOrderService.findOne(id);
        return ResultDataVoUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody BillOrderVo billOrderVo) {
        BillOrder billOrder = new BillOrder();
        BeanCopy.copyProperties(billOrderVo, billOrder);
        billOrderService.save(billOrder);
        return ResultDataVoUtil.success(billOrder);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        billOrderService.delete(id);
        return ResultDataVoUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(BillOrderVo billOrderVo) {
        BillOrder billOrder = new BillOrder();
        BeanCopy.copyProperties(billOrderVo, billOrder);
        BillOrder findOne = billOrderService.findOne(billOrder);
        return ResultDataVoUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(BillOrderVo billOrderVo, String orderStatus, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        BillOrder billOrder = new BillOrder();
        BeanCopy.copyProperties(billOrderVo, billOrder);
        Page<BillOrder> findAll = billOrderService.findAll(billOrder, orderStatus, pageable);
        return ResultDataVoUtil.success(findAll);
    }
}

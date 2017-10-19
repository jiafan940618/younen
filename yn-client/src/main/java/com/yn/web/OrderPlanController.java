package com.yn.web;

import com.yn.vo.re.ResultVOUtil;
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

import com.yn.model.OrderPlan;
import com.yn.service.OrderPlanService;
import com.yn.utils.BeanCopy;
import com.yn.vo.OrderPlanVo;

@RestController
@RequestMapping("/client/orderPlan")
public class OrderPlanController {
    @Autowired
    OrderPlanService orderPlanService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        OrderPlan findOne = orderPlanService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody OrderPlanVo orderPlanVo) {
        OrderPlan orderPlan = new OrderPlan();
        BeanCopy.copyProperties(orderPlanVo, orderPlan);
        orderPlanService.save(orderPlan);
        return ResultVOUtil.success(orderPlan);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        orderPlanService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(OrderPlanVo orderPlanVo) {
        OrderPlan orderPlan = new OrderPlan();
        BeanCopy.copyProperties(orderPlanVo, orderPlan);
        OrderPlan findOne = orderPlanService.findOne(orderPlan);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(OrderPlanVo orderPlanVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        OrderPlan orderPlan = new OrderPlan();
        BeanCopy.copyProperties(orderPlanVo, orderPlan);
        Page<OrderPlan> findAll = orderPlanService.findAll(orderPlan, pageable);
        return ResultVOUtil.success(findAll);
    }
}

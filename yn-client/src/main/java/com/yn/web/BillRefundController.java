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

import com.yn.model.BillRefund;
import com.yn.service.BillRefundService;
import com.yn.utils.BeanCopy;
import com.yn.vo.BillRefundVo;

@RestController
@RequestMapping("/client/billRefund")
public class BillRefundController {
    @Autowired
    BillRefundService billRefundService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        BillRefund findOne = billRefundService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody BillRefundVo billRefundVo) {
        BillRefund billRefund = new BillRefund();
        BeanCopy.copyProperties(billRefundVo, billRefund);
        billRefundService.save(billRefund);
        return ResultVOUtil.success(billRefund);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        billRefundService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(BillRefundVo billRefundVo) {
        BillRefund billRefund = new BillRefund();
        BeanCopy.copyProperties(billRefundVo, billRefund);
        BillRefund findOne = billRefundService.findOne(billRefund);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(BillRefundVo billRefundVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        BillRefund billRefund = new BillRefund();
        BeanCopy.copyProperties(billRefundVo, billRefund);
        Page<BillRefund> findAll = billRefundService.findAll(billRefund, pageable);
        return ResultVOUtil.success(findAll);
    }
}

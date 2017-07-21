package com.yn.web;

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

import com.yn.model.BillWithdrawals;
import com.yn.service.BillWithdrawalsService;
import com.yn.utils.BeanCopy;
import com.yn.vo.BillWithdrawalsVo;
import com.yn.vo.re.ResultDataVoUtil;

@RestController
@RequestMapping("/client/billWithdrawals")
public class BillWithdrawalsController {
    @Autowired
    BillWithdrawalsService billWithdrawalsService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        BillWithdrawals findOne = billWithdrawalsService.findOne(id);
        return ResultDataVoUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody BillWithdrawalsVo billWithdrawalsVo) {
        BillWithdrawals billWithdrawals = new BillWithdrawals();
        BeanCopy.copyProperties(billWithdrawalsVo, billWithdrawals);
        billWithdrawalsService.save(billWithdrawals);
        return ResultDataVoUtil.success(billWithdrawals);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        billWithdrawalsService.delete(id);
        return ResultDataVoUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(BillWithdrawalsVo billWithdrawalsVo) {
        BillWithdrawals billWithdrawals = new BillWithdrawals();
        BeanCopy.copyProperties(billWithdrawalsVo, billWithdrawals);
        BillWithdrawals findOne = billWithdrawalsService.findOne(billWithdrawals);
        return ResultDataVoUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(BillWithdrawalsVo billWithdrawalsVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        BillWithdrawals billWithdrawals = new BillWithdrawals();
        BeanCopy.copyProperties(billWithdrawalsVo, billWithdrawals);
        Page<BillWithdrawals> findAll = billWithdrawalsService.findAll(billWithdrawals, pageable);
        return ResultDataVoUtil.success(findAll);
    }
}

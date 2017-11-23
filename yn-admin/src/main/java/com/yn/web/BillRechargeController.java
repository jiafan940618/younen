package com.yn.web;

import com.yn.vo.re.ResultVOUtil;

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

import com.yn.model.BillRecharge;
import com.yn.model.Recharge;
import com.yn.service.BillRechargeService;
import com.yn.service.FRechargeService;
import com.yn.utils.BeanCopy;
import com.yn.vo.BillRechargeVo;
import com.yn.vo.RechargeVo;

@RestController
@RequestMapping("/server/billRecharge")
public class BillRechargeController {
    @Autowired
    BillRechargeService billRechargeService;
    @Autowired
    FRechargeService rechargeService;
    
    

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        BillRecharge findOne = billRechargeService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody BillRechargeVo billRechargeVo) {
        BillRecharge billRecharge = new BillRecharge();
        BeanCopy.copyProperties(billRechargeVo, billRecharge);
        billRechargeService.save(billRecharge);
        return ResultVOUtil.success(billRecharge);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        billRechargeService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(BillRechargeVo billRechargeVo) {
        BillRecharge billRecharge = new BillRecharge();
        BeanCopy.copyProperties(billRechargeVo, billRecharge);
        BillRecharge findOne = billRechargeService.findOne(billRecharge);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(BillRechargeVo billRechargeVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        BillRecharge billRecharge = new BillRecharge();
        BeanCopy.copyProperties(billRechargeVo, billRecharge);
        Page<BillRecharge> findAll = billRechargeService.findAll(billRecharge, pageable);
        return ResultVOUtil.success(findAll);
    }
    
    @RequestMapping(value = "/RechargefindAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object RechargefindAll(RechargeVo RechargeVo) {
        Recharge billRecharge = new Recharge();
        BeanCopy.copyProperties(RechargeVo, billRecharge);
        List<Recharge> findAll = rechargeService.findAll(billRecharge);
        return ResultVOUtil.success(findAll);
    }
    
}

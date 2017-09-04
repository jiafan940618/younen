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

import com.yn.model.ServerPlan;
import com.yn.service.ServerPlanService;
import com.yn.utils.BeanCopy;
import com.yn.vo.ServerPlanVo;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/client/serverPlan")
public class ServerPlanController {
    @Autowired
    ServerPlanService serverPlanService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        ServerPlan findOne = serverPlanService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody ServerPlanVo serverPlanVo) {
        ServerPlan serverPlan = new ServerPlan();
        BeanCopy.copyProperties(serverPlanVo, serverPlan);
        serverPlanService.save(serverPlan);
        return ResultVOUtil.success(serverPlan);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        serverPlanService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(ServerPlanVo serverPlanVo) {
        ServerPlan serverPlan = new ServerPlan();
        BeanCopy.copyProperties(serverPlanVo, serverPlan);
        ServerPlan findOne = serverPlanService.findOne(serverPlan);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(ServerPlanVo serverPlanVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        ServerPlan serverPlan = new ServerPlan();
        BeanCopy.copyProperties(serverPlanVo, serverPlan);
        Page<ServerPlan> findAll = serverPlanService.findAll(serverPlan, pageable);
        return ResultVOUtil.success(findAll);
    }
}
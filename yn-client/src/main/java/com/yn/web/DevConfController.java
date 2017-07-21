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

import com.yn.model.DevConf;
import com.yn.service.DevConfService;
import com.yn.utils.BeanCopy;
import com.yn.vo.DevConfVo;
import com.yn.vo.re.ResultDataVoUtil;

@RestController
@RequestMapping("/server/devConf")
public class DevConfController {
    @Autowired
    DevConfService devConfService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        DevConf findOne = devConfService.findOne(id);
        return ResultDataVoUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody DevConfVo devConfVo) {
        DevConf devConf = new DevConf();
        BeanCopy.copyProperties(devConfVo, devConf);
        devConfService.save(devConf);
        return ResultDataVoUtil.success(devConf);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        devConfService.delete(id);
        return ResultDataVoUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(DevConfVo devConfVo) {
        DevConf devConf = new DevConf();
        BeanCopy.copyProperties(devConfVo, devConf);
        DevConf findOne = devConfService.findOne(devConf);
        return ResultDataVoUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(DevConfVo devConfVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        DevConf devConf = new DevConf();
        BeanCopy.copyProperties(devConfVo, devConf);
        Page<DevConf> findAll = devConfService.findAll(devConf, pageable);
        return ResultDataVoUtil.success(findAll);
    }
}

package com.yn.web;

import com.yn.dao.mapper.SystemConfigMapper;
import com.yn.model.SystemConfig;
import com.yn.utils.ObjToMap;
import com.yn.vo.re.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/server/test")
public class AAATestController {


    @Autowired
    private SystemConfigMapper systemConfigMapper;


    @RequestMapping(value = "/getPhoneCode", method = {RequestMethod.POST})
    @ResponseBody
    public Object getPhoneCode(SystemConfig systemConfig) {


        SystemConfig systemConfigs = systemConfigMapper.select(34L);


        return ResultVOUtil.success(systemConfigs);
    }

}

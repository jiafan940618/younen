package com.yn.web;

import com.yn.domain.EachHourTemStation;
import com.yn.enums.AmmeterTypeEnum;
import com.yn.service.TemStationService;
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
    private TemStationService temStationService;


    @RequestMapping(value = "/getTodayKwh2", method = {RequestMethod.POST})
    @ResponseBody
    public Object getTodayKwh2() {


        List<EachHourTemStation> todayKwh2 = temStationService.getTodayKwh(null, AmmeterTypeEnum.GENERATED_ELECTRICITY.getCode().longValue());


        return ResultVOUtil.success(todayKwh2);
    }


}

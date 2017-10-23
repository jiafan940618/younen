
package com.yn.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yn.service.ElecDataDayService;

@RestController
@RequestMapping("/server/test")
public class AAATestController {


    @Autowired
    private ElecDataDayService elecDataDayService;


//    @RequestMapping(value = "/getTodayKwh2", method = {RequestMethod.POST})
//    @ResponseBody
//    public Object getTodayKwh2() {
//
//
//        List<EachHourTemStation> todayKwh2 = elecDataDayService.getTodayKwh(null, AmmeterTypeEnum.GENERATED_ELECTRICITY.getCode().longValue());
//
//
//        return ResultVOUtil.success(todayKwh2);
//    }


}


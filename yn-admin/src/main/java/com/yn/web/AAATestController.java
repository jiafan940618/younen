package com.yn.web;

import com.yn.vo.re.ResultVOUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server/test")
public class AAATestController {


    @RequestMapping(value = "/getPhoneCode", method = {RequestMethod.POST})
    @ResponseBody
    public Object getPhoneCode() {
        return ResultVOUtil.success();
    }

}

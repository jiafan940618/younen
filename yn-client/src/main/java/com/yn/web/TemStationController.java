package com.yn.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.domain.EachHourTemStation;
import com.yn.model.TemStation;
import com.yn.service.TemStationService;
import com.yn.utils.BeanCopy;
import com.yn.vo.TemStationVo;
import com.yn.vo.re.ResultDataVoUtil;

@RestController
@RequestMapping("/client/temStation")
public class TemStationController {
    @Autowired
    TemStationService temStationService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        TemStation findOne = temStationService.findOne(id);
        return ResultDataVoUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody TemStationVo temStationVo) {
        TemStation temStation = new TemStation();
        BeanCopy.copyProperties(temStationVo, temStation);
        temStationService.save(temStation);
        return ResultDataVoUtil.success(temStation);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        temStationService.delete(id);
        return ResultDataVoUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(TemStationVo temStationVo) {
        TemStation temStation = new TemStation();
        BeanCopy.copyProperties(temStationVo, temStation);
        TemStation findOne = temStationService.findOne(temStation);
        return ResultDataVoUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(TemStationVo temStationVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        TemStation temStation = new TemStation();
        BeanCopy.copyProperties(temStationVo, temStation);
        Page<TemStation> findAll = temStationService.findAll(temStation, pageable);
        return ResultDataVoUtil.success(findAll);
    }
    
    /**
     * 根据电站id查找今日每个时刻的发电量/用电量
     * @param stationId
     * @param type
     * @return
     */
    @RequestMapping(value = "/todayKwh", method = {RequestMethod.POST})
    @ResponseBody
    public Object todayKwh(@RequestParam(value="stationId",required=true)Long stationId, @RequestParam(value="type",required=true)Integer type) {
        List<EachHourTemStation> todayKwhByStationId = temStationService.getTodayKwhByStationId(stationId, type);
        return ResultDataVoUtil.success(todayKwhByStationId);
    }
    
}

package com.yn.web;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.yn.dao.ElecDataHourDao;
import com.yn.model.ElecDataHour;
import com.yn.service.ElecDataHourService;
import com.yn.utils.BeanCopy;
import com.yn.vo.TemStationVo;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/server/temStation")
public class ElecDataHourController {


    @Autowired
    private ElecDataHourService elecDataHourService;
    @Autowired
    private ElecDataHourDao elecDataHourDao;


    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
    	ElecDataHour findOne = elecDataHourService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody TemStationVo temStationVo) {
    	ElecDataHour temStation = new ElecDataHour();
        BeanCopy.copyProperties(temStationVo, temStation);
        elecDataHourService.save(temStation);
        return ResultVOUtil.success(temStation);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
    	elecDataHourService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(TemStationVo temStationVo) {
    	ElecDataHour temStation = new ElecDataHour();
        BeanCopy.copyProperties(temStationVo, temStation);
        ElecDataHour findOne = elecDataHourService.findOne(temStation);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(TemStationVo temStationVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
    	ElecDataHour temStation = new ElecDataHour();
        BeanCopy.copyProperties(temStationVo, temStation);
        Page<ElecDataHour> findAll = elecDataHourService.findAll(temStation, pageable);
        return ResultVOUtil.success(findAll);
    }

    /**
     * 根据电站id查找今日每个时刻的发电量/用电量
     *
     * @param stationId
     * @param type
     * @return
     */
    @RequestMapping(value = "/todayKwh", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object todayKwh(@RequestParam("stationId") Long stationId, @RequestParam("type") Integer type) {
    	List<Map<String, Object>> todayKwhByStationId = elecDataHourService.getTodayKwhByStationId(stationId, type);
        return ResultVOUtil.success(todayKwhByStationId);
    }


    /**
     * 设备地址
     */
    @RequestMapping(value = "/findDAddr", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object test(@RequestParam("stationId") Long stationId, @RequestParam("type") Integer type) {
        Set<Long> findDAddr = elecDataHourDao.findDAddr(stationId, type);
        return ResultVOUtil.success(findDAddr);
    }


}


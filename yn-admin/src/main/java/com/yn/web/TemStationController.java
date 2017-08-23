package com.yn.web;

import com.yn.dao.TemStationDao;
import com.yn.domain.EachHourTemStation;
import com.yn.model.TemStation;
import com.yn.service.TemStationService;
import com.yn.utils.BeanCopy;
import com.yn.vo.TemStationVo;
import com.yn.vo.re.ResultDataVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/server/temStation")
public class TemStationController {


    @Autowired
    private TemStationService temStationService;
    @Autowired
    private TemStationDao temStationDao;


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
     *
     * @param stationId
     * @param type
     * @return
     */
    @RequestMapping(value = "/todayKwh", method = {RequestMethod.POST})
    @ResponseBody
    public Object todayKwh(@RequestParam(value = "stationId") Long stationId, @RequestParam(value = "type") Integer type) {
        List<EachHourTemStation> todayKwhByStationId = temStationService.getTodayKwhByStationId(stationId, type);
        return ResultDataVoUtil.success(todayKwhByStationId);
    }


    /**
     * 设备地址
     */
    @RequestMapping(value = "/findDAddr", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object test(@RequestParam("stationId") Long stationId, @RequestParam("type") Integer type) {
        List<Long> findDAddr = temStationDao.findDAddr(stationId, type);
        return ResultDataVoUtil.success(findDAddr);
    }


}

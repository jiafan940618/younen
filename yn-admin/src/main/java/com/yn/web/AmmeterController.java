package com.yn.web;

import com.yn.dao.AmmeterDao;
import com.yn.dao.TemStationDao;
import com.yn.enums.DeleteEnum;
import com.yn.model.Ammeter;
import com.yn.service.AmmeterService;
import com.yn.utils.BeanCopy;
import com.yn.vo.AmmeterVo;
import com.yn.vo.re.ResultDataVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/server/ammeter")
public class AmmeterController {


    @Autowired
    private AmmeterService ammeterService;
    @Autowired
    private AmmeterDao ammeterDao;
    @Autowired
    private TemStationDao temStationDao;


    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        Ammeter findOne = ammeterService.findOne(id);
        return ResultDataVoUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody AmmeterVo ammeterVo) {
        Ammeter ammeter = ammeterService.saveAndbindStation(ammeterVo);
        return ResultDataVoUtil.success(ammeter);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        ammeterService.delete(id);
        return ResultDataVoUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(AmmeterVo ammeterVo) {
        Ammeter ammeter = new Ammeter();
        BeanCopy.copyProperties(ammeterVo, ammeter);
        Ammeter findOne = ammeterService.findOne(ammeter);
        return ResultDataVoUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(AmmeterVo ammeterVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Ammeter ammeter = new Ammeter();
        BeanCopy.copyProperties(ammeterVo, ammeter);
        Page<Ammeter> findAll = ammeterService.findAll(ammeter, pageable);
        return ResultDataVoUtil.success(findAll);
    }

    /**
     * 解绑电站
     *
     * @param ammeterId
     * @return
     */
    @RequestMapping(value = "/relieveStation", method = {RequestMethod.POST})
    @ResponseBody
    public Object relieveStation(@RequestParam("ammeterId") Long ammeterId) {
        Ammeter ammeter = ammeterService.relieveStation(ammeterId);
        return ResultDataVoUtil.success(ammeter);
    }

    /**
     * 设备地址
     */
    @RequestMapping(value = "/findDAddr", method = {RequestMethod.POST})
    @ResponseBody
    public Object findDAddr(Long stationId, Integer type) {
        List<Long> findDAddr = ammeterDao.findDAddr(stationId, type);
        return ResultDataVoUtil.success(findDAddr);
    }

    @RequestMapping(value = "/test", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object test(Long stationId, Integer type) {
        List<Long> findDAddr = temStationDao.findDaddr(stationId, type);
        return ResultDataVoUtil.success(findDAddr);
    }

}

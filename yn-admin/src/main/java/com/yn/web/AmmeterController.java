package com.yn.web;

import com.yn.dao.AmmeterDao;
import com.yn.enums.AmmeterTypeEnum;
import com.yn.enums.ResultEnum;
import com.yn.model.Ammeter;
import com.yn.model.City;
import com.yn.model.Province;
import com.yn.service.AmmeterService;
import com.yn.service.CityService;
import com.yn.service.ProvinceService;
import com.yn.utils.BeanCopy;
import com.yn.vo.AmmeterVo;
import com.yn.vo.re.ResultDataVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
    AmmeterService ammeterService;
    @Autowired
    AmmeterDao ammeterDao;
    @Autowired
    CityService cityService;
    @Autowired
    ProvinceService provinceService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        Ammeter findOne = ammeterService.findOne(id);
        return ResultDataVoUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody AmmeterVo ammeterVo) {
        Ammeter ammeter = new Ammeter();
        BeanCopy.copyProperties(ammeterVo, ammeter);

        // 设置cityText provinceText
        City city = cityService.findOne(ammeter.getCityId());
        Province province = provinceService.findOne(ammeter.getProvinceId());
        ammeter.setCityText(city.getCityText());
        ammeter.setProvinceText(province.getProvinceText());

        // 根据dAddr设置发电用电
        String dAddr = ammeter.getdAddr().toString();
        if (dAddr.substring(0, 1).equals(AmmeterTypeEnum.GENERATED_ELECTRICITY.getCode())) {
            ammeter.setType(1);
        } else if (dAddr.substring(0, 1).equals(AmmeterTypeEnum.USE_ELECTRICITY.getCode())) {
            ammeter.setType(2);
        }

        // 判断是否有关联电站
        if (ammeter.getStationId() == null) {
            return ResultDataVoUtil.error(ResultEnum.NO_CHOOSE_STATION);
        }

        Ammeter ammeterR = new Ammeter();
        ammeterR.setcAddr(ammeter.getcAddr());
        ammeterR.setdAddr(ammeter.getdAddr());
        Ammeter findOne = ammeterDao.findOne(Example.of(ammeterR));

        if (findOne != null) {
            if (ammeter.getId() == null) {
                ammeter.setId(findOne.getId());
            }
        }
        ammeterService.save(ammeter);

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
     */
    @RequestMapping(value = "/relieveStation", method = {RequestMethod.POST})
    @ResponseBody
    public Object relieveStation(@RequestParam("ammeterId") Long ammeterId) {
        Ammeter findOne = ammeterDao.findOne(ammeterId);
        if (findOne != null) {
            findOne.setStationId(null);
            ammeterDao.save(findOne);
        }
        return ResultDataVoUtil.success(findOne);
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

}

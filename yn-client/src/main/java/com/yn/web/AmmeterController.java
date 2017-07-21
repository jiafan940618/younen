package com.yn.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.dao.AmmeterDao;
import com.yn.model.Ammeter;
import com.yn.service.AmmeterService;
import com.yn.utils.BeanCopy;
import com.yn.vo.AmmeterVo;
import com.yn.vo.re.ResultDataVoUtil;

@RestController
@RequestMapping("/server/ammeter")
public class AmmeterController {
    @Autowired
    AmmeterService ammeterService;
    @Autowired
    AmmeterDao ammeterDao;

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
        
        String dAddr = ammeter.getdAddr().toString();
        if (dAddr.substring(0, 1).equals("1")) {
        	ammeter.setType(1);
		} else if (dAddr.substring(0, 1).equals("2")) {
			ammeter.setType(2);
		}
        
        if (ammeter.getStationId() == null) {
			return ResultDataVoUtil.error(777, "请选择电站");
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
    public Object relieveStation(Long ammeterId) {
        Ammeter findOne = ammeterDao.findOne(ammeterId);
        if (findOne == null) {
			return ResultDataVoUtil.error(777, "电表不存在");
		}
        findOne.setStationId(null);
        ammeterDao.save(findOne);
        
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

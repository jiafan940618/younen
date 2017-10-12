package com.yn.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.yn.vo.re.ResultVOUtil;



import org.springframework.beans.factory.annotation.Autowired;
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
import com.yn.dao.OrderDao;
import com.yn.dao.StationDao;
import com.yn.dao.SubsidyDao;
import com.yn.dao.TemStationDao;
import com.yn.model.Station;
import com.yn.service.OrderService;
import com.yn.service.StationService;
import com.yn.service.SystemConfigService;
import com.yn.service.TemStationService;
import com.yn.utils.BeanCopy;
import com.yn.vo.NewUserVo;
import com.yn.vo.StationVo;
import com.yn.vo.UserVo;

@RestController
@RequestMapping("/client/station")
public class StationController {
    @Autowired
    StationService stationService;
    @Autowired
    StationDao stationDao;
    @Autowired
    OrderDao orderDao;
    @Autowired
    OrderService orderService;
    @Autowired
    AmmeterDao ammeterDao;
    @Autowired
    SubsidyDao subsidyDao;
    @Autowired
    SystemConfigService systemConfigService;
    @Autowired
    TemStationDao temStationDao;
    @Autowired
    TemStationService temStationService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        Station findOne = stationService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody StationVo stationVo) {
        Station station = new Station();
        BeanCopy.copyProperties(stationVo, station);
        stationService.save(station);
        return ResultVOUtil.success(station);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        stationService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(StationVo stationVo) {
        Station station = new Station();
        BeanCopy.copyProperties(stationVo, station);
        Station findOne = stationService.findOne(station);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(StationVo stationVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Station station = new Station();
        BeanCopy.copyProperties(stationVo, station);
        Page<Station> findAll = stationService.findAll(station, pageable);
        return ResultVOUtil.success(findAll);
    }
    
    /**
	 * 电站信息
	 */
    @RequestMapping(value = "/stationInfo", method = {RequestMethod.POST})
	@ResponseBody
	public Object stationInfo(Long stationId) {
		Map<String, Object> stationInfo = stationService.stationInfo(stationId);
		return ResultVOUtil.success(stationInfo);
	}
    
    /**
	 * 25年收益
	 */
    @RequestMapping(value = "/get25YearIncome", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public Object get25YearIncome(Long stationId) {
		Map<String,Object> map = stationService.get25YearIncome(stationId);
		return ResultVOUtil.success(map);
	}
    
    /**
	 * web 查询用户的所有电站信息
	 */
	@ResponseBody
	@RequestMapping(value = "/runningStation",method = {RequestMethod.POST, RequestMethod.GET})
	public Object runningStation(HttpSession session,Station station) {
		
		NewUserVo userVo=(NewUserVo)session.getAttribute("user");
	    Map<String, Object> stationByUser=new HashMap<>();
	     
	    if (userVo!=null) {

	    	
	    	station.setUserId(userVo.getId());
	    	if (stationDao.findByUserId(station.getUserId())!=null) {
	    		List<Station> stations=stationDao.findByUserId(station.getUserId());
				stationByUser = stationService.stationByUser(stations);
			}
				List<Station> stations=stationDao.findAllStation();
			    stationByUser = stationService.stationByUser(stations);
		    }else{
		    List<Station> stations=stationDao.findAllStation();
		    stationByUser = stationService.stationByUser(stations);
		    }
		return ResultVOUtil.success(stationByUser);
	}
	
	/**
	 * web 查询用户的装机容量
	 */
	@ResponseBody
	@RequestMapping(value = "/checkCapacity",method = {RequestMethod.POST, RequestMethod.GET})
    public Object checkCapacity(HttpSession session,Station station) {

		NewUserVo userVo=(NewUserVo)session.getAttribute("user");
	    List<Map<Object, Object>> capacityAll=new ArrayList<>();
	     
	    if (userVo!=null ) {
	    	station.setUserId(userVo.getId());
	    	if (stationDao.findByUserId(station.getUserId())!=null) {
	    		List<Station> stations=stationDao.findByUserId(station.getUserId());
				capacityAll = stationService.checkCapacity(stations);
			}
	    	List<Station> stations=stationDao.findAllStation();
		    capacityAll = stationService.checkCapacity(stations);
		    }else{
		    List<Station> stations=stationDao.findAllStation();
		    capacityAll = stationService.checkCapacity(stations);
		    }
		return ResultVOUtil.success(capacityAll);
	}
    
}

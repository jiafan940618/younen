package com.yn.web;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.dao.StationDao;
import com.yn.domain.EachHourTemStation;
import com.yn.model.Station;
import com.yn.model.TemStation;
import com.yn.service.StationService;
import com.yn.service.TemStationService;
import com.yn.utils.BeanCopy;
import com.yn.vo.TemStationVo;
import com.yn.vo.UserVo;

@RestController
@RequestMapping("/client/temStation")
public class TemStationController {
	@Autowired
    TemStationService temStationService;
    @Autowired
    StationService stationService;
    @Autowired
    StationDao stationDao;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        TemStation findOne = temStationService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody TemStationVo temStationVo) {
        TemStation temStation = new TemStation();
        BeanCopy.copyProperties(temStationVo, temStation);
        temStationService.save(temStation);
        return ResultVOUtil.success(temStation);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        temStationService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(TemStationVo temStationVo) {
        TemStation temStation = new TemStation();
        BeanCopy.copyProperties(temStationVo, temStation);
        TemStation findOne = temStationService.findOne(temStation);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(TemStationVo temStationVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        TemStation temStation = new TemStation();
        BeanCopy.copyProperties(temStationVo, temStation);
        Page<TemStation> findAll = temStationService.findAll(temStation, pageable);
        return ResultVOUtil.success(findAll);
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
        return ResultVOUtil.success(todayKwhByStationId);
    }
    
    /**
     * 根据用户查找每月的发电量
     * @return
     */
    @RequestMapping(value = "/monthKwh", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object monthKwh(HttpSession session,Station station) {
    	 UserVo userVo=(UserVo)session.getAttribute("user");
    	 Map<Object, Object> monthKwh=new HashMap<>();
    	 if (userVo!=null) {
    		 station.setUserId(userVo.getUserid());
			List<Station> stations=stationDao.findByUserId(station.getUserId());
			monthKwh=temStationService.monthKwh(stations);
			
		} else {
			List<Station> stations=stationDao.findAll();
			monthKwh=temStationService.monthKwh(stations);
		}
        
        return ResultVOUtil.success(monthKwh);
    }
}

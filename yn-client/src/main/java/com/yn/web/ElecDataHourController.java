package com.yn.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.yn.dao.StationDao;
import com.yn.model.ElecDataHour;
import com.yn.model.Station;
import com.yn.service.ElecDataHourService;
import com.yn.service.StationService;
import com.yn.utils.BeanCopy;
import com.yn.utils.PageInfo;
import com.yn.vo.NewUserVo;
import com.yn.vo.TemStationVo;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/client/temStation")
public class ElecDataHourController {
	@Autowired
	ElecDataHourService elecDataHourService;
    @Autowired
    StationService stationService;
    @Autowired
    StationDao stationDao;

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
    public Object findAll(TemStationVo temStationVo, Integer pageIndex) {
    	ElecDataHour temStation = new ElecDataHour();
        BeanCopy.copyProperties(temStationVo, temStation);
        temStation.setdAddr(temStationVo.getD_addr());
        PageHelper.startPage( pageIndex==null?1:pageIndex , 15 );
		List<ElecDataHour> list = elecDataHourService.findByMapper(temStation);
		PageInfo<ElecDataHour> pageInfo=new PageInfo<>(list);
        return ResultVOUtil.success(pageInfo);
    }
    
    /**
     * 根据电站id查找今日每个时刻的发电量/用电量
     * @param stationId
     * @param type
     * @return
     */
//    @RequestMapping(value = "/todayKwh", method = {RequestMethod.POST})
//    @ResponseBody
//    public Object todayKwh(@RequestParam(value="stationId",required=true)Long stationId, @RequestParam(value="type",required=true)Long type) {
//        List<EachHourTemStation> todayKwhByStationId = elecDataHourService.getTodayKwhByStationId(stationId, type);
//        return ResultVOUtil.success(todayKwhByStationId);
//    }
    
    /**
     * 根据用户查找每月的发电量
     * @return
     */
//    @RequestMapping(value = "/monthKwh", method = {RequestMethod.POST, RequestMethod.GET})
//    @ResponseBody
//    public Object monthKwh(HttpSession session,Station station) {
//    	NewUserVo userVo=(NewUserVo)session.getAttribute("user");
//    	 List<Map<Object, Object>> monthKwh=new ArrayList<>();
//
//    	
//    	 if (userVo!=null) {
//    		 station.setUserId(userVo.getId());
//    		 if (stationDao.findByUserId(station.getUserId())!=null) {
//    		List<Station> stations=stationDao.findByUserId(station.getUserId());
//    		monthKwh=elecDataHourService.monthKwh(stations);
//			}
//			
//			List<Station> stations=stationDao.findAll();
//			monthKwh=elecDataHourService.monthKwh(stations);
//			
//		} else {
//			List<Station> stations=stationDao.findAll();
//			monthKwh=elecDataHourService.monthKwh(stations);
//		}
//        
//        return ResultVOUtil.success(monthKwh);
//    }
//    
    /**
     * 根据用户查找每小时的发电量
     * @return
     */
//    @RequestMapping(value = "/oneHourKwh", method = {RequestMethod.POST, RequestMethod.GET})
//    @ResponseBody 
//    public Object oneHourKwh(@RequestParam(value="stationId",required=true)Long stationId, @RequestParam(value="type",required=true)Long type) {
//       List<Map<String, Object>> list =new ArrayList<>();
//       list=elecDataHourService.oneHourKwh(stationId, type);
//        return ResultVOUtil.success(list);
//    }
}
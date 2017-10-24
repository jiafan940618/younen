package com.yn.web;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.yn.dao.ElecDataDayDao;
import com.yn.dao.ElecDataHourDao;
import com.yn.dao.OrderDao;
import com.yn.dao.ServerDao;
import com.yn.dao.StationDao;
import com.yn.dao.SubsidyDao;
import com.yn.model.Station;
import com.yn.service.OrderService;
import com.yn.service.StationService;
import com.yn.service.SystemConfigService;
import com.yn.utils.BeanCopy;
import com.yn.vo.NewUserVo;
import com.yn.vo.StationVo;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/client/station")
public class StationController {
	
	private static final Logger logger = LoggerFactory.getLogger(StationController.class);
	
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
    ElecDataHourDao elecDataHourDao;
    @Autowired
    ElecDataDayDao elecDataDayDao;
    @Autowired
    ServerDao serverDao;

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
//    @RequestMapping(value = "/stationInfo", method = {RequestMethod.POST})
//	@ResponseBody
//	public Object stationInfo(Long stationId) {
//		Map<String, Object> stationInfo = stationService.stationInfo(stationId);
//		return ResultVOUtil.success(stationInfo);
//	}
//    
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
//	@ResponseBody
//	@RequestMapping(value = "/runningStation",method = {RequestMethod.POST, RequestMethod.GET})
//	public Object runningStation(HttpSession session,Station station) {
//		
//		
//	    Map<String, Object> stationByUser=new HashMap<>();
//		    List<Station> stations=stationDao.findAllStation();
//		    stationByUser = stationService.stationByUser(stations);
//		return ResultVOUtil.success(stationByUser);
//	}
//	
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
			}else{
	    	List<Station> stations=stationDao.findAllStation();
		    capacityAll = stationService.checkCapacity(stations);
			}
		    }else{
		    List<Station> stations=stationDao.findAllStation();
		    capacityAll = stationService.checkCapacity(stations);
		    }
		return ResultVOUtil.success(capacityAll);
	}
	
	/**
	 * web 查询用户的装机容量
	 */
//	@ResponseBody
//	@RequestMapping(value = "/numCapacity",method = {RequestMethod.POST, RequestMethod.GET})
//    public Object numCapacity(Station station,Integer type,String dateStr) {
//
//	    List<Map<Object, Object>> capacityAll=new ArrayList<>();
//		    List<Station> stations=stationDao.findAllStation();
//		    capacityAll = stationService.checkCapacity(stations,type,dateStr);
//		    
//		return ResultVOUtil.success(capacityAll);
//	}
    
	/**
	 * 实时数据里的电站分布
	 */
	@ResponseBody
	@RequestMapping(value = "/stationFenbu", method = { RequestMethod.POST, RequestMethod.GET })
	public Object stationFenbu(HttpSession session) {

			List<Map> rm2 = new ArrayList<>();
			
				Object rm[]=stationDao.stationFenbu();
				for (int i = 0; i < rm.length; i++) {
					Map<String, Object> map=new HashMap<>();
					Object object = rm[i];
					
					Object[] obj=(Object[])object;
					String provinceName=provinceName((String)obj[0]);
					map.put("name", provinceName);
					map.put("value", (BigInteger)obj[1]);
					rm2.add(map);
				}
		return ResultVOUtil.success(rm2);
	}
	private String provinceName(String str){
		// 判断是否是直辖市
		if(str.contains("市")){
			int i = str.indexOf("市");
			String rs = str.substring(0, i);
			return rs;
		} else if(str.contains("省")){
			int i = str.indexOf("省");
			String rs = str.substring(0, i);
			return rs;
		} else if(str.equals("内蒙古自治区")){
			return "内蒙古";
		} else if(str.equals("广西壮族自治区")){
			return "广西";
		} else if(str.equals("宁夏回族自治区")){
			return "宁夏";
		} else if(str.equals("新疆维吾尔自治区")){
			return "新疆";
		} else if(str.equals("香港特别行政区")){
			return "香港";
		} else if(str.equals("澳门特别行政区")){
			return "澳门";
		}
		return null;
	}
	
	@RequestMapping(value = "/findBystaId")
	@ResponseBody
	public Object FindBystationId(StationVo stationVo) {
		
		logger.info("传递id为 ---->  ++ ++++ +++++ "+stationVo.getId());
		
		
		Station station =	stationService.selectByPrimaryKey(stationVo.getId().intValue());
		
		return ResultVOUtil.success(station);
	}
	
	/** */
	@RequestMapping(value = "/saveType")
	@ResponseBody
	public Object SaveType(StationVo stationVo,HttpSession httpSession) {
		
		logger.info("传递type: ---- ----- ----- "+stationVo.getType());
		
		httpSession.setAttribute("type", stationVo.getType());
		
		return ResultVOUtil.success(null);
	}
	
	/**
	 * 查询用户电站,电表等信息
	 */
//	@ResponseBody
//	@RequestMapping(value = "/stationInformation", method = { RequestMethod.POST, RequestMethod.GET })
//	public Object stationInformation(Long stationId) {
//
//		Map<String, Object> information = new HashMap<>();
//		information = stationService.stationInformation(stationId);
//
//		return ResultVOUtil.success(information);
//	}

	/**
	 * 根据session查询用户电站25年收益
	 */
	@ResponseBody
	@RequestMapping(value = "/user25YearIncome", method = { RequestMethod.POST, RequestMethod.GET })
	public Object user25YearIncome(Long stationId) {

		Station station = stationDao.findOne(stationId);
		Map<String, Object> map = stationService.get25YearIncome(stationId);
		map.put("stationName", station.getStationName());
		map.put("income", stationService.userIncome(station));
		return ResultVOUtil.success(map);
	}
	
}

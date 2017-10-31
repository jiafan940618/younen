package com.yn.web;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

import com.github.pagehelper.PageHelper;
import com.yn.dao.StationDao;
import com.yn.model.ElecDataDay;
import com.yn.model.Station;
import com.yn.model.User;
import com.yn.service.ElecDataDayService;
import com.yn.service.SystemConfigService;
import com.yn.session.SessionCache;
import com.yn.utils.BeanCopy;
import com.yn.utils.PageInfo;
import com.yn.vo.ElecDataDayVo;
import com.yn.vo.TemStationYearVo;
import com.yn.vo.re.ResultVOUtil;


@RestController
@RequestMapping("/client/temStationYear")
public class ElecDataDayController {
	
	@Autowired
	SystemConfigService systemConfigService;
	@Autowired
	ElecDataDayService elecDataDayService;
	@Autowired
	StationDao stationDao;

	@RequestMapping(value = "/select", method = { RequestMethod.POST })
	@ResponseBody
	public Object findOne(Long id) {
		ElecDataDay findOne = elecDataDayService.findOne(id);
		return ResultVOUtil.success(findOne);
	}
	
	
	

	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public Object save(@RequestBody TemStationYearVo temStationYearVo) {
		ElecDataDay temStationYear = new ElecDataDay();
		BeanCopy.copyProperties(temStationYearVo, temStationYear);
		elecDataDayService.save(temStationYear);
		return ResultVOUtil.success(temStationYear);
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public Object delete(Long id) {
		elecDataDayService.delete(id);
		return ResultVOUtil.success();
	}

	@ResponseBody
	@RequestMapping(value = "/findOne", method = { RequestMethod.POST })
	public Object findOne(ElecDataDayVo elecDataDayVo) {
		ElecDataDay elecDataDay = new ElecDataDay();
		BeanCopy.copyProperties(elecDataDayVo, elecDataDay);
		ElecDataDay findOne = elecDataDayService.findOne(elecDataDay);
		return ResultVOUtil.success(findOne);
	}

	@RequestMapping(value = "/findAll", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object findAll(ElecDataDayVo elecDataDayVo,@PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) 
	Pageable pageable) {
		ElecDataDay elecDataDay = new ElecDataDay();
        BeanCopy.copyProperties(elecDataDayVo, elecDataDay);
        Page<ElecDataDay> findAll = elecDataDayService.findAll(elecDataDay, pageable);
        return ResultVOUtil.success(findAll);		
//		ElecDataDay temStationYear = new ElecDataDay();
//		BeanCopy.copyProperties(temStationYearVo, temStationYear);
//		temStationYear.setdAddr(temStationYearVo.getD_addr());
//		System.out.println(temStationYear.getQueryStartDtm());
//		PageHelper.startPage( pageIndex==null?1:pageIndex , 15 );
//		List<ElecDataDay> list = elecDataDayService.findByMapper(temStationYear);
//		PageInfo<ElecDataDay> pageInfo=new PageInfo<>(list);
//        return ResultVOUtil.success(pageInfo);
	}

	/**
	 * 根据用户查找每月的发电量
	 * 
	 * @return
	 */
	@RequestMapping(value = "/monthKwh", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object monthKwh(HttpSession session, Station station) {

		List<Map<Object, Object>> monthKwh = new ArrayList<>();

		List<Station> stations = stationDao.findAllStation();
		monthKwh = elecDataDayService.monthKwh(stations);

		return ResultVOUtil.success(monthKwh);
	}
	
@ResponseBody
@RequestMapping(value = "/huanbao")
public Object huanbao(ElecDataDay temStationYear, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
	
	User user = SessionCache.instance().getUser();
	if(user == null){
		return  ResultVOUtil.error(777, "抱歉，你没有登录!");
	}

	DecimalFormat df = new DecimalFormat("0.00");
	
	Map<String, String> newmap = systemConfigService.getlist();
	
	// 植树参数
	Double plant_trees_prm = Double.valueOf(newmap.get("plant_trees_prm"));
	// co2减排参数
	Double CO2_prm = Double.valueOf(newmap.get("CO2_prm"));
	// SO减排参数
	Double SO_prm = Double.valueOf(newmap.get("SO_prm"));
	
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	String year = sdf.format(date);

	List<Map<String, Object>> rl = new ArrayList<>();
	List<Map<String, Object>> rl2 = new ArrayList<>();
	List<Map<String, Object>> rl3 = new ArrayList<>();
	String [] time = {year+"-01",year+"-02",year+"-03",year+"-04",year+"-05",year+"-06",year+"-07",year+"-08",year+"-09",year+"-10",year+"-11",year+"-12"};
	
	Double kwh = 0.0;
	
	for(String timeStr:time){
		Map<String, Object> map = new HashMap<>();
		map.put("userId", user.getId());
		map.put("createDtm", timeStr);
		ElecDataDay tsy = elecDataDayService.findHuanbao(map);
		Map<String, Object> map2 = new HashMap<>();
		Map<String, Object> map3 = new HashMap<>();
		Map<String, Object> map4 = new HashMap<>();
		if(tsy != null){
			map2.put("co2", Double.valueOf(df.format(tsy.getKwh() * CO2_prm)));
			map2.put("createDtm", timeStr);
			map3.put("treeNum", Double.valueOf(df.format(tsy.getKwh() * plant_trees_prm)));
			map3.put("createDtm", timeStr);
			map4.put("SONum", Double.valueOf(df.format(tsy.getKwh() * SO_prm)));
			map4.put("createDtm", timeStr);
			
			rl.add(map2);
			rl2.add(map3);
			rl3.add(map4);
			
			kwh += tsy.getKwh();
			
		} else{
			map2.put("co2", 0);
			map2.put("createDtm", timeStr);
			map3.put("treeNum", 0);
			map3.put("createDtm", timeStr);
			map4.put("SONum", 0);
			map4.put("createDtm", timeStr);
			
			rl.add(map2);
			rl2.add(map3);
			rl3.add(map4);
		}	
	}

	Map<String, Object> rm = new HashMap<>();
	rm.put("co2data", rl);
	rm.put("treeData", rl2);
	rm.put("SOData", rl3);	
	
	rm.put("CO2_prm",Double.valueOf(df.format(kwh * CO2_prm)));
	rm.put("plant_trees_prm", Double.valueOf(df.format(kwh * plant_trees_prm)));

	return ResultVOUtil.success(rm);	
	}

	/**
	 * 根据用户查找每月的发电量
	 * 
	 * @return
	 */
	@RequestMapping(value = "/numKwh", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object numKwh(HttpSession session, Station station, Integer type, String dateStr) {

		List<Map<Object, Object>> monthKwh = new ArrayList<>();
		List<Station> stations = stationDao.findAllStation();
		monthKwh = elecDataDayService.numKwh(stations, type, dateStr);

		return ResultVOUtil.success(monthKwh);
	}
	
	
	/**
	 * 用电/发电统计
	 */
	@RequestMapping(value = "/workUseCount", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object workUseCount(Long stationId, Integer type) {

		Map<String, Object> workUseCount = new HashMap<>();

		workUseCount = elecDataDayService.workUseCount(stationId, type);

		return ResultVOUtil.success(workUseCount);
	}
	/**
	 * 用电/发电记录
	 */
	@RequestMapping(value = "/listCount", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object listCount(ElecDataDayVo elecDataDayVo,Long stationId,Integer pageIndex) {
		System.out.println(pageIndex);
		ElecDataDay elecDataDay = new ElecDataDay();
		BeanCopy.copyProperties(elecDataDayVo, elecDataDay);
		PageHelper.startPage( pageIndex==null?1:pageIndex,15);
		List<ElecDataDay> elecDataDays=elecDataDayService.findByMapper(elecDataDay, stationId);
		PageInfo<ElecDataDay> pageInfo=new PageInfo<>(elecDataDays);
		Map<String, Object> map=new HashMap<>();
		map.put("pageInfo", pageInfo);
		return ResultVOUtil.success(map);
	}
}
package com.yn.web;

import com.yn.vo.re.ResultVOUtil;

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
import com.yn.model.Station;
import com.yn.model.TemStation;
import com.yn.model.TemStationYear;
import com.yn.service.SystemConfigService;
import com.yn.service.TemStationYearService;
import com.yn.utils.BeanCopy;
import com.yn.utils.PageInfo;
import com.yn.vo.NewUserVo;
import com.yn.vo.TemStationYearVo;

@RestController
@RequestMapping("/client/temStationYear")
public class TemStationYearController {
	
	@Autowired
	SystemConfigService systemConfigService;
	@Autowired
	TemStationYearService temStationYearService;
	@Autowired
	StationDao stationDao;

	@RequestMapping(value = "/select", method = { RequestMethod.POST })
	@ResponseBody
	public Object findOne(Long id) {
		TemStationYear findOne = temStationYearService.findOne(id);
		return ResultVOUtil.success(findOne);
	}

	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public Object save(@RequestBody TemStationYearVo temStationYearVo) {
		TemStationYear temStationYear = new TemStationYear();
		BeanCopy.copyProperties(temStationYearVo, temStationYear);
		temStationYearService.save(temStationYear);
		return ResultVOUtil.success(temStationYear);
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public Object delete(Long id) {
		temStationYearService.delete(id);
		return ResultVOUtil.success();
	}

	@ResponseBody
	@RequestMapping(value = "/findOne", method = { RequestMethod.POST })
	public Object findOne(TemStationYearVo temStationYearVo) {
		TemStationYear temStationYear = new TemStationYear();
		BeanCopy.copyProperties(temStationYearVo, temStationYear);
		TemStationYear findOne = temStationYearService.findOne(temStationYear);
		return ResultVOUtil.success(findOne);
	}

	@RequestMapping(value = "/findAll", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object findAll(TemStationYearVo temStationYearVo,Integer pageIndex) {
		
		TemStationYear temStationYear = new TemStationYear();
		BeanCopy.copyProperties(temStationYearVo, temStationYear);
		temStationYear.setdAddr(temStationYearVo.getD_addr());
		System.out.println(temStationYear.getQueryStartDtm());
		PageHelper.startPage( pageIndex==null?1:pageIndex , 15 );
		List<TemStationYear> list = temStationYearService.findByMapper(temStationYear);
		PageInfo<TemStationYear> pageInfo=new PageInfo<>(list);
        return ResultVOUtil.success(pageInfo);
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
		monthKwh = temStationYearService.monthKwh(stations);

		return ResultVOUtil.success(monthKwh);
	}
	
	@ResponseBody
	@RequestMapping(value = "/huanbao")
	public Object huanbao(TemStationYear temStationYear, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		
		NewUserVo user = (NewUserVo)httpSession.getAttribute("user");
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
		for(String timeStr:time){
			Map<String, Object> map = new HashMap<>();
			map.put("userId", user.getId());
			map.put("createDtm", timeStr);
			TemStationYear tsy = temStationYearService.findHuanbao(map);
			Map<String, Object> map2 = new HashMap<>();
			Map<String, Object> map3 = new HashMap<>();
			Map<String, Object> map4 = new HashMap<>();
			if(tsy != null){
				map2.put("co2", df.format(Double.valueOf(tsy.getKwh()) * CO2_prm));
				map2.put("createDtm", timeStr);
				map3.put("treeNum", df.format(Double.valueOf(tsy.getKwh()) * plant_trees_prm));
				map3.put("createDtm", timeStr);
				map4.put("SONum", df.format(Double.valueOf(tsy.getKwh()) * SO_prm));
				map4.put("createDtm", timeStr);
				
				rl.add(map2);
				rl2.add(map3);
				rl3.add(map4);
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
		monthKwh = temStationYearService.numKwh(stations, type, dateStr);

		return ResultVOUtil.success(monthKwh);
	}
	
	/**
	 * 
	 */
	@RequestMapping(value = "/workUseCount", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object workUseCount(Long stationId, Long type) {

		Map<String, Object> workUseCount = new HashMap<>();

		workUseCount = temStationYearService.workUseCount(stationId, type);

		return ResultVOUtil.success(workUseCount);
	}
	
	@RequestMapping(value = "/listCount", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object listCount(TemStationYearVo temStationYearVo,
			@PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		TemStationYear temStationYear = new TemStationYear();
		BeanCopy.copyProperties(temStationYearVo, temStationYear);
		Page<TemStationYear> listCount = temStationYearService.listCount(temStationYear, pageable);
		return ResultVOUtil.success(listCount);
	}
}
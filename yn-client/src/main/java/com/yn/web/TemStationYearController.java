package com.yn.web;

import com.yn.vo.re.ResultVOUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.yn.dao.StationDao;
import com.yn.model.Station;
import com.yn.model.TemStationYear;
import com.yn.service.TemStationYearService;
import com.yn.utils.BeanCopy;
import com.yn.vo.NewUserVo;
import com.yn.vo.TemStationYearVo;

@RestController
@RequestMapping("/client/temStationYear")
public class TemStationYearController {
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
	public Object findAll(TemStationYearVo temStationYearVo,
			@PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		TemStationYear temStationYear = new TemStationYear();
		BeanCopy.copyProperties(temStationYearVo, temStationYear);
		Page<TemStationYear> findAll = temStationYearService.findAll(temStationYear, pageable);
		return ResultVOUtil.success(findAll);
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
}
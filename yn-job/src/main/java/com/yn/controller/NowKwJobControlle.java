package com.yn.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yn.dao.StationDao;
import com.yn.model.Ammeter;
import com.yn.model.Station;
import com.yn.service.AmmeterService;
import com.yn.service.StationService;
import com.yn.vo.re.ResultVOUtil;

/** 
 * 更新电站的实时功率
 * 
 * @author {lzyqssn} <2017年9月29日-上午10:34:15>
 */
@Controller
@RequestMapping("/client/NKJob")
public class NowKwJobControlle {

	@Autowired
	AmmeterService ammeterService;
	@Autowired
	StationService stationService;
	@Autowired
	StationDao stationDao;

	/**
	 * 模拟NowKwJob的job
	 * 
	 * @return
	 */
	@RequestMapping("/simulationNKGAndSOrU")
	//@RequestMapping("/job")
	public @ResponseBody Object job() {
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		List<Station> stations = stationService.findAll(new Station());
		int index = 0;
		jsonResult.put("stations.size", stations.size());
		for (Station station : stations) {
			Ammeter ammeterR = new Ammeter();
			ammeterR.setStationId(station.getId());
			Double tolNowKw = 0d;
			List<Ammeter> ammeters = ammeterService.findAll(ammeterR);
			jsonResult.put("ammeters.size"+index++, ammeters.size());
			for (Ammeter ammeter : ammeters) {
				tolNowKw += ammeter.getNowKw();
			}

			station.setNowKw(tolNowKw);
			Station save = stationDao.save(station);
			jsonResult.put("save_" + (index), save.getStationName());
		}
		return ResultVOUtil.success(jsonResult);
	}
}

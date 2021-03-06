package com.yn.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yn.dao.StationDao;
import com.yn.dao.mapper.StationMapper;
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
	@Autowired
	StationMapper stationMapper;

	/**
	 * 模拟NowKwJob的job
	 * 只是为了模拟，并没有实际用途、
	 * @return
	 */
//	@RequestMapping("/simulationNKGAndSOrU")
	//@RequestMapping("/job")
	public/* @ResponseBody*/ Object job() {
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		List<Station> stations = stationService.findAll(new Station());
		for (Station station : stations) {
			Ammeter ammeterR = new Ammeter();
			ammeterR.setStationId(station.getId());

			Double tolNowKw = 0d;
			//List<Ammeter> ammeters = ammeterService.findAll(ammeterR);
			List<Ammeter> ammeters = ammeterService.findAllByMapper(ammeterR);
			for (Ammeter ammeter : ammeters) {
				tolNowKw += ammeter.getNowKw();
			}
			//station.setNowKw(tolNowKw);
			// stationDao.save(station);
			if (stationDao.findOne(station.getId()) != null){
				stationMapper.updateByPrimaryKeySelective(station);
				System.out.println("NowKwJob--> station::修改成功！-->"+new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
			}else{
				stationMapper.insert(station);
				System.out.println("NowKwJob--> station::新增成功！-->"+new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
			}
		}
		return ResultVOUtil.success(jsonResult);
	}
}

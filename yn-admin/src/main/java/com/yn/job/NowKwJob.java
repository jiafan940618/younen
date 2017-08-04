package com.yn.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.yn.dao.StationDao;
import com.yn.model.Ammeter;
import com.yn.model.Station;
import com.yn.service.AmmeterService;
import com.yn.service.StationService;
import org.springframework.stereotype.Component;


/**
 * 更新电站的实时功率
 */
//@Component
public class NowKwJob {
	@Autowired
	AmmeterService ammeterService;
	@Autowired
	StationService stationService;
	@Autowired
	StationDao stationDao;
	
	@Scheduled(fixedDelay = 10 * 1000)
	private void job() {
		List<Station> stations = stationService.findAll(new Station());
		for (Station station : stations) {
			Ammeter ammeterR = new Ammeter();
			ammeterR.setStationId(station.getId());
			
			Double tolNowKw = 0d;
			List<Ammeter> ammeters = ammeterService.findAll(ammeterR);
			for (Ammeter ammeter : ammeters) {
				tolNowKw += ammeter.getNowKw();
			}
			
			station.setNowKw(tolNowKw);
			stationDao.save(station);
		}
	}
	
}

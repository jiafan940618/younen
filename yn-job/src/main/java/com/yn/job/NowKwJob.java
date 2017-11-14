package com.yn.job;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yn.dao.StationDao;
import com.yn.dao.mapper.StationMapper;
import com.yn.model.Ammeter;
import com.yn.model.Station;
import com.yn.service.AmmeterService;
import com.yn.service.StationService;

/**
 * 更新电站的实时功率
 */
@Component
public class NowKwJob {
	@Autowired
	AmmeterService ammeterService;
	@Autowired
	StationService stationService;
	@Autowired
	StationDao stationDao;
	@Autowired
	StationMapper stationMapper;
	private static PrintStream mytxt;
	private static PrintStream out;
	public NowKwJob(){
		try {
			mytxt = new PrintStream(new FileOutputStream(new File("/opt/ynJob/log/NowKwJob.log"),true));
//			mytxt = new PrintStream("./nowKwJobLog.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Scheduled(fixedDelay = 10 * 1000)
	private void job() {
		// 设置日志文件输出路径。
		out = System.out;
		System.setOut(mytxt);
		System.out.println("NowKwJob文档执行的日期是：" + new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
		System.out.println("NowKwJob-->job::run");
		List<Station> stations = stationService.findAll(new Station());
		for (Station station : stations) {
			Ammeter ammeterR = new Ammeter();
			ammeterR.setStationId(station.getId());

			Double tolNowKw = 0d;
			// List<Ammeter> ammeters = ammeterService.findAll(ammeterR);
			List<Ammeter> ammeters = ammeterService.findAllByMapper(ammeterR);
			for (Ammeter ammeter : ammeters) {
				if (ammeter.getNowKw() != null) {
					tolNowKw += ammeter.getNowKw();
				}
			}
			// station.setNowKw(tolNowKw);
			// stationDao.save(station);
			if (stationDao.findOne(station.getId()) != null) {
				stationMapper.updateByPrimaryKeySelective(station);
				System.out.println("NowKwJob--> station::修改成功！-->"
						+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
			} else {
				stationMapper.insert(station);
				System.out.println("NowKwJob--> station::新增成功！-->"
						+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
			}
		}
		System.setOut(out);
		System.out.println("NowKwJob日志保存完毕。");
	}

}

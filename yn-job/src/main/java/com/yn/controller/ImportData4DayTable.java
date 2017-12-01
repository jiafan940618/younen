package com.yn.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yn.model.AmPhaseRecord;
import com.yn.model.Ammeter;
import com.yn.model.ElecDataDay;
import com.yn.service.AmPhaseRecordService;
import com.yn.service.AmmeterService;
import com.yn.service.ElecDataDayService;

@RestController
@RequestMapping("/job/iddt")
public class ImportData4DayTable {

	@Autowired
	private ElecDataDayService elecDataDayService;
	@Autowired
	private AmmeterService ammeterService;
	@Autowired
	private AmPhaseRecordService amPhaseRecordService;

	@RequestMapping(value = "/doImportData/{date}", method = RequestMethod.GET)
	public Object job(@PathVariable("date") String date) {
		System.out.println("start");
		ElecDataDay dataDay = new ElecDataDay();
		List<Ammeter> findAll = ammeterService.findAll(new Ammeter());
		findAll.forEach(ammeter -> {
			AmPhaseRecord aprR = new AmPhaseRecord();
			aprR.setcAddr(Integer.parseInt(ammeter.getcAddr()));
			aprR.setdType(ammeter.getdType());
			aprR.setiAddr(ammeter.getiAddr());
			aprR.setDealt(0);
			aprR.setDate(date);// 设置需要导入的时间。
			aprR.setwAddr(0);
			aprR.setdAddr(1L);
			AmPhaseRecord max1 = amPhaseRecordService.findMaxData(aprR);
			AmPhaseRecord min1 = amPhaseRecordService.findMinData(aprR);
			if (max1 != null && min1 != null) {
				Double kwh = max1.getKwhTotal() - min1.getKwhTotal();
				dataDay.setDel(0);
				dataDay.setdAddr(max1.getdAddr());
				dataDay.setdType(max1.getdType());
				dataDay.setDevConfCode(date);
				dataDay.setKw(max1.getKw());
				Long s = max1.getMeterTime();
				Long min = min1.getMeterTime();
				Date format = null;
				try {
					format = new SimpleDateFormat("yyMMddHHmmss").parse(s.toString());
					dataDay.setRecordTime(new SimpleDateFormat("yyyy-MM-dd").format(format));
					format = new SimpleDateFormat("yyMMddHHmmss").parse(min.toString());
					String string = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(format);
					dataDay.setCreateDtm(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(string));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				dataDay.setAmmeterCode(max1.getcAddr().toString());
				dataDay.setType(max1.getdAddr().toString().indexOf("1") == 0 ? 1 : 2);
				dataDay.setwAddr(0);
				dataDay.setKwh(kwh);
				elecDataDayService.insertData(dataDay);
			}

			aprR.setdAddr(11L);
			AmPhaseRecord max11 = amPhaseRecordService.findMaxData(aprR);
			AmPhaseRecord min11 = amPhaseRecordService.findMinData(aprR);
			if (max11 != null && min11 != null) {
				Double kwh = max11.getKwhTotal() - min11.getKwhTotal();
				dataDay.setDel(0);
				dataDay.setdAddr(max11.getdAddr());
				dataDay.setdType(max11.getdType());
				dataDay.setDevConfCode(date);
				dataDay.setKw(max11.getKw());
				Long s = max11.getMeterTime();
				Long min = min11.getMeterTime();
				Date format = null;
				try {
					format = new SimpleDateFormat("yyMMddHHmmss").parse(s.toString());
					dataDay.setRecordTime(new SimpleDateFormat("yyyy-MM-dd").format(format));
					format = new SimpleDateFormat("yyMMddHHmmss").parse(min.toString());
					String string = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(format);
					dataDay.setCreateDtm(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(string));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				dataDay.setAmmeterCode(max11.getcAddr().toString());
				dataDay.setType(max11.getdAddr().toString().indexOf("1") == 0 ? 1 : 2);
				dataDay.setwAddr(0);
				dataDay.setKwh(kwh);
				elecDataDayService.insertData(dataDay);
			}

			aprR.setdAddr(2L);
			AmPhaseRecord max2 = amPhaseRecordService.findMaxData(aprR);
			AmPhaseRecord min2 = amPhaseRecordService.findMinData(aprR);
			if (max2 != null && min2 != null) {
				Double kwh = max2.getKwhTotal() - min2.getKwhTotal();
				dataDay.setDel(0);
				dataDay.setdAddr(max2.getdAddr());
				dataDay.setdType(max2.getdType());
				dataDay.setDevConfCode(date);
				dataDay.setKw(max2.getKw());
				Long s = max2.getMeterTime();
				Long min = min2.getMeterTime();
				Date format = null;
				try {
					format = new SimpleDateFormat("yyMMddHHmmss").parse(s.toString());
					dataDay.setRecordTime(new SimpleDateFormat("yyyy-MM-dd").format(format));
					format = new SimpleDateFormat("yyMMddHHmmss").parse(min.toString());
					String string = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(format);
					dataDay.setCreateDtm(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(string));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				dataDay.setAmmeterCode(max2.getcAddr().toString());
				dataDay.setType(max2.getdAddr().toString().indexOf("1") == 0 ? 1 : 2);
				dataDay.setwAddr(0);
				dataDay.setKwh(kwh);
				elecDataDayService.insertData(dataDay);
			}
		});
		System.out.println("end");
		return 666+"::::"+date+"::老子天下第一、因为骚。";
	}
}

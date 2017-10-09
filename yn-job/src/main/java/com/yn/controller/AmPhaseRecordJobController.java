package com.yn.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yn.model.Am1Phase;
import com.yn.model.Am3Phase;
import com.yn.model.AmPhaseRecord;
import com.yn.service.AmPhaseRecordService;
import com.yn.service.AmPhaseService;
import com.yn.vo.re.ResultVOUtil;

@Controller
@RequestMapping("/client/aprJob")
public class AmPhaseRecordJobController {
	private Logger logger = Logger.getLogger(AmPhaseRecordJobController.class);

	@Autowired
	private AmPhaseService amPhaseService;

	@Autowired
	private AmPhaseRecordService amPhaseRecordService;

	/** 
	 * 模拟测试。两个数据源。
	 * 
	 * @return
	 */
	@RequestMapping("/getInfo")
	@ResponseBody
	public Object getInfo() {
		// old
		List<Am1Phase> findAll = amPhaseService.findAllAm1Phase();
		logger.info("AmmeterRecord...size():" + findAll.size());
		for (Am1Phase am1Phase : findAll) {
			logger.info("am1Phase.getMeterState():" + am1Phase.getMeterState());
		}
		List<AmPhaseRecord> list = amPhaseRecordService.findAll(new AmPhaseRecord());
		for (AmPhaseRecord amPhaseRecord : list) {
			logger.info("amPhaseRecord.getMeterState():" + amPhaseRecord.getMeterState());
		}
		Object[] objs = new Object[2];
		objs[0] = findAll;
		objs[0] = list;
		return objs;
	}

	/**
	 * 模拟AmPhaseRecordJob类中的collectAmPhaseRecord方法 -->采集amPhase数据
	 * 读旧表，插新表
	 * @return
	 */
	@RequestMapping("/simulationAPRGAndI")
	//@RequestMapping("/job")
	public @ResponseBody Object simulationGAndI() {
		Map<String,Object> jsonResult = new HashMap<String,Object>();
		List<Am1Phase> am1Phases = amPhaseService.findAllAm1Phase();
		List<Am3Phase> am3Phases = amPhaseService.findAllAm3Phase();
		jsonResult.put("am1Phases.size", am1Phases.size());
		jsonResult.put("am3Phases.size", am3Phases.size());
		if (am1Phases.size() > 0) {
			jsonResult.put("am1Phases.get(0).meterTime", am1Phases.get(0).getMeterTime());
			for (Am1Phase am1Phase : am1Phases) {
				AmPhaseRecord amPhaseRecordR = new AmPhaseRecord();
				amPhaseRecordR.setRowId(am1Phase.getRowId());
				amPhaseRecordR.setcAddr(am1Phase.getcAddr());
				amPhaseRecordR.setiAddr(am1Phase.getiAddr());
				amPhaseRecordR.setdAddr(am1Phase.getdAddr());
				amPhaseRecordR.setdType(am1Phase.getdType());
				amPhaseRecordR.setwAddr(am1Phase.getwAddr());
				amPhaseRecordR.setMeterTime(am1Phase.getMeterTime());
				AmPhaseRecord findOne = amPhaseRecordService.findOne(amPhaseRecordR);
				if (findOne == null) {
					AmPhaseRecord amPhaseRecord = new AmPhaseRecord();
					BeanUtils.copyProperties(am1Phase, amPhaseRecord);
					amPhaseRecord.setAmPhaseRecordId(
							"am1Phase" + am1Phase.getMeterTime().toString() + am1Phase.getRowId().toString());
					amPhaseRecordService.save(amPhaseRecord);
				}
			}
		}
		if (am3Phases.size() > 0) {
			jsonResult.put("am3Phases.get(0).meterTime", am3Phases.get(0).getMeterTime());
			for (Am3Phase am3Phase : am3Phases) {
				AmPhaseRecord amPhaseRecordR = new AmPhaseRecord();
				amPhaseRecordR.setRowId(am3Phase.getRowId());
				amPhaseRecordR.setcAddr(am3Phase.getcAddr());
				amPhaseRecordR.setiAddr(am3Phase.getiAddr());
				amPhaseRecordR.setdAddr(am3Phase.getdAddr());
				amPhaseRecordR.setdType(am3Phase.getdType());
				amPhaseRecordR.setwAddr(am3Phase.getwAddr());
				amPhaseRecordR.setMeterTime(am3Phase.getMeterTime());
				AmPhaseRecord findOne = amPhaseRecordService.findOne(amPhaseRecordR);
				if (findOne == null) {
					AmPhaseRecord amPhaseRecord = new AmPhaseRecord();
					BeanUtils.copyProperties(am3Phase, amPhaseRecord);
					amPhaseRecord.setAmPhaseRecordId(
							"am3Phase" + am3Phase.getMeterTime().toString() + am3Phase.getRowId().toString());
					amPhaseRecordService.save(amPhaseRecord);
				}
			}
		}
		return ResultVOUtil.success(jsonResult);
	}

}

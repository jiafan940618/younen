package com.yn.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yn.job.ElecDataMonthJob;
import com.yn.model.ElecDataHour;
import com.yn.model.ElecDataMonth;
import com.yn.model.ElecDataYear;

/**
 * 
    * @ClassName: ElecDataMonthJobService
    * @Description: TODO(处理数据导入，可以按照年，或者年月。)
    * @author lzyqssn
    * @date 2017年10月25日
    *
 */
@Service
public class ElecDataMonthJobService {
	

	@Autowired
	private ElecDataMonthService elecDataMonthService;

	@Autowired
	private ElecDataHourService elecDataHourService;
	
	@Autowired
	private ElecDataYearService elecDataYearService;

	private static Logger logger = Logger.getLogger(ElecDataMonthJob.class);


	/**
	 * 
	    * @Title: guess
	    * @Description: TODO(如上所述。)
	    * @param @param year 指定年<必需>
	    * @param @param month 指定月<非必需，在不等于-1的情况下，视为指定某年某月导入。>
	    * @param @return    参数
	    * @return Map<String,Object>    返回类型
	    * @throws
	 */
	public Map<String, Object> guess(int year, int month) {
		String recordTime = "";
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		List<ElecDataHour> data = null;
		if (month == -1) {
			logger.info("执行按年导入。");
			data = elecDataHourService.findAllDataByMonthOrYear(1, year, -1);
			recordTime = String.valueOf(year);
			jsonResult.put("ElecDataHour.data.size::", data.size());
			logger.info(year+"年共有记录："+data.size()+"条。");
			for (ElecDataHour elecDataHour : data) {
				String ammeterCode = elecDataHour.getAmmeterCode()==null?"":elecDataHour.getAmmeterCode();
				ElecDataYear elecDataYear = new ElecDataYear();
				elecDataYear.setRecordTime(recordTime);
				elecDataYear.setAmmeterCode(ammeterCode);
				elecDataYear.setdAddr(elecDataHour.getdAddr()==null?0:elecDataHour.getdAddr().intValue());
				List<ElecDataYear> condition = elecDataYearService.findByCondition(elecDataYear);
				Double totalKw = 0d;
				Double totalKwh = 0d;
				if (condition.size() > 0) {
					logger.info("开始修改，共有："+condition.size()+"条。");
					for (ElecDataYear elecDataYear2 : condition) {
						Integer dAddr = elecDataYear2.getdAddr();
						CharSequence subSequence = dAddr.toString().subSequence(0, 1);
						if (subSequence.equals("1")) {
							elecDataYear2.setType(1);// 用电
						} else if (subSequence.equals("2")) {
							elecDataYear2.setType(2);// 发电
						}
						totalKw += elecDataHour.getKw().doubleValue() + elecDataYear2.getKw().doubleValue();
						totalKwh += elecDataHour.getKwh().doubleValue() + elecDataYear2.getKwh().doubleValue();
					
					}
					elecDataYear.setKw(BigDecimal.valueOf(totalKw));
					elecDataYear.setKwh(BigDecimal.valueOf(totalKwh));
					boolean falg = elecDataYearService.updateByExampleSelective(elecDataYear);
					if (falg)
						jsonResult.put(elecDataYear.getAmmeterCode(), "修改成功！");
					else
						jsonResult.put(elecDataYear.getAmmeterCode(), "修改失败或异常！");
					logger.info("修改执行完成。");
				} else {
					logger.info("开始新增。");
					Long dAddr = elecDataHour.getdAddr();
					CharSequence subSequence = dAddr.toString().subSequence(0, 1);
					ElecDataYear edy = new ElecDataYear();
					edy.setAmmeterCode(ammeterCode);
					edy.setRecordTime(recordTime);
					edy.setKw(BigDecimal.valueOf(elecDataHour.getKw()));
					edy.setKwh(BigDecimal.valueOf(elecDataHour.getKwh()));
					edy.setdAddr(elecDataHour.getdAddr().intValue());
					edy.setDevConfCode(elecDataHour.getDevConfCode());
					edy.setdType(elecDataHour.getdType());
					if (subSequence.equals("1")) {
						edy.setType(1);// 用电
					} else if (subSequence.equals("2")) {
						edy.setType(2);// 发电
					}
					edy.setwAddr(elecDataHour.getwAddr());
					boolean b = elecDataYearService.saveByMapper(edy);
					if (b)
						jsonResult.put(elecDataYear.getAmmeterCode(), "保存成功！");
					else
						jsonResult.put(elecDataYear.getAmmeterCode(), "保存失败或异常！");
					logger.info("新增执行完成。");
				}
			}
		} else {
			logger.info("执行按月导入。");
			data = elecDataHourService.findAllDataByMonthOrYear(1, year, month);
			recordTime = String.valueOf(year) + "-" + String.valueOf(month);
			jsonResult.put("ElecDataHour.data.size::", data.size());
			logger.info(year+"年"+month+"月共有记录："+data.size()+"条。");
			for (ElecDataHour elecDataHour : data) {
				String ammeterCode = elecDataHour.getAmmeterCode() == null ? "" : elecDataHour.getAmmeterCode();
				ElecDataMonth elecDataMonth = new ElecDataMonth();
				elecDataMonth.setRecordTime(recordTime);
				elecDataMonth.setAmmeterCode(ammeterCode);
				elecDataMonth.setdAddr(elecDataHour.getdAddr() == null ? 0 : elecDataHour.getdAddr().intValue());
				List<ElecDataMonth> condition = elecDataMonthService.findByCondition(elecDataMonth);
				Double totalKw = 0d;
				Double totalKwh = 0d;
				if (condition.size() > 0) {
					logger.info("开始修改，共有："+condition.size()+"条。");
					for (ElecDataMonth elecDataMonth2 : condition) {
						Integer dAddr = elecDataMonth2.getdAddr();
						CharSequence subSequence = dAddr.toString().subSequence(0, 1);
						if (subSequence.equals("1")) {
							elecDataMonth.setType(1);// 用电
						} else if (subSequence.equals("2")) {
							elecDataMonth.setType(2);// 发电
						}
						totalKw += elecDataHour.getKw().doubleValue() + elecDataMonth2.getKw().doubleValue();
						totalKwh += elecDataHour.getKwh().doubleValue() + elecDataMonth2.getKwh().doubleValue();
					}
					elecDataMonth.setKw(BigDecimal.valueOf(totalKw));
					elecDataMonth.setKwh(BigDecimal.valueOf(totalKwh));
					boolean falg = elecDataMonthService.updateByExampleSelective(elecDataMonth);
					if (falg)
						jsonResult.put(elecDataMonth.getAmmeterCode(), "修改成功！");
					else
						jsonResult.put(elecDataMonth.getAmmeterCode(), "修改失败或异常！");
					logger.info("修改完成。");
				} else {
					logger.info("开始新增。");
					Long dAddr = elecDataHour.getdAddr();
					CharSequence subSequence = dAddr.toString().subSequence(0, 1);
					ElecDataMonth edm = new ElecDataMonth();
					edm.setAmmeterCode(ammeterCode);
					edm.setRecordTime(recordTime);
					edm.setKw(BigDecimal.valueOf(elecDataHour.getKw()));
					edm.setKwh(BigDecimal.valueOf(elecDataHour.getKwh()));
					edm.setdAddr(elecDataHour.getdAddr().intValue());
					edm.setDevConfCode(elecDataHour.getDevConfCode());
					edm.setdType(elecDataHour.getdType());
					if (subSequence.equals("1")) {
						edm.setType(1);// 用电
					} else if (subSequence.equals("2")) {
						edm.setType(2);// 发电
					}
					edm.setwAddr(elecDataHour.getwAddr());
					boolean b = elecDataMonthService.saveByMapper(edm);
					if (b)
						jsonResult.put(elecDataMonth.getAmmeterCode(), "保存成功！");
					else
						jsonResult.put(elecDataMonth.getAmmeterCode(), "保存失败或异常！");
					logger.info("新增完成。");
				}
			}
		}
		return jsonResult;
	}
}

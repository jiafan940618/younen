package com.yn.controller;

import java.math.BigDecimal;
import java.util.List;

import org.soofa.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.model.ElecDataHour;
import com.yn.model.ElecDataMonth;
import com.yn.model.ElecDataYear;
import com.yn.service.ElecDataHourService;
import com.yn.service.ElecDataMonthService;
import com.yn.service.ElecDataYearService;
import com.yn.vo.re.ResultVOUtil;

/**
 * 
    * @ClassName: ElecDataController
    * @Description: TODO(导入年、导入月的数据。)
    * @author lzyqssn
    * @date 2017年11月1日
    *
 */
@RestController
@RequestMapping("/client/edc")
public class ElecDataController {

	@Autowired
	private ElecDataYearService elecDataYearService;
	@Autowired
	private ElecDataMonthService elecDataMonthService;
	@Autowired
	private ElecDataHourService elecDataHourService;
	private static Logger LOGGER = Logger.getLogger(ElecDataController.class);

	/**
	 * 
	    * @Title: yearJob 
	    * @Description: TODO(导入年的数据。)
	    * @param @return    参数
	    * @return Object    返回类型
	    * @throws
	 */
	@RequestMapping("/importYearData/{year}")
	@ResponseBody
	public Object yearJob(@PathVariable("year") String year) {
		String date = year;
		List<ElecDataHour> data = elecDataHourService.findAllDataByMonthOrYear4C(date);
		for (ElecDataHour elecDataHour : data) {
			String ammeterCode = elecDataHour.getAmmeterCode() == null ? "" : elecDataHour.getAmmeterCode();
			ElecDataYear elecDataYear = new ElecDataYear();
			elecDataYear.setRecordTime(date);
			elecDataYear.setAmmeterCode(ammeterCode);
			elecDataYear.setdAddr(elecDataHour.getdAddr() == null ? 0 : elecDataHour.getdAddr().intValue());
			List<ElecDataYear> condition = elecDataYearService.findByCondition(elecDataYear);
			Double totalKw = 0d;
			Double totalKwh = 0d;
			if (condition.size() > 0) {
				for (ElecDataYear elecDataYear2 : condition) {
					Integer dAddr = elecDataYear2.getdAddr();
					CharSequence subSequence = dAddr.toString().subSequence(0, 1);
					elecDataYear2.setType(1);
					if (subSequence.equals("1")) {
						elecDataYear2.setType(1);// 用电
					} else if (subSequence.equals("2")) {
						elecDataYear2.setType(2);// 发电
					}
					totalKw += elecDataHour.getKw().doubleValue();// + elecDataYear2.getKw().doubleValue();
					totalKwh += elecDataHour.getKw().doubleValue() + elecDataYear2.getKwh().doubleValue();

				}
				elecDataYear.setKw(BigDecimal.valueOf(totalKw));
				elecDataYear.setKwh(BigDecimal.valueOf(totalKwh));
				boolean falg = elecDataYearService.updateByExampleSelective(elecDataYear);
				if (falg)
					LOGGER.info("ElecDataYear修改成功！");
				else
					LOGGER.info("ElecDataYear修改失败或异常！");
			} else {
				Long dAddr = elecDataHour.getdAddr();
				CharSequence subSequence = "";
				if(dAddr!=null){
					subSequence = dAddr.toString().subSequence(0, 1);
				}
				ElecDataYear edy = new ElecDataYear();
				edy.setAmmeterCode(ammeterCode);
				edy.setRecordTime(date);
				edy.setKw(BigDecimal.valueOf(elecDataHour.getKw()));
				edy.setKwh(BigDecimal.valueOf(elecDataHour.getKwh()));
				edy.setdAddr(elecDataHour.getdAddr()==null?1:elecDataHour.getdAddr().intValue());
				edy.setDevConfCode(elecDataHour.getDevConfCode());
				edy.setdType(elecDataHour.getdType());
				edy.setType(1);
				if (subSequence.equals("1")) {
					edy.setType(1);// 用电
				} else if (subSequence.equals("2")) {
					edy.setType(2);// 发电
				}
				edy.setwAddr(elecDataHour.getwAddr());
				boolean b = elecDataYearService.saveByMapper(edy);
				if (b)
					LOGGER.info("elecDataYear保存成功！");
				else
					LOGGER.info("ElecDataYear保存失败或异常！");
			}
		}

		return ResultVOUtil.success();
	}

	/**
	 * 
	    * @Title: monthJob 
	    * @Description: TODO(导入月的数据。)
	    * @param @return    参数
	    * @return Object    返回类型
	    * @throws
	 */
	@RequestMapping("/importMonthData/{year}/{month}")
	@ResponseBody
	public Object monthJob(@PathVariable("year") String year, @PathVariable("month") String month) {
		String date = new StringBuffer(year).append("-").append(month).toString();
		List<ElecDataHour> data = elecDataHourService.findAllDataByMonthOrYear4C(date);
		for (ElecDataHour elecDataHour : data) {
			String ammeterCode = elecDataHour.getAmmeterCode() == null ? "" : elecDataHour.getAmmeterCode();
			ElecDataMonth elecDataMonth = new ElecDataMonth();
			elecDataMonth.setRecordTime(date);
			elecDataMonth.setAmmeterCode(ammeterCode);
			elecDataMonth.setdAddr(elecDataHour.getdAddr() == null ? 0 : elecDataHour.getdAddr().intValue());
			Double totalKw = 0d;
			Double totalKwh = 0d;
			List<ElecDataMonth> condition = elecDataMonthService.findByCondition(elecDataMonth);
			if (condition.size() > 0) {
				for (ElecDataMonth elecDataMonth2 : condition) {
					Integer dAddr = elecDataMonth2.getdAddr();
					CharSequence subSequence = dAddr.toString().subSequence(0, 1);
					elecDataMonth.setType(1);
					if (subSequence.equals("1")) {
						elecDataMonth.setType(1);// 用电
					} else if (subSequence.equals("2")) {
						elecDataMonth.setType(2);// 发电
					}
					totalKw += elecDataHour.getKw().doubleValue();// + elecDataMonth2.getKw().doubleValue();
					totalKwh += elecDataHour.getKw().doubleValue() + elecDataMonth2.getKwh().doubleValue();
				}
				elecDataMonth.setKw(BigDecimal.valueOf(totalKw));
				elecDataMonth.setKwh(BigDecimal.valueOf(totalKwh));
				boolean falg = elecDataMonthService.updateByExampleSelective(elecDataMonth);
				if (falg)
					LOGGER.info("elecDataMonth修改成功！");
				else
					LOGGER.info("elecDataMonth修改失败或异常！");
			} else {
				Long dAddr = elecDataHour.getdAddr();
				CharSequence subSequence = "";
				if(dAddr!=null){
					subSequence = dAddr.toString().subSequence(0, 1);
				}
				ElecDataMonth edm = new ElecDataMonth();
				edm.setAmmeterCode(ammeterCode);
				edm.setRecordTime(date);
				edm.setKw(BigDecimal.valueOf(elecDataHour.getKw()));
				edm.setKwh(BigDecimal.valueOf(elecDataHour.getKwh()));
				edm.setdAddr(elecDataHour.getdAddr()==null?1:elecDataHour.getdAddr().intValue());
				edm.setDevConfCode(elecDataHour.getDevConfCode());
				edm.setdType(elecDataHour.getdType());
				edm.setwAddr(elecDataHour.getwAddr());
				edm.setType(1);
				if (subSequence.equals("1")) {
					edm.setType(1);// 用电
				} else if (subSequence.equals("2")) {
					edm.setType(2);// 发电
				} 
				edm.setwAddr(elecDataHour.getwAddr());
				boolean b = elecDataMonthService.saveByMapper(edm);
				if (b)
					LOGGER.info("elecDataMonth新增成功！");
				else
					LOGGER.info("elecDataMonth新增失败或异常！");
			}
		}
		return ResultVOUtil.success();
	}
}

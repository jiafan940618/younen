package com.yn.job;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yn.model.ElecDataHour;
import com.yn.model.ElecDataMonth;
import com.yn.model.ElecDataYear;
import com.yn.service.ElecDataHourService;
import com.yn.service.ElecDataYearService;

/**
 * 
    * @ClassName: ElecDataYearJob
    * @Description: TODO(统计去年用电发电)
    * @author lzyqssn
    * @date 2017年10月24日
    *
 */
@Component
public class ElecDataYearJob {

	@Autowired
	private ElecDataYearService elecDataYearService;

	@Autowired
	private ElecDataHourService elecDataHourService;

	private static Logger logger = Logger.getLogger(ElecDataYearJob.class);

	/**
	 * 
	    * @Title: job 每月1号执行、
	    * @Description: TODO(统计一年的发电、用电量)
	    * @param     参数
	    * @return void    返回类型
	    * @throws
	 */
	@Scheduled(cron = "0 0 0 1 * ? ")
	private void job() {
		String recordTime = new SimpleDateFormat("yyyy").format(new Date());
		List<ElecDataHour> data = elecDataHourService.findAllDataByMonthOrYear(-1);
		for (ElecDataHour elecDataHour : data) {
			String ammeterCode = elecDataHour.getAmmeterCode()==null?"":elecDataHour.getAmmeterCode();
			ElecDataYear elecDataYear = new ElecDataYear();
			elecDataYear.setRecordTime(recordTime);
			elecDataYear.setAmmeterCode(ammeterCode);
			elecDataYear.setdAddr(elecDataHour.getdAddr().intValue());
			List<ElecDataYear> condition = elecDataYearService.findByCondition(elecDataYear);
			Double totalKw = 0d;
			Double totalKwh = 0d;
			if (condition.size() > 0) {
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
					logger.info("修改成功！");
				else
					logger.info("修改异常！");
			} else {
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
					logger.info("保存成功！");
				else
					logger.info("报错异常！");
			}
		}

	}
}

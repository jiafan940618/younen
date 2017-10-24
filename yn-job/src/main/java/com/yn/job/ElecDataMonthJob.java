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
import com.yn.service.ElecDataHourService;
import com.yn.service.ElecDataMonthService;

/**
 * 
    * @ClassName: ElecDataMonthJob
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author lzyqssn
    * @date 2017年10月24日
    *
 */
@Component
public class ElecDataMonthJob {

	@Autowired
	private ElecDataMonthService elecDataMonthService;

	@Autowired
	private ElecDataHourService elecDataHourService;

	private static Logger logger = Logger.getLogger(ElecDataMonthJob.class);

	/**
	 * 
	    * @Title: job 每天1点执行
	    * @Description: TODO(统计上一个月的发、用电)
	    * @param     参数
	    * @return void    返回类型
	    * @throws
	 */
	@Scheduled(cron = "0 0 1 * * ? ")
	private void job() {
		String recordTime = new SimpleDateFormat("yyyy-MM").format(new Date());
		List<ElecDataHour> data = elecDataHourService.findAllDataByMonthOrYear(1);
		for (ElecDataHour elecDataHour : data) {
			String ammeterCode = elecDataHour.getAmmeterCode()==null?"":elecDataHour.getAmmeterCode();
			ElecDataMonth elecDataMonth = new ElecDataMonth();
			elecDataMonth.setRecordTime(recordTime);
			elecDataMonth.setAmmeterCode(ammeterCode);
			List<ElecDataMonth> condition = elecDataMonthService.findByCondition(elecDataMonth);
			Double totalKw = 0d;
			Double totalKwh = 0d;
			if (condition.size() > 0) {
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
					logger.info("修改成功！");
				else
					logger.info("修改异常！");
			} else {
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
					logger.info("保存成功！");
				else
					logger.info("报错异常！");
			}
		}
	}
}

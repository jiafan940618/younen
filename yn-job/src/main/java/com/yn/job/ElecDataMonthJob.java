package com.yn.job;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

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
/**
 * 全部统一为查询hour、day表。因为数据量大的情况下，发电量大的情况下，每月每年没日每小时的数据都会不准确，会存在偏差。
    * @ClassName: ElecDataMonthJob
    * @Description: TODO(已抛弃)
    * @author lzyqssn
    * @date 2017年11月14日
    *
 */
//@Component
public class ElecDataMonthJob {

	@Autowired
	private ElecDataMonthService elecDataMonthService;

	@Autowired
	private ElecDataHourService elecDataHourService;


	private static PrintStream mytxt;
	private static PrintStream out;

	public ElecDataMonthJob() {
		try {
			mytxt = new PrintStream(new FileOutputStream(new File("/opt/ynJob/log/ElecDataMonthJob.log"),true));
//			mytxt = new PrintStream("./elecDataMonthJobLog.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	    * @Title: job 每天1点执行
	    * @Description: TODO(统计上一个月的发、用电)
	    * @param     参数
	    * @return void    返回类型
	    * @throws
	 */
//	@Scheduled(cron = "0 0 1 * * ? ")
	@SuppressWarnings("unused")
	private void job() {
		// 设置日志文件输出路径。
		out = System.out;
		System.setOut(mytxt);
		System.out.println("ElecDataMonthJob文档执行的日期是：" + new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
		String recordTime = new SimpleDateFormat("yyyy-MM").format(new Date());
		List<ElecDataHour> data = elecDataHourService.findAllDataByMonthOrYear(1, -1, -1);
		for (ElecDataHour elecDataHour : data) {
			String ammeterCode = elecDataHour.getAmmeterCode();
			ElecDataMonth elecDataMonth = new ElecDataMonth();
			elecDataMonth.setRecordTime(recordTime);
			elecDataMonth.setAmmeterCode(ammeterCode);
			elecDataMonth.setdAddr(elecDataHour.getdAddr().intValue());
			Double totalKw = 0d;
			Double totalKwh = 0d;
			List<ElecDataMonth> condition = elecDataMonthService.findByCondition(elecDataMonth);
			if (condition.size() > 0) {
				for (ElecDataMonth elecDataMonth2 : condition) {
					Integer dAddr = elecDataMonth2.getdAddr();
					CharSequence subSequence = dAddr.toString().subSequence(0, 1);
					if (subSequence.equals("1")) {
						elecDataMonth.setType(1);// 发电
					} else if (subSequence.equals("2")) {
						elecDataMonth.setType(2);// 用电
					}
					totalKw += elecDataHour.getKw().doubleValue();// + elecDataMonth2.getKw().doubleValue();
					totalKwh = elecDataHour.getKw().doubleValue() + elecDataMonth2.getKwh().doubleValue();
				}
				elecDataMonth.setKw(BigDecimal.valueOf(totalKw));
				elecDataMonth.setKwh(BigDecimal.valueOf(totalKwh));
				boolean falg = elecDataMonthService.updateByExampleSelective(elecDataMonth);
				if (falg)
					System.out.println("ElecDataMonthJob--> am1Phase::" + elecDataMonth.getAmmeterCode() + "修改成功！-->"
							+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
				else
					System.out.println("ElecDataMonthJob--> am1Phase::" + elecDataMonth.getAmmeterCode() + "修改失败或异常！-->"
							+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
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
				edm.setwAddr(0);
				if (subSequence.equals("1")) {
					edm.setType(1);// 发电
				} else if (subSequence.equals("2")) {
					edm.setType(2);// 用电
				}
				edm.setwAddr(0);
				boolean b = elecDataMonthService.saveByMapper(edm);
				if (b)
					System.out.println("ElecDataMonthJob--> am1Phase::" + edm.getAmmeterCode() + "新增成功！-->"
							+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
				else
					System.out.println("ElecDataMonthJob--> am1Phase::" + edm.getAmmeterCode() + "新增失败或异常！-->"
							+ new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
			}
		}
		System.setOut(out);
		System.out.println("ElecDataMonthJob日志保存完毕。");
	}
}

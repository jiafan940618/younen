package com.yn.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yn.controller.AmPhaseRecordJobController;
import com.yn.dao.TaskExecuteRecordMapper;
import com.yn.model.TaskExecuteRecord;
import com.yn.model.TaskExecuteRecordExample;

/**
 * 
 * @ClassName: RollBack4Exception
 * @Description: TODO(在项目异常结束后再次的时候（项目每次启动），用于查找数据库中最后一次记录的时间和现在所差时间，根据计算，回滚数据。)
 * @author lzyqssn
 * @date 2017年10月20日
 *
 */
@Service
public class RollBack4Exception {

	@Autowired
	private TaskExecuteRecordMapper taskExecuteRecordMapper;

	@Autowired
	private AmPhaseRecordJobController amPhaseRecordJobController;

	@SuppressWarnings("deprecation")
	public void checkException() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
		Map<String, Object> result = new HashMap<String, Object>();
		// 当前时间
		Calendar cal = Calendar.getInstance();
		int nowYear = cal.get(Calendar.YEAR);
		int nowMonth = cal.get(Calendar.MONTH) + 1;
		int nowDay = cal.get(Calendar.DAY_OF_MONTH);
		Date nowDate = sdf.parse(sdf.format(new Date()));
		// Date nowDate = sdf.parse("2017_01_02");
		cal.setTime(nowDate);
		// 数据库中最大的时间。
		// TaskExecuteRecord errorRecord =
		// TaskExecuteRecordMapper.findByEndDate();
		TaskExecuteRecordExample recordExample = new TaskExecuteRecordExample();
		recordExample.setOrderByClause(" end_date desc");
		List<TaskExecuteRecord> example = taskExecuteRecordMapper.selectByExample(recordExample);
		if(example.size()<1){
			return;
		}
		String jobName = example.get(0).getJobName();
		if (jobName.equals("AmPhaseRecordJob")) {
			Date endDate = example.get(0).getEndDate();
			// String endT = "2017_10_19";
			// Date endDate = sdf.parse(endT);
			Calendar c = Calendar.getInstance();
			c.setTime(endDate);

			// 计算时差、相差n天、
			int difference = 0;
			long now = sdf.parse(sdf.format(nowDate)).getTime();
			long end = sdf.parse(sdf.format(endDate)).getTime();
			StringBuffer sb = new StringBuffer();
			StringBuffer sbd = new StringBuffer();
			if (end < now) {// 有异常。当前时间大于数据库最后一次插入的时间、。
				// 只计算日，不计算年月，如果真的都已经相差这么久了都没发现。你懂的、所以只计算年前月前
				difference = (int) ((end - now) / (1000 * 60 * 60 * 24)) * -1;
				System.out
						.println(nowDate.toLocaleString() + "与" + endDate.toLocaleString() + "相差" + difference + "天。");
				// 从结束的那天开始。
				int startDate = endDate.getDate();
				Calendar ca = Calendar.getInstance();
				ca.set(c.get(Calendar.YEAR), endDate.getMonth(), 0);
				int dayOfMonth = ca.get(Calendar.DAY_OF_MONTH);
				int index = 0;
				for (int i = startDate; i <= (startDate + difference); i++) {
					if (endDate.getMonth() + 1 == 12 && i == 31 && cal.get(Calendar.YEAR) > c.get(Calendar.YEAR)) {// 2016_12_31如果刚好存在年差。
						sb.append(c.get(Calendar.YEAR)).append("_").append(12).append("_").append(31);
						sbd.append(c.get(Calendar.YEAR)).append(12).append(31);
					} else {
						if (i > dayOfMonth) {// 9月31，，应该是10月1 存在月差
							index++;
							if (i == (startDate + difference)) {
								Calendar calendar = Calendar.getInstance();
								int hour = calendar.get(Calendar.HOUR_OF_DAY);
								int mi = calendar.get(Calendar.MINUTE);
								int s = calendar.get(Calendar.SECOND);
								sb.append(c.get(Calendar.YEAR) + 1).append("_")
										.append((c.get(Calendar.MONTH) + 2) >= 13 ? "01" : c.get(Calendar.MONTH) + 2)
										.append("_").append(index <= 9 ? "0" + index : index)
										.append(hour <= 9 ? "0" + hour : hour).append(mi <= 9 ? "0" + mi : mi)
										.append(s <= 9 ? "0" + s : s);
								sbd.append(c.get(Calendar.YEAR) + 1)
										.append((c.get(Calendar.MONTH) + 2) >= 13 ? "01" : c.get(Calendar.MONTH) + 2)
										.append(index <= 9 ? "0" + index : index).append(hour <= 9 ? "0" + hour : hour)
										.append(mi <= 9 ? "0" + mi : mi).append(s <= 9 ? "0" + s : s);
							} else {
								sb.append(c.get(Calendar.YEAR) + 1).append("_")
										.append((c.get(Calendar.MONTH) + 2) >= 13 ? "01" : c.get(Calendar.MONTH) + 2)
										.append("_").append(index <= 9 ? "0" + index : index);
								sbd.append(c.get(Calendar.YEAR) + 1)
										.append((c.get(Calendar.MONTH) + 2) >= 13 ? "01" : c.get(Calendar.MONTH) + 2)
										.append(index <= 9 ? "0" + index : index);
							}
						} else {
							sb.append(c.get(Calendar.YEAR))
									.append("_").append((c.get(Calendar.MONTH) + 1) <= 9
											? "0" + c.get(Calendar.MONTH) + 1 : c.get(Calendar.MONTH) + 1)
									.append("_").append(i <= 9 ? "0" + i : i);
							sbd.append(c.get(Calendar.YEAR)).append((c.get(Calendar.MONTH) + 1) <= 9
									? "0" + c.get(Calendar.MONTH) + 1 : c.get(Calendar.MONTH) + 1)
									.append(i <= 9 ? "0" + i : i);
						}
					}
					System.out.println(sb.toString());
					System.out.println(sbd.toString());
					Map<String, Object> job = amPhaseRecordJobController.job(sbd.toString(), sb.toString());
					result.put(job.hashCode() + "::-index-::" + index, job);
					// 清空数据。
					sb = new StringBuffer();
					sbd = new StringBuffer();
				}
				TaskExecuteRecord taskExecuteRecord = new TaskExecuteRecord();
				taskExecuteRecord.setEndDate(example.get(0).getEndDate());
				taskExecuteRecord.setId(example.get(0).getId());
				taskExecuteRecord.setStatus(example.get(0).getStatus());
				taskExecuteRecordMapper.updateByPrimaryKey(taskExecuteRecord);
			} else if (end == now) {
				// 时差没问题就看看是不是异常结束（是否有异常信息）
				// 没有就是说在当天异常结束了。
				String errorInfo = example.get(0).getStatus();
				System.out.println(errorInfo);
				if (errorInfo.equals("失败") || errorInfo.length() > 2) {// 数据库默认值是失败的。有可能是异常信息。
					// c.get(Calendar.YEAR)
					Calendar calendar = Calendar.getInstance();
					int y, m, d, h, mi, s;
					y = calendar.get(Calendar.YEAR);
					m = calendar.get(Calendar.MONTH) + 1;
					d = calendar.get(Calendar.DATE);
					h = calendar.get(Calendar.HOUR_OF_DAY);
					mi = calendar.get(Calendar.MINUTE);
					s = calendar.get(Calendar.SECOND);
					sb.append(c.get(Calendar.YEAR)).append("_").append((m) <= 9 ? "0" + m : m).append("_")
							.append(d <= 9 ? "0" + d : d);
					sbd.append(c.get(Calendar.YEAR)).append((m) <= 9 ? "0" + m : m).append(d <= 9 ? "0" + d : d)
							.append(h <= 9 ? "0" + h : h).append(mi <= 9 ? "0" + mi : mi);
					System.out.println(sbd.toString());
					System.out.println(sb.toString());
					Map<String, Object> job = amPhaseRecordJobController.job(sbd.toString(), sb.toString());
					result.put(job.hashCode() + "::-DAY-::" + d, job);
					TaskExecuteRecord taskExecuteRecord = new TaskExecuteRecord();
					taskExecuteRecord.setEndDate(example.get(0).getEndDate());
					taskExecuteRecord.setId(example.get(0).getId());
					taskExecuteRecord.setStatus(example.get(0).getStatus());
					taskExecuteRecordMapper.updateByPrimaryKey(taskExecuteRecord);
				}
			}
		} else {
		}
	}
}

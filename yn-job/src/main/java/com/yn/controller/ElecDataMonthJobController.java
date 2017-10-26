package com.yn.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yn.job.ElecDataMonthJob;
import com.yn.model.ElecDataHour;
import com.yn.model.ElecDataMonth;
import com.yn.service.ElecDataHourService;
import com.yn.service.ElecDataMonthJobService;
import com.yn.service.ElecDataMonthService;
import com.yn.vo.re.ResultVOUtil;

/**
 * 
    * @ClassName: ElecDataMonthJobController
    * @Description: TODO(按指定年月、年导入月份的数据、)
    * @author lzyqssn
    * @date 2017年10月25日
    *
 */
@RestController
@RequestMapping("/client/edmjc")
public class ElecDataMonthJobController {

	@Autowired
	private ElecDataMonthJobService elecDataMonthJobService;

	/**
	 * 
	    * @Title: importData4Month
	    * @Description: TODO(如上所述。)
	    * @param @param year 指定年<必需>
	    * @param @param month 指定月<非必需，在不等于-1的情况下，视为指定某年某月导入。>
	    * @param @return    参数
	    * @return Object    返回类型
	    * http://localhost:40403/client/edmjc/importData4Month/2016
	    * http://localhost:40403/client/edmjc/importData4Month/2016?month=12
	    * @throws
	 */
	@RequestMapping("/importData4Month/{year}")
	public Object importData4Month(@PathVariable("year") int year,
			@RequestParam(value = "month", required = false, defaultValue = "-1") int month) {
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		jsonResult = elecDataMonthJobService.guess(year, month);
		return ResultVOUtil.success(jsonResult);
	}

}

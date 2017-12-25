package com.yn.controller;

import java.text.ParseException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yn.service.ElecDataDayService;
import com.yn.vo.re.ResultVOUtil;

/**
 * 
    * @ClassName: StatisticsDataController
    * @Description: TODO(用于统计数据的)
    * @author lzyqssn
    * @date 2017年12月22日
    *
 */
@Controller
@RequestMapping("/job/sdc")
public class StatisticsDataController {

	@Autowired
	private ElecDataDayService elecDataDayService;
	private static final Logger LOGGER = Logger.getLogger(StatisticsDataController.class); 

	/**
	 * 
	    * @Title: statisticsData4Quarter
	    * @Description: TODO(输入起始时间、结束时间以及用户或者电表码，统计包头包尾的累计发电量-->用户名和电表码只能二选一，都填默认选择用户名)
	    * @param @param startDate yyyy-MM-dd
	    * @param @param endDate yyyy-MM-dd
	    * @param @param userName
	    * @param @param ammeterCode
	    * @param @return    参数
	    * @return Object    返回类型
	    * @throws
	 */
	@RequestMapping(value = "/sd4q", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object statisticsData4Quarter(String startDate, String endDate,
			@RequestParam(value = "userName", required = false, defaultValue = "-1") String userName,
			@RequestParam(value = "ammeterCode", required = false, defaultValue = "-1") String ammeterCode) {
		
		if(!userName.equals("-1")) {
			try {
				return ResultVOUtil.success(elecDataDayService.statistics4youNengData(startDate, endDate, userName, null));
			} catch (ParseException e) {
				LOGGER.error("时间转换异常");
				e.printStackTrace();
			}
		}else if(ammeterCode.equals("-1")) {
			try {
				return ResultVOUtil.success(elecDataDayService.statistics4youNengData(startDate, endDate, null, ammeterCode));
			} catch (ParseException e) {
				LOGGER.error("时间转换异常");
				e.printStackTrace();
			}
		}

		return null;
	}

}

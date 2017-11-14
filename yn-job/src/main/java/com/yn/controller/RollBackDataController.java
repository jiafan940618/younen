package com.yn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yn.service.AmPhaseRecordService;
import com.yn.vo.re.ResultVOUtil;

/**
 * 
 * @author lzyqssn 回滚数据（在某一个job在执行的时候，突然中断后的操作） 先清空当天的数据，再将当天的数据重新插入
 *         2017年10月10日-下午10:24:49
 */
@Controller
@RequestMapping("/client/rbd")
public class RollBackDataController {

	@Autowired
	private AmPhaseRecordService amPhaseRecordService;

	@RequestMapping("deleteAmPhaseRecordByDate")
	public @ResponseBody Object deleteAmPhaseRecordByDate(String date) {
		boolean flag = amPhaseRecordService.deleteAmPhaseRecordById(date);
		return ResultVOUtil.success(flag);
	}

}

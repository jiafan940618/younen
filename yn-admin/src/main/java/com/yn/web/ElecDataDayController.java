package com.yn.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.model.ElecDataDay;
import com.yn.service.ElecDataDayService;
import com.yn.utils.BeanCopy;
import com.yn.vo.TemStationYearVo;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/server/temStationYear")
public class ElecDataDayController {
    @Autowired
    ElecDataDayService elecDataDayService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
    	ElecDataDay findOne = elecDataDayService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody TemStationYearVo temStationYearVo) {
    	ElecDataDay temStationYear = new ElecDataDay();
        BeanCopy.copyProperties(temStationYearVo, temStationYear);
        elecDataDayService.save(temStationYear);
        return ResultVOUtil.success(temStationYear);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
    	elecDataDayService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(TemStationYearVo temStationYearVo) {
    	ElecDataDay temStationYear = new ElecDataDay();
        BeanCopy.copyProperties(temStationYearVo, temStationYear);
        ElecDataDay findOne = elecDataDayService.findOne(temStationYear);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(TemStationYearVo temStationYearVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
    	ElecDataDay temStationYear = new ElecDataDay();
        BeanCopy.copyProperties(temStationYearVo, temStationYear);
        Page<ElecDataDay> findAll = elecDataDayService.findAll(temStationYear, pageable);
        return ResultVOUtil.success(findAll);
    }
    
    /**
	 * 用电/发电统计
	 */
	@RequestMapping(value = "/workUseCount", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object workUseCount(Long stationId, Integer type) {

		Map<String, Object> workUseCount = new HashMap<>();

		workUseCount = elecDataDayService.workUseCountList(stationId, type);

		return ResultVOUtil.success(workUseCount);
	}
}
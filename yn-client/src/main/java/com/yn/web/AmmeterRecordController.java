package com.yn.web;


import com.yn.vo.re.ResultVOUtil;

import java.util.HashMap;
import java.util.List;
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

import com.github.pagehelper.PageHelper;
import com.yn.model.AmmeterRecord;
import com.yn.model.ElecDataDay;
import com.yn.service.AmmeterRecordService;
import com.yn.utils.BeanCopy;
import com.yn.utils.PageInfo;
import com.yn.vo.AmmeterRecordVo;
import com.yn.vo.ElecDataDayVo;

@RestController
@RequestMapping("/client/ammeterRecord")
public class AmmeterRecordController {
    @Autowired
    AmmeterRecordService ammeterRecordService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        AmmeterRecord findOne = ammeterRecordService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody AmmeterRecordVo ammeterRecordVo) {
        AmmeterRecord ammeterRecord = new AmmeterRecord();
        BeanCopy.copyProperties(ammeterRecordVo, ammeterRecord);
        ammeterRecordService.save(ammeterRecord);
        return ResultVOUtil.success(ammeterRecord);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        ammeterRecordService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(AmmeterRecordVo ammeterRecordVo) {
        AmmeterRecord ammeterRecord = new AmmeterRecord();
        BeanCopy.copyProperties(ammeterRecordVo, ammeterRecord);
        AmmeterRecord findOne = ammeterRecordService.findOne(ammeterRecord);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(AmmeterRecordVo ammeterRecordVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        AmmeterRecord ammeterRecord = new AmmeterRecord();
        BeanCopy.copyProperties(ammeterRecordVo, ammeterRecord);
        Page<AmmeterRecord> findAll = ammeterRecordService.findAll(ammeterRecord, pageable);
        return ResultVOUtil.success(findAll);
    }
    
    /**
	 * 前端状态日报
	 */
	@RequestMapping(value = "/stateDaily", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object listCount(AmmeterRecordVo ammeterRecordVo,Integer pageIndex) {
		AmmeterRecord ammeterRecord = new AmmeterRecord();
		BeanCopy.copyProperties(ammeterRecordVo, ammeterRecord);
		PageHelper.startPage(pageIndex == null ? 1 : pageIndex, 15);
		List<AmmeterRecord> ammeterRecords = ammeterRecordService.findByMapper(ammeterRecord);
		PageInfo<AmmeterRecord> pageInfo = new PageInfo<>(ammeterRecords);
		Map<String, Object> map = new HashMap<>();
		map.put("pageInfo", pageInfo);
		return ResultVOUtil.success(map);
	}
	
	/**
	 * 前端状态日报
	 */
	@RequestMapping(value = "/stateDailyAndroid", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object listCountAndroid(AmmeterRecordVo ammeterRecordVo,Integer pageIndex) {
		AmmeterRecord ammeterRecord = new AmmeterRecord();
		BeanCopy.copyProperties(ammeterRecordVo, ammeterRecord);
		PageHelper.startPage(pageIndex == null ? 1 : pageIndex, 5000);
		List<AmmeterRecord> ammeterRecords = ammeterRecordService.findByMapper(ammeterRecord);
		PageInfo<AmmeterRecord> pageInfo = new PageInfo<>(ammeterRecords);
		Map<String, Object> map = new HashMap<>();
		map.put("pageInfo", pageInfo);
		return ResultVOUtil.success(map);
	}
 
}

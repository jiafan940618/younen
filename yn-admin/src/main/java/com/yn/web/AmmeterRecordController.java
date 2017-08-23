package com.yn.web;

import com.yn.dao.AmmeterRecordDao;
import com.yn.model.AmmeterRecord;
import com.yn.service.AmmeterRecordService;
import com.yn.utils.BeanCopy;
import com.yn.vo.AmmeterRecordVo;
import com.yn.vo.re.ResultDataVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/server/ammeterRecord")
public class AmmeterRecordController {


    @Autowired
    private AmmeterRecordService ammeterRecordService;
    @Autowired
    private AmmeterRecordDao ammeterRecordDao;


    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        AmmeterRecord findOne = ammeterRecordService.findOne(id);
        return ResultDataVoUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody AmmeterRecordVo ammeterRecordVo) {
        AmmeterRecord ammeterRecord = new AmmeterRecord();
        BeanCopy.copyProperties(ammeterRecordVo, ammeterRecord);
        ammeterRecordService.save(ammeterRecord);
        return ResultDataVoUtil.success(ammeterRecord);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        ammeterRecordService.delete(id);
        return ResultDataVoUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(AmmeterRecordVo ammeterRecordVo) {
        AmmeterRecord ammeterRecord = new AmmeterRecord();
        BeanCopy.copyProperties(ammeterRecordVo, ammeterRecord);
        AmmeterRecord findOne = ammeterRecordService.findOne(ammeterRecord);
        return ResultDataVoUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(AmmeterRecordVo ammeterRecordVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        AmmeterRecord ammeterRecord = new AmmeterRecord();
        BeanCopy.copyProperties(ammeterRecordVo, ammeterRecord);
        Page<AmmeterRecord> findAll = ammeterRecordService.findAll(ammeterRecord, pageable);
        return ResultDataVoUtil.success(findAll);
    }


    /**
     * 设备地址
     */
    @RequestMapping(value = "/findDAddr", method = {RequestMethod.POST})
    @ResponseBody
    public Object findDAddr(@RequestParam("stationId") Long stationId, @RequestParam("type") Integer type) {
        List<Long> findDAddr = ammeterRecordDao.findDAddr(stationId, type);
        return ResultDataVoUtil.success(findDAddr);
    }


}

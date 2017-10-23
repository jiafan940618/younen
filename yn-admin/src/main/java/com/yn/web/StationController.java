package com.yn.web;


import com.yn.enums.NoticeEnum;
import com.yn.model.Station;
import com.yn.service.NoticeService;
import com.yn.service.StationService;
import com.yn.session.SessionCache;
import com.yn.utils.BeanCopy;
import com.yn.vo.StationVo;
import com.yn.vo.re.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/server/station")
public class StationController {


    @Autowired
    StationService stationService;
    @Autowired
    private NoticeService noticeService;


    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        Station findOne = stationService.findOne(id);


        // 更新记录为已读
        if (findOne != null) {
            Long userId = SessionCache.instance().getUserId();
            if (userId != null) {
                noticeService.update2Read(NoticeEnum.NEW_STATION.getCode(), id, userId);
            }
        }


        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody StationVo stationVo) {
        Station station = new Station();
        BeanCopy.copyProperties(stationVo, station);
        stationService.save(station);
        return ResultVOUtil.success(station);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    @Transactional
    public Object delete(Long id) {
        stationService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(StationVo stationVo) {
        Station station = new Station();
        BeanCopy.copyProperties(stationVo, station);
        Station findOne = stationService.findOne(station);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(StationVo stationVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Station station = new Station();
        BeanCopy.copyProperties(stationVo, station);
        Page<Station> findAll = stationService.findAll(station, pageable);


        // 判断是否已读
        Long userId = SessionCache.instance().getUserId();
        if (userId != null) {
            List<Station> content = findAll.getContent();
            for (Station one : content) {
                Boolean isNew = noticeService.findIsNew(NoticeEnum.NEW_STATION.getCode(), one.getId(), userId);
                if (isNew) {
                    one.setIsRead(NoticeEnum.UN_READ.getCode());
                }
            }
        }


        return ResultVOUtil.success(findAll);
    }

    /**
     * 更改电站的通道模式
     *
     * @param stationId
     * @param passageModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/changPassageModel", method = {RequestMethod.POST})
    public Object changPassageModel(@RequestParam(value = "stationId") Long stationId, @RequestParam(value = "passageModel") Integer passageModel) {
        Station station = stationService.changPassageModel(stationId, passageModel);
        return ResultVOUtil.success(station);
    }

    /**
     * 电站信息
     */
//    @RequestMapping(value = "/stationInfo", method = {RequestMethod.POST})
//    @ResponseBody
//    public Object stationInfo(Long stationId) {
//        Map<String, Object> stationInfo = stationService.stationInfo(stationId);
//        return ResultVOUtil.success(stationInfo);
//    }

    /**
     * 25年收益
     */
    @RequestMapping(value = "/get25YearIncome", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object get25YearIncome(Long stationId) {
        Map<String, Object> map = stationService.get25YearIncome(stationId);
        return ResultVOUtil.success(map);
    }

}

package com.yn.web;

import com.yn.enums.NoticeEnum;
import com.yn.model.Ammeter;
import com.yn.service.AmmeterService;
import com.yn.service.NoticeService;
import com.yn.session.SessionCache;
import com.yn.utils.BeanCopy;
import com.yn.vo.AmmeterVo;
import com.yn.vo.re.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/server/ammeter")
public class AmmeterController {


    @Autowired
    private AmmeterService ammeterService;
    @Autowired
    private NoticeService noticeService;


    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        Ammeter findOne = ammeterService.findOne(id);


        // 更新记录为已读
        if (findOne != null) {
            Long userId = SessionCache.instance().getUserId();
            if (userId != null) {
                noticeService.update2Read(NoticeEnum.NEW_AMMETER.getCode(), id, userId);
            }
        }


        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody AmmeterVo ammeterVo) {
        Ammeter ammeter = ammeterService.saveAndbindStation(ammeterVo);
        return ResultVOUtil.success(ammeter);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        ammeterService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(AmmeterVo ammeterVo) {
        Ammeter ammeter = new Ammeter();
        BeanCopy.copyProperties(ammeterVo, ammeter);
        Ammeter findOne = ammeterService.findOne(ammeter);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(AmmeterVo ammeterVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Ammeter ammeter = new Ammeter();
        BeanCopy.copyProperties(ammeterVo, ammeter);
        Page<Ammeter> findAll = ammeterService.findAll(ammeter, pageable);


        // 判断是否已读
        Long userId = SessionCache.instance().getUserId();
        if (userId != null) {
            List<Ammeter> content = findAll.getContent();
            for (Ammeter one : content) {
                Boolean isNew = noticeService.findIsNew(NoticeEnum.NEW_AMMETER.getCode(), one.getId(), userId);
                if (isNew) {
                    one.setIsRead(NoticeEnum.UN_READ.getCode());
                }
            }
        }

        return ResultVOUtil.success(findAll);
    }

    /**
     * 解绑电站
     *
     * @param ammeterId
     * @return
     */
    @RequestMapping(value = "/relieveStation", method = {RequestMethod.POST})
    @ResponseBody
    public Object relieveStation(@RequestParam("ammeterId") Long ammeterId) {
        Ammeter ammeter = ammeterService.relieveStation(ammeterId);
        return ResultVOUtil.success(ammeter);
    }


}

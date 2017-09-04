package com.yn.web;

import com.yn.enums.NoticeEnum;
import com.yn.model.Feedback;
import com.yn.service.FeedbackService;
import com.yn.service.NoticeService;
import com.yn.session.SessionCache;
import com.yn.utils.BeanCopy;
import com.yn.vo.FeedbackVo;
import com.yn.vo.re.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/server/feedback")
public class FeedbackController {


    @Autowired
    FeedbackService feedbackService;
    @Autowired
    private NoticeService noticeService;


    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        Feedback findOne = feedbackService.findOne(id);


        // 更新记录为已读
        if (findOne != null) {
            Long userId = SessionCache.instance().getUserId();
            if (userId != null) {
                noticeService.update2Read(NoticeEnum.NEW_FEEDBACK.getCode(), id, userId);
            }
        }


        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody FeedbackVo feedbackVo) {
        Long userId = SessionCache.instance().checkUserIsLogin();
        Feedback feedback = new Feedback();
        BeanCopy.copyProperties(feedbackVo, feedback);
        feedback.setUserId(userId);
        feedbackService.save(feedback);
        return ResultVOUtil.success(feedback);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        feedbackService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(FeedbackVo feedbackVo) {
        Feedback feedback = new Feedback();
        BeanCopy.copyProperties(feedbackVo, feedback);
        Feedback findOne = feedbackService.findOne(feedback);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(FeedbackVo feedbackVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Feedback feedback = new Feedback();
        BeanCopy.copyProperties(feedbackVo, feedback);
        Page<Feedback> findAll = feedbackService.findAll(feedback, pageable);


        // 判断是否已读
        Long userId = SessionCache.instance().getUserId();
        if (userId != null) {
            List<Feedback> content = findAll.getContent();
            for (Feedback one : content) {
                Boolean isNew = noticeService.findIsNew(NoticeEnum.NEW_FEEDBACK.getCode(), one.getId(), userId);
                if (isNew) {
                    one.setIsRead(NoticeEnum.UN_READ.getCode());
                }
            }
        }


        return ResultVOUtil.success(findAll);
    }
}

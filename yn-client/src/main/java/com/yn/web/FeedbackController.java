package com.yn.web;

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

import com.yn.model.Feedback;
import com.yn.model.User;
import com.yn.service.FeedbackService;
import com.yn.session.SessionCache;
import com.yn.utils.BeanCopy;
import com.yn.vo.FeedbackVo;
import com.yn.vo.re.ResultDataVoUtil;

@RestController
@RequestMapping("/client/feedback")
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        Feedback findOne = feedbackService.findOne(id);
        return ResultDataVoUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody FeedbackVo feedbackVo) {
        Feedback feedback = new Feedback();
        BeanCopy.copyProperties(feedbackVo, feedback);
        feedbackService.save(feedback);
        return ResultDataVoUtil.success(feedback);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        feedbackService.delete(id);
        return ResultDataVoUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(FeedbackVo feedbackVo) {
        Feedback feedback = new Feedback();
        BeanCopy.copyProperties(feedbackVo, feedback);
        Feedback findOne = feedbackService.findOne(feedback);
        return ResultDataVoUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(FeedbackVo feedbackVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Feedback feedback = new Feedback();
        BeanCopy.copyProperties(feedbackVo, feedback);
        Page<Feedback> findAll = feedbackService.findAll(feedback, pageable);
        return ResultDataVoUtil.success(findAll);
    }
}

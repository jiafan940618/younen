package com.yn.web;

import java.util.ArrayList;
import java.util.List;

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

import com.yn.dao.QuestionDao;
import com.yn.model.ElecDataDay;
import com.yn.model.News;
import com.yn.model.Question;
import com.yn.service.QuestionService;
import com.yn.utils.BeanCopy;
import com.yn.vo.NewsVo;
import com.yn.vo.QuestionVo;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/client/question")
public class QuestionController {
	@Autowired
	QuestionService questionService;
	@Autowired
	QuestionDao questionDao;
	
	 @RequestMapping(value = "/select", method = {RequestMethod.POST, RequestMethod.GET})
	    @ResponseBody
	    public Object findOne(Long id) {
		 Question findOne = questionService.findOne(id);
	        return ResultVOUtil.success(findOne);
	    }

	    @ResponseBody
	    @RequestMapping(value = "/save", method = {RequestMethod.POST, RequestMethod.GET})
	    public Object save( QuestionVo questionVo) {
	    	Question question = new Question();
	        BeanCopy.copyProperties(questionVo, question);
	        questionService.save(question);
	        return ResultVOUtil.success(question);
	    }

	    @ResponseBody
	    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
	    public Object delete(Long id) {
	    	questionService.delete(id);
	        return ResultVOUtil.success();
	    }

	    @ResponseBody
	    @RequestMapping(value = "/findOne", method = {RequestMethod.POST, RequestMethod.GET})
	    public Object findOne(QuestionVo questionVo) {
	    	Question question = new Question();
	        BeanCopy.copyProperties(questionVo, question);
	        Question findOne = questionService.findOne(question);
	        return ResultVOUtil.success(findOne);
	    }

	    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
	    @ResponseBody
	    public Object findAll(QuestionVo questionVo) {
	    	Question question = new Question();
	        BeanCopy.copyProperties(questionVo, question);
	        List<Question> findAll = questionService.findAll(question);
	        return ResultVOUtil.success(findAll);
	    }
	
}

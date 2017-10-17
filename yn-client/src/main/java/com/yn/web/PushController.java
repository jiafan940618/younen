package com.yn.web;

import com.yn.vo.re.ResultVOUtil;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.yn.model.Push;
import com.yn.service.PushService;
import com.yn.service.UserService;
import com.yn.utils.BeanCopy;
import com.yn.vo.PushVo;

@RestController
@RequestMapping("/client/push")
public class PushController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(PushController.class);
	
	@Autowired
	PushService pushService;
	@Autowired
	UserService userService;

	@RequestMapping(value = "/select", method = { RequestMethod.POST })
	@ResponseBody
	public Object findOne(Long id) {
		Push findOne = pushService.findOne(id);
		return ResultVOUtil.success(findOne);
	}

	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public Object save(@RequestBody PushVo pushVo) {
		Push push = new Push();
		BeanCopy.copyProperties(pushVo, push);
		pushService.save(push);
		return ResultVOUtil.success(push);
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public Object delete(Long id) {
		pushService.delete(id);
		return ResultVOUtil.success();
	}

	@ResponseBody
	@RequestMapping(value = "/findOne", method = { RequestMethod.POST })
	public Object findOne(PushVo pushVo) {
		Push push = new Push();
		BeanCopy.copyProperties(pushVo, push);
		Push findOne = pushService.findOne(push);
		return ResultVOUtil.success(findOne);
	}

	@RequestMapping(value = "/findAll", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object findAll(PushVo pushVo,
			@PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		Push push = new Push();
		BeanCopy.copyProperties(pushVo, push);
		Page<Push> findAll = pushService.findAll(push, pageable);
		return ResultVOUtil.success(findAll);
	}
	
	
	/** 根据查询条件查询Push*/
	@ResponseBody
	@RequestMapping(value = "/queryFind")
	public Object QueryPush(PushVo pushVo,com.yn.model.Page<Push> page) {
		/*page.setTime_to("2017-10-10");
		page.setTime_from("2017-10-01");
		page.setIndex(1);
		page.setIsRead(1);
		page.setUserId(3L);*/
		logger.info("---- ----- ---- ----- 传递的用户id,userId："+page.getTime_from());
		logger.info("---- ----- ---- ----- 传递的开始时间:time_from："+page.getTime_from());
		logger.info("---- ----- ---- ----- 传递的结束时间:time_to："+page.getTime_to());
		logger.info("---- ----- ---- ----- 当前页:index："+page.getIndex());
		logger.info("---- ----- ---- ----- 是否已读:{0:未读,1:已读,2:全部},isRead："+page.getIsRead());
		
		List<Push> list = pushService.findByPush(page);
		
		return ResultVOUtil.newsuccess(page, list);
	}
	
	/** 根据PushId修改是否已读*/
	@ResponseBody
	@RequestMapping(value = "/queryIdFind")
	public Object QueryPushId(PushVo pushVo) {
		
		logger.info("---- ----- ---- ----- 传递的id："+pushVo.getId());
		Push push = new Push();
		push.setId(pushVo.getId());
		
		pushService.save(push);
		
		return ResultVOUtil.success();	
	}
	
}

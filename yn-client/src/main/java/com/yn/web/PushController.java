package com.yn.web;

import com.yn.vo.re.ResultVOUtil;

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
	
	
	@ResponseBody
	@RequestMapping(value = "/queryFind", method = { RequestMethod.POST })
	public Object QueryPush(PushVo pushVo,com.yn.model.Page<Push> page) {
		
		logger.info("---- ----- ---- ----- 传递的开始时间："+page.getTime_from());
		logger.info("---- ----- ---- ----- 传递的结束时间："+page.getTime_to());
		logger.info("---- ----- ---- ----- 当前页："+page.getIndex());
		logger.info("---- ----- ---- ----- 是否已读:{0:未读,1:已读}："+pushVo.getIsRead());
		
		
		

		return ResultVOUtil.success();
	}
	
	
	
}

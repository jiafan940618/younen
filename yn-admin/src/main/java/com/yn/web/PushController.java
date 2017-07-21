package com.yn.web;

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

import com.yn.model.Push;
import com.yn.model.User;
import com.yn.service.PushService;
import com.yn.service.UserService;
import com.yn.utils.BeanCopy;
import com.yn.vo.PushVo;
import com.yn.vo.re.ResultDataVoUtil;

@RestController
@RequestMapping("/server/push")
public class PushController {
	@Autowired
	PushService pushService;
	@Autowired
	UserService userService;

	@RequestMapping(value = "/select", method = { RequestMethod.POST })
	@ResponseBody
	public Object findOne(Long id) {
		Push findOne = pushService.findOne(id);
		return ResultDataVoUtil.success(findOne);
	}

	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public Object save(@RequestBody PushVo pushVo) {
		Push push = new Push();
		BeanCopy.copyProperties(pushVo, push);

		// 推送给所有人
		if (push.getType().intValue() == 1) {
			List<User> findAll = userService.findAll(new User());
			for (User user : findAll) {
				Push pushR = new Push();
				BeanCopy.copyProperties(push, pushR);
				pushR.setUserId(user.getId());
				pushService.save(pushR);
			}
		} else {
			pushService.save(push);
		}

		return ResultDataVoUtil.success(push);
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public Object delete(Long id) {
		pushService.delete(id);
		return ResultDataVoUtil.success();
	}

	@ResponseBody
	@RequestMapping(value = "/findOne", method = { RequestMethod.POST })
	public Object findOne(PushVo pushVo) {
		Push push = new Push();
		BeanCopy.copyProperties(pushVo, push);
		Push findOne = pushService.findOne(push);
		return ResultDataVoUtil.success(findOne);
	}

	@RequestMapping(value = "/findAll", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object findAll(PushVo pushVo,
			@PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		Push push = new Push();
		BeanCopy.copyProperties(pushVo, push);
		Page<Push> findAll = pushService.findAll(push, pageable);
		return ResultDataVoUtil.success(findAll);
	}
}

package com.yn.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yn.model.Material;
import com.yn.service.MaterialService;
import com.yn.vo.re.ResultVOUtil;

@Controller
@RequestMapping("/client/material")
public class MaterialController {
	@Autowired
	MaterialService materialService;

	@RequestMapping(value = "/select", method = { RequestMethod.POST })
	@ResponseBody
	public Object findOne(Long id) {
		Material findOne = materialService.findOne(id);
		return ResultVOUtil.success(findOne);
	}
}

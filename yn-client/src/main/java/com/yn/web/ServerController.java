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

import com.yn.model.Server;
import com.yn.service.ServerService;
import com.yn.utils.BeanCopy;
import com.yn.vo.ServerVo;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/client/server")
public class ServerController {
	@Autowired
	ServerService serverService;

	@RequestMapping(value = "/select", method = { RequestMethod.POST })
	@ResponseBody
	public Object findOne(Long id) {
		Server findOne = serverService.findOne(id);
		return ResultVOUtil.success(findOne);
	}

	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public Object save(@RequestBody ServerVo serverVo) {
		Server server = new Server();
		BeanCopy.copyProperties(serverVo, server);
		serverService.save(server);
		return ResultVOUtil.success(server);
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public Object delete(Long id) {
		serverService.delete(id);
		return ResultVOUtil.success();
	}

	@ResponseBody
	@RequestMapping(value = "/findOne", method = { RequestMethod.POST })
	public Object findOne(ServerVo serverVo) {
		Server server = new Server();
		BeanCopy.copyProperties(serverVo, server);
		Server findOne = serverService.findOne(server);
		return ResultVOUtil.success(findOne);
	}

	@RequestMapping(value = "/findAll", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object findAll(ServerVo serverVo,
			@PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		Server server = new Server();
		BeanCopy.copyProperties(serverVo, server);
		Page<Server> findAll = serverService.findAll(server, pageable);
		return ResultVOUtil.success(findAll);
	}

	@RequestMapping(value = "/findLocalServer", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object findLocalServer(ServerVo serverVo,
			@PageableDefault(value = 5, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		Server server = new Server();
		BeanCopy.copyProperties(serverVo, server);
		Page<Server> findLocalServer = serverService.findLocalServer(server, pageable);
		return ResultVOUtil.success(findLocalServer);
	}
}
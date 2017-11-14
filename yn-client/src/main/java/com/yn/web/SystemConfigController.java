package com.yn.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yn.vo.re.ResultVOUtil;
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
import com.yn.model.SystemConfig;
import com.yn.service.SystemConfigService;
import com.yn.utils.BeanCopy;
import com.yn.vo.SystemConfigVo;

@RestController
@RequestMapping("/client/systemConfig")
public class SystemConfigController {
    @Autowired
    SystemConfigService systemConfigService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        SystemConfig findOne = systemConfigService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody SystemConfigVo systemConfigVo) {
        SystemConfig systemConfig = new SystemConfig();
        BeanCopy.copyProperties(systemConfigVo, systemConfig);
        systemConfigService.save(systemConfig);
        return ResultVOUtil.success(systemConfig);
    }

//    @ResponseBody
//    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        systemConfigService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(SystemConfigVo systemConfigVo) {
        SystemConfig systemConfig = new SystemConfig();
        BeanCopy.copyProperties(systemConfigVo, systemConfig);
        SystemConfig findOne = systemConfigService.findOne(systemConfig);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(SystemConfigVo systemConfigVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        SystemConfig systemConfig = new SystemConfig();
        BeanCopy.copyProperties(systemConfigVo, systemConfig);
        Page<SystemConfig> findAll = systemConfigService.findAll(systemConfig, pageable);
        return ResultVOUtil.success(findAll);
    }
    
    /**
     * 根据key查找值
     * @param key
     * @return
     */
    @RequestMapping(value = "/findByKey", method = {RequestMethod.POST})
    @ResponseBody
    public Object findByKey(String key) {
        String value = systemConfigService.get(key);
        return ResultVOUtil.success(value);
    }
    
    /**
     * 根据keys查找值
     * @param keys
     * @return
     */
    @RequestMapping(value = "/findByKeys", method = {RequestMethod.POST})
    @ResponseBody
    public Object findByKeys(String keys) {

        Map<String, Object> map = new HashMap<>();
        String[] split = keys.split(",");
        for (String key : split) {
        	String value = systemConfigService.get(key);
        	map.put(key, value);
		}
        
        return ResultVOUtil.success(map);
    }
    /** 安卓版本*/
   /* @ResponseBody
	@RequestMapping(value = "/updateAndroid")
	public Object deleteBatch(HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		
		Map<String, String> map = systemConfigService.getnewlist();
		
		map.put("android_version_code", map.get("android_version_code"));
		map.put("android_version_name", map.get("android_version_name"));
		map.put("android_update_url", map.get("android_update_url"));
		map.put("android_update_info", map.get("android_update_info"));
		
		return ResultVOUtil.success(map);
	}*/
    
}

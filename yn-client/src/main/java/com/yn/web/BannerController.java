package com.yn.web;

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

import com.yn.model.Banner;
import com.yn.service.BannerService;
import com.yn.utils.BeanCopy;
import com.yn.vo.BannerVo;

@RestController
@RequestMapping("/client/banner")
public class BannerController {
    @Autowired
    BannerService bannerService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        Banner findOne = bannerService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody BannerVo bannerVo) {
        Banner banner = new Banner();
        BeanCopy.copyProperties(bannerVo, banner);
        bannerService.save(banner);
        return ResultVOUtil.success(banner);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        bannerService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(BannerVo bannerVo) {
        Banner banner = new Banner();
        BeanCopy.copyProperties(bannerVo, banner);
        Banner findOne = bannerService.findOne(banner);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(BannerVo bannerVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Banner banner = new Banner();
        BeanCopy.copyProperties(bannerVo, banner);
        Page<Banner> findAll = bannerService.findAll(banner, pageable);
        return ResultVOUtil.success(findAll);
    }
}

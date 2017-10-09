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

import com.yn.model.News;
import com.yn.service.NewsService;
import com.yn.utils.BeanCopy;
import com.yn.vo.NewsVo;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/client/news")
public class NewsController {
    @Autowired
    NewsService newsService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        News findOne = newsService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody NewsVo newsVo) {
        News news = new News();
        BeanCopy.copyProperties(newsVo, news);
        newsService.save(news);
        return ResultVOUtil.success(news);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        newsService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(NewsVo newsVo) {
        News news = new News();
        BeanCopy.copyProperties(newsVo, news);
        News findOne = newsService.findOne(news);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(NewsVo newsVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        News news = new News();
        BeanCopy.copyProperties(newsVo, news);
        Page<News> findAll = newsService.findAll(news, pageable);
        return ResultVOUtil.success(findAll);
    }
}

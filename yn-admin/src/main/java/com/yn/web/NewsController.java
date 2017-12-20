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

import com.yn.model.Construction;
import com.yn.model.News;
import com.yn.service.ConstructionService;
import com.yn.service.NewsService;
import com.yn.utils.BeanCopy;
import com.yn.vo.NewsVo;

@RestController
@RequestMapping("/server/news")
public class NewsController {
    @Autowired
    NewsService newsService;
    
    @Autowired
	ConstructionService constructionService;

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
    @RequestMapping(value = "/ConstrSave")
    public Object NewSave(NewsVo newsVo) {
    	
    	System.out.println("传的id--- -- ---- ---- ---->"+newsVo.getId());
		System.out.println("传的imgurl--- -- ---- ---- ---->"+newsVo.getImgUrl());
		
		System.out.println("传的type--- -- ---- ---- ---->"+newsVo.getType());
    	
		Construction construction = new Construction();	
		
		String[] ImgUrls =	newsVo.getImgUrls();

       
        if(newsVo.getImgUrls() != null) {
            for (int i = 0; i < ImgUrls.length; i++) {
                System.out.println("传的length--- -- ---- ---- ---->" + ImgUrls.length);


                String imgUrl = ImgUrls[i];

                construction.setImgUrl(imgUrl);
                construction.setType(newsVo.getType());
                construction.setVideoUrl(newsVo.getVideoUrl());
                construction.setIdentification(0);

                constructionService.insertConstr(construction);

                System.out.println("传的ImgUrls--- -- ---- ---- ---->" + imgUrl);
            }
        }
		if(newsVo.getImgUrls() == null) {
	    	
	    	construction.setId(newsVo.getId());
	    	construction.setImgUrl(newsVo.getImgUrl());
	    	construction.setType(newsVo.getType());
	    	construction.setVideoUrl(newsVo.getVideoUrl());
	    	construction.setIdentification(0);
	    	
	    	constructionService.save(construction);
		}


        return ResultVOUtil.success(construction);
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

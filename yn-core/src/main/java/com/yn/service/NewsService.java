package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.NewsDao;
import com.yn.model.News;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class NewsService {
    @Autowired
    NewsDao newsDao;

    public News findOne(Long id) {
        return newsDao.findOne(id);
    }

    public void save(News news) {
        if(news.getId()!=null){
        	News one = newsDao.findOne(news.getId());
            try {
                BeanCopy.beanCopy(news,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            newsDao.save(one);
        }else {
            newsDao.save(news);
        }
    }

    public void delete(Long id) {
        newsDao.delete(id);
    }
    
    public void deleteBatch(List<Long> id) {
		newsDao.deleteBatch(id);
	}

    public News findOne(News news) {
        Specification<News> spec = RepositoryUtil.getSpecification(news);
        News findOne = newsDao.findOne(spec);
        return findOne;
    }

    public List<News> findAll(List<Long> list) {
        return newsDao.findAll(list);
    }

    public Page<News> findAll(News news, Pageable pageable) {
        Specification<News> spec = RepositoryUtil.getSpecification(news);
        Page<News> findAll = newsDao.findAll(spec, pageable);
        return findAll;
    }

    public List<News> findAll(News news) {
        Specification<News> spec = RepositoryUtil.getSpecification(news);
        return newsDao.findAll(spec);
    }
}

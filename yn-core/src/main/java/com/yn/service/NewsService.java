package com.yn.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.yn.model.BillWithdrawals;
import com.yn.utils.DateUtil;
import com.yn.utils.ObjToMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.NewsDao;
import com.yn.model.News;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;

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

    public News selNews(){

        return  newsDao.selNews();
    }

    public void delete(Long id) {
        newsDao.delete(id);
    }
    
    public void deleteBatch(List<Long> id) {
		newsDao.deleteBatch(id);
	}

    public News findOne(News news) {
        Specification<News> spec = getSpecification(news);
        News findOne = newsDao.findOne(spec);
        return findOne;
    }

    public List<News> findAll(List<Long> list) {
        return newsDao.findAll(list);
    }

    public Page<News> findAll(News news, Pageable pageable) {
        Specification<News> spec = getSpecification(news);
        Page<News> findAll = newsDao.findAll(spec, pageable);
        return findAll;
    }

    public List<News> findAll(News news) {
        Specification<News> spec = getSpecification(news);
        return newsDao.findAll(spec);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Specification<News> getSpecification(News news) {
        news.setDel(0);
        Map<String, Object> objectMap = ObjToMap.getObjectMap(news);
        return (Root<News> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

            Predicate conjunction = cb.conjunction();
            List<Expression<Boolean>> expressions = conjunction.getExpressions();
            Iterator<Map.Entry<String, Object>> iterator = objectMap.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                if (!entry.getKey().equals("query") && !entry.getKey().equals("queryStartDtm") && !entry.getKey().equals("queryEndDtm")) {
                    Object value = entry.getValue();
                    if (value instanceof Map) {
                        Iterator<Map.Entry<String, Object>> iterator1 = ((Map) value).entrySet().iterator();
                        while (iterator1.hasNext()) {
                            Map.Entry<String, Object> entry1 = iterator1.next();
                            expressions.add(cb.equal(root.get(entry.getKey()).get(entry1.getKey()), entry1.getValue()));
                        }
                    } else {
                        expressions.add(cb.equal(root.get(entry.getKey()), value));
                    }
                }
            }

            // 标题/作者
            String queryStr = news.getQuery();
            if (!StringUtils.isEmpty(queryStr)) {
                Predicate[] predicates = new Predicate[2];
                predicates[0] = cb.like(root.get("title"), "%" + queryStr + "%");
                predicates[1] = cb.like(root.get("author"), "%" + queryStr + "%");
                expressions.add(cb.or(predicates));
            }

            // 根据日期筛选
            String queryStartDtm = news.getQueryStartDtm();
            String queryEndDtm = news.getQueryEndDtm();
            if (!StringUtils.isEmpty(queryStartDtm)) {
                expressions.add(cb.greaterThanOrEqualTo(root.get("createDtm"), DateUtil.parseString(queryStartDtm, DateUtil.yyyy_MM_dd_HHmmss)));
            }
            if (!StringUtils.isEmpty(queryEndDtm)) {
                expressions.add(cb.lessThan(root.get("createDtm"), DateUtil.parseString(queryEndDtm, DateUtil.yyyy_MM_dd_HHmmss)));
            }

            return conjunction;
        };
    }


}

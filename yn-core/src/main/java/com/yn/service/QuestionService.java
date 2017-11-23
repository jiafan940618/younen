package com.yn.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yn.dao.QuestionDao;
import com.yn.model.News;
import com.yn.model.Question;
import com.yn.utils.BeanCopy;
import com.yn.utils.DateUtil;
import com.yn.utils.ObjToMap;

@Service
public class QuestionService {
	@Autowired
	QuestionDao questionDao;
	
	public Question findOne(Long id) {
        return questionDao.findOne(id);
    }

    public void save(Question question) {
        if(question.getId()!=null){
        	Question one = questionDao.findOne(question.getId());
            try {
                BeanCopy.beanCopy(question,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            questionDao.save(one);
        }else {
        	questionDao.save(question);
        }
    }

    public void delete(Long id) {
    	questionDao.delete(id);
    }
    
    public void deleteBatch(List<Long> id) {
    	questionDao.deleteBatch(id);
	}
    public Question findOne(Question question) {
        Specification<Question> spec = getSpecification(question);
        Question findOne = questionDao.findOne(spec);
        return findOne;
    }



//    public Page<Question> findAll(Question question, Pageable pageable) {
//        Specification<Question> spec = getSpecification(question);
//        Page<Question> findAll = questionDao.findAll(spec, pageable);
//        return findAll;
//    }

    public List<Question> findAll(Question question) {
        Specification<Question> spec = getSpecification(question);
        return questionDao.findAll(spec);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Specification<Question> getSpecification(Question question) {
    	question.setDel(0);
        Map<String, Object> objectMap = ObjToMap.getObjectMap(question);
        return (Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

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
            String queryStr = question.getQuery();
            if (!StringUtils.isEmpty(queryStr)) {
                Predicate[] predicates = new Predicate[2];
                predicates[0] = cb.like(root.get("title"), "%" + queryStr + "%");
                predicates[1] = cb.like(root.get("content"), "%" + queryStr + "%");
                expressions.add(cb.or(predicates));
            }

            // 根据日期筛选
            String queryStartDtm = question.getQueryStartDtm();
            String queryEndDtm = question.getQueryEndDtm();
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

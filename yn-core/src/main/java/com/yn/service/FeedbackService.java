package com.yn.service;

import java.util.List;

import com.yn.enums.NoticeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.FeedbackDao;
import com.yn.model.Feedback;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class FeedbackService {


    @Autowired
    FeedbackDao feedbackDao;
    @Autowired
    private NoticeService noticeService;


    public Feedback findOne(Long id) {
        return feedbackDao.findOne(id);
    }

    public void save(Feedback feedback) {
        if(feedback.getId()!=null){
        	Feedback one = feedbackDao.findOne(feedback.getId());
            try {
                BeanCopy.beanCopy(feedback,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            feedbackDao.save(one);
        }else {
            feedbackDao.save(feedback);
        }
    }

    public void delete(Long id) {
        feedbackDao.delete(id);

        // 删除未读信息
        noticeService.delete(NoticeEnum.NEW_FEEDBACK.getCode(), id);
    }
    
    public void deleteBatch(List<Long> id) {
		feedbackDao.deleteBatch(id);
	}

    public Feedback findOne(Feedback feedback) {
        Specification<Feedback> spec = RepositoryUtil.getSpecification(feedback);
        Feedback findOne = feedbackDao.findOne(spec);
        return findOne;
    }

    public List<Feedback> findAll(List<Long> list) {
        return feedbackDao.findAll(list);
    }

    public Page<Feedback> findAll(Feedback feedback, Pageable pageable) {
        Specification<Feedback> spec = RepositoryUtil.getSpecification(feedback);
        Page<Feedback> findAll = feedbackDao.findAll(spec, pageable);
        return findAll;
    }

    public List<Feedback> findAll(Feedback feedback) {
        Specification<Feedback> spec = RepositoryUtil.getSpecification(feedback);
        return feedbackDao.findAll(spec);
    }
}

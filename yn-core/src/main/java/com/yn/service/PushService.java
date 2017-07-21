package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.PushDao;
import com.yn.model.Push;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class PushService {
    @Autowired
    PushDao pushDao;

    public Push findOne(Long id) {
        return pushDao.findOne(id);
    }

    public void save(Push push) {
        if(push.getId()!=null){
        	Push one = pushDao.findOne(push.getId());
            try {
                BeanCopy.beanCopy(push,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            pushDao.save(one);
        }else {
            pushDao.save(push);
        }
    }

    public void delete(Long id) {
        pushDao.delete(id);
    }
    
    public void deleteBatch(List<Long> id) {
		pushDao.deleteBatch(id);
	}

    public Push findOne(Push push) {
        Specification<Push> spec = RepositoryUtil.getSpecification(push);
        Push findOne = pushDao.findOne(spec);
        return findOne;
    }

    public List<Push> findAll(List<Long> list) {
        return pushDao.findAll(list);
    }

    public Page<Push> findAll(Push push, Pageable pageable) {
        Specification<Push> spec = RepositoryUtil.getSpecification(push);
        Page<Push> findAll = pushDao.findAll(spec, pageable);
        return findAll;
    }

    public List<Push> findAll(Push push) {
        Specification<Push> spec = RepositoryUtil.getSpecification(push);
        return pushDao.findAll(spec);
    }
}

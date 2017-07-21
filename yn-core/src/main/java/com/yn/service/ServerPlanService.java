package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.ServerPlanDao;
import com.yn.model.ServerPlan;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class ServerPlanService {
    @Autowired
    ServerPlanDao serverPlanDao;

    public ServerPlan findOne(Long id) {
        return serverPlanDao.findOne(id);
    }

    public void save(ServerPlan serverPlan) {
        if(serverPlan.getId()!=null){
        	ServerPlan one = serverPlanDao.findOne(serverPlan.getId());
            try {
                BeanCopy.beanCopy(serverPlan,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            serverPlanDao.save(one);
        }else {
            serverPlanDao.save(serverPlan);
        }
    }

    public void delete(Long id) {
        serverPlanDao.delete(id);
    }
    
    public void deleteBatch(List<Long> id) {
		serverPlanDao.deleteBatch(id);
	}

    public ServerPlan findOne(ServerPlan serverPlan) {
        Specification<ServerPlan> spec = RepositoryUtil.getSpecification(serverPlan);
        ServerPlan findOne = serverPlanDao.findOne(spec);
        return findOne;
    }

    public List<ServerPlan> findAll(List<Long> list) {
        return serverPlanDao.findAll(list);
    }

    public Page<ServerPlan> findAll(ServerPlan serverPlan, Pageable pageable) {
        Specification<ServerPlan> spec = RepositoryUtil.getSpecification(serverPlan);
        Page<ServerPlan> findAll = serverPlanDao.findAll(spec, pageable);
        return findAll;
    }

    public List<ServerPlan> findAll(ServerPlan serverPlan) {
        Specification<ServerPlan> spec = RepositoryUtil.getSpecification(serverPlan);
        return serverPlanDao.findAll(spec);
    }
}

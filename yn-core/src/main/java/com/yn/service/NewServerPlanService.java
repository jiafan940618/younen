package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.NewServerPlanDao;
import com.yn.model.NewServerPlan;
import com.yn.model.News;
import com.yn.utils.RepositoryUtil;
@Service
public class NewServerPlanService {
	
	@Autowired
	NewServerPlanDao planDao;
	
	 public NewServerPlan findOne(Long id) {
	        return planDao.findOne(id);
	    }
	
	 public NewServerPlan findOne(NewServerPlan serverPlan) {
	        Specification<NewServerPlan> spec =RepositoryUtil.getSpecification(serverPlan);
	        NewServerPlan findOne = planDao.findOne(spec);
	        return findOne;
	    }

	    public List<NewServerPlan> findAll(List<Long> list) {
	        return planDao.findAll(list);
	    }

	    public Page<NewServerPlan> findAll(NewServerPlan serverPlan, Pageable pageable) {
	        Specification<NewServerPlan> spec = RepositoryUtil.getSpecification(serverPlan);
	        Page<NewServerPlan> findAll = planDao.findAll(spec, pageable);
	        return findAll;
	    }

	    public List<NewServerPlan> findAll(NewServerPlan serverPlan) {
	        Specification<NewServerPlan> spec = RepositoryUtil.getSpecification(serverPlan);
	        return planDao.findAll(spec);
	    }
	
	

}

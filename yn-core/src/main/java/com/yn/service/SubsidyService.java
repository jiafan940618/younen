package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.SubsidyDao;
import com.yn.model.Subsidy;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class SubsidyService {
	@Autowired
	SubsidyDao subsidyDao;

	public Subsidy findOne(Long id) {
		return subsidyDao.findOne(id);
	}

	public void save(Subsidy subsidy) {
		if (subsidy.getId() != null) {
			Subsidy one = subsidyDao.findOne(subsidy.getId());
			try {
				BeanCopy.beanCopy(subsidy, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			subsidyDao.save(one);
		} else {
			subsidyDao.save(subsidy);
		}
		System.out.println();
	}

	public void delete(Long id) {
		subsidyDao.delete(id);
	}

	public void deleteBatch(List<Long> id) {
		subsidyDao.deleteBatch(id);
	}

	public Subsidy findOne(Subsidy subsidy) {
		Specification<Subsidy> spec = RepositoryUtil.getSpecification(subsidy);
		Subsidy findOne = subsidyDao.findOne(spec);
		return findOne;
	}

	public List<Subsidy> findAll(List<Long> list) {
		return subsidyDao.findAll(list);
	}

	public Page<Subsidy> findAll(Subsidy subsidy, Pageable pageable) {
		Specification<Subsidy> spec = RepositoryUtil.getSpecification(subsidy);
		Page<Subsidy> findAll = subsidyDao.findAll(spec, pageable);
		return findAll;
	}

	public List<Subsidy> findAll(Subsidy subsidy) {
		Specification<Subsidy> spec = RepositoryUtil.getSpecification(subsidy);
		return subsidyDao.findAll(spec);
	} 
}

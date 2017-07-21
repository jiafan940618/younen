package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.DevideDao;
import com.yn.model.Devide;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class DevideService {
	@Autowired
	DevideDao devideDao;

	public Devide findOne(Long id) {
		return devideDao.findOne(id);
	}

	public void save(Devide devide) {
		if (devide.getId() != null) {
			Devide one = devideDao.findOne(devide.getId());
			try {
				BeanCopy.beanCopy(devide, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			devideDao.save(one);
		} else {
			devideDao.save(devide);
		}
		System.out.println();
	}

	public void delete(Long id) {
		devideDao.delete(id);
	}

	public void deleteBatch(List<Long> id) {
		devideDao.deleteBatch(id);
	}

	public Devide findOne(Devide devide) {
		Specification<Devide> spec = RepositoryUtil.getSpecification(devide);
		Devide findOne = devideDao.findOne(spec);
		return findOne;
	}

	public List<Devide> findAll(List<Long> list) {
		return devideDao.findAll(list);
	}

	public Page<Devide> findAll(Devide devide, Pageable pageable) {
		Specification<Devide> spec = RepositoryUtil.getSpecification(devide);
		Page<Devide> findAll = devideDao.findAll(spec, pageable);
		return findAll;
	}

	public List<Devide> findAll(Devide devide) {
		Specification<Devide> spec = RepositoryUtil.getSpecification(devide);
		return devideDao.findAll(spec);
	} 
}

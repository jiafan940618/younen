package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.ApolegamyDao;
import com.yn.model.Apolegamy;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class ApolegamyService {
	@Autowired
	ApolegamyDao apolegamyDao;

	public Apolegamy findOne(Long id) {
		return apolegamyDao.findOne(id);
	}

	public void save(Apolegamy apolegamy) {
		if (apolegamy.getId() != null) {
			Apolegamy one = apolegamyDao.findOne(apolegamy.getId());
			try {
				BeanCopy.beanCopy(apolegamy, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			apolegamyDao.save(one);
		} else {
			apolegamyDao.save(apolegamy);
		}
		System.out.println();
	}

	public void delete(Long id) {
		apolegamyDao.delete(id);
	}

	public void deleteBatch(List<Long> id) {
		apolegamyDao.deleteBatch(id);
	}

	public Apolegamy findOne(Apolegamy apolegamy) {
		Specification<Apolegamy> spec = RepositoryUtil.getSpecification(apolegamy);
		Apolegamy findOne = apolegamyDao.findOne(spec);
		return findOne;
	}

	public List<Apolegamy> findAll(List<Long> list) {
		return apolegamyDao.findAll(list);
	}

	public Page<Apolegamy> findAll(Apolegamy apolegamy, Pageable pageable) {
		Specification<Apolegamy> spec = RepositoryUtil.getSpecification(apolegamy);
		Page<Apolegamy> findAll = apolegamyDao.findAll(spec, pageable);
		return findAll;
	}

	public List<Apolegamy> findAll(Apolegamy apolegamy) {
		Specification<Apolegamy> spec = RepositoryUtil.getSpecification(apolegamy);
		return apolegamyDao.findAll(spec);
	} 
}

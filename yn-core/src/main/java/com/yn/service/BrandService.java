package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.BrandDao;
import com.yn.model.Brand;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class BrandService {
	@Autowired
	BrandDao brandDao;

	public Brand findOne(Long id) {
		return brandDao.findOne(id);
	}

	public void save(Brand brand) {
		if (brand.getId() != null) {
			Brand one = brandDao.findOne(brand.getId());
			try {
				BeanCopy.beanCopy(brand, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			brandDao.save(one);
		} else {
			brandDao.save(brand);
		}
	}

	public void delete(Long id) {
		brandDao.delete(id);
	}

	public void deleteBatch(List<Long> id) {
		brandDao.deleteBatch(id);
	}

	public Brand findOne(Brand brand) {
		Specification<Brand> spec = RepositoryUtil.getSpecification(brand);
		Brand findOne = brandDao.findOne(spec);
		return findOne;
	}

	public List<Brand> findAll(List<Long> list) {
		return brandDao.findAll(list);
	}

	public Page<Brand> findAll(Brand brand, Pageable pageable) {
		Specification<Brand> spec = RepositoryUtil.getSpecification(brand);
		Page<Brand> findAll = brandDao.findAll(spec, pageable);
		return findAll;
	}

	public List<Brand> findAll(Brand brand) {
		Specification<Brand> spec = RepositoryUtil.getSpecification(brand);
		return brandDao.findAll(spec);
	}
}

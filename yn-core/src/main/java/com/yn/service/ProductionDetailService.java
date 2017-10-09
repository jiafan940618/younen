package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.ProductionDetailDao;
import com.yn.model.ProductionDetail;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class ProductionDetailService {
	@Autowired
	ProductionDetailDao productionDetailDao;

	public ProductionDetail findOne(Long id) {
		return productionDetailDao.findOne(id);
	}

	public void save(ProductionDetail productionDetail) {
		if (productionDetail.getId() != null) {
			ProductionDetail one = productionDetailDao.findOne(productionDetail.getId());
			try {
				BeanCopy.beanCopy(productionDetail, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			productionDetailDao.save(one);
		} else {
			productionDetailDao.save(productionDetail);
		}
	}

	public void delete(Long id) {
		productionDetailDao.delete(id);
	}

	public void deleteBatch(List<Long> id) {
		productionDetailDao.deleteBatch(id);
	}

	public ProductionDetail findOne(ProductionDetail productionDetail) {
		Specification<ProductionDetail> spec = RepositoryUtil.getSpecification(productionDetail);
		ProductionDetail findOne = productionDetailDao.findOne(spec);
		return findOne;
	}

	public List<ProductionDetail> findAll(List<Long> list) {
		return productionDetailDao.findAll(list);
	}

	public Page<ProductionDetail> findAll(ProductionDetail productionDetail, Pageable pageable) {
		Specification<ProductionDetail> spec = RepositoryUtil.getSpecification(productionDetail);
		Page<ProductionDetail> findAll = productionDetailDao.findAll(spec, pageable);
		return findAll;
	}

	public List<ProductionDetail> findAll(ProductionDetail productionDetail) {
		Specification<ProductionDetail> spec = RepositoryUtil.getSpecification(productionDetail);
		return productionDetailDao.findAll(spec);
	}
}

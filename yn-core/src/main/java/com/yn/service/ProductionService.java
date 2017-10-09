package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.ProductionDao;
import com.yn.model.NewServerPlan;
import com.yn.model.ProductionDetail;
import com.yn.utils.RepositoryUtil;

@Service
public class ProductionService {
	
	@Autowired
	private ProductionDao productiondao;
	
	public ProductionDetail findOne(Long id) {
        return productiondao.findOne(id);
    }

 public ProductionDetail findOne(ProductionDetail production) {
        Specification<ProductionDetail> spec =RepositoryUtil.getSpecification(production);
        ProductionDetail findOne = productiondao.findOne(spec);
        return findOne;
    }

    public List<ProductionDetail> findAll(List<Long> list) {
        return productiondao.findAll(list);
    }

    public Page<ProductionDetail> findAll(ProductionDetail production, Pageable pageable) {
        Specification<ProductionDetail> spec = RepositoryUtil.getSpecification(production);
        Page<ProductionDetail> findAll = productiondao.findAll(spec, pageable);
        return findAll;
    }

    public List<ProductionDetail> findAll(ProductionDetail production) {
        Specification<ProductionDetail> spec = RepositoryUtil.getSpecification(production);
        return productiondao.findAll(spec);
    }

	
	

}

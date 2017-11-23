package com.yn.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.yn.dao.BrandDao;
import com.yn.dao.mapper.BrandMapper;
import com.yn.dao.mapper.InverterMapper;
import com.yn.dao.mapper.SolarPanelMapper;
import com.yn.model.Brand;
import com.yn.model.Inverter;
import com.yn.model.SolarPanel;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class BrandService {
	@Autowired
	BrandDao brandDao;
	@Autowired
	BrandMapper brandMapper;
	@Autowired
	InverterMapper inverterMapper;
	
	@Autowired
	SolarPanelMapper solarPanelMapper;
	

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
	
  
	public Integer getCount(com.yn.model.Page<Brand> page){
		
		return brandMapper.getCount(page);
	}
	

	public List<Brand> getBrand(com.yn.model.Page<Brand> page){
		
		return brandMapper.getBrand(page);	
	}
	
	public List<Inverter>  getInverter(Integer id){
		
		return brandDao.getInverter(id);
	}
	
	public List<SolarPanel>  getSolarPanel(Integer id){
		
		return brandDao.getSolarPanel(id);
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
	public List<Brand> selectBrand(String type){
		
		return brandDao.selectBrand(type);
	}
	
	
}

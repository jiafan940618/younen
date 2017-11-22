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
	
	 /** 拿到电池板 的型号*/
	
	public List<SolarPanel>  getnewSolarPanel(Integer id){
		
		return brandDao.getSolarPanel(id);
	 }
	
	public List<Brand> getSolarPanel(com.yn.model.Page<SolarPanel> page){
		
		List<Brand> blist = new LinkedList<Brand>();
		
		List<SolarPanel> list = solarPanelMapper.getSolarPanel(page);
		
		for (SolarPanel solar : list) {
			
			Brand brand = new Brand();
			brand.setId(Long.valueOf(solar.getBrandId()));
			brand.setBrandName(solar.getBrandName());
			
			blist.add(brand);
		}
		
		return blist;
	}
	
	public Integer getSolarCount(com.yn.model.Page<SolarPanel> page){
		
		List<SolarPanel> list = solarPanelMapper.getCount(page);
		
		return list.size();
	}
	
	
	
	/** 拿到逆变器型号*/
	public List<Inverter>  getnewInverter(Integer id){
		
		
		return brandDao.getInverter(id);	
	}
	
	
	
	public List<Brand>  getInverter(com.yn.model.Page<Inverter> page){
		
		List<Brand> blist = new LinkedList<Brand>();
		
		List<Inverter> list = inverterMapper.getInverter(page);
		
		for (Inverter inverter : list) {
			
			Brand brand = new Brand();
			brand.setId(Long.valueOf(inverter.getBrandId()));
			brand.setBrandName(inverter.getBrandName());
			
			blist.add(brand);
		}
		
		return blist;	
	}
	
	public Integer getCount(com.yn.model.Page<Inverter> page){
		
		List<Inverter> list = inverterMapper.getCount(page);
		
		return list.size();
	}
	
	public List<SolarPanel>  getSolarPanel01(Integer id){
		
		return brandDao.getSolarPanel(id); 
	 }
	
	public List<Brand> getBrand(com.yn.model.Page<Brand> page){
		
		return brandMapper.getBrand(page);	
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

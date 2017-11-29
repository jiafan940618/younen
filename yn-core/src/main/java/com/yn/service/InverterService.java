package com.yn.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.InverterDao;
import com.yn.model.Inverter;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class InverterService {
	@Autowired
	InverterDao inverterDao;

	public Inverter findOne(Long id) {
		return inverterDao.findOne(id);
	}
	
	public List<Inverter> FindByall(com.yn.model.Page page){
		
		
		return inverterDao.FindByall(page);
	}
	
	public int FindByconut(){
		
		return inverterDao.FindByconut();
	}

	public void save(Inverter inverter) {
		if (inverter.getId() != null) {
			Inverter one = inverterDao.findOne(inverter.getId());
			try {
				BeanCopy.beanCopy(inverter, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			inverterDao.save(one);
		} else {
			inverterDao.save(inverter);
		}
	}

	public void delete(Long id) {
		inverterDao.delete(id);
	}
	
	
	public List<Inverter> getinverter(){
		
		
		List<Inverter> solarList = new LinkedList<Inverter>();
    	
		List<Object> list=	inverterDao.getinverter();
    	
    	for (Object object : list) {
    		Inverter inverter = new Inverter();
			Object[] obj =(Object[])object;
			
			Integer brandId =(Integer)obj[0];
			String brandName =(String)obj[1];
			
			inverter.setBrandId(brandId);
			inverter.setBrandName(brandName);
			solarList.add(inverter);
		}
		
		
		return solarList;
	}

	public void deleteBatch(List<Long> id) {
		inverterDao.deleteBatch(id);
	}

	public Inverter findOne(Inverter inverter) {
		Specification<Inverter> spec = RepositoryUtil.getSpecification(inverter);
		Inverter findOne = inverterDao.findOne(spec);
		return findOne;
	}

	public List<Inverter> findAll(List<Long> list) {
		return inverterDao.findAll(list);
	}

	public Page<Inverter> findAll(Inverter inverter, Pageable pageable) {
		Specification<Inverter> spec = RepositoryUtil.getSpecification(inverter);
		Page<Inverter> findAll = inverterDao.findAll(spec, pageable);
		return findAll;
	}

	public List<Inverter> findAll(Inverter inverter) {
		Specification<Inverter> spec = RepositoryUtil.getSpecification(inverter);
		return inverterDao.findAll(spec);
	}
	
	   public	List<Inverter> selectInverter(Long brandId){
			
				int brand_id = brandId.intValue();
				
				return inverterDao.selectInverter(brand_id);
			}
}

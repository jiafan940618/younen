package com.yn.service;

import java.util.List;

import com.yn.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import com.yn.dao.ConstructionDao;
import com.yn.model.Construction;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class ConstructionService {
	 @Autowired
	 ConstructionDao constructionDao;
	 
	 
	 public  List<Construction> findbyType(Integer type){
		  
		return constructionDao.findbyType(type);  
	  }
	 
	 public void delete(Long id){
		 
		 constructionDao.delete(id);
	 }

	public void insertConstr(Construction construction){

		constructionDao.insertConstr(construction);
	}
	 
	public  List<Construction> findbyStruction(){
		
		return constructionDao.findbyStruction(); 
	 }
	 
	public List<Construction> findbyident(){
		
		return constructionDao.findbyident(); 
	 }  
	
	 public Construction findOne(Long id) {
	        return constructionDao.findOne(id);
	    }

	    public void save(Construction construction) {
	        if(construction.getId()!=null){
	        	Construction one = constructionDao.findOne(construction.getId());
	            try {
	                BeanCopy.beanCopy(construction,one);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            constructionDao.save(one);
	        }else {
	        	constructionDao.save(construction);
	        }
	    }
	 
	    
	    public List<Construction> findAll(Construction construction) {
	        Specification<Construction> spec = RepositoryUtil.getSpecification(construction);
	        return constructionDao.findAll(spec);
	    }

	public Page<Construction> findAll(Construction city, Pageable pageable) {
		Specification<Construction> spec = RepositoryUtil.getSpecification(city);
		Page<Construction> findAll = constructionDao.findAll(spec, pageable);
		return findAll;
	}
	
	

}

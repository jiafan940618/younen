package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.MaterialDao;
import com.yn.model.Material;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class MaterialService {
	@Autowired
	MaterialDao materialDao;
	
    public Material findOne(Long id) {
        return materialDao.findOne(id);
    }

    public void save(Material material) {
        if(material.getId()!=null){
        	Material one = materialDao.findOne(material.getId());
            try {
                BeanCopy.beanCopy(material,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            materialDao.save(one);
        }else {
        	materialDao.save(material);
        }
    }

    public void delete(Long id) {
    	materialDao.delete(id);
    }
    
    public void deleteBatch(List<Long> id) {
    	materialDao.deleteBatch(id);
	}

    public Material findOne(Material material) {
        Specification<Material> spec = RepositoryUtil.getSpecification(material);
        Material findOne = materialDao.findOne(spec);
        return findOne;
    }

    public List<Material> findAll(List<Long> list) {
        return materialDao.findAll(list);
    }

    public Page<Material> findAll(Material material, Pageable pageable) {
        Specification<Material> spec = RepositoryUtil.getSpecification(material);
        Page<Material> findAll = materialDao.findAll(spec, pageable);
        return findAll;
    }

    public List<Material> findAll(Material material) {
        Specification<Material> spec = RepositoryUtil.getSpecification(material);
        return materialDao.findAll(spec);
    }
}

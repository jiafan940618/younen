package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.ProvinceDao;
import com.yn.model.Province;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class ProvinceService {
    @Autowired
    ProvinceDao provinceDao;

    public Province findOne(Long id) {
        return provinceDao.findOne(id);
    }

    public void save(Province province) {
        if(province.getId()!=null){
        	Province one = provinceDao.findOne(province.getId());
            try {
                BeanCopy.beanCopy(province,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            provinceDao.save(one);
        }else {
            provinceDao.save(province);
        }
    }

    public void delete(Long id) {
        provinceDao.delete(id);
    }
    
    public void deleteBatch(List<Long> id) {
		provinceDao.deleteBatch(id);
	}

    public Province findOne(Province province) {
        Specification<Province> spec = RepositoryUtil.getSpecification(province);
        Province findOne = provinceDao.findOne(spec);
        return findOne;
    }

    public List<Province> findAll(List<Long> list) {
        return provinceDao.findAll(list);
    }

    public Page<Province> findAll(Province province, Pageable pageable) {
        Specification<Province> spec = RepositoryUtil.getSpecification(province);
        Page<Province> findAll = provinceDao.findAll(spec, pageable);
        return findAll;
    }

    public List<Province> findAll(Province province) {
        Specification<Province> spec = RepositoryUtil.getSpecification(province);
        return provinceDao.findAll(spec);
    }
}

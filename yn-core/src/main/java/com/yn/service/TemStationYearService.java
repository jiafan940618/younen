package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.TemStationYearDao;
import com.yn.model.TemStationYear;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class TemStationYearService {
    @Autowired
    TemStationYearDao temStationYearDao;

    public TemStationYear findOne(Long id) {
        return temStationYearDao.findOne(id);
    }

    public void save(TemStationYear temStationYear) {
        if(temStationYear.getId()!=null){
        	TemStationYear one = temStationYearDao.findOne(temStationYear.getId());
            try {
                BeanCopy.beanCopy(temStationYear,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            temStationYearDao.save(one);
        }else {
            temStationYearDao.save(temStationYear);
        }
    }

    public void delete(Long id) {
        temStationYearDao.delete(id);
    }
    
    public void deleteBatch(List<Long> id) {
		temStationYearDao.deleteBatch(id);
	}

    public TemStationYear findOne(TemStationYear temStationYear) {
        Specification<TemStationYear> spec = RepositoryUtil.getSpecification(temStationYear);
        TemStationYear findOne = temStationYearDao.findOne(spec);
        return findOne;
    }

    public List<TemStationYear> findAll(List<Long> list) {
        return temStationYearDao.findAll(list);
    }

    public Page<TemStationYear> findAll(TemStationYear temStationYear, Pageable pageable) {
        Specification<TemStationYear> spec = RepositoryUtil.getSpecification(temStationYear);
        Page<TemStationYear> findAll = temStationYearDao.findAll(spec, pageable);
        return findAll;
    }

    public List<TemStationYear> findAll(TemStationYear temStationYear) {
        Specification<TemStationYear> spec = RepositoryUtil.getSpecification(temStationYear);
        return temStationYearDao.findAll(spec);
    }
}

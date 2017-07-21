package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.QualificationsDao;
import com.yn.model.Qualifications;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class QualificationsService {
	@Autowired
	QualificationsDao qualificationsDao;

	public Qualifications findOne(Long id) {
		return qualificationsDao.findOne(id);
	}

	public void save(Qualifications qualifications) {
		if (qualifications.getId() != null) {
			Qualifications one = qualificationsDao.findOne(qualifications.getId());
			try {
				BeanCopy.beanCopy(qualifications, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			qualificationsDao.save(one);
		} else {
			qualificationsDao.save(qualifications);
		}
		System.out.println();
	}

	public void delete(Long id) {
		qualificationsDao.delete(id);
	}

	public void deleteBatch(List<Long> id) {
		qualificationsDao.deleteBatch(id);
	}

	public Qualifications findOne(Qualifications qualifications) {
		Specification<Qualifications> spec = RepositoryUtil.getSpecification(qualifications);
		Qualifications findOne = qualificationsDao.findOne(spec);
		return findOne;
	}

	public List<Qualifications> findAll(List<Long> list) {
		return qualificationsDao.findAll(list);
	}

	public Page<Qualifications> findAll(Qualifications qualifications, Pageable pageable) {
		Specification<Qualifications> spec = RepositoryUtil.getSpecification(qualifications);
		Page<Qualifications> findAll = qualificationsDao.findAll(spec, pageable);
		return findAll;
	}

	public List<Qualifications> findAll(Qualifications qualifications) {
		Specification<Qualifications> spec = RepositoryUtil.getSpecification(qualifications);
		return qualificationsDao.findAll(spec);
	} 
}

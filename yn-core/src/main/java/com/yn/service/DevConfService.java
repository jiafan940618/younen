package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.DevConfDao;
import com.yn.model.DevConf;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class DevConfService {
	@Autowired
	DevConfDao devConfDao;

	public DevConf findOne(Long rowId) {
		return devConfDao.findOne(rowId);
	}

	public void save(DevConf devConf) {
		if (devConf.getRowId() != null) {
			DevConf one = devConfDao.findOne(devConf.getRowId());
			try {
				BeanCopy.beanCopy(devConf, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			devConfDao.save(one);
		} else {
			devConfDao.save(devConf);
		}
		System.out.println();
	}

	public void delete(Long rowId) {
		devConfDao.delete(rowId);
	}

	public void deleteBatch(List<Long> rowIds) {
		devConfDao.deleteBatch(rowIds);
	}

	public DevConf findOne(DevConf devConf) {
		Specification<DevConf> spec = RepositoryUtil.getSpecification(devConf);
		DevConf findOne = devConfDao.findOne(spec);
		return findOne;
	}

	public List<DevConf> findAll(List<Long> list) {
		return devConfDao.findAll(list);
	}

	public Page<DevConf> findAll(DevConf devConf, Pageable pageable) {
		Specification<DevConf> spec = RepositoryUtil.getSpecification(devConf);
		Page<DevConf> findAll = devConfDao.findAll(spec, pageable);
		return findAll;
	}

	public List<DevConf> findAll(DevConf devConf) {
		Specification<DevConf> spec = RepositoryUtil.getSpecification(devConf);
		return devConfDao.findAll(spec);
	} 
}

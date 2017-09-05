package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.SolarPanelDao;
import com.yn.model.SolarPanel;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class SolarPanelService {
	@Autowired
	SolarPanelDao solarPanelDao;

	public SolarPanel findOne(Long id) {
		return solarPanelDao.findOne(id);
	}

	public void save(SolarPanel solarPanel) {
		if (solarPanel.getId() != null) {
			SolarPanel one = solarPanelDao.findOne(solarPanel.getId());
			try {
				BeanCopy.beanCopy(solarPanel, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			solarPanelDao.save(one);
		} else {
			solarPanelDao.save(solarPanel);
		}
	}

	public void delete(Long id) {
		solarPanelDao.delete(id);
	}

	public void deleteBatch(List<Long> id) {
		solarPanelDao.deleteBatch(id);
	}

	public SolarPanel findOne(SolarPanel solarPanel) {
		Specification<SolarPanel> spec = RepositoryUtil.getSpecification(solarPanel);
		SolarPanel findOne = solarPanelDao.findOne(spec);
		return findOne;
	}

	public List<SolarPanel> findAll(List<Long> list) {
		return solarPanelDao.findAll(list);
	}

	public Page<SolarPanel> findAll(SolarPanel solarPanel, Pageable pageable) {
		Specification<SolarPanel> spec = RepositoryUtil.getSpecification(solarPanel);
		Page<SolarPanel> findAll = solarPanelDao.findAll(spec, pageable);
		return findAll;
	}

	public List<SolarPanel> findAll(SolarPanel solarPanel) {
		Specification<SolarPanel> spec = RepositoryUtil.getSpecification(solarPanel);
		return solarPanelDao.findAll(spec);
	}
}

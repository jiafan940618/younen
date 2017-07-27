package com.yn.service;

import java.util.List;
import java.util.Set;

import com.yn.dao.ApolegamyDao;
import com.yn.enums.DeleteEnum;
import com.yn.model.Apolegamy;
import com.yn.model.QualificationsServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.QualificationsDao;
import com.yn.model.Qualifications;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class QualificationsService {
	@Autowired
	QualificationsDao qualificationsDao;
    @Autowired
    ApolegamyService apolegamyService;
    @Autowired
    QualificationsServerService qualificationsServerService;
    @Autowired
    ApolegamyDao apolegamyDao;

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

	@Transactional
	public void delete(Long id) {
	    // 1.删除资质
		qualificationsDao.delete(id);

		// 2.删除服务商的资质
		Qualifications qualifications = qualificationsDao.findOne(id);
		if (qualifications.getDel().equals(DeleteEnum.DEL.getCode())) {
            QualificationsServer qualificationsServerE = new QualificationsServer();
            qualificationsServerE.setQualificationsId(id);
            List<QualificationsServer> qualificationsServerList = qualificationsServerService.findAll(qualificationsServerE);
            for (QualificationsServer qualificationsServer : qualificationsServerList) {
                qualificationsServerService.delete(qualificationsServer.getId());
            }

            // 3.删除资质的选配项目
		    if (!CollectionUtils.isEmpty(qualifications.getApolegamy())) {
		        for (Apolegamy apolegamy : qualifications.getApolegamy()) {
                    apolegamyService.delete(apolegamy.getId());
                }
            }
        }
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

	@Transactional
    public void saveWithApolegamy(Qualifications qualifications) {
        Set<Apolegamy> apolegamies = qualifications.getApolegamy();

        // 保存资质
        qualifications.setApolegamy(null);
        Qualifications result = qualificationsDao.save(qualifications);

        Long qulificationsId = result.getId();

        // 更新资质选配项目的资质id
        if (!CollectionUtils.isEmpty(apolegamies)) {
            for (Apolegamy apolegamy : apolegamies) {
                apolegamy.setQualificationsId(qulificationsId);
                apolegamyService.save(apolegamy);
            }
        } else {
            Apolegamy apolegamyE = new Apolegamy();
            apolegamyE.setQualificationsId(qulificationsId);
            List<Apolegamy> apolegamyList = apolegamyService.findAll(apolegamyE);
            for (Apolegamy apolegamy : apolegamyList) {
                apolegamy.setQualificationsId(null);
                apolegamyDao.save(apolegamy);
            }
        }

    }
}

package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.AmPhaseRecordDao;
import com.yn.model.AmPhaseRecord;
import com.yn.utils.RepositoryUtil;

@Service
public class AmPhaseRecordService {
    @Autowired
    AmPhaseRecordDao amPhaseRecordDao;

    public AmPhaseRecord findOne(Long id) {
        return amPhaseRecordDao.findOne(id);
    }

    public void save(AmPhaseRecord amPhaseRecord) {
        amPhaseRecordDao.save(amPhaseRecord);
    }

    public void delete(Long id) {
        amPhaseRecordDao.delete(id);
    }
    
    public void deleteBatch(List<Long> id) {
		amPhaseRecordDao.deleteBatch(id);
	}

    public AmPhaseRecord findOne(AmPhaseRecord amPhaseRecord) {
        Specification<AmPhaseRecord> spec = RepositoryUtil.getSpecification(amPhaseRecord);
        AmPhaseRecord findOne = amPhaseRecordDao.findOne(spec);
        return findOne;
    }

    public List<AmPhaseRecord> findAll(List<Long> list) {
        return amPhaseRecordDao.findAll(list);
    }

    public Page<AmPhaseRecord> findAll(AmPhaseRecord amPhaseRecord, Pageable pageable) {
        Specification<AmPhaseRecord> spec = RepositoryUtil.getSpecification(amPhaseRecord);
        Page<AmPhaseRecord> findAll = amPhaseRecordDao.findAll(spec, pageable);
        return findAll;
    }

    public List<AmPhaseRecord> findAll(AmPhaseRecord amPhaseRecord) {
        Specification<AmPhaseRecord> spec = RepositoryUtil.getSpecification(amPhaseRecord);
        return amPhaseRecordDao.findAll(spec);
    }
}

package com.yn.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yn.dao.AmmeterRecordDao;
import com.yn.dao.mapper.AmmeterRecordMapper;
import com.yn.model.AmmeterRecord;
import com.yn.utils.BeanCopy;
import com.yn.utils.DateUtil;
import com.yn.utils.ObjToMap;

@Service
public class AmmeterRecordService {
	@Autowired
	AmmeterRecordDao ammeterRecordDao;
	@Autowired
	AmmeterRecordMapper ammeterRecordMapper;

	public AmmeterRecord findOne(Long id) {
		return ammeterRecordDao.findOne(id);
	}

	public void save(AmmeterRecord ammeterRecord) {
		if (ammeterRecord.getId() != null) {
			AmmeterRecord one = ammeterRecordDao.findOne(ammeterRecord.getId());
			try {
				BeanCopy.beanCopy(ammeterRecord, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ammeterRecordDao.save(one);
		} else {
			ammeterRecordDao.save(ammeterRecord);
		}
	}

	public void saveByMapper(AmmeterRecord ammeterRecord) {
		if (ammeterRecord.getId() != null) {
			AmmeterRecord one = ammeterRecordDao.findOne(ammeterRecord.getId());
			try {
				BeanCopy.beanCopy(ammeterRecord, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ammeterRecordMapper.updateByPrimaryKeySelective(one);
			System.out.println("AmmeterJob--> AmmeterRecord更新成功！-->"+new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
		} else {
			ammeterRecordMapper.insert(ammeterRecord);
			System.out.println("AmmeterJob--> AmmeterRecord新增成功！-->"+new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
		}
	}

	public void delete(Long id) {
		ammeterRecordDao.delete(id);
	}

	public void deleteBatch(List<Long> id) {
		ammeterRecordDao.deleteBatch(id);
	}

	public AmmeterRecord findOne(AmmeterRecord ammeterRecord) {
		Specification<AmmeterRecord> spec = getSpecification(ammeterRecord);
		AmmeterRecord findOne = ammeterRecordDao.findOne(spec);
		return findOne;
	}

	public List<AmmeterRecord> findAll(List<Long> list) {
		return ammeterRecordDao.findAll(list);
	}

	public Page<AmmeterRecord> findAll(AmmeterRecord ammeterRecord, Pageable pageable) {
		Specification<AmmeterRecord> spec = getSpecification(ammeterRecord);
		Page<AmmeterRecord> findAll = ammeterRecordDao.findAll(spec, pageable);
		return findAll;
	}

	public List<AmmeterRecord> findAll(AmmeterRecord ammeterRecord) {
		Specification<AmmeterRecord> spec = getSpecification(ammeterRecord);
		return ammeterRecordDao.findAll(spec);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Specification<AmmeterRecord> getSpecification(AmmeterRecord ammeterRecord) {
		ammeterRecord.setDel(0);
		Map<String, Object> objectMap = ObjToMap.getObjectMap(ammeterRecord);
		return (Root<AmmeterRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

			Predicate conjunction = cb.conjunction();
			List<Expression<Boolean>> expressions = conjunction.getExpressions();
			Iterator<Entry<String, Object>> iterator = objectMap.entrySet().iterator();

			while (iterator.hasNext()) {
				Entry<String, Object> entry = iterator.next();
				if (!entry.getKey().equals("query") && !entry.getKey().equals("queryStartDtm")
						&& !entry.getKey().equals("queryEndDtm")) {
					Object value = entry.getValue();
					if (value instanceof Map) {
						Iterator<Entry<String, Object>> iterator1 = ((Map) value).entrySet().iterator();
						while (iterator1.hasNext()) {
							Entry<String, Object> entry1 = iterator1.next();
							expressions.add(cb.equal(root.get(entry.getKey()).get(entry1.getKey()), entry1.getValue()));
						}
					} else {
						expressions.add(cb.equal(root.get(entry.getKey()), value));
					}
				}
			}

			// 根据日期筛选
			String queryStartDtm = ammeterRecord.getQueryStartDtm();
			String queryEndDtm = ammeterRecord.getQueryEndDtm();
			if (!StringUtils.isEmpty(queryStartDtm)) {
				expressions.add(cb.greaterThanOrEqualTo(root.get("createDtm"),
						DateUtil.parseString(queryStartDtm, DateUtil.yyyy_MM_dd_HHmmss)));
			}
			if (!StringUtils.isEmpty(queryEndDtm)) {
				expressions.add(cb.lessThan(root.get("createDtm"),
						DateUtil.parseString(queryEndDtm, DateUtil.yyyy_MM_dd_HHmmss)));
			}

			return conjunction;
		};
	}
}

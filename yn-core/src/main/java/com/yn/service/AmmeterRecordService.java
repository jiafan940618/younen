package com.yn.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.yn.model.ElecDataDay;
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
	
	public List<AmmeterRecord> findByMapper(AmmeterRecord ammeterRecord) {
		Map<String, Object> map = new HashMap<>();
		map.put("type", ammeterRecord.getType());
		map.put("queryStartDtm", ammeterRecord.getQueryStartDtm());
		map.put("queryEndDtm", ammeterRecord.getQueryEndDtm());
		map.put("stationId", ammeterRecord.getStationId());
		List<String> lStrings=new ArrayList<>();
		if ("-1".equals(ammeterRecord.getQuery())) {
			ammeterRecord.setQuery(null);
            if ("-1".equals(ammeterRecord.getStatusCode())) {
            	ammeterRecord.setStatusCode(null);
            	map.put("statusCode",null );
			}else {
				lStrings.add(ammeterRecord.getStatusCode());
				map.put("statusCode",lStrings);
			}
		}else if ("1".equals(ammeterRecord.getQuery())) {
			
				lStrings.add("0x0");
				lStrings.add("0x8");
				lStrings.add("0x80");
				lStrings.add("0x00");
				map.put("statusCode",lStrings );
	
		}else if ("2".equals(ammeterRecord.getQuery())) {
			
			if ("-1".equals(ammeterRecord.getStatusCode())) {
				lStrings.add("0x01");
				lStrings.add("0x02");
				lStrings.add("0x04");
				lStrings.add("0x08");
				lStrings.add("0x10");
				lStrings.add("0x20");
				lStrings.add("0x40");
				lStrings.add("0x18");
				map.put("statusCode",lStrings );
			}else{
				lStrings.add(ammeterRecord.getStatusCode());
				map.put("statusCode",lStrings);
			}		
	}
		
		
		
		
//		if (ammeterRecord.getStatusCode()!=null) {
//			if ("0x0".equals(ammeterRecord.getStatusCode())) {
//				lStrings.add("0x0");
//				lStrings.add("0x8");
//				lStrings.add("0x80");
//				lStrings.add("0x00");
//				map.put("statusCode",lStrings );
//			}else {
//				lStrings.add(ammeterRecord.getStatusCode());
//				map.put("statusCode",lStrings);
//			}
//		  }else {
//				if ("1".equals(ammeterRecord.getQuery())) {
//					lStrings.add("0x0");
//					lStrings.add("0x8");
//					lStrings.add("0x80");
//					lStrings.add("0x00");
//					map.put("statusCode",lStrings );
//				}else if("2".equals(ammeterRecord.getQuery())){
//					lStrings.add("0x01");
//					lStrings.add("0x02");
//					lStrings.add("0x04");
//					lStrings.add("0x08");
//					lStrings.add("0x10");
//					lStrings.add("0x20");
//					lStrings.add("0x40");
//					lStrings.add("0x18");
//					map.put("statusCode",lStrings );
//				}
//			}
		List<AmmeterRecord> list = ammeterRecordMapper.selectByQuery(map);
		List<AmmeterRecord> listAmmeterRecord = new ArrayList<>();
		for (AmmeterRecord ammeterRecord2 : list) {
			if("0x0".equals(ammeterRecord2.getStatusCode())){
				ammeterRecord2.setStationCode("正常");
				ammeterRecord2.setQuery("正常发电");
			}else if ("0x01".equals(ammeterRecord2.getStatusCode())) {
				ammeterRecord2.setStationCode("计量芯片异常计量芯片异常");
				ammeterRecord2.setQuery("发电异常");
				
			}else if ("0x02".equals(ammeterRecord2.getStatusCode())) {
				ammeterRecord2.setStationCode("参数存储区异常");
				ammeterRecord2.setQuery("发电异常");
				
			}else if ("0x04".equals(ammeterRecord2.getStatusCode())) {
				ammeterRecord2.setStationCode("冻结数据存储区异常");
				ammeterRecord2.setQuery("发电异常");
				
			}else if ("0x08".equals(ammeterRecord2.getStatusCode())) {
				ammeterRecord2.setStationCode("电压相序出错");
				ammeterRecord2.setQuery("发电异常");
				
			}else if ("0x10".equals(ammeterRecord2.getStatusCode())) {
				ammeterRecord2.setStationCode("电流相序出错");
				ammeterRecord2.setQuery("发电异常");
				
			}else if ("0x8".equals(ammeterRecord2.getStatusCode())) {
				ammeterRecord2.setStationCode("正常");
				ammeterRecord2.setQuery("正常发电");
				
			}else if ("0x80".equals(ammeterRecord2.getStatusCode())) {
				ammeterRecord2.setStationCode("正常");
				ammeterRecord2.setQuery("正常发电");
				
			}else if ("0x00".equals(ammeterRecord2.getStatusCode())) {
				ammeterRecord2.setStationCode("正常");
				ammeterRecord2.setQuery("正常发电");
				
			}else if ("0x20".equals(ammeterRecord2.getStatusCode())) {
				ammeterRecord2.setStationCode("失压");
				ammeterRecord2.setQuery("发电异常");
				
			}else if ("0x40".equals(ammeterRecord2.getStatusCode())) {
				ammeterRecord2.setStationCode("失流");
				ammeterRecord2.setQuery("发电异常");
				
			}else if ("0x18".equals(ammeterRecord2.getStatusCode())) {
				ammeterRecord2.setStationCode("未知原因");
				ammeterRecord2.setQuery("发电异常"); 	
			}
			
			listAmmeterRecord.add(ammeterRecord2);	
		}	
		return listAmmeterRecord;
	}	
}

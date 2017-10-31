package com.yn.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

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

import com.yn.dao.AmmeterDao;
import com.yn.dao.ElecDataDayDao;
import com.yn.dao.ElecDataMonthDao;
import com.yn.dao.ElecDataYearDao;
import com.yn.dao.mapper.ElecDataDayMapper;
import com.yn.model.ElecDataDay;
import com.yn.model.ElecDataDayExample;
import com.yn.model.ElecDataHour;
import com.yn.model.ElecDataDayExample.Criteria;
import com.yn.model.ElecDataMonth;
import com.yn.model.ElecDataYear;
import com.yn.model.Station;
import com.yn.utils.BeanCopy;
import com.yn.utils.DateUtil;
import com.yn.utils.NumberUtil;
import com.yn.utils.ObjToMap;
import com.yn.utils.RepositoryUtil;

@Service
public class ElecDataDayService {
	@Autowired
	ElecDataDayDao elecDataDayDao;
	@Autowired
	ElecDataDayMapper elecDataDayMapper;
	@Autowired
	ElecDataHourService elecDataHourService;
	@Autowired
	AmmeterDao ammeterDao;
	@Autowired
	ElecDataMonthDao elecDataMonthDao;
	@Autowired
	ElecDataYearDao elecDataYearDao;

	public List<ElecDataDay> selectByExample(ElecDataDayExample example) {
		return elecDataDayMapper.selectByExample(example);
	}

	public List<ElecDataDay> findByCondition(ElecDataDay elecDataDay) {
		ElecDataDayExample example = new ElecDataDayExample();
		Criteria criteria = example.createCriteria();
		if (elecDataDay.getRecordTime() != null) {
			criteria.andRecordTimeEqualTo(elecDataDay.getRecordTime());
		}
		if (elecDataDay.getAmmeterCode() != null) {
			criteria.andAmmeterCodeEqualTo(elecDataDay.getAmmeterCode());
		}
		return elecDataDayMapper.selectByExample(example);
	}

	public ElecDataDay findOne(Long id) {
		return elecDataDayDao.findOne(id);
	}

	public void save(ElecDataDay elecDataDay) {
		if (elecDataDay.getId() != null) {
			ElecDataDay one = elecDataDayDao.findOne(elecDataDay.getId());
			try {
				BeanCopy.beanCopy(elecDataDay, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			elecDataDayDao.save(one);
		} else {
			elecDataDayDao.save(elecDataDay);
		}
	}

	public void saveByMapper(ElecDataDay elecDataDay) {
		if (elecDataDay.getId() != null) {
			ElecDataDay one = elecDataDayDao.findOne(elecDataDay.getId());
			try {
				BeanCopy.beanCopy(elecDataDay, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			elecDataDayMapper.updateByPrimaryKeySelective(one);
		} else {
			elecDataDayMapper.insert(elecDataDay);
		}
	}

	public void delete(Long id) {
		elecDataDayDao.delete(id);
	}

	public void deleteBatch(List<Long> id) {
		elecDataDayDao.deleteBatch(id);
	}

	public ElecDataDay findOne(ElecDataDay elecDataDay) {
		Specification<ElecDataDay> spec = RepositoryUtil.getSpecification(elecDataDay);
		ElecDataDay findOne = elecDataDayDao.findOne(spec);
		return findOne;
	}

	public List<ElecDataDay> findAll(List<Long> list) {
		return elecDataDayDao.findAll(list);
	}

	public Page<ElecDataDay> findAll(ElecDataDay elecDataDay, Pageable pageable) {
		Specification<ElecDataDay> spec = RepositoryUtil.getSpecification(elecDataDay);
		Page<ElecDataDay> findAll = elecDataDayDao.findAll(spec, pageable);
		return findAll;
	}

	public List<ElecDataDay> findAll(ElecDataDay elecDataDay) {
		Specification<ElecDataDay> spec = RepositoryUtil.getSpecification(elecDataDay);
		return elecDataDayDao.findAll(spec);
	}

	public ElecDataDay findHuanbao(Map<String, Object> map) {

		return elecDataDayMapper.findHuanbao(map);
	}

	/**
	 * 用户每月发电量
	 *
	 * @param stations
	 * @return
	 */
	public List<Map<Object, Object>> monthKwh(List<Station> stations) {
		Map<Object, Object> objectMap = new TreeMap<Object, Object>();
		Map<Object, Object> linkHashMap = new LinkedHashMap<>();
		List<Map<Object, Object>> lists = new ArrayList<>();
		List<Map<Object, Object>> listsMap = new ArrayList<>();
		for (Station station : stations) {
			List<Long> ammeterCodes = ammeterDao.selectAmmeterCode(station.getId());
			if (ammeterCodes.size() > 0) {
				List<Object[]> kwh = elecDataDayDao.sumMonthKwh(ammeterCodes);
				for (Object[] objects : kwh) {
					Map<Object, Object> map = new HashMap<>();
					map.put("create_dtm", objects[0]);
					map.put("kwh", objects[1]);
					lists.add(map);
				}
			}

		}
		for (Map<Object, Object> map : lists) {
			if (!objectMap.containsKey(map.get("create_dtm"))) {
				// objectMap.put(map.get("create_dtm"), map.get("kwh"));
				objectMap.put(map.get("create_dtm"), map.get("kwh"));
			} else {

				// double
				// kwh=(double)objectMap.get(map.get("create_dtm"))+(double)map.get("kwh");
				// objectMap.put(map.get("create_dtm"), (Object)kwh);
				// System.out.println(objectMap.get(map.get("create_dtm")));
				BigDecimal kwh = new BigDecimal(Double.parseDouble(objectMap.get(map.get("create_dtm")).toString())
						+ Double.parseDouble(map.get("kwh").toString()));
				objectMap.put(map.get("create_dtm"), kwh);

				// String
				// kwh=objectMap.get(map.get("create_dtm"))+","+map.get("kwh").toString()
				// ;
				// String[] split = kwh.split(",");
				// objectMap.put(map.get("create_dtm"),kwh);
			}

		}
		Object[] key = objectMap.keySet().toArray();
		for (int i = 0; i < key.length; i++) {
			Map<Object, Object> listMap = new LinkedHashMap<>();
			linkHashMap.put(key[i], objectMap.get(key[i]));
			listMap.put("createDtm", key[i]);
			listMap.put("kwh", NumberUtil.accurateToTwoDecimal(Double.parseDouble(objectMap.get(key[i]).toString())));
			listsMap.add(listMap);
		}
		return listsMap;
	}

	/**
	 * 用户每月发电量
	 *
	 * @param stations
	 * @return
	 */
	public List<Map<Object, Object>> numKwh(List<Station> stations, Integer type, String dateStr) {
		Map<Object, Object> objectMap = new TreeMap<Object, Object>();
		Map<Object, Object> linkHashMap = new LinkedHashMap<>();
		List<Map<Object, Object>> lists = new ArrayList<>();
		List<Map<Object, Object>> listsMap = new ArrayList<>();

		String dateFormat = "";
		if (type == 0) {
			dateFormat = "%Y";
		} else if (type == 1) {
			dateFormat = "%Y-%m";
		} else if (type == 2) {
			dateFormat = "%Y-%m-%d";
		}
		for (Station station : stations) {
			List<Long> ammeterCodes = ammeterDao.selectAmmeterCode(station.getId());
			List<Object[]> list = elecDataDayDao.sumKwh(ammeterCodes, dateFormat, dateStr);
			for (Object[] objects : list) {
				Map<Object, Object> map = new HashMap<>();
				map.put("create_dtm", objects[0]);
				map.put("kwh", objects[1]);
				lists.add(map);
			}

		}
		for (Map<Object, Object> map : lists) {
			if (!objectMap.containsKey(map.get("create_dtm"))) {

				objectMap.put(map.get("create_dtm"), map.get("kwh"));
			} else {
				BigDecimal kwh = new BigDecimal(Double.parseDouble(objectMap.get(map.get("create_dtm")).toString())
						+ Double.parseDouble(map.get("kwh").toString()));
				objectMap.put(map.get("create_dtm"), kwh);
			}

		}
		Object[] key = objectMap.keySet().toArray();
		for (int i = 0; i < key.length; i++) {
			Map<Object, Object> listMap = new LinkedHashMap<>();
			linkHashMap.put(key[i], objectMap.get(key[i]));
			listMap.put("createDtm", key[i]);
			listMap.put("kwh", NumberUtil.accurateToTwoDecimal(Double.parseDouble(objectMap.get(key[i]).toString())));
			listsMap.add(listMap);
		}

		return listsMap;
	}

	/**
	 * 用户发电/用电统计图
	 */
	public Map<String, Object> workUseCount(Long stationId, Integer type) {
		List<Long> ammeterCodes = ammeterDao.selectAmmeterCode(stationId);
		List<Object[]> list = elecDataDayDao.workUseCount(ammeterCodes, type);
		Map<Object, Object> linkHashMap = new LinkedHashMap<>();
		List<Map<Object, Object>> listsMap = new ArrayList<>();
		List<Map<Object, Object>> lists = new ArrayList<>();
		Map<Object, Object> objectMap = new TreeMap<Object, Object>();
		for (Object[] objects : list) {
			Map<Object, Object> map = new HashMap<>();
			map.put("create_dtm", objects[0]);
			map.put("kwh", objects[1]);
			lists.add(map);
		}

		for (Map<Object, Object> map : lists) {
			if (!objectMap.containsKey(map.get("create_dtm"))) {

				objectMap.put(map.get("create_dtm"), map.get("kwh"));
			} else {
				BigDecimal kwh = new BigDecimal(Double.parseDouble(objectMap.get(map.get("create_dtm")).toString())
						+ Double.parseDouble(map.get("kwh").toString()));
				objectMap.put(map.get("create_dtm"), kwh);
			}

		}
		Object[] key = objectMap.keySet().toArray();
		for (int i = 0; i < key.length; i++) {
			Map<Object, Object> listMap = new LinkedHashMap<>();
			linkHashMap.put(key[i], objectMap.get(key[i]));
			listMap.put("createDtm", key[i]);
			listMap.put("kwh", NumberUtil.accurateToTwoDecimal(Double.parseDouble(objectMap.get(key[i]).toString())));
			listsMap.add(listMap);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("workUseCount", listsMap);
		map.put("thisYearKwh", NumberUtil.accurateToTwoDecimal(elecDataHourService.thisYearKwh(stationId, type)));
		map.put("thisMonthKwh", NumberUtil.accurateToTwoDecimal(elecDataHourService.thisMonthKwh(stationId, type)));
		map.put("lastYearKwh", NumberUtil.accurateToTwoDecimal(elecDataHourService.lastYearKwh(stationId, type)));
		map.put("lastMonthKwh", NumberUtil.accurateToTwoDecimal(elecDataHourService.lastMonthKwh(stationId, type)));
		map.put("todayKwh", NumberUtil.accurateToTwoDecimal(elecDataHourService.todayKwh(stationId, type)));
		map.put("yesterdayKwh", NumberUtil.accurateToTwoDecimal(elecDataHourService.yesterdayKwh(stationId, type)));
		return map;
	}

	public Page<ElecDataDay> listCount(ElecDataDay elecDataDay, Pageable pageable) {
		Specification<ElecDataDay> spec = getSpecification(elecDataDay);
		Page<ElecDataDay> findAll = elecDataDayDao.findAll(spec, pageable);

		return findAll;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Specification<ElecDataDay> getSpecification(ElecDataDay elecDataDay) {
		elecDataDay.setDel(0);
		Map<String, Object> objectMap = ObjToMap.getObjectMap(elecDataDay);
		return (Root<ElecDataDay> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

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
			String queryStartDtm = elecDataDay.getQueryStartDtm();
			String queryEndDtm = elecDataDay.getQueryEndDtm();
			if (!StringUtils.isEmpty(queryStartDtm)) {
				expressions.add(cb.greaterThanOrEqualTo(root.get("createDtm"),
						DateUtil.parseString(queryStartDtm, DateUtil.yyyy_MM_dd)));
			}
			if (!StringUtils.isEmpty(queryEndDtm)) {
				expressions.add(
						cb.lessThan(root.get("createDtm"), DateUtil.parseString(queryEndDtm, DateUtil.yyyy_MM_dd)));
			}

			return conjunction;
		};
	}

	public List<ElecDataDay> findByMapper(ElecDataDay elecDataDay, Long stationId) {
		Map<String, Object> map = new HashMap<>();
		List<Long> ammeterCodes = ammeterDao.selectAmmeterCode(stationId);
		map.put("ammeterCode", ammeterCodes);
		map.put("queryStartDtm", elecDataDay.getQueryStartDtm());
		map.put("queryEndDtm", elecDataDay.getQueryEndDtm());
		map.put("type", elecDataDay.getType());
		List<ElecDataDay> list = elecDataDayMapper.selectByQuery(map);
		return list;
	}

	/**
	 * 移动端获取每天每月每年发电详情
	 */
	public Map<String, Object> getElecDetailByStationCode(Long stationId, Integer type) {
		Map<String, Object> maps = new HashMap<>();

		List<Long> ammeterCodes = ammeterDao.selectAmmeterCode(stationId);
		Date end = new Date();
		// 获取当前天的发电详情
		Date[] todaySpace = DateUtil.getThisMonthSpace();
		Date dayStart = todaySpace[0];
		List<ElecDataDay> elecDataDays = elecDataDayDao.findByDays(ammeterCodes, type, dayStart, end);
		double historyTotalElec = elecDataDayDao.sumKwhByDays(dayStart, end, type, ammeterCodes);
		maps.put("dayList", elecDataDays);
		maps.put("historyTotalElec", NumberUtil.accurateToTwoDecimal(historyTotalElec));

		// 获取当前月的发电详情
		Date[] monthSpace = DateUtil.getThisYearSpace();
		Date monthStart = monthSpace[0];
		List<ElecDataMonth> elecDataMonths = elecDataMonthDao.findByMonths(ammeterCodes, type, monthStart, end);
		double monthTotalElec = elecDataMonthDao.sumKwhByMonths(dayStart, end, type, ammeterCodes);
		maps.put("monthList", elecDataMonths);
		maps.put("monthTotalElec", NumberUtil.accurateToTwoDecimal(monthTotalElec));
		// 获取当前年的发电详情
		List<ElecDataYear> elecDataYear = elecDataYearDao.findByYear(ammeterCodes, type);
		double yearTotalElec = elecDataYearDao.sumKwhByYear(type, ammeterCodes);
		maps.put("yearList", elecDataYear);
		maps.put("yearTotalElec", NumberUtil.accurateToTwoDecimal(yearTotalElec));
		return maps;
	}

}

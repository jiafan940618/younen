package com.yn.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.yn.dao.StationDao;
import com.yn.dao.mapper.ElecDataDayMapper;
import com.yn.model.Ammeter;
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
	private static final Logger logger = LoggerFactory.getLogger(ElecDataDayService.class);
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
	@Autowired
	StationDao stationDao;

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
		if (elecDataDay.getwAddr() > 0) {
			return;
		}
		if (elecDataDay.getId() != null) {
			ElecDataDay one = elecDataDayDao.findOne(elecDataDay.getId());
			ElecDataDayExample ex = new ElecDataDayExample();
			Criteria criteria = ex.createCriteria();
			criteria.andAmmeterCodeEqualTo(one.getAmmeterCode());
			criteria.andRecordTimeEqualTo(one.getRecordTime());
			List<ElecDataDay> selectByExample = elecDataDayMapper.selectByExample(ex);
			Double a = 0.0;
			for (ElecDataDay elecDataDay2 : selectByExample) {
				a += elecDataDay2.getKwh();
			}
			try {
				BeanCopy.beanCopy(elecDataDay, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// elecDataDay.setKwh(a);
			elecDataDayMapper.updateByPrimaryKeySelective(elecDataDay);
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

		List<Long> ammeterCodes = ammeterDao.selectAllAmmeter();
		if (ammeterCodes.size() > 0) {
			List<Object[]> kwh = elecDataDayDao.sumMonthKwh(ammeterCodes);
			for (Object[] objects : kwh) {
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
		map.put("thisWeekKwh", NumberUtil.accurateToTwoDecimal(elecDataHourService.thisWeekKwh(stationId, type)));
		map.put("lastWeekKwh", NumberUtil.accurateToTwoDecimal(elecDataHourService.lastWeekKwh(stationId, type)));
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
		List<ElecDataDay> listElecDataDay=new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			ElecDataDay elecDataDay2=list.get(i);
			elecDataDay2.setId(i+1l);
			listElecDataDay.add(elecDataDay2);	
		}
		return listElecDataDay;
	}

	/**
	 * 移动端获取每天每月每年发电详情
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 */
	public Map<String, Object> getElecDetailByStationCode(Long stationId, Integer type) throws NumberFormatException, ParseException {
     	Map<String, Object> maps = new HashMap<>();
		List<Long> ammeterCodes = ammeterDao.selectAmmeterCode(stationId);
		Date [] dateYesterday = DateUtil.getYesterdaySpace();
		Date endStart=dateYesterday[1];
		// 获取每天的发电详情
		List<Map<String, Object>> dayInfo = dayInfo(ammeterCodes, type);
		if (type == 1) {
			double workTotalkwh = ammeterDao.workTotalkwh(stationId);
			logger.info("-------------------workTotalkwh:" + workTotalkwh);
			double initTotalkwh = ammeterDao.initTotalkwh(stationId);
			logger.info("-------------------initTotalkwh:" + initTotalkwh);
			double historyTotalElec = workTotalkwh + initTotalkwh;
			maps.put("historyTotalElec", NumberUtil.accurateToTwoDecimal(historyTotalElec));
		}else if (type == 2) {
			double historyTotalElec=elecDataDayDao.sumKwhByHistory(type, ammeterCodes);
		    maps.put("historyTotalElec", NumberUtil.accurateToTwoDecimal(historyTotalElec));
		}
		  maps.put("dayList", dayInfo);
		// 获取当每月的发电详情
		List<Map<String, Object>> monthInfo = monthInfo(ammeterCodes, type);
		Date[] monthSpace = DateUtil.getThisYearSpace();
		Date monthStartTime = monthSpace[0];
		String monthStart = new SimpleDateFormat("yyyy-MM").format(monthStartTime);
		monthStart += "-01";
		logger.info("-------------------monthStart:" + monthStart);
		String endMonth = new SimpleDateFormat("yyyy-MM-dd").format(endStart);
		double monthTotalElec = elecDataDayDao.sumKwhByDays(monthStart, endMonth, type, ammeterCodes);
		maps.put("monthList", monthInfo);
		maps.put("monthTotalElec", NumberUtil.accurateToTwoDecimal(monthTotalElec));
		// 获取当每年的发电详情
		Map<String, Object> yearInfo = yearInfo(ammeterCodes, type, stationId);
		double yearTotalElec = (double) yearInfo.get("yearTotalElec");
		maps.put("yearList", yearInfo.get("listYear"));
		maps.put("yearTotalElec", NumberUtil.accurateToTwoDecimal(yearTotalElec));

		return maps;
	}

	/**
	 * 移动端获取每天发电详情
	 * 
	 * @throws ParseException
	 * @throws NumberFormatException
	 */

	public List<Map<String, Object>> dayInfo(List<Long> ammeterCodes, Integer type)
			throws NumberFormatException, ParseException {
		List<Map<String, Object>> listDay = new ArrayList<>();
		Date [] dateYesterday = DateUtil.getYesterdaySpace();
		Date endStart=dateYesterday[1];
		Date[] todaySpace = DateUtil.getThisMonthSpace();
		Date dayStartTime = todaySpace[0];
		String dayStart = new SimpleDateFormat("yyyy-MM-dd").format(dayStartTime);
		String end = new SimpleDateFormat("yyyy-MM-dd").format(endStart);
		SimpleDateFormat dFormat = new SimpleDateFormat("dd");
		List<Integer> recordTimeList = new ArrayList<>();
		Object[] elecDataDays = elecDataDayDao.findByDays(ammeterCodes, type, dayStart, end);
		String nowTime = dFormat.format(endStart);
		Integer num = Integer.parseInt(nowTime);
		List<Map<String, Object>> listDays = new ArrayList<>();
		for (Object ElecDataDay : elecDataDays) {
			Object[] objects = (Object[]) ElecDataDay;
			recordTimeList.add(Integer
					.parseInt(dFormat.format(new SimpleDateFormat("yyyy-MM-dd").parse(objects[0].toString()))));
		}
		for (int i = 1; i <= num; i++) {
			if (!recordTimeList.contains(i)) {
				Map<String, Object> map = new HashMap<>();
				map.put("time", i);
				map.put("kwh", 0.00);
				map.put("kw", 0.00);
				listDays.add(map);
			}
		}
		for (Object ElecDataDay : elecDataDays) {
			Object[] objects = (Object[]) ElecDataDay;
			Map<String, Object> map = new HashMap<>();
			map.put("time", Integer
					.parseInt(dFormat.format(new SimpleDateFormat("yyyy-MM-dd").parse(objects[0].toString()))));
			map.put("kwh", NumberUtil.accurateToTwoDecimal(Double.parseDouble(objects[1].toString())));
			map.put("kw", NumberUtil.accurateToTwoDecimal(Double.parseDouble(objects[2].toString())));
			listDays.add(map);
		}
		for (int i = 1; i <= listDays.size(); i++) {
			for (Map<String, Object> mapObject : listDays) {
				if (i == (int) mapObject.get("time")) {
					listDay.add(mapObject);
				}
			}
		}
		return listDay;

	}

	/**
	 * 移动端获取每月发电详情
	 * 
	 * @throws ParseException
	 * @throws NumberFormatException
	 */
	public List<Map<String, Object>> monthInfo(List<Long> ammeterCodes, Integer type)
			throws NumberFormatException, ParseException {
		List<Map<String, Object>> listMonth = new ArrayList<>();
		Date [] dateYesterday = DateUtil.getYesterdaySpace();
		Date endStart=dateYesterday[1];
		Date[] monthSpace = DateUtil.getThisYearSpace();
		Date monthStartTime = monthSpace[0];
		String monthStart = new SimpleDateFormat("yyyy-MM").format(monthStartTime);
		monthStart += "-01";
		String end = new SimpleDateFormat("yyyy-MM-dd").format(endStart);
		SimpleDateFormat dFormat = new SimpleDateFormat("MM");
		List<Integer> recordTimeList = new ArrayList<>();

		// List<ElecDataDay> elecDataMonths =
		// elecDataDayDao.findByDays(ammeterCodes, type, monthStart, end);
		Object[] elecDataMonths = elecDataDayDao.findByMonths(ammeterCodes, type, monthStart, end);
		String nowTime = dFormat.format(endStart);
		Integer num = Integer.parseInt(nowTime);
//		logger.info("----------------------------------多少个月:" + num);
//		logger.info("----------------------------------月记录条数:" + elecDataMonths.length);
		List<Map<String, Object>> listMonths = new ArrayList<>();
		for (Object ElecDataMonth : elecDataMonths) {
			Object[] objects = (Object[]) ElecDataMonth;
			if (!recordTimeList.contains(
					Integer.parseInt(dFormat.format(new SimpleDateFormat("yyyy-MM").parse(objects[0].toString()))))) {
				recordTimeList.add(
						Integer.parseInt(dFormat.format(new SimpleDateFormat("yyyy-MM").parse(objects[0].toString()))));
//				logger.info("----------------------------------月份:" + Integer
//						.parseInt(dFormat.format(new SimpleDateFormat("yyyy-MM").parse(objects[0].toString()))));
			}
		}
		for (int i = 1; i <= num; i++) {
			if (!recordTimeList.contains(i)) {
				Map<String, Object> map = new HashMap<>();
				map.put("time", i);
				map.put("kwh", 0.00);
				map.put("kw", 0.00);
				listMonths.add(map);
			}

		}
		for (Object ElecDataMonth : elecDataMonths) {
			Object[] objects = (Object[]) ElecDataMonth;
			Map<String, Object> map = new HashMap<>();
			map.put("time",
					Integer.parseInt(dFormat.format(new SimpleDateFormat("yyyy-MM").parse(objects[0].toString()))));
			map.put("kwh", NumberUtil.accurateToTwoDecimal(Double.parseDouble(objects[1].toString())));
			map.put("kw", NumberUtil.accurateToTwoDecimal(Double.parseDouble(objects[2].toString())));
			listMonths.add(map);
		}
		for (int i = 1; i <= listMonths.size(); i++) {
			for (Map<String, Object> mapObject : listMonths) {
				if (i == (int) mapObject.get("time")) {
					listMonth.add(mapObject);
				}
			}
		}
		return listMonth;
	}

	/**
	 * 移动端获取每年发电详情
	 * 
	 * @throws ParseException
	 * @throws NumberFormatException
	 */

	public Map<String, Object> yearInfo(List<Long> ammeterCodes, Integer type, Long stationId)
			throws NumberFormatException, ParseException {
		Map<String, Object> maps = new HashMap<>();

		Station station = stationDao.findOne(stationId);
		Date startTime = station.getCreateDtm();
		List<Map<String, Object>> listYear = new ArrayList<>();
		Date endStart = new Date();
		String yearStart = new SimpleDateFormat("yyyy-MM-dd").format(startTime);
		String end = new SimpleDateFormat("yyyy-MM-dd").format(endStart);
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy");
		List<Integer> recordTimeList = new ArrayList<>();

		Object[] elecDataYears = elecDataDayDao.findByYears(ammeterCodes, type, yearStart, end);
//		logger.info("----------------------------------number:" + elecDataYears.length);
		String nowTime = dFormat.format(endStart);
		String initTime = dFormat.format(startTime);
		Integer numEnd = Integer.parseInt(nowTime);
		Integer numstart = Integer.parseInt(initTime);
		List<Map<String, Object>> listYears = new ArrayList<>();
		for (Object ElecDataYear : elecDataYears) {
			Object[] objects = (Object[]) ElecDataYear;
			if (!recordTimeList.contains(
					Integer.parseInt(dFormat.format(new SimpleDateFormat("yyyy").parse(objects[0].toString()))))) {
				recordTimeList.add(
						Integer.parseInt(dFormat.format(new SimpleDateFormat("yyyy").parse(objects[0].toString()))));
//				logger.info("----------------------------------年份:"
//						+ Integer.parseInt(dFormat.format(new SimpleDateFormat("yyyy").parse(objects[0].toString()))));
			}

		}
		for (int i = numstart; i <= numEnd; i++) {
			if (!recordTimeList.contains(i)) {
				Map<String, Object> map = new HashMap<>();
				map.put("time", i);
				map.put("kwh", 0.00);
				map.put("kw", 0.00);
				listYears.add(map);
			}

		}
		List<Object> eDataYears = new ArrayList<>();
		if (type == 1) {
			List<Ammeter> ammeters = ammeterDao.findByStationId(stationId);
			for (Ammeter ammeter : ammeters) {
				for (Object ElecDataYear : elecDataYears) {
					Object[] objects = (Object[]) ElecDataYear;
					String time = dFormat.format(new SimpleDateFormat("yyyy").parse(objects[0].toString()));
//					logger.info("----------------------time:" + time);
//					logger.info("----------------------ammeter.getCreateDtm:"
//							+ dFormat.format(ammeter.getCreateDtm()).toString());
					if (time.equals(dFormat.format(ammeter.getCreateDtm()).toString())) {

						Double yearKwh = Double.valueOf(objects[1].toString());
						Double initKwh = ammeter.getInitKwh();

//						logger.info("----------------------ammeterCode:" + ammeter.getcAddr());
//						logger.info("-----------------------initkwh:" + ammeter.getInitKwh());
						objects[1] = yearKwh + initKwh;
						maps.put("yearTotalElec", yearKwh + initKwh);
						eDataYears.add(objects);
					} else {
						maps.put("yearTotalElec", Double.valueOf(objects[1].toString()));
						eDataYears.add(objects);
					}
				}
			}
		}
		if (type == 2) {
			for (Object ElecDataYear : elecDataYears) {
				Object[] objects = (Object[]) ElecDataYear;
				eDataYears.add(objects);
			}
			maps.put("yearTotalElec", elecDataDayDao.sumKwhByDays(yearStart, end, type, ammeterCodes));
		}

		for (Object ElecDataYear : eDataYears) {
			Object[] objects = (Object[]) ElecDataYear;
			Map<String, Object> map = new HashMap<>();
			map.put("time",
					Integer.parseInt(dFormat.format(new SimpleDateFormat("yyyy").parse(objects[0].toString()))));
			map.put("kwh", NumberUtil.accurateToTwoDecimal(Double.valueOf(objects[1].toString())));
			map.put("kw", NumberUtil.accurateToTwoDecimal(Double.valueOf(objects[2].toString())));
			listYears.add(map);
		}

		for (int i = numstart; i <= numEnd; i++) {
			for (Map<String, Object> mapObject : listYears) {
				if (i == (int) mapObject.get("time")) {
					listYear.add(mapObject);
				}
			}
		}
		maps.put("listYear", listYear);
		return maps;
	}


}
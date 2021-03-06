package com.yn.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.yn.dao.AmmeterDao;
import com.yn.dao.ElecDataDayDao;
import com.yn.dao.ElecDataMonthDao;
import com.yn.dao.ElecDataYearDao;
import com.yn.dao.StationDao;
import com.yn.dao.mapper.ElecDataDayMapper;
import com.yn.model.Ammeter;
import com.yn.model.AmmeterStatusCode;
import com.yn.model.ElecDataDay;
import com.yn.model.ElecDataDayExample;
import com.yn.model.ElecDataHour;
import com.yn.model.ElecDataDayExample.Criteria;
import com.yn.model.ElecDataMonth;
import com.yn.model.ElecDataYear;
import com.yn.model.Station;
import com.yn.utils.BeanCopy;
import com.yn.utils.Constant;
import com.yn.utils.DateUtil;
import com.yn.utils.NumberUtil;
import com.yn.utils.ObjToMap;
import com.yn.utils.RepositoryUtil;
import com.yn.vo.DataStatistics;

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
	@Autowired
	SystemConfigService systemConfigService;

	@Autowired
	private StationService stationService;
	@Autowired
	private AmmeterService ammeterService;

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
		Specification<ElecDataDay> spec = getSpecification(elecDataDay);
		ElecDataDay findOne = elecDataDayDao.findOne(spec);
		return findOne;
	}

	public List<ElecDataDay> findAll(List<Long> list) {
		return elecDataDayDao.findAll(list);
	}

	public Page<ElecDataDay> findAll(ElecDataDay elecDataDay, Pageable pageable) {
		Specification<ElecDataDay> spec = getSpecification(elecDataDay);
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
		List<ElecDataDay> listElecDataDay = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			ElecDataDay elecDataDay2 = list.get(i);
			elecDataDay2.setId(i + 1l);
			listElecDataDay.add(elecDataDay2);
		}
		return listElecDataDay;
	}

	/**
	 * 移动端获取每天每月每年发电详情
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 */
	public Map<String, Object> getElecDetailByStationCode(Long stationId, Integer type)
			throws NumberFormatException, ParseException {
		Map<String, Object> maps = new HashMap<>();
		List<Long> ammeterCodes = ammeterDao.selectAmmeterCode(stationId);
		Date[] dateYesterday = DateUtil.getYesterdaySpace();
		Date endStart = dateYesterday[1];
		// 获取每天的发电详情
		List<Map<String, Object>> dayInfo = dayInfo(ammeterCodes, type);
		if (type == 1) {
			double workTotalkwh = ammeterDao.workTotalkwh(stationId);
			logger.info("-------------------workTotalkwh:" + workTotalkwh);
			double initTotalkwh = ammeterDao.initTotalkwh(stationId);
			logger.info("-------------------initTotalkwh:" + initTotalkwh);
			double historyTotalElec = workTotalkwh + initTotalkwh;
			maps.put("historyTotalElec", NumberUtil.accurateToTwoDecimal(historyTotalElec));
		} else if (type == 2) {
			double historyTotalElec = elecDataDayDao.sumKwhByHistory(type, ammeterCodes);
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
		List<Map<String, Object>> listDays = new ArrayList<>();
		Date[] day = DateUtil.getThisMonthSpace();
		Date endStart = new Date();
		String end = new SimpleDateFormat("yyyy-MM-dd").format(endStart);
		String dayStart = new SimpleDateFormat("yyyy-MM-dd").format(day[0]);
		Object[] elecDataDays = elecDataDayDao.findByDays(ammeterCodes, type, dayStart, end);
		SimpleDateFormat dFormat = new SimpleDateFormat("dd");
		String nowTime = dFormat.format(endStart);
		Integer num = Integer.parseInt(nowTime);
		List<String> dateString = new ArrayList<>();
		List<String> recordTimeList = new ArrayList<>();
		for (int i = 0; i < num; i++) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -i);// 计算30天后的时间
			String days = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
			dateString.add(days);
		}
		for (Object ElecDataDay : elecDataDays) {
			Object[] objects = (Object[]) ElecDataDay;
			recordTimeList.add((String) objects[0]);
		}
		for (int i = 0; i < dateString.size(); i++) {
			if (!recordTimeList.contains(dateString.get(i))) {
				Map<String, Object> map = new HashMap<>();
				map.put("time", new SimpleDateFormat("MM'月'dd")
						.format(new SimpleDateFormat("yyyy-MM-dd").parse(dateString.get(i))));
				map.put("kwh", 0.00);
				map.put("kw", 0.00);
				listDays.add(map);
			}
		}
		for (Object ElecDataDay : elecDataDays) {
			Object[] objects = (Object[]) ElecDataDay;
			Map<String, Object> map = new HashMap<>();
			map.put("time", new SimpleDateFormat("MM'月'dd")
					.format(new SimpleDateFormat("yyyy-MM-dd").parse(objects[0].toString())));
			map.put("kwh", NumberUtil.accurateToTwoDecimal(Double.parseDouble(objects[1].toString())));
			map.put("kw", NumberUtil.accurateToTwoDecimal(Double.parseDouble(objects[2].toString())));
			listDays.add(map);
		}
		for (int i = dateString.size() - 1; i >= 0; i--) {
			String dateTime = String.valueOf(new SimpleDateFormat("MM'月'dd")
					.format(new SimpleDateFormat("yyyy-MM-dd").parse(dateString.get(i))));
			for (Map<String, Object> mapObject : listDays) {
				if (dateTime.equals(mapObject.get("time").toString())) {
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
		Date endStart = new Date();
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
		// logger.info("----------------------------------多少个月:" + num);
		// logger.info("----------------------------------月记录条数:" +
		// elecDataMonths.length);
		List<Map<String, Object>> listMonths = new ArrayList<>();
		for (Object ElecDataMonth : elecDataMonths) {
			Object[] objects = (Object[]) ElecDataMonth;
			if (!recordTimeList.contains(
					Integer.parseInt(dFormat.format(new SimpleDateFormat("yyyy-MM").parse(objects[0].toString()))))) {
				recordTimeList.add(
						Integer.parseInt(dFormat.format(new SimpleDateFormat("yyyy-MM").parse(objects[0].toString()))));
				// logger.info("----------------------------------月份:" + Integer
				// .parseInt(dFormat.format(new
				// SimpleDateFormat("yyyy-MM").parse(objects[0].toString()))));
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

		// Station station = stationDao.findOne(stationId);
		Date startTime = ammeterDao.selectCreateDtm(stationId);
		List<Map<String, Object>> listYear = new ArrayList<>();
		Date endStart = new Date();
		String yearStart = new SimpleDateFormat("yyyy-MM-dd").format(startTime);
		String end = new SimpleDateFormat("yyyy-MM-dd").format(endStart);
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy");
		List<Integer> recordTimeList = new ArrayList<>();

		Object[] elecDataYears = elecDataDayDao.findByYears(ammeterCodes, type, yearStart, end);
		// logger.info("----------------------------------number:" +
		// elecDataYears.length);
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
				// logger.info("----------------------------------年份:"
				// + Integer.parseInt(dFormat.format(new
				// SimpleDateFormat("yyyy").parse(objects[0].toString()))));
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
					// logger.info("----------------------time:" + time);
					// logger.info("----------------------ammeter.getCreateDtm:"
					// + dFormat.format(ammeter.getCreateDtm()).toString());
					if (time.equals(dFormat.format(ammeter.getCreateDtm()).toString())) {

						Double yearKwh = Double.valueOf(objects[1].toString());
						Double initKwh = ammeter.getInitKwh();

						// logger.info("----------------------ammeterCode:" +
						// ammeter.getcAddr());
						// logger.info("-----------------------initkwh:" +
						// ammeter.getInitKwh());
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

	@Transactional(propagation = Propagation.REQUIRED)
	public int insertData(ElecDataDay record) {
		return elecDataDayMapper.insertData(record);
	}

	/**
	 * 用户发电/用电统计图
	 */
	public Map<String, Object> workUseCountList(Long stationId, Integer type, String dateStr, Integer plan) {
		List<Long> ammeterCodes = ammeterDao.selectAmmeterCode(stationId);
		String dateFormat = "";
		if (plan == 0) {
			dateFormat = "%Y";
		} else if (plan == 1) {
			dateFormat = "%Y-%m";
		} else if (plan == 2) {
			dateFormat = "%Y-%m-%d";
		}
		List<Object[]> list = elecDataDayDao.oneKwh(ammeterCodes, dateFormat, dateStr, type);
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
			double capacity = stationDao.findCapacity(stationId);
			listMap.put("wantKwh", NumberUtil.accurateToTwoDecimal(capacity * lengthOfLight(plan, (String) key[i])));
			listsMap.add(listMap);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("list", listsMap);
		return map;
	}

	/**
	 * 电站的日照时长
	 */
	public double lengthOfLight(Integer plan, String date) {
		Double lengthOfLight = 0D;
		int days = 0;
		if (plan == 0) {
			days = DateUtil.whichYear(date);
			lengthOfLight = Double.valueOf(systemConfigService.get("day_light")) * days;
		} else if (plan == 1) {
			days = DateUtil.whichMonth(date);
			lengthOfLight = Double.valueOf(systemConfigService.get("day_light")) * days;
		} else if (plan == 2) {
			lengthOfLight = Double.valueOf(systemConfigService.get("day_light"));
		}

		return lengthOfLight;
	}

	public List<ElecDataDay> findByAmmeterCode(Ammeter ammeter) {
		if (ammeter != null && ammeter.getcAddr() != null) {
			return elecDataDayDao.findByAmmeterCode(ammeter.getcAddr());
		}
		return null;
	}

	/**
	 * 
	    * @Title: getStatisticsData4Quarter
	    * @Description: TODO(额。		。。忘记是干嘛用的了。)
	    * @param @param a
	    * @param @param startDate
	    * @param @param endDate
	    * @param @return    参数
	    * @return Double    返回类型
	    * @throws
	 */
	public Double getStatisticsData4Quarter(Ammeter a, String startDate, String endDate) {
		if(a != null && a.getcAddr() != null) {
			System.out.println(elecDataDayDao.findByAmmeterCodeAndDAddrInAndRecordTimeBetween(a.getcAddr(),Arrays.asList(1L,11L), startDate, endDate).size());
			return elecDataDayDao.findByAmmeterCodeAndDAddrInAndRecordTimeBetween(a.getcAddr(),Arrays.asList(1L,11L), startDate, endDate).stream().filter(ab->ab!=null).collect(()->new ArrayList<Double>(),(list,item)->list.add(item.getKwh()),(list1,list2)->list1.addAll(list2)).stream().reduce(0d,(sum,item)->sum+item);
		}
		return 0d;
	}
	findByAmmeterCodeAndDAddrInAndRecordTimeBetween Between执行没用，需要检查
	public DataStatistics statistics4youNengData(String startDate, String endDate, String userName, String ammeterCode)
			throws ParseException {
		DataStatistics dataStatistics = new DataStatistics();
		if (ammeterCode == null) {
			if (userName != null) {
				return letYouFuckBoss(startDate, endDate, userName);
			}
			return dataStatistics;
		} else {
			return comeBaby(startDate, endDate, ammeterCode);
		}
	}

	/**
	 * 
	    * @Title: comeBaby
	    * @Description: TODO(根据电表码统计)
	    * @param @param startDate
	    * @param @param endDate
	    * @param @param ammeterCode
	    * @param @return
	    * @param @throws ParseException    参数
	    * @return DataStatistics    返回类型
	    * @throws
	 */
	private DataStatistics comeBaby(String startDate, String endDate, String ammeterCode) throws ParseException {
		DataStatistics dataStatistics = new DataStatistics();
		Ammeter ammeter = ammeterService.findByCAddr(ammeterCode);
		// 装机容量
		double capacity = stationService.findOne(ammeter.getStationId()).getCapacity();
		// 周期内实际发电量 单位：度
		double totalKwh = getStatisticsData4Quarter(ammeter, startDate, endDate);
		Date outfactoryDate = ammeter.getOutfactoryDtm();
		String string = DateUtil.formatDate(outfactoryDate, "yyyy-MM-dd");
		// 应发天数
		int shouldWorkDays = DateUtil.daysBetween(string, endDate);
		// 故障天数 没发电算故障
		int faultDays = (int) elecDataDayDao.findByAmmeterCodeAndDAddrInAndRecordTimeBetween(ammeterCode,Arrays.asList(1L, 11L), startDate, endDate).stream().filter(a -> a.getKwh() == 0).count();
		// 本季度总天数
		int thisSeasonDays = DateUtil.daysBetween(startDate, endDate);
		// 上季度总天数 --> 环比
		int prevSeasonDays = 0;
		// 系统故障天数->数据没上传、数据表丢失
		int systemErrorDays = 0;
		Date date = DateUtil.formatString(startDate, "yyyy-MM-dd");
		Date[] date2 = DateUtil.getSeasonDate(date);
		for (Date date3 : date2) {
			Integer month = Integer.parseInt(DateUtil.formatDate(date3, "MM"));
			Integer year = Integer.parseInt(DateUtil.formatDate(date3, "yyyy"));
			// 计算系统数据丢失的天数
			systemErrorDays = caluErrorDate(month, year);
			prevSeasonDays += DateUtil.getDaysByYearMonth(year, month);
		}
		// 异常天数=没上传数据+没发电+系统数据丢失
		faultDays += systemErrorDays;
		// 等比
		// xxx没懂，execl中没有。。
		// 实发天数 应发天数-故障天数
		int actualDay = shouldWorkDays - faultDays;
		// 周期计划发电量 单位：度 装机容量*实际发电天数*3.06
		double cycleElectricityOfPlan = capacity * actualDay * Constant.POWER_GENERATION_CALCULATION_TIME;
		// 周期发电率 （实际/计划）*100%
		double cycleRate = (totalKwh / cycleElectricityOfPlan) * 100;
		// 返回。。。
		dataStatistics.setActualDay(actualDay);
		dataStatistics.setCapacity(capacity);
		dataStatistics.setCycleElectricityOfPlan(Math.round(cycleElectricityOfPlan * 100) * 0.01d);
		dataStatistics.setCycleRate(Math.round(cycleRate * 100) * 0.01d + "%");
		dataStatistics.setFaultDays(faultDays);
		dataStatistics.setPrevSeasonDays(prevSeasonDays);
		dataStatistics.setShouldWorkDays(shouldWorkDays);
		dataStatistics.setThisSeasonDays(thisSeasonDays);
		dataStatistics.setTotalKwh(Math.round(totalKwh * 100) * 0.01d);
		return dataStatistics;
	}

	/**
	 * 
	    * @Title: letYouFuckBoss
	    * @Description: TODO(很操蛋，看注释。。根据用户名统计)
	    * @param @param startDate
	    * @param @param endDate
	    * @param @param userName
	    * @param @return
	    * @param @throws ParseException    参数
	    * @return DataStatistics    返回类型
	    * @throws
	 */
	private DataStatistics letYouFuckBoss(String startDate, String endDate, String userName)
			throws ParseException {
		DataStatistics dataStatistics = new DataStatistics();
		List<Station> stations = stationService.findByLinkName(userName);
		// 周期内实际发电量 单位：度
		double totalKwh = 0d;
		// 装机容量
		double capacity = 0d;
		// 应发天数
		int shouldWorkDays = 0;
		// 故障天数 没发电算故障
		int faultDays = 0;
		for (Station station : stations) {
			capacity += station.getCapacity();
			List<Ammeter> ammeters = ammeterService.findByStationId(station);
			for (Ammeter ammeter : ammeters) {
				totalKwh += getStatisticsData4Quarter(ammeter, startDate, endDate);
				Date date = ammeter.getOutfactoryDtm();
				String string = DateUtil.formatDate(date, "yyyy-MM-dd");
				shouldWorkDays += DateUtil.daysBetween(string, endDate);
				faultDays = (int) elecDataDayDao.findByAmmeterCodeAndDAddrInAndRecordTimeBetween(ammeter.getcAddr(),Arrays.asList(1L, 11L), startDate, endDate).stream().filter(a -> a.getKwh() == 0).count();
			}
		}
		// 本季度总天数
		int thisSeasonDays = DateUtil.daysBetween(startDate, endDate);
		// 上季度总天数 --> 环比
		int prevSeasonDays = 0;
		// 系统故障天数->数据没上传、数据表丢失
		int systemErrorDays = 0;
		Date date = DateUtil.formatString(startDate, "yyyy-MM-dd");
		Date[] date2 = DateUtil.getSeasonDate(date);
		for (Date date3 : date2) {
			Integer month = Integer.parseInt(DateUtil.formatDate(date3, "MM"));
			Integer year = Integer.parseInt(DateUtil.formatDate(date3, "yyyy"));
			// 计算系统数据丢失的天数
			systemErrorDays = caluErrorDate(month, year);
			prevSeasonDays += DateUtil.getDaysByYearMonth(year, month);
		}
		// 异常天数=没上传数据+没发电+系统数据丢失
		faultDays += systemErrorDays;
		// 等比
		// xxx没懂，execl中没有。。
		// 实发天数 应发天数-故障天数
		int actualDay = shouldWorkDays - faultDays;
		// 周期计划发电量 单位：度 装机容量*实际发电天数*3.06
		double cycleElectricityOfPlan = capacity * actualDay * Constant.POWER_GENERATION_CALCULATION_TIME;
		// 周期发电率 （实际/计划）*100%
		double cycleRate = (totalKwh / cycleElectricityOfPlan) * 100;
		// 返回。。。
		dataStatistics.setActualDay(actualDay);
		dataStatistics.setCapacity(capacity);
		dataStatistics.setCycleElectricityOfPlan(Math.round(cycleElectricityOfPlan * 100) * 0.01d);
		dataStatistics.setCycleRate(Math.round(cycleRate * 100) * 0.01d + "%");
		dataStatistics.setFaultDays(faultDays);
		dataStatistics.setPrevSeasonDays(prevSeasonDays);
		dataStatistics.setShouldWorkDays(shouldWorkDays);
		dataStatistics.setThisSeasonDays(thisSeasonDays);
		dataStatistics.setTotalKwh(Math.round(totalKwh * 100) * 0.01d);
		return dataStatistics;
	}

	private int caluErrorDate(Integer month, Integer year) {
		int systemErrorDays = 0;
		if(year == 2016) {
			switch (month) {
			case 7:
				systemErrorDays += 7;
				break;
			case 9:
				systemErrorDays += 4;
				break;
			default:
				break;
			}
		}
		if(year == 2017) {
			switch (month) {
			case 1:
				systemErrorDays += 1;
				break;
			case 10:
				systemErrorDays += 3;
				break;
			case 11:
				systemErrorDays += 7;
				break;
			default:
				break;
			}
		}
		return systemErrorDays;
	}
}
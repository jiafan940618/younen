package com.yn.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import com.yn.dao.ElecDataHourDao;
import com.yn.dao.StationDao;
import com.yn.dao.mapper.ElecDataDayMapper;
import com.yn.dao.mapper.ElecDataHourMapper;
import com.yn.model.Ammeter;
import com.yn.model.ElecDataDay;
import com.yn.model.ElecDataDayExample;
import com.yn.model.ElecDataHour;
import com.yn.model.ElecDataHourExample;
import com.yn.model.ElecDataHourExample.Criteria;
import com.yn.model.Station;
import com.yn.utils.BeanCopy;
import com.yn.utils.DateUtil;
import com.yn.utils.NumberUtil;
import com.yn.utils.ObjToMap;

@Service
public class ElecDataHourService {
	@Autowired
	ElecDataHourDao elecDataHourDao;
	@Autowired
	ElecDataHourMapper elecDataHourMapper;
	@Autowired
	AmmeterDao ammeterDao;
	@Autowired
	StationDao stationDao;
	@Autowired
	ElecDataDayMapper elecDataDayMapper;

	/**
	
	 * 
	
	    * @Title: findAllDataByMonthOrYear
	
	    * @Description: TODO(查询到当月/当年所有的总发电、用电量。也可以指定年或者月)
	
	    * @param @param flag 大于0是月，小于0是年。
	
	    * @param @param selectYear 指定年。
	
	    * @param @param selectMonth 指定月。
	
	    * @param @return    参数
	
	    * @return List<ElecDataHour>    返回类型
	
	    * @throws
	
	 */
	public List<ElecDataHour> findAllDataByMonthOrYear(int flag, int selectYear, int selectMonth) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		String dayQuery = "";
		if (flag > 0) {
			dayQuery = String.valueOf(year) + "-" + (month <= 9 ? String.valueOf(0) + month : month);
		} else {
			dayQuery = String.valueOf(year);
		}
		if (selectYear != -1) {
			if (selectMonth == -1) {
				dayQuery = String.valueOf(selectYear);
			} else {
				dayQuery = String.valueOf(selectYear) + "-"
						+ (selectMonth <= 9 ? String.valueOf(0) + selectMonth : selectMonth);
			}
		}
		//只是玩玩的话，在下面把dayQuery改成想要的格式。
		//dayQuery="2017-12";//某一个月
		//dayQuery="2017";//整年
		System.out.println(dayQuery);
		return elecDataHourMapper.findAllDataByMonthOrYear(new ElecDataHour(dayQuery));
	}
	
	/**
	 * 
	    * @Title: findAllDataByMonthOrYear4C
	    * @Description: TODO(同上，但这是为Controller准备的方法、。)
	    * @param @param date
	    * @param @return    参数
	    * @return List<ElecDataHour>    返回类型
	    * @throws
	 */
	public List<ElecDataHour> findAllDataByMonthOrYear4C(String date) {
		System.out.println(date);
		return elecDataHourMapper.findAllDataByMonthOrYear(new ElecDataHour(date));
	}

	public ElecDataHour findOne(Long id) {
		return elecDataHourDao.findOne(id);
	}

	public void save(ElecDataHour elecDataHour) {
		if (elecDataHour.getId() != null) {
			ElecDataHour one = elecDataHourDao.findOne(elecDataHour.getId());
			try {
				BeanCopy.beanCopy(elecDataHour, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			elecDataHourDao.save(one);
		} else {
			elecDataHourDao.save(elecDataHour);
		}
	}

	public void saveByMapper(ElecDataHour elecDataHour) {
		if (elecDataHour.getId() != null) {
			ElecDataHour one = elecDataHourDao.findOne(elecDataHour.getId());
			try {
				BeanCopy.beanCopy(elecDataHour, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			elecDataHourMapper.updateByPrimaryKeySelective(elecDataHour);
		} else {
			elecDataHourMapper.insert(elecDataHour);
		}
	}

	public void delete(Long id) {
		elecDataHourDao.delete(id);
	}

	public void deleteBatch(List<Long> id) {
		elecDataHourDao.deleteBatch(id);
	}

	public ElecDataHour findOne(ElecDataHour elecDataHour) {
		Specification<ElecDataHour> spec = getSpecification(elecDataHour);
		ElecDataHour findOne = elecDataHourDao.findOne(spec);
		return findOne;
	}

	public List<ElecDataHour> findAll(List<Long> list) {
		return elecDataHourDao.findAll(list);
	}

	public Page<ElecDataHour> findAll(ElecDataHour ElecDataHour, Pageable pageable) {
		Specification<ElecDataHour> spec = getSpecification(ElecDataHour);
		Page<ElecDataHour> findAll = elecDataHourDao.findAll(spec, pageable);
		return findAll;
	}

	public List<ElecDataHour> findAll(ElecDataHour ElecDataHour) {
		Specification<ElecDataHour> spec = getSpecification(ElecDataHour);
		return elecDataHourDao.findAll(spec);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Specification<ElecDataHour> getSpecification(ElecDataHour ElecDataHour) {
		ElecDataHour.setDel(0);
		Map<String, Object> objectMap = ObjToMap.getObjectMap(ElecDataHour);
		return (Root<ElecDataHour> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

			Predicate conjunction = cb.conjunction();
			List<Expression<Boolean>> expressions = conjunction.getExpressions();
			Iterator<Map.Entry<String, Object>> iterator = objectMap.entrySet().iterator();

			while (iterator.hasNext()) {
				Map.Entry<String, Object> entry = iterator.next();
				if (!entry.getKey().equals("query") && !entry.getKey().equals("queryStartDtm")
						&& !entry.getKey().equals("queryEndDtm")) {
					Object value = entry.getValue();
					if (value instanceof Map) {
						Iterator<Map.Entry<String, Object>> iterator1 = ((Map) value).entrySet().iterator();
						while (iterator1.hasNext()) {
							Map.Entry<String, Object> entry1 = iterator1.next();
							expressions.add(cb.equal(root.get(entry.getKey()).get(entry1.getKey()), entry1.getValue()));
						}
					} else {
						expressions.add(cb.equal(root.get(entry.getKey()), value));
					}
				}
			}

			// 根据xxx来查询
			String queryStr = ElecDataHour.getQuery();
			if (!StringUtils.isEmpty(queryStr)) {
				// Predicate[] predicates = new Predicate[2];
				// predicates[0] = cb.like(root.get("title"), "%" + queryStr +
				// "%");
				// predicates[1] = cb.like(root.get("author"), "%" + queryStr +
				// "%");
				// expressions.add(cb.or(predicates));
			}

			// 根据日期筛选
			String queryStartDtm = ElecDataHour.getQueryStartDtm();
			String queryEndDtm = ElecDataHour.getQueryEndDtm();

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

	/**
	 * 查找今日每个时刻所有电站的发电 / 用电
	 *
	 * @return
	 */
	public List<ElecDataHour> getTodayKwh(Long serverId, Integer type) {

		Date[] todaySpace = DateUtil.getTodaySpace();
		Date start = todaySpace[0];
		Date end = todaySpace[1];

		List<ElecDataHour> ElecDataHourList = new ArrayList<>();
		if (serverId == null) {
			ElecDataHourList = elecDataHourDao.findByTypeAndCreateDtmBetween(type, start, end);
		}
		if (serverId != null) {
			List<Long> stationIds = stationDao.findId(serverId);
			for (Long stationId : stationIds) {
				List<Ammeter> ammeters = ammeterDao.findByStationId(stationId);
				for (Ammeter ammeter : ammeters) {
					List<ElecDataHour> ElecDataHourLists = new ArrayList<>();
					ElecDataHourLists = elecDataHourDao.findByAmmeterCodeAndTypeAndCreateDtmBetween(ammeter.getcAddr(),
							type, start, end);
					for (ElecDataHour elecDataHour : ElecDataHourLists) {
						ElecDataHourList.add(elecDataHour);
					}
				}
			}

		}
		List<ElecDataHour> eachHourElecDataHourList = groupByEachHourInOneDay(ElecDataHourList);
		return eachHourElecDataHourList;
	}

	/**
	 * 根据电站id查找今日每个时刻的发电量/用电量
	 * 
	 * 
	 * @return
	 */
	public List<ElecDataHour> getTodayKwhByStationId(Long stationId, Integer type) {

		Date[] todaySpace = DateUtil.getTodaySpace();
		Date start = todaySpace[0];
		Date end = todaySpace[1];
		List<ElecDataHour> ElecDataHourList = new ArrayList<>();
		List<Ammeter> ammeters = ammeterDao.findByStationId(stationId);
		for (Ammeter ammeter : ammeters) {
			List<ElecDataHour> ElecDataHourLists = elecDataHourDao
					.findByAmmeterCodeAndTypeAndCreateDtmBetween(ammeter.getcAddr(), type, start, end);
			for (ElecDataHour elecDataHour : ElecDataHourLists) {
				ElecDataHourList.add(elecDataHour);
			}
		}
		List<ElecDataHour> eachHourElecDataHourList = groupByEachHourInOneDay(ElecDataHourList);
		return eachHourElecDataHourList;
	}

	/**
	 * 计算某一天内每个小时的 发电/用电
	 * 
	 * @param ElecDataHourList
	 * @return
	 */
	public List<ElecDataHour> groupByEachHourInOneDay(List<ElecDataHour> ElecDataHourList) {
		List<ElecDataHour> eachHourElecDataHourList = new ArrayList<>();

		List<Date> todayEachHourBegin = DateUtil.getTodayEachHourBegin();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:00");
		for (Date each : todayEachHourBegin) {
			String timeStr = sdf.format(each);
			ElecDataHour eachHourElecDataHour = new ElecDataHour();
			eachHourElecDataHour.setKw(0D);
			eachHourElecDataHour.setKwh(0D);

			for (ElecDataHour ts : ElecDataHourList) {
				if (sdf.format(ts.getCreateDtm()).equals(timeStr)) {
					eachHourElecDataHour.setKw(eachHourElecDataHour.getKw() + ts.getKw());
					eachHourElecDataHour.setKwh(eachHourElecDataHour.getKwh() + ts.getKwh());
				}
			}

			eachHourElecDataHourList.add(eachHourElecDataHour);
		}

		return eachHourElecDataHourList;
	}

	/**
	 * 今日发电/用电
	 *
	 * @param stationId
	 * @param type
	 * @return
	 */
	public double todayKwh(Long stationId, Integer type) {
		Date[] todaySpace = DateUtil.getTodaySpace();
		double todayKwh = 0D;
		List<Ammeter> ammeters = ammeterDao.findByStationId(stationId);
		for (Ammeter ammeter : ammeters) {
			todayKwh += elecDataHourDao.sumKwhByAmmeterCode(todaySpace[0], todaySpace[1], type, ammeter.getcAddr());
		}

		return todayKwh;
	}

	/**
	 * 昨日发电/用电
	 *
	 * @param stationId
	 * @param type
	 * @return
	 */
	public double yesterdayKwh(Long stationId, Integer type) {
		Date[] yesterdaySpace = DateUtil.getYesterdaySpace();
		List<Ammeter> ammeters = ammeterDao.findByStationId(stationId);
		double yesterdayKwh = 0D;
		for (Ammeter ammeter : ammeters) {
			yesterdayKwh += elecDataHourDao.sumKwhByAmmeterCode(yesterdaySpace[0], yesterdaySpace[1], type,
					ammeter.getcAddr());
		}
		return yesterdayKwh;
	}

	/**
	 * 当月发电/用电
	 *
	 * @param stationId
	 * @param type
	 * @return
	 */
	public double thisMonthKwh(Long stationId, Integer type) {
		Date[] thisMonthSpace = DateUtil.getThisMonthSpace();
		List<Ammeter> ammeters = ammeterDao.findByStationId(stationId);
		double thisMonthKwh = 0D;
		for (Ammeter ammeter : ammeters) {
			thisMonthKwh += elecDataHourDao.sumKwhByAmmeterCode(thisMonthSpace[0], thisMonthSpace[1], type,
					ammeter.getcAddr());
		}
		return thisMonthKwh;
	}

	/**
	 * 上月发电/用电
	 *
	 * @param stationId
	 * @param type
	 * @return
	 */
	public double lastMonthKwh(Long stationId, Integer type) {
		Date[] lastMonthSpace = DateUtil.getLastMonthSpace();
		List<Ammeter> ammeters = ammeterDao.findByStationId(stationId);
		double lastMonthKwh = 0D;
		for (Ammeter ammeter : ammeters) {
			lastMonthKwh += elecDataHourDao.sumKwhByAmmeterCode(lastMonthSpace[0], lastMonthSpace[1], type,
					ammeter.getcAddr());
		}
		return lastMonthKwh;
	}

	/**
	 * 当年发电/用电
	 *
	 * @param stationId
	 * @param type
	 * @return
	 */
	public double thisYearKwh(Long stationId, Integer type) {
		Date[] thisYearSpace = DateUtil.getThisYearSpace();
		List<Ammeter> ammeters = ammeterDao.findByStationId(stationId);
		double thisYearKwh = 0D;
		for (Ammeter ammeter : ammeters) {
			thisYearKwh += elecDataHourDao.sumKwhByAmmeterCode(thisYearSpace[0], thisYearSpace[1], type,
					ammeter.getcAddr());
		}
		return thisYearKwh;
	}

	/**
	 * 去年发电/用电
	 *
	 * @param stationId
	 * @param type
	 * @return
	 */
	public double lastYearKwh(Long stationId, Integer type) {
		Date[] lastYearSpace = DateUtil.getLastYearSpace();
		List<Ammeter> ammeters = ammeterDao.findByStationId(stationId);
		double lastYearKwh = 0D;
		for (Ammeter ammeter : ammeters) {
			lastYearKwh += elecDataHourDao.sumKwhByAmmeterCode(lastYearSpace[0], lastYearSpace[1], type,
					ammeter.getcAddr());
		}
		return lastYearKwh;
	}

	/**
	 * 用户每月发电量
	 *
	 * @param stations
	 * @return
	 */
	// public List<Map<Object,Object>> monthKwh(List<Station> stations){
	// Map<Object, Object> objectMap = new TreeMap<Object, Object>();
	// Map<Object, Object> linkHashMap=new LinkedHashMap<>();
	// List<Map<Object, Object>> lists=new ArrayList<>();
	// List<Map<Object, Object>> listsMap=new ArrayList<>();
	// for (Station station : stations) {
	// List<Map<Object, Object>>
	// list=elecDataHourDao.sumMonthKwh(station.getId());
	// if (!list.isEmpty()) {
	// lists.addAll(list);
	// }
	//
	// }
	// for(Map<Object, Object> map : lists) {
	// if (!objectMap.containsKey(map.get("create_dtm"))) {
	//
	// objectMap.put(map.get("create_dtm"), map.get("kwh"));
	// }else{
	// double
	// kwh=(double)objectMap.get(map.get("create_dtm"))+(double)map.get("kwh");
	// objectMap.put(map.get("create_dtm"), (Object)kwh);
	// }
	//
	// }
	// Object[] key = objectMap.keySet().toArray();
	// for (int i = 0; i < key.length; i++) {
	// Map<Object, Object> listMap=new LinkedHashMap<>();
	// linkHashMap.put(key[i], objectMap.get(key[i]));
	// listMap.put("createDtm", key[i]);
	// listMap.put("capacity", objectMap.get(key[i]));
	// listsMap.add(listMap);
	// }
	// return listsMap;
	// }

	/**
	 * 获取当前时间的发电/用电总量
	 */
	public Map<String, Object> getNowToalKwh(Long stationId, Integer type, Date date) {

		Date start = date;

		Date end = new Date();
		List<Ammeter> ammeters = ammeterDao.findByStationId(stationId);
		Double toalKwh = 0D;
		Double kw = 0D;
		for (Ammeter ammeter : ammeters) {

			List<ElecDataHour> ElecDataHourList = elecDataHourDao
					.findByAmmeterCodeAndTypeAndCreateDtmBetween(ammeter.getcAddr(), type, start, end);
			for (ElecDataHour ElecDataHour : ElecDataHourList) {
				toalKwh += ElecDataHour.getKwh();
				kw = ElecDataHour.getKw();
			}
		}

		Map<String, Object> map = new HashMap<>();

		map.put("toalKwh", NumberUtil.accurateToTwoDecimal(toalKwh));

		map.put("kw", NumberUtil.accurateToTwoDecimal(kw));
		map.put("todayKwh", NumberUtil.accurateToTwoDecimal(todayKwh(stationId, type)));

		return map;

	}

	/**
	 * 当前12小时内的发电、用电量
	 */
	public List<Map<String, Object>> oneHourKwh(Long stationId, Integer type) {

		List<Map<String, Object>> list = new ArrayList<>();

		Date time = new Date();

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 12);

		SimpleDateFormat dFormat = new SimpleDateFormat("HH:mm");

		List<Long> ammeterCodes = ammeterDao.selectAmmeterCode(stationId);
		// String ammeterCode=NumberUtil.listToString(ammeterCodes);

		List<ElecDataHour> ElecDataHourList = elecDataHourDao.findByAmmeterCodes(ammeterCodes, type,

				calendar.getTime(), time);

		for (ElecDataHour ElecDataHour : ElecDataHourList) {

			Map<String, Object> map = new HashMap<>();

			map.put("time", dFormat.format(ElecDataHour.getCreateDtm()));
			map.put("kw", NumberUtil.accurateToTwoDecimal(ElecDataHour.getKw()));

			list.add(map);

		}

		return list;
	}

	public List<ElecDataHour> findByMapper(ElecDataHour elecDataHour) {
		List<ElecDataHour> list = elecDataHourMapper.selectByQuery(elecDataHour);
		return list;
	}

	public List<ElecDataHour> selectByExample(ElecDataHour elecDataHour) {
		ElecDataHourExample example = new ElecDataHourExample();
		Criteria criteria = example.createCriteria();
		if (elecDataHour.getRecordTime() != null) {
			criteria.andRecordTimeEqualTo(elecDataHour.getRecordTime());
		}
		if (elecDataHour.getAmmeterCode() != null) {
			criteria.andAmmeterCodeEqualTo(elecDataHour.getAmmeterCode().toString());
		}
		return elecDataHourMapper.selectByExample(example);
	}

}

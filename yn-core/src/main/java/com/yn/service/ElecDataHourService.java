package com.yn.service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import com.yn.dao.ElecDataHourDao;
import com.yn.dao.ServerDao;
import com.yn.dao.StationDao;
import com.yn.dao.TestKwhDao;
import com.yn.dao.UserDao;
import com.yn.dao.mapper.AmPhaseRecordMapper;
import com.yn.dao.mapper.ElecDataDayMapper;
import com.yn.dao.mapper.ElecDataHourMapper;
import com.yn.model.AmPhaseRecord;
import com.yn.model.ElecDataHour;
import com.yn.model.ElecDataHourExample;
import com.yn.model.ElecDataHourExample.Criteria;
import com.yn.utils.BeanCopy;
import com.yn.utils.DateUtil;
import com.yn.utils.NumberUtil;
import com.yn.utils.ObjToMap;
@Service
public class ElecDataHourService {
	private static final Logger logger = LoggerFactory.getLogger(ElecDataHourService.class);
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
	@Autowired
	SystemConfigService systemConfigService;
	@Autowired
	AmPhaseRecordMapper amPhaseRecordMapper;
	@Autowired
	ElecDataDayDao elecDataDayDao;
	@Autowired
	TestKwhDao testKwhDao;
	@Autowired
	UserDao userDao;
	@Autowired
	ServerDao serverDao;
	

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
		if(elecDataHour.getwAddr()>0){
			return;
		}
		if (elecDataHour.getId() != null) {
			ElecDataHour one = elecDataHourDao.findOne(elecDataHour.getId());
			ElecDataHourExample ex = new ElecDataHourExample();
			Criteria criteria = ex.createCriteria();
			criteria.andAmmeterCodeEqualTo(one.getAmmeterCode());
			criteria.andRecordTimeEqualTo(one.getRecordTime());
			List<ElecDataHour> selectByExample = elecDataHourMapper.selectByExample(ex);
			Double a = 0.0;
			for (ElecDataHour elecDataHour2 : selectByExample) {
				a += elecDataHour2.getKwh();
			}
			try {
				BeanCopy.beanCopy(elecDataHour, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
//			elecDataDay.setKwh(a);
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
	public List<Map<String, Object>> getTodayKwh(Long userId, Integer type) {
		List<Long> stationIds=new ArrayList<>();
		if (userDao.findOne(userId).getRoleId()==1) {
			stationIds=stationDao.findAllStationId();
		}else if(userDao.findOne(userId).getRoleId()==5){
			Long serverId=serverDao.findByUserid(userId);
			stationIds=stationDao.findId(serverId);
		}
		List<Map<String, Object>> todayKwh=new ArrayList<>();
		List<Map<String, Object>> todayKwhAll=new ArrayList<>();
		for (Long stationId : stationIds) {
			List<Map<String, Object>> todayKwhOne=getTodayKwhByStationId(stationId, type);
			for (Map<String, Object> map : todayKwhOne) {
				todayKwhAll.add(map);
			}	
		}
		SimpleDateFormat dFormat = new SimpleDateFormat("HH");
		Integer num= Integer.parseInt(dFormat.format(new Date()));
		for (int i = 0; i < num; i++) {
			Map<String, Object> mapOne=new HashMap<>();
			Double kwh =0D;
			for (Map<String, Object> map : todayKwhAll) {
				if (i==(Integer)map.get("time")) {
					kwh+=(Double)map.get("kwh");
				}
			}
			mapOne.put("time", i);
			mapOne.put("kwh",NumberUtil.accurateToTwoDecimal(kwh) );
			todayKwh.add(mapOne);
		}
		return todayKwh;
	}

	/**
	 * 根据电站id查找今日每个时刻的发电量/用电量
	 * 
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getTodayKwhByStationId(Long stationId, Integer type) {

		Map<String, Object> objectAmPhaseRecord=new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();
		List<Map<String, Object>> listArray = new ArrayList<>();
		List<Long> ammeterCodes = ammeterDao.selectAmmeterCode(stationId);
		//判断是否绑定电表码
		if (ammeterCodes.size()>0) {
		String table="am_phase_record_" +new SimpleDateFormat("yyyy_MM_dd").format(new Date());
		Date[] todaySpace = DateUtil.getTodaySpace();
		Date startTime = todaySpace[0];
		String start=new SimpleDateFormat("yyyy-MM-dd HH:00:00").format(startTime);
		String end=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		SimpleDateFormat dFormat = new SimpleDateFormat("HH");
		Integer num= Integer.parseInt(dFormat.format(new Date()));
		List<Integer> recordTimeList=new ArrayList<>();
		List<AmPhaseRecord> AmPhaseRecordList=new ArrayList<>();
		if (type==1) {
			Map<String, Object> map1=new HashMap<>();
			map1.put("start", start);
			map1.put("type", 1);
			map1.put("end", end);
			map1.put("table", table);
			map1.put("ammeterCodes", ammeterCodes);
			List<AmPhaseRecord> AmPhaseRecordList01=amPhaseRecordMapper.findByAmmeterCodes(map1);
			List<AmPhaseRecord> AmPhaseRecordList1=new ArrayList<>();
			Map<String, Object> map2=new HashMap<>();
			map2.put("start", start);
			map2.put("type", 11);
			map2.put("end", end);
			map2.put("table", table);
			map2.put("ammeterCodes", ammeterCodes);
			List<AmPhaseRecord> AmPhaseRecordList02=amPhaseRecordMapper.findByAmmeterCodes(map2);
			List<AmPhaseRecord> AmPhaseRecordList2=new ArrayList<>();
			for (int i = 0; i < AmPhaseRecordList01.size(); i++) {
				if (i==0) {
					AmPhaseRecord AmPhaseRecord=new AmPhaseRecord();
					AmPhaseRecord.setMeterState(AmPhaseRecordList01.get(i).getMeterState());
					AmPhaseRecord.setKwh(AmPhaseRecordList01.get(i).getKwh()-AmPhaseRecordList01.get(i).getaKwhTotal());
					AmPhaseRecord.setKw(AmPhaseRecordList01.get(i).getKw());
					AmPhaseRecordList1.add(AmPhaseRecord);
				}else {
					AmPhaseRecord AmPhaseRecord=new AmPhaseRecord();
					AmPhaseRecord.setMeterState(AmPhaseRecordList01.get(i).getMeterState());
					AmPhaseRecord.setKwh(AmPhaseRecordList01.get(i).getKwh()-AmPhaseRecordList01.get(i-1).getKwh());
					AmPhaseRecord.setKw(AmPhaseRecordList01.get(i).getKw());
					AmPhaseRecordList1.add(AmPhaseRecord);
				}
				
			}
			for (int i = 0; i < AmPhaseRecordList02.size(); i++) {
				if (i==0) {
					AmPhaseRecord AmPhaseRecord=new AmPhaseRecord();
					AmPhaseRecord.setMeterState(AmPhaseRecordList02.get(i).getMeterState());
					AmPhaseRecord.setKwh(AmPhaseRecordList02.get(i).getKwh()-AmPhaseRecordList02.get(i).getaKwhTotal());
					AmPhaseRecord.setKw(AmPhaseRecordList02.get(i).getKw());
					AmPhaseRecordList1.add(AmPhaseRecord);
				}else {
					AmPhaseRecord AmPhaseRecord=new AmPhaseRecord();
					AmPhaseRecord.setMeterState(AmPhaseRecordList02.get(i).getMeterState());
					AmPhaseRecord.setKwh(AmPhaseRecordList02.get(i).getKwh()-AmPhaseRecordList02.get(i-1).getKwh());
					AmPhaseRecord.setKw(AmPhaseRecordList02.get(i).getKw());
					AmPhaseRecordList2.add(AmPhaseRecord);
				}
				
			}
			
			if (AmPhaseRecordList1.size()>0) {
				for (AmPhaseRecord AmPhaseRecord1 : AmPhaseRecordList1) {
				     if (!recordTimeList.contains(Integer.parseInt(AmPhaseRecord1.getMeterState()))) {
					     recordTimeList.add(Integer.parseInt(AmPhaseRecord1.getMeterState()));
					     objectAmPhaseRecord.put(AmPhaseRecord1.getMeterState(), AmPhaseRecord1);  
				     }else if (recordTimeList.contains(Integer.parseInt(AmPhaseRecord1.getMeterState()))) {
				    	 AmPhaseRecord AmPhaseRecord=(AmPhaseRecord)objectAmPhaseRecord.get(AmPhaseRecord1.getMeterState());
				    	 AmPhaseRecord AmPhaseRecordOne=new AmPhaseRecord();
				    	 AmPhaseRecordOne.setMeterState(AmPhaseRecord.getMeterState());
				    	 AmPhaseRecordOne.setKw(AmPhaseRecord.getKw()+AmPhaseRecord1.getKw());
				    	 AmPhaseRecordOne.setKwh(AmPhaseRecord.getKwh()+AmPhaseRecord1.getKwh());
				    	 objectAmPhaseRecord.remove(AmPhaseRecord1.getMeterState());
				    	 objectAmPhaseRecord.put(AmPhaseRecordOne.getMeterState(), AmPhaseRecordOne);
				     } 
			    }
			}
			if (AmPhaseRecordList2.size()>0) {
				for (AmPhaseRecord AmPhaseRecord2 : AmPhaseRecordList2) {
				     if (!recordTimeList.contains(Integer.parseInt(AmPhaseRecord2.getMeterState()))) {
					     recordTimeList.add(Integer.parseInt(AmPhaseRecord2.getMeterState()));
					     objectAmPhaseRecord.put(AmPhaseRecord2.getMeterState(), AmPhaseRecord2); 
				     }else if (recordTimeList.contains(Integer.parseInt(AmPhaseRecord2.getMeterState()))) {
				    	 AmPhaseRecord AmPhaseRecord=(AmPhaseRecord)objectAmPhaseRecord.get(AmPhaseRecord2.getMeterState());
				    	 AmPhaseRecord AmPhaseRecordOne=new AmPhaseRecord();
				    	 AmPhaseRecordOne.setMeterState(AmPhaseRecord.getMeterState());
				    	 AmPhaseRecordOne.setKw(AmPhaseRecord.getKw()+AmPhaseRecord2.getKw());
				    	 AmPhaseRecordOne.setKwh(AmPhaseRecord.getKwh()+AmPhaseRecord2.getKwh());
				    	 objectAmPhaseRecord.remove(AmPhaseRecord2.getMeterState());
				    	 objectAmPhaseRecord.put(AmPhaseRecordOne.getMeterState(), AmPhaseRecordOne);
				     } 
			    }
			}
		}else if (type==2) {
			Map<String, Object> map3=new HashMap<>();
			map3.put("start", start);
			map3.put("type", 2);
			map3.put("end", end);
			map3.put("table", table);
			map3.put("ammeterCodes", ammeterCodes);
			List<AmPhaseRecord> AmPhaseRecordList03=amPhaseRecordMapper.findByAmmeterCodes(map3);
			List<AmPhaseRecord> AmPhaseRecordList3=new ArrayList<>();
			for (int i = 0; i < AmPhaseRecordList03.size(); i++) {
				if (i==0) {
					AmPhaseRecord AmPhaseRecord=new AmPhaseRecord();
					AmPhaseRecord.setMeterState(AmPhaseRecordList03.get(i).getMeterState());
					AmPhaseRecord.setKwh(AmPhaseRecordList03.get(i).getKwh()-AmPhaseRecordList03.get(i).getaKwhTotal());
					AmPhaseRecord.setKw(AmPhaseRecordList03.get(i).getKw());
					AmPhaseRecordList3.add(AmPhaseRecord);
				}else {
					AmPhaseRecord AmPhaseRecord=new AmPhaseRecord();
					AmPhaseRecord.setMeterState(AmPhaseRecordList03.get(i).getMeterState());
					AmPhaseRecord.setKwh(AmPhaseRecordList03.get(i).getKwh()-AmPhaseRecordList03.get(i-1).getKwh());
					AmPhaseRecord.setKw(AmPhaseRecordList03.get(i).getKw());
					AmPhaseRecordList3.add(AmPhaseRecord);
				}
				
			}
			if (AmPhaseRecordList3.size()>0) {
				for (AmPhaseRecord AmPhaseRecord3 : AmPhaseRecordList3) {
				     if (!recordTimeList.contains(Integer.parseInt(AmPhaseRecord3.getMeterState()))) {
					     recordTimeList.add(Integer.parseInt(AmPhaseRecord3.getMeterState()));
					     objectAmPhaseRecord.put(AmPhaseRecord3.getMeterState(), AmPhaseRecord3); 
				     }else if (recordTimeList.contains(Integer.parseInt(AmPhaseRecord3.getMeterState()))) {
				    	 AmPhaseRecord AmPhaseRecord=(AmPhaseRecord)objectAmPhaseRecord.get(AmPhaseRecord3.getMeterState());
				    	 AmPhaseRecord AmPhaseRecordOne=new AmPhaseRecord();
				    	 AmPhaseRecordOne.setMeterState(AmPhaseRecord.getMeterState());
				    	 AmPhaseRecordOne.setKw(AmPhaseRecord.getKw()+AmPhaseRecord3.getKw());
				    	 AmPhaseRecordOne.setKwh(AmPhaseRecord.getKwh()+AmPhaseRecord3.getKwh());
				    	 objectAmPhaseRecord.remove(AmPhaseRecord3.getMeterState());
				    	 objectAmPhaseRecord.put(AmPhaseRecordOne.getMeterState(), AmPhaseRecordOne);
				     } 
			    }
			}
		}
		
		  Set<Map.Entry<String,Object>> entrySet = objectAmPhaseRecord.entrySet();
	        Iterator<Map.Entry<String,Object>> it = entrySet.iterator();

	        while(it.hasNext())
	        { 
	            Map.Entry<String,Object> me = it.next();
	            AmPhaseRecordList.add((AmPhaseRecord)me.getValue());
	        }
		
		for (int i = 0; i <= num; i++) {
			if (!recordTimeList.contains(i)) {
				Map<String, Object> mapNum = new HashMap<>();
				mapNum.put("time", i);
				mapNum.put("kwh", 0.00);
				mapNum.put("kw", 0.00);
				list.add(mapNum);
			}
		}
		for (AmPhaseRecord AmPhaseRecord : AmPhaseRecordList) {
			Map<String, Object> mapAmPhaseRecord = new HashMap<>();
			mapAmPhaseRecord.put("time", Integer.parseInt(AmPhaseRecord.getMeterState()));
			mapAmPhaseRecord.put("kwh", NumberUtil.accurateToTwoDecimal(AmPhaseRecord.getKwh()));
			mapAmPhaseRecord.put("kw", NumberUtil.accurateToTwoDecimal(AmPhaseRecord.getKw()));
			list.add(mapAmPhaseRecord);
		}
		for (int i = 0; i < list.size(); i++) {
		for	(Map<String, Object> mapObject:list ){
			if (i==(int)mapObject.get("time")) {
				listArray.add(mapObject);
			}
		}
	}
	}
		return 	listArray;
	}

	/**
	 * 今日发电/用电
	 *
	 * @param stationId
	 * @param type
	 * @return
	 */
	public double todayKwh(Long stationId, Integer type) {
		
		double todayKwh = 0D;
		List<Long> ammeterCodes = ammeterDao.selectAmmeterCode(stationId);
		Date[] todaySpace = DateUtil.getTodaySpace();
	    String table="am_phase_record_" +new SimpleDateFormat("yyyy_MM_dd").format(new Date());
		if (type==1) {
			Map<String, Object> map1 =new HashMap<>();
			map1.put("ammeterCodes", ammeterCodes);
			map1.put("start", todaySpace[0]);
			map1.put("end", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			map1.put("type", 1);
			map1.put("table", table);
			AmPhaseRecord amPhaseRecord1 =amPhaseRecordMapper.todayKwh(map1);
			if (amPhaseRecord1!=null) {
				todayKwh+=amPhaseRecord1.getKwh();
			}
			Map<String, Object> map2 =new HashMap<>();
			map2.put("ammeterCodes", ammeterCodes);
			map2.put("start", todaySpace[0]);
			map2.put("end", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			map2.put("type", 11);
			map2.put("table", table);
			AmPhaseRecord amPhaseRecord2 =amPhaseRecordMapper.todayKwh(map2);
			if (amPhaseRecord2!=null) {
				todayKwh+=amPhaseRecord2.getKwh();
			}
		}else if (type==2) {
			Map<String, Object> map3 =new HashMap<>();
			map3.put("ammeterCodes", ammeterCodes);
			map3.put("start", todaySpace[0]);
			map3.put("end", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			map3.put("type", 2);
			map3.put("table", table);
			AmPhaseRecord amPhaseRecord3 =amPhaseRecordMapper.todayKwh(map3);
			if (amPhaseRecord3!=null) {
				todayKwh+=amPhaseRecord3.getKwh();
			}
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
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		List<Long> ammeterCodes = ammeterDao.selectAmmeterCode(stationId);
		double yesterdayKwh=0D;
		Double yesterdayKwhD =elecDataDayDao.sumKwhByDays(df.format(yesterdaySpace[0]), 
				df.format(yesterdaySpace[1]), type, ammeterCodes); 
		if (yesterdayKwhD!=null) {
			yesterdayKwh=yesterdayKwhD;
		}
		return yesterdayKwh;
	}
	/**
	 * 上周发电/用电
	 *
	 * @param stationId
	 * @param type
	 * @return
	 */
	public double lastWeekKwh(Long stationId, Integer type) {
		Date[] lastWeekSpace = DateUtil.getLsatWeek();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		List<Long> ammeterCodes = ammeterDao.selectAmmeterCode(stationId);
		double lastWeekKwh=0D;
		Double lastWeekKwhD =elecDataDayDao.sumKwhByDays(df.format(lastWeekSpace[0]), 
				df.format(lastWeekSpace[1]), type, ammeterCodes); 
		if (lastWeekKwhD!=null) {
			lastWeekKwh=lastWeekKwhD;
		}
		return lastWeekKwh;
	}

	/**
	 * 本周发电/用电
	 *
	 * @param stationId
	 * @param type
	 * @return
	 */
	public double thisWeekKwh(Long stationId, Integer type) {
		Date[] thisWeekSpace = DateUtil.getLastYearSpace();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		List<Long> ammeterCodes = ammeterDao.selectAmmeterCode(stationId);
		double thisWeekKwh=0D;
		Double thisWeekKwhD =elecDataDayDao.sumKwhByDays(df.format(thisWeekSpace[0]), 
				df.format(thisWeekSpace[1]), type, ammeterCodes);
		if (thisWeekKwhD!=null) {
			thisWeekKwh=thisWeekKwhD;
		}
		return thisWeekKwh;
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
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		List<Long> ammeterCodes = ammeterDao.selectAmmeterCode(stationId);
		double thisMonthKwh=0D;
		Double thisMonthKwhD =elecDataDayDao.sumKwhByDays(df.format(thisMonthSpace[0]), 
				df.format(thisMonthSpace[1]), type, ammeterCodes);
		if (thisMonthKwhD!=null) {
			thisMonthKwh=thisMonthKwhD;
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
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		List<Long> ammeterCodes = ammeterDao.selectAmmeterCode(stationId);
		double lastMonthKwh = 0D;
		Double lastMonthKwhD =elecDataDayDao.sumKwhByDays(df.format(lastMonthSpace[0]), 
				df.format(lastMonthSpace[1]), type, ammeterCodes);
		if (lastMonthKwhD!=null) {
			lastMonthKwh=lastMonthKwhD;
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
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		List<Long> ammeterCodes = ammeterDao.selectAmmeterCode(stationId);
		double thisYearKwh = 0D;
		Double thisYearKwhD =elecDataDayDao.sumKwhByDays(df.format(thisYearSpace[0]), 
				df.format(thisYearSpace[1]), type, ammeterCodes);
		if (thisYearKwhD!=null) {
			thisYearKwh=thisYearKwhD;
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
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		List<Long> ammeterCodes = ammeterDao.selectAmmeterCode(stationId);
		double lastYearKwh = 0D;
		Double lastYearKwhD =elecDataDayDao.sumKwhByDays(df.format(lastYearSpace[0]), 
				df.format(lastYearSpace[1]), type, ammeterCodes);
		if (lastYearKwhD!=null) {
			lastYearKwh=lastYearKwhD;
		}
		return lastYearKwh;
	}


	/**
	 * 获取当前时间的发电/用电总量
	 */
	public Map<String, Object> getNowToalKwh(Long stationId, Integer type) {

		List<Long> ammeterCodes = ammeterDao.selectAmmeterCode(stationId);
		Double toalKwh = 0D;
		Double kw = 0D;
		if (type==1) {
			toalKwh=ammeterDao.initTotalkwh(stationId)+ammeterDao.workTotalkwh(stationId);
			kw=ammeterDao.nowKw(stationId);
		}else if (type==2) {
			String table="am_phase_record_" +new SimpleDateFormat("yyyy_MM_dd").format(new Date());
			Map<String, Object> mapUser =new HashMap<>();
			mapUser.put("ammeterCodes", ammeterCodes);
			mapUser.put("type", 2);
			mapUser.put("table", table);
			AmPhaseRecord amPhaseRecord =amPhaseRecordMapper.nowAmPhaseRecord(mapUser);
			toalKwh=amPhaseRecord.getKwhTotal();
			kw=amPhaseRecord.getKw();	
		}
		Map<String, Object> map = new HashMap<>();

		map.put("toalKwh", NumberUtil.accurateToTwoDecimal(toalKwh));

		map.put("kw", NumberUtil.accurateToTwoDecimal(kw));
		map.put("todayKwh", NumberUtil.accurateToTwoDecimal(todayKwh(stationId, type)));

		return map;

	}

	/**
	 * 今日小时内的发电、用电量
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 */
	public List<Map<String, Object>> oneHourKwh(Long stationId) {
		List<Map<String, Object>> oneHourKwh=new ArrayList<>();
		List<Map<String, Object>> oneHourWork=getTodayKwhByStationId(stationId, 1);
		List<Map<String, Object>> oneHourUse=getTodayKwhByStationId(stationId, 2);
		for (Map<String, Object> mapWork : oneHourWork) {
			for (Map<String, Object> mapUse : oneHourUse) {
				if (mapUse.get("time").equals(mapWork.get("time"))) {
					Map<String, Object> map =new HashMap<>();
					map.put("time", mapWork.get("time"));
					map.put("workKw", mapWork.get("kw"));
					map.put("useKw", mapUse.get("kw"));
					oneHourKwh.add(map);
				}
			}
		}
		return oneHourKwh;
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

	
	
	/**
	 * 移动端获取当前时间段的用电发电数据
	 * @throws ParseException 
	 */
	public Map<String, Object> getMomentPower(Long stationId, Integer type) {
		Map<String, Object> maps=new HashMap<>();
		Map<String, Object> objectAmPhaseRecord=new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();
		List<Map<String, Object>> listArray = new ArrayList<>();
		List<Long> ammeterCodes = ammeterDao.selectAmmeterCode(stationId);
		//String table="am_phase_record_2017_11_18" ; //+new SimpleDateFormat("yyyy_MM_dd").format(new Date());
		String table="am_phase_record_" +new SimpleDateFormat("yyyy_MM_dd").format(new Date());
		Date[] todaySpace = DateUtil.getTodaySpace();
		Date startTime = todaySpace[0];
		String start=new SimpleDateFormat("yyyy-MM-dd HH:00:00").format(startTime);
		String end=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//		String start="2017-11-18 00:00:00";
//		String end="2017-11-18 23:59:59";
		SimpleDateFormat dFormat = new SimpleDateFormat("HH");
		Integer num= Integer.parseInt(dFormat.format(new Date()));
		List<Integer> recordTimeList=new ArrayList<>();
		
		List<AmPhaseRecord> AmPhaseRecordList=new ArrayList<>();
		if (type==1) {
			Map<String, Object> map1=new HashMap<>();
			map1.put("start", start);
			map1.put("type", 1);
			map1.put("end", end);
			map1.put("table", table);
			map1.put("ammeterCodes", ammeterCodes);
			List<AmPhaseRecord> AmPhaseRecordList01=amPhaseRecordMapper.findByAmmeterCodes(map1);
			List<AmPhaseRecord> AmPhaseRecordList1=new ArrayList<>();
			Map<String, Object> map2=new HashMap<>();
			map2.put("start", start);
			map2.put("type", 11);
			map2.put("end", end);
			map2.put("table", table);
			map2.put("ammeterCodes", ammeterCodes);
			List<AmPhaseRecord> AmPhaseRecordList02=amPhaseRecordMapper.findByAmmeterCodes(map2);
			List<AmPhaseRecord> AmPhaseRecordList2=new ArrayList<>();
			for (int i = 0; i < AmPhaseRecordList01.size(); i++) {
				if (i==0) {
					AmPhaseRecord AmPhaseRecord=new AmPhaseRecord();
					AmPhaseRecord.setMeterState(AmPhaseRecordList01.get(i).getMeterState());
					AmPhaseRecord.setKwh(AmPhaseRecordList01.get(i).getKwh()-AmPhaseRecordList01.get(i).getaKwhTotal());
					AmPhaseRecord.setKw(AmPhaseRecordList01.get(i).getKw());
					AmPhaseRecordList1.add(AmPhaseRecord);
				}else {
					AmPhaseRecord AmPhaseRecord=new AmPhaseRecord();
					AmPhaseRecord.setMeterState(AmPhaseRecordList01.get(i).getMeterState());
					AmPhaseRecord.setKwh(AmPhaseRecordList01.get(i).getKwh()-AmPhaseRecordList01.get(i-1).getKwh());
					AmPhaseRecord.setKw(AmPhaseRecordList01.get(i).getKw());
					AmPhaseRecordList1.add(AmPhaseRecord);
				}
				
			}
			for (int i = 0; i < AmPhaseRecordList02.size(); i++) {
				if (i==0) {
					AmPhaseRecord AmPhaseRecord=new AmPhaseRecord();
					AmPhaseRecord.setMeterState(AmPhaseRecordList02.get(i).getMeterState());
					AmPhaseRecord.setKwh(AmPhaseRecordList02.get(i).getKwh()-AmPhaseRecordList02.get(i).getaKwhTotal());
					AmPhaseRecord.setKw(AmPhaseRecordList02.get(i).getKw());
					AmPhaseRecordList1.add(AmPhaseRecord);
				}else {
					AmPhaseRecord AmPhaseRecord=new AmPhaseRecord();
					AmPhaseRecord.setMeterState(AmPhaseRecordList02.get(i).getMeterState());
					AmPhaseRecord.setKwh(AmPhaseRecordList02.get(i).getKwh()-AmPhaseRecordList02.get(i-1).getKwh());
					AmPhaseRecord.setKw(AmPhaseRecordList02.get(i).getKw());
					AmPhaseRecordList2.add(AmPhaseRecord);
				}
				
			}
			
			if (AmPhaseRecordList1.size()>0) {
				for (AmPhaseRecord AmPhaseRecord1 : AmPhaseRecordList1) {
				     if (!recordTimeList.contains(Integer.parseInt(AmPhaseRecord1.getMeterState()))) {
					     recordTimeList.add(Integer.parseInt(AmPhaseRecord1.getMeterState()));
					     objectAmPhaseRecord.put(AmPhaseRecord1.getMeterState(), AmPhaseRecord1);  
				     }else if (recordTimeList.contains(Integer.parseInt(AmPhaseRecord1.getMeterState()))) {
				    	 AmPhaseRecord AmPhaseRecord=(AmPhaseRecord)objectAmPhaseRecord.get(AmPhaseRecord1.getMeterState());
				    	 AmPhaseRecord AmPhaseRecordOne=new AmPhaseRecord();
				    	 AmPhaseRecordOne.setMeterState(AmPhaseRecord.getMeterState());
				    	 AmPhaseRecordOne.setKw(AmPhaseRecord.getKw()+AmPhaseRecord1.getKw());
				    	 AmPhaseRecordOne.setKwh(AmPhaseRecord.getKwh()+AmPhaseRecord1.getKwh());
				    	 objectAmPhaseRecord.remove(AmPhaseRecord1.getMeterState());
				    	 objectAmPhaseRecord.put(AmPhaseRecordOne.getMeterState(), AmPhaseRecordOne);
				     } 
			    }
			}
			if (AmPhaseRecordList2.size()>0) {
				for (AmPhaseRecord AmPhaseRecord2 : AmPhaseRecordList2) {
				     if (!recordTimeList.contains(Integer.parseInt(AmPhaseRecord2.getMeterState()))) {
					     recordTimeList.add(Integer.parseInt(AmPhaseRecord2.getMeterState()));
					     objectAmPhaseRecord.put(AmPhaseRecord2.getMeterState(), AmPhaseRecord2); 
				     }else if (recordTimeList.contains(Integer.parseInt(AmPhaseRecord2.getMeterState()))) {
				    	 AmPhaseRecord AmPhaseRecord=(AmPhaseRecord)objectAmPhaseRecord.get(AmPhaseRecord2.getMeterState());
				    	 AmPhaseRecord AmPhaseRecordOne=new AmPhaseRecord();
				    	 AmPhaseRecordOne.setMeterState(AmPhaseRecord.getMeterState());
				    	 AmPhaseRecordOne.setKw(AmPhaseRecord.getKw()+AmPhaseRecord2.getKw());
				    	 AmPhaseRecordOne.setKwh(AmPhaseRecord.getKwh()+AmPhaseRecord2.getKwh());
				    	 objectAmPhaseRecord.remove(AmPhaseRecord2.getMeterState());
				    	 objectAmPhaseRecord.put(AmPhaseRecordOne.getMeterState(), AmPhaseRecordOne);
				     } 
			    }
			}
		}else if (type==2) {
			Map<String, Object> map3=new HashMap<>();
			map3.put("start", start);
			map3.put("type", 2);
			map3.put("end", end);
			map3.put("table", table);
			map3.put("ammeterCodes", ammeterCodes);
			List<AmPhaseRecord> AmPhaseRecordList03=amPhaseRecordMapper.findByAmmeterCodes(map3);
			List<AmPhaseRecord> AmPhaseRecordList3=new ArrayList<>();
			for (int i = 0; i < AmPhaseRecordList03.size(); i++) {
				if (i==0) {
					AmPhaseRecord AmPhaseRecord=new AmPhaseRecord();
					AmPhaseRecord.setMeterState(AmPhaseRecordList03.get(i).getMeterState());
					AmPhaseRecord.setKwh(AmPhaseRecordList03.get(i).getKwh()-AmPhaseRecordList03.get(i).getaKwhTotal());
					AmPhaseRecord.setKw(AmPhaseRecordList03.get(i).getKw());
					AmPhaseRecordList3.add(AmPhaseRecord);
				}else {
					AmPhaseRecord AmPhaseRecord=new AmPhaseRecord();
					AmPhaseRecord.setMeterState(AmPhaseRecordList03.get(i).getMeterState());
					AmPhaseRecord.setKwh(AmPhaseRecordList03.get(i).getKwh()-AmPhaseRecordList03.get(i-1).getKwh());
					AmPhaseRecord.setKw(AmPhaseRecordList03.get(i).getKw());
					AmPhaseRecordList3.add(AmPhaseRecord);
				}
				
			}
			if (AmPhaseRecordList3.size()>0) {
				for (AmPhaseRecord AmPhaseRecord3 : AmPhaseRecordList3) {
				     if (!recordTimeList.contains(Integer.parseInt(AmPhaseRecord3.getMeterState()))) {
					     recordTimeList.add(Integer.parseInt(AmPhaseRecord3.getMeterState()));
					     objectAmPhaseRecord.put(AmPhaseRecord3.getMeterState(), AmPhaseRecord3); 
				     }else if (recordTimeList.contains(Integer.parseInt(AmPhaseRecord3.getMeterState()))) {
				    	 AmPhaseRecord AmPhaseRecord=(AmPhaseRecord)objectAmPhaseRecord.get(AmPhaseRecord3.getMeterState());
				    	 AmPhaseRecord AmPhaseRecordOne=new AmPhaseRecord();
				    	 AmPhaseRecordOne.setMeterState(AmPhaseRecord.getMeterState());
				    	 AmPhaseRecordOne.setKw(AmPhaseRecord.getKw()+AmPhaseRecord3.getKw());
				    	 AmPhaseRecordOne.setKwh(AmPhaseRecord.getKwh()+AmPhaseRecord3.getKwh());
				    	 objectAmPhaseRecord.remove(AmPhaseRecord3.getMeterState());
				    	 objectAmPhaseRecord.put(AmPhaseRecordOne.getMeterState(), AmPhaseRecordOne);
				     } 
			    }
			}
		}
		
		  Set<Map.Entry<String,Object>> entrySet = objectAmPhaseRecord.entrySet();
	        Iterator<Map.Entry<String,Object>> it = entrySet.iterator();

	        while(it.hasNext())
	        { 
	            Map.Entry<String,Object> me = it.next();
	            AmPhaseRecordList.add((AmPhaseRecord)me.getValue());
	        }
		
		for (int i = 0; i <= num; i++) {
			if (!recordTimeList.contains(i)) {
				Map<String, Object> mapNum = new HashMap<>();
				mapNum.put("time", i);
				mapNum.put("kwh", 0.00);
				mapNum.put("kw", 0.00);
				list.add(mapNum);
			}
		}
		for (AmPhaseRecord AmPhaseRecord : AmPhaseRecordList) {
			Map<String, Object> mapAmPhaseRecord = new HashMap<>();
			mapAmPhaseRecord.put("time", Integer.parseInt(AmPhaseRecord.getMeterState()));
			mapAmPhaseRecord.put("kwh", NumberUtil.accurateToTwoDecimal(AmPhaseRecord.getKwh()));
			mapAmPhaseRecord.put("kw", NumberUtil.accurateToTwoDecimal(AmPhaseRecord.getKw()));
			list.add(mapAmPhaseRecord);
		}
		for (int i = 0; i < list.size(); i++) {
		for	(Map<String, Object> mapObject:list ){
			if (i==(int)mapObject.get("time")) {
				listArray.add(mapObject);
			}
		}
	}
		double todayKwh = todayKwhDetail(ammeterCodes, type);

		if (type == 1) {
			double treeNum = todayKwh * Double.valueOf(systemConfigService.get("plant_trees_prm"));
			maps.put("treeNum", NumberUtil.accurateToTwoDecimal(treeNum));
			maps.put("capacity",NumberUtil.accurateToTwoDecimal(stationDao.findCapacity(stationId)) );
		}
		
		maps.put("todayKwh", NumberUtil.accurateToTwoDecimal(todayKwh));
		maps.put("nowKw", NumberUtil.accurateToTwoDecimal(todayKw(ammeterCodes, type)));

		maps.put("list", listArray);
		
		return maps;
	}
	
	/**
	 * 今日发电/用电
	 *
	 * @param stationId
	 * @param type
	 * @return
	 */
	public double todayKwhDetail(List<Long> ammeterCodes , Integer type) {
		
		Date[] todaySpace = DateUtil.getTodaySpace();
		
	  String table="am_phase_record_" +new SimpleDateFormat("yyyy_MM_dd").format(new Date());
//	        String table="am_phase_record_2017_11_18" ;
//			String start="2017-11-18 00:00:00";
//			String end="2017-11-18 23:59:59";
	    
//		map.put("start", start);
//		map.put("end", end);
	  double kwh=0D;
		if (type==1) {
			
			Map<String, Object> map1 =new HashMap<>();
			map1.put("ammeterCodes", ammeterCodes);
			map1.put("start", todaySpace[0]);
			map1.put("end", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			map1.put("type", 1);
			map1.put("table", table);
			AmPhaseRecord amPhaseRecord1 =amPhaseRecordMapper.todayKwh(map1);
			if (amPhaseRecord1!=null) {
				kwh+=amPhaseRecord1.getKwh();
			}
			
			Map<String, Object> map2 =new HashMap<>();
			map2.put("ammeterCodes", ammeterCodes);
			map2.put("start", todaySpace[0]);
			map2.put("end", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			map2.put("type", 11);
			map2.put("table", table);
			AmPhaseRecord amPhaseRecord2 =amPhaseRecordMapper.todayKwh(map2);
			if (amPhaseRecord2!=null) {
				kwh+=amPhaseRecord2.getKwh();
			}
		}else if (type==2) {
			Map<String, Object> map3 =new HashMap<>();
			map3.put("ammeterCodes", ammeterCodes);
			map3.put("start", todaySpace[0]);
			map3.put("end", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			map3.put("type", 2);
			map3.put("table", table);
			AmPhaseRecord amPhaseRecord3 =amPhaseRecordMapper.todayKwh(map3);
			if (amPhaseRecord3!=null) {
				kwh+=amPhaseRecord3.getKwh();
			}
		}
		return kwh;
	}
	/**
	 * 今日发电/用电
	 *
	 * @param stationId
	 * @param type
	 * @return
	 */
 public double todayKw(List<Long> ammeterCodes , Integer type) {
		
	  String table="am_phase_record_" +new SimpleDateFormat("yyyy_MM_dd").format(new Date());
//	        String table="am_phase_record_2017_11_18" ;
//			String start="2017-11-18 00:00:00";
//			String end="2017-11-18 23:59:59";
	    
//		map.put("start", start);
//		map.put("end", end);
	  double kw=0D;
		if (type==1) {
			Map<String, Object> map1 =new HashMap<>();
			map1.put("ammeterCodes", ammeterCodes);
			map1.put("type", 1);
			map1.put("table", table);
			List<AmPhaseRecord> amPhaseRecord1 =amPhaseRecordMapper.nowKw(map1);
			if (amPhaseRecord1.size()>0) {
				kw+=amPhaseRecord1.get(amPhaseRecord1.size()-1).getKw();
			}
			
			Map<String, Object> map2 =new HashMap<>();
			map2.put("ammeterCodes", ammeterCodes);
			map2.put("type", 11);
			map2.put("table", table);
			List<AmPhaseRecord>  amPhaseRecord2 =amPhaseRecordMapper.nowKw(map2);
			if (amPhaseRecord2.size()>0) {
				kw+=amPhaseRecord2.get(amPhaseRecord2.size()-1).getKw();
			}
		}else if (type==2) {
			Map<String, Object> map3 =new HashMap<>();
			map3.put("ammeterCodes", ammeterCodes);
			map3.put("type", 2);
			map3.put("table", table);
			List<AmPhaseRecord> amPhaseRecord3 =amPhaseRecordMapper.nowKw(map3);;
			if (amPhaseRecord3.size()>0) {
				kw+=amPhaseRecord3.get(amPhaseRecord3.size()-1).getKw();
			}
		}
		return kw;
	}
 
}

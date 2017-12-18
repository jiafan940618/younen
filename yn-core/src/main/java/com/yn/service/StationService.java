package com.yn.service;


import com.yn.dao.AmmeterDao;
import com.yn.dao.ElecDataDayDao;
import com.yn.dao.OrderDao;
import com.yn.dao.StationDao;
import com.yn.dao.SubsidyDao;
import com.yn.dao.mapper.StationMapper;
import com.yn.enums.DeleteEnum;
import com.yn.enums.NoticeEnum;
import com.yn.exception.MyException;
import com.yn.model.Ammeter;
import com.yn.model.Order;
import com.yn.model.Station;
import com.yn.model.Subsidy;
import com.yn.utils.BeanCopy;
import com.yn.utils.DateUtil;
import com.yn.utils.NumberUtil;
import com.yn.utils.ObjToMap;
import com.yn.vo.StationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

@Service
public class StationService {
	@Autowired
	StationDao stationDao;
	@Autowired
	StationMapper stationMapper;
	@Autowired
	ElecDataHourService elecDataHourService;
	@Autowired
	SystemConfigService systemConfigService;
	@Autowired
	SubsidyDao subsidyDao;
	@Autowired
	OrderDao orderDao;
	@Autowired
	AmmeterDao ammeterDao;
	@Autowired
	ServerService serverService;
	@Autowired
	AmmeterService ammeterService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	OrderService orderService;
	@Autowired
	ElecDataDayDao elecDataDayDao;
	

	private static DecimalFormat df = new DecimalFormat("0.00");
	private static DecimalFormat df1 = new DecimalFormat("0000");
	private static Random rd = new Random();
	private static SimpleDateFormat format = new SimpleDateFormat("yyMMddHH");
	/** 自定义进制(0,1没有加入,容易与o,l混淆) */
	private static final char[] r = new char[] { 'q', 'w', 'e', '8', 'a', 's', '2', 'd', 'z', 'x', '9', 'c', '7', 'p',
			'5', 'i', 'k', '3', 'm', 'j', 'u', 'f', 'r', '4', 'v', 'y', 'l', 't', 'n', '6', 'b', 'g', 'h' };
	/** (不能与自定义进制有重复) */
	private static final char b = 'o';
	/** 进制长度 */
	private static final int binLen = r.length;

	public Station findOne(Long id) {
		return stationDao.findOne(id);
	}

	public Station FindByStationCode(Long orderId) {

		return stationDao.FindByStationCode(orderId);
	}

	public Station selectByPrimaryKey(Integer id) {

		return stationMapper.selectByPrimaryKey(id);
	}
	
	public List<Long> FindByStationId(){
		
		List<Long> ids = new  LinkedList<Long>();
		
		List<Integer> list =stationDao.FindByStationId();
		
		for (Integer integer : list) {
			ids.add(Long.valueOf(integer));
		}
		
		return ids;
	}
	
	/** 业务员查询所有电站*/
	public List<Station> getselStation(){
		List<Station> list =	stationDao.getselStation();
		
		for (Station station : list) {
			station.setOrder(null);
			station.setAmmeter(null);
			station.setServer(null);
			station.setUser(null);
		}
		
		return list;
	}

	public void save(Station station) {
		if (station.getId() != null) {
			Station one = stationDao.findOne(station.getId());
			try {
				BeanCopy.beanCopy(station, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			stationDao.save(one);
		} else {
			stationDao.save(station);
		}
		System.out.println();
	}

	@Transactional
	public void delete(Long id) {
		// 1.删除电站
		stationDao.delete(id);

		// 2.删除订单
		Station station = stationDao.findOne(id);
		if (station.getDel().equals(DeleteEnum.DEL.getCode())) {
			// 将采集器码设置成 null
			station.setDevConfCode(null);
			stationDao.save(station);

			Order order = orderDao.findOne(station.getOrderId());
			orderDao.delete(order.getId());

			// 解绑电表
			Set<Ammeter> ammeters = station.getAmmeter();
			for (Ammeter ammeter : ammeters) {
				ammeter.setStationId(null);
				ammeterDao.save(ammeter);
			}
		}

		// 3.删除未读信息
		noticeService.delete(NoticeEnum.NEW_STATION.getCode(), id);
	}

	public void deleteBatch(List<Long> id) {
		stationDao.deleteBatch(id);
	}

	public Station findOne(Station station) {
		Specification<Station> spec = getSpecification(station);
		Station findOne = stationDao.findOne(spec);
		return findOne;
	}

	public List<Station> findAll(List<Long> list) {
		return stationDao.findAll(list);
	}

	public Page<Station> findAll(Station station, Pageable pageable) {
		Specification<Station> spec = getSpecification(station);
		Page<Station> findAll = stationDao.findAll(spec, pageable);
		return findAll;
	}

	public List<Station> findAll(Station station) {
		Specification<Station> spec = getSpecification(station);
		return stationDao.findAll(spec);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Specification<Station> getSpecification(Station station) {
		station.setDel(0);
		Map<String, Object> objectMap = ObjToMap.getObjectMap(station);
		return (Root<Station> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

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

			// 根据电站码，电站名，联系人，联系人手机号，城市，采集器码，服务商名称
			if (!StringUtils.isEmpty(station.getQuery())) {
				Predicate[] predicates = new Predicate[7];
				predicates[0] = cb.like(root.get("stationCode"), "%" + station.getQuery() + "%");
				predicates[1] = cb.like(root.get("stationName"), "%" + station.getQuery() + "%");
				predicates[2] = cb.like(root.get("linkMan"), "%" + station.getQuery() + "%");
				predicates[3] = cb.like(root.get("linkPhone"), "%" + station.getQuery() + "%");
				predicates[4] = cb.like(root.get("cityText"), "%" + station.getQuery() + "%");
				predicates[5] = cb.like(root.get("devConfCode"), "%" + station.getQuery() + "%");
				predicates[6] = cb.like(root.get("server").get("companyName"), "%" + station.getQuery() + "%");
				expressions.add(cb.or(predicates));
			}

			// 根据日期筛选
			String queryStartDtm = station.getQueryStartDtm();
			String queryEndDtm = station.getQueryEndDtm();
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
	 * 电站信息
	 *
	 * @param stationId
	 * @return
	 */
	public Map<String, Object> stationInfo(Long stationId) {
		Station station = stationDao.findOne(stationId);
		station.setUser(null);
		station.setServer(null);
		station.setAmmeter(null);

		Map<String, Object> objectMap = ObjToMap.getObjectMap(station);

		// 今日发电量
		double todayKwh = elecDataHourService.todayKwh(stationId, 1);
		objectMap.put("todayEG", todayKwh);
		// 昨日发电量
		double yesterdayKwh = elecDataHourService.yesterdayKwh(stationId, 1);
		objectMap.put("yesterdayEG", yesterdayKwh);
		// 当月发电量
		double thisMonthKwh = elecDataHourService.thisMonthKwh(stationId, 1);
		objectMap.put("thisMonthEG", thisMonthKwh);
		// 上月发电量
		double lastMonthKwh = elecDataHourService.lastMonthKwh(stationId, 1);
		objectMap.put("lastMonthEG", lastMonthKwh);
		// 今年发电量
		double thisYearKwh = elecDataHourService.thisYearKwh(stationId, 1);
		objectMap.put("thisYearEG", thisYearKwh);
		// 去年发电量
		double lastYearKwh = elecDataHourService.lastYearKwh(stationId, 1);
		objectMap.put("lastYearEG", lastYearKwh);

		// 电站总发电量
		Double egt = 0D;
		Double nowKw=0D;
		Integer workTotaTm=0;
		List<Ammeter> ammeters = ammeterDao.findByStationId(stationId);
		if (!ammeters.isEmpty()) {
			for (Ammeter ammeter : ammeters) {
				egt += ammeter.getInitKwh() + ammeter.getWorkTotalKwh();
				nowKw+=ammeter.getNowKw();
				workTotaTm +=ammeter.getWorkTotalTm();	
			}
		}	
		objectMap.put("electricityGenerationTol", NumberUtil.accurateToTwoDecimal(egt));
		objectMap.put("nowKw", NumberUtil.accurateToTwoDecimal(nowKw));
		objectMap.put("workTotaTm", workTotaTm);
		
		// 电站装机容量
		Double capacity = station.getCapacity();
		// 相当于植树
		double plantTreesPrm = Double.valueOf(systemConfigService.get("plant_trees_prm"));
		objectMap.put("plantTreesPrm", NumberUtil.accurateToTwoDecimal(plantTreesPrm * egt));
		// 相当于减排二氧化碳
		Double CO2Prm = Double.valueOf(systemConfigService.get("CO2_prm"));
		objectMap.put("CO2Prm", NumberUtil.accurateToTwoDecimal(CO2Prm * egt));
		// 相当于减排二氧化硫
		Double SOPrm = Double.valueOf(systemConfigService.get("SO_prm"));
		objectMap.put("SOPrm", NumberUtil.accurateToTwoDecimal(SOPrm * egt));
		// 节省面积
		Double saveSqmPrm = Double.valueOf(systemConfigService.get("save_sqm_prm"));
		objectMap.put("saveSqmPrm", NumberUtil.accurateToTwoDecimal(saveSqmPrm * capacity));

		return objectMap;
	}

	/**
	 * 25年收益
	 *
	 * @param stationId
	 * @return
	 */
	public Map<String, Object> get25YearIncome(Long stationId) {
		Station station = stationDao.findOne(stationId);

		// 25年总收益
		Double totalMoneyOf25Year = 0d;

		Subsidy subsidyR = new Subsidy();
		subsidyR.setCityId(station.getCityId());
		subsidyR.setType(station.getType());
		Subsidy sob = subsidyDao.findOne(Example.of(subsidyR));

		if (sob == null) {
			throw new MyException(777, "该电站所在的城市没有模拟数据");
		}

		// 电站使用年限: 25年
		int year = Integer.parseInt(systemConfigService.get("station_durable_years"));

		// 衰减率，每年0.8%（0.008），25年共20%
		Double dampingRate = Double.valueOf(systemConfigService.get("damping_rate")) / 100;

		// 年发电量=装机容量*年日招数
		Double yearTotalKWH = station.getCapacity() * Double.valueOf(sob.getSunCount());

		// 一年国家补贴=全年发电量*国家补贴（元/度）*国家补贴年限
		Double countrySub = Double.valueOf(systemConfigService.get("country_subsidy"));
		Double countrySubYear = Double.valueOf(systemConfigService.get("country_subsidy_year"));
		Double countrySubTotal = countrySub * countrySubYear * yearTotalKWH;
		Double countrySubOneYear = countrySub * yearTotalKWH;

		// 一年地方补贴=全年发电量*地方补贴（元/度）*地方补贴年限
		Double difangSub = sob.getAreaSubsidyPrice();
		int difangSubYear = sob.getAreaSubsidyYear();
		Double difangSubTotal = difangSub * difangSubYear * yearTotalKWH;
		Double difangSubOneYear = difangSub * yearTotalKWH;

		// 一年：节省电费 + 国家补贴 + 地方补贴 + 卖出电费
		DecimalFormat df = new DecimalFormat("#.00");

		List<Map<String, Object>> list = new ArrayList<>();
		for (int i = 1; i <= year; i++) {
			Map<String, Object> map = new HashMap<>();
			// 第几年
			map.put("year", i);
			// 国家补贴
			if (countrySubYear >= i) {
				map.put("countrySub", df.format(countrySubOneYear));
				totalMoneyOf25Year += countrySubOneYear;
			} else {
				map.put("countrySub", 0);
				totalMoneyOf25Year += 0;
			}
			// 地方补贴
			if (difangSubYear >= i) {
				map.put("areaSub", df.format(difangSubOneYear));
				totalMoneyOf25Year += difangSubOneYear;
			} else {
				map.put("areaSub", 0);
				totalMoneyOf25Year += 0;
			}

			if ((i - 1) == 0) {
				// 节省电费 = 年发电量 * 本地用电价格 * 自用率
				Double economicTotal = yearTotalKWH * sob.getUsePrice() * (1 - sob.getSellProportion());
				// 出售电费 = 年发电量 * 售电价格 * 出售率
				Double sellTotal = yearTotalKWH * sob.getSellPrice() * sob.getSellProportion();
				// 年发电量 * 衰减率
				yearTotalKWH = yearTotalKWH * (1 - dampingRate);

				// 第一年不用减去衰减率
				map.put("saveTol", df.format(economicTotal));
				map.put("sellTol", df.format(sellTotal));
				totalMoneyOf25Year += economicTotal;
				totalMoneyOf25Year += sellTotal;
			} else {
				Double economicTotal = yearTotalKWH * sob.getUsePrice() * (1 - sob.getSellProportion());
				Double sellTotal = yearTotalKWH * sob.getSellPrice() * sob.getSellProportion();
				yearTotalKWH = yearTotalKWH * (1 - dampingRate);

				map.put("saveTol", df.format(economicTotal));
				map.put("sellTol", df.format(sellTotal));
				totalMoneyOf25Year += economicTotal;
				totalMoneyOf25Year += sellTotal;
			}

			list.add(map);
		}
		String totalMoneyOf25YearStr = df.format(totalMoneyOf25Year);

		Map<String, Object> rm = new HashMap<>();
		rm.put("list", list);
		rm.put("tolMoneyOf25Year", totalMoneyOf25YearStr);
		return rm;
	}

	/**
	 * 更改电站的通道模式
	 *
	 * @param stationId
	 * @param passageModel
	 * @return
	 */
	public Station changPassageModel(Long stationId, Integer passageModel) {
		Station station = stationDao.findOne(stationId);
		station.setPassageModel(passageModel);
		stationDao.save(station);
		return station;
	}

	public void insertStation(Order order) {
		/** 生成电站码*/
		String stradNo = format.format(System.currentTimeMillis()) + df1.format(rd.nextInt(9999));
		order = orderService.findOne(order.getId());
		Station station = new Station();
		station.setStationCode(stradNo);
		station.setAddressText(order.getAddressText());
		
		String StationName = order.getLinkMan()+"的电站";

		station.setStationName(StationName);
		station.setCapacity(order.getCapacity());
		station.setOrderId(order.getId());
		station.setLinkMan(order.getLinkMan());
		station.setLinkPhone(order.getLinkPhone());
		station.setUserId(order.getUserId());
		station.setType(order.getType());
		station.setCityId(order.getCityId());
		station.setCityText(order.getCityText());
		station.setServerId(order.getServerId());
		station.setStatus(0);//默认未绑定。
		
		save(station);
	}
	
	public void insertSta(Order order) {
		/** 生成电站码*/
		String stradNo = format.format(System.currentTimeMillis()) + df1.format(rd.nextInt(9999));
		order = orderService.findOne(order.getId());
		Station station = new Station();
		station.setStationCode(stradNo);
		station.setAddressText(order.getAddressText());
		
		String StationName = order.getLinkMan()+"的电站";

		station.setStationName(StationName);
		station.setCapacity(order.getCapacity());
		station.setOrderId(order.getId());
		station.setLinkMan(order.getLinkMan());
		station.setLinkPhone(order.getLinkPhone());
		station.setUserId(order.getUserId());
		station.setType(order.getType());
		station.setCityId(order.getCityId());
		station.setCityText(order.getCityText());
		station.setServerId(order.getServerId());
		station.setStatus(1);//默认未绑定。
		station.setType(1);//默认居民
		station.setStatus(1);//正在发电
		
		save(station);
	}
	

	/**
	 * 用户的所有电站信息
	 */
	public Map<String, Object> stationByUser(List<Station> stations) {
		Map<String, Object> objectMap = new LinkedHashMap<String, Object>();
		double egt = 0;
		double nowKw = 0;
		double capacity = 0;
		double efficiency = 0;
		for (Station station : stations) {
			List<Ammeter> ammeters = ammeterDao.findByStationId(station.getId());
			for (Ammeter ammeter : ammeters) {
			// 发电功率
			nowKw = nowKw + ammeter.getNowKw();
			// 发电总量
			egt = egt + ammeter.getInitKwh() + ammeter.getWorkTotalKwh();
			}
			// 装机容量
			capacity = capacity + station.getCapacity();
		}
		if (capacity > nowKw) {
			// 发电效率（百分比）
			efficiency = (nowKw / capacity) * 100;
		}
		// 相当于植树
		double plantTreesPrm = egt * Double.valueOf(systemConfigService.get("plant_trees_prm"));
		// 相当于减排二氧化碳
		double CO2Prm = egt * Double.valueOf(systemConfigService.get("CO2_prm")) / 1000;
		objectMap.put("plantTreesPrm", NumberUtil.getIntegerTenThousand(plantTreesPrm));
		objectMap.put("CO2Prm", NumberUtil.getTenThousand(CO2Prm));
		objectMap.put("nowKw", NumberUtil.accurateToTwoDecimal(nowKw));
		objectMap.put("egt", NumberUtil.getTenThousand(egt));
		objectMap.put("efficiency", (int) efficiency);
		objectMap.put("capacity", NumberUtil.accurateToTwoDecimal(capacity));
		return objectMap;

	}

	/**
	 * 全网所有电站信息
	 */
	public Map<String, Object> stationByAll(List<Station> stations) {
		Map<String, Object> objectMap = new LinkedHashMap<String, Object>();
		double egt = 0;
		double nowKw = 0;
		double capacity = 0;
		double efficiency = 0;
		for (Station station : stations) {
		  // 装机容量
		  capacity = capacity + station.getCapacity();
		}
		List<Ammeter> ammeters = ammeterDao.findAll();
		for (Ammeter ammeter : ammeters) {
			// 发电功率
			nowKw = nowKw + ammeter.getNowKw();
			// 发电总量
			egt = egt + ammeter.getInitKwh() + ammeter.getWorkTotalKwh();
		}
		if (capacity > nowKw) {
			// 发电效率（百分比）
			efficiency = (nowKw / capacity) * 100;
		}
		// 相当于植树
		double plantTreesPrm = egt * Double.valueOf(systemConfigService.get("plant_trees_prm"));
		// 相当于减排二氧化碳
		double CO2Prm = egt * Double.valueOf(systemConfigService.get("CO2_prm")) / 1000;
		objectMap.put("plantTreesPrm", NumberUtil.getIntegerTenThousand(plantTreesPrm));
		objectMap.put("CO2Prm", NumberUtil.getTenThousand(CO2Prm));
		objectMap.put("nowKw", NumberUtil.accurateToTwoDecimal(nowKw));
		objectMap.put("egt", NumberUtil.getTenThousand(egt));
		objectMap.put("efficiency", (int) efficiency);
		objectMap.put("capacity", NumberUtil.accurateToTwoDecimal(capacity));
		return objectMap;

	}

	public List<StationVo> findByUserIdS(Long userId, Map<String, String> map) {

		List<StationVo> staList = new LinkedList<StationVo>();

		List<Object> list = stationDao.findByUserIdS(userId);

		Double plant_trees_prm = Double.valueOf(map.get("plant_trees_prm"));
		/**co2减排参数*/
		Double CO2_prm = Double.valueOf(map.get("CO2_prm"));

		for (Object object : list) {
			Object[] obj = (Object[]) object;

			Integer s_id = (Integer) obj[0];
			String stationName = (String) obj[1];
			Integer user_id = (Integer) obj[2];
			BigDecimal capacity = (BigDecimal) obj[3];
			Integer status = (Integer) obj[4];
			String stationCode = (String) obj[5];
			BigDecimal initkwh = (BigDecimal) obj[6];
			
			if(null == initkwh){
				initkwh =new BigDecimal(0.0);
			}
			
			BigDecimal workTotalKwh = (BigDecimal) obj[7];
			if(null == workTotalKwh){
				workTotalKwh =new BigDecimal(0.0);
			}
			
			Integer workTotalTm = (Integer) obj[8];
			if(null == workTotalTm){
				workTotalTm =0;
			}
			
			String userName = (String) obj[9];
			BigDecimal nowKwh = (BigDecimal) obj[10];

			DecimalFormat df = new DecimalFormat("#0.00");
			
			Double	electricityGenerationTol = initkwh.doubleValue() + workTotalKwh.doubleValue();


			StationVo stationVo = new StationVo();
			stationVo.setId(s_id.longValue());
			stationVo.setStationName(stationName);
			stationVo.setCapacity(capacity.doubleValue());
			stationVo.setStatus(status);
			stationVo.setStationCode(stationCode);
			stationVo.setWorkTotaTm(Double.valueOf(df.format(workTotalTm/60)));
			stationVo.setElectricityGenerationTol(Double.valueOf(df.format(electricityGenerationTol)));
			stationVo.setUserName(userName);

			
			/** co2排放量*/
			stationVo.setCO2_PM(df.format(electricityGenerationTol * CO2_prm));
			stationVo.setTrees_prm(df.format(electricityGenerationTol * plant_trees_prm));

			if (null == nowKwh) {
				stationVo.setNowKw(0.0);
			} else {
				stationVo.setNowKw(nowKwh.doubleValue());
			}

			staList.add(stationVo);
		}
		return staList;
	}
	
	public List<StationVo> findByList(List<Long> newlist, Map<String, String> map) {

		List<StationVo> staList = new LinkedList<StationVo>();

		List<Object> list = stationDao.findByList(newlist);

		Double plant_trees_prm = Double.valueOf(map.get("plant_trees_prm"));
		/**co2减排参数*/
		Double CO2_prm = Double.valueOf(map.get("CO2_prm"));

		for (Object object : list) {
			Object[] obj = (Object[]) object;

			Integer s_id = (Integer) obj[0];
			String stationName = (String) obj[1];
			Integer user_id = (Integer) obj[2];
			BigDecimal capacity = (BigDecimal) obj[3];
			Integer status = (Integer) obj[4];
			String stationCode = (String) obj[5];
			BigDecimal initkwh = (BigDecimal) obj[6];
			
			if(null == initkwh){
				initkwh =new BigDecimal(0.0);
			}
			
			BigDecimal workTotalKwh = (BigDecimal) obj[7];
			if(null == workTotalKwh){
				workTotalKwh =new BigDecimal(0.0);
			}
			
			Integer workTotalTm = (Integer) obj[8];
			if(null == workTotalTm){
				workTotalTm =0;
			}
			
			String userName = (String) obj[9];
			BigDecimal nowKwh = (BigDecimal) obj[10];

			DecimalFormat df = new DecimalFormat("#0.00");
			
			Double	electricityGenerationTol = initkwh.doubleValue() + workTotalKwh.doubleValue();


			StationVo stationVo = new StationVo();
			stationVo.setId(s_id.longValue());
			stationVo.setStationName(stationName);
			stationVo.setCapacity(capacity.doubleValue());
			stationVo.setStatus(status);
			stationVo.setStationCode(stationCode);
			stationVo.setWorkTotaTm(Double.valueOf(df.format(workTotalTm/60)));
			stationVo.setElectricityGenerationTol(Double.valueOf(df.format(electricityGenerationTol)));
			stationVo.setUserName(userName);

			
			/** co2排放量*/
			stationVo.setCO2_PM(df.format(electricityGenerationTol * CO2_prm));
			stationVo.setTrees_prm(df.format(electricityGenerationTol * plant_trees_prm));

			if (null == nowKwh) {
				stationVo.setNowKw(0.0);
			} else {
				stationVo.setNowKw(nowKwh.doubleValue());
			}

			staList.add(stationVo);
		}
		return staList;
	}
	

	/**
	 * 用户的装机容量
	 * 
	 */
	public List<Map<Object, Object>> checkCapacity(List<Station> stations, Integer type, String dateStr) {
		Map<Object, Object> objectMap = new HashMap<>();
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
			List<Map<Object, Object>> list = stationDao.numCapacity(station.getId(), dateFormat, dateStr);
			if (!list.isEmpty()) {
				lists.addAll(list);
			}

		}
		for (Map<Object, Object> map : lists) {
			if (!objectMap.containsKey(map.get("create_dtm"))) {

				objectMap.put(map.get("create_dtm"), map.get("capacity"));
			} else {
				double kwh = (double) objectMap.get(map.get("create_dtm")) + (double) map.get("capacity");
				objectMap.put(map.get("create_dtm"), (Object) kwh);
			}

		}
		Object[] key = objectMap.keySet().toArray();
		Arrays.sort(key);
		for (int i = 0; i < key.length; i++) {
			Map<Object, Object> listMap = new LinkedHashMap<>();
			linkHashMap.put(key[i], objectMap.get(key[i]));
			listMap.put("createDtm", key[i]);
			listMap.put("capacity", NumberUtil.accurateToTwoDecimal((Double) objectMap.get(key[i])));
			listsMap.add(listMap);
		}
		return listsMap;
	}

	// 0:未绑定电表,1:正在发电,2:电表异常
	public List<StationVo> getnewstation(Long userId) {

		List<StationVo> newlist = new LinkedList<StationVo>();

		List<Station> list = stationDao.getstation(userId);
		for (Station station : list) {
			StationVo stationVo = new StationVo();
			BeanCopy.copyProperties(station, stationVo);

			if (stationVo.getStatus() == 0) {
				stationVo.setRemark("未绑定电表");
			} else if (stationVo.getStatus() == 1) {
				stationVo.setRemark("正在发电");
			} else if (stationVo.getStatus() == 2) {
				stationVo.setRemark("电表异常");
			}
            if (stationVo.getStatus()!=0) {
    			List<Object> liststa = ammeterDao.findBynewStationId(station.getId());
    			/** 累计发电量*/
    			Double power = 0.0;
    			for (Object object : liststa) {

    				Object[] obj = (Object[]) object;
    				BigDecimal initKwh = (BigDecimal) obj[0];
    				BigDecimal workTotalKwh = (BigDecimal) obj[1];
    				Integer workTotalTm = (Integer) obj[2];

    				if (null != initKwh) {
    					power += initKwh.doubleValue();
    				}
    				if (null != workTotalKwh) {
    					power += workTotalKwh.doubleValue();
    				}

    				stationVo.setElectricityGenerationTol(power);
    				/** 工作时长*/
    				stationVo.setWorkTotaTm(Double.valueOf(df.format(workTotalTm/60)));
    				//stationVo.setWorkTotaTm(workTotalTm);
    			}
			}else {
				stationVo.setElectricityGenerationTol(0D);
				/** 工作时长*/
				stationVo.setWorkTotaTm(Double.valueOf(df.format(0D)));
			}
			newlist.add(stationVo);
		}

		return newlist;
	}


	/**
	 * 查询用户电站,电表等信息
	 * 
	 */
	public Map<String, Object> stationInformation(Long stationId) {

		Station station = stationDao.findOne(stationId);
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("stationCode", station.getStationCode());
		map.put("stationName", station.getStationName());
		map.put("capacity", station.getCapacity());
		map.put("orderCode", orderDao.findByStationId(station.getOrderId()));
		map.put("status", station.getStatus());
		map.put("addressText", station.getAddressText());
		
		System.out.println(station.getId());
		List<Ammeter> ammeters = ammeterDao.findByStationId(stationId);
		System.out.println(ammeters.size());
		if (ammeters.size()>0) {
			for (Ammeter ammeter : ammeters) {
				System.out.println(ammeter.getId());
				Map<String, Object> map2 = new LinkedHashMap<>();
				map2.put("workTotaTm", ammeter.getWorkTotalTm() + ammeter.getInitKwh());
				map2.put("ammeterCode", ammeter.getcAddr());
				map2.put("ammeterScale", ammeter.getInitKwh());
				map.put("ammeterRecode", map2);
			}
		}else {
			Map<String, Object> map2 = new LinkedHashMap<>();
			map2.put("workTotaTm", "0");
			map2.put("ammeterCode", "0");
			map2.put("ammeterScale","0");
			map.put("ammeterRecode", map2);
		}
		map.put("workInfo", elecDataHourService.getNowToalKwh(stationId, 1));
		map.put("useInfo", elecDataHourService.getNowToalKwh(stationId, 2));
		return map;
	}

	/**
	 * 根据session按时间获取用户收益
	 */
	public Map<String, Object> userIncome(Station station) {

		Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
		int days = aCalendar.getActualMaximum(Calendar.DATE);
		double price = 1 / (Double.valueOf(systemConfigService.get("watt_price"))) * 1000;
		Map<String, Object> map = new HashMap<>();
		// 今日预计收益
		map.put("todayIncome", NumberUtil.accurateToTwoDecimal(station.getCapacity() * 3.06 * price));
		// 本月预计收益
		map.put("monthIncome", NumberUtil.accurateToTwoDecimal(station.getCapacity() * 3.06 * days * price));
		// 本年预计收益
		map.put("yearIncome", NumberUtil.accurateToTwoDecimal(station.getCapacity() * 3.06 * 365 * price));

		return map;
	}
	/**
	 * 
	    * @Title: majorKeyByUserId
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param stations
	    * @param @return    参数
	    * @return Map<String,Object>    返回类型
	    * @throws
	 */
	public Map<String, Object> majorKeyByUserId(List<Station> stations) {
		
		Map<String, Object> map = new HashMap<>();
		List<Long> stationIds=new ArrayList<>();
	    for (Station station : stations) {
		    stationIds.add(station.getId());
	     }
	    Date date=new Date();
		String dateTime=new SimpleDateFormat("yyyy-MM-dd").format(date);
		List<Long>ammeterCodes=ammeterDao.selectAmmeterCodeByStationIds(stationIds);
	    double kw=ammeterDao.sumNowKwByStationIds(stationIds);
	    double kwh=elecDataDayDao.sumKwh(dateTime, dateTime, ammeterCodes);
		map.put("kwh", NumberUtil.accurateToTwoDecimal(kwh));
		map.put("kw", NumberUtil.accurateToTwoDecimal(kw));
		
		return map;
	}
	/**
	 * 
	    * @Title: majorKeyByAll
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @return Map<String,Object>    返回类型
	    * @throws
	 */
	public Map<String, Object> majorKeyByAll() {
		
		Date date=new Date();
		String dateTime=new SimpleDateFormat("yyyy-MM-dd").format(date);
		
		Map<String, Object> map = new HashMap<>();
		
		double kwh=elecDataDayDao.sumKwhAll(dateTime, dateTime);
		double kw=ammeterDao.sumNowKw();
		map.put("kwh", NumberUtil.accurateToTwoDecimal(kwh));
		map.put("kw", NumberUtil.accurateToTwoDecimal(kw));		
		return map;
	}
	
}

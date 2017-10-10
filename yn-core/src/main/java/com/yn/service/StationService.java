package com.yn.service;

import com.yn.dao.AmmeterDao;
import com.yn.dao.OrderDao;
import com.yn.dao.StationDao;
import com.yn.dao.SubsidyDao;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

@Service
public class StationService {
    @Autowired
    StationDao stationDao;
    
    @Autowired
    TemStationService temStationService;
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
    private NoticeService noticeService;
    
    
   	private static DecimalFormat df = new DecimalFormat("0.00");
   	private static DecimalFormat df1 = new DecimalFormat("0000");
   	private static Random rd = new Random();
       private  static	SimpleDateFormat format = new SimpleDateFormat("yyMMddHH");
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


    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Specification<Station> getSpecification(Station station) {
        station.setDel(0);
        Map<String, Object> objectMap = ObjToMap.getObjectMap(station);
        return (Root<Station> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

            Predicate conjunction = cb.conjunction();
            List<Expression<Boolean>> expressions = conjunction.getExpressions();
            Iterator<Entry<String, Object>> iterator = objectMap.entrySet().iterator();

            while (iterator.hasNext()) {
                Entry<String, Object> entry = iterator.next();
                if (!entry.getKey().equals("query") && !entry.getKey().equals("queryStartDtm") && !entry.getKey().equals("queryEndDtm")) {
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
                expressions.add(cb.greaterThanOrEqualTo(root.get("createDtm"), DateUtil.parseString(queryStartDtm, DateUtil.yyyy_MM_dd_HHmmss)));
            }
            if (!StringUtils.isEmpty(queryEndDtm)) {
                expressions.add(cb.lessThan(root.get("createDtm"), DateUtil.parseString(queryEndDtm, DateUtil.yyyy_MM_dd_HHmmss)));
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
        double todayKwh = temStationService.todayKwh(stationId, 1);
        objectMap.put("todayEG", todayKwh);
        // 昨日发电量
        double yesterdayKwh = temStationService.yesterdayKwh(stationId, 1);
        objectMap.put("yesterdayEG", yesterdayKwh);
        // 当月发电量
        double thisMonthKwh = temStationService.thisMonthKwh(stationId, 1);
        objectMap.put("thisMonthEG", thisMonthKwh);
        // 上月发电量
        double lastMonthKwh = temStationService.lastMonthKwh(stationId, 1);
        objectMap.put("lastMonthEG", lastMonthKwh);
        // 今年发电量
        double thisYearKwh = temStationService.thisYearKwh(stationId, 1);
        objectMap.put("thisYearEG", thisYearKwh);
        // 去年发电量
        double lastYearKwh = temStationService.lastYearKwh(stationId, 1);
        objectMap.put("lastYearEG", lastYearKwh);

        // 电站总发电量
        Double egt = station.getElectricityGenerationTol();
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
    
    
    public void insertStation(Order order){
    	/** 生成电站码*/
     String stradNo =  format.format(System.currentTimeMillis())+ df1.format(rd.nextInt(9999));
     
     Station station = new Station();
     station.setStationCode(stradNo);
     station.setAddressText(order.getAddressText());
     station.setStationName(order.getServerName());
     station.setCapacity(order.getCapacity());
     station.setOrderId(order.getId());
     station.setLinkMan(order.getLinkMan());
     station.setLinkPhone(order.getLinkPhone());
     station.setUserId(order.getUserId());
      /** 默认为居民*/
     station.setType(0);
     station.setServerId(order.getServerId());
     station.setStatus(0);
     
     save(station);
    }
    
    /**
     * 用户的所有电站信息
     */
    public Map<String, Object> stationByUser(List<Station> stations){
    	Map<String, Object> objectMap = new LinkedHashMap<String, Object>();
    	double  egt=0;
    	double nowKw=0;
    	double plantTreesPrm=0;
    	double CO2Prm=0;
    	double capacity=0;
    	double efficiency=0;
    	for(Station station2 :stations){
    		// 相当于植树
         plantTreesPrm=plantTreesPrm+Double.valueOf(systemConfigService.get("plant_trees_prm"));
            // 相当于减排二氧化碳
         CO2Prm =CO2Prm+ Double.valueOf(systemConfigService.get("CO2_prm"));
            //发电功率
         nowKw=nowKw+station2.getNowKw();
            //发电总量
         egt=egt+station2.getElectricityGenerationTol();
            //装机容量
         capacity=capacity+station2.getCapacity();
    	}
    	 if (capacity>nowKw) {
        	 //发电效率（百分比）
       	  efficiency=(nowKw/capacity)*100;
		 }
    	objectMap.put("plantTreesPrm",(int) NumberUtil.accurateToTwoDecimal(plantTreesPrm * egt));
    	objectMap.put("CO2Prm", NumberUtil.accurateToTwoDecimal(CO2Prm * egt)/1000);
    	objectMap.put("nowKw",nowKw);
    	objectMap.put("egt", egt);
    	objectMap.put("efficiency", (int)efficiency);
    	return objectMap;
    }
    
    /**
     * 用户的装机容量
     * 
     */
    public Map<Object, Object> checkCapacity(List<Station> stations){
    	Map<Object, Object> objectMap=new HashMap<>();
    	Map<Object, Object> linkHashMap=new LinkedHashMap<>();
    	List<Map<Object, Object>> lists=new ArrayList<>();
    	for (Station station : stations) {
    		List<Map<Object, Object>> list=stationDao.findUserCapacity(station.getId());
           if (!list.isEmpty()) {
	          lists.addAll(list);
			}
    			
    	}
    	for(Map<Object, Object> map : lists) {
    		if (!objectMap.containsKey(map.get("create_dtm"))) {
    			
    			objectMap.put(map.get("create_dtm"), map.get("capacity"));
			}else{
				double kwh=(double)objectMap.get(map.get("create_dtm"))+(double)map.get("capacity");
				objectMap.put(map.get("create_dtm"), (Object)kwh);
			}
    		
    	}
    	Object[] key = objectMap.keySet().toArray();
    	Arrays.sort(key);
    	for (int i = 0; i < key.length; i++) { 
    		linkHashMap.put(key[i], objectMap.get(key[i]));
        	}
    	return linkHashMap;
    }

}

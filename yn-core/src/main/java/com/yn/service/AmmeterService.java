package com.yn.service;

import com.yn.dao.AmmeterDao;
import com.yn.dao.ServerDao;
import com.yn.dao.StationDao;
import com.yn.dao.UserDao;
import com.yn.dao.mapper.AmmeterMapper;
import com.yn.enums.*;
import com.yn.exception.MyException;
import com.yn.model.Ammeter;
import com.yn.model.City;
import com.yn.model.Province;
import com.yn.model.Station;
import com.yn.utils.BeanCopy;
import com.yn.utils.DateUtil;
import com.yn.utils.NumberUtil;
import com.yn.utils.ObjToMap;
import com.yn.utils.StringUtil;
import com.yn.vo.AmmeterVo;
import com.yn.vo.UserVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

@Service
public class AmmeterService {


    @Autowired
    AmmeterDao ammeterDao;
    @Autowired
    CityService cityService;
    @Autowired
    ProvinceService provinceService;
    @Autowired
    StationService stationService;
    @Autowired
    StationDao stationDao;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AmmeterMapper ammeterMapper;
	@Autowired
	SystemConfigService systemConfigService;
	@Autowired
    ServerDao serverDao;
    
   public Ammeter findByCAddr(String caddr){
    	return ammeterDao.findByCAddr(caddr);
    }

    public Ammeter findOne(Long id) {
        return ammeterDao.findOne(id);
    }

    public Ammeter save(Ammeter ammeter) {
        if (ammeter.getId() != null) {
            Ammeter one = ammeterDao.findOne(ammeter.getId());
            try {
                BeanCopy.beanCopy(ammeter, one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ammeterDao.save(one);
        } else {
            return ammeterDao.save(ammeter);
        }
    }

    /** 根据userId查询总电量并相加*/
  public  Double findByUserId(UserVo userVo){

	  List<Object> list = ammeterDao.findByUserId(userVo);
	  /** 累计发电量*/
	  Double power =0.0; 
	  for (Object object : list) {
		   
		  Object[] obj = (Object[])object;
		  BigDecimal initKwh = (BigDecimal)obj[0];
		  BigDecimal workTotalKwh = (BigDecimal)obj[1];
		  
		   //.add(workTotalKwh))
		
			  if(null != initKwh){
				  power +=  initKwh.doubleValue();  
			  }
			  if(null != workTotalKwh){
				  power +=  workTotalKwh.doubleValue();  
			  }
	   
	}
	return power;
    }

    @Transactional
    public void delete(Long id) {
        // 1.删除电表
        ammeterDao.delete(id);

        // 2.解绑电站
        relieveStation(id);

        // 3.删除未读信息
        noticeService.delete(NoticeEnum.NEW_AMMETER.getCode(), id);
    }

    public void deleteBatch(List<Long> id) {
        ammeterDao.deleteBatch(id);
    }

    public Ammeter findOne(Ammeter ammeter) {
        Specification<Ammeter> spec = getSpecification(ammeter);
        Ammeter findOne = ammeterDao.findOne(spec);
        return findOne;
    }

    public List<Ammeter> findAll(List<Long> list) {
        return ammeterDao.findAll(list);
    }
    
    public List<Ammeter> findAllByMapper(Ammeter ammeter) {
        return ammeterMapper.selectByStationId(ammeter.getStationId().intValue());
    }

    public Page<Ammeter> findAll(Ammeter ammeter, Pageable pageable) {
        Specification<Ammeter> spec = getSpecification(ammeter);
        Page<Ammeter> findAll = ammeterDao.findAll(spec, pageable);
        return findAll;
    }

    public List<Ammeter> findAll(Ammeter ammeter) {
        Specification<Ammeter> spec = getSpecification(ammeter);
        return ammeterDao.findAll(spec);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Specification<Ammeter> getSpecification(Ammeter ammeter) {
        ammeter.setDel(0);
        Map<String, Object> objectMap = ObjToMap.getObjectMap(ammeter);
        return (Root<Ammeter> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

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

            // 根据采集器码，城市名，电站码，电站名
            if (!StringUtils.isEmpty(ammeter.getQuery())) {
                Predicate[] predicates = new Predicate[4];
                predicates[0] = cb.like(root.get("cAddr"), "%" + ammeter.getQuery() + "%");
                predicates[1] = cb.like(root.get("cityText"), "%" + ammeter.getQuery() + "%");
                predicates[2] = cb.like(root.join("station", JoinType.LEFT).get("stationCode"), "%" + ammeter.getQuery() + "%");
                predicates[3] = cb.like(root.join("station", JoinType.LEFT).get("stationName"), "%" + ammeter.getQuery() + "%");
                expressions.add(cb.or(predicates));
            }

            // 根据日期筛选
            String queryStartDtm = ammeter.getQueryStartDtm();
            String queryEndDtm = ammeter.getQueryEndDtm();
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
     * 新增电表 和 绑定电站
     *
     * @param ammeterVo
     * @return
     */
    @Transactional
    public Ammeter saveAndbindStation(AmmeterVo ammeterVo) {
        Ammeter ammeter = new Ammeter();
        BeanCopy.copyProperties(ammeterVo, ammeter);
        // 判断是否有关联电站
        if (ammeter.getStationId() == null) {
            throw new MyException(ResultEnum.NO_CHOOSE_STATION);
        }
        // 判断电表是否已经存在
        Ammeter ammeterR = new Ammeter();
        ammeterR.setcAddr(ammeter.getcAddr());
        ammeterR.setDel(DeleteEnum.NOT_DEL.getCode());
        Ammeter findOne = ammeterDao.findOne(Example.of(ammeterR));
        if (findOne != null) {
            if (ammeter.getId() == null) {
                ammeter.setId(findOne.getId());
            }
        }
        Ammeter result = save(ammeter);


        // 插入电表的未读信息
        if (findOne == null) {
            // 查找超级管理员、电表管理员、业务管理员、未认证服务商、已认证服务商的userId
            List<Long> userIds = userDao.findIdByRoleIds(Arrays.asList(1L, 2L, 3L, 4L, 5L));
            noticeService.insertBatch(NoticeEnum.NEW_AMMETER.getCode(), result.getId(), userIds);
        }

        // 判断电站已经存在的采集器码 与 要绑定的电表的采集器码是否一致
        Station station = stationService.findOne(ammeter.getStationId());
        if (!StringUtil.isEmpty(station.getDevConfCode())) {
            if (!station.getDevConfCode().equalsIgnoreCase(ammeter.getcAddr())) {
                throw new MyException(777, "电站已经绑定的采集器是：" + station.getDevConfCode()
                        + "，而新绑定电站的电表的采集器是：" + ammeter.getcAddr()
                        + "，请先解绑电站的电表再重新绑定该电表！");
            }
        }
        station.setDevConfCode(ammeter.getcAddr());
        // 如果电站未绑定电表，电站的状态改成正在发电
        if (station.getStatus().equals(StationStatusEnum.NOT_BINDING_AMMETER.getCode())) {
            station.setStatus(StationStatusEnum.ELECTRICITY_GENERATING.getCode());
        }
        stationService.save(station);

        return ammeter;
    }

    /**
     * 解绑电站
     *
     * @param ammeterId
     * @return
     */
    public Ammeter relieveStation(Long ammeterId) {
        // 将电表的stationId设置成null
        Ammeter findOne = ammeterDao.findOne(ammeterId);
        if (findOne != null) {
            Long stationId = findOne.getStationId();
            if (stationId != null) {
                findOne.setStationId(null);
                ammeterDao.save(findOne);

                changDevConfCodeAndStatus(stationId);
            }
        }

        return findOne;
    }

    /**
     * 判断电站是否还存在电表，
     * 如果电站下没有绑定电表了，
     * 将电站的 采集器码 字段设置成null，
     * 并且将电站的状态改成 0：未绑定电站
     *
     * @param stationId
     */
    private void changDevConfCodeAndStatus(Long stationId) {
        Station station = stationDao.findOne(stationId);
        if (CollectionUtils.isEmpty(station.getAmmeter())) {
            station.setDevConfCode(null);
            station.setStatus(StationStatusEnum.NOT_BINDING_AMMETER.getCode());
            stationDao.save(station);
        }
    }
    /**
     * 管理后台的节能减排量
     * @param stationId
     */
    public Map<String, Object> energyConservation(Long userId){ 
    	Double kwh=0D;
    	if (userDao.findOne(userId).getRoleId()!=Long.parseLong(systemConfigService.get("server_role_id"))) {
    		 kwh=ammeterDao.sumAllKwh();
		}else {
			Long serverId=serverDao.findByUserid(userId);
			 kwh=ammeterDao.sumKwh(serverId);
		}
    	if (kwh==null) {
    		kwh=0D;
		}
    	Map<String, Object> objectMap = new HashMap<>();
    	
    	// 相当于植树
    	double plantTreesPrm = Double.valueOf(systemConfigService.get("plant_trees_prm"));
    	objectMap.put("plantTreesPrm", NumberUtil.accurateToTwoDecimal(plantTreesPrm * kwh));
    	// 相当于减排二氧化碳
    	Double CO2Prm = Double.valueOf(systemConfigService.get("CO2_prm"));
    	objectMap.put("CO2Prm", NumberUtil.accurateToTwoDecimal(CO2Prm * kwh/1000));
    	// 相当于减排二氧化硫
    	Double SOPrm = Double.valueOf(systemConfigService.get("SO_prm"));
    	objectMap.put("SOPrm", NumberUtil.accurateToTwoDecimal(SOPrm * kwh/1000));
    	
    	return objectMap;
    }

	public List<Ammeter> findByStationId(Station station) {
		if(station!=null){
			return ammeterDao.findByStationId(station.getId());
		}
		return null;
	}
}

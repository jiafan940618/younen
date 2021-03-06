
package com.yn.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.yn.dao.AmmeterDao;
import com.yn.dao.ElecDataDayDao;
import com.yn.dao.FeedbackDao;
import com.yn.dao.NoticeDao;
import com.yn.dao.OrderDao;
import com.yn.dao.ServerDao;
import com.yn.dao.StationDao;
import com.yn.dao.UserDao;
import com.yn.enums.AmmeterTypeEnum;
import com.yn.enums.DeleteEnum;
import com.yn.enums.NoticeEnum;
import com.yn.model.ElecDataHour;
import com.yn.model.Order;
import com.yn.model.Station;
import com.yn.model.User;
import com.yn.service.AmmeterService;
import com.yn.service.ElecDataHourService;
import com.yn.service.SystemConfigService;
import com.yn.session.SessionCache;
import com.yn.utils.NumberUtil;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/server/homePage")
public class HomePageController {


    @Autowired
    StationDao stationDao;
    @Autowired
    ElecDataHourService elecDataHourService;
    @Autowired
    ElecDataDayDao elecDataDayDao;
    @Autowired
    FeedbackDao feedbackDao;
    @Autowired
    UserDao userDao;
    @Autowired
    ServerDao serverDao;
    @Autowired
    OrderDao orderDao;
    @Autowired
    AmmeterDao ammeterDao;
    @Autowired
    private NoticeDao noticeDao;
    @Autowired
    AmmeterService ammeterService;
    @Autowired
    SystemConfigService systemConfigService;
    


    /**
     * 发电功率和发电总量
     *
     * @return
     */
    @RequestMapping(value = "/tolNowKwAndTolKwh", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Object tolNowKwAndTolKwh(Long userId) {
        Double sumNowKw = 0D;
        Double sumKwh = 0D;
        if (userDao.findOne(userId).getRoleId()!=Long.parseLong(systemConfigService.get("server_role_id"))) { 	
        	sumNowKw=ammeterDao.sumNowKw();
        	sumKwh=ammeterDao.sumInitKwh()+ammeterDao.sumWorkTotalKwh();
        } else {
        	Long serverId=serverDao.findByUserid(userId);
        	List<Long> stationIds=stationDao.findId(serverId);
        	if (!stationIds.isEmpty()) {
				sumNowKw=ammeterDao.sumNowKwByStationIds(stationIds);
	            sumKwh =ammeterDao.sumInitKwhByStationIds(stationIds)+ammeterDao.sumWorkTotalKwhByStationIds(stationIds);
			}        	
        }

        Map<String, Object> map = new HashMap<>();
        map.put("tolNowKw",NumberUtil.accurateToTwoDecimal(sumNowKw==null?0D:sumNowKw));
        map.put("tolKwh", NumberUtil.accurateToTwoDecimal(sumKwh==null?0D:sumKwh));

        return ResultVOUtil.success(map);
    }

    /**
     * 最新动态
     *
     * @return
     */
//    @RequestMapping(value = "/latestNews", method = {RequestMethod.POST})
//    @ResponseBody
//    public Object latestNews(Long serverId) {
//
//        Date[] todaySpace = DateUtil.getTodaySpace();
//        Date startDtm = todaySpace[0];
//        Date endDtm = todaySpace[1];
//
//        long unreadMessageNum = 0;
//        long stationNum = 0;
//        long userNum = 0;
//        long serverNum = 0;
//        long orderNum = 0;
//        long ammeterNum = 0;
//
//        if (serverId == null) {
//            unreadMessageNum = feedbackDao.countUnconfirmed();
//            stationNum = stationDao.countNum(startDtm, endDtm);
//            userNum = userDao.countNum(startDtm, endDtm);
//            serverNum = serverDao.countNum(startDtm, endDtm);
//            orderNum = orderDao.countNum(startDtm, endDtm);
//            ammeterNum = ammeterDao.countNum(startDtm, endDtm);
//        } else {
//            stationNum = stationDao.countNum(startDtm, endDtm, serverId);
//            orderNum = orderDao.countNum(startDtm, endDtm, serverId);
//        }
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("unreadMessageNum", unreadMessageNum);
//        map.put("stationNum", stationNum);
//        map.put("userNum", userNum);
//        map.put("serverNum", serverNum);
//        map.put("orderNum", orderNum);
//        map.put("ammeterNum", ammeterNum);
//
//        return ResultVOUtil.success(map);
//    }


    /**
     * 最新动态
     *
     * @return
     */
    @RequestMapping(value = "/latestNews", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Object latestNews() {


        Long userId = SessionCache.instance().checkUserIsLogin();


        long userNum = noticeDao.countByUserIdAndTypeAndIsReadAndDel(userId, NoticeEnum.NEW_USER.getCode(), NoticeEnum.UN_READ.getCode(), DeleteEnum.NOT_DEL.getCode());
        long serverNum = noticeDao.countByUserIdAndTypeAndIsReadAndDel(userId, NoticeEnum.NEW_SERVER.getCode(), NoticeEnum.UN_READ.getCode(), DeleteEnum.NOT_DEL.getCode());
        long orderNum = noticeDao.countByUserIdAndTypeAndIsReadAndDel(userId, NoticeEnum.NEW_ORDER.getCode(), NoticeEnum.UN_READ.getCode(), DeleteEnum.NOT_DEL.getCode());
        long stationNum = noticeDao.countByUserIdAndTypeAndIsReadAndDel(userId, NoticeEnum.NEW_STATION.getCode(), NoticeEnum.UN_READ.getCode(), DeleteEnum.NOT_DEL.getCode());
        long ammeterNum = noticeDao.countByUserIdAndTypeAndIsReadAndDel(userId, NoticeEnum.NEW_AMMETER.getCode(), NoticeEnum.UN_READ.getCode(), DeleteEnum.NOT_DEL.getCode());
        long unreadMessageNum = noticeDao.countByUserIdAndTypeAndIsReadAndDel(userId, NoticeEnum.NEW_FEEDBACK.getCode(), NoticeEnum.UN_READ.getCode(), DeleteEnum.NOT_DEL.getCode());


        Map<String, Object> map = new HashMap<>();
        map.put("userNum", userNum);
        map.put("serverNum", serverNum);
        map.put("orderNum", orderNum);
        map.put("stationNum", stationNum);
        map.put("ammeterNum", ammeterNum);
        map.put("unreadMessageNum", unreadMessageNum);


        return ResultVOUtil.success(map);
    }


    /**
     * 业务数据
     *
     * @return
     */
    @RequestMapping(value = "/businessData", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Object businessData(Long userId) {
        Station station = new Station();
        station.setDel(0);
        Order order = new Order();
        order.setDel(0);
        User user = new User();
        user.setDel(0);

        long stationNum = 0;
        long orderNum = 0;
        double stationCapacity = 0;
        double orderCapacity = 0;
        long userNum = 0;
        if (userDao.findOne(userId).getRoleId()!=Long.parseLong(systemConfigService.get("server_role_id"))) {
            stationNum = stationDao.count(Example.of(station));
            orderNum = orderDao.count(Example.of(order));
            stationCapacity = stationDao.sumCapacity();
            orderCapacity = orderDao.sumCapacity();
            userNum = userDao.countNum();
        } else {
        	Long serverId=serverDao.findByUserid(userId);
            station.setServerId(serverId);
            stationNum = stationDao.count(Example.of(station));
            order.setServerId(serverId);
            orderNum = orderDao.count(Example.of(order));
            stationCapacity = stationDao.sumCapacity(serverId);
            orderCapacity = orderDao.sumCapacity(serverId);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("stationNum", stationNum);
        map.put("orderNum", orderNum);
        map.put("stationCapacity", stationCapacity);
        map.put("orderCapacity", orderCapacity);
        map.put("userNum", userNum);

        return ResultVOUtil.success(map);
    }

    /**
     * 今日发电量
     *
     * @return
     */
    @RequestMapping(value = "/todayKwh", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Object todayKwh(Long userId) {
    	List<Map<String, Object>> todayKwh = elecDataHourService.getTodayKwh(userId, AmmeterTypeEnum.GENERATED_ELECTRICITY.getCode());
        return ResultVOUtil.success(todayKwh);
    }

    /**
     * 首页的全网植树和碳排量
     *
     * @return
     */
    @RequestMapping(value = "/energyConservation", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Object energyConservation(Long userId) {
        Map<String, Object> energyConservation = ammeterService.energyConservation(userId);
        return ResultVOUtil.success(energyConservation);
    }
}


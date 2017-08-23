package com.yn.web;

import com.yn.dao.*;
import com.yn.domain.EachHourTemStation;
import com.yn.model.Order;
import com.yn.model.Station;
import com.yn.model.User;
import com.yn.service.TemStationService;
import com.yn.utils.DateUtil;
import com.yn.vo.re.ResultDataVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/server/homePage")
public class HomePageController {


    @Autowired
    StationDao stationDao;
    @Autowired
    TemStationService temStationService;
    @Autowired
    TemStationDao temStationDao;
    @Autowired
    TemStationYearDao temStationYearDao;
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


    /**
     * 发电功率和发电总量
     *
     * @return
     */
    @RequestMapping(value = "/tolNowKwAndTolKwh", method = {RequestMethod.POST})
    @ResponseBody
    public Object tolNowKwAndTolKwh(Long serverId) {

        double sumNowKw = 0;
        double sumKwh = 0;
        if (serverId == null) {
            sumNowKw = stationDao.sumNowKw();
            sumKwh = temStationDao.sumKwh(1);
        } else {
            sumNowKw = stationDao.sumNowKw(serverId);
            sumKwh = temStationDao.sumKwh(1, serverId);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("tolNowKw", sumNowKw);
        map.put("tolKwh", sumKwh);

        return ResultDataVoUtil.success(map);
    }

    /**
     * 最新动态
     *
     * @return
     */
    @RequestMapping(value = "/latestNews", method = {RequestMethod.POST})
    @ResponseBody
    public Object latestNews(Long serverId) {

        Date[] todaySpace = DateUtil.getTodaySpace();
        Date startDtm = todaySpace[0];
        Date endDtm = todaySpace[1];

        long unreadMessageNum = 0;
        long stationNum = 0;
        long userNum = 0;
        long serverNum = 0;
        long orderNum = 0;
        long ammeterNum = 0;

        if (serverId == null) {
            unreadMessageNum = feedbackDao.countUnconfirmed();
            stationNum = stationDao.countNum(startDtm, endDtm);
            userNum = userDao.countNum(startDtm, endDtm);
            serverNum = serverDao.countNum(startDtm, endDtm);
            orderNum = orderDao.countNum(startDtm, endDtm);
            ammeterNum = ammeterDao.countNum(startDtm, endDtm);
        } else {
            stationNum = stationDao.countNum(startDtm, endDtm, serverId);
            orderNum = orderDao.countNum(startDtm, endDtm, serverId);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("unreadMessageNum", unreadMessageNum);
        map.put("stationNum", stationNum);
        map.put("userNum", userNum);
        map.put("serverNum", serverNum);
        map.put("orderNum", orderNum);
        map.put("ammeterNum", ammeterNum);

        return ResultDataVoUtil.success(map);
    }

    /**
     * 业务数据
     *
     * @return
     */
    @RequestMapping(value = "/businessData", method = {RequestMethod.POST})
    @ResponseBody
    public Object businessData(Long serverId) {
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
        if (serverId == null) {
            stationNum = stationDao.count(Example.of(station));
            orderNum = orderDao.count(Example.of(order));
            stationCapacity = stationDao.sumCapacity();
            orderCapacity = orderDao.sumCapacity();
            userNum = userDao.countNum();
        } else {
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

        return ResultDataVoUtil.success(map);
    }

    /**
     * 今日发电量
     *
     * @return
     */
    @RequestMapping(value = "/todayKwh", method = {RequestMethod.POST})
    @ResponseBody
    public Object todayKwh(Long serverId) {
        List<EachHourTemStation> todayKwh = temStationService.getTodayKwh(serverId);
        return ResultDataVoUtil.success(todayKwh);
    }

}

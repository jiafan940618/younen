package com.yn.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yn.exception.MyException;
import com.yn.model.User;
import com.yn.service.UserService;
import com.yn.session.SessionCache;
import com.yn.utils.Constant;
import com.yn.utils.GetIP;
import com.yn.utils.StringUtil;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;

    /**
     * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
     * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {




        String ip = GetIP.getRemoteAddress(request);
        String requestURL = request.getRequestURL().toString();
        String url = request.getQueryString() == null ? requestURL + "" : (requestURL + "?" + request.getQueryString());
        logger.info(url);
        logger.info(ip);
       
        if (fromUserLogin(url)) {
            return true;
        }

        String token = request.getHeader("token");
        
        logger.info("传过来的token为: ---- ---->"+token);
        
        User user = SessionCache.instance().getUser(); 
        if (user == null) {
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            UserService userService = (UserService) factory.getBean("userService");
           
            logger.info("验证的token为: ---- ---->"+StringUtil.isEmpty(token));
            if (!StringUtil.isEmpty(token)) {
            	
                User userE = new User();
                userE.setToken(token);
                user = userService.findOne(userE);
                if (user != null) {
                    SessionCache.instance().setUser(user);
                   
                    return true;
                } else {
                    throw new MyException(5003, Constant.NO_LOGIN);
                }
            }else{
            	//return true;
            	throw new MyException(5003, Constant.NO_LOGIN);
            }

            }

        return true;
    }

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作 可在modelAndView中加入数据，比如当前时间
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     * <p>
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    /**
     * 不拦截UserLoginController
     *
     * @param url
     * @return
     */
    public boolean fromUserLogin(String url) {
        if (url.indexOf("/client/userLogin") > -1 || url.indexOf("/client/station/runningStation")>-1
        		|| url.indexOf("/client/station/runStations")>-1 || url.indexOf("/client/subsidy/monishouyi")>-1
        		|| url.indexOf("/client/server")>-1 || url.indexOf("/client/news")>-1 || url.indexOf("/client/construction")>-1 
        		|| url.indexOf("/client/station/numCapacity")>-1 || url.indexOf("/client/station/stationFenbu")>-1
        		|| url.indexOf("/client/temStationYear/monthKwh")>-1 || url.indexOf("/client/banner")>-1 ||
        		url.indexOf("/client/station/saveType")>-1 || url.indexOf("/client/weather/getWeather")>-1
        		|| url.indexOf("/client/question")>-1|| url.indexOf("/client/download")>-1|| url.indexOf("/client/station/majorKey")>-1
        		|| url.indexOf("/client/sign/doresult")>-1 || url.indexOf("/client/sign/doSucRep")>-1
        		|| url.indexOf("/client/appConfig/isSystemMaintain") > -1 || url.indexOf("/systemConfig/updateAndroid") > -1
        		|| url.indexOf("/client/visitor") > -1 || url.indexOf("/client/user/share") > -1  ) {
        	    

            return true;
        }
        return false;
    }
}

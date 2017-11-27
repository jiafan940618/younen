package com.yn.interceptor;

import com.yn.enums.ResultEnum;
import com.yn.exception.MyException;
import com.yn.model.User;
import com.yn.service.UserService;
import com.yn.session.SessionCache;
import com.yn.utils.GetIP;
import com.yn.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    protected Logger logger = LoggerFactory.getLogger(getClass());

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

        Long userId = SessionCache.instance().getUserId();
        if (userId == null) {
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            UserService userService = (UserService) factory.getBean("userService");
            String token = request.getHeader("token");
            if (!StringUtil.isEmpty(token)) {
                User userR = new User();
                userR.setToken(token);
                User user = userService.findOne(userR);
                if (user != null) {
                    SessionCache.instance().setUserId(user.getId());
                    return true;
                }
            } else if (ip.equals("0:0:0:0:0:0:0:1")) {
                User user = userService.findOne(1L);
                SessionCache.instance().setUserId(user.getId());
                return true;
            }

            throw new MyException(ResultEnum.NO_LOGIN);
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
        if ((url.indexOf("/userLogin/") > -1) || (url.indexOf("/upload/") > -1)|| (url.indexOf("/temStation/") > -1)) {
            return true;
        }
        return false;
    }
}

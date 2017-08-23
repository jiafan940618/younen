package com.yn.session;

import com.yn.enums.ResultEnum;
import com.yn.exception.MyException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionCache {

    Long userId;

    String code;

    String phone;

    String code4register;

    String code4findpaw;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCode4findpaw() {
        return code4findpaw;
    }

    public void setCode4findpaw(String code4findpaw) {
        this.code4findpaw = code4findpaw;
    }

    public String getCode4register() {
        return code4register;
    }

    public void setCode4register(String code4register) {
        this.code4register = code4register;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private SessionCache() {

    }

    public static SessionCache instance() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute("SessionCache");
        if (attribute == null) {
            attribute = new SessionCache();
            session.setAttribute("SessionCache", attribute);
        }
        return (SessionCache) attribute;
    }

    /**
     * 清空缓存
     */
    public static void clean() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("SessionCache", null);
    }

    /**
     * 检查用户是否登陆
     *
     * @return
     */
    public Long checkUserIsLogin() {
        Long sessionUserId = SessionCache.instance().getUserId();
        if (sessionUserId == null) {
            throw new MyException(ResultEnum.NO_LOGIN);
        }
        return sessionUserId;
    }
}

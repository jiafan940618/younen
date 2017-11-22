package com.yn.session;

import com.yn.exception.MyException;
import com.yn.model.User;
import com.yn.utils.Constant;
import com.yn.vo.NewUserVo;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionCache {

    User user;

    String code;

    String phone;

    String code4register;

    String code4findpaw;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
            /** 设置失效时间*/
            session.setMaxInactiveInterval(1*60);
        }
        return (SessionCache) attribute;
    }

    /**
     * 检查用户是否登陆
     *
     * @return
     */
    public User checkUserIsLogin() {
        User sessionUser = SessionCache.instance().getUser();
        if (sessionUser == null) {
            throw new MyException(444, Constant.NO_LOGIN);
        }
        return sessionUser;
    }
}

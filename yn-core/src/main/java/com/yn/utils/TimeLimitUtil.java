package com.yn.utils;

import javax.servlet.http.HttpSession;

public class TimeLimitUtil {

    /**
     * 限制同一手机号码1分钟内只允许发送一条验证码
     */
    public static ResultData<Object> timeLimit(String phone, HttpSession httpSession,
                                               ResultData<Object> resultData) {
        Long time = System.currentTimeMillis();
        String sessionPhone = (String) httpSession.getAttribute("phone");
        Long sessionTime = (Long) httpSession.getAttribute("time");
        if ((sessionPhone != null && sessionTime != null) && (sessionPhone.equals(phone) && (time - sessionTime < 60000))) {
            resultData.setCode(403);
            resultData.setSuccess(false);
            resultData.setMsg("请在" + (60 - (time - sessionTime) / 1000) + "秒后重新发送验证码");
            return resultData;
        }
        httpSession.setAttribute("time", time);
        httpSession.setAttribute("phone", phone);
        return resultData;
    }
}

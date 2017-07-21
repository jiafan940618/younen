package com.yn.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by TT on 2017/1/16.
 */
public class GetIP {
    public static String getRemoteAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}

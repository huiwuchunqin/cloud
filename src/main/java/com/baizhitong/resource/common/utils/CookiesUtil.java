package com.baizhitong.resource.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookiesUtil {
    /**
     * 存入cookie ()<br>
     * 
     * @param name 名称
     * @param value 值
     * @param response
     */
    public static void write(String name, String value, HttpServletResponse response) {
        response.addHeader("Set-Cookie", name + "=" + value + ";Path=/; HttpOnly");
    }

    /**
     * 拿cookie值
     * 
     * @param key
     * @param req
     * @return
     */
    public static String loadCookie(String key, HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals(key)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}

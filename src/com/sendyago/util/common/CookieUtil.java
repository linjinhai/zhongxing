/* ==============================================================
 * $ID: LoginController.java, v1.0 2016/5/17 14:02:12 Rick Exp $
 * created: [2016-05-17 14:02:12] by Rick
 * ==============================================================
 * 健康监测系统模块化公共信息
 *
 * 健康监测系统模块化即将健康监测系统各功能模块进行项目化形式进行开发
 * 将各功能模块划分为各个WEB项目
 * ==============================================================
 * Copyright (c) 哈尔滨工大云帆智慧信息技术有限公司 All rights reserved
 * ==============================================================
 */
package com.sendyago.util.common;

import com.sendyago.util.context.CONFIG;

import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 用户信息缓存到客户端类
 * 用于将用户信息保存到客户端中,方便下次用户登录
 *
 * @author $Author: Rick$
 * @version $Revision: 1.0 $Date: 2016/5/17 14:02:12 $Description 类创建 &
 */
public class CookieUtil {

    //设置cookie有效期是四个星期，根据需要自定义
    private final static long cookieMaxAge = 60 * 60 * 24 * 7 * 4;

    /**
     * 保存用户信息到客户端,可方便用户下次登录
     * @param map   保存的用户信息
     * @param response   用于保存cookie
     */
    public static void saveCookie(Map map,HttpServletResponse response) {
        // cookie的有效期
        long currentTimeMillis = System.currentTimeMillis();
        long validTime = currentTimeMillis + (cookieMaxAge * 5000);
        // 获取用户名,密码
        String user_name = (String) map.get("USER_ID");
        String user_pass = (String) map.get("USER_PASS");
        // 创建cookie, key为占位符
        Cookie cookie = new Cookie(CONFIG.COOKIE_USER, user_name + ":" + user_pass + ":" + validTime + ":" + "key" + ":" + "key");
        // 设置cookie的存储时间,保存2个月
        cookie.setMaxAge((int) (cookieMaxAge * 2));
        // cookie有效路径是网站根目录
        cookie.setPath("/");
        // 将cookie写入客户端
        response.addCookie(cookie);
    }

    /**
     * 读取cookie中内容
     * @param cookies 缓存内容
     * @return
     */
    public static String getCookie(Cookie[] cookies) {
        String user_info = null;
        // 判断cookies是否为null
        if (cookies != null) {
            // 循环cookies,取出保存的用户信息
            for (Cookie cookie : cookies) {
                if(CONFIG.COOKIE_USER.equals(cookie.getName())) {
                    user_info = cookie.getValue();
                    break;
                }
            }
            // 没有保存用户的cookie信息
            if(user_info == null) {
                return "";
            }
            // 判断取出的内容长度是否为固定长度
            String[] infos = user_info.split(":");
            if(infos.length != 5) {
                return "您正在用非正常方式进入系统...";
            }
            // 获取有效时期,判断是否超过缓存时间
            long validMaxAge = new Long(infos[2]);
            long currentTimeMillis = System.currentTimeMillis();
            if(validMaxAge < currentTimeMillis){
                return "保存的用户信息已失效,请重新输入用户名密码进行登录!";
            }
        } else {
            // 没有保存cookie信息
            return "";
        }
        return user_info;
    }

    /**
     * 清理浏览器cookie中的缓存
     * @param servletResponse
     */
    public static void clearCookie(ServletResponse servletResponse) {
        Cookie cookie = new Cookie(CONFIG.COOKIE_USER, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.addCookie(cookie);
    }
}

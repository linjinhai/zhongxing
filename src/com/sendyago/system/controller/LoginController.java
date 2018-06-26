/* ==============================================================
 * $ID: LoginController.java, v1.3.1 2016/6/3 17:24:12 Rick Exp $
 * created: [2016-04-25 13:20:12] by Rick
 * ==============================================================
 * 健康监测系统模块化公共信息
 *
 * 健康监测系统模块化即将健康监测系统各功能模块进行项目化形式进行开发
 * 将各功能模块划分为各个WEB项目
 * ==============================================================
 * Copyright (c) 哈尔滨工大云帆智慧信息技术有限公司 All rights reserved
 * ==============================================================
 */
package com.sendyago.system.controller;

import com.sendyago.main.service.MainService;
import com.sendyago.system.service.LocalIpService;
import com.sendyago.system.service.LogService;
import com.sendyago.system.service.LoginService;
import com.sendyago.util.common.CookieUtil;
import com.sendyago.util.common.LogWSClinet;
import com.sendyago.util.common.MD5Util;
import com.sendyago.util.context.CONFIG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 系统登录控制类
 * 需校验登录信息的正确性,即用户名是否存在是否正确,密码是否正确等
 *
 * @author $Author: Rick$
 * @version $Revision: 1.0 $Date: 2016/4/25 13:20:12 $Description 类创建 &
 * @version $Revision: 1.1 $Date: 2016/4/28 13:10:12 $
 * @version $Revision: 1.2 $Date: 2016/5/6 16:40:12 $Description 创建logout()方法 &
 * @version $Revision: 1.3 $Date: 2016/5/17 17:12:12 $Description 创建 logon()方法 &
 * @version $Revision: 1.3.1 $Date: 2016/6/3 17:24:12 $Description 登录保存日志 &
 */
@Controller
@RequestMapping("/controller")
@Scope("prototype")
public class LoginController {

    @Autowired
    @Qualifier("loginServiceImpl")
    private LoginService service;

    @Autowired
    @Qualifier("mainServiceImpl")
    private MainService mainService;

    @Autowired
    @Qualifier("logService")
    private LogService logService;

    
    @Autowired
    @Qualifier("localipServiceImpl")
    private LocalIpService localipService;
    /**
     * 进入登录页面前获取用户cookie缓存
     * @param req
     * @param rep
     * @param params
     * @return
     */
    @RequestMapping(value = "logon")
    public String logon(HttpServletRequest req, HttpServletResponse rep,
                                @RequestParam LinkedHashMap<String, Object> params) {
        getUserCookie(req, rep);
        return "login/login";
    }

    /**
     * 功能: 跳转到浏览器下载页面
     * @param req
     * @param rep
     * @param params
     * @return
     */
    @RequestMapping(value = "toDownload")
    public String toDownload(HttpServletRequest req, HttpServletResponse rep,
                             @RequestParam LinkedHashMap<String, Object> params) {
        return "login/browseDownload";
    }

    /**
     * 用户登录
     * @param modelMap 用于传参的集合
     * @param req 前台请求的信息
     * @param params 封装的对象信息,包括用户信息
     */
    @RequestMapping(value = "main")
    public String login(ModelMap modelMap,HttpServletRequest req, HttpServletResponse rep,
                        @RequestParam LinkedHashMap<String, Object> params){
        String returnStr = "login/login";
        try {
            // 查询用户信息
            Map _user = (Map) service.queryForObject(params);
            String user_id = (String) params.get("USER_ID");
            String password = (String) params.get("USER_PASS");
            String cookiePass = password;
            password = MD5Util.MD5(password);
            /*
             * 判断登录的用户信息是否正常
             * 1.判断是否NULL
             * 2.判断密码是否正常
             * 3.判断当前用户状态是否可用
             * 4.判断用户的是否有登录期限
             * 全部没问题则进入系统首页
             */
            if(null == user_id) {
                params.put(CONFIG.ERRORS,"");
                modelMap.put(CONFIG.UN_LOGINED, params);
                returnStr = "login/logon";
            }else if(_user == null){
                params.put(CONFIG.ERRORS,CONFIG.USER_LOGIN_ERROR);
                modelMap.put(CONFIG.UN_LOGINED, params);
            }else if(!password.equals(_user.get("USER_PASS"))){
                params.put(CONFIG.ERRORS,CONFIG.USER_LOGIN_ERROR);
                modelMap.put(CONFIG.UN_LOGINED, params);
            }else if(CONFIG.UN_AVAILABLE.equals(_user.get("IS_AVAILABLE").toString())) {
                params.put(CONFIG.ERRORS, CONFIG.USER_STATUS_ERROR);
                modelMap.put(CONFIG.UN_LOGINED, params);
            }else if(null != _user.get("AVAILABLE_DATE")){

            }else{
                req.getSession().setAttribute(CONFIG.LOGINED,_user);
                // 查询用户角色菜单
                List list = service.queryMenuByUserRole(user_id);
                req.setAttribute("menus", list);
                // 判断用户是否勾选了"记住用户名密码"选项,为null时代表未勾选
                if(params.get("IS_CHECK") != null) {
                    // 将用户信息保存到cookie中
                    _user.put("USER_PASS",cookiePass);
                    CookieUtil.saveCookie(_user, rep);
                }
                // 查询最新报警数量
                int count = getNewWarnCount();
                req.setAttribute("warn_count", count);

                // 保存登录日志
                LinkedHashMap<String, Object> logMap = new LinkedHashMap<String,Object>();
                logMap.put("str1", UUID.randomUUID());
                logMap.put("str2", user_id);
                logMap.put("str3", 0);
                logMap.put("str4", 10);
                String ip=localipService.getLocalIp(req);
                logMap.put("ip", ip);
                logService.logInsert(logMap);

                returnStr = "main/home";
            }
        } catch (Exception e) {
            e.printStackTrace();
            params.put(CONFIG.ERRORS, "DB Connection Error!");
        }
        return returnStr;
    } // end login()

    /**
     * 系统退出功能
     * 系统退出需要删除用户的缓存信息,并跳转到登录页面
     */
    @RequestMapping(value = "logout")
    public String logout(HttpServletRequest req, HttpServletResponse rep, @RequestParam LinkedHashMap<String, Object> params) {
        // 获取当前用户信息
        Map user = (Map) req.getSession().getAttribute(CONFIG.LOGINED);
        // 处理用户缓存等
        req.getSession().setAttribute(CONFIG.LOGINED, CONFIG.NULL);

        getUserCookie(req, rep);
        // todo 处理用户在数据库中的登录状态
        return "login/login";
    }

    // 获取用户cookie信息
    private HttpServletRequest getUserCookie(HttpServletRequest req, HttpServletResponse rep) {
        // 获取cookie中的内容
        Cookie cookies[] = req.getCookies();
        String infos = CookieUtil.getCookie(cookies);
        String[] user_info = infos.split(":");
        if( user_info.length > 1) {
            Map map = new HashMap();
            map.put("USER_ID", user_info[0]);
            map.put("USER_PASS", user_info[1]);
            req.setAttribute(CONFIG.UN_LOGINED, map);
        } else {
            CookieUtil.clearCookie(rep);
            req.setAttribute(CONFIG.ERRORS, infos);
        }
        return req;
    }

    // 查询最新报警数量
    private int getNewWarnCount() {
        int count = 0;
        LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("page", "");
        params.put("limit", "");
        params.put("IS_COUNT", 1);
        try {
            List list = mainService.queryWarnList(params);
            if(list.size() > 0) {
                Map map = (Map)list.get(0);
                count = Integer.valueOf(map.get("NUM").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }


} // end class

/* ==============================================================
 * $ID: SystemModel.java, v1.3 2016/4/25 13:29:14 Rick Exp $
 * created: [2016-04-25 13:29:14] by Rick
 * ==============================================================
 * 健康监测系统模块化公共信息
 *
 * 健康监测系统模块化即将健康监测系统各功能模块进行项目化形式进行开发
 * 将各功能模块划分为各个WEB项目
 * ==============================================================
 * Copyright (c) 哈尔滨工大云帆智慧信息技术有限公司 All rights reserved
 * ==============================================================
 */
package com.sendyago.util.context;

/**
 * 公共常量声明类
 *
 * @author $Author: Rick$
 * @version $Revision: 1.3 $Date: 2016/4/27 09:21:04 $
 */
public class CONFIG {

    // 用户信息
    public static final String UN_AVAILABLE = "0";
    public static final String LOGINED = "logined"; // 已登录的用户
    public static final String UN_LOGINED = "un_info"; // 已登录的用户
    public static final String NULL = null; // 已登录的用户

    // 用于保存在cookie中的用户信息标识
    public static final String COOKIE_USER = "USER_INFO";

    public static final String USER_LOGIN_ERROR = "用户名或密码错误，请重新输入！";

    public static final String USER_STATUS_ERROR = "当前用户已失效，请联系系统管理员！";

    /* AJAX return str */
    public static final String SUCCESS = "success";
    public static final String SUCCESS_TRUE = "{success:true}";
    public static final String FAILURE = "failure";
    public static final String ERRORS = "error";

    public static final String ENCODE = "UTF-8";
    public static final String JSON_ENCODE = "text/json;charset=utf-8";

    // 过滤器信息
    public final static String[] LOGIN_FILTER_STRS = {"/jsp", "/img", "/css", "/js", "/controller"};

    // SPRING拦截器传值MAP
    public final static String VALUE_MAP = "VALUE_MAP";

    // 传感器id
    public final static int[][] SENSOR_IDS ={
            {1,2,3,4},  // 温度
            {5,6,7,8,9,10}, // 水准仪
            {11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34}, // 应变
            {35,36,37,38,39,40,41,42}   // 索力
    };

    // 调用存储过程返回类型
    public final static int CURSOR = 1;
    public final static int NUMBER = 2;
    public final static int VARCHAR = 3;

    public final static int SYSTEM = 1;
    public final static int MONITOR = 2;
    public final static int QUERY = 3;
    public final static int PINGHU = 4;

    // 当存储过程返回number或varchar时,使用该标志常量进行封装
    public static final String RESULT = "result";

}

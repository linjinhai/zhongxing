/* ==============================================================
 * $ID: ServiceImpl.java, v1.1 2016/5/25 09:32:00 Rick Exp $
 * created: [2016-04-28 10:25:00] by Rick
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

import net.sf.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 工具类
 *
 * @author $Author: Rick$
 * @version $Revision: 1.0 $Date: 2016/4/28 10:25:00 $
 * @version $Revision: 1.1 $Date: 2016/5/25 09:32:00 加入日期转换的一些方法 $
 */
public class Utils {

    private static String DateFormat1 = "yyyy-MM-dd";
    private static String DateFormat2 = "yyyy-MM-dd HH";
    private static String DateFormat3 = "yyyy-MM-dd HH:mm";
    private static String DateFormat4 = "yyyy-MM-dd HH:mm:ss";

    /**
     * Convert http request params, such as "name=tom&age=20"
     * generally speaking,request body should be decode after convert complete.
     *
     * @param params request params
     * @return map
     */
    public static LinkedHashMap<String, String> paramsToMap(String params) {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        if (params != null && !params.equals("")) {
            String[] array = params.split("&");
            for (String pair : array) {
                if ("=".equals(pair.trim())) {
                    continue;
                }
                String[] entity = pair.split("=");
                if (entity.length == 1) {
                    map.put(decode(entity[0]), null);
                } else {
                    map.put(decode(entity[0]), decode(entity[1]));
                }
            }
        }
        return map;
    }

    public static String decode(String content) {
        try {
            return URLDecoder.decode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Generate a random number
     *
     * @return uui string
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Parse date string to Date object
     * @param dateValue     date string
     * @param strFormat     yyyy-MM-dd , yyyy-MM-dd hh:mm:ss
     * @return              Date Object or Null
     */
    public static Date parseDate(String dateValue,String strFormat) throws ParseException {
        if(dateValue==null) return null;
        if(strFormat==null) strFormat = DateFormat1;
        SimpleDateFormat format = new SimpleDateFormat(strFormat);
        return format.parse(dateValue);
    }


    /**
     * 获取系统时间,yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getSystemDate() {
        SimpleDateFormat sf = new SimpleDateFormat(DateFormat4);
        long date = System.currentTimeMillis();
        return sf.format(date);
    }

    /**
     * 获取系统时间，yyyy-MM-dd
     * @return
     */
    public static String getSystemDateDay() {
        SimpleDateFormat sf = new SimpleDateFormat(DateFormat1);
        long date = System.currentTimeMillis();
        return sf.format(date);
    }

    /**
     * 根据毫秒数获取时间格式, yyyy-mm-dd hh24:mi:ss
     * @param millis
     * @return
     */
    public static String getDateByMillis(long millis) {
        SimpleDateFormat sf = new SimpleDateFormat(DateFormat4);
        return sf.format(millis);
    }

    /**
     * 根据毫秒数获取时间格式, yyyy-mm-dd hh24:mi:ss
     * @param millis
     * @return
     */
    public static String getDateHourByMillis(long millis) {
        SimpleDateFormat sf = new SimpleDateFormat(DateFormat2);
        return sf.format(millis);
    }

    /**
     * 功能描述: 返回昨天的日期, yyyy-mm-dd
     * @return
     */
    public static String getYestoday() {
        String today = Utils.getSystemDateDay();
        long todayMillis = getMillisByFormat(today, DateFormat1);
        long oneDay = 24 * 60 * 60 * 1000;
        long yestodayMillis = todayMillis - oneDay;
        SimpleDateFormat sf = new SimpleDateFormat(DateFormat1);
        return sf.format(yestodayMillis);
    }

    /**
     * 功能描述：返回年
     *
     * @param date
     *            Date 日期
     * @return 返回年份
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 功能描述：返回月
     *
     * @param date
     *            Date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }


    /**
     * 功能描述：返回日
     *
     * @param date
     *            Date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 功能描述：返回小时
     *
     * @param date
     *            日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 功能描述：返回分
     *
     * @param date
     *            日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date
     *            Date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 功能描述：返回毫秒
     *
     * @param date
     *            日期
     * @return 返回毫秒
     */
    public static long getMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * 功能:将字符型日期转化为毫秒,日期格式:yyyy-mm-dd
     * @param date
     * @return
     */
    public static long getMillis(String date) {
        return getMillisByFormat(date, DateFormat2);
    }

    /**
     * 功能:将字符型日期转化为毫秒,日期格式:yyyy-mm-dd hh:mm:ss
     * @param date
     * @return
     */
    public static long getMillis2(String date) {
        return getMillisByFormat(date, DateFormat4);
    }


    /**
     * 功能:根据日期与格式,返回毫秒数
     * @param date
     * @param dateFormat
     * @return
     */
    public static long getMillisByFormat(String date, String dateFormat) {
        long millis = 0;
        try {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
            c.setTime(sf.parse(date));
            millis = c.getTimeInMillis();
            return millis;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return millis;
    }


    /**
     * 根据毫秒计算时间长度
     * @param result
     * @return
     */
    public static String getTimeByMills(long result){
        long sec = result/1000; // 总秒
        long min = sec/60; // 总分
        long hour = min/60; // 总时
        long new_ms = result%1000;
        long new_sec = sec%60; // 剩余秒
        long new_min = min%60; // 剩余分钟
        return hour+"小时"+new_min+"分钟"+new_sec+"秒"+new_ms+"毫秒";
    }

    /*
        =================================================================
        以下为张强项目代码
        合并日期: 2016-07-19
        =================================================================
     */

    public static String jsonListStr(List list) {

        JSONArray jsonObj = JSONArray.fromObject(list);

        return jsonObj.toString();

    }

    /**
     *
     * @Description: 传入参数返回pageBean
     * @param  page_size
     * @param  currentPage
     * @param  list
     * @param  map
     * @param  allsize
     * @return PageBean
     * @throws
     */

    public static PageBean getPageBean(int page_size, int currentPage, List list, Map map,
                                       int allsize) {
        PageBean pageBean = new PageBean();
        pageBean.setPageSize(page_size);
        pageBean.setCurrentPage(currentPage);
        pageBean.setAllRow(allsize);
        pageBean.setTotalPage(PageBean.countTotalPage(page_size, pageBean.getAllRow()));
        pageBean.setList(list);
        pageBean.setParamMap(map);
        pageBean.init();
        return pageBean;
    }


}

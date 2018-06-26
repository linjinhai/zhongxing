/* ==============================================================
 * $ID: MainServiceImpl.java, v1.4 2016/7/1 15:45:01 Rick Exp $
 * created: [2016-05-18 14:12:01] by Rick
 * ==============================================================
 * 健康监测系统模块化公共信息
 *
 * 健康监测系统模块化即将健康监测系统各功能模块进行项目化形式进行开发
 * 将各功能模块划分为各个WEB项目
 * ==============================================================
 * Copyright (c) 哈尔滨工大云帆智慧信息技术有限公司 All rights reserved
 * ==============================================================
 */
package com.sendyago.main.service.impl;

import com.sendyago.main.service.MainService;
import com.sendyago.util.common.Utils;
import com.sendyago.util.jdbc.OracleJDBC;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.*;

/**
 * 系统主页信息管理接口类
 * 用于实现系统主页数据查询的接口
 * 其中主要包括实时曲线数据查询,时均值曲线数据查询,报警信息数据查询等
 *
 * queryMonitorLines()用于实时曲线数据查询;
 * queryHourLines()用于时均值曲线数据查询;
 * queryWarnColumnS()用于报警柱状图数据查询;
 *
 * @author $Author: Rick$
 * @version $Revision: 1.0 $Date: 2016/5/18 14:07:01 类创建,实时曲线方法$
 * @version $Revision: 1.1 $Date: 2016/5/19 13:15:01 时均值方法$
 * @version $Revision: 1.2 $Date: 2016/5/19 15:40:01 querySensorInfoByCode() 公共私有方法$
 * @version $Revision: 1.3 $Date: 2016/5/23 13:26:01 实现报警查询方法 $
 * @version $Revision: 1.4 $Date: 2016/7/1 15:45:01 实现queryAllSensorUpdate()方法 $
 */
@Component
public class MainServiceImpl extends OracleJDBC implements MainService {

    /**
     * 查询所有传感器实时数据/状态等
     * @param params
     * @return
     * @throws Exception
     */
    public List queryAllSensorUpdate(LinkedHashMap<String, Object> params) throws Exception {
        List list = procedures4Query(params, "MAIN_GET_SENSOR_UPDATE");
        return list;
    }


    /**
     * 查询传感器实时曲线数据
     * 查询过去50条数据,并根据存储间隔再实时获取数据,以曲线展示
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public Map queryMonitorLines(LinkedHashMap<String, Object> params) throws Exception {
        //根据传感器编号获取传感器的信息
        Map map = querySensorInfoByCode(params);
        if(null != map) {
        /*
         * 查询传感器数据,设置开始,结束时间;
         * 查询50条数据,开始时间根据存储时间间隔进行设置;
         */
            long partSecond = Long.valueOf(map.get("PART_SECOND").toString()); // 存储时间间隔(秒)
            long partSecondMillis = partSecond * 1000; // 将存储时间间隔转换为毫秒
            long endMillis = System.currentTimeMillis();
            long startMillis = endMillis - partSecondMillis * 50; // 按照50条数据的时间进行向前推
            String end_time = Utils.getDateByMillis(endMillis);
            String start_time = Utils.getDateByMillis(startMillis);
            String sensor_id = map.get("SENSOR_ID").toString();

            // 将查询条件封装到params中
            params.remove("SENSOR_CODE");
            params.put("SENSOR_ID", sensor_id);
            params.put("START_TIME", start_time);
            params.put("END_TIME", end_time);

            // 查询数据,并将结果封装到map中返回到前台
            List dataList = procedures4Query(params, "MAIN_SENSOR_HISTORY_QUERY");
            map.put("list", dataList);
        }
        return map;
    }

    /**
     * 根据传感器id查询最新更新的数据
     * @param params
     * @return
     * @throws Exception
     */
    public Map queryMonitorPoint(LinkedHashMap<String, Object> params) throws Exception {
        List list = procedures4Query(params, "MAIN_SENSOR_POINT");
        Map map = null;
        if(list.size() > 0) {
            map = (Map)list.get(0);
        }
        return map;
    }

    /**
     * 每小时查询时均值数据
     * @param params
     * @return
     * @throws Exception
     */
    public Map queryHourPoint(LinkedHashMap<String, Object> params) throws Exception {
        // 当天时期  y-m-d
        Date d = new Date();
        int hours = Utils.getHour(d);
        // 今天日期
        String endTime = Utils.getSystemDateDay() + " " + hours;
        long endMillis = Utils.getMillis(endTime);
        // 减1小时
        long startMillis = endMillis - 60 * 60 * 1000;
        // 根据毫秒获取开始时间
        String startTime = Utils.getDateHourByMillis(startMillis);

        params.put("START_TIME", startTime);
        params.put("END_TIME", endMillis);

        // 数据查询
        List list = procedures4Query(params, "MAIN_SENSOR_AVG_QUERY");
        Map map = null;
        if(list.size() >0) {
            map = (Map)list.get(0);
        }

        // 将时间转为毫秒
        if(null != map) {
            String dataTime = (String) map.get("TIM");
            long newDataTime = Utils.getMillis(dataTime);
            map.put("TIM", newDataTime);
        }

        return map;
    }

    /**
     * 查询传感器的过去24小时时均值数据,并以曲线形式进行展示
     * 每小时刷新
     * @param params 查询条件
     * @return
     * @throws Exception
     */
    @Override
    public Map queryHourLines(LinkedHashMap<String, Object> params) throws Exception {
        //根据传感器编号获取传感器的信息
        Map map = querySensorInfoByCode(params);
        if(null != map) {
            // 当天时期  y-m-d
            Date d = new Date();
            int hours = Utils.getHour(d);
            // 今天日期
            String today = Utils.getSystemDateDay();
            // 获取昨日日期
            String yestoday = Utils.getYestoday();
            String end_time = today + " " + hours;
            String start_time = yestoday + " " + hours;
            // 传感器id
            String sensor_id = map.get("SENSOR_ID").toString();

            params.remove("SENSOR_CODE");
            params.put("SENSOR_ID", sensor_id);
            params.put("START_TIME", start_time);
            params.put("END_TIME", end_time);

            List dataList = procedures4Query(params, "MAIN_SENSOR_AVG_QUERY");
            List newList = dateToMillis(dataList, true);
            map.put("list", newList);
        }
        return map;
    }

    /**
     * 查询七日内报警次数
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public List queryWarnColumnS(LinkedHashMap<String, Object> params) throws Exception {

        List list = procedures4Query(params, "MAIN_WARNING_COUNT");

        return list;
    }


    /**
     * 功能: 查询最新报警列表
     * @param params
     * @return
     * @throws Exception
     */
    public List queryWarnList(LinkedHashMap<String, Object> params) throws Exception {

        List list = procedures4Query(params, "MAIN_WARNING_NEW_LIST");

        return list;
    }

    /**
     * 功能: 更新报警报状态值
     * @param params
     * @return
     * @throws Exception
     */
    public void updateWarnStatus(LinkedHashMap<String, Object> params) throws Exception {
        procedures4Change(params, "MAIN_WARNING_NEW_UPDATE");
    }

    /**
     * 功能: 展示报警曲线图
     * @param params
     * @return
     * @throws Exception
     */
    public Map queryWarnLineList(LinkedHashMap<String, Object> params) throws Exception {
        // 报警时间
        String warning_time = (String) params.get("warning_time");
        params.remove("warning_time");
        //根据传感器编号获取传感器的信息
        Map map = querySensorInfoByCode(params);
        params.remove("sensor_code");
        /*
         * 根据报警时间和传感器信息查询报警曲线的数据
         * 查询报警时间点前后10分钟的数据
         * 先将报警时间点转成毫秒,再进行计算获得前后时间点
         */
        long warn_millis = Utils.getMillis2(warning_time);
        // 十分钟的毫秒数, 如果查询时间有变,例如查询前后1小时,只需要将下面的10改成60即可.
        long tenMins = 60 * 10 * 1000;
        // 获取报警时间点前10分钟的时间日期
        String start_time = Utils.getDateByMillis(warn_millis - tenMins);
        // 获取报警时间点后10分钟的时间日期
        String end_time = Utils.getDateByMillis(warn_millis + tenMins);
        // 查询曲线数据
        params.put("in_start_time", start_time);
        params.put("in_end_time", end_time);
        params.put("sensor_id", map.get("SENSOR_ID"));
        List list = procedures4Query(params, "MAIN_WARNING_LINE");
        list = dateToMillis(list, false);
        map.put("list", list);
        return map;
    }

    /**
     * 根据传感器编号查询传感器所有信息
     * 包括id,名称,类型名称,单位等
     * @param params
     * @return
     */
    private Map querySensorInfoByCode(LinkedHashMap<String, Object> params) throws SQLException, ClassNotFoundException {
        /*
         * 根据传感器编号获取传感器的信息
         * 传感器的信息包括传感器的名称,类型名称,小数点位数,单位,存储时间间隔等
         */
        List list = procedures4Query(params, "MAIN_SENSOR_INFO");
        Map map = null;
        if(list.size() > 0) {
            map = (Map) list.get(0); // 获取第一条返回信息
        }
        return map;
    }

    /**
     * 将list中日期格式转为毫秒
     * @param list
     * @return list
     */
    private List dateToMillis(List list, boolean flag) {
        List newlist = new ArrayList();
        for(int i=0; i<list.size(); i++) {
            Map map = (Map)list.get(i);
            String date = (String)map.get("TIM");
            long millis = 0;
            if(flag) {
                millis = Utils.getMillis(date);
            }else {
                millis = Utils.getMillis2(date);
            }
            map.put("TIM", millis);
            newlist.add(map);
        }
        return newlist;
    }

    public void test() throws Exception {
        procedures4Change(null, "test");
    }


    public void deleteRandomData() throws  Exception {
        for(int i=1; i<=42; i++) {
            LinkedHashMap<String, Object> params = new LinkedHashMap<String,Object>();
            params.put("sensorId", i);
            procedures4Change(params, "MAIN_DELETE_RANDOM_DATA");
        }
    }

    public List getSensorList() throws Exception {
        LinkedHashMap<String, Object> params = new LinkedHashMap<String,Object>();
        params.put("search", "");
        params.put("page", 0);
        params.put("limit", 0);
        List list = procedures4Query(params, "SYSTEM_SENSOR_LIST");
        return list;
    }

    public void setRandomStatus(LinkedHashMap<String, Object> params) throws Exception {
        procedures4Change(params, "MAIN_SET_RANDOM_STATUS");
    }

}

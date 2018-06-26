/* ==============================================================
 * $ID: MainService.java, v1.0 2016/5/18 14:07:01 Rick Exp $
 * created: [2016-05-18 14:07:01] by Rick 
 * ==============================================================
 * 健康监测系统模块化公共信息
 *
 * 健康监测系统模块化即将健康监测系统各功能模块进行项目化形式进行开发
 * 将各功能模块划分为各个WEB项目
 * ==============================================================
 * Copyright (c) 哈尔滨工大云帆智慧信息技术有限公司 All rights reserved
 * ==============================================================
 */
package com.sendyago.main.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
 * @version $Revision: 1.0 $Date: 2016/5/18 14:07:01 $
 */
public interface MainService {

    public List queryAllSensorUpdate(LinkedHashMap<String, Object> params) throws Exception;
    public Map queryMonitorLines(LinkedHashMap<String, Object> params) throws Exception;
    public Map queryHourLines(LinkedHashMap<String, Object> params) throws Exception;
    public List queryWarnColumnS(LinkedHashMap<String, Object> params) throws Exception;
    public List queryWarnList(LinkedHashMap<String, Object> params) throws Exception;
    public void updateWarnStatus(LinkedHashMap<String, Object> params) throws Exception;
    public Map queryWarnLineList(LinkedHashMap<String, Object> params) throws Exception;
    public Map queryMonitorPoint(LinkedHashMap<String, Object> params) throws Exception;
    public Map queryHourPoint(LinkedHashMap<String, Object> params) throws Exception;
    public void test() throws Exception;
    public void deleteRandomData() throws  Exception;
    public List getSensorList() throws Exception;
    public void setRandomStatus(LinkedHashMap<String, Object> params) throws Exception;
}

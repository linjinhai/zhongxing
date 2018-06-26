/* ==============================================================
 * $ID: LoginService.java, v1.2 2016/4/28 14:50:01 Rick Exp $
 * created: [2016-04-25 13:31:101] by Rick
 * ==============================================================
 * 健康监测系统模块化公共信息
 *
 * 健康监测系统模块化即将健康监测系统各功能模块进行项目化形式进行开发
 * 将各功能模块划分为各个WEB项目
 * ==============================================================
 * Copyright (c) 哈尔滨工大云帆智慧信息技术有限公司 All rights reserved
 * ==============================================================
 */
package com.sendyago.system.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * >>>注意: 将属性封装到map中一定要使用linkedHashMap(),该集合是有序的,否则在调用存储过程传参时会出问题.<<<
 *
 * @author $Author: Rick$
 * @version $Revision: 1.0 $Date: 2016/4/25 13:31:01 $
 * @version $Revision: 1.1 $Date: 2016/4/28 13:43:01 $
 * @version $Revision: 1.2 $Date: 2016/4/28 14:50:01 $
 */
public interface LoginService {


    public Object queryForObject(LinkedHashMap<String, Object> params) throws Exception;

    public List queryMenuByUserRole (String user_id) throws Exception;

    public Map queryIpAddress(int id) throws Exception;
}

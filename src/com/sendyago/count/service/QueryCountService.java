 /* ==============================================================
 * $ID: Service.java, v1.0 2016/5/4 17:13:01 zq Exp $
 * created: [2016-06-25 17:13:101] by zq
 * ==============================================================
 * 健康监测系统模块统计分析查询
 * ==============================================================
 * Copyright (c) 哈尔滨工大云帆智慧信息技术有限公司 All rights reserved
 * ==============================================================
 */
package com.sendyago.count.service;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sendyago.util.common.*;
/**
 * 
 * 描述类  统计分析查询接口类
 * 描述类声明方法
 * @author  $Author: zq&    
 * @version $Revision:1.0 2016年5月3日 下午5:11:29 $
 *
 */
public interface QueryCountService {

    public List queryForList(LinkedHashMap<String, Object> params) throws Exception;
    
    public List queryForList0(LinkedHashMap<String, Object> params) throws Exception;
    
    public List queryForList1(LinkedHashMap<String, Object> params) throws Exception;
    
    public ResultSet exportExcel(LinkedHashMap<String, Object> params, String flag) throws Exception;

    public Map<Object, String> queryForObject(LinkedHashMap<String, Object> params) throws Exception;

    public Map queryLineWarn(LinkedHashMap<String, Object> params) throws Exception; 
 
    public List queryTypeList(LinkedHashMap<String, Object> params) throws Exception;
    
    public List queryPartList(LinkedHashMap<String, Object> params) throws Exception;
    
    public List querySectionList(LinkedHashMap<String, Object> params) throws Exception;
    
    public List querySensorList(LinkedHashMap<String, Object> params) throws Exception;
    
    PageBean createQueryPageForWarn(LinkedHashMap<String, Object> param);
    public List createQueryPageForState(LinkedHashMap<String, Object> param);
    
}

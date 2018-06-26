/* ==============================================================
 * $ID: MonitoringService.java, v1.0 2016/7/28 14:57:18 fsd Exp $
 * created: [2016-07-25 13:33:18] by fsd
 * ==============================================================
 * 健康监测系统模块化公共信息
 *
 * 健康监测系统模块化即将健康监测系统各功能模块进行项目化形式进行开发
 * 将各功能模块划分为各个WEB项目
 * ==============================================================
 * Copyright (c) 哈尔滨工大云帆智慧信息技术有限公司 All rights reserved
 * ==============================================================
 */
package com.sendyago.monitoring.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Component;
/**
 * 实时监测 service接口
 * @author fsd
 *
 */
@Component
public interface MonitoringService {

	public List queryForSectionList(LinkedHashMap<String, Object> params) throws Exception;

	public List queryForSensorList(LinkedHashMap<String, Object> params) throws Exception;

	public List queryTop50(LinkedHashMap<String, Object> params) throws Exception;

	public List queryWaringVal(LinkedHashMap<String, Object> params) throws Exception;

	public List getDataForLast(LinkedHashMap<String, Object> params) throws Exception;
	
	public List cxlsqx(LinkedHashMap<String, Object> params) throws Exception;

	public List getWaringCountByTypeId(LinkedHashMap<String, Object> params) throws Exception;

	public List queryForWarningList(LinkedHashMap<String, Object> params) throws Exception;

	public List getTodaySs(LinkedHashMap<String, Object> params) throws Exception;

	public String querySensorTypeName(String type_id);
	
	public List queryForIp() throws Exception;
	
	public List dxcx(LinkedHashMap<String, Object> params) throws Exception;
	
	public List getAllSensorList();
	
	
	public List queryTcbjxx(String typeid, String i, String pageno) throws Exception;
	
	
}
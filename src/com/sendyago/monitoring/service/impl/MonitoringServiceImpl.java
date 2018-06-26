/* ==============================================================
 * $ID: MonitoringServiceImpl.java, v1.0 2016/7/28 14:57:18 fsd Exp $
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
package com.sendyago.monitoring.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.sendyago.monitoring.service.MonitoringService;
import com.sendyago.util.jdbc.OracleJDBC;
/**
 * 
 * @author fsd
 *
 */
@Component
public class MonitoringServiceImpl extends OracleJDBC implements MonitoringService {
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM");
	
	@Override
	public List queryTcbjxx(String typeid, String i, String pageno) throws Exception {
		// TODO Auto-generated method stub
		//本周一
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 1);
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("typeid", Integer.parseInt(typeid));
		map.put("i", Integer.parseInt(i));
		map.put("bz", df.format(c.getTime()) + " 00:00:00");
		map.put("byy", df2.format(new Date()) + "-01 00:00:00");
		map.put("pageno", Integer.parseInt(pageno));
		return procedures4Query(map, "MONITORING_queryTcbjxx");
	}
	/**
	 * 通过调用存储过程public_get_ip 获取截面所在ip地址
	 * 传入：
	 * 返回集合
	 */
	@Override
	public List queryForIp() throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("pid", 1);
		List list = procedures4Query(map, "PUBLIC_GET_IP");
		return list;
	}

	/**
	 * 通过调用存储过程MONITORING_SENSOR_SECTION_LIST 获取传感器截面信息
	 * 传入：sensorType
	 * 返回集合
	 */
	@Override
	public List queryForSectionList(LinkedHashMap<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("sensorType", params.get("sensorType"));
		List list = procedures4Query(map, "MONITORING_SENSOR_SECTION_LIST");
		return list;
	}

	/**
	 * 通过调用存储过程MONITORING_SENSOR_LIST 获取传感器信息
	 * 传入：sensorType
	 * 返回集合
	 */
	@Override
	public List queryForSensorList(LinkedHashMap<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("sensorType", params.get("sensorType"));
		List list = procedures4Query(map, "MONITORING_SENSOR_LIST");
		return list;
	}

	/**
	 * 通过调用存储过程MONITORING_GET_TOP_DATA 获取某传感器的历史数据
	 * 传入：sensor_id
	 * 返回集合
	 */
	@Override
	public List queryTop50(LinkedHashMap<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("sensor_id", params.get("sensor_id"));
		List list = procedures4Query(map, "MONITORING_GET_TOP_DATA");
		return list;
	}

	/**
	 * 通过调用存储过程MONITORING_WARING_VAL 获取某传感器的过去24小时时均值
	 * 传入：sid
	 * 返回集合
	 */
	@Override
	public List queryWaringVal(LinkedHashMap<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("sid", params.get("sensor_id"));
		List list = procedures4Query(map, "MONITORING_WARING_VAL");
		return list;
	}

	/**
	 * 通过调用存储过程MONITORING_GET_SENSOR_TYPENAME 获取某传感器的监测项目
	 * 传入：sensorType
	 * 返回字符串：TYPE_NAME
	 */
	@Override
	public String querySensorTypeName(String type_id) {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("sensorType", type_id);
		List list = procedures4Query(map, "MONITORING_GET_SENSOR_TYPENAME");
		Map map2 = (Map) list.get(0);
		return map2.get("TYPE_NAME") + "";
	}

	/**
	 * 通过调用存储过程MONITORING_GET_DATA_FOR_LAST 获取某传感器的过去1,6,12小时数据信息
	 * 传入：sid，last_time
	 * 返回集合
	 */
	@Override
	public List getDataForLast(LinkedHashMap<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int last_flag = Integer.parseInt(params.get("last_flag") + "");
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("sid", params.get("sensor_id"));
		map.put("last_time", df.format(new Date(d.getTime() - last_flag * 60 * 60 * 1000)));
		List list = procedures4Query(map, "MONITORING_GET_DATA_FOR_LAST");
		return list;
	}

	/**
	 * 通过调用存储过程MONITORING_TODAY_AVG 获取某传感器的当天时均值信息
	 * 传入：sensor_id
	 * 返回集合
	 */
	@Override
	public List getTodaySs(LinkedHashMap<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("sensor_id", params.get("sensor_id"));
		List list = procedures4Query(map, "MONITORING_TODAY_AVG");
		return list;
	}

	/**
	 * 通过调用存储过程MONITORING_WARING_COUNT_BYID 获取某种监测项目本周，本月的报警次数信息
	 * 传入：tid，bz，byy
	 * 返回集合
	 */
	@Override
	public List getWaringCountByTypeId(LinkedHashMap<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM");
		//本周一
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 1);
		//本月
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("tid", params.get("sensorType"));
		map.put("bz", df.format(c.getTime()) + " 00:00:00");
		map.put("byy", df2.format(new Date()) + "-01 00:00:00");
		List list = procedures4Query(map, "MONITORING_WARING_COUNT_BYID");
		return list;
	}

	/**
	 * 通过调用存储过程MONITORING_WARNING_LIST_TYPEID 获取某种监测项目本周，本月的报警信息
	 * 传入：sensorType
	 * 返回集合
	 */
	@Override
	public List queryForWarningList(LinkedHashMap<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("sensorType", params.get("sensorType"));
		//今日报警
		if (params.get("flag").equals("1")) {
			map.put("time1", df.format(d));
		}
		//昨日报警
		else {
			map.put("time1", df.format(new Date(d.getTime() - 24 * 60 * 60 * 1000)));
		}
		map.put("fy_page", params.get("fy_page"));
		List list = procedures4Query(map, "MONITORING_WARNING_LIST_TYPEID");
		return list;
	}

	/**
	 * 通过调用存储过程MONITORING_LSCX 获取某种传感器历史数据
	 * 传入：sid,w7,w8,w9,w10
	 * 返回集合
	 */
	@Override
	public List cxlsqx(LinkedHashMap<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		//传感器id
		map.put("sid", params.get("cxcgq"));
		//开始时间
		map.put("w7", params.get("w7"));
		//结束时间
		map.put("w8", params.get("w8"));
		//开始数据范围
		map.put("w9", params.get("w9").toString().trim());
		//结束数据范围
		map.put("w10", params.get("w10").toString().trim());
		List list = new ArrayList();
		if (params.get("lsqxcx_page") == null) {
			list = procedures4Query(map, "MONITORING_LSCX");
		} else {
			map.put("lsqxcx_page", params.get("lsqxcx_page"));
			list = procedures4Query(map, "MONITORING_LSCX_PAGE");
		}
		
		return list;
	}
	
	/**
	 * 通过调用存储过程MONITORING_DXCX_DATA 更新正常异常，并实时 更新页面各个图表，数据
	 * 传入：sensorType
	 * 返回集合
	 */
	@Override
	public List dxcx(LinkedHashMap<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("tid", params.get("tid"));
		List list = procedures4Query(map, "MONITORING_DXCX_DATA");
		return list;
	}

	@Override
	public List getAllSensorList() {
		// TODO Auto-generated method stub
		List list = procedures4Query(new LinkedHashMap<String, Object>(),"MONITORING_SENSOR_LIST_all");
		return list;
	}

}
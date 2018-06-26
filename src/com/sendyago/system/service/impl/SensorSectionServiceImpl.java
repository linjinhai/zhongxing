/* ==============================================================
 * $ID: SensorSectionServiceImpl.java, v1.4 2016/4/28 14:57:18 zgx Exp $
 * created: [2016-04-25 13:33:18] by zgx
 * ==============================================================
 * 健康监测系统模块化公共信息
 *
 * 健康监测系统模块化即将健康监测系统各功能模块进行项目化形式进行开发
 * 将各功能模块划分为各个WEB项目
 * ==============================================================
 * Copyright (c) 哈尔滨工大云帆智慧信息技术有限公司 All rights reserved
 * ==============================================================
 */
package com.sendyago.system.service.impl;

import java.util.LinkedHashMap;

import org.springframework.stereotype.Service;

import com.sendyago.system.service.SensorSectionService;

/**
 * ================================================== 
 * Service业务层实现类 - 系统管理 - 传感器截面管理
 * --------------------------------------------------
 * @author $Author: ZGX$ 
 * --------------------------------------------------
 * @version $Revision: 1.0 $Date: 2016/5/10 9:09:20$
 * ==================================================
 */
@Service("sensorSectionService")
public class SensorSectionServiceImpl extends BaseServiceImpl implements SensorSectionService {

	@Override
	public void update(LinkedHashMap<String, Object> params) throws Exception {
		String flag = params.get("flag").toString();
		String t = params.get("t").toString();
		params.remove("flag");
		switch (flag) {
		case "insert":
			LinkedHashMap<String, Object> i_map = new LinkedHashMap<String, Object>();
			i_map.put("section_name", params.get("section_name"));
			i_map.put("section_path", params.get("section_path"));
			i_map.put("type_id", params.get("type_id"));
			super.update(i_map, "SYSTEM_SENSOR_SECTION_INSERT");
			break;
		case "update":
			LinkedHashMap<String, Object> u_map = new LinkedHashMap<String, Object>();
			u_map.put("section_id", params.get("section_id"));
			u_map.put("section_name", params.get("section_name"));
			u_map.put("section_path", params.get("section_path"));
			u_map.put("type_id", params.get("type_id"));
			super.update(u_map, "SYSTEM_SENSOR_SECTION_UPDATE");
			break;
		case "delete":
			params.remove("t");
			super.update(params, "SYSTEM_SENSOR_SECTION_DELETE");
			break;
		}
	}

}

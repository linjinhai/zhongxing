/* ==============================================================
 * $ID: SensorTypeServiceImpl.java, v1.4 2016/4/28 14:57:18 zgx Exp $
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

import com.sendyago.system.service.SensorTypeService;

/**
 * ================================================== 
 * Service业务层实现类 - 系统管理 - 传感器类型管理
 * --------------------------------------------------
 * @author $Author: ZGX$ 
 * --------------------------------------------------
 * @version $Revision: 1.0 $Date: 2016/5/10 9:09:20$
 * ==================================================
 */
@Service("sensorTypeService")
public class SensorTypeServiceImpl extends BaseServiceImpl implements SensorTypeService {
	
	@Override
	public void update(LinkedHashMap<String, Object> params) throws Exception {
		String flag = params.get("flag").toString();
		params.remove("flag");
		switch (flag) {
		case "insert":
			super.update(params, "SYSTEM_SENSOR_TYPE_INSERT");
			break;
		case "update":
			super.update(params, "SYSTEM_SENSOR_TYPE_UPDATE");
			break;
		case "delete":
			super.update(params, "SYSTEM_SENSOR_TYPE_DELETE");
			break;
		}
	}

}

/* ==============================================================
 * $ID: ParamAssessServiceImpl.java, v1.0 2016/7/28 14:57:18 chengqiang Exp $
 * created: [2016-07-25 13:33:18] by chengqiang
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

import com.sendyago.system.service.ParamAssessService;
/**
 * 评定参数管理接口实现类
 * 评定参数信息的查询,保存,更新,删除等操作
 * update()方法用于保存,更新,删除等操作
 * @author $Author: chengqiang$
 * @version $Revision: 1.0 $Date: 2016/7/25 13:51:01 $
 */
@Service("paramassessService")
public class ParamAssessServiceImpl extends BaseServiceImpl implements ParamAssessService {

	@Override
	public void update(LinkedHashMap<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				String flag = params.get("flag").toString();
				params.remove("flag");
				switch (flag) {
				case "insert":
					LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
					map.put("struct_id", params.get("struct_id"));
					map.put("desc_index", params.get("desc_index"));
					map.put("desc_dx", params.get("desc_dx"));
					map.put("desc_dl", params.get("desc_dl"));
					map.put("param_type", params.get("param_type"));
					super.update(map, "BRIDGE_PARAM_ASSESS_INSERT");
					break;
				case "update":
					LinkedHashMap<String, Object> updatemap = new LinkedHashMap<String, Object>();
					updatemap.put("struct_id", params.get("struct_id"));
					updatemap.put("param_id", params.get("param_id"));
					updatemap.put("desc_index", params.get("desc_index"));
					updatemap.put("desc_dx", params.get("desc_dx"));
					updatemap.put("desc_dl", params.get("desc_dl"));
					updatemap.put("param_type", params.get("param_type"));
					super.update(updatemap, "BRIDGE_PARAM_ASSESS_UPDATE");
					break;
				case "delete":
					super.update(params, "BRIDGE_PARAM_ASSESS_DELETE");
					break;
				}
	}

}

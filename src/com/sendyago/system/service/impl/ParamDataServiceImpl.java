/* ==============================================================
 * $ID: ParamDataServiceImpl.java, v1.0 2016/7/28 14:57:18 chengqiang Exp $
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
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import com.sendyago.system.service.ParamDataService;
import com.sendyago.util.common.Convert;
import com.sendyago.util.common.Pager;
/**
 * 评定系统管理接口实现类
 * 评定系统信息的查询,保存,更新,删除等操作
 * update()方法用于保存,更新,删除等操作
 * custompager()方法用于查询
 * @author $Author: chengqiang$
 * @version $Revision: 1.0 $Date: 2016/7/25 13:31:01 $
 */
@Service("paramdataService")
public class ParamDataServiceImpl extends BaseServiceImpl implements ParamDataService {

	@Override
	public String update(LinkedHashMap<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		String flag = params.get("flag").toString();
		params.remove("flag");
		switch (flag) {
		case "insert":
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("data_name", params.get("data_name"));
			map.put("data_pid", params.get("data_pid"));
			map.put("data_flag", params.get("data_flag"));
			map.put("data_score", params.get("data_score"));
			super.update(map, "BRIDGE_PARAM_DATA_INSERT");
			break;
		case "update":
			super.update(params, "BRIDGE_PARAM_DATA_UPDATE");
			break;
		case "delete":
			List<Object> list = super.list(params, "BRIDGE_STRUCT_BYPID");
			if (list.size()>0 ) {
				return "no";
			} 
			super.update(params, "BRIDGE_PARAM_DATA_DELETE");
			break;
		}
		return "yes";
	}

	@Override
	public Pager custompager(HttpServletRequest request, Pager pager, String procedure, String searchtwo,
			String searchone) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("search", pager.getSearch());
		map.put("searchone", searchone);
		map.put("searchtwo", searchtwo);
		map.put("pageNumber", pager.getPageNumber());
		map.put("pageSize", pager.getPageSize());
		List<T> list = procedures4Query(map, procedure);
		
		map.put("pageNumber", 0);
		List<T> count_list = procedures4Query(map, procedure);
		pager.setUrl(request.getRequestURL().toString());
		pager.setTotalCount(count_list.size());
		pager.setList(list);
		
		return pager;
	}

}

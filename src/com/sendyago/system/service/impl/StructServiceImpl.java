/* ==============================================================
 * $ID: StructServiceImpl.java, v1.0 2016/7/28 14:57:18 chengqiang Exp $
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

import com.sendyago.system.service.StructService;
import com.sendyago.util.common.Pager;
/**
 * 评定项和构件接口实现类
 * 评定项信息的查询,保存,更新,删除等操作
 * update()方法用于保存,更新,删除等操作
 * custompager()方法用于查询
 * queryDataList()获得评定系统
 * 构件信息的查询,保存,更新,删除等操作
 * cpupdate()方法用于保存,更新,删除等操作
 * customlist()方法用于查询
 * @author $Author: chengqiang$
 * @version $Revision: 1.0 $Date: 2016/7/25 13:51:01 $
 */
@Service("structService")
public class StructServiceImpl extends BaseServiceImpl implements StructService{

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

	@Override
	public String update(LinkedHashMap<String, Object> params) throws Exception {
		String flag = params.get("flag").toString();
		params.remove("flag");
		switch (flag) {
		case "insert":
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("struct_name", params.get("struct_name"));
			map.put("struct_pid", params.get("struct_pid"));
			map.put("is_child", "1");
			super.update(map, "BRIDGE_STRUCT_INSERT");
			break;
		case "update":
			params.put("is_child", "1");
			super.update(params, "BRIDGE_STRUCT_UPDATE");
			break;
		case "delete":
			List<Object> list = super.list(params, "BRIDGE_PARAM_ASSESS_BYSID");
			if (list.size()>0 ) {
				return "no";
			} 
			super.update(params, "BRIDGE_STRUCT_DELETE");
			break;
		}
		return "yes";
	}

	@Override
	public List queryDataList(LinkedHashMap<String, Object> params) {
		// TODO Auto-generated method stub
		  List list = procedures4Query(params, "BRIDGE_PARAM_DATA_BYPID");
		return list;
	}

	@Override
	public List<T> customlist(String procedure) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("search", null);
		map.put("searchone", null);
		map.put("searchtwo", null);
		map.put("pageNumber", 0);
		map.put("pageSize", null);
		List<T> list = list(map, procedure);
		return list;
	}

	@Override
	public void cpupdate(LinkedHashMap<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		String flag = params.get("flag").toString();
		params.remove("flag");
		switch (flag) {
		case "insert":
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("struct_name", params.get("struct_name"));
			map.put("struct_pid", params.get("struct_pid"));
			map.put("is_child", "2");
			super.update(map, "BRIDGE_STRUCT_INSERT");
			break;
		case "update":
			params.put("is_child", "2");
			super.update(params, "BRIDGE_STRUCT_UPDATE");
			break;
		case "delete":
			super.update(params, "BRIDGE_STRUCT_DELETE");
			break;
		}
	}

}

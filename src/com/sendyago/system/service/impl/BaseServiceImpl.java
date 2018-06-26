/* ==============================================================
 * $ID: BaseServiceImpl.java, v1.4 2016/5/6 14:57:18 zgx Exp $
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
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sendyago.system.service.BaseService;
import com.sendyago.util.common.Pager;
import com.sendyago.util.jdbc.OracleJDBC;

/**
 * ================================================== 
 * Service业务层实现类 - 系统管理 - Service基类
 * --------------------------------------------------
 * @author $Author: ZGX$ 
 * --------------------------------------------------
 * @version $Revision: 1.0 $Date: 2016/5/6 10:55:11$
 * ==================================================
 */
public class BaseServiceImpl extends OracleJDBC implements BaseService {

	public <T> List<T> list(String procedure) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("search", null);
		map.put("pageNumber", 0);
		map.put("pageSize", null);
		List<T> list = list(map, procedure);
		return list;
	}
	
	public <T> List<T> list(LinkedHashMap<String, Object> params, String procedure) throws Exception {
		List<T> list = procedures4Query(params, procedure);
		return list;
	}

	public <T> Pager pager(HttpServletRequest request, Pager pager, String procedure) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("search", pager.getSearch());
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

	public <T> T object(LinkedHashMap<String, Object> params, String procedure) throws Exception {
		List<T> list = procedures4Query(params, procedure);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void update(LinkedHashMap<String, Object> params, String procedure) throws Exception {
		procedures4Change(params, procedure);
	}

}

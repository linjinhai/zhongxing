/* ==============================================================
 * $ID: BaseService.java, v1.2 2016/4/28 14:50:01 Rick Exp $
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

import javax.servlet.http.HttpServletRequest;

import com.sendyago.util.common.Pager;

/**
 * ================================================== 
 * Service业务层接口 - 系统管理 - Service基类
 * --------------------------------------------------
 * @author $Author: ZGX$ 
 * --------------------------------------------------
 * @version $Revision: 1.0 $Date: 2016/5/6 10:55:11$
 * ==================================================
 */
public interface BaseService {

	/**
	 * 查询所有列表
	 * 
	 * @return 数据列表集合
	 * @throws Exception
	 */
	<T> List<T> list(String procedure) throws Exception;
	<T> List<T> list(LinkedHashMap<String, Object> params, String procedure) throws Exception;

	/**
	 * 查询分页列表
	 * 
	 * @param request
	 *            请求对象
	 * @param pager
	 *            封装的分页基础信息
	 * @return Pager
	 * 			     分页对象
	 * @throws Exception
	 */
	<T> Pager pager(HttpServletRequest request, Pager pager, String procedure) throws Exception;

	/**
	 * 根据条件属性查询记录
	 * 
	 * @param params
	 *            查询条件
	 * @return 单条记录
	 * @throws Exception
	 */
	<T> T object(LinkedHashMap<String, Object> params, String procedure) throws Exception;

	/**
	 * 保存/更新/删除用户信息
	 * 
	 * @param params
	 *            用户信息的集合
	 * @throws Exception
	 */
	void update(LinkedHashMap<String, Object> params, String procedure) throws Exception;

}

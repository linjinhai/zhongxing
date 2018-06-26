/* ==============================================================
 * $ID: LogService.java, v1.2 2016/4/28 14:50:01 zgx Exp $
 * created: [2016-04-25 13:31:101] by zgx
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

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * ================================================== 
 * Service业务层接口 - 系统管理 - 日志管理
 * --------------------------------------------------
 * @author $Author: ZGX$ 
 * --------------------------------------------------
 * @version $Revision: 1.0 $Date: 2016/5/19 9:09:20$
 * ==================================================
 */
public interface LogService extends BaseService {
	
	/**
	 * 添加日志
	 * 
	 * @param params
	 *            封装的查询条件信息
	 * @return void
	 * @throws Exception 
	 */
	void logInsert(LinkedHashMap<String, Object> params) throws Exception;
	
	/**
	 * 删除日志
	 * 
	 * @param params
	 *            封装的查询条件信息
	 * @return void
	 * @throws Exception 
	 */
	void logDelete(LinkedHashMap<String, Object> params) throws Exception;
	
	/**
	 * 清空日志
	 * 
	 * @param params
	 *            封装的查询条件信息
	 * @return void
	 * @throws Exception 
	 */
	void logClean(LinkedHashMap<String, Object> params) throws Exception;
	/**
	 * 导出
	 * @param rep
	 * @param list
	 * @throws IOException
	 */
	void export(HttpServletResponse rep, List list) throws IOException;
}

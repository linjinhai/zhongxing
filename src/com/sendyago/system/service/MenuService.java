/* ==============================================================
 * $ID: MenuService.java, v1.2 2016/4/28 14:50:01 zgx Exp $
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

import java.util.LinkedHashMap;

/**
 * ================================================== 
 * Service业务层接口 - 系统管理 - 菜单管理
 * --------------------------------------------------
 * @author $Author: ZGX$ 
 * --------------------------------------------------
 * @version $Revision: 1.0 $Date: 2016/5/19 9:09:20$
 * ==================================================
 */
public interface MenuService extends BaseService {
	
	/**
	 * 保存、更新、删除菜单
	 * 
	 * @param params
	 *            封装的查询条件信息
	 * @return void
	 * @throws Exception 
	 */
	void update(LinkedHashMap<String, Object> params, String... buttonTypeIds) throws Exception;
	
	/**
	 * 保存、更新、删除按钮
	 * 
	 * @param params
	 *            封装的查询条件信息
	 * @return void
	 * @throws Exception 
	 */
	void buttonUpdate(LinkedHashMap<String, Object> params) throws Exception;
	
}

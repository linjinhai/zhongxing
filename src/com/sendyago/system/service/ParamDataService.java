/* ==============================================================
 * $ID: ParamDataService.java, v1.0 2016/7/28 14:50:01 chengqiang Exp $
 * created: [2016-07-25 13:31:101] by chengqiang
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

import javax.servlet.http.HttpServletRequest;

import com.sendyago.util.common.Pager;

/**
 * 评定系统管理接口实现类
 * 评定系统信息的查询,保存,更新,删除等操作
 * update()方法用于保存,更新,删除等操作
 * custompager()方法用于查询
 * @author $Author: chengqiang$
 * @version $Revision: 1.0 $Date: 2016/7/25 13:31:01 $
 */
public interface ParamDataService extends BaseService{

	String update(LinkedHashMap<String, Object> params) throws Exception;
	
	Pager custompager(HttpServletRequest request, Pager pager, String procedure, String searchtwo, String searchone);

}

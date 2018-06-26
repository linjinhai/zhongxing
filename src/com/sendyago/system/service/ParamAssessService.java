/* ==============================================================
 * $ID: ParamAssessService.java, v1.0 2016/7/28 14:50:01 chengqiang Exp $
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

/**
 * 评定参数管理接口实现类
 * 评定参数信息的查询,保存,更新,删除等操作
 * update()方法用于保存,更新,删除等操作
 * @author $Author: chengqiang$
 * @version $Revision: 1.0 $Date: 2016/7/25 13:51:01 $
 */
public interface ParamAssessService extends BaseService{

	void update(LinkedHashMap<String, Object> params) throws Exception;

}

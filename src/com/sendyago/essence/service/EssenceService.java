/* ==============================================================
 * $ID: EssenceService.java, v1.0 2016/4/28 13:32:32 fsd Exp $
 * created: [2016-04-28 13:32:32] by fsd
 * ==============================================================
 * 健康监测系统模块化公共信息
 *
 * 健康监测系统模块化即将健康监测系统各功能模块进行项目化形式进行开发
 * 将各功能模块划分为各个WEB项目
 * ==============================================================
 * Copyright (c) 哈尔滨工大云帆智慧信息技术有限公司 All rights reserved
 * ==============================================================
 */
package com.sendyago.essence.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sendyago.system.service.BaseService;
/**
 * 人工巡检 service接口
 * @author fsd
 *
 */
@Component
public interface EssenceService extends BaseService{

	public void addRgxj(LinkedHashMap<String, Object> params) throws Exception;

	public void cap(LinkedHashMap<String, Object> params, String pressName) throws Exception;
	
	public void docap(String sql) throws Exception;
	
	public List docaplist(String sql) throws Exception;
	
	public List allcap(LinkedHashMap<String, Object> params, String pressName) throws Exception;
	
}
/* ==============================================================
 * $ID: EssenceServiceImpl.java, v1.0 2016/4/28 13:32:32 fsd Exp $
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
package com.sendyago.essence.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sendyago.essence.service.EssenceService;
import com.sendyago.system.service.impl.BaseServiceImpl;
import com.sendyago.util.jdbc.OracleJDBC;
/**
 * 人工巡检 service实现类
 * @author fsd
 *
 */
@Component
public class EssenceServiceImpl extends BaseServiceImpl implements EssenceService {
	
	@Override
	public void addRgxj(LinkedHashMap<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		List list = procedures4Query(params, "PUBLIC_GET_IP");
	}
	/**
	 * 根据传入参数语句来执行存储过程
	 */
	@Override
	public void cap(LinkedHashMap<String, Object> params, String pressName) throws Exception {
		// TODO Auto-generated method stub
		procedures4Change(params, pressName);
	}
	/**
	 * 根据传入sql语句来执行命令
	 */
	@Override
	public void docap(String sql) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap map = new LinkedHashMap<>();
		map.put("sql", sql);
		procedures4Change(map, "essence_rgxj_cap");
	}
	/**
	 * 根据传入sql语句来执行查询
	 * 返回List集合
	 */
	@Override
	public List docaplist(String sql) throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap map = new LinkedHashMap<>();
		map.put("sql", sql);
		return procedures4Query(map, "essence_rgxj_list");
	}
	/**
	 * 根据存储过程名 查询集合
	 * 返回List集合
	 */
	@Override
	public List allcap(LinkedHashMap<String, Object> params, String pressName) throws Exception {
		// TODO Auto-generated method stub
		return procedures4Query(params, pressName);
	}

}
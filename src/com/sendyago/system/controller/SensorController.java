/* ==============================================================
 * $ID: SensorController.java, v1.0 2016/4/28 13:32:32 zgx Exp $
 * created: [2016-04-28 13:32:32] by zgx
 * ==============================================================
 * 健康监测系统模块化公共信息
 *
 * 健康监测系统模块化即将健康监测系统各功能模块进行项目化形式进行开发
 * 将各功能模块划分为各个WEB项目
 * ==============================================================
 * Copyright (c) 哈尔滨工大云帆智慧信息技术有限公司 All rights reserved
 * ==============================================================
 */
package com.sendyago.system.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sendyago.system.service.SensorService;
import com.sendyago.util.common.Pager;

/**
 * ================================================== 
 * 控制层 - 系统管理 - 传感器管理
 * --------------------------------------------------
 * @author $Author: ZGX$ 
 * --------------------------------------------------
 * @version $Revision: 1.0 $Date: 2016/5/10 9:09:20$
 * ==================================================
 */
@Controller
@RequestMapping("sensor")
public class SensorController extends BaseController{

	@Autowired
	private SensorService sensorService;

	/**
	 * 查询传感器分页列表
	 * 
	 * @param pager
	 *            分页bean
	 * @return String
	 *            返回页面路径
	 * @throws Exception
	 */
	@RequestMapping("list")
	public String list(Pager pager) throws Exception {
		pager = sensorService.pager(request, pager, "SYSTEM_SENSOR_LIST");
		request.setAttribute("pager", pager);
		return "sensor/list";
	}
	
	/**
	 * 传感器添加页面
	 * 
	 * @param request
	 *            请求对象
	 * @return String
	 *            返回页面路径
	 * @throws Exception
	 */
	@RequestMapping("insertPage")
	public String insertPage() throws Exception {
		List<Map<String, ?>> part_list = sensorService.list("SYSTEM_SENSOR_PART_LIST");
		request.setAttribute("part_list", part_list);
		List<Map<String, ?>> section_list = sensorService.list("SYSTEM_SENSOR_SECTION_LIST");
		request.setAttribute("section_list", section_list);
		return "sensor/edit";
	}

	/**
	 * 传感器修改页面，根据查询条件查询传感器信息
	 * 
	 * @param params
	 *            封装的查询条件信息
	 * @return String
	 *            返回页面路径
	 * @throws Exception 
	 */
	@RequestMapping(value = "updatePage")
	public String updatePage(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		List<Map<String, ?>> part_list = sensorService.list("SYSTEM_SENSOR_PART_LIST");
		request.setAttribute("part_list", part_list);
		List<Map<String, ?>> section_list = sensorService.list("SYSTEM_SENSOR_SECTION_LIST");
		request.setAttribute("section_list", section_list);
		Map<?,?> object = sensorService.object(params, "SYSTEM_SENSOR_INFO");
		request.setAttribute("object", object);
		return "sensor/edit";
	}

	/**
	 * 添加、修改、删除传感器
	 * 
	 * @param params
	 *            封装的查询条件信息
	 * @return redirect 
	 *            请求跳转
	 * @throws Exception 
	 */
	@RequestMapping(value = "update")
	public String update(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		sensorService.update(params);
		return "redirect:list";
	}

}
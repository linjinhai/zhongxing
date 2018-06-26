/* ==============================================================
 * $ID: SensorPartController.java, v1.0 2016/4/28 13:32:32 zgx Exp $
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

import com.sendyago.system.service.SensorPartService;
import com.sendyago.util.common.Pager;

/**
 * ================================================== 
 * 控制层 - 系统管理 - 传感器检测项目管理
 * --------------------------------------------------
 * @author $Author: ZGX$ 
 * --------------------------------------------------
 * @version $Revision: 1.0 $Date: 2016/5/10 9:09:20$
 * ==================================================
 */
@Controller
@RequestMapping("sensorPart")
public class SensorPartController extends BaseController {

	@Autowired
	private SensorPartService sensorPartService;

	/**
	 * 查询传感器检测项目分页列表
	 * 
	 * @param pager
	 *            分页bean
	 * @return String
	 *            返回页面路径
	 * @throws Exception
	 */
	@RequestMapping("list")
	public String list(Pager pager) throws Exception {
		pager = sensorPartService.pager(request, pager, "SYSTEM_SENSOR_PART_LIST");
		request.setAttribute("pager", pager);
		return "sensorPart/list";
	}

	/**
	 * 传感器检测项目添加页面
	 * 
	 * @param request
	 *            请求对象
	 * @return String
	 *            返回页面路径
	 * @throws Exception
	 */
	@RequestMapping("insertPage")
	public String insertPage() throws Exception {
		List<Map<String, ?>> type_list = sensorPartService.list("SYSTEM_SENSOR_TYPE_LIST");
		request.setAttribute("type_list", type_list);
		return "sensorPart/edit";
	}

	/**
	 * 传感器检测项目修改页面，根据查询条件查询传感器检测项目信息
	 * 
	 * @param params
	 *            封装的查询条件信息
	 * @return String
	 *            返回页面路径
	 * @throws Exception 
	 */
	@RequestMapping(value = "updatePage")
	public String updatePage(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		List<Map<String, ?>> type_list = sensorPartService.list("SYSTEM_SENSOR_TYPE_LIST");
		request.setAttribute("type_list", type_list);
		Map<?, ?> object = sensorPartService.object(params, "SYSTEM_SENSOR_PART_INFO");
		request.setAttribute("object", object);
		return "sensorPart/edit";
	}

	/**
	 * 添加、修改、删除传感器检测项目
	 * 
	 * @param params
	 *            封装的查询条件信息
	 * @return redirect 
	 *            请求跳转
	 * @throws Exception 
	 */
	@RequestMapping(value = "update")
	public String update(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		if (!params.get("flag").equals("delete")) {

			//上传文件并返回文件名
			String section_path = this.fileUpload("section_file");
			//如果上传文件不为null，删除原文件，并设定新文件名参数
			if (section_path != null) {
				//如果是修改，则执行删除文件
				/*
				if (params.get("flag").toString().equals("update")) {
					LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
					map.put("section_id", params.get("section_id"));
					Map<?, ?> object = sensorSectionService.object(map, "SYSTEM_SENSOR_SECTION_INFO");
					if (!"null".equals(object.get("SECTION_PATH")) && object.get("SECTION_PATH") != null) {
						this.fileDelete(object.get("SECTION_PATH").toString());
					}
				}
				*/
				params.put("section_path", section_path);
			}
		}
		sensorPartService.update(params);
		return "redirect:list";
	}

}
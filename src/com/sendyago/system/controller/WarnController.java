/* ==============================================================
 * $ID: WarnController.java, v1.0 2016/4/28 13:32:32 zgx Exp $
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
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sendyago.system.service.WarnService;
import com.sendyago.util.common.Pager;

/**
 * ================================================== 
 * 控制层 - 系统管理 - 预警阀值管理
 * --------------------------------------------------
 * @author $Author: ZGX$ 
 * --------------------------------------------------
 * @version $Revision: 1.0 $Date: 2016/5/10 9:09:20$
 * ==================================================
 */
@Controller
@RequestMapping("warn")
public class WarnController extends BaseController{

	@Autowired
	private WarnService warnService;

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
		pager = warnService.pager(request, pager, "SYSTEM_SENSOR_WARN_LIST");
		request.setAttribute("pager", pager);
		return "warn/list";
	}
	
	
	/**
	 * 预警阀值编辑页面
	 * 
	 * @param params
	 *            封装的查询条件信息
	 * @return String
	 *            返回页面路径
	 * @throws Exception 
	 */
	@RequestMapping(value = "updatePage")
	public String updatePage(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		Map<?,?> object = warnService.object(params, "SYSTEM_SENSOR_WARN_INFO");
		request.setAttribute("object", object);
		return "warn/edit";
	}

	/**
	 * 保存预警阀值
	 * 
	 * @param params
	 *            封装的查询条件信息
	 * @return redirect 
	 *            请求跳转
	 * @throws Exception 
	 */
	@RequestMapping(value = "update")
	public String update(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("sensor_id", params.get("sensor_id")==""? null:Integer.valueOf(params.get("sensor_id").toString()));
		
		map.put("warn_lv1",params.get("warn_lv1")==""? null:Double.valueOf(params.get("warn_lv1").toString()));
		map.put("warn_lv11", params.get("warn_lv11")==""?null :Double.valueOf(params.get("warn_lv11").toString()));
		map.put("warn_lv2", params.get("warn_lv2")==""? null:Double.valueOf(params.get("warn_lv2").toString()));
		 
		map.put("warn_lv22", params.get("warn_lv22")==""? null:Double.valueOf(params.get("warn_lv22").toString()));
		map.put("warn_lv3", params.get("warn_lv3")==""? null:Double.valueOf(params.get("warn_lv3").toString()));
		map.put("warn_lv33", params.get("warn_lv33")==""?null :Double.valueOf(params.get("warn_lv33").toString()));
		System.out.println(map);
		warnService.update(map, "SYSTEM_SENSOR_WARN_UPDATE");
		return "redirect:list";
	}

}
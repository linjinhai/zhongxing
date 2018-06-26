/* ==============================================================
 * $ID: LogController.java, v1.0 2016/4/28 13:32:32 ZGX Exp $
 * created: [2016-04-28 13:32:32] by ZGX
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

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sendyago.system.service.LogService;
import com.sendyago.util.common.Pager;

/**
 * ================================================== 
 * 控制层 - 系统管理 - 日志管理
 * --------------------------------------------------
 * @author $Author: ZGX$ 
 * 
 * list()：查询菜单分页列表
 * logInsert()：插入日志
 * dellog（）：日志全部删除
 * export（）：导出日志
 * --------------------------------------------------
 * @version $Revision: 1.0 $Date: 2016/5/18 9:09:20$
 * ==================================================
 */
@Controller
@RequestMapping("log")
public class LogController extends BaseController{

	@Autowired
	private LogService logService;

	/**
	 * 查询菜单分页列表
	 * 
	 * @param pager
	 *            分页bean
	 * @return String
	 *            返回页面路径
	 * @throws Exception
	 */
	@RequestMapping("list")
	public String list(Pager pager) throws Exception {
		pager = logService.pager(request, pager, "SYSTEM_LOG_LIST");
		request.setAttribute("pager", pager);
		return "log/list";
	}
	
	/**
	 * 查询菜单分页列表
	 * 
	 * @param pager
	 *            分页bean
	 * @return String
	 *            返回页面路径
	 * @throws Exception
	 */
	@RequestMapping("logInsert")
	public void logInsert(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		logService.logInsert(params);
	}
	/**
	 * 日志全部删除
	 * @param params
	 * @throws Exception
	 */
	@RequestMapping("del")
	public String dellog(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		
		logService.update(map, "SYSTEM_LOG_DELETE");
		return "redirect:list";
	}
	/**
	 * 导出日志
	 */
	@RequestMapping(value = "export")
	public void export(HttpServletResponse rep, @RequestParam LinkedHashMap<String, Object> params) throws Exception {
		List list = logService.list("SYSTEM_LOG_LIST");
		logService.export(rep, list);
	}

}
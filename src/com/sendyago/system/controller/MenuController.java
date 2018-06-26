/* ==============================================================
 * $ID: MenuController.java, v1.0 2016/4/28 13:32:32 zgx Exp $
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

import com.sendyago.system.service.MenuService;
import com.sendyago.util.common.Pager;

/**
 * ================================================== 
 * 控制层 - 系统管理 - 菜单管理
 * --------------------------------------------------
 * @author $Author: ZGX$ 
 * list():查询菜单分页列表
 * insertPage():菜单添加页面
 * updatePage():菜单修改页面，根据查询条件查询菜单信息
 * update():添加、修改、删除菜单
 * buttonList():查询按钮分页列表
 * buttonInsertPage():按钮添加页面
 * buttonUpdatePage（）：按钮修改页面，根据查询条件查询按钮信息
 * buttonUpdate（）：添加、修改、删除按钮
 * --------------------------------------------------
 * @version $Revision: 1.0 $Date: 2016/5/18 9:09:20$
 * ==================================================
 */
@Controller
@RequestMapping("menu")
public class MenuController extends BaseController{

	@Autowired
	private MenuService menuService;

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
		pager = menuService.pager(request, pager, "SYSTEM_MENU_LIST");
		request.setAttribute("pager", pager);
		List<Map<String, ?>> list = menuService.list("SYSTEM_MENU_LIST");
		request.setAttribute("list", list);
		return "menu/list";
	}
	
	/**
	 * 菜单添加页面
	 * 
	 * @param request
	 *            请求对象
	 * @return String
	 *            返回页面路径
	 * @throws Exception
	 */
	@RequestMapping("insertPage")
	public String insertPage() throws Exception {
		List<Map<String, ?>> list = menuService.list("SYSTEM_MENU_LIST");
		request.setAttribute("list", list);
		
		List<Map<String, ?>> button_list = menuService.list("SYSTEM_BUTTON_LIST");
		request.setAttribute("button_list", button_list);
		
		return "menu/edit";
	}

	/**
	 * 菜单修改页面，根据查询条件查询菜单信息
	 * 
	 * @param params
	 *            封装的查询条件信息
	 * @return String
	 *            返回页面路径
	 * @throws Exception 
	 */
	@RequestMapping(value = "updatePage")
	public String updatePage(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		List<Map<String, ?>> list = menuService.list("SYSTEM_MENU_LIST");
		request.setAttribute("list", list);
		
		List<Map<String, ?>> button_list = menuService.list("SYSTEM_BUTTON_LIST");
		request.setAttribute("button_list", button_list);
		
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("menu_id", params.get("id"));
		List<Map<String, ?>> menu_button_list = menuService.list(map, "SYSTEM_MENU_BUTTON_LIST");
		request.setAttribute("menu_button_list", menu_button_list);
		
		Map<?,?> object = menuService.object(params, "SYSTEM_MENU_INFO");
		request.setAttribute("object", object);
		
		return "menu/edit";
	}

	/**
	 * 添加、修改、删除菜单
	 * 
	 * @param params
	 *            封装的查询条件信息
	 * @return redirect 
	 *            请求跳转
	 * @throws Exception 
	 */
	@RequestMapping(value = "update")
	public String update(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		String[] buttonTypeIds = request.getParameterValues("button_type_id");
		menuService.update(params, buttonTypeIds);
		return "redirect:list";
	}
	
	/**
	 * 查询按钮分页列表
	 * 
	 * @param pager
	 *            分页bean
	 * @return String
	 *            返回页面路径
	 * @throws Exception
	 */
	@RequestMapping("buttonList")
	public String buttonList(Pager pager) throws Exception {
		pager = menuService.pager(request, pager, "SYSTEM_BUTTON_LIST");
		request.setAttribute("pager", pager);
		return "menu/buttonList";
	}
	
	/**
	 * 按钮添加页面
	 * 
	 * @param request
	 *            请求对象
	 * @return String
	 *            返回页面路径
	 * @throws Exception
	 */
	@RequestMapping("buttonInsertPage")
	public String buttonInsertPage() throws Exception {
		return "menu/buttonEdit";
	}

	/**
	 * 按钮修改页面，根据查询条件查询按钮信息
	 * 
	 * @param params
	 *            封装的查询条件信息
	 * @return String
	 *            返回页面路径
	 * @throws Exception 
	 */
	@RequestMapping(value = "buttonUpdatePage")
	public String buttonUpdatePage(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		Map<?,?> object = menuService.object(params, "SYSTEM_BUTTON_INFO");
		request.setAttribute("object", object);
		return "menu/buttonEdit";
	}

	/**
	 * 添加、修改、删除按钮
	 * 
	 * @param params
	 *            封装的查询条件信息
	 * @return redirect 
	 *            请求跳转
	 * @throws Exception 
	 */
	@RequestMapping(value = "buttonUpdate")
	public String buttonUpdate(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		menuService.buttonUpdate(params);
		return "redirect:buttonList";
	}
	
}
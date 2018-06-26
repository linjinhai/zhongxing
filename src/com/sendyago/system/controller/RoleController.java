/* ==============================================================
 * $ID: RoleController.java, v1.0 2016/4/28 13:32:32 zgx Exp $
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

import com.sendyago.system.service.RoleService;
import com.sendyago.util.common.Pager;

/**
 * ================================================== 
 * 控制层 - 系统管理 - 角色管理
 * --------------------------------------------------
 * @author $Author: ZGX$ 
 * --------------------------------------------------
 * @version $Revision: 1.0 $Date: 2016/5/10 9:09:20$
 * ==================================================
 */
@Controller
@RequestMapping("role")
public class RoleController extends BaseController{

	@Autowired
	private RoleService roleService;

	/**
	 * 查询角色分页列表
	 * 
	 * @param pager
	 *            分页bean
	 * @return String
	 *            返回页面路径
	 * @throws Exception
	 */
	@RequestMapping("list")
	public String list(Pager pager) throws Exception {
		pager = roleService.pager(request, pager, "SYSTEM_ROLE_LIST");
		request.setAttribute("pager", pager);
		return "role/list";
	}
	
	/**
	 * 角色添加页面
	 * 
	 * @param request
	 *            请求对象
	 * @return String
	 *            返回页面路径
	 * @throws Exception
	 */
	@RequestMapping("insertPage")
	public String insertPage() throws Exception {
		return "role/edit";
	}

	/**
	 * 角色修改页面，根据属性信息查询角色信息
	 * 
	 * @param params
	 *            封装的查询条件信息
	 * @return String
	 *            返回页面路径
	 * @throws Exception 
	 */
	@RequestMapping(value = "updatePage")
	public String updatePage(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		Map<?,?> object = roleService.object(params, "SYSTEM_ROLE_INFO");
		request.setAttribute("object", object);
		return "role/edit";
	}

	/**
	 * 添加、修改角色
	 * 
	 * @param params
	 *            封装的查询条件信息
	 * @return redirect 
	 *            请求跳转
	 * @throws Exception 
	 */
	@RequestMapping(value = "update")
	public String update(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		roleService.update(params);
		return "redirect:list";
	}
	
	/**
	 * 删除角色
	 * 
	 * @param params
	 *            封装的查询条件信息
	 * @return void
	 * @throws Exception 
	 */
	@RequestMapping(value = "delete")
	public void delete(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		Map<?,?> object = roleService.object(params, "SYSTEM_ROLE_DELETE");
		if(object==null){
			ajax(true);
		} else {
			ajax(false);
		}
	}
	
	/**
	 * 编辑角色权限页面
	 * 
	 * @param params
	 *            封装的查询条件信息
	 * @return void
	 * @throws Exception 
	 */
	@RequestMapping(value = "permissionPage")
	public String permissionPage(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		//菜单集合
		List<Map<String, String>> menu_list = roleService.list("SYSTEM_MENU_LIST");
		request.setAttribute("menu_list", menu_list);
		
		//菜单权限
		List<?> role_menu_list = roleService.list(params, "SYSTEM_ROLE_MENU_LIST");
		request.setAttribute("role_menu_list", role_menu_list);
		
		//获取菜单拥有按钮权限的最大值
		int max_size = roleService.buttonSize(menu_list);
		request.setAttribute("max_size", max_size);
		
		//菜单对应的按钮集合
		List<Map<String, String>> menu_button_list = roleService.menuButtons(menu_list);
		request.setAttribute("menu_button_list", menu_button_list);
		
		//菜单对应的按钮权限
		List<Map<String, String>> role_menu_button_list = roleService.list(params, "SYSTEM_ROLE_BUTTON_LIST");
		request.setAttribute("role_menu_button_list", role_menu_button_list);
		
		request.setAttribute("role_id", params.get("role_id"));
		return "role/permission";
	}
	
	/**
	 * 编辑角色权限页面
	 * 
	 * @param params
	 *            封装的查询条件信息
	 * @return void
	 * @throws Exception 
	 */
	@RequestMapping(value = "permission")
	public String permission(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		//保存菜单权限
		String role_id = request.getParameter("role_id");
		String[] menu_ids = request.getParameterValues("menu_id");
		roleService.permission_menu(role_id, menu_ids);
		
		//保存菜单对应的按钮权限
		String[] button_ids = request.getParameterValues("button_id");
		roleService.permission_button(button_ids);
		
		return "redirect:list";
	}
	
	/**
	 * 查询角色是否具备操作功能的权限
	 * 
	 * @param params
	 *            封装的查询条件信息
	 * @return void
	 * @throws Exception 
	 */
	@RequestMapping(value = "shiro")
	public void shiro(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("IN_ROLE_ID", params.get("role_id"));
		map.put("IN_MENU_ID", params.get("menu_id"));
		map.put("IN_BUTTON_TYPE_ID", params.get("buttonType"));
		Map<String, String> object = roleService.object(map, "SYSTEM_ROLE_MENU_BUTTON_INFO");
		boolean is = object == null || equals("") ? false : true;
		ajax(is);
	}

}
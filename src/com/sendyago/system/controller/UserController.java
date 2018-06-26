/* ==============================================================
 * $ID: UserController.java, v1.0 2016/4/28 13:32:32 zgx Exp $
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

import com.sendyago.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sendyago.util.common.Pager;

/**
 * ================================================== 
 * 控制层 - 系统管理 - 用户管理
 * --------------------------------------------------
 * @author $Author: ZGX$ 
 * --------------------------------------------------
 * @version $Revision: 1.0 $Date: 2016/5/6 10:55:11$
 * ==================================================
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController{

	@Autowired
	private UserService userService;

	/**
	 * 查询用户分页列表
	 * 
	 * @param pager
	 *            分页bean
	 * @return String
	 *            返回页面路径
	 * @throws Exception
	 */
	@RequestMapping("list")
	public String queryList(Pager pager) throws Exception {
		pager = userService.pager(request, pager, "SYSTEM_USER_LIST");
		request.setAttribute("pager", pager);
		return "user/list";
	}
	
	/**
	 * 用户添加页面，需要传递ROLE列表
	 * 
	 * @return String
	 *            返回页面路径
	 * @throws Exception
	 */
	@RequestMapping("insertPage")
	public String insertPage() throws Exception {
		List<Map<String, ?>> list = userService.list("SYSTEM_ROLE_LIST");
		request.setAttribute("list", list);
		return "user/edit";
	}

	/**
	 * 用户修改页面，根据属性信息查询用户信息
	 * 
	 * @param params
	 *            封装的查询条件信息
	 * @return String
	 *            返回页面路径
	 * @throws Exception 
	 */
	@RequestMapping(value = "updatePage")
	public String updatePage(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("id", params.get("id"));
		Map<String, Object> object = userService.object(map, "SYSTEM_USER_INFO");
		object.put("USER_PASS", object.get("USER_PASS"));
		request.setAttribute("object", object);
		List<?> list = userService.list("SYSTEM_ROLE_LIST");
		request.setAttribute("list", list);
		if(params.get("tag") != null){
			request.setAttribute("tag", params.get("tag"));
		}
		if(params.get("zz") != null){
			request.setAttribute("zz", params.get("zz"));
		} 
		return "user/edit";
	}

	/**
	 * 添加、修改、删除用户
	 * 
	 * @param params
	 *            封装的查询条件信息
	 * @return redirect 
	 *            请求跳转
	 * @throws Exception 
	 */
	@RequestMapping(value = "update")
	public String update(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		Object tag = params.get("tag");
		userService.update(params);
		if(tag != null){
			return "redirect:updatePage?tag=1&zz=zz&id=" + params.get("user_id");
		} else{
			return "redirect:list";
		}
		
	}
	
	/**
	 * 按照用户登录名查询用户信息(验证是否重复)
	 * 
	 * @param params
	 *            封装的查询条件信息
	 * @return ajax
	 * @throws Exception 
	 */
	@RequestMapping(value = "verifyRepeat")
	public void verifyRepeat(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		Map<?,?> object = userService.object(params, "SYSTEM_USER_INFO");
		if(object==null){
			ajax(true);
		} else {
			ajax(false);
		}
	}

}
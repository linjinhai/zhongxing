/* ==============================================================
 * $ID: SystemModel.java, v1.0 2016/4/25 13:14:14 Rick Exp $
 * created: [2016-04-25 13:14:14] by Rick
 * ==============================================================
 * 健康监测系统模块化公共信息
 *
 * 健康监测系统模块化即将健康监测系统各功能模块进行项目化形式进行开发
 * 将各功能模块划分为各个WEB项目
 * ==============================================================
 * Copyright (c) 哈尔滨工大云帆智慧信息技术有限公司 All rights reserved
 * ==============================================================
 */
package com.sendyago.util.spring;

import com.sendyago.util.context.CONFIG;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Spring过滤器类
 *
 * @author $Author: Rick$
 * @version $Revision: 1.0 $Date: 2016/4/25 13:14:14 $
 */
@Repository
public class SystemInterceptor  implements HandlerInterceptor  {

	
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * SPRING MVC拦截器
	 * 用于将前台传递的所有参数进行封装
	 */
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		
//		/* 获取REQUEST中所有参数，并重新放入MAP中，controller层直接获取使用*/
//		Map map = request.getParameterMap();
//		Map<String, String> valMap = new HashMap();
//		Set key = map.keySet();
//		/* 循环request的MAP */
//        for(Object aaa: key.toArray()){
//        	String parakey = aaa.toString();
//        	String paravalue = ((String[])map.get(aaa))[0];
//        	valMap.put(parakey, paravalue);
//        }
//
//        /* 首次进入一些查询页面时，需要初始化分页对象的内容 */
//        if (valMap.get("currentPage")==null || "".equals(valMap.get("currentPage"))) {
//        	valMap.put("currentPage", "1");
//        }
//        if (valMap.get("pageSize")==null || "".equals(valMap.get("pageSize"))) {
//        	valMap.put("pageSize", "10");
//        }
//        /* 将参数MAP存回request，controller层获取即可  */
//        request.setAttribute(CONFIG.VALUE_MAP, valMap);
//		return true;

		HttpSession session = request.getSession();
		if(request.getParameter("shiro_role_id") != null && request.getParameter("shiro_menu_id") != null && request.getParameter("shiro_user_id") != null){
			session.removeAttribute("shiro_role_id");
			session.removeAttribute("shiro_menu_id");
			session.removeAttribute("shiro_user_id");
			session.setAttribute("shiro_role_id", request.getParameter("shiro_role_id"));
			session.setAttribute("shiro_menu_id", request.getParameter("shiro_menu_id"));
			session.setAttribute("shiro_user_id", request.getParameter("shiro_user_id"));
		}
		return true;
	}

}

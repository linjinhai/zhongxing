package com.sendyago.util.spring;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sendyago.system.service.LogService;
import com.sendyago.util.common.Convert;

/**
 * ================================================== 
 * 拦截器 - 记录日志
 * --------------------------------------------------
 * @author $Author: ZGX$ 
 * --------------------------------------------------
 * @version $Revision: 1.0 $Date: 2016/5/18 9:09:20$
 * ==================================================
 */
public class LogIntercepter implements HandlerInterceptor {

	@Autowired
	private LogService logService;

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		if (arg0.getParameter("flag") != null) {
			HttpSession session = arg0.getSession();
			String log_id = Convert.uuidConvert();
			String user_id = session.getAttribute("shiro_user_id") == null ? "":session.getAttribute("shiro_user_id").toString();
			String menu_id = session.getAttribute("shiro_menu_id") == null ? "":session.getAttribute("shiro_menu_id").toString();
			String button_id = "";
			if (arg0.getParameter("flag").equals("insert")) {
				button_id = "1";
			} else if (arg0.getParameter("flag").equals("update")) {
				button_id = "2";
			} else if (arg0.getParameter("flag").equals("delete")) {
				button_id = "3";
			} else {
				button_id = "8";
			}
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("log_id", log_id);
			map.put("user_id", user_id);
			map.put("menu_id", menu_id);
			map.put("button_id", button_id);
			map.put("ip", null);
			logService.logInsert(map);
		}
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		return true;
	}

}

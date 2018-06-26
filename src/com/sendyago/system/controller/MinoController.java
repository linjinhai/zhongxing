/* ==============================================================
 * $ID: LoginController.java, v1.3.1 2016/6/3 17:24:12 Rick Exp $
 * created: [2016-04-25 13:20:12] by Rick
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

import com.sendyago.essence.service.EssenceService;
import com.sendyago.main.service.MainService;
import com.sendyago.system.service.LocalIpService;
import com.sendyago.system.service.LogService;
import com.sendyago.system.service.LoginService;
import com.sendyago.util.common.CookieUtil;
import com.sendyago.util.common.LogWSClinet;
import com.sendyago.util.common.MD5Util;
import com.sendyago.util.context.CONFIG;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

/**
 * 系统登录控制类
 * 需校验登录信息的正确性,即用户名是否存在是否正确,密码是否正确等
 *
 * @author $Author: Rick$
 * @version $Revision: 1.0 $Date: 2016/4/25 13:20:12 $Description 类创建 &
 * @version $Revision: 1.1 $Date: 2016/4/28 13:10:12 $
 * @version $Revision: 1.2 $Date: 2016/5/6 16:40:12 $Description 创建logout()方法 &
 * @version $Revision: 1.3 $Date: 2016/5/17 17:12:12 $Description 创建 logon()方法 &
 * @version $Revision: 1.3.1 $Date: 2016/6/3 17:24:12 $Description 登录保存日志 &
 */
@Controller
@RequestMapping("/mioncontroller")
@Scope("prototype")
public class MinoController {

	@Autowired
	private EssenceService es;
	
	
	@RequestMapping(value = "getPartByTypeid")
	public void getPartByTypeid(HttpServletRequest req, HttpServletResponse rep, @RequestParam LinkedHashMap<String, Object> params) {
		try {
			rep.getWriter().println(JSONArray.fromObject(   es.docaplist("select * from MONITOR_SENSOR_PART t where type_id = (select type_id from monitor_sensor_section where section_id="+req.getParameter("sid")+")")).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "getPartByTypeid2")
	public void getPartByTypeid2(HttpServletRequest req, HttpServletResponse rep, @RequestParam LinkedHashMap<String, Object> params) {
		try {
			es.update(params, "SYSTEM_SENSOR_INSERT");
			rep.getWriter().println(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

	/**
	 * 根据截面id获取传感器截面图
	 * @param req
	 * @param rep
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "getSectionPath")
	public void getSectionPath(HttpServletRequest req, HttpServletResponse rep, @RequestParam LinkedHashMap<String, Object> params) {
		try {
			String sql = "";
			String sid = req.getParameter("sid");
			sql = sid.equals("-1") ? "select * from monitor_sensor_section where type_id=-1" : "select * from monitor_sensor_section where section_id=" + req.getParameter("sid");
			List list = es.docaplist(sql);
			JSONArray js = JSONArray.fromObject(list);
			rep.getWriter().print(js.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 根据截面id 获取该截面下所有传感器
	 * @param req
	 * @param rep
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "getSectionAllSensor")
	public void getSectionAllSensor(HttpServletRequest req, HttpServletResponse rep, @RequestParam LinkedHashMap<String, Object> params) {
		try {
			String sql = "";
			String sid = req.getParameter("sid");
			if (sid.equals("-1")) {
				sql = "select q.*,w.part_img,e.mon_p_top,e.mon_p_left,e.mon_v_top,e.mon_v_left,    e.main_p_top,e.main_p_left,e.main_v_top,e.main_v_left from monitor_sensor q left join monitor_sensor_part w on q.part_id=w.part_id left join monitor_sensor_postion e on e.sensor_id=q.sensor_id  order by q.part_id";
			} else {
				sql = "select q.*,w.part_img,e.mon_p_top,e.mon_p_left,e.mon_v_top,e.mon_v_left,    e.main_p_top,e.main_p_left,e.main_v_top,e.main_v_left from monitor_sensor q left join monitor_sensor_part w on q.part_id=w.part_id left join monitor_sensor_postion e on e.sensor_id=q.sensor_id where q.section_id=" + req.getParameter("sid") + " order by q.part_id";
			}
			List list = es.docaplist(sql);
			JSONArray js = JSONArray.fromObject(list);
			rep.getWriter().print(js.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 保存当前 对象  位置信息
	 * @param req
	 * @param rep
	 * @param params
	 * @return
	 * //i:  1，初始化 2，删除 3.移动位置
	   //t:  1,传感器  2，数值
	 */
	@RequestMapping(value = "savepost")
	public void savepost(HttpServletRequest req, HttpServletResponse rep, @RequestParam LinkedHashMap<String, Object> params) {
		String sid = req.getParameter("sid");
		String i = req.getParameter("i");
		String t = req.getParameter("t");
		String hidf = req.getParameter("hidf");

		String top = req.getParameter("top");
		String left = req.getParameter("left");

		String sql = "";
		String gh = hidf.equals("-1") ? "main" : "mon";
		try {
			List list = es.docaplist("select * from MONITOR_SENSOR_POSTION where sensor_id=" + sid);
			if (list.size() == 0) {
				es.docap("insert into MONITOR_SENSOR_POSTION (sensor_id) values(" + sid + ")");
			}

			if (i.equals("1")) {
				if (t.equals("1")) {
					sql = "update MONITOR_SENSOR_POSTION set "+gh+"_P_TOP=0,"+gh+"_P_LEFT=0 where sensor_id=" + sid;
				} else if (t.equals("2")) {
					sql = "update MONITOR_SENSOR_POSTION set "+gh+"_v_TOP=0,"+gh+"_v_LEFT=0 where sensor_id=" + sid;
				}
			} else if (i.equals("2")) {
				if (t.equals("1")) {
					sql = "update MONITOR_SENSOR_POSTION set "+gh+"_P_TOP=null,"+gh+"_P_LEFT=null where sensor_id=" + sid;
				} else if (t.equals("2")) {
					sql = "update MONITOR_SENSOR_POSTION set "+gh+"_v_TOP=null,"+gh+"_v_LEFT=null where sensor_id=" + sid;
				}
			} else if (i.equals("3")) {
				if (t.equals("1")) {
					sql = "update MONITOR_SENSOR_POSTION set "+gh+"_P_TOP=" + top + ","+gh+"_P_LEFT=" + left + " where sensor_id=" + sid;
				} else if (t.equals("2")) {
					sql = "update MONITOR_SENSOR_POSTION set "+gh+"_v_TOP=" + top + ","+gh+"_v_LEFT=" + left + " where sensor_id=" + sid;
				}
			}
			es.docap(sql);
			List list2 = new ArrayList<>();
			JSONArray js = JSONArray.fromObject(list2);
			rep.getWriter().print(js.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

} // end class

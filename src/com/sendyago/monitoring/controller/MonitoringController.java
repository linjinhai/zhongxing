/* ==============================================================
 * $ID: MonitoringController.java, v1.1 2016/6/21 13:10:12 Fsd Exp $
 * created: [2016-04-25 13:20:12] by Fsd
 * ==============================================================
 * 健康监测系统模块化公共信息
 *
 * 健康监测系统模块化即将健康监测系统各功能模块进行项目化形式进行开发
 * 将各功能模块划分为各个WEB项目
 * ==============================================================
 * Copyright (c) 哈尔滨工大云帆智慧信息技术有限公司 All rights reserved
 * ==============================================================
 */
package com.sendyago.monitoring.controller;

import com.sendyago.monitoring.service.MonitoringService;
import net.sf.json.JSONArray;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.proxy.dwr.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 实时监测控制类
 * 
 * monitoringType():根据传感器类型id 获取基础数据 并跳转至实时监测页面
 * getTop50（）：获取50初始化HIGHCHARTS的数据点
 * getWaringVal（）：获取传感器预警值
 * updateNow（）：PUSH更新数据点
 * getDataForLast（）：获取snesor_id过去1,6,12小时的曲线数据
 * getTodaySs（）：获取snesor_id今天的实时平均值
 * getWarningData（）：根据type_id获取昨天和今天的报警数据信息
 * cxlsqx（）：根据sensor_id获取历史数据查询
 * cxlsqxPage（）：根据sensor_id获取历史数据查询 分页
 * dscx（）：根据type_id 定时查询
 * cxlsqxDc（）：根据sensor_id获取 导出 历史数据查询
 *
 * @author $Author: FSD$
 * @version $Revision: 1.0 $Date: 2016/6/21 13:20:12$
 */
@Controller
@RequestMapping("/monitoringController")
@Scope("prototype")
public class MonitoringController { 
	WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
	ServletContext servletContext = webApplicationContext.getServletContext();
	Date date = new Date();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private MonitoringService ms;
	
	/**
	 * 报警信息弹出窗口信息查询
	 *
	 * @param modelMap
	 * @param req
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "tcbjxx")
	public void tcbjxx(HttpServletResponse rep, ModelMap modelMap, HttpServletRequest req, @RequestParam LinkedHashMap<String, Object> params) throws Exception {
		List list = ms.queryTcbjxx(req.getParameter("typeid"), req.getParameter("i"), req.getParameter("pageno") == null ? "1" : req.getParameter("pageno"));
		JSONArray js = JSONArray.fromObject(list);
		rep.getWriter().print(js.toString());
	}

	/**
	 * 根据传感器类型id 获取基础数据 并跳转至实时监测页面
	 * @param modelMap
	 * @param rep
	 * @param req
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "monitoringType")
	public String monitoringType(ModelMap modelMap, HttpServletResponse rep, HttpServletRequest req, @RequestParam LinkedHashMap<String, Object> params) throws Exception {
		rep.setHeader("Access-Control-Allow-Origin", "*");
		//传感器类型id
		String type_id = params.get("sensorType") + "";
		//获取此类型id所有截面图
		List list_section = ms.queryForSectionList(params);
		//获取所有传感器
		List list_sensor = ms.queryForSensorList(params);
		//获取此类型id今天报警
		//List list_warning_today = ms.queryForWarningList(params,1);
		//获取此类型id昨天报警
		//List list_warning_yesterday = ms.queryForWarningList(params,2);
		//获取此类型id 报警次数
		List list_warning_count = ms.getWaringCountByTypeId(params);
		//获取定时查询 时间间隔
		int[] su = new int[list_sensor.size()];
		for (int i = 0; i < list_sensor.size(); i++) {
			Map map = (Map) list_sensor.get(i);
			su[i] = Integer.parseInt(map.get("PART_SECOND") + "");
		}
		Arrays.sort(su);

		//SetAttribute
		if(su.length>0){
			req.setAttribute("dxcxjg", su[0]);
		}else{
			req.setAttribute("dxcxjg", 60);
		}
		
		req.setAttribute("type_id", type_id);
		req.setAttribute("type_name", ms.querySensorTypeName(type_id));
		req.setAttribute("list_section", list_section);
		req.setAttribute("list_sensor", list_sensor);
		req.setAttribute("warning_count", (Map) list_warning_count.get(0));
		//req.setAttribute("list_warning_today", list_warning_today);
		//req.setAttribute("list_warning_yesterday", list_warning_yesterday);
		req.setAttribute("sensor_section_path", servletContext.getInitParameter("sensor_section_path"));
		return "monitoring/monitoringType";
	}

	/**
	 * 获取50初始化HIGHCHARTS的数据点
	 *
	 * @param modelMap
	 * @param req
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getTop50")
	public void getTop50(HttpServletResponse rep, ModelMap modelMap, HttpServletRequest req, @RequestParam LinkedHashMap<String, Object> params) throws Exception {
		//获取图表历史数据
		List list = ms.queryTop50(params);
		JSONArray js = JSONArray.fromObject(list);
		rep.getWriter().print(js.toString());
	}

	/**
	 * 获取传感器预警值
	 *
	 * @param modelMap
	 * @param req
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getWaringVal")
	public void getWaringVal(HttpServletResponse rep, ModelMap modelMap, HttpServletRequest req, @RequestParam LinkedHashMap<String, Object> params) throws Exception {
		//获取预警值
		List list = ms.queryWaringVal(params);
		JSONArray js = JSONArray.fromObject(list);
		rep.getWriter().print(js.toString());
	}

	/**
	 * PUSH更新数据点
	 */
	@RequestMapping(value = "updateNow")
	public void updateNow(HttpServletResponse rep, ModelMap modelMap, HttpServletRequest req, @RequestParam LinkedHashMap<String, Object> params) throws Exception {
		rep.setHeader("Access-Control-Allow-Origin", "*");
		tuisong("/monitoringController/monitoringType", req, rep);

		//doProperties(req);
	}

	public void tuisong(String url, HttpServletRequest request, HttpServletResponse rep) {
		try {

			ServletContext sc = request.getSession().getServletContext();

			rep.setHeader("Access-Control-Allow-Origin", "*");

			org.directwebremoting.ServerContext sctx = ServerContextFactory.get(sc);
			String page = "" + request.getContextPath() + url;
			System.out.println(page);
			Collection<ScriptSession> sessions = sctx.getScriptSessionsByPage(page);
			org.directwebremoting.proxy.dwr.Util utilAll = new Util(sessions);
			utilAll.addFunctionCall("callBack", request.getParameter("sensor_id"), request.getParameter("data"));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	public void tuisong(String url,HttpServletRequest request){
	    System.out.println(222);
	    javax.servlet.ServletContext sc = request.getSession().getServletContext();
	    org.directwebremoting.ServerContext sctx = ServerContextFactory.get(sc);
	    String page = "http://localhost:8080/loginPro/pages/main/home.jsp";
	    java.util.Collection<ScriptSession> sessions = sctx.getScriptSessionsByPage(page);
	    org.directwebremoting.proxy.dwr.Util utilAll = new Util(sessions);
	    utilAll.addFunctionCall("callBack", request.getParameter("sensor_id"), request.getParameter("data"));
	    System.out.println(333);
	}
	*/
	/*
	 * 保存接收数据到info.properties
	 */
	public void doProperties(HttpServletRequest req) {
		try {
			Properties prop = new Properties();
			FileInputStream fis = new FileInputStream(req.getRealPath("/pro/info.properties"));
			prop.load(fis);
			fis.close();
			prop.setProperty("sensor_" + req.getParameter("sensor_id"), req.getParameter("data") + "^" + df.format(date));
			FileOutputStream fos = new FileOutputStream(req.getRealPath("/pro/info.properties"));
			prop.store(fos, "Copyright (c) Boxcode Studio");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取snesor_id过去1,6,12小时的曲线数据
	 */
	@RequestMapping(value = "getDataForLast")
	public void getDataForLast(HttpServletResponse rep, ModelMap modelMap, HttpServletRequest req, @RequestParam LinkedHashMap<String, Object> params) throws Exception {
		List list = ms.getDataForLast(params);
		JSONArray js = JSONArray.fromObject(list);
		rep.getWriter().print(js.toString());
	}

	/**
	 * 获取snesor_id今天的实时平均值
	 */
	@RequestMapping(value = "getTodaySs")
	public void getTodaySs(HttpServletResponse rep, ModelMap modelMap, HttpServletRequest req, @RequestParam LinkedHashMap<String, Object> params) throws Exception {
		List list = ms.getTodaySs(params);
		JSONArray js = JSONArray.fromObject(list);
		rep.getWriter().print(js.toString());
	}

	/**
	 * 获取info.properties
	 */
	@RequestMapping(value = "getProData")
	public void getProData(HttpServletResponse rep, ModelMap modelMap, HttpServletRequest req, @RequestParam LinkedHashMap<String, Object> params) throws Exception {
		try {
			String uri = servletContext.getInitParameter("infPro");
			Properties prop = new Properties();// 属性集合对象
			FileInputStream fis = new FileInputStream(uri);
			prop.load(fis);// 将属性文件流装载到Properties对象中
			fis.close();// 关闭流

			Map map = new HashMap();
			Enumeration enum1 = prop.propertyNames();// 得到配置文件的名字
			while (enum1.hasMoreElements()) {
				String strKey = (String) enum1.nextElement();
				String strValue = prop.getProperty(strKey);
				map.put(strKey, strValue);
			}
			JSONArray js = JSONArray.fromObject(map);
			rep.getWriter().print(js.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 根据type_id获取昨天和今天的报警数据信息
	 */
	@RequestMapping(value = "getWarningData")
	public void getWarningData(HttpServletResponse rep, ModelMap modelMap, HttpServletRequest req, @RequestParam LinkedHashMap<String, Object> params) throws Exception {
		List list = ms.queryForWarningList(params);
		JSONArray js = JSONArray.fromObject(list);
		rep.getWriter().print(js.toString());
	}

	/**
	 * 根据sensor_id获取历史数据查询
	 */
	@RequestMapping(value = "cxlsqx")
	public void cxlsqx(HttpServletResponse rep, ModelMap modelMap, HttpServletRequest req, @RequestParam LinkedHashMap<String, Object> params) throws Exception {
		List list = ms.cxlsqx(params);
		JSONArray js = JSONArray.fromObject(list);
		rep.getWriter().print(js.toString());
	}

	/**
	 * 根据sensor_id获取历史数据查询 分页
	 */
	@RequestMapping(value = "cxlsqxPage")
	public void cxlsqxPage(HttpServletResponse rep, ModelMap modelMap, HttpServletRequest req, @RequestParam LinkedHashMap<String, Object> params) throws Exception {
		List list = ms.cxlsqx(params);
		JSONArray js = JSONArray.fromObject(list);
		rep.getWriter().print(js.toString());
	}

	/**
	 * 根据type_id 定时查询
	 */
	@RequestMapping(value = "dscx")
	public void dscx(HttpServletResponse rep, ModelMap modelMap, HttpServletRequest req, @RequestParam LinkedHashMap<String, Object> params) throws Exception {
		List list = ms.dxcx(params);
		JSONArray js = JSONArray.fromObject(list);
		rep.getWriter().print(js.toString());
	}

	/**
	 * 根据type_id 定时查询
	 */
	@RequestMapping(value = "getAllSensorList")
	public void getAllSensorList(HttpServletResponse rep, ModelMap modelMap, HttpServletRequest req, @RequestParam LinkedHashMap<String, Object> params) throws Exception {
		List list = ms.getAllSensorList();
		JSONArray js = JSONArray.fromObject(list);
		rep.getWriter().print(js.toString());  
	}

	/**
	 * 根据sensor_id获取 导出 历史数据查询
	 */
	@RequestMapping(value = "cxlsqxDc")
	public void cxlsqxDc(HttpServletResponse rep, ModelMap modelMap, HttpServletRequest req, @RequestParam LinkedHashMap<String, Object> params) throws Exception {
		List list = ms.cxlsqx(params);

		HSSFRow row = null;
		HSSFCell cell = null;
		String cgqdw = params.get("cgqdw") + "";
		HSSFWorkbook wb = new HSSFWorkbook();
		int total = 0;
		int pageSize = 50000;
		total = (list.size() % pageSize == 0) ? (list.size() / pageSize) : (list.size() / pageSize + 1);
		for (int i = 1; i <= total; i++) {
			HSSFSheet sheet = wb.createSheet("" + params.get("cxcgqcode") + "-" + i);
			int k = 0;
			for (int j = (i - 1) * pageSize; j < i * pageSize; j++) {
				if (j < list.size()) {
					Map map = (Map) list.get(j);
					row = sheet.createRow((int) k++);
					cell = row.createCell(0);
					cell.setCellValue(map.get("T") + "");
					cell = row.createCell(1);
					cell.setCellValue(map.get("V") + cgqdw);
				}
			}
		}

		try {
			rep.reset();
			rep.setContentType("application/vnd.ms-excel;charset=utf-8");
			rep.addHeader("Content-Disposition", "attachment;filename=" + new String(("数据查询导出" + params.get("cxcgqcode") + "" + ".xls").getBytes("GB2312"), "ISO-8859-1"));
			OutputStream ouputStream = rep.getOutputStream();
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

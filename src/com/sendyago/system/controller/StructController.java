/* ==============================================================
 * $ID: StructController.java, v1.0 2016/7/27 13:58:32 chengqiang Exp $
 * created: [2016-07-27 13:52:32] by chengqiang
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.sendyago.system.service.StructService;
import com.sendyago.util.common.Pager;
import com.sendyago.util.common.Utils;
/**
 * 评定项管理控制类&&构件管理控制类
 * 
 *
 * @author $Author: chengqiang$
 * @version $Revision: 1.0 $Date: 2016/7/25 13:30:12 $Description 类创建 &
 */
@Controller
@RequestMapping("struct")
public class StructController extends BaseController{
	@Autowired
	private StructService structservice;
	
	/**
	 * 评定项  列表
	 * @param pager
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public String queryList(Pager pager , String searchtwo, String searchone,String judge) throws Exception {
		pager = structservice.custompager(request, pager, "BRIDGE_STRUCT_LIST",searchtwo,searchone);
		request.setAttribute("pager", pager);
		request.setAttribute("searchtwo", searchtwo);
		request.setAttribute("searchone", searchone);
		//获得评定系统
		List<T> datalist = structservice.customlist("BRIDGE_PARAM_DATA_LIST");
		request.setAttribute("datalist", datalist);
		request.setAttribute("judge", judge);
		return "struct/list";
	}
	
	/**
	 * 评定项  添加页
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("insertPage")
	public String insertPage() throws Exception {
		List<T> datalist = structservice.customlist("BRIDGE_PARAM_DATA_LIST");
		//获得评定系统
		request.setAttribute("datalist", datalist);
		return "struct/edit";
	}

	/**
	 * 评定项  修改页
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updatePage")
	public String updatePage(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("id", params.get("id"));
		Map<String, Object> object = structservice.object(map, "BRIDGE_STRUCT_INFO");
		request.setAttribute("object", object);
		//获得评定系统
		List<T> datalist = structservice.customlist("BRIDGE_PARAM_DATA_LIST");
		request.setAttribute("datalist", datalist);
		if(params.get("tag") != null){
			request.setAttribute("tag", params.get("tag"));
		}
		if(params.get("zz") != null){
			request.setAttribute("zz", params.get("zz"));
		} 
		return "struct/edit";
	}

	/**
	 * 评定项增删改
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "update")
	public String update(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		Object tag = params.get("tag");
		String judge=structservice.update(params);
		if(tag != null){
			return "redirect:updatePage?tag=1&zz=zz&";
		} else{
			return "redirect:list?judge="+judge;
		}
		
	}

	/**
	 * 级联查询
	 * @param request
	 * @param response
	 * @param data_pid
	 */
	@RequestMapping(value = "getData")
	public void getPart(HttpServletRequest request, HttpServletResponse response, String data_pid) {
	    try {
	        LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
	        params.put("data_pid", data_pid);
	        List list_data = structservice.queryDataList(params);
	
	        response.getWriter().print(Utils.jsonListStr(list_data));
	    } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	
	}

	/**
	 * 构件  列表
	 * @param pager
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("componentlist")
	public String querycomponentlist(Pager pager , String searchtwo, String searchone) throws Exception {
		pager = structservice.custompager(request, pager, "BRIDGE_STRUCT_CPLIST",searchtwo,searchone);
		request.setAttribute("pager", pager);
		request.setAttribute("searchtwo", searchtwo);
		request.setAttribute("searchone", searchone);
		//获得评定系统
		List<T> datalist = structservice.customlist("BRIDGE_PARAM_DATA_LIST");
		request.setAttribute("datalist", datalist);
		return "struct/componentlist";
	}
	
	/**
	 * 构件添加页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("insertcpPage")
	public String insertcpPage() throws Exception {
		List<T> datalist = structservice.customlist("BRIDGE_PARAM_DATA_LIST");
		//获得评定系统
		request.setAttribute("datalist", datalist);
		return "struct/componentedit";
	}
	/**
	 * 构件 修改页
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updatecpPage")
	public String updatecpPage(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("id", params.get("id"));
		Map<String, Object> object = structservice.object(map, "BRIDGE_STRUCT_INFO");
		request.setAttribute("object", object);
		//获得评定系统
		List<T> datalist = structservice.customlist("BRIDGE_PARAM_DATA_LIST");
		request.setAttribute("datalist", datalist);
		if(params.get("tag") != null){
			request.setAttribute("tag", params.get("tag"));
		}
		if(params.get("zz") != null){
			request.setAttribute("zz", params.get("zz"));
		} 
		return "struct/componentedit";
	}

	/**
	 * 构件删改
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "cpupdate")
	public String cpupdate(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		Object tag = params.get("tag");
		structservice.cpupdate(params);
		if(tag != null){
			return "redirect:updatecpPage?tag=1&zz=zz&";
		} else{
			return "redirect:componentlist";
		}
		
	}

}

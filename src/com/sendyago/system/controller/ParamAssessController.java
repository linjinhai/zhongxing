/* ==============================================================
 * $ID: ParamAssessController.java, v1.0 2016/7/27 13:32:32 chengqiang Exp $
 * created: [2016-07-27 13:32:32] by chengqiang
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

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sendyago.system.service.ParamAssessService;
import com.sendyago.system.service.StructService;
import com.sendyago.util.common.Pager;
/**
 * 评定标准控制类
 * 
 *
 * @author $Author: chengqiang$
 * @version $Revision: 1.0 $Date: 2016/7/25 13:20:12 $Description 类创建 &
 */
@Controller
@RequestMapping("paramassess")
public class ParamAssessController extends BaseController{
	@Autowired
	private ParamAssessService paramassessService;
	@Autowired
	private StructService structService;
	/**
	 * 评定参数  列表
	 * @param pager
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public String queryList(Pager pager , String search, String searchone) throws Exception {
		pager = paramassessService.pager(request, pager, "BRIDGE_PARAM_ASSESS_LIST");
		request.setAttribute("pager", pager);
		request.setAttribute("search", search);
		//获得评定项
		List<T> structlist = structService.customlist("BRIDGE_STRUCT_LIST");
		request.setAttribute("structlist", structlist);
		return "paramassess/list";
	}
	/**
	 * 评定参数  添加页
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("insertPage")
	public String insertPage() throws Exception {
		//获得评定项
		List<T> structlist = structService.customlist("BRIDGE_STRUCT_LIST");
		request.setAttribute("structlist", structlist);
		return "paramassess/edit";
	}
	/**
	 * 评定参数  修改页
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updatePage")
	public String updatePage(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("id", params.get("id"));
		Map<String, Object> object = paramassessService.object(map, "BRIDGE_PARAM_ASSESS_INFO");
		request.setAttribute("object", object);
		//获得评定项
		List<T> structlist = structService.customlist("BRIDGE_STRUCT_LIST");
		request.setAttribute("structlist", structlist);
		if(params.get("tag") != null){
			request.setAttribute("tag", params.get("tag"));
		}
		if(params.get("zz") != null){
			request.setAttribute("zz", params.get("zz"));
		} 
		return "paramassess/edit";
	}

	/**
	 * 评定参数增删改
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "update")
	public String update(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		Object tag = params.get("tag");
		paramassessService.update(params);
		if(tag != null){
			return "redirect:updatePage?tag=1&zz=zz";
		} else{
			return "redirect:list";
		}
		
	}
	
	
}

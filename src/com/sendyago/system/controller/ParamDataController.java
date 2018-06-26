/* ==============================================================
 * $ID: ParamDataController.java, v1.0 2016/7/27 13:32:32 chengqiang Exp $
 * created: [2016-07-26 13:32:32] by chengqiang
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

import com.sendyago.system.service.ParamDataService;
import com.sendyago.util.common.Pager;
/**
 * 评定系统管理控制类
 * 
 *
 * @author $Author: chengqiang$
 * @version $Revision: 1.0 $Date: 2016/7/25 13:20:12 $Description 类创建 &
 */
@Controller
@RequestMapping("paramdata")
public class ParamDataController extends BaseController{
	@Autowired
	private ParamDataService paramdataService;
	/**
	 * 评定系统  列表
	 * @param pager
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public String queryList(Pager pager , String searchtwo, String searchone, String judge) throws Exception {
		pager = paramdataService.custompager(request, pager, "BRIDGE_PARAM_DATA_LIST",searchtwo,searchone);
		request.setAttribute("pager", pager);
		request.setAttribute("searchtwo", searchtwo);
		request.setAttribute("searchone", searchone);
		request.setAttribute("judge", judge);
		return "paramdata/list";
	}
	/**
	 * 评定系统  添加页
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("insertPage")
	public String insertPage() throws Exception {
		return "paramdata/edit";
	}
	/**
	 * 评定系统  修改页
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updatePage")
	public String updatePage(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("id", params.get("id"));
		Map<String, Object> object = paramdataService.object(map, "BRIDGE_PARAM_DATA_INFO");
		request.setAttribute("object", object);
		if(params.get("tag") != null){
			request.setAttribute("tag", params.get("tag"));
		}
		if(params.get("zz") != null){
			request.setAttribute("zz", params.get("zz"));
		} 
		return "paramdata/edit";
	}

	/**
	 * 评定系统增删改
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "update")
	public String update(@RequestParam LinkedHashMap<String, Object> params) throws Exception {
		Object tag = params.get("tag");
		String judge=paramdataService.update(params);
		if(tag != null){
			return "redirect:updatePage?tag=1&zz=zz";
		} else{
			return "redirect:list?judge="+judge;
		}
		
	}
	
	
}

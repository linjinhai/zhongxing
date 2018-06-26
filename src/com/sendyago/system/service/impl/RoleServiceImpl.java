/* ==============================================================
 * $ID: RoleServiceImpl.java, v1.4 2016/4/28 14:57:18 zgx Exp $
 * created: [2016-04-25 13:33:18] by zgx
 * ==============================================================
 * 健康监测系统模块化公共信息
 *
 * 健康监测系统模块化即将健康监测系统各功能模块进行项目化形式进行开发
 * 将各功能模块划分为各个WEB项目
 * ==============================================================
 * Copyright (c) 哈尔滨工大云帆智慧信息技术有限公司 All rights reserved
 * ==============================================================
 */
package com.sendyago.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sendyago.system.service.RoleService;
import com.sendyago.util.common.Convert;

/**
 * ================================================== 
 * Service业务层实现类 - 系统管理 - 角色管理
 * --------------------------------------------------
 * @author $Author: ZGX$ 
 * --------------------------------------------------
 * @version $Revision: 1.0 $Date: 2016/5/10 9:09:20$
 * ==================================================
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {
	
	@Override
	public void update(LinkedHashMap<String, Object> params) throws Exception {
		String flag = params.get("flag").toString();
		params.remove("flag");
		switch (flag) {
		case "insert":
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("role_id", Convert.uuidConvert());
			map.put("role_name", params.get("role_name"));
			map.put("role_memo", params.get("role_memo"));
			super.update(map, "SYSTEM_ROLE_INSERT");
			break;
		case "update":
			super.update(params, "SYSTEM_ROLE_UPDATE");
			break;
		case "delete":
			super.update(params, "SYSTEM_ROLE_DELETE");
			break;
		}
	}
	
	@Override
	public int buttonSize(List<Map<String, String>> menu_list) throws Exception {
		int max_size = 0;
		for(Map<String, String> menu_map : menu_list){
			LinkedHashMap<String, Object> param_map = new LinkedHashMap<String, Object>();
			param_map.put("menu_id", menu_map.get("MENU_ID"));
			List<Map<String, String>> menu_button_list = this.list(param_map, "SYSTEM_MENU_BUTTON_LIST");
			max_size = menu_button_list.size() > max_size ? menu_button_list.size() : max_size;
		}
		return max_size;
	}
	
	@Override
	public List<Map<String, String>> menuButtons(List<Map<String, String>> menu_list) throws Exception {
		//获取菜单拥有按钮权限的最大值，用于填充无按钮权限的菜单，目的是使页面的表格td对齐
		int max_size = this.buttonSize(menu_list);
		//按钮权限
		List<Map<String, String>> menu_button_list_total = new ArrayList<Map<String, String>>();
		for(Map<String, String> menu_map : menu_list){
			LinkedHashMap<String, Object> param_map = new LinkedHashMap<String, Object>();
			param_map.put("menu_id", menu_map.get("MENU_ID"));
			List<Map<String, String>> menu_button_list = this.list(param_map, "SYSTEM_MENU_BUTTON_LIST");
			//如果菜单没有对应的按钮，则按照max_size赋空值
			if(menu_button_list.size() <= 0 || menu_button_list == null){
				for(int i = 0; i < max_size; i++){
					Map<String, String> menu_button_map = new HashMap<String, String>();
					menu_button_map.put("BUTTON_ID", "");
					menu_button_map.put("MENU_ID", menu_map.get("MENU_ID"));
					menu_button_map.put("BUTTON_TYPE_ID", "");
					menu_button_list_total.add(menu_button_map);
				}
			//如果菜单对应的按钮数量小于max_size，赋空值填补数量
			} else if(menu_button_list.size() < max_size){
				for(Map<String, String> menu_button_map : menu_button_list){
					menu_button_list_total.add(menu_button_map);
				}
				for(int i = 0; i < (max_size - menu_button_list.size()); i++){
					Map<String, String> menu_button_map = new HashMap<String, String>();
					menu_button_map.put("BUTTON_ID", "");
					menu_button_map.put("MENU_ID", menu_map.get("MENU_ID"));
					menu_button_map.put("BUTTON_TYPE_ID", "");
					menu_button_list_total.add(menu_button_map);
				}
			} else{
				for(Map<String, String> menu_button_map : menu_button_list){
					menu_button_list_total.add(menu_button_map);
				}
			}
		}
		return menu_button_list_total;
	}
	
	@Override
	public void permission_menu(String role_id, String...menu_ids) throws Exception {
		LinkedHashMap<String, Object> map_delete = new LinkedHashMap<String, Object>();
		map_delete.put("role_id", role_id);
		update(map_delete, "SYSTEM_ROLE_MENU_DELETE");
		for(String menu_id : menu_ids){
			LinkedHashMap<String, Object> map_update = new LinkedHashMap<String, Object>();
			map_update.put("role_id", role_id);
			map_update.put("menu_id", menu_id);
			super.update(map_update, "SYSTEM_ROLE_MENU_INSERT");
		}
	}
	
	@Override
	public void permission_button(String...button_ids) throws Exception {
		if(button_ids.length > 0){
			LinkedHashMap<String, Object> map_delete = new LinkedHashMap<String, Object>();
			map_delete.put("role_id", button_ids[0].split(",")[0]);
			update(map_delete, "SYSTEM_ROLE_BUTTON_DELETE");
			
			for(String roles_menus_buttons : button_ids){
				String[] role_menu_button = roles_menus_buttons.split(",");
				LinkedHashMap<String, Object> map_update = new LinkedHashMap<String, Object>();
				map_update.put("role_id", role_menu_button[0]);
				map_update.put("menu_id", role_menu_button[1]);
				map_update.put("button_id", role_menu_button[2]);
				super.update(map_update, "SYSTEM_ROLE_BUTTON_INSERT");
			}
		}
	}
}

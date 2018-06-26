/* ==============================================================
 * $ID: MenuServiceImpl.java, v1.4 2016/4/28 14:57:18 zgx Exp $
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sendyago.system.service.MenuService;
import com.sendyago.util.common.Convert;

/**
 * ================================================== 
 * Service业务层实现类 - 系统管理 - 菜单管理
 * --------------------------------------------------
 * @author $Author: ZGX$ 
 * --------------------------------------------------
 * @version $Revision: 1.0 $Date: 2016/5/10 9:09:20$
 * ==================================================
 */
@Service("menuService")
public class MenuServiceImpl extends BaseServiceImpl implements MenuService {
	
	@Override
	public void update(LinkedHashMap<String, Object> params, String...buttonTypeIds) throws Exception {
		String flag = params.get("flag").toString();
		params.remove("flag");
		
		switch (flag) {
		case "insert":
			LinkedHashMap<String, Object> id_map = new LinkedHashMap<String, Object>();
			id_map.put("menu_pid", params.get("menu_pid"));
			id_map.put("is_child", params.get("is_child"));
			Map<String, Object> object = super.object(id_map, "SYSTEM_MENU_MAXID");
			
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			String id = "";
			if(object.get("MAXID") == null){
				id = (Integer.parseInt(params.get("menu_pid").toString()) + 1000000) + "";
			} else{
				id = object.get("MAXID").toString();
			}
			map.put("menu_id", id);
			//如果是父菜单，pid为空，则给pid赋menu_id值
			map.put("menu_pid", "".equals(params.get("menu_pid").toString()) ? id : params.get("menu_pid"));
			map.put("menu_name", params.get("menu_name"));
			map.put("menu_path", params.get("menu_path"));
			map.put("is_child", params.get("is_child"));
			map.put("menu_img", params.get("menu_img"));
			super.update(map, "SYSTEM_MENU_INSERT");
			
			//添加system_menu_button
			if(buttonTypeIds != null){
				for(String buttonTypeId : buttonTypeIds){
					LinkedHashMap<String, Object> button_map = new LinkedHashMap<String, Object>();
					button_map.put("button_id", Convert.uuidConvert());
					button_map.put("menu_id", id);
					button_map.put("button_type_id", buttonTypeId);
					super.update(button_map, "SYSTEM_MENU_BUTTON_INSERT");
				}
			}
			
			
			break;
		case "update":
			LinkedHashMap<String, Object> map_u = new LinkedHashMap<String, Object>();
			map_u.put("menu_id", params.get("menu_id"));
			map_u.put("menu_pid", "1".equals(params.get("is_child").toString()) ? params.get("menu_pid") : params.get("menu_id"));
			map_u.put("menu_name", params.get("menu_name"));
			map_u.put("menu_path", params.get("menu_path"));
			map_u.put("is_child", params.get("is_child"));
			map_u.put("menu_img", params.get("menu_img"));
			super.update(map_u, "SYSTEM_MENU_UPDATE");
			
			//修改system_menu_button，先删除menu_id的所有权限按钮数据
			if(buttonTypeIds != null){
				List<String> list_del = new ArrayList<String>();
				List<String> list_add = new ArrayList<String>();
				LinkedHashMap<String, Object> map_menu_button = new LinkedHashMap<String, Object>();
				map_menu_button.put("menu_id", params.get("menu_id"));
				List<Map<String, String>> menu_button_list = list(map_menu_button, "SYSTEM_MENU_BUTTON_LIST");
				if(menu_button_list != null && menu_button_list.size() > 0){
					//删除菜单对应的权限
					List<String> odd_button_type_id = new ArrayList<String>();
					for(Map<String, String> menu_button_map : menu_button_list){
						odd_button_type_id.add(menu_button_map.get("BUTTON_TYPE_ID"));
					}
					String button_type_id_str = "";
					for(int i = 0; i < buttonTypeIds.length; i++){
						button_type_id_str += buttonTypeIds[i];
					}
					for(Map<String, String> menu_button_map : menu_button_list){
						String button_type_id = menu_button_map.get("BUTTON_TYPE_ID");
						if(button_type_id_str.indexOf(button_type_id) < 0){
							list_del.add(button_type_id);
						}
					}
					//增加菜单对应的权限
					String button_type_id_str_add = "";
					for(Map<String, String> menu_button_map : menu_button_list){
						String button_type_id = menu_button_map.get("BUTTON_TYPE_ID");
						button_type_id_str_add += button_type_id;
					}
					for(String button_type_id_add : buttonTypeIds){
						if(button_type_id_str_add.indexOf(button_type_id_add) < 0){
							list_add.add(button_type_id_add);
						}
					}
					
					if(list_del != null && list_del.size() > 0){
						for(String buttonTypeId : list_del){
							LinkedHashMap<String, Object> button_map_d = new LinkedHashMap<String, Object>();
							button_map_d.put("menu_id", params.get("menu_id"));
							button_map_d.put("button_type_id", buttonTypeId);
							super.update(button_map_d, "SYSTEM_ROLE_MENU_BUTTON_DELETE");
						}
					}
					if(list_add != null && list_add.size() > 0){
						for(String buttonTypeId : list_add){
							LinkedHashMap<String, Object> button_map_u = new LinkedHashMap<String, Object>();
							button_map_u.put("button_id", Convert.uuidConvert());
							button_map_u.put("menu_id", params.get("menu_id"));
							button_map_u.put("button_type_id", buttonTypeId);
							super.update(button_map_u, "SYSTEM_MENU_BUTTON_INSERT");
						}
					}
				} else{
					for(String buttonTypeId : buttonTypeIds){
						LinkedHashMap<String, Object> button_map_u = new LinkedHashMap<String, Object>();
						button_map_u.put("button_id", Convert.uuidConvert());
						button_map_u.put("menu_id", params.get("menu_id"));
						button_map_u.put("button_type_id", buttonTypeId);
						super.update(button_map_u, "SYSTEM_MENU_BUTTON_INSERT");
					}
				}
			} else{
				LinkedHashMap<String, Object> button_map_d = new LinkedHashMap<String, Object>();
				button_map_d.put("menu_id", params.get("menu_id"));
				super.update(button_map_d, "SYSTEM_MENU_BUTTON_DELETE");
			}
			break;
		case "delete":
			super.update(params, "SYSTEM_MENU_DELETE");
			break;
		}
	}

	@Override
	public void buttonUpdate(LinkedHashMap<String, Object> params) throws Exception {
		String flag = params.get("flag").toString();
		params.remove("flag");
		
		switch (flag) {
		case "insert":
			super.update(params, "SYSTEM_BUTTON_INSERT");
			break;
		case "update":
			super.update(params, "SYSTEM_BUTTON_UPDATE");
			break;
		case "delete":
			super.update(params, "SYSTEM_BUTTON_DELETE");
			break;
		}
	}

}

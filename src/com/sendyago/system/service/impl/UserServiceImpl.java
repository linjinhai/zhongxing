/* ==============================================================
 * $ID: UserServiceImpl.java, v1.4 2016/4/28 14:57:18 zgx Exp $
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

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sendyago.system.service.UserService;
import com.sendyago.util.common.Convert;

/**
 * ================================================== 
 * Service业务层接口 - 系统管理 - 用户管理
 * --------------------------------------------------
 * @author $Author: ZGX$ 
 * --------------------------------------------------
 * @version $Revision: 1.0 $Date: 2016/5/6 10:55:11$
 * ==================================================
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	@Override
	public void update(LinkedHashMap<String, Object> params) throws Exception {
		String flag = params.get("flag").toString();
		params.remove("flag");
		
		//判断密码是否修改，密码已修改则md5加密
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("user_id", params.get("user_id"));
		Map<String, Object> object = this.object(map, "SYSTEM_USER_INFO");
		String user_pass = "";
		if(flag.equals("insert")){
			user_pass = Convert.md5Convert(params.get("user_pass").toString());
		} else if(flag.equals("update")){
			if(object.get("USER_PASS").toString().equals(params.get("user_pass").toString())){
				user_pass = params.get("user_pass").toString();
			} else{
				user_pass = Convert.md5Convert(params.get("user_pass").toString());
			}
		}
		
		
		//创建新的map实例，与存储过程参数顺序对应
		LinkedHashMap<String, Object> z_map = new LinkedHashMap<String, Object>();
		z_map.put("user_id", params.get("user_id"));
		z_map.put("user_pass", user_pass);
		z_map.put("user_name", params.get("user_name"));
		z_map.put("user_email", params.get("user_email"));
		z_map.put("user_addr", params.get("user_addr"));
		z_map.put("user_phone", params.get("user_phone"));
		z_map.put("is_available", params.get("is_available"));
		z_map.put("available_date", params.get("available_date"));
		z_map.put("role_id", params.get("role_id"));
		switch (flag) {
		case "insert":
			super.update(z_map, "SYSTEM_USER_INSERT");
			break;
		case "update":
			super.update(z_map, "SYSTEM_USER_UPDATE");
			break;
		case "delete":
			super.update(params, "SYSTEM_USER_DELETE");
			break;
		}
	}

}

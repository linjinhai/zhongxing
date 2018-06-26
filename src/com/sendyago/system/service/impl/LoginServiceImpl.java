/* ==============================================================
 * $ID: LoginServiceImpl.java, v1.5 2016/5/6 15:24:18 Rick Exp $
 * created: [2016-04-25 13:33:18] by Rick
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

import com.sendyago.system.service.LoginService;
import com.sendyago.util.jdbc.OracleJDBC;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户信息管理接口实现类
 * 用于管理用户信息的查询,保存,更新,删除等操作
 * queryList()方法用于查询用户列表
 * queryObject()方法用于根据某一属性信息查询单条用户信息
 * save()方法用于创建新用户,update()方法用于更新用户信息,delete()方法用于删除用户信息
 *
 * >>>注意: 将属性封装到map中一定要使用linkedHashMap(),该集合是有序的,否则在调用存储过程传参时会出问题.<<<
 *
 * @author $Author: Rick$
 * @version $Revision: 1.0 $Date: 2016/4/25 13:40:18 $
 * @version $Revision: 1.1 $Date: 2016/4/25 14:24:18 $
 * @version $Revision: 1.2 $Date: 2016/4/27 10:02:18 $
 * @version $Revision: 1.3 $Date: 2016/4/28 13:22:18 $
 * @version $Revision: 1.4 $Date: 2016/4/28 14:57:18 $
 * @version $Revision: 1.5 $Date: 2016/5/5 15:24:18 $
 */
@Component
public class LoginServiceImpl extends OracleJDBC implements LoginService {

    /**
     * 根据条件属性查询记录
     * @param params 查询条件
     * @return 单条记录
     * @throws Exception
     */
    public Object queryForObject(LinkedHashMap<String, Object> params) throws Exception {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("user_id", params.get("USER_ID")); // 查询条件
        List list = procedures4Query(map, "SYSTEM_USER_INFO");
        if(list.size() > 0) {
            return list.get(0);
        }else {
            return null;
        }
    }


    /**
     * 根据用户及角色查询对应的系统菜单项目
     * @param user_id 用户ID
     * @return  菜单列表
     * @throws Exception
     */
    public List queryMenuByUserRole (String user_id) throws Exception {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("user_id", user_id);
        List list = procedures4Query(map, "SYSTEM_USER_MENU_LIST");
        return list;
    }

    /**
     * 功能: 查询项目ip,项目名称
     * @param id 项目名称id
     * @return
     * @throws Exception
     */
    public Map queryIpAddress(int id) throws Exception {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("id", id);
        List list = procedures4Query(map, "PUBLIC_GET_IP");
        Map ipAddress = new HashMap();
        if(list.size() > 0) {
            ipAddress = (Map)list.get(0);
        }
        return ipAddress;
    }

}

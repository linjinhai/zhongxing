/* ==============================================================
 * $ID: SystemCountService.java, v1.0 2016/5/18 14:07:01 zq Exp $
 * created: [2016-05-18 14:07:01] by zq
 * ==============================================================
 * 健康监测系统模块化统计分析查询
 *
 * 健康监测系统模块化即将健康监测系统各功能模块进行项目化形式进行开发
 * 将各功能模块划分为各个WEB项目
 * ==============================================================
 * Copyright (c) 哈尔滨工大云帆智慧信息技术有限公司 All rights reserved
 * ==============================================================
 */
package com.sendyago.count.controller;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import com.sendyago.util.jdbc.OracleJDBC;
/**
 * 统计分析查询工具类
 *
 *
 * OperationButton()用于 调用权限按钮的方法;
 * OperationLog()用于 调用系统log方法写入日志信息;
 *
 * @author $Author: zq$
 * @version $Revision: 1.0 $Date: 2016/5/18 14:07:01 $
 */
public class SystemCountService {
    static OracleJDBC oracleJDBC=new OracleJDBC();  
  
    /**
     * 
     * @Description: 调权限按钮方法
     * @return void    返回类型
     * @throws
     */
    public static boolean OperationButton(String roleid, String menuId, String buttonId) {
       boolean flag = false;
   
        try {
            LinkedHashMap<String, Object> map=new LinkedHashMap<String, Object>();
            map.put("roleid", roleid);    
            map.put("menuId", menuId); 
            map.put("buttonId", buttonId); 
            @SuppressWarnings("unchecked")
            List<HashMap<String, Object>> list= oracleJDBC.procedures4Query(map,"SYSTEM_ROLE_MENU_BUTTON_INFO");
         if (list.size()>0) {            
            flag=true;
        }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
      
        return flag;
    }

    /**
    * 
    * @Description: 调用系统log方法写入日志信息
    * @param  log_id
    * @param  user_id
    * @param  menu_id
    * @param  button_Id    设定文件 
    * @return void    返回类型 
    * @throws
    */
    // log_id、user_id、menu_id、button_type_id
    public static void OperationLog(String log_id, String user_id, String menu_id,
            String button_Id) {
        try {           
            LinkedHashMap<String, Object> map=new LinkedHashMap<String, Object>();
            map.put("log_id", log_id);    
            map.put("user_id", user_id); 
            map.put("menu_id", menu_id); 
            map.put("button_Id", button_Id);    
            map.put("ip", "");   
           oracleJDBC.procedures4Change(map,"SYSTEM_LOG_INSERT");       
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
  
    public static void main(String args[]) throws Exception {
       OperationLog("1111","admin", "1100000000", "2");
        
       
    }
}

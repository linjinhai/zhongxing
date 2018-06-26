/* ==============================================================
 * $ID: LocalIpServiceImpl.java, v1.0 2016/7/28 15:24:18 chengqiang Exp $
 * created: [2016-07-28 13:33:18] by chengqiang
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

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.sendyago.system.service.LocalIpService;
/**
 * 获得ip接口实现类
 * getLocalIp()方法获得ip地址
 * @author $Author: chengqiang$
 * @version $Revision: 1.0 $Date: 2016/7/25 13:31:01 $
 */
@Service("localipServiceImpl")
public class LocalIpServiceImpl implements LocalIpService{
	
	@Override
	public String getLocalIp(HttpServletRequest request) {
		 String remoteAddr = request.getRemoteAddr();
	        String forwarded = request.getHeader("X-Forwarded-For");
	        String realIp = request.getHeader("X-Real-IP");

	        String ip = null;
	        if (realIp == null) {
	            if (forwarded == null) {
	                ip = remoteAddr;
	            } else {
	                ip = remoteAddr + "/" + forwarded.split(",")[0];
	            }
	        } else {
	            if (realIp.equals(forwarded)) {
	                ip = realIp;
	            } else {
	                if(forwarded != null){
	                    forwarded = forwarded.split(",")[0];
	                }
	                ip = realIp + "/" + forwarded;
	            }
	        }
	        return ip;
	}

}

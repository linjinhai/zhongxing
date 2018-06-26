/* ==============================================================
 * $ID: BaseController.java, v1.0 2016/4/28 13:32:32 zgx Exp $
 * created: [2016-04-28 13:32:32] by zgx
 * ==============================================================
 * 健康监测系统模块化 基类
 *
 * 健康监测系统模块化即将健康监测系统各功能模块进行项目化形式进行开发
 * 将各功能模块划分为各个WEB项目
 * ==============================================================
 * Copyright (c) 哈尔滨工大云帆智慧信息技术有限公司 All rights reserved
 * ==============================================================
 */
package com.sendyago.system.controller;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sendyago.util.common.Convert;

/**
 * ================================================== 
 * 控制层 - 基类
 * --------------------------------------------------
 * @author $Author: ZGX$ 
 * 
 * fileUpload（） ：上传文件
 * fileDelete（）：删除文件
 * --------------------------------------------------
 * @version $Revision: 1.0 $Date: 2016/5/6 10:55:11$
 * ==================================================
 */

public abstract class BaseController {
	private final static String UPLOAD_FOLDER = "/upload";
	private final static String SEPARATOR = "/";
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected LinkedHashMap<String, Object> params;

	@ModelAttribute
	public void setModelAttribute(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}
	
	//上传文件
	public String fileUpload(String baseName) throws Exception{
		MultipartHttpServletRequest mul = (MultipartHttpServletRequest)request;
		MultipartFile file = mul.getFile(baseName);
		if(!file.isEmpty()){
			String path =  request.getSession().getServletContext().getRealPath(UPLOAD_FOLDER);
			if(!new File(path).exists()){
				new File(path).mkdir();
			}
			String oName = file.getOriginalFilename();
			String suf = oName.substring(oName.lastIndexOf("."), oName.length());
			String nName = Convert.uuidConvert().concat(suf);
			file.transferTo(new File(path, nName));
			//return request.getContextPath() + UPLOAD_FOLDER + SEPARATOR + nName;
			return UPLOAD_FOLDER + SEPARATOR + nName;
		}
		return null;
	}
	
	//删除文件
	public void fileDelete(String fileName) {
		try {
			fileName = fileName.substring(fileName.lastIndexOf(SEPARATOR), fileName.length());
			String rootPath = request.getSession().getServletContext().getRealPath(UPLOAD_FOLDER);
			File file = new File(rootPath, fileName);
			if (file.exists()&&file.isFile()) {
				FileUtils.deleteQuietly(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//枚举型状态
	protected enum Status {
		WARN, ERROR, SUCCESS
	}

	//根据布尔类型返回ajax
	protected void ajax(boolean booleanStatus) {
		try {
			response.getWriter().print(booleanStatus);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//根据枚举状态返回ajax
	protected void ajax(Status status) {
		try {
			response.getWriter().print(status);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//根据文本返回ajax
	protected void ajax(String text) {
		try {
			response.getWriter().print(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
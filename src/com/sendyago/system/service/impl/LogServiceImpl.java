/* ==============================================================
 * $ID: LogServiceImpl.java, v1.4 2016/4/28 14:57:18 zgx Exp $
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

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.stereotype.Service;

import com.sendyago.system.service.LogService;

/**
 * ================================================== 
 * Service业务层实现类 - 系统管理 - 日志管理
 * --------------------------------------------------
 * @author $Author: ZGX$ 
 * --------------------------------------------------
 * @version $Revision: 1.0 $Date: 2016/5/10 9:09:20$
 * ==================================================
 */
@Service("logService")
public class LogServiceImpl extends BaseServiceImpl implements LogService {
	
	@Override
	public void logInsert(LinkedHashMap<String, Object> params) throws Exception {
		super.update(params, "SYSTEM_LOG_INSERT");
	}

	@Override
	public void logDelete(LinkedHashMap<String, Object> params) throws Exception {
		
	}

	@Override
	public void logClean(LinkedHashMap<String, Object> params) throws Exception {
		
	}
	@Override
	public void export(HttpServletResponse rep, List list) throws IOException {
		// TODO Auto-generated method stub
		HSSFRow row = null;
		HSSFCell cell = null;
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Sheet1");
		sheet.setColumnWidth((short) 0, (short) 1500);
		sheet.setColumnWidth((short) 1, (short) 5000);
		sheet.setColumnWidth((short) 2, (short) 5000);
		sheet.setColumnWidth((short) 3, (short) 5200);
		sheet.setColumnWidth((short) 4, (short) 5500);
	
		
		HSSFCellStyle cellStyle_th = wb.createCellStyle();
		cellStyle_th.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle_th.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle_th.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
		cellStyle_th.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle_th.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle_th.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle_th.setWrapText(true);
		cellStyle_th.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyle_th.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFFont font = (HSSFFont) wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		cellStyle_th.setFont(font);
		row = sheet.createRow(0);
		row.setHeight((short) 450);
		cell = row.createCell((short) 0);
		cell.setCellValue("序号");
		cell.setCellStyle(cellStyle_th);

		cell = row.createCell((short) 1);
		cell.setCellValue("操作用户名称");
		cell.setCellStyle(cellStyle_th);

		cell = row.createCell((short) 2);
		cell.setCellValue("操作菜单名称");
		cell.setCellStyle(cellStyle_th);

		cell = row.createCell((short) 3);
		cell.setCellValue("操作功能");
		cell.setCellStyle(cellStyle_th);

		cell = row.createCell((short) 4);
		cell.setCellValue("操作时间");
		cell.setCellStyle(cellStyle_th);
	
		
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setWrapText(true);
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			row = sheet.createRow(i + 1);
			row.setHeight((short) 400);
			
			cell = row.createCell((short) 0);
			cell.setCellValue((i+1)+"");
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell((short) 1);
			cell.setCellValue(map.get("USER_NAME").toString());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell((short) 2);
			cell.setCellValue(map.get("MENU_NAME").toString());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell((short) 3);
			cell.setCellValue(map.get("BUTTON_NAME").toString());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell((short) 4);
			cell.setCellValue(map.get("ADD_TIME").toString());
			cell.setCellStyle(cellStyle);
			
			
		}
		OutputStream os = null;
		rep.setContentType("application/vnd.ms-excel;charset=utf-8");
		rep.setHeader("Content-Disposition", "attachment; filename=" + new String("日志数据.xls".getBytes("UTF-8"), "ISO-8859-1"));
		os = rep.getOutputStream();
		wb.write(os);
		os.close();
	}

}

package com.sendyago.util.common;

import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
/**
 * 响应格式，分别导入导出
 * 描述类
 * 描述类声明方法
 * @author  $Author: zq&    
 * @version $Revision:1.0 2016年5月9日 上午8:48:11 $
 *
 */
public class ResponseUtil {
	/**
	 * 
	 * @Description: TODO 写入
	 * @param @param response
	 * @param @param o
	 * @param @throws Exception    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public static void write(HttpServletResponse response,Object o) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(o.toString());
		out.flush();
		out.close();
	}
	/**
     * 
     * @Description: TODO 导出
     * @param @param response
     * @param @param o
     * @param @throws Exception    设定文件 
     * @return void    返回类型 
     * @throws
     */
	public static void export(HttpServletResponse response,Workbook wb,String fileName)throws Exception{
		response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("utf-8"),"iso8859-1"));
		response.setContentType("application/ynd.ms-excel;charset=UTF-8");
		OutputStream out=response.getOutputStream();
		wb.write(out);
		out.flush();
		out.close();
	}
}

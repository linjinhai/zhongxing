package com.sendyago.util.common;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelUtil {
	
	
	/**
	 * 
	 * @Description: TODO导出excel文件，src下设置模板路径
	 * @param @param rs
	 * @param @param templateFileName
	 * @param @return
	 * @param @throws Exception    设定文件 
	 * @return Workbook    返回类型 
	 * @throws
	 */
	public static Workbook fillExcelDataWithTemplate(ResultSet rs,String templateFileName)throws Exception{	
	    //设置路径
		InputStream inp=ExcelUtil.class.getResourceAsStream("/com/sendyago/util/template/"+templateFileName);
		POIFSFileSystem fs=new POIFSFileSystem(inp);
		Workbook wb=new HSSFWorkbook(fs);
		Sheet sheet=wb.getSheetAt(0);

	
		int cellNums=sheet.getRow(0).getLastCellNum();
		int rowIndex=1;
		while(rs.next()){
			Row row=sheet.createRow(rowIndex++);
			for(int i=0;i<cellNums;i++){

				row.createCell(i).setCellValue(rs.getObject(i+1).toString());
			}
		}
		rs.close();
		return wb;
	}
	
	   /**
     * 
     * @Description: TODO导出excel文件，src下设置模板路径 @param @param list @param @param
     * templateFileName @param @return @param @throws Exception 设定文件 @return
     * Workbook 返回类型 @throws
     */
	
	   public static Workbook fillExcelDataWithTemplate(List<HashMap<String, Object>> list,String templateFileName) throws IOException{ 
	        //设置路径
	        InputStream inp=ExcelUtil.class.getResourceAsStream("/com/sendyago/util/template/"+templateFileName);
	        POIFSFileSystem fs=new POIFSFileSystem(inp);
	        Workbook wb=new HSSFWorkbook(fs);
	        Sheet sheet=wb.getSheetAt(0);

	        int rowIndex=1;
	        	      
	        for(int i=0;i<list.size(); i++){
	            Row row=sheet.createRow(rowIndex++);
	            list.get(i).get("input_time").toString();
	            row.createCell(0).setCellValue( list.get(i).get("input_value").toString());
                row.createCell(1).setCellValue( list.get(i).get("input_time").toString());
	        }
	        
	      
	        return wb;
	    }
	    /**
	     * 
	     * @Description: TODO导出excel文件，src下设置模板路径 @param @param list @param @param
	     * templateFileName @param @return @param @throws Exception 设定文件 @return
	     * Workbook 返回类型 @throws
	     */
	   
	    public static Workbook fillExcelDataWithTemplate0(List<LinkedHashMap<String, Object>> list0,List<LinkedHashMap<String, Object>> list1,List<LinkedHashMap<String, Object>> list2, String templateFileName)
	            throws IOException {
	        // 设置路径
	        InputStream inp = ExcelUtil.class.getResourceAsStream("/com/sendyago/util/template/" + templateFileName);
	        POIFSFileSystem fs = new POIFSFileSystem(inp);
	        Workbook wb = new HSSFWorkbook(fs);
	        Sheet sheet = wb.getSheetAt(0);
	        
	        Sheet sheet1=wb.getSheetAt(1);
	        int rowIndex = 1;
	        int rowIndex1 = 1;
	     
	/*    for (int i=0;i<list0.size();i++){
	        
	        Row row = sheet.createRow(rowIndex++);
	        if(i>=0&&i<list1.size()){
	            row.createCell(0).setCellValue(list0.get(i).get("X").toString());
	            row.createCell(1).setCellValue(list0.get(i).get("Y").toString());

	        }
	            
	    
	    if(i>list1.size()-1&&i<list0.size()){
	        row.createCell(2).setCellValue(list0.get(i).get("X1").toString());
	        row.createCell(3).setCellValue(list0.get(i).get("Y1").toString());
	    
	        }

	       
	    }*/

	        for (int i=0;i<list1.size();i++){
	            Row row = sheet.createRow(rowIndex++);
	            row.createCell(0).setCellValue(list1.get(i).get("Y").toString());
                row.createCell(1).setCellValue(list1.get(i).get("X").toString());
	            
	        }
	        for (int j=0;j<list2.size();j++){
                Row row1 = sheet1.createRow(rowIndex1++);
                row1.createCell(0).setCellValue(list2.get(j).get("Y1").toString());
                row1.createCell(1).setCellValue(list2.get(j).get("X1").toString());
                
            }
	        
	        return wb;
	    }

	
	public static String formatCell(HSSFCell hssfCell){
		if(hssfCell==null){
			return "";
		}else{
			if(hssfCell.getCellType()==HSSFCell.CELL_TYPE_BOOLEAN){
				return String.valueOf(hssfCell.getBooleanCellValue());
			}else if(hssfCell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
				return String.valueOf(hssfCell.getNumericCellValue());
			}else{
				return String.valueOf(hssfCell.getStringCellValue());
			}
		}
	}
	/**
	 *  
	 * @Description: 获取cell
	 * @param @param cell
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	
	public static String getCell(HSSFCell cell) {
		DecimalFormat df = new DecimalFormat("#");
		if (cell == null)
			return "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC:
			if(HSSFDateUtil.isCellDateFormatted(cell)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
			}
			return df.format(cell.getNumericCellValue());
		case HSSFCell.CELL_TYPE_STRING:
			System.out.println(cell.getStringCellValue());
			return cell.getStringCellValue();
		case HSSFCell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case HSSFCell.CELL_TYPE_BLANK:
			return "";
		case HSSFCell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue() + "";
		case HSSFCell.CELL_TYPE_ERROR:
			return cell.getErrorCellValue() + "";
		}
		return "";
	}

}

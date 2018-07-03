/* ==============================================================
 * $ID: EssenceController.java, v1.0 2016/8/5 13:32:32 fsd Exp $
 * created: [2016-04-28 13:32:32] by fsd
 * ==============================================================
 * 健康监测系统模块化公共信息
 *
 * 健康监测系统模块化即将健康监测系统各功能模块进行项目化形式进行开发
 * 将各功能模块划分为各个WEB项目
 * ==============================================================
 * Copyright (c) 哈尔滨工大云帆智慧信息技术有限公司 All rights reserved
 * ==============================================================
 */
package com.sendyago.essence.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sendyago.essence.service.EssenceService;
import com.sendyago.system.controller.BaseController;
import com.sendyago.util.common.Pager;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.sf.json.JSONArray;

/**
 * ================================================== 
 * 控制层 - 人工巡检 - 
 * --------------------------------------------------
 * @author $Author: FSD$ 
 * --------------------------------------------------
 * @version $Revision: 1.0 $Date: 2016/8/5 8:55:11$
 * ==================================================
 */
@Controller
@RequestMapping("essence")
public class EssenceController extends BaseController {

	@Autowired
	private EssenceService es;

	/**
	 * 人工巡检 跳到编辑
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toadd")
	public String toadd(ModelMap modelMap, HttpServletResponse rep, HttpServletRequest req, @RequestParam LinkedHashMap<String, Object> params) throws Exception {
		if (request.getParameter("id") != null) {
			params.put("IN_PAGE", -1);
			params.put("IN_LIMIT", -1);
			Map<?, ?> object = es.object(params, "essence_rgxj_query");
			modelMap.put("object", object);
		}
		return "essence/toaddEssence";
	}

	/**
	 * 人工巡检编辑
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addRgxjEnd")
	public String addRgxjEnd(ModelMap modelMap, HttpServletResponse rep, HttpServletRequest req, @RequestParam LinkedHashMap<String, Object> params) throws Exception {
		if (params.get("id") == null) {
			//增加
			if (params.get("INSPECT_ID").equals("")) {
				params.put("flag", "insert");
			}
			//修改
			else if (!params.get("INSPECT_ID").equals("")) {
				params.put("flag", "update");
			}
		}
		//删除
		else {
			Map map = params;
			params = new LinkedHashMap<>();
			params.put("id", map.get("id"));
			params.put("q1", "");
			params.put("q2", "");
			params.put("q3", "");
			params.put("flag", "delete");
		}
		es.cap(params, "essence_rgxj_edit");
		modelMap.put("message", "ok");
		return "redirect:queryrgxj";
	}

	/**
	 * 人工巡检 分页查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryrgxj")
	public String queryrgxj(Pager pager, @RequestParam(value = "type", defaultValue = "") String type, HttpServletRequest req) throws Exception {
		pager = es.pager(request, pager, "essence_rgxj_query");
		request.setAttribute("pager", pager);
		request.setAttribute("type", type);
		return "essence/queryEssence";
	}

	/**
	 * 跳到 开始评估 页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toAnpg")
	public String toAnpg(HttpServletRequest req, @RequestParam(value = "id", defaultValue = "") String id) throws Exception {
		req.setAttribute("id", id);
		return "essence/editAnpg";
	}

	/**
	 * 开始评估 页面 获取构件系统
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getTop2")
	public void getTop2(HttpServletRequest req, HttpServletResponse rep, @RequestParam(value = "id", defaultValue = "") String id) throws Exception {
		List list = es.docaplist("select * from YUCI.BRIDGE_PARAM_DATA where data_pid=" + id + " and data_flag=0 order by data_id");
		JSONArray js = JSONArray.fromObject(list);
		rep.getWriter().print(js.toString());

	}

	/**
	 * 开始评估 页面 获取 根据id 获取   已经评估的数据 主表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getTop3")
	public void getTop3(HttpServletRequest req, HttpServletResponse rep, @RequestParam(value = "id", defaultValue = "") String id) throws Exception {
		List list = es.docaplist("select * from YUCI.BRIDGE_ASSESS where assess_id=" + id + " ");
		JSONArray js = JSONArray.fromObject(list);
		rep.getWriter().print(js.toString());

	}

	/**
	 * 开始评估 页面 获取 根据id 获取   已经评估的数据 附表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getTop4")
	public void getTop4(HttpServletRequest req, HttpServletResponse rep, @RequestParam(value = "id", defaultValue = "") String id) throws Exception {
		List list = es.docaplist("select * from YUCI.BRIDGE_ASSESS_DATA where assess_id=" + id + " ");
		JSONArray js = JSONArray.fromObject(list);
		rep.getWriter().print(js.toString());

	}

	/**
	 * 获取结构树 一级目录
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getTop1")
	public void getTop1(ModelMap modelMap, HttpServletResponse rep, HttpServletRequest req, @RequestParam LinkedHashMap<String, Object> params) throws Exception {
		if (params.get("in_id") == null) {
			params.put("in_id", "");
			params.put("str_id", "");
		}
		List list = es.allcap(params, "essence_get_data1");
		JSONArray js = JSONArray.fromObject(list);
		rep.getWriter().print(js.toString());
	}

	/**
	 * 保存人工巡检DataDetail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addEssenceDetail")
	public void addEssenceDetail(ModelMap modelMap, HttpServletResponse rep, HttpServletRequest req, @RequestParam LinkedHashMap<String, Object> params, String in_id, String[] xzsl, String gj_id) throws Exception {
		es.docap("delete from YUCI.BRIDGE_INSPECTION_DATA where struct_id=" + gj_id + " and inspect_id=" + in_id);
		es.docap("update BRIDGE_INPSECTION set INSPECT_FLAG='1' where  inspect_id=" + in_id);
		for (String a : xzsl) {
			es.docap("insert into BRIDGE_INSPECTION_DATA values (" + in_id + "," + gj_id + "," + a.split("_")[0] + "," + getScore(a) + "," + a.split("_")[1] + ") ");
		}
		rep.getWriter().print("ok");
	}

	/**
	 * 保存安全评估DataDetail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addAqpgDetail")
	public void addAqpgDetail(ModelMap modelMap, HttpServletResponse rep, HttpServletRequest req, @RequestParam LinkedHashMap<String, Object> params, String[] RT) throws Exception {
		//删除主表
		es.docap("delete from YUCI.BRIDGE_ASSESS where assess_id=" + params.get("assess_id"));
		//删除附表
		es.docap("delete from YUCI.BRIDGE_ASSESS_DATA where assess_id=" + params.get("assess_id"));
		//增加主表
		es.docap("insert into BRIDGE_ASSESS (ASSESS_ID,MP_1,MP_2,MP_3,MP_4,MP_5,MP_6,MP_7,MP_8,MP_9,MP_10,MP_11,MP_12,TY_1,TY_2,TY_3,TY_4,TY_5,TY_6,YU_1,YU_2,YU_3,YU_4,YU_5,YU_6,YU_7,YU_8) values ( '" + params.get("assess_id") + "' , '" + params.get("MP_1") + "' , '" + params.get("MP_2") + "' , '" + params.get("MP_3") + "' , '" + params.get("MP_4") + "' , '" + params.get("MP_5") + "' , '" + params.get("MP_6") + "' , '" + params.get("MP_7") + "' , '" + params.get("MP_8") + "' , '" + params.get("MP_9") + "' , '" + params.get("MP_10") + "' , '" + params.get("MP_11") + "' , '" + params.get("MP_12") + "' , '" + params.get("TY_1") + "' , '" + params.get("TY_2") + "' , '" + params.get("TY_3") + "' , '" + params.get("TY_4") + "' , '" + params.get("TY_5") + "' , '" + params.get("TY_6") + "' , '" + params.get("YU_1")
				+ "' , '" + params.get("YU_2") + "' , '" + params.get("YU_3") + "' , '" + params.get("YU_4") + "' , '" + params.get("YU_5") + "' , '" + params.get("YU_6") + "' , '" + params.get("YU_7") + "' , '" + params.get("YU_8") + "' )  ");
		//增加附表
		for (String a : RT) {
			String sql = "insert into BRIDGE_ASSESS_DATA values('" + params.get("assess_id") + "','" + req.getParameter("RT_" + a + "_1") + "','" + req.getParameter("RT_" + a + "_2") + "','" + req.getParameter("RT_" + a + "_3") + "','" + req.getParameter("RT_" + a + "_4") + "','" + req.getParameter("RT_" + a + "_5") + "','" + req.getParameter("RT_" + a + "_6") + "','" + a + "')";
			es.docap(sql);
		}
		//修改查询安全评估的首页 为 已评估
		es.docap("update BRIDGE_INPSECTION set INSPECT_FLAG='2' where  inspect_id=" + params.get("assess_id"));
	}

	/**
	 * 安全评估  开始评估
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/kspg")
	public void kspg(ModelMap modelMap, HttpServletResponse rep, HttpServletRequest req, @RequestParam LinkedHashMap<String, Object> params) throws Exception {
		/**
		 * 1）针对未评估部分，可按无病害处理，否则出现大量未评估的项目。
		 */
		List bp = es.docaplist("select * from YUCI.BRIDGE_STRUCT t left join YUCI. BRIDGE_PARAM_ASSESS q on t.struct_id=q.struct_id where t.is_child=1 and q.desc_index=1");
		List t1 = es.docaplist("select q.* from YUCI.BRIDGE_STRUCT q where q.is_child=2 and q.struct_id not in (select w.struct_id from YUCI.BRIDGE_INSPECTION_DATA w where w.inspect_id=" + params.get("assess_id") + " ) ");
		for (int i = 0; i < t1.size(); i++) {
			Map mi1 = (Map) t1.get(i);
			for (int j = 0; j < bp.size(); j++) {
				Map mi2 = (Map) bp.get(j);
				if (mi1.get("STRUCT_PID").equals(mi2.get("STRUCT_PID"))) {
					es.docap("insert into BRIDGE_INSPECTION_DATA values(" + params.get("assess_id") + ",'" + mi1.get("STRUCT_ID") + "','" + mi2.get("PARAM_ID") + "',0,1)");
				}
			}
		}
		/**
		 * 2）开始计算
		 */
		Map m2 = new HashMap();
		List<Map> list_qq = es.docaplist("select * from YUCI.BRIDGE_PARAM_DATA t where data_flag=0 order by DATA_ID");
		String[] allChile = new String[list_qq.size()];
		for (int i = 0; i < list_qq.size(); i++) {
			Map mapui = list_qq.get(i);
			allChile[i] = mapui.get("DATA_ID").toString();
		}
		for (String a : allChile) {
			List l3 = new java.util.ArrayList();
			List<Map> l1 = es.docaplist("select * from YUCI.BRIDGE_STRUCT t where t.is_child=2 and t.struct_pid=" + a);
			for (Map m : l1) {
				List l2 = es.docaplist("select * from YUCI.BRIDGE_INSPECTION_DATA t  where t.inspect_id=" + params.get("assess_id") + " and t.struct_id=" + m.get("STRUCT_ID") + " order by score desc");
				Map map0 = (Map) l2.get(0);
				float u0 = Float.parseFloat(map0.get("SCORE") + "");
				float zf = 100 - u0;
				for (int i = 1; i < l2.size(); i++) {
					Map map = (Map) l2.get(i);
					float u = (float) (Float.parseFloat(map.get("SCORE") + "") / (100 * Math.sqrt(i + 1))) * (100 - u0);
					float ss = (float) (Math.round(u * 100)) / 100;
					u0 = u0 + u;
					zf = zf - ss;
				}
				l3.add(zf);
			}
			if (l3.size() > 0) {
				float avg = 0, count = 0, t = 0;
				float[] arr = new float[l3.size()];
				for (int i = 0; i < l3.size(); i++) {
					count = count + Float.parseFloat(l3.get(i) + "");
					arr[i] = Float.parseFloat(l3.get(i) + "");
				}
				Arrays.sort(arr);

				List l4 = es.docaplist("select * from YUCI.BRIDGE_PARAM_T where n=" + l3.size());
				if (l4.size() == 0) {
					t = (float) 2.30;
				} else {
					Map map4 = (Map) l4.get(0);
					t = Float.parseFloat(map4.get("T") + "");
				}
				avg = count / l3.size() - (100 - arr[0]) / t;

				m2.put("fs_" + a, (float) (Math.round(avg * 100)) / 100);
				if (avg >= 95)
					m2.put("dj_" + a, 1);
				else if (avg >= 80 && avg < 95)
					m2.put("dj_" + a, 2);
				else if (avg >= 60 && avg < 80)
					m2.put("dj_" + a, 3);
				else if (avg >= 40 && avg < 60)
					m2.put("dj_" + a, 4);
				else if (avg >= 0 && avg < 40)
					m2.put("dj_" + a, 5);

			}
		}

		Map m3 = new HashMap();

		for (int i = 0; i < allChile.length; i++) {
			m3.put("RT_" + allChile[i] + "_1", m2.get("fs_" + allChile[i]).toString());
			m3.put("RT_" + allChile[i] + "_2", m2.get("dj_" + allChile[i]).toString());
		}

		//计算上部，下部，桥面 ，分数
		String[] bh = { "1", "2", "3" };

		for (String a : bh) {
			float pcci = 0;
			for (int i = 0; i < list_qq.size(); i++) {
				Map m5 = (Map) list_qq.get(i);
				if ((m5.get("DATA_PID").toString()).equals(a)) {
					pcci += Float.parseFloat(m3.get("RT_" + m5.get("DATA_ID") + "_1").toString()) * Float.parseFloat(m5.get("DATA_SCORE").toString());
				}
			}
			if (pcci >= 95) {
				m3.put("TY_" + (Integer.parseInt(a) + 3), 1);
			} else if (pcci >= 80 && pcci < 95) {
				m3.put("TY_" + (Integer.parseInt(a) + 3), 2);
			} else if (pcci >= 60 && pcci < 80) {
				m3.put("TY_" + (Integer.parseInt(a) + 3), 3);
			} else if (pcci >= 40 && pcci < 60) {
				m3.put("TY_" + (Integer.parseInt(a) + 3), 4);
			} else if (pcci >= 0 && pcci < 40) {
				m3.put("TY_" + (Integer.parseInt(a) + 3), 5);
			}
			m3.put("TY_" + a, (float) (Math.round(pcci * 100)) / 100 + "");

		}

		//计算整体
		float pcci = (float) (Float.parseFloat(m3.get("TY_1") + "") * 0.4 + Float.parseFloat(m3.get("TY_2") + "") * 0.4 + Float.parseFloat(m3.get("TY_3") + "") * 0.2);
		if (pcci >= 95) {
			m3.put("YU_2", 1);
		} else if (pcci >= 80 && pcci < 95) {
			m3.put("YU_2", 2);
		} else if (pcci >= 60 && pcci < 80) {
			m3.put("YU_2", 3);
		} else if (pcci >= 40 && pcci < 60) {
			m3.put("YU_2", 4);
		} else if (pcci >= 0 && pcci < 40) {
			m3.put("YU_2", 5);
		}
		m3.put("YU_1", (float) (Math.round(pcci * 100)) / 100 + "");

		JSONArray js = JSONArray.fromObject(m3);
		rep.getWriter().print(js.toString());

	}

	/**
	 * 导出安全评估excel
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param id
	 * @throws Exception 
	 */
	@RequestMapping(value = "dcAqpgExcel", method = { RequestMethod.GET, RequestMethod.POST })
	public void dcAqpgExcel(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, String id) throws Exception {
		try {
			Map map = (Map) es.docaplist("select * from YUCI.BRIDGE_ASSESS where assess_ID='" + id + "'").get(0);
			List list1 = es.docaplist("select * from YUCI.BRIDGE_ASSESS_DATA t left join YUCI. bridge_param_data q on t.data_id=q.data_id where t.assess_id= " + id + " and q.data_pid=1 order by q.data_id");
			List list2 = es.docaplist("select * from YUCI.BRIDGE_ASSESS_DATA t left join YUCI. bridge_param_data q on t.data_id=q.data_id where t.assess_id= " + id + " and q.data_pid=2 order by q.data_id");
			List list3 = es.docaplist("select * from YUCI.BRIDGE_ASSESS_DATA t left join YUCI. bridge_param_data q on t.data_id=q.data_id where t.assess_id= " + id + " and q.data_pid=3 order by q.data_id");
			File templateFile = null;
			File templateFile2 = null;

			templateFile = new File(request.getRealPath("/pro/xui_1.xls"));
			templateFile2 = new File(request.getRealPath("/pro/xui_1_" + id + ".xls"));

			Workbook wb = Workbook.getWorkbook(templateFile);
			WritableWorkbook wwb = Workbook.createWorkbook(templateFile2, wb);
			WritableSheet wws = wwb.getSheet("Sheet1");

			WritableCellFormat wc = new WritableCellFormat();
			//		把水平对齐方式指定为居中
			wc.setAlignment(jxl.format.Alignment.LEFT);
			//		把垂直对齐方式指定为居中
			wc.setVerticalAlignment(jxl.format.VerticalAlignment.TOP);
			//		设置自动换行
			wc.setWrap(true);
			wc.setBorder(Border.ALL, BorderLineStyle.THIN);
			wc.setFont(new WritableFont(WritableFont.createFont("仿宋_GB2312"), 12, WritableFont.NO_BOLD));

			wws.addCell(new Label(1, 0, map.get("MP_1") + "", wc));
			wws.addCell(new Label(5, 0, map.get("MP_2") + "", wc));
			wws.addCell(new Label(13, 0, map.get("MP_3") + "", wc));
			wws.addCell(new Label(1, 1, map.get("MP_4") + "", wc));
			wws.addCell(new Label(5, 1, map.get("MP_5") + "", wc));
			wws.addCell(new Label(13, 1, map.get("MP_6") + "", wc));
			wws.addCell(new Label(1, 2, map.get("MP_7") + "", wc));
			wws.addCell(new Label(5, 2, map.get("MP_8") + "", wc));
			wws.addCell(new Label(13, 2, map.get("MP_9") + "", wc));
			wws.addCell(new Label(1, 3, map.get("MP_10") + "", wc));
			wws.addCell(new Label(5, 3, map.get("MP_11") + "", wc));
			wws.addCell(new Label(13, 3, map.get("MP_12") + "", wc));

			//列行-列行
			wws.mergeCells(0, 6, 0, 6 + list1.size() - 1);
			wws.mergeCells(1, 6, 1, 6 + list1.size() - 1);
			wws.mergeCells(2, 6, 2, 6 + list1.size() - 1);

			wws.addCell(new Label(0, 6, "上部结构\n(SPCI)", wc));
			wws.addCell(new Label(1, 6, map.get("TY_1") + "", wc));
			wws.addCell(new Label(2, 6, map.get("TY_4") + "", wc));

			//列行-列行
			wws.mergeCells(0, 6 + list1.size(), 0, 6 + list1.size() + list2.size() - 1);
			wws.mergeCells(1, 6 + list1.size(), 1, 6 + list1.size() + list2.size() - 1);
			wws.mergeCells(2, 6 + list1.size(), 2, 6 + list1.size() + list2.size() - 1);

			wws.addCell(new Label(0, 6 + list1.size(), "下部结构\n(SBCI)", wc));
			wws.addCell(new Label(1, 6 + list1.size(), map.get("TY_2") + "", wc));
			wws.addCell(new Label(2, 6 + list1.size(), map.get("TY_5") + "", wc));

			//列行-列行
			wws.mergeCells(0, 6 + list1.size() + list2.size(), 0, 6 + list1.size() + list2.size() + list3.size() - 1);
			wws.mergeCells(1, 6 + list1.size() + list2.size(), 1, 6 + list1.size() + list2.size() + list3.size() - 1);
			wws.mergeCells(2, 6 + list1.size() + list2.size(), 2, 6 + list1.size() + list2.size() + list3.size() - 1);

			wws.addCell(new Label(0, 6 + list1.size() + list2.size(), "桥面系\n(BDCI)", wc));
			wws.addCell(new Label(1, 6 + list1.size() + list2.size(), map.get("TY_3") + "", wc));
			wws.addCell(new Label(2, 6 + list1.size() + list2.size(), map.get("TY_6") + "", wc));
			
			
			for (int i = 0; i < list1.size(); i++) {
				int t= 6 + i;
				Map m = (Map)list1.get(i);
				wws.mergeCells(5, t, 6, t);
				wws.mergeCells(8, t, 10, t);
				wws.mergeCells(12, t, 13, t);
				
				wws.addCell(new Label(3, t,"PCCI"+(i+1) , wc));
				wws.addCell(new Label(4, t,m.get("DATA_NAME")+"" , wc));
				wws.addCell(new Label(5, t,m.get("RU1")+"" , wc));
				wws.addCell(new Label(7, t,m.get("RU2")+"" , wc));
				wws.addCell(new Label(8, t,m.get("RU3")+"" , wc));
				wws.addCell(new Label(11, t,m.get("RU4")+"" , wc));
				wws.addCell(new Label(12, t,m.get("RU5")+"" , wc));
				wws.addCell(new Label(14, t,m.get("RU6")+"" , wc));
				
			}
			
			for (int i = 0; i < list2.size(); i++) {
				int t= 6 + i+ list1.size(); 
				Map m = (Map)list2.get(i);
				wws.mergeCells(5, t, 6, t);
				wws.mergeCells(8, t, 10, t);
				wws.mergeCells(12, t, 13, t);
				
				wws.addCell(new Label(3, t,"BCCI"+(i+1) , wc));
				wws.addCell(new Label(4, t,m.get("DATA_NAME")+"" , wc));
				wws.addCell(new Label(5, t,m.get("RU1")+"" , wc));
				wws.addCell(new Label(7, t,m.get("RU2")+"" , wc));
				wws.addCell(new Label(8, t,m.get("RU3")+"" , wc));
				wws.addCell(new Label(11, t,m.get("RU4")+"" , wc));
				wws.addCell(new Label(12, t,m.get("RU5")+"" , wc));
				wws.addCell(new Label(14, t,m.get("RU6")+"" , wc));
				
			}
			
			for (int i = 0; i < list3.size(); i++) {
				int t= 6 + i+ list1.size()+list2.size(); 
				Map m = (Map)list3.get(i);
				wws.mergeCells(5, t, 6, t);
				wws.mergeCells(8, t, 10, t);
				wws.mergeCells(12, t, 13, t);
				
				wws.addCell(new Label(3, t,"DCCI"+(i+1) , wc));
				wws.addCell(new Label(4, t,m.get("DATA_NAME")+"" , wc));
				wws.addCell(new Label(5, t,m.get("RU1")+"" , wc));
				wws.addCell(new Label(7, t,m.get("RU2")+"" , wc));
				wws.addCell(new Label(8, t,m.get("RU3")+"" , wc));
				wws.addCell(new Label(11, t,m.get("RU4")+"" , wc));
				wws.addCell(new Label(12, t,m.get("RU5")+"" , wc));
				wws.addCell(new Label(14, t,m.get("RU6")+"" , wc));
				
			}
			
			int t=6 + list1.size()+list2.size()+list3.size();
			wws.mergeCells(0, t, 3, t);wws.addCell(new Label(0, t, "总体技术状况评分", wc));
			wws.mergeCells(4, t, 6, t);wws.addCell(new Label(4, t, map.get("YU_1")+"", wc));
			wws.mergeCells(7, t, 11, t);wws.addCell(new Label(7, t, "总体技术状况等级", wc));
			wws.mergeCells(12, t, 14, t);wws.addCell(new Label(12, t, map.get("YU_2")+"", wc));
			t=t+1;
			wws.mergeCells(0, t, 3, t);wws.addCell(new Label(0, t, "全桥清洁状况评分(0～100)", wc));
			wws.mergeCells(4, t, 6, t);wws.addCell(new Label(4, t, map.get("YU_3")+"", wc));
			wws.mergeCells(7, t, 11, t);wws.addCell(new Label(7, t, "保养、小修状况评分(0～100)", wc));
			wws.mergeCells(12, t, 14, t);wws.addCell(new Label(12, t, map.get("YU_4")+"", wc));
			t=t+1;
			wws.mergeCells(0, t, 1, t);wws.addCell(new Label(0, t, "养护建议", wc));
			wws.mergeCells(2, t, 14, t);wws.addCell(new Label(2, t, map.get("YU_5")+"", wc));
			t=t+1;
			wws.addCell(new Label(0, t, "记录人", wc));
			wws.mergeCells(1, t, 3, t);wws.addCell(new Label(1, t, map.get("YU_6")+"", wc));
			wws.addCell(new Label(4, t, "负责人", wc));
			wws.mergeCells(5, t, 7, t);wws.addCell(new Label(5, t, map.get("YU_7")+"", wc));
			wws.mergeCells(8, t, 11, t);wws.addCell(new Label(8, t, "下次检查时间", wc));
			wws.mergeCells(12, t, 14, t);wws.addCell(new Label(12, t, map.get("YU_8")+"", wc));
			

			

			wwb.write();
			wb.close();
			wwb.close();

			downFile(request, response, "pro//xui_1_" + id + ".xls", "安全评估结果导出.xls");

		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 根据 标度获取评估分数
	 * @param d
	 * @return
	 */
	public String getScore(String d) {
		try {
			String a = d.split("_")[1] + "_" + d.split("_")[2];
			Class c = Class.forName("com.sendyago.essence.controller.PGFS");
			Object o = c.newInstance();
			Method m = c.getMethod("getP_" + a);
			return (String) m.invoke(o);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 下载导出Excel文件
	 * @param request
	 * @param response
	 * @param filename
	 * @param realname
	 * @throws IOException
	 */
	public static void downFile(HttpServletRequest request, HttpServletResponse response, String filename, String realname) throws IOException {
		OutputStream fos = null;
		InputStream fis = null;
		String path = request.getSession().getServletContext().getRealPath(filename);
		File uploadFile = new File(path);
		fis = new FileInputStream(uploadFile);
		if (fis != null) {
			fos = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment;filename=" + new String(realname.getBytes("gbk"), "iso-8859-1"));
			int bytesRead = 0;
			byte[] buffer = new byte[1024 * 2];
			while ((bytesRead = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, bytesRead);
			}
			fos.flush();

		}
		if (fis != null) {
			fis.close();
		}
		if (fos != null) {
			fos.close();
		}
	}

}
/**
 * 检测指标所能达到的最高等级类别和指标类别的计算公式
 * @author tom
 *
 */
class PGFS {
	private static String P_1_3 = "0";

	private static String P_2_3 = "20";

	private static String P_3_3 = "35";

	private static String P_1_4 = "0";

	private static String P_2_4 = "25";

	private static String P_3_4 = "40";

	private static String P_4_4 = "50";

	private static String P_1_5 = "0";

	private static String P_2_5 = "35";

	private static String P_3_5 = "45";

	private static String P_4_5 = "60";

	private static String P_5_5 = "100";

	public static String getP_1_3() {
		return P_1_3;
	}

	public static void setP_1_3(String p_1_3) {
		P_1_3 = p_1_3;
	}

	public static String getP_1_4() {
		return P_1_4;
	}

	public static void setP_1_4(String p_1_4) {
		P_1_4 = p_1_4;
	}

	public static String getP_1_5() {
		return P_1_5;
	}

	public static void setP_1_5(String p_1_5) {
		P_1_5 = p_1_5;
	}

	public static String getP_2_3() {
		return P_2_3;
	}

	public static void setP_2_3(String p_2_3) {
		P_2_3 = p_2_3;
	}

	public static String getP_2_4() {
		return P_2_4;
	}

	public static void setP_2_4(String p_2_4) {
		P_2_4 = p_2_4;
	}

	public static String getP_2_5() {
		return P_2_5;
	}

	public static void setP_2_5(String p_2_5) {
		P_2_5 = p_2_5;
	}

	public static String getP_3_3() {
		return P_3_3;
	}

	public static void setP_3_3(String p_3_3) {
		P_3_3 = p_3_3;
	}

	public static String getP_3_4() {
		return P_3_4;
	}

	public static void setP_3_4(String p_3_4) {
		P_3_4 = p_3_4;
	}

	public static String getP_3_5() {
		return P_3_5;
	}

	public static void setP_3_5(String p_3_5) {
		P_3_5 = p_3_5;
	}

	public static String getP_4_4() {
		return P_4_4;
	}

	public static void setP_4_4(String p_4_4) {
		P_4_4 = p_4_4;
	}

	public static String getP_4_5() {
		return P_4_5;
	}

	public static void setP_4_5(String p_4_5) {
		P_4_5 = p_4_5;
	}

	public static String getP_5_5() {
		return P_5_5;
	}

	public static void setP_5_5(String p_5_5) {
		P_5_5 = p_5_5;
	}

}

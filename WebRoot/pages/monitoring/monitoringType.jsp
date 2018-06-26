<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/pages/common/common_monitor.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%--
<script type="text/javascript" src="<%=path%>/dwr/util.js"></script>
<script type="text/javascript" src="<%=path%>/dwr/engine.js"></script>
<script type="text/javascript" src="<%=path%>/dwr/PushDwrData.js"></script>
 --%>
<script src="<%=path%>/js/plugins/My97DatePicker/WdatePicker.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

</head>
<body onload="dw();">
	<input type="hidden" id="path" value="<%=path%>" />
	<span id="warn"></span>
	<input type="hidden" id='s1' value='' />
	<input type="hidden" id='s2' value='' />
	<input type="hidden" id='s3' value='' />
	<input type="hidden" id='x1' value='' />
	<input type="hidden" id='x2' value='' />
	<input type="hidden" id='x3' value='' />
	<input type="hidden" id='type_name' value='${type_name }' />
	<input type="hidden" id='type_id' value='${type_id }' />
	<input type="hidden" id='jt_page1' value='1' />
	<input type="hidden" id='jt_page2' value='1' />
	<input type="hidden" id='lsqxcx_page' value='1' />
	<input type="hidden" id='dxcxjg' value='${dxcxjg }' />
	<%
		List list_sensor = (List)request.getAttribute("list_sensor");
	%>
	<input type="hidden" id="sensor_length" value="<%=list_sensor.size()%>">
	<!-- content -->
	<div class="page-header">
		<ol class="breadcrumb">
			<li><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 首页</li>
			<li>一级目录</li>
			<li class="active">${type_name }传感器实时监测</li>
		</ol>
	</div>
	<div class="container-fluid">

		<div class="row">
			<div class="col-lg-7">
				<div class="panel panel-default">
					<div class="panel-heading">
						<span>传感器截面图信息</span> <a href="#" class="dropdown-toggle pull-right" data-toggle="dropdown"> 监测截面</span>
						</a>
						<ul class="dropdown-menu pull-right">
							<c:forEach items="${list_section }" var="v" varStatus="e">
								<li><a href="javascript:changeSection('${e.index }');">截面 ${v.SECTION_NAME }</a></li>
							</c:forEach>
						</ul>
					</div>
					<div class="panel-body">
					<c:forEach items="${list_section }" var="v" varStatus="e">
						<div style="height: 350px;display: none;position: relative;" id="section_img_${e.index }">
								<img src="<%=path %>/${v.SECTION_PATH}" height="100%"  />
								<c:forEach items="${list_sensor }" var="s" varStatus="w">
									<c:if test="${s.SECTION_ID==v.SECTION_ID and s.MON_V_TOP!='' and s.MON_V_LEFT!='' }">
										<div style="background-color:white;position:absolute;width:;height:18px;z-index:1;left: ${s.MON_V_LEFT}%;top: ${s.MON_V_TOP}%;">
											<a href="javascript:refDraw('${ s.SENSOR_ID}^${ s.SENSOR_CODE}^${s.PART_UNIT }^${s.SENSOR_PSTN }^${s.PART_POINT }^${s.PART_SECOND }');">
												<span id="res${s.SENSOR_ID }">0</span>${s.PART_UNIT }
											</a>
										</div>
									</c:if>
									<c:if test="${s.SECTION_ID==v.SECTION_ID and s.MON_P_TOP>=0 and s.MON_P_LEFT>=0 }">
										<div style="position:absolute;width:;height:18px;z-index:1;left: ${s.MON_P_LEFT}%;top: ${s.MON_P_TOP+1}%;">
											<img src="<%=path%>/${s.PART_IMG}" style="width: 15px;height: 15px;"/>
										</div>
									</c:if>
								</c:forEach>
						</div>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="col-lg-5">
				<div class="panel panel-default">
					<div class="panel-heading">
						<span>实时曲线图信息</span> <a href="#" class="dropdown-toggle pull-right" data-toggle="dropdown"> <span class="glyphicon glyphicon-align-justify" aria-hidden="true"></span></a>
						<ul class="dropdown-menu pull-right">
							<li><a href="javascript:dyHighcharts('1');">打印</a></li>
							<li><a href="javascript:dcHighcharts('1');">导出</a></li>
						</ul>
					</div>
					<div class="panel-body">
						<div class="proccess" id="loading" style="display:; width: 97.6%; height: 86.7%; /* padding-top: 20%; */ margin-top: 7.2%; margin-left: 1%;">
							<b>正在加载中...请稍候</b>
						</div>
						<div style="height: 350px;" id="hcharts"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-8">
				<div class="panel panel-default">
					<div class="panel-heading">
						<span>历史曲线图信息</span>
					</div>
					<div class="panel-body">
						<div class="col-sm-4" style="width: 33.3%;">
							<div class="proccess" id="loading_ls1" style="display:; width: 97.6%; height: 107.7%; padding-top: 40%; margin-top: -4.8%; margin-left: 1%;">
								<b>正在加载中...请稍候</b>
							</div>
							<div style="height: 30%;" id="ch1"></div>
						</div>
						<div class="col-sm-4" style="width: 33.3%">
							<div class="proccess" id="loading_ls2" style="display:; width: 97.6%; height: 107.7%; padding-top: 40%; margin-top: -4.8%; margin-left: 1%;">
								<b>正在加载中...请稍候</b>
							</div>
							<div style="height: 30%;" id="ch2"></div>
						</div>
						<div class="col-sm-4" style="width: 33.3%">
							<div class="proccess" id="loading_ls3" style="display:; width: 97.6%; height: 107.7%; padding-top: 40%; margin-top: -4.8%; margin-left: 1%;">
								<b>正在加载中...请稍候</b>
							</div>
							<div style="height: 30%;" id="ch3"></div>
						</div>
					</div>
				</div>
			</div>

			<div class="col-lg-4">
				<div class="panel panel-default">
					<div class="panel-heading">
						<span>传感器时均值曲线(过去24小时)</span> <a href="#" class="dropdown-toggle pull-right" data-toggle="dropdown"> <span class="glyphicon glyphicon-align-justify" aria-hidden="true"></span></a>
						<ul class="dropdown-menu pull-right">
							<li><a href="javascript:dyHighcharts('2');">打印</a></li>
							<li><a href="javascript:dcHighcharts('2');">导出</a></li>
						</ul>
					</div>
					<div class="tab-content no-margin">
						<div class="proccess" id="loading_pjz" style="display:; width: 95.6%; height: 80.7%; /* padding-top: 20%; */ margin-top: 9.2%; margin-left: 2%;">
							<b>正在加载中...请稍候</b>
						</div>
						<div class="tab-pane active fade in" id="sspjz" style="height: 35%"></div>

					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-8">
				<div class="panel panel-default">
					<div class="panel-heading nobottom-border">
						<span>传感器信息列表</span>
					</div>

					<c:forEach items="${list_section }" var="v" varStatus="e">
						<table class="table text-center" id="section_table_${e.index }" style="display: none;">
							<tbody>
								<c:forEach items="${list_sensor }" var="s" varStatus="w">
									<c:if test="${s.SECTION_ID==v.SECTION_ID }">
										<c:if test="${w.index==0 }">
											<input type="hidden" value="${ s.SENSOR_ID}^${s.SENSOR_CODE}^${s.PART_UNIT }^${s.SENSOR_PSTN }^${s.PART_POINT }^${s.PART_SECOND }" id="gu" />
										</c:if>
											<input type="hidden" value="${ s.SENSOR_ID}^${s.SENSOR_CODE}^${s.PART_UNIT }^${s.SENSOR_PSTN }^${s.PART_POINT }^${s.PART_SECOND }" id="guop${ s.SENSOR_ID}" />
										<tr>
											<td>${s.SENSOR_CODE }</td>
											<td>${s.SENSOR_NAME }</td>
											<td><font style="color: green;"><span id="ren${s.SENSOR_ID }">0</span>${s.PART_UNIT }</font></td>
											<td><span id="st${s.SENSOR_ID }"><font style="color: #FF8040;">初始化</font> </span></td>
											<td>${s.SENSOR_PSTN }</td>
											<td>${s.TYPE_NAME }</td>
											<td style="vertical-align: inherit;"><a href="javascript:refDraw('${ s.SENSOR_ID}^${ s.SENSOR_CODE}^${s.PART_UNIT }^${s.SENSOR_PSTN }^${s.PART_POINT }^${s.PART_SECOND }');"><span class="label bg-success">查看</span></a></td>
											<td style="vertical-align: inherit;"><a href="javascript:jui('${ s.SENSOR_ID}^${ s.SENSOR_CODE}^${s.PART_UNIT }^${s.SENSOR_PSTN }^${s.PART_POINT }^${s.PART_SECOND }');"><span class="label bg-success" style="background-color: #607D8B;" id="mn${s.SENSOR_ID }">展开</span></a></td>
										</tr>
										<tr style="display: none;">
											<td colspan="8">
												<div style="height: 183px;" id="div_chart_${ s.SENSOR_ID}"></div>
											</td>
										</tr>
										<script>
											window['charts${ s.SENSOR_ID}'] = null;
											window['chartsTime${ s.SENSOR_ID}'] = null;
										</script>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
						
					</c:forEach>
					<%-- 
					<table class="table text-center">
							<tr style="background-color:#f5f5f5;border-top: 1px solid #ddd;">
								<td style="text-align: left;font-size:14px;line-height:1.42857143;color:#333">&nbsp;&nbsp;传感器历史数据查询</td>
							</tr>
							<tr style="">
								<td style="text-align: left;font-size:14px;line-height:1.42857143;color:#333">
									传感器编号：
									<select id="cxcgq">
										<c:forEach items="${list_sensor }" var="s" varStatus="w">
											<option value="${ s.SENSOR_ID}^${s.SENSOR_CODE}^${s.PART_UNIT }^${s.SENSOR_PSTN }^${s.PART_POINT }^${s.PART_SECOND }">${s.SENSOR_CODE }</option>
										</c:forEach>
									</select><br/><br/>
									时间范围：从<input type="text" readonly="readonly" id="w7" name="w7" placeholder="开始时间" class="Wdate" onFocus="WdatePicker({startDate:'%y-%M-%d ',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/> 到
									 <input type="text" readonly="readonly" id="w8" name="w8" placeholder="结束时间" class="Wdate" onFocus="WdatePicker({startDate:'%y-%M-%d ',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/><br/><br/>
									数据范围：从<input type="text" id="w9"  onkeyup="value=value.replace(/[^\d\.-]/g,'')" /> 到 
									 <input type="text" id="w10"  onkeyup="value=value.replace(/[^\d\.-]/g,'')"/>
									<a href="javascript:cxlsqx();"><span class="label bg-success">查询</span></a>
								</td>
							</tr>
							<tr id="g7" style="display: none;">
								<td style="text-align: left;">
									<a href="javascript:xq(1,2);"><span class="label bg-success">曲线查询</span></a>
									<a href="javascript:xq(2,1);"><span class="label bg-success">列表查询</span></a>
									<a href="javascript:dcCxlb();"><span class="label bg-success">导出</span></a>
									<br/>
									<div id="lscxChart1" style="width: 100%;height: 350px;display: ;"></div>
									<div id="lscxChart2" style="width: 100%;height: 350px;display: none;">
										<br/>
										<table class="table table-striped table-hover text-right index-table">
											<thead>
												<tr>
													<th class="text-left">编号</th>
													<th class="text-left">数值</th>
													<th class="text-left">时间</th>
												</tr>
											</thead>
											<tbody id="bjsjl">
											
											</tbody>
										</table>
									</div>
								</td>
							</tr>
							
						</table>
						--%>
				</div>
			</div>

			<div class="col-lg-4">
				<div class="panel panel-default">
					<div class="panel-heading">
						<span>报警信息</span>
					</div>
					<div class="panel-body text-center">
						<div class="col-md-4 color-blue" onclick="tcbjxx(1);" style="cursor:pointer;">
							<h4 class="no-margin">
								<span class="glyphicon glyphicon-stats" aria-hidden="true"></span> <span id="bjcs_1">${warning_count.Q1 }</span>次
							</h4>
							<span class=" small">本周</span>
						</div>
						<div class="col-md-4 color-orange" onclick="tcbjxx(2);" style="cursor:pointer;">
							<h4 class="no-margin">
								<span class="glyphicon glyphicon-stats" aria-hidden="true"></span> <span id="bjcs_2">${warning_count.Q2 }</span>次
							</h4>
							<span class=" small">本月</span>
						</div>
						<div class="col-md-4 color-bluegray" onclick="tcbjxx(3);" style="cursor:pointer;">
							<h4 class="no-margin">
								<span class="glyphicon glyphicon-stats" aria-hidden="true"></span> <span id="bjcs_3">${warning_count.Q3 }</span>次
							</h4>
							<span class="small">全部</span>
						</div>
					</div>
					<!-- Tabs title-->
					<ul class="nav nav-lg nav-tabs nav-justified no-margin">
						<li class="active" style="width: 50%;"><a href="#messages-1" class="small" data-toggle="tab">今日报警</a></li>
						<li style="width: 50%;"><a href="#messages-2" class="small" data-toggle="tab">昨日报警</a></li>
					</ul>
					<!-- Tabs content -->
					<div class="tab-content no-margin" style="overflow-y: auto; height: 40%">
						<div class="tab-pane active fade in" id="messages-1">
							<table class="table table-striped table-hover text-right index-table">
								<thead>
									<tr>
										<th class="text-left">编号</th>
										<th class="text-left">等级</th>
										<th class="text-left">数值</th>
										<th class="text-left">时间</th>
									</tr>
								</thead>
								<tbody id="bjsj1">
								
								</tbody>
							</table>
						</div>
						<div class="tab-pane fade" id="messages-2">
							<table class="table table-striped table-hover text-right index-table">
								<thead style="border-bottom:1px solid #ddd;">
									<tr>
										<th class="text-left">编号</th>
										<th class="text-left">等级</th>
										<th class="text-left">数值</th>
										<th class="text-left">时间</th>
									</tr>
								</thead>
								<tbody id="bjsj2">
								
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<form name="dcForm" id="dcForm" action='' method="post">
	</form>
	<script src="<%=path%>/pages/monitoring/js/monitoringType.js"></script>
	<script>
					function tcbjxx(i){
						var sUrl = '<%=path%>/monitoringController/tcbjxx?typeid='+$("#type_id").val()+'&i='+i;  
						var s = '<%=path%>/pages/monitoring/monitoringWarn.jsp?typeid='+$("#type_id").val()+'&i='+i;
						 var str = "";
						    if (window.screen) {
						        var ah = screen.availHeight - 20;
						        var aw = screen.availWidth - 10;
						        var xc = (aw - 50) / 2-400;
						        var yc = (ah - 485) / 2;
						        str += "dialogLeft:"+xc + ";";
						        str += "dialogTop:" + yc + ";";
						    }
						    var oWin=window.showModalDialog(s,window, "dialogHeight:550px;dialogWidth:850px;location:yes;toolbar=no;status=no;"+str);
					}
				</script>
	
</body>
</html>
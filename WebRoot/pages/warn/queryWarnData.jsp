<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page language="java" import="java.util.*,com.sendyago.util.common.PageBean" pageEncoding="utf-8"%>
<%@ page language="java" import="java.util.*,com.sendyago.count.controller.SystemCountService"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<title>健康监测系统</title>
<%@ include file="/pages/common/common_query2.jsp"%>
</head>
<%
	PageBean pageBean = (PageBean) request.getAttribute("pageBean");
	String menu_id = (String) request.getAttribute("shiro_menu_id");
	String role_id = (String) request.getAttribute("shiro_role_id");
%>

<body>

	<div class="page-header">
		<ol class="breadcrumb">
			<li><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 首页</li>
			<li>数据查询与统计</li>
			<li class="active">报警信息查询</li>
		</ol>
	</div>
	<div class="container-fluid">
		<div class="col-md-12">
			<div class="panel">
				<div class="panel-heading">
					<form action="" method="post" id="form1" name="form1" class="navbar-form navbar-left width100">
						<input type="hidden" id="currentPage" name="currentPage" value="1" /> <input type="hidden" id="viewFlag" name="viewFlag" value="1" /> <input type="hidden" id="mapCheckType" name="mapCheckType" value="${map.checkType}" /> <input type="hidden" id="mapPartType" name="mapPartType" value="${map.partType}" /> <input type="hidden" id="mapSectionList" name="mapSectionList" value="${map.sectionList}" /> <input type="hidden" id="shiro_menu_id" name="shiro_menu_id" value="${shiro_menu_id}" /> <input type="hidden" id="shiro_role_id" name="shiro_role_id" value="${shiro_role_id}" /> <input type="hidden" id="shiro_user_id" name="shiro_user_id" value="${shiro_user_id}" /> <input type="hidden" id="path"
							name="path" value="<%=path%>" />
						<div class="form-group">
							<div class="input-group input-group-no-border">
								<span class="input-group-addon"><font color="#ffffff">监测类型</font> </span> <select class="form-control" id="checkType" name="checkType"></select>

							</div>

							<div class="input-group input-group-no-border">
								<span class="input-group-addon"><font color="#ffffff">传感器编号</font></span> <input type="text" id="s_sensor" name="s_sensor" value="${map.s_sensor}" class="form-control" />

							</div>
							<div class="input-group input-group-no-border">
								<span class="input-group-addon"> <font color="#ffffff">日期</font></span> <span class="input-group-addon"><font color="#ffffff">从</font></span> <input type="text" name="b_date" id="b_date" value="${map.b_date}" placeholder="开始时间" onFocus="WdatePicker({startDate:'%y-%M-%d ',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" readonly="readonly" class="form-control"> <span class="input-group-addon"><font color="#ffffff">到</font></span> <input type="text" name="e_date" id="e_date" placeholder="结束时间" value="${map.e_date}" onFocus="WdatePicker({startDate:'%y-%M-%d ',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" readonly="readonly" class="form-control">

							</div>

						</div>
						<br />
						<div class="form-group">

							<div class="input-group input-group-no-border">
								<span class="input-group-addon"> <font color="#ffffff">报警数值</font></span> 
								<span class="input-group-addon"><font color="#ffffff">从</font></span> 
								<input type="text" name="zx" id="zx" value="${map.zx}" placeholder="最小数值" class="form-control"> 
								<span class="input-group-addon"><font color="#ffffff">到</font></span>
								 <input type="text" name="zd" id="zd" placeholder="最大数值" value="${map.zd}" class="form-control">

							</div>

						</div>
						<div class="form-group pull-right">
							<div class="btn-group btn-group-sm2" role="group">
								<button type="button" class="btn btn-info" onclick="javascript:sousuo('1');">
									<span class="glyphicon glyphicon-list"></span> 查询
								</button>
								<button type="button" class="btn btn-warning" onclick="javascript:exportExcel('3','<%=String.valueOf(SystemCountService.OperationButton(role_id, menu_id, "6"))%>');">
									<span class="glyphicon glyphicon-share"></span> 导出
								</button>
							</div>
						</div>
						<div class="clearfix"></div>
				</div>
				</br>

				<div class="panel-body" style="margin-top: 44px;">

					<table class="table table-striped table-hover table-bordered">
						<thead>
							<tr>
								<th width="">序号</th>
								<th width="">传感器编码</th>
								<th width="">传感器名称</th>
								<th width="">位置</th>
								<th width="">报警时间</th>
								<th width="">报警级别</th>
								<th width="">报警值</th>
								<th width="">操作</th>
							</tr>
						</thead>
						<tbody>

							<c:forEach items="${pageBean.list }" var="v" varStatus="s">
								<tr>
									<td>${(pageBean.currentPage-1)*pageBean.pageSize+s.index+1 }</td>
									<td>${v.SENSOR_CODE }</td>
									<td>${v.SENSOR_NAME }</td>
									<td>${v.SENSOR_PSTN}</td>
									<td>${v.WARNING_TIME}</td>
									<td><c:if test="${v.WARNING_LEVEL == '1'}">
											<span style="color: yellowgreen;">一级上限预警</span>
										</c:if> <c:if test="${v.WARNING_LEVEL == '2'}">
											<span style="color: darkorange;">二级上限预警</span>
										</c:if> <c:if test="${v.WARNING_LEVEL == '3'}">
											<span style="color: red;">三级上限预警</span>
										</c:if> <c:if test="${v.WARNING_LEVEL == '11'}">
											<span style="color: yellowgreen;">一级下限预警</span>
										</c:if> <c:if test="${v.WARNING_LEVEL == '22'}">
											<span style="color: darkorange;">二级下限预警</span>
										</c:if> <c:if test="${v.WARNING_LEVEL == '33'}">
											<span style="color: red;">三级下限预警</span>
										</c:if></td>


									<td>${v.WARNING_VALUE_UNIT }</td>
									<td>
										<div id="wrap">
											<section id="ajax">
												<a href="<%=path%>/pages/warn/warnLine.jsp?s_sensor=${v.SENSOR_ID}&warning_time=${v.WARNING_TIME}" class="btn btn-warning btn-xs framer" data-framer-type="ajax"> 查看报警曲线</a>
											</section>
										</div>
									</td>

								</tr>
							</c:forEach>
						</tbody>

					</table>


					<jsp:include page="../common/page.jsp">
						<jsp:param name="formId" value="form1" />
					</jsp:include>
				</div>
			</div>

		</div>

		</form>
	</div>

</body>
<script type="text/javascript" src="<%=path%>/pages/warn/js/queryWarn.js"></script>
</html>

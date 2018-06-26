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
	<input type="hidden" id="typeid" value="<%=request.getParameter("typeid")%>" />
	<input type="hidden" id="i" value="<%=request.getParameter("i")%>" />
	<input type="hidden" id="pageno" value="0" />
	<div class="page-header">
		<ol class="breadcrumb">
			<li class="active">报警信息查询</li>
		</ol>
	</div>
	<div class="container-fluid">
		<div class="col-md-12">
			<div class="panel">


				<div class="panel-body">

					<table class="table table-striped table-hover table-bordered">
						<thead>
							<tr>
								<th width="">序号</th>
								<th width="">传感器编号</th>
								<th width="">监测项目</th>
								<th width="">位置</th>
								<th width="">报警时间</th>
								<th width="">报警级别</th>
								<th width="">报警值</th>
							</tr>
						</thead>
						<tbody id="nr">

						</tbody>


					</table>


				</div>
			</div>

		</div>

	</div>

</body>
<script>

tcbjxx();
					function tcbjxx(){
						var pageno=Number($("#pageno").val())+1;
						var sUrl = '<%=path%>/monitoringController/tcbjxx?typeid=' + $("#typeid").val() + '&i=' + $("#i").val() + '&pageno=' + pageno;
		$.ajax({
			type : "POST",
			async : false,
			url : sUrl,
			dataType : "json",
			success : function(data) {
				$("#jzgd").remove();
				var str = "";
				if (data.length == 0) {
					str = str + '<tr style="text-align:center;" id="jzgd"><td colspan="7">无更多数据</td></tr>';
				} else {
					for (var i = 0; i < data.length; i++) {
						str = str + '<tr>';
						str = str + '<td>' + (Number($("#pageno").val()) * 20 + 1 + i) + '</td>';
						str = str + '<td>' + data[i].SENSOR_CODE + '</td>';
						str = str + '<td>' + data[i].SENSOR_NAME + '</td>';
						str = str + '<td>' + data[i].SENSOR_PSTN + '</td>';
						str = str + '<td>' + data[i].WARNING_TIME + '</td>';
						var war = '';
						if (data[i].WARNING_LEVEL == 1) {
							war = '一级上限预警';
						}
						if (data[i].WARNING_LEVEL == 11) {
							war = '一级下限预警';
						}
						if (data[i].WARNING_LEVEL == 2) {
							war = '二级上限预警';
						}
						if (data[i].WARNING_LEVEL == 22) {
							war = '二级下限预警';
						}
						if (data[i].WARNING_LEVEL == 3) {
							war = '三级上限预警';
						}
						if (data[i].WARNING_LEVEL == 33) {
							war = '三级下限预警';
						}
						str = str + '<td>' + war + '</td>';
						str = str + '<td>' + data[i].WARNING_VALUE + '' + data[i].PART_UNIT + '</td>';
						str = str + '</tr>';
					}
					str = str + '<tr style="text-align:center;" id="jzgd"><td colspan="7"><a href="javascript:tcbjxx();">加载更多</a></td></tr>';
					$("#pageno").val(pageno);
				}
				$("#nr").append(str);

			}
		});

	}
</script>
</html>

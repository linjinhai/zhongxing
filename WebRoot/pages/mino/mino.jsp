<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<%@ include file="../common/common_system.jsp"%>
<script src="<%=path%>/js/drag.js"></script>
<script src="<%=path%>/pages/mino/mino.js"></script>
<script src="<%=path%>/js/layer.js"></script>
<!-- 
<script src="<%=path%>/pages/mino/jquery-ui.js"></script>
 -->
<title>传感器管理 - 传感器列表</title>
<style type="text/css">
.b1 {
	width: 47px;
	background-color: #d0d3d4;
}

.b2 {
	width: 47px;
	background-color: #989b9c;
}
.yj{
	border-radius:15px;
	background-color:white;
	position:absolute;
	z-index:1;
	left: 58%;
	top: 24%;
	width: 124px;
	text-align: center;
	display: none;
}
</style>
</head>
<script>
function opentjcgq(){
	
	  
	  layer.open({
			maxmin : true,
			title : $("#symc").text(),
			type : 2,
			skin : 'layui-layer-rim',
			area : [ '800px', '600px' ],
			content : '<%=path%>/pages/mino/edit.jsp?sid='+$("#sid").val()
		});

	  
	  
	  
	  
}

</script>
<body>
	<input type="hidden" id="sid" value="<%=request.getParameter("sid")%>"/>
	<div class="yj"  >
							<span style="font-size: 26px;">         已保存         </span>
							</div>
	<input type="hidden" id="hidf" value=""/>
	<div class="page-header">
		<ol class="breadcrumb">
			<li><a href="#"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 首页</a></li>
			<li><a href="<%=path%>//sensorSection/list?t=b">截面列表查询</a></li>
			<li class="active">传感器布置</li>
		</ol>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-3">
				<div class="panel panel-default">
					<div class="panel-heading">
						<span>传感器列表</span>
						<span style="float: right;"><a href="javascript:opentjcgq();">添加传感器</a></span>
					</div>
					<div class="panel-body">
						<div style="overflow: auto;" id="wo2">
							<table border="1" bordercolor="#ddd" style="border-collapse: collapse; text-align: center;" width="100%">
								<tr style="background-color: #4c72a2; color: white; font-size: 12px;" id="gy">
									<td width="12%">模型图片</td>
									<td>传感器编号</td>
									<td width="25%">传感器位置</td>
									<td width="21%">数值位置</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="col-lg-9">
				<div class="panel panel-default">
					<div class="panel-heading">
						<span id="symc"></span><a href="<%=path %>/sensorSection/list?t=b" class="dropdown-toggle pull-right" data-toggle=""> 返回</span>
						</a>

					</div>
					<div class="panel-body">

						<div style="" id="wo1">
							<%--
							<div style="position: absolute; top: 0%; left: 0%;" class="dragMe">
								<img src="<%=path%>/upload/2.png" style="width: 35px; height: 35px;" />
							</div>
							<div style="position: absolute; top: 10%; left: 0%;" class="dragMe">
								<img src="<%=path%>/upload/2.png" style="width: 35px; height: 35px;" />
							</div>
							<div style="background-color:white;position:absolute;width:;height:18px;z-index:1;left: 10%;top: 0%;" class="dragMe">
								<span id="">B01-SZY-014</span>
							</div>
							 --%>

							<img src="" style="height: 100%" id="dt" />
						</div>

					</div>
				</div>
			</div>

		</div>
	</div>
</body>
</html>

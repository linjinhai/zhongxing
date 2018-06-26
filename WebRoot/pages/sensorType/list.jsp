<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<%@ include file="../common/common_system.jsp"%>
<script src="../pages/sensorType/js/sensorType.js"></script>
<title>传感器类型管理 - 传感器类型列表</title>
</head>
<body>
	<div class="page-header">
		<ol class="breadcrumb">
			<li><a href="#"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 首页</a></li>
			<li><a href="#" onclick="javascript:crumb('sensorType/list');">传感器类型管理</a></li>
			<li class="active">传感器类型列表</li>
		</ol>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="panel">
					<div class="panel-heading">
						<div class="navbar-form navbar-left">
							<div class="form-group">
								<div class="input-group input-group-no-border">
									<input type="text" id="search" name="search" value="${pager.search}" placeholder="关键字..." class="form-control form-heading">
									<span class="input-group-addon"><a href="javascript:pager('1');">搜索</a></span>
								</div>
							</div>
						</div>
						<div class="btn-group-sm pull-right" role="group">
							<button type="button" class="btn btn-info" onclick="javascript:add('sensorType/insertPage','1');"><span class="glyphicon glyphicon-plus"></span> 添加</button>
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="panel-body notop-padding">
						<table class="table table-striped table-hover table-bordered table-condensed">
							<thead>
								<tr>
									<th width="40">序号</th>
									<th width="">传感器类型名称</th>
									<th width="">监测类型</th>
									<th width="150">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pager.list}" var="sensorType" varStatus="i">
								<tr>
									<td>${i.index+1}</td>
									<td>${sensorType.TYPE_NAME}</td>
									<td>
										
										<c:if test="${sensorType.TYPE_FLAG==1}">传感器监测</c:if>
										<c:if test="${sensorType.TYPE_FLAG==2}">视频监控</c:if>
									</td>
									<td>
										<div class="btn-group-sm">
											<button type="button" class="btn btn-warning" onclick="javascript:upd('sensorType/updatePage','${sensorType.TYPE_ID}','2');"><span class="glyphicon glyphicon-edit"></span> 修改</button>
											<button type="button" class="btn btn-danger" onclick="javascript:del('sensorType/update','${sensorType.TYPE_ID}','3');"><span class="glyphicon glyphicon-trash"></span> 删除</button>
										</div>
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
						<jsp:include page="../common/pager.jsp"></jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

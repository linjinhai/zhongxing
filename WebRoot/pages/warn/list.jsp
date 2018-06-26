<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<%@ include file="../common/common_system.jsp"%>
<script src="../pages/warn/js/warn.js"></script>
<title>预警阀值管理 - 传感器列表</title>
</head>
<body>
	<div class="page-header">
		<ol class="breadcrumb">
			<li><a href="#"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 首页</a></li>
			<li><a href="#" onclick="javascript:crumb('warn/list');">预警阀值管理</a></li>
			<li class="active">传感器列表</li>
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
						<div class="clearfix"></div>
					</div>
					<div class="panel-body notop-padding">
						<table class="table table-striped table-hover table-bordered table-condensed">
							<thead>
								<tr>
									<th width="40">序号</th>
									<th width="">传感器名称</th>
									<th width="10%">传感器编号</th>
									<th width="10%">传感器位置</th>
									<th width="9%">三级上限预警</th>
									<th width="9%">二级上限预警</th>
									<th width="9%">一级上限预警</th>
									<th width="9%">一级下限预警</th>
									<th width="9%">二级下限预警</th>
									<th width="9%">三级下限预警</th>
									<th width="80">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pager.list}" var="warn" varStatus="i">
								<tr>
									<td>${i.index+1}</td>
									<td>${warn.SENSOR_NAME}</td>
									<td>${warn.SENSOR_CODE}</td>
									<td>${warn.SENSOR_PSTN}</td>
									<td>${warn.WARN_LV3}<c:if test="${!empty warn.WARN_LV3}">${warn.PART_UNIT}</c:if></td>
									<td>${warn.WARN_LV2}<c:if test="${!empty warn.WARN_LV2}">${warn.PART_UNIT}</c:if></td>
									<td>${warn.WARN_LV1}<c:if test="${!empty warn.WARN_LV1}">${warn.PART_UNIT}</c:if></td>
									<td>${warn.WARN_LV11}<c:if test="${!empty warn.WARN_LV11}">${warn.PART_UNIT}</c:if></td>
									<td>${warn.WARN_LV22}<c:if test="${!empty warn.WARN_LV22}">${warn.PART_UNIT}</c:if></td>
									<td>${warn.WARN_LV33}<c:if test="${!empty warn.WARN_LV33}">${warn.PART_UNIT}</c:if></td>
									<td>
										<div class="btn-group-sm">
											<button type="button" class="btn btn-warning" onclick="javascript:upd('warn/updatePage','${warn.SENSOR_ID}','2');"><span class="glyphicon glyphicon-edit"></span> 修改</button>
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

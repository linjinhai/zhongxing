<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<%@ include file="../common/common_system.jsp"%>
<script src="../pages/log/js/log.js"></script>
<title>日志管理 - 日志列表</title>
</head>
<body>
	<div class="page-header">
		<ol class="breadcrumb">
			<li><a href="#"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 首页</a></li>
			<li><a href="#" onclick="javascript:crumb('menu/list');">日志管理</a></li>
			<li class="active">日志列表</li>
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
						<form action="" method="post" id="form1" name="form1"
							class="navbar-form navbar-left width100">
						<button type="button" class="btn btn-danger " onclick="javascript:del('log/del','1','3');"><span class="glyphicon glyphicon-trash"></span> 清空</button>
						<button type="button" class="btn btn-warning" onclick="javascript:exportExcel('log/export');"><span class="glyphicon glyphicon-share"></span> 导出</button>
						</form>
						</div>
						<div class="btn-group-sm pull-right" role="group">
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="panel-body notop-padding">
						<table class="table table-striped table-hover table-bordered table-condensed">
							<thead>
								<tr>
									<th width="40">序号</th>
									<th width="">操作用户名称</th>
									<th width="">操作菜单名称</th>
									<th width="">操作功能</th>
									<th width="150">操作时间</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pager.list}" var="log" varStatus="i">
								<tr>
									<td>${i.index+1}</td>
									<td>${log.USER_NAME}</td>
									<td>${log.MENU_NAME}</td>
									<td>${log.BUTTON_NAME}</td>
									<td>${log.ADD_TIME}</td>
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

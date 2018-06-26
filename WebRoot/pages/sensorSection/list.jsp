<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<%@ include file="../common/common_system.jsp"%>
<script src="../pages/sensorSection/js/sensorSection.js"></script>
<SCRIPT>
function ksbj(sid,spath){
	location.href="<%=path%>/pages/mino/mino.jsp?sid=" + sid;
	}
function ksbjsy(sid,spath){
	location.href="<%=path%>/pages/mino/mino.jsp?sid=-1";
	}
</SCRIPT>
<title>传感器截面管理 - 传感器截面列表</title>
</head>
<body>
	<div class="page-header">
		<ol class="breadcrumb">
			<li><a href="#"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 首页</a></li>
			<li><a href="#" onclick="javascript:crumb('sensorSection/list');">传感器截面管理</a></li>
			<li class="active">传感器截面列表</li>
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
									<input type="text" id="search" name="search" value="${pager.search}" placeholder="关键字..." class="form-control form-heading"> <span class="input-group-addon"><a href="javascript:pager('1');">搜索</a></span>
								</div>
							</div>
						</div>
						<div class="btn-group-sm pull-right" role="group">
							<c:if test="${t == 'j' }">
								<button type="button" class="btn btn-info" onclick="javascript:add('sensorSection/insertPage','1');">
									<span class="glyphicon glyphicon-plus"></span> 添加
								</button>
								<!-- 
								<button type="button" class="btn btn-info" onclick="javascript:add('sensorSection/insertPage?t=sy','1');">
									<span class="glyphicon glyphicon-plus"></span> 修改首页截面
								</button>
								 -->
							</c:if>
							<c:if test="${t == 'b' }">
								<!--  
								<button type="button" class="btn btn-warning" onclick="javascript:ksbjsy('${sensorSection.SECTION_ID}','${sensorSection.SECTION_PATH}');">
									<span class="glyphicon glyphicon-edit" style="background-color:#f0ad4e;"></span> 开始布置首页
								</button>
							-->
							</c:if>
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="panel-body notop-padding">
						<table class="table table-striped table-hover table-bordered table-condensed">
							<thead>
								<tr>
									<th width="40">序号</th>
									<th width="">传感器截面名称</th>
									<th width="28%">传感器截面模型</th>
									<th width="28%">传感器类型</th>
									<th width="150">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pager.list}" var="sensorSection" varStatus="i">
									<tr>
										<td>${i.index+1}</td>
										<td>${sensorSection.SECTION_NAME}</td>
										<td><img src="<%=path %>/${sensorSection.SECTION_PATH}" class="sectionImg" /></td>
										<td><c:if test="${sensorSection.TYPE_ID=='-1' }">首页</c:if> <c:if test="${sensorSection.TYPE_ID!='-1' }">${sensorSection.TYPE_NAME}</c:if></td>
										<td>
											<div class="btn-group-sm">
												<c:if test="${t == 'j' }">
													<button type="button" class="btn btn-warning" onclick="javascript:upd('sensorSection/updatePage','${sensorSection.SECTION_ID}','2');">
														<span class="glyphicon glyphicon-edit"></span> 修改
													</button>
													<button type="button" class="btn btn-danger" onclick="javascript:sectionDel('${sensorSection.SECTION_ID}','3');">
														<span class="glyphicon glyphicon-trash"></span> 删除
													</button>
												</c:if>
												<c:if test="${t == 'b' }">
													<c:if test="${sensorSection.TYPE_ID !='-1' }">
														<button type="button" class="btn btn-warning" onclick="javascript:ksbj('${sensorSection.SECTION_ID}','${sensorSection.SECTION_PATH}');">
															<span class="glyphicon glyphicon-edit"></span> 开始布置
														</button>
													</c:if>
													<c:if test="${sensorSection.TYPE_ID =='-1' }">
														<button type="button" class="btn btn-warning" onclick="javascript:ksbjsy('${sensorSection.SECTION_ID}','${sensorSection.SECTION_PATH}');">
															<span class="glyphicon glyphicon-edit"></span> 开始布置
														</button>
													</c:if>
												</c:if>
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
	<script>
		$(function() {
			$("#pagerForm").prepend("<input type='hidden' name='t' value='${t}' />");
		});
	</script>
</body>
</html>

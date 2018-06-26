<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<%@ include file="../common/common_system.jsp"%>
<c:if test="${type=='' }">
<title>人工巡检 - 记录查询</title>
</c:if>
<c:if test="${type=='aq' }">
<title>安全评估 - 记录查询</title>
</c:if>
</head>

<body>
		<div class="page-header">
			<ol class="breadcrumb">
				<li><a href="#"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 首页</a></li>
				<c:if test="${type=='' }">
				<li><a href="#" onclick="javascript:crumb('essence/queryrgxj');">人工巡检</a></li>
				</c:if>
				<c:if test="${type=='aq' }">
				<li><a href="#" onclick="javascript:crumb('essence/queryrgxj?type=aq');">安全评估</a></li>
				</c:if>
				<li class="active">记录查询</li>
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
								
							</div>
							<div class="clearfix"></div>
						</div>
						<div class="panel-body notop-padding">
							<table class="table table-striped table-hover table-bordered table-condensed">
								<thead>
									<tr>
										<th width="5%">序号</th>
										<th width="20%">巡检日期</th>
										<th width="">巡检类型</th>
										<th width="">巡检员</th>
										<c:if test="${type=='aq' }"><th>巡检结果情况</th></c:if>
										<th width="17%">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${pager.list}" var="v" varStatus="i">
									<tr>
										<td>${i.index+1}</td>
										<td>${v.INSPECT_DATE}</td>
										<td>
											<c:if test="${v.INSPECT_TYPE eq '1'}">临时巡检</c:if>
											<c:if test="${v.INSPECT_TYPE eq '2'}">月度巡检</c:if>
											<c:if test="${v.INSPECT_TYPE eq '3'}">季度巡检</c:if>
											<c:if test="${v.INSPECT_TYPE eq '4'}">年度巡检</c:if>
										</td>
										<td>${v.INSPECT_USER}</td>
										<c:if test="${type=='aq' }"><td>
											<c:if test="${v.INSPECT_FLAG eq '1'}">
												<font style="color: red">有改动（建议重新评估）</font>
											</c:if>
											<c:if test="${v.INSPECT_FLAG eq '2'}">
												<font style="color: green">无改动</font>
											</c:if>
										</td></c:if>
										<td>
											<div class="btn-group-sm">
											<c:if test="${type=='' }">
												<button type="button" class="btn btn-warning btn-xs" onclick="javascript:upd('essence/toadd','${v.INSPECT_ID}','2');"><span class="glyphicon glyphicon-edit"></span> 修改</button>
												<button type="button" class="btn btn-danger btn-xs" onclick="javascript:del('essence/addRgxjEnd','${v.INSPECT_ID}','3');"><span class="glyphicon glyphicon-trash"></span> 删除</button>
												<button type="button" class="btn btn-warning btn-xs" onclick="javascript:upd('/pages/essence/editGjData.jsp','${v.INSPECT_ID}','11');"><span class="glyphicon glyphicon-trash"></span> 编辑巡检</button>
											</c:if>
											<c:if test="${type=='aq' }">
												<button type="button" class="btn btn-info btn-xs" onclick="javascript:upd('essence/toAnpg','${v.INSPECT_ID}','13');"><span class="glyphicon glyphicon-check"></span> 评估</button>
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
$(function(){
	$("#pagerForm").prepend("<input type='hidden' name='type' value='${type}' />");
});
</script>
</body>
</html>

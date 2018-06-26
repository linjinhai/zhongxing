<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<%@ include file="../common/common_system.jsp"%>
<title>评定参数管理 - 评定参数列表</title>
</head>
<body>
		<div class="page-header">
			<ol class="breadcrumb">
				<li><a href="#"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 首页</a></li>
				<li><a href="#" onclick="javascript:crumb('paramassess/list');">评定参数管理</a></li>
				<li class="active">评定参数列表</li>
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
									<span class="input-group-addon"><font style="color: white;">评定项</font></span>
										<select id="search" name="search" class="form-control" >
                                        		<option value="">[请选择]</option>
                                        		<c:forEach items="${structlist }" var="s">
                                        		<option value="${s.STRUCT_ID }" <c:if test="${s.STRUCT_ID eq search}">selected</c:if>>${s.STRUCT_NAME}(${s.DATA_NAME } ---
                                        		<c:if test="${s.DATA_PID eq 1}">上部结构</c:if>
                                        	    <c:if test="${s.DATA_PID eq 2}">下部结构</c:if>
                                        	    <c:if test="${s.DATA_PID eq 3}">桥面系</c:if>
                                        		)</option>
                                        		</c:forEach>
                                        	</select>
										<span class="input-group-addon"><a href="javascript:pager('1');">搜索</a></span>

                                        	</div>
								</div>
							</div>
							<div class="btn-group-sm pull-right" role="group">
								<button type="button" class="btn btn-info btn-xs" onclick="javascript:add('paramassess/insertPage','1');"><span class="glyphicon glyphicon-plus"></span> 添加</button>
							</div>
							<div class="clearfix"></div>
						</div>
						<div class="panel-body notop-padding">
							<table class="table table-striped table-hover table-bordered table-condensed">
								<thead>
									<tr>
										<th width="40">序号</th>
										<th width="">所在方位</th>
										<th width="25%">评定系统名称</th>
										<th width="15%">评定项名称</th>
										<th width="10%">标度</th>
										<th width="10%">评定标准类别</th>
										<th width="120">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${pager.list}" var="assess" varStatus="i">
									<tr>
										<td>${i.index+1}</td>
										<td><c:if test="${assess.DATA_PID eq '1'}">上部结构</c:if><c:if test="${assess.DATA_PID eq '2'}">下部结构</c:if><c:if test="${assess.DATA_PID eq '3'}">桥面系</c:if></td>
										<td>${assess.DATA_NAME }</td>
										<td>${assess.STRUCT_NAME}</td>
										<td>
											<c:if test="${assess.DESC_INDEX eq '1'}">1</c:if>
											<c:if test="${assess.DESC_INDEX eq '2'}">2</c:if>
											<c:if test="${assess.DESC_INDEX eq '3'}">3</c:if>
											<c:if test="${assess.DESC_INDEX eq '4'}">4</c:if>
											<c:if test="${assess.DESC_INDEX eq '5'}">5</c:if>
										</td>
										<td>
											<c:if test="${assess.PARAM_TYPE eq '3'}">三类</c:if>
											<c:if test="${assess.PARAM_TYPE eq '4'}">四类</c:if>
											<c:if test="${assess.PARAM_TYPE eq '5'}">五类</c:if>
										</td>
										<td>
											<div class="btn-group-sm">
												<button type="button" class="btn btn-warning btn-xs" onclick="javascript:upd('paramassess/updatePage','${assess.PARAM_ID}','2');"><span class="glyphicon glyphicon-edit"></span> 修改</button>
												<button type="button" class="btn btn-danger btn-xs" onclick="javascript:del('paramassess/update','${assess.PARAM_ID}','3');"><span class="glyphicon glyphicon-trash"></span> 删除</button>
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

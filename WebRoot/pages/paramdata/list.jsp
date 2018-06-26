<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<%@ include file="../common/common_system.jsp"%>
<title>评定系统管理管理 - 评定系统列表</title>
</head>
<body>
 <c:if test="${ judge eq 'no'}">
			<script type="text/javascript">
			alert("该评定系统正在使用,不能删除");
			</script>
	   </c:if>
		<div class="page-header">
			<ol class="breadcrumb">
				<li><a href="#"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 首页</a></li>
				<li><a href="#" onclick="javascript:crumb('paramdata/list');">评定系统管理管理</a></li>
				<li class="active">评定系统列表</li>
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
									<span class="input-group-addon"><font style="color: white;">所在方位</font></span>
										<select id="searchone" name="searchone" class="form-control" >
                                        		<option value="">[请选择]</option>
                                        			<option VALUE="1" <c:if test="${searchone eq 1}">selected</c:if>>上部结构</option>
                                        			<option VALUE="2" <c:if test="${searchone eq 2}">selected</c:if>>下部结构</option>
                                        			<option VALUE="3" <c:if test="${searchone eq 3}">selected</c:if>>桥面系</option>
                                        	</select>
                                        	</div>
                                        	
                                        		<div class="input-group input-group-no-border">
                                        		<span class="input-group-addon"><font style="color: white;">是否使用</font></span>
										<select id="searchtwo" name="searchtwo" class="form-control" >
                                        		<option value="">[请选择]</option>
                                        			<option VALUE="0" <c:if test="${searchtwo eq 0}">selected</c:if>>使用</option>
                                        			<option VALUE="1" <c:if test="${searchtwo eq 1}">selected</c:if>>未使用</option>
                                        			
                                        	</select>
                                        	</div>
										<div class="input-group input-group-no-border">
										<input type="text" id="search" name="search" value="${pager.search}" placeholder="评定系统名称..." class="form-control form-heading">
										<span class="input-group-addon"><a href="javascript:pager('1');">搜索</a></span>
									</div>
								</div>
							</div>
							<div class="btn-group-sm pull-right" role="group">
								<button type="button" class="btn btn-info btn-xs" onclick="javascript:add('paramdata/insertPage','1');"><span class="glyphicon glyphicon-plus"></span> 添加</button>
							</div>
							<div class="clearfix"></div>
						</div>
						<div class="panel-body notop-padding">
							<table class="table table-striped table-hover table-bordered table-condensed">
								<thead>
									<tr>
										<th width="40">序号</th>
										<th width="">评定系统名称</th>
										<th width="20%">所在方位</th>
										<th width="20%">是否使用</th>
										<th width="20%">本项权重分数</th>
										<th width="120">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${pager.list}" var="data" varStatus="i">
									<tr>
										<td>${i.index+1}</td>
										<td>${data.DATA_NAME}</td>
										<td><c:if test="${data.DATA_PID eq '1'}">上部结构</c:if><c:if test="${data.DATA_PID eq '2'}">下部结构</c:if><c:if test="${data.DATA_PID eq '3'}">桥面系</c:if></td>
										<td><c:if test="${data.DATA_FLAG eq '0'}">使用</c:if><c:if test="${data.DATA_FLAG eq '1'}">未使用</c:if></td>
										<td>${data.DATA_SCORE}</td>
										<td>
											<div class="btn-group-sm">
												<button type="button" class="btn btn-warning btn-xs" onclick="javascript:upd('paramdata/updatePage','${data.DATA_ID}','2');"><span class="glyphicon glyphicon-edit"></span> 修改</button>
												<button type="button" class="btn btn-danger btn-xs" onclick="javascript:del('paramdata/update','${data.DATA_ID}','3');"><span class="glyphicon glyphicon-trash"></span> 删除</button>
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

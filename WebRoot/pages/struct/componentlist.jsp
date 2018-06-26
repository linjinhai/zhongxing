<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<%@ include file="../common/common_system.jsp"%>
<title>构件管理 - 构件列表</title>
</head>
<body>
		<div class="page-header">
			<ol class="breadcrumb">
				<li><a href="#"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 首页</a></li>
				<li><a href="#" onclick="javascript:crumb('struct/componentlist');">构件管理</a></li>
				<li class="active">构件列表</li>
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
									<input type="hidden" id="path" name="path" value="<%=path%>" />
									<span class="input-group-addon"><font style="color: white;">所在方位</font></span>
                                        	  <select    id="searchtwo"     name="searchtwo"  class="form-control"  onchange="getData()">
													<option value="">[请选择]</option>
                                        			<option VALUE="1" <c:if test="${searchtwo eq 1}">selected</c:if>>上部结构</option>
                                        			<option VALUE="2" <c:if test="${searchtwo eq 2}">selected</c:if>>下部结构</option>
                                        			<option VALUE="3" <c:if test="${searchtwo eq 3}">selected</c:if>>桥面系</option>
                                            </select>
                                        	</div>
                                        	
                                        		<div class="input-group input-group-no-border">
                                        	<span class="input-group-addon"><font style="color: white;">所属评定系统</font></span>
                                        	  <select class="form-control"   id="searchone" name="searchone"  >
                                              <option value="">[请选择]</option>
                                        			<c:forEach items="${datalist}" var="data">
                                        	    <option value="${data.DATA_ID }" <c:if test="${searchone eq data.DATA_ID}">selected</c:if>>${data.DATA_NAME}
                                        	    </option>		
                                        			
                                        			</c:forEach>
                                            </select>
                                        	</div>
										<div class="input-group input-group-no-border">
										<input type="text" id="search" name="search" value="${pager.search}" placeholder="构件名称..." class="form-control form-heading">
										<span class="input-group-addon"><a href="javascript:pager('1');">搜索</a></span>
									</div>
								</div>
							</div>
							<div class="btn-group-sm pull-right" role="group">
								<button type="button" class="btn btn-info btn-xs" onclick="javascript:add('struct/insertcpPage','1');"><span class="glyphicon glyphicon-plus"></span> 添加</button>
							</div>
							<div class="clearfix"></div>
						</div>
						<div class="panel-body notop-padding">
							<table class="table table-striped table-hover table-bordered table-condensed">
								<thead>
									<tr>
										<th width="40">序号</th>
										<th width="">构件名称</th>
										<th width="40%">所属评估系统</th>
										<th width="120">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${pager.list}" var="struct" varStatus="i">
									<tr>
										<td>${i.index+1}</td>
										<td>${struct.STRUCT_NAME}</td>
										<td>${struct.DATA_NAME }</td>
										<td>
											<div class="btn-group-sm">
												<button type="button" class="btn btn-warning btn-xs" onclick="javascript:upd('struct/updatecpPage','${struct.STRUCT_ID}','2');"><span class="glyphicon glyphicon-edit"></span> 修改</button>
												<button type="button" class="btn btn-danger btn-xs" onclick="javascript:del('struct/cpupdate','${struct.STRUCT_ID}','3');"><span class="glyphicon glyphicon-trash"></span> 删除</button>
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
  <script type="text/javascript" src="<%=path%>/pages/struct/js/structlist.js" ></script>
</html>

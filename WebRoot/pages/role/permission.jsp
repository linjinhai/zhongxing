<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<%@ include file="../common/common_system.jsp"%>
<script src="../pages/role/js/role.js"></script>
<title>角色管理 - 权限编辑</title>
</head>
<body>
	<div class="page-header">
		<ol class="breadcrumb">
			<li><a href="#"><span class="glyphicon glyphicon-home"
					aria-hidden="true"></span> 首页</a></li>
			<li><a href="javascript:crumb('role/list');">角色管理</a></li>
			<li class="active">权限编辑</li>
		</ol>
	</div>

	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="panel">
					<div class="panel-heading">角色管理</div>
					<form action="<%=path%>/role/permission" id="z_form" class="form-horizontal">
						<input type="hidden" name="flag" value="permission" />
						<div class="panel-body">
							<input type="hidden" name="role_id" value="${role_id}" />
							<legend>
								<strong>权限</strong> 编辑
							</legend>
							<div class="col-md-offset-2 col-md-8">
								<table class="table text-center table-right table-hover">
									<tr class="active">
										<td>全选 <input id="allCheck" type="checkbox" /></td>
										<c:forEach begin="0" end="${max_size}">
											<td>&nbsp;</td>
										</c:forEach>
									</tr>
									<c:forEach items="${menu_list}" var="menu" varStatus="i">
										<c:if test="${menu.IS_CHILD eq '0'}">
											<tr class="success">
												<td><strong>${menu.MENU_NAME}</strong></td>
												<td><input type="checkbox" name="menu_id" value="${menu.MENU_ID}" id="${menu.MENU_ID}" onclick="javascript:pcheck('${menu.MENU_ID}');" /> 显示</td>
												<c:forEach begin="0" end="${max_size - 1}">
													<td>&nbsp;</td>
												</c:forEach>
											</tr>
										</c:if>
										<c:if test="${menu.IS_CHILD eq '1'}">
											<tr>
												<td>${menu.MENU_NAME}</td>
												<td><input type="checkbox" id="${menu.MENU_ID}" pid="${menu.MENU_PID}" name="menu_id" value="${menu.MENU_ID}" onclick="javascript:ccheck('${menu.MENU_PID}','${menu.MENU_ID}');" <c:forEach items="${role_menu_list}" var="roleMenu" varStatus="j"><c:if test="${roleMenu.MENU_ID eq menu.MENU_ID}">checked</c:if></c:forEach> /> 显示</td>
												<c:forEach items="${menu_button_list}" var="menu_button" varStatus="k">
													<c:if test="${menu.MENU_ID eq menu_button.MENU_ID and not empty menu_button.BUTTON_ID}"><td><input type="checkbox" id="button_id" mid="${menu.MENU_ID}" ppid="${menu.MENU_PID}" name="button_id" onclick="javascript:bcheck('${menu.MENU_ID}');" value="${role_id},${menu.MENU_ID},${menu_button.BUTTON_ID}" <c:forEach items="${role_menu_button_list}" var="role_button" varStatus="l"><c:if test="${menu_button.BUTTON_ID eq role_button.BUTTON_ID}">checked</c:if></c:forEach> /> ${menu_button.BUTTON_NAME}</td></c:if>
													<c:if test="${menu.MENU_ID eq menu_button.MENU_ID and empty menu_button.BUTTON_ID}"><td>&nbsp;</td></c:if>
												</c:forEach>
											</tr>
										</c:if>
									</c:forEach>
								</table>
							</div>
						</div>
						<div class="panel-body notop-padding">
							<div class="form-actions">
								<div class="row">
									<div class="col-sm-offset-5 col-sm-7">
										<button type="submit" class="btn btn-success">确 定</button>
										<button type="reset" class="btn btn-bluegray">重 置</button>
										<button type="button" class="btn btn-default" onclick="javascript:prev();">返回</button>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
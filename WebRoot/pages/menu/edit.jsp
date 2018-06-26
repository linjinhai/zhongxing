<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<%@ include file="../common/common_system.jsp"%>
<script src="../pages/menu/js/menu.js"></script>
<title>菜单管理 - 菜单编辑</title>
</head>
<body>
	<div class="page-header">
		<ol class="breadcrumb">
			<li><a href="#"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 首页</a></li>
			<li><a href="javascript:crumb('menu/list');">菜单管理</a></li>
			<li class="active">菜单编辑</li>
		</ol>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="panel">
					<div class="panel-heading">菜单管理</div>
					<div class="panel-body">
						<form action="<%=path%>/menu/update" id="z_form" class="form-horizontal">
							<c:if test="${empty object.MENU_ID}">
								<input type="hidden" name="flag" value="insert" />
							</c:if>
							<c:if test="${!empty object.MENU_ID}">
								<input type="hidden" name="flag" value="update" />
								<input type="hidden" name="menu_id" value="${object.MENU_ID}" />
							</c:if>
							<fieldset>
								<legend>
									<strong>菜单</strong> 编辑
								</legend>
								<div class="form-group">
                                    <label for="hint-field" class="col-sm-2 control-label">
                                       	 <font class="condition">*</font>菜单名称
                                    </label>
                                    <div class="col-sm-4">
                                        <input type="text" id="menu_name" name="menu_name" value="${object.MENU_NAME}" class="form-control" required />
                                    </div>
                                    <label class="col-sm-0 control-label">
                                       	<font class="explain"></font>
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label for="hint-field" class="col-sm-2 control-label">
                                       	 <font class="condition">*</font>菜单路径
                                    </label>
                                    <div class="col-sm-4">
                                        <input type="text" id="menu_path" name="menu_path" value="${object.MENU_PATH}" class="form-control" required />
                                    </div>
                                    <label class="col-sm-0 control-label">
                                       	<font class="explain"></font>
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label for="hint-field" class="col-sm-2 control-label">
                                       	 <font class="condition">*</font>菜单等级
                                    </label>
                                    <div class="col-sm-4">
                                    	<select id="is_child" name="is_child" class="form-control" required >
                                       		<option value="">[请选择]</option>
                                       		<option value="0" <c:if test="${object.IS_CHILD eq 0}">selected</c:if>>父菜单</option>
                                       		<option value="1" <c:if test="${object.IS_CHILD eq 1}">selected</c:if>>子菜单</option>
                                       	</select>
                                    </div>
                                    <label class="col-sm-0 control-label">
                                       	<font class="explain"></font>
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label for="hint-field" class="col-sm-2 control-label">
                                       	 <font class="condition">*</font>父菜单
                                    </label>
                                    <div class="col-sm-4">
                                    	<select id="menu_pid" name="menu_pid" class="form-control">
                                       		<option value="">[请选择]</option>
                                       		<c:forEach items="${list}" var="menu" varStatus="i">
                                       			<c:if test="${menu.IS_CHILD eq 0}">
                                       				<option value="${menu.MENU_ID}" <c:if test="${menu.MENU_ID eq object.MENU_PID}">selected</c:if>>${menu.MENU_NAME}</option>
                                       			</c:if>
                                       		</c:forEach>
                                       	</select>
                                    </div>
                                    <label class="col-sm-0 control-label">
                                       	<font class="explain"></font>
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label for="hint-field" class="col-sm-2 control-label">
                                       	 样式名称
                                    </label>
                                    <div class="col-sm-4">
                                        <input type="text" id="menu_img" name="menu_img" value="${object.MENU_IMG}" class="form-control" />
                                    </div>
                                    <label class="col-sm-0 control-label">
                                       	<font class="explain"></font>
                                    </label>
                                </div>
                                <c:if test="${object.IS_CHILD eq 1 or empty object.MENU_ID}">
	                                <div class="form-group">
	                                    <label for="hint-field" class="col-sm-2 control-label">
	                                       	 按钮权限
	                                    </label>
	                                    <div class="col-sm-4" style="margin-top:7px;">
	                                    	<c:forEach items="${button_list}" var="button" varStatus="i">
	                                    		<input type="checkBox" name="button_type_id" value="${button.BUTTON_TYPE_ID}" style="margin-right:1px;" 
	                                    			<c:forEach items="${menu_button_list}" var="menu_button" varStatus="j">
	                                    				<c:if test="${menu_button.BUTTON_TYPE_ID eq button.BUTTON_TYPE_ID}">checked</c:if>
	                                    			</c:forEach>
	                                    		/>${button.BUTTON_NAME}&nbsp;&nbsp;&nbsp;&nbsp;
	                                    	</c:forEach>
	                                    </div>
	                                    <label class="col-sm-0 control-label">
	                                       	<font class="explain"></font>
	                                    </label>
	                                </div>
	                            </c:if>
							</fieldset>
							<div class="form-actions">
                                   <div class="row">
                                       <div class="col-sm-offset-3 col-sm-3">
                                           <button type="submit" class="btn btn-info">保存</button>
										<button type="button" class="btn btn-default" onclick="javascript:prev();">返回</button>
                                       </div>
                                   </div>
                               </div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
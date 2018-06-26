<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<%@ include file="../common/common_system.jsp"%>
<script src="../pages/user/js/user.js"></script>
<title>用户管理 - 用户编辑</title>
</head>
<body>
	<div class="page-header">
		<ol class="breadcrumb">
			<li><a href="#"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 首页</a></li>
			<li><a href="javascript:crumb('user/list');">用户管理</a></li>
			<li class="active">用户编辑</li>
		</ol>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="panel">
					<div class="panel-heading">用户管理</div>
					<div class="panel-body">
						<form action="<%=path%>/user/update" id="z_form" class="form-horizontal">
							<c:if test="${tag eq 1}">
								<input type="hidden" name="tag" value="${tag}" />
								<c:if test="${!empty zz}">
									<script type="text/javascript">
										alert("修改成功");
									</script>
								</c:if>
							</c:if>
							<c:if test="${empty object.USER_ID}">
								<input type="hidden" name="flag" value="insert" />
							</c:if>
							<c:if test="${!empty object.USER_ID}">
								<input type="hidden" name="flag" value="update" />
							</c:if>
							<fieldset>
								<legend>
									<strong>用户</strong> 编辑
								</legend>
									<div class="form-group">
                                        <label for="hint-field" class="col-sm-2 control-label">
                                           	 <font class="condition">*</font>登录ID
                                        </label>
                                        <div class="col-sm-4">
                                            <input type="text" id="user_id" name="user_id" value="${object.USER_ID}" class="form-control" required <c:if test="${!empty object.USER_ID}">readonly</c:if> />
                                       	</div>
                                       	<label class="col-sm-0 control-label">
                                           	<font class="explain">不能重复，推荐英文、数字、_，编辑时不可修改</font>
                                        </label>
                                    </div>
                                    <div class="form-group">
                                        <label for="hint-field" class="col-sm-2 control-label">
                                           	<font class="condition">*</font>密码
                                        </label>
                                        <div class="col-sm-4">
                                            <input type="password" id="user_pass" name="user_pass" value="${object.USER_PASS}" class="form-control" required />
                                        </div>
                                        <label class="col-sm-0 control-label">
                                           	<font class="explain">建议填写较为复杂的密码</font>
                                        </label>
                                    </div>
                                    <div class="form-group">
                                        <label for="hint-field" class="col-sm-2 control-label">
                                           	<font class="condition">*</font>确认密码
                                        </label>
                                        <div class="col-sm-4">
                                            <input type="password" id="user_pass_again" name="user_pass_again" value="${object.USER_PASS}" class="form-control" required />
                                        </div>
                                        <label class="col-sm-0 control-label">
                                           	<font class="explain">必须与第一次填写的密码相同</font>
                                        </label>
                                    </div>
									<div class="form-group">
                                        <label for="hint-field" class="col-sm-2 control-label">
                                           	 <font class="condition">*</font>用户名
                                        </label>
                                        <div class="col-sm-4">
                                            <input type="text" id="user_name" name="user_name" value="${object.USER_NAME}" class="form-control" required />
                                        </div>
                                        <label class="col-sm-0 control-label">
                                           	<font class="explain">推荐填写用户真实姓名</font>
                                        </label>
                                    </div>
                                    <div class="form-group">
                                        <label for="hint-field" class="col-sm-2 control-label">
                                           	邮件
                                        </label>
                                        <div class="col-sm-4">
                                            <input type="text" id="user_email" name="user_email" value="${object.USER_EMAIL}" class="form-control" email="true" />
                                        </div>
                                        <label class="col-sm-0 control-label">
                                           	<font class="explain">如：xxxxxx@xxx.com格式</font>
                                        </label>
                                    </div>
                                    <div class="form-group">
                                        <label for="hint-field" class="col-sm-2 control-label">
                                           	地址
                                        </label>
                                        <div class="col-sm-4">
                                            <input type="text" id="user_addr" name="user_addr" value="${object.USER_ADDR}" class="form-control" maxlength="100"/>
                                        </div>
                                        <label class="col-sm-0 control-label">
                                           	<font class="explain">限制100字以内</font>
                                        </label>
                                    </div>
                                    <div class="form-group">
                                        <label for="hint-field" class="col-sm-2 control-label">
                                           	电话
                                        </label>
                                        <div class="col-sm-4">
                                            <input type="text" id="user_phone" name="user_phone" value="${object.USER_PHONE}" class="form-control" />
                                        </div>
                                        <label class="col-sm-0 control-label">
                                           	<font class="explain">如139xxxxxxxx或是xxxx-xxxxxxxx</font>
                                        </label>
                                    </div>
                                    <c:if test="${tag eq 1}">
                                    	<input type="hidden" name="is_available" value="${object.IS_AVAILABLE}" />
                                    	<input type="hidden" name="available_date" value="${object.AVAILABLE_DATE}" />
                                    	<input type="hidden" name="role_id" value="${object.ROLE_ID}" />
                                    </c:if>
                                    <c:if test="${empty tag or tag ne 1}">
                                    <div class="form-group">
                                        <label for="hint-field" class="col-sm-2 control-label">
                                           	是否可用
                                        </label>
                                        <div class="col-sm-4" style="margin-top:7px;">
                                            <input type="radio" id="available_date_y" name="is_available" value="1" <c:if test="${object.IS_AVAILABLE eq '1' or empty object.IS_AVAILABLE}">checked</c:if> /> 是&nbsp;&nbsp;&nbsp;
                                        	<input type="radio" id="available_date_n" name="is_available" value="0" <c:if test="${object.IS_AVAILABLE eq '0'}">checked</c:if> /> 否
                                        </div>
                                        <label class="col-sm-0 control-label">
                                           	<font class="explain">选择"是"此用户可以正常登录，选择"否"禁止此用户登录</font>
                                        </label>
                                    </div>
                                    <div class="form-group">
                                        <label for="hint-field" class="col-sm-2 control-label">
                                           	可用期限
                                        </label>
                                        <div class="col-sm-4">
                                            <input type="text" id="available_date" name="available_date" value="${object.AVAILABLE_DATE}" class="form-control" onclick="WdatePicker({minDate:'%y-%M-{%d+1}'});" dateISO="true" />
                                        </div>
                                        <label class="col-sm-0 control-label">
                                           	<font class="explain">在可用时间期限内此用户可以正常登录，不填写标识用户永久有效</font>
                                        </label>
                                    </div>
                                    <div class="form-group">
                                        <label for="hint-field" class="col-sm-2 control-label">
                                           	<font class="condition">*</font>角色
                                        </label>
                                        <div class="col-sm-4">
                                        	<select id="role_id" name="role_id" class="form-control" required >
                                        		<option value="">[请选择]</option>
                                        		<c:forEach items="${list}" var="role" varStatus="i">
                                        			<option VALUE="${role.ROLE_ID}" <c:if test="${object.ROLE_ID eq role.ROLE_ID}">selected</c:if>>${role.ROLE_NAME}</option>
                                        		</c:forEach>
                                        	</select>
                                        </div>
                                        <label class="col-sm-0 control-label">
                                           	<font class="explain">选择角色用于控制此用户对系统的操作权限</font>
                                        </label>
                                    </div>
                                    </c:if>
							</fieldset>
							<div class="form-actions">
                                   <div class="row">
                                       <div class="col-sm-offset-3 col-sm-3">
                                           	<button type="submit" class="btn btn-info" onclick="javascript:loading();">保存</button>
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
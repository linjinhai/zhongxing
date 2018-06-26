<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<%@ include file="../common/common_system.jsp"%>
<script src="../pages/role/js/role.js"></script>
<title>角色管理 - 角色编辑</title>
</head>
<body>
	<div class="page-header">
		<ol class="breadcrumb">
			<li><a href="#"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 首页</a></li>
			<li><a href="javascript:crumb('role/list');">角色管理</a></li>
			<li class="active">角色编辑</li>
		</ol>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="panel">
					<div class="panel-heading">角色管理</div>
					<div class="panel-body">
						<form action="<%=path%>/role/update" id="z_form" class="form-horizontal">
							<c:if test="${empty object.ROLE_ID}">
								<input type="hidden" name="flag" value="insert" />
							</c:if>
							<c:if test="${!empty object.ROLE_ID}">
								<input type="hidden" name="flag" value="update" />
								<input type="hidden" name="role_id" value="${object.ROLE_ID}" />
							</c:if>
							<fieldset>
								<legend>
									<strong>角色</strong> 编辑
								</legend>
								<div class="form-group">
                                    <label for="hint-field" class="col-sm-2 control-label">
                                       	 <font class="condition">*</font>角色名称
                                    </label>
                                    <div class="col-sm-4">
                                        <input type="text" id="role_name" name="role_name" value="${object.ROLE_NAME}" class="form-control" required />
                                    </div>
                                	<label class="col-sm-0 control-label">
                                       	<font class="explain">角色权限标识，用于用户编辑时选择</font>
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label for="hint-field" class="col-sm-2 control-label">
                                       	 备注
                                    </label>
                                    <div class="col-sm-4">
                                    	<textarea name="role_memo" class="form-control" maxlength="300">${object.ROLE_MEMO}</textarea>
                                    </div>
                                    <label class="col-sm-0 control-label">
                                       	<font class="explain">角色说明，限制在300字符以内</font>
                                    </label>
                                </div>
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
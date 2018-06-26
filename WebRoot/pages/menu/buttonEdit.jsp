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
<title>菜单管理 - 按钮编辑</title>
</head>
<body>
	<div class="page-header">
		<ol class="breadcrumb">
			<li><a href="#"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 首页</a></li>
			<li><a href="javascript:crumb('menu/list');">菜单管理</a></li>
			<li class="active">按钮编辑</li>
		</ol>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="panel">
					<div class="panel-heading">菜单管理</div>
					<div class="panel-body">
						<form action="<%=path%>/menu/buttonUpdate" id="z_form" class="form-horizontal">
							<c:if test="${empty object.BUTTON_TYPE_ID}">
								<input type="hidden" name="flag" value="insert" />
							</c:if>
							<c:if test="${!empty object.BUTTON_TYPE_ID}">
								<input type="hidden" name="flag" value="update" />
								<input type="hidden" name="button_type_id" value="${object.BUTTON_TYPE_ID}" />
							</c:if>
							<fieldset>
								<legend>
									<strong>按钮</strong> 编辑
								</legend>
								<div class="form-group">
                                    <label for="hint-field" class="col-sm-2 control-label">
                                       	 <font class="condition">*</font>按钮名称
                                    </label>
                                    <div class="col-sm-4">
                                        <input type="text" id="button_name" name="button_name" value="${object.BUTTON_NAME}" class="form-control" required />
                                    </div>
                                    <label class="col-sm-0 control-label">
                                       	<font class="explain"></font>
                                    </label>
                                </div>
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
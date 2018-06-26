<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<%@ include file="../common/common_system.jsp"%>
<script src="../pages/essence/js/essence.js"></script>
<title>用户管理 - 用户编辑</title>
</head>
<script>
$(function(){
	$("#INSPECT_TYPE").val('${object.INSPECT_TYPE}');
});
</script>
<body>
	<div class="page-header">
		<ol class="breadcrumb">
			<li><a href="#"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 首页</a></li>
			<li><a href="javascript:crumb('user/list');">人工巡检</a></li>
			<li class="active">巡检编辑</li>
		</ol>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="panel">
					<div class="panel-heading"></div>
					<div class="panel-body">
						<form action="<%=path%>/essence/addRgxjEnd" id="z_form" class="form-horizontal">
						
							<input type="hidden" id="INSPECT_ID" name="INSPECT_ID" value="${object.INSPECT_ID}" class="form-control" />
						
						
							<fieldset>
								<legend>
									<strong>人工巡检</strong> 编辑
								</legend>
								<div class="form-group">
									<label for="hint-field" class="col-sm-2 control-label"> <font class="condition">*</font>巡检日期
									</label>
									<div class="col-sm-4">
										<input type="text" id="INSPECT_DATE" name="INSPECT_DATE" value="${object.INSPECT_DATE}" class="form-control" required  onFocus="WdatePicker({startDate:'%y-%M-%d ',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" />
									</div>
									<label class="col-sm-0 control-label"> <font class="explain"></font>
									</label>
								</div>
								<div class="form-group">
										<label for="hint-field" class="col-sm-2 control-label"> <font class="condition">*</font>巡检类型
										</label>
										<div class="col-sm-4">
											<select id="INSPECT_TYPE" name="INSPECT_TYPE" class="form-control" required>
												<option value="">[请选择]</option>
												<option value="1">临时巡检</option>
	                                        	<option value="2">月度巡检</option>
	                                        	<option value="3">季度巡检</option>
	                                        	<option value="4">年度巡检</option>
											</select>
										</div>
										<label class="col-sm-0 control-label"> <font class="explain"></font>
										</label>
									</div>
								<div class="form-group">
									<label for="hint-field" class="col-sm-2 control-label"> <font class="condition">*</font>巡检员
									</label>
									<div class="col-sm-4">
										<input type="text" id="INSPECT_USER" name="INSPECT_USER" value="${object.INSPECT_USER}" class="form-control" required />
									</div>
									<label class="col-sm-0 control-label"> <font class="explain"></font>
									</label>
								</div>
								
								
							</fieldset>
							<div class="form-actions">
								<div class="row">
									<div class="col-sm-offset-3 col-sm-3">
										<button type="submit" class="btn btn-info" onclick="javascript:loading();">保存</button>
										
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
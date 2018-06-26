<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<%@ include file="../common/common_system.jsp"%>
<script src="../pages/sensorPart/js/sensorPart.js"></script>
<title>传感器监测项目管理 - 传感器监测项目编辑</title>
</head>
<style>
#cr{

    width: 40px;
    height: 40px;
    
    -moz-border-radius: 50px;
    -webkit-border-radius: 50px;
    border-radius: 50px;

}
</style>
<body>
	<div class="page-header">
		<ol class="breadcrumb">
			<li><a href="#"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 首页</a></li>
			<li><a href="javascript:crumb('sensorPart/list');">传感器监测项目管理</a></li>
			<li class="active">传感器监测项目编辑</li>
		</ol>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="panel">
					<div class="panel-heading">传感器监测项目管理</div>
					<div class="panel-body">
						<form action="<%=path%>/sensorPart/update" method="post"  id="z_form" class="form-horizontal" enctype="multipart/form-data">
							<c:if test="${empty object.PART_ID}">
								<input type="hidden" name="flag" value="insert" />
							</c:if>
							<c:if test="${!empty object.PART_ID}">
								<input type="hidden" name="flag" value="update" />
								<input type="hidden" name="part_id" value="${object.PART_ID}" />
							</c:if>
							<fieldset>
								<legend>
									<strong>传感器监测项目</strong> 编辑
								</legend>
								<div class="form-group">
									<label for="hint-field" class="col-sm-2 control-label"> <font class="condition">*</font>传感器监测项目名称
									</label>
									<div class="col-sm-4">
										<input type="text" id="part_name" name="part_name" value="${object.PART_NAME}" class="form-control" required />
									</div>
									<label class="col-sm-0 control-label"> <font class="explain">传感器监测项目名称的描述</font>
									</label>
								</div>
								<div class="form-group">
									<label for="hint-field" class="col-sm-2 control-label"> <font class="condition">*</font>传感器类型
									</label>
									<div class="col-sm-4">
										<select id="type_id" name="type_id" class="form-control" required>
											<option value="">[请选择]</option>
											<c:forEach items="${type_list}" var="type" varStatus="i">
												<option VALUE="${type.TYPE_ID}" <c:if test="${object.TYPE_ID eq type.TYPE_ID}">selected</c:if>>${type.TYPE_NAME}</option>
											</c:forEach>
										</select>
									</div>
									<label class="col-sm-0 control-label"> <font class="explain"></font>
									</label>
								</div>
								<div class="form-group">
									<label for="hint-field" class="col-sm-2 control-label"> 传感器监测项目单位 </label>
									<div class="col-sm-4">
										<input type="text" id="part_unit" name="part_unit" value="${object.PART_UNIT}" class="form-control" />
									</div>
									<label class="col-sm-0 control-label"> <font class="explain">如：℃、kN、mm</font>
									</label>
								</div>
								<div class="form-group">
									<label for="hint-field" class="col-sm-2 control-label"> 小数位数 </label>
									<div class="col-sm-4">
										<input type="text" id="part_point" name="part_point" value="${object.PART_POINT}" class="form-control" digits="true" range="0,8" />
									</div>
									<label class="col-sm-0 control-label"> <font class="explain">只能输入0-8位的整数</font>
									</label>
								</div>
								<div class="form-group">
									<label for="hint-field" class="col-sm-2 control-label"> 传感器数据更新时间（秒） </label>
									<div class="col-sm-4">
										<input type="text" id="part_second" name="part_second" value="${object.PART_SECOND}" class="form-control" number="true" />
									</div>
									<label class="col-sm-0 control-label"> <font class="explain">只能输入数字</font>
									</label>
								</div>
								<div class="form-group">
									<label for="hint-field" class="col-sm-2 control-label"> <font class="condition">*</font>传感器图片
									</label>
									<div class="col-sm-4">
										<c:if test="${!empty object.PART_IMG}">
											<div style="margin-bottom: 5px;">
												<img alt="" src="<%=path %>/${object.PART_IMG}" style="width: 50px;height: 50px;" />
											</div>
										</c:if>
										<input type="file" id="section_file" name="section_file" class="form-control" value="zxczxc" <c:if test="${empty object.PART_ID}">required</c:if> style="padding: 0px 12px;" accept="image/gif, image/jpeg, image/png, image/bmp" /> 
										<input type="hidden" id="section_path" name="section_path" value="${object.PART_IMG}" />
									</div>
									<label class="col-sm-0 control-label"> <font class="explain">图片格式限制在gif、jpeg、png、bmp</font>
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
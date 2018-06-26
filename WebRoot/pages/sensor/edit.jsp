<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<%@ include file="../common/common_system.jsp"%>
<script src="../pages/sensor/js/sensor.js"></script>
<title>传感器管理 - 传感器编辑</title>
</head>
<body>
	<div class="page-header">
		<ol class="breadcrumb">
			<li><a href="#"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 首页</a></li>
			<li><a href="javascript:crumb('sensor/list');">传感器管理</a></li>
			<li class="active">传感器编辑</li>
		</ol>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="panel">
					<div class="panel-heading">传感器管理</div>
					<div class="panel-body">
						<form action="<%=path%>/sensor/update" id="z_form" class="form-horizontal">
							<c:if test="${empty object.SENSOR_ID}">
								<input type="hidden" name="flag" value="insert" />
							</c:if>
							<c:if test="${!empty object.SENSOR_ID}">
								<input type="hidden" name="flag" value="update" />
								<input type="hidden" name="sensor_id" value="${object.SENSOR_ID}" />
							</c:if>
							<fieldset>
								<legend>
									<strong>传感器</strong> 编辑
								</legend>
								<div class="form-group">
                                    <label for="hint-field" class="col-sm-2 control-label">
                                       	 <font class="condition">*</font>传感器名称
                                    </label>
                                    <div class="col-sm-4">
                                        <input type="text" id="sensor_name" name="sensor_name" value="${object.SENSOR_NAME}" class="form-control" required />
                                    </div>
                                    <label class="col-sm-0 control-label">
                                       	<font class="explain">传感器名称的描述</font>
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label for="hint-field" class="col-sm-2 control-label">
                                       	 <font class="condition">*</font>传感器编号
                                    </label>
                                    <div class="col-sm-4">
                                        <input type="text" id="sensor_code" name="sensor_code" value="${object.SENSOR_CODE}" class="form-control" required />
                                    </div>
                                    <label class="col-sm-0 control-label">
                                       	<font class="explain">建议由字母、数字、-组成，如：B02-HWD-001</font>
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label for="hint-field" class="col-sm-2 control-label">
                                       	 <font class="condition">*</font>传感器位置
                                    </label>
                                    <div class="col-sm-4">
                                        <input type="text" id="sensor_pstn" name="sensor_pstn" value="${object.SENSOR_PSTN}" class="form-control" required />
                                    </div>
                                    <label class="col-sm-0 control-label">
                                       	<font class="explain">传感器位置的描述</font>
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label for="hint-field" class="col-sm-2 control-label">
                                       	 <font class="condition">*</font>传感器监测项目
                                    </label>
                                    <div class="col-sm-4">
                                    	<select id="part_id" name="part_id" class="form-control" required >
                                       		<option value="">[请选择]</option>
                                       		<c:forEach items="${part_list}" var="part" varStatus="i">
                                       			<option VALUE="${part.PART_ID}" <c:if test="${object.PART_ID eq part.PART_ID}">selected</c:if>>${part.PART_NAME}</option>
                                       		</c:forEach>
                                       	</select>
                                    </div>
                                    <label class="col-sm-0 control-label">
                                       	<font class="explain"></font>
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label for="hint-field" class="col-sm-2 control-label">
                                       	 <font class="condition">*</font>传感器截面
                                    </label>
                                    <div class="col-sm-4">
                                    	<select id="section_id" name="section_id" class="form-control" required >
                                       		<option value="">[请选择]</option>
                                       		<c:forEach items="${section_list}" var="section" varStatus="i">
                                       			<option VALUE="${section.SECTION_ID}" <c:if test="${object.SECTION_ID eq section.SECTION_ID}">selected</c:if>>${section.SECTION_NAME}</option>
                                       		</c:forEach>
                                       	</select>
                                    </div>
                                    <label class="col-sm-0 control-label">
                                       	<font class="explain"></font>
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
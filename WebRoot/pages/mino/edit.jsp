<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<%@ include file="../common/common_system.jsp"%>
<script src="<%=path%>/pages/sensor/js/sensor.js"></script>
<script src="<%=path%>/js/layer.js"></script>
<title>传感器管理 - 传感器编辑</title>
</head>
<script>
var sid = <%=request.getParameter("sid")%>;
function bc(){
	if($("#sensor_name").val()==false){
		alert("请填写：传感器名称");return ;
	}
	if($("#sensor_code").val()==false){
		alert("请填写：传感器编号");return ;
	}
	if($("#sensor_pstn").val()==false){
		alert("请填写：传感器位置");return ;
	}
	if($("#part_id").val()==false){
		alert("请填写：传感器监测项目");return ;
	}
	
	var f = {};
	f.sensor_name = $("#sensor_name").val();
	f.sensor_code = $("#sensor_code").val();
	f.sensor_pstn = $("#sensor_pstn").val();
	f.part_id = $("#part_id").val();
	f.section_id = sid;
	
	$.ajax({
		type : "POST",
		async : false,
		data:f,
		url : "<%=path%>/mioncontroller/getPartByTypeid2",
		dataType : "text",
		success : function(d) {
			if(d==1){
				alert('添加成功！');
				parent.window.location.reload();
			}else{
				alert('添加失败！');
			}
			
			
			
			}
		});
	
	
	
	
	
	//parent.window.location.reload();
}
function gb(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}

$(function(){
	$.ajax({
		type : "POST",
		async : false,
		url : "<%=path%>/mioncontroller/getPartByTypeid?sid=" + sid,
			dataType : "json",
			success : function(data) {
				$("#part_id").append("<option value=''>请选择</option>");
				for (var i = 0; i < data.length; i++) {
					$("#part_id").append("<option value='"+data[i].PART_ID+"'>" + data[i].PART_NAME + "</option>");
				}
			}
		});

	});
</script>
<body>

	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="panel">
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
									<label for="hint-field" class="col-sm-2 control-label"> <font class="condition">*</font>传感器名称
									</label>
									<div class="col-sm-4">
										<input type="text" id="sensor_name" name="sensor_name" value="${object.SENSOR_NAME}" class="form-control" required />
									</div>
									<label class="col-sm-0 control-label"> <font class="explain">传感器名称的描述</font>
									</label>
								</div>
								<div class="form-group">
									<label for="hint-field" class="col-sm-2 control-label"> <font class="condition">*</font>传感器编号
									</label>
									<div class="col-sm-4">
										<input type="text" id="sensor_code" name="sensor_code" value="${object.SENSOR_CODE}" class="form-control" required />
									</div>
									<label class="col-sm-0 control-label"> <font class="explain">建议由字母、数字、-组成，如：B02-HWD-001</font>
									</label>
								</div>
								<div class="form-group">
									<label for="hint-field" class="col-sm-2 control-label"> <font class="condition">*</font>传感器位置
									</label>
									<div class="col-sm-4">
										<input type="text" id="sensor_pstn" name="sensor_pstn" value="${object.SENSOR_PSTN}" class="form-control" required />
									</div>
									<label class="col-sm-0 control-label"> <font class="explain">传感器位置的描述</font>
									</label>
								</div>
								<div class="form-group">
									<label for="hint-field" class="col-sm-2 control-label"> <font class="condition">*</font>监测项目
									</label>
									<div class="col-sm-4">
										<select id="part_id" name="part_id" class="form-control" required>

										</select>
									</div>
									<label class="col-sm-0 control-label"> <font class="explain"></font>
									</label>
								</div>

							</fieldset>
							<div class="form-actions">
								<div class="row">
									<div class="col-sm-offset-3 col-sm-3">
										<button type="button" class="btn btn-info" onclick="javascript:bc();">保存</button>
										<button type="button" class="btn btn-default" onclick="javascript:gb();">关闭</button>
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<%@ include file="../common/common_system.jsp"%>
<script src="../pages/sensorType/js/sensorType.js"></script>
<title>传感器类型管理 - 传感器类型编辑</title>
<script>
$(function(){
	  $("input:radio[name=type_flag]").change(function () {
          var v = $('input:radio:checked').val();
          if(v==2){
        	  $("#spurl").css('display','');
          }else{
        	  $("#spurl").css('display','none');
          }
      });
});
</script>
<c:if test="${object.TYPE_FLAG==1 or object==null }">
<script>
$(function(){
    $("#spurl").css('display','none');
});
</script>
</c:if>
</head>
<body>
	<div class="page-header">
		<ol class="breadcrumb">
			<li><a href="#"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 首页</a></li>
			<li><a href="javascript:crumb('sensorType/list');">传感器类型管理</a></li>
			<li class="active">传感器类型编辑</li>
		</ol>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="panel">
					<div class="panel-heading">传感器类型管理</div>
					<div class="panel-body">
						<form action="<%=path%>/sensorType/update" id="z_form" class="form-horizontal">
							<c:if test="${empty object.TYPE_ID}">
								<input type="hidden" name="flag" value="insert" />
							</c:if>
							<c:if test="${!empty object.TYPE_ID}">
								<input type="hidden" name="flag" value="update" />
								<input type="hidden" name="type_id" value="${object.TYPE_ID}" />
							</c:if>
							<fieldset>
								<legend>
									<strong>传感器类型</strong> 编辑
								</legend>
								<div class="form-group">
                                    <label for="hint-field" class="col-sm-2 control-label">
                                       	 <font class="condition">*</font>传感器类型名称
                                    </label>
                                    <div class="col-sm-4">
                                        <input type="text" id="type_name" name="type_name" value="${object.TYPE_NAME}" class="form-control" required />
                                    </div>
                                    <label class="col-sm-0 control-label">
                                       	<font class="explain">传感器类型名称的描述</font>
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label for="hint-field" class="col-sm-2 control-label">
                                       	 <font class="condition">*</font>传感器监测类型
                                    </label>
                                    <div class="col-sm-4">
                                        <input type="radio" name="type_flag" value="1" <c:if test="${object.TYPE_FLAG==1 or object==null }"> checked="checked" </c:if> />传感器监测
                                        <input type="radio" name="type_flag" value="2" <c:if test="${object.TYPE_FLAG==2}"> checked="checked" </c:if>      />视频监测
                                    </div>
                                    <label class="col-sm-0 control-label">
                                       	<font class="explain">传感器监测类型的描述</font>
                                    </label>
                                </div>
                                 <div class="form-group" id="spurl">
                                    <label for="hint-field" class="col-sm-2 control-label">
                                       	 <font class="condition"></font>视频监控URL
                                    </label>
                                    <div class="col-sm-4">
                                        <input type="text" id="type_url" name="type_url" value="${object.TYPE_URL}" class="form-control"  />
                                    </div>
                                    <label class="col-sm-0 control-label">
                                       	<font class="explain">视频监控地址</font>
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
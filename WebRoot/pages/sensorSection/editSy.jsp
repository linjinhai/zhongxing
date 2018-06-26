<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<%@ include file="../common/common_system.jsp"%>
<script src="../pages/sensorSection/js/sensorSection.js"></script>
<title>传感器截面管理 - 传感器截面编辑</title>
</head>
<body>
	<div class="page-header">
		<ol class="breadcrumb">
			<li><a href="#"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 首页</a></li>
			<li><a href="javascript:crumb('sensorSection/list');">传感器截面管理</a></li>
			<li class="active">传感器截面编辑</li>
		</ol>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="panel">
					<div class="panel-heading">传感器截面管理</div>
					<div class="panel-body">
						<form action="<%=path%>/sensorSection/update?t=t" method="post" id="z_form" class="form-horizontal" enctype="multipart/form-data">
							<c:if test="${empty object.SECTION_ID}">
								<input type="hidden" name="flag" value="insert" />
							</c:if>
							<c:if test="${!empty object.SECTION_ID}">
								<input type="hidden" name="flag" value="update" />
								<input type="hidden" name="section_id" value="${object.SECTION_ID}" />
							</c:if>
							<fieldset>
								<legend>
									<strong>传感器首页截面</strong> 编辑
								</legend>
								
                                <div class="form-group">
                                    <label for="hint-field" class="col-sm-2 control-label">
                                       	 <font class="condition">*</font>传感器截面模型
                                    </label>
                                    <div class="col-sm-4">
                                    	<c:if test="${!empty object.SECTION_PATH}">
                                    		<div style="margin-bottom:5px;">
                                    			<img alt="" src="<%=path %>/${object.SECTION_PATH}" height="300" />
                                    		</div>
                                    	</c:if>
                                    	<input type="file" id="section_file" name="section_file" class="form-control" value="zxczxc" <c:if test="${empty object.SECTION_ID}">required</c:if> style="padding:0px 12px;" accept="image/gif, image/jpeg, image/png, image/bmp" />
                                    	<input type="hidden" id="section_path" name="section_path" value="${object.SECTION_PATH}" />  
                                    </div>
                                    <label class="col-sm-0 control-label">
                                       	<font class="explain">图片格式限制在gif、jpeg、png、bmp</font>
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
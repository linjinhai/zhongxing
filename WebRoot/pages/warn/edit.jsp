<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<%@ include file="../common/common_system.jsp"%>
<script src="../pages/warn/js/warn.js"></script>
<title>预警阀值管理 - 预警阀值编辑</title>
</head>
<body>
	<div class="page-header">
		<ol class="breadcrumb">
			<li><a href="#"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 首页</a></li>
			<li><a href="javascript:crumb('warn/list');">预警阀值管理</a></li>
			<li class="active">预警阀值编辑</li>
		</ol>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="panel">
					<div class="panel-heading">预警阀值管理</div>
					<div class="panel-body">
						<form action="<%=path%>/warn/update" id="z_form" class="form-horizontal">
							<input type="hidden" name="sensor_id" value="${object.SENSOR_ID}" />
							<fieldset>
								<legend>
									<strong>预警阀值</strong> 编辑
								</legend>
								<div class="form-group">
                                    <label for="hint-field" class="col-sm-2 control-label">
                                       	 传感器名称
                                    </label>
                                    <div class="col-sm-4" style="color:#666;margin-top:7px;">
                                        ${object.SENSOR_NAME}
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="hint-field" class="col-sm-2 control-label">
                                       	 传感器编号
                                    </label>
                                    <div class="col-sm-4" style="color:#666;margin-top:7px;">
                                        ${object.SENSOR_CODE}
                                    </div>
                                </div>
                                
                                
                                
                                
                                <div class="form-group">
                                    <label for="hint-field" class="col-sm-2 control-label">
                                       	 三级上限预警值
                                    </label>
                                    <div class="col-sm-4">
                                    	<div class="input-group">
	                                        <input type="text" id="warn_lv3" name="warn_lv3" value="${object.WARN_LV3}" class="form-control" />
	                                    	<span class="input-group-addon">${object.PART_UNIT}</span>
                                        </div>
                                    </div>
                                    <label class="col-sm-0 control-label">
                                       	<font class="explain">必须为数字，必须大于二级上限预警值</font>
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label for="hint-field" class="col-sm-2 control-label">
                                       	 二级上限预警值
                                    </label>
                                    <div class="col-sm-4">
                                    	<div class="input-group">
	                                        <input type="text" id="warn_lv2" name="warn_lv2" value="${object.WARN_LV2}" class="form-control" />
	                                    	<span class="input-group-addon">${object.PART_UNIT}</span>
                                        </div>
                                    </div>
                                    <label class="col-sm-0 control-label">
                                       	<font class="explain">必须为数字，必须大于一级上限预警值</font>
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label for="hint-field" class="col-sm-2 control-label">
                                       	 一级上限预警值
                                    </label>
                                    <div class="col-sm-4">
                                        <div class="input-group">
                                        	<input type="text" id="warn_lv1" name="warn_lv1" value="${object.WARN_LV1}" class="form-control" />
                                        	<span class="input-group-addon">${object.PART_UNIT}</span>
                                        </div>
                                    </div>
                                	<label class="col-sm-0 control-label">
                                       	<font class="explain">必须为数字</font>
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label for="hint-field" class="col-sm-2 control-label">
                                       	  一级下限预警值
                                    </label>
                                    <div class="col-sm-4">
                                    	<div class="input-group">
	                                        <input type="text" id="warn_lv11" name="warn_lv11" value="${object.WARN_LV11}" class="form-control" />
	                                    	<span class="input-group-addon">${object.PART_UNIT}</span>
                                        </div>
                                    </div>
                                    <label class="col-sm-0 control-label">
                                       	<font class="explain">必须为数字</font>
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label for="hint-field" class="col-sm-2 control-label">
                                       	  二级下限预警值
                                    </label>
                                    <div class="col-sm-4">
                                    	<div class="input-group">
	                                        <input type="text" id="warn_lv22" name="warn_lv22" value="${object.WARN_LV22}" class="form-control" />
	                                    	<span class="input-group-addon">${object.PART_UNIT}</span>
                                        </div>
                                    </div>
                                    <label class="col-sm-0 control-label">
                                       	<font class="explain">必须为数字，必须小于一级下限预警值</font>
                                    </label>
                                </div>
                                <div class="form-group">
                                    <label for="hint-field" class="col-sm-2 control-label">
                                       	  三级下限预警值
                                    </label>
                                    <div class="col-sm-4">
                                    	<div class="input-group">
	                                        <input type="text" id="warn_lv33" name="warn_lv33" value="${object.WARN_LV33}" class="form-control" />
	                                    	<span class="input-group-addon">${object.PART_UNIT}</span>
                                        </div>
                                    </div>
                                    <label class="col-sm-0 control-label">
                                       	<font class="explain">必须为数字，必须小于二级上限预警值</font>
                                    </label>
                                </div>
							</fieldset>
							<div class="form-actions">
                                   <div class="row">
                                       <div class="col-sm-offset-3 col-sm-3">
                                           	<button type="button" class="btn btn-info" onclick="javascript:bc();">保存</button>
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
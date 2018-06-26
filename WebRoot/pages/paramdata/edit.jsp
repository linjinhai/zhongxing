<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<%@ include file="../common/common_system.jsp"%>
<script src="../pages/paramdata/js/paramdata.js"></script>
<title>评定系统管理 - 评定系统编辑</title>
</head>
<body>
	<div class="page-header">
		<ol class="breadcrumb">
			<li><a href="#"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 首页</a></li>
			<li><a href="javascript:crumb('paramdata/list');">评定系统管理</a></li>
			<li class="active">评定系统编辑</li>
		</ol>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="panel">
					<div class="panel-heading">评定系统管理</div>
					<div class="panel-body">
						<form action="<%=path%>/paramdata/update" id="z_form" class="form-horizontal">
							<c:if test="${tag eq 1}">
								<input type="hidden" name="tag" value="${tag}" />
								<c:if test="${!empty zz}">
									<script type="text/javascript">
										alert("修改成功");
									</script>
								</c:if>
							</c:if>
							<c:if test="${empty object.DATA_ID}">
								<input type="hidden" name="flag" value="insert" />
							</c:if>
							<c:if test="${!empty object.DATA_ID}">
								<input type="hidden" name="flag" value="update" />
								<input type="hidden" name="data_id" value="${object.DATA_ID}" />
							</c:if>
							<fieldset>
								<legend>
									<strong>评定系统</strong> 编辑
								</legend>
								<!-- 名称 -->
                                    <div class="form-group">
                                        <label for="hint-field" class="col-sm-2 control-label">
                                           	<font class="condition">*</font>评定系统名称
                                        </label>
                                        <div class="col-sm-4">
                                            <input  id="data_name" name="data_name" value="${object.DATA_NAME}" class="form-control" required />
                                        </div>
                                        <label class="col-sm-0 control-label">
                                           	<font class="explain">对评定系统的描述</font>
                                        </label>
                                    </div>
                                 <!-- 位置 -->   
                                     <div class="form-group">
                                        <label for="hint-field" class="col-sm-2 control-label">
                                           	<font class="condition">*</font>所在方位
                                        </label>
                                        <div class="col-sm-4">
                                        	<select id="data_pid" name="data_pid" class="form-control" required >
                                        		<option value="">[请选择]</option>
                                        			<option VALUE="1" <c:if test="${object.DATA_PID eq 1}">selected</c:if>>上部结构</option>
                                        			<option VALUE="2" <c:if test="${object.DATA_PID eq 2}">selected</c:if>>下部结构</option>
                                        			<option VALUE="3" <c:if test="${object.DATA_PID eq 3}">selected</c:if>>桥面系</option>
                                        	</select>
                                        </div>
                                        <label class="col-sm-0 control-label">
                                           	<font class="explain">说明评定系统所在的具体位置</font>
                                        </label>
                                    </div>
                                  <!-- 使用-->   
                                    
                                    <div class="form-group">
                                        <label for="hint-field" class="col-sm-2 control-label">
                                           	是否使用
                                        </label>
                                        <div class="col-sm-4" style="margin-top:7px;">
                                            <input type="radio" id="available_date_y" name="data_flag" value="0" <c:if test="${object.DATA_FLAG eq '0' }">checked</c:if> /> 是&nbsp;&nbsp;&nbsp;
                                        	<input type="radio" id="available_date_n" name="data_flag" value="1" <c:if test="${object.DATA_FLAG eq '1' or empty object.DATA_FLAG}">checked</c:if> /> 否
                                        </div>
                                        <label class="col-sm-0 control-label">
                                           	<font class="explain">选择"使用"使用该评定系统，选择"未使用"没有使用该评定系统</font>
                                        </label>
                                    </div>
                                    
                                 	<!-- 分数 -->
                                    <div class="form-group">
                                        <label for="hint-field" class="col-sm-2 control-label">
                                           	<font class="condition">*</font>本项权重分数
                                        </label>
                                        <div class="col-sm-4">
                                            <input  id="data_score" name="data_score" value="${object.DATA_SCORE}" class="form-control" required  number="true" />
                                        </div>
                                        <label class="col-sm-0 control-label">
                                           	<font class="explain">分数只能输入数字</font>
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
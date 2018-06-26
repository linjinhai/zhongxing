<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<%@ include file="../common/common_system.jsp"%>
<script src="../pages/paramassess/js/paramassess.js"></script>
<title>评定参数管理 - 评定参数编辑</title>
</head>
<body>
	<div class="page-header">
		<ol class="breadcrumb">
			<li><a href="#"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 首页</a></li>
			<li><a href="javascript:crumb('paramassess/list');">评定参数管理</a></li>
			<li class="active">评定参数编辑</li>
		</ol>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="panel">
					<div class="panel-heading">评定参数管理</div>
					<div class="panel-body">
						<form action="<%=path%>/paramassess/update" id="z_form" class="form-horizontal">
							<c:if test="${tag eq 1}">
								<input type="hidden" name="tag" value="${tag}" />
								<c:if test="${!empty zz}">
									<script type="text/javascript">
										alert("修改成功");
									</script>
								</c:if>
							</c:if>
							<c:if test="${empty object.PARAM_ID }">
								<input type="hidden" name="flag" value="insert" />
							</c:if>
							<c:if test="${!empty object.PARAM_ID }">
								<input type="hidden" name="flag" value="update" />
								<input type="hidden" name="param_id" value="${object.PARAM_ID }" />
							</c:if>
							<fieldset>
								<legend>
									<strong>评定参数</strong> 编辑
								</legend>
							 
                                     <div class="form-group">
                                        <label for="hint-field" class="col-sm-2 control-label">
                                           	<font class="condition">*</font>评定项
                                        </label>
                                        <div class="col-sm-4">
                                        	<select id="struct_id" name="struct_id" class="form-control" required >
                                        		<option value="">[请选择]</option>
                                  						<c:forEach items="${structlist }" var="s">
                                        		<option value="${s.STRUCT_ID }" <c:if test="${s.STRUCT_ID eq object.STRUCT_ID }">selected</c:if>>${s.STRUCT_NAME}(${s.DATA_NAME } ---
                                        		<c:if test="${s.DATA_PID eq 1}">上部结构</c:if>
                                        	    <c:if test="${s.DATA_PID eq 2}">下部结构</c:if>
                                        	    <c:if test="${s.DATA_PID eq 3}">桥面系</c:if>
                                        		)</option>
                                        		</c:forEach>
                                        	</select>
                                        </div>
                                        <label class="col-sm-0 control-label">
                                           	<font class="explain">说明评定参数所属的评定项</font>
                                        </label>
                                    </div>
                                    
                                    
                                    <div class="form-group">
                                        <label for="hint-field" class="col-sm-2 control-label">
                                           	标度
                                        </label>
                                        <div class="col-sm-4" style="margin-top:7px;">
                                            <input type="radio"  name="desc_index" value="1" <c:if test="${object.DESC_INDEX eq '1'  or empty object.DESC_INDEX}">checked</c:if> /> 1&nbsp;&nbsp;&nbsp;
                                        	<input type="radio"  name="desc_index" value="2" <c:if test="${object.DESC_INDEX eq '2' }">checked</c:if> /> 2&nbsp;&nbsp;&nbsp;
                                        	<input type="radio"  name="desc_index" value="3" <c:if test="${object.DESC_INDEX eq '3' }">checked</c:if> /> 3&nbsp;&nbsp;&nbsp;
                                        	<input type="radio"  name="desc_index" value="4" <c:if test="${object.DESC_INDEX eq '4' }">checked</c:if> /> 4&nbsp;&nbsp;&nbsp;
                                        	<input type="radio"  name="desc_index" value="5" <c:if test="${object.DESC_INDEX eq '5' }">checked</c:if> /> 5&nbsp;&nbsp;&nbsp;
                                        </div>
                                        <label class="col-sm-0 control-label">
                                           	<font class="explain">一系列用线或点标出来的间隔，用来计量距离、数额或数量</font>
                                        </label>
                                    </div>
                                    
                                 	<!-- 定性描述 -->
                                    <div class="form-group">
                                        <label for="hint-field" class="col-sm-2 control-label">
                                           	<font class="condition">*</font>定性描述
                                        </label>
                                        <div class="col-sm-4">
                                            <textarea style="width: 383px; height: 53px;" name="desc_dx"    >${object.DESC_DX}</textarea>
                                        </div>
                                        <label class="col-sm-0 control-label">
                                           	<font class="explain">指总体属性,趋势</font>
                                        </label>
                                    </div>
                                   <!-- 定量描述 -->
                                    <div class="form-group">
                                        <label for="hint-field" class="col-sm-2 control-label">
                                           	<font class="condition">*</font>定量描述
                                        </label>
                                        <div class="col-sm-4">
                                               <textarea style="width: 383px; height: 53px;" name="desc_dl"    >${object.DESC_DL}</textarea>
                                        </div>
                                        <label class="col-sm-0 control-label">
                                           	<font class="explain">用精确的数据来描述</font>
                                        </label>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label for="hint-field" class="col-sm-2 control-label">
                                           	评定标准类别
                                        </label>
                                        <div class="col-sm-4" style="margin-top:7px;">
                                            <input type="radio"  name="param_type" value="3" <c:if test="${object.PARAM_TYPE eq '3' or empty object.DATA_FLAG}">checked</c:if> /> 三类&nbsp;&nbsp;&nbsp;
                                        	<input type="radio"  name="param_type" value="4" <c:if test="${object.PARAM_TYPE eq '4' }">checked</c:if> /> 四类&nbsp;&nbsp;&nbsp;
                                        	<input type="radio"  name="param_type" value="5" <c:if test="${object.PARAM_TYPE eq '5' }">checked</c:if> /> 五类&nbsp;&nbsp;&nbsp;
                                        </div>
                                        <label class="col-sm-0 control-label">
                                           	<font class="explain">评定标准分为的种类</font>
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
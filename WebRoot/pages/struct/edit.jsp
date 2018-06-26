<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<%@ include file="../common/common_system.jsp"%>
<script src="../pages/struct/js/struct.js"></script>
<title>评定项管理 - 评定项编辑</title>
</head>
<body>
	<div class="page-header">
		<ol class="breadcrumb">
			<li><a href="#"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 首页</a></li>
			<li><a href="javascript:crumb('struct/list');">评定项管理</a></li>
			<li class="active">评定项编辑</li>
		</ol>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="panel">
					<div class="panel-heading">评定项管理</div>
					<div class="panel-body">
						<form action="<%=path%>/struct/update" id="z_form" class="form-horizontal">
							<c:if test="${tag eq 1}">
								<input type="hidden" name="tag" value="${tag}" />
								<c:if test="${!empty zz}">
									<script type="text/javascript">
										alert("修改成功");
									</script>
								</c:if>
							</c:if>
							<c:if test="${empty object.STRUCT_ID}">
								<input type="hidden" name="flag" value="insert" />
							</c:if>
							<c:if test="${!empty object.STRUCT_ID}">
								<input type="hidden" name="flag" value="update" />
								<input type="hidden" name="struct_id" value="${object.STRUCT_ID}" />
							</c:if>
							<fieldset>
								<legend>
									<strong>评定项</strong> 编辑
								</legend>
								<!-- 名称 -->
                                    <div class="form-group">
                                        <label for="hint-field" class="col-sm-2 control-label">
                                           	<font class="condition">*</font>评定项名称
                                        </label>
                                        <div class="col-sm-4">
                                            <input  id="struct_name" name="struct_name" value="${object.STRUCT_NAME}" class="form-control" required />
                                        </div>
                                        <label class="col-sm-0 control-label">
                                           	<font class="explain">对评定项的描述</font>
                                        </label>
                                    </div>
                                 <!-- 位置 -->   
                                     <div class="form-group">
                                        <label for="hint-field" class="col-sm-2 control-label">
                                           	<font class="condition">*</font>所属评定系统
                                        </label>
                                        <div class="col-sm-4">
                                        	<select id="struct_pid" name="struct_pid" class="form-control" required >
                                        		<option value="">[请选择]</option>
                                        			<c:forEach items="${datalist}" var="data">
                                        	    <option value="${data.DATA_ID }" <c:if test="${object.STRUCT_PID eq data.DATA_ID}">selected</c:if>>${data.DATA_NAME}
                                        	    <c:if test="${data.DATA_PID eq 1}">(上部结构)</c:if>
                                        	    <c:if test="${data.DATA_PID eq 2}">(下部结构)</c:if>
                                        	    <c:if test="${data.DATA_PID eq 3}">(桥面系)</c:if>
                                        	    </option>		
                                        			
                                        			</c:forEach>
                                        	</select>
                                        </div>
                                        <label class="col-sm-0 control-label">
                                           	<font class="explain">说明评定项所属评定系统</font>
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
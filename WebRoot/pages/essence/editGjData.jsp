<%@page import="com.sendyago.util.jdbc.OracleJDBC"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<%@ page language="java" import="java.util.*,com.*" pageEncoding="utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name=”renderer” content=”webkit”>
<title>平湖编组站大桥健康监测系统</title>
<%@ include file="/pages/common/common.jsp"%>

<!--图标样式-->
<link rel="stylesheet" type="text/css" href="<%=path%>/pages/essence/css/bootstrap.min.css" />
<!--主要样式-->
<link rel="stylesheet" type="text/css" href="<%=path%>/pages/essence/css/style.css" />
<script type="text/javascript">
	
</script>
</head>
<body>
	<input type="hidden" id="path" value="<%=path%>" />
	<input type="hidden" id="t" value="<%=request.getParameter("t")%>" />
	<form name="form1" id="form1" method="post">
		<input type="hidden" name="in_id" id="in_id" value="<%=request.getParameter("id")%>" /> <input type="hidden" name="gj_id" id="gj_id" value="" /> <a name="top" id="top"></a> <input type="hidden" id="path" value="<%=path%>" />
		<div class="page-header">
			<ol class="breadcrumb">
				<li><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 人工巡检</li>

			</ol>
		</div>
		<div class="container-fluid">
			<div class="row">

				<div style="width: 35%; margin-left: 14%;" class="col-lg-6">
					<div class="panel panel-default">
						<div class="panel-heading">
							<span>全桥结构</span>


						</div>
						<div class="panel-body">
							<div style="height: 700px; overflow: auto;" class="tree">
								<ul>
									<li id="qq">
										<!-- 
									<ul>
										<li><span><i class="icon-minus-sign"></i>上部结构</span>
											<ul>
												<li><span><i class="icon-minus-sign"></i>上部结构2</span>
													<ul>
														<li><span>9.00</span></li>
													</ul>
												</li>
											</ul>
										</li>
									</ul>
									 -->
									</li>
								</ul>
							</div>


						</div>
					</div>
				</div>


				<div style="width: 40%;" class="col-lg-6">
					<div class="panel panel-default">
						<div class="panel-heading">
							<span id="gjxx">构件信息</span>


						</div>
						<div class="panel-body">
							<div style="height: 100%;" id="pfnr"></div>
						</div>
					</div>
				</div>
			</div>

		</div>

	</form>

</body>
<script src="<%=path%>/pages/essence/js/gj.js"></script>
</html>
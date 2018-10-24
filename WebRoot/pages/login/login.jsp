<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name=”renderer” content=”webkit”>
		<title><%@ include file="/pages/main/title.jsp"%></title>
		<link href="<%=path %>/css/bootstrap.min.css" rel="stylesheet">
		<link href="<%=path %>/css/style.css" rel="stylesheet">
		<link href="<%=path %>/css/login.css" rel="stylesheet">
		<script src="<%=path %>/js/jquery.min.js"></script>
		<script src="<%=path %>/js/jquery.Framer.js"></script>
		<script src="<%=path %>/js/sample.js"></script>
		<script src="<%=path %>/js/Vague.js"></script>
		<script type="text/javascript" src="<%=path %>/pages/login/js/login.js"></script>
	</head>
<%--<body onload="checkBrower();">--%>
<body>
	<input type="hidden" value="<%=path %>" id="path" />
	<div class="wrapper">
		<div class="container">
			<%--<h1><img src="<%=path %>/img/logo.png"></h1>--%>
			<h1>中兴大桥健康监测系统</h1>
			<form action="" method="post" id="loginForm" name="loginForm" class="form-horizontal">
				<input type="text" id="USER_ID" name="USER_ID" placeholder="账号" class="form-control" value="${un_info.USER_ID}">
				<input type="password" id="USER_PASS" name="USER_PASS" placeholder="密码" required="" value="${un_info.USER_PASS}" class="form-control">
				<div class="checkbox">
					<input type="checkbox" id="IS_CHECK" name="IS_CHECK"/>
					<label>
						记住账号和密码(<font color="red">不建议在公共场所勾选此选项</font>)
					</label>
				</div>
				<button type="button" onclick="login();" id="login-button"> 登录</button>
				<div>
					<label style="color:red;">${un_info.error }</label>
				</div>
			</form>
		</div>
		<div class="bg-map"></div>
		<ul class="bg-bubbles">
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
		</ul>
	</div>
	<div id="wrap">
		<section id="ajax">
			<a href="<%=path%>/controller/toDownload" id="downloadPage"
			   class="framer" data-framer-type="ajax"> </a>
		</section>
	</div>
</body>
</html>
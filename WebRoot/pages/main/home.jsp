<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name=”renderer” content=”webkit”>
    <title><%@ include file="/pages/main/title.jsp"%></title>
    <%@ include file="/pages/common/common.jsp"%>
</head>
<body onload="initPage();">
<input type="hidden" id="path" value="<%=path %>" />
<input type="hidden" id="role_id" value="${logined.ROLE_ID}" />
<input type="hidden" id="user_id" value="${logined.USER_ID}" />
<input type="hidden" id="systemUrl" value="${systemUrl}" />

<bgsound id="eggs_bgsound" src=""></bgsound>
<audio src='' controls autoplay='autoplay' id='eggs_audio' style='display:none;'></audio>

<!-- header -->
<div class="header navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <ul class="nav navbar-nav pull-left">
                <li class="ml-sm mr-n-xs hidden-xs logo"><a href="javascript:gotorandom();">
                    <span class="icon"><span class="glyphicon glyphicon-link" aria-hidden="true"></span></span>
                    <strong><%@ include file="/pages/main/title.jsp"%></strong></a>
                </li>
                <li>
                    <!-- shown on md & mg screen. collapses and expands navigation -->
                    <a class="hidden-sm hidden-xs" id="nav-state-toggle" href="#" title="Show/hide 展示/隐藏 系统菜单" data-placement="bottom" onclick="menuClick();">
                        <span class="glyphicon glyphicon-option-horizontal" aria-hidden="true" id="menu-span"></span>
                    </a>
                    <!-- shown on xs & sm screen. collapses and expands navigation -->
                    <a class="visible-sm visible-xs" id="nav-collapse-toggle" href="#" title="Show/hide 展示/隐藏 系统菜单" data-placement="bottom">
                        <span class="glyphicon glyphicon-menu-hamburger" aria-hidden="true"></span>
                    </a>
                </li>
                <li>
                    <a class="hidden-sm hidden-xs" id="nav-sound-switch" href="#" title="报警声音开/关" onclick="soundSwitchSet();">
                        <span class="glyphicon glyphicon-volume-up" aria-hidden="true" id="sound-span"></span>
                    </a>
                </li>
            </ul>
            <!-- xs & sm screen logo -->
            <a class="navbar-brand visible-xs" href="#">
                <%@ include file="/pages/main/title.jsp"%>
            </a>
        </div>

        <!-- this part is hidden for xs screens -->
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <strong>${logined.USER_NAME}</strong>
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="#" onclick="forwardTo('<%=path %>/user/updatePage?id=${logined.USER_ID}&tag=1','0903000000');"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> &nbsp; 个人信息设置</a></li>
                        <li class="divider"></li>
                        <li><a href="#" onclick="forwardTo('<%=path %>/mcontroller/warnList?page=1&limit=20&IS_COUNT=0','warn');">
                            <span class="glyphicon glyphicon-volume-up" aria-hidden="true"></span>
                            &nbsp; 最新报警信息 &nbsp;&nbsp;<span class="badge bg-danger animated bounceIn" id="warn_count_open">${warn_count}</span></a>
                        </li>
                        <li class="divider"></li>
                        <li><a href="javascript:logout();"><span class="glyphicon glyphicon-log-out" aria-hidden="true"></span> &nbsp; 退出</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>

<!-- sidebar -->
<div id="sidebar" class="sidebar" role="navigation">
    <!-- need this .js class to initiate slimscroll -->
    <div class="js-sidebar-content">
        <!-- 系统菜单 START -->
        <!-- 系统菜单信息,该菜单信息是根据登录用户的角色进行查询 -->
        <c:forEach items="${menus }" var="MENU" varStatus="status">
            <c:if test="${MENU.IS_CHILD == 0}">
                <c:if test="${status.count > 1}">
                    </ul>
                </c:if>
                <h5 class="sidebar-nav-title">${MENU.MENU_NAME}<a class="action-link" href="#"><span class="glyphicon glyphicon-bookmark" aria-hidden="true"></span></a></h5>
                <ul class="sidebar-nav">
            </c:if>
            <c:if test="${MENU.IS_CHILD != 0}">
                <li>
                    <c:choose>
                        <c:when test="${MENU.MENU_ID == '0901000000'}">  <!-- 首页 -->
                            <a href="#" onclick="forwardTo('<%=path %>${MENU.MENU_PATH}','${MENU.MENU_ID}');">
                                <span class="icon"><span class="glyphicon ${MENU.MENU_IMG}" aria-hidden="true"></span></span> ${MENU.MENU_NAME}
                            </a>
                        </c:when>
                        <c:when test="${MENU.MENU_ID == '0902000000'}"> <!-- 报警 -->
                            <a href="#" onclick="forwardTo('<%=path %>${MENU.MENU_PATH}','${MENU.MENU_ID}');">
                                <span class="icon"><span class="glyphicon ${MENU.MENU_IMG}" id="warn-icon-span" aria-hidden="true"></span></span> ${MENU.MENU_NAME}
                                <span class="label label-danger" id="warn_count">${warn_count}</span>
                            </a>
                        </c:when>
                        <c:when test="${MENU.MENU_ID == '0903000000'}"> <!-- 个人信息 -->
                            <a href="#" onclick="forwardTo('<%=path %>${MENU.MENU_PATH}?id=${logined.USER_ID}&tag=1','${MENU.MENU_ID}');">
                                <span class="icon"><span class="glyphicon ${MENU.MENU_IMG}" aria-hidden="true"></span></span> ${MENU.MENU_NAME}
                            </a>
                        </c:when>
                        <c:otherwise>
                        
                        	
                        	<c:if test="${fn:startsWith(MENU.MENU_PATH,'http://')==true }">
	                        	<a href="#" onclick="forwardTo('${MENU.MENU_PATH}','${MENU.MENU_ID}');">
	                                <span class="icon"><span class="glyphicon ${MENU.MENU_IMG}" aria-hidden="true"></span></span> ${MENU.MENU_NAME}
	                            </a>
                        	</c:if>
                        	<c:if test="${fn:startsWith(MENU.MENU_PATH,'http://')==false }">
	                        	<a href="#" onclick="forwardTo('<%=path %>${MENU.MENU_PATH}','${MENU.MENU_ID}');">
	                                <span class="icon"><span class="glyphicon ${MENU.MENU_IMG}" aria-hidden="true"></span></span> ${MENU.MENU_NAME}
	                            </a>
                        	</c:if>
                        
                            
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:if>
        </c:forEach>
        </ul>
        <!-- 系统菜单 END -->

        <%--<h5 class="sidebar-nav-title">其他信息 <a class="action-link" href="#"><span class="glyphicon glyphicon-bookmark" aria-hidden="true"></span></a></h5>--%>
        <%--<ul class="sidebar-labels">--%>
            <%--<li>--%>
                <%--<a href="#">--%>
                    <%--<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>--%>
                    <%--<span class="label-name">系统信息</span>--%>
                <%--</a>--%>
            <%--</li>--%>
            <%--<li>--%>
                <%--<a href="#">--%>
                    <%--<span class="glyphicon glyphicon-book" aria-hidden="true"></span>--%>
                    <%--<span class="label-name">系统说明</span>--%>
                <%--</a>--%>
            <%--</li>--%>
        <%--</ul>--%>
        <%--<h5 class="sidebar-nav-title">系统开发进度进度</h5>--%>
        <%--<div class="sidebar-alerts">--%>
            <%--<div class="alert fade in">--%>
                <%--<a href="#" class="close" data-dismiss="alert" aria-hidden="true">&times;</a>--%>
                <%--<span class="text-white fw-semi-bold">整体开发进度</span>--%>
                <%--<div class="progress progress-xs mt-xs mb-0"><div class="progress-bar progress-bar-warning" style="width: 85%" title="85%"></div></div>--%>
            <%--</div>--%>
            <%--<div class="alert fade in">--%>
                <%--<a href="#" class="close" data-dismiss="alert" aria-hidden="true">&times;</a>--%>
                <%--<span class="text-white fw-semi-bold">系统管理模块</span>--%>
                <%--<div class="progress progress-xs mt-xs mb-0"><div class="progress-bar progress-bar-success" style="width: 99%" title="99%"></div></div>--%>
            <%--</div>--%>
            <%--<div class="alert fade in">--%>
                <%--<a href="#" class="close" data-dismiss="alert" aria-hidden="true">&times;</a>--%>
                <%--<span class="text-white fw-semi-bold">实时监测与预警模块</span>--%>
                <%--<div class="progress progress-xs mt-xs mb-0"><div class="progress-bar progress-bar-success" style="width: 92%" title="92%"></div></div>--%>
            <%--</div>--%>
            <%--<div class="alert fade in">--%>
                <%--<a href="#" class="close" data-dismiss="alert" aria-hidden="true">&times;</a>--%>
                <%--<span class="text-white fw-semi-bold">数据查询模块</span>--%>
                <%--<div class="progress progress-xs mt-xs mb-0"><div class="progress-bar progress-bar-success" style="width: 90%" title="90%"></div></div>--%>
            <%--</div>--%>
            <%--<div class="alert fade in">--%>
                <%--<a href="#" class="close" data-dismiss="alert" aria-hidden="true">&times;</a>--%>
                <%--<span class="text-white fw-semi-bold">系统工作状态模块</span>--%>
                <%--<div class="progress progress-xs mt-xs mb-0"><div class="progress-bar progress-bar-danger" style="width: 10%" title="10%"></div></div>--%>
            <%--</div>--%>
            <%--<div class="alert fade in">--%>
                <%--<a href="#" class="close" data-dismiss="alert" aria-hidden="true">&times;</a>--%>
                <%--<span class="text-white fw-semi-bold">其他</span>--%>
                <%--<div class="progress progress-xs mt-xs mb-0"><div class="progress-bar progress-bar-success" style="width: 98%" title="98%"></div></div>--%>
                <%--<br /><br /><br />--%>
            <%--</div>--%>
        <%--</div>--%>
        <br /><br /><br /><br />
    </div>
</div>


<!-- content -->
<div class="content-wrap">
    <div id="content" class="content" role="main">
        <div class="ifr_div">
            <div class="proccess" id="loading" style="display: none;"><b>正在加载中...请稍候</b></div>
            <iframe name="main" id="main" src="" marginwidth="0"
                marginheight="0" width="100%" height="100%" scrolling="yes" border="0" frameborder="0"
                    allowtransparency="true"></iframe>
        </div>
        <%--<div class="footer">宁波杉工结构监测与控制工程中心有限公司</div>--%>
        <div class="footer">&nbsp;</div>
    </div>
</div>

<script type="text/javascript" src="<%=path %>/pages/main/js/home.js"></script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<%String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";%>
<input type="hidden" id="systemPath" value="<%=path%>" />
<input type="hidden" id="shiro_user_id" value="${shiro_user_id}" />
<input type="hidden" id="shiro_role_id" value="${shiro_role_id}" />
<input type="hidden" id="shiro_menu_id" value="${shiro_menu_id}" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script src="<%=path%>/js/jquery.min.js"></script>
<script src="<%=path%>/js/bootstrap.min.js"></script>
<script src="<%=path%>/js/jquery.pjax.js"></script>
<script src="<%=path%>/js/jquery.slimscroll.min.js"></script>
<script src="<%=path%>/js/widgster.js"></script>
<script src="<%=path%>/js/settings.js"></script>
<script src="<%=path%>/js/app.js"></script>
<script src="<%=path%>/js/system.js"></script>
<script src="<%=path%>/js/plugins/validate/jquery.validate.min.js"></script>
<script src="<%=path%>/js/plugins/validate/messages_zh.min.js"></script>
<script src="<%=path%>/js/plugins/validate/form-validate.js"></script>
<script src="<%=path%>/js/plugins/My97DatePicker/WdatePicker.js"></script>

<link href="<%=path%>/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/css/font-awesome.min.css" rel="stylesheet">
<link href="<%=path%>/css/style.css" rel="stylesheet">
<link href="<%=path%>/css/system.css" rel="stylesheet">

<script>
    $(function() {
        if ($("#loading").length == 0) {
            $('body').prepend(
                    "<div id='loading'>" + " <div class='spinner2'>"
                    + "    <div class='spinner-container container1'>"
                    + "        <div class='circle1'></div>"
                    + "        <div class='circle2'></div>"
                    + "        <div class='circle3'></div>"
                    + "        <div class='circle4'></div>"
                    + "     </div>"
                    + "    <div class='spinner-container container2'>"
                    + "        <div class='circle1'></div>"
                    + "        <div class='circle2'></div>"
                    + "        <div class='circle3'></div>"
                    + "        <div class='circle4'></div>"
                    + "     </div>"
                    + "     <div class='spinner-container container3'>"
                    + "        <div class='circle1'></div>"
                    + "        <div class='circle2'></div>"
                    + "         <div class='circle3'></div>"
                    + "         <div class='circle4'></div>"
                    + "     </div>" + " </div>"
                    + " <b class='loding-font'>正在加载中...请稍候</b></div>");
        }
    });
</script>
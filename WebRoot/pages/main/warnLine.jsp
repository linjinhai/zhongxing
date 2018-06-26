<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
%>

<input type="hidden" id="sensor_code" value="${code}" />
<input type="hidden" id="warning_time" value="${tim}" />
<section id="ajaxContents">
    <div id="frmTitle">报警曲线展示</div>
    <div class="panel-body" style="width:600px; height:410px;">
        <div id="warnLine"></div>
    </div>
    <%-- 在需要底部按钮的页面中放开注释内容 --%>
    <div class="panel-footer text-center">
        <%--<button class="btn btn-primary btn-sm">确定</button>--%>
        <button class="btn btn-primary btn-sm" onclick="$.Framer.close();">关闭</button>
    </div>
</section>
<script type="text/javascript" src="<%=path %>/pages/main/js/warnLine.js" />

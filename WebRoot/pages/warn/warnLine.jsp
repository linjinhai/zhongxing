<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>

<% 
String path = request.getContextPath();
String s_sensor =request.getParameter("s_sensor");
String warning_time =request.getParameter("warning_time");
%>
<script type="text/javascript">
var s_sensor='<%=s_sensor%>';
var path='<%=path%>';
var warning_time = '<%=warning_time%>';
</script>

<input type="hidden" id="sensor_code" value="<%=s_sensor%>" />
<input type="hidden" id="warning_time" value="<%=warning_time%>" /> 
<section id="ajaxContents">
    <div id="frmTitle">报警曲线展示</div>
    <div class="panel-body" style="width:660px; height:440px;">
        <div id="warnLine"></div>
    </div>

</section>
<script type="text/javascript" src="<%=path%>/pages/warn/js/warnLine.js" />


<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!-- css -->
<link href="<%=path %>/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>



<!--base -->

<script language="Javascript" src="<%=path %>/js/jquery.min.js"></script>
<script language="Javascript" src="<%=path %>/js/bootstrap.min.js"></script>
<script language="Javascript" src="<%=path %>/js/jquery.pjax.js"></script>
<script language="Javascript" src="<%=path %>/js/widgster.js"></script>
<script language="Javascript" src="<%=path %>/js/settings.js"></script>
<script language="Javascript" src="<%=path %>/js/app.js"></script>

<script language="Javascript" src="<%=path %>/js/jquery.slimscroll.min.js"></script>
<!--  弹出窗口-->


<script language="Javascript" src="<%=path%>/js/jquery.Framer.js"></script>
<script language="Javascript" src="<%=path%>/js/sample.js"></script>
<script language="Javascript" src="<%=path%>/js/Vague.js"></script>


<!-- date -->
<script type="text/javascript" src="<%=path%>/js/plugins/My97DatePicker/WdatePicker.js"></script>

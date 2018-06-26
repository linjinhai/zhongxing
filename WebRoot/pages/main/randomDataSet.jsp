<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%
    //String path = request.getContextPath();
%>
<html>
<head>
    <title></title>
    <%@ include file="/pages/common/common.jsp" %>
</head>
<body>
    <table WIDTH="90%" border="1">
        <tr>
            <td>传感器ID</td>
            <td>传感器编号</td>
            <td>传感器名称</td>
            <td>随机数开区间x</td>
            <td>随机数闭区间y</td>
            <td>随机数运行状态</td>
            <td>操作</td>
        </tr>
        <c:forEach var="s" items="${sensors}">
            <tr>
                <td>${s.SENSOR_ID}</td>
                <td>${s.SENSOR_CODE}</td>
                <td>${s.SENSOR_NAME}</td>
                <td><input type="text" value="${s.RANDOM_X}" id="x_${s.SENSOR_ID}"/></td>
                <td><input type="text" value="${s.RANDOM_Y}" id="y_${s.SENSOR_ID}"/></td>
                <td id="status_${s.SENSOR_ID}" title="${s.RANDOM_STATUS}">
                    <c:if test="${s.RANDOM_STATUS == 0}">
                        <font color="green">已关闭</font>
                    </c:if>
                    <c:if test="${s.RANDOM_STATUS == 1}">
                        <font color="red">已开启</font>
                    </c:if>
                </td>
                <td>
                    <a href="#" onclick="setRandomData(${s.SENSOR_ID},2);">设置值</a>&nbsp;&nbsp;
                    <a href="#" onclick="setRandomData(${s.SENSOR_ID},1);">开启</a>&nbsp;&nbsp;
                    <a href="#" onclick="closeRandom(${s.SENSOR_ID});">关闭</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
<script>
    function setRandomData(id,flag) {
        var x = document.getElementById("x_"+id).value;
        var y = document.getElementById("y_"+id).value;
        var str, str1;
        if(x == null||x == "") {
            alert("Error, x need a valid value !")
            return;
        }
        if(y == null||y == "") {
            alert("Error, y need a valid value !")
            return;
        }
        x = x/1;
        y = y/1;
        if(x>=y) {
            alert("Error, sensor id is "+id+", y must bigger than x !")
            return;
        }
        var status_td = document.getElementById("status_"+id);
        if(status_td.title == 1&&flag == 1) {
            alert("Error, status is already on !");
            return;
        }
        if(flag == 1){
            str = "开启中...";
            str1 = "<font color=red>已开启</font>";
        }else if(flag == 2){
            str = "设置中...";
            str1 = status_td.innerHTML;
        }

        status_td.innerHTML = "<font color=blue>"+str+"</font>";
        $.ajax({
            type: 'post',
            url: '<%=path%>/mcontroller/startOrCloseRandom?flag='+flag+'&x='+x+'&y='+y+'&id='+id,
            cache: false,
            dataType: 'json',
            success: function (data) {
                if(data == 1&&flag == 1) {
                    status_td.title=1;
                }
                status_td.innerHTML = str1;
            }
        });
    }

    function closeRandom(id) {
        var status_td = document.getElementById("status_"+id),x=0,y=0;
        if(status_td.title == 0) {
            alert("Error, status is already off !");
            return;
        }
        status_td.innerHTML = "<font color=blue>关闭中...</font>";
        $.ajax({
            type: 'post',
            url: '<%=path%>/mcontroller/startOrCloseRandom?flag=0&x='+x+'&y='+y+'&id='+id,
            cache: false,
            dataType: 'json',
            success: function (data) {
                if(data == 1) {
                    status_td.innerHTML = "<font color=green>已关闭</font>";
                    status_td.title=0;
                }
            }
        });
    }
</script>
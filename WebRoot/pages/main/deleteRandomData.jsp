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
<br/><br/><br/>
删除数据库随机数据(请谨慎操作,以免误删真实数据):
<input type="button" value="Delete Random" onclick="deleteRandom();"/>
<span id="show"></span><br/><br/>
设置随机数:
<input type="button" value="Set Random Data" onclick="setRandom();"/>
</body>
</html>
<script>
    function deleteRandom() {
        var img = document.getElementById("show"), c=0;
        var showBusy = setInterval(function () {
            if(c == 0) {
                img.innerHTML = ".";
                c ++;
            }else if(c == 1){
                img.innerHTML = "..";
                c ++;
            }else if(c == 2){
                img.innerHTML = "...";
                c =0;
            }
        }, 1000);
        $.ajax({
            type: 'post',
            url: '<%=path%>/mcontroller/deleteRandomData',
            cache: false,
            dataType: 'json',
            success: function (flag) {
                if(flag == "success") {
                    clearInterval(showBusy);
                    img.innerHTML="Success to Delete random data !";
                }
            }
        });
    }

    function setRandom() {
        window.location.href="<%=path%>/mcontroller/setRandom";
    }
</script>
<%@ page language="java" import="com.sendyago.util.common.PageBean" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%String path = request.getContextPath(); %>
<!-- css -->


<!-- 弹出窗口-->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>


    <title>My JSP 'page.jsp' starting page</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <%
        PageBean pageBean = (PageBean) request.getAttribute("pageBean");
    %>
    <style type="text/css">
        .disableBtn {
            border: 4 solid black;

        }
    </style>
    <link href="<%=path %>/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>

    <script type="text/javascript">
        function gotoPage(currentPage) {

            var formId = "<%=request.getParameter("formId")%>";
            if (currentPage == "select") {

                document.getElementById("currentPage").value = 1;
            } else {

                document.getElementById("currentPage").value = currentPage;
            }


            document.form1.submit();
        }
    </script>

</head>

<body>

<div class="row">
    <div class="col-md-6">
        <div class=" info">
            每页<strong> <select id="pageSize" name="pageSize" onchange="javascript:gotoPage('select')">

            <option
                    <c:if test="${pageBean.pageSize == 10 }">selected</c:if>>10
            </option>
            <option
                    <c:if test="${pageBean.pageSize == 20 }">selected</c:if>>20
            </option>
            <option
                    <c:if test="${pageBean.pageSize == 40 }">selected</c:if>>40
            </option>
            <option
                    <c:if test="${pageBean.pageSize == 50 }">selected</c:if>>50
            </option>
            <option
                    <c:if test="${pageBean.pageSize == 100 }">selected</c:if>>100
            </option>
            <option
                    <c:if test="${pageBean.pageSize == 200 }">selected</c:if>>200
            </option>
        </select>
        </strong>条，共${pageBean.allRow}条
        </div>
    </div>
    <div class="col-md-6">
        <div class="btn-group pull-right" role="group">
            <c:if test="${pageBean.currentPage == 1}">
                <button type="button" class="btn btn-default " disabled>首页</button>
                <button type="button" class="btn btn-default " disabled>上一页</button>
            </c:if>
            <c:if test="${pageBean.currentPage != 1&&pageBean.allRow!=0}">
                <button type="button" class="btn btn-default"
                        onclick="gotoPage(1)">首页
                </button>
                <button type="button" class="btn btn-default"
                        onclick="gotoPage(${pageBean.currentPage-1})">上一页
                </button>
            </c:if>
            <%
                if (pageBean.getCurrentPage() - 5 > 0) {
            %>
            <button type="button" class="btn btn-default "
                    onclick="gotoPage('${pageBean.currentPage-5 }')">${pageBean.currentPage-5}</button>
            <%
                }
            %>
            <%
                if (pageBean.getCurrentPage() - 4 > 0) {
            %>
            <button type="button" class="btn btn-default"
                    onclick="gotoPage('${pageBean.currentPage-4 }')">${pageBean.currentPage-4}</button>
            <%
                }
            %>
            <%
                if (pageBean.getCurrentPage() - 3 > 0) {
            %>
            <button type="button" class="btn btn-default"
                    onclick="gotoPage('${pageBean.currentPage-3 }')">${pageBean.currentPage-3}</button>
            <%
                }
            %>
            <%if (pageBean.getCurrentPage() - 2 > 0) {%>

            <button type="button" class="btn btn-default"
                    onclick="gotoPage('${pageBean.currentPage-2 }')">${pageBean.currentPage-2}</button>
            <%
                }
            %>
            <%
                if (pageBean.getCurrentPage() - 1 > 0) {
            %>
            <button type="button" class="btn btn-default"
                    onclick="gotoPage('${pageBean.currentPage-1 }')">${pageBean.currentPage-1}</button>
            <%
                }
            %>
            <button type="button" class="btn btn-default Page">${pageBean.currentPage }</button>
            <%
                if (pageBean.getCurrentPage() + 1 <= pageBean.getTotalPage()) {
            %>
            <button type="button" class="btn btn-default"
                    onclick="gotoPage('${pageBean.currentPage+1 }')">${pageBean.currentPage+1}</button>
            <%
                }
            %>
            <%
                if (pageBean.getCurrentPage() + 2 <= pageBean.getTotalPage()) {
            %>
            <button type="button" class="btn btn-default"
                    onclick="gotoPage('${pageBean.currentPage+2 }')">${pageBean.currentPage+2}</button>
            <%
                }
            %>
            <%
                if (pageBean.getCurrentPage() + 3 <= pageBean.getTotalPage()) {
            %>
            <button type="button" class="btn btn-default"
                    onclick="gotoPage('${pageBean.currentPage+3 }')">${pageBean.currentPage+3}</button>
            <%
                }
            %>
            <%
                if (pageBean.getCurrentPage() + 4 <= pageBean.getTotalPage()) {
            %>
            <button type="button" class="btn btn-default"
                    onclick="gotoPage('${pageBean.currentPage+4 }')">${pageBean.currentPage+4}</button>
            <%
                }
            %>
            <%
                if (pageBean.getCurrentPage() + 5 <= pageBean.getTotalPage()) {
            %>
            <button type="button" class="btn btn-default"
                    onclick="gotoPage('${pageBean.currentPage+5 }')">${pageBean.currentPage+5}</button>
            <%
                }
            %>
            <c:if
                    test="${pageBean.currentPage != pageBean.totalPage&&pageBean.allRow!=0}">
                <button type="button" class="btn btn-default"
                        onclick="gotoPage(${pageBean.currentPage+1})">下一页
                </button>
                <button type="button" class="btn btn-default"
                        onclick="gotoPage(${pageBean.totalPage})">尾页
                </button>
            </c:if>
            <c:if test="${pageBean.currentPage == pageBean.totalPage}">
                <button type="button" class="btn btn-default" disabled>下一页</button>
                <button type="button" class="btn btn-default" disabled>尾页</button>
            </c:if>
        </div>
    </div>
</div>

</body>
</html>

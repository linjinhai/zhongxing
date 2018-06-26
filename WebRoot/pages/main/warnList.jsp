<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="format-detection" content="telephone=no">
    <meta name="renderer" content="webkit">
    <title>最新报警列表</title>
    <%@ include file="/pages/common/common.jsp" %>
    <script type="text/javascript" src="<%=path %>/highcharts/highcharts.js"></script>
    <script type="text/javascript" src="<%=path %>/highcharts/style-grid-light.js"></script>
</head>
<body>
<input type="hidden" id="path" value="<%=path %>" />
<div class="page-header">
    <ol class="breadcrumb">
        <li><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span> 最新报警信息</li>
    </ol>
</div>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="panel">
                <div class="panel-body notop-padding">
                    <table class="table table-striped table-hover table-bordered table-condensed" id="table">
                        <thead>
                        <tr>
                            <th width="40px">序号</th>
                            <th width="13%">传感器编号</th>
                            <th width="15%">传感器位置</th>
                            <th width="15%">传感器名称</th>
                            <th width="15%">报警时间</th>
                            <th width="15%">报警级别</th>
                            <th width="11%">报警值</th>
                            <th width="12%">操作</th>
                        </tr>
                        </thead>
                        <tbody id="tbody">
                        <c:forEach var="warn" items="${list}" varStatus="i">
                            <tr>
                                <td>${i.index+1}</td>
                                <td>${warn.SENSOR_CODE}</td>
                                <td>${warn.SENSOR_PSTN}</td>
                                <td>${warn.SENSOR_NAME}</td>
                                <td>${warn.WARNING_TIME}</td>
                                <td>
                                    <c:if test="${warn.WARNING_LEVEL == '1'}"><span style="color:yellowgreen;">一级上限预警</span></c:if>
                                    <c:if test="${warn.WARNING_LEVEL == '2'}"><span style="color:darkorange;">二级上限预警</span></c:if>
                                    <c:if test="${warn.WARNING_LEVEL == '3'}"><span style="color:red;">三级上限预警</span></c:if>
                                    <c:if test="${warn.WARNING_LEVEL == '11'}"><span style="color:yellowgreen;">一级下限预警</span></c:if>
                                    <c:if test="${warn.WARNING_LEVEL == '22'}"><span style="color:darkorange;">二级下限预警</span></c:if>
                                    <c:if test="${warn.WARNING_LEVEL == '33'}"><span style="color:red;">三级下限预警</span></c:if>
                                </td>
                                <td>${warn.WARNING_VALUE}${warn.PART_UNIT}</td>
                                <td>
                                    <div id="wrap">
                                        <section id="ajax">
                                            <a href="<%=path%>/publicCtrl/toSomeWhere?str1=code&code=${warn.SENSOR_CODE}&str2=tim&tim=${warn.WARNING_TIME}&returnValue=main/warnLine"
                                               class="btn btn-warning btn-xs framer" data-framer-type="ajax">
                                                <span class="glyphicon glyphicon-stats"></span> 查看报警曲线
                                            </a>
                                        </section>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="text-center">
                        <img src="<%=path%>/img/loading.gif" id="load_img" style="display:none;width:15px;height:15px;">
                        <button class="btn btn-default btn-sm" onclick="getMore();">加载更多</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=path %>/pages/main/js/warnList.js"></script>
</html>
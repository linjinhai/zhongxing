<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page language="java" import="java.util.*,com.sendyago.util.common.PageBean,com.sendyago.util.common.PartBase" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<title>健康监测系统</title>
<%@ include file="/pages/common/common_query2.jsp"%>

</head>
<%
int partSecond= PartBase.GetPart(request.getParameter("partId"))*1000;
 PageBean pageBean = (PageBean) request.getAttribute("pageBean");
%>


<body>

    <div class="page-header">
        <ol class="breadcrumb">
            <li><span class="glyphicon glyphicon-home" aria-hidden="true"></span>
                首页</li>
            <li>系统工作状态监控</li>
            <li class="active">传感器实时状态监控</li>
        </ol>
    </div>
    <div class="container-fluid">
        <div class="col-md-12">
            <div class="panel">
                <div class="panel-heading">
                    <form action="" method="post" id="form1" name="form1" class="navbar-form navbar-left width100">
                        <input type="hidden" id="currentPage" name="currentPage" value="1" />
                        <input type="hidden" id="mapCheckType" name="mapCheckType" value="${map.checkType}" />
                        <input type="hidden" id="mapPartType" name="mapPartType" value="${map.partType}" />
                        <input type="hidden" id="mapSectionList" name="mapSectionList" value="${map.sectionList}" />
                        <input type="hidden" id="path" name="path" value="<%=path%>" />
                         <input type="hidden" id="menu_id" name="menu_id" value="${param.shiro_menu_id}" />
                         <input type="hidden" id="role_id" name="role_id" value="${param.shiro_role_id}" />
                         <input type="hidden" id="user_id" name="user_id" value="${param.shiro_user_id}" />
                         <input type="hidden" id="partSecond" name="partSecond" value="<%=partSecond%>" />
                        <div class="form-group">
                            <div class="input-group input-group-no-border">
                                <span class="input-group-addon"><font color="#ffffff">传感器类型</font></span> <select
                                    class="form-control" id="checkType" name="checkType"  ></select>

                            </div>
                           
                             <div class="input-group input-group-no-border">
                                <span class="input-group-addon"><font color="#ffffff">传感器编号</font></span> <input
                                    type="text" id="s_sensor" name="s_sensor" value="${map.s_sensor}"   class="form-control" /> 
                           
                            </div>
             
          
                        

                        </div>
                        <div class="form-group pull-right">
                            <div class="btn-group btn-group-sm " role="group">
                                <button type="button" class="btn btn-info"
                                    onclick="javascript:sousuo();"><span class="glyphicon glyphicon-list"></span> 列表查询</button>
                                
                            </div>
                        </div>
                        <div class="clearfix"></div>
                </div>
    
                <div  class="panel-body">
             	<table id="tb"
						class="table table-striped table-hover table-bordered">
						<thead>
							<tr>
								<th>序号</th>
								<th>传感器编号</th>
								<th>传感器名称</th>
								<th>传感器类型</th>
								<th>传感器种类</th>
								<th>截面</th>
								<th>工作状态</th>
								<th>当前值</th>
								<th>时间</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
                </div>
            </div>

        </div>

        </form>
    </div>
</body>
<script type="text/javascript" src="<%=path%>/pages/state/js/queryState.js"></script>
</html>

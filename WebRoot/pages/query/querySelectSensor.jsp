<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page language="java" import="java.util.*,com.sendyago.util.common.PartBase" pageEncoding="utf-8"%>
<%@ include file="/pages/common/common_query2.jsp"%>

<% 
int partSecond= PartBase.GetPart(request.getParameter("partId"))*1000;
%>
  
  <input type="hidden" id="flag" name="flag" value="${param.flag}" />
  <input type="hidden" id="partId" name="partId" value="${param.partId}" />
  <input type="hidden" id="path" name="path" value="<%=path%>" />
   <input type="hidden" id="partSecond" name="partSecond" value="<%=partSecond%>" />
  <c:if test="${param.flag eq '1'}">
    <input type="hidden" id="section_id" name="section_id" value="${param.sectionList}" />
  </c:if>
   <c:if test="${param.flag eq '2'}">
    <input type="hidden" id="section_id" name="section_id" value="${param.sectionList0}" />
  </c:if>

	<section id="iframe">
		<div id="frmTitle">传感器数据</div>
		<div class="panel-body" style="height:364px;">
			<div id="sensorValue"></div>
		</div>

	</section>
  <script type="text/javascript" src="<%=path%>/pages/query/js/query.js" ></script> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<%@ page language="java" pageEncoding="utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name=”renderer” content=”webkit”>
<title>平湖编组站大桥健康监测系统</title>
<%@ include file="/pages/common/common.jsp"%>


<style>
#i1 {
	background: #5DA24C;
	border-left: 1px solid #000;
	border-top: 1px solid #000;
	width: 100%;
	padding: 0;
	margin: 0;
}

#i1 td {
	color: white;
	font-size: 12px;
	font-family: "Microsoft Yahei", 'Open Sans', "Helvetica Neue", Helvetica, Arial, sans-serif;
	border-right: 1px solid #000;
	border-bottom: 1px solid #000;
	text-align: center;
}

#i1 input[type="text"] {
	width: 100%;
	padding: 0;
	margin: 0;
	line-height: 24px;
	color: black;
}
</style>
</head>
<body>
	<input type="hidden" id="path" value="<%=path%>" />
	<div class="proccess" id="loading" style="display: none; height: 176%;">
		<b>正在加载中...请稍候</b>
	</div>
	<form name="form1" id="form1" method="post">
		<input type="hidden" name="assess_id" value="${id }" id="aid" />

		<div class="page-header">
			<ol class="breadcrumb">
				<li><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 安全评估</li>

			</ol>
		</div>
		<div class="container-fluid">
			<div class="row">

				<div style="width: 100%;" class="col-lg-6">
					<div class="panel panel-default">
						<div class="panel-heading">
							<span>安全评估</span>


						</div>
						<div class="panel-body">
							<div class="navbar-form navbar-left">
								<input type="radio" name="pg" onclick="$('#i1').css('display','');$('#i2').css('display','none');" checked> 评估信息 <input type="radio" name="pg" onclick="$('#i2').css('display','');$('#i1').css('display','none');"> 巡检信息
							</div>
							<div class="btn-group-sm pull-right" role="group">
								<button class="btn btn-warning btn-group-sm2" type="button" onclick="javascript:kspg();">
									<span class="glyphicon glyphicon-time"></span>评估
								</button>
								&nbsp;
								<button class="btn btn-sm btn-success" type="button" onclick="javascript:bc();">
									<span class="glyphicon glyphicon-floppy-save"></span>保存
								</button>
								&nbsp;
								<button class="btn btn-sm btn-info" type="button" onclick="javascript:dc();">
									<span class="glyphicon glyphicon-export"></span>导出
								</button>
								&nbsp;
								<button class="btn btn-sm btn-info" type="button" onclick="javascript:window.print();">
									<span class="glyphicon glyphicon-print"></span>打印
								</button>
								&nbsp;
								<button class="btn btn-sm btn-info" type="button" onclick="javascript:window.location.href='<%=path%>/essence/queryrgxj?type=aq';">
									<span class="glyphicon glyphicon-repeat"></span>返回
								</button>
								&nbsp;
							</div>
							<br /> <br />
							<table cellspacing="0" cellpadding="0" id="i1">
								<tr class="">
									<td>隧道编码</td>
									<td colspan="3"><input type="text" id='MP_1' name='MP_1' value='' placeholder="请输入隧道编码" /></td>
									<td>结构情况</td>
									<td colspan="3"><input type="text" id='MP_2' name='MP_2' value='' placeholder="请输入结构情况" /></td>
									<td colspan="2">上次检查日期</td>
									<td><input type="text" id='MP_3' name='MP_3' value='' placeholder="请输入上次检查日期" /></td>
								</tr>
								<tr class="">
									<td>隧道名称</td>
									<td colspan="3"><input type="text" id='MP_4' name='MP_4' value='' placeholder="请输入隧道名称" /></td>
									<td>隧道长</td>
									<td colspan="3"><input type="text" id='MP_5' name='MP_5' value='' placeholder="请输入隧道长" /></td>
									<td colspan="2">建成年月</td>
									<td><input type="text" id='MP_6' name='MP_6' value='' placeholder="请输入建成年月" /></td>
								</tr>
								<tr class="">
									<td>路线名称</td>
									<td colspan="3"><input type="text" id='MP_7' name='MP_7' value='' placeholder="请输入路线名称" /></td>
									<td>最大跨径</td>
									<td colspan="3"><input type="text" id='MP_8' name='MP_8' value='' placeholder="请输入最大跨径" /></td>
									<td colspan="2">本次检查日期</td>
									<td><input type="text" id='MP_9' name='MP_9' value='' placeholder="请输入本次检查日期" /></td>
								</tr>
								<tr class="">
									<td>桩号</td>
									<td colspan="3"><input type="text" id='MP_10' name='MP_10' value='' placeholder="请输入桩号" /></td>
									<td>管养单位</td>
									<td colspan="3"><input type="text" id='MP_11' name='MP_11' value='' placeholder="请输入管养单位" /></td>
									<td colspan="2">上次大中修日期</td>
									<td><input type="text" id='MP_12' name='MP_12' value='' placeholder="请输入上次大中修日期" /></td>
								</tr>
								<tr class="">
									<td colspan="3">桥梁组成及评级</td>
									<td colspan="4">桥梁部件及评级</td>
									<td rowspan="2">维修范围</td>
									<td rowspan="2">维修方式</td>
									<td rowspan="2">维修时间</td>
									<td rowspan="2">是否需要进行特殊检查</td>
								</tr>
								<tr class="" id="ko0">
									<td>桥梁组成</td>
									<td>技术状况评分</td>
									<td>技术状况等级</td>
									<td>类别</td>
									<td>评价部件</td>
									<td>技术状况评分</td>
									<td>技术状况等级</td>
								</tr>


								<tr>
									<td colspan="4">总体技术状况评分</td>
									<td colspan="2"><input type="text" id='YU_1' name='YU_1' value='' readonly="readonly" /></td>
									<td colspan="3">总体技术状况等级</td>
									<td colspan="2"><input type="text" id='YU_2' name='YU_2' value='' readonly="readonly" /></td>
								</tr>
								<tr>
									<td colspan="4">全桥清洁状况评分(0～100)</td>
									<td colspan="2"><input type="text" id='YU_3' name='YU_3' value='' placeholder="请输入清洁状况评分" /></td>
									<td colspan="3">保养、小修状况评分(0～100)</td>
									<td colspan="2"><input type="text" id='YU_4' name='YU_4' value='' placeholder="请输入保养、小修状况评分" /></td>
								</tr>
								<tr>
									<td colspan="2">养护建议</td>
									<td colspan="9"><input type="text" id='YU_5' name='YU_5' value='' placeholder="请输入养护建议" /></td>

								</tr>
								<tr>
									<td>记录人</td>
									<td colspan="3"><input type="text" id='YU_6' name='YU_6' value='' placeholder="请输入记录人" /></td>
									<td>负责人</td>
									<td colspan="2"><input type="text" id='YU_7' name='YU_7' value='' placeholder="请输入负责人" /></td>
									<td colspan="2">下次检查时间</td>
									<td colspan="2"><input type="text" id='YU_8' name='YU_8' value='' placeholder="请输入下次检查时间" /></td>

								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>

		</div>

	</form>
	<iframe src="<%=path%>/pages/essence/editGjData.jsp?t=t&id=${id}" width="100%" height="700px;" id="i2" style="display: none;" frameborder='no' border='0'></iframe>
</body>
<script src="<%=path%>/pages/essence/js/aqpg.js"></script>
</html>
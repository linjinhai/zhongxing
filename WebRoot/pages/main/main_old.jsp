<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name=”renderer” content=”webkit”>
    <title><%@ include file="/pages/main/title.jsp"%></title>
    <%@ include file="/pages/common/common.jsp" %>

    <script type="text/javascript" src="<%=path %>/highcharts/highcharts.js"></script>
    <script type="text/javascript" src="<%=path %>/highcharts/exporting.js"></script>
    <script type="text/javascript" src="<%=path %>/highcharts/style-grid-light.js"></script>
    <script type="text/javascript" src="<%=path %>/js/UnityObject2.js"></script>
    <%--<script type="text/javascript" src="<%=path %>/highcharts/style-sand-signka.js"></script>--%>
<script type="text/javascript">
$(function(){
	$.ajax({
		type : "POST",
		async : false,
		url :  "<%=path%>/mioncontroller/getSectionPath?sid=-1",
		dataType : "json",
		success : function(data) {
			if(data.length>0){
				$("#dt").attr('src','<%=path%>' + data[0].SECTION_PATH);
			}
		}
	});
	//根据截面id 获取该截面下所有传感器
	$.ajax({
		type : "POST",
		async : false,
		url :  "<%=path%>/mioncontroller/getSectionAllSensor?sid=-1",
		dataType : "json",
		success : function(data) {
			for(var i=0;i<data.length;i++){
				if(data[i].MAIN_P_LEFT!=null){
					var cv='';
					cv+='<div style="position: absolute; top: '+data[i].MAIN_P_TOP+'%; left: '+data[i].MAIN_P_LEFT+'%;" class="dragMe" id="tp'+data[i].SENSOR_ID+'" title="'+data[i].SENSOR_CODE+'" >';
					cv+='<img src="<%=path%>/' + data[i].PART_IMG + '" style="width: 15px; height: 15px;" />';
					cv += '</div>';
					$("#unityPlayer1").append(cv);
				}
				if(data[i].MAIN_V_LEFT!=null){
					var cv='';
					cv += '<div  style="background-color:white;position:absolute;width:;height:18px;z-index:1;left: '+data[i].MAIN_V_LEFT+'%;top: '+data[i].MAIN_V_TOP+'%;" class="dragMe" id="va' + data[i].SENSOR_ID + '" >';
					cv += '<span ><a href="javascript:sensor_id(&quot;'+data[i].SENSOR_CODE+'&quot;);" >' + data[i].SENSOR_CODE + '</a></span>';
					cv += '</div>';
					$("#unityPlayer1").append(cv);
				}
			}
			
			
			}
		});
});
</script>

    <link href="<%=request.getContextPath() %>/viwer3d/style.css" rel="stylesheet" />
	<script src="<%=request.getContextPath() %>/viwer3d/three.min.js"></script>
    <script src="<%=request.getContextPath() %>/viwer3d/viewer3D.min.js"></script>
    <script>
        var docs =
            [{"path":"../../viwer3d/0/0.svf","name":"提篮拱桥1.3DS"}]
        ;

        var oViewer =null ;
        $(document).ready (function () {
            //var options ={ 'document': '', 'env': 'AutodeskProduction' } ;
            var options ={ 'docid': docs [0].path, env: 'Local' } ;

            //oViewer =new Autodesk.Viewing.Viewer3D ($("#viewer") [0], {}) ; // No toolbar
            oViewer =new Autodesk.Viewing.Private.GuiViewer3D ($("#viewer") [0], {}) ; // With toolbar
            Autodesk.Viewing.Initializer (options, function () {
				oViewer.initialize () ;
                oViewer.addEventListener (Autodesk.Viewing.GEOMETRY_LOADED_EVENT, function (event) {
                    //oViewer.removeEventListener (Autodesk.Viewing.GEOMETRY_LOADED_EVENT, arguments.callee) ;
                    oViewer.fitToView (true) ;
                    setTimeout (function () { oViewer.autocam.setHomeViewFrom (oViewer.navigation.getCamera ()) ; }, 1000) ;
                }) ;
				oViewer.loadModel (options.docid) ;

				for ( var i =0 ; i < docs.length ; i++ ) {
					var r =$('<div><button id="view_' + i + '">'
						+ docs [i].name
						+ '<div><img id="img__' + i + '" src="' + docs [i].path + '.png"></div></button></div>') ;
					$('#list').append (r) ;
					$('#view_' + i).click (function (e) {
						e.stopPropagation () ;
                        //oViewer.impl.unloadCurrentModel () ;
                        // API would be tearDown()/setUp()
                        // tearDown() unloads extensions too, so you need setUp() after that to load again
                        // setUp() requires the viewer configuration again, the one you use to start the viewer.
                        oViewer.tearDown () ;
                        oViewer.setUp ({ env: 'Local' }) ;
						var i =parseInt (e.target.id.substring (5)) ;
						oViewer.loadModel (docs [i].path) ;
					}) ;
				}
			}) ;
        }) ;
    </script>
</head>
<body onload="initLines();">
<input type="hidden" id="path" value="<%=path %>" />
<div class="page-header">
    <ol class="breadcrumb">
        <li><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 系统首页</li>

    </ol>
</div>
<div class="container-fluid">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <span>整体BIM图形预览</span>
                    <a href="#" class="dropdown-toggle pull-right" data-toggle="dropdown">
                        <span class="glyphicon glyphicon-align-justify" aria-hidden="true"></span>
                    </a>
                    <ul class="dropdown-menu pull-right">
                        <li><a href="#" onclick="hideUnity();">显示/隐藏</a></li>
                    </ul>
                </div>
                <div class="panel-body">
                    <div style="height:400px;">
                        <div id="viewer" style="display: ;position: relative;height: 100%">
                        <!-- 
                            <div class="missing"></div>
                            <div class="broken"></div>
                            -->
                           
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <span>传感器实时曲线</span>
                    <a href="#" class="dropdown-toggle pull-right" data-toggle="dropdown">
                        <span class="glyphicon glyphicon-align-justify" aria-hidden="true"></span>
                    </a>
                    <ul class="dropdown-menu pull-right">
                        <li><a href="#" id="printMonitorChart">打印</a></li>
                        <li><a href="#" id="exportMonitorChart">导出图像</a></li>
                    </ul>
                </div>
                <div>
                    <div class="proccess" id="loading_line" style="display:block;height:301px;width:97%; margin-top:39px;margin-left:8px;"><b>正在加载中...请稍候</b></div>
                    <div style="width:100%;height:300px;" id="monitor-line"></div>
                </div>
            </div>
        </div>
        <div class="col-lg-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <span>传感器时均值柱状图(过去24小时)</span>
                    <a href="#" class="dropdown-toggle pull-right" data-toggle="dropdown">
                        <span class="glyphicon glyphicon-align-justify" aria-hidden="true"></span>
                    </a>
                    <ul class="dropdown-menu pull-right">
                        <li><a href="#" id="printHourChart">打印</a></li>
                        <li><a href="#" id="exportHourChart">导出图像</a></li>
                    </ul>
                </div>
                <div>
                    <div class="proccess" id="loading_hline" style="display:block;height:301px;width:97%; margin-top:39px;margin-left:8px;"><b>正在加载中...请稍候</b></div>
                    <div style="height:300px;" id="hour-line"></div>
                </div>
            </div>
        </div>
        <div class="col-lg-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <span>传感器报警分析柱状图(1周)</span>
                    <a href="#" class="dropdown-toggle pull-right" data-toggle="dropdown">
                        <span class="glyphicon glyphicon-align-justify" aria-hidden="true"></span>
                    </a>
                    <ul class="dropdown-menu pull-right">
                        <li><a href="#" id="printWarnColumn">打印</a></li>
                        <li><a href="#" id="exportWarnColumn">导出图像</a></li>
                    </ul>
                </div>
                <div>
                    <div class="proccess" id="loading_column" style="display:block;height:301px;width:97%; margin-top:39px;margin-left:8px;"><b>正在加载中...请稍候</b></div>
                    <div style="height:300px;" id="warn-column"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<%=path %>/pages/main/js/main.js"></script>
</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title><%@ include file="/pages/main/title.jsp"%></title>
    <%--<%@ include file="/pages/common/common.jsp" %>--%>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>

    <link href="<%=path %>/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=path%>/css/style2.css" rel="stylesheet"/>
    <link href="<%=path%>/viwer3d/style.css" rel="stylesheet" />
    <script src="<%=path %>/js/jquery.min.js"></script>
    <script src="<%=path%>/viwer3d/three.min.js"></script>
    <script src="<%=path%>/viwer3d/viewer3D.min.js"></script>
    <script src="<%=path%>/viwer3d/ScreenShotManager.js"></script>
    <script type="text/javascript" src="<%=path %>/highcharts/highcharts.js"></script>
    <script type="text/javascript" src="<%=path %>/highcharts/exporting.js"></script>
    <script type="text/javascript" src="<%=path %>/highcharts/style-grid-light.js"></script>
</head>
<body>
<!-- Start: Content -->
<div class="container-fluid content">
    <div class="row">
        <!-- Page Header -->
        <div class="page-header">
            <div class="pull-left">
                <ol class="breadcrumb visible-sm visible-md visible-lg">
                    <li><a href="index.html"><i class="icon fa fa-home"></i>首页</a></li>
                    <li class="active"><i class="fa fa-laptop"></i>整体监测与预警</li>
                </ol>
            </div>
            <div class="pull-right">
                <%--<h2>运维展示系统</h2>--%>
            </div>
        </div>
        <!-- End Page Header -->
        <!-- Main Page -->
        <div class="main" id="viewer">
            <%--<div class="m-f-block">--%>
            <div class="l-block" style="left:10px;display:none;">
                <div class="m-block">
                    <div class="m-block-title">机房功能</div>
                    <div class="m-block-cont" id="chart1"></div>
                </div>
                <div class="m-block">
                    <div class="m-block-title">机房功能</div>
                    <div class="m-block-cont" id="chart2"></div>
                </div>
                <div class="m-block">
                    <div class="m-block-title">机房功能</div>
                    <div class="m-block-cont"></div>
                </div>
            </div>

            <div class="l-block" style="left:315px;display:none;">
                <div class="m-block">
                    <div class="m-block-title">机房功能</div>
                    <div class="m-block-cont"></div>
                </div>
                <div class="m-block">
                    <div class="m-block-title">机房功能</div>
                    <div class="m-block-cont"></div>
                </div>
                <div class="m-block">
                    <div class="m-block-title">机房功能</div>
                    <div class="m-block-cont"></div>
                </div>
            </div>

            <div class="r-block" style="display:none;">
                <div class="m-block">
                    <div class="m-block-title">机房功能</div>
                    <div class="m-block-cont" style="height: 93%;overflow: hidden;overflow-y: scroll;"></div>
                </div>
            </div>
            <%--</div>--%>
        </div>
        <!-- End Main Page -->

    </div>
</div><!--/container-->
<div class="clearfix"></div>

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
        // alert("111");
        Autodesk.Viewing.Initializer (options, function () {
            oViewer.initialize () ;
            oViewer.addEventListener (Autodesk.Viewing.GEOMETRY_LOADED_EVENT, function (event) {
                //oViewer.removeEventListener (Autodesk.Viewing.GEOMETRY_LOADED_EVENT, arguments.callee) ;
                oViewer.fitToView (true) ;
                setTimeout (function () { oViewer.autocam.setHomeViewFrom (oViewer.navigation.getCamera ()) ; }, 1000) ;
            }) ;

            oViewer.loadModel (options.docid) ;

            var config = {extensions: [], modId:1};
            var screenShot = new Autodesk.ADN.Viewing.Extension.ScreenShotManager(oViewer, config);
            // 加载扩展内容
            screenShot.load();
        }) ;
    }) ;



    Highcharts.chart('chart1', {
        chart: {
            type: 'spline'
        },
        title: {
            // text: 'Snow depth at Vikjafjellet, Norway'
        },
        subtitle: {
            // text: 'Irregular time data in Highcharts JS'
        },
        xAxis: {
            type: 'datetime',
            dateTimeLabelFormats: { // don't display the dummy year
                month: '%e. %b',
                year: '%b'
            },
            title: {
                text: 'Date'
            }
        },
        yAxis: {
            title: {
                text: 'Snow depth (m)'
            },
            min: 0
        },
        tooltip: {
            headerFormat: '<b>{series.name}</b><br>',
            pointFormat: '{point.x:%e. %b}: {point.y:.2f} m'
        },

        plotOptions: {
            spline: {
                marker: {
                    enabled: true
                }
            }
        },

        colors: ['#6CF', '#39F', '#06C', '#036', '#000'],

        // Define the data points. All series have a dummy year
        // of 1970/71 in order to be compared on the same x axis. Note
        // that in JavaScript, months start at 0 for January, 1 for February etc.
        series: [{
            name: "Winter 2014-2015",
            data: [
                [Date.UTC(1970, 10, 25), 0],
                [Date.UTC(1970, 11,  6), 0.25],
                [Date.UTC(1970, 11, 20), 1.41],
                [Date.UTC(1970, 11, 25), 1.64],
                [Date.UTC(1971, 0,  4), 1.6],
                [Date.UTC(1971, 0, 17), 2.55],
                [Date.UTC(1971, 0, 24), 2.62],
                [Date.UTC(1971, 1,  4), 2.5],
                [Date.UTC(1971, 1, 14), 2.42],
                [Date.UTC(1971, 2,  6), 2.74],
                [Date.UTC(1971, 2, 14), 2.62],
                [Date.UTC(1971, 2, 24), 2.6],
                [Date.UTC(1971, 3,  1), 2.81],
                [Date.UTC(1971, 3, 11), 2.63],
                [Date.UTC(1971, 3, 27), 2.77],
                [Date.UTC(1971, 4,  4), 2.68],
                [Date.UTC(1971, 4,  9), 2.56],
                [Date.UTC(1971, 4, 14), 2.39],
                [Date.UTC(1971, 4, 19), 2.3],
                [Date.UTC(1971, 5,  4), 2],
                [Date.UTC(1971, 5,  9), 1.85],
                [Date.UTC(1971, 5, 14), 1.49],
                [Date.UTC(1971, 5, 19), 1.27],
                [Date.UTC(1971, 5, 24), 0.99],
                [Date.UTC(1971, 5, 29), 0.67],
                [Date.UTC(1971, 6,  3), 0.18],
                [Date.UTC(1971, 6,  4), 0]
            ]
        }]
    });

    Highcharts.chart('chart2', {
        chart: {
            type: 'bar'
        },
        // title: {
        //     // text: 'Historic World Population by Region'
        // },
        // subtitle: {
        //     // text: 'Source: <a href="https://en.wikipedia.org/wiki/World_population">Wikipedia.org</a>'
        // },
        xAxis: {
            categories: ['Africa', 'America', 'Asia', 'Europe', 'Oceania'],
            title: {
                text: null
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Population (millions)',
                align: 'high',
                offset: -30,
                y: -10
            },
            labels: {
                overflow: 'justify'
            }
        },
        tooltip: {
            valueSuffix: ' millions'
        },
        plotOptions: {
            bar: {
                dataLabels: {
                    enabled: true
                }
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'top',
            x: -40,
            y: 80,
            floating: true,
            borderWidth: 1,
            backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
            shadow: true
        },
        credits: {
            enabled: false
        },
        series: [{
            name: 'Year 1800',
            data: [107, 31, 635, 203, 2]
        }, {
            name: 'Year 1900',
            data: [133, 156, 947, 408, 6]
        }, {
            name: 'Year 2000',
            data: [814, 841, 3714, 727, 31]
        }, {
            name: 'Year 2016',
            data: [1216, 1001, 4436, 738, 40]
        }]
    });
</script>
<!-- end: JavaScript-->
</body>
</html>

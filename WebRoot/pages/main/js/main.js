var path = $('#path').attr("value"), dataInterval;
/**
 *   u3d展示
 */
var config = {
    width: '100%',
    height: 400,
    params: { enableDebugging:"0" }

};
var u = new UnityObject2(config);

jQuery(function() {
    var $missingScreen = jQuery("#unityPlayer").find(".missing");
    var $brokenScreen = jQuery("#unityPlayer").find(".broken");
    $missingScreen.hide();
    $brokenScreen.hide();

    u.observeProgress(function (progress) {
        switch(progress.pluginStatus) {
            case "broken":
                $brokenScreen.find("a").click(function (e) {
                    e.stopPropagation();
                    e.preventDefault();
                    u.installPlugin();
                    return false;
                });
                $brokenScreen.show();
                break;
            case "missing":
                $missingScreen.find("a").click(function (e) {
                    e.stopPropagation();
                    e.preventDefault();
                    u.installPlugin();
                    return false;
                });
                $missingScreen.show();
                break;
            case "installed":
                $missingScreen.remove();
                break;
            case "first":
                break;
        }
    });
    u.initPlugin(jQuery("#unityPlayer")[0], path+"/pages/main/unity3D/yongjiangweb.unity3d");
});


/**
 * 初始化系统首页
 * 延迟3秒展示所有图形
 * 获取所有传感器实时数据/状态
 */
function initLines() {
	
	$.ajax({
		type : "POST",
		async : false,
		url :  path+"/monitoringController/getAllSensorList",
		dataType : "json",
		success : function(data) {
			console.log(data);
			if(data.length>0){
				var sensor_code = data[0].SENSOR_CODE;
				 window.setTimeout("showLines('"+sensor_code+"');", 1000);
			}
			if(data.length==0){
				$("#loading_hline").html('暂无传感器信息...请添加传感器');
				$("#loading_column").html('暂无传感器信息...请添加传感器');
				$("#loading_line").html('暂无传感器信息...请添加传感器');
			}
			
			
		}

	});
	
	
   

    // 实时获取所有传感器数据信息
    getAllSensorDataTimerStart();
}


/**
 * 实时获取数据器数据定时器
 * 每隔5秒
 */
function getAllSensorDataTimerStart() {
    dataInterval = setInterval(function() {
        sendMessage();
    }, 5000);
}

/**
 * 实时查询传感器数据
 * 与unity3d进行通信,上传数据
 */
function sendMessage() {
    var sensor_code, status, data, i;
    // Ajax获取所有传感器数据
    $.ajax({
        type: 'post',
        url: path + '/mcontroller/getAllSensorDatas',
        cache: false,
        dataType: 'json',
        success: function (datas) {
            for (i=0; i<datas.length; i++) {
                var thisData = datas[i];
                sensor_code = thisData.SENSOR_CODE;
                status = thisData.UPDATE_STATUS;
                data = thisData.UPDATE_VALUE;

                var values = sensor_code+","+status+","+data;
                //调用SendMessage函数访问Unity3D浏览器对象中的脚本函数
                try {
                    u.getUnity().SendMessage("GetObject","GetFunction", values);
                } catch(e) {
                    //alert("error");
                }
            }
        }
    });
}


/**
 * 显示或隐藏unity3d插件
  */
function hideUnity() {
    // 获取3d的div对象
    var unityObj = document.getElementById("unityPlayer");
    if(unityObj.style.display == "") {
        unityObj.style.display = "none";
    } else {
        unityObj.style.display = "";
    }
}

/**
 * 展示所有图形
 */
function showLines(sensor_code) {
    monitorLine(sensor_code);
    // hourLine(sensor_code);
    hourColumn(sensor_code);
    warnColumn();
}

/**
 * 点击3d图形上的传感器时刷新曲线图形
 */
function sensor_id(sensor_code) {
    // 根据id查询实时曲线
    document.getElementById("loading_line").style.display = "block"
    monitorLine(sensor_code);
    // 根据id查询时均值曲线
    document.getElementById("loading_hline").style.display = "block";
    hourColumn(sensor_code);
}

// 时间缓存
var lastmillis, monitorChart, hourChart;

/**
 * 打印实时曲线
 */
function printMonitorChart() {
    var chart = $('#monitor-line').highcharts();
    chart.print();
}

/**
 * 实时曲线图
  */
function monitorLine(sensor_code) {
    // AJAX获取后台数据
    $.ajax({
        type:'post',
        url: path+'/mcontroller/monitorLines?SENSOR_CODE=' + sensor_code,
        cache:false,
        dataType:'json',
        success:function(data){
            // 传感器id
            var sensor_id = data[0].SENSOR_ID;
            // 传感器类型名称
            var type_name = data[0].TYPE_NAME;
            // 传感器编号
            var sensor_code = data[0].SENSOR_CODE;
            // 传感器名称
            var sensor_name = data[0].SENSOR_NAME;
            // 传感器单位
            var sensor_unit = data[0].PART_UNIT;
            // 位置
            var sensor_pstn = data[0].SENSOR_PSTN;
            // 存储时间间隔(秒)
            var sensor_second = data[0].PART_SECOND;
            // 小数点位数
            var sensor_point = data[0].PART_POINT;
            // 数据
            var list = data[0].list;
            var plotLines = setWarnValue(data[0]);

            $(function () {
                $(document).ready(function() {
                    Highcharts.setOptions({
                        global: {
                            useUTC: false
                        }
                    });

                    monitorChart = $('#monitor-line').highcharts({
                        chart: {
                            //type: 'line',
                            animation: Highcharts.svg, // don't animate in old IE
                            events: {
                                load: function() {
                                    // set up the updating of the chart each second
                                    var series = this.series[0];
                                    setInterval(function() {
                                        $.ajax({
                                            type: 'post',
                                            url: path + '/mcontroller/getPoint?SENSOR_ID=' + sensor_id,
                                            cache: false,
                                            dataType: 'json',
                                            success: function (data) {
                                                // var x = (new Date()).getTime(), // current time
                                                //     y = Math.random();
                                                if(data.length > 0) {
                                                    var y = parseFloat(data[0].UPDATE_VALUE),
                                                        x = data[0].UPDATE_TIME;
                                                    if(lastmillis != x){
                                                        series.addPoint([x, y], true, true);
                                                    }
                                                    lastmillis = x;
                                                }
                                            }
                                        });
                                    }, sensor_second * 1000);
                                }
                            }
                        },
                        credits: {
                            enabled: false
                        },
                        title: {
                            text: type_name + '实时曲线',
                            //floating: true
                            //verticalAlign: 'top'
                        },
                        subtitle: {
                            text: '编号:' + sensor_code + ", 位置:" +sensor_pstn
                        },
                        xAxis: {
                            type: 'datetime',
                            tickPixelInterval: 100
                        },
                        yAxis: {
                            title: {
                                text: '单位:' + sensor_unit,
                                align: 'high',
                                rotation: 0,
                                offset: -30,
                                y: -10
                            },
                            plotLines: plotLines
                        },
                        tooltip: {
                            formatter: function() {
                                return '<b>'+ this.series.name +'</b><br/>'+
                                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br/>'+
                                    Highcharts.numberFormat(this.y, sensor_point) + " " + sensor_unit;
                            }
                        },
                        legend: {
                            enabled: false
                        },
                        plotOptions: {
                            series: {
                                marker: {
                                    enabled: false,
                                    states: {
                                        hover: {
                                            enabled: true,
                                            radius: 3
                                        }
                                    }
                                }
                            }
                        },
                        exporting: {
                            enabled: false
                        },
                        series: [{
                            name: sensor_name + "(" +sensor_code +")",
                            data: (function() {
                                // generate an array of random data
                                var data = [],
                                    time = (new Date()).getTime(),
                                    i;
                                /*
                                 // 曲线共展示50个点,如查询的数据个数小于50,则在前面补空白
                                 // 如果查询的数据个数为0,则补充50个空白
                                 */
                                if(list.length > 0) {
                                    var len = list.length, startLen, endLen;
                                    if(len > 50){
                                        startLen = len - 50;
                                        endLen = len;
                                    }else{
                                        startLen = 0;
                                        endLen = len;
                                    }

                                    // 数据长度小于50条,则前面填上空白
                                    if(endLen < 50) {
                                        var newLen = 50 - endLen;
                                        for(i=newLen*(-1); i<0; i++) {
                                            data.push({
                                                x: time + i * sensor_second * 1000,
                                                y: null
                                            });
                                        }
                                    }
                                    // 循环数据内容
                                    for (i=startLen; i<endLen; i++) {
                                        var val = parseFloat(list[i].INPUT_VALUE.toFixed(sensor_point)),
                                            tim = parseFloat(list[i].INPUT_TIME);
                                        data.push({
                                            x: tim,
                                            y: val
                                        });
                                    }
                                } else {
                                    for (i = -49; i <= 0; i++) {
                                        data.push({
                                            x: time + i * sensor_second * 1000,
                                            y: null
                                        });
                                    }
                                }
                                document.getElementById("loading_line").style.display = "none";
                                return data;
                            })()
                        }]
                    });
                    // 打印, printMonitorChart为按钮id, monitor-line为曲线div的id
                    $('#printMonitorChart').click(function () {
                        var chart = $('#monitor-line').highcharts();
                        chart.print();
                    });

                    // 导出图片
                    $('#exportMonitorChart').click(function () {
                        var chart = $('#monitor-line').highcharts();
                        chart.exportChart();
                    });
                });
                
            });
        }
    });
}

/**
 * 时均值曲线图
 * 注: 该功能已失效
  */
function hourLine(sensor_code) {

    $.ajax({
        type: 'post',
        url: path + '/mcontroller/hourLines?SENSOR_CODE=' + sensor_code,
        cache: false,
        dataType: 'json',
        success: function (data) {
            // 传感器id
            var sensor_id = data[0].SENSOR_ID;
            // 传感器类型名称
            var type_name = data[0].TYPE_NAME;
            // 传感器编号
            var sensor_code = data[0].SENSOR_CODE;
            // 传感器名称
            var sensor_name = data[0].SENSOR_NAME;
            // 传感器单位
            var sensor_unit = data[0].PART_UNIT;
            // 位置
            var sensor_pstn = data[0].SENSOR_PSTN;
            // 存储时间间隔(秒)
            var sensor_second = 60 * 60 * 1000;
            // 小数点位数
            var sensor_point = data[0].PART_POINT;
            // 数据
            var list = data[0].list;
            // 预警线
            var plotLines = setWarnValue(data[0]);

            $(function () {
                $(document).ready(function () {
                    Highcharts.setOptions({
                        global: {
                            useUTC: false
                        }
                    });

                    hourChart = $('#hour-line').highcharts({
                        chart: {
                            //type: 'line',
                            animation: Highcharts.svg, // don't animate in old IE
                            events: {
                                load: function () {
                                    // set up the updating of the chart each second
                                    var series = this.series[0];
                                    setInterval(function () {

                                        $.ajax({
                                            type: 'post',
                                            url: path + '/mcontroller/getHourPoint?SENSOR_ID=' + sensor_id,
                                            cache: false,
                                            dataType: 'json',
                                            success: function (data) {
                                                if(data.length > 0) {
                                                    var y = (data[0].VAL)/1,
                                                        x = (data[0].TIM)/1;
                                                    series.addPoint([x, y], true, true);
                                                }
                                            }
                                        });
                                        // 生成新的数据点
                                        // var x = (new Date()).getTime(), // current time
                                        //     y = Math.random();
                                        // series.addPoint([x, y], true, true);
                                    }, sensor_second);
                                }
                            }
                        },
                        credits: {
                            enabled: false
                        },
                        title: {
                            text: type_name + '时均值曲线',
                            //floating: true
                            //verticalAlign: 'top'
                        },
                        subtitle: {
                            text: '编号:' + sensor_code + ", 位置:" +sensor_pstn
                        },
                        xAxis: {
                            type: 'datetime',
                            tickPixelInterval: 100
                        },
                        yAxis: {
                            allowDecimals:false,
                            title: {
                                text: '单位:' + sensor_unit,
                                align: 'high',
                                rotation: 0,
                                offset: -30,
                                y: -10
                            },
                            plotLines: plotLines
                        },
                        tooltip: {
                            formatter: function () {
                                return '<b>' + this.series.name + '</b><br/>' +
                                    Highcharts.dateFormat('%Y-%m-%d %H', this.x) + '<br/>' +
                                    Highcharts.numberFormat(this.y, sensor_point) + " " + sensor_unit;
                            }
                        },
                        legend: {
                            enabled: false
                        },
                        plotOptions: {
                            series: {
                                marker: {
                                    enabled: false,
                                    states: {
                                        hover: {
                                            enabled: true,
                                            radius: 3
                                        }
                                    }
                                }
                            }
                        },
                        exporting: {
                            enabled: false
                        },
                        series: [{
                            name: sensor_name + "(" +sensor_code +")",
                            color: 'green',
                            data: (function () {
                                // generate an array of random data
                                var data = [],
                                    time = (new Date()).getTime(),
                                    i,len;
                                len = list.length;
                                if (list.length > 0) {
                                    for (i = len*(-1); i < 0; i++) {
                                        var val = (Highcharts.numberFormat(list[i+len].VAL, sensor_point))/1;
                                        data.push({
                                            x: (list[i+len].TIM)/1,
                                            y: val
                                            //x: time + i * sensor_second,
                                            //y: Math.random()
                                        });
                                    }
                                } else {
                                    for (i = -23; i <= 0; i++) {
                                        data.push({
                                            x: time + i * sensor_second,
                                            y: null
                                        });
                                    }
                                }
                                document.getElementById("loading_hline").style.display = "none";
                                return data;
                            })()
                        }]
                    });
                    // 打印
                    $('#printHourChart').click(function () {
                        var chart = $('#hour-line').highcharts();
                        chart.print();
                    });

                    // 导出图片
                    $('#exportHourChart').click(function () {
                        var chart = $('#hour-line').highcharts();
                        chart.exportChart();
                    });
                });

            });
        }
    });
}

/**
 * 将毫秒转换为固定格式时间日期
 * @param time 毫秒数
 * @param format 格式
 * @returns {string|*|void|XML}
 */
var format = function(time, format){
    var t = new Date(time);
    var tf = function(i){return (i < 10 ? '0' : '') + i};
    return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
        switch(a){
            case 'yyyy':
                return tf(t.getFullYear());
                break;
            case 'MM':
                return tf(t.getMonth() + 1);
                break;
            case 'mm':
                return tf(t.getMinutes());
                break;
            case 'dd':
                return tf(t.getDate());
                break;
            case 'HH':
                return tf(t.getHours());
                break;
            case 'ss':
                return tf(t.getSeconds());
                break;
        }
    })
}
// alert(format(new Date().getTime(), 'yyyy-MM-dd HH:mm:ss'))

/**
 * 过去24小时状柱图
 * @param sensor_code
 */
function hourColumn(sensor_code) {
    $.ajax({
        type: 'post',
        url: path + '/mcontroller/hourLines?SENSOR_CODE=' + sensor_code,
        cache: false,
        dataType: 'json',
        success: function (data) {
            // 传感器id
            var sensor_id = data[0].SENSOR_ID;
            // 传感器类型名称
            var type_name = data[0].TYPE_NAME;
            // 传感器编号
            var sensor_code = data[0].SENSOR_CODE;
            // 传感器名称
            var sensor_name = data[0].SENSOR_NAME;
            // 传感器单位
            var sensor_unit = data[0].PART_UNIT;
            // 位置
            var sensor_pstn = data[0].SENSOR_PSTN;
            // 存储时间间隔(秒)
            var sensor_second = 60 * 60 * 1000;
            // 小数点位数
            var sensor_point = data[0].PART_POINT;
            // 数据
            var list = data[0].list;
            // 预警线
            // var plotLines = setWarnValue(data[0]);

            var categories = [],datas = [], i;
            for(i=0; i<list.length; i++) {
                var val = parseFloat(list[i].VAL.toFixed(sensor_point));
                var tim = (list[i].TIM)/1;
                var newTime = format(tim, "HH");
                categories.push(newTime);
                datas.push(val);
            }

            $(function () {
                $('#hour-line').highcharts({
                    chart: {
                        type: 'column'
                    },
                    credits: {
                        enabled: false
                    },
                    title: {
                        text: type_name + '时均值柱状图',
                    },
                    subtitle: {
                        text: '编号:' + sensor_code + ", 位置:" +sensor_pstn
                    },
                    xAxis: {
                        categories: categories
                    },
                    yAxis: {
                        min: 0,
                        title: {
                            text: '单位:' + sensor_unit,
                            align: 'high',
                            rotation: 0,
                            offset: -30,
                            y: -10
                        },
                    },
                    tooltip: {
                        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                        '<td style="padding:0"><b>{point.y:.0f}' + sensor_unit + '</b></td></tr>',
                        footerFormat: '</table>',
                        shared: true,
                        useHTML: true
                    },
                    plotOptions: {
                        column: {
                            pointPadding: 0.2,
                            borderWidth: 0
                        }
                    },
                    exporting: {
                        enabled: false
                    },
                    series:
                        [{
                        name: '时均值柱状图',
                        color: 'green',
                        data: (function () {
                            document.getElementById("loading_hline").style.display = "none";
                            return datas;
                        })()
                    }]
                });

                // 打印
                $('#printHourChart').click(function () {
                    var chart = $('#hour-line').highcharts();
                    chart.print();
                });

                // 导出图片
                $('#exportHourChart').click(function () {
                    var chart = $('#hour-line').highcharts();
                    chart.exportChart();
                });
            });
        }
    });
}

/**
 * 报警次数柱状图
  */
function warnColumn() {
    $.ajax({
        type: 'post',
        url: path + '/mcontroller/warnColumn',
        cache: false,
        dataType: 'json',
        success: function (data) {
            var datas = [], levelDatas = [], categories = [], i,j;
            for(i=0; i<data.length; i++) {
                for(j=0; j<data[i].length; j++) {
                    levelDatas.push((data[i][j].VAL)/1);
                    if(i == 0)
                        categories.push(data[i][j].TIM);
                }
                var color, name;
                if(i == 0) {
                    name = "一级报警";
                    color = "green";
                }else if(i == 1) {
                    name = "二级报警";
                    color = "orange";
                }else if(i == 2) {
                    name = "三级报警";
                    color = "red";
                }


                datas.push({
                    name: name,
                    color: color,
                    data: levelDatas
                });
                levelDatas = new Array();
            }

            document.getElementById("loading_column").style.display = "none";

            $(function () {
                $('#warn-column').highcharts({
                    chart: {
                        type: 'column'
                    },
                    credits: {
                        enabled: false
                    },
                    title: {
                        text: '七日内报警次数统计'
                    },
                    subtitle: {
                        text: ''
                    },
                    xAxis: {
                        categories: categories
                    },
                    yAxis: {
                        min: 0,
                        title: {
                            text: '报警次数',
                            align: 'high',
                            rotation: 0,
                            offset: -30,
                            y: -10
                        },
                    },
                    tooltip: {
                        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                        '<td style="padding:0"><b>{point.y:.0f} 次</b></td></tr>',
                        //'<td style="padding:0"><b>{point.y:.1f} 次</b></td></tr>',
                        footerFormat: '</table>',
                        shared: true,
                        useHTML: true
                    },
                    plotOptions: {
                        column: {
                            pointPadding: 0.2,
                            borderWidth: 0
                        }
                    },
                    exporting: {
                        enabled: false
                    },
                    series: datas
                    //     [{
                    //     // name: '报警统计',
                    //     // color: '#d84315',
                    //     data: (function () {
                    //
                    //         return datas;
                    //     })()
                    // }]
                });

                // 打印
                $('#printWarnColumn').click(function () {
                    var chart = $('#warn-column').highcharts();
                    chart.print();
                });

                // 导出图片
                $('#exportWarnColumn').click(function () {
                    var chart = $('#warn-column').highcharts();
                    chart.exportChart();
                });
            });
        }
    });
}


/*
 设置预警线,一级预警黄色,二级预警橙色,三级预警红色
 */
function setWarnValue(obj) {
    var plotLines = [];
    if(obj.WARN_LV1 != null) {
        plotLines.push({
            value: obj.WARN_LV1,
            width: 2,
            color: 'yellow'
        });
    }

    if(obj.WARN_LV11 != null) {
        plotLines.push({
            value: obj.WARN_LV11,
            width: 2,
            color: 'yellow'
        });
    }

    if(obj.WARN_LV2 != null) {
        plotLines.push({
            value: obj.WARN_LV2,
            width: 2,
            color: 'orange'
        });
    }

    if(obj.WARN_LV22 != null) {
        plotLines.push({
            value: obj.WARN_LV22,
            width: 2,
            color: 'orange'
        });
    }

    if(obj.WARN_LV3 != null) {
        plotLines.push({
            value: obj.WARN_LV3,
            width: 2,
            color: 'red'
        });
    }

    if(obj.WARN_LV3 != null) {
        plotLines.push({
            value: obj.WARN_LV3,
            width: 2,
            color: 'red'
        });
    }
    return plotLines;
}
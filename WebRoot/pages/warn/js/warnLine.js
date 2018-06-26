/*
    报警曲线
    AJAX获取后台数据
 */



$.ajax({
	
    type:'post',
    url: path+'/queryCount/queryListLineWarn?s_sensor=' + s_sensor + '&warning_time=' + warning_time,  
    	
    cache:false,
    dataType:'json',
    success:function(data){
    
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
        // 预警线
        var plotLines = setWarnValue(data[0]);

        $(function () {
            $(document).ready(function() {
                Highcharts.setOptions({
                    global: {
                        useUTC: false
                    }
                });

                var chart;
                $('#warnLine').highcharts({
                    chart: {
                        //type: 'line',
                        animation: Highcharts.svg // don't animate in old IE
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
                        tickPixelInterval: 70,
                        labels:{ 
                            step:2
                        } 
                    },
                    
                    yAxis: {
                        title: {
                            text: '单位:' + sensor_unit,
                            align: 'high',
                            rotation: 0,
                            offset: -20,
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
                                i,j;
                           
                            if(list.length > 0) {
                                var len = list.length;
                              
                               
                                for (i = 0; i < len; i++) {
                                
                                    data.push({
                                        x: list[i].WARNING_TIME,
                                        y: list[i].WARNING_VALUE
                                    });
                                }
                            } 
                        
                            return data;
                        })()
                    }]
                });
            });

        });
    }
});

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
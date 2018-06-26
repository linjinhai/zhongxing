var path = null;
$(function() {
	path = $("#path").val();
	$("#section_img_0").css("display", "");//默认显示第一张截面图
	$("#section_table_0").css("display", "");//默认显示第一张TABLE截面传感器集合
	if($("#sensor_length").val()==0){
		var loadhtml='<b>暂无传感器信息</b>';
		$("#loading").html(loadhtml);
		$("#loading_ls1").html(loadhtml);
		$("#loading_ls2").html(loadhtml);
		$("#loading_ls3").html(loadhtml);
		$("#loading_pjz").html(loadhtml);
		alert("                       暂无传感器信息!");
	}
	/*
	dwr.engine.setActiveReverseAjax(true);
	dwr.engine._errorHandler = function(message, ex) {
		dwr.engine._debug("Error: " + ex.name + ", " + ex.message, true);
	};
	*/
	//初始化 正常异常
	showTime();
	Highcharts.setOptions({
		global : {
			useUTC : false
		}
	});
	//初始化报警信息今天
	getWarningData(1);
	//初始化报警信息昨天
	getWarningData(2);

});
var chartsTime = null;
var chart = null;
var chartLs1 = null;//过去1小时曲线
var chartLs6 = null;//过去5小时曲线
var chartLs12 = null;//过去10小时曲线
var chartPj = null;//平均值曲线
var chartLs = null;//查询历史曲线
window['chart1'] = null;
//切换截面图
function changeSection(t) {
	for (var i = 0; i < 200; i++) {
		$("#section_img_" + i).css("display", "none");
		$("#section_table_" + i).css("display", "none");
	}
	$("#section_img_" + t).css("display", "");
	$("#section_table_" + t).css("display", "");
}
//初始化曲线图
function dw() {
	var pointsizearray = getSjd($("#gu").val().split("^")[0]);
	var plotLines = getYjz($("#gu").val().split("^")[0]);
	chart = new Highcharts.Chart({
		chart : {
			renderTo : 'hcharts',
			marginRight : 10,
			animation : Highcharts.svg
		// don't animate in old IE
		},
		//chart end 
		title : {
			text : $("#type_name").val() + '实时曲线'
		//x: -20 //center
		},
		credits : {
			enabled : false
		},
		subtitle : {
			text : '编号：' + $("#gu").val().split("^")[1] + ',位置：' + $("#gu").val().split("^")[3],
			x : -20
		},
		xAxis : {
			type : "datetime",
			tickPixelInterval : 70,
			dateTimeLabelFormats : {
				hour : '%m/%e %H',
			//day : '%m/%e',
			//month : '%Y/%m',
			//year : '%Y'
			}
		},
		plotOptions : {
			series : {
				marker : {
					enabled : false,
					states : {
						hover : {
							enabled : true,
							radius : 3
						}
					}
				}
			}
		},
		yAxis : {
			title : {
				text : '单位：' + $("#gu").val().split("^")[2],
				align : 'high',
				rotation : 0,
				offset : -30,
				y : -10
			},
			//tickPixelInterval : 15,
			/*
			plotLines: [{
			    value: 85,
			    width: 1,
			   color : 'red'
			}]*/
			plotLines : plotLines,
			labels : {
				formatter : function() {
					return this.value.toFixed($("#gu").val().split("^")[4]);//这里是两位小数，你要几位小数就改成几
				},
				style : {
					color : 'black'
				}
			}

		},
		tooltip : {
			formatter : function() {
				return '<b>' + this.series.name + '<br/>' + '时间：' + Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' + '实时数据：' + Highcharts.numberFormat(this.y, $("#gu").val().split("^")[4]) + ' ' + $("#gu").val().split("^")[2] + '</b>';
			}
		},
		legend : {
			enabled : false
		},
		exporting : {
			enabled : false
		},
		series : [ {
			//color : "blue",
			name : $("#gu").val().split("^")[1],
			data : pointsizearray
		} ]
	});
	$("#loading").css("display", "none");
	LsSjd($("#gu").val().split("^")[0], 1);
	$("#loading_ls1").css("display", "none");//切换过去1小时曲线
	LsSjd($("#gu").val().split("^")[0], 6);
	$("#loading_ls2").css("display", "none");//切换过去6小时曲线
	LsSjd($("#gu").val().split("^")[0], 12);
	$("#loading_ls3").css("display", "none");//切换过去12小时曲线
	LsSjdPj($("#gu").val().split("^")[0]);
	$("#loading_pjz").css("display", "none");//切换平均值曲线
}
//根据传感器id重置所有曲线图
function refDraw(c) {
	$("#gu").val(c);
	dw();

}

for (var i = 0; i < 3; i++) {
	window['a' + i] = i + 'i';
}
//打开独立传感器曲线图
function thn(c) {
	var pointsizearray = getSjd(c.split("^")[0]);
	var plotLines = getYjz(c.split("^")[0]);
	window['charts' + c.split("^")[0]] = new Highcharts.Chart({
		chart : {
			renderTo : 'div_chart_' + c.split("^")[0],
			marginRight : 10,
			animation : Highcharts.svg
		// don't animate in old IE
		},
		//chart end 
		title : {
			text : $("#type_name").val() + '实时曲线'
		//x: -20 //center
		},
		credits : {
			enabled : false
		},
		subtitle : {
			text : '编号：' + c.split("^")[1] + ',位置：' + c.split("^")[3],
			x : -20
		},
		xAxis : {
			type : "datetime",
			tickPixelInterval : 100,
			dateTimeLabelFormats : {
				hour : '%m/%e %H',
			//day : '%m/%e',
			//month : '%Y/%m',
			//year : '%Y'
			}
		},
		plotOptions : {
			series : {
				marker : {
					enabled : false,
					states : {
						hover : {
							enabled : true,
							radius : 3
						}
					}
				}
			}
		},
		yAxis : {
			title : {
				text : '单位：' + c.split("^")[2],
				align : 'high',
				rotation : 0,
				offset : -30,
				y : -10
			},
			//tickPixelInterval : 15,
			/*
			plotLines: [{
			    value: 85,
			    width: 1,
			   color : 'red'
			}]*/
			plotLines : plotLines,
			labels : {
				formatter : function() {
					return this.value.toFixed(c.split("^")[4]);//这里是两位小数，你要几位小数就改成几
				},
				style : {
					color : 'black'
				}
			}

		},
		tooltip : {
			formatter : function() {
				return '<b>' + this.series.name + '<br/>' + '时间：' + Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' + '实时数据：' + Highcharts.numberFormat(this.y, c.split("^")[4]) + ' ' + c.split("^")[2] + '</b>';
			}
		},
		legend : {
			enabled : false
		},
		exporting : {
			enabled : false
		},
		series : [ {
			//color : "blue",
			name : c.split("^")[1],
			data : pointsizearray
		} ]
	});
}
//打开/关闭 独立曲线图 
function jui(c) {
	if ($("#div_chart_" + c.split("^")[0]).parent().parent().css('display') == 'none') {
		$("#div_chart_" + c.split("^")[0]).parent().parent().fadeIn(2000);
		thn(c);
	} else {
		$("#div_chart_" + c.split("^")[0]).parent().parent().fadeOut(1200);
	}

	if ($('#mn' + c.split("^")[0]).text() == '展开') {
		$('#mn' + c.split("^")[0]).text('关闭');
	} else {
		$('#mn' + c.split("^")[0]).text('展开');
	}
}

// 获取初始50个数据点
function getSjd(sensor_id) {
	var pointsizearray = new Array();
	$.ajax({
		type : "POST",
		async : false,
		url : path + "/monitoringController/getTop50?sensor_id=" + sensor_id,
		dataType : "json",
		success : function(data) {

			if (data.length == 0) {
				for (var i = -100; i <= 0; i++) {
					pointsizearray.push([ (new Date()).getTime() + (i * Number($("#gu").val().split("^")[5]) * 1000), null ]);
				}
			} else if (data.length <= 100 && data.length > 0) {
				for (var i = 0; i < data.length; i++) {
					pointsizearray.push([ (new Date((data[i].S).replace(/-/g, "/"))).getTime(), parseFloat(data[i].A) ]);
				}
				for (var i = 0; i <= 100 - data.length; i++) {
					pointsizearray.push([ (new Date((data[Number(data.length - 1)].S).replace(/-/g, "/"))).getTime(), parseFloat(data[Number(data.length - 1)].A) ]);
				}
			} else if (data.length > 100) {
				for (var i = data.length - 100; i < data.length; i++) {
					pointsizearray.push([ (new Date((data[i].S).replace(/-/g, "/"))).getTime(), parseFloat(data[i].A) ]);
				}
			}

		}
	});
	return pointsizearray;
}
// 获取预警值
function getYjz(sensor_id) {
	var plotLines = [];
	$.ajax({
		type : "POST",
		async : false,
		url : path + "/monitoringController/getWaringVal?sensor_id=" + sensor_id,
		dataType : "json",
		success : function(data) {

			if (data[0].WARN_LV1 != null && data[0].WARN_LV1 != '') {
				var resultSeriesData1 = {
					color : 'yellow',
					width : 3,
					value : data[0].WARN_LV1,
					label : {
						// text : '1级预警',
						align : 'right'
					}
				}
				plotLines.push(resultSeriesData1);
				$("#s1").attr('value', data[0].WARN_LV1);
			}
			if (data[0].WARN_LV11 != null && data[0].WARN_LV11 != '') {
				var resultSeriesData1_1 = {
					color : 'yellow',
					width : 3,
					value : data[0].WARN_LV11,
					label : {
						// text : '1级预警',
						align : 'right'
					}
				}
				plotLines.push(resultSeriesData1_1);
				$("#x1").attr('value', data[0].WARN_LV11);
			}
			if (data[0].WARN_LV2 != null && data[0].WARN_LV2 != '') {
				var resultSeriesData2 = {
					color : '#ffa909',
					width : 3,
					value : data[0].WARN_LV2,
					label : {
						// text : '2级预警',
						align : 'right'
					}
				}
				plotLines.push(resultSeriesData2);
				$("#s2").attr('value', data[0].WARN_LV2);
			}
			if (data[0].WARN_LV22 != null && data[0].WARN_LV22 != '') {
				var resultSeriesData2_2 = {
					color : '#ffa909',
					width : 3,
					value : data[0].WARN_LV22,
					label : {
						// text : '2级预警',
						align : 'right'
					}
				}
				plotLines.push(resultSeriesData2_2);
				$("#x2").attr('value', data[0].WARN_LV22);
			}
			if (data[0].WARN_LV3 != null && data[0].WARN_LV3 != '') {
				var resultSeriesData3 = {
					color : 'red',
					width : 3,
					value : data[0].WARN_LV3,
					label : {
						// text : '3级预警',
						align : 'right'
					}
				}
				plotLines.push(resultSeriesData3);
				$("#s3").attr('value', data[0].WARN_LV3);
			}
			if (data[0].WARN_LV33 != null && data[0].WARN_LV33 != '') {
				var resultSeriesData3_3 = {
					color : 'red',
					width : 3,
					value : data[0].WARN_LV33,
					label : {
						// text : '3级预警',
						align : 'right'
					}
				}
				plotLines.push(resultSeriesData3_3);
				$("#x3").attr('value', data[0].WARN_LV33);
			}

		}

	});

	return plotLines;
}
// 获取历史 1，5,10小时数据点
function getSjdLs(sensor_id, last_flag) {
	var pointsizearray = new Array();
	$.ajax({
		type : "POST",
		async : false,
		url : path + "/monitoringController/getDataForLast?last_flag=" + last_flag + "&sensor_id=" + sensor_id,
		dataType : "json",
		success : function(data) {
			if (data.length == 0) {
				for (var i = -100; i <= 0; i++) {
					pointsizearray.push([ (new Date()).getTime() + (i * Number($("#gu").val().split("^")[5]) * 1000), null ]);
				}
			} else {
				for (var i = 0; i < data.length; i++) {
					pointsizearray.push([ (new Date((data[i].S).replace(/-/g, "/"))).getTime(), parseFloat(data[i].A) ]);
				}
			}
		}
	});
	return pointsizearray;
}
//根据id获取过去传感器数据集合   
function LsSjd(sensor_id, last_flag) {
	var pointsizearray = getSjdLs(sensor_id, last_flag);
	var plotLines = getYjz(sensor_id);
	var kj, kl, ko = null;
	if (last_flag == '1') {
		kj = 'chartLs1';
		kl = 'ch1';
		ko = '过去一小时';
	} else if (last_flag == '6') {
		kj = 'chartLs6';
		kl = 'ch2';
		ko = '过去六小时';
	} else if (last_flag == '12') {
		kj = 'chartLs12';
		kl = 'ch3';
		ko = '过去十二小时';
	}

	window['' + kj] = new Highcharts.Chart({
		chart : {
			renderTo : kl,
			marginRight : 10,
			animation : Highcharts.svg
		// don't animate in old IE
		},
		// chart end
		title : {
			text : ko + "每分钟平均值"
		// x: -20 //center
		},
		credits : {
			enabled : false
		},
		subtitle : {
			text : '编号：' + $("#gu").val().split("^")[1] + ',位置：' + $("#gu").val().split("^")[3] + '<br/>.',
			x : 20
		},
		xAxis : {
			type : "datetime",
			tickPixelInterval : 70,
			dateTimeLabelFormats : {
				hour : '%m/%e %H',
			// day : '%m/%e',
			// month : '%Y/%m',
			// year : '%Y'
			}
		},
		plotOptions : {
			series : {
				marker : {
					enabled : false,
					states : {
						hover : {
							enabled : true,
							radius : 3
						}
					}
				}
			}
		},
		yAxis : {
			title : {
				text : '单位：' + $("#gu").val().split("^")[2],
				align : 'high',
				rotation : 0,
				offset : -30,
				y : -10
			},
			//tickPixelInterval : 15,
			/*
			 * plotLines: [{ value: 85, width: 1, color : 'red' }]
			 */
			plotLines : plotLines,
			labels : {
				formatter : function() {
					return this.value.toFixed($("#gu").val().split("^")[4]);//这里是两位小数，你要几位小数就改成几
				},
				style : {
					color : 'black'
				}
			}

		},
		tooltip : {
			formatter : function() {
				return '<b>' + this.series.name + '<br/>' + '时间：' + Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' + '实时数据：' + Highcharts.numberFormat(this.y, $("#gu").val().split("^")[4]) + ' ' + $("#gu").val().split("^")[2] + '</b>';
			}
		},
		legend : {
			enabled : false
		},
		exporting : {
			enabled : false
		},
		series : [ {
			//color : "blue",
			name : $("#gu").val().split("^")[1],
			data : pointsizearray
		} ]
	});

}
//获取实时平均值数据
function getSjdSsPj(sensor_id) {
	var pointsizearray = new Array();
	$.ajax({
		type : "POST",
		async : false,
		url : path + "/monitoringController/getTodaySs?&sensor_id=" + sensor_id,
		dataType : "json",
		success : function(data) {
			var d = new Date();
			if (data.length == 0) {
				for (var i = -24; i < 0; i++) {
					pointsizearray.push([ d.getTime() + (i * 1000 * 60 * 60), null ]);
				}
			} else {
				for (var i = 0; i < data.length; i++) {
					pointsizearray.push([ (new Date((data[i].S + ":00:00").replace(/-/g, "/"))).getTime(), parseFloat(data[i].A) ]);
				}
			}

		}
	});
	return pointsizearray;
}
//更新传感器平均值曲线图
function LsSjdPj(sensor_id) {
	var pointsizearray = getSjdSsPj(sensor_id);
	var plotLines = getYjz(sensor_id);

	chartPj = new Highcharts.Chart({
		chart : {
			renderTo : 'sspjz',
			marginRight : 10,
			animation : Highcharts.svg
		// don't animate in old IE
		},
		// chart end
		title : {
			text : $("#type_name").val() + '时均值曲线'
		// x: -20 //center
		},
		credits : {
			enabled : false
		},
		subtitle : {
			text : '编号：' + $("#gu").val().split("^")[1] + ',位置：' + $("#gu").val().split("^")[3],
		// x: -20
		},
		xAxis : {
			type : "datetime",
			tickPixelInterval : 70,
			dateTimeLabelFormats : {
				hour : '%m/%e %H',
			// day : '%m/%e',
			// month : '%Y/%m',
			// year : '%Y'
			}
		},
		plotOptions : {
			series : {
				marker : {
					enabled : false,
					states : {
						hover : {
							enabled : true,
							radius : 3
						}
					}
				}
			}
		},
		yAxis : {
			title : {
				text : '单位：' + $("#gu").val().split("^")[2],
				align : 'high',
				rotation : 0,
				offset : -30,
				y : -10
			},
			//tickPixelInterval : 15,
			/*
			 * plotLines: [{ value: 85, width: 1, color : 'red' }]
			 */
			plotLines : plotLines,
			labels : {
				formatter : function() {
					return this.value.toFixed($("#gu").val().split("^")[4]);//这里是两位小数，你要几位小数就改成几
				},
				style : {
					color : 'black'
				}
			}

		},
		tooltip : {
			formatter : function() {
				return '<b>' + this.series.name + '<br/>' + '时间：' + Highcharts.dateFormat('%Y-%m-%d %H', this.x) + '<br/>' + '实时数据：' + Highcharts.numberFormat(this.y, $("#gu").val().split("^")[4]) + ' ' + $("#gu").val().split("^")[2] + '</b>';
			}
		},
		legend : {
			enabled : false
		},
		exporting : {
			enabled : false
		},
		series : [ {
			color : "green",
			name : $("#gu").val().split("^")[1],
			data : pointsizearray
		} ]
	});

}
//打印  实时图 （1） ，时均图（2）
function dyHighcharts(id) {
	if (id == '1') {
		chart.print();
	} else {
		chartPj.print();
	}
}
//导出  实时图 （1） ，时均图（2）
function dcHighcharts(id) {
	if (id == '1') {
		chart.exportChart();
	} else {
		chartPj.exportChart();
	}
}
//报警信息查询
function getWarningData(h) {
	var opk = $("#jt_page" + h).val();
	$.ajax({
		type : "POST",
		async : false,
		url : path + "/monitoringController/getWarningData?flag=" + h + "&fy_page=" + opk + "&sensorType=" + $("#type_id").val(),
		dataType : "json",
		success : function(data) {
			var str = "";
			if (data.length == 0) {
				$("#jzgd" + h).remove();
				str += '<tr><td colspan="4" style="text-align:center;" style="padding-left: 41%;background-color:white;border-bottom: 0px;"><B>无信息！<B></td></tr>';
				$("#bjsj" + h).append(str);
			} else {
				$("#jzgd" + h).remove();
				for (var i = 0; i < data.length; i++) {
					str += "<tr>";
					str += '<td style="text-align: left;">' + data[i].SENSOR_CODE + '</td>';
					str += '<td style="text-align: left;">' + data[i].WARNING_LEVEL + '</td>';
					str += '<td style="text-align: left;">' + Number(data[i].WARNING_VALUE).toFixed(($("#guop" + data[i].SENSOR_ID).val()).split("^")[4]) + data[i].PART_UNIT + '</td>';
					str += '<td style="text-align: left;">' + data[i].WARNING_TIME + '</td>';
					str += "</tr>";
				}
				str += '<tr id="jzgd' + h + '"><td colspan="4" style="text-align:center;"><a href="javascript:getWarningData(' + h + ');"><span class="label bg-success">加载更多</span></a></td></tr>';
				$("#bjsj" + h).append(str);
				$("#jt_page" + h).val((Number($("#jt_page" + h).val()) + 1));
			}
		}
	});
}
//PUSH推送方法
function callBack(sensor_id, datav) {
	if (datav == '') {
		$("#st" + sensor_id).html('<font style="color: red;">异常</font>');
	} else if (datav != '') {

		if ($("#guop" + sensor_id).length > 0) {
			$("#st" + sensor_id).html('<font style="color: green;">正常</font>');
			$("#res" + sensor_id).text(parseFloat(datav).toFixed(($("#guop" + sensor_id).val()).split("^")[4]));
			$("#ren" + sensor_id).text(parseFloat(datav).toFixed(($("#guop" + sensor_id).val()).split("^")[4]));
		}

		var gu = $("#gu").val().split("^");
		if (sensor_id == gu[0]) {
			chart.series[0].addPoint([ (new Date()).getTime(), parseFloat(datav) ], true, true);
		}
		if (window['charts' + sensor_id]) {
			window['charts' + sensor_id].series[0].addPoint([ (new Date()).getTime(), parseFloat(datav) ], true, true);
		}
	}
}
//定时查询

setInterval("showTime()", (Number($("#dxcxjg").val())) * 1000);
function showTime() {
	var gu = $("#gu").val().split("^");
	//console.log(chart.series[0].processedXData[chart.series[0].processedXData.length-1]);
	//console.log(chart.series[0].processedXData.length-1);
	$.ajax({
		type : "POST",
		async : false,
		url : path + "/monitoringController/dscx?tid=" + $("#type_id").val(),
		dataType : "json",
		success : function(data) {
			for (var i = 0; i < data.length; i++) {
				$("#res" + data[i].SENSOR_ID).text(parseFloat(data[i].UPDATE_VALUE).toFixed(($("#guop" + data[i].SENSOR_ID).val()).split("^")[4]));
				$("#ren" + data[i].SENSOR_ID).text(parseFloat(data[i].UPDATE_VALUE).toFixed(($("#guop" + data[i].SENSOR_ID).val()).split("^")[4]));
				if (data[i].UPDATE_STATUS == '1') {
					$("#st" + data[i].SENSOR_ID).html('<font style="color: red;">异常</font>');
				} else if (data[i].UPDATE_STATUS == '0') {
					$("#st" + data[i].SENSOR_ID).html('<font style="color: green;">正常</font>');
				}

				if (chart != null) {
					if (data[i].SENSOR_ID == gu[0]) {
						if (chartsTime != data[i].T) {
							chart.series[0].addPoint([ data[i].T, parseFloat(data[i].UPDATE_VALUE) ], true, true);
							chartsTime = data[i].T;
						}
					}
				}

				if (window['charts' + data[i].SENSOR_ID]) {
					if (window['chartsTime' + data[i].SENSOR_ID] != data[i].T) {
						window['charts' + data[i].SENSOR_ID].series[0].addPoint([ data[i].T, parseFloat(data[i].UPDATE_VALUE) ], true, true);
						window['chartsTime' + data[i].SENSOR_ID] = data[i].T;
					}

				}

			}

		}
	});
}
//查询历史曲线
function cxlsqx() {
	if ($("#w7").val() == '' || $("#w8").val() == '' || ($("#w8").val().replace(/\-/g, '').replace(/\ /g, '').replace(/\:/g, '') - $("#w7").val().replace(/\-/g, '').replace(/\ /g, '').replace(/\:/g, '') < 0)) {
		alert("请选择日期，或时间区间有误！");
		return;
	}
	var objValue = $("#cxcgq").val();
	var pointsizearray = new Array();
	var plotLines = getYjz(objValue.split("^")[0]);

	$("#g7").css('display', '');
	$("#lscxChart1").css('display', '');
	$("#lscxChart2").css('display', 'none');

	$.ajax({
		type : "POST",
		async : false,
		url : path + "/monitoringController/cxlsqx?cxcgq=" + objValue.split("^")[0] + "&w7=" + $("#w7").val() + "&w8=" + $("#w8").val() + "&w9=" + $("#w9").val() + "&w10=" + $("#w10").val(),
		dataType : "json",
		success : function(data) {

			for (var i = 0; i < data.length; i++) {
				pointsizearray.push([ (new Date((data[i].T).replace(/-/g, "/"))).getTime(), parseFloat(data[i].V) ]);
			}

			chartLs = new Highcharts.Chart({
				chart : {
					renderTo : 'lscxChart1',
					marginRight : 10,
					animation : Highcharts.svg
				// don't animate in old IE
				},
				// chart end
				title : {
					text : "历史曲线查询"
				// x: -20 //center
				},
				credits : {
					enabled : false
				},
				subtitle : {
					text : '编号：' + objValue.split("^")[1] + ',位置：' + objValue.split("^")[3] + '<br/>.',
					x : 20
				},
				xAxis : {
					type : "datetime",
					tickPixelInterval : 70,
					dateTimeLabelFormats : {
						hour : '%m/%e %H',
					// day : '%m/%e',
					// month : '%Y/%m',
					// year : '%Y'
					}
				},
				plotOptions : {
					series : {
						marker : {
							enabled : false,
							states : {
								hover : {
									enabled : true,
									radius : 3
								}
							}
						}
					}
				},
				yAxis : {
					title : {
						text : '单位：' + objValue.split("^")[2],
						align : 'high',
						rotation : 0,
						offset : -30,
						y : -10
					},
					//tickPixelInterval : 15,
					/*
					 * plotLines: [{ value: 85, width: 1, color : 'red' }]
					 */
					plotLines : plotLines,
					labels : {
						formatter : function() {
							return this.value.toFixed(objValue.split("^")[4]);//这里是两位小数，你要几位小数就改成几
						},
						style : {
							color : 'black'
						}
					}

				},
				tooltip : {
					formatter : function() {
						return '<b>' + this.series.name + '<br/>' + '时间：' + Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' + '实时数据：' + Highcharts.numberFormat(this.y, objValue.split("^")[4]) + ' ' + objValue.split("^")[2] + '</b>';
					}
				},
				legend : {
					enabled : false
				},
				exporting : {
					enabled : false
				},
				series : [ {
					//color : "blue",
					name : objValue.split("^")[1],
					data : pointsizearray
				} ]
			});
		}
	});

}
//历史曲线 曲线图，列表 按钮切换
function xq(q, w) {
	$("#lscxChart" + q).css('display', '');
	$("#lscxChart" + w).css('display', 'none');
}
//查询历史曲线分页列表
function cxlsqxlb(k) {
	if ($("#w7").val() == '' || $("#w8").val() == '' || ($("#w8").val().replace(/\-/g, '').replace(/\ /g, '').replace(/\:/g, '') - $("#w7").val().replace(/\-/g, '').replace(/\ /g, '').replace(/\:/g, '') < 0)) {
		alert("请选择日期，或时间区间有误！");
		return;
	}
	$("#g7").css('display', '');
	$("#lscxChart1").css('display', 'none');
	$("#lscxChart2").css('display', '');
	if (k == '1') {
		$("#bjsjl").html('');
		$("#lsqxcx_page").attr('value', '1');
	}

	$.ajax({
		type : "POST",
		async : false,
		url : path + "/monitoringController/cxlsqx?lsqxcx_page=" + $("#lsqxcx_page").val() + "&cxcgq=" + $("#cxcgq").val().split("^")[0] + "&w7=" + $("#w7").val() + "&w8=" + $("#w8").val() + "&w9=" + $("#w9").val() + "&w10=" + $("#w10").val(),
		dataType : "json",
		success : function(data) {
			var str = "";
			if (data.length == 0) {
				$("#jzgdl").remove();
				str += '<tr><td colspan="4" style="text-align:center;" style="padding-left: 41%;background-color:white;border-bottom: 0px;"><B>无信息！<B></td></tr>';
				$("#bjsjl").append(str);
			} else {
				$("#jzgdl").remove();
				for (var i = 0; i < data.length; i++) {
					str += "<tr>";
					str += '<td style="text-align: left;">' + $("#cxcgq").val().split("^")[1] + '</td>';
					str += '<td style="text-align: left;">' + Number(data[i].V).toFixed($("#cxcgq").val().split("^")[4]) + $("#cxcgq").val().split("^")[2] + '</td>';
					str += '<td style="text-align: left;">' + data[i].T + '</td>';
					str += "</tr>";
				}
				str += '<tr id="jzgdl"><td colspan="4" style="text-align:center;"><a href="javascript:cxlsqxlb(2);"><span class="label bg-success">加载更多</span></a></td></tr>';
				$("#bjsjl").append(str);
				$("#lsqxcx_page").val((Number($("#lsqxcx_page").val()) + 1));
			}
		}
	});

}
//导出历史查询列表
function dcCxlb() {
	if ($("#w7").val() == '' || $("#w8").val() == '' || ($("#w8").val().replace(/\-/g, '').replace(/\ /g, '').replace(/\:/g, '') - $("#w7").val().replace(/\-/g, '').replace(/\ /g, '').replace(/\:/g, '') < 0)) {
		alert("请选择日期，或时间区间有误！");
		return;
	}
	document.dcForm.action = path + "/monitoringController/cxlsqxDc?cgqsec=" + $("#cxcgq").val().split("^")[4] + "&cgqdw=" + $("#cxcgq").val().split("^")[2] + "&cxcgqcode=" + $("#cxcgq").val().split("^")[1] + "&cxcgq=" + $("#cxcgq").val().split("^")[0] + "&w7=" + $("#w7").val() + "&w8=" + $("#w8").val() + "&w9=" + $("#w9").val() + "&w10=" + $("#w10").val();
	document.dcForm.submit();
}
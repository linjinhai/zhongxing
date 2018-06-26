var path = document.getElementById("systemPath").value + "/";
$(function() {
	//获取截面id
	var sid = $("#sid").val();
	$("#hidf").val(sid);
	//获取截面 放置工作区
	$.ajax({
		type : "POST",
		async : false,
		url : path + "/mioncontroller/getSectionPath?sid=" + sid,
		dataType : "json",
		success : function(data) {
			$("#dt").attr('src', path+'' + data[0].SECTION_PATH);
			$("#symc").text("所属截面："+data[0].SECTION_NAME);
		}

	});

	//根据截面id 获取该截面下所有传感器
	$.ajax({
		type : "POST",
		async : false,
		url : path + "/mioncontroller/getSectionAllSensor?sid=" + sid,
		dataType : "json",
		success : function(data) {
			var str = '';
			for (var i = 0; i < data.length; i++) {
				str += '<tr onmouseover="javascript:tpchange(1,' + data[i].SENSOR_ID + ');" onmouseout="javascript:tpchange(2,' + data[i].SENSOR_ID + ');">';
				str += '<td><img src="' + path + data[i].PART_IMG + '" width="25px" height="25px"/></td>';
				str += '<td>' + data[i].SENSOR_CODE + '</td>';
				var cl = "b2";
				var cp = "b2";
				//实时监测截图
				if (sid != '-1') {
					if (data[i].MON_P_LEFT == null) {
						cl = "b1";
					}
					if (data[i].MON_V_LEFT == null) {
						cp = "b1";
					}
					if (data[i].MON_P_LEFT != null) {
						var cv = '';
						cv += '<div style="position: absolute; top: ' + data[i].MON_P_TOP + '%; left: ' + data[i].MON_P_LEFT + '%;" class="dragMe" id="tp' + data[i].SENSOR_ID + '" title="' + data[i].SENSOR_CODE + '" onmouseup="javascript:savepost(3,' + data[i].SENSOR_ID + ',1);">';
						cv += '<img src="' + path + data[i].PART_IMG + '" style="width: 15px; height: 15px;" />';
						cv += '</div>';
						$("#wo1").append(cv);
						ref();
					}
					if (data[i].MON_V_LEFT != null) {
						var cv = '';
						cv += '<div  style="background-color:white;position:absolute;width:;height:18px;z-index:1;left: ' + data[i].MON_V_LEFT + '%;top: ' + data[i].MON_V_TOP + '%;" class="dragMe" id="va' + data[i].SENSOR_ID + '" onmouseup="javascript:savepost(3,' + data[i].SENSOR_ID + ',2);">';
						cv += '<span>' + data[i].SENSOR_CODE + '</span>';
						cv += '</div>';
						$("#wo1").append(cv);
						ref();
					}
				}
				//首页
				else if (sid == '-1') {
					if (data[i].MAIN_P_LEFT == null) {
						cl = "b1";
					}
					if (data[i].MAIN_V_LEFT == null) {
						cp = "b1";
					}
					if (data[i].MAIN_P_LEFT != null) {
						var cv = '';
						cv += '<div style="position: absolute; top: ' + data[i].MAIN_P_TOP + '%; left: ' + data[i].MAIN_P_LEFT + '%;" class="dragMe" id="tp' + data[i].SENSOR_ID + '" title="' + data[i].SENSOR_CODE + '" onmouseup="javascript:savepost(3,' + data[i].SENSOR_ID + ',1);">';
						cv += '<img src="' + path + data[i].PART_IMG + '" style="width: 15px; height: 15px;" />';
						cv += '</div>';
						$("#wo1").append(cv);
						ref();
					}
					if (data[i].MAIN_V_LEFT != null) {
						var cv = '';
						cv += '<div  style="background-color:white;position:absolute;width:;height:18px;z-index:1;left: ' + data[i].MAIN_V_LEFT + '%;top: ' + data[i].MAIN_V_TOP + '%;" class="dragMe" id="va' + data[i].SENSOR_ID + '" onmouseup="javascript:savepost(3,' + data[i].SENSOR_ID + ',2);">';
						cv += '<span>' + data[i].SENSOR_CODE + '</span>';
						cv += '</div>';
						$("#wo1").append(cv);
						ref();
					}
				}

				str += '<td><button class="' + cl + '" onclick="p_onclick(this);" hco="' + data[i].SENSOR_CODE + '" huo="' + data[i].SENSOR_ID + '" him="' + data[i].PART_IMG + '">布置</button></td>';
				str += '<td><button class="' + cp + '" onclick="v_onclick(this);" hco="' + data[i].SENSOR_CODE + '" huo="' + data[i].SENSOR_ID + '" him="' + data[i].PART_IMG + '">布置</button></td>';
				str += '</tr>';
			}
			$("#gy").after(str);
		}
	});

	$("#wo1").css("height", document.documentElement.clientHeight - 135);
	$("#wo2").css("height", document.documentElement.clientHeight - 135);

	ref();

});
function co() {
	$("#wo2").text($('#draggable2').position().top + "  " + $("#wo1").height() + "  <br/>" + $('#draggable2').position().left + "  " + $("#wo1").width());

}
function tpchange(i, sid) {
	if (i == 1) {
		$("#tp" + sid).find("img").css('width', '50');
		$("#tp" + sid).find("img").css('height', '50');
	} else {
		$("#tp" + sid).find("img").css('width', '15');
		$("#tp" + sid).find("img").css('height', '15');
	}
}
function ref() {
	$('.dragMe').each(function() {
		$(this).dragging({
			move : 'both',
			randomPosition : false
		});
	});
}
//传感器位置按钮
function p_onclick(obj) {
	var senid = $(obj).attr('huo');
	var senpath = $(obj).attr('him');
	var scode = $(obj).attr('hco');
	if ($(obj).attr('class') == 'b1') {
		$(obj).attr('class', 'b2');
		var str = '';
		str += '<div style="position: absolute; top: 0%; left: 0%;" class="dragMe" id="tp' + senid + '" title="' + scode + '" onmouseup="javascript:savepost(3,' + senid + ',1);">';
		str += '<img src="' + path + senpath + '" style="width: 15px; height: 15px;" />';
		str += '</div>';
		$("#wo1").append(str);
		ref();
		savepost(1, senid, 1);
	} else {
		$(obj).attr('class', 'b1');
		$("#tp" + senid).remove();
		savepost(2, senid, 1);
	}
}
//数值位置按钮
function v_onclick(obj) {
	var senid = $(obj).attr('huo');
	var senpath = $(obj).attr('him');
	var scode = $(obj).attr('hco');
	if ($(obj).attr('class') == 'b1') {
		$(obj).attr('class', 'b2');
		var str = '';
		str += '<div  style="background-color:white;position:absolute;width:;height:18px;z-index:1;left: 10%;top: 0%;" class="dragMe" id="va' + senid + '" onmouseup="javascript:savepost(3,' + senid + ',2);">';
		str += '<span>' + scode + '</span>';
		str += '</div>';
		$("#wo1").append(str);
		ref();
		savepost(1, senid, 2);
	} else {
		$(obj).attr('class', 'b1');
		$("#va" + senid).remove();
		savepost(2, senid, 2);
	}
}

//保存元素位置
//i:  1，初始化 2，删除 3.移动位置
//t:  1,传感器  2，数值
function savepost(i, sid, t) {
	var top = 0;
	var left = 0;
	var y = '';
	var hidf = $("#hidf").val();

	(t == 1) ? y = 'tp' : y = 'va';

	if (i == 3) {
		top = (Number($('#' + y + sid).position().top) / (Number($("#wo1").height()))) * 100;
		left = (Number($('#' + y + sid).position().left) / (Number($("#wo1").width()))) * 100;
	}

	$.ajax({
		type : "POST",
		async : false,
		url : path + "/mioncontroller/savepost?hidf=" + hidf + "&left=" + left + "&top=" + top + "&sid=" + sid + "&i=" + i + "&t=" + t,
		dataType : "json",
		success : function(data) {
			ybc();
		}

	});
}

function ybc() {
	$(".yj").fadeIn();
	$(".yj").fadeOut();
}


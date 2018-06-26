var path = document.getElementById("path").value;

$(function() {
	//获取一级目录
	$.ajax({
		type : "POST",
		async : false,
		dataType : "json",
		url : path + "/essence/getTop1?id=1",
		success : function(data) {
			var str = '<span><i class="icon-calendar"></i>全桥</span>';

			str += op2(1, data);
			str += op2(2, data);
			str += op2(3, data);

			$("#qq").html(str);
		}
	});
});
var el = null;
function op2(id, data) {
	var df;
	$.ajax({
		type : "POST",
		async : false,
		dataType : "json",
		url : path + "/essence/getTop1?id=2",
		success : function(d) {
			df = d;
		}
	});
	var str = '';
	var k = '';
	if (id == '1') {
		k = '上部结构';
	} else if (id == '2') {
		k = '下部结构';
	} else if (id == '3') {
		k = '桥面系';
	}
	str += '<ul><li><span><i class="icon-minus-sign"></i>' + k + '</span>';
	for (var i = 0; i < data.length; i++) {
		if (data[i].DATA_PID == id) {
			str += '<ul>';
			str += '<li><span><i class="icon-minus-sign"></i>' + data[i].DATA_NAME + '</span>';
			for (var j = 0; j < df.length; j++) {
				if (data[i].DATA_ID == df[j].STRUCT_PID) {
					str += '<ul>';
					str += '<li onclick="op3(\'' + df[j].STRUCT_NAME + '\',\'' + df[j].STRUCT_ID + '\',\'' + df[j].STRUCT_PID + '\');"><span>' + df[j].STRUCT_NAME + '</span></li>';
					str += '</ul>';
				}
			}
			str += '</li>';
			str += '</ul>';
		}
	}
	str += '</li>';
	str += '</ul>';
	return str;
}
function op3(n, id, pid) {
	var df;
	var cf;
	var af;
	var str = '';
	$("#gjxx").text("构件：" + n);
	el = document.createElement("a");
	document.body.appendChild(el);
	el.href = "#top";
	el.target = '_self';
	el.click();
	$.ajax({
		type : "POST",
		async : false,
		dataType : "json",
		url : path + "/essence/getTop1?id=3",
		success : function(d) {
			df = d;//评定项
		}
	});

	$.ajax({
		type : "POST",
		async : false,
		dataType : "json",
		url : path + "/essence/getTop1?id=4",
		success : function(d) {
			cf = d;//评定标准
		}
	});
	$.ajax({
		type : "POST",
		async : false,
		dataType : "json",
		url : path + "/essence/getTop1?id=5&in_id=" + $("#in_id").val() + "&sid=" + id,
		success : function(d) {
			af = d;//根据构件id 和  主键id 查出的 已录入评定标准和评定项
		}
	});
	for (var i = 0; i < df.length; i++) {
		if (df[i].STRUCT_PID == pid) {
			str += '<B>评分指标 ' + (i + 1) + ': ' + df[i].STRUCT_NAME + '</B><br/><br/>';
			str += '<select onchange="op4(this);" name="xzsl">';
			for (var j = 0; j < cf.length; j++) {
				if (df[i].STRUCT_ID == cf[j].STRUCT_ID) {
					var lp = '';
					for (var h = 0; h < af.length; h++) {
						if (af[h].PARAM_ID == cf[j].PARAM_ID) {
							lp = 'selected';
						}
					}
					str += '<option ' + lp + ' value="' + cf[j].PARAM_ID + '_' + cf[j].DESC_INDEX + '_' + cf[j].PARAM_TYPE + '" ta=' + cf[j].DESC_DL + '>';
					str += cf[j].DESC_DX;
					str += '</option>';
				}
			}
			str += '</select><br/><br/>';
			var lp = '0';
			for (var j = 0; j < cf.length; j++) {
				for (var h = 0; h < af.length; h++) {
					if (af[h].PARAM_ID == cf[j].PARAM_ID && df[i].STRUCT_ID == cf[j].STRUCT_ID) {
						str += '<span>' + af[h].DESC_DL + '</span>';
						lp = '1';
					}
				}
			}
			if (lp == '0') {
				for (var j = 0; j < cf.length; j++) {
					if (df[i].STRUCT_ID == cf[j].STRUCT_ID && cf[j].DESC_INDEX == '1') {
						str += '<span>' + cf[j].DESC_DL + '</span>';
					}
				}
			}
			str += '<br/><hr/>';
		}
	}

	if ($("#t").val() != "t") {

		str += '<button type="button" class="btn btn-info" onclick="javascript:op5(' + id + ');">保存</button>　　　';
		var pt = path + '/essence/queryrgxj';
		str += '<button type="button" class="btn btn-info" onclick="javascript:window.location.href=\'' + pt + '\';">返回</button>';
	}

	$("#pfnr").html(str);

}
function op4(obj) {
	var c = $(obj).find("option:selected").attr("ta");
	var a = $(obj).next().next().next();
	$(a).html(c);
}
function op5(sid) {
	$("#gj_id").val(sid);
	$.ajax({
		type : "POST",
		async : false,
		url : path + "/essence/addEssenceDetail",
		data : $('#form1').serialize(),
		success : function(data) {
			if (data == 'ok') {
				alert("保存成功!");
				el.click();
			} else {
				alert("保存失败!请联系管理员!");
			}
			return false;
		}
	});
}
$(function() {
	$('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Collapse this branch');
	$('.tree li.parent_li > span').on('click', function(e) {
		var children = $(this).parent('li.parent_li').find(' > ul > li');
		if (children.is(":visible")) {
			children.hide('fast');
			$(this).attr('title', '').find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');
		} else {
			children.show('fast');
			$(this).attr('title', '').find(' > i').addClass('icon-minus-sign').removeClass('icon-plus-sign');
		}
		e.stopPropagation();
	});

	var dr = $('.tree li.parent_li > span');
	var children = $(dr).parent('li.parent_li').find(' > ul > li');
	children.hide('fast');
	$(dr).attr('title', '').find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');

});
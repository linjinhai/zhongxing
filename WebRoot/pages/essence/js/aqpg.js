var path = document.getElementById("path").value;
//保存
function bc() {
	// if (confirm("确定保存？")) {
		$.ajax({
			type : "POST",
			async : false,
			url : path + "/essence/addAqpgDetail",
			data : $('#form1').serialize(),
			success : function(data) {
				alert('保存成功！');
			}
		});
	// }

}
//开始评估
function kspg() {
	if (confirm("确定评估？")) {
		$("#loading").show();
		$.ajax({
			type : "POST",
			async : true,
			dataType : 'json',
			url : path + "/essence/kspg",
			data : $('#form1').serialize(),
			success : function(json) {
				$("#loading").hide();
				alert('评估成功,请点击[保存]按钮来保存评估结果!');
				for ( var key in json[0]) {
					$("#" + key).val(json[0][key]);
				}

			}
		});
	}

}
//导出Excel
function dc() {
	if (confirm("确定导出？")) {
		document.form1.action = path + "/essence/dcAqpgExcel?id=" + $("#aid").val();
		document.form1.submit();
	}

}
$(function() {
	op0(1);
	op0(2);
	op0(3);
	op2();
	op3();
});

function op0(id) {
	$.ajax({
		type : "POST",
		async : false,
		dataType : "json",
		url : path + "/essence/getTop2?id=" + id,
		success : function(data) {
			var str = '';
			var st1 = '', st2 = '', st3 = 0;
			if (id == '1') {
				st1 = '上部结构(SPCI)';
				st2 = 'PCCI';
			} else if (id == '2') {
				st1 = '下部结构(SBCI)';
				st2 = 'BCCI';
			} else if (id == '3') {
				st1 = '桥面系(BDCI)';
				st2 = 'DCCI';
			}
			for (var i = 0; i < data.length; i++) {
				if (i == 0) {
					str += '<tr>';
					str += '<td rowspan="' + data.length + '">' + st1 + '</td>';
					str += '<td rowspan="' + data.length + '"><input type="text" id="TY_' + id + '" name="TY_' + id + '" value="" style="height: 103%" readonly="readonly" /></td>';
					str += '<td rowspan="' + data.length + '"><input type="text" id="TY_' + (id + 3) + '" name="TY_' + (id + 3) + '" value="" style="height: 103%" readonly="readonly" /></td>';
				} else if (i != 0 && i != (data.length - 1)) {
					str += '<tr>';
				} else if (i == (data.length - 1)) {
					str += '<tr id="ko' + id + '">';
				}
				str += '<td>' + st2 + (i + 1) + '</td>';
				str += op1(data[i]);
			}
			$("#ko" + (id - 1)).after(str);
		}
	});
}

function op1(obj) {
	var str = '';
	str += '<td>' + obj.DATA_NAME + '</td>';
	str += '<td><input type="text" id="RT_' + obj.DATA_ID + '_1" name="RT_' + obj.DATA_ID + '_1" value="" readonly="readonly" /></td>';
	str += '<td><input type="text" id="RT_' + obj.DATA_ID + '_2" name="RT_' + obj.DATA_ID + '_2" value="" readonly="readonly" /></td>';
	str += '<td><input type="text" id="RT_' + obj.DATA_ID + '_3" name="RT_' + obj.DATA_ID + '_3" value="" /></td>';
	str += '<td><input type="text" id="RT_' + obj.DATA_ID + '_4" name="RT_' + obj.DATA_ID + '_4" value="" /></td>';
	str += '<td><input type="text" id="RT_' + obj.DATA_ID + '_5" name="RT_' + obj.DATA_ID + '_5" value="" /></td>';
	str += '<td><input type="text" id="RT_' + obj.DATA_ID + '_6" name="RT_' + obj.DATA_ID + '_6" value="" /></td>';
	str += '<input type="hidden"  name="RT" value="' + obj.DATA_ID + '" />';
	str += '</tr>';
	return str;
}
function op2() {
	$.ajax({
		type : "POST",
		url : path + "/essence/getTop3?id=" + $("#aid").val(),
		async : false,
		dataType : 'json',
		success : function(json) {
			for ( var key in json[0]) {
				$("#" + key).val(json[0][key]);
			}

		}

	});
}
function op3() {
	$.ajax({
		type : "POST",
		url : path + "/essence/getTop4?id=" + $("#aid").val(),
		async : false,
		dataType : 'json',
		success : function(json) {
			for (var i = 0; i < json.length; i++) {
				for (var j = 1; j < 7; j++) {
					$("#RT_" + json[i].DATA_ID + "_" + j).val(json[i]['RU' + j]);
				}
			}
		}
	});
}

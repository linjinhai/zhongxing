var path = $('#path').val();
// 监测项目1 获取所有传感器类型
$(function() {
	jQuery("#checkType").empty();
	$.ajax({
		type : "POST",
		url : path + "/queryCount/getType",
		async : false,
		dataType : 'json',
		data : $('#form1').serialize(),
		success : function(data) {
			jQuery("<option value=''>请选择</option>").appendTo("#checkType");
			for (var i = 0; i < data.length; i++) {
				jQuery(
						"<option value='" + data[i].TYPE_ID + "'>"
								+ data[i].TYPE_NAME + "</option>").appendTo(
						"#checkType");
			}
		}
	});
	$("#checkType").val($("#mapCheckType").val());
	

});


// 报警导出
function exportExcel(viewFlag,outFlag) {

	if (outFlag == "false") {	   
        alert("抱歉！您没有权限操作此功能！");
        return;
    }
		var data = $('#form1').serialize();
		var url = path + "/queryCount/exportExcel?" + data + "&viewFlag="+viewFlag;
		window.open(url)

}

// 列表数据查询
function sousuo(viewFlag) {

	var b_date = $('#b_date').val();
	var e_date = $('#e_date').val();

	if (b_date != '') {
		if (e_date == '') {
			alert("请选择：结束时间！");
			return;
	}
 }
if (e_date != '') {
	if (b_date == '') {
		alert("请选择：开始时间！");
		return;
	}
}
	

		// 时间判断
		var start = new Date(b_date.replace("-", "/").replace("-", "/"));
		var end = new Date(e_date.replace("-", "/").replace("-", "/"));
		if (end < start) {
			alert("开始时间小于结束时间！！");
			return false;
		}
		
		var zx = $('#zx').val();
		var zd = $('#zd').val();
		
		
		
	

	document.form1.action = path + "/queryCount/queryListWarn?viewFlag="+viewFlag
	document.form1.submit();

}
//翻页跳转函数
function gotoPage(currentPage) {
	if (currentPage == "select") {
		document.getElementById("currentPage").value = 1;
	} else {
		document.getElementById("currentPage").value = currentPage;
	}
	document.form1.action = path + "/queryCount/queryListWarn"
	document.form1.submit();
}
// 校验查询字段

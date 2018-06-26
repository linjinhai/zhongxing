//全局变量
zgx={
	base : document.getElementById("systemPath").value + "/",
	shiro_role_id : document.getElementById("shiro_role_id").value,
	shiro_menu_id : document.getElementById("shiro_menu_id").value,
	errorSign : "<i class='fa fa-times-circle'></i> "
}

//按钮权限判断
function shiro(url, buttonType){
//	$.ajax({
//		url : zgx.base + "role/shiro",
//		type : "get",
//		data : {role_id : zgx.shiro_role_id, menu_id : zgx.shiro_menu_id, buttonType : buttonType},
//		dataType : "text",
//		success : function(data){
//			if (data == "true") {
//				$("#loading").show();
//				location.href = url;
//			} else{
//				$("#loading").hide();
//				alert("抱歉！您没有权限操作此功能！");
//			}
//		},
//		error : function(XMLHttpRequest, textStatus, errorThrown) {
//			alert(XMLHttpRequest.status);
//			alert(XMLHttpRequest.readyState);
//			alert(textStatus);
//			alert(errorThrown);
//		}
//	});
//	
	$.get(zgx.base + 'role/shiro', {role_id : zgx.shiro_role_id, menu_id : zgx.shiro_menu_id, buttonType : buttonType}, function(data) {
		if (data == "true") {
			$("#loading").show();
			location.href = url;
		} else{
			$("#loading").hide();
			alert("抱歉！您没有权限操作此功能！");
		}
	});
}

//添加跳转
function add(subURL, buttonType) {
	var url = zgx.base + subURL;
	shiro(url, buttonType);
};

//修改跳转
function upd(subURL, id, buttonType) {
	var url = zgx.base + subURL + "?id=" + id;
	shiro(url, buttonType);
};

//删除
function del(subURL, id, buttonType) {
	$.get(zgx.base + 'role/shiro', {role_id : zgx.shiro_role_id, menu_id : zgx.shiro_menu_id, buttonType : buttonType}, function(data) {
		if (data == "true") {
			if(confirm("确定删除?")){
				$("#loading").show();
				location.href = zgx.base + subURL + "?flag=delete&id=" + id;
			}
		} else{
			$("#loading").hide();
			alert("抱歉！您没有权限操作此功能！");
		}
	});
	
};

//下载
function download(subURL, fileName) {
	location.href = zgx.base + subURL + "?fileName=" + fileName;
};

//面包屑导航
function crumb(subURL){
	location.href = zgx.base + subURL;
}

//上一步
function prev() {
	history.go(-1);
}

//返回
function back() {
	history.back(-1);
}
//导出文件
function exportExcel(subURL) {
	document.form1.action = zgx.base + subURL;
	document.form1.submit();
}


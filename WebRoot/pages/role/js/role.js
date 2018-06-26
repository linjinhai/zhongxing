$().ready(function() {
	//表单验证
	var validator = $("#z_form").validate();
	//加载效果
	window.loading = function(){
		if(validator.form()){
			$("#loading").show();
		}
	}
	
	//删除角色
	window.roleDel = function(role_id, buttonType) {
		if(role_id == '1'){
			alert("此用户不允许删除");
		}else{
			$.get(zgx.base + 'role/shiro', {role_id : zgx.shiro_role_id, menu_id : zgx.shiro_menu_id, buttonType : buttonType}, function(data) {
				if (data == "true") {
					if(confirm("确定删除?")){
						$("#loading").show();
						$.post(zgx.base + 'role/delete', {
							role_id : role_id
						}, function(data) {
							if (data == "true") {
								location.href = zgx.base + "role/list";
							} else {
								alert("当前角色下存在用户，请先删除用户");
							}
						});
					}
				} else{
					$("#loading").hide();
					alert("抱歉！您没有权限操作此功能！");
				}
			});
		}
	}
	
	//权限编辑
	window.permission = function(role_id, buttonType) {
		$.get(zgx.base + 'role/shiro', {role_id : zgx.shiro_role_id, menu_id : zgx.shiro_menu_id, buttonType : buttonType}, function(data) {
			if (data == "true") {
				$("#loading").show();
				location.href = zgx.base + "role/permissionPage?role_id=" + role_id;
			} else{
				$("#loading").hide();
				alert("抱歉！您没有权限操作此功能！");
			}
		});
	}
	
	// 初始化菜单时(进入页面时)，子菜单中有任意选中，父菜单默认选中
	$("input[type=checkbox]").each(function() {
		if ($(this).is(":checked")) {
			$("#allCheck").get(0).checked = true;
		}
	});
	$("input[pid]").each(function() {
		if ($(this).is(":checked")) {
			$("#" + $(this).attr("pid")).get(0).checked = true;
		}
	});
	$("input[ppid]").each(function() {
		if ($(this).is(":checked")) {
			$("#" + $(this).attr("ppid")).get(0).checked = true;
			$("#" + $(this).attr("mid")).get(0).checked = true;
		}
	});
	
	//全选
	$("#allCheck").click(function(){
		if($(this).is(":checked")){
			$("input[type=checkbox]").prop("checked",true); 
		} else{
			$("input[type=checkbox]").prop("checked",false); 
		}
	});

	// 单击父菜单选中or不选，子菜单全选or全不选
	window.pcheck = function(menu_id) {
		if ($("#" + menu_id).is(":checked")) {
			$("input[pid=" + menu_id + "]").each(function() {
				$(this).get(0).checked = true;
			});
			$("input[ppid=" + menu_id + "]").each(function() {
				$(this).get(0).checked = true;
			});
		} else {
			$("input[pid=" + menu_id + "]").each(function() {
				$(this).get(0).checked = false;
			});
			$("input[ppid=" + menu_id + "]").each(function() {
				$(this).get(0).checked = false;
			});
		}
	}

	// 单击子菜单任意选中，父菜单选中、对应按钮选中
	window.ccheck = function(menu_pid, menu_id) {
		var flag = false;
		$("input[pid=" + menu_pid + "]").each(function() {
			if ($(this).is(":checked")) {
				flag = true;
			}
		});
		if (flag) {
			$("#" + menu_pid).get(0).checked = true;
		} else {
			$("#" + menu_pid).get(0).checked = false;
		}
		
		if ($("#" + menu_id).is(":checked")) {
			$("input[mid=" + menu_id + "]").each(function() {
				$(this).get(0).checked = true;
			});
		} else {
			$("input[mid=" + menu_id + "]").each(function() {
				$(this).get(0).checked = false;
			});
		}
	}
	
	// 单击按钮权限任意选中，子菜单选中、父菜单选中
	window.bcheck = function(menu_id) {
		var flag = false;
		$("input[mid=" + menu_id + "]").each(function() {
			if ($(this).is(":checked")) {
				flag = true;
			}
		});
		if (flag) {
			$("#" + menu_id).get(0).checked = true;
		} 
		//else {
		//	$("#" + menu_id).get(0).checked = false;
		//}
		
		var flag1 = false;
		$("input[pid=" + $("#" + menu_id).attr("pid") + "]").each(function() {
			if ($(this).is(":checked")) {
				flag1 = true;
			}
		});
		if (flag1) {
			$("#" + $("#" + menu_id).attr("pid")).get(0).checked = true;
		} else {
			$("#" + $("#" + menu_id).attr("pid")).get(0).checked = false;
		}
	}

});
$().ready(function() {
	$("#z_form").validate();

	if ($("#is_child").val() == "0") {
		$("#menu_pid").attr("readonly", "readonly");
		$("#menu_pid").children().hide();
	}
	$("#is_child").change(function() {
		if ($(this).val() == "0") {
			$("#menu_pid").attr("readonly", "readonly");
			$("#menu_pid").children().hide();
		} else {
			if ($("#menu_pid[readonly]")) {
				$("#menu_pid").removeAttr("readonly");
				$("#menu_pid").children().show();
			}
		}
	});
	
	window.buttonList = function(){
		location.href = zgx.base + "menu/buttonList";
	}
	
	window.menu_add = function(subURL) {
		location.href = zgx.base + subURL;
	};
	window.menu_upd = function(subURL, id) {
		location.href = zgx.base + subURL + "?id=" + id;
	};

	window.menu_del = function(subURL, id) {
		if(confirm("确定删除?")){
			location.href = zgx.base + subURL + "?flag=delete&id=" + id;
		}
	};
});
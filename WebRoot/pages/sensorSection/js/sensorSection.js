$().ready(function() {
	//表单验证
	var validator = $("#z_form").validate();
	//加载效果
	window.loading = function() {
		if ($("#type_id").val() == '-1') {
			$.ajax({
				type : "POST",
				async : false,
				url : zgx.base + "sensorSection/getSectionPathSysf",
				dataType : "text",
				success : function(data) {
					if ($("#flag").val() == 'insert') {
						if (data != 0) {
							alert("已存在首页截面，不可重复添加！");
							return false;
						} else {
							if (validator.form()) {
								$("#z_form").submit();
								$("#loading").show();
							}
						}
					} else {
						if (validator.form()) {
							$("#z_form").submit();
							$("#loading").show();
						}
					}
				}

			});
		} else {
			if (validator.form()) {
				$("#z_form").submit();
				$("#loading").show();
			}
		}

	}

	//删除传感器截面
	window.sectionDel = function(section_id, buttonType) {
		$.get(zgx.base + 'role/shiro', {
			role_id : zgx.shiro_role_id,
			menu_id : zgx.shiro_menu_id,
			buttonType : buttonType
		}, function(data) {
			if (data == "true") {
				if (confirm("确定删除?")) {
					$("#loading").show();
					$.post(zgx.base + 'sensorSection/delete', {
						flag : "delete",
						section_id : section_id
					}, function(data) {
						if (data == "true") {
							location.href = zgx.base + "sensorSection/list?t=j";
						}
					});
				}
			} else {
				$("#loading").hide();
				alert("抱歉！您没有权限操作此功能！");
			}
		});
	}

	//图片方法查看
	$(".sectionImg").each(function() {
		$(this).click(function() {
			//$(this).next().show();
			var img = "<div id='sectionImg'><div style='position:absolute; width:100%;height:100%;z-index:999;background-color:#333;opacity:0.5;'></div>"
			//+ "<span id='sectionClose' onclick=\"javascript:document.body.removeChild(document.getElementById('sectionImg'));\" class='glyphicon glyphicon-remove-sign' style='color:#ffffff;position:absolute;z-index:9999;top:0;right:0;font-size:30px;cursor:pointer; margin:auto;'></span>"
			+ "<div style='position:absolute;width:500px;height:500px;z-index:1000;margin:auto;top:0;left:0;bottom:0;right:0;'>" + "		<span id='sectionClose' onclick=\"javascript:document.body.removeChild(document.getElementById('sectionImg'));\" class='glyphicon glyphicon-remove-sign' style='color:#ffffff;position:absolute;z-index:9999;top:0;right:0;font-size:30px;cursor:pointer; margin:auto;'></span>" + "		<img src='" + $(this).attr("src") + "' />" + "</div></div>";
			$("body", window.document).prepend(img);
		});
	});

});
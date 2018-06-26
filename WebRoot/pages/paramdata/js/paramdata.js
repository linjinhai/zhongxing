$().ready(function() {
	//表单验证
	var validator = $("#z_form").validate();
	//加载效果
	window.loading = function(){
		if(validator.form()){
			$("#loading").show();
		}
	}
});
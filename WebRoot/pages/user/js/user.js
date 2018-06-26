$().ready(function() {
	//表单验证
	var validator = $("#z_form").validate({
		rules : {
			user_id: {
				remote : {
					url : zgx.base + "user/verifyRepeat",
					data : {user_id : function(){
						return $("#user_id").val();
					}}
				}
			},
			user_pass_again : {
				equalTo : "#user_pass"
			}
		},
		messages : {
			user_id : {
				remote : zgx.errorSign + "登录名已存在"
			},
			user_pass_again : {
				equalTo : zgx.errorSign + "两次输入密码不相同"
			}
		}
	});
	
	//加载效果
	window.loading = function(){
		if(validator.form()){
			$("#loading").show();
		}
	}
});
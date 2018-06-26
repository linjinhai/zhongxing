/*
$().ready(function() {

	//自定义验证方法的表单验证
	$.validator.addMethod("greater2", function(value, element, param) {
		return this.optional(element) || value == "" ? true : Number(value) > Number($("#warn_lv1").val());
	}, zgx.errorSign + "必须大于一级上限预警值");

	$.validator.addMethod("greater3", function(value, element, param) {
		return this.optional(element) || value == "" ? true : Number(value) > Number($("#warn_lv2").val());
	}, zgx.errorSign + "必须大于二级上限预警值");

	$.validator.addMethod("less22", function(value, element, param) {
		return this.optional(element) || value == "" ? true : Number(value) < Number($("#warn_lv11").val());
	}, zgx.errorSign + "必须小于一级下限预警值");

	$.validator.addMethod("less33", function(value, element) {
		return this.optional(element) || value == "" ? true : Number(value) < Number($("#warn_lv22").val());
	}, zgx.errorSign + "必须小于二级下限预警值");
	$.validator.addMethod("less11", function(value, element) {
		return this.optional(element) || value == "" ? true : Number(value) < Number($("#warn_lv1").val());
	}, zgx.errorSign + "必须小于一级上限预警值");
	

	

	var validator = $("#z_form").validate({
		rules : {
			warn_lv1 : {
				number : true
			},
			warn_lv11 : {
				number : true,
				less11 : true
			},
			warn_lv2 : {
				number : true,
				greater2 : true
			},
			warn_lv22 : {
				number : true,
				less22 : true
			},
			warn_lv3 : {
				number : true,
				greater3 : true
			},
			warn_lv33 : {
				number : true,
				less33 : true
			}
		},
		messages : {}
	});
	//加载效果
	window.loading = function(){
		if(validator.form()){
			$("#loading").show();
		}
	}
});
*/
function bc(){
	var warn_lv1 = $("#warn_lv1").val();
	var warn_lv11 = $("#warn_lv11").val();
	var warn_lv2 = $("#warn_lv2").val();
	var warn_lv22 = $("#warn_lv22").val();
	var warn_lv3 = $("#warn_lv3").val();
	var warn_lv33 = $("#warn_lv33").val();
	
	if(warn_lv1!=''){
		if (testFds(warn_lv1) == false) {
			alert('数据格式错误！');
			return;
		}
	}
	if(warn_lv2!=''){
		if (testFds(warn_lv2) == false) {
			alert('数据格式错误！');
			return;
		}
	}
	if(warn_lv3!=''){
		if (testFds(warn_lv3) == false) {
			alert('数据格式错误！');
			return;
		}
	}
	if(warn_lv11!=''){
		if (testFds(warn_lv11) == false) {
			alert('数据格式错误！');
			return;
		}
	}
	if(warn_lv22!=''){
		if (testFds(warn_lv22) == false) {
			alert('数据格式错误！');
			return;
		}
	}
	if(warn_lv33!=''){
		if (testFds(warn_lv33) == false) {
			alert('数据格式错误！');
			return;
		}
	}
	
	

	if(warn_lv3!=''){
		if(warn_lv2!=''){
			if(Number(warn_lv2)>Number(warn_lv3)){
				alert('二级上限不能大于三级上限！');
				return;
			}
		}
		if(warn_lv1!=''){
			if(Number(warn_lv1)>Number(warn_lv3)){
				alert('一级上限不能大于三级上限！');
				return;
			}
		}
		if(warn_lv11!=''){
			if(Number(warn_lv11)>Number(warn_lv3)){
				alert('一级下限不能大于三级上限！');
				return;
			}
		}
		if(warn_lv22!=''){
			if(Number(warn_lv22)>Number(warn_lv3)){
				alert('二级下限不能大于三级上限！');
				return;
			}
		}
		if(warn_lv33!=''){
			if(Number(warn_lv33)>Number(warn_lv3)){
				alert('三级下限不能大于三级上限！');
				return;
			}
		}
	}
	
	
	
	if(warn_lv2!=''){
		if(warn_lv3!=''){
			if(Number(warn_lv2)>Number(warn_lv3)){
				alert('二级上限不能大于三级上限！');
				return;
			}
		}
		if(warn_lv1!=''){
			if(Number(warn_lv1)>Number(warn_lv2)){
				alert('一级上限不能大于二级上限！');
				return;
			}
		}
		if(warn_lv11!=''){
			if(Number(warn_lv11)>Number(warn_lv2)){
				alert('一级下限不能大于二级上限！');
				return;
			}
		}
		if(warn_lv22!=''){
			if(Number(warn_lv22)>Number(warn_lv2)){
				alert('二级下限不能大于二级上限！');
				return;
			}
		}
		if(warn_lv33!=''){
			if(Number(warn_lv33)>Number(warn_lv2)){
				alert('三级下限不能大于二级上限！');
				return;
			}
		}
	}
	
	
	
	if(warn_lv1!=''){
		if(warn_lv3!=''){
			if(Number(warn_lv1)>Number(warn_lv3)){
				alert('一级上限不能大于三级上限！');
				return;
			}
		}
		if(warn_lv2!=''){
			if(Number(warn_lv1)>Number(warn_lv2)){
				alert('一级上限不能大于二级上限！');
				return;
			}
		}
		if(warn_lv11!=''){
			if(Number(warn_lv11)>Number(warn_lv1)){
				alert('一级下限不能大于一级上限！');
				return;
			}
		}
		if(warn_lv22!=''){
			if(Number(warn_lv22)>Number(warn_lv1)){
				alert('二级下限不能大于一级上限！');
				return;
			}
		}
		if(warn_lv33!=''){
			if(Number(warn_lv33)>Number(warn_lv1)){
				alert('三级下限不能大于一级上限！');
				return;
			}
		}
	}
	
	if(warn_lv11!=''){
		if(warn_lv3!=''){
			if(Number(warn_lv11)>Number(warn_lv3)){
				alert('一级下限不能大于三级上限！');
				return;
			}
		}
		if(warn_lv2!=''){
			if(Number(warn_lv11)>Number(warn_lv2)){
				alert('一级下限不能大于二级上限！');
				return;
			}
		}
		if(warn_lv1!=''){
			if(Number(warn_lv11)>Number(warn_lv1)){
				alert('一级下限不能大于一级上限！');
				return;
			}
		}
		if(warn_lv22!=''){
			if(Number(warn_lv22)>Number(warn_lv11)){
				alert('二级下限不能大于一级下限！');
				return;
			}
		}
		if(warn_lv33!=''){
			if(Number(warn_lv33)>Number(warn_lv11)){
				alert('三级下限不能大于一级下限！');
				return;
			}
		}
	}
	
	if(warn_lv22!=''){
		if(warn_lv3!=''){
			if(Number(warn_lv22)>Number(warn_lv3)){
				alert('二级下限不能大于三级上限！');
				return;
			}
		}
		if(warn_lv2!=''){
			if(Number(warn_lv22)>Number(warn_lv2)){
				alert('二级下限不能大于二级上限！');
				return;
			}
		}
		if(warn_lv1!=''){
			if(Number(warn_lv22)>Number(warn_lv1)){
				alert('二级下限不能大于一级上限！');
				return;
			}
		}
		if(warn_lv11!=''){
			if(Number(warn_lv22)>Number(warn_lv11)){
				alert('二级下限不能大于一级下限！');
				return;
			}
		}
		if(warn_lv33!=''){
			if(Number(warn_lv33)>Number(warn_lv22)){
				alert('三级下限不能大于二级下限！');
				return;
			}
		}
	}
	
	
	if(warn_lv33!=''){
		if(warn_lv3!=''){
			if(Number(warn_lv33)>Number(warn_lv3)){
				alert('三级下限不能大于三级上限！');
				return;
			}
		}
		if(warn_lv2!=''){
			if(Number(warn_lv33)>Number(warn_lv2)){
				alert('三级下限不能大于二级上限！');
				return;
			}
		}
		if(warn_lv1!=''){
			if(Number(warn_lv33)>Number(warn_lv1)){
				alert('三级下限不能大于一级上限！');
				return;
			}
		}
		if(warn_lv11!=''){
			if(Number(warn_lv33)>Number(warn_lv11)){
				alert('三级下限不能大于一级下限！');
				return;
			}
		}
		if(warn_lv22!=''){
			if(Number(warn_lv33)>Number(warn_lv22)){
				alert('三级下限不能大于二级下限！');
				return;
			}
		}
	}
	
	
	$("#z_form").submit();
	
	
	
	
}
//判断是否为浮点数
function testFds(str) {
	var r = /^(-?\d+)(\.\d+)?$/;
	return r.test(str);
}
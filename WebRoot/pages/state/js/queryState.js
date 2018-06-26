
var path = $('#path').val();
var partSecond=$('#partSecond').val();
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

$(function() {
	sousuo();
});



setInterval(sousuo,5000); 
function sousuo(){
	 $.ajax({
	        type: "POST",
	        url: path + "/queryCount/queryListState",
	           async: false,
	           cache:false,
	           dataType:'json',	    
	           data : $('#form1').serialize(),
	           success: function (data) {	
	        	   jQuery("#tb tbody").empty();
	            var str="";     
         
             for(var i=0;i<data.length;i++){           
             str=str+"<tr>";                  
             str=str+"<td>";
             str=str+data[i].SENSOR_ID;
             str=str+"</td>";           
             str=str+"<td>";
             str=str+data[i].SENSOR_CODE;
             str=str+"</td>";
             str=str+"<td>";
             str=str+data[i].SENSOR_NAME;
             str=str+"</td>";
             str=str+"<td>";
             str=str+data[i].TYPE_NAME;
             str=str+"</td>";
             str=str+"<td>";
             str=str+data[i].PART_NAME;
             str=str+"</td>";
             str=str+"<td>";
             str=str+data[i].SECTION_NAME; 
             str=str+"</td>";
             str=str+"<td>";
            if(data[i].UPDATE_STATUS=='0') {
             str=str+" <span style=\"color: yellowgreen;\">正常</span>"; }
            if(data[i].UPDATE_STATUS=='1'){
             str=str+" <span style=\"color: red;\">异常</span>"; }
             str=str+"</td>";
             str=str+"<td>";
             str=str+data[i].UPDATE_VALUE; 
             str=str+"</td>";
             str=str+"<td>";
             str=str+data[i].UPDATE_TIME; 
             str=str+"</td>";
       
            str=str+"</tr>";           
             }
             $("#tb tbody").append(str);              
        }
	        
	    });
}




//翻页跳转函数
function gotoPage(currentPage) {
	if (currentPage == "select") {
		document.getElementById("currentPage").value = 1;
	} else {
		document.getElementById("currentPage").value = currentPage;
	}
	document.form1.action = path + "/queryCount/queryListState"
	document.form1.submit();
}

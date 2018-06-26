//初始化默认曲线和列表查询不显示
$(function() {
	$("#div1").css("display", "none");// 隐藏div
	$("#div2").css("display", "none");// 隐藏div
    $("#bh"+$("#sdw").val()).css("display","block");	
});
var path = $('#path').attr("value");
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
						"<option value='" + data[i].TYPE_ID + "'>"+ data[i].TYPE_NAME + "</option>").appendTo("#checkType");
			}
		}
	});

});

// 监测项目1 通过传感器type_id来获取传感器种类列表
function getPart() {
	jQuery("#partType").empty();
	jQuery("#sectionList").empty();
	$("#s_sensor").val("");
	$("#s_sensor_name").val("");
	$.ajax({
		type : "POST",
		url : path + "/queryCount/getPart?type_id=" + $("#checkType").val(),
		async : false,
		dataType : 'json',
		data : $('#form1').serialize(),
		success : function(data) {
			jQuery("<option value=''>请选择</option>").appendTo("#partType");
			for (var i = 0; i < data.length; i++) {
				jQuery("<option value='" + data[i].PART_ID + "'>"+ data[i].PART_NAME + "</option>").appendTo("#partType");
			}
		}
	});	
}
// 监测项目1 通过传感器种类type_id获取截面列表
function getSection() {
	jQuery("#sectionList").empty();
	$.ajax({
		type : "POST",
		url : path + "/queryCount/getSection?part_id=" + $("#partType").val(),
		async : false,
		dataType : 'json',
		data : $('#form1').serialize(),
		success : function(data) {
			jQuery("<option value=''>请选择</option>").appendTo("#sectionList");
			for (var i = 0; i < data.length; i++) {
				jQuery("<option value='" + data[i].SECTION_ID + "'>"+ data[i].SECTION_NAME + "</option>").appendTo("#sectionList");
				$("#partUnit").val(data[0].PART_UNIT);
			}

		}
	});
}
//根据查询周期选择日期类型
function getSelect(){   	
    for(var i=1;i<5;i++){
        $("#bh"+i).css("display","none");
    }
    $("#bh"+$("#sdw").val()).css("display","");
}     
// 通过传感器种类section_id获取传感器列表
function getSectionSensor(flag) {
	if ($("#sectionList").val() != null) {
		$("#id1").attr(
				"href",path + "/pages/query/querySelectSensor.jsp?flag=" + flag+ "&partId=" + $("#checkType").val()+ "&sectionList=" + $("#sectionList").val());
		$("#id1").click();
	} else {
		alert("请输入监测项目！");
	}
}
// 导出

function exportExcel(viewFlag,outFlag) {
	if (outFlag == "false") {	   
        alert("抱歉！您没有权限操作此功能！");
        return;
    }
	   var data = $('#form1').children().not($('#dataList')).serialize();	
		var url = path + "/queryCount/exportExcel?" + data + "&viewFlag="+ viewFlag;
		window.open(url)
}

// 列表曲线数据查询
function serachShowDiv(obj,viewFlag) {
	
	if (viewFlag == "false") {	   
        alert("抱歉！您没有权限操作此功能！");
        return;
    }
	  var  checkType=$('#checkType').val();
	   if(checkType==''){alert("请选择：监测类型！");return;}
	   
	   var  partType=$('#partType').val();
	   if(partType==''){alert("请选择：监测项目！");return;}
	   
	   var  sectionList=$('#sectionList').val();
	   if(sectionList==''){alert("请选择：截面 ！");return;}
	  
	   var  s_sensor=$('#s_sensor').val();
	   if(s_sensor==''){alert("请选择：监测编号！");return;}
	   
	   var sdw=$('#sdw').val();
	
	   var sdw2=$('#sdw2').val();
	   var sdw3=$('#sdw3').val();
	   var sdw4=$('#sdw4').val();
	   var sdw5=$('#sdw5').val();
	   var sdw6=$('#sdw6').val();
	   var sdw7=$('#sdw7').val();
	   var sdw8=$('#sdw8').val();
	   var sdw9=$('#sdw9').val();
	   var sdw10=$('#sdw10').val();
	   var countType=$('#countType').val();

	   
	   if($("#sdw").val()=='1'){
	       if($("#sdw2").val()=='' ||$("#sdw3").val()=='' ||($("#sdw3").val()-$("#sdw2").val()<0)){
	           alert("请选择日期，或日区间有误！");
	           return;
	       }

	   }
	   
	   if($("#sdw").val()=='2'){
	       if($("#sdw4").val()=='' || ($("#sdw6").val()-$("#sdw5").val()<0)){
	           alert("请选择日期，或周区间有误！");
	           return;
	       }
	   
	   }
	   
	   if($("#sdw").val()=='3'){
	       if($("#sdw7").val()=='' || $("#sdw8").val()==''||($("#sdw8").val().replace(/\-/g, '')-$("#sdw7").val().replace(/\-/g, '')<0)){
	           alert("请选择日期，或月区间有误！");
	           return;
	       }
	      
	   }
	   
	   if($("#sdw").val()=='4'){
	       if($("#sdw9").val()=='' || $("#sdw10").val()==''||($("#sdw10").val()-$("#sdw9").val()<0)){
	           alert("请选择日期，或年区间有误！");
	           return;
	       }
	   }
	   
	  //  数据范围判断 
	   var reg=/^[0-9]+([.]{1}[0-9]+){0,1}$/;
	   var  b_data=$('#b_data').val();
	 
	      if(b_data!=''){
	    	     if (!reg.exec(b_data)) {
	    	         alert("请输入整数或者小数！");
	    	         return }   	   
	      }       
	       var  e_data=$('#e_data').val();
	       if(e_data!=''){
	           if(!reg.exec(e_data)){
	               alert("请输入整数或者小数！");
	               return;
	           }      
	       } 
	  var mapSsensor = $('#mapSensor').val();
	 if (mapSsensor != '') {
	    var list=$('#dataList').val();
	    var data=eval("("+list+")");
	
		   	if(sdw=='1'){
				   if(mapSsensor == s_sensor&&data.map.b_data ==b_data && data.map.e_data==e_data&&data.map.countType == countType
						   &&data.map.sdw ==sdw&&data.map.sdw2 ==sdw2&&data.map.sdw3 ==sdw3) {							
						/*	if (obj == 'div1') {
								$("#div2").css("display", "none");// 隐藏div
								$("#div1").css("display", "block");// 显示div
								autoTable(data.list);
							}
							if (obj == 'div2') {
								$("#div2").css("display", "block");// 显示div
								$("#div1").css("display", "none");// 隐藏div					
								var sensor_name=$('#sensor_name').val();					
								charts(data.list,sensor_name);}*/
					          ChangDivShow(obj,data.list,$('#sensor_name').val());
							}
							else{
								getAjaxCount(obj);
							}}
			     if(sdw=='2'){
                   if(mapSsensor == s_sensor&&data.map.b_data ==b_data && data.map.e_data==e_data&&data.map.countType == countType
                		   &&data.map.sdw ==sdw&&data.map.sdw4==sdw4&&data.map.sdw5 ==sdw5&&data.map.sdw6 ==sdw6) {
							
                	      ChangDivShow(obj,data.list,$('#sensor_name').val());
							}
							else{
								getAjaxCount(obj);
							}}
			      if(sdw=='3'){
                     if(mapSsensor == s_sensor&&data.map.b_data ==b_data && data.map.e_data==e_data&&data.map.countType == countType
                    		 &&data.map.sdw ==sdw&&data.map.sdw7==sdw7&&data.map.sdw8 ==sdw8) {
							
                    	     ChangDivShow(obj,data.list,$('#sensor_name').val());
							}
							else{
								getAjaxCount(obj);
							}}
					if(sdw=='4'){	
                      if(mapSsensor == s_sensor&&data.map.b_data ==b_data && data.map.e_data==e_data
                    		  &&data.map.countType ==countType&&data.map.sdw ==sdw&&data.map.sdw9==sdw9&&data.map.sdw10 ==sdw10 ){
							
                    	       ChangDivShow(obj,data.list,$('#sensor_name').val());
                    	       }
			
							else{
								getAjaxCount(obj);
							}
                     }

	  } else {
		getAjaxCount(obj);
	}

}
// 根据div 显示列表 曲线图
function ChangDivShow(obj,list,name){
	//列表显示
	if (obj == 'div1') {
		$("#div2").css("display", "none");// 隐藏div
		$("#div1").css("display", "block");// 显示div
		autoTable(list);
	}
	///曲线图
	if (obj == 'div2') {
		$("#div2").css("display", "block");// 显示div
		$("#div1").css("display", "none");// 隐藏div					
		var sensor_name=$('#sensor_name').val();					
		charts(list,name);
		}
	}
	

//ajax 获取后台数据
function getAjaxCount(obj){
	$.ajax({
		type : "POST",
		url : path + "/queryCount/queryListCount?page=1&divFlag="+ obj,		
		dataType : 'text',
		data : $('#form1').serialize(),
		success : function(data1) {			
			var data=eval("("+data1+")");
			var sensor_name = data.sensor;
			$("#sensor_name").val(data.sensor);
			$("#mapSensor").val(data.map.s_sensor);
			$("#dataList").val(data1);
			if(data.divFlag=='div1'){		
				 $("#div1").css("display","block");//显示div
				 $("#div2").css("display", "none");// 隐藏div
				 	autoTable(data.list);
			}else if(data.divFlag=='div2'){
				 $("#div2").css("display","block");//显示div
				 $("#div1").css("display", "none");// 隐藏div	
                  charts(data.list,sensor_name);
			}
		}
	});
}


//构造表结构动态生成
function autoTable(list){
	jQuery("#tb tbody").empty();
	document.getElementById("loading").style.display = "block";  
	var   str="";
	for(i=0;i<list.length;i++){			
    str=str+"<tr>";
    str=str+"<td>";
    str=str+list[i].input_time;
    str=str+"</td>";
    str=str+"<td>";
    str=str+list[i].input_value + $("#partUnit").val();
    str=str+"</td>";
    str=str+"</tr>";	
	}
	  $("#tb tbody").append(str);
	document.getElementById("loading").style.display = "none";  
}



// 曲线图
function charts(data,name){
	 document.getElementById("loading").style.display = "block"; 
    var a=new Array();
    var b=new Array(); 
    for(var i=0;i<data.length;i++){
            a.push(data[i].input_time);
            b.push(data[i].input_value);
        }   
	    $('#pline').highcharts({
	        title: {
	            text: '数据统计查询曲线图',
	            x: -20 //center
	        },	       
	        xAxis: {//横轴的数据  
	            categories:a,  
	          lineWidth:1,//纵轴一直为空所对应的轴，即X轴  
	          plotLines: [{//一条竖线  
	               color: '#FF0000',  
	               width: 2,  
	               value: 5.5  
	           }]  
	  
	        },  
	        yAxis: {
	            
	            plotLines: [{
	                value: 0,
	                width: 1,
	                color: '#808080'
	            }]
	        },
	        tooltip: {
	            valueSuffix: ''
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            borderWidth: 0
	        },
	        series: [{
	            name: name,
	            //data: b
	            data: 
	            (function () {
					 document.getElementById("loading").style.display = "none"; 
	                 return b;
	             })()
	        }]
	    });
	}






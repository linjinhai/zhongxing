$(function(){ 

	$("#div1").css("display","none");//隐藏div
    $("#div2").css("display","none");//隐藏div   
	

 });


var path = $('#path').attr("value");

$(function(){
    // 获取所有传感器类型
	jQuery("#checkType").empty();
	 jQuery("#checkType0").empty();
    $.ajax({type : "POST",  
       url : path+"/queryCount/getType",   
       async: false,
       dataType:'json',
       data:$('#form1').serialize(),       
       success : function (data){  
            jQuery("<option value=''>传感器类型</option>").appendTo("#checkType");
            for(var i=0;i<data.length;i++){
                jQuery("<option value='"+data[i].TYPE_ID+"'>"+data[i].TYPE_NAME+"</option>").appendTo("#checkType");
           }
            
            jQuery("<option value=''>传感器类型</option>").appendTo("#checkType0");
            for(var i=0;i<data.length;i++){
                jQuery("<option value='"+data[i].TYPE_ID+"'>"+data[i].TYPE_NAME+"</option>").appendTo("#checkType0");
           }
       }  
    });
 

  
});


//通过传感器type_id来获取传感器种类列表
function getPart(){        
    jQuery("#partType").empty();
    jQuery("#sectionList").empty();
	$("#s_sensor").val("");
	$("#s_sensor_name").val("");
    //清空项目1传感器id，名称

	
      $.ajax({type : "POST",  
         url : path+"/queryCount/getPart?type_id="+$("#checkType").val(),   
         async: false,
         dataType:'json',
         data:$('#form1').serialize(),       
         success : function (data){  
              jQuery("<option value=''>请选择</option>").appendTo("#partType");
              for(var i=0;i<data.length;i++){
                  jQuery("<option value='"+data[i].PART_ID+"'>"+data[i].PART_NAME+"</option>").appendTo("#partType");
              }
              
         }  
      });
   
  }
//通过传感器种类type_id获取截面列表
function getSection(){
	$("#s_sensor").val("");
	$("#s_sensor_name").val("");
     jQuery("#sectionList").empty();
      $.ajax({type : "POST",  
         url : path+"/queryCount/getSection?part_id="+$("#partType").val(),   
         async: false,
         dataType:'json',
         data:$('#form1').serialize(),       
         success : function (data){  
              jQuery("<option value=''>请选择</option>").appendTo("#sectionList");
              for(var i=0;i<data.length;i++){
                  jQuery("<option value='"+data[i].SECTION_ID+"'>"+data[i].SECTION_NAME+"</option>").appendTo("#sectionList");
                  $("#partUnit").val(data[0].PART_UNIT);
              }
         }  
      });
    
  }

//通过传感器种类section_id获取传感器列表
function getSectionSensor(flag){
 if($("#sectionList").val()!=null){
 $("#id1").attr("href",path+"/pages/query/querySelectSensor.jsp?flag="+flag+"&partId=" + $("#checkType").val()+ "&sectionList="+$("#sectionList").val());    
 $("#id1").click(); 
 }else{
    alert("请输入监测项目！");}
}

//监测项目2通过传感器type_id来获取传感器种类列表
function getPart0(){         
      jQuery("#partType0").empty();
      jQuery("#sectionList0").empty();
      //清空项目2传感器id，名称
  		$("#s_sensor0").val("");
  		$("#s_sensor_name0").val("")
        $.ajax({type : "POST",  
           url : path+"/queryCount/getPart?type_id="+$("#checkType0").val(),   
           async: false,
           dataType:'json',
           data:$('#form1').serialize(),       
           success : function (data){  
                jQuery("<option value=''>请选择</option>").appendTo("#partType0");
                for(var i=0;i<data.length;i++){
                    jQuery("<option value='"+data[i].PART_ID+"'>"+data[i].PART_NAME+"</option>").appendTo("#partType0");
                }
               
           }  
        });
      
    }  
    
//监测项目2通过传感器种类type_id获取截面列表
function getSection0(){
    // alert($("#partType").val())  
       jQuery("#sectionList0").empty();
        $.ajax({type : "POST",  
           url : path+"/queryCount/getSection?part_id="+$("#partType0").val(),   
           async: false,
           dataType:'json',
           data:$('#form1').serialize(),       
           success : function (data){  
                jQuery("<option value=''>请选择</option>").appendTo("#sectionList0");
                for(var i=0;i<data.length;i++){
                    jQuery("<option value='"+data[i].SECTION_ID+"'>"+data[i].SECTION_NAME+"</option>").appendTo("#sectionList0");
                    $("#partUnit0").val(data[0].PART_UNIT);
                }
                
           }  
        });
 
      
    }

//通过传感器种类section_id获取传感器列表

function getSectionSensor0(flag){
	
 if($("#sectionList0").val()!=null){
    $("#id2").attr("href",path+"/pages/query/querySelectSensor.jsp?flag="+flag+"&partId=" + $("#checkType0").val()+ "&sectionList0="+$("#sectionList0").val());    
    $("#id2").click(); }
  else{
	 alert("请输入监测项目！"); 
 }
}

//通过列表数据查询
function serachShowDiv(obj,viewFlag){      
	if (viewFlag == "false") {	   
        alert("抱歉！您没有权限操作此功能！");
        return;
    }
   var  checkType=$('#checkType').val();
   if(checkType==''){alert("请选择：监测项目1！");return;}
   
   var  partType=$('#partType').val();
   if(partType==''){alert("请选择：监测项目1！");return;}
   
   var  sectionList=$('#sectionList').val();
   if(sectionList==''){alert("请选择：监测项目1！");return;}
  
   var  s_sensor=$('#s_sensor').val();
   if(s_sensor==''){alert("请选择：监测项目1！");return;}
   
   var  b_date=$('#b_date').val();
   if(b_date==''){alert("开始时间！");return;}
   
   var  e_date=$('#e_date').val();
   if(e_date==''){alert("请选择：结束时间！");return;}
	   
   var  checkType0=$('#checkType0').val();
   if(checkType0==''){alert("请选择：监测项目2！");return;}
   
   var  partType0=$('#partType0').val();
   if(partType0==''){alert("请选择：监测项目2！");return;}
   
   var  sectionList0=$('#sectionList0').val();
   if(sectionList0==''){alert("请选择：监测项目2！");return;} 
   var  s_sensor0=$('#s_sensor0').val();
   if(s_sensor0==''){alert("请选择：监测项目2！");return;}
   
   var  b_date=$('#b_date').val();
   if(b_date==''){alert("开始时间！");return;}
   
   var  e_date=$('#e_date').val();
   if(e_date==''){alert("请选择：结束时间！");return;}
   //时间判断 
   var start=new Date(b_date.replace("-", "/").replace("-", "/"));  
   var end=new Date(e_date.replace("-", "/").replace("-", "/"));  
   if(end<start){  
       alert("开始时间小于结束时间！！");
       return false;  
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
       var  b_data0=$('#b_data0').val();
       
       if(b_data0!=''){
              if (!reg.exec(b_data0)) {
                  alert("请输入整数或者小数！");
                  return }          
       }       
        var  e_data0=$('#e_data0').val();
        if(e_data0!=''){
            if(!reg.exec(e_data0)){
                alert("请输入整数或者小数！");
                return;
            }      
        } 
       var mapSsensor = $('#mapSensor').val();
       var mapSsensor0 = $('#mapSensor0').val();
  if (mapSsensor != '') {
    var data=$("#div11").data("hidValue");
     if(mapSsensor==s_sensor&&mapSsensor0==s_sensor0&& data.map.b_date==b_date && data.map.e_date == e_date
    		 && data.map.b_data == b_data&& data.map.e_data==e_data&& data.map.b_data0 == b_data0&& data.map.e_data0==e_data0) {          
         if(obj=='div1'){      	
             $("#div2").css("display","none");//隐藏div
             $("#div1").css("display","block");//显示div
        	
			 if($(" table tr td:eq(1)").text()==''){
             autoTable(data.page_list);
             autoTable0(data.page_list0);}
         } if(obj=='div2'){
        	   $("#div2").css("display","block");//显示div
               $("#div1").css("display","none");//隐藏div   
               var sensor_name=$('#sensor_name').val();
               var sensor_name0=$('#sensor_name0').val();
			  charts(data.list,sensor_name);
			  charts0(data.list0,sensor_name0);
         }          
    }
      else{       
    	    getAjaxCount(obj);
    	  $("#page").val("1");
   }
        
    } 
   else{
	   getAjaxCount(obj);
	   $("#page").val("1");
    }

}
function getAjaxCount(obj){

	 $.ajax({
			type : "POST",
			url : path + "/queryCount/queryListCompare?page=1&divFlag="+ obj,		
			dataType : 'json',
			data : $('#form1').serialize(),
			success : function(data) {			
			//	var data=eval("("+data1+")");				
				$("#sensor_name").val(data.sensor);
				$("#sensor_name0").val(data.sensor0);
				$("#mapSensor").val(data.map.s_sensor);
				$("#mapSensor0").val(data.map.s_sensor0);
				$("#page").val(data.map.page);
				$("#div11").data("hidValue", data);
				if(data.divFlag=='div1'){		
					 $("#div1").css("display","block");//显示div
					 $("#div2").css("display", "none");// 隐藏div				
					 	autoTable(data.page_list);
					 	autoTable0(data.page_list0);
				}else if(data.divFlag=='div2'){
					 $("#div2").css("display","block");//显示div
					 $("#div1").css("display", "none");// 隐藏div	
	                  charts(data.list,data.sensor);
	                  charts0(data.list0,data.sensor0);
				}
			}
		});
}

//项目1列表
function autoTable(list){
	  document.getElementById("loading").style.display = "block"; 
	jQuery("#tb tbody").empty();
	var   str="";
	for(i=0;i<list.length;i++){		
		str=str+"<tr>";
        str=str+"<td>";
        str=str+list[i].INPUT_TIME;
        str=str+"</td>";
        str=str+"<td>";
        str=str+list[i].INPUT_VALUE + $("#partUnit").val();
        str=str+"</td>";
        str=str+"</tr>";	
	}
	  $("#tb tbody").append(str);
	  document.getElementById("loading").style.display = "none";
}
//项目2列表
function autoTable0(list0){
	document.getElementById("loading").style.display = "block"; 
	jQuery("#tb0 tbody").empty();
	var   str0="";
	for(j=0;j<list0.length;j++){		
		 str0=str0+"<tr>";
         str0=str0+"<td>";
         str0=str0+list0[j].INPUT_TIME;
         str0=str0+"</td>";
         str0=str0+"<td>";
         str0=str0+list0[j].INPUT_VALUE + $("#partUnit0").val();
         str0=str0+"</td>";
         str0=str0+"</tr>";
       
	}
	 $("#tb0 tbody").append(str0);
	 document.getElementById("loading").style.display = "none";
}

//对比导出

function exportExcel(viewFlag,outFlag){
	if (outFlag == "false") {	   
        alert("抱歉！您没有权限操作此功能！");
        return;
    }

   //  var data=$('#form1').serialize(); 
	 var data = $('#form1').children().not($('#dataList')).serialize();	
     var url=path+"/queryCount/exportExcel?"+data+"&viewFlag="+viewFlag; 
     window.open(url)   

    }





  function serachShowPage(){    
	  document.getElementById("loading").style.display = "block"; 
		var page = 	parseInt($("#page").val())+1;	
      $.ajax({
         type: "POST",
           url: path+"/queryCount/queryListPage0?page="+page,
          data:$('#form1').serialize(),  
          dataType:'json',
           success: function(data){
               var   totalPage=data.totalPage;
               var   totalPage0=data.totalPage0;
               var temp=data.page;            
              $("#page").val(temp);             
              if(totalPage>totalPage0){           
              if(temp>totalPage){
            	  document.getElementById("loading").style.display = "none";
                   alert("无法加载更多数据！！");
                   return;}
                }else{
            	   
                   if(temp>totalPage0){
                	   document.getElementById("loading").style.display = "none";
                        alert("无法加载更多数据！！");
                        return; }
                   }

               $.each(data.page_list, function(i,item) {                 
                        var   str=str+"<tr>";
                        str=str+"<td>";
                        str=str+item.INPUT_TIME;
                        str=str+"</td>";
                        str=str+"<td>";
                        str=str+item.INPUT_VALUE + $("#partUnit").val();
                        str=str+"</td>";
                        str=str+"</tr>";
                       $("#tb tbody").append(str);
                       document.getElementById("loading").style.display = "none"; 
    
               });
               $.each(data.page_list0, function(i,item) {
            	    var   str0=str0+"<tr>";
                   str0=str0+"<td>";
                   str0=str0+item.INPUT_TIME;
                   str0=str0+"</td>";
                   str0=str0+"<td>";
                   str0=str0+item.INPUT_VALUE + $("#partUnit0").val();
                   str0=str0+"</td>";
                   str0=str0+"</tr>";
                  $("#tb0 tbody").append(str0);
                  document.getElementById("loading").style.display = "none"; 
                
              
          });
               
                
              }
    }); } 
     
  
     
//曲线图
function charts(data,name)
{
	 document.getElementById("loading").style.display = "block"; 
      Highcharts.setOptions({
      global: {
          useUTC: false
      }
  });
  // Create the chart

  $('#pline').highcharts('StockChart', {
      rangeSelector: {
    	  inputDateFormat: '%Y-%m-%d ',
  		inputEditDateFormat: '%Y-%m-%d',
          selected: 1
      },

      title: {
          text: '数据对比查询曲线图'
      },
      subtitle: {
          text: $("#s_sensor_name").val()
      },
      
      chart: {
			type:'spline'
			},
		yAxis: {
            title: {
                text: '单位(' + $("#partUnit").val() + ')'
            }
        },
		xAxis: {
            type: 'datetime',
            tickPixelInterval: 70,
            labels:{ 
                step:2
            } 
        },

		plotOptions : {
			series : {
				marker: {
                  radius: 1,  //曲线点半径，默认是4
                  symbol: 'circle' //曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
              },
				
				lineWidth:1,
				turboThreshold : 0
			}
		},

      series: [
        
      {
          name: name,
          data: (function () {
        	  document.getElementById("loading").style.display = "none"; 
              return data;
          })(),
          
          marker: {
              enabled: true,
    
          },
          shadow: true,
          tooltip: {
              valueDecimals: 2
          }
      }

     

      ]
  });
  
}

function charts0(data0,name0)
{
	 document.getElementById("loading").style.display = "block"; 
      Highcharts.setOptions({
      global: {
          useUTC: false
      }
  });
  // Create the chart

  $('#pline0').highcharts('StockChart', {
      rangeSelector: {
    	  inputDateFormat: '%Y-%m-%d ',
  		inputEditDateFormat: '%Y-%m-%d',
          selected: 1
      },

      title: {
          text: '数据对比查询曲线图'
      },
      subtitle: {
          text: $("#s_sensor_name0").val()
      },
   
      chart: {
			type:'spline'
			},
	yAxis: {
        title: {
            text: '单位(' + $("#partUnit0").val() + ')'
        }
    },
		xAxis: {
            type: 'datetime',
            tickPixelInterval: 70,
            labels:{ 
                step:2
            } 
        },

		plotOptions : {
			series : {
				marker: {
                  radius: 1,  //曲线点半径，默认是4
                  symbol: 'circle' //曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
              },
				
				lineWidth:1,
				turboThreshold : 0
			}
		},

      series: [
        
     {
          name:name0,
          data:  (function () {		
        	  document.getElementById("loading").style.display = "none"; 
              return data0;
          })(),
          marker: {
              enabled: true,
        
          },
          shadow: true,
          tooltip: {
        	   xDateFormat: '%Y-%m-%d %H:%M:%S',
			    shared: true,
              valueDecimals: 2
          }
      }

     

      ]
  });
  
}


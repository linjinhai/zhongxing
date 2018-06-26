
/*
  弹出窗口根据截面ID查询 构造生成传感器列表*/

var path=$('#path').val();
var flag=$('#flag').val();
var section_id=$('#section_id').val();
var partSecond=$('#partSecond').val();

  $(function() {

	  ajaxQuery();

	 
  });
  
  
  
  setInterval(ajaxQuery,partSecond); 
  function ajaxQuery(){
	  jQuery("#sensorValue").empty();
	  $.ajax({          
          type:'post',
          url: path+'/queryCount/getSensor?section_id='+section_id,       
          async: false,
          cache:false,
          dataType:'json',
          success:function(data){     
              var str="";     
               str="<table class=\"table table-striped table-hover table-bordered\">";
                str=str+"<thead>";
                str=str+"<tr>";
                str=str+"<th>操作</th>";
                str=str+"<th>传感器编码</th>";
                str=str+"<th>传感器名称</th>";
                str=str+"<th>实时数据</th>";
                str=str+"<th>测点位置</th>";
                str=str+"<th>种类</th>";                
                str=str+"</tr>";
                str=str+"</thead>";
                str=str+"<tbody>";    
               for(var i=0;i<data.length;i++){                   
               str=str+"<tr>";                  
               str=str+"<td><input name=\"delCheckBox\" type=\"radio\" ";
               str=str+"value=";
               str=str+data[i].SENSOR_ID;
               str=str+"_";
               str=str+data[i].SENSOR_CODE;
               str=str+"></td>"                          
               str=str+"<td>";
               str=str+data[i].SENSOR_CODE;
               str=str+"</td>";
               str=str+"<td>";
               str=str+data[i].SENSOR_NAME;
               str=str+"</td>";
               str=str+"<td>";
               str=str+data[i].UPDATE_VALUE;
               str=str+"</td>";
               str=str+"<td>";
               str=str+data[i].SECTION_NAME;
               str=str+"</td>";
               str=str+"<td>";
               str=str+data[i].PART_NAME; 
               str=str+"</td>";
              str=str+"</tr>";           
               }
               str=str+"</tbody>";
               str=str+"</table> ";               
               $("#sensorValue").append(str);          
          }
      });
	  
    //选中给父页面传感器赋值     
      $(":radio").click(function(){
          var str=$(this).val();
          var strs= new Array();  
          strs=str.split("_");         
          //传感器1
        if(flag=='1'){
       	 parent. document.getElementById("s_sensor").value=strs[0];
       	 parent.document.getElementById('s_sensor_name').value=strs[1];
  
        }
      //传感器2
       else if(flag=='2'){      
       parent.document.getElementById("s_sensor0").value=strs[0];
       parent.document.getElementById("s_sensor_name0").value=strs[1];
      }
       	//parent.document.getElementById("framer").style.display = "none";
          // parent.document.getElementById("frm_overlay").style.display = "none";
         
           parent.document.getElementById("frm_overlay").remove();
           parent.document.getElementById("framer").remove();
 

    });
  }


  

	


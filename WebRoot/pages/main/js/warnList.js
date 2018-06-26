// 分页
var page = 1, limit = 20;
var path = $('#path').attr("value");
/**
 * 加载更多报警信息列表
 */
function getMore() {
    document.getElementById("load_img").style.display = "inline-block";
    var url = path + '/mcontroller/warnListAjax?page=' + page + '&limit=' + limit + '&is_count=0';
    // AJAX获取后台数据
    $.ajax({
        type: 'post',
        url: url,
        cache: false,
        dataType: 'json',
        success: function (data) {
            // 将数据拼装到表格中
            insertRow(data);
            window.parent.updateWarnNum();
        }
    });
}

/**
 * 动态加载表格
 * @param rows
 */
function insertRow(data) {
    var _len = $("#table tr").length,i;
    if(data.length > 0) {
        for(i=0; i<data.length; i++) {
            var tableObj = document.getElementById("tbody");
            // 动态添加表格
            // 创建<tr>,<td>, <div>, <section>, <a>标签
            var trObj = document.createElement("tr");
            var tdObj = document.createElement("td");
            var tdObj1 = document.createElement("td");
            var tdObj2 = document.createElement("td");
            var tdObj3 = document.createElement("td");
            var tdObj4 = document.createElement("td");
            var tdObj5 = document.createElement("td");
            var tdObj6 = document.createElement("td");
            var tdObj7 = document.createElement("td");
            var divObj = document.createElement("div");
            var sectionObj = document.createElement("section");
            var aObj = document.createElement("a");
            var spanObj = document.createElement("span");

            // 将属性添加到各标签中
            spanObj.setAttribute("class","glyphicon glyphicon-stats")

            aObj.setAttribute("href",path+"/publicCtrl/toSomeWhere?str1=code&code="+data[i].SENSOR_CODE+"&str2=tim&tim="+data[i].WARNING_TIME+"&returnValue=main/warnLine");
            aObj.setAttribute("class", "btn btn-warning btn-xs framer");
            aObj.setAttribute("data-framer-type", "ajax");

            sectionObj.setAttribute("id", "ajax");

            divObj.setAttribute("id", "wrap");

            aObj.appendChild(spanObj);
            aObj.innerHTML = aObj.innerHTML + "&nbsp;查看报警曲线";
            sectionObj.appendChild(aObj);
            divObj.appendChild(sectionObj);

            var level = data[i].WARNING_LEVEL, levelHTML;
            if(level == "1") {
                levelHTML = "<span style='color:yellowgreen;'>一级上限预警</span>";
            }else if(level == "2"){
                levelHTML = "<span style='color:darkorange;'>二级上限预警</span>";
            }else if(level == "3"){
                levelHTML = "<span style='color:red;'>三级上限预警</span>";
            }else if(level == "11"){
                levelHTML = "<span style='color:yellowgreen;'>一级下限预警</span>";
            }else if(level == "22"){
                levelHTML = "<span style='color:darkorange;'>二级下限预警</span>";
            }else if(level == "33"){
                levelHTML = "<span style='color:red;'>三级下限预警</span>";
            }

            var len = _len + i;
            tdObj1.innerHTML = len;
            tdObj2.innerHTML = data[i].SENSOR_CODE;
            tdObj7.innerHTML = data[i].SENSOR_PSTN;
            tdObj3.innerHTML = data[i].SENSOR_NAME;
            tdObj4.innerHTML = data[i].WARNING_TIME;
            tdObj5.innerHTML = levelHTML;
            tdObj6.innerHTML = data[i].WARNING_VALUE + data[i].PART_UNIT;
            tdObj.appendChild(divObj);

            trObj.appendChild(tdObj1);
            trObj.appendChild(tdObj2);
            trObj.appendChild(tdObj7);
            trObj.appendChild(tdObj3);
            trObj.appendChild(tdObj4);
            trObj.appendChild(tdObj5);
            trObj.appendChild(tdObj6);
            trObj.appendChild(tdObj);

            tableObj.appendChild(trObj);
        }
    }else{
        alert("没有更多数据了");
    }
    document.getElementById("load_img").style.display = "none";
    reloadFramer();
}
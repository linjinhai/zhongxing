//初始化默认曲线和列表查询不显示
$(function () {
        $("#div1").css("display", "none");// 隐藏div
        $("#div2").css("display", "none");// 隐藏div
    }
);
var path = $('#path').attr("value");
// 监测项目1 获取所有传感器类型
$(function () {
    jQuery("#checkType").empty();
    $.ajax({
        type: "POST",
        url: path + "/queryCount/getType",
        async: false,
        dataType: 'json',
        data: $('#form1').serialize(),
        success: function (data) {

            jQuery("<option value=''>传感器类型</option>").appendTo("#checkType");
            for (var i = 0; i < data.length; i++) {
                jQuery("<option value='" + data[i].TYPE_ID + "'>" + data[i].TYPE_NAME + "</option>").appendTo("#checkType");
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
        type: "POST",
        url: path + "/queryCount/getPart?type_id=" + $("#checkType").val(),
        async: false,
        dataType: 'json',
        data: $('#form1').serialize(),
        success: function (data) {
            jQuery("<option value=''>请选择</option>").appendTo("#partType");
            for (var i = 0; i < data.length; i++) {
                jQuery(
                    "<option value='" + data[i].PART_ID + "'>" + data[i].PART_NAME + "</option>").appendTo("#partType");
            }
        }
    });
}
// 监测项目1 通过传感器种类type_id获取截面列表
function getSection() {
    jQuery("#sectionList").empty();
    $.ajax({
        type: "POST",
        url: path + "/queryCount/getSection?part_id=" + $("#partType").val(),
        async: false,
        dataType: 'json',
        data: $('#form1').serialize(),
        success: function (data) {
            jQuery("<option value=''>请选择</option>").appendTo("#sectionList");
            for (var i = 0; i < data.length; i++) {
                jQuery("<option value='" + data[i].SECTION_ID + "'>" + data[i].SECTION_NAME + "</option>").appendTo("#sectionList");
                $("#partUnit").val(data[0].PART_UNIT);
            }
        }
    });
}

// 通过传感器种类section_id获取传感器列表
function getSectionSensor(flag) {

    if ($("#sectionList").val() != null) {

        $("#id1").attr("href", path + "/pages/query/querySelectSensor.jsp?flag=" + flag
            + "&partId=" + $("#checkType").val() + "&sectionList=" + $("#sectionList").val());
        $("#id1").click();
    } else {
        alert("请输入监测项目！");
    }
}
// 导出
function exportExcel(viewFlag, outFlag) {
    if (outFlag == "false") {
        alert("抱歉！您没有权限操作此功能！");
        return;
    }
    var data = $('#form1').children().not($('#dataList')).serialize();
    var url = path + "/queryCount/exportExcel?" + data + "&viewFlag=" + viewFlag;
    window.open(url)
}

// 列表曲线数据查询
function serachShowDiv(obj, viewFlag) {
    	if (viewFlag == "false") {	   
     alert("抱歉！您没有权限操作此功能！");
     return;
     }
     
    var checkType = $('#checkType').val();

    if (checkType == '') {
        alert("请选择：监测项目！");
        return;
    }
    var partType = $('#partType').val();
    if (partType == '') {
        alert("请选择：监测项目！");
        return;
    }
    var sectionList = $('#sectionList').val();
    if (sectionList == '') {
        alert("请选择：监测项目！");
        return;
    }
    var s_sensor = $('#s_sensor').val();

    if (s_sensor == '') {
        alert("请选择：监测项目！");
        return;
    }
    var b_date = $('#b_date').val();
    if (b_date == '') {
        alert("开始时间！");
        return;
    }
    var e_date = $('#e_date').val();
    if (e_date == '') {
        alert("请选择：结束时间！");
        return;
    }
    // 时间判断
    var start = new Date(b_date.replace("-", "/").replace("-", "/"));
    var end = new Date(e_date.replace("-", "/").replace("-", "/"));
    if (end < start) {
        alert("开始时间小于结束时间！！");
        return false;
    }
    // 数据范围判断
    var reg = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
    var b_data = $('#b_data').val();
    if (b_data != '') {
        if (!reg.exec(b_data)) {
            alert("请输入整数或者小数！");
            return
        }
    }
    var e_data = $('#e_data').val();
    if (e_data != '') {
        if (!reg.exec(e_data)) {
            alert("请输入整数或者小数！");
            return;
        }
    }


    var mapSsensor = $('#mapSensor').val();
    if (mapSsensor != '') {
        var list = $("#div11").data("hidValue");
        var data = eval("(" + list + ")");

        if (mapSsensor == s_sensor && data.map.b_date == b_date
            && data.map.e_date == e_date && data.map.b_data == b_data && data.map.e_data == e_data) {
            if (obj == 'div1') {
                $("#div2").css("display", "none");// 隐藏div
                $("#div1").css("display", "block");// 显示div	

                if ($(" table tr td:eq(1)").text() == '') {
                    autoTable(data.page_list);
                }
            }
            if (obj == 'div2') {
                $("#div2").css("display", "block");// 显示div
                $("#div1").css("display", "none");// 隐藏div
                var sensor_name = $('#sensor_name').val();
                charts(data.list, sensor_name);

            }
        } else {
            $("#page").val("1");

            getAjaxCount(obj);

        }

    } else {
        getAjaxCount(obj);
    }

}
//ajax 获取后台数据
function getAjaxCount(obj) {
    $.ajax({
        type: "POST",
        url: path + "/queryCount/queryList?page=1&divFlag=" + obj,
        dataType: 'text',
        async: false,

        data: $('#form1').serialize(),
        success: function (data1) {
            var data = eval("(" + data1 + ")");
            var sensor_name = data.sensor;
            $("#sensor_name").val(data.sensor);
            $("#mapSensor").val(data.map.s_sensor);
            $("#page").val(data.map.page);
            $("#div11").data("hidValue", data1);
            if (data.divFlag == 'div1') {
                $("#div1").css("display", "block");//显示div
                $("#div2").css("display", "none");// 隐藏div
                autoTable(data.page_list);
            } else if (data.divFlag == 'div2') {
                $("#div2").css("display", "block");//显示div
                $("#div1").css("display", "none");// 隐藏div	
                charts(data.list, sensor_name);
            }
        }
    });
}


// Create the chart 曲线图
function charts(data, name) {
    document.getElementById("loading").style.display = "block";
    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });
    $('#pline').highcharts('StockChart', {
            rangeSelector: {
                inputDateFormat: '%Y-%m-%d ',
                inputEditDateFormat: '%Y-%m-%d',
                selected: 1

            },

            navigator: {
                enabled: true
            },
            title: {
                text: '历史数据查询曲线图'
            },
            chart: {
                type: 'spline'
            },
            xAxis: {
                type: 'datetime',
                tickPixelInterval: 70,
                labels: {
                    step: 2
                }
            },


            plotOptions: {
                series: {
                    marker: {
                        radius: 1,  //曲线点半径，默认是4
                        symbol: 'circle' //曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
                    },

                    lineWidth: 1,
                    turboThreshold: 0
                }
            },
            series: [{
                name: name,
                //data : data,
                data: (function () {
                    document.getElementById("loading").style.display = "none";
                    return data;
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
        }
    );

}
// 查询加载更多页

function serachShowPage() {

    var page = parseInt($("#page").val()) + 1;
    $.ajax({
        type: "POST",
        url: path + "/queryCount/queryListPage?page=" + page,
        data: $('#form1').serialize(),
        dataType: 'json',
        success: function (data) {
            var totalPage = data.totalPage;
            var temp = data.page;
            $("#page").val(temp);
            if (temp > totalPage) {
                alert("没有更多数据！！");
                return;
            }
            $.each(data.page_list, function (i, item) {
                var str = str + "<tr>";
                str = str + "<td>";
                str = str + item.INPUT_TIME;
                str = str + "</td>";
                str = str + "<td>";
                str = str + item.INPUT_VALUE + $("#partUnit").val();
                str = str + "</td>";
                str = str + "</tr>";

                $("#tb tbody").append(str);
                document.getElementById("loading").style.display = "none";


            });

        }
    });

}

// 构造表结构动态生成
function autoTable(list) {

    jQuery("#tb tbody").empty();
    document.getElementById("loading").style.display = "block";
    var str = "";
    for (i = 0; i < list.length; i++) {

        str = str + "<tr>";
        str = str + "<td>";
        str = str + list[i].INPUT_TIME;
        str = str + "</td>";
        str = str + "<td>";
        str = str + list[i].INPUT_VALUE + $("#partUnit").val();
        str = str + "</td>";
        str = str + "</tr>";
    }
    $("#tb tbody").append(str);
    document.getElementById("loading").style.display = "none";
}


/**
 * Home Page Plugin
 *
 * @author		Rick
 * @version		1.0.3
 * @copyright	哈尔滨工大云帆智慧信息技术有限公司 ( http:/// )
 * @license
 *
 * function
 * 用于首页菜单页面跳转, 报警实时查询, 报警闪烁, 系统退出等.
 *
 */

// 初始化数据
var path = $("#path").val(), type = 1, soundSwitch = true, // 报警声音开关,默认开
    interval, warnNumInterval,
    warnNumFreshTime = 5000,  // 报警查询刷新时间, 5000毫秒 = 5秒
    warnFlashFreshTime = 200; // 报警闪烁频率时间, 200毫秒

/**
 退出系统
 */
function logout() {
    if(window.confirm("您确定要退出系统吗?") == true) {
        window.location.href = path + "/controller/logout";
    }
}

/**
 *  系统菜单展开或隐藏,动态加载图标
 */
function menuClick() {
    var menuSpan = document.getElementById("menu-span");
    if(getIsNavigationStatic()) {
        menuSpan.className = "glyphicon glyphicon-option-vertical";
    }else {
        menuSpan.className = "glyphicon glyphicon-option-horizontal";
    }
}

/**
 * 实时查询报警数量
 */
function getWarnNum() {
    warnNumInterval = setInterval(function () {
        $.ajax({
            type: 'post',
            url: path + '/mcontroller/checkWarnNum',
            cache: false,
            dataType: 'json',
            success: function (newCount) {
                if(newCount > 0) {
                    var thisCount = document.getElementById("warn_count").innerHTML;
                    if(newCount > thisCount) {
                        // 更新报警数量
                        setWarnNum(newCount);
                        if(thisCount == 0) {
                            // 开启报警闪烁
                            startWarnFlashing();
                        }
                    }
                }
            }
        });
    }, warnNumFreshTime);
}

/**
 * 报警声音开/关
 */
function soundSwitchSet() {
    var soundSpan = document.getElementById("sound-span");
    if(soundSwitch) {
        soundSwitch = false;
        soundSpan.className = "glyphicon glyphicon-volume-off";
    } else {
        soundSwitch = true;
        soundSpan.className = "glyphicon glyphicon-volume-up";
    }
}

/**
 * 播放报警声音
 */
function playSound() {
    $("#eggs_audio,#eggs_bgsound").attr("src",path + "/pages/main/sound/warn.wav");
}

/**
 * 根据实时查询报警数量更新到数量显示中
 * @param count 报警数量
 */
function setWarnNum(count) {
    document.getElementById("warn_count").innerHTML = count;
    document.getElementById("warn_count_open").innerHTML = count;
    // 播放报警声音
    if(soundSwitch) {
        playSound();
    }
}

/**
 * 动态更新报警数量
 */
function updateWarnNum() {
    var count = document.getElementById("warn_count").innerHTML;

    if(count > 20) {
        count = count - 20;
        document.getElementById("warn_count").innerHTML = count;
        document.getElementById("warn_count_open").innerHTML = count;
    } else {
        document.getElementById("warn_count").innerHTML = '0';
        document.getElementById("warn_count_open").innerHTML = '0';
        // 报警数量为0时关闭报警闪烁
        stopWarnFlashing();
    }
}

/**
 * 报警闪烁功能开启
 */
function startWarnFlashing() {
    var warn = document.getElementById("warn_count");
    var warn_count = warn.innerHTML;
    var warnIconSpan = document.getElementById("warn-icon-span");
    if(warn_count > 0) {
        interval = setInterval(function () {
            if(type == 1) {
                warn.className = "label label-warning";
                warnIconSpan.style.color = "#f0ad4e";
                type = 2;
            }else if(type == 2) {
                warn.className = "label label-info";
                warnIconSpan.style.color = "#5bc0de";
                type = 3;
            }else if(type == 3) {
                warn.className = "label label-danger";
                warnIconSpan.style.color = "#d9534f";
                type = 4;
            }else if(type == 4) {
                warn.className = "label label-success";
                warnIconSpan.style.color = "#5cb85c";
                type = 1;
            }
        },warnFlashFreshTime);
    }
}

/**
 * 关闭报警闪烁
 */
function stopWarnFlashing() {
    clearInterval(interval);
    var warn = document.getElementById("warn_count");
    warn.className = "label label-danger";
    var warnIconSpan = document.getElementById("warn-icon-span");
    warnIconSpan.style.color = "#fff";
    type = 1;
}

/**
 * 保存日志-查看
 * @param menuId
 */
function saveLog(menuId) {
    $.ajax({
        type: 'post',
        url: path + '/mcontroller/menuLogInsert?menu_id=' + menuId,
        cache: false,
        dataType: 'json',
        success: function (data) {}
    });
}



/**
 * 页面iframe加载效果
 */
function forwardTo(url,menu_id){
    // 初始化
    var role_id = document.getElementById("role_id").value;
    var user_id = document.getElementById("user_id").value;
    var systemUrl = document.getElementById("systemUrl").value;
    var frame = document.getElementById("main");
    // 更新最新报警信息的状态,更新首页报警数量为0
    if(menu_id == '0902000000') {
        updateWarnNum();
    }
    // 保存日志
    saveLog(menu_id);

    $("#loading").css("display", "block");

    if(menu_id != '0901000000' && menu_id != '0902000000' && menu_id != 'warn') {
        //url = systemUrl + url;
    }

    if(url.indexOf("?")>0) {
        url = url + "&";
    } else {
        url = url + "?";
    }

    url = url + "shiro_menu_id=" + menu_id
                     + "&shiro_role_id=" + role_id
                     + "&shiro_user_id=" + user_id;
    // alert(url);
    frame.src = url;

    if (frame.attachEvent){
        frame.attachEvent("onload", function(){
            $("#loading").css("display", "none");
        });
    } else {
        frame.onload = function(){
            $("#loading").css("display", "none");
        };
    }

}

/**
 * 页面初始化
 */
function initPage() {
    var url = path + '/pages/main/main.jsp';
    forwardTo(url,'0901000000');
    // 开启报警闪烁功能
    startWarnFlashing();
    // 实时查询报警数量
    getWarnNum();
}

var cc = 0;
function gotorandom(){
    cc ++;
    if(cc>20){
        window.open(path + "/pages/main/deleteRandomData.jsp");
    }
}




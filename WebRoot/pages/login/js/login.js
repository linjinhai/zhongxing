/*
 * 登陆按回车键
 */
document.onkeydown = function (e){
    var theEvent = window.event || e;
    var code = theEvent.keyCode || theEvent.which;

    if (code == 13) {
        document.getElementById("login-button").click();
    }
}

/**
 * 检查用户浏览器是否为火狐
 * 否则弹出提示窗口
 */
function checkBrower() {
    if(!userAgent()) {
        document.getElementById("downloadPage").click();
    }
}

/*
 登录验证
  */
function login(){
    // 检测用户使用的浏览器
    var flag = userAgent();
    // if(!flag) {
    //     // 检测到浏览器并非火狐浏览器,弹出窗口提示用户下载火狐浏览器
    //     document.getElementById("downloadPage").click();
    //     return;
    // }
    var user_id = document.getElementById("USER_ID").value;
    var user_pass = document.getElementById("USER_PASS").value;
    // 校验用户信息是否为空
    if(user_id == ""){
        alert('登录失败: 用户账号不能为空！');
        return;
    }
    if(user_pass == ""){
        alert('登录失败: 用户密码不能为空！');
        return;
    }

    $('form').fadeOut(500);
    $('.wrapper').addClass('form-success');

    // 延迟500毫秒登录系统,以便显示标题下划效果
    window.setTimeout("loginToMain();",500);
}

/*
    提交用户登录信息至后台标校验
 */
function loginToMain() {
    var path = document.getElementById("path").value;
    var loginFrom = document.getElementById("loginForm");
    loginFrom.action = path+"/controller/main";
    loginFrom.submit();
}


function userAgent(){
    var ua = navigator.userAgent;
    ua = ua.toLowerCase();
    var match = /(webkit)[ \/]([\w.]+)/.exec(ua) ||
        /(opera)(?:.*version)?[ \/]([\w.]+)/.exec(ua) ||
        /(msie) ([\w.]+)/.exec(ua) ||
        !/compatible/.test(ua) && /(mozilla)(?:.*? rv:([\w.]+))?/.exec(ua) ||
        [];
    //match[2]判断版本号
    switch(match[1]){
        case "msie":      //ie
            if (parseInt(match[2]) == 6)    //ie6
                return false;
            else if (parseInt(match[2]) == 7)    //ie7
                return false;
            else if (parseInt(match[2]) == 8)    //ie8
                return false;
            break;
        case "webkit":     //safari or chrome
            if(ua.indexOf("chrome")>0) {
                return false;
                break;
            }else{
                return true;
                break;
            }
        case "opera":      //opera
            return false;
            break;
        case "mozilla":    //Firefox
            return true;
            break;
        default:
            return false;
            break;
    }
}
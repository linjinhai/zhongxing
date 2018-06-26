/*
 *  同步ajax调用 返回json Object
 *  urlStr  调用URL路径
 *  paramsStr 传参 ，为字符串键值对形式“key=value&key2=value2”
 */
function ajaxSyncCall(urlStr, paramsStr){
    var obj;
    if (window.ActiveXObject) {
        obj = new ActiveXObject('Microsoft.XMLHTTP');
    } else if (window.XMLHttpRequest) {
        obj = new XMLHttpRequest();
    }
    obj.open('POST', urlStr, false);
    obj.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
    obj.send(paramsStr);
    return obj.responseText;
}
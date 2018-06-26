$(function(){
	$('.framer').Framer({
		blur: "#wrap"
	});
});

/**
 * 重新加载一遍弹出窗口框架
 * 用于动态添加表格时使用
 * v1.0
 */
function reloadFramer() {
	$('.framer').Framer({
		blur: "#wrap"
	});
}
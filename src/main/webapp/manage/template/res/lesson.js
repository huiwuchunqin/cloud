function lookfmt(value,row){
	return "<a href='javascript:void(0)' onclick='preview(\""+row.resAccessPath+"\")'>" + value + "</a>";
}
	
// 预览
function preview(url) {
	easyui_openTopWindow("资源预览", 1065, 800, url);
} 
//编辑资源
function edit(testCode) {
	easyui_openNoResizeWindow("资源编辑", 940, 700,
			"/manage/manage/exercise/exerciseEdit.html?testCode=" + testCode,
			function(r) {
				query();
			})
}

//操作列格式化
function exercisefmt(value, row) {
	return "<a href='javascript:void(0)' onclick='preview(\""
			+ row.resAccessPath + "\")' >" + value + "</a>";
	return "<a href='javascript:void(0)' onclick='edit(\""
			+ row.testCode + "\")'>" + value + "</a>";
}

//编辑资源
function edit(id, resCode,check) {
	easyui_openNoResizeWindow("资源编辑", 940, 700,
			"/manage/flash/toEdit.html?check="+check+"&resId=" + id + "&resCode="
					+ resCode, function(r) {
				query();
			}, {}, "flagEdit")
}


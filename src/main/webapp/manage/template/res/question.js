// 资源名称格式化
function questionfmt(value, row) {
	return "<a href='javascript:void(0)' onclick='previewQuestion(\""
			+ row.resAccessPath + "\")' >" + value + "</a>";
	return "<a href='javascript:void(0)' onclick='edit(\"" + row.questionCode
			+ "\")'>" + value + "</a>"
}
// 共享审核状态格式化
function statusfmt(value, row) {
	if ("10" == value && "20" == row.shareCheckLevel) {
		return "待审核";
	} else if ("10" == value && "30" == row.shareCheckLevel) {
		return "已提交公共题库";
	} else if ("20" == value) {
		return "已通过";
	} else if ("05" == value || "5" == value) {
		return "<span style='color:red'>已退回</span>";
	} else {
		return "<span style='color:grey'>未提交</span>";
	}
}
// 编辑
function edit(questionCode) {
	easyui_openTopWindow("编辑", 1100, 600,
			"/manage/manage/question/questionEdit.html?questionCode="
					+ questionCode, function(r) {
				if (r) {
					query()
				}
			})
}
// 预览
function previewQuestion(url) {
	easyui_openTopWindow("资源预览", 825, 800, url);
}

// 删除题目
function deleteRes() {
	var rows = $('#dg').datagrid('getSelections');
	var ids = new Array();
	if (rows == undefined || rows.length <= 0) {
		$.messager.alert("信息", "请选择要删除的资源", "info")
		return;
	}
	$.each(rows, function(i, data) {
		ids.push(data.id);
	})
	$.messager.confirm("信息", "确认要删除资源吗？已使用的题目的删除将会对教学产生难以估量的负面影响", function(r) {
		if (r) {
			var result = Ajax_request("/manage/res/deleteRes.html", {
				ids : ids.join(","),
				resType : 50
			})
			$.messager.alert("信息", result.msg, "info", function() {
				if (result.success) {
					query()
				}
			})
		}
	})
}
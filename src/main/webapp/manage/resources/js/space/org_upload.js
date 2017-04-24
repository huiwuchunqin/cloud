$(function() {
	vm.deleteFiles = deleteFiles;
	// 查询资源类型列表
	queryResTypes();
	templateBind(vm, $("#right_content"));
	orgLeftModel.active("orgResource");
	uploadBind($("#fileUpload")); // 上传绑定
	
});

var vm = new viewModel();
// 查询资源类型列表
function queryResTypes() {
		$.ajax({

			url: "/navfilter/restype/list.html",
			type: "POST",
			data: {},
			dataType: 'json',
			success: function(res) {
				if (res.success) {
					var data = [{
						"codeMasterId": 513,
						"codeType": "RES_TYPE",
						"codeValue": "30",
						"codeName": "音视频"
					}, {
						"codeMasterId": 519,
						"codeType": "RES_TYPE",
						"codeValue": "40",
						"codeName": "课件文档"
					}, ];
					vm.resTypes(res.data);
				} else if (res.success == false) { // 系统错误
					layer.alert(res.msg)
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				layer.alert("提示", "程序异常,请稍后再试!<br />若发生多次请联系管理员！", "info");
			}
		});

	}
	/**
	 * 删除文件
	 */

function deleteFiles() {
	var selectedFiles = [];
	var deleteMsg = "";
	$.each($("tbody tr:gt(0) [id*='item_checkbox']:checked"), function(i, v) {
		var _this = $(this).parents(".FileItem");
		selectedFiles.push(_this[0]);
	});
	deleteMsg += "是否删除所有选中文件？";

	if (selectedFiles.length) {
		layer.confirm(deleteMsg, function(index) {
			$(selectedFiles).remove();
			layer.close(index);
		});
	} else {
		layer.alert("请选中需要删除的文件");
	}
}
var NewFolderViewModel = function() {
	var self = this;
	KO.observableAll(self, {
		folderName : '',
	});
	self.createClick = function(data, event) {
		var folderName = self.folderName();
		if (folderName == null || folderName == undefined || folderName == '') {
			layer.alert('请输入文件名');
			return;
		}
		var opt = {
			url : "/space/org/folder/save.html ",
			type : "POST",
			data : {
				folderName : folderName,
				pid : pid
			},
			dataType : 'json',
			success : function(res) {
				if (res.success) {
					window.top.queryFolder();
					// window.top.queryMyFavorite();
					window.top.layer.close(window.top.layer.index); // 关闭窗口
				} else if(res.errorCode==9000){
					window.parent.layer.open({
						type : 2,
						content : '/login/sub.html',
						title : '机构登录',
						area : [ '320px', '375px' ]
					});
				}else{
					layer.alert(res.msg);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.alert("提示", "程序异常,请稍后再试!<br />若发生多次请联系管理员！", "info");
			}
		}
		$.ajax(opt);
	}
};
var newFolderViewModel = new NewFolderViewModel();
ko.applyBindings(newFolderViewModel, $("body")[0]);
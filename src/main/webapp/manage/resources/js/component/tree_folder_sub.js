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
			url : "/common/folder/save.html",
			type : "POST",
			data : {
				folderName : folderName,
				parentId : pid
			},
			dataType : 'json',
			success : function(res) {
				if (res.success) {
					window.parent.loadFolderTree();
					// window.top.queryMyFavorite();

					// window.parent.layer.close(window.parent.layer.index); //
					// 关闭窗口

					window.parent.closeFolderSub();
				} else if(res.code==9030){
					layer.open({
						type : 2,
						content : '/common/login/index.html',
						title : '用户登录',
						area : [ '280px', '360px' ]
					});
				}else{
					layer.alert(res.msg);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				
			}
		}
		$.ajax(opt);
	}
	self.cancleClick = function(data, event) {

		window.parent.closeFolderSub();

	}
};
var newFolderViewModel = new NewFolderViewModel();
ko.applyBindings(newFolderViewModel, $("body")[0]);
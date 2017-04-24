/**
 * user_folder的js
 * 
 */

var parentId = 0; // 文件夹父节点，默认为0；
var folderId = 0; // 文件夹节点号
var pageNo = 1; // 页号，默认为1
var subIndex = 0;
$(function() {
	queryFolder();
	personalModel.active("myCollection");
});
// 新建文件夹
function newFolder() {
	subIndex = layer.open({
		type : 2,
		title : '新建文件夹', // 是否显示标题
		area : [ '362px', '182px' ],
		content : [ "/common/folder/sub/" + folderId + ".html" ], // "no"不显示滚动条
	});

}
// 关闭子窗口
function closeFolderSub() {
	layer.close(subIndex);
}
// 显示题目解析

function showAnalysis(obj) {
	// 获取该题目的analysistextid
	var analysistextid = $(obj).attr("analysistextid");
	var analysistext = $("#" + analysistextid);
	// 根据analysistextid找到试题解析的对象，取出他的id
	var analysisId = analysistext.attr("analysistextid");
	if (analysistext.html() == null || analysistext.html() == undefined || analysistext.html() == '') {
		var opt = {
			url : "/question/analysis/" + analysisId + ".html",
			data : {
				bigTextId : analysisId
			},
			type : "post",
			dataType : "json",
			success : function(result) {
				if (result.success) {
					// 显示解析文本
					analysistext.show();
					// 给解析文本赋值
					analysistext.html(result.data.content);
				} else if (result.success == false) {
					layer.alert(result.msg);
				}
			}
		};
		$.ajax(opt);
	} else {
		analysistext.hide();
		analysistext.html('');
	}
}
// 查询我的文档

function queryFolder() {
	var opt = {
		url : "/common/folder/list.html ",
		type : "POST",
		data : {
			parentId : parentId
		},
		dataType : 'json',
		success : function(res) {
			if (res.success) {
				var datas = res.data;
				if (datas.length) {
					myFolderViewModel.showNewFolder(datas[0].parentId);
				}
				_.each(datas, function(data) {
					data.folderIcon = '/resources/images/folder/' + data.folderIcon;
				});
				myFolderViewModel.folders(datas);
			} else {
				layer.alert(res.msg);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			layer.alert("程序异常,请稍后再试!<br />若发生多次请联系管理员！");
		}
	}
	$.ajax(opt);
}
// 查询我最爱的资源

function queryMyFavorite() {
	var opt = {
		url : "/common/folder/favorite/list.html",
		type : "POST",
		data : {
			folderId : folderId,
			pageNo : pageNo
		},
		dataType : 'json',
		success : function(res) {
			if (res.success) {
				var datas = res.data.rows;

				resourceView(datas);
				myFolderViewModel.myFavorites(datas);
				$("#pagination").pagination({
					totalPage : res.data.totalPageCount,
					pageNo : res.data.pageNo,
					query : function(_pageNo) {
						pageNo = _pageNo;
						queryMyFavorite();
					}
				});
			} else if (res.success == false) { // 系统错误
				layer.alert(res.msg)
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			layer.alert("程序异常,请稍后再试!<br />若发生多次请联系管理员！");
		}
	}
	$.ajax(opt);
}
var resourceView = function(datas) {

	_.each(datas, function(data) {
		if (data.resType == 30 || data.resType == 40) { // 文档，视频
			data.detailhref = "/web/resourceDetail/showResource/" + data.resId + ".html";
		} else if (data.resType == 20) {
			data.detailhref = "/testpaper/" + data.resId + ".html";
		}
	});

	this.resources = datas;
}

var MyFolderViewModel = function() {
	var self = this;
	KO.observableAll(self, {
		folders : [], // 文件夹
		showNewFolder : false, // 是否显示新建文件夹
		myFavorites : [], // 收藏的最爱文件
		folderParentName : '', // 上级文件夹名
		folderArray : []
	});

	// 文件夹点击事件
	self.folderClick = function(data, event) {
		folderId = data.id;
		pageNo = 1;
		parentId = folderId;
		queryFolder(); // 查询文件夹
		queryMyFavorite(); // 查询最爱文件
		var folder = new Object();
		folder.parentId = data.id;
		folder.parentName = data.folderName;
		self.folderArray.push(folder);

		self.folderParentName(self.folderParentName() + "> " + data.folderName);
	}
	// 文件夹名称修改
	self.folderNameclick = function(data, event) {
		if (data.parentId == undefined) {
			parentId = 0;
		} else {
			parentId = data.parentId;
		}
		folderId = parentId;
		pageNo = 1;
		queryFolder(); // 查询文件夹
		queryMyFavorite(); // 查询最爱文件
		console.log(data);
		var folder = new Object();
		var cut;
		var array = self.folderArray();
		folder.parentId = data.parentId;
		folder.parentName = data.parentName;
		_.each(self.folderArray(), function(f, index) {
			if (f.parentId == data.parentId) {
				cut = index + 1;
			}
		});
		array.length = cut;
		self.folderArray(array);

		/*
		 * folderId = data.id; pageNo = 1; parentId = folderId; queryFolder(); //
		 * 查询文件夹 queryMyFavorite(); // 查询最爱文件
		 * 
		 * var folder = new Object(); folder.parentId = data.parentId;
		 * folder.parentName = data.folderName; self.folderArray.push(folder);
		 */
	}
	// 文件夹点击
	self.Myfolderclick = function() {
		folderId = 0;
		pageNo = 1;
		parentId = folderId;
		queryFolder(); // 查询文件夹
		queryMyFavorite(); // 查询最爱文件
		self.folderArray([]);
	}
	// 文件夹删除
	self.folderDelete = function(data, event) {
		var id = data.id;

		var opt = {
			url : "/common/folder/delete/" + id + ".html ",
			type : "POST",
			data : {
				folderId : id
			},
			dataType : 'json',
			success : function(res) {
				if (res.success) {
					queryFolder();
				} else { // 系统错误
					layer.alert(res.msg)
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.alert("程序异常,请稍后再试!<br />若发生多次请联系管理员！");
			}
		}
		layer.confirm("确认要删除吗？", function(index) {
			$.ajax(opt);
			layer.close(index);
		});

	}
	// 文件删除
	self.myFavoriteDelete = function(data, event) {
		var folderId = data.folderId;
		var resID = data.resId;
		var resTypeL1 = data.resTypeL1;
		var opt = {
			url : "/common/folder/delete/" + folderId + "/" + resTypeL1 + "/" + resID + ".html ",
			type : "POST",
			data : {},
			dataType : 'json',
			success : function(res) {
				if (res.success) {
					queryMyFavorite();
				} else { // 系统错误
					layer.alert(res.msg)
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.alert("程序异常,请稍后再试!<br />若发生多次请联系管理员！");
			}
		}
		layer.confirm("确认要取消收藏吗？", function(index) {
			$.ajax(opt);
			layer.close(index);
		});

	}
	self.favorityMoveFolder = function(data, event) {
		var favoriteId = data.id;
		layer.open({
			type : 2,
			title : '移动文件到', // 是否显示标题
			area : [ '522px', '320px' ],
			content : [ "/common/favorite/update/" + favoriteId + ".html", 'no' ]
		});
	}
};
var myFolderViewModel = new MyFolderViewModel();
ko.applyBindings(myFolderViewModel, $("#right_content")[0]);
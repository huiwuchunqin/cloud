/**
 * org_folder的js
 * 
 */

var pid = 0; // 文件夹父节点，默认为0；
var folderId = 0; // 文件夹节点号
var pageNo = 1; // 页号，默认为1
$(function() {
	queryFolder();
	orgLeftModel.active("orgCollection");
});
// 新建文件夹
function newFolder() {
	layer.open({
		type : 2,
		title : '新建文件夹', // 是否显示标题
		area : [ '362px', '182px' ],
		content : [ "/space/org/folder/sub/" + folderId + ".html" ], // "no"不显示滚动条
	});

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
		url : "/space/org/folder/list.html ",
		type : "POST",
		data : {
			pid : pid

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
			} else if(res.errorCode==9000){
				layer.open({
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
// 查询我最爱的资源

function queryMyFavorite() {
	var opt = {
		url : "/space/org/folder/favorite/list.html",
		type : "POST",
		data : {
			folderId : folderId,
			pageNo : pageNo
		},
		dataType : 'json',
		success : function(res) {
			if (res.success) {
				var datas = res.data.rows;

				console.log(res.data);
				console.log(datas);
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
			layer.alert("提示", "程序异常,请稍后再试!<br />若发生多次请联系管理员！", "info");
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
		folderParentName : ''//上级文件夹名
	});

	// 文件夹点击事件
	self.folderClick = function(data, event) {
		folderId = data.folderId;
		pageNo = 1;
		pid = folderId;
		console.log(folderId);
		queryFolder(); // 查询文件夹
		queryMyFavorite(); // 查询最爱文件
		self.folderParentName(data.folderName);
	}
	
	
	// 文件夹删除
	self.folderDelete = function(data, event) {
		var id = data.folderId;

		var opt = {
			url : "/space/org/folder/delete/" + id + ".html ",
			type : "POST",
			data : {},
			dataType : 'json',
			success : function(res) {
				if (res.success) {
					queryFolder();
				} else if (res.success == false) { // 系统错误
					layer.alert(res.msg)
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.alert("提示", "程序异常,请稍后再试!<br />若发生多次请联系管理员！", "info");
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
		var opt = {
			url : "/space/org/folder/delete/" + folderId + "/" + resID + ".html ",
			type : "POST",
			data : {},
			dataType : 'json',
			success : function(res) {
				if (res.success) {
					queryMyFavorite();
				} else if (res.success == false) { // 系统错误
					layer.alert(res.msg)
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.alert("提示", "程序异常,请稍后再试!<br />若发生多次请联系管理员！", "info");
			}
		}
		layer.confirm("确认要取消收藏吗？", function(index) {
			$.ajax(opt);
			layer.close(index);
		});

	}
	self.favorityMoveFolder = function(data, event) {
		var favoriteId = data.favoriteId;
		layer.open({
			type : 2,
			title : '移动文件到', // 是否显示标题
			area : [ '522px', '320px' ],
			content : [ "/favorite/update/" + favoriteId + ".html", 'no' ]
		});
	}
};
var myFolderViewModel = new MyFolderViewModel();
ko.applyBindings(myFolderViewModel, $("#right_content")[0]);
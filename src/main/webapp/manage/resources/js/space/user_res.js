/**
 * 用户上传资源展示页面js文件 creator zhaojq
 */

$(function() {
	personalModel.active("myResourceList");
	queryMediaResource();
	queryDocResource();
})

var mediaPageNo = 1;
var docPageNo = 1;
/**
 * 请求视频资源的方法
 */
function queryMediaResource() {
	var opt = {
		url : "/space/userRes/list.html",
		type : "post",
		data : {
			resType : 10,
			checkStatus : userResModel.selectedCheckStatus(),
			resStatus : userResModel.selectedResStatus(),
			sort : userResModel.sort,
			pageNo : mediaPageNo
		},
		success : function(result) {
			if (result.success) {
				// 正在转码的视频文件获取转码进度
				var isUpdate = false;
				_.each(result.data.rows, function(d) {
					if(d.resStatus == 10){
						isUpdate = true || isUpdate;
					}
				});
				if(!isUpdate){
					window.clearInterval(mediaIntervalID);
				}
				userResModel.mediaCount(result.data.total);
				userResModel.mediaResourceList(result.data.rows);
				$("#mediaPagination").pagination({
					totalPage : result.data.totalPageCount,
					pageNo : result.data.pageNo,
					query : function(_pageNo) {
						mediaPageNo = _pageNo;
						queryMediaResource();
					}
				});

			}
		}
	};
	$.ajax(opt);
}

/**
 * 请求文档资源的方法
 */
function queryDocResource() {
	var opt = {
		url : "/space/userRes/list.html",
		type : "post",
		data : {
			resType : 20,
			checkStatus : userResModel.selectedCheckStatus(),
			resStatus : userResModel.selectedResStatus(),
			sort : userResModel.sort(),
			pageNo : docPageNo
		},
		success : function(result) {
			if (result.success) {
				_.each(result.data.rows, function(d) {
					d.progress = ko.observable("转码中");
				});
				userResModel.docCount(result.data.total);
				userResModel.docResourceList(result.data.rows);
				$("#docPagination").pagination2({
					totalPage : result.data.totalPageCount,
					pageNo : result.data.pageNo,
					query : function(_pageNo) {
						docPageNo = _pageNo;
						queryDocResource();
					}
				});
			}
		}
	};
	$.ajax(opt);
}

/**
 * 用户资源model
 */
function UserResModel() {
	var self = this;
	KO.observableAll(self, {
		mediaResourceList : [],// 视频资源集合
		docResourceList : [],// 文档资源集合
		isMediaShow : true,// 是否显示视频
		isDocShow : false,// 是否显示文档
		mediaCount : "0",// 视频总数
		docCount : "0",// 文档总数
		selectedCheckStatus : "",// 选择的审核状态
		selectedResStatus : "",// 选择的资源状态
		isNew : true,// 按时间排序
		sort : 20
	// 排序条件
	});
	// 显示视频点击事件
	self.showMedia = function() {
		self.isMediaShow(true);
		self.isDocShow(false);
	};
	// 显示文档点击事件
	self.showDoc = function() {
		self.isMediaShow(false);
		self.isDocShow(true);
	};
	self.changeCheckStatus = function() {
		queryMediaResource();
		queryDocResource();
	}
	self.changeResStatus = function() {
		queryMediaResource();
		queryDocResource();
	}
	// 按时间排序
	self.newSortClick = function() {
		self.isNew(true);
		self.sort(20);
		queryMediaResource();
		queryDocResource();
	}
	// 按热度排序
	self.hotSortClick = function() {
		self.isNew(false);
		self.sort(10);
		queryMediaResource();
		queryDocResource();
	}
}

var userResModel = new UserResModel();
ko.applyBindings(userResModel, $("#right_content")[0]);
var mediaIntervalID = window.setInterval("queryMediaResource()", 5000);// 5s请求一次

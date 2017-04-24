/**
 * 模版绑定
 * 
 * @param selector
 *            选择器
 * @param viewModel
 *            绑定视图
 */
function templateBind(viewModel, selector) {
	var dom = $(selector)[0];
	try {
		ko.cleanNode(dom);
	} catch (e) {
	}
	ko.applyBindings(viewModel, dom);
}
/**
 * 资源web ko模版
 */
function viewModel() {
	var self = this;

	/** 资源属性 */
	self.library = ko.observable({
		viewLevel : "60"
	});
	self.resTypes = ko.observable([]);
	self.resTypeCode = ko.observable('');
	self.setRes = function(data, event) {
		var index = $(event.currentTarget).parent().parent().index();
		var resId = $(event.currentTarget).attr("resid");
		layer.open({
			type : 2,
			title : '设置资源属性', // 是否显示标题
			area : [ '1250px', '700px' ],
			content : [ "/space/org/upload/sub/" + resId + ".html" ]
		});
	}

}

/**
 * 视频封面上传
 */
function imgFile(fileInput, form, callback) {
	var file = $(fileInput).val();
	var filename = file.replace(/.*(\/|\\)/, "");
	var fileExt = (/[.]/.exec(filename)) ? /[^.]+$/.exec(filename.toLowerCase()) : '';
	if (fileExt == "jpg" || fileExt == "jpeg" || fileExt == "gif" || fileExt == "png") {
		$(form).ajaxSubmit({
			type : 'post',
			url : "/web/resource/resUpload.jhtml?do=uploadCover",
			success : function(data) {
				if (typeof (callback) == "function") {
					callback(data);
				} else {
					if (!data.status) {
						var child = vm.child();
						child.coverPath = imgHost + data.data;
						vm.child(child);
					} else {
						alert(v.msg);
						/* $.alert({mask:true, msg:v.msg, title:"提示"}); */
					}
				}
			},
			error : function(XmlHttpRequest, textStatus, errorThrown) {
				alert("上传失败，请重试");
				// $.alert({mask:true, msg:"程序异常，请重试！", title:"提示"});
			}
		});
	} else {
		alert("文件格式错误，只支持jpg、jpeg、gif、png格式的图片上传！");
		// alert({mask:true, msg:"文件格式错误，只支持jpg、jpeg、gif、png格式的图片上传！",
		// title:"提示"});
	}
}

/**
 * 文件上传事件绑定
 */
function uploadBind(document) {
	$(document).bztUpload({
		url : datadepotHost + '/chunkedDiskUpload.shtml',
		queryUrl : datadepotHost + '/chunkedInfoQuery.shtml',
		checkExistUrl : '/common/io/checkFileExist.html',
		uploadParam : {
			'type' : "1",
			'key' : "00000930",
			'enc' : "875b9057d7bf96c401cf5505e13365b8",
			'remain' : "1073741824"
		},
		uniqueCodeName : 'uniqueCode',
		method : 'POST',
		sizeLimit : (1024 * 1024 * 1000),
		fileNameLen : 30,
		maxFileLength : 10,
		fileExt : "*.f4v;*.wmv;*.mkv;*.mov;*.avi;*.flv;*.rm;*.mp4;*.ppt;*.pdf;*.doc;*.xls;*.xlsx;*.docx;*.ppt;*.pptx;",
		fileDesc : "请选择 *.f4v,*.wmv,*.mkv,*.mov,*.avi,*.flv,*.rm,*.mp4,*.ppt,*.pdf,*.doc,*.xls/*.xlsx,*.docx,*.ppt/*.pptx格式 文件",
		queueID : 'custom-queue',
		onCheck : function(event, item, ID, fileObj) {
			console.info("正在分析文件,请稍候 ...");
		},
		onCheckComplete : checkComplete,
		onUpload : function(event, item, ID, uniquecode, fileObj, data) {
			uniqueCode = uniquecode;
		},
		onCancelComplete : function(event, queueItem, ID, fileObj, data, clearFast) {
		},

		onComplete : saveToFileManager,
		uploadAllBtn : "#uploadAllBtn",// 批量上传所有文件选择器
		progressContainer : "#progressContainer",
		uploadStarted : function(fileItems, allFileCnt) {
			var flag = true;
			if (!allFileCnt) {
				alert("请选择文件");
				return false;
			}
			_.each(fileItems, function(fileItem) {
				var resType = $(fileItem).find("#select").val();
				if (resType == "" || resType == undefined || resType == null) {
					layer.alert("请选择上传资源类型");
					flag = false;
					return flag;
				}
				return flag;

			});
			return flag;
			// return true;
		}

	});
}
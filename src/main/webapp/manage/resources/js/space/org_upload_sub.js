var sectionCode; // 学段号
var gradeCode; // 年级号
var subjectCode; // 学科号
var versionCode; // 版本号
var settingId; // 知识点id
var settingIds = new Array(); // 知识点id
var contentId; // 知识点内容id
var contentIds = new Array(); // 知识点内容id
var chapterIds = new Array(); // 教材章节id
var resContentType; // 资源类型
var tagContentIdsJson; // 知识点描述json
var tagResRelList; // 知识点选中的
var resTextbookRelList; // 章节选中的
var resContentTypeCode; // 资源类型号
var viewLevel; // 资源查看等级
var knowledgeSelectNode = new Array(); // 选中的知识点
var textBookSelectNode = new Array(); // 选中的知识点
var resDesc; // 资源描述
var contentIds = new Array();;

function initData() {

	// 设置知识点树
	$.extend(knowledgePointSetting, {
		callback: {
			onClick: knowledgeClick,
		},
		view: {
			selectedMulti: false,
			showIcon: false,
			showLine: false,
		}
	});

	// 设置教材章节树
	$.extend(textbookSetting, {
		callback: {
			onClick: textbookClick,
		},
		view: {
			selectedMulti: false,
			showIcon: false,
			showLine: false,
		}
	});
	queryResInfo();

}

// 获取上传资源的额信息
function queryResInfo() {
	var opt = {
		url: "/space/org/upload/info/" + resID + ".html",
		data: {},
		type: "post",
		dataType: "json",
		success: function(result) {
			if (result.success) {
				resType = result.data.resType;
				resName = result.data.resourceName;
				setResourcePropertiesViewModel.resName(resName);
				resContentTypeCode = result.data.resContentTypeCode;
				viewLevel = result.data.viewLevel + "";
				setResourcePropertiesViewModel.viewLevel(viewLevel);
				sectionCode = result.data.sectionCode;
				subjectCode = result.data.subjectCode;
				gradeCode = result.data.gradeCode;
				versionCode = result.data.versionCode;
				resDesc = result.data.resDesc;
				setResourcePropertiesViewModel.description(resDesc);
				tagResRelList = result.data.tagResRelList;
				knowledgeSelectNode = knowledgeSelectNode.concat(tagResRelList);
				resTextbookRelList = result.data.resTextbookRelList;
				textBookSelectNode = textBookSelectNode.concat(resTextbookRelList);
				setResourcePropertiesViewModel.textbookCheckedNodes(result.data.resTextbookRelList);
				setResourcePropertiesViewModel.knowledgeCheckedNodes(result.data.tagResRelList);
				queryResTypeContentList();
				querySections();
			} else if (result.success == false) {
				layer.alert(result.msg);
			}
		}
	};
	$.ajax(opt);
}

// 知识点树点击事件
function knowledgeClick() {
	settingIds = [];
	var treeObj = $.fn.zTree.getZTreeObj("knowledgePointNodeInfo");
	var nodes = treeObj.getSelectedNodes();
	_.each(nodes, function(node) {
		// node.
		if (node.level == 0) {
			// settingIds.push(node.id);
		} else {

			getRootNode(node);
			/*var tagContentIdsObj = new Object(); // 知识点描述对象 
			 tagContentIdsObj.tagSettingId = settingId;
			tagContentIdsObj.tagContentId = node.id;
			contentIds.push(tagContentIdsObj);
			//tagContentIdsJson = JSON.stringify(contentIds);*/
			node.tagSettingId = settingId;
			if (!hasContainedKnowledge(node.id, knowledgeSelectNode)) {
				knowledgeSelectNode.push(node);
			}

		}
	});
	// removeRootNode(nodes);
	setResourcePropertiesViewModel.knowledgeCheckedNodes(knowledgeSelectNode);
}

//去除根节点
function removeRootNode(nodes) {
		_.each(nodes, function(node, index) {
			if (node.level == 0)
				nodes.splice(index, 1);
		});

	}
	// 获取顶级节点

function getRootNode(node) {
		if (node.level == 0) {
			settingId = node.id;
		} else {
			getRootNode(node.getParentNode());
		}

	}
	// 章节目录树点击事件

function textbookClick() {
		chapterIds = [];
		var treeObj = $.fn.zTree.getZTreeObj("textbookInfo");
		var nodes = treeObj.getSelectedNodes();
		_.each(nodes, function(node) {
			if (node.level == 0) {
				// settingIds.push(node.id);
			} else {
				//chapterIds.push(node.id);
				if (!hasContainedTextBook(node.id, textBookSelectNode)) {
					textBookSelectNode.push(node);
				}

			}
		});
		setResourcePropertiesViewModel.textbookCheckedNodes(textBookSelectNode);
	}
	// 查询资源内容类型集合 例如：真题，一般。。

function queryResTypeContentList() {
		var opt = {
			url: "/navfilter/restype/content/list.html",
			data: {
				resTypeValue: resType
			},
			type: "post",
			dataType: "json",
			success: function(result) {
				if (result.success) {
					setResourcePropertiesViewModel.resTypeContents(result.data);
					setResourcePropertiesViewModel.resTypeContentCode(resContentTypeCode);
				} else if (result.success == false) {
					layer.alert(result.msg);
				}
			}
		};
		$.ajax(opt);
	}
	// 设置otherParam

function setOtherParam() {
		textbookSetting.async.otherParam = ["sectionCode", sectionCode, "gradeCode", gradeCode, "subjectCode", subjectCode, "versionCode", versionCode, "resType", resType];
		knowledgePointSetting.async.otherParam = ["sectionCode", sectionCode, "gradeCode", gradeCode, "subjectCode", subjectCode, "versionCode", versionCode, "resType", resType];
	}
	// 初始化学段集合

function querySections() {
		var getSections = {
			url: "/navfilter/section/list.html",
			type: "POST",
			data: {},
			dataType: 'json',
			success: function(res) {
				if (res.success) {
					// 当学段初始化后，初始化选择框的sections
					setResourcePropertiesViewModel.sections(res.data);
					setResourcePropertiesViewModel.sectionCode(sectionCode);
					if (sectionCode) {
						queryGrades();
					}

				} else if (res.success == false) { // 系统错误
					layer.alert(res.msg)
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				layer.alert("提示", "程序异常,请稍后再试!<br />若发生多次请联系管理员！", "info");
			}
		};
		$.ajax(getSections);
	}
	// 初始化学科集合

function querySubjects() {
		// 没有选择section就把grade和subjecs置空
		if (sectionCode == undefined || sectionCode == "" || sectionCode == null) {
			self.subjects([]);
			self.grades([]);
		} else {
			// 查询学科
			var opts = {
				url: "/navfilter/subject/list.html",
				type: "post",
				data: {
					sectionCode: sectionCode
				},
				success: function(res) {
					if (res.success) {
						setResourcePropertiesViewModel.subjects(res.data);
						setResourcePropertiesViewModel.subjectCode(subjectCode);
						queryVersions();
						// $.fn.zTree.destroy("textbookInfo");
						// $.fn.zTree.destroy("knowledgePointNodeInfo");
					} else if (res.success == false) { // 系统错误
						layer.alert(res.msg)
					}
				},
				error: function() {}
			}
			$.ajax(opts);

		}
	}
	// 初始化年级集合

function queryGrades() {
		// 查询年级
		var opts = {
			url: "/navfilter/grade/list.html",
			type: "post",
			data: {
				sectionCode: sectionCode
			},
			success: function(res) {
				if (res.success) {
					setResourcePropertiesViewModel.grades(res.data);
					setResourcePropertiesViewModel.gradeCode(gradeCode);
					querySubjects();

					// $.fn.zTree.destroy("textbookInfo");
					// $.fn.zTree.destroy("knowledgePointNodeInfo");
				} else if (res.success == false) { // 系统错误
					layer.alert(res.msg)
				}
			},
			error: function() {}
		}

		$.ajax(opts);
	}
	// 初始化版本

function queryVersions() {
	// 获取版本集合
	if (gradeCode != null && subjectCode != null) {
		$.ajax({
			url: "/navfilter/version/list.html",
			data: {
				sectionCode: sectionCode,
				gradeCode: gradeCode,
				subjectCode: subjectCode
			},
			type: "post",
			dataType: "json",
			success: function(result) {

				if (result.success) {
					datas = result.data;
					console.log(datas);
					setResourcePropertiesViewModel.versions(datas);
					setResourcePropertiesViewModel.versionCode(versionCode);
					loadTextBookTree();
					loadKnowledgePointTree();
					// $.fn.zTree.destroy("textbookInfo");
				} else if (result.success == false) {
					layer.alert(result.msg);
				}
			}
		});
	}
	// knowledgePointSetting.async.otherParam = [ "sectionCode",
	// sectionCode, "gradeCode", gradeCode, "subjectCode", subjectCode,
	// "resType", resType ];
	// 加载知识点树

}

// 获取String数组，str用，分割
function getStringArray(str) {
		return str.split("，").length > str.split(',').length ? str.split("，") : str.split(',');
	}
	//过滤已经选择的textbook节点

function hasContainedTextBook(id, nodes) {
		var flag = false;
		_.each(nodes, function(node) {
			if (node) {
				if ("textbookNodeID" in node) {
					if (node.textbookNodeID == id) {
						flag = true;
					}
				} else {
					if (node.id == id) {
						flag = true;
					}
				}

			}
		});
		return flag;
	}
	//过滤已经选择的knowledge节点

function hasContainedKnowledge(id, nodes) {
	var flag = false;
	_.each(nodes, function(node) {
		if (node) {
			if ("tagContentID" in node) {
				if (node.tagContentID == id) {
					flag = true;
				}
			} else {
				if (node.id == id) {
					flag = true;
				}
			}

		}
	});
	return flag;
}

function SetResourcePropertiesViewModel() {
	var self = this;
	KO.observableAll(self, {
		isNoteShow: true, // 是否显示教材章节树
		isKnowledgeShow: false, // 是否显示知识点树
		sections: [], // 所有学段
		grades: [], // 根据学段获取的年级
		subjects: [], // 根据学段获取的学科
		versions: [], // 根据学段学科年级获取出版社
		sectionCode: '', // 选中的sectioncode
		gradeCode: '', // 选中的年级code
		subjectCode: '', // 选中的课程code
		versionCode: '', // 选中的出版社code
		resTypeContents: [], // 资源类型
		resTypeContentCode: '', // 选中的资源类型
		knowledgeCheckedNodes: [], // 知识点选中节点
		textbookCheckedNodes: [], // 章节目录树选中节点
		keyWords: [], // 关键字
		description: '', // 描述
		resName: '',
		viewLevel: "10",
		showSetPart: false
	});
	self.removeTextBookCheckedNodes = function(data, event) {
		_.each(textBookSelectNode, function(node, index) {
			if (node) {
				if ("textbookNodeID" in node) {
					if (node.textbookNodeID == data.textbookNodeID) {
						textBookSelectNode.splice(index, 1);
					}
				} else {
					if (node.id == data.id) {
						textBookSelectNode.splice(index, 1);
					}
				}
			}
		});
		self.textbookCheckedNodes(textBookSelectNode);
		console.log(data);
	}
	self.removeKnowledgeCheckedNodes = function(data, event) {
		_.each(knowledgeSelectNode, function(node, index) {
			if (node) {
				if ("tagContentID" in node) {
					if (node.tagContentID == data.tagContentID) {
						knowledgeSelectNode.splice(index, 1);
					}
				} else {
					if (node.id == data.id) {
						knowledgeSelectNode.splice(index, 1);
					}
				}
			}
		});
		self.knowledgeCheckedNodes(knowledgeSelectNode);
		console.log(data);
	}
	self.showNode = function() { // 教材章节树点击显示事件
		self.isNoteShow(true);
		self.isKnowledgeShow(false);
		if (self.versionCode() != null && self.versionCode() != '' && self.versionCode() != undefined) {
			loadTextBookTree();
		}

	};
	self.showKnowledge = function() { // 知识点树点击显示事件
			self.isNoteShow(false);
			self.isKnowledgeShow(true);
			if (self.subjectCode() != null && self.subjectCode() != '' && self.subjectCode() != undefined) {
				loadKnowledgePointTree();
			}
		}
		// 根据学段获取年级
	self.changeSection = function() {
			sectionCode = self.sectionCode();
			// 没有选择section就把grades和subjecs置空
			if (sectionCode == undefined || sectionCode == "" || sectionCode == null) {
				self.grades([]);
				self.subjects([]);
			} else {
				// 查询年级
				var opts = {
					url: "/navfilter/grade/list.html",
					type: "post",
					data: {
						sectionCode: sectionCode
					},
					success: function(res) {
						if (res.success) {
							self.grades(res.data);
							$.fn.zTree.destroy("textbookInfo");
							$.fn.zTree.destroy("knowledgePointNodeInfo");
						} else if (res.success == false) { // 系统错误
							layer.alert(res.msg)
						}
					},
					error: function() {}
				}

				$.ajax(opts);

			}
		}
		// 根据年级变化获取学科
	self.changeGrade = function() {
			sectionCode = self.sectionCode();
			self.subjects([]);
			// 没有选择section就把grade和subjecs置空
			if (sectionCode == undefined || sectionCode == "" || sectionCode == null) {
				self.subjects([]);
				self.grades([]);
			} else {
				// 查询学科
				var opts = {
					url: "/navfilter/subject/list.html",
					type: "post",
					data: {
						sectionCode: sectionCode
					},
					success: function(res) {
						if (res.success) {
							self.subjects(res.data);
							$.fn.zTree.destroy("textbookInfo");
							$.fn.zTree.destroy("knowledgePointNodeInfo");
						} else if (res.success == false) { // 系统错误
							layer.alert(res.msg)
						}
					},
					error: function() {}
				}
				$.ajax(opts);

			}
		}
		// 根据学科变化获取知识点树
	self.changeSubject = function() {
			sectionCode = self.sectionCode();
			gradeCode = self.gradeCode();
			subjectCode = self.subjectCode();
			self.versions([]);
			// 获取版本集合
			if (gradeCode != null && subjectCode != null) {
				$.ajax({
					url: "/navfilter/version/list.html",
					data: {
						sectionCode: sectionCode,
						gradeCode: gradeCode,
						subjectCode: subjectCode
					},
					type: "post",
					dataType: "json",
					success: function(result) {

						if (result.success) {
							datas = result.data;
							console.log(datas);
							setResourcePropertiesViewModel.versions(datas);
							$.fn.zTree.destroy("textbookInfo");
						} else if (result.success == false) {
							layer.alert(result.msg);
						}

					}
				});
			}
			// knowledgePointSetting.async.otherParam = [ "sectionCode",
			// sectionCode, "gradeCode", gradeCode, "subjectCode", subjectCode,
			// "resType", resType ];
			// 加载知识点树
			loadKnowledgePointTree();

		}
		// 根据出版社的事件
	self.changeVersion = function() {
			versionCode = self.versionCode();
			// 加载知识点树
			loadKnowledgePointTree();
			// 加载教材章节
			loadTextBookTree();
		}
		// 改变资源类型事件
	self.changeResType = function() {
			resContentType = self.resTypeContentCode();
		}
		// 提交信息
	self.saveFiles = function() {
		resContentType = self.resTypeContentCode();
		sectionCode = self.sectionCode();
		gradeCode = self.gradeCode();
		subjectCode = self.subjectCode();
		versionCode = self.versionCode();
		_.each(knowledgeSelectNode, function(node) {
			if ("level" in node) {
				var tagContentIdsObj = new Object(); // 知识点描述对象 
				tagContentIdsObj.tagSettingId = node.tagSettingId;
				tagContentIdsObj.tagContentId = node.id;
				contentIds.push(tagContentIdsObj);

			} else {
				var tagContentIdsObj = new Object();
				tagContentIdsObj.tagSettingId = node.tagSettingsId;
				tagContentIdsObj.tagContentId = node.tagContentID;
				contentIds.push(tagContentIdsObj);
			}
		});
		chapterIds = [];
		_.each(textBookSelectNode, function(node) {
			if ("textbookNodeID" in node) {
				chapterIds.push(node.textbookNodeID);
			} else {
				chapterIds.push(node.id);
			}


		});
		tagContentIdsJson = JSON.stringify(contentIds);
		var description = self.description(); // 描述
		// var viewLevel = $("check-box").find(':checked').val();// 公开级别
		var viewLevel = self.viewLevel(); // 公开级别
		var opt = {
			url: "/space/org/upload/update.html",
			data: {
				resId: resID,
				resName: resName,
				resContentType: resContentType,
				sectionCode: sectionCode,
				gradeCode: gradeCode,
				subjectCode: subjectCode,
				versionCode: versionCode,
				textbookStructureIds: chapterIds,
				tagContentIdsJson: tagContentIdsJson,
				viewLevel: viewLevel,
				resDesc: description,
			},
			type: "post",
			dataType: "json",
			success: function(result) {

				if (result.success) {
					//window.top.layer.close(window.top.layer.index); // 关闭窗口
					if (resType == 30 || resType == 40) { //文档 视频
						window.location = "/space/org/folder/index.html";
					} else if (resType == 20) { //作业试卷
						self.showSetPart(false); //隐藏属性设置
					} else if (resType == 50) { //习题试题
						self.showSetPart(false); //隐藏属性设置
					}

				} else if (result.success == false) {
					layer.alert(result.msg);
				}

			}
		};
		if (sectionCode == null || sectionCode == undefined || sectionCode == '') {
			layer.alert("请选择学段号");

		} else if (gradeCode == null || gradeCode == undefined || gradeCode == '') {
			layer.alert("请选择年级号");
		} else if (subjectCode == null || subjectCode == undefined || subjectCode == '') {
			layer.alert("请选择学科号");
		} else {
			$.ajax(opt);
		}

	}

	// 取消
	self.cancel = function() {

		window.top.layer.close(window.top.layer.index); // 关闭窗口
	}

}
var setResourcePropertiesViewModel = new SetResourcePropertiesViewModel();
ko.applyBindings(setResourcePropertiesViewModel, $("#setPart")[0]);
    var model =new ViewModel(_res);
    var orgList;
	$(function(){
		//图片上传
		imageUpload(
					$('#file'),
					$('#mediaCover'),
					function(path){
						model.coverPath(imgHost+"/"+path);
						model.realPath(path);
						}
				);
		
		$.post("/manage/company/sectionOrg.html",
			function (res){
				orgList=res;
			}
		);
		
		$("body").click(function (event) {
			// 机构选择列表隐藏
			$('#orgList').hide();
			// 章节知识点列表隐藏
			if (model.isShowChapterMenu()) {
				model.isShowChapterMenu(false);
			}
			if (model.isShowKnowlegePoints()) {
				model.isShowKnowlegePoints(false);
			}
		});
		
		// 机构筛选功能
		var orgDiv=$('#orgList');
		$("#makerOrgName")[0].onkeydown=function(){
			var bind_name = 'keyup';
			if (navigator.userAgent.indexOf("MSIE") != -1){
					bind_name = 'input propertychange';
				}
			$(this).unbind(bind_name).bind(bind_name,function(){
				var orgName=$(this).val();
				orgDiv.empty().show();
				console.info(orgName);
				var fliterOrg=_.filter(orgList,function(item){return item.orgName.includes(orgName)});
				$.each(fliterOrg,function(i,object){
					$("<div>").text(object.orgName).val(object.orgCode).appendTo(orgDiv).click(function(){
						$("#makerOrgName").val(object.orgName);
						model.makerOrgCode(object.orgCode);
						model.makerOrgName(object.orgName);
						orgDiv.toggle();
					});
				})
			})
		};
		
		//初始化文档
		init();
	})
	
	// 章节 知识点鼠标进入事件
	function mouseenter(object,event){
		visible=$(object).siblings().children("div").find("ul:visible").length>0;
		if(visible){
			$(object).siblings().children("div").find("ul").hide();
		}
		$(object).children("div:first ").find("ul:first").attr("style"," display: block; width: 135px;position: absolute; color:black;top: 0px;left: 135px;border: 1px solid #1790C2;");
	}

	// 图片路径处理
	function pathHandle(path){
		var _path="";
		if(path!=null&&path!=""){
			if(path.indexOf("img_upload")>=0){
				_path=imgHost+"/"+path;
			}else{
				_path=videoHost+"/"+path;
			}
		}
		return _path||"/manage/template/images/defaultCover.png";
	}

	/**
	 * photoSwipe.js 选择图片给图片 给图片放大插件使用
	 */
	function chooseImage(){
		model.coverPath(model.currentSrc());
		model.realPath(model.currentRealSrc());
	}
	
	
	// 初始化
	function init(){
		if(selectedChapterList!=undefined&&selectedChapterList!=null&&selectedChapterList.length>0){
			$.each(selectedChapterList,function(i,obj){
				obj[obj.length-1].saved=1;
			})
		}
		if(selectedKnowledgeList!=undefined&&selectedKnowledgeList!=null&&selectedKnowledgeList.length>0){
			$.each(selectedKnowledgeList,function(i,obj){
				obj[obj.length-1].saved=1;
			})
		}
		
		// 缩略图
		_.each(thumbnailList,function(item){
			var path=pathHandle(item.path);
			model.thumbnailList.push({"coverPath":path,"realPath":item.path});

		})
		model.hasTypeL2(_res.resTypeL1==_RESTYPE.RES_TYPE_Flash||_res.resTypeL1==_RESTYPE.RES_TYPE_Audio);
		
		model.flagAllowDownload.source([{name:"允许",code:1},{name:"不允许",code:0}]);
		
		// 学段
		model.section.source(sectionList);		// 学段
		var _sectionCode=relateSection.code||"-1";
		model.section.val(_sectionCode);	// 适合的学段
		
		// 标签
		model.tags(_res.tags||[]);
		
		// 章节
		model.selectedChapterList(selectedChapterList||[]);
		
		// 知识点
		model.selectedKnowledgeList(selectedKnowledgeList||[]);
		
		// 资源类型
		model.resTypeL2.source(resTypeL2);
		model.resTypeL2.val(_res.resTypeL2);
		
		/**
		 * 封面 没有的话取缩略图第一张
		 */
		var coverPath=_res.coverPath||(model.thumbnailList()[0]?model.thumbnailList()[0].coverPath:"");
		model.coverPath(pathHandle(coverPath));
		model.realPath(_res.coverPath);
		
		// 允许下载
		model.flagAllowDownload.val(_res.flagAllowDownload);
		
		/**
		 * 晚一点执行
		 * 不然学段改变事件会执行不到
		 */
		var _subjectCode,
		    _gradeCode;
		setTimeout(function(){
			// 学科
			var subjectList=model.subject.source();
			_subjectCode=getValue(relateSubject.code,subjectList)||"-1";
			model.subject.val(_subjectCode);
			
			// 年级
			var gradeList=model.grade.source();
			_gradeCode=getValue(relateGrade.code,gradeList)||"-1";
			model.grade.val(_gradeCode);
			
			//初始化树
			query(_sectionCode,_gradeCode,_subjectCode);
			
		},500)
	
		
		// 作者
		var value=getValue(_res.userCode,teacherList,"teacherCode");
		if(value==""){
			teacherList.unshift({"teacherCode":_res.userCode,"userName":_res.userName})
		}
		model.user.source(teacherList);
		model.user.val(_res.userCode);
		
		
		// 建议
		model.adviceList=ko.observableArray([]);
		
		// 机构
		model.makerOrgCode.subscribe(function(data){
			getTeacherList(data);
		});
		
		
		
		ko.applyBindings(model, $("body")[0]);
	}

	
	// easyui下拉框类
	function easyuiDefaultModel(){
		var self=this;
		self.source=ko.observableArray();
		self.val=ko.observable("");
		self.viewSettings={
			valueField:'code',
			textField:'name',
		}
	}
	
	
	var changed,selectRow;
	function ViewModel(res) {
		var self = this;
		
		// 学段
		var section=new easyuiDefaultModel();
		section.viewSettings.onChange=sectionChange;
		
		// 作者
		var user=new easyuiDefaultModel();
		user.viewSettings={
			valueField	:	"teacherCode",
			required	:	true,
			textField	:	"userName",
			onHidePanel	:	function(){
								var t = $(this).combobox('getText');// 当前value值
								var datas=$(this).combobox('getData');
								var _textField=$(this).combobox('options').textField;// 文本key
								var exist=_.find(datas,function(item){
									return item[_textField]==t
								})
								if(!exist&&t!=""){
									alert("请不要手动输入");
									$(this).combobox('setValue','');
									model.user.val("");
								}else{
									$(this).combobox('select',exist.teacherCode);
									model.user.val(exist.teacherCode);
								}
							},
			onSelect	:	function(row,index){
								selectRow=row;
							},
		};
		
		// 年级
		var grade=new easyuiDefaultModel();
		grade.viewSettings.onSelect=gradeSelect;
		
		
		// 学科
		var subject=new easyuiDefaultModel();
		subject.viewSettings.onSelect=subjectSelect;
		
		// 是否可下载
		var flagAllowDownload=new easyuiDefaultModel();
		$.extend(flagAllowDownload.viewSettings,{
			required	:	true,
			editable	:	false,
		});
		
		
		self.thumbnailList=ko.observableArray();
		self.tags=ko.observableArray();
		self.flagAllowDownload=flagAllowDownload;
		self.currentSrc=ko.observable();
		self.currentRealSrc=ko.observable();
		self.coverPath=ko.observable();
		self.realPath=ko.observable();
		self.resName = ko.observable(res.resName || "");
		self.sectionName = res.sectionName || "";
		self.subjectName = res.subjectName || "";
		self.gradeName = res.gradeName || "";
		self.userName =ko.observable(res.userName || "");
		self.makeTime = res.makeTime || "";
		self.makerOrgCode=ko.observable(res.orgCode||"");
		self.browseCount = res.browseCount || 0;
		self.referCount = res.referCount || 0;
		self.goodCount = res.goodCount || 0;
		self.shareLevelStr = res.shareLevelStr || "";
		self.makerOrgName = ko.observable(res.orgName || "");
		self.resDesc = ko.observable(res.resDesc || "");
		self.grade=grade;				// 所属年级
		self.section=section;
		self.subject=subject;
		self.user=user;
		self.deny=ko.observable(false);
		self.makerCode=ko.observable("");
		self.selectedChapterList=ko.observableArray([]);
		self.selectedKnowledgeList=ko.observableArray([]);
		self.resTypeL2=new easyuiDefaultModel();
		self.hasTypeL2=ko.observable(false);
		self.tags=ko.observableArray([]);
		self.tagToAdd=ko.observable("");
		
		// 添加标签
		self.pushTag=function(){
			if(self.tagToAdd().trim()==""){
				layer.alert("请输入标签内容");
				return
			}
			if(self.tagToAdd().length>10){
				layer.alert("标签长度不能超过10");
				return
			}
			if(self.tags()&&self.tags.indexOf(self.tagToAdd())>=0){
				layer.alert("标签已经存在");
				return
			}
			self.tags.push(self.tagToAdd().trim());
			layer.closeAll();
			self.tagToAdd("");
		}
		
		// 删除标签
		self.deleteTag=function(data){
			self.tags.remove(data);
		}
		
		// 打开标签添加窗口
		self.addTag=function(){
			if(self.tags()&&self.tags().length==5){
				return false
				layer.alert("最多只能添加5个标签");
			}else{
				self.tagToAdd("");
				layer.open({
					type:'1',
					title:'添加标签',
					area:['400px','140px'],
					content:$('#tagAdd'),
				})
			}
		}
		
		// 删除章节
		self.delChapter = function(data) {
			if(self.selectedChapterList().length==1){
				$.messager.alert("信息","至少保留一个教材章节!","info");
				return false;
			}
			if(data[data.length-1].saved==1){
				$.messager.confirm("信息","该条记录已经保存,确认要删除吗",function(r){
					if(r){
						self.selectedChapterList.remove(data);
						deleteChapter(data[data.length-1].code);
					}

				})
			}else{
				self.selectedChapterList.remove(data);
				deleteChapter(data[data.length-1].code);
			}

		};
		
		// 删除知识点
		self.delKnowledge = function(data) {
			if(self.selectedKnowledgeList().length==1){
				$.messager.alert("信息","至少保留一个知识点！","info");
				return false;
			}
			if(data[data.length-1].saved==1){
				$.messager.confirm("信息","该条记录已经保存,确认要删除吗",function(r){
					if(r){
						self.selectedKnowledgeList.remove(data);
						deleteKnowledge(data[data.length-1].code);
					}
				})
			}else{
				self.selectedKnowledgeList.remove(data);
				deleteKnowledge(data[data.length-1].code);
			}

		};
		self.chaptershow=function(){
			if(self.selectedChapterList()&&self.selectedChapterList().length>0){
				return true;
			}
			return false;
		};
		self.knowledgeshow=function(){
			if(self.selectedKnowledgeList()&&self.selectedKnowledgeList().length>0){
				return true;
			}
			return false;
		}
		self.isShowChapterMenu=ko.observable(false);
		self.isShowKnowlegePoints=ko.observable(false);
		self.currentTitle=ko.observable("name");
		self.currentCode=ko.observable("code");
		self.toggleChapterMenu=function(event){
			self.isShowKnowlegePoints(false);
			var e = window.event || event;
			if (e.stopPropagation) {
				e.stopPropagation();
			} else {
				e.cancelBubble = true;
			}
			if (isEmpty(self.section.val())) {
				messagerAlert("请选择学段");
				return;
			}
			if (isEmpty(self.subject.val())) {
				messagerAlert("请选择学科");
				return;
			}
			self.isShowChapterMenu(!self.isShowChapterMenu());
		}
		self.toggleKnowlegepoints=function(event){
			self.isShowChapterMenu(false);
			var e = window.event || event;
			if (e.stopPropagation) {
				e.stopPropagation();
			} else {
				e.cancelBubble = true;
			}
			if (isEmpty(self.section.val())) {
				messagerAlert("请选择学段");
				return;
			}
			if (isEmpty(self.subject.val())) {
				messagerAlert("请选择学科");
				return;
			}
			self.isShowKnowlegePoints(!self.isShowKnowlegePoints());
		}
		self.chapterMenuOptions = {
			url: ko.observable(''), // URL
			urlparams:ko.observable(""),  // url参数
			dataArray:ko.observable(),  // 若无url可直接传数组,若有数组，则以数组数据为准
			CodeValue: ko.observable('pcode'),  // 父code的值
			CodeType: ko.observable(''),    // 父code类型
			/*
			 * unselectStyle: ko.observable('white'), selectStyle:
			 * ko.observable('red'),
			 */
			ItemTitle: self.currentTitle,               // 自己的名称
			ItemCode: self.currentCode,                 // 自己的code值
			/* isShowLeftIcon: ko.observable(false),//是否显示往左图片 */
			topClass:ko.observable(2),    // menuOptions对象的层级数，默认为2
			/*
			 * leftIconImgUrl:
			 * ko.observable("/manage/resources/img/left.png"),//图片地址
			 */
			selectfocus: function (data) {    // 点击列表后返回的点击项，以及他的父类对象
				if(data[data.length-1].data){
					alert("请选择到最后一层章节");
					return false;// 有子节点不能选
				}
				var code=data[data.length-1].code;
				if(data[data.length-1].nodeType=="textbook")return false;// 教材不能选
				if (self.selectedChapterList()&&self.selectedChapterList().length) {
					var selectChapter=new Array();
					if(model.selectedChapterList()&&model.selectedChapterList().length>0){
						$.each(model.selectedChapterList(),function(i,data){
							selectChapter.push(data[data.length-1].code);
						})
					}
					if (selectChapter.indexOf(code)<0) {
						self.selectedChapterList.push(data);
					}
				} else {
					self.selectedChapterList.push(data);
				}
			},
			changedata:function(data){  // 对url获取的数据进行重组并返回，如无需操作，直接返回数据即可
				return data;
			}
		}
		self.knowledgeMenuOptions = {
			url: ko.observable(''), // URL
			urlparams:ko.observable(""),  // url参数
			dataArray:ko.observable(),  // 若无url可直接传数组,若有数组，则以数组数据为准
			CodeValue: ko.observable('pcode'),  // 父code的值
			CodeType: ko.observable(''),    // 父code类型
			/*
			 * unselectStyle: ko.observable('white'), selectStyle:
			 * ko.observable('red'),
			 */
			ItemTitle: self.currentTitle,               // 自己的名称
			ItemCode: self.currentCode,                 // 自己的code值
			/* isShowLeftIcon: ko.observable(false),//是否显示往左图片 */
			topClass:ko.observable(2),    // menuOptions对象的层级数，默认为2
			/*
			 * leftIconImgUrl:
			 * ko.observable("/manage/resources/img/left.png"),//图片地址
			 */
			selectfocus: function (data) {    // 点击列表后返回的点击项，以及他的父类对象
				if(data[data.length-1].data){
					alert("请选择到最后一层知识点");
					return false;// 有子节点不能选
				}
				var code=data[data.length-1].code;
				if(data[data.length-1].nodeType=="knowledgeSerial")return false;// 知识体系不能选
				if (self.selectedKnowledgeList()&&self.selectedKnowledgeList().length) {
					var selectKnowledge=new Array();
					if(model.selectedKnowledgeList()&&model.selectedKnowledgeList().length>0){
						$.each(model.selectedKnowledgeList(),function(i,data){
							selectKnowledge.push(data[data.length-1].code);
						})
					}
					if (selectKnowledge.indexOf(code)<0) {
						self.selectedKnowledgeList.push(data);
					}
				} else {
					self.selectedKnowledgeList.push(data);
				}
			},
			changedata:function(data){  // 对url获取的数据进行重组并返回，如无需操作，直接返回数据即可
				return data;
			}
		};
		return self;

	}
	
	// 查询老师
	function getTeacherList(data){
		$.ajax({
			url:"/manage/teacher/getTeacherList.html",
			data:{
				orgCode:data
			},
			success:function(res){
				model.user.val("");
				model.user.source(res);
			}
		})
	}
	
	// 年级选择事件
	function gradeSelect(record){
		/* delAll(); */
		query(model.section.val(),model.grade.val(),model.subject.val());
	}
	
	// 学科选择事件
	function subjectSelect(record){
		/* delAll(); */
		query(model.section.val(),model.grade.val(),model.subject.val());
	}

	// 删除所有知识点章节
	function delAll(){
		$.each(model.selectedChapterList(),function(i,data){
			if(data[data.length-1].saved==1){
				model.selectedChapterList.remove(data);
				deleteChapter(data[data.length-1].code);
			}else{
				model.selectedChapterList.remove(data);
			}
		})
		$.each(model.selectedKnowledgeList(),function(i,data){
			if(data[data.length-1].saved==1){
				model.selectedKnowledgeList.remove(data);
				deleteKnowledge(data[data.length-1].code);
			}else{
				model.selectedKnowledgeList.remove(data);
			}
		})
	}
	
	// 初始化章节知识点树
	function query(sectionCode,gradeCode,subjectCode) {
		resId=_res.id;
		if (isEmpty(sectionCode)) {
			/* messagerAlert("请选择学段"); */
			return;
		}
		if (isEmpty(subjectCode)) {
			/* messagerAlert("请选择学科"); */
			return;
		}
		var param={sectionCode:sectionCode,gradeCode:gradeCode,subjectCode:subjectCode};
		model.chapterMenuOptions.urlparams(param);
		model.chapterMenuOptions.url('/manage/textbook/getChapterAll.html');
		model.isShowChapterMenu(false);
		var knowlegeparam={sectionCode:sectionCode,subjectCode:subjectCode};
		model.knowledgeMenuOptions.urlparams(knowlegeparam);
		model.knowledgeMenuOptions.url('/manage/textbook/getKnowLedgeAll.html');
		model.isShowKnowlegePoints(false);

		/*
		 * knowledgeQP = { resId : resId, sectionCode : sectionCode, subjectCode :
		 * subjectCode }//知识点查询条件 chapterQP = { resId : resId, sectionCode :
		 * sectionCode, subjectCode : subjectCode, gradeCode : gradeCode,
		 * };//章节查询条件 initKnowledge(knowledgeQP); initChapter(chapterQP);
		 */

	}
	// 所属学段选择事件
	function sectionChange(newValue,oldValue){
		/* delAll(); */
		if(newValue==""||newValue==undefined){
			model.grade.source([]);
			model.grade.val("");
			model.subject.source([]);
			model.subject.val("");
			return false;
		}
		var gradeList=Ajax_request("/manage/sectionGradeRef/sectionGrade.html",{sectionCode:newValue}) // 查询学段年级
		model.grade.source(gradeList);
		model.grade.val("");
		var subjectList=Ajax_request("/manage/sectionSubjectRef/sectionSubject.html",{sectionCode:newValue})	// 查询学段学科
		model.subject.source(subjectList);
		model.subject.val("");
		console.info(model.subject.val());

	}
	// ajax请求
	function Ajax_request(url,data){
		var result=[];
		$.ajax({
			url:url,
			data:data,
			async :false,
			type:'post',
			success:function(json){
				result=json;
			}
		})
		return result;
	}
	// 删除资源章节
	function deleteChapter(code,data) {
		$.ajax({
			url : '/manage/res/delResChapter.html',
			data : {
				resId : resId,
				chapterCode : code,
			},
			type : 'post',
			success : function(res) {
				if (res.success) {
					model.selectedChapterList.remove(data);
				}
			}
		})
	}
	// 删除资源知识点
	function deleteKnowledge(code,data) {
		$.ajax({
			url : '/manage/res/delResKnowledge.html',
			data : {
				resId : resId,
				knowledgeCode : code,
			},
			type : 'post',
			success : function(res) {
				if (res.success) {
					model.selectedKnowledgeList.remove(data);
				}
			}
		})
	}
	// 更新资源表 资源关联表信息
	var isUpdate=false;
	function update(){
		var _coverPath=model.realPath();
		var selectChapter=new Array();
		var selectKnowledge=new Array();
		if(model.selectedChapterList()&&model.selectedChapterList().length>0){
			$.each(model.selectedChapterList(),function(i,data){
				selectChapter.push(data[data.length-1].code);
			})
		}
		if(model.selectedKnowledgeList()&&model.selectedKnowledgeList().length>0){
			$.each(model.selectedKnowledgeList(),function(i,data){
				selectKnowledge.push(data[data.length-1].code);
			})
		}
		var resName=model.resName();
		if(resName==""||resName.trim()==""){
			$.messager.alert("信息","请输入资源名称","");
			return false;
		}
		/** 设置保存post参数 */
		/* var tbvCode=vm.version.val(); //教材版本 */
		var gradeCode=model.grade.val();							// 年级相关
		var subjectCode=model.subject.val();						// 学科相关
		var sectionCode=model.section.val();					// 学段相关
		if(sectionCode==null||sectionCode==""){
			alert("请选择学段");
			return false;
		} 
		if(subjectCode==null||subjectCode==""){
			alert("请选择学科");
			return false;
		}
		if(gradeCode==null||gradeCode==""){
			alert("请选择年级");
			return false;
		}
		var markerCode=model.user.val();
		var teacherList=model.user.source();
		var teacher=_.find(teacherList,function(item){
			return item.teacherCode==markerCode;
		})// 作者
		var userName=teacher?teacher.userName:"";
		var makerOrgCode=model.makerOrgCode();						// 机构
		var makerOrgName=model.makerOrgName();
		var flagAllowDownload=model.flagAllowDownload.val();
		if(userName==""){
			alert("请选择作者");
			return false;
		}
		var orgExits=true;
		var tags="";
		_.each(model.tags(),function(item){
			tags=tags+item+",";
		})
		tags=tags.substring(0,tags.length-1);
		$.ajax({
			url:"/manage/company/getOrgByName.html",
			type:"POST",
			data:{orgName:makerOrgName,isLike:0},
			async:false,
			success:function (res){
				var orgList=res.data
				var objects=eval(orgList);
				if(objects==null||objects==undefined||objects.length<=0){
					orgExits=false;
				}
			},
		});
		if(!orgExits){
			$.messager.alert("信息","机构不存在","info");
			return false;
		}
		var data={
			/* tbvCodes:tbvCode, */
			resDesc:model.resDesc(),
			makerOrgCode:makerOrgCode,
			makerOrgName:makerOrgName,
			flagAllowDownload:flagAllowDownload,
			makerCode:markerCode,
			coverPath:_coverPath,
			resTypeL2:model.resTypeL2.val(),						// 资源分类
			resName:resName,
			resId:_res.id,
			gradeCodes:gradeCode,
			subejctCodes:subjectCode,
			sectionCodes:sectionCode,
			kpCodes:selectKnowledge.join(),
			tbcCodes:selectChapter.join(),
			userName:userName,
			tags:tags,
			/* termCode:vm.term.val, */
		};
		updateInfo(data);
	}

	// 更新文档资源信息
	function updateInfo(data){
		var url="";
		var resType=_res.resTypeL1;
		if(resType=="10"){
			url="/manage/res/mediaUpdate.html";
		}else if(resType=="20"){
			url="/manage/res/docUpdate.html";
		}else if(resType=="15"){
			url="/manage/flash/flashUpdate.html"
		}else if(resType=="12"){
			url="/manage/audio/audioUpdate.html"
		}
		var opt={
			url:url,
			data:data,
			type:"post",
			success:function(json){
				isUpdate=false;
				$.messager.alert("信息",json.msg,"info",function(){
					easyui_closeTopWindow(true);
				});
			}
		}
		if(isUpdate){
			$.messager.alert("信息","不要重复提交","info")
			return false;
		}else{
			isUpdate=true;
			$.ajax(opt);
		}
	}

/**
 * 后台用资源新增，编辑ko模版
 */
function viewModel(){
	var self=this;
	/**资源表*/
	self.library=ko.observable({currencyCount:0,trialTimeRate:30});
	
	//学段
	self.section={
		source:ko.observableArray([]),
		val:ko.observable(""),
		viewSettings:{
			valueField:'codeValue',textField:'codeName',
			onChange: sectionChange 
		}
	}
	
	//年级
	self.grade={
		source:ko.observableArray([]),
		val:ko.observable(),
		viewSettings:{
			valueField:'codeValue',textField:'codeName',
			onChange:gradeChange
		}
	}
	
	//学科
	self.subject={
		source:ko.observableArray([]),
		val:ko.observable(),
		viewSettings:{
			valueField:'codeValue',textField:'codeName',
			onChange:subjectChange
		}
	}
	
	/**标签*/
	self.tag=ko.observable({});			//标签显示隐藏
	self.setTag=setTag;					//根据年级，学科，学段
	/**教材*/
	self.textbookList=ko.observableArray([]);
	self.deleteTextbook=deleteTextbook;
	/**知识点*/
	self.knowledgeList=ko.observableArray([]);
	self.deleteKnowledge=deleteKnowledge;
	/**关键字*/
	self.tag_keyword=ko.observableArray([]);
	self.keywordList=ko.observableArray([]);
	self.deleteKeyword=deleteKeyword;
	self.view=ko.observable(true);
	self.switchKeywordView=switchKeywordView;
	self.keyword=ko.observable("");
	
	/**试卷题库数据*/
	self.questionLists=ko.observableArray([]);
}

/**
 * 打开设置知识点窗口
 */
function openKnowledge_point_rel(){
	var data=getSubWindowParam();
	if(data.resType&&data.sectionCode&&data.gradeCode&&data.subjectCode){
		var url="/admin/resource/resourceUpload_setKnowledge_point.html?sectionGlobalCode={0}&gradeGlobalCode={1}&subjectGlobalCode={2}&resType={3}&resID={4}"
			.format(data.sectionCode,data.gradeCode,data.subjectCode,data.resType,data.id);
		easyui_openTopWindow("设置教材",900,600,url,function(data){
			if(data){
				vm.knowledgeList(data);
			}
		});
	}else{
		$.messager.alert("信息","请勾选需要设置的资源，并设置学段，学科，年级","info");
	}
}

/**
 * 打开设置出版社窗口
 */
function openTextbookNode(){
	var data=getSubWindowParam();
	var url="/admin/resource/resourceUpload_setTextbook.html?sectionGlobalCode={0}&gradeGlobalCode={1}&subjectGlobalCode={2}&resID={3}"
		.format(data.sectionCode,data.gradeCode,data.subjectCode,data.id);
	easyui_openTopWindow("设置教材",900,600,url,function(data){
		if(data){
			vm.textbookList(data);
		}
	});
}



/**
 * 删除教材关系
 */
function deleteTextbook(index){
	var textbookList=vm.textbookList();
	textbookList.splice(index,1);
	vm.textbookList(textbookList);
}

/**
 * 学段改变
 */
function sectionChange (newVal, oldVal) {
	if(newVal){
		var library=vm.library();
		library.sectionCode=newVal;
		library.gradeCode="";
		library.subjectCode="";
		vm.library(library);
		
		var loadGrade=Ajax_getChildCodeMaster("SECTION",newVal,"GRADE");
		loadGrade.length||(loadGrade=[]);
		vm.grade.source(loadGrade);
		
		var loadSubject=Ajax_getChildCodeMaster("SECTION",newVal,"SUBJECT");
		loadSubject.length||(loadSubject=[]);
		vm.subject.source(loadSubject);
		
		vm.grade.val("");
		vm.subject.val("");
	}
	
}

/**
 * 年级改变
 */
function gradeChange(newVal,oldVal){
	if(newVal){
		var library=vm.library();
		library.gradeCode=newVal;
		vm.library(library);
		var param=getSubWindowParam();
		param.gradeGlobalCode=newVal;
		setTag(param);
	}
}

/**
 * 学科改变
 */
function subjectChange(newVal,oldVal){
	if(newVal){
		var library=vm.library();
		library.subjectCode=newVal;
		vm.library(library);
		var param=getSubWindowParam();
		param.subjectGlobalCode=newVal;
		setTag(param);
	}
}
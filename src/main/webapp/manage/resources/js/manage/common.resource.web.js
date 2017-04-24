/**
 * 模版绑定
 * @param selector 		选择器
 * @param viewModel		绑定视图
 */
function templateBind(viewModel,selector){
	var dom=$(selector)[0];
	try{
		ko.cleanNode(dom);	
	}catch(e){}
	ko.applyBindings(viewModel,dom);
}
/**
 * 资源web 	ko模版
 */
function viewModel(){
	var self=this;
	/**教师信息*/
	self.teacher=ko.observable({});
	
	/**机构信息*/
	self.org=ko.observable({});
	
	/**学段*/
	self.section={
		options:ko.observableArray([]),								//数据源
		sectionCode:ko.observable(),								//学段
		change:sectionChange										//学段改变回调
	}
	
	/**年级*/
	self.grade={
		options:ko.observableArray([]),								//数据源
		gradeCode:ko.observable(),									//年级代码
		change:gradeChange											//年级改变回调
	}
	
	/**学科*/
	self.subject={
		list:ko.observableArray([]),								//数据源
		index:ko.observable(0),										//学科索引
		change:function(i,CodeValue){								//学科改变回调
			self.subject.index(i);
			subjectChange();
		}
	}
	
	/**资源属性*/
	self.library=ko.observable({viewLevel:"60"});
	
	/**资源子表属性*/
	self.child=ko.observable({});
	
	/**教材章节*/
	self.textbookList=ko.observableArray([]);
	self.deleteTextbook=function(i){
		$("#"+self.textbookList()[i].tId+"_a").click();
	}
	
	/**标签*/
	self.tag=ko.observable({});										//标签显示隐藏
	self.setTag=setTag;												//根据年级，学科，学段
	/**知识点*/
	self.knowledgeList=ko.observableArray([]);
	self.deleteKnowledge=deleteKnowledge;
	/**关键字*/
	self.tag_keyword=ko.observable({});								//标签列表
	self.keywordList=ko.observableArray([]);						//关键字列表
	self.deleteKeyword=deleteKeyword;								//删除关键字		
	self.view=ko.observable(false);									//关键字显示视图
	self.switchKeywordView=switchKeywordView;						//关键字视图切换
	self.keyword=ko.observable("");									//关键字
	
	/**资源类型 与资源类型内容*/
	self.resType=ko.observableArray(resTypeMap.resType);
	self.resTypeContent=ko.observableArray();
	
	/***资源描述可以输字数*/
	self.resDesCannAddcLen=ko.observable(500);						//资源描述可增字符
	self.updateResDesLen=function(){								//计算资源描述可增加字符数
		var val= self.library().resDesc;
		val||(val="");
		var len = 0;
        for (var i = 0; i < val.length; i++) {
           /*  if (val[i].match(/[^\x00-\xff]/ig) != null) //全角
                len += 2;
            else */
                len += 1;
        }
        self.resDesCannAddcLen(500-len);
	}
	
	
	self.uploadCover=uploadCover;									//上传文件
	self.saveFiles=saveFiles;										//保存文件
	self.cancel=function (){										//关闭页面
		parent.layerClose();
	}
}

/**
 * 获取教材关系
 */
function getTextbookRel(){
	var textbookRel=[];
	$.each(vm.textbookList(),function(i,v){
		var textbook=null;
		if(v.nodeType=="ntt"){
			textbook={
				textbookID:v.id
			}
		}else if(!v.nodeType){
			textbook={
				textbookID:v.textbookID,
				textbookNodeID:v.id,
				textbookNodeCode:v.nodeCode
			}
		}
		textbookRel.push(textbook);
	});
	
	return textbookRel;
}

/**
 * 教材树选择
 */
function textbookRelTreeClick(){
	var checkedNodes=TextbookRelzTreeClick(zTree_textbook);
	
	vm.textbookList(checkedNodes);
}

/**
 * 知识点选择
 */
function knowledgeRelzTreeClick(){
	var param=getSubWindowParam();
	var initData={
		sectionGlobalCode: param.sectionGlobalCode,
		subjectGlobalCode: param.subjectGlobalCode,
		gradeGlobalCode:   param.gradeGlobalCode
	};
	var ary=knowledgezTreeClick($.fn.zTree.getZTreeObj("knowledgePoint"),initData);
	
	vm.knowledgeList(ary);
}


/**
 * 视频封面上传
 */
function imgFile(fileInput,form,callback){
	var file=$(fileInput).val();
	var filename=file.replace(/.*(\/|\\)/, "");  
	var fileExt=(/[.]/.exec(filename)) ? /[^.]+$/.exec(filename.toLowerCase()) : ''; 
	if(fileExt=="jpg"||fileExt=="jpeg"||fileExt=="gif"||fileExt=="png"){
		$(form).ajaxSubmit({
            type: 'post',
            url: "/web/resource/resUpload.jhtml?do=uploadCover" ,
            success: function(data){
                if(typeof(callback)=="function"){
                	callback(data);
                }else{
                	if(!data.status){
                        var child=vm.child();
                        child.coverPath=imgHost+data.data;
                		vm.child(child);
                    }else{
                    	alert(v.msg);
                    	/* $.alert({mask:true, msg:v.msg, title:"提示"}); */
                    }
                }
            },
            error: function(XmlHttpRequest, textStatus, errorThrown){
            	alert("上传失败，请重试");
            	//$.alert({mask:true, msg:"程序异常，请重试！", title:"提示"});
            }
        });
	}else{
		alert("文件格式错误，只支持jpg、jpeg、gif、png格式的图片上传！");
		//alert({mask:true, msg:"文件格式错误，只支持jpg、jpeg、gif、png格式的图片上传！", title:"提示"});
	}
}

/**
 * 文件上传事件绑定
 */
function uploadBind(document){
	$(document).bztUpload({
		url      	   : datadepotHost+'/chunkedDiskUpload.shtml', 
 		queryUrl		 : datadepotHost+'/chunkedInfoQuery.shtml',
 		checkExistUrl  : '/common/io/checkFileExist.html',
 		uploadParam 	 : {'type':"1", 'key':"00000930", 'enc':"875b9057d7bf96c401cf5505e13365b8", 'remain':"1073741824"},
 		uniqueCodeName : 'uniqueCode',
 		method		 : 'POST',
 		sizeLimit		 : (1024 * 1024 * 1000),
 		fileNameLen	 : 30,
 		maxFileLength : 10,
		fileExt: "*.f4v;*.wmv;*.mkv;*.mov;*.avi;*.flv;*.rm;*.mp4;*.ppt;*.pdf;*.doc;*.xls;*.xlsx;*.docx;*.ppt;*.pptx;",
        fileDesc: "请选择 *.f4v,*.wmv,*.mkv,*.mov,*.avi,*.flv,*.rm,*.mp4,*.ppt,*.pdf,*.doc,*.xls/*.xlsx,*.docx,*.ppt/*.pptx格式 文件",
        queueID: 'custom-queue',
        onCheck: function(event, item, ID, fileObj) {
            console.info("正在分析文件,请稍候 ...");
        },
        onCheckComplete: checkComplete,
        onUpload: function(event, item, ID, uniquecode, fileObj, data) {
            uniqueCode = uniquecode;
        },
        onCancelComplete: function(event, queueItem, ID, fileObj, data, clearFast) {
        },
        onComplete: saveToFileManager,
        uploadAllBtn: "#uploadAllBtn",//批量上传所有文件选择器
        progressContainer:"#progressContainer"
 	});
}
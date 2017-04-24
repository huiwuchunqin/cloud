	//更新资源表 资源关联表信息
	function save(){
		var data={
				shareLevel:vm.shareLevel.val(),
				sectionCode:vm.suitableSection.val(),
				subjectCode:vm.suitableSubject.val(),
				gradeCode:vm.suitableGrade.val(),
				downloadPoints:vm.downloadPoints(),
				resDesc:vm.resDesc(),
				source:0,										//资源来源 bzt上传
				flagUnionRes:1,									//联盟资源
				flagAllowDownload:vm.flagAllowDownload.val(),	//是否允许下载
				flagAllowBrowse:vm.flagAllowBrowse.val(),		//是否允许浏览
				trialTimeRate:vm.trialTimeRate(),				//允许试看百分比
		};
		var selectFiles=getSelectFiles();//所有选中文件
		if(selectFiles.length){//存在选中的文件
			if($("body").form("validate")){							//表单验证
				var library=vm.library();	
				var libryList=[];
				
				/**组装资源列表*/
				$.each(selectFiles,function(){
					var library=$(this).data("library");
						//合并资源信息
						$.extend(library,data);						
						libryList.push(library);
					
				});
				
				if(!libryList.length){
					$.messager.alert("信息","选中文件未上传","info");
				}else{
					/**设置保存post参数*/
					var tbvCodes=getCodeArray(vm.selectedVersion());			//教材版本
					var kpCodes=getCodeArray(vm.knowledgeArray());				//知识点
					var tbcCodes=getCodeArray(vm.chapterArray());				//教材章节
					var gradeCodes=getCodeArray(vm.selectedGrade());			//年级相关 
					var subejctCodes=getCodeArray(vm.selectedSubject());		//学科相关
					var data={
						res:JSON.stringify(libryList),
						tbvCodes:arryToString(tbvCodes),
						kpCodes:arryToString(kpCodes),
						tbcCodes:arryToString(tbcCodes),
						gradeCodes:arryToString(gradeCodes),
						subejctCodes:arryToString(subejctCodes),
					};
					addCompleteLibraryInfo(data);
				}
			}else{													
				$($(".validatebox-invalid")[0]).focus().mouseover();
			}
		}else{														//未选中文件，给予提醒
			$.messager.alert("信息","请选中需要保存的文件","info");
		}
		
	}
	
	function addCompleteLibraryInfo(data){
		var opt={
				url:'/manage/res/mediaUpdate.html',
				data:data,
				type:"post",
				success:function(json){
					$.messager.alert("信息",json.msg,"info");
				}
		}
		$.ajax(opt);
	}
	
	//更新资源表 资源关联表信息
	function update(){
		var data={
				shareLevel:vm.shareLevel.val(),
				sectionCode:vm.suitableSection.val(),
				subjectCode:vm.suitableSubject.val(),
				gradeCode:vm.suitableGrade.val(),
				downloadPoints:vm.downloadPoints(),
				resDesc:vm.resDesc(),
				source:0,										//资源来源 bzt上传
				flagUnionRes:1,									//联盟资源
				flagAllowDownload:vm.flagAllowDownload.val(),	//是否允许下载
				flagAllowBrowse:vm.flagAllowBrowse.val(),		//是否允许浏览
				trialTimeRate:vm.trialTimeRate(),				//允许试看百分比
		};
		var doclist=new Array();
		doclist.push($.extend(res,data));
			if($("body").form("validate")){							//表单验证			
					/**设置保存post参数*/
					var tbvCodes=getCodeArray(vm.selectedVersion());			//教材版本
					var kpCodes=getCodeArray(vm.knowledgeArray());				//知识点
					var tbcCodes=getCodeArray(vm.chapterArray());				//教材章节
					var gradeCodes=getCodeArray(vm.selectedGrade());			//年级相关 
					var subejctCodes=getCodeArray(vm.selectedSubject());		//学科相关
					var data={
						res:JSON.stringify(doclist),
						tbvCodes:arryToString(tbvCodes),
						kpCodes:arryToString(kpCodes),
						tbcCodes:arryToString(tbcCodes),
						gradeCodes:arryToString(gradeCodes),
						subejctCodes:arryToString(subejctCodes),
					};
					addCompleteLibraryInfo(data);
			}else{													
				$($(".validatebox-invalid")[0]).focus().mouseover();
			}
		
	}
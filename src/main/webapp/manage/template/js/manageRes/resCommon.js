
	//所属学段选择事件
	function sectionChange(newValue,oldValue){
		vm.version.source([]);
		vm.version.val("");	
		var gradeList=Ajax_request("/manage/sectionGradeRef/sectionGrade.html",{sectionCode:newValue}) //查询学段年级
		vm.grade.source(gradeList);
		vm.grade.val("");
		
		var subjectList=Ajax_request("/manage/sectionSubjectRef/sectionSubject.html",{sectionCode:newValue})	//查询学段学科
		vm.subject.source(subjectList);
		vm.subject.val("");

	}
	//学科选择事件
	function subjectChange(newValue,oldValue){
		versionIni(newValue);
	}
	//是否允许浏览选择事件
	function flagAllowBrowseChange(newValue,oldValue){
		if(newValue==0){
			vm.trialTimeRate("");
		}
	}
	//版本查询
	function versionIni(subjectCode){
		var versionList=Ajax_request("/manage/textbook/getVersionBySubjectCode.html",{subjectCode:subjectCode}) //查询学段年级
		vm.version.source(defaultOptions.concat(versionList));
		vm.version.val(-1);
	}
	var resId=-1;//资源id
	function chooseKnowLedge(){
		var subjectCode=vm.subject.val();
		var versionCode=vm.version.val();
		var gradeCode=vm.grade.val();
		easyui_openTopWindow("知识点选择",800,700,"/manage/res/knowledgeChoose.html?gradeCode="+gradeCode+"&subjectCodes="+subjectCode+"&versionCodes="+versionCode+"&resId="+resId,function(r){
			if(r){
				vm.knowledgeArray(r);
				}
		})
	}
	function chooseChapter(){
		var subjectCode=vm.subject.val();
		var versionCode=vm.version.val();
		var gradeCode=vm.grade.val();
		easyui_openTopWindow("教材章节选择",800,700,"/manage/res/chapterChoose.html?gradeCode="+gradeCode+"&subjectCodes="+subjectCode+"&versionCodes="+versionCode+"&resId="+resId,function(r){
			if(r){
				vm.chapterArray(r);	
			}
		})	
	}
	//获取数组中的编码数组
	function getCodeArray(list){
		if(list==null||list.length<=0||list==undefined)return"";
		var codeArry=new Array();
		$.each(list,function(){
			codeArry.push(this.code);	
		})
		return codeArry
	}
	//把数组转成，分割的字符串
	function arryToString(array){
		if(array.length<=0||array==undefined)return "";
		return array.join()
	}
	//去除下拉框多余数据
	function removeSeleced(list,selectedList){
		if(list==undefined||list.length<=0)return [];
		if(selectedList==undefined)return list;
		for(var i=0;i<list.length;i++){
			for(var j=0;j<selectedList.length;j++){
				if(list[i].code==selectedList[j].code){
					list.splice(i,1);
				}
			}
		}
		return list;
		
	}
	/**
	 * 删除文件
	 * @param 删除文件按钮
	 */
	function deleteUploadFile(obj){
		var _delete=$(obj);
		var _fileItem=_delete.parents(".FileItem");
		var libraryIndex=_fileItem.data("index");
	
		var uniqueCode=_fileItem.attr("uniqueCode");
		
		if(_fileItem.find(".checkbox :checkbox").length){
			$.messager.confirm("提示","是否删除",function(flag){
				if(flag){
					doDelete();
				}	
			});	
		}else{
			doDelete();
		}
		
		function doDelete(){
			_fileItem.remove();
			vm.library.remove(libraryIndex);
			/* delete fileManager[uniqueCode]; */
		}
	}
	/**
	 * 获取所有选中文件
	 */
	function getSelectFiles(){
		return $(".PoyBt .Poy_yi.checkbox :checkbox:checked").parents(".FileItem");
	}

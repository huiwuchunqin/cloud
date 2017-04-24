
	//年级选择事件
	function gradeSelect(record){
		if(record.code!=-1){
			vm.selectedGrade.push(record);
			vm.grade.source.remove(record);	
			vm.grade.val(-1);
		}
	}
	//版本选择事件
	function versionSelect(record){
		if(record.code!=-1){
			vm.selectedVersion.push(record);
			vm.version.source.remove(record);	
			vm.version.val(-1);
		}
	}
	function sectionSelect(record){
		if(record.code!=-1){
			vm.selectedSection.push(record);
			vm.section.source.remove(record);	
			vm.section.val(-1);
		}
	}
	function subjectIni(){
		var sectionCodeArry=getCodeArray(vm.selectedSection());
		var subjectList=Ajax_request("/manage/textbook/getVersionInSubjectCodes.html",{subjectCodes:arryToString(subjectCodeArry)}) //查询学段年级
		vm.subject.source(defaultOptions.concat(versionList));
		vm.version.val(-1);
		vm.selectedVersion([]);
	}
	function gradeIni(){
		var subjectCodeArry=getCodeArray(vm.selectedSubject());
		var versionList=Ajax_request("/manage/textbook/getVersionInSubjectCodes.html",{subjectCodes:arryToString(subjectCodeArry)}) //查询学段年级
		vm.version.source(defaultOptions.concat(versionList));
		vm.version.val(-1);
		vm.selectedVersion([]);
	}
	//适用学段选择事件
	function suitSectionChange(newValue,oldValue){
		var gradeList=Ajax_request("/manage/sectionGradeRef/sectionGrade.html",{sectionCode:newValue}) //查询学段年级
		vm.suitableGrade.source(gradeList);
		vm.suitableGrade.val("");
		vm.grade.source(defaultOptions.concat(gradeList));
		vm.grade.val(-1);
		vm.selectedGrade([]);
		
		var subjectList=Ajax_request("/manage/sectionSubjectRef/sectionSubject.html",{sectionCode:newValue})	//查询学段学科
		vm.suitableSubject.source(subjectList);
		vm.suitableSubject.val("");
		vm.subject.source(defaultOptions.concat(subjectList));
		vm.subject.val(-1);
		vm.selectedSubject([]);
		
		vm.version.source([]);
		vm.version.val("");
		vm.selectedVersion([]);
		vm.knowledgeArray([]);
		vm.chapterArray([]);
	}
	//学科选择事件
	function subjectSelect(record){
		if(record.code!=-1){
			vm.selectedSubject.push(record);
			vm.subject.source.remove(record);	
			vm.subject.val(-1);
		}
		versionIni();		
	}
	//版本查询
	function versionIni(){
		var subjectCodeArry=getCodeArray(vm.selectedSubject());
		var versionList=Ajax_request("/manage/textbook/getVersionInSubjectCodes.html",{subjectCodes:arryToString(subjectCodeArry)}) //查询学段年级
		vm.version.source(defaultOptions.concat(versionList));
		vm.version.val(-1);
		vm.selectedVersion([]);
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

function ajaxRequest(url,data){
	var res=[];
	$.ajax({
		url : url,
		data:data,
		async:false,
		dataType : "json",
		success : function(result) {
				res=result;
		}
	});	
	return res
}
function loadData(obj,list){
	obj.combobox('loadData',list)
}
//学段学科
function orgSectionSubject(sectionCode,obj,hasDefault){
	obj.combobox('clear');
	if(sectionCode==""||sectionCode==-1){
		loadData(obj,[]);
		return ;
	}
	var result=ajaxRequest("/manage/companySubject/subjectList.html",{sectionCode:sectionCode})
	if(hasDefault){
		result.unshift({name:"请选择",code:""});	
	}
	obj.combobox('loadData',result);
}
//获取学段年级集合
function orgSectionGrade(sectionCode,obj,hasDefault){
	obj.combobox('clear');
	if(sectionCode==""||sectionCode==-1){
		loadData(obj,[]);
		return ;
	}
	var result=ajaxRequest("/manage/sectionGradeRef/sectionGrade.html",{sectionCode : sectionCode})
	if(hasDefault){
		result.unshift({name:"请选择",code:""});	
	}
	loadData(obj,result);
}
//年级行政班级
function orgGradeAdminClass(gradeCode,obj,hasDefault){
	obj.combobox('clear');
	if(gradeCode==""||gradeCode==-1){
		loadData(obj,[]);
		return
	}
	var result=ajaxRequest("/manage/adminClass/getAdminClassList.html",{gradeCode:gradeCode});
	if(hasDefault){
		result.unshift({className:"请选择",classCode:""});	
	}
	loadData(obj,result);
}
//学段学期
function orgSectionYearTerm (sectionCode,obj,hasDefault){
	obj.combobox('clear');
	if(sectionCode==""||sectionCode==-1){
		loadData(obj,[]);
		return
	}
	var result=ajaxRequest("/manage/studyYear/getOrgTermList.html",{sectionCode:sectionCode});
	if(hasDefault){
		result.unshift({name:"请选择",code:""});	
	}
	loadData(obj,result);
}
//老师和学段锁定
function teacherSectionLock(row,teacherObj,sectionObj){
	sectionObj.combobox('setValue',row.sectionCode).combobox('disable');
}
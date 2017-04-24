var selectAll = {
			code : "",
			name : "全部" 
		};

var orgSelectAll ={
		orgCode : "",
		orgName : "全部"
};
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
//给combo赋值 
function loadData(obj,result){
	if(result==undefined||result==null){
		obj.combobox("loadData",[]);
		return ;
	}
	if(obj.attr('id')=='org'){
		result.unshift(orgSelectAll);
	}else{
		result.unshift(selectAll);
	}
	obj.combobox("loadData",result);
	/*obj.combobox("select", "");*/
}
//获取学段学科集合
function sectionSubject(sectionCode,obj){
	obj.combobox('clear');
	if(sectionCode==""||sectionCode==-1){
		loadData(obj,[]);
		return ;
	} 
	var result=ajaxRequest("/manage/sectionSubjectRef/sectionSubject.html",{sectionCode : sectionCode})
	loadData(obj,result);
}
//获取学段年级集合
function sectionGrade(sectionCode,obj){
	obj.combobox('clear');
	if(sectionCode==""||sectionCode==-1){
		loadData(obj,[]);
		return ;
	}
	var result=ajaxRequest("/manage/sectionGradeRef/sectionGrade.html",{sectionCode : sectionCode})
	loadData(obj,result);
}
//学段机构
function sectionOrg(sectionCode,obj){
	obj.combobox('clear');
	if(sectionCode==""||sectionCode==-1){
		loadData(obj,[]);
		return ;
	}
	var result=ajaxRequest("/manage/company/sectionOrg.html",{sectionCode : sectionCode})
	loadData(obj,result);
}
//获取年级学科集合
function gradeSubject(gradeCode,obj){
	obj.combobox('clear');
	if(gradeCode==""||gradeCode==-1){
		loadData(obj,[]);
		return ;
	}
	var result=ajaxRequest("/manage/gradeSubjectRef/gradeSubject.html",{gradeCode : gradeCode})
	loadData(obj,result);
}
//获取学科题型
function subjectQuestionType(subjectCode,obj){
	obj.combobox('clear');
	if(subjectCode==""||subjectCode==-1){
		loadData(obj,[]);
		return ;
	}
	var result=ajaxRequest("/manage/questionType/questionTypeSubjectList.html",{subjectCode :subjectCode})
	loadData(obj,result);	
}
//获取学科教材版本
function subjectTextbookVer(subjectCode,obj){
	obj.combobox('clear');
	if(subjectCode==""||subjectCode==-1){
		loadData(obj,[]);
		return ;
	}
	var result=ajaxRequest("/manage/textbook/getVersionBySubjectCode.html",{subjectCode :subjectCode})
	loadData(obj,result);	
}
//获取学科知识体系
function subjectKps(subjectCode,obj){
	obj.combobox('clear');
	if(subjectCode==""||subjectCode==-1){
		loadData(obj,[]);
		return ;
	}
	var result=ajaxRequest("/manage/knowledgeSerial/getKpsBySubjectCode.html",{subjectCode :subjectCode})
	loadData(obj,result);	
}
//获取学科教材
function textbookVerTextbook(sectionCode,subjectCode,gradeCode,versionCode,obj){
	var result=ajaxRequest("/manage/textbook/getTextbookList.html",
						{
						subjectCode : subjectCode,
						gradeCode:gradeCode,
						sectionCode:sectionCode,
						versionCode:versionCode,
					   }
				)
	loadData(obj,result);
}
//资源分类2
function getResTyleL2(resTypeL1Code,obj){
	obj.combobox('clear');
	if(resTypeL1Code==""||resTypeL1Code==-1){
		loadData(obj,[]);
		return ;
	}
	var result=ajaxRequest("/manage/resType/resTypeL2List.html",{resTypel1 :resTypeL1Code})
	loadData(obj,result);
}

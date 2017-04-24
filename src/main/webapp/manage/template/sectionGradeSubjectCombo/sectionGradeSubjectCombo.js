 defaultOption=[{codeValue:'-1',codeName:'不限'}];
 var defaultAjax={
			type:"post",
			async:false,
			dataType:"json",
	};
function setSectionGradeSubjectCombobox(data){
	data.section.combobox("loadData",section)
	data.section.combobox({
		onSelect: select,
		onLoadSuccess:select
	});
	/* data.subject.combobox("loadData",defaultOption);
	data.grade.combobox("loadData",defaultOption); */
	
	if(data.sectionCode){
		data.section.combobox("select",data.sectionCode);
		data.section.combobox("setValue",data.sectionCode);
	}
	function select(param){
		if(param.codeValue){
			sectionCode=param.codeValue
			var loadGrade=Ajax_getChildCodeMaster("SECTION",param.codeValue,"GRADE");
			var loadSubject=Ajax_getChildCodeMaster("SECTION",param.codeValue,"SUBJECT");
			data.grade.combobox("loadData",loadGrade);
			data.grade.combobox("validate");
			data.grade.combobox({
				onSelect: gradeSelect,
			});
			if(data.gradeCode){
				data.grade.combobox("select",data.gradeCode);
				data.grade.combobox("select",data.gradeCode);
			}
			
			data.subject.combobox("loadData",loadSubject)
			data.subject.combobox("validate");
			data.subject.combobox({
				onSelect: subjectSelect,
			});
			 if(data.subjectCode){
			    	data.subject.combobox("setValue",data.subjectCode);
			    	data.subject.combobox("select",data.subjectCode);
			    }
		}
	}
	loadTagDataCombo();
	
}
function gradeSelect(param){
	gradeCode=param.codeValue;
	loadTagDataCombo();
}
function subjectSelect(param){
	subjectCode=param.codeValue;
	loadTagDataCombo();
}
function Ajax_getChildCodeMaster(parentCodeType,parentCodeValue,childCodeType){
	var obj=[];
	$.ajax(
			$.extend(defaultAjax,{
				url:"/common/resource/resourceMgt.jhtml?do=getChildCodeMaster",
				data:{parentCodeType:parentCodeType, parentCodeValue:parentCodeValue, childCodeType:childCodeType},
				success:function(json){
					obj=json;	
				}
			})
	);
	return obj;
}

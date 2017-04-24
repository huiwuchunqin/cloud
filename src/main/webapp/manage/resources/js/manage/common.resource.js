/**
 * 根据后缀分配资源类型
 * @param suffix
 */
function getResTypeBySuffix(suffix){
	var    videoType=[".f4v",".wmv",".mkv",".mov",".avi",".flv",".rm",".mp4"],
	   audioType=[".aac",".ape",".mp3",".ogg",".wav",".wma"],
	documentType=[".ppt",".pdf",".doc",".xls",".docx",".pptx",".xlsx"],
		 pptType=[".ppt",".pptx"];

	if(videoType.indexOf(suffix)>=0||audioType.indexOf(suffix)>=0){
		return "30";
	}else if(documentType.indexOf(suffix)>=0||pptType.indexOf(suffix)>=0){
		return "40";
	}else{
		return "";
	}
}

//设置标签显示隐藏
function setTag(param){
	if(!param){
		param=getSubWindowParam();
	}
	if(param.sectionGlobalCode&&param.subjectGlobalCode&&param.gradeGlobalCode&param.resType){
		vm.knowledgeList([]);
		vm.keywordList([]);
		var ary_Tag=Ajax_getTagType(param);
		var tag={};
		_.each(ary_Tag,function(tagSetting){
			if(tagSetting.tagCode==TAG_CODE_KNOWLEDGE_POINT){
				tag.knowledge=true;
			}else if(tagSetting.tagCode==TAG_CODE_KEYWORD){
				tag.keyword=true;
				vm.tag_keyword(tagSetting);
			}
		});
		vm.tag(tag);
	}
}
/**
 * 切换关键字显示状态
 */
function switchKeywordView(){
	var data=getSubWindowParam();
	if(data.sectionCode&&data.subjectCode&&data.gradeCode){
		if(vm.view()){			//当前视图为显示视图
			var keyword="";
			_.each(vm.keywordList(),function(kword){
				keyword+=kword.keyword+"  ";
			})
			vm.keyword(keyword.trim());
		}else{					//当前视图为编辑视图
			
			var ary_keyword=vm.keyword();
			if(ary_keyword){
				var keywordList=[];
				var tag_keyword=vm.tag_keyword();
				var keyword_tagInfo={
					tagID:tag_keyword.tagID,
					tagCode:tag_keyword.tagCode,
					tagSettingsId:tag_keyword.id,
					textBookVersionCode:tag_keyword.textBookVersionCode
				}
				_.each(ary_keyword.split("  "),function(kword){
					keywordList.push($.extend({
						keyword:kword
					},keyword_tagInfo));
				})
				vm.keywordList(keywordList);
			}else{
				vm.keywordList([]);
			}
		}
		vm.view(!vm.view());
	}else{
		$.messager.alert("信息","请勾选需要设置的资源，并设置学段，学科，年级","info");
	}
	
}

/**
 * 删除关键字
 */
function deleteKeyword(index){
	var keywordList=vm.keywordList();
	keywordList.splice(index,1);
	vm.keywordList(keywordList);
}

/**
 * 删除知识点
 */
function deleteKnowledge(index){
	var knowledgeList=vm.knowledgeList();
	knowledgeList.splice(index,1);
	vm.knowledgeList(knowledgeList);
}

/**=======================================================================小工具==================================================================*/
/**
 * 设置学段、年级、学科
 * @param data
 * @param func 	下拉列表改变时触发，用于控制其是否允许改变
 */
function setSectionGradeSubjectCombobox(data){
	data.section.combobox("loadData",section)
	data.section.combobox({
		onSelect: select,
		onLoadSuccess:select
	});
	/* data.subject.combobox("loadData",defaultOption);
	data.grade.combobox("loadData",defaultOption); */
	_section.combobox("setValue","-1");
	select({codeValue:"-1"});
	function select(param){
		if(param.codeValue){
			var loadGrade=Ajax_getChildCodeMaster("SECTION",param.codeValue,"GRADE");
			var loadSubject=Ajax_getChildCodeMaster("SECTION",param.codeValue,"SUBJECT");
			
			if(data.grade){
				loadGrade=defaultOption.concat(loadGrade);
			}else{
				loadGrade=defaultOption;
			}
			data.grade.combobox("loadData",loadGrade);
			data.grade.combobox("setValue","-1");
			data.grade.combobox("validate");
		
		
			if(data.subject){
				loadSubject=defaultOption.concat(loadSubject);
			}else{
				loadSubject=defaultOption;
			}
			data.subject.combobox("loadData",loadSubject)
			data.subject.combobox("setValue","-1");
			data.subject.combobox("validate");
		}
	}
	
}

/**
 * 过滤undefined的为""
 */
function replaceUndefined(str){
	if(typeof(str)=="undefined"){
		return "";
	}
	return str;
}

/**
 * 数据容量转换
 * @param bytes				字节数
 * @returns {String}
 */
function bytesToSize(bytes) {
    if (bytes === 0) return '0 B';
    var k = 1000, // or 1024
        sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
        i = Math.floor(Math.log(bytes) / Math.log(k));
   return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];
}
/**
 * 遍历用
 * @param ary				对象数组
 * @param value				比较key
 * @param name				键
 * @param returnName		返回key
 * @returns {String}
 */
function eachName(ary,value,name,returnName){
	var returnStr="";
	if(value&&typeof(ary)=="object"&&ary.length){
		$.each(ary,function(i,v){
			if(!returnStr&&v[name]==value){
				if(typeof(returnName)=="object"){
					returnStr=v;
				}else{
					returnStr=v[returnName];
				}
			}
		})
	}
	return returnStr;
}

/**
 * 
 * @param ary
 * @param value
 * @param key
 */
function getArrayIndex(ary,key,value){
	var index=0;
	$.each(ary,function(i,v){
		if(v[key]==value){
			index=i;
		}
	});
	return index;
}

/**
 * 字符串格式化
 */
String.prototype.format= function(){
    var args = arguments;
    return this.replace(/\{(\d+)\}/g,function(s,i){
      return args[i];
    });
}

/**
 * 对象比较
 */
objEquals=function(obj,otherObj){
	if(typeof(obj)=="object"&&typeof(otherObj)=="object"){
		for(key in obj){
			if(obj[key]!=otherObj[key]){
				return false;
			}
		}
	}
};
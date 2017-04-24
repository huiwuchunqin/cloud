var defaultAjax={
		type:"post",
		async:false,
		dataType:"json",
};

/**
 * 获取编码子节点
 * @param parentCodeType
 * @param parentCodeValue
 * @param childCodeType
 * @returns {Array}
 */
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

/**
 * 添加资源库记录
 * @param data			
 * @returns {___anonymous_ajax_success_obj}
 */
function Ajax_addLibrary(data){
	var obj={};
	$.ajax(
		$.extend(defaultAjax,{
			url:"/common/resource/resourceMgt.jhtml?do=addLibrary",
			data:data,
			success:function(json){
				obj=json;
			}
		})
	);
	return obj;
}

/**
 * 添加多个资源库记录
 * @param data
 */
function Ajax_addLibraryList(data,callBackFun){
	var obj={};
	$.ajax(
		$.extend(defaultAjax,{
			url:"/common/resource/resourceMgt.jhtml?do=addLibraryList",
			data:data,
			success:function(json){
				obj=json;
				if(typeof(callBackFun)=="function"){
					callBackFun(json);
				}
			}
		})
	);
	return obj;
}

/**
 * 更新资源库记录
 * @param data			
 * @param callBackFun		回调函数
 */
function Ajax_updateLibrary(data,callBackFun){
	var obj={};
	$.ajax(
		$.extend(defaultAjax,{
			url:"/common/resource/resourceMgt.jhtml?do=updateLibrary",
			data:data,
			success:function(json){
				obj=json;
				if(typeof(callBackFun)=="function"){
					callBackFun(json);
				}
			},error:function(json){
				
			}
		})
	);
	return obj;
}

/**
 * 通过资源库id进行转码
 * @param id		资源库Id
 */
function Ajax_libraryConvert(id){
	$.ajax(
		$.extend(defaultAjax,{
			url:"/common/io/fileConvert.html?do=convert",
			data:{id:id}
		})
	);
}

/**
 * 获取教材
 * {sectionCode:sectionCode,gradeCode:gradeCode,subjectCode:subjectCode,versionCode:versionCode,textbookID:textbookID,}
 */
function Ajax_getPublisher(data){
	var obj=[];
	$.ajax(
		$.extend(defaultAjax,{
			url:"/web/textbookNode/nodeList.html?do=publisher",
			data:data,
			success:function(json){
				obj=json;
			}
		})
	);
	return obj;
}


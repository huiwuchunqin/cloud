const TAG_CODE_KNOWLEDGE_POINT = "1000",		//知识点
	  TAG_CODE_KEYWORD = "1010";				//关键字

if(!window.defaultAjax){
	window.defaultAjax={
		type:"post",
		async:false,
		dataType:"json",
	};	
}

/**
 * 获取知识点根节点(出版社版)
 * @param data {resType:资源类型,sectionGlobalCode:学段代码,subjectGlobalCode:学科代码,gradeGlobalCode:年级代码,textBookVersionCode:教材版本代码}
 * @returns {Array}
 */
function getKnowledeg_initData(data){
	var obj=[];
	$.ajax($.extend(
			defaultAjax,{
				url:"/common/resource/resourceMgt.jhtml?do=getKnowledeg_point_initData",
				data:data,
				success:function(json){
					obj=json;
				}
			})
	);
	return obj;
}

/**
 * 获取知识点内容节点
 * @param data {}
 * @returns {Array}
 */
function Ajax_getKonwledeg_contentNode(data){
	var obj=[];
	$.ajax($.extend(
			defaultAjax,{
				url:"/common/resource/resourceMgt.jhtml?do=getKnowledeg_point_contentNode",
				data:data,
				success:function(json){
					obj=json;
				}
			})
	);
	return obj;
}

/**
 * 获取关键字tagSetting信息
 * @param data		{resType:资源类型,sectionGlobalCode:学段,subjectGlobalCode:学科,gradeGlobalCode:年级,tagCode:标签Code}
 * @returns {Array}
 */
function Ajax_getTagType(data){
	var obj=[];
	$.ajax($.extend(
			defaultAjax,{
				url:"/common/resource/resourceMgt.jhtml?do=getTagType",
				data:data,
				success:function(json){
					obj=json;
				}
			})
	);
	return obj;
}
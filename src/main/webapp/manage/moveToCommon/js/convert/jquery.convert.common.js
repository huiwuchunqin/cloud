/**
 * 文件信息记录，存储键值对 key:crc32文件名, {data:{library:(资源主表),child:(资源子表)},status:0:可保存}}
 */
var fileManager={};

$(function(){
	/*** 文件上传头部全选按钮*/
	$(document).on("click","[id='top_checkbox']",function(){
		var _this=$(this);
		var checked=_this[0].checked;
		var selector="[id*='item_checkbox']";
		if(checked){
			selector+=":not(:checked)";
		}else{
			selector+=":checked"
		}
		$(selector).attr("checked",!checked).click();
		fileItem_control();
	});
	
	/***文件上传删除按钮*/
	$(document).on("click","[id='upload_delete']",function(){
		var _delete=$(this);
		if(confirm("是否删除？")){
			var _fileItem=_delete.parents(".FileItem");
			_fileItem.remove();
			setTop_checkbox();
			delete fileManager[_fileItem.attr("uniqueCode")];
		}
	});
	
	$(document).on("click","[id*='item_checkbox']",function(){
		fileItem_control();
	});
	
});

/**
 * 控制板块选中
 */
function fileItem_control(){
	var ary_resType=[];				//所有选择文件的资源类型数组
	var library=vm.library();		//
	$.each($(".FileItem"),function(i,fileItem){
		var $fileItem=$(fileItem);
		var $item_checkbox=$fileItem.find("[id*='item_checkbox']");
		if($item_checkbox[0].checked){
			var resType=$fileItem.find("select.resType");
			if(ary_resType.indexOf(resType.val())){
				ary_resType.push(resType.val());
			}
		}
	});
	if(ary_resType.length>1){
		$("#upload-msg").html("您所选中的文件资源类型不一致，将无法设置知识点，关键字等...").show();
		library.resType=-1;
		vm.library(library);
		cleanKnowledegTree();
	}else{
		$("#upload-msg").html("").hide();
		library.resType=ary_resType[0];
		vm.library(library);
		if(ary_resType.length){
			refreshTag();
		}else{
			cleanKnowledegTree();
		}
	}
	
	
	
	function cleanKnowledegTree(){
		if(typeof($.fn.zTree)!="undefined"&&typeof($.fn.zTree.destroy)=="function"){				//前台用，清除知识点
			$.fn.zTree.destroy("knowledgePoint");
		}
	}
	
	function refreshTag(){
		
		if(typeof(refreshPublisher)=="function"){				//前台用，获取标签等
			refreshPublisher();
		}else{
		}
	}
}

/**
 * 获取资源内容类型
 * @param resType		资源类型
 */
function getResTypeCotnent(resType){
	return resTypeMap.resTypeContent[resType];
}

/**
 * 修改资源类型
 * @param obj
 */
function changeResType(obj){
	var _resType=$(obj),
		_resTypeContent=_resType.parent().find(".resContentTypeCode");
	/**更新当前资源类型 ko数据*/
	var library=vm.library();
	library.resType=_resType.val(); 
	vm.library(library);
	
	/**更新课程内容类型*/
	_resTypeContent.empty();
	resTypeContent=getResTypeCotnent(_resType.val());
	$.each(resTypeContent,function(index,rtCotent){
		_resTypeContent.append('<option value="'+rtCotent.codeValue+'">'+rtCotent.codeName+'</option>');
	})
	
}

/**
 * 字符串格式化
 */
if(!String.prototype.format){
	String.prototype.format= function(){
	    var args = arguments;
	    return this.replace(/\{(\d+)\}/g,function(s,i){
	      return args[i];
	    });
	}
}


/**
 * 取文件名的扩展名
 * @param filename		文件名
 * @returns
 */
function suffixFilename(filename)
{
	if(filename.indexOf(".") == -1)
	{
		return "";
	}
	return filename.substring(filename.lastIndexOf('.') );
}
/**
 * 根据uniqueCode获取文件名crc校验码
 * @param uniqueCode	文件crc校验码
 */
function getUploadFlagByUniqueCode(uniqueCode){
	var returnValue=null;
	$.each($.fn.bztUpload.crcCode,function(key,value){
		if(returnValue){
			return false;
		}
		if(uniqueCode==value){
			returnValue=key;
		}
	})
	return returnValue;
}

/**
 * 根据ID,uniqueCode，获取文件信息
 * @param ID			插件生成random
 * @param uniqueCode	文件唯一码
 */
function getFileObjByIDAndUniqueCode(ID,uniqueCode){
	var crcFileName=getUploadFlagByUniqueCode(uniqueCode),				
		fileObj={
			size:uniqueCode.substring(uniqueCode.indexOf("-")+1)
		};
	if(typeof(ID)!="undefined"){
		var fileItem="#fileUpload{0}".format(ID);
		var $poyBt =$(fileItem);
		if(!$poyBt.length){
			fileItem="#fileUpload{0}{1}".format(ID,crcFileName);
			$poyBt=$(fileItem);
		}
		var name=$poyBt.find(".FileName").text();
		$poyBt.data("uniqueCode",uniqueCode).attr("uniqueCode",uniqueCode).attr("randomId",ID);
		
		$.extend(fileObj,{
			name:name,
			type:suffixFilename(name),
			resType:$poyBt.find("select.resType").val(),
			resContentTypeCode:$poyBt.find("select.resContentTypeCode").val(),
			fileItem:fileItem
		});
	}
	
	return fileObj;
}

var i=0;



function webCheckComplete(){
	
}


/***
 * 检测文件唯一性，已存在文件进行记录
 * @param event
 * @param item
 * @param ID
 * @param uniquecode
 * @param fileObj
 * @param response
 * @returns {Boolean}
 */
function checkComplete(event, item, ID,uniquecode, fileObj,response){
	
	setTop_checkbox();
	fileObj=getFileObjByIDAndUniqueCode(ID,uniquecode);
	var $fileItem=$(fileObj.fileItem),										//文件项
		$resType=$fileItem.find("select.resType"),							//资源类型
		$resContentTypeCode=$fileItem.find("select.resContentTypeCode");	//资源类型内容
	i++;
	
	var $item_checkbox=$fileItem.find("#item_checkbox");
	/*.removeAttr("disabled")*/
	$item_checkbox.attr("id","item_checkbox"+ID);
	var $item_label=$fileItem.find("[for='item_checkbox']");
	$item_label.attr("for","item_checkbox"+ID);
	
	var resType=getResTypeBySuffix(fileObj.type);							//根据后缀分配资源类型
	$resType.val(resType).change();											//系统分配资源类型
	
	fileObj.resType=resType;
	if(response.status!=-1){////无需上传
		if(!response.data.library){
			response.data.library={};
		}
		var library=response.data.library;
		fileManager[uniquecode]=response;
		if(library.resType)$resType.val(library.resType);
		if(library.resContentTypeCode)$resContentTypeCode.val(library.resContentTypeCode);
		return false;		
	}
	return true;			//需要上传
}

/**
 * 文件上传完毕回调
 * @param event
 * @param item
 * @param ID
 * @param fileObj
 * @param response
 * @param data
 */
function saveToFileManager(event, item, ID,fileObj, response, data) {
	var id=0,			/**资源库id*/
		library=null,   /**资源库记录 对象*/
		child=null;     /**资源子表记录*/
	$("[randomid='"+ID+"']").data("submit","");
	var	uniqueCode=$.fn.bztUpload.crcCode[crc32(fileObj.name)]; //文件唯一码
		fileObj=getFileObjByIDAndUniqueCode(ID,uniqueCode);		//获取资源类型信息等
	
	$(fileObj.fileItem).find("[id*='item_checkbox']").removeAttr("disabled");
	if(typeof(response.MSG)=="undefined"){//非秒传
		var res = eval("("+unescape(response)+")");//上传完毕数据仓库返回值
		
		if(res.state&&res.data&&res.data[0]){/**是否成功上传*/
			var file=res.data[0],			//获取文件信息
				library={};					//传送至后台 library json字符串
			
			
			/**资源库记录拼装*/
			library={
						resourceName:fileObj.name,						//资源名称
						resType:fileObj.resType,						//资源类型，根据后缀区分
						resContentTypeCode:fileObj.resContentTypeCode,	//资源内容类型
						viewLevel:60									//完全
			};
			
			/**资源子表记录拼装*/
			child={
						objectId:file.id,							//mangoDB id
						uniqueCode:uniqueCode,						//文件唯一码-文件大小
						resStatus:10,								//资源状态
						resSize:fileObj.size,						//文件大小
						suffix:fileObj.type,						//后缀
						resName:fileObj.name						//资源名称
			};
			
			/**存入文件管理*/
			fileManager[uniqueCode]={
					data:{library:library,child:child},
					status:0
			};
			setUploadSuccess();
		}else{//上传失败
			setUploadSuccess(res.msg);
		}
	}else{
		var file=fileManager[uniqueCode];
		if(file.data){
			file.data.library={resourceName:fileObj.name};
		}
		setUploadSuccess();
	}
	
	function setUploadSuccess(msg){
		if(!msg){
			msg="文件上传完毕，可以保存";
		}
		$(fileObj.fileItem).find(".ProgressBarMsg").html(msg);
	}
}


/**
 * 文件保存完毕回调
 * @param json
 */
function saveFilesCallBack(json){
	setTop_checkbox(true);
	if(json.data){
		$.each(json.data,function(key,value){
			fileManager[key].data.library.id=value;
			var _fileItem=$(".FileItem[uniqueCode='"+key+"']");
			_fileItem.find("#item_checkbox:checkbox").remove();
			_fileItem.find(".ProgressBarMsg").text("文件已保存");
		})
	}
	setTop_checkbox();
}

/**
 * 设置全选按钮状态
 * @param flag		去除选中
 */
function setTop_checkbox(flag){
	var $item_cb=$("[id*='item_checkbox']:gt(0)");				//文件上传列表
	var $top_cb=$("#top_checkbox");								//全选按钮
	if(flag){													//取消全选
		cancelTop_cb();
	}else{
		if($item_cb.length){									//存在文件可保存的文件列表
			$top_cb.removeAttr("disabled");
		}else{													//禁用全选按钮，取消选中状态
			$top_cb.attr("disabled");
			cancelTop_cb();
		}
	}
	
	/**取消全选*/
	function cancelTop_cb(){									
		$top_cb[0].checked=false;
	}
}
	
//ajax请求
function Ajax_request(url,data){
	var result=[];
	$.ajax({
		url:url,
		data:data,
		async :false,
		type:'post',
		success:function(json){
			result=json;
		}
})
return result;
}

	function checkComplete(event, item, ID,uniquecode, fileObj,response){
        $('#uploadAllBtn').attr("disabled","disabled");
		$('#uploadAllBtn').text("文件分析中"); 
		var result = true;
		var data  = response.data;
		var fileUploadData=$("#fileUpload"+ID+"ProgressBarMsg");//用于写入文件信息		
		if (data!=undefined&&typeof(data.objectId)!="undefined") {//资源库存在该文件
			fileUploadData.attr("resCode",data.resCode);
			fileUploadData.text("服务器上已经存在该文件,可以秒传!");
			//写入文件转码状态
			if (data.resStatus=="10"||typeof(data.resStatus)=="undefined"){//存在文件，但未转码
				fileUploadData.attr("resStatus","10");
			}else if(data.resStatus=="20"){//已转码
				fileUploadData.attr("resStatus","20");
			}
			//写入文件信息
			fileUploadData.attr("objectId",data.objectId);
			fileUploadData.attr("fastFlag",true);
			//快速上传标示
			//返回false，为快速上传
			result = false;
			fileUploadData.data("objectId",data.objectId).data("fileObj",data);
			
			console.debug("服务器已存在文件 objectId:",data.objectId,"crccode",data.resCode,"无需继续上传");
		}else{
			fileUploadData.text("文件分析完毕，可以上传...");
		}
		i++;
		if(i>=$("ul.FileItem").length-1){
			 $('#uploadAllBtn').removeAttr("disabled");
			 $('#uploadAllBtn').text("上传"); 
			
		}
		//返回true，继续上传，返回false 则不继续上传
		return result; 
	}
	//上传完毕的事件
	function onFileComplete(event,item,ID,fileObj,response,data){	
		if (response && response !== "") {
			var fileUploadData=$("#fileUpload"+ID+"ProgressBarMsg");//获取之前写入的文件信息
			var fileItem=$("#fileUpload"+ID);
			var objectId = "";//MongoDB ID
			var crcCode = "";//uniqeueCode处理后生成
			var convertStatus=0;
			delete fileItem.data().submit;
			if (fileObj == null) fileObj = {};
			if(fileUploadData.attr("fastFlag")){//快速上传
				convertStatus=fileUploadData.attr("resStatus");//拿到文件转码状态
				objectId=fileUploadData.attr("objectId");//拿到文件objectId
				crcCode=fileUploadData.attr("resCode");
				var file_Obj=fileUploadData.data("fileObj");
				fileObj["objectId"] = objectId;
				fileObj["resName"] = fileObj.name;
				fileObj["type"] = file_Obj.suffix;
				fileObj["size"] = file_Obj.resSize;
				fileObj["resCode"] = crcCode
			}else{
				var	uniqueCode=$.fn.bztUpload.crcCode[crc32(fileObj.name)]; //文件唯一码
				var res = eval("("+unescape(response)+")");
				if (res && res["data"] && res["data"].length > 0) {
					objectId = res["data"][0]["id"];
					crcCode=uniqueCode;			
					fileObj["objectId"] = objectId;
					
				}
			}
			   rescontent = {
					'objectId' : objectId,
					'resName' : fileObj.name,
					'resSize' : fileObj.size,
					'resCode' : crcCode,
					'suffix':fileObj.type
			};
			
			fileUploadData.text("上传成功");
			fileUploadData.parents(".FileItem").find(".CancelBtn").click();
		}
		saveResInfo(rescontent,fileItem);
	}
	//保存资源信息
	function saveResInfo(rescontent,fileUploadData){
		var opt={
				url:resInfoAddUrl,
				data:{res:JSON.stringify(rescontent)},
				type:'post',
				success:function(res){
					rescontent.id=res.data;
					vm.library.push(rescontent);
					fileUploadData.data("index",vm.library.length);
					fileUploadData.data("library",rescontent);
				/*$.messager.alert("信息",res.msg,"info")	*/
				}
		}
		$.ajax(opt);
		
	}
	
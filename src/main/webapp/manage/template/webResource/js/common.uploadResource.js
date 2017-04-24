/**
 * 视频封面上传
 */
function imgFile(fileInput,form,callback){
	var file=$(fileInput).val();
	var filename=file.replace(/.*(\/|\\)/, "");  
	var fileExt=(/[.]/.exec(filename)) ? /[^.]+$/.exec(filename.toLowerCase()) : ''; 
	if(fileExt=="jpg"||fileExt=="jpeg"||fileExt=="gif"||fileExt=="png"){
		$(form).ajaxSubmit({
            type: 'post',
            url: "/web/resource/resUpload.jhtml?do=uploadCover" ,
            success: function(data){
                if(typeof(callback)=="function"){
                	callback(data);
                }else{
                	if(!data.status){
                        vm.coverPath(imgHost+data.data);
                    }else{
                    	alert(v.msg);
                    	/* $.alert({mask:true, msg:v.msg, title:"提示"}); */
                    }
                }
            },
            error: function(XmlHttpRequest, textStatus, errorThrown){
            	alert("上传失败，请重试");
            	//$.alert({mask:true, msg:"程序异常，请重试！", title:"提示"});
            }
        });
	}else{
		alert("文件格式错误，只支持jpg、jpeg、gif、png格式的图片上传！");
		//alert({mask:true, msg:"文件格式错误，只支持jpg、jpeg、gif、png格式的图片上传！", title:"提示"});
	}
}
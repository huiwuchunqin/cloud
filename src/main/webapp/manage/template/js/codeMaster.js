$.fn.loadMaster =function (codeType,url){
	var object=$(this);
	var opt={
			url:url,
			data:{
				codeType:codeType,
			},
			type:'post',
			success:function(res){
				var json=res;
				object.combobox('loadData',res);
			}	
	}
	$.ajax(opt);
}
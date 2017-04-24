//商品级别格式化
function levelfmt(value){
	if(value==20){
		return "机构";
	}else if(value==30){
		return "区域";
	}else{
		return value;
	}
}

//业务类型格式化
function typefmt(value){
	if(value==10){
		return "兑换商品";
	}else if(value==20){
		return "抽奖商品";
	}else{
		return value;
	}
}

//类型格式化
function resTypefmt(value, row) {
	var str = "";
	if (value == 10) {
		str = "视频";
	} else if (value == 20) {
		str = "文档";
	} else if (value == 11) {
		str = "特色资源";
	} else if (value == 12) {
		str = "音频";
	}  else if (value == 15) {
		str = "互动资源";
	} else if (value == 30) {
		str = "测验";
	} else {
		str = "题库";
	}
	return str;
}
//共享级别格式化
function shareLevelfmt(value,row){
	var result="";
	if(value=="10"){
		result="个人私有";
	}else if(value=="20"){
		result="校内共享";
	}else if(value=="30"){
		result="区域共享";
	}
	return result;
}
//时间格式化
function timefmt(value){
	return value?moment(value).format("YYYY-MM-DD"):"";
}
//时间格式化
function timeLongfmt(value){
	return value?moment(value).format("YYYY-MM-DD HH:mm"):"";
}

//长度截取格式化
function lengthSubfmt(value,row){
	if(value!=void(0)&&value.length>30){
		value=value.substring(0,50)+"...";
	}
	return value;
	
}
function pecentfmt(value,row){
	if(_.isNumber(Number(value))){
	 return (Math.round(value*10000)/100).toFixed(2)+"%";
	}else{
		return "";
	}
}


//审核意见详情
function detailPreviewfmt(value,row){
	var detail=row.checkComments
	if(detail){
		detail=detail.replace(/\n/g,"");	
	}else{
		detail="";
	}
	return "<a href='javascript:void(0)' onclick='detailPrevew(\""+detail+"\")'>审核意见详情</a>"	
}

//详情显示
function detailPrevew(checkComments){
	easyui_openTopWindow("审核意见详细", 600, 300,
			"/manage/resMediaSpecial/detail.html", function(r) {
			}, {checkComments:checkComments});
}

//代理商级别格式化
function agencyLevelfmt(value,row){
	if(_AGENCY_LEVEL){
		var selectedItem=_.find(_AGENCY_LEVEL,function(item){
			return item.value==value;
		})
		return selectedItem?selectedItem.name:""
	}else{
		return "";
	}
}

function orgStatefmt(value,row){
	if(value==1){
		return "有效"
	}else{
		return "无效";
	}
}
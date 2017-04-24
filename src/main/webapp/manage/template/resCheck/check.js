// 资源审核
function examine(examineCode) {
	if($('#dg').length>0){
		var rows = $('#dg').datagrid('getSelections');
		if (rows.length <= 0) {
			alert("请选择审核的数据");
			return false;
		}
	}
	$("#comment").val("");
	$('#commentSelect').find("option:eq(0)").attr("selected","selected");
	if (examineCode == 20) {
		model.deny(false);
	} else {
		model.deny(true);
		var adviceList=getAdviceList();
		if(!model.adviceList){
			model.adviceList=ko.observableArray();
		}
		model.adviceList(adviceList||[]);
	}
	$("#commentCheck").attr("checked",false);
	if (examineCode == 20) {
		$('#dlg').dialog({
			title : "审核意见(非必填)"
		}).dialog('center').dialog('open');
	} else {
		$('#dlg').dialog({
			title : "审核意见(必填)"
		}).dialog('center').dialog('open');
	}
	
}
//checkbox选择事件
function checkChange(obj) {
	var check = ($(obj).attr("checked") == "checked");
	if (check) {
		$('#comment').val($(obj).next().val());
	} else {
		$('#comment').val("");
	}
}
//下拉框选择事件
function selectChange(obj) {
	var check = ($(obj).prev().attr("checked") == "checked");
	if (check) {
		$('#comment').val($(obj).val());
	}
}
// 保存审核
function saveExamine(type) {
	if($('#dg').length>0){
		var rows = $('#dg').datagrid('getSelections');
		var codesArray = _.pluck(rows, "resCode");
		if(type==60){
			codesArray = _.pluck(rows, "lessonCode");
		}
	}else{
		if(_res){
			var codesArray=[_res.resCode];
			type=_res.resTypeL1;
		}else{
			return false;
		}
		
	}
	var data = {};
	data.checkCode = model.deny() ? "05" : "20";
	data.comment = $.trim($("#comment").val());
	if(data.checkCode=="05"&&data.comment==""){
		alert("请输入退回原因");
		$("#comment").focus();
		return false;
	}
	data.resCodes = codesArray;
	data.resType = type;
	$.ajax({
		url : '/manage/res/checkRes.html',
		data : data,
		success : function(res) {
			$.messager.alert("信息", res.msg, "info");
			if(typeof update=="function"){
				update();
			}
			if (res.success) {
				if($('#dg').length>0){
					query();
				}
				$('#dlg').dialog('close');
				easyui_closeTopWindow(true);
			}
		}
	})
}

function getAdviceList(){
	var result;
	var res= $.ajax({
		url:'/manage/advice/list.html',
		async:false,
		success:function(res){
			result= res;
		}
	})	
	return result;
}
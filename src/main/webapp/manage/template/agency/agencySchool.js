$(function(){
	sectionList.unshift({name:"全部",code:""});
	$('#sectionCode').combobox('loadData',sectionList);
	
})
function onBeforeLoad(param){
	$.extend(param,getParam("queryBox"));
}


function optfmt(value,row){
	//格式化显示的字符 当前状态启用 则显示禁用 
	var str=row.flagValid!=_FLAG_COMPANY_VALIDAYE_YES?"启用":"禁用";
	//新状态
	var newState=row.flagValid==_FLAG_COMPANY_VALIDAYE_YES?_FLAG_COMPANY_VALIDAYE_NO:_FLAG_COMPANY_VALIDAYE_YES;
	return "<a href='javascript:void(0)' onclick='edit(this)'>编辑 </a>&nbsp;&nbsp;"
		+"<a href='javascript:void(0)' onclick='updateState("+newState+",\""+row.orgCode+"\")'>"+str+"</a>"
}


function searchData(){
	$('#dg').datagrid('reload');
}

//新增
function add(){
	
	easyui_openNoResizeWindow("机构新增",1000,600,"/manage/agency/toAgencySchoolAdd.html",function(){
		$('#dg').datagrid('reload');
	})
	
}

//编辑
function edit(object){
	
	var row=getRow(object);
	easyui_openNoResizeWindow("机构修改",1000,600,"/manage/agency/toAgencySchoolEdit.html?orgCode="+row.orgCode,function(){
		$('#dg').datagrid('reload');
	})
	
}

//更新机构状态
function updateState(state,orgCode){
	$.messager.confirm("提示","你确定要更改吗?",function(r){
		if(r){
			$.post(
					"/manage/agency/updateState.html",
					{
						flagValid:state,
						orgCode:orgCode,
					},
					function(res){
							alert(res.msg,function(){
									$('#dg').datagrid('reload')
									})
							}
				)
			
		}
	})
} 
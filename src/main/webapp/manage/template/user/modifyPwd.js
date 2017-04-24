	function onRowContextMenu(e,rowIndex,rowData){
 		e.preventDefault();
		$('#menu').menu('show', {    
			  left: e.pageX,    
			  top: e.pageY-50    
			}).menu({
				onClick : function(item) {
					optRow(item.name,rowData);
				}
			});
	} 
 	//右击菜单操作
 	function optRow(name,rowData){
 		var userCode=rowData.userCode||rowData.studentCode||rowData.teacherCode;
 		console.info(userCode);
 		if(name=="useful"){
 			update(userCode,1);
 		}else if(name=="useless"){
 			update(userCode,0);
 		}else if(name="modifypwd"){
 			modifypwd(userCode);
 		}
 	}
	//修改密码
	function modifypwd(userCode){
		easyui_openNoResizeWindow("重置密码",550,260,"/manage/toPwdEdit.html?userCodes="+userCode,function(r){
			if(r){
				$('#dg').datagrid('reload');
			}
		})
	}
	//批量修改密码
	function batchModifyPwd(userCodes){
		easyui_openNoResizeWindow("重置密码",550,260,"/manage/toPwdEdit.html?userCodes="+userCodes,function(r){
			if(r){
				$('#dg').datagrid('reload');
			}
		})
	}
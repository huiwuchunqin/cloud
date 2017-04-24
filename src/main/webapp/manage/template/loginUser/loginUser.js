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
 		if(name=="useful"){
 			update(rowData.userCode,1);
 		}else if(name=="useless"){
 			update(rowData.userCode,0);
 		}else if(name="modifypwd"){
 			modifypwd(rowData.userCode);
 		}
 	}
	//修改密码
	function modifypwd(userCode){
		easyui_openNoResizeWindow("密码修改",550,240,"/manage/toPwdEdit.html?userCodes="+userCode,function(r){
			if(r){
				$('#dg').datagrid('reload');
			}
		})
	}
	//修改管理员信息
	function modify(userCode){
		easyui_openNoResizeWindow("管理员信息修改",550,250,"/manage/toLoginUserEdit.html?userCode="+userCode,function(r){
			if(r){
				$('#dg').datagrid('reload');
			}
		})
	}
	//更新登录用户状态
	function update(userCode,status){
		var opts={
				url:'/manage/updateState.html',
				data:{
					userCode:userCode,
					status:status
				},
				type:'post',
				success:function(res){
					$.messager.alert("信息",res.msg,"info",function(){
						if(res.success){
							reload();
						}
					});
				}
			}
		$.ajax(opts);
	}
	
	//状态格式化
	function statusfmt(value){
		if(value==0){
			return "无效";
		}else if(value==1){
			return "有效"; 
		}else if(value==2){
			return "锁定";
		}else{
			return ""
		}
	}
	/* //删除登录用户信息
	function del(){
		var rows=$('#dg').datagrid('getChecked');
		var gids=new Array();
		if(rows==null||rows.length<=0){
			$.messager.alert("信息","请先选中你要删除的数据","info")
			return false;
		}
		$.each(rows,function(i,obj){
			console.info(obj);
			gids.push(obj.gid);
		})
		var opt={
				url:'/manage/delLoginUser.html',
				data:{
					gids:gids,
				},
				type:'post',
				success:function(res){
					$.messager.alert("信息",res.msg,"info",function(){
						reload();
					});
				}
			}
		$.messager.confirm("提示","你确定要删除选中的用户信息吗？",function(r){
			if(r){
				$.ajax(opt)
			}
		})
	} */
	
	//操作格式化
	function optfmt(value,row){	
		if("${adminUser.userCode}"!=row.userCode){
			var str= "<a href='javascript:void(0)' onclick='modify(\""+row.userCode+"\")'>修改</a>&nbsp;&nbsp;<a href='javascript:void(0)' onclick='recall(\""+row.userCode+"\")'>撤销</a>";
			return str;
		}
		
	}

	//刷新
	function reload(){
		$('#dg').datagrid('reload');
	}
		const defaultOpt={name:"不限",code:'-1'};
		//ajax请求
		function Ajax_request(url, data) {
			var result = [];
			$.ajax({
				url : url,
				data : data,
				async : false,
				type : 'post',
				success : function(json) {
					result = json;
				}
			})
			return result;
		}
		//知识点标注格式化
		function kpfmt(value){
			return (value&&value>0)?"已标注":"";
		}
		//章节标注格式化
		function tbcfmt(value){
			return (value&&value>0)?"已标注":"";
		}
		//资源状态
		function statusfmt(value,row){
			var str="";
			if(row.value=="10"){
				str="<center><span>转码中</span></center>";
			}else if(value=="20"){
				str="<center style='color:green'><span>转码成功</span></center>";
			}else if(value=="30"){
				str="<center style='color:red'><span>转码失败</span></center>";
			}else{
				str="转码中";
			}
			return str;
		}

		//审核状态格式化
		function checkStatusfmt(value){
			 if ("10"==value) {
		       return "待审核";
		   } else if ("20"==value) {
		       return "已通过";
		   } else if ("05"==value||"5"==value) {
		       return "<span style='color:red'>已退回</span>";
		   } else {
		       return "<span style='color:grey'>未提交</span>";
		   }
		}
		
		
		//改变ui
		function UIChange(checkStatus){
			var $span=$(".datagrid-header-row td[field='shareCheckTime']").find("span");
			if(checkStatus=="10"){
				$span=$(".datagrid-header-row td[field='shareTime']").find("span");
				$span.html("申请时间");
				$('#timeQuery').html("申请日期");
			}else if(checkStatus=="20"){
				$span.html("审核时间");
				$('#timeQuery').html("审核日期");
			}else if(checkStatus=="05"){
				$span.html("审核时间");
				$('#timeQuery').html("审核日期");
			}
		}
		//学段下拉框选择事件 
		function sectionSelect(record) {
			sectionGrade(record.code,_gradeCombo);
			sectionSubject(record.code,_subjectCombo)
		}
		//学科题型
		function subjectChange(newValue,oldValue){
			subjectQuestionType(newValue,_questionTypeCombo);
		}
		
		//查询
		function query() {
			$('#dg').datagrid('reload');
		}
		// 预览
		function preview(url) {
			easyui_openTopWindow("资源预览", 825, 800, url);
		}
		//删除资源
		function deleteRes(resType) {
			var rows = $('#dg').datagrid('getSelections');
			var ids = new Array();
			if (rows == undefined || rows.length <= 0) {
				$.messager.alert("信息", "请选择要删除的资源", "info")
				return;
			}
			$.each(rows, function(i, data) {
				ids.push(data.id);
			})
			$.messager.confirm("信息", "确认要删除资源吗? ", function(r) {
				if (r) {
					$.post("/manage/res/deleteRes.html", {
						ids : ids.join(","),
						resType : resType
					}).then(function(result){
						$.messager.alert("信息", result.msg, "info", function() {
							if (result.success) {
								query()
							}
						})
					})
				}
			})
		}
		

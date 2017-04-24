	

//ztree 配置
var knowLedgeSetting = {
	data: {
		simpleData: {//设置简单数据格式
			enable: true,
			idKey: "code",
			pIdKey: "pcode",
			rootPId: null
		},
		key: {//设置节点显示key
			name: "name"
		}
	},view:{//去除图标
		showTitle:true,
		showIcon:false,
	},async: {//异步加载数据
		enable: true,
		type:'post',
		contentType:"application/x-www-form-urlencoded",  
		url: "/manage/textbook/getContentKnowledge.html",
		autoParam: ["subjectCode","textbookVerCode","code"]
	},callback: {
		onClick: knowledgeZTreeClick,
		
	}
};

function initKnowledge(param){
	var ztreeData=getKnowledeg_initData(param);
	if(ztreeData.length){
		knowledgeZtreeObj=$.fn.zTree.init($("#knowLedgeZtreeNode"), knowLedgeSetting,ztreeData);
		/*treeStateInitial("knowledgeZtreeNode",selectedList);*/
		model.knowledgeShow(true);
	}else{
		$.messager.alert("信息","当前学科教材版本下没有知识点。","info");
		$(".icon-save").parents(".easyui-linkbutton").hide();
		model.knowledgeShow(false);
	}
}
function getKnowledeg_initData(param){
	var obj=[];
	$.ajax($.extend(
			defaultAjax,{
				url:"/manage/textbook/getKnowledgeRoot.html",
				data:param,
				success:function(json){
					obj=json;
				}
			})
	);
	return obj;
}

function setFontCss(treeId,treeNode){
	if(treeNode.nodeType=="knowledgeSerial"){
		return {"color":"#ff6c00","font-family":"SimSun","font-weight":"bold","size":"10px","weight":"20px"};
	}
	
}
/*function knowledgeSelectedIni(){
	if(selectedKnowledge==undefined||selectedKnowledge==null||selectedKnowledge=="")return;
	_.each(selectedKnowledge,function(node){
		var _node=knowledgeZtreeObj.getNodeByParam("code",node.code, null)
		knowledgeZtreeObj.selectNode(_node,true);
		knowledgeAry.push(node.code);
	})
}*/
function knowledgeZTreeClick(){
	var checkedNodes=[];
	//获取所有被选中的最底层节点
	_.each(knowledgeZtreeObj.getSelectedNodes(),function(checkedNode,index){
		if(checkedNode.rel!="rootNode"&&checkedNode.nodeType!="knowledgeSerial"){										//非根节点
			if(!checkedNode.children){											//没有子节点，加入数组
				checkedNodes.push(checkedNode);
			}else{
				if($("#"+checkedNode.tId).find(".curSelectedNode").length==1){  //虽然有子节点，但子节点没有被选中，加入数组
					checkedNodes.push(checkedNode);
				}
			}
		}
	});
	knowledgeAry=[];
	_.each(checkedNodes,function(checkedNode){
		knowledgeAry.push(checkedNode.code);
	});
}
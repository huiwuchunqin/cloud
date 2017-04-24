

//ztree 配置
var chapterSetting = {
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
		showIcon:false,
		showTitle:false,
	},async: {//异步加载数据
		enable: true,
		type:'post',
		contentType:"application/x-www-form-urlencoded",  
		url: "/manage/textbook/getContentVersion.html",
		autoParam: ["code"]
	},callback: {
		onClick: chapterZTreeClick,
	}
};
//初始化
function initChapter(param){
	var ztreeData=getChapter_initData(param);
	if(ztreeData.length){
		chapterZtreeObj=$.fn.zTree.init($("#chapterZtreeNode"), chapterSetting,ztreeData);
		model.chapterShow(true);
		
		/*treeStateInitial("chapterZtreeNode",selectedList);*/
	}else{
		$.messager.alert("信息","当前学科,教材版本下没有教材章节。","info");
		$(".icon-save").parents(".easyui-linkbutton").hide();
		model.chapterShow(false);
	}
}
/*//选中所有已选节点
function chapterSlectedIni(){
	if(selectedChapter==undefined||selectedChapter==null||selectedChapter=="")return;
	_.each(selectedChapter,function(node){
		var _node=chapterZtreeObj.getNodeByParam("code",node.code, null)
		chapterZtreeObj.selectNode(_node,true);
		chapterAry.push(node.code);
	})
}*/
function setFontCss(treeId,treeNode){
	if(treeNode.nodeType=="textbook"){
		return {"color":"#ff6c00","font-family":"SimSun","font-weight":"bold","size":"10px","weight":"20px"};
	}
	
}
function getChapter_initData(param){
	var obj=[];
	$.ajax($.extend(
			defaultAjax,{
				url:"/manage/textbook/getVersionRoot.html",
				data:param,
				success:function(json){
					obj=json;
				}
			})
	);
	return obj;
}
function chapterZTreeClick(){
	var checkedNodes=[];
	//获取所有被选中的最底层节点
	_.each(chapterZtreeObj.getSelectedNodes(),function(checkedNode,index){
		if(checkedNode.rel!="rootNode"&&checkedNode.nodeType!="textbook"){										//非根节点
			if(!checkedNode.children){											//没有子节点，加入数组
				checkedNodes.push(checkedNode);
			}else{
				if($("#"+checkedNode.tId).find(".curSelectedNode").length==1){  //虽然有子节点，但子节点没有被选中，加入数组
					checkedNodes.push(checkedNode);
				}
			}
		}
	});
	chapterAry=[];
	_.each(checkedNodes,function(checkedNode){
		chapterAry.push(checkedNode.code);
	});
}
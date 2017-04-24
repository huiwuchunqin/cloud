
var inilIndex=0;//当前在操作的节点下表
var nextVo="";//节点的下一个父节点对象
var currentParentList;//当前节点的父节点
var  defaultAjax={
		type:'post',
		async:false,
}

function treeStateInitial(id,list){
		if(list==null||list.length<=0||list==undefined)return false;
		var treeObj=$.fn.zTree.getZTreeObj(id);
		if(treeObj==null||treeObj==undefined)return false;
		selectNode(treeObj,list[inilIndex])
	}

	function onKnowledgeAsyncSuccess(){
		if(nextVo!=null&&nextVo!=undefined){
			var treeObj=$.fn.zTree.getZTreeObj("knowLedgeZtreeNode");
			expendParent(treeObj,nextVo);	
		}	
	}
	function onChapterAsyncSuccess(){
		if(nextVo!=null&&nextVo!=undefined){
			var treeObj=$.fn.zTree.getZTreeObj("chapterZtreeNode");
			expendParent(treeObj,nextVo);	
		}	
	}
	//选中节点
	function selectNode(treeObj,vo){
			topParent=vo.topParent;
			if(topParent==null||topParent==undefined){
				var node = treeObj.getNodeByParam("code",vo.code, null);	
				treeObj.selectNode(node,true);
				nextVo=undefined;
				if(inilIndex<selectedList.length-1){
					inilIndex++;
					selectNode(treeObj,selectedList[inilIndex]);	
				}			
			}else{
				expendParent(treeObj,topParent);
			}
	}
	//把某个节点的父节点从上往下展开,并选中节点
	function expendParent(treeObj,vo){
		if(vo.child==null||vo.child==undefined){							//没有父节点了就点击自己
			var node = treeObj.getNodeByParam("code",vo.code, null);	
			treeObj.selectNode(node,true);
			nextVo=undefined;
			if(inilIndex<selectedList.length-1){
				inilIndex++;
				selectNode(treeObj,selectedList[inilIndex]);	
			}			
		}else{															//展开父节点
			var pNode = treeObj.getNodeByParam("code",vo.code, null);		
			var isOpen = pNode.open;
			nextVo=vo.child;
			if(!isOpen){
				treeObj.expandNode(pNode, true , false,true);					
			}else{
				expendParent(treeObj,nextVo);
			}			
		}
	}
	/**
	 * 展开ztree节点
	 * @param node		ztree节点
	 */
	function switchNode(tId,flag){
		var selector="#"+tId+"_switch";
		if(flag){
			selector+="[class$='close']";
		}else{
			selector+="[class$='open']";
		}
		$(selector).click();
	}
	

	 
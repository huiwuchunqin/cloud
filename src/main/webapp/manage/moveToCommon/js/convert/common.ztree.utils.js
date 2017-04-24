/**
 * ztree 反选用
 * @param event
 * @param treeId
 * @param treeNode
 * @param msg
 */
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg){
	if(typeof(treeNode.callBack)=="function"){
		treeNode.callBack();
	}
	if(typeof(treeNode.callBack)=="object"){
		$.each(treeNode.callBack,function(i,v){
			if(typeof(v)=="function"){
				v();
			}
		})
	}
}

/**
 * 根据id获取根节点
 * @param id		id
 * @param zTree		ztree对象
 * @returns
 */
function getRootNodeById(id,zTree){
	var r_node=null;
	var idKey=zTree.setting.data.simpleData.idKey;
	_.each(zTree.getNodes(),function(node,index){
		if(!r_node&&node[idKey]==id){
			r_node=node;
		}
	});
	return r_node;
}

/**
 * 展开与闭合ztree节点
 * @param tId		ztree节点tid(dom Id)
 * @param flag		展开or闭合
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

/**
 * 展开ztree节点的所有父节点
 * @param node		ztree节点
 */
function switchFartherZtreeNode(node){
	var flag=node.getParentNode()!=null;
	var pNode=node;
	var tId=[];
	while(flag){
		tId.push(pNode.tId);
		flag=pNode.getParentNode()!=null;
		if(flag){
			pNode=pNode.getParentNode();
		}
	}
	
	_.each(tId.reverse(),function(v_tId){
		switchNode(v_tId,true);
	});
}

/**
 * 选中节点
 * @param node		ztree节点
 * @param flag 		选中or取消选中
 */
function selectNode(node,flag){
	var selector="#"+node[0].tId+"_a";
	if(flag){
		selector+=":not(.curSelectedNode)";
	}else{
		selector+=".curSelectedNode";
	}
	$(selector).click();
}
/**
 * 反选知识点
 * @param data				知识点列表
 * @param zTree				ztree对象
 */
function reverseKnowledegSelect(data,zTree){
	_.each(data,function(tagRel,index){
		var zte_tagSet=getRootNodeById(tagRel.tagSettingsId,zTree);		//获取tagsettings
		/**加载完tagSettings后回调*/
		if(!zte_tagSet||!zte_tagSet.callBack||!zte_tagSet.callBack.length){ 		    
			zte_tagSet.callBack=[];
		}
		zte_tagSet.callBack.push(
			function(){
				var zte_tagRel=getTagContentById(tagRel.tagContentID);
				switchFartherZtreeNode(zte_tagRel);
				selectNode(zte_tagRel,true);
			}
		);
		
		switchNode(zte_tagSet.tId,true);							//异步加载tagContent
	});
	
	function getTagContentById(id){
		var tagContent=null;
		var ztreeNodes=ztreeObj.getNodesByParam('id',id);
		_.each(ztreeNodes,function(node,index){
			if(node.tagSettingsId){
				tagContent=node;
			}
		});
		return tagContent;
	}
}

/**
 * 反选教材
 * @param textbookList		教材列表
 * @param zTree				ztree对象
 */
function reverseTextbookSelect(textbookList,zTree){
	$.each(textbookList,function(i,tb_rel){
		var publisher=getPublisherNode(tb_rel.publisherCD);
		switchNode(publisher.tId,true);
		
		if(publisher.cildren){
			var tId="";
			var textbook=getTextbookNode(publisher.children,tb_rel.textbookID);
			if(tb_rel.textbookNodeID){
				var node=zTree.getNodesByParam('id',tb_rel.textbookNodeID,textbook);
				tId=node.tId;
			}else{
				tId=textbook.tId;
			}
			clickZtreeNodeByTid(tId);
		}else{
			var pCallBack=[];
			if(publisher.callBack){
				pCallBack=publisher.callBack;
			}
			pCallBack.push(
					function(){
						var textbook=getTextbookNode(publisher.children,tb_rel.textbookID);
						if(textbook){
							var callBack=[];
							if(textbook.callBack){
								callBack=textbook.callBack;
							}
							callBack.push(
									function(){
										if(tb_rel.textbookNodeID){
											var node=zTree.getNodesByParam('id',tb_rel.textbookNodeID,textbook);
											switchFartherZtreeNode(node[0]);
											selectNode(node,true);
										}else{
											selectNode(textbook,true);
										}
									}
							);
							textbook.callBack=callBack;
							switchFartherZtreeNode(textbook);
						}
					}	
			);
			
			publisher.callBack=pCallBack;		
		}
	});
	
	//选中节点
	function clickZtreeNodeByTid(tId){
		$("#"+tId+"_a:not(.curSelectedNode)").click();
	}
	
	//打开未打开的父节点
	function openFartherZtreeNodeBytId(tId){
		$("#"+tId+"_switch.noline_close").click();
	}
	
	//打开父节点
	function openFartherZtreeNode(node,click){
		var flag=node.getParentNode()!=null;
		var pNode=node;
		while(flag){
			openFartherZtreeNodeBytId(pNode.tId);
			flag=pNode.getParentNode()!=null;
			if(flag){
				pNode=pNode.getParentNode();
			}
		}
		if(click){
			clickZtreeNodeByTid(node.tId);	
		}
	}
	
	//根据教材id找教材ztree对象
	function getTextbookNode(nodes,textbookId){
		var textbook=null;
		$.each(nodes,function(i,v){
			if(v.id==textbookId){
				textbook=v;
			}
		});
		return textbook;
	}
	
	//根据出版社id找出版社ztree对象
	function getPublisherNode(publisherCD){
		var publisher=null;
		$.each(zTree.getNodes(),function(i,v){
			if(v.publisherCD==publisherCD){
				publisher=v;
			}
		});
		return publisher;
	}
}

/**
 * 教材树选择
 */
function TextbookRelzTreeClick(zTree){
	var checkedNodes=[];
	//获取所有被选中的最底层节点
	$.each(zTree.getSelectedNodes(),function(index,checkedNode){
		if(!checkedNode.children&&checkedNode.nodeType!="ntp"){
			checkedNodes.push(checkedNode);
		}else{
			if(checkedNode.nodeType!="ntp"){
				if($("#"+checkedNode.tId).find(".curSelectedNode").length==1){
					checkedNodes.push(checkedNode);
				}	
			}
		}
	});
	
	$.each(checkedNodes,function(i,node){
		var fullName="",
			textbookName="",
			publisherName="";
		
		var _thisNode=$("#"+node.tId),
			_textbook=null,
			_publisher=null;
		
		if("ntt"!=node.nodeType){
			_textbook=_thisNode.parents(".level1").prev();
			textbookName=_textbook.attr("title");
		}else{
			textbookName=node.name;
		}
		_publisher=_thisNode.parents(".level0").prev();
		publisherName=_publisher.attr("title");
		
		if(!node.nodeFullName){
			node.nodeFullName="";
		}
		
		fullName=publisherName+textbookName+node.nodeFullName;
		node.fullName=fullName;
	});
	return checkedNodes;
}
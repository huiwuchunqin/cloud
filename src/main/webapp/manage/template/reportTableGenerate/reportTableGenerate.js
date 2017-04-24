$.fn.getTable=function(dataList,_statsticTitle){
	var self=this;
	getDate(dataList,_statsticTitle);
	//查询表格数据
	function getDate(dataList,_statsticTitle){
			var chapterThCount=1,//(章节)一共几列
			 	totalTh;//表格一共几列
				statsticTitle=_.keys(_statsticTitle);
			 	statisticTitleList=_.values(_statsticTitle);
			 	
			//赋予孩子并计算 chapterThCount
			giveLevel(dataList);
			giveChildCount(dataList);//计算每个节点的末节点数好计算高度 
			/* getLevelDeep(data);// */
			totalTh=chapterThCount+statsticTitle.length;//计算表格一共几列
			raw(dataList);//给章节和统计数据中间插空格;使得表格中间填满 
			rawLeft(dataList);//给节点后的数据插空格使得表格后面填满
			giveChild(dataList);//给每个节点赋予子节点 
			sort(dataList);//排序
			getTable(dataList,chapterThCount,totalTh,statisticTitleList);
			
			//给数据按编码值排序
			function sort(listDate){
				_.each(listDate,function(item){
					if(item.name==""||item.name=="null"||!item.name){
						item.name="--";
					}
					if(item.children&&item.children.length){
						item.children.sort(function(a,b){
							return a.code>b.code;
						})	
					}
				})
			}
			/** 
			 * 找元素统计数据列的爸爸  章节列不算在内
			 * 找爸爸 每多一个爸爸 level+1
			 */
			function giveLevel(listDate){
				_.each(listDate,function(item){
					if(item.level==undefined){
						item.level=getLevel(item,1);
						if(item.notChapter!=1){
							if(item.level>chapterThCount)chapterThCount=item.level;
						}
					}
					
				})
			}
			function getLevel(item,level){
				var parent=_.find(dataList,function(item2){
					return item2.code==item.pcode;
				})
				if(parent!=undefined){
					level++;
					return getLevel(parent,level)
				}else{
					return level;
				}
			}
			//加工
			function raw(data){
				_.each(data,function(item){
					if(statsticTitle.indexOf(item.level)>=0){
						_.each(data,function(item2){
							if(item2.code==item.pcode){
								var _level=item2.level;
								var number=Number(_level);
								if(statsticTitle.indexOf(_level)<0){
									var levelMin=chapterThCount-number;
									if(levelMin>0){
										item.pcode=(Math.random()*10000000000000).toFixed(0);
										var top=giveParentAndRetunTop(levelMin,item)
										item2.code=top.pcode;
										item2.children=top;
									}
								}
							}
						})
					}
				})
			}
			
			function giveParentAndRetunTop(level,child){
				var _code=(Math.random()*10000000000000).toFixed(0);
				var obj={code:child.pcode,pcode:_code,name:""};
				dataList.push(obj);
				//obj.children=child;
				level--;
				if(level>0){
					return giveParentAndRetunTop(level,obj)	
				}else{
					return obj;
				}
			}
			
			//加工
			function rawLeft(data){
				_.each(data,function(item){
					var child=_.find(data,function(item2){
						return item2.pcode==item.code
					});
					if(child==undefined){
						if(statsticTitle.indexOf(item.level)<0){
							giveLeftChild(item.level,item)
						}else{
							var level=statsticTitle.indexOf(item.level)+chapterThCount+1;
							if(level<totalTh){
								giveLeftChild(level,item);
							}
						}
					}
				})
			}
			function giveLeftChild(level,parent){
				var _code=(Math.random()*10000000000000).toFixed(0);
				var obj={code:_code,pcode:parent.code,name:""};
				data.push(obj);
				level++;
				if(level<totalTh){
					giveLeftChild(level,obj);
				}
			}
		
			
			//赋予末节点数 
			function giveChildCount(data){
				data.sort(function(a,b){return b.code.length!=a.code.length?(b.code.length-a.code.length):(a.code-b.code)});
				_.each(data,function(item){
					if(!item.endChildCount)item.endChildCount=1;
					_.find(data,function(item2){
						if(item.pcode==item2.code){
							var endChildCount=item.endChildCount||1;
							if(_.isNumber(item2.endChildCount)){
								item2.endChildCount=item2.endChildCount+endChildCount;
							}else{
								item2.endChildCount=endChildCount;
							}
						}
					})	
				})
			}
			
			function giveChild(data){
				_.each(data,function(item){
					var levelDeep=0;
					_.each(data,function(item2){
						if(item.code==item2.pcode){
							if(!_.isArray(item.children)){
								item.children=new Array();	
							}
							item.children.push(item2);
						}
					})
				})
			}
		/*	 function getLevelDeep(data){
					_.each(data,function(item){
						if(item.notChapter==undefined){
							calculateDeep(1,data,item);
						}
					})
				}
			 function calculateDeep(deepLevel,data,item1){
				_.each(data,function(item){
					if(item.code==item1.pcode){
						deepLevel++;
						calculateDeep(deepLevel,data,item);
					}
					if(deepLevel>chapterThCount)chapterThCount=deepLevel;
					
				})
			}*/
		 }
	
		function getTable(data,chapterThCount,totalTh,statisticTitleList){
			$(self).empty();
			$(self).addClass("table")
			var normalHeight=30;
			var tdName=new Array();
			var data=_.filter(data,function(item){
				return !item.pcode
			})
			var chinese=["一","二","三","四","五","六"];
			var ids=["first","second","third","forth","five","six"];
			
			
			//生成表格头
			var head="<div class='tr'> \n";
			for(var i=0;i<chapterThCount;i++){
				tdName.push(chinese[i]+"级节点");
			}
			_.each(statisticTitleList,function(item){
				tdName.push(item);	
			})
			for(var i=0;i<tdName.length;i++){
				if(i<chapterThCount){
					head +=	"<div class='container chapterTd th'>	<span>"+tdName[i]+"</span></div> \n"	
				}else{
					head +=	"<div class='container td th'>	<span>"+tdName[i]+"</span></div> \n"	
				}
				
			}
			head +="</div> \n"	
			
				
			//生成表格主体部分
			var body="";
			for(var j=0;j<data.length;j++){
				body	+="<div class='tr '> \n";
				
				var div=$("<div>").addClass(" chapterTd container")
									.attr("data-code",data[j].code)
									.attr("data-pcode",data[j].pcode)
									.attr("data-counts",data[j].endChildCount)
									.append($("<span>").text(data[j].name).attr("title",data[j].name));
				var html=appendNext(div[0].outerHTML,div,[data[j]],1);
				body=body+html;
				body	+="</div> \n";
			}
			var table=head+body;
			$(self).append(table);
			
			/**通过孩子的个数决定高度
			 * 并给予相同的行高使得数据垂直居中
			 */
			$(self).find("div").each(function(index,item){
				var count=$(item).attr("data-counts");
				if(count!="undefined"&&count&&count>0){
					$(item).attr("style","line-height:"+(count*normalHeight+(count-1)*2)+"px");
					$(item).height(count*normalHeight+(count-1)*2);
					
				}
				if($(item).children().length<=0){
					$(item).height(normalHeight);
				}
			})
			
			
			//计算表格宽度
			var width=0;
			$("#tb>div:first-child").find("div").each(function(index,item){
				width+=$(item).width()+2;
			})
			 $(self).width(width); 
			
			
		/**
		 * @param html 表格的html
		 * @param preHtmlObj  之前一个单元格的html
		 * @param parentList  前一列的数据项
		 * @param currentTh  当前是第几列
		 */
		function appendNext(html,preHtmlObj,parentList,currentTh){
			var hasChild=false;
			var childrenList=new Array();
			var clone=preHtmlObj.clone();
			clone.removeClass("chapterTd");
			clone.addClass("td");
			
			if(currentTh<chapterThCount){
				clone.removeClass("td");
				clone.addClass("chapterTd");
			}
			clone.find("span").not(".container").each(function(i,item){
				item.remove();
			})
			
			clone.find("div").each(function(index,item){
				$(item).attr("clone-code",$(item).attr("data-code")).removeAttr("data-code");
				$(item).attr("clone-pcode",$(item).attr("data-pcode")).removeAttr("data-pcode");
			})
			
			/**在前一列的基础上 生成相邻列
			 * 吧前一列布局中再分成若干行 放子节点数据
			 */
			for(var i=0;i<parentList.length;i++){	
				var parent=parentList[i];
				var childrens=parentList[i].children;
				var parentDiv=clone.filter("div[data-code='"+parent.code+"']");
				if(parentDiv.length<=0){
					parentDiv=clone.find("div[clone-code='"+parent.code+"']");	
				}
				if(childrens!=undefined){
					if(!_.isArray(childrens))childrens=[childrens];
					for(var j=0;j<childrens.length;j++){
						var div=$("<div>").addClass("border")
											.attr("data-code",childrens[j].code)
											.attr("data-pcode",childrens[j].pcode)
											.attr("data-counts",childrens[j].endChildCount)
											.append($("<span>").text(childrens[j].name||"--").attr("title",childrens[j].name||"--"))
					 	if(!childrens[j].children||childrens[j].children.length<=0){
							/* div.attr("data-last","1").height("normalHeight"); */
						} 
						div.append("\n");				
						parentDiv.append(div);
						childrenList.push(childrens[j]);
						if(childrens[j].children!=undefined||(childrens[j].children&&childrens[j].children.length>0)){
							hasChild=true;	
						}
					}
				}
			}
			html=html+"\n"+clone[0].outerHTML;
			if(hasChild){
				currentTh++;
				return appendNext(html,clone,childrenList,currentTh);	
			}else{
				return html;
			}
		}
		
	}	
}
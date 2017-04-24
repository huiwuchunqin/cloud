
// 非空判断
function isEmpty(value) {
	if (value == "" || value == undefined || value == null) {
		return true;
	} else {
		return false;
	}
}

/**
 * 获取查询条件区的查询条件
 * 
 * @param id
 *            要获取参数的区域
 * @returns {key:value,key2:value2}
 */
function getParam(id){
	var param={};
	if(id=="")return param;
	$('#'+id).find("input,textarea,select").each(function(index,item){
		if(item.id||item.name){
			if(item.className.indexOf("combobox")>=0){
				param[item.id||item.name]=$(item).combobox('getValue');	
			}else{
				param[item.id||item.name]=$(item).val().trim();
			}	
		}
	})
	return param
}
/**
 * 给url添加参数
 * 
 * @param url
 *            url地址
 * @param param
 *            参数 {param1:value1,param2:value2}
 * @returns 加参数后的url url?param1=value1&param2=value2
 */
function urlWithParam(url,param){
	for(var i in param){
		if(url.indexOf("?")>=0){
			url=url+"&"+i+"="+param[i];		
		}else{
			url=url+"?"+i+"="+param[i];
		}
	}
	return url;
}

/**
 * 下拉框输入筛选 使得combobox可以通过输入来筛选数据
 * 
 * @param obj
 */
$.extend($.fn.combobox.defaults,{
	onHidePanel : function() {
		var t = $(this).combobox('getText');// 当前value值
		if(t=="")return
		var datas = $(this).combobox('getData');
		var _textField = $(this).combobox('options').textField;// 文本key
		var exist = _.find(datas, function(item) {
			return item[_textField] == t
		})
		if (!exist) {
			alert("请不要手动输入");
			$(this).combobox('setValue', '')
		}
	},
	filter:function(q,row){
				var opts = $(this).combobox('options');
				return row[opts.textField].includes(q);
	}

})

// 下载帮助
function downloadHelper(url) {
	canDownLoad(function(){
		window.location.href=url;
	})
}

// 得到数据表格总数
function canDownLoad(callback) {
	var $table=$(".datagrid-view>table");
	$table.datagrid('reload');
	if(!$table){
		alert("downloadHelper只适用datagrid")
	}
	var flag = true;
	var dtd = $.Deferred();
	var wait = function(dtd) {
		setTimeout(function() {
			var total = $table.datagrid('getData').total;
			if(total>5000){
				alert("数据不能超过5000条，请加些查询条件进行过滤！");
				flag = false;
			}
			if(total==0){
				alert("没有数据");
				flag = false;
			}
			dtd.resolve()
		}, 1000)
		return dtd.promise();
	};
	
	/**
	 * 页面刷新完以后执行回调 确保下载满足数据量不能过多,且不能为空得条件
	 */
	$.when(wait(dtd)).done(function() {
		if (flag) {
			callback();
		}
	});
}

/**
 * 得到datagrid点击事件所在的行
 * 
 * @param object
 * @returns row 点击所在的行
 */
function getRow(object){
	if(!object)return{};
	var index=$(object).closest("tr").index();
	var $table=$(".datagrid-view>table");
	$table.datagrid('selectRow',index);
	var row=$table.datagrid('getData').rows[index];
	return row||{};
}

/**
 * 选中下拉框第一项
 * 
 * @param obj
 *            下拉框jquery对象
 */
function selectComboFirst(obj) {
	var list = obj.combobox('getData');
	var valueField = obj.combobox('options').valueField;
	if (list && list.length > 0) {
		obj.combobox('setValue', list[0][valueField]);
	}
}
/**
 * 
 * @param obj
 *            要操作的input file对象
 * @param imgObj
 *            用来放图片的对象
 * @param callback
 *            回调函数获取图片地址
 * @param minHeight
 *            最小高度
 * @param minWidth
 *            最小宽度
 */
function imageUpload(obj, imgObj, callback,minWidth,minHeight,info) {
	var objEvt = imgObj.attr("onClick")
	if (objEvt) {
		
	}else{
		// 转移file的click事件
		$(imgObj).click(function(){
			$(obj).click();	
		})
	}

	var maxSize=10000000;
	minWidth=minWidth||0;
	minHeight=minHeight||0;
	/**
	 * 去除原本的背景 针对上传的图片是透明的情况
	 */
	var src=$(imgObj).attr("src");
	if(src!=""&&src!=undefined){
		$(imgObj).css("background","none");
	}
	//$(imgObj).attr("src","/manage/template/images/upload.png");
	// 隐藏file元素
	$(obj).attr("accept", ".png,.jpg,.gif") 
		  .hide()
		  .change(function(data) {
					var file = data.target.files[0];
					console.info("fileSize"+file);
					if (!file) {
						return false;
					}
					if (file.type.indexOf("image") < 0) {
						alert("封面只能选择图片");
						return false;
					}
					if (file.size > maxSize) {
						alert("图片不能大于10M");	
						$(obj).val("");
						return false;
					}
					var url = window.URL.createObjectURL($(obj)[0].files[0]);
					var image=new Image();
					var allow=true;
					image.onload=function(){
	                          var width = image.width;
	                          var height = image.height;
	                          if(width<minWidth||height<minHeight){
	                        	  alert(info);
	                        	  $(obj).val("");
	                        	  allow= false;
	                          }
	              			if(allow){
								$(imgObj).attr("src", url).css("background","none");
								/**
								 * 保存图片并加遮罩 防止用户提交 把服务器的图片地址传回回调函数
								 */
								layer.load();
								$(obj).parents('form').form('submit', {
									success : function(json) {
										var res = $.parseJSON(json);
										if(res&&res.msg){
											if (res.success) {
												_coverPath = res.data;
												if (typeof callback == 'function') {
													callback(_coverPath);
												}
											}else{
												alert(res.msg);
											}
										}
										layer.closeAll();
									},
								})	
							}
						 };
					image.src=url;
		  })
}

/**
 * 获取导出文件名称
 */
function getExportName() {
	var $tab = window.top.$('#tab');
	if ($tab[0] != undefined) {
		return encodeURI($tab.tabs('getSelected').panel('options').title
				+ ".xls")
	} else {
		return "";
	}
}
function getUnencodePanelTitle(){
	var $tab = window.top.$('#tab');
	if ($tab[0] != undefined) {
		return $tab.tabs('getSelected').panel('options').title
	} else {
		return "";
	}
}
/**
 * 获取选项卡名称
 */
function getPanelTitle() {
	var $tab = window.top.$('#tab');
	if ($tab[0] != undefined) {
		return encodeURI($tab.tabs('getSelected').panel('options').title)
	} else {
		return "";
	}
}


/**
 * 查看选项在列表中是否存在
 * 
 * @param value
 * @param list
 * @param code
 * @returns {String}
 */
function getValue(value,list,code){
	var key="code";
	if(list==null||list==undefined){
		return "";
	}
	if(code){
		key=code;
	}
	var _value="";
	$.each(list,function(i,data){
		if(data[key]==value){
			_value=value;
		};
	})
	return _value;
}
/**
 * 
 * @param longStr
 *            长字符串
 * @param shortStr
 *            短字符串
 * @param caseSensitive
 *            大小写敏感
 * @returns {Boolean} 是否包含
 */
function bztContains(longStr,shortStr,caseSensitive){
	if(caseSensitive){
		return longStr.indexOf(shortStr)>=0
	}else{
		return longStr.toLowerCase().indexOf(shortStr.toLowerCase())>=0	
	}
}
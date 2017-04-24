/**
 * 右击显示或隐藏列
 * @param {} tableId 对应存放 datagrid的table的id
 */
function easyui_datagrid_rightclick_Menu(tableId) {
	if (tableId == null || tableId == "")
		return;
	tableId = "#" + tableId;
	var tmenu = $('<div id="tmenu" style="width:100px;"></div>').appendTo('body');
	var fields = $(tableId).datagrid('getColumnFields');
	// alert(fields[0])
	for (var i = 0; i < fields.length; i++) {
		$('<div iconCls="icon-ok" />').html($(tableId).datagrid('getColumnOption', fields[i]).title).appendTo(tmenu).attr('field_id', fields[i]);//
	}
	tmenu.menu({
		onClick : function(item) {
			if (item.iconCls == 'icon-ok') {
				// for(var i in item)alert(i+":"+item[i])
				$(tableId).datagrid('hideColumn', $(item.target).attr('field_id'));// item.text
				tmenu.menu('setIcon', {
					target : item.target,
					iconCls : 'icon-empty'
				});
			} else {
				$(tableId).datagrid('showColumn', $(item.target).attr('field_id'));
				tmenu.menu('setIcon', {
					target : item.target,
					iconCls : 'icon-ok'
				});
			}
		}
	});
}
/**
 * 弹出窗口
 * @param {} title
 * @param {} width
 * @param {} height
 * @param {} url
 * @param {} callbackFunc
 * @param {} params
 */
function easyui_openTopWindow(title, width, height, url, callbackFunc, param) {
	var top = "", left = "";
	var maxHeight = window.top.document.body.clientHeight;// parseInt(window.top.screen.availHeight)
	// -
	// parseInt(window.top.screenTop);
	var maxWidth = window.top.document.body.clientWidth;// parseInt(window.top.screen.availWidth)
	// -
	// parseInt(window.top.screenLeft);
	if (height > maxHeight) {
		top = "top:5px;";
		height = maxHeight - 10;
	}
	if (width > maxWidth) {
		left = "left:5px;";
		width = maxWidth - 10;
	}
	var date = new Date().pattern("yyyy-MM-dd-HH:mm:ss");
	var win = window.top.document.createElement("div");
	win.setAttribute("name", "openTopWindow" + date);
	win.setAttribute("style", "padding:5px;" + top + left);
	// var win = window.top.document.createElement("<div
	// name='openTopWindow"+date+"' style='padding:0px;"+top+left+"'></div>");
	window.top.document.body.appendChild(win);
	(function(title, width, height, url, callbackFunc, param) {
		return function() {
			window.top.$(win).window({
				title : title + " ",
				width : width,
				modal : true,
				shadow : false,
				closed : true,
				height : height,
				draggable : true,
				zIndex : 90000,
				// inline:true,
				collapsible : false,
				minimizable : false,
				maximizable : true,
				closable : true,
				content : "<iframe scrolling='auto' frameborder='0' src=" + url + " style='width:100%;height:98%;' allowfullscreen='true' webkitallowfullscreen='true' mozallowfullscreen='true' oallowfullscreen='true' msallowfullscreen='true' allowtransparency='true'></iframe>",
				onClose : function() {
					if (callbackFunc != null) {
						var returnV = window.top.$("[name^='openTopWindow']:last").data("returnValue");
						if (returnV == null)
							returnV = "";
						callbackFunc(returnV);
					}

					window.top.$("[name^='openTopWindow']:last").window('destroy', false);
				},
				onBeforeOpen : function() {
					window.top.$("[name^='openTopWindow']:last").data("param", param);
				}
			})
		}
	}(title, width, height, url, callbackFunc, param))();
	window.top.$(win).window('open');
}
function easyui_openNoResizeWindow(title, width, height, url, callbackFunc, param,id) {
	var top = "", left = "";
	var maxHeight = window.top.document.body.clientHeight;// parseInt(window.top.screen.availHeight)
	// -
	// parseInt(window.top.screenTop);
	var maxWidth = window.top.document.body.clientWidth;// parseInt(window.top.screen.availWidth)
	// -
	// parseInt(window.top.screenLeft);
	if (height > maxHeight) {
		top = "top:5px;";
		height = maxHeight - 10;
	}
	if (width > maxWidth) {
		left = "left:5px;";
		width = maxWidth - 10;
	}
	var date = new Date().pattern("yyyy-MM-dd-HH:mm:ss");
	var win = window.top.document.createElement("div");
	win.setAttribute("name", "openTopWindow" + date);
	win.setAttribute("style", "padding:5px;" + top + left);
	// var win = window.top.document.createElement("<div
	// name='openTopWindow"+date+"' style='padding:0px;"+top+left+"'></div>");
	window.top.document.body.appendChild(win);
	(function(title, width, height, url, callbackFunc, param) {
		return function() {
			window.top.$(win).window({
				title : title + " ",
				width : width,
				modal : true,
				shadow : false,
				closed : true,
				height : height,
				draggable : true,
				zIndex : 90000,
				resizable:false,
				// inline:true,
				collapsible : false,
				minimizable : false,
				maximizable : false,
				closable : true,
				content : "<iframe scrolling='auto' id='"+id+"'  frameborder='0' src=" + url + " style='width:100%;height:100%;'allowfullscreen></iframe>",
				onClose : function() {
					if (callbackFunc != null) {
						var returnV = window.top.$("[name^='openTopWindow']:last").data("returnValue");
						if (returnV == null)
							returnV = "";
						callbackFunc(returnV);
					}

					window.top.$("[name^='openTopWindow']:last").window('destroy', false);
				},
				onBeforeOpen : function() {
					window.top.$("[name^='openTopWindow']:last").data("param", param);
				}
			})
		}
	}(title, width, height, url, callbackFunc, param,id))();
	window.top.$(win).window('open');
}
function easyui_closeTopWindow(returnValue) {
	if (window != top) {
		if (returnValue == null)
			returnValue = "";
		window.top.$("[name^='openTopWindow']:last").data("returnValue", returnValue).window('close');
	}
}
function easyui_getParam() {
	var params = window.top.$("[name^='openTopWindow']:last").data("param");
	return params;
}
/**
 * 把日期格式化成 yyyy-MM-dd HH(hh):mm:ss var date = new Date();
 * window.alert(date.pattern("yyyy-MM-dd hh:mm:ss"));
 * @param {} fmt
 * @return {}
 */
Date.prototype.pattern = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, // 小时
		"H+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	var week = {
		"0" : "\u65e5",
		"1" : "\u4e00",
		"2" : "\u4e8c",
		"3" : "\u4e09",
		"4" : "\u56db",
		"5" : "\u4e94",
		"6" : "\u516d"
	};
	if (/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	if (/(E+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "\u661f\u671f" : "\u5468") : "") + week[this.getDay() + ""]);
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
}
/**
 * 打开等待进度条
 * @param {} func 要执行的方法
 */
function easyui_openWaitProgress(func) {// 打开等待进度条
	$.messager.progress('close');
	$.messager.progress({
		title : '请等待',
		msg : '程序处理中...'
	});
	setTimeout(function() {
		try {
			func();
		} catch (e) {

		}
		// $.messager.progress('close');//关闭进度条
	}, 500);
}
/**
 * 获取form中的数据，根据name
 * @param {} formId
 */
function easyui_getFormValue(formId) {
	var map = {};
	$("[name]", $("#" + formId)).each(function(_i) {
		var tmpName = $(this).attr('name');
		var type = $(this).attr('type');
		if (type != null) {
			switch (type) {
			case 'radio':
				$.each($(this), function() {
					if ($(this).attr("checked")) {
						map[tmpName] = $(this).val();
					}
				});
				break;
			default:
				map[tmpName] = $(this).val();
			}
		} else {
			map[tmpName] = $(this).val();
		}
	});
	return map;
}
/**
 * 设置form中的数据，根据name
 * @param {} formId
 * @param {} data
 */
function easyui_setFormValue(formId, data) {
	$("[name]", $("#" + formId)).each(function(_i) {
		var tmpName = $(this).attr('name');
		if (data[tmpName] != null) {
			var type = $(this).attr('type');
			// if(tmpName ==
			// "ACCOUNTING_SUBJECT_ID")alert($(this).parent().html());

			if ($(this).attr("class") == "combo-value") {// 可能为select
				// 日期控件还没有遇到
				$("#" + tmpName).combobox('select', data[tmpName]);
				// alert($(this).parent().html());
				/*
				 * var tmpId = $(this).attr('name').replace(".","_");
				 * if(jQuery.inArray(tmpId,datetimeboxArr) != -1){//确定是日期控件
				 * $("#"+tmpId).datetimebox('setValue',eval("row."+tmpName)); }
				 */
			} else if ($(this).attr("type") == "checkbox") {// checkbox
				if (data[tmpName] == $(this).val()) {
					$(this).attr("checked", true);
				} else {
					$(this).attr("checked", false);
				}
			} else if (type == "radio") {// radio
				var radios = $(this);
				$.each(radios, function() {
					if ($(this).val() == data[tmpName]) {
						$(this).attr("checked", true);
					} else {
						$(this).attr("checked", false);
					}
				});
			} else if (type == "hidden") {// 目前遇到easyui-numberbox
				var len = $(this).parent().find("[numberboxname='" + tmpName + "']").length;
				if (len == 1) {
					$("#" + tmpName).numberbox('setValue', data[tmpName]);
				} else {
					$(this).val(data[tmpName]);
				}
			} else {
				$(this).val(data[tmpName]);
			}
		}
	});
}
/**
 * 请用encodeURIComponent对中文数据进行编码
 * @return {}
 */
function easyui_getRequestPara() {
	var url = location.search;
	if (url == null)
		return null;
	var theRequest = new Object();
	if (url.indexOf("?") != -1) {
		var str = url.substr(1);
		strs = str.split("&");
		for (var i = 0; i < strs.length; i++) {
			theRequest[strs[i].split("=")[0]] = decodeURIComponent(strs[i].split("=")[1]);
		}
	}
	return theRequest;
}
function easyui_setDisabled(ids) {
	var components = "input,textarea,a,select,span";
	$(ids).find(components).attr('disabled', true);// 输入的值不能修改
}
/**
 * format数据带，
 * @param {} s 要格式化的数据
 * @param {} n 小数位数
 * @return {}
 */
function easyui_fmoney(value, n) {
	if (value == null)
		return "";
	value = value + "";
	if (/^[-]{0,1}(\d+)[\.]{1}(\d+)$/.test(value) || /^[-]{0,1}(\d+)$/.test(value)) {
		var valueHead = value.substring(0, 1);
		var valueBody = "";
		if (valueHead == "-")
			valueBody = value.substring(1, value.length);
		else
			valueBody = value;
		valueBody = (valueBody + "").replace(/^(\d*)$/, "$1.");
		valueBody = (valueBody + "00").replace(/(\d*\.\d\d)\d*/, "$1");
		valueBody = valueBody.replace(".", ",");
		var re = /(\d)(\d{3},)/;
		while (re.test(valueBody))
			valueBody = valueBody.replace(re, "$1,$2");
		valueBody = valueBody.replace(/,(\d\d)$/, ".$1");
		valueBody = valueBody.replace(/^\./, "0.");
		if (valueHead == "-")
			valueBody = valueHead + valueBody;
		return valueBody;
	} else {
		return "0.00";
		// return "invalid value";
	}
}
function easyui_rmoney(s) {
	return parseFloat(s.replace(/[^\d\.-]/g, ""));
}
/**
 * 日期控件 格式化
 * @param {} date
 * @return {}
 */
function easyui_datebox_formatter(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
}
/**
 * 日期控件 格式化
 * @param {} s
 * @return {}
 */
function easyui_datebox_parser(s) {
	if (!s)
		return new Date();
	var ss = s.split('-');
	var y = parseInt(ss[0], 10);
	var m = parseInt(ss[1], 10);
	var d = parseInt(ss[2], 10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
		return new Date(y, m - 1, d);
	} else {
		return new Date();
	}
}

/**
 * 弹出窗口
 */
$.fn.easyui_openTopWindow = function(options) {
	// 设置默认值
	var defaultValue = {
		url : "",
		title : "",
		width : $(window).width() * 0.8,
		height : $(window).height() * 0.8,
		callbackFunc : null,
		param : null,
		modal : true
	};
	// 与默认值进行合并，如options中存在则覆盖默认值
	var opts = $.extend({}, defaultValue, options);

	var top = "";
	var left = "";
	var maxWidth = window.top.document.body.clientWidth;// parseInt(window.top.screen.availWidth)-parseInt(window.top.screenLeft);
	var maxHeight = window.top.document.body.clientHeight;// parseInt(window.top.screen.availHeight)-parseInt(window.top.screenTop);
	if (opts.width > maxWidth) {
		left = "left:5px;";
		opts.width = maxWidth - 10;
	}
	if (opts.height > maxHeight) {
		top = "top:5px;";
		opts.height = maxHeight - 10;
	}

	var date = new Date().pattern("yyyyMMddHHmmss");
	var win = window.top.document.createElement("div");
	win.setAttribute("name", "openTopWindow" + date);
	win.setAttribute("style", "padding:0px;" + top + left);

	// 生成唯一的窗体
	var index = opts.url.indexOf("?");
	if (index > 0) {
		opts.url = opts.url.substring(0, index) + "?windowName=" + win.getAttribute("name") + "&" + opts.url.substring(index + 1, opts.url.length);
	} else {
		opts.url = opts.url + "?windowName=" + win.getAttribute("name");
	}

	window.top.document.body.appendChild(win);
	(function(opts) {
		return function() {
			window.top.$(win).window({
				title : "&nbsp;" + opts.title + "&nbsp;",
				width : opts.width,
				height : opts.height,
				modal : opts.modal,
				shadow : false,
				closed : true,
				draggable : true,
				// inline:true,
				collapsible : true,
				minimizable : false,
				maximizable : true,
				closable : true,
				zIndex : 90000,
				content : "<iframe scrolling='auto' frameborder='0' src=" + opts.url + " style='width:100%;height:100%;'></iframe>",
				onClose : function() {
					if (opts.callbackFunc != null) {
						var returnVal = window.top.$("[name='" + win.getAttribute("name") + "']").data("returnValue");
						if (returnVal == null) {
							returnVal = "";
						}
						opts.callbackFunc(returnVal);
					}
					window.top.$("[name='" + win.getAttribute("name") + "']").window('destroy', false);
				},
				onBeforeOpen : function() {
					window.top.$("[name='" + win.getAttribute("name") + "']").data("param", opts.param);
				}
			})
		}
	}(opts))();
	window.top.$(win).window('open');
}

/**
 * 关闭窗口
 */
$.fn.easyui_closeTopWindow = function(returnValue) {
	if (window != top) {
		if (returnValue == null) {
			returnValue = "";
		}
		window.top.$("[name='" + $.query.get("windowName") + "']").data("returnValue", returnValue).window('close');
	}
}
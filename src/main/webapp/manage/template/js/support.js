/**
 * 后台管理界面依赖插件
 */

var currDialog;
/**
 * 重写alert 方法
 * @param content
 * @param title
 * @param width
 * @param height
 */
function dialog(content,title,width,height){
	currDialog = null;
	currDialog = $("#easyui-dialog-win").css("padding","10px").dialog({
		href:content,
		title: title ? title : "提示",
		iconCls:'icon-info',
		width: width ? width : 400,
		height: height ? height : 200,
		closed: false,
		cache: false,
		resizable:false,
		modal: true
	});
	currDialog.dialog("panel").center();
}

/**
 * 关闭弹窗
 */
function closeDialog(){
	if(!(currDialog == null || currDialog === undefined)){
		currDialog.dialog("close");
		//currDialog.find(".panel").remove();
	}
}

/**
 * 弹出式提示框
 * @param title
 * @param msg
 */
function alert(msg,fnc){
	if( typeof fnc ==="function"){
		$.messager.alert("提示",msg,fnc());
	}else{
		$.messager.alert("提示",msg);
	}
	
	/*$(".messager-window").center();*/
}

/**
 * 确定和取消弹窗
 * @param title
 * @param msg
 * @param callback
 */
function confirm(title,msg,callback){
	$.messager.confirm(title,msg,callback);
/*	$(".messager-window").center();*/
}

/**
 * 弹出窗口
 */
var subWin = null;
function showWindow(options){
	var win = $("#easyui-window-win").css("padding","10px");
	subWin = win.window(options);
	win.window("center");
	win.window("open");
	win.window("panel").center();
}

/**
 * 隐藏窗口
 */
function hideWindow(){
	if(undefined != subWin && null != subWin){
		subWin.window("close");
		subWin.find(".panel").remove();
	}
}

/***
 * 自瘾式提示框
 * @param msg
 */
function toast(msg,speed,onHide){
	var sp = 2000;
	if(!(undefined == speed || 0 == speed)){
		sp = speed;
	}
	if(undefined == onHide || null == onHide){
		onHide = function(){};
	}
	//$("<div class=\"datagrid-mask\"></div>").css({ display: "block", width: "100%", height: $(window).height() }).appendTo("body").fadeOut(sp);  
    $("<div class=\"datagrid-mask-msg\"></div>").html(msg).appendTo("body").css({ display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 45) / 2 }).fadeOut(sp,onHide).center();
}

/**
 * 系统提示
 * @param msg
 */
function sysMessage(msg,distime,width,height){
	$.messager.show({
		title:'系统提示',
		msg:msg,
		width:width ? width : 300,
		height:height ? height : 100,
		timeout:distime ? distime : 5000,
		showType:'slide'
	});
}


if(jQuery)(function($) {
	//将数据填充至模板中
	$.fn.template = function(options){
		var opts = $.extend(true,{
			datas:[],
			fields:[],
			onComplete:function(item,o){}}, options);
		var allItems = new Array();
		$(this).each(function() {
			var tmpl = $(this).html();
			var fieldMap = new Array();
			$.each(opts.fields,function(i,o){
				fieldMap[o.name] = o;
			});
			$.each(opts.datas,function(i,o){
				var item = $(tmpl);
				$.each(o,function(key,val){
					var m = fieldMap[key];
					var className = key;
					var fitVal = val;
					if(!(null == m || undefined == m)){
						if(!(null == m.className || undefined == m.className)){
							className = m.className;
						}
						if(!(null == m.formatter || undefined == m.formatter)){
							fitVal = m.formatter(val,o);
						}
					}
					var attr = item.attr(key,val).find("." + className);
					attr.html(fitVal);
				});
				allItems.push(item);
				opts.onComplete(item,o);
			});
		});
		return allItems;
	};
	
	/**
	 * 给对象添加遮罩
	 */
	$.fn.addMask = function(msg, maskDivClass){
		this.removeMask();
		// 参数
		var op = {
			opacity : 0.4,
			z : 10000,
			bgcolor : ''
		};
		var original = $(document.body);
		var position = {
			top : 0,
			left : 0
		};
		if (this[0] && this[0] !== window.document) {
			original = this;
			position = original.position();
		}
		// 创建一个 Mask 层，追加到对象中
		var maskDiv = $('<div class="mask_div"> </div>');
		maskDiv.appendTo(original);
		var maskWidth = original.outerWidth();
		if (!maskWidth) {
			maskWidth = original.width();
		}
		var maskHeight = original.outerHeight();
		if (!maskHeight) {
			maskHeight = original.height();
		}
		maskDiv.css({
			position : 'absolute',
			top : position.top,
			left : position.left,
			'z-index' : op.z,
			width : maskWidth,
			height : maskHeight,
			'background-color' : op.bgcolor,
			opacity : 0
			//,
			//cursor : 'wait'
		});
		if (maskDivClass) {
			maskDiv.addClass(maskDivClass);
		}
		if (msg) {
			var msgDiv = $('<div style="position:absolute;background:#fff;"><div style="line-height:24px;border:#CCC 1px solid;background:#fff;padding:2px 10px 2px 10px;">'
					+ msg + '</div></div>');
			msgDiv.appendTo(maskDiv);
			var widthspace = (maskDiv.width() - msgDiv
					.width());
			var heightspace = (maskDiv.height() - msgDiv
					.height());
			msgDiv.css({
				top : (heightspace / 2 - 2),
				left : (widthspace / 2 - 2)
			});
		}
		maskDiv.fadeIn('fast', function() {
			// 淡入淡出效果
			$(this).fadeTo('slow', op.opacity);
		});
		return maskDiv;
	};
	
	/**
	 * 删除遮罩
	 */
	$.fn.removeMask = function(){
		var original = $(this);
		if (this[0] && this[0] !== window.document) {
			original = $(this[0]);
		}
		original.find("> div.mask_div").fadeOut('slow',
				0, function() {
					$(this).remove();
				});
	};
	
})(jQuery);


/**
 * 检查表单是否被编辑过
 */
function check_edit(o){
	var flag = false;
	o.find("input").each(function(i,o){
		var type = o.type;
		if (type == "checkbox" || type == "radio") {
			if (o.checked != o.defaultChecked) {
				flag = true;
				return false;
			}
		} else if (type == "hidden" || type == "password" || type == "text"
				|| type == "textarea") {
			if (o.value != o.defaultValue) {
				flag = true;
				return false;
			}
		} else if (type == "select-one" || type == "select-multiple") {
			for ( var j = 0; j < o.options.length; j++) {
				if (o.options[j].selected != o.options[j].defaultSelected) {
					flag = true;
					return false;
				}
			}
		}
	});
	return flag;
}


/**
 * 比较数组
 */
Array.prototype.contains = function(e) {  
	for(i = 0;i < this.length && this[i] != e;i++);  
	return !(i == this.length);  
}; 

/**
 * 数组去重
 */
Array.prototype.unique = function()
{
	var n = {},r=[]; //n为hash表，r为临时数组
	for(var i = 0; i < this.length; i++) //遍历当前数组
	{
		if (!n[this[i]]) //如果hash表中没有当前项
		{
			n[this[i]] = true; //存入hash表
			r.push(this[i]); //把当前数组的当前项push到临时数组里面
		}
	}
	return r;
}

/**
 * 格式化日期
 */
Date.prototype.format = function(format)
{
    var o =
    {
        "M+" : this.getMonth()+1, //month
        "d+" : this.getDate(),    //day
        "h+" : this.getHours(),   //hour
        "m+" : this.getMinutes(), //minute
        "s+" : this.getSeconds(), //second
        "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
        "S" : this.getMilliseconds() //millisecond
    }
    if(/(y+)/.test(format))
    format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
    if(new RegExp("("+ k +")").test(format))
    format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
};

/**
 * 加载
 * @param parent
 * @param msg
 * @returns
 */
function loading(parent,msg){
	//$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:parent.width(),height:parent.height()}).appendTo(parent);
	return $("<div class=\"datagrid-mask-msg\"></div>").html(msg).appendTo(parent).css({display:"block",left:(parent.outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});
}

//复制内容到粘贴板
function copyTextToClipboard(text){
	if(window.clipboardData) {  
             window.clipboardData.clearData();  
             window.clipboardData.setData("Text", text);  
     } else if(navigator.userAgent.indexOf("Opera") != -1) {  
          //window.location = text;
    	 //window.open(text,target='_blank');
    	 alert("您的浏览器安全设置不允许自动执行复制操作，<br/>请使用键盘快捷键(Ctrl+C)来完成。!");
    	 return;
     } else if (window.netscape) {  
          try {  
               netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");  
          } catch (e) { 
        	  alert("您的浏览器安全设置不允许自动执行复制操作，<br/>" + 
        			"请在浏览器地址栏输入'about:config'并回车<br/>然后将'signed.applets.codebase_principal_support'设置为'true',再复制。<br/>" +
        			"或者您直接使用键盘快捷键(Ctrl+C)来完成。");
        	  return;
          }  
          var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);  
          if (!clip)  
               return;  
          var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);  
          if (!trans)  
               return;  
          trans.addDataFlavor('text/unicode');  
          var str = new Object();  
          var len = new Object();  
          var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);  
          var copytext = text;  
          str.data = copytext;  
          trans.setTransferData("text/unicode",str,copytext.length*2);  
          var clipid = Components.interfaces.nsIClipboard;  
          if (!clip)  
               return false;  
          clip.setData(trans,null,clipid.kGlobalClipboard);  
     }  
  	alert("已复制到粘贴板！") ;
}

/**
 * 正则验证
 * @param regx
 * @param str
 * @returns
 */
function regxTest(regx,str){
	return new RegExp(regx,["g"]).test(str);;
}

/**
 * 鼠标悬浮提示美化
 */
function tooltip() {
	xOffset = 10;
	yOffset = 20;
	$(".tooltip").hover(
			function(e) {
				this.t = this.title.replace(/&gt;/g,">").replace(/&lt;/g,"<");
				this.title = "";
				$("body").append("<p id='tooltip'>" + this.t +"</p>");
				$("#tooltip").css("top", (e.pageY - xOffset) + "px")
							 .css("left", (e.pageX + yOffset) + "px").fadeIn("fast");
			}, function() {
				this.title = this.t;
				$("#tooltip").remove();
			});
	$(".tooltip").mousemove(
			function(e) {
				$("#tooltip").css("top", (e.pageY - xOffset) + "px")
							 .css("left", (e.pageX + yOffset) + "px");
			});
}


$.ajaxSetup ({cache: false});//关闭AJAX相应的缓存 
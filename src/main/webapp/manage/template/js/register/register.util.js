const browser = $.NV('name') + "-" + $.NV('version');
	
	$(function(){
		//checkShowVCImg(1);
		//checkShowVCImg(2);
		initialization();
		//selectBaseSubject();
		//$(document).unbind("keydown").die("keydown").off("keydown");
		$("input,textarea").keydown(function(e){
			var ev = document.all ? window.event : e;
		    if(ev.keyCode==13) {
		    	if ( e && e.preventDefault ) 
		    		//阻止默认浏览器动作(W3C) 
		    		e.preventDefault(); 
		    	else
		    		//IE中阻止函数器默认动作的方式 
		    		window.event.returnValue = false; 
		    }
		});
		
		$(".registerform[id]").keydown(function(e){
			if ($(this).is(":hidden")) {
				return;
			}
			var fid = $(this).attr("id");
			var type = -1;
			if (fid == "studentInfo") {//学员
				type = 1;
			} else if (fid == "teacherInfo") {//讲师
				type = 2;
			} else if (fid == "orgInfo") {//机构
				type = 3;
			}
			var ev = document.all ? window.event : e;
		    if(ev.keyCode==13) {
		    	if (type != -1) {
		    		if ($(this).find(":checkbox:last").prop("checked")) {
		    			submitClick(type);
		    		} else {
		    			window.top.$.messager.alert("提示", "您还未同意《联盟协议》，请勾选同意后注册", "info");
		    		}
		    	}
		     }
		});
	});
	
	//sundx-------------------------------------------------------------
	/*tabs*/
	
	function changecon(obj,id){
		$(obj).parent().find("li").removeClass("active");
		var nowId = $(".listes").attr("id");
		$(obj).addClass("active");
		$(".tab-pane").hide();
		//$("#"+id).show();
		$("#"+id).show().animate({opacity:'0.0',marginLeft:'1000px'},0).animate({marginLeft:'-100px',opacity:'1'},800).animate({marginLeft:'0px'},300);
		
//		if(id=='con_conorgInfo'){
//			selectPrince();
//			selectBaseOrg();
//		}else if(id=='con_teacherInfo'){
//			selectBaseSubject();
//		}
		}
	//验证初始化
	function initialization(){
		$('.form_box').jqTransform({imgPath:'jqtransformplugin/img/'});
		$(".registerform").Validform({
			tiptype:3,
			showAllError:false,
			ajaxPost:true,
			beforeCheck:function(curform){
				return false;
			}
		});
		$.Hidemsg();
		
	}
	

	
	function submitClick(type){
		var form,url;
		if (type == 1) {//学员
			form = $("#studentInfo");
			url = "register";
		} else if (type == 2) {//讲师
			form = $("#teacherInfo");
			url = "register";
		} else if (type == 3) {//机构
			form = $("#orgInfo");
			url = "org";
		}
		
		if (form) {
			//验证
			if (!form.moocValidate("validate")) {
				showVCImg(type);
				return;
			}
			//数据
			var obj = {};
			obj["role"] = type;
			$.each(form.find(":visible[name]").filter(":text,:password,textarea,:radio,select"), function(){
				var name = $(this).attr("name");
				if($(this).is(":radio")){
					if($(this).prop("checked")){
						obj[name] = $.trim($(this).val());
					}
					return true;
				}
				obj[name] = $.trim($(this).val());
			});	
			obj["companyCode"]=$("#companyCode").val();
			
			
			if (type == 3) {
				obj["flagHasSpoc"] = $("#flagHasSpoc").prop("checked");
				obj["applyType"] = obj["applyType"] || obj["applyType2"];
				obj["applyType2"] = undefined;
			}
			
			if (obj["sex"] === "-1") {
				obj["sex"] = undefined;
			}
			
			if (obj["subjectID"] === "") {
				obj["subjectID"] = undefined;
			}
			
			if (obj["orgSortId"] === "") {
				obj["orgSortId"] = undefined;
			}
			
			if (obj["companyCode"] === "") {
				obj["companyCode"] = undefined;
			}
			obj["avatarsImg"] = form.find("img[name=cutImg]").attr("srcPath");
			obj["orgLogoPath"] = form.find("img[name=orgImg]").attr("srcPath");
			
			var opts = {
				url:"/passport/register/submit.jhtml?browser=" + browser + "&do=" + url,
				type: "POST",
				data : obj,
				dataType:'json',
				success: function(res){
					if (res) {
						if (res.status == 0) {//成功
							//window.location.href = res.data;
							var url = "/passport/register/success.html";
							if (type == 3) {
								url = "/passport/register/orgSuccess.html";
							} else {
//								if (res.data) {
//									url += "?e=" + res.data.loginName;
//									url += "&p=" + res.data.password;
//									encodeURI(url);
//								}
							}
							window.location.href = url;
							return;
						} else if (res.status == -1) {//系统错误
							alert(res.msg);
						} else if (res.status == 1) {//验证错误
							showVCImg(type);
							if (res.data) {
								for(var item in res.data){
									if(res.data[item]!=null)
									{form.find("[name="+item+"]").moocValidate("setInvalid", res.data[item]);}
								}
							}
						}
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					window.top.$.messager.alert("提示", "程序异常,请稍后再试!<br />若发生多次请联系管理员！", "info");
				}
			};
			$.ajax(opts);
		}
	}
	
	function resetFormData(type){
		var form;
		var isResetHidden = false;
		if (type == 1) {//学员
			form = $("#studentInfo");
			form.find(":radio[name=sex]:first").prev().click();
			var xy = form.find(":checkbox:last");
			if (!xy.prop("checked")) {
				var btn = form.find(">a.submits");
				xy.prev().click();
				btn.removeClass("disabled").attr("onclick", "submitClick(1);");
			}
		} else if (type == 2) {//讲师
			form = $("#teacherInfo");
			form.find(":radio[name=sex]:first").prev().click();
			var xy = form.find(":checkbox:last");
			if (!xy.prop("checked")) {
				var btn = form.find(">a.submits");
				xy.prev().click();
				btn.removeClass("disabled").attr("onclick", "submitClick(2);");
			}
		} else if (type == 3) {//机构
			form = $("#orgInfo");
			form.find(":radio[name=flagHasSpoc]:first").prev().click();
			form.find(":radio[name=applyType]:first").prev().click();
			var xy = form.find(":checkbox:last");
			if (!xy.prop("checked")) {
				var btn = form.find(">a.submits");
				xy.prev().click();
				btn.removeClass("disabled").attr("onclick", "submitClick(3);");
			}
			isResetHidden = true;
		} 
		
		if (form) {
			form.moocValidate("reset", isResetHidden);
			//form.find(":text,:password,textarea").val("");
			//var r = form.find(":radio[name=*]:first");
		}
	}
	
	function roleSelected(obj){
		var value = $(obj).val();
		if (value === 1) { //学员
			$("#studentInfo").show();
			$("#teacherInfo,#orgInfo").hide();
		} else if (value === 2) { //讲师
			$("#teacherInfo").show();
			$("#studentInfo,#orgInfo").hide();
		} else if (value === 3) { //机构
			$("#orgInfo").show();
			$("#teacherInfo,#studentInfo").hide();
		}
	}
	
	function hasSpocChange(obj){
		if ($("#spocType").is(":hidden")) {
			$("#spocType").show();
			$("#spocUrl").hide();
			
			var ra = $("#orgInfo").find(":radio[name=flagHasSpoc2]");
			ra.prop("checked", false);
			ra.prev().removeClass("jqTransformChecked");
		}
	}
	
	function hasSpocChange2(obj){
		if ($(obj).prop("checked")) {
			if (!$("#spocType").is(":hidden")) {
				$("#spocUrl").show();
				$("#spocType").hide();
				var cc = $("#spocUrl").find(">*:eq(1)");
				if(cc.is("div.jqTransformInputWrapper")){
					var ipt = cc.find("input").clone(true);
					ipt.removeAttr("style").removeAttr("class").addClass("inputs").addClass("moocValidate");
					ipt.moocValidate();
					$("#spocUrl").find("*:eq(0)").after(ipt);
					cc.remove();
				}
				
				var ra = $("#orgInfo").find(":radio[name=flagHasSpoc]");
				ra.prop("checked", false);
				ra.prev().removeClass("jqTransformChecked");
			}
		}
	}
	
	function isPrivateChange(obj){
		if (!$("#expectUrl").is(":hidden")) {
			$("#expectUrl").hide();
			
			var ra = $("#orgInfo").find(":radio[name=applyType2]");
			ra.prop("checked", false);
			ra.prev().removeClass("jqTransformChecked");
		}
		/* var value = $(obj).val();
		if (value === "$!orgApplyTypeInside#if(!$orgApplyTypeInside)1#end") { //联盟网站内嵌型
			$("#expectUrl").hide();
		} else if (value === "$!orgApplyTypeOutside#if(!$orgApplyTypeOutside)2#end") { //独立域名的SPOC平台
			$("#expectUrl").show();
		} */
	}
	
	function isPrivateChange2(obj){
		if ($("#expectUrl").is(":hidden")) {
			$("#expectUrl").show();
			var cc = $("#expectUrl").find(">*:eq(1)");
			if(cc.is("div.jqTransformInputWrapper")){
				var ipt = cc.find("input").clone(true);
				ipt.removeAttr("style").removeAttr("class").addClass("inputs_min").addClass("moocValidate");
				ipt.moocValidate();
				$("#expectUrl").find("*:eq(0)").after(ipt);
				cc.remove();
			}
			
			var ra = $("#orgInfo").find(":radio[name=applyType]");
			ra.prop("checked", false);
			ra.prev().removeClass("jqTransformChecked");
		}
	}
	
	function reloadVC(obj){
		var date = new Date();
		var s = obj.src;
		var tIndex = s.indexOf("&");
		if (tIndex > -1) {
			s = s.substring(0, tIndex);
		}
		s += "&t=" + date.getTime();
		obj.src = s;
	}
	
	function checkShowVCImg(type){
		if (!(parseInt(type) > -1)) return;
		var opts = {
			url:"/register/vc_show.html",
			type: "POST",
			data : {type:type,browser:browser},
			dataType:'json',
			success: function(res){
				if (res) {
					showVCImg(type, true);
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				//window.top.$.alert({mask:true,msg:"程序异常,请稍后再试!<br />若发生多次请联系管理员!"});
			}
		};
		$.ajax(opts);
	}
	
	function showVCImg(type, isFirstShow){
		var vcDiv;
		if (type == 1) {//学员
			vcDiv = $("#studentVC");
		} else if (type == 2) {//讲师
			vcDiv = $("#teacherVC");
		} else if (type == 3) {//机构
			vcDiv = $("#orgVC");
		}
		if (vcDiv) {
			vcDiv.show();
			var img = vcDiv.find("img");
			if (img && img.length > 0) {
				var s = "/passport/register/validate.html?type="+type+"&t=" + new Date().getTime();
				img[0].src = s;
			}
			if (isFirstShow) return;
			setTimeout(function(){
				vcDiv.find("input[name=validateCode]").moocValidate("validate");
			}, 50);
		}
	}
	
	function xyChangeEvent(obj){
		var form = $(obj).closest("form");
		var fid = form.attr("id");
		var btn = form.find("a.submits");
		if (btn && btn.length === 1) {
			if($(obj).prop("checked")){
				if (btn.hasClass("disabled")) {
					var clkStr = "";
					if (fid == "studentInfo") {//学员
						clkStr = "submitClick(1);";
					} else if (fid == "teacherInfo") {//讲师
						clkStr = "submitClick(2);";
					} else if (fid == "orgInfo") {//机构
						clkStr = "submitClick(3);";
					}
					btn.removeClass("disabled").attr("onclick", clkStr);
				}
			} else {
				if (!btn.hasClass("disabled")) {
					btn.addClass("disabled").removeAttr("onclick");
				}
			}
		}
	}
	
/******************************************************************************************** 头像      *****************************************************/
	/**
	 * 刷新前台传到后台的参数
	 */
	function freshParam(){
		var uploadURL = flash_uploadURL+"?"+flash_params;
		var flashvars = "uploadURL="+uploadURL + 
						"&&fileExt="+flash_fileExt +
						"&&img_width=" + flash_img_width + 
						"&&img_height=" + flash_img_height;
		flashCode = 
			'<OBJECT classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" '+
				'codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,40,0" '+
				'WIDTH="325" HEIGHT="350" id="photoEditor"> '+
				'<PARAM NAME=movie VALUE='+flashSrcURL+'> '+
				'<PARAM NAME=quality VALUE=high> '+
				'<PARAM NAME=bgcolor VALUE=#FFFFFF> '+
				'<PARAM NAME=flashvars VALUE='+flashvars+'> '+
				'<EMBED src="'+flashSrcURL+'" quality=high bgcolor=#FFFFFF WIDTH="325" HEIGHT="350"  '+
					'NAME="photoEditor" TYPE="application/x-shockwave-flash" flashvars="'+flashvars+'" '+
					'PLUGINSPAGE="http://www.macromedia.com/go/getflashplayer"> '+
				'</EMBED> '+
			'</OBJECT> ';
	}
	/**
	 * 学生头像修改
	 */
	function stuUpHeadPic(id){
		showPhotoEditor(
				"/static/js/plugins/flash/photoEditor.swf",  // flash头像编辑器地址
				"/register/uploadHeadPic.html", // 上传地址
				"id=" + id, // 上传的同时向后台传递的参数
				"", // "PNG"或"JPG"格式的图片
				"160",
				"160"
		);
	}
	/**
	 * 机构LOGO
	 */
	function stuUpOrgLogoPic(id){
		showPhotoEditor(
				"/static/js/plugins/flash/photoEditor.swf",  // flash头像编辑器地址
				"/register/uploadOrgLogoPic.html", // 上传地址
				"id="+id, // 上传的同时向后台传递的参数
				"", // "PNG"或"JPG"格式的图片
				"160",
				"160"
		);
	}
	/**
	 * 回调函数
	 * @param {Object} state 1为上传成功;0为上传失败
	 * @param {Object} mess
	 */
	function uploadStage(state,mess){
		artDg.close();
		var jsonstr = $.parseJSON(mess);
		var msg = jsonstr.msg;
		var status = jsonstr.status;
		var path = jsonstr.data;
		if(status == "1"){
			var split = "|split|";
			var realPath = path.replace(split, "");
			var splitIndex = path.indexOf(split);
			$("#"+msg).attr("src", realPath).attr("srcPath", path.substring(splitIndex + split.length));
		}else{
			window.top.$.messager.alert("提示", msg, "info");
		}
	}
	

//	const _studentType = $!studentType#if(!$studentType)1#end,
//		  _teacherType = $!teacherType#if(!$teacherType)2#end,
//		  _orgType = $!orgType#if(!$orgType)3#end;


function checkStrong(sValue) {
    var modes = 0;
    if (sValue.length < 6) return modes;
    if (/\d/.test(sValue)) modes++; //数字
    if (/[a-z]/.test(sValue)) modes++; //小写
    if (/[A-Z]/.test(sValue)) modes++; //大写  
    if (/\W/.test(sValue)) modes++; //特殊字符
    switch (modes) {
    case 1:
        return 1;
    case 2:
        return 2;
    case 3:
	    return 3;
    case 4:
        return 3;
        break;
    }
}

function areaSelected(obj){
	var $this = $(obj);
	var val = $this.val();
	if (val === "") {
		$.each($this.nextAll("select"), function(){
			clearSelect(this);
		});
	} else {
		var opts = {
			url:"/passport/register/getAreaData.html",
			type: "POST",
			data : {parentAreaCD:val},
			dataType:'json',
			success: function(res){
				if (res.status==0) {
					$.each($this.nextAll("select"), function(){
						clearSelect(this);
					});
					if (!res.data || res.data.length==0) {
						$this.nextAll("select").hide();
					}
					$.each(res.data, function(){
						var option = $('<option value="'+this.areaCD+'">'+this.areaName+'</option>');
						$this.next("select").append(option);
					});
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){}
		};
		$.ajax(opts);
	}
}

function orgSelected(obj){
	var $this = $(obj);
	var val = $this.val();
	if (val === "") {
		//$.each($this.nextAll("select"), function(){
		$.each($this.nextAll("select"), function(){

			clearSelect(this);
		});
	} else {
		var opts = {
			url:"/passport/register/orgClassData.html",
			type: "POST",
			data : {parentCodeValue:val},
			dataType:'json',
			success: function(res){
				if (res.status==0) {
					//$.each($this.nextAll("select"), function(){
					$.each($this.nextAll("select"), function(){
						clearSelect(this);
					});
					$.each(res.data, function(){
						var option = $('<option value="'+this.codeValue+'">'+this.codeName+'</option>');
						//$this.next().append(option);
						$this.next("select").append(option);
					});
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){}
		};
		$.ajax(opts);
	}
}

function selectPrince(){
		var opts = {
			url:"/passport/register/getAreaData.html",
			type: "POST",
			data : {parentAreaCD:1},
			dataType:'json',
			success: function(res){
				if (res.status==0) {
//					$("#selectPrince").empty();
//					var baseoption = $('<option value="">&lt;省份&gt;</option>');
//					$("#selectPrince").append(baseoption);
					$.each(res.data, function(){
						var option = $('<option value="'+this.areaCD+'">'+this.areaName+'</option>');
						$("#selectPrince").append(option);
					});
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){}
		};
		$.ajax(opts);
	
}
/**
 * 选择机构
 */
var index;
function selectOrg() {
	
	layer.open({
		type : 2,
		bgcolor : '#fff',//背景颜色
		title : '机构选择',//是否显示标题
		area : [ '1200px', '700px' ],
		border : [ 0 ], //去掉默认边框
		shade : [ 0.5, '#000' ], //去掉遮罩  不显示 直接是0
		closeBtn : [ 0, true ], //去掉默认关闭按钮
		
			content : 'selectOrg.html', //modify by zhangll 2015-04-03
			//src: 'addNoticeTip.html',
			scrolling : 'no',
		
		end : function() {
			document.getElementById("teacherOrgName").focus();
			document.getElementById("teacherOrgName").blur();
			//location.reload();
		}
	});
}
/**
 * 选择科目
 */
function selectSubject() {
	
	layer.open({
		type : 2,
		bgcolor : '#fff',//背景颜色
		title : '学科选择',//是否显示标题
		area : [ '1200px', '700px' ],
		border : [ 0 ], //去掉默认边框
		shade : [ 0.5, '#000' ], //去掉遮罩  不显示 直接是0
		closeBtn : [ 0, true ], //去掉默认关闭按钮
		
			content : 'selectSubject.html', //modify by zhangll 2015-04-03
			//src: 'addNoticeTip.html',
			scrolling : 'no',
		
		end : function() {
			document.getElementById("subJectName").focus();
			document.getElementById("subJectName").blur();
			//location.reload();
		}
	});
}


function selectBaseOrg(){
	var opts = {
		url:"/passport/register/orgClassData.html",
		type: "POST",
		//data : {parentAreaCD:1},
		dataType:'json',
		success: function(res){
			if (res.status==0) {
//				$("#selectBaseOrg").empty();
//				var baseoption = $('<option value="">&lt;机构学段&gt;</option>');
//				$("#selectBaseOrg").append(baseoption);
				$.each(res.data, function(){
					var option = $('<option value="'+this.codeValue+'">'+this.codeName+'</option>');
					$("#selectBaseOrg").append(option);
				});
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){}
	};
	$.ajax(opts);

}

function selectBaseSubject(){
	var opts = {
		url:"/passport/getAllSection.jhtml",
		type: "POST",
		//data : {parentAreaCD:1},
		dataType:'json',
		success: function(res){
			if (res.status==0) {
//				$("#selectBaseSubject").empty();
//				var baseoption = $('<option value="">&lt;学段&gt;</option>');
//				$("#selectBaseSubject").append(baseoption);
				$.each(res.data, function(){
					var option = $('<option value="'+this.codeValue+'">'+this.codeName+'</option>');
					$("#selectBaseSubject").append(option);
				});
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){}
	};
	$.ajax(opts);

}


function subjectSelected(obj){
	var $this = $(obj);
	var val = $this.val();
	if (val === "") {
		//$.each($this.nextAll("select"), function(){
		$.each($this.nextAll("select"), function(){

			clearSelect(this);
		});
	} else {
		var opts = {
			url:"/passport/getSubjectData.jhtml",
			type: "POST",
			data : {parentValue:val},
			dataType:'json',
			success: function(res){
				if (res.status==0) {
					//$.each($this.nextAll("select"), function(){
					$.each($this.nextAll("select"), function(){
						clearSelect(this);
					});
					$.each(res.data, function(){
						var option = $('<option value="'+this.childCodeValue+'">'+this.childCodeName+'</option>');
						//$this.next().append(option);
						$this.next("select").append(option);
					});
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){}
		};
		$.ajax(opts);
	}
}

function clearSelect(obj){
	$(obj).val("");
	$(obj).find("option:gt(0)").remove();
}
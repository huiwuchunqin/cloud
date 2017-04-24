/**
 * 联盟 表单验证 JQ 插件
 * zhangll v1.0
 * @param $
 */
(function ($) {
	
	const V_CLASS = ".moocValidate",
		 V_VALID_CLASS = "Validform_right",
		 V_INVALID_CLASS = "Validform_error";
	
	const V_OPTIONS_ATTR = "valid-options";
	
	$.extend({
	    moocValidate : function(){
	    	//$(V_CLASS).die("blur").live("blur", function(){
	        	
	        //});
	    	$(V_CLASS).moocValidate();
	    }
	});
	
	var MoocV = function(){
		
		//this.fns = ["init", "validate", "setInvalid", "setValid"];
		
		this.init = function(jq){
			var m_v = this;
			return jq.each(function(){
				$(this).undelegate("blur").unbind("blur").bind("blur", function(){
					m_v.validate($(this));
				}).undelegate("focus").unbind("focus").bind("focus",function(){
					m_v.focusM($(this));
				});
			});
		},
		this.focusM = function(jq){
			if (jq == null || !jq.is(V_CLASS)) return; 
			jq.parent().removeClass(V_INVALID_CLASS).removeClass(V_VALID_CLASS);
		//	jq.parent().nextAll("div.default").show();
			jq.parent().nextAll("div.Validform_wrong").remove();
		},
		this.validate = function(jq){
			if (jq == null) return false; 
			var m_v = this;
			if (jq.length == 1 && jq.is(V_CLASS)) {
				if (jq.is(":hidden")) return true;
				var res = true;
				jq.each(function(){
					var attr = $(this).attr(V_OPTIONS_ATTR);
					var opts = parseOpts(attr);
					var value = $(this).val();
					if (opts.isTrim === true) {
						value = $.trim(value);
						$(this).val($.trim(value));
					}
					
					if (opts.required === true) {
						if (value.length <= 0) {
							var msg = "该项为必输项";
							if ($.trim($(this).attr("nullmsg")) != "") {
								msg = $.trim($(this).attr("nullmsg"));
							}
							m_v.setInvalid($(this), msg);
							return res = false;
						}
					} else {
						if (value.length <= 0) {
							$(this).nextAll("div.default").hide().nextAll("div.Validform_wrong").remove();
							return true;
						}
					}
					
					if (opts.length) {
						if (typeof opts.length === "number") {
							if (value.length > opts.length) {
								var msg = "长度不能超过" + opts.length + "位字符";
								if ($.trim($(this).attr("lengthmsg")) != "") {
									msg = $.trim($(this).attr("lengthmsg"));
									msg = msg.replace("{0}", opts.length);
								}
								m_v.setInvalid($(this), msg);
								return res = false;
							}
						} else if (opts.length instanceof Array) {
							var len = opts.length.length;
							if (len === 1) {
								var maxL = opts.length[0];
								if (value.length > maxL) {
									var msg = "长度不能超过" + maxL + "位字符";
									if ($.trim($(this).attr("lengthmsg")) != "") {
										msg = $.trim($(this).attr("lengthmsg"));
										msg = msg.replace("{0}", maxL);
									}
									m_v.setInvalid($(this), msg);
									return res = false;
								}
							} else if (len === 2) {
								var minL = opts.length[0];
								var maxL = opts.length[1];
								if (minL && maxL) {
									if (value.length < minL || value.length > maxL) {
										var msg = "长度须在" + minL + "到" + maxL + "位字符之间";
										if ($.trim($(this).attr("lengthmsg")) != "") {
											msg = $.trim($(this).attr("lengthmsg"));
											msg = msg.replace("{0}", minL);
											msg = msg.replace("{0}", maxL);
										}
										m_v.setInvalid($(this), msg);
										return res = false;
									}
								} else {
									if (minL == null) {
										if (value.length > maxL) {
											var msg = "长度不能超过" + maxL + "位字符";
											if ($.trim($(this).attr("lengthmsg")) != "") {
												msg = $.trim($(this).attr("lengthmsg"));
												msg = msg.replace("{0}", maxL);
											}
											m_v.setInvalid($(this), msg);
											return res = false;
										}
									} else if (maxL == null) {
										if (value.length < minL) {
											var msg = "长度不能少于" + minL + "位字符";
											if ($.trim($(this).attr("lengthmsg")) != "") {
												msg = $.trim($(this).attr("lengthmsg"));
												msg = msg.replace("{0}", minL);
											}
											m_v.setInvalid($(this), msg);
											return res = false;
										}
									}
								}
							}
						}
					}
					
					if (opts.reg) {
						var reg,msg;
						if (typeof opts.reg === "string") {
							if (defaultReg[opts.reg]) {
								reg = defaultReg[opts.reg];
								msg = defaultRegMsg[opts.reg];
							}
						} else {
							reg = opts.reg;
							msg = "格式不正确";
						}
						if (reg && reg instanceof RegExp) {
							if (!reg.test(value)) {
								if ($.trim($(this).attr("regmsg")) != "") {
									msg = $.trim($(this).attr("regmsg"));
								}
								m_v.setInvalid($(this), msg);
								return res = false;
							}
						}
					}
					
					if (opts.eq) {
						if ($(opts.eq).val() !== $(this).val()) {
							var msg = "输入不一致";
							if ($.trim($(this).attr("eqmsg")) != "") {
								msg = $.trim($(this).attr("eqmsg"));
							}
							m_v.setInvalid($(this), msg);
							return res = false;
						}
					}
					
					var remote = opts.remote;
					if (remote && typeof remote.url == "string") {
						var $this = $(this);
						$.ajax({
							url:remote.url,
							type: "POST",
							async:true,
							data : $.extend({}, {value:value}, remote.param),
							dataType:'json',
							success: function(_j){
								if (_j.data === false) {
									var msg = "远程url验证不通过";
									if ($.trim($this.attr("remotemsg")) != "") {
										msg = $.trim($this.attr("remotemsg"));
									}
									m_v.setInvalid($this, msg);
									res = false;
								}
							},
							error:function(XMLHttpRequest, textStatus, errorThrown){
								var msg = "远程验证地址出错，请联系管理员";
								m_v.setInvalid($this, msg);
								res = false;
								//window.top.$.alert({mask:true,msg:"程序异常,请稍后再试!<br />若发生多次请联系管理员!"});
							}
						});
					}
					
					if (res === true) {
						m_v.setValid($(this));
					}
				});
				return res;
			}
			
			var last,res = true;
			jq.each(function(i){
				if ($(this).is(V_CLASS)) {
					if (i == 0) {//第一次
						last = res = m_v.validate($(this));
					} else {
						res = m_v.validate($(this)) && last;
					}
				} else {
					if (i == 0) {//第一次
						last = res = m_v.validate($(this).find(V_CLASS));
					} else {
						res = m_v.validate($(this).find(V_CLASS)) && last;
					}
				}
			});
			
			//var res2 = m_v.validate(jq.find(V_CLASS));
			return res;
		},
		this.setInvalid = function(jq, msg){
			if (jq == null || !jq.is(V_CLASS)) return jq;
			jq.parent().nextAll("div.Validform_wrong").remove();
			var msgE = '<div class="Validform_checktip Validform_wrong bg-danger"></div>';
			jq.addClass(V_INVALID_CLASS).removeClass(V_VALID_CLASS).parent().nextAll("div.default").hide().after($(msgE).html(msg));
			/*
			jq.nextAll("span.Validform_wrong").remove();
			var msgE = '<span class="Validform_checktip Validform_wrong"></span>';
			jq.addClass(V_INVALID_CLASS).removeClass(V_VALID_CLASS).nextAll("span.default").hide().after($(msgE).html(msg));
			*/
			return jq;
		},
		this.setValid = function(jq){
			if (jq == null || !jq.is(V_CLASS)) return; 
			jq.addClass(V_VALID_CLASS).removeClass(V_INVALID_CLASS).parent().nextAll("div.default").hide().nextAll("div.Validform_wrong").remove();
		},
		this.reset = function(jq, isResetHidden){
			if (jq == null) return; 
			var m_v = this;
			if (jq.length == 1 && jq.is(V_CLASS)) {
				jq.removeClass(V_VALID_CLASS).removeClass(V_INVALID_CLASS).parent().nextAll("div.default").hide().nextAll("div.Validform_wrong").remove();
				if (jq.is(":hidden")) {
					if (isResetHidden) jq.val("");
				} else {
					jq.val("");
				}
				return jq;
			}
			
			return jq.each(function(){
				if ($(this).is(V_CLASS)) {
					$(this).moocValidate("reset", isResetHidden);
				} else {
					$(this).find(V_CLASS).moocValidate("reset", isResetHidden);
				}
			});
		};
		
	}
	
	
	$.fn.moocValidate = function(type, params){
		
		var m_v = new MoocV();
		
		if (type == null || typeof type === "object") {
			return m_v.init(this);
		}
		
		if (typeof type === "string" && m_v[type]) {
			return m_v[type](this, params);
		}
	}
	
	var defaultOpts = {
		isTrim : false,
		required:false,
		length:false,
		reg:false,
		eq:false,
		remote:false,
		func:false//自定义
	};
	
	var defaultReg = {
		mobilephone:/^1\d{10}$/,
		email:/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
		url:/^(\w+:\/\/)?\w+(\.\w+)+.*$/,
		qq:/^[1-9][0-9]{4,12}$/
	}
	
	var defaultRegMsg = {
		mobilephone:"手机号码格式不正确，正确示例：13888888888",
		email:"邮箱格式不正确，正确示例：xxxx@xxx.com",
		url:"网址格式不正确，正确示例：www.emooc.com",
		qq:"QQ号码格式不正确，正确示例188888"
	}
	
	var defaultMsg = {
		
	};
	
	var parseOpts = function(str){
		var obj = optStrToObj(str);
		return $.extend({}, defaultOpts, obj);
	}
	
	var optStrToObj = function(str){
		if (typeof str === "undefinded" || str == null) {
			str = "";
		}
		
		if (typeof str != "string") return str;
		
		str = $.trim(str);
		str = '{' + str + '}';
		var res;
		try {
			res = eval('(' + str + ')');
		} catch (e) {}
		return res;
	}
	
})(jQuery);

$(function(){
	$.moocValidate();
});
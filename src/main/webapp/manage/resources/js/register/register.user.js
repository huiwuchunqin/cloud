$(document).keydown(function(event) {
		if (event.keyCode == 13) {
			checkSubmit();
		}
	});
//提示框隐藏 事件绑定 数据初始化
$(function(){
	$(".bg-danger").hide();//警告信息隐藏
	$("#pwdStrong").hide();//密码强度提示隐藏
	$("input").focus(msgHide);//所有的输入框在获取焦点后，对应的提示信息隐藏
	$("input[name='loginname']").blur(checkLoginname);//登录名输入框失去焦点后验证
	$("input[name='email']").blur(checkEmail);//email输入框失去焦点后验证
	$("input[name='mobile']").blur(checkMobile);//手机号输入框失去焦点后验证
	$("input[name='username']").blur(checkUsername);//姓名输入框失去焦点后验证
	$("input[name='password']").blur(checkPassword);//密码输入框失去焦点后验证
	$("input[name='password']").keyup(showPwdStrong);//根据输入的密码显示密码强度
	$("input[name='repassword']").blur(checkRepassword);//重复密码输入框失去焦点后验证
	$("input[name='captcha']").blur(checkCaptcha);//验证码输入框失去焦点后验证
	$("#area select").change(checkArea);//改变区域下拉框选择时验证
	$("#captcha_img").click(nextCaptcha);//点击验证码图片更换验证码
	$("#read").click(checkRead);//点击时检查协议是否同意
	$("#regist_btn").click(checkSubmit);//提交注册时重新判断以上所有检查是否通过

	queryAreas();//初始化地区集合
});

//校验是否通过检查的标记
var ok_loginname = false;
var ok_email = false;
var ok_mobile = false;
var ok_username = false;
var ok_area = false;
var ok_pwd = false;
var ok_repwd = false;
var ok_captcha = false;
var ok_read = false;

//校验用户名方法
function checkLoginname(){
	var $loginname = $("input[name='loginname']");
	var loginname = $loginname.val();
	var $loginname_msg = $("#loginname_msg");
	if(loginname != null && loginname != undefined){
		loginname = loginname.trim();
		$loginname_msg.hide();
		if(loginname != ""){
			if(loginname.length<21){
				var opts = {
						url : "/register/user/check/loginname.html",
						type : "post",
						data : {loginName : loginname},
						dataType : 'json',
						success : function(res) {
							if (res.success) {
								ok_loginname = true;
							}else{
								$loginname_msg.show();
								$loginname_msg.html(res.msg);
								ok_loginname = false;
							}; 
						},
						error : function(XMLHttpRequest, textStatus, errorThrown) {
							layer.alert("提示", "程序异常,请稍后再试!<br />若发生多次请联系管理员！", "info");
						}
				};
				$.ajax(opts);
			}else{
				$loginname_msg.show();
				$loginname_msg.html("用户名不能超过20个字符");
				ok_loginname = false;
			};
		}else {
			$loginname_msg.show();
			$loginname_msg.html("请输入用户名");
			ok_loginname = false;
		};
	}else{
		ok_loginname = false;
	};
};

//校验邮箱方法
function checkEmail(){
	var $email = $("input[name='email']");
	var email = $email.val();
	var $email_msg = $("#email_msg");
	var reg_email = /^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\.][A-Za-z]{2,3}([\.][A-Za-z]{2})?$/;
	if(email != null && email != undefined){
		email = email.trim();
		$email_msg.hide();
		if(email != ""){
			if(reg_email.test(email)){
				var opts = {
						url : "/register/user/check/email.html",
						type : "post",
						data : {email : email},
						dataType : 'json',
						success : function(res) {
							if (res.success) {
								ok_email = true;
							}else{
								$email_msg.show();
								$email_msg.html(res.msg);
								ok_email = false;
							}; 
						},
						error : function(XMLHttpRequest, textStatus, errorThrown) {
							layer.alert("提示", "程序异常,请稍后再试!<br />若发生多次请联系管理员！", "info");
						}
				};
				$.ajax(opts);
			}else{
				$email_msg.show();
				$email_msg.html("邮箱格式不正确，正确示例：xxxx@xxx.com");
				ok_email = false;
			}
		}else {
			$email_msg.show();
			$email_msg.html("请输入您的邮箱");
			ok_email = false;
		};
	}else{
		ok_email = false;
	};
};

//校验手机号方法
function checkMobile(){
	var $mobile = $("input[name='mobile']");
	var mobile = $mobile.val();
	var $mobile_msg = $("#mobile_msg");
	var reg_mobile = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
	if(mobile != null && mobile != undefined){
		mobile = mobile.trim();
		$mobile_msg.hide();
		if(mobile != ""){
			if(reg_mobile.test(mobile)){
				var opts = {
						url : "/register/user/check/mobile.html",
						type : "post",
						data : {mobile : mobile},
						dataType : 'json',
						success : function(res) {
							if (res.success) {
								ok_mobile = true;
							}else{
								$mobile_msg.show();
								$mobile_msg.html(res.msg);
								ok_mobile = false;
							}; 
						},
						error : function(XMLHttpRequest, textStatus, errorThrown) {
							layer.alert("提示", "程序异常,请稍后再试!<br />若发生多次请联系管理员！", "info");
						}
				};
				$.ajax(opts);
			}else{
				$mobile_msg.show();
				$mobile_msg.html("手机号码格式不正确，正确示例：13888888888");
				ok_mobile = false;
			};
		}else {
			$mobile_msg.show();
			$mobile_msg.html("请输入您的手机号");
			ok_mobile = false;
		}
	}else{
		ok_mobile = false;
	};
};

//校验姓名方法
function checkUsername(){
	var $username = $("input[name='username']");
	var username = $username.val();
	var $username_msg = $("#username_msg");
	if(username != null && username != undefined){
		username = username.trim();
		$username_msg.hide();
		if(username != ""){
			if(username.length<51){
				ok_username = true;
			}else{
				$username_msg.show();
				$username_msg.html("姓名不能超过50个字符");
				ok_username = false;
			};
		}else{
			$username_msg.show();
			$username_msg.html("请输入您的姓名，建议使用真实姓名");
			ok_username = false;
		};
	}else{
		ok_username = false;
	};
};

//校验地区选择方法
function checkArea(){
	var province = $("#province option:selected").val();
	var city = $("#city option:selected").val();
	var district = $("#district option:selected").val();
	var $area_msg = $("#area_msg");
	if(province != null && province != undefined && province != ""){
		if(city != null && city != undefined && city != ""){
			if(province == 99 || province == 82){//当省份不是海外和澳门时才判断区县是否选择
				$area_msg.hide();
				ok_area = true;
			}else{
				if(district != null && district != undefined && district != "" ){
					$area_msg.hide();
					ok_area = true;
				}else{
					$area_msg.show();
					$area_msg.html("请选择地区");
					ok_area = false;
				};
			};
		}else{
			$area_msg.show();
			$area_msg.html("请选择城市");
			ok_area = false;
		};
	}else{
		$area_msg.show();
		$area_msg.html("未选择所在区域");
		ok_area = false;
	};
}

//校验密码方法
function checkPassword(){
	var $password = $("input[name='password']");
	var password = $password.val();
	var $password_msg = $("#password_msg");
	if(password != null){
		password = password.trim();
		$password_msg.hide();
		if(password != "" && password.length>5){
			ok_pwd = true;
		}else{
			$password_msg.show();
			$password_msg.html("请输入六位以上密码，建议字母+特殊字符的组合");
			ok_pwd = false;
		};
	}else{
		ok_pwd = false;
	};
};

//校验重复密码方法
function checkRepassword(){
	var password = $("input[name='password']").val();
	var $repassword = $("input[name='repassword']");
	var repassword = $repassword.val();
	var $repassword_msg = $("#repassword_msg");
	if(repassword != null){
		repassword = repassword.trim();
		$repassword_msg.hide();
		if(repassword != ""){
			if(repassword == password){
				ok_repwd = true;
			}else{
				$repassword_msg.show();
				$repassword_msg.html("两次输入的密码不一致");
				ok_repwd = false;
			}
		}else{ 
			$repassword_msg.show();
			$repassword_msg.html("请再次确认密码");
			ok_repwd = false;
		};
	}else{
		ok_repwd = false;
	};
};

//校验验证码方法
function checkCaptcha(){
	var $captcha = $("input[name='captcha']");
	var captcha = $captcha.val();
	var $captcha_msg = $("#captcha_msg");
	if(captcha != null){
		captcha = captcha.trim();
		$captcha_msg.hide();
		if(captcha != ""){
			var opts = {
					url : "/register/user/check/captcha.html",
					type : "post",
					data : {captcha : captcha},
					dataType : 'json',
					success : function(res) {
						if (res.success) {
							ok_captcha = true;
						}else{
							$captcha_msg.show();
							$captcha_msg.html(res.msg);
							ok_captcha = false;
						}; 
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						layer.alert("提示", "程序异常,请稍后再试!<br />若发生多次请联系管理员！", "info");
					}
			};
			$.ajax(opts);
		}else{
			$captcha_msg.show();
			$captcha_msg.html("请输入验证码");
			ok_captcha = false;
		};
	}else{
		ok_captcha = false;
	};
};


//检查协议阅读是否勾选
function checkRead(){
	$read_msg = $("#read_msg");
	if($("#read").is(":checked")){
		$read_msg.hide();
		ok_read = true;
	}else{
		$read_msg.show();
		$read_msg.html("需要同意我们的协议才能完成注册哦~");
		ok_read = false;
	}
}
//校验是否可以提交注册
function checkSubmit(){
	checkLoginname();
	checkEmail();
	checkMobile();
	checkArea();
	checkUsername();
	checkPassword();
	checkRepassword();
	checkCaptcha();
	checkRead();
	if(ok_loginname && ok_email && ok_mobile && ok_area && ok_username && ok_pwd && ok_repwd && ok_captcha && ok_read){
		//验证通过后将全局变量置空,方便浏览器回收
		ok_loginname = null;
		ok_email = null;
		ok_mobile = null;
		ok_area = null;
		ok_username = null;
		ok_pwd = null;
		ok_repwd = null;
		ok_captcha = null;
		ok_read = null;
		
		sureSubmit();
	};
};

//提交保存
function sureSubmit(){
	var loginName = $("input[name='loginname']").val();//登录名
	var sex = $("#sex input:radio:checked").val();//性别
	var email = $("input[name='email']").val();//邮箱
	var mobile = $("input[name='mobile']").val();//手机号
	var username = $("input[name='username']").val();//用户名
	var loginPwd = $("input[name='password']").val();//登录密码
	var province = $("#province option:selected").val();//所在省
	var city = $("#city option:selected").val();//所在市
	var area = $("#district option:selected").val();//所在区县
	var captcha = $("input[name='captcha']").val();//验证码
	var opts = {
			url : "/register/user/save.html",
			type : "post",
			data : {
				loginName : loginName,
				sex : sex,
				email : email,
				mobile : mobile,
				userName : username,
				loginPwd : loginPwd,
				province : province,
				city : city,
				area : area,
				captcha : captcha
			},
			dataType : 'json',
			success : function(res) {
				if (res.success) {
					layer.alert("申请成功！");
					setTimeout(function(){
						window.location.href="/space/user/set/index.html"
				    },800);
				}else{
					alert(res.msg);
				}; 
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.alert("提示", "程序异常,请稍后再试!<br />若发生多次请联系管理员！", "info");
			}
	};
	$.ajax(opts);

};

//更换验证码方法
function nextCaptcha(){
	var r = Math.random();
	var img_url = "/register/user/captcha.html?"+r;
	$("#captcha_img").attr("src",img_url);	
};

//警告信息隐藏方法
function msgHide(){
	$(this).parent().parent().find(".bg-danger").hide();
};

//检查密码安全强度方法
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
	case 4:
		return 3;
		break;
	};
};

//显示密码强度标记
function showPwdStrong(){
	var password = $("input[name='password']").val();
	if(password != null && password != undefined){
		password = password.trim();
		var type = checkStrong(password);
		if(type == 0){
			$("#pwdStrong").hide();
			$("#pwd_p").addClass("btn-cancel");
			$("#pwd_m").addClass("btn-cancel");
			$("#pwd_s").addClass("btn-cancel");					
		}
		if(type == 1){
			$("#pwdStrong").show();
			$("#pwd_p").removeClass("btn-cancel");
			$("#pwd_p").addClass("btn-danger");
			$("#pwd_m").addClass("btn-cancel");
			$("#pwd_s").addClass("btn-cancel");
		};
		if(type == 2){
			$("#pwdStrong").show();
			$("#pwd_m").removeClass("btn-cancel");
			$("#pwd_m").addClass("btn-warning");
			$("#pwd_p").addClass("btn-cancel");
			$("#pwd_s").addClass("btn-cancel");
		};
		if(type == 3){
			$("#pwdStrong").show();
			$("#pwd_s").removeClass("btn-cancel");
			$("#pwd_s").addClass("btn-success");
			$("#pwd_m").addClass("btn-cancel");
			$("#pwd_p").addClass("btn-cancel");
		};
	};			
};

//获取地区集合
function queryAreas() {
	var opts = {
			url : "/navfilter/area/list.html",
			type : "post",
			data : {parentAreaCD :setAreasViewModel.provinceParentAreaCD() },
			dataType : 'json',
			success : function(res) {
				if (res.success) {
					//当地区信息初始化后，初始化省份选择框的sections
					setAreasViewModel.provinceAreas(res.data);
				} else if (res.success == false) { //系统错误
					layer.alert(res.msg)
				};
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.alert("提示", "程序异常,请稍后再试!<br />若发生多次请联系管理员！", "info");
			}
	};
	$.ajax(opts);
};

function SetAreasViewModel() {
	var self = this;
	KO.observableAll(self, {
		provinceParentAreaCD : '1',//省份父区域代码
		provinceAreaCD : '',//选中的省份区域代码
		cityAreaCD : '',//选中的城市区域代码
		provinceAreas : [],//根据父区域代码获取的省份区域信息
		cityAreas : [],//根据父区域代码获取的城市区域信息
		districtAreas : []//根据父区域代码获取的县区区域信息
	});

	//改变省份选择
	self.changeProvince = function(){
		//选中省份的区域代码
		provinceAreaCD = self.provinceAreaCD();
		if(provinceAreaCD == undefined || provinceAreaCD == "" || provinceAreaCD == null){
			self.cityAreas([]);
			self.districtAreas([]);
		}else{
			var opts = {
					url : "/navfilter/area/list.html",
					type : "post",
					data : {parentAreaCD : provinceAreaCD},
					dataType : 'json',
					success : function(res) {
						if (res.success) {
							//根据省份信息加载城市选择框的sections
							self.cityAreas(res.data);
						} else if (res.success == false) { //系统错误
							layer.alert(res.msg)
						};
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						layer.alert("提示", "程序异常,请稍后再试!<br />若发生多次请联系管理员！", "info");
					}
			};
			$.ajax(opts);
		};
	};

	//改变城市选择
	self.changeCity = function(){
		//选中城市的区域代码
		cityAreaCD = self.cityAreaCD();
		if(cityAreaCD == undefined || cityAreaCD == "" || cityAreaCD == null){
			self.districtAreas([]);
		}else{
			var opts = {
					url : "/navfilter/area/list.html",
					type : "post",
					data : {parentAreaCD : cityAreaCD},
					dataType : 'json',
					success : function(res) {
						if (res.success) {
							//根据城市信息加载县区选择框的sections
							self.districtAreas(res.data);
						} else if (res.success == false) { //系统错误
							layer.alert(res.msg)
						};
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						layer.alert("提示", "程序异常,请稍后再试!<br />若发生多次请联系管理员！", "info");
					}
			};
			$.ajax(opts);
		};
	};
};
var setAreasViewModel = new SetAreasViewModel();
ko.applyBindings(setAreasViewModel, $("#area")[0]);
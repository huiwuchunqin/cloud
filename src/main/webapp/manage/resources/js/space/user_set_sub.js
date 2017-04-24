
	//提示框隐藏 事件绑定 数据初始化
	$(function(){
		queryUserInfo();
	});
	
	var oldSectionCode="";
	var oldGradeCode="";
	var oldSubjectCode="";
	//校验是否通过检查的标记
	var ok_email = true;
	var ok_mobile = true;
	var ok_username = false;
	var ok_subject = false;
	var ok_userText = false;

	
	//查询机构信息
	function queryUserInfo() {
		var opt = {
			url : "/space/user/set/info.html",
			data : {},
			type : "post",
			dataType : "json",
			success : function(result) {
				if (result.success) {
					$("input").focus(msgHide);//所有的输入框在获取焦点后，对应的提示信息隐藏
					$("#userText").focus(msgHide);//文本域在获取焦点后，对应的提示信息隐藏
					$("#subjectSpace select").change(checkSubject);//学科下拉框改变后检查
					$(".bg-danger").hide();//警告信息隐藏
					userSetSubModel.userName(result.data.userName);
					userSetSubModel.sex(result.data.sex+"");
					userSetSubModel.orgName(result.data.orgName);
					userSetSubModel.email(result.data.email);
					userSetSubModel.mobile(result.data.mobile);
					userSetSubModel.userText(result.data.userText);
					userSetSubModel.role(result.data.role);
					userSetSubModel.loginName(result.data.loginName);
					oldSectionCode = result.data.sectionCode;
					oldGradeCode = result.data.gradeCode;
					oldSubjectCode = result.data.subjectCode;
					querySections();
				} else if(result.errorCode==9000){
					layer.open({
						type : 2,
						content : '/login/sub.html',
						title : '用户登录',
						area : [ '320px', '375px' ],
						closeBtn: false
					});
				}else{
					layer.alert(result.msg);
				}
			}
		};
		$.ajax(opt);

	}

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
							url : "/space/user/set/check/email.html",
							type : "post",
							data : {email : email},
							dataType : 'json',
							success : function(res) {
								if (res.success) {
									ok_email = true;
									return;
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
							url : "/space/user/set/check/mobile.html",
							type : "post",
							data : {mobile : mobile},
							dataType : 'json',
							success : function(res) {
								if (res.success) {
									ok_mobile = true;
									return;
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
	function checkUserName(){
		
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
	//校验学科选择方法,先判断是否为老师,若老师则判断,不是则跳过
	function checkSubject(){
		if(userSetSubModel.role()==2){
			var subject = $("#subject option:selected").val();
			var grade = $("#grade option:selected").val();
			var section = $("#section option:selected").val();
			var $subject_msg = $("#subject_msg");
			if(section != null && section != undefined && section != ""){
				if(grade != null && grade != undefined && grade != ""){
					if(subject != null && subject != undefined && subject != ""){
						$subject_msg.hide();
						ok_subject = true;
					}else{
						$subject_msg.show();
						$subject_msg.html("请选择学科");
						ok_subject = false;
					};
				}else{
					$subject_msg.show();
					$subject_msg.html("请选择年级");
					ok_subject = false;
				};
			}else{
				$subject_msg.show();
				$subject_msg.html("请选择学段");
				ok_subject = false;
			};
		}else{
			ok_subject = true;
		}
	}
	//校验个人简介方法
	function checkUserText(){
		var $userText = $("#userText");
		var userText = $userText.val();
		var $userText_msg = $("#userText_msg");
		if(userText != null && userText != undefined){
			$userText_msg.hide();
			if(userText.length<251){
				ok_userText = true;
			}else{
				$userText_msg.show();
				$userText_msg.html("个人签名不能超过250个字符");
				ok_userText = false;
			}
		}else{
			ok_userText = false;
		}
		
	}

	function sureSubmit(){
		var userName = $("input[name='username']").val();//用户名
		var sex = $("#sex input:radio:checked").val();//性别
		var email = $("input[name='email']").val();//邮箱
		var mobile = $("input[name='mobile']").val();//手机号
		var section = $("#section option:selected").val();//学段
		var grade = $("#grade option:selected").val();//年级
		var subject = $("#subject option:selected").val();//学科
		var userText = $("#userText").val();//个人签名
		var opts = {
				url : "/space/user/set/info/save.html",
				type : "post",
				data : {
					userName : userName,
					sex : sex,
					email : email,
					mobile : mobile,
					sectionCode : section,
					gradeCode : grade,
					subjectCode : subject,
					userText : userText,
				},
				dataType : 'json',
				success : function(res) {
					if (res.success) {
						window.parent.queryUserInfo();
						window.top.layer.close(window.top.layer.index); //关闭窗口
						return;
					}else{
						layer.alert(res.msg);
					}; 
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					layer.alert("提示", "程序异常,请稍后再试!<br />若发生多次请联系管理员！", "info");
				}
		};
		$.ajax(opts);
	}


	//警告信息隐藏方法
	function msgHide(){
		$(this).parent().parent().find(".bg-danger").hide();
	};

	//初始化学段集合
	function querySections() {
		var getSections = {
			url : "/navfilter/section/list.html",
			type : "POST",
			data : {},
			dataType : 'json',
			success : function(res) {
				if (res.success) {
					//当学段初始化后，初始化选择框的sections
					userSetSubModel.sections(res.data);
					//当学段初始化后，设置学段额选中值							
					userSetSubModel.sectionCode(oldSectionCode);
					//初始化年级集合
					queryGrade(userSetSubModel.sectionCode());
					//初始化学科集合
					querySubject(userSetSubModel.sectionCode());
				} else if (res.success == false) { //系统错误
					layer.alert(res.msg)
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.alert("提示", "程序异常,请稍后再试!<br />若发生多次请联系管理员！", "info");
			}
		};
		$.ajax(getSections);
	}
	//获取年级集合
	function queryGrade(sectionCode) {
		$.ajax({
			url : "/navfilter/grade/list.html",
			data : {
				sectionCode : sectionCode
			},
			type : "post",
			dataType : "json",
			success : function(result) {
				if (result.success) {
					//当年级初始化后，初始化选择框的Grades
					userSetSubModel.grades(result.data);
					//当年级初始化后，初始化选择框的gradecode
					userSetSubModel.gradeCode(oldGradeCode);
				}
			}
		});
	}
	//获取学科集合
	function querySubject(sectionCode) {
		//获取学科集合
		$.ajax({
			url : "/navfilter/subject/list.html",
			data : {
				sectionCode : sectionCode
			},
			type : "post",
			dataType : "json",
			success : function(result) {
				if (result.success) {
					//当学科初始化后，初始化选择框的subjects
					userSetSubModel.subjects(result.data);
					//当学科初始化后，初始化选择框的subjectCode
					userSetSubModel.subjectCode(oldSubjectCode);
				}
			}
		});
	}
	function UserSetSubModel() {
		var self = this;
		KO.observableAll(self, {
			loginName : "",//登录账号
			role : "",//角色类型
			sections : "", //所有的学段
			sectionCode : "", //选中的学段
			grades : "", //所有年级
			gradeCode : "", //选中的年级
			subjects : "", //所有学科
			subjectCode : "", //选中学科
			userName : "",//用户名
			orgName : "", //机构名
			userText : "", //用户介绍
			email : "",//邮箱
			mobile : "",//手机号
			sex : "",//性别
		});
		//根据学段获取年级
		self.changeSection = function() {
			sectionCode = self.sectionCode();
			//没有选择section就把grades和subjecs置空
			if (sectionCode == undefined || sectionCode == "" || sectionCode == null) {
				self.grades([]);
				self.subjects([]);
			} else {
				//查询年级
				var opts = {
					url : "/navfilter/grade/list.html",
					type : "post",
					data : {
						sectionCode : sectionCode
					},
					success : function(res) {
						if (res.success) {
							self.grades(res.data);
							//学段改变，将年级置为空
							self.gradeCode("");
						} else if (res.success == false) { //系统错误
							layer.alert(res.msg)
						}
					},
					error : function() {
					}
				}
				$.ajax(opts);
				var gradeCode = self.gradeCode();
				var subjectCode = self.subjectCode();
				
			}
		}
		//根据年级变化获取学科
		self.changeGrade = function() {
			gradeCode = self.gradeCode();
			sectionCode = self.sectionCode();
			//没有选择section就把grade和subjecs置空
			if (gradeCode == undefined || gradeCode == "" || gradeCode == null) {
				self.subjects([]);
			} else {
				//查询学科
				var opts = {
					url : "/navfilter/subject/list.html",
					type : "post",
					data : {
						sectionCode : sectionCode
					},
					success : function(res) {
						if (res.success) {
							self.subjects(res.data);
							//年级改变，将学科置为空
							self.subjectCode("");
						} else if (res.success == false) { //系统错误
							layer.alert(res.msg)
						}
					},
					error : function() {
					}
				}
				$.ajax(opts);
				var gradeCode = self.gradeCode();
				var subjectCode = self.subjectCode();
			}
		}
		//检查并提交保存
		self.checkSubmit = function(){
			checkEmail();
			checkMobile();
			checkSubject();
			checkUserName();
			checkUserText();
			if(ok_email && ok_mobile && ok_subject && ok_username && ok_userText){
				sureSubmit();
				//验证通过后将全局变量置空,方便浏览器回收
				ok_email = null;
				ok_mobile = null;
				ok_subject = null;
				ok_username = null;
				ok_userText = null;
				oldSectionCode=null;
				oldGradeCode=null;
				oldSubjectCode=null;
			};

		};
		//取消按钮事件
		self.cancle = function() {
			window.top.layer.close(window.top.layer.index);
			
		}
	}
	var userSetSubModel = new UserSetSubModel();
	ko.applyBindings(userSetSubModel, $("body")[0]);
	
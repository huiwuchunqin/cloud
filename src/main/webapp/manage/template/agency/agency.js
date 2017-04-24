$(function(){
		
			var model=new School(org,sectionList);
			
			/**
			*机构不为空为修改 隐藏 账号密码项
			*机构为空位新增
			*/
			model.add(org==null);
			
			/**修改的情况下
			 * 不要验证密码
			 * 不需要验证登录名
			 */
			if(!model.add()){
				delete model.password;
				delete model.loginAccount;
			}
			
			model.schoolRelated=ko.observable(schoolRelated);
			ko.applyBindingsWithValidation(model);
/*			
			//图片上传事件
				imageUpload(
							$('#file'),
							$('#image'), 
							function(path){
								model.logoPath(path);
							}
					     );
*/	})

function School(org,sectionList){
		var self=this;
		
		if(org==null){
			org={
						orgName : "",		
						orgNameShaore	:	"",
						sectionName	:	"",
						sectionCode	:	"",
						mail	:	"",
						phone	:	"",
						orgCode	:	"",
						path	:	"",
						remark	:	"",
						topName	:	"",
					/*	domailURL	:	"",
						logoUrl	:	"",*/
					}		
		}
		
		
		//编辑还是修改
		self.add=ko.observable(true);
		//是否显示取消按钮
		self.withCancel=ko.observable(noCancel!="true");
		/**
		*是否是跟机构有关的操作
		* true 		是
		* ""||false 不是
		*/
		self.schoolRelated=ko.observable(false);
		self.orgName=ko.validatedObservable(org.orgName).extend({
									required	:	true,
									maxLength	: 	60,
								});
		self.orgNameShort=ko.validatedObservable(org.orgNameShort).extend({
									required	:	true,
									maxLength	:	 60,
								});
		self.sectionName=ko.observable(org.sectionName);
		self.mail=ko.validatedObservable(org.mail).extend({
			email:{
				message:"邮箱格式不正确"
			}
		}); 
		
		self.phone=ko.observable(org.phone).extend({
								contact:{
									param	:	true,
									message	:	"联系电话格式不正确",
								}
							});
		self.orgCode=ko.observable(org.orgCode);
		var path=org.logoUrl?((imgHost+"/"+org.logoUrl)):org.logoUrl;
		self.path=ko.observable(path);
		/* self.modifyTime=moment(org.modifyTime).format("YYYY-MM-DD  H:mm")||"暂无"; */
		self.remark=ko.validatedObservable(org.remark).extend({
					maxLength	:	200
		});
		self.section={
				source:ko.observableArray(sectionList||[]),
				val:ko.observable(org.sectionCode),
				viewSettings:{
					editable	:	false,
					textField	:	"name",
					valueField	:	"code",
					required	:	true,
				}
		}
		self.loginAccount=ko.validatedObservable("").extend({
										required:true,
										alphanumMaxLength:50
								});
		self.password=ko.validatedObservable("").extend({
				password:{
				param	:	true, 
				message	:	"6位以上的半角字符或数字",
			}
		});
		self.topName=ko.observable(org.topName);
/*		self.domainURL=ko.observable(org.domainURL).extend({
									required	:	true,
									maxLength	:	 100,
									web:true,
								})*/
		
		//获得系统自动生成的管理员账号
		self.orgNameShort.subscribe(function(data){
			if(self.loginAccount){
				$.get("/manage/transformAccount.html",{orgShortName:data},function(data){
					self.loginAccount(data)
					
				})
			}
		});
		
/*		self.logoPath=ko.observable(org.logoUrl).extend({
							required	:	true,
						});*/
		self.cancel=function(){
			easyui_closeTopWindow();
			window.location.href=window.location.href;
		};
		self.save=function(){
				
				if(self.schoolRelated()&&self.section.val()==""){
					alert("学段不能为空");
					return false;
				}
			/*	if(self.logoPath()==""){
					alert("请上传logo");
					return false;
				}*/
				if(ko.validation.group(self)().length>0){
					ko.validation.group(self).showAllMessages();
					return false;
				}
				var data={
						orgName:self.orgName(),
						orgNameShort:self.orgNameShort(),
						phone:self.phone(),
						mail:self.mail(),
						orgCode:self.orgCode(),
						sectionCode:self.section.val(),
						remark:self.remark(),
						topName:self.topName(),
						mail:self.mail(),
						
						
				};
				var url="";
				if(!self.add()){
					 url="/manage/agency/updateAgency.html";
				}else{
					//新增要输入密码
					data.password=self.password();
					data.loginAccount=self.loginAccount();
					if(self.schoolRelated()){
						url="/manage/agency/addSchool.html";					
					}else{
						 url="/manage/agency/addAgency.html";
					}
					
				}
				$.ajax({
					url:url,
					data:data,
					success:function(res){
						alert(res.msg,function(){
							if(res.success){
								easyui_closeTopWindow();
							}
						});
					}
				})	
		}
	}
var oldUserIconPath = "";
var newUserIconPath = "";
$(function() {
	personalModel.active('userSetting');
	queryUserInfo();

});
// 查询机构信息
function queryUserInfo() {
	var opt = {
		url : "/common/user/info.html",
		data : {},
		type : "post",
		dataType : "json",
		success : function(result) {
			if (result.success) {
				var data = result.data;
				// 10：教师；20：学生；30：管理人员；40：家长；90：系统管理员
				if (data.userRole == 10) {
					data.role = "教师";
				} else if (data.userRole == 20) {
					data.role = "学生";
				} else if (data.userRole == 30) {
					data.role = "管理人员";
				} else if (data.userRole == 40) {
					data.role = "家长";
				} else if (data.userRole == 90) {
					data.role = "系统管理员";
				}
				userSetModel.userInfo(result.data);
				userSetModel.userIconFile(result.data.userIcon);
				userSetModel.userIconFilePath(imgHost + "/" + result.data.avatarsImg);

			} else if (result.errorCode == 9000) {
				layer.open({
					type : 2,
					content : '/login/sub.html',
					title : '用户登录',
					area : [ '320px', '375px' ],
					closeBtn : false
				});
			} else {
				layer.alert(result.msg);
			}
		}

	};
	$.ajax(opt);

}

function UserSetModel() {
	var self = this;
	KO.observableAll(self, {
		userInfo : '',// 用户信息
		userIconFile : "",// 用户图片
		userIconFilePath : "",
	});

}
var userSetModel = new UserSetModel();
ko.applyBindings(userSetModel, $("#right_content")[0]);
		
	$(function() {

		$(document).bind("click",function(){
			$('.infoMenu').hide();
		})
		
		//左侧菜单
		$(".easyui-tree").tree({
			onClick : function(node) {
				toogle(node.target);
				
				//窗口已存在，则选中该窗口，否则新增一个窗口
				if ($("#tab").tabs("exists", node.text)) {
					var $tab=$("#tab").tabs('getTab',node.text);
					refreshPanel($tab);
					$("#tab").tabs("select", node.text);
				} else {
					if (node.attributes.url != undefined&&node.attributes.url !="") {
						$("#tab").tabs('add', {
							title : node.text,
							closable : true,
							content : '<iframe id="myframe"scrolling="no" frameborder="0" src="' +node.attributes.url + '" style="width:100%;height:100%;"></iframe>'
						}).tabs('resize');
					}
				}
			}
		});
		//右侧窗口
		$("#tab").tabs({
			onContextMenu : function(e, title) {
				e.preventDefault();
				$("#tabMenu").menu('show', {
					left : e.pageX,
					top : e.pageY
				}).data('tabTitle', title);
			},
			onSelect:function(title,index){
				/*var $tab=$('#tab').tabs('getTab',index);
				refreshPanel($tab);*/
			}
			/*tools : [ {
				iconCls : 'icon-reload',
				handler : function() {
					var href = $("#tab").tabs('getSelected').panel('options').href;
					//说明tab是以href方式引入的目标页面
					if (href) {
						var index = $("#tab").tabs('getTabIndex', $("#tab").tabs('getSelected'));
						$("#tab").tabs('getTab', index).panel('refresh');
					}
					//说明tab是以content方式引入的目标页面
					else {
						var panel = $("#tab").tabs('getSelected').panel('panel');
						var frame = panel.find('iframe');
						try {
							if (frame.length > 0) {
								for (var i = 0; i < frame.length; i++) {
									frame[i].contentWindow.document.write('');
									frame[i].contentWindow.close();
									frame[i].src = frame[i].src;
								}
								//IE特有回收内存方法
								if (navigator.userAgent.indexOf("MSIE") > 0) {
									try {
										CollectGarbage();
									} catch (e) {
									}
								}
							}
						} catch (e) {
						}
					}
				}
			}, {
				iconCls : 'icon-remove',
				handler : function() {
					var index = $("#tab").tabs('getTabIndex', $("#tab").tabs('getSelected'));
					var tb = $("#tab").tabs('getTab', index);
					if (tb.panel('options').closable) {
						$("#tab").tabs('close', index);
					} else {
						$.messager.alert('提示', '[' + tb.panel('options').title + ']不可以被关闭！', 'info');
					}
				}
			} ]*/
		});
		
		//刷新页面
		function refreshPanel($tab){
			var panel = $tab.panel('panel');
			var href = panel.href;
			//说明tab是以href方式引入的目标页面
			if (href) {
				$tab.panel('refresh');
			}
			//说明tab是以content方式引入的目标页面
			else {
				var frame = panel.find('iframe');
				try {
					if (frame.length > 0) {
						for (var i = 0; i < frame.length; i++) {
							frame[i].contentWindow.document.write('');
							frame[i].contentWindow.close();
							frame[i].src = frame[i].src;
						}
						//IE特有回收内存方法
						if (navigator.userAgent.indexOf("MSIE") > 0) {
							try {
								CollectGarbage();
							} catch (e) {
							}
						}
					}
				} catch (e) {
				}
			}
		}
		//右键菜单
		$("#tabMenu").menu({
			onClick : function(item) {
				var curTabTitle = $(this).data('tabTitle');
				var type = $(item.target).attr('title');

				//关闭
				if (type === 'close') {
					var t = $("#tab").tabs('getTab', curTabTitle);
					if (t.panel('options').closable) {
						$("#tab").tabs('close', curTabTitle);
					}
					return;
				}
				//关闭其他或全部
				var allTabs = $("#tab").tabs('tabs');
				var closeTabsTitle = [];
				$.each(allTabs, function() {
					var opt = $(this).panel('options');
					//关闭其他
					if (opt.closable && opt.title != curTabTitle && type === 'closeOther') {
						closeTabsTitle.push(opt.title);
					}
					//关闭全部
					else if (opt.closable && type === 'closeAll') {
						closeTabsTitle.push(opt.title);
					}
				});

				//循环关闭
				for (var i = 0; i < closeTabsTitle.length; i++) {
					$("#tab").tabs('close', closeTabsTitle[i]);
					if (type === 'closeOther') {
						$("#tab").tabs("select", curTabTitle);
					}
				}
			}
		});
		
		//安全退出
		$("#btnExit").click(function() {
			window.top.$.messager.confirm("提示", "您确定要退出吗?", function(r) {
				if (r) {
					$.ajax({
						url : "/manage/exit.html",
						dataType : "json",
						success : function(result) {
							$('#formLogot').attr("src",result.msg)
							var url=result.data
							if(url.indexOf("cas")>=0){
								url=url+location.origin+"/manage/adminGlobalLogin.html";	
							}
							window.location.href = url;
						}
					});
				}
			});
		});
	})

		//展开菜单
		function toogle(node){
			var hit=$(">span.tree-hit",node);
			if(hit.hasClass("tree-collapsed")){
				var otherNode = $(node).parent().siblings().children();
				hit.removeClass('tree-collapsed tree-collapsed-hover').addClass('tree-expanded');
				hit.next().addClass('tree-folder-open');
				otherNode.children(".tree-hit").removeClass('tree-expanded tree-expanded-hover').addClass('tree-collapsed');
				var ul = $(node).next();
				if (ul.length){
					if (true){
						otherNode.next().slideUp();
						ul.slideDown();
					} else {
						ul.css('display','block');
					}
				}
			}else{
				hit.removeClass('tree-expanded tree-expanded-hover').addClass('tree-collapsed');
				hit.next().removeClass('tree-folder-open');
				if (true){
					$(node).next().slideUp();
				} else {
					$(node).next().css('display','none');
				}
			}
		}
		
		//显示用户信息
		function showMenu(event){
			var e = window.event || event;
			if (e.stopPropagation) {
				e.stopPropagation();
			} else {
				e.cancelBubble = true;
			}
			$('.infoMenu').toggle();
		}
	
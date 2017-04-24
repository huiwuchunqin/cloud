(function($){
	var _FilePath = "";
	var __filename = "jquery.bzt.doc.player.js";
	var es = document.getElementsByTagName("script");
	for (var i = 0; i < es.length; i++){
		var tsrc = es[i].src;
		if ((typeof tsrc)=="string" && tsrc.substr(tsrc.length-__filename.length, tsrc.length).toLowerCase() ==__filename)	{
			_FilePath = tsrc.substr(0, tsrc.lastIndexOf("/") + 1);
			break;
		}
	}
	var _ImagePath = _FilePath + "images/";
/***
 *
 * @param options
 */
$.fn.bztdocplayer = function(options){
	var PAGE_MODE = "page", //整页模式
		SINGLE_MODE = "single", //单页模式
		MAX_SCALE = 3,
		MIN_SCALE = 0.6,
		SCALE_STEP = 0.1;
	var defaults={
		mode: SINGLE_MODE,
		width: 500,     //宽度
		height: 300,    //高度
		dataSource: [], //数据源
		url: "",
		initPageNo: 1,
		toolbarAutoHide: true,
		fitWithHeight: true,
		onInitComplete: null,
		onSkipPage: null,
		onScale: null,
		onFullScreen: null,
		onNormalScreen: null,
		onError: null,
	};
	var opts = $.extend(defaults,options);
	var self = this;
	//定义参数
	var isTouchEnable = "ontouchstart" in document,
		lastTouchDistance = 0, isScaleByTouch = false, touchStartPoint = null,
		scale = 1, currPageIndex=-1, totalPageNo=0,
		keyupTimeoutId = null, lastInputValue = "",
		isImgPanelDragging = false, lastMovePointer = null,
		isSliderDragging = false,
		fadeToolbarTimeoutId = null, isInToolbar=false,
		isFullscreenManual = false, fullscreenElement;
	//翻页手势相关参数
	var g_srcX, g_srcY,
		g_m_IGNORE_X = 5,	//在该范围内忽略
		g_m_IGNORE_Y = 60,	//在该范围内记录
		g_m_DISTANCE_RATIO = 4.5,
		//m_ZOOM_RATIO = 1,
		g_canSwipeLeft = false, g_canSwipeRight = false,
		g_doSwipeLeft = false, g_doSwipeRight = false,
		g_movePoints = [],
		g_swipeable = true,	//判断是否忽略本次移动
		g_lastDx = 0,	//用于判断方向是否一致
		g_canScroll = true;
	//控件对象
	var playerRoot, divWaiting,
		btnZoomIn, btnZoomOut, btnZoomReset, btnPrev, btnNext, inputCurrPage, txtTotalPage, btnFullscreen, divToolbar,
		divSingleImgPanel, imgSingle, hScrollbar, hPlayProgress, hTotalProgress, hSlider, hLabel, hLabelText,
		divPageImgPanel;
	//vScrollbar, vPlayProgress, vTotalProgress, vSlider

	self.html("");
	self.width(opts.width);
	self.height(opts.height);
	//load ui
	//var htmlUrl = _FilePath + "player-ui.html";
	//self.load(htmlUrl, undefined, function(){
	//	initUi();
	//	loadData();
	//});
	var uihtml =
		'<div class="doc-player-root">\
			<div class="doc-player-main">\
				<div class="doc-player-waiting">\
					<table>\
						<tr><td>\
							<img class="icon" src="loading2.gif">\
						</td></tr>\
					</table>\
				</div>\
			<div class="doc-player-single-mode">\
				<img class="doc-player-single-img" >\
			</div>\
			<div class="doc-player-page-mode"></div>\
			<div class="doc-player-toolbar">\
				<div class="doc-player-zoom">\
					<img class="doc-player-btn btn-zoom-in icon" src="zoom_in.png">\
					<img class="doc-player-btn btn-zoom-reset icon" src="zoom_reset.png">\
					<img class="doc-player-btn btn-zoom-out icon" src="zoom_out.png">\
				</div>\
				<div class="doc-player-page-operate">\
					<div>\
						<img class="doc-player-btn btn-prev icon" src="left_disable.png" >\
						<div class="doc-player-page">\
							<input class="doc-player-current-page" value="0">\
							<span class="doc-player-page-split">/</span>\
							<span class="doc-player-total-page">0</span>\
						</div>\
						<img class="doc-player-btn btn-next icon" src="right_disable.png" >\
					</div>\
				</div>\
				<div class="doc-player-fullscreen">\
					<img class="doc-player-btn btn-fullscreen icon" src="fullscreen.png" >\
				</div>\
			</div>\
			<div class="doc-player-scrollbar-horizontal">\
				<div class="scrollbar-total-progress">\
					<div class="scrollbar-play-progress"></div>\
					<div class="scrollbar-slider"></div>\
				</div>\
				<div class="scrollbar-horizontal-label">\
					第<span class="hscrollbar-label-text">100</span>页\
				</div>\
			</div>\
			<div class="doc-player-scrollbar-vertical"></div></div>\
		</div>';
	self.append(uihtml);
	initUi();
	loadData();

	/**
	 * 初始化UI页面，设置事件回调
	 */
	function initUi(){
		//初始化控件对象
		playerRoot = self.find(".doc-player-root");
		divWaiting = self.find(".doc-player-root .doc-player-waiting");
		btnZoomIn = self.find(".doc-player-root .btn-zoom-in");
		btnZoomOut = self.find(".doc-player-root .btn-zoom-out");
		btnZoomReset = self.find(".doc-player-root .btn-zoom-reset");
		btnPrev = self.find(".doc-player-root .btn-prev");
		btnNext = self.find(".doc-player-root .btn-next");
		inputCurrPage = self.find(".doc-player-root .doc-player-current-page");
		txtTotalPage = self.find(".doc-player-root .doc-player-total-page");
		btnFullscreen = self.find(".doc-player-root .btn-fullscreen");
		divToolbar = self.find(".doc-player-root .doc-player-toolbar");
		divSingleImgPanel = self.find(".doc-player-root .doc-player-single-mode");
		imgSingle = self.find(".doc-player-root .doc-player-single-img");
		hScrollbar = self.find(".doc-player-root .doc-player-scrollbar-horizontal");
		hPlayProgress = self.find(".doc-player-root .scrollbar-play-progress");
		hTotalProgress = self.find(".doc-player-root .scrollbar-total-progress");
		hSlider = self.find(".doc-player-root .scrollbar-slider");
		hLabel = self.find(".doc-player-root .scrollbar-horizontal-label");
		hLabelText = self.find(".doc-player-root .hscrollbar-label-text");

		divPageImgPanel = self.find(".doc-player-root .doc-player-page-mode");
		//vScrollbar = $(".doc-player-root .doc-player-scrollbar-vertical");


		self.find("img.icon").each(function(i, img){
			img.src = _ImagePath + img.src.substr(img.src.lastIndexOf("/") + 1, img.src.length);
		});
		if(opts.fitWithHeight){
			imgSingle.height(divSingleImgPanel.height()*scale);
		}else{
			imgSingle.width(divSingleImgPanel.width()*scale*0.98);
		}
		//设置鼠标指针
		divSingleImgPanel.css("cursor", "url('"+_ImagePath+"grab.png'), pointer");
		divPageImgPanel.css("cursor", "url('"+_ImagePath+"grab.png'), pointer");

		//注册事件

		//放大按钮
		btnZoomIn.off("click").on("click", function () {
			if(opts.mode === SINGLE_MODE){
				_zoomIn(divSingleImgPanel);
			}else if(opts.mode === PAGE_MODE){
				_zoomIn(divPageImgPanel);
			}
		});

		//缩小按钮
		btnZoomOut.off("click").on("click", function(){
			if(opts.mode === SINGLE_MODE){
				_zoomOut(divSingleImgPanel);
			}else if(opts.mode === PAGE_MODE){
				_zoomOut(divPageImgPanel);
			}
		});

		//缩放回复
		btnZoomReset.off("click").on("click", function(){
			if(opts.mode === SINGLE_MODE){
				_zoomReset(divSingleImgPanel);
			}else if(opts.mode === PAGE_MODE){
				_zoomReset(divPageImgPanel);
			}
		});

		//上一页
		btnPrev.off("click").on("click", function(){
			gotoPrevPage();
		});

		//下一页
		btnNext.off("click").on("click", function(){
			gotoNextPage()
		});

		//当前页输入框keyup事件
		inputCurrPage.off("keyup").on("keyup", function(){
			onInputCurrPageKeyup($(this));
		});
		inputCurrPage.off("blur").on("blur", function(){
			var text = inputCurrPage.val();
			if(!text){
				inputCurrPage.val(lastInputValue);
			}
		});

		//全屏
		btnFullscreen.off("click").on("click", function(){
			if(isFullScreen()){
				exitFullscreen();
			}else{
				launchFullscreen(playerRoot[0]);
			}
		});
		playerRoot.bind('webkitfullscreenchange mozfullscreenchange fullscreenchange', _onFullscreenChanged);

		//隐藏工具栏
		initFadeToolbarTimer();
		//toolbar鼠标事件
		divToolbar.off("mouseover").on("mouseover", function(){
			isInToolbar = true;
		});
		divToolbar.off("mouseout").on("mouseout", function(){
			isInToolbar = false;
			initFadeToolbarTimer();
		});

		//全局的keydown事件
		$(document).on("keydown", function(e){
			//console.log("document keydown,  keycode=" + e.keyCode);
			//手动全屏模式下点击esc键退出全屏
			if(isFullscreenManual == true && fullscreenElement && e.keyCode==27){
				exitFullscreenManual(fullscreenElement);
			}
		});

		if(opts.mode === SINGLE_MODE){
			divPageImgPanel.hide();
			hScrollbar.show();
			divSingleImgPanel.show();
			btnPrev.show();
			btnNext.show();
			if(isTouchEnable){
				inputCurrPage.attr("disabled", "disabled");
			}else{
				inputCurrPage.removeAttr("disabled");
			}
			//$(".doc-player-page-operate .doc-player-page").removeClass("doc-player-page-margin");

			if(!isTouchEnable){   //不支持touch事件
				//横向滚动条MouseMove事件
				hTotalProgress.off("mousemove").on("mousemove", _onHorizontalProgressMouseMove);
				hTotalProgress.off("mouseout").on("mouseout", _onHorizontalProgressMouseOut);
				//横向滚动条点击事件
				hTotalProgress.off("click").on("click", _onHorizontalProgressClick);
				//横向滚动条滑块事件
				hSlider.off("mousedwon").on("mousedown", _onHorizontalSliderMouseDown);

				//单页模式拖拽事件
				divSingleImgPanel.off("mousedown").on("mousedown", _onMouseDown4SingleImgPanel);
				divSingleImgPanel.off("mousemove").on("mousemove", _onMouseMove4SingleImgPanel);
				divSingleImgPanel.off("mouseup").on("mouseup", _onMouseUp4SingleImgPanel);
				divSingleImgPanel.off("mouseout").on("mouseout", _onMouseOut4SingleImgPanel);
				//鼠标滚轮事件，放大缩小
				var mousewheelevt=(/Firefox/i.test(navigator.userAgent))? "DOMMouseScroll" : "mousewheel" //FF doesn't recognize mousewheel as of FF3.x
				if (document.attachEvent){  //if IE (and Opera depending on user setting)
					divSingleImgPanel[0].detachEvent("on"+mousewheelevt, _onMouseWheel4SingleImgPanel);
					divSingleImgPanel[0].attachEvent("on"+mousewheelevt, _onMouseWheel4SingleImgPanel);
				} else if (document.addEventListener) { //WC3 browsers
					divSingleImgPanel[0].removeEventListener(mousewheelevt, _onMouseWheel4SingleImgPanel);
					divSingleImgPanel[0].addEventListener(mousewheelevt, _onMouseWheel4SingleImgPanel, false);
				}
			}else{      //支持touch事件
				if (document.attachEvent){  //if IE (and Opera depending on user setting)
					divSingleImgPanel[0].detachEvent("touchstart", _onTouchStart4SingleImgPanel);
					divSingleImgPanel[0].detachEvent("touchmove", _onTouchMove4SingleImgPanel);
					divSingleImgPanel[0].detachEvent("touchend", _onTouchEnd4SingleImgPanel);

					divSingleImgPanel[0].attachEvent("touchstart", _onTouchStart4SingleImgPanel);
					divSingleImgPanel[0].attachEvent("touchmove", _onTouchMove4SingleImgPanel);
					divSingleImgPanel[0].attachEvent("touchend", _onTouchEnd4SingleImgPanel);
				} else if (document.addEventListener) { //WC3 browsers
					divSingleImgPanel[0].removeEventListener("touchstart", _onTouchStart4SingleImgPanel, false);
					divSingleImgPanel[0].removeEventListener("touchmove", _onTouchMove4SingleImgPanel, false);
					divSingleImgPanel[0].removeEventListener("touchend", _onTouchEnd4SingleImgPanel, false);

					divSingleImgPanel[0].addEventListener("touchstart", _onTouchStart4SingleImgPanel, false);
					divSingleImgPanel[0].addEventListener("touchmove", _onTouchMove4SingleImgPanel, false);
					divSingleImgPanel[0].addEventListener("touchend", _onTouchEnd4SingleImgPanel, false);
				}
			}

		}else if(opts.mode === PAGE_MODE){
			hScrollbar.hide();
			divSingleImgPanel.hide();
			divPageImgPanel.show();
			if(isTouchEnable){
				inputCurrPage.attr("disabled", "disabled");
			}else{
				inputCurrPage.removeAttr("disabled");
			}
			//$(".doc-player-page-operate .doc-player-page").addClass("doc-player-page-margin");
			//纵向滚动条滑块事件
			divPageImgPanel.on("scroll", _onScroll4PageImgPanel);

			//整页模式拖拽事件
			if(!isTouchEnable) {   //不支持touch事件
				divPageImgPanel.off("mousedown").on("mousedown", _onMouseDown4PageImgPanel);
				divPageImgPanel.off("mousemove").on("mousemove", _onMouseMove4PageImgPanel);
				divPageImgPanel.off("mouseup").on("mouseup", _onMouseUp4PageImgPanel);
				divPageImgPanel.off("mouseout").on("mouseout", _onMouseOut4PageImgPanel);
			}else{      //支持touch事件
				if (document.attachEvent){  //if IE (and Opera depending on user setting)
					divPageImgPanel[0].detachEvent("touchstart", _onTouchStart4PageImgPanel);
					divPageImgPanel[0].detachEvent("touchmove", _onTouchMove4PageImgPanel);
					divPageImgPanel[0].detachEvent("touchend", _onTouchEnd4PageImgPanel);

					divPageImgPanel[0].attachEvent("touchstart", _onTouchStart4PageImgPanel);
					divPageImgPanel[0].attachEvent("touchmove", _onTouchMove4PageImgPanel);
					divPageImgPanel[0].attachEvent("touchend", _onTouchEnd4PageImgPanel);
				} else if (document.addEventListener) { //WC3 browsers
					divPageImgPanel[0].removeEventListener("touchstart", _onTouchStart4PageImgPanel);
					divPageImgPanel[0].removeEventListener("touchmove", _onTouchMove4PageImgPanel);
					divPageImgPanel[0].removeEventListener("touchend", _onTouchEnd4PageImgPanel);

					divPageImgPanel[0].addEventListener("touchstart", _onTouchStart4PageImgPanel, false);
					divPageImgPanel[0].addEventListener("touchmove", _onTouchMove4PageImgPanel, false);
					divPageImgPanel[0].addEventListener("touchend", _onTouchEnd4PageImgPanel, false);
				}
			}
		}
	}

	/***
	 * 显示等待
	 * @private
	 */
	function _showWaiting(){
		if(opts.mode === SINGLE_MODE) {
			divSingleImgPanel.hide();
		}else if(opts.mode === PAGE_MODE){
			divPageImgPanel.hide();
		}
		divWaiting.show();
	}

	/***
	 * 隐藏等待
	 * @private
	 */
	function _hideWaiting(){
		divWaiting.hide();
		if(opts.mode === SINGLE_MODE) {
			divSingleImgPanel.show();
		}else if(opts.mode === PAGE_MODE){
			divPageImgPanel.show();
		}
	}

	/***
	 * 全屏状态改变时
	 * @private
	 */
	function _onFullscreenChanged(){
		var isfull = isFullScreen();
		if (isfull) {
			console.log('进入全屏');
			btnFullscreen.attr("src", _ImagePath+"quit_fullscreen.png");
		} else {
			console.log('退出全屏');
			btnFullscreen.attr("src", _ImagePath+"fullscreen.png");
		}
		if(opts.mode === SINGLE_MODE){
			_refreshHorizontalScrollbar();
			_onFullscreenChanged4SingleMode(isfull);
		}else if(opts.mode === PAGE_MODE){
			_onFullscreenChanged4PageMode(isfull);
		}
		if (isfull) {
			opts.onFullScreen && opts.onFullScreen(self);
		} else{
			opts.onNormalScreen && opts.onNormalScreen(self);
		}
	}

	/***
	 * 设置工具栏淡入淡出计时器
	 */
	function initFadeToolbarTimer(){
		if(!opts.toolbarAutoHide){
			return;
		}

		if(divToolbar.is(":visible") == false){
			divToolbar.fadeIn();
		}
		if(opts.mode === SINGLE_MODE && hScrollbar.is(":visible") == false){
			hScrollbar.fadeIn();
		}

		if(fadeToolbarTimeoutId != null){
			clearTimeout(fadeToolbarTimeoutId);
		}
		fadeToolbarTimeoutId = setTimeout(function(){
			if(isInToolbar || isSliderDragging){
				return;
			}
			divToolbar.fadeOut();
			if(opts.mode === SINGLE_MODE){
				hScrollbar.fadeOut();
			}
			fadeToolbarTimeoutId = null;
		}, 3000);
	}

	/***
	 * 加载数据
	 */
	function loadData(){
		if(opts.url){
			loadDataFromUrl(opts.url);
		}else if(opts.dataSource && opts.dataSource.length > 0){
			_showWaiting();
			loadDataFromSource(opts.dataSource);
			_hideWaiting();
		}
	}

	/**
	 * 通过url加载数据
	 */
	function loadDataFromUrl(url){
		_showWaiting();
		$.ajax({
			type: "POST",
			dataType: "json",
			url: url,
			data: {},
			success: function(result){
				opts.dataSource = [];
				var data = result.datas;
				for (var i = 0; i < data.length; i++) {
					var item = data[i];
					if(item.getPageUrl.indexOf("swf")<0){
						item.getPageUrl=item.getPageUrl.replace("jpg","png");
					}
					console.log("doc img url = "+item.getPageUrl);
					opts.dataSource.push(item.getPageUrl);
				}
				loadDataFromSource(opts.dataSource);
				_hideWaiting();
			},
			error: function (error, text) {
				console.error("文档阅读器：获取文档数据失败 -- " + text);
				opts.onError && opts.onError(self, error, text);
			},
		});
	}

	/***
	 * 通过数据源dataSource加载数据
	 * @param dataSource
	 */
	function loadDataFromSource(dataSource){
		totalPageNo = dataSource.length;
		if(opts.initPageNo && opts.initPageNo > 0){
			currPageIndex = opts.initPageNo-1;
		}else{
			currPageIndex = 0;
		}
		inputCurrPage.val(currPageIndex+1);
		txtTotalPage.text(totalPageNo);
		lastInputValue = (currPageIndex+1)+"";
		//加载图片数据
		if(opts.mode === SINGLE_MODE){
			imgSingle.attr("src", dataSource[currPageIndex]);
			_refreshPageButton();
			_refreshHorizontalScrollbar();
		}else if(opts.mode === PAGE_MODE){
			var imgWidth = divPageImgPanel.width()*scale*0.98;
			for(var i=0; i<dataSource.length; i++){
				var imghtml = $('<img class="doc-player-page-img doc-player-lazy-img" style="width: '+imgWidth+'px;">');
				if(i==0){
					imghtml.attr("src", dataSource[i]);
					imghtml.on("load", _firstImageLoaded4PageMode);
				}else{
					imghtml.attr("data-original", dataSource[i]);
				}
				divPageImgPanel.append(imghtml);
			}
			_refreshPageButton();
			self.find("img.doc-player-lazy-img").lazyload({
				container: divPageImgPanel,
			});
			setTimeout(function(){
				if(currPageIndex != 0){
					gotoPage4PageMode(currPageIndex);
					divPageImgPanel.scrollTop(divPageImgPanel.scrollTop()-1);
				}
			}, 400);
		}
		opts.onInitComplete && opts.onInitComplete(self);
	}

	/***
	 * 整页模式下，第一张图片加载完成后
	 * @param e
	 * @private
	 */
	function _firstImageLoaded4PageMode(e){
		//console.log("firset image loaded");
	}

	/***
	 * 缩放panel
	 * @param panel
	 * @param scale
	 * @private
	 */
	function __scalePanel(panel, s){
		panel.css("zoom", s);
		opts.onScale && opts.onScale(self, s);
	}

	/**
	 * 放大
	 * @param panel
	 * @private
	 */
	function _zoomIn(panel){
		if(_canZoomIn()){
			scale += SCALE_STEP;
			if(scale > MAX_SCALE){
				scale = MAX_SCALE;
			}
			__scalePanel(panel, scale);
		}else{
			console.log("不能放大", "scale="+scale);
		}
	}
	/**
	 * 能否放大
	 * @private
	 */
	function _canZoomIn(){
		return scale < MAX_SCALE;
	}

	/***
	 * 缩小
	 * @param panel
	 * @private
	 */
	function _zoomOut(panel){
		if(_canZoomOut()){
			scale -= SCALE_STEP;
			if(scale <= MIN_SCALE){
				scale = MIN_SCALE;
			}
			__scalePanel(panel, scale);
		}else{
			console.log("不能缩小", "scale="+scale);
		}
	}

	/***
	 * 缩放回复
	 * @param panel
	 * @private
	 */
	function _zoomReset(panel){
		scale = 1;
		__scalePanel(panel, scale);
	}

	/***
	 * 能否缩小
	 * @private
	 */
	function _canZoomOut(){
		return scale > MIN_SCALE;
	}

	/***
	 * 上一页
	 */
	function gotoPrevPage(){
		if(currPageIndex == 0){
			return;
		}
		currPageIndex--;
		if(currPageIndex < 0){
			currPageIndex = 0;
		}
		if(opts.mode === SINGLE_MODE){
			gotoPage4SingleMode(currPageIndex);
		}else if(opts.mode === PAGE_MODE){
			gotoPage4PageMode(currPageIndex);
		}
	}

	/**
	 * 下一页
	 */
	function gotoNextPage(){
		if(currPageIndex == totalPageNo-1){
			return;
		}
		currPageIndex++;
		if(currPageIndex > totalPageNo-1){
			currPageIndex = totalPageNo-1;
		}
		if(opts.mode === SINGLE_MODE){
			gotoPage4SingleMode(currPageIndex);
		}else if(opts.mode === PAGE_MODE){
			gotoPage4PageMode(currPageIndex);
		}
	}

	/**
	 * 单页模式跳转页面
	 * @param pageNo
	 */
	function gotoPage4SingleMode(pageIndex){
		currPageIndex = pageIndex;
		imgSingle.attr("src", opts.dataSource[pageIndex]);
		_refreshPageButton();
		_refreshHorizontalScrollbar();
		divSingleImgPanel.scrollTop(0);
		inputCurrPage.val(pageIndex+1);
		opts.onSkipPage && opts.onSkipPage(self, pageIndex+1);
	}

	/***
	 * TODO 整页模式跳转页面
	 * @param pageNo
	 */
	function gotoPage4PageMode(pageIndex){
		currPageIndex = pageIndex;
		var imgs = divPageImgPanel.find("img");
		imgs[pageIndex].scrollIntoView();
		_refreshPageButton();
		inputCurrPage.val(pageIndex+1);
		opts.onSkipPage && opts.onSkipPage(self, pageIndex+1);
	}

	/***
	 * 当前页面输入框按键抬起时
	 * @param inputPage
	 */
	function onInputCurrPageKeyup(inputPage){
		var inputNo = inputPage.val();
		console.log("keyup: " + inputNo);
		if(inputNo == ""){
			if(keyupTimeoutId != null){
				clearTimeout(keyupTimeoutId);
			}
			return;
		}
		var num;
		if($.isNumeric(inputNo)){
			num = parseInt(inputNo);
			if(num < 1 || num > totalPageNo){
				inputPage.val(lastInputValue);
				return;
			}
		}else{
			inputPage.val(lastInputValue);
			return;
		}

		if(keyupTimeoutId != null){
			clearTimeout(keyupTimeoutId);
		}
		keyupTimeoutId = setTimeout(function(){
			if(currPageIndex != num-1){
				currPageIndex = num-1;
				if(opts.mode === SINGLE_MODE){
					gotoPage4SingleMode(currPageIndex);
				}else if(opts.mode === PAGE_MODE){
					gotoPage4PageMode(currPageIndex);
				}
			}
			keyupTimeoutId = null;
			lastInputValue = inputNo;
		}, 600);
	}

	/***
	 * 刷新上一页，下一页按钮状态
	 * @private
	 */
	function _refreshPageButton(){
		var enablePrev = currPageIndex+1 > 1;
		var enableNext = currPageIndex+1 < totalPageNo;
		if(enablePrev){
			btnPrev.attr("src", _ImagePath + "left.png");
		}else{
			btnPrev.attr("src", _ImagePath + "left_disable.png");
		}
		if(enableNext){
			btnNext.attr("src", _ImagePath + "right.png");
		}else{
			btnNext.attr("src", _ImagePath + "right_disable.png");
		}
	}

	/***
	 * 刷新横向滚动条
	 * @private
	 */
	function _refreshHorizontalScrollbar(){
		var currNo = currPageIndex,
			totalIndex = totalPageNo - 1,
			totalWidth = hTotalProgress.width(),
			sliderWidth = hSlider.width(),
			totalPlayWidth = totalWidth - sliderWidth;
		var playWidth;
		if(currNo == totalIndex || totalIndex == 0){
			playWidth = totalPlayWidth;
		}else{
			playWidth = totalPlayWidth * (currNo / (totalPageNo-1));
		}
		hPlayProgress.width(playWidth);
		hSlider.css("left", playWidth+"px");
	}


	/***
	 * 刷新纵向滚动条
	 * @private
	 */
	function _refreshVerticalScrollbar(){

	}

	/***
	 * 单页模式下，鼠标按下
	 * @param e
	 * @private
	 */
	function _onMouseDown4SingleImgPanel(e){
		__onMouseDown2Panel(e, divSingleImgPanel);
	}

	/***
	 *  单页模式下，触摸开始
	 * @param e
	 * @private
	 */
	function _onTouchStart4SingleImgPanel(e){
		__onTouchStart2Panel(e, divSingleImgPanel);
		//翻页手势准备
		g_srcX = touchStartPoint.x;
		g_srcY = touchStartPoint.y;
		g_movePoints = [];
		g_swipeable = true;
		g_doSwipeLeft = false;
		g_doSwipeRight = false;
		g_canSwipeLeft = false;
		g_canSwipeRight = false;
		g_canScroll = true;
		g_lastDx = 0;
		var panelWidth = divSingleImgPanel.width(),
			imgWidth = imgSingle.width(),
			sLeft = divSingleImgPanel.scrollLeft();
		if(panelWidth >= imgWidth){
			g_canSwipeLeft = true;
			g_canSwipeRight = true;
		}else{
			if(sLeft == 0){
				g_canSwipeRight = true;
			}else
				if(scale == 1){
					if(sLeft == (imgWidth - panelWidth)){
						g_canSwipeLeft = true;
					}
				}else{
					//console.log("onTouchStart checkLeft:" + Math.abs(panelWidth+sLeft-imgWidth));
					if(Math.abs(panelWidth+sLeft-imgWidth) <= 2.5){
						g_canSwipeLeft = true;
					}
				}
		}
	}

	/***
	 * 图片panel鼠标按下
	 * @param e
	 * @param panel
	 * @private
	 */
	function __onMouseDown2Panel(e, panel){
		initFadeToolbarTimer();
		isImgPanelDragging = true;
		lastMovePointer = __getEventPointer(e);
		panel.css("cursor", "url('"+_ImagePath+"grabing.png'), pointer");
	}

	function __getEventPointer(e){
		var typeName = e.type.toLowerCase();
		if(typeName == "touchstart" || typeName == "touchmove" || typeName == "touchend"){
			if(e.touches && e.touches.length >=1){
				return {x: e.touches[0].clientX, y: e.touches[0].clientY};
			}else{
				return null;
			}
		}else{
			return {x: e.clientX, y: e.clientY};
		}
	}

	/***
	 * 单页模式下，鼠标移动
	 * @param e
	 * @private
	 */
	function _onMouseMove4SingleImgPanel(e){
		if(isSliderDragging){
			return;
		}
		//console.log("_onMouseMove SingleImgPanel ");
		__onMouseMove2Panel(e, divSingleImgPanel);
		isInToolbar = false;
		initFadeToolbarTimer();
		return false;
	}

	/***
	 * 单页模式下，触摸移动
	 * @param e
	 * @private
	 */
	function _onTouchMove4SingleImgPanel(e){
		//判断是否为翻页
		__onTouchMove2Panel(e, divSingleImgPanel);
		if(touchStartPoint && e.touches.length == 1){
			var currPoint = __getEventPointer(e);
			var dx = g_srcX - currPoint.x;
			var dy = g_srcY - currPoint.y;
			var ddy = touchStartPoint.y - currPoint.y;
			//判断左右移动手势
			//console.log("_onTouchMove4SingleImgPanel: dx="+ dx + ", dy="+ dy + ", ddy="+ ddy);
			if(g_swipeable && (g_canSwipeLeft || g_canSwipeRight)){
				if(Math.abs(dy) <= g_m_IGNORE_Y && Math.abs(ddy) <= g_m_IGNORE_Y){		//如果Y轴偏移过大，则忽略此次手势
					if(g_movePoints.length > 120){	//记录120个点则忽略此次手势
						g_swipeable = false;
						console.log("此次移动忽略：记录过多的点");
						return;
					}
					if(Math.abs(dx) > g_m_IGNORE_X){	//如果X轴移动距离超过阀值，则记录
						//console.log("g_lastDx=" + g_lastDx + ", dx=" + dx);
						//x轴移动方向发生了改变
						if((g_lastDx > 0 && dx < 0) || (g_lastDx < 0 && dx > 0)){
							g_swipeable = false;
							console.log("此次移动忽略：x轴方向改变");
							return;
						}else{
							g_movePoints.push(currPoint);
							//console.log("add Point success");
						}
					}
				}else{
					g_swipeable = false;
					//console.log("此次移动忽略：g_swipeable=" + g_swipeable + ",g_canSwipeLeft=" + g_canSwipeLeft + ", g_canSwipeRight=" + g_canSwipeRight);
					return;
				}
			}
			//记录本次结果，供下次调用
			g_srcX= currPoint.x;
			g_srcY= currPoint.y;
			g_lastDx = dx;
		}
	}

	/***
	 * 图片panel鼠标移动
	 * @param e
	 * @param panel
	 * @returns {boolean}
	 * @private
	 */
	function __onMouseMove2Panel(e, panel){
		if(isImgPanelDragging){
			var p = __getEventPointer(e);
			//ife.cancelBubble = true;
			if(e.preventBubble){
				e.preventBubble();
			}
			e.preventDefault();  // 阻止默认行为
			if (e.stopPropagation) {
				e.stopPropagation();    // 阻止事件冒泡
			}
			if(lastMovePointer){
				var moveX = lastMovePointer.x - p.x,
					moveY = lastMovePointer.y - p.y;
				//console.log("_onMouseMove SingleImgPanel", "x="+x, "y="+y, "moveX="+moveX, "moveY="+moveY);
				panel.scrollLeft(panel.scrollLeft() + moveX);
				panel.scrollTop(panel.scrollTop() + moveY);
			}
			lastMovePointer = p;
			return false;
		}
	}

	/***
	 * 单页模式下，鼠标抬起
	 * @param e
	 * @private
	 */
	function _onMouseUp4SingleImgPanel(e){
		__onMouseUp2Panel(e, divSingleImgPanel);
	}

	/***
	 * 单页模式下，触摸结束
	 * @param e
	 * @private
	 */
	function _onTouchEnd4SingleImgPanel(e){
		__onTouchEnd2Panel(e, divSingleImgPanel);
		if(e.touches.length == 0){		//单点移动结束后
			//console.log("_onTouchEnd4SingleImgPanel: " + event.touches.length + ", points=" + g_movePoints.length + ", g_swipeable=" + g_swipeable);
			if(g_swipeable && (g_canSwipeLeft || g_canSwipeRight)){
				//检查记录的点，判断手势
				__checkGesture(divSingleImgPanel);
				//console.log("检查手势结束：g_doSwipeLeft="+g_doSwipeLeft + ", g_doSwipeRight="+g_doSwipeRight);
				//判断是否翻页
				if(g_doSwipeLeft){	//向左划，下一页
					//console.log("onTouchEnd: docNext");
					gotoNextPage();
				}else if(g_doSwipeRight){	//向右划，上一页
					//console.log("onTouchEnd: docPrev");
					gotoPrevPage();
				}
			}
		}
	}

	//遍历记录的点，判断手势
	//移动divContent宽度一半才有效
	function __checkGesture(panel){
		if(g_movePoints.length > 2){
			var firstPoint = g_movePoints[0];
			var lastPoint = g_movePoints[g_movePoints.length-1];
			var divWidth = panel.width();
			//console.log("checkGesture: total dx = " + (firstPoint.clientX - lastPoint.clientX) + ", 有效距离：" + (divWidth/m_DISTANCE_RATIO))
			if(g_canSwipeLeft && firstPoint.x > lastPoint.x
				&& (firstPoint.x - lastPoint.x) > divWidth/g_m_DISTANCE_RATIO){
				g_doSwipeLeft = true;
				return;
			}else if(g_canSwipeRight && lastPoint.x > firstPoint.x
				&& (lastPoint.x - firstPoint.x) > divWidth/g_m_DISTANCE_RATIO){
				g_doSwipeRight = true;
			}
		}else{
			g_doSwipeLeft = false;
			g_doSwipeRight = false;
		}
	}

	/***
	 * 图片panel鼠标提起
	 * @param e
	 * @param panel
	 * @returns {boolean}
	 * @private
	 */
	function __onMouseUp2Panel(e, panel){
		isImgPanelDragging = false;
		lastMovePointer = null;
		panel.css("cursor", "url('"+_ImagePath+"grab.png'), pointer");
	}

	/***
	 * 单页模式下，鼠标移除
	 * @param e
	 * @private
	 */
	function _onMouseOut4SingleImgPanel(e){
		__onMouseOut2Panel(e, divSingleImgPanel);
	}

	/***
	 * 图片panel鼠标移出
	 * @param e
	 * @param panel
	 * @returns {boolean}
	 * @private
	 */
	function __onMouseOut2Panel(e, panel){
		if(isImgPanelDragging){
			isImgPanelDragging = false;
			lastMovePointer = null;
			panel.css("cursor", "url('"+_ImagePath+"grab.png'), pointer");
		}
	}

	/***
	 * 单页模式下，鼠标滚轮
	 * @param event
	 * @param delta
	 * @param deltaX
	 * @param deltaY
	 * @private
	 */
	function _onMouseWheel4SingleImgPanel(e){
		var delta = e.wheelDelta || e.detail;
		//console.log("_onMouseWheel SingleImgPanel", delta);
		//先判断能否滚动翻页
		var panelHeight = divSingleImgPanel.height(),
			imgHeight = imgSingle.height(),
			sTop = divSingleImgPanel.scrollTop(),
			canGoNext = false, canGoPrev = false;
		if(panelHeight >= imgHeight){
			canGoNext = true;
			canGoPrev = true;
		}else{
			if(sTop == 0){
				canGoPrev = true;
			}else{
				if(scale == 1){
					if(sTop == (imgHeight - panelHeight)){
						canGoNext = true;
					}
				}else{
					if(Math.abs(panelHeight+sTop-imgHeight) <= 2.5){
						canGoNext = true;
					}
				}
			}
		}
		//console.log("_onMouseWheel SingleImgPanel: canGoNext=" + canGoNext + ", canGoPrev="+canGoPrev);
		var isNext = delta < 0;
		if(isNext){
			if(canGoNext){
				gotoNextPage();
			}else{
				divSingleImgPanel.scrollTop(divSingleImgPanel.scrollTop()+(100/scale));
			}
		}else{
			if(canGoPrev){
				gotoPrevPage();
			}else{
				divSingleImgPanel.scrollTop(divSingleImgPanel.scrollTop()-(100/scale));
			}
		}
		if(e.preventDefault){
			e.preventDefault();
		}
		if(e.stopPropagation){
			e.stopPropagation();
		}
		return false;
	}

	/***
	 * 单页模式下，横向滚动条鼠标移动事件
	 * @param e
	 * @private
	 */
	function _onHorizontalProgressMouseMove(e){
		var hpOffset = hTotalProgress.offset(),
			offsetX = e.clientX - hpOffset.left;
		var toPageNo = __calcHorizontalPageNoFromOffsetX(offsetX);
		//console.log("_onHorizontal ProgressMouseMove", "calcOffsetX="+(e.clientX - hpOffset.left), "offsetX="+ e.offsetX, "toPageNo="+toPageNo);
		__showHorizontalPageLabel(toPageNo, offsetX);
		isInToolbar = true;
	}

	/***
	 * 显示横向滚动条的Label
	 * @param toPageNo
	 * @param offsetX
	 * @private
	 */
	function __showHorizontalPageLabel(toPageNo, offsetX){
		var totalWidth = hTotalProgress.width();
		hLabelText.text(toPageNo);
		var labelOuterWidth = hLabel.outerWidth();
		if(offsetX <= labelOuterWidth/2){
			hLabel.css("left", "1px");
		}else if(offsetX >= (totalWidth - labelOuterWidth/2 - 2)){
			hLabel.css("left", (totalWidth - labelOuterWidth - 2)+"px");
		}else{
			hLabel.css("left", (offsetX - (labelOuterWidth/2))+"px");
		}
		hLabel.show();
	}

	/***
	 * 单页模式下，横向滚动条鼠标移出事件
	 * @param e
	 * @private
	 */
	function _onHorizontalProgressMouseOut(e){
		hLabel.hide();
		isInToolbar = false;
	}

	/***
	 * 单页模式下，横向滚动条点击事件
	 * @param e
	 * @private
	 */
	function _onHorizontalProgressClick(e){
		var hpOffset = hTotalProgress.offset();
		var toPageNo = __calcHorizontalPageNoFromOffsetX(e.clientX - hpOffset.left);
		//console.log("_onHorizontal ProgressMouseMove", "calcOffsetX="+(e.clientX - hpOffset.left), "offsetX="+ e.offsetX, "toPageNo="+toPageNo);
		if((currPageIndex+1) != toPageNo){
			currPageIndex = toPageNo - 1;
			gotoPage4SingleMode(currPageIndex);
		}
	}

	/***
	 * 根据横向滚动条上的点，计算页数
	 * @param point
	 * @private
	 */
	function __calcHorizontalPageNoFromOffsetX(x){
		var	currNo = currPageIndex + 1,
			totalNo = totalPageNo,
			totalWidth = hTotalProgress.width();
		var perWidth = totalWidth/(totalNo-1),
			halfPerWidth = perWidth / 2,
			totalHalfCount = (totalNo-1)*2;
		var toPageNo = 0, xCount = x / halfPerWidth;
		if(xCount < 1){
			toPageNo = 1;
		}else if(xCount > totalHalfCount-1){
			toPageNo = totalNo;
		}else{
			toPageNo = totalNo;
			for(var i=1; i<(totalHalfCount-1); i++){
				if(xCount <= (1+i*2)){
					toPageNo = 1+i;
					break;
				}
			}
		}
		return toPageNo;
	}

	/***
	 *  单页模式下，横向滑块鼠标按下事件
	 * @param e
	 * @private
	 */
	function _onHorizontalSliderMouseDown(e){
		var p = {x: e.offsetX, y: e.offsetY};
		console.log("_onHorizontalSlider MouseDown", p.x, p.y);
		$(document).off("mousemove", _onDocumentMouseMove4HorizontalSlider).on("mousemove", _onDocumentMouseMove4HorizontalSlider);
		$(document).off("mouseup", _onDocumentMouseUp4HorizontalSlider).on("mouseup", _onDocumentMouseUp4HorizontalSlider);
		isSliderDragging = true;
		isInToolbar = true;
		return false;
	}

	/***
	 *  单页模式下，横向滑块鼠标移出事件
	 * @param e
	 * @private
	 */
	function _onDocumentMouseMove4HorizontalSlider(e){
		if(isSliderDragging){
			var p = {x: e.clientX, y: e.clientY};
			var hpOffset = hTotalProgress.offset(),
				hpWidth = hTotalProgress.width(),
				sliderWidth = hSlider.outerWidth(),
				offsetX = p.x - hpOffset.left;
			if(offsetX < 0){
				offsetX = 0;
			}else if(offsetX > (hpWidth - sliderWidth)){
				offsetX = hpWidth - sliderWidth;
			}
			var toPageNo = __calcHorizontalPageNoFromOffsetX(offsetX);
			//console.log("_onDocument MouseMove HorizontalSlider", "offsetX="+offsetX, "pageNo="+toPageNo);
			var labelOffsetX = offsetX + sliderWidth/2;
			__showHorizontalPageLabel(toPageNo, labelOffsetX);
			hPlayProgress.width(offsetX);
			hSlider.css("left", offsetX+"px");
			return false;
		}
	}

	/***
	 *  单页模式下，横向滑块鼠标移动事件
	 * @param e
	 * @private
	 */
	function _onDocumentMouseUp4HorizontalSlider(e){
		if(isSliderDragging){
			var p = {x: e.clientX, y: e.clientY};
			//console.log("_onDocument MouseUp HorizontalSlider", p.x, p.y);
			$(document).off("mousemove", _onDocumentMouseMove4HorizontalSlider);
			$(document).off("mouseup", _onDocumentMouseUp4HorizontalSlider);
			isSliderDragging = false;
			hLabel.hide();
			//跳转页面
			var p = {x: e.clientX, y: e.clientY};
			var hpOffset = hTotalProgress.offset(),
				hpWidth = hTotalProgress.width(),
				offsetX = p.x - hpOffset.left;
			if(offsetX < 0){
				offsetX = 0;
			}else if(offsetX > hpWidth){
				offsetX = hpWidth;
			}
			var toPageNo = __calcHorizontalPageNoFromOffsetX(offsetX);
			currPageIndex = toPageNo-1;
			gotoPage4SingleMode(toPageNo-1);

			if(e.preventBubble){
				e.preventBubble();
			}
			e.preventDefault();  // 阻止默认行为
			if (e.stopPropagation) {
				e.stopPropagation();    // 阻止事件冒泡
			}
			return false;

		}
	}

	/***
	 * 单页模式下，全屏改变事件
	 * @param isFull
	 * @private
	 */
	function _onFullscreenChanged4SingleMode(isFull){
		if(opts.fitWithHeight){
			imgSingle.height(divSingleImgPanel.height()*scale);
		}else{
			imgSingle.width(divSingleImgPanel.width()*scale*0.98);
		}
	}

	/***
	 * 整页模式下，全屏改变事件
	 * @param isFull
	 * @private
	 */
	function _onFullscreenChanged4PageMode(isFull){
		divPageImgPanel.find("img").width(divPageImgPanel.width()*scale*0.98);
		gotoPage4PageMode(currPageIndex);
		if(isFull){
			divPageImgPanel.scrollTop(divPageImgPanel.scrollTop()-1);
		}else{
			divPageImgPanel.scrollTop(divPageImgPanel.scrollTop()+1);
		}
	}

	/***
	 * 整页模式下，图片区域滚动事件
	 * @param e
	 * @private
	 */
	function _onScroll4PageImgPanel(e){
		var _this = divPageImgPanel;
		//console.log("_onScroll PageImgPanel", _this.scrollTop());
		var thisTop = _this.offset().top,
			thisHeight = _this.height(),
			i = 0,
			imgs = _this.find("img"),
			reTop;
		for(; i<imgs.length; i++){
			var imgTop = $(imgs[i]).offset().top;
			reTop = imgTop - thisTop;
			if(reTop >= 0){
				break;
			}
		}
		var pageNo;
		if(i==imgs.length || i==imgs.length-1){
			pageNo = imgs.length;
		}else{
			if(reTop >= thisHeight){
				pageNo = i;
			}else if(reTop/thisHeight <= 0.6){
				pageNo = i+1;
			}else{
				pageNo = i;
			}
		}
		if(pageNo < 1){
			pageNo = 1;
		}else if(pageNo > totalPageNo){
			pageNo = totalPageNo;
		}
		if(currPageIndex != pageNo -1){
			currPageIndex = pageNo -1;
			inputCurrPage.val(pageNo);
			_refreshPageButton();
			opts.onSkipPage && opts.onSkipPage(self, pageNo);
		}
	}

	/***
	 * 整页模式， 图片鼠标按下事件
	 * @param e
	 * @private
	 */
	function _onMouseDown4PageImgPanel(e){
		__onMouseDown2Panel(e, divPageImgPanel);
	}

	/***
	 * 整页模式， 图片鼠标移动事件
	 * @param e
	 * @private
	 */
	function _onMouseMove4PageImgPanel(e){
		__onMouseMove2Panel(e, divPageImgPanel);
		isInToolbar = false;
		initFadeToolbarTimer();
		return false;
	}

	/***
	 * 整页模式， 图片鼠标抬起事件
	 * @param e
	 * @private
	 */
	function _onMouseUp4PageImgPanel(e){
		__onMouseUp2Panel(e, divPageImgPanel);
	}

	/***
	 * 整页模式， 图片鼠标移出事件
	 * @param e
	 * @private
	 */
	function _onMouseOut4PageImgPanel(e){
		__onMouseOut2Panel(e, divPageImgPanel);
	}

	/***
	 * 整页模式， 图片触摸开始事件
	 * @param e
	 * @private
	 */
	function _onTouchStart4PageImgPanel(e){
		if(e.touches == undefined || e.touches == null){
			return;
		}
		//console.log("_onTouchStart PageImgPanel", e.touches.length);
		__onTouchStart2Panel(e, divPageImgPanel);
	}

	function __onTouchStart2Panel(e, panel){
		if(e.touches.length == 1){
			__onMouseDown2Panel(e, panel);
			isScaleByTouch = false;
			touchStartPoint = __getEventPointer(e);
		}else if(e.touches.length == 2){
			var p1 = {x: e.touches[0].clientX, y: e.touches[0].clientY},
				p2 = {x: e.touches[1].clientX, y: e.touches[1].clientY};
			lastTouchDistance = __calcDistanceInTwoPoints(p1, p2);
			isScaleByTouch = true;
		}
	}

	/***
	 * 整页模式， 图片触摸移动事件
	 * @param e
	 * @private
	 */
	function _onTouchMove4PageImgPanel(e){
		if(e.touches == undefined || e.touches == null){
			return;
		}
		//console.log("_onTouchMove PageImgPanel", e.touches.length);
		__onTouchMove2Panel(e, divPageImgPanel);
	}

	function __onTouchMove2Panel(e, panel){
		if(e.touches.length == 1){
			if(isScaleByTouch){
				return;
			}
			__onMouseMove2Panel(e, panel);
			isInToolbar = false;
			initFadeToolbarTimer();
			return false;
		}else if(e.touches.length == 2){
			e.preventDefault();  // 阻止默认行为
			var p1 = {x: e.touches[0].clientX, y: e.touches[0].clientY},
				p2 = {x: e.touches[1].clientX, y: e.touches[1].clientY},
				currDis = __calcDistanceInTwoPoints(p1, p2);
			if(lastTouchDistance > 0){
				var s = ((currDis / lastTouchDistance) - 1) / 2;
				//console.log("touch scale="+s);
				scale += s;
				if(scale < MIN_SCALE){
					scale = MIN_SCALE;
				}else if(scale > MAX_SCALE){
					scale = MAX_SCALE;
				}
				__scalePanel(panel, scale);
				lastTouchDistance = currDis;
			}
		}
	}

	/***
	 * 整页模式， 图片触摸结束事件
	 * @param e
	 * @private
	 */
	function _onTouchEnd4PageImgPanel(e){
		//console.log("_onTouchEnd PageImgPanel", e.touches.length);
		__onTouchEnd2Panel(e, divPageImgPanel);
	}

	function __onTouchEnd2Panel(e, panel){
		if(e.touches && e.touches.length < 1){
			__onMouseUp2Panel(e, panel);
			touchStartPoint = null;
		}else if(e.touches && e.touches.length == 1){
			lastTouchDistance = 0;
			touchStartPoint = null;
		}
	}

	/***
	 * 计算p1、p2点之间的距离
	 * @param p1
	 * @param p2
	 */
	function __calcDistanceInTwoPoints(p1, p2){
		var ac = Math.abs(p1.y - p2.y);
		var bc = Math.abs(p1.x - p2.x);
		return Math.sqrt(Math.pow(ac,2) + Math.pow(bc,2));
	}

	/***
	 * 全屏
	 * @param element
	 */
	function launchFullscreen(element) {
		if(element.requestFullscreen) {
			element.requestFullscreen();
		} else if(element.mozRequestFullScreen) {
			element.mozRequestFullScreen();
		} else if(element.webkitRequestFullscreen) {
			element.webkitRequestFullscreen();
		} else if(element.msRequestFullscreen) {
			element.msRequestFullscreen();
		}
		//调用系统方法后依然没有全屏，则手动全屏
		if(!isFullScreen()){
			launchFullscreenManual(element);
		}
	}

	/***
	 * 手动全屏
	 * @param element
	 */
	function launchFullscreenManual(element){
		if(window.top != window){       //在iframe里
			var ifr = __getCurrentIframe();
			$(ifr).addClass("doc-player-fullscreen-root");
		}
		playerRoot.addClass("doc-player-fullscreen-root");
		fullscreenElement = element;
		isFullscreenManual = true;
		_onFullscreenChanged();
	}

	/***
	 * 获得当前所在的iframe的dom
	 * @private
	 */
	function __getCurrentIframe(){
		var ifrs = parent.document.getElementsByTagName('iframe');
		for (var i = 0, j = ifrs.length; i < j; i++){
			if (ifrs[i].contentWindow == window) {
				return ifrs[i];
			}
		}
	}

	/***
	 * 退出全屏
	 */
	function exitFullscreen() {
		if(document.exitFullscreen) {
			document.exitFullscreen();
		} else if(document.mozCancelFullScreen) {
			document.mozCancelFullScreen();
		} else if(document.webkitExitFullscreen) {
			document.webkitExitFullscreen();
		}
		//退出全屏后，依然全屏，则手动退出全屏
		if(isFullScreen() && fullscreenElement){
			exitFullscreenManual(fullscreenElement);
		}
	}

	/***
	 * 手动退出全屏
	 * @param element
	 */
	function exitFullscreenManual(element){
		if(window.top != window) {       //在iframe里
			var ifr = __getCurrentIframe();
			$(ifr).removeClass("doc-player-fullscreen-root");
		}
		playerRoot.removeClass("doc-player-fullscreen-root");
		isFullscreenManual = false;
		fullscreenElement = null;
		_onFullscreenChanged();
	}

	/***
	 * 是否全屏
	 * @returns {boolean}
	 */
	function isFullScreen() {
		var sysFlag = !!(document.fullscreenElement || document.mozFullScreenElement || document.webkitFullscreenElement);
		if(sysFlag == false){
			return isFullscreenManual;
		}else{
			return sysFlag;
		}
	}


	/****************************************************************
	 * 公开方法
	 ****************************************************************/
	self.gotoPage = function(num){
		if(num < 1){
			num = 1;
		}else if(num > totalPageNo){
			num = totalPageNo;
		}
		currPageIndex = num-1;
		if(opts.mode == SINGLE_MODE){
			gotoPage4SingleMode(num-1);
		}else if(opts.mode == PAGE_MODE){
			gotoPage4PageMode(num-1);
		}
	};

	self.nextPage = function(){
		gotoNextPage();
	};

	self.prevPage = function(){
		gotoPrevPage();
	};

	self.getCurrentPageNo = function(){
		return currPageIndex+1;
	};

	self.getTotalPageNo = function(){
		return totalPageNo;
	};

	return self;
};
})(jQuery);
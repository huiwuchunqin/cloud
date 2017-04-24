$.fn.photoSwipe=function(){
	try {
		var noButton=arguments[0]
		var items=new Array();
		var _currentSrc=$(this).attr("src");
		var _realPath=$(this).attr("realPath");
		if(!noButton){
			model.currentSrc(_currentSrc);
			model.currentRealSrc(_realPath);	
		}
		$(this).parent().find("img").each(function(index,item){
			if(index<=7){
				 var i = {
		                    src: item.src,
		                    w: item.naturalWidth || 512,
		                    h: item.naturalHeight || 288
		                };
		                items.push(i);	
			}
               
		})
		var index = $(this).parent().find("img").index(this);
		 if ((typeof window.top.PhotoSwipe) != "undefined" && (typeof window.top.PhotoSwipeUI_Default) != "undefined") {
			 if (window.top.$(".pswp").length == 0) {
				 if(noButton==1){
					 window.top.$("body").append('<div class="pswp" tabindex="-1" role="dialog" aria-hidden="true"><div class="pswp__bg"></div><div class="pswp__scroll-wrap"><div class="pswp__container"><div class="pswp__item"></div><div class="pswp__item"></div><div class="pswp__item"></div></div><div class="pswp__ui pswp__ui--hidden"><div class="pswp__top-bar"><div class="pswp__counter"></div><button class="pswp__button pswp__button--close" title="Close (Esc)"></button><button class="pswp__button pswp__button--fs" title="Toggle fullscreen"></button><button class="pswp__button pswp__button--zoom" title="Zoom in/out"></button><div class="pswp__preloader"><div class="pswp__preloader__icn"><div class="pswp__preloader__cut"><div class="pswp__preloader__donut"></div></div></div></div></div><div class="pswp__share-modal pswp__share-modal--hidden pswp__single-tap"><div class="pswp__share-tooltip"></div></div><button class="pswp__button pswp__button--arrow--left" title="Previous (arrow left)"></button><button class="pswp__button pswp__button--arrow--right" title="Next (arrow right)"></button><div class="pswp__caption"><div class="pswp__caption__center"></div></div></div></div></div>'); 
				 }else{
					 window.top.$("body").append('<div class="pswp" tabindex="-1" role="dialog" aria-hidden="true"><div class="pswp__bg"></div><div class="pswp__scroll-wrap"><div class="pswp__container"><div class="pswp__item"></div><div class="pswp__item"></div><div class="pswp__item"></div></div><div class="pswp__ui pswp__ui--hidden"><div class="pswp__top-bar"><div class="pswp__counter"></div><button class="pswp__button pswp__button--close" title="Close (Esc)"></button><button class="pswp__button pswp__button--fs" title="Toggle fullscreen"></button><button class="pswp__button pswp__button--zoom" title="Zoom in/out"></button><div class="pswp__preloader"><div class="pswp__preloader__icn"><div class="pswp__preloader__cut"><div class="pswp__preloader__donut"></div></div></div></div></div><div class="pswp__share-modal pswp__share-modal--hidden pswp__single-tap"><div class="pswp__share-tooltip"></div></div><button class="pswp__button pswp__button--arrow--left" title="Previous (arrow left)"></button><button class="pswp__button pswp__button--arrow--right" title="Next (arrow right)"></button><div class="pswp__caption"><div class="pswp__caption__center"></div></div><button class="btn btnOrange" style="position:absolute;right:40px;bottom:40px;" onclick="window.top.document.getElementById(\'mediaEdit\').contentWindow.chooseImage();window.top.gallery.close(window.top.gallery.getCurrentIndex())">直接设置为资源封面</button></div></div></div>'); 
				 }
				 
		        }
		        if ((typeof window.top) != "undefined" && window.top.PhotoSwipe && window.top.PhotoSwipeUI_Default) {
		            var pswpElement = window.top.document.querySelectorAll('.pswp')[0];
		            items = items || [];
		            var options = {
		                closeOnScroll: false,
		                index: index || 0
		            };
		            console.info("inited");
		            window.top.gallery = new window.top.PhotoSwipe(pswpElement, window.top.PhotoSwipeUI_Default, items, options);
		            window.top.gallery.init();
		        }

	     }
        
    } catch (e) {
   	 console.info(e);
    }

} 
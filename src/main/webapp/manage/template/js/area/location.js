
var provinceHtml = '<div class="content"><div data-widget="tabs" class="m JD-stock" id="JD-stock">'
								+'<div class="mt">'
								+'    <ul class="tab">'
								+'        <li data-index="0" data-widget="tab-item" class="curr"><a href="#none" class="hover"><em>请选择</em><i></i></a></li>'
								+'        <li data-index="1" data-widget="tab-item" style="display:none;"><a href="#none" class=""><em>请选择</em><i></i></a></li>'
								+'        <li data-index="2" data-widget="tab-item" style="display:none;"><a href="#none" class=""><em>请选择</em><i></i></a></li>'
								+'        <li data-index="3" data-widget="tab-item" style="display:none;"><a href="#none" class=""><em>请选择</em><i></i></a></li>'
								+'    </ul>'
								+'    <div class="stock-line"></div>'
								+'</div>'
								+'<div class="mc" data-area="0" data-widget="tab-content" id="stock_province_item">'
						/*		+'    <ul class="area-list">'
								+'       <li><a href="#none" data-value="1">北京</a></li><li><a href="#none" data-value="2">上海</a></li><li><a href="#none" data-value="3">天津</a></li><li><a href="#none" data-value="4">重庆</a></li><li><a href="#none" data-value="5">河北</a></li><li><a href="#none" data-value="6">山西</a></li><li><a href="#none" data-value="7">河南</a></li><li><a href="#none" data-value="8">辽宁</a></li><li><a href="#none" data-value="9">吉林</a></li><li><a href="#none" data-value="10">黑龙江</a></li><li><a href="#none" data-value="11">内蒙古</a></li><li><a href="#none" data-value="12">江苏</a></li><li><a href="#none" data-value="13">山东</a></li><li><a href="#none" data-value="14">安徽</a></li><li><a href="#none" data-value="15">浙江</a></li><li><a href="#none" data-value="16">福建</a></li><li><a href="#none" data-value="17">湖北</a></li><li><a href="#none" data-value="18">湖南</a></li><li><a href="#none" data-value="19">广东</a></li><li><a href="#none" data-value="20">广西</a></li><li><a href="#none" data-value="21">江西</a></li><li><a href="#none" data-value="22">四川</a></li><li><a href="#none" data-value="23">海南</a></li><li><a href="#none" data-value="24">贵州</a></li><li><a href="#none" data-value="25">云南</a></li><li><a href="#none" data-value="26">西藏</a></li><li><a href="#none" data-value="27">陕西</a></li><li><a href="#none" data-value="28">甘肃</a></li><li><a href="#none" data-value="29">青海</a></li><li><a href="#none" data-value="30">宁夏</a></li><li><a href="#none" data-value="31">新疆</a></li><li><a href="#none" data-value="32">台湾</a></li><li><a href="#none" data-value="42">香港</a></li><li><a href="#none" data-value="43">澳门</a></li><li><a href="#none" data-value="84">钓鱼岛</a></li>'
								+'    </ul>'*/
								+'</div>'
								+'<div class="mc" data-area="1" data-widget="tab-content" id="stock_city_item"></div>'
								+'<div class="mc" data-area="2" data-widget="tab-content" id="stock_area_item"></div>'
							/*	+'<div class="mc" data-area="3" data-widget="tab-content" id="stock_town_item"></div>'*/
								+'</div></div>';

function getAreaList(result){
	var html = ["<ul class='area-list'>"];
	var longhtml = [];
	var longerhtml = [];
	if (result&&result.length > 0){
		for (var i=0,j=result.length;i<j ;i++ ){
			result[i].name = result[i].name.replace(" ","");
			if(result[i].name.length > 12){
				longerhtml.push("<li class='longer-area'><a href='#none' data-cd='"+result[i].areaCd+"' data-value='"+result[i].id+"'>"+result[i].name+"</a></li>");
			}
			else if(result[i].name.length > 5){
				longhtml.push("<li class='long-area'><a href='#none' data-cd='"+result[i].areaCd+"' data-value='"+result[i].id+"'>"+result[i].name+"</a></li>");
			}
			else{
				html.push("<li><a href='#none'data-cd='"+result[i].areaCd+"' data-value='"+result[i].id+"'>"+result[i].name+"</a></li>");
			}
		}
	}
	else{
		html.push("<li><a href='#none' data-value='"+currentAreaInfo.currentFid+"'> </a></li>");
	}
	html.push(longhtml.join(""));
	html.push(longerhtml.join(""));
	html.push("</ul>");
	return html.join("");
}
function cleanKuohao(str){
	if(str&&str.indexOf("(")>0){
		str = str.substring(0,str.indexOf("("));
	}
	if(str&&str.indexOf("（")>0){
		str = str.substring(0,str.indexOf("（"));
	}
	return str;
}
var page_load = true;
var province;
var childList;
var threeLevelArea;
var third,second,first;
/**
 * 得到第一层
 */
function getTopParent(){
	var opt={
			url:'/area/getSimpleTopList.html',
			type:'post',
			async:false,
			success:function(res){
				province=res;
			}
	}
	$.ajax(opt);
}
/**
 * 查询子节点
 * @param id
 */
function getChildList(id){
	var opt={
			url:'/area/getSimpleChildList.html',
			type:'post',
			async:false,
			data:{
				id:id,
			},
			success:function(res){
				childList=res;
			}
	}
	$.ajax(opt);
}
/**
 * 查询第三层节点
 * @param areaCD
 * @returns
 */
function getThreeArea(areaCD){
	var opt={
			url:'/area/get3Area.html',
			type:'post',
			async:false,
			data:{
				areaCD:areaCD,
			},
			success:function(res){
				threeLevelArea=res;
			}
	}
	$.ajax(opt);
}

function getStockOpt(id,name){
	if(currentAreaInfo.currentLevel==3){
		currentAreaInfo.currentAreaId = id;
		currentAreaInfo.currentAreaName = name;
		if(!page_load){
			currentAreaInfo.currentTownId = 0;
			currentAreaInfo.currentTownName = "";
		}
	}
	else if(currentAreaInfo.currentLevel==4){
		currentAreaInfo.currentTownId = id;
		currentAreaInfo.currentTownName = name;
	}
	//添加20140224
	$('#store-selector').removeClass('hover');
	//setCommonCookies(currentAreaInfo.currentProvinceId,currentLocation,currentAreaInfo.currentCityId,currentAreaInfo.currentAreaId,currentAreaInfo.currentTownId,!page_load);
	if(page_load){
		page_load = false;
	}
	//替换gSC
	var address = currentAreaInfo.currentProvinceName+currentAreaInfo.currentCityName+currentAreaInfo.currentAreaName+currentAreaInfo.currentTownName;
	$("#store-selector .text div").html(currentAreaInfo.currentProvinceName+cleanKuohao(currentAreaInfo.currentCityName)+cleanKuohao(currentAreaInfo.currentAreaName)+cleanKuohao(currentAreaInfo.currentTownName)).attr("title",address);
}
function getAreaListcallback(r){
	currentDom.html(getAreaList(r));
	if (currentAreaInfo.currentLevel >= 2){
		currentDom.find("a").click(function(){
			if(page_load){
				page_load = false;
			}
			if(currentDom.attr("id")=="stock_area_item"){
				currentAreaInfo.currentLevel=3;
			}
			else if(currentDom.attr("id")=="stock_town_item"){
				currentAreaInfo.currentLevel=4;
			}
			getStockOpt($(this).attr("data-value"),$(this).html());
		});
		if(page_load){ //初始化加载
			currentAreaInfo.currentLevel = currentAreaInfo.currentLevel==2?3:4;
			if(currentAreaInfo.currentAreaId&&new Number(currentAreaInfo.currentAreaId)>0){
				getStockOpt(currentAreaInfo.currentAreaId,currentDom.find("a[data-value='"+currentAreaInfo.currentAreaId+"']").html());
			}
			else{
				getStockOpt(currentDom.find("a").eq(0).attr("data-value"),currentDom.find("a").eq(0).html());
			}
		}
	}
}
function chooseProvince(provinceId,provinceName,object){
	provinceContainer.hide();
	currentAreaInfo.currentLevel = 1;
	currentAreaInfo.currentProvinceId = provinceId;
	currentAreaInfo.currentProvinceName =provinceName;
	if(!page_load){
		currentAreaInfo.currentCityId = 0;
		currentAreaInfo.currentCityName = "";
		currentAreaInfo.currentAreaId = 0;
		currentAreaInfo.currentAreaName = "";
		currentAreaInfo.currentTownId = 0;
		currentAreaInfo.currentTownName = "";
	}
	areaTabContainer.eq(0).removeClass("curr").show().find("em").html(provinceName);
	areaTabContainer.eq(1).addClass("curr").show().find("em").html("请选择");
	areaTabContainer.eq(2).hide();
	areaTabContainer.eq(3).hide();
	cityContainer.show();
	areaContainer.hide();
	townaContainer.hide();
	getChildList(provinceId);
	cityContainer.html(getAreaList(childList));
	cityContainer.find("a").click(function(){
		if(page_load){
			page_load = false;
		}
		$("#store-selector").unbind("mouseout");
		chooseCity($(this).attr("data-value"),$(this).attr("data-cd"),$(this).html(),object);
	});
/*	if(page_load){ //初始化加载
		if(currentAreaInfo.currentCityId&&new Number(currentAreaInfo.currentCityId)>0){
			chooseCity(currentAreaInfo.currentCityId,cityContainer.find("a[data-value='"+currentAreaInfo.currentCityId+"']").html());
		}
		else{
			chooseCity(cityContainer.find("a").eq(0).attr("data-value"),cityContainer.find("a").eq(0).html());
		}
	}*/
}
function chooseCity(cityId,cityCd,cityName,object){
	provinceContainer.hide();
	cityContainer.hide();
	currentAreaInfo.currentLevel = 2;
	currentAreaInfo.currentCityId = cityId;
	currentAreaInfo.currentCityName = cityName;
	if(!page_load){
		currentAreaInfo.currentAreaId = 0;
		currentAreaInfo.currentAreaName = "";
		currentAreaInfo.currentTownId = 0;
		currentAreaInfo.currentTownName = "";
	}
	areaTabContainer.eq(1).removeClass("curr").show().find("em").html(cityName);
	areaTabContainer.eq(2).addClass("curr").show().find("em").html("请选择");
	areaTabContainer.eq(3).hide();
	areaContainer.show().html("<div class='iloading'>正在加载中，请稍候...</div>");
	townaContainer.hide();
	currentDom = areaContainer;
	getChildList(cityId);
	//如果没有第三层则直接返回值
	if(childList==undefined||childList==null||childList.length<=0){
		$("#store-selector").unbind("mouseout");
		$("#store-selector .content,#JD-stock").hide();
		$(object).next('div._areaDiv').empty().hide();
		$(object).val("");
		$(object).val(currentAreaInfo.currentProvinceName+currentAreaInfo.currentCityName)
		$(object).attr("data-value",cityCd);
		return false;
	}
	areaContainer.html(getAreaList(childList));
	areaContainer.find("a").click(function(){
		if(page_load){
			page_load = false;
		}
		areaTabContainer.eq(2).find("em").html($(this).html());
		currentAreaInfo.currentLevel = 3;
		currentAreaInfo.currentAreaId = $(this).attr("data-value");
		currentAreaInfo.currentAreaName =$(this).html();
		$("#store-selector").unbind("mouseout");
		$("#store-selector .content,#JD-stock").hide();
		$(object).next('div._areaDiv').empty().hide();
		var name=currentAreaInfo.currentProvinceName+currentAreaInfo.currentCityName+currentAreaInfo.currentAreaName;
		$(object).val("");
		$(object).val(name)
		$(object).attr("data-value",$(this).attr("data-cd"))
		/*********弄个input框存cd*************/
	/*	var areaInput=$('#_areaCD')[0];
		if(typeof(areaInput)!="undefined")$(areaInput).remove();//移除之前存在的input框
		$('.area').after("<input id='_areaCD' hidden='true' value='"+$(this).attr("data-cd")+"'/>")*/
	/*	chooseArea($(this).attr("data-value"),$(this).html());*/
	});
}
function chooseArea(areaId,areaName){
	provinceContainer.hide();
	cityContainer.hide();
	areaContainer.hide();
	currentAreaInfo.currentLevel = 3;
	currentAreaInfo.currentAreaId = areaId;
	currentAreaInfo.currentAreaName = areaName;
	if(!page_load){
		currentAreaInfo.currentTownId = 0;
		currentAreaInfo.currentTownName = "";
	}
	areaTabContainer.eq(2).removeClass("curr").find("em").html(areaName);
	areaTabContainer.eq(3).addClass("curr").show().find("em").html("请选择");
	townaContainer.show().html("<div class='iloading'>正在加载中，请稍候...</div>");
	currentDom = townaContainer;
	getChildList(areaId);
	townaContainer.html(getAreaList(childList));
/*	$.getJSONP("http://d.360buy.com/area/get?fid="+areaId+"&callback=getAreaListcallback");*/
}


//当前地域信息
var currentAreaInfo;
//初始化当前地域信息
function CurrentAreaInfoInit(object){
	currentAreaInfo =  {"currentLevel": 1,"currentProvinceId": '',"currentProvinceName":"","currentCityId":'',"currentCityName":"","currentAreaId":'',"currentAreaName":""};
	getTopParent();
	provinceContainer.html(getAreaList(province));
	currentAreaInfo.currentProvinceId=province[0].id;
	currentAreaInfo.currentProvinceName=province[0].name;
	/*var areaCd=$(object).next('._areaCD').val();*/
	var areaCd=$(object).attr("data-value");
	if(areaCd!=undefined&&areaCd!=""){
		getThreeArea(areaCd);
		if(threeLevelArea==null||threeLevelArea==undefined)return false;
		third=threeLevelArea.thirdNode;
		second=threeLevelArea.secondNode;
		first=threeLevelArea.firstNode;
		chooseProvince(first.id,first.name,object)
	   if(second==null||second==undefined){
		   areaTabContainer.eq(0).find("em").html(first.name);
	   }else{
			chooseProvince(first.id,first.name,object)   
	   }
		if(third!=null){//第三层不为空
			chooseCity(second.id,second.areaCd,second.name,object)
			areaTabContainer.eq(2).find("em").html(third.name);
		}else{//第三层为空
			areaTabContainer.eq(1).find("em").html(second.name);
		}
	}
	/*	ipLoc = ipLoc?ipLoc.split("-"):[1,72,0,0];
	if(ipLoc.length>0&&ipLoc[0]){
		currentAreaInfo.currentProvinceId = ipLoc[0];
		currentAreaInfo.currentProvinceName = getNameById(ipLoc[0]);
	}
	if(ipLoc.length>1&&ipLoc[1]){
		currentAreaInfo.currentCityId = ipLoc[1];
	}
	if(ipLoc.length>2&&ipLoc[2]){
		currentAreaInfo.currentAreaId = ipLoc[2];
	}
	if(ipLoc.length>3&&ipLoc[3]){
		currentAreaInfo.currentTownId = ipLoc[3];
	}*/
}
function removeClass(){
	$("#store-selector").removeClass("hover");
	$('._areaDiv').remove();
	}
function initeArea(){
	var ul='<ul id="list1"  style="width:620px">'
        +'<li id="summary-stock">'
			+'<div class="dd">'
				+'<div id="store-selector" class="store-selector">'
					+'<div class="text"><div></div>选择地区</div>   '
					+'<div onclick="removeClass()" class="close"></div>'
				+'</div>'
				+'<div id="store-prompt"><strong></strong></div>'
			+'</div>'
		+'</li>'
	+'</ul>'
	areaInput=$('.area');
	$.each(areaInput,function(){
		var areaInput_=$(this)[0]
		$(areaInput_).attr("readOnly","readOnly");
		/*var top=areaInput_.offsetTop+$(window).scrollTop();
		var left=areaInput_.offsetLeft-$(window).scrollLeft();*/
		 var offset = $(areaInput_).position();
		var top=offset.top+parseInt(areaInput_.style.marginTop||0);
		var left=offset.left+parseInt(areaInput_.style.marginLeft||0);
		/*var ul=$('#list1');*/
		var height=$(areaInput).outerHeight();
		var div=$('<div class="_areaDiv"style="width:50px;position:absolute;z-index:1000">').css({'left':left+'px','top':top+'px'});
		$(ul).find('div.store-selector>div.text').height(height);
		div.html($(ul));
		$(areaInput_).bind("click",function(){
			var existAreaDiv=$('._areaDiv:visible');
			if(existAreaDiv.length!=0)return false;
			div.show();
			div.html($(ul));
			/*div.show();*/
			$(areaInput_).after(div);
			$("#store-selector .text").after(provinceHtml);
		 /*   areaTabContainer=$("#JD-stock .tab li");
		    provinceContainer=$("#stock_province_item");
			cityContainer=$("#stock_city_item");
			areaContainer=$("#stock_area_item");
			townaContainer=$("#stock_town_item");*/
			areaTabContainer=$(areaInput_).next("div").find(".tab li")
			provinceContainer=$(areaInput_).next("div").find("div[data-area='0']");
			cityContainer=$(areaInput_).next("div").find("div[data-area='1']");
			areaContainer=$(areaInput_).next("div").find("div[data-area='2']");
			townaContainer=$(areaInput_).next("div").find("div[data-area='3']");
			currentDom = provinceContainer;
			CurrentAreaInfoInit(areaInput_);
			$(".store-selector").unbind("mouseover").bind("mouseover",function(){
				$('.store-selector').addClass('hover');
				$(".store-selector .content,.JD-stock").show();
			}).find("dl").remove();
			areaTabContainer.eq(0).find("a").click(function(){
				areaTabContainer.removeClass("curr");
				areaTabContainer.eq(0).addClass("curr").show();
				provinceContainer.show();
				cityContainer.hide();
				areaContainer.hide();
				townaContainer.hide();
				areaTabContainer.eq(1).hide();
				areaTabContainer.eq(2).hide();
				areaTabContainer.eq(3).hide();
			});
			areaTabContainer.eq(1).find("a").click(function(){
				areaTabContainer.removeClass("curr");
				areaTabContainer.eq(1).addClass("curr").show();
				provinceContainer.hide();
				cityContainer.show();
				areaContainer.hide();
				townaContainer.hide();
				areaTabContainer.eq(2).hide();
				areaTabContainer.eq(3).hide();
			});
			areaTabContainer.eq(2).find("a").click(function(){
				areaTabContainer.removeClass("curr");
				areaTabContainer.eq(2).addClass("curr").show();
				provinceContainer.hide();
				cityContainer.hide();
				areaContainer.show();
				townaContainer.hide();
				areaTabContainer.eq(3).hide();
			});
			provinceContainer.find("a").click(function() {
				if(page_load){
					page_load = false;
				}
				$("#store-selector").unbind("mouseout");
				chooseProvince($(this).attr("data-value"),$(this).html(),areaInput_);
			}).end();
		})
	})
}
(function(){
	initeArea()
	/*chooseProvince(currentAreaInfo.currentProvinceId);*/
})();

function getCookie(name) {
	var start = document.cookie.indexOf(name + "=");
	var len = start + name.length + 1;
	if ((!start) && (name != document.cookie.substring(0, name.length))) {
		return null;
	}
	if (start == -1) return null;
	var end = document.cookie.indexOf(';', len);
	if (end == -1) end = document.cookie.length;
	return unescape(document.cookie.substring(len, end));
};






/**
 * Created by dong on 2015/7/22.
 */

$.fn.extend({
	navClick : function(options, callback) {
		$(this).on("click", "span", function() {
			$(this).parent().find("span").removeClass("active");
			$(this).addClass("active");
			var data = [];
			data.text = $(this).text();
			data.id = $(this).attr("id");
			console.log(data);
			return data;
		});
	}
});

$.fn.extend({
	navChange : function(options, callback) {
		$(this).on("click", "dd", function() {
			$(this).parent().find("dd").removeClass("active");
			$(this).addClass("active");
			var data = [];
			data.text = $(this).text();
			data.id = $(this).attr("id");
			console.log(data);
			//return data;
		});

		if (callback) {
			callback();
		}
	}

});

/**
 * 扩充String原型
 * @returns {string}
 */
String.prototype.format=function(){
	var args = arguments;
	return this.replace(/\{(\d+)\}/g,function(s,i){
		return args[i];
	});
}
/**
 * 同步版 $.getJSON
 * @private
 */
$._getJSON=function(){
	$.ajaxSettings.async = false;
	$.getJSON.apply($,arguments);
	$.ajaxSettings.async = true;
};

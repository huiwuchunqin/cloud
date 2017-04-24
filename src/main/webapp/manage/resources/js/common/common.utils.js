/**
 * Created by bzt-00 on 2015/12/17.
 */
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

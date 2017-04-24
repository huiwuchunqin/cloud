/**
 * 根据后缀分配资源类型
 * @param suffix
 */
function getResTypeBySuffix(suffix){
	var    videoType=[".f4v",".wmv",".mkv",".mov",".avi",".flv",".rm",".mp4"],
	   audioType=[".aac",".ape",".mp3",".ogg",".wav",".wma"],
	documentType=[".ppt",".pdf",".doc",".xls",".docx",".pptx",".xlsx"],
		 pptType=[".ppt",".pptx"];

	if(videoType.indexOf(suffix)>=0||audioType.indexOf(suffix)>=0){
		return "30";
	}else if(documentType.indexOf(suffix)>=0||pptType.indexOf(suffix)>=0){
		return "40";
	}else{
		return "";
	}
}




/**=======================================================================小工具==================================================================*/

/**
 * 过滤undefined的为""
 */
function replaceUndefined(str){
	if(typeof(str)=="undefined"){
		return "";
	}
	return str;
}

/**
 * 数据容量转换
 * @param bytes				字节数
 * @returns {String}
 */
function bytesToSize(bytes) {
    if (bytes === 0) return '0 B';
    var k = 1000, // or 1024
        sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
        i = Math.floor(Math.log(bytes) / Math.log(k));
   return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];
}
/**
 * 遍历用
 * @param ary				对象数组
 * @param value				比较key
 * @param name				键
 * @param returnName		返回key
 * @returns {String}
 */
function eachName(ary,value,name,returnName){
	var returnStr="";
	if(value&&typeof(ary)=="object"&&ary.length){
		$.each(ary,function(i,v){
			if(!returnStr&&v[name]==value){
				if(typeof(returnName)=="object"){
					returnStr=v;
				}else{
					returnStr=v[returnName];
				}
			}
		})
	}
	return returnStr;
}

/**
 * 
 * @param ary
 * @param value
 * @param key
 */
function getArrayIndex(ary,key,value){
	var index=0;
	$.each(ary,function(i,v){
		if(v[key]==value){
			index=i;
		}
	});
	return index;
}

/**
 * 字符串格式化
 */
String.prototype.format= function(){
    var args = arguments;
    return this.replace(/\{(\d+)\}/g,function(s,i){
      return args[i];
    });
}

/**
 * 对象比较
 */
objEquals=function(obj,otherObj){
	if(typeof(obj)=="object"&&typeof(otherObj)=="object"){
		for(key in obj){
			if(obj[key]!=otherObj[key]){
				return false;
			}
		}
	}
};
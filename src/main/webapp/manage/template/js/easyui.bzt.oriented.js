(function(){
	//针对下拉框设置的时候值不存在
	var comSetValue=$.fn.combobox.methods.setValue
	$.fn.combobox.methods.setValue=function(){
		var target=arguments[0];
		var data=$(target).combobox('getData');
		var options=$(target).combobox('options');
		var argu=arguments;
		var same=_.find(data,function(item){
			return item[options.valueField]==argu[1]
		})
		//找到了就设置不然就不设
		if(same){
			console.info(arguments);
			 comSetValue.apply(null,arguments);
		}else{
			if(argu[1]==""){
				$(target).combobox('clear');
			}
		}
		return target
	}
	
	$.fn.combobox.defaults.panelHeight="auto"
	
	$.fn.datagrid.defaults.pageSize=15
})()
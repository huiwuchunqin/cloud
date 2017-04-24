$.extend($.fn.datagrid.methods, {
	getRowIndexByObj : function(jq, target) {
		var $tr = $(target).closest("[datagrid-row-index]");
		return parseInt($tr.attr("datagrid-row-index"), 10);
	},
	getFieldTotal : function(jq, fieldName) {
		var rows = jq.datagrid("getRows");
		var i = 0;
		var total = 0.00;
		// var oldTotal = 0.00;
		for (var i = 0; i < rows.length; i++) {
			var editor = jq.datagrid("getEditor", {
				index : i,
				field : fieldName
			});
			if (editor) {
				var val = editor.actions.getValue(editor.target);
				if (!isNaN(val) && $.trim(val) != "") {
					total += parseFloat(val);
				}
			} else {
				var val = rows[i][fieldName];
				if (!isNaN(val) && $.trim(val) != "") {
					total += parseFloat(val);
				}
			}
			// var valOld = rows[i][fieldName];
			// if (!isNaN(valOld) && $.trim(valOld) != "") {
			// oldTotal += parseFloat(valOld);
			// }
		}
		// var feet = jq.datagrid('getFooterRows');
		// var serverTotal = feet[0][fieldName];
		// var lastTotal = parseFloat(serverTotal) - oldTotal + total;
		// return lastTotal;
		return total;
	}
});
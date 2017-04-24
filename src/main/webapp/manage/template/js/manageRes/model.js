function easyuiDefaultModel(){
	var self=this;
	self.source=ko.observableArray([]),
	self.val=ko.observable(""),
	self.viewSettings={
			valueField:'code',
			textField:'name',
		}

}
    //编辑资源
    function edit(id,check) {
        easyui_openNoResizeWindow("资源编辑", 940, 700,
                "/manage/res/resMediaEdit.html?check="+check+"&resId=" + id,
                function (r) {
                    query();
                }, {}, "mediaEdit")
    }
	
  

    //编辑资源
    function edit(id,check) {
        easyui_openNoResizeWindow("资源编辑", 940, 700,
                "/manage/audio/resAudioEdit.html?check="+check+"&resId=" + id,
                function (r) {
                    query();
                }, {}, "audioEdit")
    }
    

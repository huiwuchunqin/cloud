/**
 * Created by bzt-00 on 2015/12/16.
 */
//资源上传业务操作

var service={};

/**
 * ajax回调过滤
 * @param res       response
 * @param fun       回调
 */
service.ajaxCallback=function(res,fun){
    res.success && (fun()) || (console.info(res.msg));
}

/**
 * 下拉列表回调处理
 * @param $root
 * @param name
 * @param msg
 */
service.processSelectCallBack=function($root,name,msg,res){
    service.ajaxCallback(res,function(){
        $root[name+selectTagName.options](res.data);
        $root[name]("");
       // $root[name+selectTagName.caption](msg);
    });
}

/**
 * 取消下拉列表选择
 * @param $root
 * @param name
 * @param msg
 */
service.cancelSelectChoice=function($root,name,msg){
    $root[name+selectTagName.options]([]);
    $root[name]("");
    //$root[name+selectTagName.caption](msg);
}
/**
 * 资源类型改变——改变子类
 * @param resType       当前选中资源类型对象
 * @param $data         ko对象
 */
service.changeResType=function(resType,$root,$data){
    var code=resType && resType.code,
        name='resTypeChild';
    if(code) {
        $._getJSON('/common/upload/resType/{0}'.format(code), {}, function (res) {
            service.processSelectCallBack($root,name,'请选择标签', res);
        });
    }else{
        service.cancelSelectChoice($root,name,'请选择资源类型');
    }
}

/**
 * 学段改变回调——改变年级
 * @param section       当前选中学段
 * @param $data         ko对象
 */
service.changeSection=function(section,$data){
    var code=section && section.code;
    if(code) {
        $._getJSON('/common/grade/{0}/list.html'.format(code), {}, function (res) {
            service.processSelectCallBack($data, 'grade', '请选择年级', res);
        });
        $._getJSON('/common/subject/{0}/list.html'.format(code), {}, function (res) {
            service.processSelectCallBack($data, 'subject', '请选择学科', res);
        });
    }else {
        service.cancelSelectChoice($data, 'grade', '');
        service.cancelSelectChoice($data,'subject','');
    }
}

/**
 *年级改变回调——改变学科
 * @param grade
 * @param $data
 */
service.changeGrade=function(grade,$data){
    var code=grade && grade.code,name='subject';

}

/**
 * 学科改变回调——改变教材版本
 * @param subject
 * @param $data
 */
service.changeSubject=function(subject,$data){
    var code=subject && subject.code,name='textbook';
    if(code) {
        queryVersion(code,$data);
        $._getJSON('/common/textbookver/{0}/list.html'.format(code), {}, function (res) {
            service.processSelectCallBack($data, name, '请选择教材', res);
        });
    }else{
        service.cancelSelectChoice($data,name,'');
        queryVersion("",$data);
    }
}


service.changeTextbook=function(textbook,$data){

}

/**
 * 保存文件
 * @param $data
 */
service.save=function($data){
    var uploader=$data.multipleUploader,                        //WU 实例
        result=getChoiceFile("#multiple-file-items",uploader),  //选中的文件
        list=result.ko_fileList,                                //选中的文件列表
        saveFileList={                                          //组装后台保存数组
            file            :[],        //文件列表
            knowledegList   :[],        //与知识点关系
            chapterList     :[],        //与教材章节关系
            sectionRelList  :[],        //与学段关系
            gradeRelList    :[],        //与年级关系
            subjectRelList  :[],        //与学科关系
            textbookList    :[]
        };

    if(!list.length){
        layer.msg("请选择需要保存的文件.");
        return;
    }

    if(!$data.section()){
        layer.msg("请选择学段.");
        return;
    }

    if(!$data.grade()){
        layer.msg("请选择年级.");
        return;
    }

    if(!$data.subject()){
        layer.msg("请选择学科.");
        return;
    }

    if(!$data.textbook()){
        layer.msg("请选择教材版本.");
        return;
    }

    if(!$data.resType()){
        layer.msg("请选择一级分类.");
        return;
    }

    if(!$data.resTypeChild()){
        layer.msg("请选择二级分类.");
        return;
    }

    if(!$data.shareLevel()){
        layer.msg("请选择分享级别.");
        return;
    }

    //资源属性
    var otherInfo={
        resTypeL1   :   $data.resType().code,
        coverPath   :   $data.imgUploader.ko_file().url && $data.imgUploader.ko_file().url(),
        subjectCode :   $data.subject() && $data.subject().code,
        sectionCode :   $data.section() && $data.section().code,
        gradeCode   :   $data.grade() && $data.grade().code,
        shareLevel  :   $data.shareLevel(),
        textbook    :   $data.textbook() && $data.textbook().code,
        resTypeL2   :   $data.resTypeChild().code,
        resDesc     :   $data.resDesc()
    };

    //未上传的文件
    var notUploadFileName=[];
    //获取所有资源信息
    $.each(list,function(i,v){
        var res=$.extend({
            objectId    :  v.objectId,
            resName     :  v.name(),
            suffix      :  v.ext(),
            resSize     :  v.size(),
            crcCode     :  v.crcCode()
        },otherInfo);                   //资源信息

        v.file && ( res.objectId= v.file.objectId );
        !res.objectId && notUploadFileName.push(res.name)  || saveFileList.file.push(res);
    });

    if(notUploadFileName.length){
        if(!saveFileList.file.length){
            layer.msg("文件尚未上传，请先上传文件！");
            return;
        }else{
            layer.alert("以下文件由于未上传无法保存:\n{0}{1}".format(notUploadFileName.join(",")));
        }
    }

    //教材关系
    $.each($data.chapterList(),function(i,v){
         saveFileList.chapterList.push(v.code);
    });

    //知识点关系
    $.each($data.knowledegList(),function(i,v){
        saveFileList.knowledegList.push(v.code);
    });

    //学段关系
    saveFileList.sectionRelList.push(otherInfo.sectionCode);

    //年级关系
    saveFileList.gradeRelList.push(otherInfo.gradeCode);

    //学科关系
    saveFileList.subjectRelList.push(otherInfo.subjectCode);

    //教材关系
    saveFileList.textbookList.push(otherInfo.textbook);

    saveFile(saveFileList);
}

/**
 * 还原
 */
service.cancel=function(){
    var vm=viewModel;
    vm.resType("");
    vm.resTypeChild("");
    vm.section("");
    vm.grade("");
    vm.subject("");
    vm.textbook("");
    vm.resDesc("");
    vm.shareLevel("");
    vm.chapterList("");
    vm.knowledegList()
    vm.imgUploader.cancel(vm.imgUploader.ko_file,vm.imgUploader);

    //需要取消选中的ztree树
    var ztree_selectors=["#nodeInfo","#knowledgePoint"];
    $.each(ztree_selectors,function(i,treeId){
        //获取当前ztree对象
        var nodeZtree=$.fn.zTree.getZTreeObj(treeId);
        nodeZtree && nodeZtree.cancelSelectedNode();
    });

    $("#tb_kp").css("display","none");
    $("#property").css("display","none");
}

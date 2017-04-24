/**
 * Created by wgh on 2015/12/16.
 * 以下WU简称 WebUploader，WU对象简称 webUploader 文件对象
 * 更多文档关注——http://fex.baidu.com/webuploader/doc/index.html
 */



var myUploader={
    resTypeList:{
        "10":["f4v","wmv","mkv","mov","avi","flv","rm","mp4"],
        "20":["ppt","pdf","doc","xls","docx","pptx","xlsx"]
    }
};

/**
 * 文件上传插件基类         （增加一些回调默认处理方式,例:非允许类型文件添加队列之前弹窗提醒，所有文件上传完毕重置队列）
 * @param uploader
 */
myUploader.baseUploader=function(uploader){
    //非允许类型文件添加队列之前弹窗提醒
    uploader.on( 'beforeFileQueued',myUploader.beforeFileQueued);

    //所有文件上传完毕重置队列
    uploader.on( 'uploadFinished',myUploader.uploadFinished);

    return uploader;
}
/**
 * 图片上传简单封装 (单图片上传，多图片上传时请自行重写回调)
 * @param options 文件上传配置项，[{pick:pick}] 即可使用，如需个性化写入更多参数即可，api url在js顶部
 * pick {Selector, Object} [可选] [默认值：undefined] 指定选择文件的按钮容器，不指定则不创建按钮。
 * id {Seletor|dom} 指定选择文件的按钮容器，不指定则不创建按钮。注意 这里虽然写的是 id, 但是不是只支持 id, 还支持 class, 或者 dom 节点。
 * label {String} 请采用 innerHTML 代替
 * innerHTML {String} 指定按钮文字。不指定时优先从指定的容器中看是否自带文字。
 * multiple {Boolean} 是否开起同时选择多个文件能力。
 */
myUploader.imgUploader=function(options){

    var imgUploader=WebUploader.create($.extend({},{
        // swf文件路径
        swf: 'template/js/webuploader-0.1.5/Uploader.swf',
        auto:true,
        //分片上传
        chunked:false,
        chunkSize:5*1024*1024,
        //formData:{"type":"1","key":"00000007","enc":"21e205db9f37bf8edc765d0dc278c573","remain":"1073741824"},

        // 文件接收服务端。
        server: '/common/upload/uploadCover.html?do=upload',

        //// 选择文件的按钮。可选。
        //// 内部根据当前运行是创建，可能是input元素，也可能是flash.
        //pick: pick,

        // 只允许选择图片文件。
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/*'
        },

        //单个文件小于500m
        fileSingleSizeLimit:500 * 1024 * 1024,
    },options));

    imgUploader.ko_file=ko.observable({});


    //文件被添加队列后
    imgUploader.on( 'fileQueued', function( file ) {
        // 创建缩略图
        // 如果为非图片文件，可以不用调用此方法。
        // thumbnailWidth x thumbnailHeight 为 100 x 100
        this.makeThumb( file, function( error, src ) {
            //无法预览
            if ( error ) {
                file.img='<span>不能预览</span>';
            }else{
                file.img="<img src='"+src+"'/>";
            }

            //队列中只允许一个
            imgUploader.ko_file(ko.mapping.fromJS(new myUploader.uploadFile(file)));
        }, 100, 100 );
    });

    // 文件上传过程中创建进度条实时显示。
    imgUploader.on( 'uploadProgress', function( file, percentage ) {
        myUploader.updateProcess(imgUploader.ko_file(),percentage);
    });

    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    imgUploader.on( 'uploadSuccess', function( file,res ) {
        service.ajaxCallback(res,function(){
            imgUploader.ko_file().url(res.data);
            imgUploader.reset();
        })
        //console.info("文件上传成功",arguments);
    });

    // 文件上传失败，现实上传出错。
    imgUploader.on( 'uploadError', function( file ) {
        console.info("文件上传失败",unescape(arguments[1]._raw));
    });


    /**
     * 取消图片上传
     * @param ko_file       文件信息
     * @param imgUploader   图片上传实例
     */
    imgUploader.cancel=function(ko_file,imgUploader){
        var id=ko_file().id && ko_file().id();
        if(id) {
            ko_file({});
            var file=imgUploader.getFile(id);
            file && imgUploader.cancelFile(file);
            imgUploader.reset();
        }
    }

    return myUploader.baseUploader(imgUploader);
}

/**
 * 多文件上传，简单封装（支持秒传，断点续传，默认进行秒传验证）
 * @param options  略.
 */
myUploader.multipleUploader=function(options){
    var multipleUploader=WebUploader.create($.extend({},{
        // swf文件路径
        swf: 'template/js/webuploader-0.1.5/Uploader.swf',
        //分片上传
        chunked:false,
        //分片大小 50m
        chunkSize:50*1024*1024,

        // 只允许选择图片文件。
        accept: {
            title: '请选择视频与文档',
            extensions: 'f4v,wmv,mkv,mov,avi,flv,rm,mp4,ppt,pdf,doc,xls,docx,pptx,xlsx',
            mimeTypes:".f4v,.wmv,.mkv,.mov,.avi,.flv,.rm,.mp4,.ppt,.pdf,.doc,.xls,.docx,.pptx,.xlsx"
            //mimeTypes: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.openxmlformats-officedocument.presentationml.presentation,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/msword,application/pdf,application/vnd.ms-powerpoint,video/mp4,application/octet-stream,video/x-flv,video/mp4,video/avi,video/quicktime,application/octet-stream,video/x-ms-wmv'
        },

        //单个文件小于500m.
        fileSingleSizeLimit:500 * 1024 * 1024,
    },options));

    multipleUploader.ko_fileList=ko.observableArray([]);

    //文件添加队列
    multipleUploader.on( 'fileQueued', function( file ) {
        //清空文件队列(单文件)
        var ko_fileList=multipleUploader.ko_fileList();
        $.each(ko_fileList,function(i,v){
           var _file=multipleUploader.getFile(v.id());
            _file && multipleUploader.removeFile(_file,true);
        });
        multipleUploader.ko_fileList([]);
        //multipleUploader.reset();


        //ko文件队列新增文件
        var newFile=ko.mapping.fromJS(new myUploader.uploadFile(file));
        multipleUploader.ko_fileList.push(newFile);

        //取文件前50M 计算crcCode
        multipleUploader.crcFile(file);
    });

    //crc校验中...
    multipleUploader.on( 'crcFileProgress',function(file,event){
        //获取当前正在校验的文件 ko对象（用于更新UI）
        var ko_file=myUploader.ko_fileList_find(multipleUploader.ko_fileList(),file.id);
        //更新UI
        var percent=myUploader.getPercent(event.loaded/event.total)
        ko_file.percent(percent);
        ko_file.msg('文件分析中({0})'.format(percent));
    });


    //crc校验完毕
    multipleUploader.on( 'crcFileSuccess',function(file){
        //获取当前正在校验的文件 ko对象（用于更新UI）
        var ko_file=myUploader.ko_fileList_find(multipleUploader.ko_fileList(),file.id);
        //更新UI
        ko_file.percent("100%");
        ko_file.msg('开始分析是否可以秒传');
        ko_file.ext(file.ext);
        ko_file.crcCode(file.crcCode);

        //根据后缀表示一级分类
        for(var type in myUploader.resTypeList){
            if(myUploader.resTypeList[type].indexOf(file.ext) > -1){
                $.each(viewModel.resTypeList(),function(i,v){
                   if(v.code.toString() == type){
                       viewModel.resType(v);
                       window.service.changeResType(viewModel.resType(),viewModel,v);
                   }
                });
            }
        }
        //进行服务端秒传验证（文件是否已存在）
        $._getJSON('/common/transcoding/exist/{0}/{1}'.format(file.crcCode,file.ext),{},function(res){
           if(res.success && res.code == "true"){
               multipleUploader.skipFile(file);         //跳过上传,标示其为上传完毕
               multipleUploader.removeFile(file,true);  //移除队列，避免第二次无法添加
               ko_file.file=res.data;                   //存储文件信息至ko文件队列,用于保存用
               ko_file.msg('上传完毕');

           }else{
               ko_file.msg('分析完毕，可以上传');
           }
        });
    });

    //上传之前调用，只调用一次
    multipleUploader.on( 'uploadStart',function(file){
        //文件状态
        var status=file.getStatus();
        //ko对象
        var ko_file = myUploader.ko_fileList_find(multipleUploader.ko_fileList(), file.id);

        ko_file.objectId && multipleUploader.skipFile(file);
        ko_file.percent("0%");
        ko_file.isUploading(true);
        ko_file.msg("开始上传");
    });


    //向服务端发生数据之前,用来附带参数,返回false,则跳过上传
    multipleUploader.on( 'uploadBeforeSend',function(object ,data ,headers){
        var isNeedSend=true;
        data.uploadTag=object.file.crcCode;
        //$._getJSON(constParam.datadepotHost+constParam.chunkQueryHost,{uploadTag:data.uploadTag,file:object.file.name},function(){
        //   console.info(arguments);
        //    //isNeedSend=false
        //});
        return isNeedSend;
    });

    //分片上传服务端回调
    multipleUploader.on( 'uploadAccept',function(object ,ret ){
        return true;
    });

    // 文件上传过程中创建进度条实时显示。
    multipleUploader.on( 'uploadProgress', function( file, percentage ) {
        //更新进度条UI
        $.each(multipleUploader.ko_fileList(),function(i,v){
            v.id() == file.id && myUploader.updateProcess(v,percentage);
        });
    });

    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    multipleUploader.on( 'uploadSuccess', function( file,res ) {
        var _raw=unescape(res._raw),//服务端返回信息
            rst=eval("("+_raw+")"),data=null,
            ko_file=myUploader.ko_fileList_find(multipleUploader.ko_fileList(),file.id);

        ko_file.isUploading(false);

        if(rst.state && rst.data[0].id){  //上传成功
            //找到WU对象 对应ko对象,增加objectId

            ko_file.objectId=rst.data[0].id;
            //标示上传成功
            multipleUploader.skipFile(file);
            ko_file.msg("上传完毕");
            //移除队列
            multipleUploader.removeFile(file,true);
        }else{          //上传失败
            layer.msg("上传失败,请重新上传.")
            console.info(file,_raw);
        }
    });

    // 文件上传失败，现实上传出错。
    multipleUploader.on( 'uploadError', function( file ) {

    });

    /**
     * 取消图片上传
     * @param ko_file       文件信息
     * @param imgUploader   图片上传实例
     */
    multipleUploader.cancel=function(multipleUploader,ko_file){
        //var id=ko_file.id && ko_file.id();
        //if(id) {
        //    var file=imgUploader.getFile(id);
        //    file && imgUploader.cancelFile(file);
        //}
    }

    return myUploader.baseUploader(multipleUploader);
}


/**
 * 重新选择文件
 * @param uploader     ko对象
 */
myUploader.fileReChoice=function(){
    return $(this.options.pick.id+" input:file").click();
}

/**
 * 文件添加队列之前回调（对于将要过滤的文件给予提示，弥补webuploader自身不足）
 * @param file
 */
myUploader.beforeFileQueued=function(file){
    var self=this,accepts=[];
    $.each(self.options.accept,function(i,v){
        accepts.push(v.extensions);
    });
    accepts=accepts.join(",");
    return accepts.indexOf(file.ext) < 0 && (layer.alert("文件类型不支持,请选择后缀为{0}的文件".format(accepts))) && false || true;
}

/**
 * 获取进度
 * @param percent       小于0小数
 * @returns {string}
 */
myUploader.getPercent=function(percentage){
    return (percentage*100).toFixed(2)+'%';
}

/**
 * 更新ko进度条
 * @param ko_file           ko队列-项
 * @param percentage        进度
 */
myUploader.updateProcess=function(ko_file,percentage){
    var per=myUploader.getPercent(percentage);
    //200ms 更新一次，避免浏览器响应不过来
    //if(!ko_file.percent_lastUpdateTime || new Date().getTime() - ko_file.percent_lastUpdateTime > 50) {
        ko_file.percent(per);
        ko_file.percent_lastUpdateTime = new Date().getTime();
        percentage != 1 && ko_file.msg(per) || ko_file.msg('上传成功');
    //}
}

/**
 * 通过Id寻找ko文件队列中的某一项
 * @param ko_fileList       ko队列
 * @param wuId              id
 */
myUploader.ko_fileList_find=function(ko_fileList,wuId){
    var ko_file=undefined;
    $.each(ko_fileList,function(i,v){
        ko_file || v.id() == wuId && (ko_file=v );
    });
    return ko_file;
}

/**
 * 文件实体 构造
 * @param file
 */
myUploader.uploadFile=function(file){
    file||(file={});
    this.id     =   file.id||'';
    this.name   =   file.name||'';
    this.type   =   file.type||'';
    this.size   =   file.size||'';
    this.percent=   file.percent||'0%';
    this.error  =   file.error||'';
    this.img    =   file.img||'';
    this.msg    =   file.msg||'';
    this.url    =   file.url||'';
    this.isUploading=false;
    this.crcCode=   file.crcCode||'';
    this.ext    =   file.ext||'';
    this.crcFileEnd=false;
}
/**
 * Created by bzt-00 on 2015/12/16.
 */

//页面所用model
var _model={};
var viewModel=null;

//上传页面模型与初始化
$(function(){
    personalModel.active("myResource");

    viewModel=ko.mapping.fromJS(new myViewModel());
    $._getJSON('/common/section/list.html',{},function(res){
        service.ajaxCallback(res,function(){
            viewModel.sectionList(res.data)
        });
    })
    $._getJSON('/common/upload/resType',{},function(res){
        service.ajaxCallback(res,function(){
            viewModel.resTypeList(res.data);
        })
    });
    viewModel.imgUploader=new myUploader.imgUploader({pick:{id:$("#imgUploader")[0]}});

    var multipleOpt={
        pick    :   {id:$("#multipleUploader")[0]},                         //上传容器
        server  :   constParam.datadepotHost+constParam.chunkUploadHost,    //上传地址
        fromData:   constParam.fromData                                     //携带参数
    };
    viewModel.multipleUploader=new myUploader.multipleUploader(multipleOpt);
    ko.applyBindings(viewModel,$("#content")[0]);
});

/**
 * 页面模型
 */
function myViewModel(){
    var self=this;

    //文件上传实例
    self.fileUploader=null;

    //封面上传实例
    self.coverUploader=null;

    //资源类型
    _model.selectModel(self,'resType');

    //标签（资源类型子类)
    _model.selectModel(self,'resTypeChild');

    //学段
    _model.selectModel(self,'section');

    //年级
    _model.selectModel(self,'grade');

    //学科
    _model.selectModel(self,'subject');

    //教材版本
    _model.selectModel(self,'textbook');

    //章节列表
    self.chapterList=[];

    //知识点
    self.knowledegList=[];

    //学段关系
    self.sectionRelList=[];

    //学段关系
    self.gradeRelList=[];

    //学段关系
    self.subjectRelList=[];

    self.deleteList=function($index,$list,$data){
        $("#"+$data.tId+" .curSelectedNode").click();
    }

    //资源描述
    self.resDesc='';

    //分享级别
    self.shareLevel='60';
}


/**
 * 简单封装select
 * @param self ko对象-this
 * @param opt  {name:'选中值变量名'}
 */
_model.selectModel=function(self,name){
    self[name+'List']=[];       //数组
    self[name]='';              //选中项
    self[name+'Caption']='请选择';    //提示语
}

/**
 * 设置所有checkbox为选中状态
 * @param   $root       $root
 * @param   event       事件对象
 */
function change_all_item_checked($root,event){
    var $target=$(event.currentTarget),
        ele_ipt=$target.prev()[0],
        items=$target.parents("thead").next().find("input:checkbox");

    if(items.length) {
        ele_ipt.checked = !ele_ipt.checked;

        $.each(items, function (i, v) {
            v.checked = ele_ipt.checked;
        });
    }else{
        layer.alert("请添加资源");
    }
}

/**
 * 获取选中的文件
 * @param   container  checkbox容器 （dom对象 或 selector）
 * @param   uploader   webuploader实例
 * @reutrn  {list:webuploader file对象 数组 ，ids: webuploader file对象 id数组}
 */
function getChoiceFile(container,uploader){
    var checkboxs=$(container).find("input:checkbox"),wu_files=[],wu_file_ids=[],ko_files=[];//所有checkbox;被选中的file数组，被选中的file id 数组
    //填充数组
    $.each(checkboxs, function (i,v) {
        var ko_file=uploader.ko_fileList()[i],id=ko_file.id();

        v.checked && (ko_files.push(ko_file)) && ( wu_files.push(uploader.getFile(id)) && wu_file_ids.push(id)) ;
    });
    return { list:wu_files,ids:wu_file_ids,ko_fileList:ko_files};
}

/**
 * 删除选中资源
 * @param $root     ko根对象
 */
function delFile($root){
    var uploader=$root.multipleUploader,                         //插件实例
         wu_files=getChoiceFile("#multiple-file-items",uploader),//获取所有被选中的文件
        ko_fileList=uploader.ko_fileList;                        //ko文件队列

    if(!wu_files.list.length){
        layer.alert('请选中需要删除的文件');return;
    }

    //删除ko队列与插件实例队列
    $.each(ko_fileList(),function(i,v){
        v.id() == wu_files.ids[i] && ko_fileList.splice(i,1) && wu_files.list[i] && uploader.removeFile(wu_files.list[i],true);
    });
}


/**
 * 删除一个子项
 * @param $index        索引
 * @param ko_fileList   文件ko队列
 * @param uplodaer      WU插件实例
 */
function delFileItem($index,ko_fileList,uploader,$data){
    layer.confirm('是否删除该文件', 
    	function(index){
	        ko_fileList.splice($index,1);
	        var wu_file=uploader.getFile($data.id());
	        wu_file && uploader.removeFile(wu_file);
	        layer.close(index);
    	},
    	function(index){
    		layer.close(index);
    	});
}

/**
 * 上传所选资源
 * @param $root     ko根对象
 */
function uploadFile($root){
    var uploader=$root.multipleUploader,                       //插件实例
        result=getChoiceFile("#multiple-file-items",uploader), //获取所有被选中的文件
        files=result.list,                                     //文件列表
        msg="";                                                //提示信息
    if(!files.length){
        layer.alert('请选中需要上传的文件');return;
    }

    //开始上传
    $.each(files,function(i,v){
    	var status="";
    	if(v!=undefined){
    		status=v.getStatus();
    	}

        status == "progress" && ( msg="{0}正在上传,请不要重复上传");
        status == "complete" && ( msg="{0}已上传,请不要重复上传" );

        msg && layer.msg(msg.format(v.name)) || uploader.upload(v);
    });
    
    $("#tb_kp").css("display","block");
    $("#property").css("display","block");
}

/**
 * 文件保存
 * @param list      文件列表
 */
function saveFile(list){
    $.ajax({
        url:'/common/upload/saveList',
        data:{list:JSON.stringify(list)},
        type:"post",
        dataType:"json",
        success:function(data){
            if(data.success && data.code=="2000"){
                layer.msg("保存成功,马上进入我的资源...",{time:1000},function(){
                    location.href = "/space/userRes/index.html";
                });
                window.location.href = "/space/userRes/index.html";
            }else{
                layer.msg(data.msg);
            }
        }
    });
}

/**
 * 获取知识点与教材
 * @param subjectCode       学科代码
 * @param $root             根ko对象
 */
function queryVersion(subjectCode,$root) {
    subjectCode || (subjectCode=0);
    var nodeTreeSetting = {
        view: {
            selectedMulti: true,//是否能多选
            showIcon: false,
            showLine: false,
            //是否显示图标
        },
        data: {
            key: {
                id: "code",
                name: "name"
            }
        },
        async: {
            enable: true,//是否异步
            url: "/common/textbookchapter/" + subjectCode + "/list.html",//请求地址
            autoParam: ["code=pcode",],//自动提交的参数
            otherParam: {
                "textbookVerCode": $root.textbook()
            },
            type: "post"//请求方式
        },
        callback: {
            onClick: function (event, treeId, treeNode) {
                ztreeClick(treeId,treeNode,"chapterList");
            }
        }
    };
    $.fn.zTree.init($("#nodeInfo"), nodeTreeSetting);
    var knowledgeTreeSetting = {
        view: {
            selectedMulti: true,//是否能多选
            showIcon: false,
            showLine: false,
            //是否显示图标
        },
        data: {
            key: {
                id: "code",
                name: "name"
            }
        },
        async: {
            enable: true,//是否异步
            url: "/common/knowledgepoint/" + subjectCode + "/list.html",//请求地址
            autoParam: ["code=pcode", "bizTypeNo"],//自动提交的参数
            otherParam: {
                "textbookVerCode": $root.textbook()
            },
            type: "post"//请求方式
        },
        callback: {
            onClick: function (event, treeId, treeNode) {
                ztreeClick(treeId,treeNode,"knowledegList");
            }
        }
    };
    $.fn.zTree.init($("#knowledgePoint"), knowledgeTreeSetting);
}

/**
 * 取消父节点选中，刷新对应ko数组
 * @param treeId            ztree对象Id
 * @param treeNode          当前选中节点
 * @param koListName        将要更新的ko数组名称
 */
function ztreeClick(treeId,treeNode,koListName){
    //获取当前ztree对象
    var nodeZtree=$.fn.zTree.getZTreeObj(treeId);
    //当前节点dom对象
    var selectChilds=$("#"+treeNode.tId+" .curSelectedNode");
    //获取当前节点code
    var pcode=treeNode.pcode;
    //取消父节点选中状态
    if(selectChilds.length>1){                          //存在子节点被选中,取消选中当前节点
        nodeZtree.cancelSelectedNode(treeNode);
        layer.msg("您已选择更详细的节点，当前节点不会被选中.");
    }else {                                             //不存在子节点被选中，则取消选择当前节点父节点
        while (pcode) {
            var pNode=nodeZtree.getNodeByParam("code", pcode);
            nodeZtree.cancelSelectedNode(pNode);
            pcode=pNode.pcode;
        }
    }
    //刷新对应ko数组
    viewModel[koListName](nodeZtree.getSelectedNodes());
}
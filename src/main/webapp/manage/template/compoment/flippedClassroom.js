ko.components.register('bzt-tabs', {
viewModel:function (params, componentInfo) {
    var self = this;
    self._options = {
        elementName: "bzt-tabs",//标签名称
        isBlock: true,//整个选项卡是否充满整个块
        type: "",//选项卡类型（样式）
        tabsType: "",//根据类型对应的样式class
        tabsList: [],//选项卡组集合
        title: "",//整个选项卡的标题
        isContentShow: true,//是否显示选项卡对应的内容页
        tabActive: "0",//当前选中的选项卡
        tabActiveObj: ""//当前选中的选项对象
    };
    //将_options对象中所有的属性替换为，将内部参数替换为外部传入的参数对象
    ko.components.bindOptions(self, params);
    _.each(self.tabsList(), function (data) {
        dataFormat(data);
    });
    self.tabClick = function(index,data){
        self.tabActive(index);
        self.tabActiveObj(data);
    };
    if(self.isContentShow()){
        var _index_=self.tabActive();
        self.tabActive(self.tabsList()[_index_].contentId + "-tab-content-template");
    }
    /**
     *  以下定义了当前使用的选项卡类型定义的样式
     *  .tabs-n是根据n值的改变来调整选项卡标题宽度的class
     *  取值范围为（整数）1-10
     */
    var types = {
        "1": "nav-tabs",
        "2": "nav-tabs nav-tabs-line",
        "3": "nav-tabs nav-tabs-bg",
        "4": "nav-tabs-pills"
    };
    if (types[self.type()] != undefined && types[self.type()] != "") {
        self.tabsType(types[self.type()]);
    } else {
        self.tabsType(types["1"]);
    }
    if (self.isBlock()) {
        self.tabsType(self.tabsType() + " tabs-" + self.tabsList().length);
    }

    _.each(self.tabsList(), function(d){
        d.tabImg = d.tabImg ? d.tabImg : "";
        d.tabImgCss = d.tabImgCss ? d.tabImgCss : "";
        d.tabTitle = d.tabTitle ? d.tabTitle : "";
    });
    /*
     * 根据节点标签名称将子标签元素取出，放入模板中
     */
    _.each(componentInfo.templateNodes, function (node) {
        if(node.nodeName == "DIV") {
            $(componentInfo.element).find(".tab-content-template").append('<div class="tab-pane" data-bind="css:{active:tabActive() ==\''+ node.className +'\' }">'+ $(node).html() +'</div>')
        }
    });
    /**
     * 数据格式化模型，将后台与模板不一致的数据转成模板需要的数据键值
     * @param data 一条从后台查出的数据
     * @returns {*} 将处理后的数据返回
     */
    function dataFormat(data){
        if(self.dataFormat){
            _.map(self.dataFormat(), function(value, key){
                data[key] = data[key] ? data[key] : data[value];
            });
        }
    }
}
,
template:
'<!--选项卡选项标题-->\
<ul class="nav" data-bind="css:tabsType">\
    <!-- ko if: (title != undefined && title().length > 0) -->\
    <li class="title" data-bind="text:title"></li>\
    <!-- /ko -->\
    <!-- ko if: isContentShow -->\
    <!-- ko foreach: tabsList -->\
    <li data-bind="css:{active: $parent.tabActive() == (contentId+\'-tab-content-template\')},click:$parent.tabClick.bind($data,(contentId+\'-tab-content-template\'))">\
        <a>\
            <!--ko if: tabImg --><img data-bind="attr:{src:tabImg}, css:tabImgCss"><!--/ko-->\
            <!--ko text: tabTitle--><!--/ko-->\
        </a>\
    </li>\
    <!-- /ko -->\
    <!-- /ko -->\
    <!-- ko ifnot: isContentShow -->\
    <!-- ko foreach: tabsList -->\
    <li data-bind="css:{active: $parent.tabActive() == $index()},click:$parent.tabClick.bind($data,$index())">\
        <a>\
            <!--ko if: tabImg --><img data-bind="attr:{src:tabImg}, css:tabImgCss"><!--/ko-->\
            <!--ko text: tabTitle--><!--/ko-->\
        </a>\
    </li>\
    <!-- /ko -->\
    <!-- /ko -->\
</ul>\
<!-- ko if: isContentShow -->\
<!--选项卡内容-->\
<div class="tab-content tab-content-template">\
\
\
</div>\
<!-- /ko -->\
'
})
ko.components.register('bzt-filter', {
viewModel:function (params, componentInfo) {
    var self = this;
    self._options= {
        more:ko.observable(false),//是否一行显示不下
        isPills:ko.observable(false),
        isSelect : ko.observable(false), //是否显示为过滤条
        url : ko.observable(''), //URL
        selected:ko.observable(),  //选中项value
        selectedFiled:ko.observable(),  //选中项名称
        filter:ko.observable(),  //过滤条件单个对象
        filterBy:ko.observable(""),//过滤对象字段名
        filterField:ko.observable(''),//过滤条件的字段名称（子项中）
        ItemValue:ko.observable('code'), //数据项的值字段名称
        ItemName:ko.observable('name'),  //数据项的显示字段名称
        initValue:ko.observable(''), //初始值
        allName:ko.observable('全部'), //全选项配置
        allCode:ko.observable(''), //初始值
    };
    ko.components.bindOptions(self,params);
    self.filter.subscribe(function(data){  //父项节点被改变
        //setTimeout(function () {
            var filter=data;
            if(typeof data === 'object')
            {
                filter=data._id_;
                if(self.filterBy()!="")filter=data[self.filterBy()];

            }
            var line=self.lines[filter];
            if(line) {
                line._init_= true;
                self.line(line);
                initSelection();
            }
        //},1)
    });
    self.innerChange=false;
    self.selected.subscribe(function(data){
        var line=self.line();

        if(line._init_){
            line._init_=false;
        }
        else {
            line._selected = data;
        }
        //self.innerChange=true;
        //self.initValue(data[self.ItemValue()]);
    });
    self.initValue.subscribe(function(data){
        if(data===false)return;
      if(self.allData){
        self.initData=self.allData[data];
      initSelection();
      self.initData=self.allData[data];

    }
    });
    if(!self.line)self.line=ko.observableArray([]);
    if(!self.lines)self.lines={};
    self.initData=null;
    function ajaxQuery(jsonUrl,param){
        $.ajax({
            url:jsonUrl,
            data:param,
            type:"get",
            dataType:"json",
            success:function(result){  //回调操作
                if(result.success){
                    var filterField=self.filterField();  //依据过滤条件显示部分
                    var filterValue='';
                    self.allData={};//记录所有数据供下次设置初始值
                    _.each(result.data,function(data,index){
                        self.allData[data[self.ItemValue()]]=data;
                        data._id_=data[self.ItemValue()];  //数据项值名称字段的值给内部新属性
                        if(data[self.ItemValue()]==self.initValue())self.initData=data;  //当数据项值字段的值等于监控的初始值的值时，选中当前项

                        if(filterField!=='')filterValue=data[filterField];  //获取父节点的code值

                        if (!self.lines[filterValue]) {
                            var firsItem={};
                            self.lines[filterValue] = [];
                            if(self.allName()!='') {  //全选名称不为空，添加一个置前的对象把code和name属性添加给它
                                firsItem[self.ItemName()] = self.allName();
                                firsItem[self.ItemValue()]='';
                                if(self.selectedFiled()!="")firsItem[self.selectedFiled()] = self.allCode();//filterValue;//self.allCode();
                                self.lines[filterValue].push(firsItem);
                            }

                        }

                        self.lines[filterValue].push(data);  //得到分好类的的相关数组

                    });
                    var filter=self.filter();
                    if(typeof filter === 'object'){
                        var newFilter=filter._id_;
                        if(self.filterBy()!="")newFilter=filter[self.filterBy()];
                        filter=newFilter;
                    }

                    if(!!self.lines[filter])filterValue=filter;
                    self.line.subscribe(function () {
                        setTimeout(function(){
                            var element = componentInfo.element;//获取容器
                            if(!self.isPills())$(element).find(".nav").removeClass("nav-pills").addClass("nav-bordered");
                            var filterElement = $(element).find(".line");//获取节点
                            var liElement=filterElement.find("li").height();//li的高度
                            if(filterElement.height()>=2*liElement)self.more(true);
                            else
                                self.more(false);
                        });
                    });
                    self.line(self.lines[filterValue]);
                    initSelection();

                }
            },
            error:function(){
                console.log("error",arguments);
            }
        });
    };
    ajaxQuery(self.url(),"");
    function initSelection(){
        var data=self.initData; //data为选中的当前对象
        /*if(!data){
            if(self.line() && self.line().length > 0){
                data=self.line()._selected;
                if(!data)data=self.line()[0];
            }
        }*/
        if(!data)data=self.line()[0];
        if(data){
          _.each(self.line(),function(item,index){
            if(item[self.ItemValue()]==data[self.ItemValue()])self.selected(item);
          })

        }
        if(self.initData)self.initValue(false);
        self.initData=null;
    }
    /* var element = componentInfo.element;//获取容器
     var filterElement = $(element).find(".line");//获取节点
     console.log(filterElement.css("height"))
     var liElement=filterElement.find("li").css("height");//li的高度
     console.log(liElement);*/
}
,
template:
'<!-- ko if: isSelect() -->\
<!--下拉框样式-->\
\
\
<select\
        data-bind="options:line,optionsText:ItemName(),value:selected">\
</select>\
\
\
</div>\
</div>\
<!--下拉框样式结束-->\
<!-- /ko -->\
\
<!-- ko if: !isSelect() -->\
<!--过滤条-->\
<!--<div class="selector" >-->\
<div class="line">\
    <ul class="nav nav-pills" data-bind="foreach:line">\
        <li>\
            <a data-bind="css:{active:$parent.selected()==$data},click:$parent.selected.bind($data),text:$data[$parent.ItemName()]"></a>\
        </li>\
    </ul>\
</div>\
<!--</div>-->\
<!--过滤条-->\
<!-- /ko -->\
'
})
ko.components.register('bzt-menu', {
viewModel:function (params, componentInfo) {

    var self = this;
    self._options = {
        url: "",
        urlparams: "",
        dataArray: ko.observable(),
        menulist: ko.observableArray(),
        selectList: [],
        CodeValue: ko.observable(''),
        CodeType: ko.observable(''),
        ItemTitle: ko.observable(''),
        ItemCode: ko.observable(''),
        unselectStyle: ko.observable(''),
        selectStyle: ko.observable(''),
        isShowLeftIcon: ko.observable(false),
        leftIconImgUrl: ko.observable(""),
        selectfocus: function (data) {
        },
        getdataerror: function (error) {
        },
        changedata: function (data) {
        }
    };

    ko.components.bindOptions(self, params);


    function ajaxQuery(jsonUrl, param) {
        $.ajax({
            url: jsonUrl,
            data: param,
            type: "get",
            dataType: "json",
            success: function (result) {  //回调操作
                if (result.success) {
                	if(result.data==null||result.data.length<=0){
                		$.messager.alert("信息","没有查询到数据请更换查询条件试试","info");
                		return false;
                	}
                    /*var _tempdata=result.data[0].data;*/
                    var _tempdata = self.changedata(result.data);
                    _tempdata = _tempdata ? _tempdata : [];
                    if (_tempdata.length) {
                        var _temp_ = [];
                        _.each(_tempdata, function (data, index) {
                            if (!data[self.CodeValue()]) {
                                _temp_.push(data)
                            }
                        });
                        var data = recombinantArray(_tempdata, _temp_);
                        mapList(data);  //遍历数组，为每个子项创建root属性指向父项
                        mapobservable(data); //遍历数组，为每项的isSelect，__ststus__进行观察
                        self.menulist(data);
                    } else {
                        self.getdataerror(result);
                    }
                } else {
                    self.getdataerror(result);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                self.getdataerror(XMLHttpRequest, textStatus, errorThrown);
            }
        });
    };

    if (self.dataArray()) {
        var _tempdata = self.dataArray();
        if (_tempdata.length) {
            var _temp_ = [];
            _.each(_tempdata, function (data, index) {
                if (!data[self.CodeValue()]) {
                    _temp_.push(data)
                }
            });
          /*  _temp_.sort(function (a, b) {
                return parseInt(a[self.ItemCode()]) - parseInt(b[self.ItemCode()])
            });*/
            var data = recombinantArray(_tempdata, _temp_);
            mapList(data);  //遍历数组，为每个子项创建root属性指向父项
            mapobservable(data); //遍历数组，为每项的isSelect，__ststus__进行观察
            self.menulist(data);
        }
    } else {
    	var url=self.url();
    	if(!!url){
    		  ajaxQuery(self.url(), self.urlparams());	
    	}
    }
    /*__status__状态：
     * 0：全不选
     * 1：全选
     * 2：部分选中
     * */
    self.selectoption = function (root, data) {
        var templist = data;
        data.isSelect(!data.isSelect());
        if (data.isSelect()) {
            data.__status__(1);
        } else {
            data.__status__(0);
        }
        if (templist.data) {
            recursion(templist.isSelect(), templist.data);
        }
        ;

        if (templist.root) {
            rootlist(templist.isSelect(), templist, templist.root);
        }
        ;


        self.selectList([]);
        selectOption(templist);
        root.selectfocus(self.selectList())
    }

    function recombinantArray(datalist, parent) {
        var _root_ = parent;
     /*   _root_.sort(function (a, b) {
            return parseInt(a[self.ItemCode()]) - parseInt(b[self.ItemCode()])
        });*/
        _.each(_root_, function (data, index) {
            var _templist_ = [];
            _.each(datalist, function (item, i) {
                if (data[self.ItemCode()] == item[self.CodeValue()]) {
                    _templist_.push(item);
                    _root_[index].data = _templist_;
                }
            });
            if (_root_[index].data) {
                recombinantArray(datalist, _root_[index].data);
            }
        })
        return _root_;
    }

    function mapList(list) {
        for (var i = 0; i < list.length; i++) {
            if (list[i].data) {
                list[i].data.root = list;
                for (var j = 0; j < list[i].data.length; j++) {
                    list[i].data[j].root = list[i];
                }
                mapList(list[i].data)
            }
        }
    }

    function mapobservable(data) {
        for (var i = 0; i < data.length; i++) {
            data[i].__status__ = ko.observable(0);
            data[i].isSelect = ko.observable(false);
            if (data[i].data) {
                mapobservable(data[i].data);
            }
        }
    }


    function selectOption(childdata) {
        var temp = {};
        _.extend(temp, childdata);
     /*   temp.data = "";
        temp.isSelect = "";
        temp.__status__ = "";*/
        self.selectList.unshift(temp);
        var rootdata = temp.root;
        if (rootdata) {
            selectOption(rootdata);
            temp.root = "";
        }
    }


    /**
     * 向下递归遍历子项
     * */
    function recursion(isselect, temp) {
        var __status__;
        if (isselect) {
            __status__ = 1;
        } else {
            __status__ = 0;
        }
        for (var i = 0; i < temp.length; i++) {
            if (temp[i].data) {
                recursion(isselect, temp[i].data);
                temp[i].isSelect(isselect);
                temp[i].__status__(__status__);
            } else {
                temp[i].isSelect(isselect);
                temp[i].__status__(__status__);
            }
        }
    }


    /**
     * 向上递归遍历父项
     * */
    function rootlist(isselect, data, list) {
        var __status__ = 1;
        var __count__ = 0;
        var __num__ = 0;
        if (list.data) {
            list.data.forEach(function (item) {
                if (item.isSelect() && item.__status__() == 1) {
                    __count__++;
                } else if (!item.isSelect() && item.__status__() == 0) {
                    __num__++;
                }
            });
            if (__count__ == list.data.length) {
                __status__ = 1;
            } else if (__num__ == list.data.length) {
                __status__ = 0;
            } else {
                __status__ = 2;
            }
        }
        ;
        if (__status__ == 1) {
            list.__status__(1);
            list.isSelect(isselect);
        } else if (__status__ == 2) {
            list.__status__(2);
        } else {
            list.__status__(0);
            list.isSelect(isselect);
        }
        ;

        if (list.root) {
            var temp = list.root;
            for (var i = 0; i < temp.data.length; i++) {
                if (temp.data[i].root) {
                    rootlist(isselect, temp.data[i], temp.data[i].root);
                } else {
                    if (isselect) {
                        temp.data[i].__status__(1);
                    } else {
                        temp.data[i].__status__(0);
                    }
                    temp.data[i].isSelect(isselect);
                }
            }
        }
    }
},
template:
'<ul data-bind="foreach:menulist">\
    <li onmouseenter="mouseenter(this,event)" >\
        <label  data-bind="attr:{title:$data[$parents[$parents.length-$parent.topClass()].ItemTitle()]},click:$parent.selectoption.bind($data,$parents[$parents.length-$parent.topClass()])">\
            <!-- <input type="checkbox" data-bind="checked:isSelect">-->\
            <!--<span data-bind="text:__status__"></span>-->\
            <span data-bind="text:$data[$parents[$parents.length-$parent.topClass()].ItemTitle()]"></span>\
            <!--<span data-bind="text:$data[$parents[$parents.length-$parent.topClass()].ItemCode()]"></span>-->\
            <!--ko if:$parents[$parents.length-$parent.topClass()].isShowLeftIcon-->\
            <!--ko if:$data.data-->\
            <img data-bind="attr:{src:$parents[$parents.length-$parent.topClass()].leftIconImgUrl}">\
            <!--/ko-->\
            <!--/ko-->\
        </label>\
        <!--ko if:$data.data-->\
        <div id="menu"\
             data-bind="component: { name: \'bzt-menu\', params: {options:{menulist:$data.data,topClass:$parent.topClass()}}}"></div>\
        <!--/ko-->\
    </li>\
</ul>\
\
\
\
'
})
ko.components.register('bzt-list-content', {
viewModel:function (params, componentInfo) {

    var self = this;
    self._options = {
        elementName: "bzt-list-content",//标签名称
        dataList: [],//数据集合
        urlParam: {}//发送ajax的参数对象

    };
    //将_options对象中所有的属性替换为，将内部参数替换为外部传入的参数对象
    ko.components.bindOptions(self, params);
    //将tags对象中所有属性添加到数据数组
    if(self.tags && self.tags())bzt.bindChildComponent(self.tags(), self.dataList());

    /*
     * 是否开启多选、全选复选框，并将其所有相关属性与操作放入外界传入的multipleControl的ko对象中
     */
    if (self.isMultiple && self.isMultiple()) {
        if (self.multipleControl) {
            _.each(self.dataList(), function (data) {
                data.isSelect = data.isSelect ? data.isSelect : ko.observable(false);
                data.isSelect.subscribe(function (value) {
                    self.multipleControl().selections.remove(data);
                    if (value)self.multipleControl().selections.push(data);
                });
            });
            self.multipleControl({
                selections: ko.observableArray([]),
                selectAll: ko.pureComputed({
                    read: function () {
                        return self.multipleControl().selections().length == self.dataList().length;
                    },
                    write: function (data) {
                        _.each(self.dataList(), function (value) {
                            value.isSelect(data);
                        });
                    },
                    owner: this
                }),
                removeSelected: function () {
                    var prevLength = self.dataList().length;
                    var selectedLength = self.multipleControl().selections().length;
                    self.dataList.remove(function (item) {
                        return item.isSelect();
                    });
                    if (self.dataList().length == (prevLength - selectedLength)) {
                        return true;
                    } else {
                        return false;
                    }
                }

            });

        } else {
            layer.alert("你开启了多选模式却没有添加multipleControl的ko对象，外界将无法访问到，请检查参数设置！")
        }
    }
    /*
     * 监控dataList，改变时将所有在内部添加的属性重新添加
     */
    self.dataList.subscribe(function (data) {
        if(self.tags && self.tags())bzt.bindChildComponent(self.tags(), self.dataList());
        if(self.isMultiple && self.isMultiple() && self.multipleControl) {
            self.multipleControl().selections([]);
            _.each(self.dataList(), function (value) {
                value.isSelect = value.isSelect ? value.isSelect : ko.observable(false);
                value.isSelect.subscribe(function (data) {
                    self.multipleControl().selections.remove(value);
                    if (data)self.multipleControl().selections.push(value);
                });
                if (value.isSelect() == true) {
                    self.multipleControl().selections.push(value);
                }
            });
        }
    });



    //匹配{value}形式的正则
    var reg = /\{[_\(\)\.0-9a-zA-Z\u4e00-\u9fa5]+\}/g;

    var rows;//数据模板整体行参数设置（数量、行高）
    var cols;//数据模板整体列参数设置（数量、列宽，auto自适应
    var tableSize;//记录行、列整体位置剩余的数组，tableSize[行]=列
    //记录所有html模板的对象
    var templates = {};//管理整个模板的对象

    /*
     * 根据节点标签名称将标签元素赋值到templates对象的不同属性上，以便后续使用
     */
    _.each(componentInfo.templateNodes, function (node) {
        if ($(node).hasClass("item-template")) {
            templates.listTemplate = {
                name: "listTemplate",
                node: node
            };//列表模板（参与循环）
        }
        if ($(node).hasClass("first-item")) {
            templates.firstItem = {
                name: "firstItem",
                node: node
            };//第一个元素（不参与循环）
        }
        if ($(node).hasClass("last-item")) {
            templates.lastItem = {
                name: "lastItem",
                node: node
            };//最后一个元素
        }
    });

    var rowTemplate = document.createElement("tr");
    var prevRowElement;
    _.map(templates, function (value, key) {

        templates[key] = appendItemToTemplate(value.name, value.node);
    });

    var element = document.createElement("div");
    _.map(templates, function (value, key) {
        if (value != "") {
            if (key == "listTemplate") {
                $(element).append("<!--ko foreach: dataList-->");
                $(element).append(value);
                $(element).append("<!--/ko-->");
            } else {
                $(element).append(value);
            }
        }
    });
    if (self.isPagination && self.isPagination()) {
        if (self.paginationClass) {
            $(element).append('<div class="' + self.paginationClass() + '" data-bind="component: { name: \'bzt-pagination\', params: {options:paginationOptions }}"></div>');
        } else {
            $(element).append('<div data-bind="component: { name: \'bzt-pagination\', params: {options:paginationOptions }}"></div>');
        }
    }
    $(componentInfo.element).html($(element).html());

    /**
     * 把外界传入的数据格式转换成html模板的方法
     *
     * @param dataTemplate 传入的数据模板(参数)
     * @returns {Element} 根据数据模板生成html元素模板并返回
     */
    function appendItemToTemplate(templateName, dataTemplate) {
        rows = $(dataTemplate).attr("row").split(/,/);
        rows = rows ? rows : [$(dataTemplate).attr("row")];
        cols = $(dataTemplate).attr("col").split(/,/);
        cols = cols ? cols : [$(dataTemplate).attr("col")];
        tableSize = new Array(rows.length);
        for (var i = 0; i < rows.length; i++) {
            tableSize[i] = cols.length;
        }
        //表格列宽布局，一个空行记录所有的列宽
        var colLayout = '<thead><tr class="col-layout">';
        /*
         * 是否开启多选、全选复选框
         */
        if (templateName == "listTemplate" && self.isMultiple && self.isMultiple()) {
            colLayout += '<td width="20"></td>';
        }
        _.each(cols, function (c) {
            c = parseInt(c);
            if (c) {
                colLayout += '<td width="' + c + '"></td>';
            } else {
                colLayout += '<td></td>';
            }
        });
        colLayout += '</tr></thead>';

        var tableClass = $(dataTemplate).attr("item-class") ? $(dataTemplate).attr("item-class") : "";
        //开始创建模板
        var template = document.createElement("div");
        var tableTemplate = document.createElement("table");
        $(template).addClass(tableClass);
        var rowIndex = 0;
        if (dataTemplate) {
            $(tableTemplate).append(colLayout);
            _.each(dataTemplate.children, function (item) {
                rowIndex = appendRowToTemplate(templateName, tableTemplate, rowIndex);
                if ($(item).attr("extra") == "true" || $(dataTemplate).attr("extra") === true) {
                    $(template).append($(item).html());
                } else {
                    var itemTemplateObj = itemToTemplate(item, rowIndex);
                    if (!itemTemplateObj.isAppendToPrev) {
                        $(rowTemplate).append(itemTemplateObj.cellTemplate);
                    }
                }
            });
            tableTemplate.appendChild(rowTemplate);
        }
        template.appendChild(tableTemplate);
        rowTemplate = document.createElement("tr");
        return template;
    }

    /**
     * 把一行插入html模板的方法
     *
     * @param tableTemplate 要生成的模板
     * @param rowIndex 当前行
     * @returns {*} 返回计算后当前行数
     */
    function appendRowToTemplate(templateName, tableTemplate, rowIndex) {
        var rowHeight = parseInt(rows[rowIndex]);
        if (rowHeight)$(rowTemplate).attr("height", rowHeight);
        /*
         * 是否开启多选、全选复选框
         */
        if (self.isMultiple && self.isMultiple()) {
            if (templateName == "listTemplate" && !rowTemplate.hasChildNodes()) {
                $(rowTemplate).append('<td rowspan="' + tableSize.length + '"><input type="checkbox" data-bind="checked:isSelect"></td>')
            }
        }
        if (tableSize[rowIndex] == 0) {
            tableTemplate.appendChild(rowTemplate);
            rowIndex++;
            prevRowElement = rowTemplate;
            rowTemplate = document.createElement("tr");
        }
        return rowIndex;
    }

    /**
     * 把数据格式的一个item转换成html模板的一个单元格的方法
     *
     * @param item 一个单元格的数据格式
     * @param rowIndex 当前行
     * @returns {*} 一个单元格的模板对象，记录了是否跟随上一格和当前单元格的htmlString
     */
    function itemToTemplate(item, rowIndex) {
        var $item = $(item);
        var rowspan = $item.attr("rows") ? parseInt($item.attr("rows")) : 1;
        var colspan = ($item.attr("cols") != undefined) ? parseInt($item.attr("cols")) : 1;
        if (!rowspan && !colspan) {
            alert("rows/cols设置有误，请检查数据格式！");
            return "";
        }
        var str;//正则匹配可能使用
        var cellClass = $item.attr("cell-class") ? $item.attr("cell-class") : "";
        var itemClass = $item.attr("class") ? $item.attr("class") : "";
        var itemImg = $item.attr("img") ? $item.attr("img") : "";
        var imgClass = $item.attr("img-class") ? $item.attr("img-class") : "";
        var itemIcon = $item.attr("icon") ? $item.attr("icon") : "";
        var itemTitle = $item.attr("title") ? $item.attr("title") : "";
        var itemText = $item.attr("text") ? $item.attr("text") : "";
        var itemValue = $item.attr("value") ? $item.attr("value") : "";
        var itemEvent = $item.attr("event") ? $item.attr("event") : "";
        var itemLink = $item.attr("link") ? $item.attr("link") : "";
        var itemLinkHref = $item.attr("link-href") ? $item.attr("link-href") : "";
        var itemLinkTarget = $item.attr("link-target") ? $item.attr("link-target") : "_self";
        var linkTitle = (($item.attr("link-title") == "true") || ($item.attr("link-title") === true)) ? true : false;
        var cellTemplateObj = {
            isAppendToPrev: false,
            cellTemplate: '<div class="' + itemClass + '">'
        };
        if (itemEvent != "") {
            cellTemplateObj.cellTemplate = '<div class="' + itemClass + '" data-bind="event:{' + itemEvent + '}">'
        }
        if (itemImg != "") {
            str = itemImg.match(reg);
            if (str) {
                itemImg = "'" + itemImg.substring(0, itemImg.indexOf(str[0])) + "'+" + str[0].substring(1, str[0].length - 1) + "+'" + itemImg.substring(itemImg.indexOf(str[0]) + str[0].length, itemImg.length) + "'";
                cellTemplateObj.cellTemplate += '<img class="' + imgClass + '" data-bind="attr:{src:' + itemImg + '}"/>';
            } else {
                cellTemplateObj.cellTemplate += '<img class="' + imgClass + '" data-bind="attr:{src:' + itemImg + '}"/>';
            }
        }
        if (itemIcon != "" && itemTitle != "") {
            if (reg.test(itemIcon)) {
                itemIcon = itemIcon.replace(reg, function (str) {
                    return str.substring(1, str.length - 1);
                });
            } else {
                itemIcon = "\'" + itemIcon + "\'";
            }
            if (reg.test(itemTitle)) {
                itemTitle = itemTitle.replace(reg, function (str) {
                    return str.substring(1, str.length - 1);
                });
            } else {
                itemTitle = "\'" + itemTitle + "\'";
            }
            cellTemplateObj.cellTemplate += '<i data-bind="css:' + itemIcon + ',attr:{title:' + itemTitle + '}"></i>';
        }
        if (itemIcon != "" && itemTitle == "") {
            if (reg.test(itemIcon)) {
                itemIcon = itemIcon.replace(reg, function (str) {
                    return str.substring(1, str.length - 1);
                });
            } else {
                itemIcon = "\'" + itemIcon + "\'";
            }
            cellTemplateObj.cellTemplate += '<i data-bind="css:' + itemIcon + '"></i>';
        }
        if (itemTitle != "" && itemIcon == "") {
            itemTitle = itemTitle.replace(reg, function (str) {
                return "<!--ko text:" + str.substring(1, str.length - 1) + "--><!--/ko-->";
            });
            cellTemplateObj.cellTemplate += '<span class="item-title">' + itemTitle + '</span>';
        }
        if (itemLink != "") {
            str = itemLink.match(reg);
            if (str) {
                itemLink = "'" + itemLink.substring(0, itemLink.indexOf(str[0])) + "'+" + str[0].substring(1, str[0].length - 1) + "+'" + itemLink.substring(itemLink.indexOf(str[0]) + str[0].length, itemLink.length) + "'";
            }

            if (itemLinkHref != "") {
                str = itemLinkHref.match(reg);
                if (str) {
                    itemLinkHref = "'" + itemLinkHref.substring(0, itemLinkHref.indexOf(str[0])) + "'+" + str[0].substring(1, str[0].length - 1) + "+'" + itemLinkHref.substring(itemLinkHref.indexOf(str[0]) + str[0].length, itemLinkHref.length) + "'";
                }
            }
            if (linkTitle) {
                cellTemplateObj.cellTemplate += '<a class="item-link" data-bind="attr:{href:' + itemLinkHref + ',title:' + itemLink + ',target:\'' + itemLinkTarget+ '\'},text:' + itemLink + '"></a>';
            } else {
                cellTemplateObj.cellTemplate += '<a class="item-link" data-bind="attr:{href:' + itemLinkHref + ',target:\'' + itemLinkTarget+ '\'},text:' + itemLink + '"></a>';
            }
        }
        if (itemText != "") {
            itemText = itemText.replace(reg, function (str) {
                return "<!--ko text:" + str.substring(1, str.length - 1) + "--><!--/ko-->";
            });
            cellTemplateObj.cellTemplate += '<span class="item-text">' + itemText + '</span>';
        }
        if (itemValue != "") {
            itemValue = itemValue.replace(reg, function (str) {
                return '<span class="item-value" data-bind="text:' + str.substring(1, str.length - 1) + '"></span>';
            });
            cellTemplateObj.cellTemplate += '<span class="item-value-text">' + itemValue + '</span>';
        }
        cellTemplateObj.cellTemplate += '</div>';
        /*
         * 将所有子节点内容填入单元格
         */
        if (item.hasChildNodes()) {
            cellTemplateObj.cellTemplate += $item.html();
        }
        if (tableSize[rowIndex] > 0) {
            colspan = (colspan <= tableSize[rowIndex]) ? colspan : tableSize[rowIndex];
        }
        if (tableSize[rowIndex] == cols.length && rowIndex == 0) {
            colspan = colspan ? colspan : 1;
        }
        for (var i = 0; i < rowspan; i++) {
            if (tableSize[rowIndex + i]) {
                tableSize[rowIndex + i] = tableSize[rowIndex + i] - colspan;
            }
        }

        if (colspan != 0) {
            if (cellClass != "") {
                cellTemplateObj.cellTemplate = '<td class="' + cellClass + '" colspan="' + colspan + '" rowspan="' + rowspan + '">' + cellTemplateObj.cellTemplate + '</td>';
            } else {
                cellTemplateObj.cellTemplate = '<td colspan="' + colspan + '" rowspan="' + rowspan + '">' + cellTemplateObj.cellTemplate + '</td>';
            }
        } else {
            cellTemplateObj.isAppendToPrev = true;
            if ((tableSize[rowIndex] == cols.length) || (tableSize[rowIndex] == undefined)) {
                $(prevRowElement).find("td:last").append(cellTemplateObj.cellTemplate);
            } else {
                $(rowTemplate).find("td:last").append(cellTemplateObj.cellTemplate);
            }
        }
        return cellTemplateObj;
    }
}
,
template:
'"<!--ko foreach: dataList()-->\
<DIV class=data-list-hover>\
    <DIV class=data-list-hover-btn data-bind="component: { name: \'bzt-drop-list\', params: {options:dropListOptions}}">\
        <BUTTON class="btn btn-focus btn-sm" type=submit>��������</BUTTON>\
    </DIV>\
    <TABLE>\
        <THEAD>\
        <TR class=col-layout>\
            <TD width=20></TD>\
            <TD width=120></TD>\
            <TD width=150></TD>\
            <TD width=150></TD>\
            <TD></TD>\
            <TD></TD>\
        </TR>\
        </THEAD>\
        <TR height=30>\
            <TD rowSpan=4><INPUT type=checkbox data-bind="checked:isSelect"></TD>\
            <TD rowSpan=2 colSpan=1>\
                <DIV><I data-bind="css:\'fa fa-file-o\'"></I></DIV>\
                <DIV class=title><SPAN class=item-title><!--ko text:title--><!--/ko--></SPAN></DIV>\
            </TD>\
            <TD rowSpan=1 colSpan=1>\
                <DIV><SPAN class=item-title>�ϴ�ʱ�䣺</SPAN><SPAN class=item-text><!--ko text:uploadTime--><!--/ko--></SPAN>\
                </DIV>\
            </TD>\
            <TD rowSpan=1 colSpan=1>\
                <DIV><I data-bind="css:\'{icon}\',attr:{title:\'������2\'}"></I><SPAN class=item-text><!--ko text:uploadTime--><!--/ko--></SPAN>\
                </DIV>\
            </TD>\
            <TD rowSpan=1 colSpan=1>\
                <DIV><I data-bind="css:\'fa fa-eye\',attr:{title:\'������3\'}"></I><SPAN class=item-text><!--ko text:uploadTime--><!--/ko--></SPAN>\
                </DIV>\
            </TD>\
            <TD rowSpan=1 colSpan=1>\
                <DIV><I data-bind="css:\'fa fa-eye\',attr:{title:\'������4\'}"></I><SPAN class=item-text><!--ko text:uploadTime--><!--/ko--></SPAN>\
                </DIV>\
            </TD>\
        </TR>\
        <TR>\
            <TD rowSpan=1 colSpan=3>\
                <DIV></DIV>\
                <DIV class=bzt-progressbar\
                     data-bind="component: { name: \'bzt-progressbar\', params: {options:process}}"></DIV>\
            </TD>\
        </TR>\
    </TABLE>\
</DIV><!--/ko-->\
<DIV>\
    <TABLE>\
        <THEAD>\
        <TR class=col-layout>\
            <TD width=120></TD>\
            <TD></TD>\
            <TD width=100></TD>\
            <TD></TD>\
            <TD></TD>\
        </TR>\
        </THEAD>\
        <TR height=30>\
            <TD rowSpan=2 colSpan=1>\
                <DIV><I data-bind="css:\'fa fa-file-video-o title-lg\'"></I></DIV>\
            </TD>\
            <TD rowSpan=1 colSpan=4>\
                <DIV class=title><SPAN class=item-title>��һ������</SPAN></DIV>\
            </TD>\
        </TR>\
        <TR height=20>\
            <TD rowSpan=1 colSpan=1>\
                <DIV class=pull-left><SPAN class=item-title>�ϴ�ʱ�䣺</SPAN><SPAN class=item-text><!--ko text:uploadTime--><!--/ko--></SPAN>\
                </DIV>\
                <DIV class=pull-left><I data-bind="css:\'fa fa-eye\',attr:{title:\'������\'}"></I><SPAN class=item-value-text>������(<SPAN\
                        class=item-value data-bind="text:uploadTime"></SPAN>)</SPAN></DIV>\
                <DIV class=pull-left><I data-bind="css:\'fa fa-eye\',attr:{title:\'������2\'}"></I><SPAN class=item-text><!--ko text:uploadTime--><!--/ko--></SPAN>\
                </DIV>\
                <DIV class=pull-left data-bind="event:{click:function(){alert(4)}}"><I\
                        data-bind="css:\'fa fa-eye\',attr:{title:\'������3\'}"></I><SPAN class=item-text><!--ko text:uploadTime--><!--/ko--></SPAN>\
                </DIV>\
            </TD>\
            <TD rowSpan=1 colSpan=1>\
                <DIV><I data-bind="css:\'fa fa-eye\',attr:{title:\'������4\'}"></I><SPAN class=item-text><!--ko text:uploadTime--><!--/ko--></SPAN>\
                </DIV>\
            </TD>\
        </TR>\
    </TABLE>\
</DIV>\
<DIV>\
    <TABLE>\
        <THEAD>\
        <TR class=col-layout>\
            <TD width=50></TD>\
            <TD width=50></TD>\
            <TD width=40></TD>\
        </TR>\
        </THEAD>\
        <TR height=80>\
            <TD class=res-list-img-cell rowSpan=1 colSpan=3>\
                <DIV class=res-list-img><IMG data-bind="attr:{src:\'\'+resImg()+\'\'}"></DIV>\
                <DIV class=res-list-img-hover data-bind="event:{click:mediaPlayer.bind($data,id())}"><I\
                        data-bind="css:\'fa fa-play-circle res-list-img-hover-icon\'"></I></DIV>\
            </TD>\
        </TR>\
        <TR height=20>\
            <TD rowSpan=1 colSpan=3>\
                <DIV class=title><A class=item-link\
                                    data-bind="attr:{href:\'http://192.168.0.111:80/media/\'+id()+\'\',title:\'\'+resTitle()+\'\'},text:\'\'+resTitle()+\'\'"></A>\
                </DIV>\
            </TD>\
        </TR>\
        <TR height=20>\
            <TD rowSpan=1 colSpan=1>\
                <DIV><I data-bind="css:\'fa fa-eye\',attr:{title:\'������\'}"></I><SPAN class=item-text><!--ko text:browseCount--><!--/ko--></SPAN>\
                </DIV>\
            </TD>\
            <TD rowSpan=1 colSpan=1>\
                <DIV><I data-bind="css:\'fa fa-thumbs-o-up\',attr:{title:\'������\'}"></I><SPAN class=item-text><!--ko text:goodCount--><!--/ko--></SPAN>\
                </DIV>\
            </TD>\
            <TD rowSpan=1 colSpan=1>\
                <DIV><I data-bind="css:\'fa fa-share\',attr:{title:\'������\'}"></I><SPAN class=item-text><!--ko text:shareCount--><!--/ko--></SPAN>\
                </DIV>\
            </TD>\
        </TR>\
    </TABLE>\
</DIV>\
<DIV class=pull-left data-bind="component: { name: \'bzt-pagination\', params: {options:paginationOptions }}"></DIV>"'
})
ko.components.register('bzt-drop-list', {
viewModel:function (params, componentInfo) {
    var self = this;
    self._options = {
        top:0,
        elementName: "bzt-drop-list",//标签名称
        showListEvent: ko.observable("hover"),
        isShowList: ko.observable(true),
        //所有被选中的选项(checkbox、radio)
        selections: ko.observableArray([]),
        css: ko.observable(""),
        dataList: ko.observableArray([]),
        radioSelected: ko.observable("")
    };
    //将_options对象中所有的属性替换为，将内部参数替换为外部传入的参数对象
    ko.components.bindOptions(self, params);
    self.controlClick = function () {};

    _.each(componentInfo.templateNodes, function (node) {
        $(componentInfo.element).find("._drop-list-control").append(node);
    });
    self.radioSelected.subscribe(function () {
        _.each(self.dataList(), function (item) {
            if (item.icon == "radio" && item.text == self.radioSelected()) {
                self.selections.radioSelected = item.data;
                return;
            }
        });
    });
    _.each(self.dataList(), function (item) {
        item.icon = item.icon ? item.icon : ko.observable("");
        item.text = item.text ? item.text : ko.observable("");
        item.data = item.data ? item.data : {};
        if (item.icon == "checkbox") {
            item.isSelected = item.isSelected ? item.isSelected : ko.observable(false);
            item.isSelected.subscribe(function (that) {
                self.selections.remove(item.data);
                if (that)self.selections.push(item.data);
            });
        }
        item.click = item.click ? item.click : function () {};
    });
    if (self.showListEvent() == "hover") {
        self.isShowList(true);
        $("._drop-list-box").addClass("_drop-list-box-hover")
    }
    if (self.showListEvent() == "click") {
        $("._drop-list-box").click(function(event) {
            var e = window.event || event;
            if (e.stopPropagation) {
                e.stopPropagation();
            } else {
                e.cancelBubble = true;
            }
        });
        self.isShowList(false);
        self.controlClick = function (event) {
            self.isShowList(true);
            var e = window.event || event;
            if (e.stopPropagation) {
                e.stopPropagation();
            } else {
                e.cancelBubble = true;
            }
        };
        $("body").click(function() {
           self.isShowList(false);
        })
    }
    if (self.showListEvent() == "value") {

    }
},
template:
'<div class="_drop-list-box">\
    <div class="_drop-list-control" data-bind="click:controlClick">\
\
\
    </div>\
    <ul class="_drop-list-display" data-bind="foreach:dataList, css:css, visible:isShowList, style:{top:top}">\
        <li>\
            <!-- ko if:icon=="checkbox"-->\
            <label>\
                <input type="checkbox" data-bind="checked:isSelected">\
                <span data-bind="text:text"></span>\
            </label>\
            <!-- /ko-->\
            <!-- ko if:icon=="radio"-->\
            <label>\
                <input type="radio" name="_drop-list-display-radio" data-bind="value:text,checked:$parent.radioSelected">\
                <span data-bind="text:text"></span>\
            </label>\
            <!-- /ko-->\
            <div data-bind="click:click.bind($data, $parents[0], $parents[1], $parents[2])">\
                <!-- ko ifnot:(icon=="checkbox" || icon=="radio")-->\
                <!-- ko if:(/\./.test(icon))-->\
                <img class="drop-list-display-img" data-bind="attr:{src:icon}">\
                <!-- /ko-->\
                <!-- ko ifnot:(/\./.test(icon))-->\
                <i data-bind="css:icon"></i>\
                <!-- /ko-->\
                <span data-bind="text:text"></span>\
                <!-- /ko-->\
            </div>\
        </li>\
    </ul>\
</div>\
'
})
ko.components.register('bzt-pagination', {
viewModel:function (params) {
    var self = this;
    self._options = {
        elementName:"pagination",
        pageNo: ko.observable(1),//页码
        totalPageCount: ko.observable(15),//总页数
        howMany: ko.observable(10),//一共显示多少个页码
        inputPageNo: ko.observable(""),//“跳转到”输入框输入内容
        jumpToTop: ko.observable(false),//改变页码后是否返回到顶部
        inputShow : ko.observable(true),
        onChange: function (pageNo) {//改编页码触发事件
        }
    };
    ko.components.bindOptions(self, params);
    self.toPageNo=function (pageNo) {//点击页码时的函数
        if (pageNo != "" && pageNo != undefined) {
            if (/[0123456789]+/.test(pageNo)) {
                pageNo = parseInt(pageNo);
                if (pageNo > 0 && pageNo <= self.totalPageCount()) {
                    pageNo = parseInt(pageNo);
                    self.inputPageNo("");
                } else if(pageNo == 0) {
                    pageNo = 1;
                    self.inputPageNo("");
                } else if(pageNo < 0) {
                    self.inputPageNo("");
                    layer.msg("请输入正确的页码");
                    return;
                } else if(pageNo > self.totalPageCount()){
                    pageNo = self.totalPageCount();
                    self.inputPageNo("");
                }
            } else {
                self.inputPageNo("");
                layer.msg("请输入正确的页码");
                return;

            }
        }
        if (self.jumpToTop()) {
            window.scrollTo(0, 0);
        }
        self.onChange(pageNo);
    };
    self.submitPageNo = function(e) {
        e = e || window.event;
        var k = e.keyCode || e.which;
        if(k == 13) {
            self.toPageNo(self.inputPageNo())
        }
    }

},
template:
'<ul class="pagination" data-bind="visible:(totalPageCount() > 1)">\
    <li data-bind="click:toPageNo.bind($data,1)"><a class="page-link" href=\'javascript:void(0);\'>首页</a></li>\
    <!-- 上一页 -->\
    <li data-bind="visible:pageNo()!=1,click:toPageNo.bind($data,pageNo()-1)">\
        <a href="javascript:void(0);">\
            <i class="fa fa-chevron-left"></i>\
        </a>\
    </li>\
    <!-- ko foreach: ko.utils.range(1, totalPageCount) -->\
    <li data-bind=" click:$parent.toPageNo.bind($data), css: { active: $parent.pageNo()==$data },visible:($data>=$parent.pageNo() && $data<($parent.pageNo()+$parent.howMany())) || ($parent.howMany()>($parent.totalPageCount()-$parent.pageNo()) && $data > ($parent.totalPageCount()-$parent.howMany()))">\
        <a href="javascript:void(0);" data-bind="text: $data"></a>\
    </li>\
    <!-- /ko -->\
    <!-- 下一页 -->\
    <li data-bind="visible:pageNo()!=totalPageCount(),click:toPageNo.bind($data,pageNo()+1)">\
        <a href="javascript:void(0);">\
            <i class="fa fa-chevron-right"></i>\
        </a>\
    </li>\
    <li data-bind="click:toPageNo.bind($data,totalPageCount())"><a class="page-link" href=\'javascript:void(0);\'>末页</a></li>\
    <!-- ko if:inputShow-->\
    <li>\
        <b>共<!-- ko text:totalPageCount --><!-- /ko -->页，转到第</b>\
        <input type="text" class="redirect-page" data-bind="valueUpdate:\'afterkeydown\',value:inputPageNo, event:{keyup:submitPageNo.bind(event)}" oncontextmenu="return false">页\
    </li>\
    <li data-bind="click:toPageNo.bind($data,inputPageNo())"><a href="javascript:void(0);"><i class="fa fa-arrow-right"></i></a></li>\
    <!-- /ko -->\
</ul>\
\
'
})

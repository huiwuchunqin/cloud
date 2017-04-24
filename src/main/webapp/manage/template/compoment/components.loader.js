/**
 * 自定义组件的加载器，覆盖了ko默认的模块和
 */
(function (ko,underscore) {
    var templateFromUrlLoader = {
        getConfig: function (name, callback) {
            ko.components.defaultLoader.getConfig(name, callback);
        },
        'loadComponent': function (componentName, config, callback) {
            ko.components.defaultLoader.loadComponent(componentName, config, callback);
        },
        loadTemplate: function (name, templateConfig, callback) {
            if (templateConfig.fromUrl) {
                // Uses jQuery's ajax facility to load the markup from a file
                var fullUrl = templateConfig.fromUrl;
                $.get(fullUrl, function (markupString) {
                    try{
                        ko.components.defaultLoader.loadTemplate(name, markupString, callback);
                    }catch(e)
                    {
                        console.error("加载Template:"+ name +",url:" + fromUrl + " 出现异常！！！！！！！");
                        throw e;
                    }
                });
            } else {
                callback(null);
            }
        },
        loadViewModel: function (name, viewModelConfig, callback) {
            if (viewModelConfig.fromUrl) {
                var fromUrl = viewModelConfig.fromUrl;
                $.ajax({
                    url: fromUrl,
                    async: true,
                    dataType: "text",
                    success: function (viewModelString) {
                        try{
                            var viewModelConstructor = eval("(" + viewModelString + ")");
                            //兼容ie8，hack
                            if (!viewModelConstructor) {
                                viewModelConstructor = eval("(function() {return " + viewModelString + ";})()");
                            }
                            if(!ko.bzt)ko.bzt={};
                            ko.bzt[name.replace(/-/g,'_')]=viewModelConstructor;
                            ko.components.defaultLoader.loadViewModel(name, viewModelConstructor, callback);
                        }catch(e)
                        {
                            console.error("加载ViewModel:"+ name +",url:" + fromUrl + " 出现异常！！！！！！！");
                            throw e;
                        }
                    },
                    error: function (error) {
                        console.error(error);
                    }
                });

            } else {
                // Unrecognized config format. Let another loader handle it.
                callback(null);
            }
        }
    };

    // Register it
    ko.components.loaders.unshift(templateFromUrlLoader);

    //ko插件，实例绑定代码
    ko.components.bindInstance = function (model, params) {
        //取出instance绑定对象
        var attachObject = params.options || params || {};
        //绑定instance实例
        attachObject.instance = attachObject.instance || {};
        //绑定默认的参数对象给实例
        attachObject.instance._options = model._options;
        return attachObject.instance;
    };

    //ko插件，默认参数绑定方法
    ko.components.bindOptions = function (model, params) {
        var self = model;
        var _options = model._options;
        params = params || {};
        /*
        * 将_options对象中的所有属性替换为ko的对象
        */
        _.map(_options || {}, function (value, key) {
            if (_.isArray(value)) {
                _options[key] = ko.isObservable(value) ? value : ko.observableArray(value);
            }
            else if (_.isFunction(value)) {
                _options[key] = value;
            }
            else {
                _options[key] = ko.isObservable(value) ? value : ko.observable(value);
            }
        });
        /*
         * 将params.options对象中的属性替换为ko的对象并更新_options对象中对应的属性
         */
        params.options = ko.isObservable(params.options) ? params.options() : params.options;
        _.map(params.options || {}, function (value, key) {
            if (_.isArray(value)) {
                _options[key] = ko.isObservable(value) ? value : ko.observableArray(value);
            }
            else if (_.isFunction(value)) {
                _options[key] = value;
            }
            else {
                _options[key] = ko.isObservable(value) ? value : ko.observable(value);
            }
        });

        //将对应的属性和值，映射到model对象中
        _.map(_options, function (value, key) {
            self[key] = value;
        });
        //初始化onStart方法
        if(self.onStart)
        {
            self.onStart.call(self,_options.elementName,_options);
        }
        //返回值为instance
        return ko.components.bindInstance(self, params);
    };

    //hack underscore的extend方法，用于当前插件的onready事件触发
    if(underscore)
    {
        var _extend = underscore.extend;
        underscore.extend = function (){
            var args = Array.prototype.slice.call(arguments);
            _extend.apply(null,args);
            //触发onReady事件
            var _instance = args[0];
            if(_instance && _instance._options && _instance._options.onReady)
            {
                _instance._options.onReady.call(self,_instance._options.elementName,_instance);
            }
        };
    }

    /**
     * 类似foreach，但循环的是js的object，而不是数组
     * @type {{transformObject: ko.bindingHandlers.foreachprop.transformObject, init: ko.bindingHandlers.foreachprop.init}}
     */
    ko.bindingHandlers.foreachprop = {
        transformObject: function (obj) {
            var properties = [];
            for (var key in obj) {
                if (obj.hasOwnProperty(key)) {
                    properties.push({key: key, value: obj[key]});
                }
            }
            return properties;
        },
        init: function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
            var value = ko.utils.unwrapObservable(valueAccessor()),
                properties = ko.bindingHandlers.foreachprop.transformObject(value);
            ko.applyBindingsToNode(element, {foreach: properties}, bindingContext);
            return {controlsDescendantBindings: true};
        }
    };

})(ko,_);
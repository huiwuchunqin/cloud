package com.baizhitong.syscode.frontend.service.impl;

import com.baizhitong.syscode.frontend.service.ICodeParse;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * 获取对的实现方法
 * 
 * @author creator jiayy 2016年1月15日 上午9:39:01
 * @author updater
 *
 * @version 1.0.0
 */
public class CodeParseMap {
    public static ICodeParse get(String str)
                    throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        String package_ = CodeParseMap.class.getPackage().toString();
        str = StringUtils.upperCaseFirstChar(str);
        Class<?> clazz = Class.forName(package_ + str);
        return (ICodeParse) clazz.newInstance();
    }
}

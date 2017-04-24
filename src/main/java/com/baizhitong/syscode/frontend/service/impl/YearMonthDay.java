package com.baizhitong.syscode.frontend.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baizhitong.syscode.dao.ICodeParseDao;
import com.baizhitong.syscode.frontend.service.ICodeParse;
import com.baizhitong.utils.DateUtils;

/**
 * 
 * 获取当前的年月日
 * 
 * @author creator jiayy 2016年1月18日 下午2:30:20
 * @author updater
 *
 * @version 1.0.0
 */
@Component("yearMonthDay")
public class YearMonthDay implements ICodeParse {

    @Autowired
    ICodeParseDao codeParseDao;

    @Override
    public String parseCode(String code, Map<String, String> map, String... str) {
        String formatStr = str[0];
        String returnValue = DateUtils.getDate(formatStr);
        return returnValue;
    }

}

package com.baizhitong.syscode.frontend.service.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.baizhitong.syscode.frontend.service.ICodeParse;
import com.baizhitong.utils.StringUtils;

/***
 * 
 * 国标学校代码，不足10位，左补0,为空
 * 
 * @author creator jiayy 2016年1月16日 上午11:07:46
 * @author updater
 *
 * @version 1.0.0
 */
@Component("schoolCode")
public class SchoolCode implements ICodeParse {

    @Autowired
    @Qualifier("swiftNumber")
    ICodeParse swiftNumber;

    @Override
    public String parseCode(String code, Map<String, String> map, String... str) {
        String schoolCode = MapUtils.getString(map, "SchoolCode");
        // 字符长度
        String parameter = str[0];
        if (StringUtils.isNotEmpty(schoolCode)) {
            schoolCode = StringUtils.lpad(Integer.parseInt(parameter), schoolCode);
        } else {
            schoolCode = swiftNumber.parseCode(code, map);
        }
        return schoolCode;
    }

}

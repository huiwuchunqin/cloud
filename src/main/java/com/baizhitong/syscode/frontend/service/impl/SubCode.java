package com.baizhitong.syscode.frontend.service.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.baizhitong.syscode.frontend.service.ICodeParse;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * 截取code代码
 * 
 * @author creator jiayy 2016年1月16日 上午11:03:58
 * @author updater
 *
 * @version 1.0.0
 */
@Component("subCode")
public class SubCode implements ICodeParse {

    @Override
    public String parseCode(String code, Map<String, String> map, String... str) {
        // 需要截取的code
        String subCode = str[0];
        // 需要截取的长度
        int len = Integer.parseInt(str[1]);
        // value
        String value = MapUtils.getString(map, subCode);
        // 空的或者长度不足2位
        if (StringUtils.isEmpty(value) || value.length() <= len) {
            throw new RuntimeException("请传入正确的年级编码！");
        }
        int valueLen = value.length();
        return value.substring(valueLen - len, valueLen);
    }

}

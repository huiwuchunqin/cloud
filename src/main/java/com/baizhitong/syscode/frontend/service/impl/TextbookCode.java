package com.baizhitong.syscode.frontend.service.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baizhitong.resource.common.vo.ListVo;
import com.baizhitong.syscode.dao.ICodeParseDao;
import com.baizhitong.syscode.frontend.service.ICodeParse;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * 教材编码
 * 
 * @author creator jiayy 2016年1月18日 下午2:33:04
 * @author updater
 *
 * @version 1.0.0
 */
@Component("textbookCode")
public class TextbookCode implements ICodeParse {

    @Autowired
    ICodeParseDao codeParseDao;

    @Override
    public String parseCode(String code, Map<String, String> map, String... str) {
        // 教材版本编码
        String textbookVerCode = map.get(str[0]);
        textbookVerCode = StringUtils.notNull(textbookVerCode);
        // 年级
        String gradeCode = map.get(str[1]);
        gradeCode = StringUtils.notNull(gradeCode);
        ListVo<Map<String, Object>> list = codeParseDao.getTextbookCode(textbookVerCode, gradeCode);
        Map<String, Object> newMap = list.get(0);
        // 去除父节点
        String returnValue = MapUtils.getString(newMap, "tbCode");
        // 判断是否为空
        if (StringUtils.isEmpty(returnValue)) {
            returnValue = "A";
        } else {
            // 后 1 位
            returnValue = returnValue.trim();
            returnValue = returnValue.substring(returnValue.length() - 1, returnValue.length());
            char c = returnValue.toCharArray()[0];
            c += 1;
            returnValue = c + "";
        }
        if (StringUtils.isEmpty(gradeCode)) {
            returnValue = "XX" + returnValue;
        }

        return returnValue;

    }

}

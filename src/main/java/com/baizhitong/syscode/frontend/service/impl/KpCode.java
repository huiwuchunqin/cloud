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
 * 知识点编码
 * 
 * @author creator jiayy 2016年1月18日 下午2:30:20
 * @author updater
 *
 * @version 1.0.0
 */
@Component("kpCode")
public class KpCode implements ICodeParse {

    @Autowired
    ICodeParseDao codeParseDao;

    @Override
    public String parseCode(String code, Map<String, String> map, String... str) {
        // 学科
        String subjectCode = map.get(str[0]);
        // 父节点
        String pcode = map.get(str[1]);
        pcode = StringUtils.notNull(pcode);
        // 长度
        int len = Integer.parseInt(str[2]);
        // 备用
        String bycode = str[3];

        ListVo<Map<String, Object>> list = codeParseDao.getKpCode(subjectCode, pcode);
        Map<String, Object> newMap = list.get(0);
        // 去除父节点
        String returnValue = MapUtils.getString(newMap, "code");// .replace(pcode, "");
        // 判断是否为空
        if (StringUtils.isEmpty(returnValue)) {
            returnValue = "1";
        } else {
            // 后 len 位
            returnValue = returnValue.substring(returnValue.length() - len, returnValue.length());
            int value = Integer.parseInt(returnValue) + 1;
            returnValue = value + "";
        }
        // 去除前缀
        pcode = pcode.replace(subjectCode, "").replace(bycode, "");
        returnValue = pcode + StringUtils.lpad(len, returnValue);
        return returnValue;
    }

}

package com.baizhitong.syscode.frontend.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baizhitong.syscode.dao.ISysCodeDao;
import com.baizhitong.syscode.frontend.service.ICodeParse;
import com.baizhitong.syscode.vo.SysCodeVo;
import com.baizhitong.utils.ObjectUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * SwiftNumber TODO
 * 
 * @author creator jiayy 2016年1月14日 下午6:54:45
 * @author updater
 *
 * @version 1.0.0
 */
@Component("swiftNumber")
public class SwiftNumber implements ICodeParse {
    /** 全局 code 数据接口 */
    @Autowired
    ISysCodeDao sysCodedao;

    public String parseCode(String code, Map<String, String> map, String... str) {
        // 流水号长度
        String parameter = str[0];
        char a = 'A';
        a += 1;
        // 获取当前的 sysCode 信息
        SysCodeVo sysCodeVo = sysCodedao.getSysCodeVo(code);
        String returnStr = swiftNumber(code, sysCodeVo.getSwiftNumber(), Integer.parseInt(parameter));
        return returnStr;
    }

    /**
     * 
     * (产生流水号)<br>
     * 
     * @param code 字段名
     * @param swiftNumber 当前最值
     * @param len 流水号长度
     * @return
     */
    public String swiftNumber(String code, Integer swiftNumber, Integer len) {
        if (ObjectUtils.isNull(swiftNumber)) {
            swiftNumber = 0;
        }
        String str = "";
        int num = sysCodedao.updateSwiftNumber(code, swiftNumber);
        if (num == 0) {
            return swiftNumber(code, swiftNumber + 1, len);
        }
        swiftNumber++;
        str = StringUtils.lpad(len, swiftNumber + "");
        return str;
    }
}

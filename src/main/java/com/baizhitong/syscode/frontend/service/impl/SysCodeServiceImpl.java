package com.baizhitong.syscode.frontend.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.dao.basic.ShareAdminClassDao;
import com.baizhitong.syscode.dao.ISysCodeDao;
import com.baizhitong.syscode.frontend.service.ICodeParse;
import com.baizhitong.syscode.frontend.service.ISysCodeService;
import com.baizhitong.syscode.vo.SysCodeVo;
import com.baizhitong.utils.ObjectUtils;

/**
 * 
 * 全局 code 业务接口实现
 * 
 * @author creator jiayy 2016年1月14日 上午11:30:35
 * @author updater
 *
 * @version 1.0.0
 */
@Service()
public class SysCodeServiceImpl implements ISysCodeService {

    /** 全局 code 数据接口 */
    @Autowired
    ISysCodeDao                sysCodedao;

    @Autowired
    ApplicationContext         applicationContext;
    // 行政班级dao
    @Autowired
    private ShareAdminClassDao shareAdminClassDao;

    /**
     * 获取行政班级编码 ()<br>
     * 
     * @param orgCode
     * @param sectionCode
     * @param entryYear
     * @return
     */
    public String getNextAdminClassCode(String orgCode, String sectionCode, Integer entryYear) {
        String maxCode = shareAdminClassDao.getMax(orgCode, entryYear);
        String adminClassCode = "BJ" + orgCode + sectionCode + entryYear;
        if (StringUtils.isEmpty(sectionCode))
            sectionCode = "#";
        if (ObjectUtils.isInt(maxCode)) {
            Integer next = Integer.parseInt(maxCode) + 1;
            adminClassCode += next < 10 ? "0" + next : next;
        } else {
            adminClassCode += "01";
        }

        return adminClassCode;
    }

    /***
     * 
     * (获取全局编码)<br>
     * 
     * @param code 字段名称
     * @param str 参数，成对出现，如sectionCode,001
     * @return
     */
    @Override
    public String getCode(String code, String... str) {
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0, j = str.length / 2; i < j; i++) {
            map.put(str[2 * i] + "", str[2 * i + 1]);
        }
        return getCode(code, map);
    }

    /***
     * 
     * (获取全局编码)<br>
     * 
     * @param code 字段名称
     * @param str 参数，成对出现，如sectionCode,001
     * @return
     */
    @Override
    public String getCode(String code, Map<String, String> map) {
        // 提取｛｝函数
        Pattern p = Pattern.compile("\\{([^\\}]*)\\}");
        // 将函数名与参数分开
        Pattern p2 = Pattern.compile("(\\w*)\\((.*)\\)");
        // 获取当前的 sysCode 信息
        SysCodeVo sysCodeVo = sysCodedao.getSysCodeVo(code);
        // 获取 code公式
        String returnCode = sysCodeVo.getFormula().trim().replaceAll("\\+", "");
        // 将所有字符替换
        for (String key : map.keySet()) {
            returnCode = returnCode.replaceAll("\\{" + key + "\\}", map.get(key));
        }
        // 正则提取｛｝函数
        Matcher matcher = p.matcher(returnCode);
        while (matcher.find()) {
            String tab = matcher.group(1);
            // 将函数名与参数分开
            Matcher matcher2 = p2.matcher(tab);
            if (matcher2.find()) {
                // 函数名
                String functionName = matcher2.group(1);
                // 参数
                String parameter[] = matcher2.group(2).split(",");

                ICodeParse codeParse = (ICodeParse) applicationContext.getBean(functionName);
                String string = codeParse.parseCode(code, map, parameter);
                returnCode = returnCode.replace("{" + tab + "}", string);
            }
        }
        return returnCode;
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
        // str=StringUtils.lpad(len, swiftNumber+"");
        return str;
    }

    /***
     * 
     * (默认创建全局Code表)<br>
     */
    @PostConstruct
    public void createSysCode() {
        sysCodedao.createTableSysCode();
    }
}

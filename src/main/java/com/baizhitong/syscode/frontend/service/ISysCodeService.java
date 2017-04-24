package com.baizhitong.syscode.frontend.service;

import java.util.Map;

/**
 * 
 * 全局 code 业务接口
 * 
 * @author creator jiayy 2016年1月14日 上午11:31:13
 * @author updater
 *
 * @version 1.0.0
 */
public interface ISysCodeService {
    /***
     * 
     * (获取全局编码)<br>
     * 
     * @param code 字段名称
     * @param str 参数，成对出现，如sectionCode,001
     * @return
     */
    String getCode(String code, String... str);

    /**
     * 获取行政班级编码 ()<br>
     * 
     * @param orgCode 机构编码
     * @param sectionCode 学段编码
     * @param entryYear 入学年月
     * @return
     */
    String getNextAdminClassCode(String orgCode, String sectionCode, Integer entryYear);

    /***
     * 
     * (获取全局编码)<br>
     * 
     * @param code 字段名称
     * @param map 参数，成对出现，如sectionCode 001
     * @return
     */
    String getCode(String code, Map<String, String> map);
}

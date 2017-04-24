package com.baizhitong.syscode.dao;

import com.baizhitong.syscode.vo.SysCodeVo;

/**
 * 
 * 全局 code 数据接口
 * 
 * @author creator jiayy 2016年1月14日 上午11:14:38
 * @author updater
 *
 * @version 1.0.0
 */
public interface ISysCodeDao {
    /**
     * 
     * (获取一个SysCodeVo)<br>
     * 
     * @param code 字段名
     * @return
     */
    public SysCodeVo getSysCodeVo(String code);

    /**
     * 
     * (修改流水号)<br>
     * 
     * @param code 字段名
     * @param swiftNumber 原流水口
     * @return
     */
    public int updateSwiftNumber(String code, Integer swiftNumber);

    /***
     * 
     * (默认创建全局Code表)<br>
     */
    public void createTableSysCode();
}

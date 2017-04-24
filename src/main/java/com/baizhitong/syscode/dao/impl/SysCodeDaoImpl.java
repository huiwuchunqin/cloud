package com.baizhitong.syscode.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.syscode.dao.ISysCodeDao;
import com.baizhitong.syscode.entity.SysCodeEntity;
import com.baizhitong.syscode.vo.SysCodeVo;
import com.baizhitong.utils.convert.ConvertUtils;

/**
 * 
 * 全局 code 数据接口实现
 * 
 * @author creator jiayy 2016年1月14日 上午11:20:33
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class SysCodeDaoImpl extends BaseSqlServerDao<SysCodeEntity> implements ISysCodeDao {

    /**
     * 
     * (获取一个SysCodeVo)<br>
     * 
     * @param code 字段名
     * @return
     */
    public SysCodeVo getSysCodeVo(String code) {
        SysCodeEntity SysCodeEntity = super.get(ConvertUtils.map("fieldName", code));
        return ConvertUtils.convert(SysCodeEntity, SysCodeVo.class);
    }

    /**
     * 
     * (修改流水号)<br>
     * 
     * @param code 字段名
     * @param swiftNumber 原流水口
     * @return
     */
    public int updateSwiftNumber(String code, Integer swiftNumber) {
        String sql = "UPDATE sys_code SET swiftNumber=isnull(swiftNumber,0)+1 WHERE fieldName=:code AND isnull(swiftNumber,0)=:swiftNumber";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("swiftNumber", swiftNumber);
        return this.update(sql, map);
    }

    /***
     * 
     * (默认创建全局Code表)<br>
     */
    public void createTableSysCode() {
        // String createTable = "CREATE TABLE sys_code " + " ( " + " [fieldName] varchar(50) NOT
        // NULL PRIMARY KEY ,"
        // + " [codeName] varchar(50) NULL ," + " [type] varchar(50) NULL ," + " [len] int NULL ,"
        // + " [explain] nvarchar(200) NULL ," + " [formula] nvarchar(200) NULL ,"
        // + " [swiftNumber] int NULL DEFAULT 0 " + ") ";
        // String sql = " if object_id(N'sys_code',N'U') is null " + " begin " + createTable + " end
        // ";
        // this.update(sql);
    }

}

package com.baizhitong.resource.dao.share.sqlserver;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareOrgCloudDiskParamDao;

@Service
public class ShareOrgCloudDiskParamDaoImpl extends BaseSqlServerDao<HashMap> implements ShareOrgCloudDiskParamDao {
    /**
     * ()<br>
     * 
     * @param orgCode
     * @return
     */
    public Integer insertOrgClod(String orgCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("INSERT ");
        sql.append("    INTO");
        sql.append("        share_org_cloud_disk_param");
        sql.append("        (     gid,     orgCode,     userRole,     capacityQuota,     memo,     modifyPgm,     modifier,     modifyTime,     modifyIP,     sysVer    )     select");
        sql.append("            NEWID()");
        sql.append("            ,:orgCode");
        sql.append("            ,userRole");
        sql.append("            ,capacityQuota");
        sql.append("            ,''");
        sql.append("            ,'ShareOrgCloudDiskParamDaoImpl'");
        sql.append("            ,'init'");
        sql.append("            ,GETDATE()");
        sql.append("            ,'init'");
        sql.append("            ,1    ");
        sql.append("        FROM");
        sql.append("            share_platform_cloud_disk_param");
        sqlParam.put("orgCode", orgCode);
        return super.update(sql.toString(), sqlParam);
    }
}

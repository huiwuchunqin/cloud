package com.baizhitong.resource.dao.share.sqlserver;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareResTypeL2Dao;
import com.baizhitong.resource.model.share.ShareResTypeL2;
import com.baizhitong.utils.StringUtils;

/**
 * 资源二级分类SqlServer数据接口实现
 * 
 * @author creator Cuidc 2015/12/09
 * @author updater
 */
@Service("ShareResTypeL2Dao")
public class ShareResTypeL2SqlServerDaoImpl extends BaseSqlServerDao<ShareResTypeL2> implements ShareResTypeL2Dao {
    /**
     * 获取资源二级分类集合
     * 
     * @param codeL1 资源一级分类编码
     * @return 集合
     * @throws Exception 异常
     */
    @Override
    public List<ShareResTypeL2> selectResTypeL2List(Integer codeL1) throws Exception {
        QueryRule queryRule = QueryRule.getInstance();
        queryRule.andEqual("codeL1", codeL1);
        queryRule.addAscOrder("code");
        return super.find(queryRule);
    }

    /************************************************** |以上已确认| **************************************************/

    @Override
    public ShareResTypeL2 selectByCode(String code) throws Exception {
        QueryRule queryRule = QueryRule.getInstance();
        queryRule.andEqual("code", code);
        return super.findUnique(queryRule);
    }

    /**
     * 更新 ()<br>
     * 
     * @param newName
     * @param code
     * @return
     */
    public Integer update(String newName, String description, String code, String modifyIp) {
        String sql = "update share_res_type_l2 set description=:description,name=:name,modifyIP=:modifyIP  where code=:code";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("name", newName);
        sqlParam.put("description", description);
        sqlParam.put("modifyIP", modifyIp);
        sqlParam.put("code", code);
        return super.update(sql, sqlParam);
    }

    /**
     * 新增 ()<br>
     * 
     * @param resTypeL2
     */
    public void add(ShareResTypeL2 resTypeL2) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("INSERT ");
        sql.append("    INTO");
        sql.append("        share_res_type_l2");
        sql.append("        (    gid,    codeL1,    code,    name,    description,    dispOrder,    modifyPgm,    modifyTime,    modifyIP,    sysVer   )   ");
        sql.append("    VALUES");
        sql.append("        (     NEWID(),    :codeL1,     (isnull((select");
        sql.append("            max(code) ");
        sql.append("        FROM");
        sql.append("            share_res_type_l2 ");
        sql.append("        WHERE");
        sql.append("            code like '9%'),9000)+1),    :name,    :description,     (isnull((select");
        sql.append("            max(dispOrder) ");
        sql.append("        FROM");
        sql.append("            share_res_type_l2 ");
        sql.append("        WHERE");
        sql.append("            codeL1=:codeL1),0)+1),:modifyPgm,:modifyTime,:modifyIP,0    );");

        sqlParam.put("codeL1", resTypeL2.getCodeL1());
        sqlParam.put("name", resTypeL2.getName());
        sqlParam.put("description", resTypeL2.getDescription());
        sqlParam.put("modifyPgm", resTypeL2.getModifyPgm());
        sqlParam.put("modifyTime", new Timestamp(new Date().getTime()));
        sqlParam.put("modifyIP", resTypeL2.getModifyIP());
        super.update(sql.toString(), sqlParam);
    }

    /**
     * 查询列表 ()<br>
     * 
     * @param page
     * @param rows
     * @param resTypeL1Code
     * @return
     */
    public Page pageList(Integer page, Integer rows, String resTypeL1Code) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        a.gid");
        sql.append("        ,a.codeL1");
        sql.append("        ,a.code");
        sql.append("        ,a.name");
        sql.append("        ,a.description");
        sql.append("        ,a.modifyIP");
        sql.append("        ,a.modifyTime");
        sql.append("        ,a.sysVer");
        sql.append("        ,a.dispOrder ");
        sql.append("        ,b.name as resTypeL1Name ");
        sql.append("    FROM");
        sql.append("        share_res_type_l2 a");
        sql.append("       left join share_res_type_l1 b on b.code=a.codeL1");
        sql.append("   WHERE 1=1 ");
        /**
         * 一级分类编码
         */
        if (StringUtils.isNotEmpty(resTypeL1Code)) {
            sql.append("   and codeL1=:codeL1");
            sqlParam.put("codeL1", resTypeL1Code);
        }
        sql.append(" order by b.dispOrder asc ,a.dispOrder  asc ");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }
}
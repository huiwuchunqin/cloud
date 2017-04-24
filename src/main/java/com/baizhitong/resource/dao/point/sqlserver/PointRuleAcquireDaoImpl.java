package com.baizhitong.resource.dao.point.sqlserver;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.point.PointRuleAcquireDao;
import com.baizhitong.resource.model.point.PointRuleAcquire;

@Service
public class PointRuleAcquireDaoImpl extends BaseSqlServerDao<PointRuleAcquire> implements PointRuleAcquireDao {
    /**
     * 查询积分规则 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getAcquirePage(Map<String, Object> param, Integer page, Integer rows) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("        a.id");
        sql.append("        ,a.bizVersion");
        sql.append("        ,a.startTime");
        sql.append("        ,a.expireTime");
        sql.append("        ,a.userRole");
        sql.append("        ,a.actionType");
        sql.append("        ,a.actionTypeName");
        sql.append("        ,a.actionCode");
        sql.append("        ,a.actionName");
        sql.append("        ,a.actionDescription");
        sql.append("        ,a.flagGraded");
        sql.append("        ,a.algorithmsType");
        sql.append("        ,a.algorithmsJson");
        sql.append("        ,a.algorithmsTypeOrg");
        sql.append("        ,a.algorithmsJsonOrg");
        sql.append("        ,a.modifier");
        sql.append("        ,a.modifyTime");
        sql.append("        ,a.modifierIP");
        sql.append("        ,a.sysVersion  ");
        sql.append("        ,a.flagOrgValid  ");
        sql.append("    FROM");
        sql.append("        point_rule_acquire a ");
        sql.append(" order by a.startTime desc");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    /**
     * 根据code查询 ()<br>
     * 
     * @param actionCode
     * @return
     */
    public PointRuleAcquire getByActionCode(String actionCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("actionCode", actionCode);
        return super.findUnique(qr);
    }

    /**
     * 更新 ()<br>
     * 
     * @param ruleAcquire
     * @return
     */
    public boolean updatePointRuleAcquire(PointRuleAcquire ruleAcquire) {
        try {
            return super.saveOne(ruleAcquire);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
}

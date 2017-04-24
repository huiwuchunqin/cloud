package com.baizhitong.resource.dao.point.sqlserver;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.point.PointRuleAcquireOrgDao;
import com.baizhitong.resource.model.point.PointRuleAcquireOrg;
import com.baizhitong.utils.StringUtils;

@Service
public class PointRuleAcquireOrgDaoImpl extends BaseSqlServerDao<PointRuleAcquireOrg>
                implements PointRuleAcquireOrgDao {
    /**
     * 查询积分规则 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getAcquirePage(Map<String, Object> param, Integer page, Integer rows) {
        String orgCode = MapUtils.getString(param, "orgCode");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("        a.id");
        sql.append("        ,a.bizVersion");
        sql.append("        ,a.startTime");
        sql.append("        ,a.expireTime");
        sql.append("        ,a.orgCode");
        sql.append("        ,a.settingType");
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
        sql.append("    FROM");
        sql.append("        point_rule_acquire_org a");
        sql.append(" where 1=1 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and a.orgCode=:orgCode ");
            sqlParam.put("orgCode", orgCode);
        }
        sql.append("order by a.startTime ");

        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    /**
     * 根据code查询 ()<br>
     * 
     * @param id
     * @return
     */
    public PointRuleAcquireOrg getById(Integer id) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("id", id);
        return super.findUnique(qr);
    }

    /**
     * 更新 ()<br>
     * 
     * @param ruleAcquire
     * @return
     */
    public boolean updatePointRuleAcquire(PointRuleAcquireOrg ruleAcquireOrg) {
        try {
            return super.saveOne(ruleAcquireOrg);
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

    /**
     * 更新积分获取规则 ()<br>
     * 
     * @param actionCode
     * @param pointOrg
     * @param pointPlt
     * @return
     */
    public int update(String actionCode, String pointOrg, String pointPlt) {
        StringBuffer sql = new StringBuffer();
        sql.append("update");
        sql.append("        point_rule_acquire_org ");
        sql.append("    SET");
        sql.append("        algorithmsJson= :algorithmsJson ");
        sql.append("        ,algorithmsJsonOrg=:algorithmsJsonOrg ");
        sql.append("    WHERE");
        sql.append("        actionCode=:actionCode ");
        sql.append("        and settingType=0");
        sql.append("        and orgCode not in ");
        sql.append("     ( ");
        sql.append("SELECT");
        sql.append("        orgCode ");
        sql.append("    FROM");
        sql.append("        point_rule_acquire_org ");
        sql.append("    WHERE");
        sql.append("        settingType =:settingTypeNotExtend ");
        sql.append("        ");
        sql.append("    GROUP BY");
        sql.append("        orgCode");
        sql.append("     ) ");
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("algorithmsJson", pointPlt);
        param.put("settingTypeNotExtend", CoreConstants.SETTING_TYPE_NOT_EXTEND);
        param.put("algorithmsJsonOrg", pointOrg);
        param.put("actionCode", actionCode);
        return super.update(sql.toString(), param);

    }

    /**
     * 把平台导入机构 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public int importDefaultToOrg(String orgCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> param = new HashMap<String, Object>();
        sql.append("INSERT ");
        sql.append("    INTO");
        sql.append("        point_rule_acquire_org");
        sql.append("        (    bizVersion,    startTime,    expireTime,    orgCode,    settingType,    userRole,    actionType,    actionTypeName,    actionCode,    actionName,    actionDescription,    flagGraded,    algorithmsType,    algorithmsJson,    algorithmsTypeOrg,    algorithmsJsonOrg,    modifier,    modifyTime,    modifierIP,    sysVersion   )   select");
        sql.append("            a.bizVersion");
        sql.append("            ,a.startTime");
        sql.append("            ,a.expireTime");
        sql.append("            ,:orgCode");
        sql.append("            ,0");
        sql.append("            ,a.userRole");
        sql.append("            ,a.actionType");
        sql.append("            ,a.actionTypeName");
        sql.append("            ,a.actionCode");
        sql.append("            ,a.actionName");
        sql.append("            ,a.actionDescription");
        sql.append("            ,a.flagGraded");
        sql.append("            ,a.algorithmsType");
        sql.append("            ,a.algorithmsJson");
        sql.append("            ,a.algorithmsTypeOrg");
        sql.append("            ,a.algorithmsJsonOrg");
        sql.append("            ,a.modifier");
        sql.append("            ,GETDATE()");
        sql.append("            ,a.modifierIP");
        sql.append("            ,a.sysVersion   ");
        sql.append("        FROM");
        sql.append("            point_rule_acquire a   ");
        sql.append("        WHERE");
        sql.append("            a.actionCode not ");
        sql.append("        IN (");
        sql.append("            select");
        sql.append("                actionCode FROM");
        sql.append("                    point_rule_acquire_org ");
        sql.append("                WHERE");
        sql.append("                     orgCode=:orgCode");
        sql.append("            )");

        param.put("orgCode", orgCode);
        return super.update(sql.toString(), param);
    }

}

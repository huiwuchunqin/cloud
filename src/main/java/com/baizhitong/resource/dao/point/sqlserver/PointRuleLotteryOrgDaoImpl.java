package com.baizhitong.resource.dao.point.sqlserver;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.point.PointRuleLotteryOrgDao;
import com.baizhitong.resource.model.point.PointRuleLotteryOrg;
import com.baizhitong.utils.DateUtils;

/**
 * 积分规则dao实现 PointRuleLotteryOrgDaoImpl TODO
 * 
 * @author creator BZT 2016年6月23日 下午5:03:07
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class PointRuleLotteryOrgDaoImpl extends BaseSqlServerDao<PointRuleLotteryOrg>
                implements PointRuleLotteryOrgDao {

    /**
     * 查询积分规则 ()<br>
     * 
     * @param page
     * @param rows
     * @return
     */
    public Page getList(String orgCode, Integer page, Integer rows) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("select");
        sql.append("        * ");
        sql.append("    FROM");
        sql.append("        point_rule_lottery_org ");
        sql.append("    WHERE");
        sql.append("        1=1 and flagDelete=0 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and orgCode=:orgCode ");
            sqlParam.put("orgCode", orgCode);
        }
        sql.append(" order by startTime desc ");

        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);

    }

    /**
     * 机构积分规则 ()<br>
     * 
     * @param ruleOrg
     * @return
     */
    public boolean add(PointRuleLotteryOrg ruleOrg) {
        try {
            return super.saveOne(ruleOrg);
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
     * 查询机构积分规则 ()<br>
     * 
     * @param id
     * @return
     */
    public PointRuleLotteryOrg getById(Integer id) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("id", id);
        qr.andEqual("flagDelete", 0);
        return super.findUnique(qr);
    }

    /**
     * 让之前的规则失效 ()<br>
     * 
     * @return
     */
    public int makeExpire(String orgCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("UPDATE");
        sql.append("        point_rule_lottery_org  ");
        sql.append("    SET");
        sql.append("        expireTime =:expireTime  ");
        sql.append("    WHERE");
        sql.append("        expireTime='99999999999999'  ");
        sql.append("        and orgCode=:orgCode");
        sqlParam.put("orgCode", orgCode);
        sqlParam.put("expireTime", Long.parseLong(DateUtils.getDate(new Date(), "yyyyMMddHHmmss")));
        sqlParam.put("startTime", Long.parseLong(DateUtils.getDate(new Date(), "yyyyMMddHHmmss")));
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 删除 ()<br>
     * 
     * @param id
     * @return
     */
    public int delete(int id) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("UPDATE");
        sql.append("        point_rule_lottery_org  ");
        sql.append("    SET");
        sql.append("        flagDelete =1  ");
        sql.append("    WHERE");
        sql.append("        id =:id ");
        sqlParam.put("id", id);
        return super.update(sql.toString(), sqlParam);
    }

}

package com.baizhitong.resource.dao.point.sqlserver;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.point.PointRuleLotteryPlatformDao;
import com.baizhitong.resource.model.point.PointRuleLotteryPlatform;
import com.baizhitong.utils.DateUtils;

@Service
public class PointRuleLotteryPlatformDaoImpl extends BaseSqlServerDao<PointRuleLotteryPlatform>
                implements PointRuleLotteryPlatformDao {
    /**
     * 查询积分规则 ()<br>
     * 
     * @param page
     * @param rows
     * @return
     */
    public Page getList(Integer page, Integer rows) {
        StringBuffer sql = new StringBuffer();
        sql.append("select");
        sql.append("        * ");
        sql.append("    FROM");
        sql.append("        point_rule_lottery_platform ");
        sql.append("    WHERE");
        sql.append("        1=1 and flagDelete=0 ");
        sql.append(" order by startTime desc ");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);

    }

    /**
     * 机构积分规则 ()<br>
     * 
     * @param rulePlatform
     * @return
     */
    public boolean add(PointRuleLotteryPlatform rulePlatform) {
        try {
            return super.saveOne(rulePlatform);
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
    public PointRuleLotteryPlatform getById(Integer id) {
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
    public int makeExpire() {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("UPDATE");
        sql.append("        point_rule_lottery_platform  ");
        sql.append("    SET");
        sql.append("        expireTime = :expireTime  ");
        sql.append("    WHERE");
        sql.append("        expireTime='99999999999999'  ");
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
        sql.append("        point_rule_lottery_platform  ");
        sql.append("    SET");
        sql.append("        flagDelete =1  ");
        sql.append("    WHERE");
        sql.append("        id =:id ");
        sqlParam.put("id", id);
        return super.update(sql.toString(), sqlParam);
    }

}

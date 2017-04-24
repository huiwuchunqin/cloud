package com.baizhitong.resource.dao.point;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.model.point.PointUserStatus;

@Repository
public class PointUserStatusDaoImpl extends BaseSqlServerDao<PointUserStatus> implements PointUserStatusDao {
    /**
     * 更新用户状态 ()<br>
     * 
     * @param rankCode
     * @param rankName
     * @param orgCode
     * @return
     */
    public int update(String rankCode, String rankName, String orgCode) {
        String sql = " update point_user_status set rankName=:rankName where orgCode=:orgCode and rankCode=:rankCode";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("rankName", rankName);
        param.put("orgCode", orgCode);
        param.put("rankCode", rankCode);
        return super.update(sql, param);
    }

    /**
     * 平台关联的机构修改 ()<br>
     * 
     * @param rankCode
     * @param rankName
     * @return
     */
    public int platRelateOrgUpdate(String rankCode, String rankName) {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE");
        sql.append("        point_user_status   ");
        sql.append("    SET");
        sql.append("        rankName =:rankName   ");
        sql.append("    WHERE");
        sql.append("        orgCode not in  (");

        sql.append(" SELECT ");
        sql.append("        orgCode ");
        sql.append("    FROM");
        sql.append("        point_rank_org ");
        sql.append("    WHERE");
        sql.append("        settingType =:notExtend ");
        sql.append("        ");
        sql.append("    GROUP BY");
        sql.append("        orgCode");

        sql.append("        )");
        sql.append(" and rankCode=:rankCode ");
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("rankCode", rankCode);
        param.put("rankName", rankName);
        param.put("settingType", CoreConstants.SETTING_TYPE_EXTEND);
        param.put("notExtend", CoreConstants.SETTING_TYPE_NOT_EXTEND);
        return super.update(sql.toString(), param);
    }
}

package com.baizhitong.resource.dao.clouddisk.sqlserver;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.clouddisk.CloudUserQuotaDao;
import com.baizhitong.resource.model.clouddisk.CloudUserQuota;
import com.baizhitong.utils.DateUtils;

/**
 * 
 * 用户云盘配额DAO接口实现类
 * 
 * @author creator ZhangQiang 2016年9月19日 下午4:57:04
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class CloudUserQuotaDaoImpl extends BaseSqlServerDao<CloudUserQuota> implements CloudUserQuotaDao {

    /**
     * 
     * (批量更新用户云盘许可容量)<br>
     * 
     * @param userRole 用户身份
     * @param capacitySize 许可的容量
     * @param modifier 更新者
     * @param modifierIP 更新者IP
     * @return
     */
    @Override
    public int updateCapacitySizeByUserRole(Integer userRole, Long capacitySize, String modifier, String modifierIP) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("UPDATE");
        sql.append("        cloud_user_quota ");
        sql.append("    SET");
        sql.append("        capacitySize = :capacitySize");
        sql.append("        ,modifier = :modifier");
        sql.append("        ,modifyTime = :modifyTime");
        sql.append("        ,modifierIP = :modifierIP ");
        sql.append("    WHERE");
        sql.append("        userCode ");
        sql.append("    IN (");
        sql.append("        SELECT");
        sql.append("            userCode FROM");
        sql.append("                share_user_login ");
        sql.append("            WHERE");
        sql.append("                userRole = :userRole ");
        sql.append("        ) ");
        sql.append("        AND flagDelete = :flagDelete ");
        sqlParam.put("capacitySize", capacitySize);
        sqlParam.put("modifier", modifier);
        sqlParam.put("modifierIP", modifierIP);
        sqlParam.put("modifyTime", DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
        sqlParam.put("userRole", userRole);
        sqlParam.put("flagDelete", CoreConstants.FLAG_DELETE_NO);
        return super.update(sql.toString(), sqlParam);
    }

}

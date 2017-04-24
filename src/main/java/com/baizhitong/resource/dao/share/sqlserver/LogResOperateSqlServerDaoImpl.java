package com.baizhitong.resource.dao.share.sqlserver;

import org.springframework.stereotype.Service;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.LogResOperateDao;
import com.baizhitong.resource.model.res.LogResOperate;
import com.baizhitong.utils.StringUtils;

/**
 * 日志 - 资源操作接口
 * 
 * @author creator lushunming 2015/12/04
 * @author updater
 */

@Service("logResOperateDaoSqlServerDao")
public class LogResOperateSqlServerDaoImpl extends BaseSqlServerDao<LogResOperate> implements LogResOperateDao {
    /**
     * 插入一条操作数据
     * 
     * @param logResOperate 操作数据
     * @return 是否成功
     */
    @Override
    public boolean saveLogResOperate(LogResOperate logResOperate) {
        boolean success = false;
        try {
            success = super.saveOne(logResOperate);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 获取用户的操作次数
     * 
     * @param resID资源id
     * @param userCode 用户usercode
     * @param resType 资源类型
     * @param operateType 操作类型
     * @return 操作次数
     */

    @Override
    public int getLogResOperateCount(Integer resID, Integer resType, String userCode, Integer operateType) {
        QueryRule queryRule = QueryRule.getInstance();

        if (null != resID) {
            queryRule.andEqual("resID", resID);
        }
        if (null != resType) {
            queryRule.andEqual("resTypeL1", resType);
        }
        if (null != operateType) {
            queryRule.andEqual("operateType", operateType);
        }
        if (!StringUtils.isEmpty(userCode)) {
            queryRule.andEqual("userCode", userCode);
        }

        return (int) super.getCount(queryRule);

    }
}

package com.baizhitong.resource.dao.share.mysql;

import org.springframework.stereotype.Service;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseMySqlDao;
import com.baizhitong.resource.dao.share.LogResOperateDao;
import com.baizhitong.resource.model.res.LogResOperate;

/**
 * 日志 - 资源操作接口
 * 
 * @author creator lushunming 2015/12/04
 * @author updater
 */

@Service("logResOperateDaoMySqlDao")
public class LogResOperateDaoImpl extends BaseMySqlDao<LogResOperate, Integer> implements LogResOperateDao {
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
        queryRule.andEqual("resID", resID);
        queryRule.andEqual("userCode", userCode);
        queryRule.andEqual("resTypeL1", resType);
        queryRule.andEqual("operateType", operateType);
        return (int) super.getCount(queryRule);

    }
}

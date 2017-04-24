package com.baizhitong.resource.dao.share;

import com.baizhitong.resource.model.res.LogResOperate;

/**
 * 日志 - 资源操作接口
 * 
 * @author creator lushunming 2015/12/04
 * @author updater
 */
public interface LogResOperateDao {

    /**
     * 插入一条操作数据
     * 
     * @param logResOperate 操作数据
     * @return 是否成功
     */
    public boolean saveLogResOperate(LogResOperate logResOperate);

    /**
     * 获取用户的操作次数
     * 
     * @param resID资源id
     * @param userCode 用户usercode
     * @param resType 资源类型
     * @return 操作次数
     */

    public int getLogResOperateCount(Integer resID, Integer resType, String userCode, Integer operateType);

}

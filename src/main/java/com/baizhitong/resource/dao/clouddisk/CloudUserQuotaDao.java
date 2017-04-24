package com.baizhitong.resource.dao.clouddisk;

/**
 * 
 * 用户云盘配额DAO接口
 * 
 * @author creator ZhangQiang 2016年9月19日 下午4:54:13
 * @author updater
 *
 * @version 1.0.0
 */
public interface CloudUserQuotaDao {

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
    int updateCapacitySizeByUserRole(Integer userRole, Long capacitySize, String modifier, String modifierIP);
}

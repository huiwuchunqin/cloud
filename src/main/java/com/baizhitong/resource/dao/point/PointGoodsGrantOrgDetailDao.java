package com.baizhitong.resource.dao.point;

public interface PointGoodsGrantOrgDetailDao {
    /**
     * 插入 ()<br>
     * 
     * @return
     */
    int insert(String orgCode, String studyYearCode, String termCode, String yearTermCode, String actionBatch,
                    String modifier, String modifierIP, String adminClassCode, String userName, String userRole,
                    String ids);
}

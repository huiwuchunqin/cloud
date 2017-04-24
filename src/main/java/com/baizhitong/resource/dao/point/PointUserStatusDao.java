package com.baizhitong.resource.dao.point;

public interface PointUserStatusDao {
    /**
     * 机构修改 ()<br>
     * 
     * @param rankCode
     * @param rankName
     * @param orgCode
     * @return
     */
    public int update(String rankCode, String rankName, String orgCode);

    /**
     * 平台关联的机构修改 ()<br>
     * 
     * @param rankCode
     * @param rankName
     * @return
     */
    public int platRelateOrgUpdate(String rankCode, String rankName);

}

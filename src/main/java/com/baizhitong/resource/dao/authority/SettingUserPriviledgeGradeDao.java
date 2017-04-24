package com.baizhitong.resource.dao.authority;

import java.util.List;
import java.util.Map;

import com.baizhitong.resource.model.authority.SettingUserPriviledgeGrade;

/**
 * 
 * 设置-管理员权限-年级DAO
 * 
 * @author creator ZhangQiang 2016年9月26日 下午2:34:49
 * @author updater
 *
 * @version 1.0.0
 */
public interface SettingUserPriviledgeGradeDao {

    /**
     * 
     * (新增一条记录)<br>
     * 
     * @param entity
     * @return
     */
    boolean insert(SettingUserPriviledgeGrade entity);

    /**
     * 根据登录者编码查询年级权限并返回<br>
     * 处理说明:按显示顺序升序排序
     * 
     * @param userCode 登录帐号
     * @return 年级权限列表
     */
    List<Map<String, Object>> selectListByUserCode(String userCode);

    /**
     * 根据登录帐号更新删除标识
     * 
     * @param userCode 登录帐号
     * @return 操作成功个数
     */
    int deleteByUserCode(String userCode);

    /**
     * 
     * (根据学段编码查询用户可以审核的年级列表)<br>
     * 
     * @param userCode
     * @param sectionCode
     * @return
     */
    List<Map<String, Object>> selectGradeListByUserCode(String userCode, String sectionCode);
}

package com.baizhitong.resource.dao.authority;

import java.util.List;
import java.util.Map;

import com.baizhitong.resource.model.authority.SettingUserPriviledgeSubject;

/**
 * 
 * 设置-管理员权限-学科DAO
 * 
 * @author creator ZhangQiang 2016年9月26日 下午2:42:32
 * @author updater
 *
 * @version 1.0.0
 */
public interface SettingUserPriviledgeSubjectDao {

    /**
     * 
     * (新增一条记录)<br>
     * 
     * @param entity
     * @return
     */
    boolean insert(SettingUserPriviledgeSubject entity);

    /**
     * 根据登录者编码查询学科权限并返回<br>
     * 处理说明：按显示顺序升序排序
     * 
     * @param userCode 登录者编码
     * @return 学科权限列表
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
     * (根据用户编码和学段编码查询学科列表)<br>
     * 
     * @param userCode
     * @param sectionCode
     * @return
     */
    List<Map<String, Object>> selectSubjectListByUserCode(String userCode, String sectionCode);
}

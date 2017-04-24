package com.baizhitong.resource.dao.res;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.res.ResMediaSpecial;

/**
 * 
 * 资源_特色资源DAO接口
 * 
 * @author creator zhangqiang 2016年8月9日 上午11:30:46
 * @author updater
 *
 * @version 1.0.0
 */
public interface ResMediaSpecialDao {

    /**
     * 
     * (查询特色资源全部信息)<br>
     * 
     * @param param 查询参数
     * @return
     */
    Page<Map<String, Object>> selectSpecialAllInfoPage(Map<String, Object> param);

    /**
     * 
     * (删除特色资源)<br>
     * 
     * @param id
     * @param userName
     * @param ipAddress
     * @return
     */
    int delete(String id, String userName, String ipAddress);

    /**
     * 
     * (查询特色资源待审核信息)<br>
     * 
     * @param param 查询参数
     * @return
     */
    Page<Map<String, Object>> selectSpecialCheckInfoPage(Map<String, Object> param);

    /**
     * 
     * (特色资源审核)<br>
     * 
     * @param resCode 资源编码
     * @param shareCheckLevel 分享审核中级别
     * @param modifier 更新者姓名
     * @param modifierIP 更新者IP
     * @param shareCheckStatus 分享审核中状态
     * @return
     */
    int updateShareCheckStatus(String resCode, Integer shareCheckLevel, String modifier, String modifierIP,
                    Integer shareCheckStatus);

    /**
     * 
     * (查询特色资源已通过审核信息)<br>
     * 
     * @param param 查询参数
     * @return
     */
    Page<Map<String, Object>> selectSpecialCheckedInfoPage(Map<String, Object> param);

    /**
     * 
     * (查询特色资源已退回信息)<br>
     * 
     * @param param 查询参数
     * @return
     */
    Page<Map<String, Object>> selectSpecialBackedInfoPage(Map<String, Object> param);

    /**
     * 
     * (根据主键id获取)<br>
     * 
     * @param id 主键id
     * @return
     */
    Map<String, Object> selectById(Integer id);

    /**
     * 
     * (新增或修改特色资源)<br>
     * 
     * @param entity 实体
     * @return
     */
    boolean add(ResMediaSpecial entity);

    /**
     * 
     * (批量操作特色资源)<br>
     * 
     * @param ids
     * @param operateType
     * @return
     */
    int updateFlagDeleteBatch(String ids, Integer operateType, String userName, String ip);

    /**
     * 
     * (资源首页设置，查询特色资源列表)<br>
     * 
     * @param param
     * @param rows
     * @param page
     * @return
     */
    Page<Map<String, Object>> selectPageByResSetting(Map<String, Object> param, Integer rows, Integer page);
}

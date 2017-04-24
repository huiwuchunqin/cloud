package com.baizhitong.resource.dao.res;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.res.ResFlash;

/**
 * 
 * ResFlashDao 互动资源DAO接口
 * 
 * @author creator gaow 2016年12月20日 上午9:57:02
 * @author updater
 *
 * @version 1.0.0
 */
public interface ResFlashDao {
    /**
     * 查询flash到 ()<br>
     * 
     * @param param 参数
     * @param page 页码
     * @param rows 行数
     * @return page
     */
    Page selectFlash(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 查询flash ()<br>
     * 
     * @param resCode 资源编码
     * @return flash对象
     */
    Map<String, Object> getFlash(String resCode);

    /**
     * 查询flash ()<br>
     * 
     * @param resCode
     * @return
     */
    ResFlash selectFlash(String resCode);

    /**
     * 查询flash ()<br>
     * 
     * @param resId
     * @return
     */
    ResFlash selectFlash(Integer resId);

    /**
     * 保存 ()<br>
     * 
     * @param flash
     */
    void save(ResFlash flash);

    /**
     * 删除Flash ()<br>
     * 
     * @param ids
     * @return
     */
    int deleteFlash(String ids, String modifier, String modifierIP);

    /**
     * 
     * (批量操作flash)<br>
     * 
     * @param ids flashId集合
     * @param operateType 操作类型
     * @param userName 用户姓名
     * @param ip IP地址
     * @return
     */
    int updateFlagDeleteBatch(String ids, Integer operateType, String userName, String ip);

}

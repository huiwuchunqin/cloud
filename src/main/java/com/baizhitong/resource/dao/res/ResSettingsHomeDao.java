package com.baizhitong.resource.dao.res;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.res.ResSettingsHome;

/**
 * 
 * 资源设置-首页显示DAO
 * 
 * @author creator zhangqiang 2016年7月27日 下午1:36:54
 * @author updater
 *
 * @version 1.0.0
 */
public interface ResSettingsHomeDao {

    /**
     * 
     * (分页查询资源设置显示信息)<br>
     * 
     * @param param 查询参数
     * @return
     */
    Page<Map<String, Object>> selectPage(Map<String, Object> param);

    /**
     * 
     * (新增或修改)<br>
     * 
     * @param entity 实体
     * @return
     */
    boolean add(ResSettingsHome entity);

    /**
     * 
     * (根据显示类别查询当前已有的最大顺序号)<br>
     * 
     * @param setType 显示类别
     * @return
     */
    int selectMaxOrderBySetType(String setType);

    /**
     * 
     * (删除首页显示资源)<br>
     * 
     * @param id 主键id
     * @return
     */
    int delete(String id);

    /**
     * 
     * (根据资源编码和显示类型查询资源首页设置信息)<br>
     * 
     * @param resCode 资源编码
     * @param setType 显示类别
     * @return
     */
    ResSettingsHome selectByResCodeAndSetType(String resCode, String setType);

    /**
     * 
     * (修改资源在首页是否使用)<br>
     * 
     * @param id 主键id
     * @param flagAvailable 是否使用
     * @return
     */
    int updateFlagAvailable(Integer id, Integer flagAvailable);

    /**
     * 
     * (查询列表，已使用的情况)<br>
     * 
     * @param setType 显示类别
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @return
     */
    List<ResSettingsHome> selectList(String setType, String sectionCode, String subjectCode);

    /**
     * 
     * (查询特色资源列表，已使用的情况)<br>
     * 
     * @param setType 显示类别
     * @param resSpecialTypeL2 特色二级分类
     * @return
     */
    List<ResSettingsHome> selectList(String setType, String resSpecialTypeL2);
}

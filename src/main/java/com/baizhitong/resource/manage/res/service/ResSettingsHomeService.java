package com.baizhitong.resource.manage.res.service;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.res.ResSettingsHome;

/**
 * 
 * 资源设置-首页显示Service
 * 
 * @author creator zhangqiang 2016年7月27日 下午1:30:07
 * @author updater
 *
 * @version 1.0.0
 */
public interface ResSettingsHomeService {

    /**
     * 
     * (分页查询资源设置显示信息)<br>
     * 
     * @param param 查询参数
     * @return
     * @throws Exception
     */
    Page<Map<String, Object>> queryResSettingsHomeInfoPage(Map<String, Object> param) throws Exception;

    /**
     * 
     * (分页查询视频列表信息)<br>
     * 
     * @param param 查询参数
     * @param rows 每页大小
     * @param page 当前页
     * @return
     * @throws Exception
     */
    Page<Map<String, Object>> queryMediaPageInfo(Map<String, Object> param, Integer rows, Integer page)
                    throws Exception;

    /**
     * 
     * (添加或修改资源首页设置显示)<br>
     * 
     * @param entity 实体
     * @return
     */
    ResultCodeVo addResSettingsHome(ResSettingsHome entity);

    /**
     * 
     * 删除首页显示资源
     * 
     * @param id 主键id
     * @return
     */
    int deleteResSettingsHome(String id) throws Exception;

    /**
     * 
     * (修改资源在首页是否使用)<br>
     * 
     * @param id 主键id
     * @param setType 显示类别
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param flagAvailable 是否使用
     * @return
     * @throws Exception
     */
    ResultCodeVo changeFlagAvailable(String id, String setType, String sectionCode, String subjectCode,
                    Integer flagAvailable, String resSpecialTypeL2) throws Exception;

    /**
     * 
     * (分页查询特色资源列表信息)<br>
     * 
     * @param param
     * @param rows
     * @param page
     * @return
     * @throws Exception
     */
    Page<Map<String, Object>> queryMediaSpecialPageInfo(Map<String, Object> param, Integer rows, Integer page)
                    throws Exception;

}

package com.baizhitong.resource.dao.report;

import java.util.Map;

import com.baizhitong.common.Page;

/**
 * 
 * 作者统计DAO
 * 
 * @author creator zhangqiang 2016年7月19日 下午12:45:52
 * @author updater
 *
 * @version 1.0.0
 */
public interface TeacherResMakeDailyDao {

    /**
     * 
     * (查询作者统计)<br>
     * 
     * @param page 当前页
     * @param rows 每页大小
     * @param param 查询参数
     * @return
     */
    public Page select(Integer page, Integer rows, Map<String, Object> param);

    /**
     * 
     * (查询总数)<br>
     * 
     * @param param 查询参数
     * @return
     */
    public Map<String, Object> select(Map<String, Object> param);

    /**
     * 
     * (查询作者统计-机构管理员)<br>
     * 
     * @param page
     * @param rows
     * @param param
     * @return
     */
    public Page<Map<String, Object>> selectByOrg(Integer page, Integer rows, Map<String, Object> param);

    /**
     * 
     * (查询总数-机构管理员)<br>
     * 
     * @param param
     * @return
     */
    public Map<String, Object> selectByOrg(Map<String, Object> param);
}

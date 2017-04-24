package com.baizhitong.resource.dao.report;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;

/**
 * 
 * 机构_日次_资源操作统计DAO
 * 
 * @author creator zhangqiang 2016年7月26日 下午2:05:18
 * @author updater
 *
 * @version 1.0.0
 */
public interface OrgResOperateDailyDao {

    /**
     * 
     * (查询机构使用情况统计)<br>
     * 
     * @param param 查询参数
     * @param page 当前页数
     * @param rows 每页大小
     * @return
     */
    public Page getOrgDaily(Map<String, Object> param, Integer page, Integer rows);

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
     * (根据机构编码查询机构使用情况统计)<br>
     * 
     * @param param
     * @return
     */
    public List<Map<String, Object>> getOrgDailyByOrgCode(Map<String, Object> param);

    /**
     * 
     * (查询总数-机构管理员)<br>
     * 
     * @param param
     * @return
     */
    public Map<String, Object> selectByOrgCode(Map<String, Object> param);
}

package com.baizhitong.resource.dao.report;

import java.util.Map;

import com.baizhitong.common.Page;

/**
 * 
 * 使用情况详细DAO
 * 
 * @author creator zhangqiang 2016年7月20日 下午3:21:20
 * @author updater
 *
 * @version 1.0.0
 */
public interface UserResOperateDailyDao {

    /**
     * 
     * (分页查询使用情况详细统计信息)<br>
     * 
     * @param param 查询参数
     * @param page 当前页
     * @param rows 每页大小
     * @return
     */
    Page select(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 
     * (查询总数)<br>
     * 
     * @param param 查询参数
     * @return
     */
    Map<String, Object> select(Map<String, Object> param);
}

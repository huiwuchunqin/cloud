package com.baizhitong.resource.dao.report;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;

public interface ReportOrgResDailyDao {
    /**
     * 查询机构资源每日统计 ()<br>
     * 
     * @param page
     * @param rows
     * @param sqlParam
     * @return
     */
    public Page getReportOrgResDaily(Integer page, Integer rows, Map<String, Object> sqlParam);

    /**
     * 查询文档总数 ()<br>
     * 
     * @param sqlParam
     * @return
     */
    public List<Map<String, Object>> getDocSum(Map<String, Object> sqlParam);

    /**
     * 查询视频总数 ()<br>
     * 
     * @param sqlParam
     * @return
     */
    public List<Map<String, Object>> getMediaSum(Map<String, Object> sqlParam);

    /**
     * 查询问题总数 ()<br>
     * 
     * @param sqlParam
     * @return
     */
    public List<Map<String, Object>> getQuestionSum(Map<String, Object> sqlParam);

    /**
     * 查询练习总数 ()<br>
     * 
     * @param sqlParam
     * @return
     */
    public List<Map<String, Object>> getExerciseSum(Map<String, Object> sqlParam);

    /**
     * 查询文档列表 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getDocList(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 查询视频列表 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getMediaList(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 查询题目列表 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getQuestionList(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 查询试卷列表 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getExerciseList(Map<String, Object> param, Integer page, Integer rows);
}

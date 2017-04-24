package com.baizhitong.resource.dao.res;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.res.ResTestpaper;

/**
 * 资源_试卷数据接口
 * 
 * @author creator cuidc 2015/12/03
 * @author updater
 */
public interface ResTestpaperDao {
    /**
     * 获取试卷分页集合
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param versionCode 版本编码
     * @param tbcCode 教材章节编码
     * @param testpaperName 试卷名称
     * @param resTypeL2 资源分类(二级)
     * @param sort 排序方式(null:默认,10:最热,20:最新)
     * @param pageNo 当前页数
     * @param pageSize 每页条数
     * @return 试卷分页集合
     * @throws Exception 异常
     */
    public Page<Map<String, Object>> selectTestpaperPage(String sectionCode, String gradeCode, String subjectCode,
                    String versionCode, String tbcCode, String testpaperName, String resTypeL2, String sort,
                    Integer pageNo, Integer pageSize) throws Exception;

    /**
     * 获取试卷信息
     * 
     * @param testpaperId 试卷ID
     * @return 信息
     * @throws Exception 异常
     */
    public Map<String, Object> selectTestpaperInfo(Integer testpaperId) throws Exception;

    /**
     * 根据试卷ID获取试卷信息
     * 
     * @param testpaperId 试卷ID
     * @return 信息
     * @throws Exception 异常
     */
    public Map<String, Object> selectTestpaper(Integer testpaperId) throws Exception;

    /**
     * 获取试卷预览信息
     * 
     * @param testpaperId 试卷ID
     * @return 信息
     * @throws Exception 异常
     */
    public Map<String, Object> selectTestpaperPreviewInfo(Integer testpaperId) throws Exception;

    /**
     * 生成试卷
     * 
     * @param testpaperId 试卷ID
     * @param testpaperName 试卷名称
     * @param kpCode 知识点编码
     * @return
     * @throws Exception 异常
     */
    public void generateTestpaper(Integer testpaperId, String testpaperName, String kpCode) throws Exception;

    /**
     * 根据resID获取资源对象
     * 
     * @param resID 资源ID
     * @return 资源对象
     */
    public ResTestpaper selectResTestpaper(Integer resID);

    /**
     * 更新资源
     * 
     * @param resTestpaper 资源对象
     * @return 是否成功
     */
    public boolean updateTestpaperInfo(ResTestpaper resTestpaper);

    /**
     * 创建试卷
     * 
     * @param resTestpaper 试卷对象
     * @return 试卷ID
     * @throws Exception 异常
     */
    public Integer saveTestpaperInfo(ResTestpaper resTestpaper) throws Exception;

    /**
     * 根据学段编码、学科编码、基础题型编码获取题目学科题型信息
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param questionTypeCode 基础题型编码
     * @return 题目学科题型信息
     * @throws Exception 异常
     */
    public Map<String, Object> selectQuestionTypeSubject(String sectionCode, String subjectCode,
                    String questionTypeCode) throws Exception;
}

package com.baizhitong.resource.manage.res.service;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;

public interface LessonHomeSetService {
    /**
     * 查询首页课程 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    Page<Map<String,Object>> selectPage(Map<String, Object> param, Integer page, Integer rows);
    
    /**
     * 是否使用
     * ()<br>
     * @param id
     * @param available
     * @return
     */
    ResultCodeVo update(Integer id ,Integer available);
    
    /**
     * 
     * 设置为课程首页
     * @param lessonCode    课程编码
     * @param lessonId      课程id
     * @param orgCode       机构编码
     * @param sectionCode   学段编码
     * @param gradeCode     年级编码
     * @param subjectCode   学科编码
     * @param coverPath     封面地址
     * @param thumbnailPath 缩略图地址
     */
    ResultCodeVo setCourseHome(String lessonName,String lessonCode,Integer lessonId,String orgCode,String sectionCode,String gradeCode,String subjectCode,String coverPath,String thumbnailPath,boolean available);
    
    /**
     * 删除
     * ()<br>
     * @param id
     * @return
     */
    ResultCodeVo delete(Integer id);

    /**
     * 
     * (查询可以设置在首页显示的课程信息)<br>
     * @param param 查询参数
     * @param page 当前页码
     * @param rows 每页大小
     * @return 课程信息列表
     */
    Page<Map<String, Object>> queryLessonHomeSettingPage(Map<String, Object> param, Integer page, Integer rows);
    
}

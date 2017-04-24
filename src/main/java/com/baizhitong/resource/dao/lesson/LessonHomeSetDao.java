package com.baizhitong.resource.dao.lesson;

import java.util.Map;

import com.baizhitong.common.Page;

public interface LessonHomeSetDao {
    /**
     * 查询首页课程 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
     Page<Map<String,Object>> selectPage(Map<String, Object> param, Integer page, Integer rows) ;
    
    /**
     * 更新使用状态
     * ()<br>
     * @param id
     * @param avaliable
     * @return
     */
     int update(Integer id,Integer avaliable);
     
     /**
      * 
      * 设置为课程首页
      * @param lessonName 课程名称
      * @param lessonCode    课程编码
      * @param lessonId      课程id
      * @param orgCode       机构编码
      * @param sectionCode   学段编码
      * @param gradeCode     年级编码
      * @param subjectCode   学科编码
      * @param coverPath     封面地址
      * @param thumbnailPath 缩略图地址
      * @param userName      用户姓名
      */
     int setCourseHome(String lessonName,String lessonCode,Integer lessonId,String orgCode,String sectionCode,String gradeCode,String subjectCode,String coverPath,String thumbnailPath,String userName,boolean available);
     
     /**
      * 删除
      * ()<br>
      * @param id
      * @return
      */
     int delete(Integer id);
     /**
      * 查询首页课程数
      * ()<br>
      * @return
      */
     long selectAvailableCount();
}

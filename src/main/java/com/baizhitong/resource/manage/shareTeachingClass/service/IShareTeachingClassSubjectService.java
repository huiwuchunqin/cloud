package com.baizhitong.resource.manage.shareTeachingClass.service;

import java.util.List;

import com.baizhitong.resource.model.vo.share.ShareTeachingClassSubjectVo;

public interface IShareTeachingClassSubjectService {
    /**
     * 查询教学班级学科列表 ()<br>
     * 
     * @param teachingClassCode
     * @param subjectCode
     * @param gradeCode
     * @param teacherCode
     * @param orgCode
     * @param studyYearCode
     * @param termCode
     * @return
     */
    List<ShareTeachingClassSubjectVo> getShareTeachingClassSubjectList(String teachingClassCode, String subjectCode,
                    String gradeCode, String teacherCode, String orgCode, String studyYearCode, String termCode);
}

package com.baizhitong.resource.manage.shareTeachingClass.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.dao.share.ShareTeachingClassSubjectDao;
import com.baizhitong.resource.manage.shareTeachingClass.service.IShareTeachingClassSubjectService;
import com.baizhitong.resource.model.vo.share.ShareTeachingClassSubjectVo;

@Service
public class ShareTeachingClassSubjectServiceImpl implements IShareTeachingClassSubjectService {
    @Autowired
    private ShareTeachingClassSubjectDao teachingClassSubjectDao;

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
    public List<ShareTeachingClassSubjectVo> getShareTeachingClassSubjectList(String teachingClassCode,
                    String subjectCode, String gradeCode, String teacherCode, String orgCode, String studyYearCode,
                    String termCode) {
        List<Map<String, Object>> mapList = teachingClassSubjectDao.getTeachingClassSubject(teachingClassCode,
                        subjectCode, gradeCode, teacherCode, orgCode, studyYearCode, termCode);
        return ShareTeachingClassSubjectVo.mapListToVo(mapList);
    }
}

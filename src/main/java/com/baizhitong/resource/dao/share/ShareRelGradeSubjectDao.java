package com.baizhitong.resource.dao.share;

import java.util.List;

import com.baizhitong.resource.model.share.ShareRelGradeSubject;

public interface ShareRelGradeSubjectDao {
    /**
     * 删除年级学科关系
     * 
     * @param gradeCode 年级编码
     * @author gaow
     * @date:2015年12月15日 下午2:08:31
     */
    public void delGradeSubject(String gradeCode);

    /**
     * 保存学段年级关系
     * 
     * @param relSectionGradeList
     * @author gaow
     * @date:2015年12月15日 下午2:10:42
     */
    public void saveGradeSubjectRef(List<ShareRelGradeSubject> relGradeSubjectList) throws Exception;

}

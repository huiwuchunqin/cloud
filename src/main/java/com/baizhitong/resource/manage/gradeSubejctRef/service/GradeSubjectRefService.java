package com.baizhitong.resource.manage.gradeSubejctRef.service;

import java.util.List;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.vo.share.SectionGradeVo;
import com.baizhitong.resource.model.vo.share.SectionSubjectVo;
import com.baizhitong.resource.model.vo.share.ShareCodeSubjectVo;

/**
 * 年级学科关系业务接口
 * 
 * @author gaow
 * @date:2015年12月14日 下午4:48:13
 */
public interface GradeSubjectRefService {
    /**
     * 年级学科
     * 
     * @param gradeCode 年级编码
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 上午11:35:04
     */
    public List<ShareCodeSubjectVo> getGradeSubjectList(String gradeCode) throws Exception;

    /**
     * 年级学科分页信息
     * 
     * @param gradeCode 年级代码
     * @param pageNo 页码
     * @param pageSize 每页记录数
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 下午2:00:07
     */
    public Page<ShareCodeSubjectVo> getGradeSubjectPage(String gradeCode, Integer pageNo, Integer pageSize)
                    throws Exception;

    /**
     * 学段年级设置列表
     * 
     * @param gradeCode 学段代码
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 上午11:35:04
     */
    public List<SectionSubjectVo> gradeSubjectRefSetList(String gradeCode, String sectionCode) throws Exception;

    /**
     * 保存学段年级关系
     * 
     * @param sectionCode 学段代码
     * @param gradeCodeArray 年级编码数组
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 下午2:00:07
     */
    public void saveGradeSubjectList(String gradeCode, String[] subjectCodeArray) throws Exception;

}

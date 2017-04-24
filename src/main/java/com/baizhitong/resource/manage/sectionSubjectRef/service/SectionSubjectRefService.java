package com.baizhitong.resource.manage.sectionSubjectRef.service;

import java.util.List;
import java.util.Map;

import com.baizhitong.resource.model.vo.share.SectionSubjectVo;

/**
 * 学段学科关系业务接口
 * 
 * @author gaow
 * @date:2015年12月14日 下午4:48:13
 */
public interface SectionSubjectRefService {
    /**
     * 学段学科设置列表
     * 
     * @param sectionCode 学段代码
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 上午11:35:04
     */
    public List<SectionSubjectVo> sectionSubjectRefSetList(String sectionCode) throws Exception;

    /**
     * 保存学段学科关系
     * 
     * @param sectionCode 学段代码
     * @param subjectCodeArray 学科编码数组
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 下午2:00:07
     */
    public void saveSectionSubjectList(String sectionCode, String[] subjectCodeArray) throws Exception;

    /**
     * 
     * (根据学段查询学段学科关系列表)<br>
     * 
     * @param sectionCode
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> querySectionSubjectRelList(String sectionCode) throws Exception;

}

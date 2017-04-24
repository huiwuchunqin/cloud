package com.baizhitong.resource.manage.section.service;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.share.ShareCodeSection;
import com.baizhitong.resource.model.vo.share.ShareCodeGradeVo;
import com.baizhitong.resource.model.vo.share.ShareCodeSectionVo;
import com.baizhitong.resource.model.vo.share.ShareCodeSubjectVo;

/**
 * 学段业务接口
 * 
 * @author gaow
 * @date:2015年12月14日 下午4:47:39
 */
public interface SectionService {
    /**
     * 分页查询学段信息
     * 
     * @param param 查询条件
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月14日 下午4:55:00
     */
    public Page<Map<String, Object>> selectSectionPage(Map<String, Object> param, Integer pageSize, Integer pageNo)
                    throws Exception;

    /**
     * 查询学段信息列表
     * 
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 下午1:54:45
     */
    public List<ShareCodeSection> selectSectionList() throws Exception;

    /**
     * 查询学段学科信息
     * 
     * @param sectionCode 学段代码
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 上午11:02:29
     */
    public List<ShareCodeSubjectVo> getSectionSubject(String sectionCode) throws Exception;

    /**
     * 查询学段年级信息
     * 
     * @param sectionCode 学段代码
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 上午11:03:31
     */
    public List<ShareCodeGradeVo> getSectionGrade(String sectionCode) throws Exception;

    /**
     * 获取资源相关学段
     * 
     * @param resId
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月22日 下午7:46:35
     */
    public ShareCodeSectionVo getResRelateSection(Integer resId, Integer resTypeL1) throws Exception;

    /**
     * 查询学段学科信息
     * 
     * @param sectionCode 学段代码
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 上午11:02:29
     */
    public Page<ShareCodeSubjectVo> getSectionSubjectPage(String sectionCode, Integer pageSize, Integer pageNo)
                    throws Exception;

    /**
     * 查询学段年级信息
     * 
     * @param sectionCode 学段代码
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 上午11:03:31
     */
    public Page<ShareCodeGradeVo> getSectionGradePage(String sectionCode, Integer pageSize, Integer pageNo)
                    throws Exception;

    /**
     * 
     * (获取用户学段列表)<br>
     * 
     * @param userCode
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryUserSectionList(String userCode) throws Exception;
}

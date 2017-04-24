package com.baizhitong.resource.manage.section.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.authority.SettingUserPriviledgeGradeDao;
import com.baizhitong.resource.dao.authority.SettingUserPriviledgeSectionDao;
import com.baizhitong.resource.dao.authority.SettingUserPriviledgeSubjectDao;
import com.baizhitong.resource.dao.share.ShareCodeSectionDao;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.model.share.ShareCodeSection;
import com.baizhitong.resource.model.vo.share.ShareCodeGradeVo;
import com.baizhitong.resource.model.vo.share.ShareCodeSectionVo;
import com.baizhitong.resource.model.vo.share.ShareCodeSubjectVo;

/**
 * 学段业务接口实现
 * 
 * @author gaow
 * @date:2015年12月14日 下午4:48:00
 */
@Service(value = "sectionService")
public class SectionServiceImpl extends BaseService implements SectionService {
    private @Autowired ShareCodeSectionDao             shareCodeSectionDao;
    private @Autowired SettingUserPriviledgeSectionDao settingUserPriviledgeSectionDao;
    private @Autowired SettingUserPriviledgeSubjectDao settingUserPriviledgeSubjectDao;
    private @Autowired SettingUserPriviledgeGradeDao   settingUserPriviledgeGradeDao;

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
    public Page<Map<String, Object>> selectSectionPage(Map param, Integer pageSize, Integer pageNo) throws Exception {
        return shareCodeSectionDao.selectSectionPage(param, pageSize, pageNo);
    }

    /**
     * 查询学段信息列表
     * 
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 下午1:54:45
     */
    public List<ShareCodeSection> selectSectionList() throws Exception {
        return shareCodeSectionDao.selectSectionList();
    }

    /**
     * 查询学段学科信息
     * 
     * @param sectionCode 学段代码
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 上午11:02:29
     */
    public List<ShareCodeSubjectVo> getSectionSubject(String sectionCode) throws Exception {
       
        UserInfoVo userInfo =getUserInfoVo();
        // 教研员登录
        if (isEduStaff()) {
            List<Map<String, Object>> mapList = settingUserPriviledgeSubjectDao
                            .selectSubjectListByUserCode(userInfo.getUserCode(), sectionCode);
            List<ShareCodeSubjectVo> voList = new ArrayList<ShareCodeSubjectVo>();
            if (mapList != null && mapList.size() > 0) {
                for (Map<String, Object> map : mapList) {
                    ShareCodeSubjectVo vo = ShareCodeSubjectVo.mapToVo(map);
                    voList.add(vo);
                }
            }
            return voList;
        } else {
            List<Map<String, Object>> subjectList = shareCodeSectionDao.getSectionSubject(sectionCode);
            ShareCodeSubjectVo subjectVo = new ShareCodeSubjectVo();
            return subjectVo.getMapToVoList(subjectList);
        }

    }

    /**
     * 查询学段年级信息
     * 
     * @param sectionCode 学段代码
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 上午11:03:31
     */
    public List<ShareCodeGradeVo> getSectionGrade(String sectionCode) throws Exception {
       
        UserInfoVo userInfo =getUserInfoVo();
        // 教研员登录
        if (isEduStaff()) {
            List<Map<String, Object>> mapList = settingUserPriviledgeGradeDao
                            .selectGradeListByUserCode(userInfo.getUserCode(), sectionCode);
            List<ShareCodeGradeVo> voList = new ArrayList<ShareCodeGradeVo>();
            if (mapList != null && mapList.size() > 0) {
                for (Map<String, Object> map : mapList) {
                    ShareCodeGradeVo vo = ShareCodeGradeVo.mapToVo(map);
                    voList.add(vo);
                }
            }
            return voList;
        } else {
            List<Map<String, Object>> gradeList = shareCodeSectionDao.getSectionGrade(sectionCode);
            ShareCodeGradeVo gradeVo = new ShareCodeGradeVo();
            return gradeVo.getMapToVoList(gradeList);
        }
    }

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
                    throws Exception {
        Page page = shareCodeSectionDao.getSectionSubjectPage(sectionCode, pageNo, pageSize);
        List<Map<String, Object>> subjectList = page.getRows();
        ShareCodeSubjectVo subjectVo = new ShareCodeSubjectVo();
        if (subjectList != null && subjectList.size() > 0)
            page.setRows(subjectVo.getMapToVoList(subjectList));
        return page;

    }

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
                    throws Exception {
        Page page = shareCodeSectionDao.getSectionGradePage(sectionCode, pageSize, pageNo);
        List<Map<String, Object>> gradeList = page.getRows();
        ShareCodeGradeVo gradeVo = new ShareCodeGradeVo();
        if (gradeList != null && gradeList.size() > 0)
            page.setRows(gradeVo.getMapToVoList(gradeList));
        return page;
    }

    /**
     * 获取资源相关学段
     * 
     * @param resId
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月22日 下午7:46:35
     */
    public ShareCodeSectionVo getResRelateSection(Integer resId, Integer resTypeL1) throws Exception {
        List<Map<String, Object>> sectionList = shareCodeSectionDao.getResRelateSection(resId, resTypeL1);
        ShareCodeSectionVo sectionVo = new ShareCodeSectionVo();
        List<ShareCodeSectionVo> list = sectionVo.getMapToVoList(sectionList);
        if (list != null && list.size() > 0)
            return list.get(0);
        return null;
    }

    /**
     * 
     * (获取用户学段列表)<br>
     * 
     * @param userCode
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> queryUserSectionList(String userCode) throws Exception {
        return settingUserPriviledgeSectionDao.selectSectionListByUserCode(userCode);
    }

}

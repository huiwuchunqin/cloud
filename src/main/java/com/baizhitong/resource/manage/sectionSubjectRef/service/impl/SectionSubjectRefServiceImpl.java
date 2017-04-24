package com.baizhitong.resource.manage.sectionSubjectRef.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.share.ShareRelSectionSubjectDao;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.manage.sectionSubjectRef.service.SectionSubjectRefService;
import com.baizhitong.resource.manage.subject.service.SubjectService;
import com.baizhitong.resource.model.share.ShareRelSectionSubject;
import com.baizhitong.resource.model.vo.share.SectionSubjectVo;
import com.baizhitong.resource.model.vo.share.ShareCodeSubjectVo;
import com.baizhitong.utils.DateUtils;

/**
 * 学段学科关系业务接口实现
 * 
 * @author gaow
 * @date:2015年12月14日 下午4:48:13
 */
@Service(value = "sectionSubjectRefService")
public class SectionSubjectRefServiceImpl extends BaseService implements SectionSubjectRefService {
    @Autowired
    SectionService            sectionService;       // 学段业务接口
    @Autowired
    SubjectService            subjectService;       // 学科业务接口
    @Autowired
    ShareRelSectionSubjectDao relSectionSubjectDao; // 学段和学科关系业务接口

    /**
     * 学段学科设置列表
     * 
     * @param sectionCode 学段代码
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 上午11:35:04
     */
    public List<SectionSubjectVo> sectionSubjectRefSetList(String sectionCode) throws Exception {
        List<ShareCodeSubjectVo> allSubjectList = subjectService.selectSubjectList(sectionCode);
        List<ShareCodeSubjectVo> selectSubjectList = sectionService.getSectionSubject(sectionCode);
        return dealWidthSectionSubjectRef(allSubjectList, selectSubjectList);
    }

    /**
     * 学段学科勾选操作
     * 
     * @param allSubjectList 所有学科
     * @param selectSubjectList 选中的学科
     * @return
     * @author gaow
     * @date:2015年12月15日 上午11:14:48
     */
    private List<SectionSubjectVo> dealWidthSectionSubjectRef(List<ShareCodeSubjectVo> allSubjectList,
                    List<ShareCodeSubjectVo> selectSubjectList) {
        if (allSubjectList == null || allSubjectList.size() <= 0)
            return null;
        List<SectionSubjectVo> sectionSubjectVoList = new ArrayList<SectionSubjectVo>();
        for (ShareCodeSubjectVo subject : allSubjectList) {
            SectionSubjectVo sectionSubjectVo = new SectionSubjectVo(subject);
            sectionSubjectVoList.add(sectionSubjectVo);
        }
        if (selectSubjectList == null)
            return sectionSubjectVoList;
        for (SectionSubjectVo sectionSubjectVo : sectionSubjectVoList) {
            for (ShareCodeSubjectVo subject : selectSubjectList) {
                if (sectionSubjectVo.getSubjectCode().equals(subject.getCode())) {
                    sectionSubjectVo.setSelected(1);
                }
            }
        }
        return sectionSubjectVoList;
    }

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
    public void saveSectionSubjectList(String sectionCode, String[] subjectCodeArray) throws Exception {
       
        UserInfoVo userInfoVo =getUserInfoVo();
        String ip = getIp();
        List<ShareRelSectionSubject> relSectionSubjectList = new ArrayList<ShareRelSectionSubject>();
        if (subjectCodeArray != null) {
            for (int i = 0; i < subjectCodeArray.length; i++) {
                ShareRelSectionSubject relSectionSubject = new ShareRelSectionSubject(UUID.randomUUID().toString(),
                                sectionCode, subjectCodeArray[i], "sectionSubjectRefService",
                                DateUtils.getTimestamp(DateUtils.formatDate(new Date())), ip, 0);
                relSectionSubjectList.add(relSectionSubject);
            }
        }
        relSectionSubjectDao.delSectionSubject(sectionCode);// 删除原来的关系
        relSectionSubjectDao.saveSectionSubjectRef(relSectionSubjectList);// 保存新的关系
    }

    /**
     * 
     * (根据学段查询学段学科关系列表)<br>
     * 
     * @param sectionCode
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> querySectionSubjectRelList(String sectionCode) throws Exception {
        return relSectionSubjectDao.selectListBySectionCode(sectionCode);
    }

}

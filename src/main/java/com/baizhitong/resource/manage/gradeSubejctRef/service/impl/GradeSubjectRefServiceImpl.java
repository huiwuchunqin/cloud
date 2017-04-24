package com.baizhitong.resource.manage.gradeSubejctRef.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.share.ShareCodeGradeDao;
import com.baizhitong.resource.dao.share.ShareRelGradeSubjectDao;
import com.baizhitong.resource.manage.gradeSubejctRef.service.GradeSubjectRefService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.manage.subject.service.SubjectService;
import com.baizhitong.resource.model.share.ShareRelGradeSubject;
import com.baizhitong.resource.model.vo.share.SectionSubjectVo;
import com.baizhitong.resource.model.vo.share.ShareCodeSubjectVo;
import com.baizhitong.utils.DateUtils;

/**
 * 学段年级关系业务接口实现
 * 
 * @author gaow
 * @date:2015年12月14日 下午4:48:13
 */
@Service
public class GradeSubjectRefServiceImpl extends BaseService implements GradeSubjectRefService {
    private @Autowired ShareCodeGradeDao       gradeDao;           // 年级接口
    @Autowired
    SubjectService                             subjectService;     // 学科业务接口
    @Autowired
    SectionService                             sectionService;     // 学科业务接口
    private @Autowired ShareRelGradeSubjectDao relGradeSubjectDao; // 学段和年级关系业务接口

    /**
     * 年级学科
     * 
     * @param gradeCode 年级编码
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 上午11:35:04
     */
    public List<ShareCodeSubjectVo> getGradeSubjectList(String gradeCode) throws Exception {
        List<Map<String, Object>> gradeList = gradeDao.getGradeSubject(gradeCode);
        ShareCodeSubjectVo vo = new ShareCodeSubjectVo();
        return vo.getMapToVoList(gradeList);
    }

    /**
     * 年级学科分页信息
     * 
     * @param gradeCode 学段代码
     * @param pageNo 页码
     * @param pageSize 每页记录数
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 下午2:00:07
     */
    public Page<ShareCodeSubjectVo> getGradeSubjectPage(String gradeCode, Integer pageNo, Integer pageSize)
                    throws Exception {
        Page page = gradeDao.getGradeSubjectPage(gradeCode, pageNo, pageSize);
        if (page == null)
            return null;
        List<Map<String, Object>> gradeList = page.getRows();
        ShareCodeSubjectVo vo = new ShareCodeSubjectVo();
        page.setRows(vo.getMapToVoList(gradeList));
        return page;
    }

    /**
     * 查询年级学科 ()<br>
     * 
     * @param gradeCode
     * @return
     * @throws Exception
     */
    public List<SectionSubjectVo> gradeSubjectRefSetList(String gradeCode, String sectionCode) throws Exception {
        List<ShareCodeSubjectVo> allSubjectList = sectionService.getSectionSubject(sectionCode);
        List<ShareCodeSubjectVo> selectSubjectList = getGradeSubjectList(gradeCode);
        return dealWidthGradeSubjectRef(allSubjectList, selectSubjectList);
    }

    /**
     * 
     * 保存年级学科
     * 
     * @param gradeCode
     * @param subjectCodeArray
     * @throws Exception
     */
    public void saveGradeSubjectList(String gradeCode, String[] subjectCodeArray) throws Exception {
       
        UserInfoVo userInfoVo =getUserInfoVo();
        String ip = getIp();
        List<ShareRelGradeSubject> relSectionSubjectList = new ArrayList<ShareRelGradeSubject>();
        if (subjectCodeArray != null) {
            for (int i = 0; i < subjectCodeArray.length; i++) {
                ShareRelGradeSubject shareRelGradeSubject = new ShareRelGradeSubject(UUID.randomUUID().toString(),
                                gradeCode, subjectCodeArray[i], "gradeSubjectRefService",
                                DateUtils.getTimestamp(DateUtils.formatDate(new Date())), ip, 0);
                relSectionSubjectList.add(shareRelGradeSubject);
            }
        }
        relGradeSubjectDao.delGradeSubject(gradeCode);
        relGradeSubjectDao.saveGradeSubjectRef(relSectionSubjectList);// 保存新的关系

    }

    /**
     * 年级学科勾选操作
     * 
     * @param allSubjectList 所有学科
     * @param selectSubjectList 选中的学科
     * @return
     * @author gaow
     * @date:2015年12月15日 上午11:14:48
     */
    private List<SectionSubjectVo> dealWidthGradeSubjectRef(List<ShareCodeSubjectVo> allSubjectList,
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

}

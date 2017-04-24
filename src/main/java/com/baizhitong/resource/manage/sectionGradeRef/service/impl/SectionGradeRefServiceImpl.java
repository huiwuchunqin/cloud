package com.baizhitong.resource.manage.sectionGradeRef.service.impl;

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
import com.baizhitong.resource.dao.share.ShareCodeGradeDao;
import com.baizhitong.resource.dao.share.ShareRelSectionGradeDao;
import com.baizhitong.resource.manage.grade.service.GradeService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.manage.sectionGradeRef.service.SectionGradeRefService;
import com.baizhitong.resource.model.share.ShareRelSectionGrade;
import com.baizhitong.resource.model.vo.share.SectionGradeVo;
import com.baizhitong.resource.model.vo.share.ShareCodeGradeVo;
import com.baizhitong.utils.DateUtils;

/**
 * 学段年级关系业务接口实现
 * 
 * @author gaow
 * @date:2015年12月14日 下午4:48:13
 */
@Service(value = "sectionGradeRefService")
public class SectionGradeRefServiceImpl extends BaseService implements SectionGradeRefService {
    private @Autowired GradeService            gradeService;       // 年级业务接口
    private @Autowired SectionService          sectionService;     // 学段业务接口
    private @Autowired ShareRelSectionGradeDao relSectionGradeDao; // 学段和年级关系业务接口
    private @Autowired ShareCodeGradeDao       gradeDao;           // 年级接口

    /**
     * 学段年级设置列表
     * 
     * @param sectionCode
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 上午11:35:04
     */
    public List<SectionGradeVo> sectionGradeRefSetList(String sectionCode) throws Exception {
        List<ShareCodeGradeVo> allGradeList = gradeService.selectGradeList(sectionCode);
        List<ShareCodeGradeVo> selectGradeList = sectionService.getSectionGrade(sectionCode);
        return dealWidthSectionGradeRef(allGradeList, selectGradeList);
    }

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
    public void saveSectionGradeList(String sectionCode, String[] gradeCodeArray) throws Exception {
       
        UserInfoVo userInfoVo =getUserInfoVo();
        String ip = getIp();
        List<ShareRelSectionGrade> relSectionGradeList = new ArrayList<ShareRelSectionGrade>();
        if (gradeCodeArray != null) {
            for (int i = 0; i < gradeCodeArray.length; i++) {
                ShareRelSectionGrade relSectionGrade = new ShareRelSectionGrade(UUID.randomUUID().toString(),
                                sectionCode, gradeCodeArray[i], "sectionGradeRefService",
                                DateUtils.getTimestamp(DateUtils.formatDate(new Date())), ip, 0);
                relSectionGradeList.add(relSectionGrade);
            }
        }
        relSectionGradeDao.delSectionGrade(sectionCode);// 删除已有关系
        relSectionGradeDao.saveSectionGradeRef(relSectionGradeList);// 建立新的关系

    }

    /**
     * 学段年级勾选操作
     * 
     * @param allGradeList 所有年级
     * @param selectGradeList 选中的年级
     * @return
     * @author gaow
     * @date:2015年12月15日 上午11:14:48
     */
    private List<SectionGradeVo> dealWidthSectionGradeRef(List<ShareCodeGradeVo> allGradeList,
                    List<ShareCodeGradeVo> selectGradeList) {
        if (allGradeList == null || allGradeList.size() <= 0)
            return null;
        List<SectionGradeVo> sectionGradeVoList = new ArrayList<SectionGradeVo>();
        for (ShareCodeGradeVo grade : allGradeList) {
            SectionGradeVo sectionGradeVo = new SectionGradeVo(grade);
            sectionGradeVoList.add(sectionGradeVo);
        }
        if (selectGradeList == null)
            return sectionGradeVoList;
        for (SectionGradeVo sectionGradeVo : sectionGradeVoList) {
            for (ShareCodeGradeVo grade : selectGradeList) {
                if (sectionGradeVo.getGradeCode().equals(grade.getCode())) {
                    sectionGradeVo.setSelected(1);
                }
            }
        }
        return sectionGradeVoList;
    }

    @Override
    public List<Map<String, Object>> queryListBySectionCode(String sectionCode) throws Exception {
        return relSectionGradeDao.selectGradeListBySectionCode(sectionCode);
    }

}

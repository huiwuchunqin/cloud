package com.baizhitong.resource.manage.res.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.rel.RelResGradeDao;
import com.baizhitong.resource.dao.rel.RelResKpDao;
import com.baizhitong.resource.dao.rel.RelResSectionDao;
import com.baizhitong.resource.dao.rel.RelResSubjectDao;
import com.baizhitong.resource.dao.rel.RelResTbcDao;
import com.baizhitong.resource.dao.rel.RelResTbvDao;
import com.baizhitong.resource.dao.res.ResFlashDao;
import com.baizhitong.resource.dao.res.ResShareCheckDao;
import com.baizhitong.resource.manage.authority.service.CommonService;
import com.baizhitong.resource.manage.res.service.FlashService;
import com.baizhitong.resource.model.rel.RelResGrade;
import com.baizhitong.resource.model.rel.RelResKp;
import com.baizhitong.resource.model.rel.RelResSection;
import com.baizhitong.resource.model.rel.RelResSubject;
import com.baizhitong.resource.model.rel.RelResTbc;
import com.baizhitong.resource.model.rel.RelResTbv;
import com.baizhitong.resource.model.res.ResFlash;
import com.baizhitong.resource.model.res.ResMedia;
import com.baizhitong.resource.model.vo.res.ResVo;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

/**
 * flash接口 FlashServiceImpl TODO
 * 
 * @author creator gaow 2016年12月20日 上午10:01:55
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class FlashServiceImpl extends BaseService implements FlashService {
    // flash dao
    @Autowired
    ResFlashDao                         flashDao;
    // 校验严权限接口
    @Autowired
    CommonService                       commonService;
    private @Autowired RelResGradeDao   relResGradeDao;
    private @Autowired RelResSubjectDao relResSubjectDao;
    private @Autowired RelResSectionDao relResSectionDao;
    private @Autowired RelResTbcDao     relResTbcDao;
    private @Autowired RelResTbvDao     relResTbvDao;
    private @Autowired RelResKpDao      relResKpDao;
    private @Autowired ResShareCheckDao resShareCheckDao;

    /**
     * 查询flash ()<br>
     * 
     * @param param 查询参数
     * @param page 页码
     * @param rows 行数
     * @return page
     */
    public Page selectFlash(Map<String, Object> param, Integer page, Integer rows) {
       
        UserInfoVo userInfoVo =getUserInfoVo();
        if (isEduStaff()) {
            String sectionCodes = commonService.queryUserAuthSectionCodes(userInfoVo.getUserCode());
            String subjectCodes = commonService.queryUserAuthSubjectCodes(userInfoVo.getUserCode());
            String gradeCodes = commonService.queryUserAuthGradeCodes(userInfoVo.getUserCode());
            param.put("sectionCodes", sectionCodes);
            param.put("subjectCodes", subjectCodes);
            param.put("gradeCodes", gradeCodes);
        }
        Page page2 = flashDao.selectFlash(param, page, rows);
        if (page2 == null)
            return page2;
        List<Map<String, Object>> mapList = page2.getRows();
        List<ResVo> voList = ResVo.getVoListFromMapList(mapList);
        if (voList != null && voList.size() > 0) {
            for (ResVo vo : voList) {
                Map<String, Object> map = resShareCheckDao.getRecentlyInfo(vo.getResCode(), vo.getShareCheckStatus());
                vo.setCheckComments(MapUtils.getString(map, "checkComments"));
                vo.setCheckerName(MapUtils.getString(map, "checkerName"));
                vo.setApplyTime(MapUtils.getString(map, "applyTime"));
            }
        }
        page2.setRows(voList);
        return page2;
    }

    /**
     * 查询flash ()<br>
     * 
     * @param resCode 资源编码
     * @return flash
     */
    public ResVo getFlash(String resCode) {
        Map<String, Object> map = flashDao.getFlash(resCode);
        ResVo vo = new ResVo(map);
        return vo;
    }

    /**
     * 
     * 更新互动资源
     * 
     * @param resId 资源id
     * @param resDesc 资源描述
     * @param resName 资源名称
     * @param userCode 用户编码
     * @param userName 用户名称
     * @param ip ip
     * @param kpCodes 知识点
     * @param tbcCodes 章节
     * @param gradeCodes 年级
     * @param subejctCodes 学科
     * @param sectionCodes 学段
     * @param uploader 上传人姓名
     * @param makerOrgCode 制作机构编码
     * @param makerOrgName 制作机构名称
     * @param makerCode 上传人编码
     * @param flagAllowDownload 允许下载
     * @param coverPath 封面地址
     * @param tags 标签
     * @return
     * @throws Exception
     */
    public ResultCodeVo flashInfoUpdate(Integer resId, String resDesc, String resName, String userCode, String userName,
                    String ip, String kpCodes, String tbcCodes, String gradeCodes, String subejctCodes,
                    String sectionCodes, String uploader, String makerOrgCode, String makerOrgName, String makerCode,
                    Integer flagAllowDownload, String coverPath, String tags) throws Exception {
        try {
            ResFlash flash = flashDao.selectFlash(resId);
            flash.setModifier(userName);
            flash.setModifierIP(ip);
            flash.setModifyTime(DateUtils.getTimestamp(
                            DateUtils.formatDate(DateUtils.getTimestamp(DateUtils.formatDate(new Date())))));
            flash.setResDesc(resDesc);
            flash.setResName(resName);
            flash.setMakerCode(makerCode);
            flash.setMakerName(uploader);
            flash.setTags(tags);
            flash.setFlagAllowDownload(flagAllowDownload);
            flash.setCoverPath(coverPath);
            flash.setMakerOrgCode(makerOrgCode);
            flash.setMakerOrgName(makerOrgName);
            flashDao.save(flash);
            String resCode = flash.getResCode();
            /************** 关系表保存 *****************/
            relResGradeDao.delResResGrade(resId); // 删除原本年级关系
            relResSubjectDao.delResResSubject(resId); // 删除原本学科关系
            relResSectionDao.delResResSection(resId); // 删除原本学段关系
            relResKpDao.delRelResKp(resId); // 删除原本知识点关系
            relResTbcDao.delRelResTbc(resId); // 删除原本教材章节关系
            if (!StringUtils.isEmpty(tbcCodes)) { // 教材版本关系保存
                String[] tbcCodeArray = tbcCodes.split(",");
                for (String tbcCode : tbcCodeArray) {
                    RelResTbc tbc = new RelResTbc(null, CoreConstants.RES_TYPE_FLASH, resId, flash.getResCode(),
                                    tbcCode, userName, DateUtils.getTimestamp(DateUtils.formatDate(new Date())), ip); 
                    relResTbcDao.saveRelResTbc(tbc);
                }
            }
            if (!StringUtils.isEmpty(kpCodes)) { // 教材版本关系保存
                String[] kpCodeArray = kpCodes.split(",");
                for (String kpCode : kpCodeArray) {
                    RelResKp kp = new RelResKp(null, CoreConstants.RES_TYPE_FLASH, resId, flash.getResCode(), kpCode,
                                    userName, DateUtils.getTimestamp(DateUtils.formatDate(new Date())), ip);
                    relResKpDao.saveRelResKp(kp);
                }
            }
            if (!StringUtils.isEmpty(sectionCodes)) { // 学段关系保存
                String[] sectionCodeArray = sectionCodes.split(",");
                for (String code : sectionCodeArray) {
                    if (!"-1".equals(code)) {
                        RelResSection section = new RelResSection(null, CoreConstants.RES_TYPE_FLASH, resId, resCode,
                                        code, userName, DateUtils.getTimestamp(DateUtils.formatDate(new Date())), ip);
                        relResSectionDao.saveRelResSection(section);
                    }
                }
            }
            if (!StringUtils.isEmpty(gradeCodes)) { // 年级关系保存
                String[] gradeCodeArray = gradeCodes.split(",");
                for (String code : gradeCodeArray) {
                    if (!"-1".equals(code)) {
                        RelResGrade grade = new RelResGrade(null, CoreConstants.RES_TYPE_FLASH, resId, resCode, code,
                                        "", userName, DateUtils.getTimestamp(DateUtils.formatDate(new Date())), ip);
                        relResGradeDao.saveRelResGrade(grade);
                    }
                }
            }
            if (!StringUtils.isEmpty(subejctCodes)) { // 学科关系保存
                String[] subejctCodeArray = subejctCodes.split(",");
                for (String code : subejctCodeArray) {
                    if (!"-1".equals(code)) {
                        RelResSubject subject = new RelResSubject(null, CoreConstants.RES_TYPE_FLASH, resId, resCode,
                                        code, userName, DateUtils.getTimestamp(DateUtils.formatDate(new Date())), ip);
                        relResSubjectDao.saveRelResSubject(subject);
                    }
                }
            }
            return ResultCodeVo.rightCode("互动资源更新成功");
        } catch (Exception e) {
            throw e;
        }
    }

}

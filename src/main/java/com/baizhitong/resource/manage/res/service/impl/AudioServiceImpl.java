package com.baizhitong.resource.manage.res.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.dao.rel.RelResGradeDao;
import com.baizhitong.resource.dao.rel.RelResKpDao;
import com.baizhitong.resource.dao.rel.RelResSectionDao;
import com.baizhitong.resource.dao.rel.RelResSubjectDao;
import com.baizhitong.resource.dao.rel.RelResTbcDao;
import com.baizhitong.resource.dao.rel.RelResTbvDao;
import com.baizhitong.resource.dao.res.ResAudioDao;
import com.baizhitong.resource.dao.res.ResShareCheckDao;
import com.baizhitong.resource.manage.authority.service.CommonService;
import com.baizhitong.resource.manage.messager.service.impl.MessageServiceImpl;
import com.baizhitong.resource.manage.res.service.AudioService;
import com.baizhitong.resource.model.rel.RelResGrade;
import com.baizhitong.resource.model.rel.RelResKp;
import com.baizhitong.resource.model.rel.RelResSection;
import com.baizhitong.resource.model.rel.RelResSubject;
import com.baizhitong.resource.model.rel.RelResTbc;
import com.baizhitong.resource.model.rel.RelResTbv;
import com.baizhitong.resource.model.res.ResAudio;
import com.baizhitong.resource.model.vo.res.ResVo;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

@Service
public class AudioServiceImpl extends BaseService implements AudioService {
    /** 权限共通服务 */
    @Autowired
    private CommonService commonService;
    @Autowired
    private ResAudioDao   audioDao;
    @Autowired
    private ResShareCheckDao shareCheckDao;
    private @Autowired RelResGradeDao     relResGradeDao;
    private @Autowired RelResSubjectDao   relResSubjectDao;
    private @Autowired RelResSectionDao   relResSectionDao;
    private @Autowired RelResTbcDao       relResTbcDao;
    private @Autowired RelResTbvDao       relResTbvDao;
    private @Autowired RelResKpDao        relResKpDao;
    private @Autowired MessageServiceImpl messageService;
    /**
     * ()<br>
     * @param id
     * @return
     */
    @Override
    public ResVo getById(Integer id) {
        return new ResVo(audioDao.getAudioById(id));
    }
    
     /**
     * ()<br>
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getPage(Map<String, Object> param, Integer page, Integer rows){
        UserInfoVo userInfoVo =getUserInfoVo();
        if (isEduStaff()) {
            String sectionCodes = commonService.queryUserAuthSectionCodes(userInfoVo.getUserCode());
            String subjectCodes = commonService.queryUserAuthSubjectCodes(userInfoVo.getUserCode());
            String gradeCodes = commonService.queryUserAuthGradeCodes(userInfoVo.getUserCode());
            param.put("sectionCodes", sectionCodes);
            param.put("subjectCodes", subjectCodes);
            param.put("gradeCodes", gradeCodes);
        }
        Page audioPage=audioDao.getAllAudioInfo(param, page, rows);
        if (page == null){
            return null;
        }
        List<Map<String, Object>> resList =  DataFormatter.format(audioPage.getRows());
            if (resList != null && resList.size() > 0) {
                for (Map resMap : resList) {
                    Map<String, Object> map = shareCheckDao.getRecentlyInfo(MapUtils.getString(resMap, "resCode"),MapUtils.getInteger(resMap,"shareCheckStatus"));
                    resMap.put("checkComments",MapUtils.getString(map, "checkComments"));
                    resMap.put("checkerName",MapUtils.getString(map, "checkerName"));
                    resMap.put("applyTime",MapUtils.getString(map, "applyTime"));
                }
            }
            audioPage.setRows(resList);
        return audioPage;
    }
    
    /**
     * ()<br>
     * @param resId
     * @param resDesc
     * @param resName
     * @param resTypeL2
     * @param userCode
     * @param userName
     * @param ip
     * @param kpCodes
     * @param tbcCodes
     * @param tbvCodes
     * @param gradeCodes
     * @param subejctCodes
     * @param sectionCodes
     * @param uploader
     * @param makerOrgCode
     * @param makerOrgName
     * @param makerCode
     * @param flagAllowDownload
     * @param coverPath
     * @param tags
     * @return
     */
    @Override
    public ResultCodeVo update(Integer resId, String resDesc, String resName, String resTypeL2,
                    String userCode, String userName, String ip, String kpCodes, String tbcCodes, String tbvCodes,
                    String gradeCodes, String subejctCodes, String sectionCodes, String uploader, String makerOrgCode,
                    String makerOrgName, String makerCode, Integer flagAllowDownload, String coverPath, String tags){
        ResAudio oldAudio = audioDao.select(resId);
        oldAudio.setModifier(userName);
        oldAudio.setModifierIP(ip);
        oldAudio.setModifyTime(DateUtils.getTimestamp(
                        DateUtils.formatDate(DateUtils.getTimestamp(DateUtils.formatDate(new Date())))));
        oldAudio.setResDesc(resDesc);
        oldAudio.setResTypeL2(resTypeL2);
        oldAudio.setResName(resName);
        oldAudio.setMakerCode(makerCode);
        oldAudio.setMakerName(uploader);
        oldAudio.setTags(tags);
        oldAudio.setFlagAllowDownload(flagAllowDownload);
        oldAudio.setCoverPath(coverPath);
        oldAudio.setMakerOrgCode(makerOrgCode);
        oldAudio.setMakerOrgName(makerOrgName);
        audioDao.updateAudio(oldAudio);
        String resCode = oldAudio.getResCode();
        /************** 关系表保存 *****************/
        relResTbvDao.deleteRelResTbv(resId); // 删除原本教材版本关系
        relResGradeDao.delResResGrade(resId); // 删除原本年级关系
        relResSubjectDao.delResResSubject(resId); // 删除原本学科关系
        relResSectionDao.delResResSection(resId); // 删除原本学段关系
        relResKpDao.delRelResKp(resId); // 删除原本知识点关系
        relResTbcDao.delRelResTbc(resId); // 删除原本教材章节关系
        if (!StringUtils.isEmpty(tbcCodes)) { // 教材版本关系保存
            String[] tbcCodeArray = tbcCodes.split(",");
            for (String tbcCode : tbcCodeArray) {
                RelResTbc tbc = new RelResTbc(null, CoreConstants.RES_TYPE_AUDIO, resId, oldAudio.getResCode(),
                                tbcCode, userName, DateUtils.getTimestamp(DateUtils.formatDate(new Date())), ip);
                relResTbcDao.saveRelResTbc(tbc);
            }
        }
        if (!StringUtils.isEmpty(kpCodes)) { // 教材版本关系保存
            String[] kpCodeArray = kpCodes.split(",");
            for (String kpCode : kpCodeArray) {
                RelResKp kp = new RelResKp(null, CoreConstants.RES_TYPE_AUDIO, resId, oldAudio.getResCode(), kpCode,
                                userName, DateUtils.getTimestamp(DateUtils.formatDate(new Date())), ip);
                relResKpDao.saveRelResKp(kp);
            }
        }
        if (!StringUtils.isEmpty(tbvCodes)) { // 教材版本关系保存
            String[] tbvCodeArray = tbvCodes.split(",");
            for (String code : tbvCodeArray) {
                RelResTbv kp = new RelResTbv(null, CoreConstants.RES_TYPE_AUDIO, resId, resCode, code, userName,
                                DateUtils.getTimestamp(DateUtils.formatDate(
                                                DateUtils.getTimestamp(DateUtils.formatDate(new Date())))),
                                ip);
                relResTbvDao.saveRelResTbv(kp);
            }
        }
        if (!StringUtils.isEmpty(sectionCodes)) { // 学段关系保存
            String[] sectionCodeArray = sectionCodes.split(",");
            for (String code : sectionCodeArray) {
                if (!"-1".equals(code)) {
                    RelResSection section = new RelResSection(null, CoreConstants.RES_TYPE_AUDIO, resId, resCode,
                                    code, userName, DateUtils.getTimestamp(DateUtils.formatDate(new Date())), ip);
                    relResSectionDao.saveRelResSection(section);
                }
            }
        }
        if (!StringUtils.isEmpty(gradeCodes)) { // 年级关系保存
            String[] gradeCodeArray = gradeCodes.split(",");
            for (String code : gradeCodeArray) {
                if (!"-1".equals(code)) {
                    RelResGrade grade = new RelResGrade(null, CoreConstants.RES_TYPE_AUDIO, resId, resCode, code,
                                    "", userName, DateUtils.getTimestamp(DateUtils.formatDate(new Date())), ip);
                    relResGradeDao.saveRelResGrade(grade);
                }
            }
        }
        if (!StringUtils.isEmpty(subejctCodes)) { // 学科关系保存
            String[] subejctCodeArray = subejctCodes.split(",");
            for (String code : subejctCodeArray) {
                if (!"-1".equals(code)) {
                    RelResSubject subject = new RelResSubject(null, CoreConstants.RES_TYPE_AUDIO, resId, resCode,
                                    code, userName, DateUtils.getTimestamp(DateUtils.formatDate(new Date())), ip);
                    relResSubjectDao.saveRelResSubject(subject);
                }
            }
        }

        return ResultCodeVo.rightCode("音频资源更新成功");
    }
    
}

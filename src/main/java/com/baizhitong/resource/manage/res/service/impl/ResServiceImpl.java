package com.baizhitong.resource.manage.res.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.config.SystemConfig;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.UserHelper;
import com.baizhitong.resource.dao.lesson.LessonDao;
import com.baizhitong.resource.dao.lesson.LessonShareDao;
import com.baizhitong.resource.dao.rel.RelResGradeDao;
import com.baizhitong.resource.dao.rel.RelResKpDao;
import com.baizhitong.resource.dao.rel.RelResSectionDao;
import com.baizhitong.resource.dao.rel.RelResSubjectDao;
import com.baizhitong.resource.dao.rel.RelResTbcDao;
import com.baizhitong.resource.dao.rel.RelResTbvDao;
import com.baizhitong.resource.dao.res.ResAudioDao;
import com.baizhitong.resource.dao.res.ResDocDao;
import com.baizhitong.resource.dao.res.ResFlashDao;
import com.baizhitong.resource.dao.res.ResMediaDao;
import com.baizhitong.resource.dao.res.ResMediaSpecialDao;
import com.baizhitong.resource.dao.res.ResShareCheckDao;
import com.baizhitong.resource.dao.test.ExerciseQuestionDao;
import com.baizhitong.resource.dao.test.QuestionDao;
import com.baizhitong.resource.dao.test.TestDao;
import com.baizhitong.resource.manage.authority.service.CommonService;
import com.baizhitong.resource.manage.messager.service.impl.MessageServiceImpl;
import com.baizhitong.resource.manage.res.service.ResService;
import com.baizhitong.resource.model.lesson.Lesson;
import com.baizhitong.resource.model.lesson.LessonShare;
import com.baizhitong.resource.model.rel.RelResGrade;
import com.baizhitong.resource.model.rel.RelResKp;
import com.baizhitong.resource.model.rel.RelResSection;
import com.baizhitong.resource.model.rel.RelResSubject;
import com.baizhitong.resource.model.rel.RelResTbc;
import com.baizhitong.resource.model.rel.RelResTbv;
import com.baizhitong.resource.model.res.ResAudio;
import com.baizhitong.resource.model.res.ResDoc;
import com.baizhitong.resource.model.res.ResFlash;
import com.baizhitong.resource.model.res.ResMedia;
import com.baizhitong.resource.model.res.ResShareCheck;
import com.baizhitong.resource.model.test.Question;
import com.baizhitong.resource.model.test.Test;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.resource.model.vo.res.DocumentReaderPlayDataVO;
import com.baizhitong.resource.model.vo.res.DocumentReaderPlayListVO;
import com.baizhitong.resource.model.vo.res.ResVo;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.ListUtils;
import com.baizhitong.utils.ObjectUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 资源业务接口
 * 
 * @author gaow
 * @date:2015年12月21日 上午9:54:12
 */
@Service(value = "resService")
public class ResServiceImpl extends BaseService implements ResService {
    private @Autowired ResDocDao          resDocDao;          // 文档资源数据接口
    private @Autowired ResMediaDao        resMediaDao;        //
    private @Autowired RelResGradeDao     relResGradeDao;
    private @Autowired RelResSubjectDao   relResSubjectDao;
    private @Autowired RelResSectionDao   relResSectionDao;
    private @Autowired RelResTbcDao       relResTbcDao;
    private @Autowired RelResTbvDao       relResTbvDao;
    private @Autowired RelResKpDao        relResKpDao;
    private @Autowired ResShareCheckDao   shareCheckDao;
    private @Autowired MessageServiceImpl messageService;
    private @Autowired TestDao            testDao;
    private @Autowired QuestionDao        questionDao;
    private @Autowired ResMediaSpecialDao resMediaSpecialDao;
    private @Autowired ResFlashDao        resFlashDao;
    private @Autowired ResAudioDao        resAudioDao;
    /** 权限共通服务 */
    @Autowired
    private CommonService                 commonService;
    private @Autowired LessonDao          lessonDao;
    /** 作业题目数据接口 */
    @Autowired
    private ExerciseQuestionDao           exerciseQuestionDao;
    /** 课时共享数据接口 */
    @Autowired
    private LessonShareDao                lessonShareDao;

    /**
     * 查询文档资源信息
     * 
     * @param param 查询条件
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     * @author gaow
     * @date:2015年12月16日 下午1:34:16
     */
    @SuppressWarnings("unchecked")
    public Page<ResVo> getDocPageInfo(Map<String, Object> param, Integer pageSize, Integer pageNo) throws Exception {

        UserInfoVo userInfoVo = getUserInfoVo();
        if (isEduStaff()) {
            String sectionCodes = commonService.queryUserAuthSectionCodes(userInfoVo.getUserCode());
            String subjectCodes = commonService.queryUserAuthSubjectCodes(userInfoVo.getUserCode());
            String gradeCodes = commonService.queryUserAuthGradeCodes(userInfoVo.getUserCode());
            param.put("sectionCodes", sectionCodes);
            param.put("subjectCodes", subjectCodes);
            param.put("gradeCodes", gradeCodes);
        }
        Page page = resDocDao.getAllDocInfo(param, pageNo, pageSize);
        List<Map<String, Object>> resList = DataFormatter.format(page.getRows());
        if (resList != null && resList.size() > 0) {
            for (Map<String, Object> resMap : resList) {
                Map<String, Object> map = shareCheckDao.getRecentlyInfo(MapUtils.getString(resMap, "resCode"),
                                MapUtils.getInteger(resMap, "shareCheckStatus"));
                resMap.put("checkComments", MapUtils.getString(map, "checkComments"));
                resMap.put("checkerName", MapUtils.getString(map, "checkerName"));
                resMap.put("applyTime", MapUtils.getString(map, "applyTime"));
            }
        }
        page.setRows(resList);
        return page;
    }

    /**
     * 查询视频资源信息
     * 
     * @param param 查询条件
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     * @author gaow
     * @date:2015年12月16日 下午1:34:16
     */
    public Page<ResVo> getMediaPageInfo(Map<String, Object> param, Integer pageSize, Integer pageNo) throws Exception {

        UserInfoVo userInfoVo = getUserInfoVo();
        if (isEduStaff()) {
            String sectionCodes = commonService.queryUserAuthSectionCodes(userInfoVo.getUserCode());
            String subjectCodes = commonService.queryUserAuthSubjectCodes(userInfoVo.getUserCode());
            String gradeCodes = commonService.queryUserAuthGradeCodes(userInfoVo.getUserCode());
            param.put("sectionCodes", sectionCodes);
            param.put("subjectCodes", subjectCodes);
            param.put("gradeCodes", gradeCodes);
        }
        Page page = resMediaDao.getAllMediaInfo(param, pageNo, pageSize);
        List<Map<String, Object>> resList = DataFormatter.format(page.getRows());
        if (resList != null && resList.size() > 0) {
            for (Map<String, Object> resMap : resList) {
                Map<String, Object> map = shareCheckDao.getRecentlyInfo(MapUtils.getString(resMap, "resCode"),
                                MapUtils.getInteger(resMap, "shareCheckStatus"));
                resMap.put("checkComments", MapUtils.getString(map, "checkComments"));
                resMap.put("checkerName", MapUtils.getString(map, "checkerName"));
                resMap.put("applyTime", MapUtils.getString(map, "applyTime"));
            }
        }
        page.setRows(resList);
        return page;
    }

    /**
     * 查询不正常视频资源信息
     * 
     * @param param 查询条件
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     * @author gaow
     * @date:2015年12月16日 下午1:34:16
     */
    public Page<ResVo> getUnUsualMediaPageInfo(Map<String, Object> param, Integer pageSize, Integer pageNo)
                    throws Exception {
        Page page = resMediaDao.getUnUsualMediaInfo(param, pageNo, pageSize);
        List<ResVo> resVoList = new ArrayList<ResVo>();
        if (page == null)
            return null;
        resVoList = ResVo.getVoListFromMapList(page.getRows());
        if (resVoList != null && resVoList.size() > 0)
            page.setRows(resVoList);
        return page;
    }

    /**
     * 
     * (查询资源回收站资源)<br>
     * 
     * @param param
     * @param pageSize
     * @param pageNo
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked" })
    public Page<ResVo> queryGarbageResPage(Map<String, Object> param, Integer pageSize, Integer pageNo)
                    throws Exception {
        // HttpServletRequest request=getRequest();
        // UserInfoVo userInfoVo=BeanHelper.getAdminUserFromCookie(request);
        Page page = resMediaDao.getGarbageMediaInfo(param, pageNo, pageSize);
        List<ResVo> resVoList = new ArrayList<ResVo>();
        if (page == null)
            return null;
        /*
         * List<Map<String,Object>> mapList=page.getRows(); String
         * exerciseUrl=SystemConfig.exercisePreviewUrl; String
         * questionUrl=SystemConfig.questionPreviewUrl; if(mapList!=null&&mapList.size()>0){
         * for(Map<String,Object> map:mapList){ Integer resTypeL1=MapUtils.getInteger(map,
         * "resTypeL1"); if(CoreConstants.RES_TYPE_EXERCISE.equals(resTypeL1)){ String
         * testCode=MapUtils.getString(map, "resCode"); Integer shareLevel=MapUtils.getInteger(map,
         * "shareLevel"); String path=exerciseUrl.replaceAll("(resCode[^&]*)",
         * "resCode="+testCode).replaceAll("(shareLevel[^&]*)", "shareLevel="+shareLevel); String
         * uid=UserHelper.encrypt(userInfoVo.getUserCode()); path=path+"&uid="+uid;
         * map.put("resAccessPath",path); }else
         * if(CoreConstants.RES_TYPE_QUESTION.equals(resTypeL1)){ String
         * questionCode=MapUtils.getString(map, "resCode"); String
         * path=questionUrl.replaceAll("(resCode[^&]*)", "resCode="+questionCode); String
         * uid=UserHelper.encrypt(userInfoVo.getUserCode()); path=path+"&uid="+uid;
         * map.put("resAccessPath",path); }else{ map.put("resAccessPath",""); } } }
         * page.setRows(mapList);
         */
        resVoList = ResVo.getVoListFromMapList(page.getRows());
        if (resVoList != null && resVoList.size() > 0) {
            page.setRows(resVoList);
        }
        return page;
    }

    /**
     * 查询不正常文档资源信息
     * 
     * @param param 查询条件
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     * @author gaow
     * @date:2015年12月16日 下午1:34:16
     */
    public Page<ResVo> getUnUsualDocPageInfo(Map<String, Object> param, Integer pageSize, Integer pageNo)
                    throws Exception {
        Page page = resDocDao.getUnUsualDocInfo(param, pageNo, pageSize);
        List<ResVo> resVoList = new ArrayList<ResVo>();
        if (page == null)
            return null;
        resVoList = ResVo.getVoListFromMapList(page.getRows());
        if (resVoList != null && resVoList.size() > 0)
            page.setRows(resVoList);
        return page;
    }

    /**
     * 根据文档id查询文档
     * 
     * @param id
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月19日 下午7:13:35
     */
    public ResVo getDocAllInfoById(Integer id) throws Exception {
        Map<String, Object> map = resDocDao.getResDocById(id);
        ResVo vo = new ResVo(map);
        return vo;
    }

    /**
     * 保存文档资源
     * 
     * @param vo
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月18日 下午3:48:14
     */
    public Integer addDco(ResDoc doc) throws Exception {
        /* ResDoc doc=ResDoc.resVoToResDoc(vo); */
        return resDocDao.saveDocAndReturnId(doc);
    }

    /**
     * 保存视频资源
     * 
     * @param media
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月21日 下午2:18:42
     */
    @Override
    public Integer addMedia(ResMedia media) throws Exception {
        return resMediaDao.saveMediaAndReturnId(media);
    }

    /**
     * 
     * @param docList 资源列表
     * @param userCode 用户信息
     * @param userName 用户姓名
     * @param ip ip地址
     * @param kpCodes 知识点编码集合
     * @param tbcCodes 章节编码集合
     * @param tbvCodes 教材版本编码集合
     * @param gradeCodes 年级编码集合
     * @param subejctCodes 学科编码集合
     * @param sectionCodes 学段编码集合
     * @param tags 标签
     * @return
     * @author gaow
     * @date:2015年12月18日 下午4:03:16
     */
    public ResultCodeVo docInfoUpdate(Integer resId, String resDesc, String resName, String resTypeL2, String userCode,
                    String userName, String ip, String kpCodes, String tbcCodes, String tbvCodes, String gradeCodes,
                    String subejctCodes, String sectionCodes, String uploader, String makerOrgCode, String makerOrgName,
                    String makerCode, Integer flagAllowDownload, String tags) throws Exception {
        try {
            ResDoc oldDoc = resDocDao.selectResDoc(resId);
            oldDoc.setModifier(userName);
            oldDoc.setModifierIP(ip);
            oldDoc.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
            oldDoc.setResDesc(resDesc);
            oldDoc.setResTypeL2(resTypeL2);
            oldDoc.setResName(resName);
            oldDoc.setMakerName(uploader);
            oldDoc.setMakerCode(makerCode);
            oldDoc.setMakerOrgCode(makerOrgCode);
            oldDoc.setFlagAllowDownload(flagAllowDownload);
            oldDoc.setMakerOrgName(makerOrgName);
            oldDoc.setTags(tags);
            resDocDao.updateDocInfo(oldDoc);
            String resCode = oldDoc.getResCode();
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
                    RelResTbc tbc = new RelResTbc(null, CoreConstants.RES_TYPE_DOC, resId, oldDoc.getResCode(), tbcCode,
                                    userName, DateUtils.getTimestamp(DateUtils.formatDate(new Date())), ip);
                    relResTbcDao.saveRelResTbc(tbc);
                }
            }
            if (!StringUtils.isEmpty(kpCodes)) { // 教材版本关系保存
                String[] kpCodeArray = kpCodes.split(",");
                for (String kpCode : kpCodeArray) {
                    RelResKp kp = new RelResKp(null, CoreConstants.RES_TYPE_DOC, resId, oldDoc.getResCode(), kpCode,
                                    userName, DateUtils.getTimestamp(DateUtils.formatDate(new Date())), ip);
                    relResKpDao.saveRelResKp(kp);
                }
            }
            if (!StringUtils.isEmpty(tbvCodes)) { // 教材版本关系保存
                String[] tbvCodeArray = tbvCodes.split(",");
                for (String code : tbvCodeArray) {
                    RelResTbv kp = new RelResTbv(null, CoreConstants.RES_TYPE_DOC, resId, resCode, code, userName,
                                    DateUtils.getTimestamp(DateUtils.formatDate(new Date())), ip);
                    relResTbvDao.saveRelResTbv(kp);
                }
            }
            if (!StringUtils.isEmpty(sectionCodes)) { // 学段关系保存
                String[] sectionCodeArray = sectionCodes.split(",");
                for (String code : sectionCodeArray) {
                    RelResSection section = new RelResSection(null, CoreConstants.RES_TYPE_DOC, resId, resCode, code,
                                    userName, DateUtils.getTimestamp(DateUtils.formatDate(new Date())), ip);
                    relResSectionDao.saveRelResSection(section);
                }
            }
            if (!StringUtils.isEmpty(gradeCodes)) { // 年级关系保存
                String[] gradeCodeArray = gradeCodes.split(",");
                for (String code : gradeCodeArray) {
                    RelResGrade grade = new RelResGrade(null, CoreConstants.RES_TYPE_DOC, resId, resCode, code, "",
                                    userName, DateUtils.getTimestamp(DateUtils.formatDate(new Date())), ip);
                    relResGradeDao.saveRelResGrade(grade);
                }
            }
            if (!StringUtils.isEmpty(subejctCodes)) { // 学科关系保存
                String[] subejctCodeArray = subejctCodes.split(",");
                for (String code : subejctCodeArray) {
                    RelResSubject subject = new RelResSubject(null, CoreConstants.RES_TYPE_DOC, resId, resCode, code,
                                    userName, DateUtils.getTimestamp(DateUtils.formatDate(new Date())), ip);
                    relResSubjectDao.saveRelResSubject(subject);
                }
            }
        } catch (Exception e) {
            throw e;

        }
        return ResultCodeVo.rightCode("文档资源更新成功");
    }

    /**
     * 根据id查询文档资源
     * 
     * @param id 资源id
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月19日 下午2:46:02
     */
    public ResVo getDocResById(Integer id) throws Exception {
        ResDoc doc = resDocDao.selectResDoc(id);
        return ResVo.getResVoFromResDoc(doc);
    }

    /**
     * 查询视频资源的详细信息
     * 
     * @param id 资源id
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月21日 下午2:07:40
     */
    @Override
    public ResVo getMediaAllInfoById(Integer id) throws Exception {
        Map<String, Object> map = resMediaDao.getResMediaById(id);
        ResVo vo = new ResVo(map);
        return vo;
    }

    /**
     * 新增视频资源并返回id
     * 
     * @param media 视频资源实体
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月21日 下午2:07:58
     */
    public Integer addDco(ResMedia media) throws Exception {
        /* ResDoc doc=ResDoc.resVoToResDoc(vo); */
        return resMediaDao.saveMediaAndReturnId(media);
    }

    /**
     * 视频资源信息更新
     * 
     * @param mediaList 视频资源列表
     * @param userCode 用户编号
     * @param userName 用户姓名
     * @param ip ip地址
     * @param kpCodes 知识点编码集合
     * @param tbcCodes 教材章节编码集合
     * @param tbvCodes 教材版本编码集合
     * @param gradeCodes 年级编码集合
     * @param subejctCodes 学科编码集合
     * @param sectionCodes 学段编码集合
     * @param tags 标签
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月21日 下午2:08:27
     */
    @Override
    public ResultCodeVo mediaInfoUpdate(Integer resId, String resDesc, String resName, String resTypeL2,
                    String userCode, String userName, String ip, String kpCodes, String tbcCodes, String tbvCodes,
                    String gradeCodes, String subejctCodes, String sectionCodes, String uploader, String makerOrgCode,
                    String makerOrgName, String makerCode, Integer flagAllowDownload, String coverPath, String tags)
                                    throws Exception {
        try {
            ResMedia oldMedia = resMediaDao.selectResMedia(resId);
            oldMedia.setModifier(userName);
            oldMedia.setModifierIP(ip);
            oldMedia.setModifyTime(DateUtils.getTimestamp(
                            DateUtils.formatDate(DateUtils.getTimestamp(DateUtils.formatDate(new Date())))));
            oldMedia.setResDesc(resDesc);
            oldMedia.setResTypeL2(resTypeL2);
            oldMedia.setResName(resName);
            oldMedia.setMakerCode(makerCode);
            oldMedia.setMakerName(uploader);
            oldMedia.setTags(tags);
            oldMedia.setFlagAllowDownload(flagAllowDownload);
            oldMedia.setCoverPath(coverPath);
            oldMedia.setMakerOrgCode(makerOrgCode);
            oldMedia.setMakerOrgName(makerOrgName);
            resMediaDao.updateMediaInfo(oldMedia);
            String resCode = oldMedia.getResCode();
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
                    RelResTbc tbc = new RelResTbc(null, CoreConstants.RES_TYPE_MEDIA, resId, oldMedia.getResCode(),
                                    tbcCode, userName, DateUtils.getTimestamp(DateUtils.formatDate(new Date())), ip);
                    relResTbcDao.saveRelResTbc(tbc);
                }
            }
            if (!StringUtils.isEmpty(kpCodes)) { // 教材版本关系保存
                String[] kpCodeArray = kpCodes.split(",");
                for (String kpCode : kpCodeArray) {
                    RelResKp kp = new RelResKp(null, CoreConstants.RES_TYPE_MEDIA, resId, oldMedia.getResCode(), kpCode,
                                    userName, DateUtils.getTimestamp(DateUtils.formatDate(new Date())), ip);
                    relResKpDao.saveRelResKp(kp);
                }
            }
            if (!StringUtils.isEmpty(tbvCodes)) { // 教材版本关系保存
                String[] tbvCodeArray = tbvCodes.split(",");
                for (String code : tbvCodeArray) {
                    RelResTbv kp = new RelResTbv(null, CoreConstants.RES_TYPE_MEDIA, resId, resCode, code, userName,
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
                        RelResSection section = new RelResSection(null, CoreConstants.RES_TYPE_MEDIA, resId, resCode,
                                        code, userName, DateUtils.getTimestamp(DateUtils.formatDate(new Date())), ip);
                        relResSectionDao.saveRelResSection(section);
                    }
                }
            }
            if (!StringUtils.isEmpty(gradeCodes)) { // 年级关系保存
                String[] gradeCodeArray = gradeCodes.split(",");
                for (String code : gradeCodeArray) {
                    if (!"-1".equals(code)) {
                        RelResGrade grade = new RelResGrade(null, CoreConstants.RES_TYPE_MEDIA, resId, resCode, code,
                                        "", userName, DateUtils.getTimestamp(DateUtils.formatDate(new Date())), ip);
                        relResGradeDao.saveRelResGrade(grade);
                    }
                }
            }
            if (!StringUtils.isEmpty(subejctCodes)) { // 学科关系保存
                String[] subejctCodeArray = subejctCodes.split(",");
                for (String code : subejctCodeArray) {
                    if (!"-1".equals(code)) {
                        RelResSubject subject = new RelResSubject(null, CoreConstants.RES_TYPE_MEDIA, resId, resCode,
                                        code, userName, DateUtils.getTimestamp(DateUtils.formatDate(new Date())), ip);
                        relResSubjectDao.saveRelResSubject(subject);
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }

        return ResultCodeVo.rightCode("视频资源更新成功");
    }

    /**
     * 查询视频资源信息
     * 
     * @param id 资源id
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月21日 下午2:13:13
     */
    @Override
    public ResVo getMediaResById(Integer id) throws Exception {
        ResMedia media = resMediaDao.selectResMedia(id);
        return ResVo.getResVoFromResMidea(media);
    }

    /**
     * 删除资源章节关系 ()<br>
     * 
     * @param resId
     * @param chapterCode
     * @throws Exception
     */
    public void delResChapter(Integer resId, String chapterCode) throws Exception {
        relResTbcDao.deleteRelResTbcByParam(resId, chapterCode);
    }

    /**
     * 
     * 删除资源知识点关系
     * 
     * @param resId
     * @param knowledgeCode
     * @throws Exception
     */
    public void delResKnowledge(Integer resId, String knowledgeCode) throws Exception {
        relResKpDao.deleteRelResKpByParam(resId, knowledgeCode);
    }

    /**
     * 审核资源
     * 
     * @param resId 资源id
     * @param type 类型
     * @param status 审核状态
     * @throws Exception
     * @author gaow
     * @date:2015年12月23日 下午1:57:36
     */
    public ResultCodeVo examine(Integer resId, Integer type, String status) throws Exception {
        if (type.equals(CoreConstants.RES_TYPE_MEDIA)) {
            resMediaDao.examine(resId, status);
        } else if (type.equals(CoreConstants.RES_TYPE_DOC)) {
            resDocDao.examine(resId, status);
        }
        return ResultCodeVo.rightCode("审核成功");
    }

    /**
     * 查询页码
     * 
     * @param pageNo 当前页面
     * @param pageSize 总页数
     * @param resId 资源Id
     * @return
     * @author gaow
     * @date:2015年12月23日 下午3:55:00
     */
    public DocumentReaderPlayListVO getPageListJsonForAnalyitcal(Integer pageNo, Integer pageSize, Integer resId)
                    throws Exception {
        if (resId == null) {
            throw new Exception("文件不存在！");
        }
        ResDoc entity = resDocDao.selectResDoc(resId);
        if (entity == null) {
            throw new Exception("文件不存在！");
        }
        if (pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize < 1) {
            pageSize = 1;
        }
        DocumentReaderPlayListVO vo = new DocumentReaderPlayListVO();
        vo.setPageNo(pageNo);
        int pageFp = vo.getPageFp();
        if (pageFp < 1) {
            pageFp = DocumentReaderPlayListVO.getDefaultPageFp();
            vo.setPageFp(pageFp);
        }
        int pages = entity.getPages();// 总页数
        String url = entity.getConvertedPathImages();// 文档路径
        String docHost = SystemConfig.getDocHost();
        if (StringUtils.isEmpty(url)) {
            url = docHost + "/" + "";
        } else {
            url = docHost + "/" + url.substring(0, url.lastIndexOf('/') + 1);
        }
        vo.setTotalRec(pages);// 总记录数
        vo.setTotalPages(pages * pageFp);// 总页码数
        // 下面是分页的算法计算
        int foreCount = (pageNo - 1) * pageSize;// 当前页前面的记录数
        int countBegin = foreCount + 1;// 开始的记录点
        int count = Math.min(pages - foreCount, pageSize);// 每页记录数 和 总记录数 - 当前页前面的记录数 取小的那个
                                                          // 就是本次应该要取出的数据总数
        // 本次应该要取出的数据数组容器
        DocumentReaderPlayDataVO[] array = new DocumentReaderPlayDataVO[count];
        // 依次去数据放入容器中
        String endPix = ".jpg";
        if (entity.getConvertedPathImages().lastIndexOf("png") > 0) {
            endPix = ".png";
        }
        for (int i = 0; i < count; i++, countBegin++) {
            DocumentReaderPlayDataVO data = new DocumentReaderPlayDataVO();
            data.setPageId(countBegin);
            data.setGetPageUrl(url + countBegin + endPix);
            array[i] = data;
        }
        vo.setDatas(array);
        return vo;
    }

    /**
     * 保存视频知识点关系 ()<br>
     * 
     * @param resId
     * @param kpCodes
     * @return
     */
    public ResultCodeVo saveMediaKnowledgeRelated(Integer resId, String kpCodes) {
        try {
            // 从cookie中获取用户登录信息
            UserInfoVo userInfo = getUserInfoVo();
            String ip = getIp();
            ResMedia media = resMediaDao.selectResMedia(resId);
            if (media == null)
                return ResultCodeVo.errorCode("资源不存在");
            List<String> kpList = JSON.parseArray(kpCodes, String.class);
            List<String> kpList1 = ListUtils.removeRepeat(kpList);
            relResKpDao.delRelResKp(resId); // 删除原本知识点关系
            if (kpList1 != null && kpList1.size() > 0) { // 知识点关系保存
                for (String kpCode : kpList1) {
                    RelResKp kp = new RelResKp(null, CoreConstants.RES_TYPE_MEDIA, resId, media.getResCode(), kpCode,
                                    userInfo.getUserName(), DateUtils.getTimestamp(DateUtils.formatDate(new Date())),
                                    ip);
                    relResKpDao.saveRelResKp(kp);
                }
            }
            return ResultCodeVo.rightCode("关系保存成功");
        } catch (Exception e) {
            return ResultCodeVo.errorCode("关系保存失败");
        }
    }

    /**
     * 文档知识点关系 ()<br>
     * 
     * @param resId
     * @param kpCodes
     * @return
     */
    public ResultCodeVo saveDocKnowledgeRelated(Integer resId, String kpCodes) {
        try {

            // 从cookie中获取用户登录信息
            UserInfoVo userInfo = getUserInfoVo();
            String ip = getIp();
            ResDoc doc = resDocDao.selectResDoc(resId);
            if (doc == null)
                return ResultCodeVo.errorCode("资源不存在");
            List<String> kpList = JSON.parseArray(kpCodes, String.class);
            List<String> kpList1 = ListUtils.removeRepeat(kpList);
            relResKpDao.delRelResKp(resId); // 删除原本知识点关系
            if (kpList1 != null && kpList1.size() > 0) { // 知识点关系保存
                for (String kpCode : kpList1) {
                    RelResKp kp = new RelResKp(null, CoreConstants.RES_TYPE_DOC, resId, doc.getResCode(), kpCode,
                                    userInfo.getUserName(), DateUtils.getTimestamp(DateUtils.formatDate(new Date())),
                                    ip);
                    relResKpDao.saveRelResKp(kp);
                }
            }
            return ResultCodeVo.rightCode("关系保存成功");
        } catch (Exception e) {
            return ResultCodeVo.errorCode("关系保存失败");
        }
    }

    /**
     * 视频章节关系 ()<br>
     * 
     * @param resId
     * @param chapterCodes
     * @return
     */
    public ResultCodeVo saveMediaChapterRelated(Integer resId, String chapterCodes) {
        try {

            // 从cookie中获取用户登录信息
            UserInfoVo userInfo = getUserInfoVo();
            String ip = getIp();
            ResMedia media = resMediaDao.selectResMedia(resId);
            if (media == null)
                return ResultCodeVo.errorCode("资源不存在");
            List<String> chapterList = JSON.parseArray(chapterCodes, String.class);
            List<String> chapterList1 = ListUtils.removeRepeat(chapterList);
            relResTbcDao.delRelResTbc(resId); // 删除原本教材章节关系
            if (chapterList1 != null && chapterList1.size() > 0) { // 教材章节关系保存
                for (String tbCode : chapterList1) {
                    RelResTbc tbc = new RelResTbc(null, CoreConstants.RES_TYPE_MEDIA, resId, media.getResCode(), tbCode,
                                    userInfo.getUserName(), DateUtils.getTimestamp(DateUtils.formatDate(new Date())),
                                    ip);
                    relResTbcDao.saveRelResTbc(tbc);
                }
            }
            return ResultCodeVo.rightCode("关系保存成功");
        } catch (Exception e) {
            return ResultCodeVo.errorCode("关系保存失败");
        }

    }

    /**
     * 文档章节关系 ()<br>
     * 
     * @param resId
     * @param chapterCodes
     * @return
     */
    public ResultCodeVo saveDocChapterRelated(Integer resId, String chapterCodes) {
        try {

            // 从cookie中获取用户登录信息
            UserInfoVo userInfo = getUserInfoVo();
            String ip = getIp();
            ResDoc doc = resDocDao.selectResDoc(resId);
            if (doc == null)
                return ResultCodeVo.errorCode("资源不存在");
            List<String> chapterList = JSON.parseArray(chapterCodes, String.class);
            List<String> chapterList1 = ListUtils.removeRepeat(chapterList);
            relResTbcDao.delRelResTbc(resId); // 删除原本教材章节关系
            if (chapterList1 != null && chapterList1.size() > 0) { // 教材章节关系保存
                for (String tbCode : chapterList1) {
                    RelResTbc tbc = new RelResTbc(null, CoreConstants.RES_TYPE_DOC, resId, doc.getResCode(), tbCode,
                                    userInfo.getUserName(), DateUtils.getTimestamp(DateUtils.formatDate(new Date())),
                                    ip);
                    relResTbcDao.saveRelResTbc(tbc);
                }
            }
            return ResultCodeVo.rightCode("关系保存成功");
        } catch (Exception e) {
            return ResultCodeVo.errorCode("关系保存失败");
        }
    }

    /**
     * 
     * 查询视频文档资源
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param gradeCode 年级编码
     * @param shareCheckStatus 分享审核中级别先
     * @param orgName 机构名称
     * @param userName 上传人姓名
     * @param resTypeL2 资源类别
     * @param uploadStartTime 上传时间开始
     * @param uploadEndTime 上传时间结束
     * @param shareCheckLevel 分享审核中状态
     * @param page 页码
     * @param rows 每页记录数
     * @return
     */
    public Page<List<Map<String, Object>>> getAllRes(String sectionCode, String subjectCode, String gradeCode,
                    Integer shareCheckStatus, String orgName, String userName, Integer resTypeL1,
                    String uploadStartTime, String uploadEndTime, Integer shareCheckLevel, String resName,
                    Integer pageNo, Integer rows) {

        CompanyInfoVo companyInfoVo = getCompanyInfo();
        Page page = resDocDao.getAllRes(sectionCode, subjectCode, gradeCode, shareCheckStatus, orgName, userName,
                        resTypeL1, uploadStartTime, uploadEndTime, shareCheckLevel, resName, companyInfoVo.getOrgCode(),
                        pageNo, rows);
        List<ResVo> resVoList = new ArrayList<ResVo>();
        resVoList = ResVo.getVoListFromMapList(page.getRows());
        if (resVoList != null && resVoList.size() > 0)
            page.setRows(resVoList);
        return page;
    }

    /**
     * 删除资源 ()<br>
     * 
     * @param id 视频id
     * @return
     */
    public ResultCodeVo deleteRes(String ids, Integer resType) {

        UserInfoVo userInfo = getUserInfoVo();
        String modifierIP = getIp();

        int count = 0;
        if (CoreConstants.RES_TYPE_MEDIA.equals(resType)) {
            count = resMediaDao.deleteMedia(ids, userInfo.getUserName(), modifierIP);
        } else if (CoreConstants.RES_TYPE_DOC.equals(resType)) {
            count = resDocDao.deleteDoc(ids, userInfo.getUserName(), modifierIP);
        } else if (CoreConstants.RES_TYPE_EXERCISE.equals(resType)) {
            count = testDao.deleteTest(ids, userInfo.getUserName(), modifierIP);
        } else if (CoreConstants.RES_TYPE_QUESTION.equals(resType)) {
            count = questionDao.deleteQuestion(ids, userInfo.getUserName(), modifierIP);
        } else if (CoreConstants.RES_TYPE_FLASH.equals(resType)) {
            count = resFlashDao.deleteFlash(ids, userInfo.getUserName(), modifierIP);
        } else if (CoreConstants.RES_TYPE_AUDIO.equals(resType)) {
            count = resAudioDao.deleteAudio(ids, userInfo.getUserName(), modifierIP);
        }

        if (count > 0) {
            return ResultCodeVo.rightCode("删除成功！");
        } else {
            return ResultCodeVo.errorCode("删除失败！");
        }
    }

    /**
     * 
     * (操作回收站资源)<br>
     * 
     * @param docIds 文档ID集合
     * @param mediaIds 视频ID集合
     * @param mediaSpecialIds 特色视频ID集合
     * @param lessonIds 课时ID集合
     * @param exerciseIds 测验ID集合
     * @param questionIds 题目ID集合
     * @param flashIds 互动资源ID集合
     * @param operateType 操作类型：1，删除；2，恢复；
     * @return 操作结果
     */
    public ResultCodeVo operateGarbageRes(String docIds, String mediaIds, String mediaSpecialIds, String lessonIds,
                    String exerciseIds, String questionIds, String flashIds, Integer operateType) throws Exception {

        UserInfoVo userInfo = getUserInfoVo();
        String ip = getIp();
        int count = 0;
        if (StringUtils.isNotEmpty(mediaIds)) {
            count += resMediaDao.updateFlagDeleteBatch(mediaIds, operateType, userInfo.getUserName(), ip);
        }
        if (StringUtils.isNotEmpty(docIds)) {
            count += resDocDao.updateFlagDeleteBatch(docIds, operateType, userInfo.getUserName(), ip);
        }
        if (StringUtils.isNotEmpty(mediaSpecialIds)) {
            count += resMediaSpecialDao.updateFlagDeleteBatch(mediaSpecialIds, operateType, userInfo.getUserName(), ip);
        }
        /*
         * if(StringUtils.isNotEmpty(lessonIds)){ count+=lessonDao.updateFlagDeleteBatch(lessonIds,
         * operateType); }
         */
        if (StringUtils.isNotEmpty(exerciseIds)) {
            count += testDao.updateFlagDeleteBatch(exerciseIds, operateType, userInfo.getUserName(), ip);
        }
        if (StringUtils.isNotEmpty(questionIds)) {
            count += questionDao.updateFlagDeleteBatch(questionIds, operateType, userInfo.getUserName(), ip);
        }
        if (StringUtils.isNotEmpty(flashIds)) {
            count += resFlashDao.updateFlagDeleteBatch(flashIds, operateType, userInfo.getUserName(), ip);
        }
        if (0 < count) {
            return ResultCodeVo.rightCode("操作成功！");
        } else {
            return ResultCodeVo.errorCode("操作失败！");
        }
    }

    /**
     * 
     * 恢复删除资源
     * 
     * @param docIds 文档资源id
     * @param mediaIds 视频资源id
     * @return
     */
    public ResultCodeVo recoveryRes(String docIds, String mediaIds) {
        int docCount = 1;
        int mediaCount = 1;
        if (StringUtils.isNotEmpty(docIds)) {
            docCount = resDocDao.recoveryDocList(docIds);
        }
        if (StringUtils.isNotEmpty(mediaIds)) {
            mediaCount = resMediaDao.recoveryMediaList(mediaIds);
        }
        if (docCount > 0 && mediaCount > 0) {
            return ResultCodeVo.rightCode("恢复成功");
        } else {
            return ResultCodeVo.errorCode("恢复失败");
        }
    }

    /**
     * 资源审核列表 param 查询参数
     * 
     * @return
     */
    public Page getResShareCheckList(Map<String, Object> param) {
        return shareCheckDao.selectPage(param);

    }

    /**
     * 
     * 资源审核
     * 
     * @param id 资源id
     * @param resTypeL1 资源一级分类
     * @param status 资源状态
     * @param shareCheckLevel 分享审核中级别
     * @return
     */
    public ResultCodeVo updateResShareCheck(String resCode, Integer id, Integer resTypeL1, Integer status,
                    Integer shareCheckLevel, String applierOrgCode, String applierCode, String checkComments) {

        UserInfoVo userInfoVo = getUserInfoVo();
        String ip = getIp();
        ResShareCheck resShareCheck = shareCheckDao.getRes(id);
        if (resShareCheck == null) {
            return ResultCodeVo.errorCode("该资源已被取消审核,请刷新页面");
        }
        Integer count = shareCheckDao.updateShareCheckStatus(id, resTypeL1, status, userInfoVo, ip, checkComments);
        if (count > 0) {
            messageService.sendMessage(status, resCode, resTypeL1, shareCheckLevel, userInfoVo.getUserCode(),
                            applierOrgCode, applierCode);
            return ResultCodeVo.rightCode("审核成功");

        } else {
            return ResultCodeVo.rightCode("审核失败");
        }

    }

    /**
     * 
     * 审核资源
     * 
     * @param resCodes 资源编码集合（可以一个或者多个）
     * @param checkCode 审核状态编码 05退回  20通过 
     * @param comment 审核意见
     * @param resType 资源类型 10视频；11特色资源；12音频资源；15互动资源；20文档； 30测验； 50题目；60课时
     * @return 审核结果
     */
    public ResultCodeVo checkRes(String resCodes[], String checkCode, String comment, String resType) {
        if (resCodes.length == 0){
            return ResultCodeVo.errorCode("请选择需要审核的资源！");
        }
        UserInfoVo userInfoVo = getUserInfoVo(); 
        String ip = getIp();
        int shareCheckUpdateCount=0; 
        // 课程
        if (CoreConstants.RES_TYPE_COURSE.toString().equals(resType)) {
            for (String lessonCode : resCodes) {
                Lesson lesson = lessonDao.selectByLessonCode(lessonCode);
                if (ObjectUtils.isNull(lesson)){
                    return ResultCodeVo.errorCode("有课程没有查询到，请重新审核！"); 
                }
                lessonDao.updateShareCheckStatus(lessonCode, lesson.getShareCheckLevel(), userInfoVo.getUserName(), ip,
                                Integer.parseInt(checkCode));
                shareCheckUpdateCount= shareCheckDao.update(lessonCode, CoreConstants.RES_TYPE_COURSE, Integer.parseInt(checkCode), userInfoVo,
                                ip, comment);
                // 审核通过，生成积分消息
                if (CoreConstants.CHECK_STATUS_CHECKED.toString().equals(checkCode)) {
                    //审核通过往课时共享表插入一条记录
                    insertLessonShareInfo(lesson);
                    if (CoreConstants.RESOURCE_SHARE_LEVEL_COMPANY.equals(lesson.getShareCheckLevel())) {
                        messageService.sendPointMessage(CoreConstants.ACTION_LESSON_CHECKED, lesson.getOrgCode(),
                                        lesson.getTeacherCode(), 1);
                    } else {
                        messageService.sendPointMessage(CoreConstants.ACTION_LESSON_CHECKED, lesson.getOrgCode(),
                                        lesson.getTeacherCode(), 0);
                    }
                }
            }
        }
        // 视频
        else if (CoreConstants.RES_TYPE_MEDIA.toString().equals(resType)) {
            for (String resCode : resCodes) {
                ResMedia media = resMediaDao.selectResMedia(resCode);
                if (ObjectUtils.isNull(media)){
                    return ResultCodeVo.errorCode("有视频没有查询到，请重新审核！");
                }
                media.setShareCheckStatus(Integer.parseInt(checkCode));
                media.setShareCheckTime(new Timestamp(new Date().getTime()));
                if (CoreConstants.CHECK_STATUS_CHECKED.toString().equals(checkCode)) {
                    media.setShareLevel(media.getShareCheckLevel());
                }
                media.setModifier(userInfoVo.getUserName());
                media.setModifierIP(ip);
                media.setModifyTime(new Timestamp(new Date().getTime()));
                ResShareCheck resShareCheck = null;
                List<ResShareCheck> checkList = shareCheckDao.getResByCodeAndType(CoreConstants.RES_TYPE_MEDIA,
                                resCode);
                if (checkList != null && checkList.size() > 0) {
                    resShareCheck = checkList.get(0);
                }
                resMediaDao.updateMediaInfo(media);
                shareCheckUpdateCount= shareCheckDao.updateShareCheckStatusNew(resCode, media.getShareTime(), checkCode, userInfoVo, ip,
                                comment);
                if (ObjectUtils.isNull(resShareCheck))
                    continue;
                messageService.sendMessage(Integer.parseInt(checkCode), resCode, Integer.parseInt(resType),
                                media.getShareCheckLevel(), userInfoVo.getUserCode(), resShareCheck.getApplierOrgCode(),
                                resShareCheck.getApplierCode());
            }

        } // 音频
        else if (CoreConstants.RES_TYPE_AUDIO.toString().equals(resType)) {
            for (String resCode : resCodes) {
                ResAudio audio = resAudioDao.getAudio(resCode);
                if (ObjectUtils.isNull(audio)){
                    return ResultCodeVo.errorCode("有音频没有查询到，请重新审核！");
                }
                audio.setShareCheckStatus(Integer.parseInt(checkCode));
                audio.setShareCheckTime(new Timestamp(new Date().getTime()));
                if (CoreConstants.CHECK_STATUS_CHECKED.toString().equals(checkCode)) {
                    audio.setShareLevel(audio.getShareCheckLevel());
                }
                audio.setModifier(userInfoVo.getUserName());
                audio.setModifierIP(ip);
                audio.setModifyTime(new Timestamp(new Date().getTime()));
                ResShareCheck resShareCheck = null;
                List<ResShareCheck> checkList = shareCheckDao.getResByCodeAndType(CoreConstants.RES_TYPE_AUDIO,
                                resCode);
                if (checkList != null && checkList.size() > 0) {
                    resShareCheck = checkList.get(0);
                }
                resAudioDao.updateAudio(audio);
                shareCheckUpdateCount= shareCheckDao.updateShareCheckStatusNew(resCode, audio.getShareTime(), checkCode, userInfoVo, ip,
                                comment);
                if (ObjectUtils.isNull(resShareCheck))
                    continue;
                messageService.sendMessage(Integer.parseInt(checkCode), resCode, Integer.parseInt(resType),
                                audio.getShareCheckLevel(), userInfoVo.getUserCode(), resShareCheck.getApplierOrgCode(),
                                resShareCheck.getApplierCode());
            }
            // 文档
        } else if (CoreConstants.RES_TYPE_DOC.toString().equals(resType)) {
            for (String resCode : resCodes) {
                ResDoc doc = resDocDao.selectResDoc(resCode);
                if (ObjectUtils.isNull(doc)){
                    return ResultCodeVo.errorCode("有文档没有查询到，请重新审核！");
                }
                doc.setShareCheckStatus(Integer.parseInt(checkCode));
                doc.setShareCheckTime(new Timestamp(new Date().getTime()));
                if (CoreConstants.CHECK_STATUS_CHECKED.toString().equals(checkCode)) {
                    doc.setShareLevel(doc.getShareCheckLevel());
                }
                doc.setModifier(userInfoVo.getUserName());
                doc.setModifierIP(ip);
                doc.setModifyTime(new Timestamp(new Date().getTime()));
                ResShareCheck resShareCheck = null;
                List<ResShareCheck> checkList = shareCheckDao.getResByCodeAndType(CoreConstants.RES_TYPE_DOC, resCode);
                if (checkList != null && checkList.size() > 0) {
                    resShareCheck = checkList.get(0);
                }
                resDocDao.updateDocInfo(doc);
                shareCheckUpdateCount= shareCheckDao.updateShareCheckStatusNew(resCode, doc.getShareTime(), checkCode, userInfoVo, ip,
                                comment);
                if (ObjectUtils.isNull(resShareCheck))
                    continue;
                messageService.sendMessage(Integer.parseInt(checkCode), resCode, Integer.parseInt(resType),
                                doc.getShareCheckLevel(), userInfoVo.getUserCode(), resShareCheck.getApplierOrgCode(),
                                resShareCheck.getApplierCode());
            }
            //测验
        } else if (CoreConstants.RES_TYPE_EXERCISE.toString().equals(resType)) {
            for (String testCode : resCodes) {
                Test test = testDao.findByTestCode(testCode);
                if (ObjectUtils.isNull(test)){
                    return ResultCodeVo.errorCode("有测验没有查询到，请重新审核！");
                }
                ResShareCheck resShareCheck = null;
                List<ResShareCheck> checkList = shareCheckDao.getResByCodeAndType(CoreConstants.RES_TYPE_EXERCISE,
                                testCode);
                if (checkList != null && checkList.size() > 0) {
                    resShareCheck = checkList.get(0);
                }
                testDao.updateCheckStatus(testCode, checkCode,ip,userInfoVo.getUserName());
                shareCheckUpdateCount=shareCheckDao.updateShareCheckStatusNew(testCode, test.getShareTime(), checkCode, userInfoVo, ip,
                                comment);
                if(CoreConstants.CHECK_STATUS_CHECKED.toString().equals(checkCode)){
                    approveCheckQuestion4Test(test);
                }
                if (ObjectUtils.isNull(resShareCheck))
                    continue;
                messageService.sendMessage(Integer.parseInt(checkCode), testCode, Integer.parseInt(resType),
                                test.getShareCheckLevel(), userInfoVo.getUserCode(), resShareCheck.getApplierOrgCode(),
                                resShareCheck.getApplierCode());
            }
            // 题目
        } else if (CoreConstants.RES_TYPE_QUESTION.toString().equals(resType)) {
            for (String questionCode : resCodes) {
                Question question = questionDao.findbyQuestionCode(questionCode);
                if (ObjectUtils.isNull(question)){
                    return ResultCodeVo.errorCode("有题目没有查询到，请重新审核！");
                }
                ResShareCheck resShareCheck = null;
                List<ResShareCheck> checkList = shareCheckDao.getResByCodeAndType(CoreConstants.RES_TYPE_QUESTION,
                                questionCode);
                if (checkList != null && checkList.size() > 0) {
                    resShareCheck = checkList.get(0);
                }
                questionDao.updateCheckStatus(questionCode, checkCode,ip,userInfoVo.getUserName());
                shareCheckUpdateCount= shareCheckDao.updateShareCheckStatusNew(questionCode, question.getShareTime(), checkCode, userInfoVo, ip,
                                comment);
                if (ObjectUtils.isNull(resShareCheck))
                    continue;
                messageService.sendMessage(Integer.parseInt(checkCode), questionCode, Integer.parseInt(resType),
                                question.getShareCheckLevel(), userInfoVo.getUserCode(),
                                resShareCheck.getApplierOrgCode(), resShareCheck.getApplierCode());
            }
        } else if (CoreConstants.RES_TYPE_FLASH.toString().equals(resType)) {//互动资源
            for (String resCode : resCodes) {
                ResFlash flash = resFlashDao.selectFlash(resCode);
                if (ObjectUtils.isNull(flash)){
                    return ResultCodeVo.errorCode("有互动资源没有查询到，请重新审核！");
                }
                ResShareCheck resShareCheck = null;
                List<ResShareCheck> checkList = shareCheckDao.getResByCodeAndType(CoreConstants.RES_TYPE_FLASH,
                                resCode);
                if (checkList != null && checkList.size() > 0) {
                    resShareCheck = checkList.get(0);
                }
                flash.setShareCheckStatus(Integer.parseInt(checkCode));
                flash.setShareCheckTime(new Timestamp(new Date().getTime()));
                if (CoreConstants.CHECK_STATUS_CHECKED.toString().equals(checkCode)) {
                    flash.setShareLevel(flash.getShareCheckLevel());
                }
                flash.setModifier(userInfoVo.getUserName());
                flash.setModifierIP(ip);
                flash.setModifyTime(new Timestamp(new Date().getTime()));
                resFlashDao.save(flash);
                shareCheckUpdateCount=shareCheckDao.updateShareCheckStatusNew(resCode, flash.getShareTime(), checkCode, userInfoVo, ip,
                                comment);
                if (ObjectUtils.isNull(resShareCheck))
                    continue;
                messageService.sendMessage(Integer.parseInt(checkCode), resCode, Integer.parseInt(resType),
                                flash.getShareCheckLevel(), userInfoVo.getUserCode(), resShareCheck.getApplierOrgCode(),
                                resShareCheck.getApplierCode());
            }
        }
        if(0<shareCheckUpdateCount){
            return ResultCodeVo.rightCode("审核成功！");
        }else{
            return ResultCodeVo.errorCode("审核失败！");
        }
    }
    
    /**
     * 
     * (试卷共享审核通过，试卷下教师自己创建的题目也默认共享)<br>
     * @param test
     */
    private void approveCheckQuestion4Test(Test test){
        String testCode=test.getTestCode();
        String makerCode=test.getMakerCode();
        String makerOrgCode=test.getMakerOrgCode();
        Integer shareCheckLevel=test.getShareCheckLevel();
        UserInfoVo userInfo=getUserInfoVo();
        String ipAddress=getIp();
        Timestamp currentTime=DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        //查询一份试卷下教师自己创建的题目
        List<Map<String,Object>> questionList=exerciseQuestionDao.selectQuestionListByMakerCode(testCode, makerCode, makerOrgCode);
        if(ObjectUtils.isNotNull(questionList)&&questionList.size()>0){
            for(Map<String,Object> map:questionList){
                String questionCode=MapUtils.getString(map, "questionCode");
                //查询题目详细信息
                Question question = questionDao.findbyQuestionCode(questionCode);
                if (question.getShareLevel().intValue() >= shareCheckLevel.intValue() || (ObjectUtils
                                .isNotNull(question.getShareCheckStatus())
                                && !CoreConstants.CHECK_STATUS_CHECKED.equals(question.getShareCheckStatus()))) {
                    // 题目的共享级别>测验的共享审核级别，或者题目处于共享待审核状态或已退回状态，本题不做处理，开始下一个循环
                    continue;
                }else{
                    //更新题目的共享级别
                    questionDao.updateShareLevel(questionCode, shareCheckLevel, CoreConstants.CHECK_STATUS_CHECKED,
                                    userInfo.getUserName(), ipAddress);
                    // 新增一条题目共享审核成功记录
                    ResShareCheck resShareCheck = new ResShareCheck();
                    resShareCheck.setResCode(questionCode);
                    resShareCheck.setResTypeL1(CoreConstants.RES_TYPE_QUESTION);
                    resShareCheck.setResName("题目内容");
                    String commonUrl = SystemConfig.questionPreviewUrl;
                    String resAccessPath = commonUrl.replaceAll("(resCode[^&]*)", "resCode=" + questionCode);
                    String uid = UserHelper.encrypt(userInfo.getUserCode());
                    resAccessPath = resAccessPath + "&uid=" + uid;
                    resShareCheck.setResAccessPath(resAccessPath);
                    resShareCheck.setSubjectCode(question.getSubjectCode());
                    resShareCheck.setShareLevel(question.getShareLevel());
                    resShareCheck.setShareCheckLevel(shareCheckLevel);
                    resShareCheck.setShareCheckStatus(CoreConstants.CHECK_STATUS_CHECKED);
                    resShareCheck.setApplierCode(question.getMakerCode());
                    resShareCheck.setApplierName(question.getMakerName());
                    resShareCheck.setApplierOrgCode(question.getMakerOrgCode());
                    resShareCheck.setApplierOrgName(question.getMakerOrgName());
                    resShareCheck.setApplyTime(currentTime);
                    resShareCheck.setCheckerCode(userInfo.getUserCode());
                    resShareCheck.setCheckerName(userInfo.getUserName());
                    resShareCheck.setCheckerOrgCode(userInfo.getOrgCode());
                    resShareCheck.setCheckerOrgName(userInfo.getOrgName());
                    resShareCheck.setCheckTime(currentTime);
                    resShareCheck.setFlagDelete(CoreConstants.FLAG_DELETE_NO);
                    resShareCheck.setModifier(userInfo.getUserName());
                    resShareCheck.setModifierIP(ipAddress);
                    resShareCheck.setModifyTime(currentTime);
                    try {
                        shareCheckDao.save(resShareCheck);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //发送题目审核通过信息到资源中心
                    messageService.sendMessage(CoreConstants.CHECK_STATUS_CHECKED, questionCode,
                                    CoreConstants.RES_TYPE_QUESTION, shareCheckLevel, userInfo.getUserCode(),
                                    question.getMakerOrgCode(), question.getMakerCode());
                }
            }
        }
    }
    
    /**
     * 
     * 课时共享审核通过，往课时共享表插入一条记录
     * 为课程中心做准备
     * @param lesson 课时信息
     */
    private void insertLessonShareInfo(Lesson lesson){
        LessonShare lessonShare=new LessonShare();
        lessonShare.setGradeCode(lesson.getGradeCode());
        lessonShare.setSubjectCode(lesson.getSubjectCode());
        lessonShare.setTbCode(lesson.getTbCode());
        lessonShare.setTextbookVerCode(lesson.getTextbookVerCode());
        lessonShare.setLessonCode(lesson.getLessonCode());
        lessonShare.setLessonName(lesson.getLessonName());
        lessonShare.setBizVersion(1);
        lessonShare.setShareLevel(lesson.getShareCheckLevel());
        lessonShare.setShareTime(lesson.getShareTime());
        lessonShare.setMakerOrgCode(lesson.getOrgCode());
        lessonShare.setMakerCode(lesson.getTeacherCode());
        lessonShare.setMakerName(lesson.getTeacherName());
        lessonShare.setMakerOrgName(lesson.getOrgName());
        lessonShareDao.insert(lessonShare);
    }
}

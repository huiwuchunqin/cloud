package com.baizhitong.resource.common.core.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baizhitong.resource.common.config.SystemConfig;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.service.ResourceService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.FileAboutHelper;
import com.baizhitong.resource.common.utils.HttpUtils;
import com.baizhitong.resource.dao.rel.RelResGradeDao;
import com.baizhitong.resource.dao.rel.RelResKpDao;
import com.baizhitong.resource.dao.rel.RelResSectionDao;
import com.baizhitong.resource.dao.rel.RelResSubjectDao;
import com.baizhitong.resource.dao.rel.RelResTbcDao;
import com.baizhitong.resource.dao.rel.RelResTbvDao;
import com.baizhitong.resource.dao.res.ResDocDao;
import com.baizhitong.resource.dao.res.ResMediaDao;
import com.baizhitong.resource.dao.share.ShareResTypeL1Dao;
import com.baizhitong.resource.dao.share.ShareResTypeL2Dao;
import com.baizhitong.resource.manage.textbook.service.TextbookChapterService;
import com.baizhitong.resource.model.rel.RelResGrade;
import com.baizhitong.resource.model.rel.RelResKp;
import com.baizhitong.resource.model.rel.RelResSection;
import com.baizhitong.resource.model.rel.RelResSubject;
import com.baizhitong.resource.model.rel.RelResTbc;
import com.baizhitong.resource.model.rel.RelResTbv;
import com.baizhitong.resource.model.res.ResDoc;
import com.baizhitong.resource.model.res.ResMedia;
import com.baizhitong.resource.model.share.ShareResTypeL1;
import com.baizhitong.resource.model.share.ShareResTypeL2;
import com.baizhitong.resource.model.vo.share.ShareResTypeL1Vo;
import com.baizhitong.resource.model.vo.share.ShareResTypeL2Vo;
import com.baizhitong.utils.StringUtils;

/**
 * Created by bzt-00 on 2015/12/16
 * 
 * @author wgh 共用接口-资源 （提供一些资源相关接口）
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    /**
     * 资源基类 dao
     */
    private @Autowired ShareResTypeL1Dao      shareResTypeL1Dao;

    /**
     * 资源子类 dao
     */
    private @Autowired ShareResTypeL2Dao      shareResTypeL2Dao;

    /**
     * 资源-视频 Dao
     */
    private @Autowired ResMediaDao            resMediaDao;

    /**
     * 资源-文档 Dao
     */
    private @Autowired ResDocDao              resDocDao;

    /**
     * 教材章节接口
     */
    private @Autowired TextbookChapterService textbookChapterService;

    /** 资源与学科关系 */
    private @Autowired RelResSubjectDao       relResSubjectDao;

    /** 资源与年级关系 */
    private @Autowired RelResGradeDao         relResGradeDao;

    /** 资源与知识点关系 */
    private @Autowired RelResKpDao            relResKpDao;

    /** 资源与教材章节关系 */
    private @Autowired RelResTbcDao           relResTbcDao;

    /** 资源与教材版本关系 */
    private @Autowired RelResTbvDao           relResTbvDao;

    /** 资源与学段关系 */
    private @Autowired RelResSectionDao       relResSectionDao;

    private static final Logger               log = LoggerFactory.getLogger(ResourceServiceImpl.class);

    /**
     * 查询资源大类
     * 
     * @return
     */
    @Override
    public ResultCodeVo queryResType() throws Exception {
        List<ShareResTypeL1> resTypeL1List = shareResTypeL1Dao.selectResTypeL1List();
        return new ResultCodeVo(true, "", "", new ShareResTypeL1Vo().getVoList(resTypeL1List));
    }

    /**
     * 查询资源子类
     * 
     * @param code 父类Code
     * @return
     * @throws Exception
     */
    @Override
    public ResultCodeVo queryResTypeChilds(Integer code) throws Exception {
        List<ShareResTypeL2> resTypeL2List = shareResTypeL2Dao.selectResTypeL2List(code);
        return new ResultCodeVo(true, "", "", new ShareResTypeL2Vo().getVoList(resTypeL2List));
    }

    /**
     * 判断文件是否已存在
     * 
     * @param crcCode 文件唯一码
     * @param ext 文件后缀
     * @return
     */
    @Override
    public ResultCodeVo fileIsExist(String crcCode, String ext) throws Exception {
        ResultCodeVo result = new ResultCodeVo(true, FileAboutHelper.FILE_EXIST_FALSE, "");
        String resourceType = FileAboutHelper.getResourceType(ext);

        if (resourceType.equals(FileAboutHelper.RESOURCE_TYPE_VIDEO)) {
            ResMedia media = resMediaDao.selectResMediaByCrcCode(crcCode, null);
            if (media != null) {
                result.setCode(FileAboutHelper.FILE_EXIST_TRUE);
                result.setData(media);
            }
        } else if (resourceType.equals(FileAboutHelper.RESOURCE_TYPE_DOCUMENT)) {
            ResDoc doc = resDocDao.selectResDocByCrcCode(crcCode, null);
            if (doc != null) {
                result.setCode(FileAboutHelper.FILE_EXIST_TRUE);
                result.setData(doc);
            }
        } else {
            result.setMsg("该类型文件不支持秒传.");
        }
        return result;
    }

    /**
     * 转码回调处理
     * 
     * @param resType 资源类型
     * @param dataId 资源id
     * @param fileInfo 文件信息
     */
    @Override
    public void transcodingUpdate(Integer resType, Integer dataId, Map fileInfo) {
        Timestamp now = new Timestamp(new Date().getTime());
        // 更新视频
        if (CoreConstants.RES_TYPE_MEDIA.equals(resType)) {
            ResMedia media = resMediaDao.selectResMedia(dataId);
            if (CoreConstants.TRANSCODING_STATUS_SUCCESS.equals(MapUtils.getString(fileInfo, "convertStatus"))) {
                media.setMediaDurationMS(MapUtils.getInteger(fileInfo, "mediaDuration")); // 时长
                media.setThumbnailPath(MapUtils.getString(fileInfo, "thumbnail")); // 12秒一张缩略图
                media.setHighPath(MapUtils.getString(fileInfo, "highPath")); // mp4地址
                media.setP2pPath(MapUtils.getString(fileInfo, "p2pPath")); // p2p地址
                media.setConvertCompletedTime(now);
                media.setResStatus(CoreConstants.RESOURCE_STATE_CONVERT_SUCCESS);
            } else
                media.setResStatus(CoreConstants.RESOURCE_STATE_CONVERT_FAIL);
            resMediaDao.updateMediaInfo(media);
            if (CoreConstants.TRANSCODING_STATUS_SUCCESS.equals(MapUtils.getString(fileInfo, "convertStatus"))) {
                resMediaDao.updateSameResCodeMedia(media);
            }

        } else if (CoreConstants.RES_TYPE_DOC.equals(resType)) {// 更新文档
            ResDoc doc = resDocDao.selectResDoc(dataId);
            if (CoreConstants.TRANSCODING_STATUS_SUCCESS.equals(MapUtils.getString(fileInfo, "convertStatus"))) {
                doc.setConvertCompletedTime(now);
                doc.setPages(MapUtils.getInteger(fileInfo, "pages"));
                // 文档页数
                /*
                 * //互动ppt地址 if
                 * (doc.getFlagDynamicPPT().compareTo(CoreConstants.DYNAMICPPT_STATUS_TRUE) == 0) {
                 * doc.setConvertedPathHTML(MapUtils.getString(fileInfo, "visitUrl")); } else//文档地址
                 */ doc.setConvertedPathHTML(MapUtils.getString(fileInfo, "convertedPath"));
                doc.setResStatus(CoreConstants.RESOURCE_STATE_CONVERT_SUCCESS);
            } else
                doc.setResStatus(CoreConstants.RESOURCE_STATE_CONVERT_FAIL);
            resDocDao.updateDocInfo(doc);
            if (CoreConstants.TRANSCODING_STATUS_SUCCESS.equals(MapUtils.getString(fileInfo, "convertStatus"))) {
                resDocDao.updateDocInfo(doc);
            }
        }
    }

    /**
     * 提交转码
     * 
     * @param id 资源id
     * @param ResTypeL1 资源类型
     *
     */
    @Override
    public Map submitConvertByResId(Integer id, Integer ResTypeL1, String referer) {
        Map responseMap = null; // 提交转码返回信息
        ResDoc doc = null; // 文档
        ResMedia media = null; // 视频
        // 提交转码接口params
        Map<String, String> param = new HashedMap();
        param.put("resType", ResTypeL1 + "");
        param.put("referer", referer);
        param.put("dataId", id + "");
        param.put("convertType", "0"); // ppt转换标志(1为ppt，其他为0)

        Integer resStatus = 0; // 资源状态
        String url = ""; // 转码提交地址

        // 组装转码接口params
        if (CoreConstants.RES_TYPE_DOC.equals(ResTypeL1)) {
            doc = resDocDao.selectResDoc(id);
            resStatus = doc.getResStatus();
            url = SystemConfig.getConvertHost() + CoreConstants.CONVERT_SRC_ASYNCHRONOUS;
            /*
             * if(doc.getFlagDynamicPPT().compareTo(CoreConstants.DYNAMICPPT_STATUS_TRUE)==0){
             * param.put("convertType","1"); }
             */
            param.put("objectId", doc.getObjectId());
        } else if (CoreConstants.RES_TYPE_MEDIA.equals(ResTypeL1)) {
            media = resMediaDao.selectResMedia(id);
            url = SystemConfig.getVideoConvertHost() + CoreConstants.CONVERT_SRC_ASYNCHRONOUS;
            resStatus = media.getResStatus();
            param.put("objectId", media.getObjectId());
        }

        // 提交转码
        if (!CoreConstants.RESOURCE_STATE_CONVERT_SUCCESS.equals(resStatus)) {
            String response = HttpUtils.URLPost(url, param, HttpUtils.URL_PARAM_DECODECHARSET_UTF8);

            // 分析接口反馈信息

            try {
                responseMap = JSONObject.parseObject(response, Map.class);
                String fileKey = MapUtils.getString(responseMap, "fileKey");
                Boolean status = MapUtils.getBoolean(responseMap, "status", false);

                // 成功提交,更新资源信息
                if (status) {
                    if (CoreConstants.RES_TYPE_DOC.equals(ResTypeL1)) {
                        doc.setFileKey(fileKey);
                        doc.setResStatus(CoreConstants.RESOURCE_STATE_CONVERTING);
                        resDocDao.updateDocInfo(doc);
                    } else if (CoreConstants.RES_TYPE_MEDIA.equals(ResTypeL1)) {
                        media.setFileKey(fileKey);
                        media.setResStatus(CoreConstants.RESOURCE_STATE_CONVERTING);
                        resMediaDao.updateMediaInfo(media);
                    }
                } else {
                    log.error("转码提交失败,url:" + url + "\n params:" + JSONObject.toJSONString(param) + "\n回调信息："
                                    + response);
                }
            } catch (Exception e) {
                log.error("异步提交转码任务！url:" + url + "\n params:" + JSONObject.toJSONString(param) + "\n回调信息：" + response);
            }

        }

        return responseMap;
    }

    /**
     * 新增 资源与教材章节关系
     * 
     * @param res
     * @param chapterList
     */
    private void addRelReschapterList(Map res, List<String> chapterList, UserInfoVo userInfo, String ip)
                    throws InvocationTargetException, IllegalAccessException {
        List<RelResTbc> list = new ArrayList<RelResTbc>();
        String resCode = MapUtils.getString(res, "crcCode");
        Integer resTypeL1 = MapUtils.getInteger(res, "resTypeL1");
        Integer resId = MapUtils.getInteger(res, "id");
        Timestamp now = new Timestamp(new Date().getTime());
        String userName = userInfo.getUserName();

        if (chapterList != null && chapterList.size() > 0) {
            for (String code : chapterList) {
                if (!StringUtils.isEmpty(code)) {
                    Map<String, Object> relResTbcMap = relResTbcDao.selectRelResTbcMap(resTypeL1, resId, code);
                    if (relResTbcMap != null) {
                        RelResTbc tbc = new RelResTbc(MapUtils.getInteger(relResTbcMap, "id"), resTypeL1, resId,
                                        resCode, code, userName, now, ip);
                        relResTbcDao.updateRelResTbc(tbc);
                    } else {
                        RelResTbc tbc = new RelResTbc(null, resTypeL1, resId, resCode, code, userName, now, ip);
                        list.add(tbc);
                    }
                }
            }
        }

        relResTbcDao.saveRelResTbcList(list);
    }

    /**
     * 新增 资源与教材版本关系
     * 
     * @param res
     * @param textbookList
     */
    private void addRelResTextbookList(Map res, List<String> textbookList, UserInfoVo userInfo, String ip)
                    throws InvocationTargetException, IllegalAccessException {
        List<RelResTbv> list = new ArrayList<RelResTbv>();
        String resCode = MapUtils.getString(res, "crcCode");
        Integer resTypeL1 = MapUtils.getInteger(res, "resTypeL1");
        Integer resId = MapUtils.getInteger(res, "id");
        Timestamp now = new Timestamp(new Date().getTime());
        String userName = userInfo.getUserName();

        if (textbookList != null && textbookList.size() > 0) {
            for (String code : textbookList) {
                if (!StringUtils.isEmpty(code)) {
                    RelResTbv tbc = new RelResTbv(null, resTypeL1, resId, resCode, code, userName, now, ip);
                    list.add(tbc);
                }
            }
        }

        relResTbvDao.saveRelResTbvList(list);
    }

    /**
     * 新增 资源与知识点关系
     * 
     * @param res
     * @param knowledegList
     */
    private void addRelResknowLedegList(Map res, List<String> knowledegList, UserInfoVo userInfo, String ip)
                    throws InvocationTargetException, IllegalAccessException {
        List<RelResKp> list = new ArrayList<RelResKp>();
        String resCode = MapUtils.getString(res, "crcCode");
        Integer resTypeL1 = MapUtils.getInteger(res, "resTypeL1");
        Integer resId = MapUtils.getInteger(res, "id");
        Timestamp now = new Timestamp(new Date().getTime());
        String userName = userInfo.getUserName();

        if (knowledegList != null && knowledegList.size() > 0) {
            for (String code : knowledegList) {
                if (!StringUtils.isEmpty(code)) {
                    Map<String, Object> relResKpMap = relResKpDao.selectRelResKpMap(resTypeL1, resId, code);
                    if (relResKpMap != null) {
                        RelResKp kp = new RelResKp(MapUtils.getInteger(relResKpMap, "id"), resTypeL1, resId, resCode,
                                        code, userName, now, ip);
                        relResKpDao.updateRelResKp(kp);
                    } else {
                        RelResKp kp = new RelResKp(null, resTypeL1, resId, resCode, code, userName, now, ip);
                        list.add(kp);
                    }
                }
            }
        }

        relResKpDao.saveRelResKpList(list);
    }

    /**
     * 新增 资源与学科关系
     * 
     * @param res
     * @param subjectRelList
     */
    private void addRelResSubjectList(Map res, List<String> subjectRelList, UserInfoVo userInfo, String ip) {

        List<RelResSubject> list = new ArrayList<RelResSubject>();
        String resCode = MapUtils.getString(res, "crcCode");
        Integer resTypeL1 = MapUtils.getInteger(res, "resTypeL1");
        Integer resId = MapUtils.getInteger(res, "id");
        Timestamp now = new Timestamp(new Date().getTime());
        String userName = userInfo.getUserName();

        if (subjectRelList != null && subjectRelList.size() > 0) {
            for (String code : subjectRelList) {
                if (!StringUtils.isEmpty(code)) {
                    RelResSubject tbc = new RelResSubject(null, resTypeL1, resId, resCode, code, userName, now, ip);
                    list.add(tbc);
                }
            }
        }

        try {
            relResSubjectDao.saveRelResSubjectList(list);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 资源 资源与年级关系
     * 
     * @param res
     * @param gradeRelList
     */
    private void addRelResGradeList(Map res, List<String> gradeRelList, UserInfoVo userInfo, String ip)
                    throws InvocationTargetException, IllegalAccessException {
        List<RelResGrade> list = new ArrayList<RelResGrade>();
        String resCode = MapUtils.getString(res, "crcCode");
        Integer resTypeL1 = MapUtils.getInteger(res, "resTypeL1");
        Integer resId = MapUtils.getInteger(res, "id");
        Timestamp now = new Timestamp(new Date().getTime());
        String userName = userInfo.getUserName();

        if (gradeRelList != null && gradeRelList.size() > 0) {
            for (String code : gradeRelList) {
                if (!StringUtils.isEmpty(code)) {
                    RelResGrade tbc = new RelResGrade(null, resTypeL1, resId, resCode, code, "", userName, now, ip);
                    list.add(tbc);
                }
            }
        }

        relResGradeDao.saveRelResGradeList(list);
    }

    /**
     * 新增 资源与学段关系
     * 
     * @param res 资源Map
     * @param sectionRelList 学段列表
     */
    private void addRelResSectionList(Map res, List<String> sectionRelList, UserInfoVo userInfo, String ip)
                    throws InvocationTargetException, IllegalAccessException {
        List<RelResSection> list = new ArrayList<RelResSection>();
        String resCode = MapUtils.getString(res, "crcCode");
        Integer resTypeL1 = MapUtils.getInteger(res, "resTypeL1");
        Integer resId = MapUtils.getInteger(res, "id");
        Timestamp now = new Timestamp(new Date().getTime());
        String userName = userInfo.getUserName();

        if (sectionRelList != null && sectionRelList.size() > 0) {
            for (String code : sectionRelList) {
                if (!StringUtils.isEmpty(code)) {
                    RelResSection tbc = new RelResSection(null, resTypeL1, resId, resCode, code, userName, now, ip);
                    list.add(tbc);
                }
            }
        }

        relResSectionDao.saveRelResSectionList(list);
    }

    @Override
    public ResultCodeVo saveList(String list, UserInfoVo userInfoVo, String ip, String hostByRequest) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
}

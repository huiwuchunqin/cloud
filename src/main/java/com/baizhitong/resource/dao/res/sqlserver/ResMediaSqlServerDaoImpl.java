package com.baizhitong.resource.dao.res.sqlserver;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.res.ResMediaDao;
import com.baizhitong.resource.dao.res.ResRecommendDao;
import com.baizhitong.resource.model.res.ResMedia;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.ObjectUtils;
import com.baizhitong.utils.RowMapperUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 资源_音视频媒体数据SqlServer接口实现
 * 
 * @author creator cuidongcheng 2015/12/03
 * @author updater
 */
@Service("resMediaSqlServerDao")
public class ResMediaSqlServerDaoImpl extends BaseSqlServerDao<ResMedia> implements ResMediaDao {
    /** 推荐资源数据接口 */
    private @Autowired ResRecommendDao resRecommendDao;

    /**
     * 获取视频分页集合
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param versionCode 版本编码
     * @param tbcCode 教材章节编码
     * @param kpCode 知识点编码
     * @param mediaName 视频名称
     * @param resTypeL2 资源分类(二级)
     * @param sort 排序方式(null:默认,10:最热,20:最新)
     * @param pageNo 当前页数
     * @param pageSize 每页条数
     * @return 视频分页集合
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<Map<String, Object>> selectMediaPage(String sectionCode, String gradeCode, String subjectCode,
                    String versionCode, String tbcCode, String kpCode, String mediaName, String resTypeL2, String sort,
                    Integer pageNo, Integer pageSize) {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        // sql.append(" SELECT tb.* ");
        // sql.append(" FROM ( ");DISTINCT
        sql.append(" SELECT  rm.id, rm.resName, rm.mediaDurationMS, rm.thumbnailPath, rm.trialTimeRate, rm.browseCount, rm.downloadCount, rm.goodCount, ");
        sql.append(" rm.commentCount, rm.makeTime as uploadTime, scse.name AS sectionName, scs.name AS subjectName, scg.name AS gradeName, sctv.name AS textbookVerName ");
        sql.append(" FROM res_media rm ");
        sql.append(" LEFT JOIN rel_res_section rrse ON rrse.resTypeL1 = rm.resTypeL1 AND rrse.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_section scse ON scse.code = rrse.sectionCode ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rm.resTypeL1 AND rrs.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rm.resTypeL1 AND rrg.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode ");
        sql.append(" LEFT JOIN rel_res_tbv rrt ON rrt.resTypeL1 = rm.resTypeL1 AND rrt.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_textbook_ver sctv ON sctv.code = rrt.tbvCode ");
        // sql.append(" LEFT JOIN rel_res_kp rrk ON rrk.resTypeL1 = rm.resTypeL1 AND rrk.resId =
        // rm.id ");
        // sql.append(" LEFT JOIN share_knowledge_point_textbook skpt ON skpt.textbookVerCode =
        // sctv.code AND skpt.code = rrk.kpCode ");
        // sql.append(" LEFT JOIN rel_res_tbc rrt2 ON rrt2.resTypeL1 = rm.resTypeL1 AND rrt2.resId =
        // rm.id ");
        // sql.append(" LEFT JOIN share_textbook_chapter stc ON stc.textBookCode = sctv.code AND
        // stc.code = rrt2.tbcCode ");
        sql.append(" WHERE rm.shareLevel = 60 AND　rm.flagDelete = 0 AND rm.flagAllowBrowse = 1 AND rm.shareCheckStatus = 20 AND rm.resStatus = 20 ");
        // 判断学段编码是否为空
        if (!StringUtils.isEmpty(sectionCode)) {
            sql.append(" AND rrse.sectionCode = :sectionCode ");
        }
        // 判断年级编码是否为空
        if (!StringUtils.isEmpty(gradeCode)) {
            sql.append(" AND rrg.gradeCode = :gradeCode ");
        }
        // 判断学科编码是否为空
        if (!StringUtils.isEmpty(subjectCode)) {
            sql.append(" AND rrs.subjectCode = :subjectCode ");
        }
        // 判断版本编码是否为空
        if (!StringUtils.isEmpty(versionCode)) {
            sql.append(" AND rrt.tbvCode = :versionCode ");
        }
        // 判断资源分类(二级)是否为空
        if (!StringUtils.isEmpty(resTypeL2)) {
            sql.append(" AND rm.resTypeL2 = :resTypeL2 ");
        }
        // 判断视频名称是否为空
        if (!StringUtils.isEmpty(mediaName)) {
            sql.append(" AND rm.resName LIKE '%" + mediaName + "%' ");
        }
        // 判断教材章节编码是否为空
        if (!StringUtils.isEmpty(tbcCode)) {
            // sql.append(" AND rrt2.tbcCode LIKE '" + tbcCode + "%' ");
            sql.append(" AND rm.id IN(SELECT resId FROM rel_res_tbc WHERE resTypeL1 = 10 AND tbcCode LIKE '" + tbcCode
                            + "%') ");
        }
        // 判断知识点编码是否为空
        if (!StringUtils.isEmpty(kpCode)) {
            // sql.append(" AND rrk.kpCode LIKE '" + kpCode + "%' ");
            sql.append(" AND rm.id IN(SELECT resId FROM rel_res_kp WHERE resTypeL1 = 10 AND kpCode LIKE '" + kpCode
                            + "%') ");
        }

        // 排序方式(null:默认,10:最热,20:最新)
        if (!StringUtils.isEmpty(sort) && "10".equals(sort)) {
            sql.append(" ORDER BY rm.browseCount DESC, rm.id ");
        } else if (!StringUtils.isEmpty(sort) && "20".equals(sort)) {
            sql.append(" ORDER BY rm.makeTime DESC, rm.id ");
        } else {
            sql.append(" ORDER BY rm.id ");
        }
        // sql.append(" ) tb ");

        // 排序方式(null:默认,10:最热,20:最新)
        /*
         * if (!StringUtils.isEmpty(sort) && "10".equals(sort)) { sql.append(
         * " ORDER BY tb.browseCount DESC, tb.id "); } else if (!StringUtils.isEmpty(sort) &&
         * "20".equals(sort)) { sql.append(" ORDER BY tb.uploadTime DESC, tb.id "); } else {
         * sql.append(" ORDER BY tb.id "); }
         */

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sectionCode", sectionCode);
        map.put("gradeCode", gradeCode);
        map.put("subjectCode", subjectCode);
        map.put("versionCode", versionCode);
        map.put("tbcCode", tbcCode);
        map.put("kpCode", kpCode);
        map.put("resName", mediaName);
        map.put("resTypeL2", resTypeL2);
        map.put("sort", sort);

        // 执行SQL
        return super.queryDistinctPage(sql.toString(), map, pageNo, pageSize);
    }

    /**
     * 获取视频信息
     * 
     * @param mediaId 视频ID
     * @return 信息
     */
    @Override
    public Map<String, Object> selectMediaInfo(Integer mediaId) {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT rm.id, rm.resName, rm.mediaDurationMS, rm.coverPath, rm.lowPath, rm.highPath, rm.p2pPath, rm.trialTimeRate, ");
        sql.append("  rm.creator, rm.createTime, rm.browseCount, rm.downloadCount, rm.goodCount, rm.commentCount, rm.shareLevel, ");
        sql.append(" rm.resDesc ");
        sql.append(" FROM res_media rm ");
        sql.append(" WHERE rm.id = :mediaId AND　rm.flagDelete = 0 AND rm.resStatus = 20 ");

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mediaId", mediaId);

        // 执行SQL
        return super.findUniqueBySql(sql.toString(), map);
    }

    /**
     * 获取相关视频信息
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param textbookVerCode 教材版本编码
     * @param knowledgePointCode 知识点编码
     * @param textbookChapterCode 教材章节编码
     * @return 相关视频信息
     */
    @Override
    public List<Map<String, Object>> selectRelevantMediaInfo(String sectionCode, String gradeCode, String subjectCode,
                    String textbookVerCode, String knowledgePointCode, String textbookChapterCode) {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT DISTINCT TOP 5 * FROM ( ");
        sql.append(" SELECT DISTINCT TOP 5 rm.id, rm.resName, rm.browseCount, rm.makeTime as uploadTime ");
        sql.append(" FROM res_media rm ");
        sql.append(" LEFT JOIN rel_res_section rrse ON rrse.resTypeL1 = rm.resTypeL1 AND rrse.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_section scse ON scse.code = rrse.sectionCode ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rm.resTypeL1 AND rrs.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rm.resTypeL1 AND rrg.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode ");
        sql.append(" LEFT JOIN rel_res_tbv rrt ON rrt.resTypeL1 = rm.resTypeL1 AND rrt.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_textbook_ver sctv ON sctv.code = rrt.tbvCode ");
        sql.append(" LEFT JOIN rel_res_tbc rrt2 ON rrt2.resTypeL1 = rm.resTypeL1 AND rrt2.resId = rm.id ");
        sql.append(" LEFT JOIN share_textbook_chapter stc ON stc.textBookCode = sctv.code AND stc.code = rrt2.tbcCode ");
        sql.append(" WHERE rm.shareLevel = 60 AND　rm.flagDelete = 0 AND rm.flagAllowBrowse = 1 AND rm.shareCheckStatus = 20 AND rm.resStatus = 20 ");
        // 判断学段编码是否为空
        if (!StringUtils.isEmpty(sectionCode)) {
            sql.append(" AND rrse.sectionCode = :sectionCode ");
        }
        // 判断年级编码是否为空
        if (!StringUtils.isEmpty(gradeCode)) {
            sql.append(" AND scg.code = :gradeCode ");
        }
        // 判断学科编码是否为空
        if (!StringUtils.isEmpty(subjectCode)) {
            sql.append(" AND scs.code = :subjectCode ");
        }
        // 判断版本编码是否为空
        if (!StringUtils.isEmpty(textbookVerCode)) {
            sql.append(" AND sctv.code = :textbookVerCode ");
        }
        // 判断教材章节编码是否为空
        if (!StringUtils.isEmpty(textbookChapterCode)) {
            sql.append(" AND stc.code LIKE '" + textbookChapterCode + "%' ");
        }
        sql.append(" ORDER BY rm.browseCount DESC, rm.makeTime  DESC ");
        sql.append(" UNION ALL ");
        sql.append(" SELECT DISTINCT TOP 5 rm.id, rm.resName, rm.browseCount, rm.makeTime as uploadTime ");
        sql.append(" FROM res_media rm ");
        sql.append(" LEFT JOIN rel_res_section rrse ON rrse.resTypeL1 = rm.resTypeL1 AND rrse.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_section scse ON scse.code = rrse.sectionCode ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rm.resTypeL1 AND rrs.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rm.resTypeL1 AND rrg.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode ");
        sql.append(" LEFT JOIN rel_res_tbv rrt ON rrt.resTypeL1 = rm.resTypeL1 AND rrt.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_textbook_ver sctv ON sctv.code = rrt.tbvCode ");
        sql.append(" LEFT JOIN rel_res_kp rrk ON rrk.resTypeL1 = rm.resTypeL1 AND rrk.resId = rm.id ");
        sql.append(" LEFT JOIN share_knowledge_point_textbook skpt ON skpt.textbookVerCode = sctv.code AND skpt.code = rrk.kpCode ");
        sql.append(" WHERE rm.shareLevel = 60 AND　rm.flagDelete = 0 AND rm.flagAllowBrowse = 1 AND rm.shareCheckStatus = 20 AND rm.resStatus = 20 ");
        // 判断学段编码是否为空
        if (!StringUtils.isEmpty(sectionCode)) {
            sql.append(" AND rrse.sectionCode = :sectionCode ");
        }
        // 判断年级编码是否为空
        if (!StringUtils.isEmpty(gradeCode)) {
            sql.append(" AND scg.code = :gradeCode ");
        }
        // 判断学科编码是否为空
        if (!StringUtils.isEmpty(subjectCode)) {
            sql.append(" AND scs.code = :subjectCode ");
        }
        // 判断版本编码是否为空
        if (!StringUtils.isEmpty(textbookVerCode)) {
            sql.append(" AND sctv.code = :textbookVerCode ");
        }
        // 判断知识点编码是否为空
        if (!StringUtils.isEmpty(knowledgePointCode)) {
            sql.append(" AND skpt.code LIKE '" + knowledgePointCode + "%' ");
        }
        sql.append(" ORDER BY rm.browseCount DESC, rm.makeTime  DESC ");
        sql.append(" ) rm_relevant ");
        sql.append(" ORDER BY rm_relevant.browseCount DESC, rm_relevant.uploadTime DESC ");

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sectionCode", sectionCode);
        map.put("gradeCode", gradeCode);
        map.put("subjectCode", subjectCode);
        map.put("textbookVerCode", textbookVerCode);
        map.put("knowledgePointCode", knowledgePointCode);
        map.put("textbookChapterCode", textbookChapterCode);

        // SQL执行
        return super.findBySql(sql.toString(), map);
    }

    /**
     * 更新resmedia
     * 
     * @param resMedia 资源对象
     * @return 是否成功
     */
    @Override
    public boolean updateMediaInfo(ResMedia resMedia) {
        int count = 0;
        try {
            count = super.update(resMedia);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return count > 0;
    }

    /**
     * 根据resID获取resMedia
     * 
     * @param resID 资源id
     * @return resMedia对象
     */
    @Override
    public ResMedia selectResMedia(Integer resID) {
        QueryRule queryRule = QueryRule.getInstance();
        queryRule.andEqual("id", resID);
        return super.findUnique(queryRule);
    }

    /**
     * 根据resCode获取resMedia
     * 
     * @param resCode 资源编码
     * @return resMedia对象
     */
    public ResMedia selectResMedia(String resCode) {
        QueryRule queryRule = QueryRule.getInstance();
        queryRule.andEqual("resCode", resCode);
        queryRule.andEqual("flagDelete", CoreConstants.FLAG_DELETE_NO);
        return super.findUnique(queryRule); 
    }

    /**
     * 保存资源信息
     * 
     * @param resMedia 资源对象
     * @return 是否成功
     */
    @Override
    public boolean saveMediaInfo(ResMedia resMedia) {
        boolean success = false;
        try {
            success = super.saveOne(resMedia);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 保存资源信息并返回Map
     * 
     * @param resMedia 资源对象
     * @return 是否成功
     */
    @Override
    public Integer saveMediaAndReturnId(ResMedia resMedia) throws Exception {
        Map<String, Object> mp = super.saveAndReturnId(resMedia);

        if (mp != null)
            return MapUtils.getInteger(mp, "id");

        return null;
    }

    /**
     * 获取首页视频资源集合
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param mediaName 视频名称
     * @param isHotSort 是否最热降序
     * @param isNewSort 是否最新降序
     * @return 首页视频资源集合信息
     * @exception 异常
     */
    @Override
    public List<Map<String, Object>> selectMediaResList(String sectionCode, String subjectCode, String mediaName,
                    Boolean isHotSort, Boolean isNewSort) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT TOP 6 rm.id, rm.resName, rm.browseCount, rm.downloadCount, rm.goodCount, rm.makerName, rm.makeTime as uploadTime, ");
        sql.append(" scse.name AS sectionName, scs.name AS subjectName, scg.name AS gradeName,rm.trialTimeRate ");
        sql.append(" FROM res_media rm ");
        sql.append(" LEFT JOIN rel_res_section rrse ON rrse.resTypeL1 = rm.resTypeL1 AND rrse.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_section scse ON scse.code = rrse.sectionCode ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rm.resTypeL1 AND rrs.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rm.resTypeL1 AND rrg.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode ");
        sql.append(" WHERE rm.shareLevel = 60 AND　rm.flagDelete = 0 AND rm.flagAllowBrowse = 1 AND rm.shareCheckStatus = 20 AND rm.resStatus = 20 ");

        // 判断学段编码是否为空
        if (!StringUtils.isEmpty(sectionCode)) {
            sql.append(" AND rrse.sectionCode = :sectionCode ");
        }
        // 判断学科编码是否为空
        if (!StringUtils.isEmpty(subjectCode)) {
            sql.append(" AND rrs.subjectCode = :subjectCode ");
        }
        // 判断视频名称是否为空
        if (!StringUtils.isEmpty(mediaName)) {
            sql.append(" AND rm.resName LIKE '%" + mediaName + "%' ");
        }

        // 排序方式:最热
        if (isHotSort != null && isHotSort) {
            sql.append(" ORDER BY rm.browseCount DESC, rm.id ");
        }
        // 排序方式:最新
        if (isNewSort != null && isNewSort) {
            sql.append(" ORDER BY rm.makeTime  DESC, rm.id ");
        }

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sectionCode", sectionCode);
        map.put("subjectCode", subjectCode);
        map.put("resName", mediaName);

        // 执行SQL
        return super.findBySql(sql.toString(), map);
    }

    /**
     * 根据crcCode获取视频
     * 
     * @param crcCode 文件唯一码
     * @return
     */
    @Override
    public ResMedia selectResMediaByCrcCode(String crcCode, String creator) {
        QueryRule queryRule = QueryRule.getInstance();
        queryRule.andEqual("resCode", crcCode);
        queryRule.andEqual("flagDelete", "0");

        if (StringUtils.isEmpty(creator)) {
            List<ResMedia> resMedias = super.find(queryRule);

            if (resMedias != null && resMedias.size() > 0) {
                return resMedias.get(0);
            } else {
                return null;
            }
        } else {
            queryRule.andEqual("creator", creator);
            return super.findUnique(queryRule);
        }
    }

    /**
     * 获取相同的资源编码文件
     * 
     * @param resCode
     * @return
     * @author gaow
     * @date:2015年12月25日 下午1:37:41
     */
    public int updateSameResCodeMedia(ResMedia media) {
        media.setResStatus(CoreConstants.RESOURCE_STATE_CONVERT_SUCCESS);
        String sqlString = "update  res_media set mediaDurationMS=?,thumbnailPath=?,highPath=?,p2pPath=?,convertCompletedTime=?,resStatus=?  where resCode=? and resStatus!=20";
        return super.update(sqlString, media.getMediaDurationMS(), media.getThumbnailPath(), media.getHighPath(),
                        media.getP2pPath(), media.getConvertCompletedTime(), media.getResStatus(), media.getResCode());
    }

    /**
     * 获取用户资源分页集合
     * 
     * @param userCode 用户编码
     * @param resStatus 资源状态
     * @param shareCheckStatus 审核状态
     * @param sort 排序方式(null:默认,10:最热,20:最新)
     * @param pageNo 当前页数
     * @param pageSize 每页条数
     * @return 用户资源分页集合
     * @exception 异常
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<Map<String, Object>> selectSpaceUserResPage(String userCode, Integer resStatus,
                    Integer shareCheckStatus, String sort, Integer pageNo, Integer pageSize) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT rm.id, rm.resName, rm.mediaDurationMS, rm.thumbnailPath, rm.trialTimeRate, rm.browseCount, rm.downloadCount, rm.goodCount, ");
        sql.append(" rm.commentCount, rm.makeTime as uploadTime, rm.resStatus, rm.shareCheckStatus, rm.objectId, rm.fileKey, rm.resTypeL1, ");
        sql.append(" scse.name AS sectionName, scs.name AS subjectName, scg.name AS gradeName, sctv.name AS textbookVerName ");
        sql.append(" FROM res_media rm ");
        sql.append(" LEFT JOIN rel_res_section rrse ON rrse.resTypeL1 = rm.resTypeL1 AND rrse.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_section scse ON scse.code = rrse.sectionCode ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rm.resTypeL1 AND rrs.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rm.resTypeL1 AND rrg.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode ");
        sql.append(" LEFT JOIN rel_res_tbv rrt ON rrt.resTypeL1 = rm.resTypeL1 AND rrt.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_textbook_ver sctv ON sctv.code = rrt.tbvCode ");
        sql.append(" WHERE rm.flagDelete = 0 ");

        // 判断用户编码是否为空
        if (!StringUtils.isEmpty(userCode)) {
            sql.append(" AND rm.userCode = :userCode ");
        }
        // 判断资源状态是否为空
        if (resStatus != null) {
            sql.append(" AND rm.resStatus = :resStatus ");
        }
        // 判断审核状态是否为空
        if (shareCheckStatus != null) {
            sql.append(" AND rm.shareCheckStatus = :shareCheckStatus ");
        }

        // 排序方式(null:默认,10:最热,20:最新)
        if (!StringUtils.isEmpty(sort) && "10".equals(sort)) {
            sql.append(" ORDER BY rm.browseCount DESC, rm.id ");
        } else if (!StringUtils.isEmpty(sort) && "20".equals(sort)) {
            sql.append(" ORDER BY rm.makeTime , rm.id ");
        } else {
            sql.append(" ORDER BY rm.id ");
        }

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userCode", userCode);
        map.put("resStatus", resStatus);
        map.put("shareCheckStatus", shareCheckStatus);
        map.put("sort", sort);

        // 执行SQL
        return super.queryDistinctPage(sql.toString(), map, pageNo, pageSize);
    }

    /**
     * 后台分页查询视频资源信息
     * 
     * @param param 查询参数
     * @param pageNo 页码
     * @param pageSize 每页记录数
     * @return
     * @author gaow
     * @date:2015年12月16日 上午11:39:53
     */
    @SuppressWarnings("unchecked")
    public Page<Map<String, Object>> getUnUsualMediaInfo(Map<String, Object> param, Integer pageNo, Integer pageSize) {
        // 查询参数
        // 查询参数
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String orgName = MapUtils.getString(param, "orgName");
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" select rm.*,so.orgName, scsec.name as sectionName,scs.name as subjectName,scg.name as gradeName,");
        sql.append(" (select count (*)   from rel_res_tbc  rrt where rrt.resId=rm.id and rrt.resTypeL1=rm.resTypeL1) as tbcCount ,");
        sql.append(" (select count (*)  from rel_res_kp  rrk where rrk.resId=rm.id and rrk.resTypeL1=rm.resTypeL1) as kpCount  ");
        sql.append(" FROM res_media rm ");
        sql.append(" LEFT JOIN rel_res_section rrsec ON rrsec.resTypeL1 = rm.resTypeL1 AND rrsec.resId = rm.id  ");
        sql.append(" LEFT JOIN share_code_section scsec ON scsec.code = rrsec.sectionCode  and scsec.flagDelete=0 ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rm.resTypeL1 AND rrs.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode     and scs.flagDelete=0 ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rm.resTypeL1 AND rrg.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode  and scg.flagDelete=0 ");
        sql.append(" LEFT JOIN share_org so ON so.orgCode = rm.makerOrgCode ");
        sql.append(" WHERE 1=1 and rm.flagDelete=0 ");
        sql.append(" and (  scsec.name is null or scs.name is null or scg.name is null or rm.makerName is null or rm.makerOrgCode = '' or rm.makerOrgCode IS NULL)");
        // 查询条件参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(gradeCode) && !"-1".equals(gradeCode)) {
            sql.append(" and  rrg.gradeCode=:gradeCode ");
            sqlParam.put("gradeCode", gradeCode);
        }
        if (!StringUtils.isEmpty(subjectCode) && !"-1".equals(subjectCode)) {
            sql.append(" and rrs.subjectCode=:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (!StringUtils.isEmpty(sectionCode) && !"-1".equals(sectionCode)) {
            sql.append(" and rrsec.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and so.orgName like :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        sql.append("  order by rm.makeTime desc ");
        return super.queryDistinctPage(sql.toString(), sqlParam, pageNo, pageSize);
    }

    /*************************************************** 后台查询方法 ****************************************************/
    /**
     * 后台分页查询视频资源信息
     * 
     * @param param 查询参数
     * @param pageNo 页码
     * @param pageSize 每页记录数
     * @return
     * @author gaow
     * @date:2015年12月16日 上午11:39:53
     */
    @SuppressWarnings("unchecked")
    public Page<Map<String, Object>> getAllMediaInfo(Map<String, Object> param, Integer pageNo, Integer pageSize) {
        // 查询参数
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        Integer shareCheckStatus = MapUtils.getInteger(param, "shareCheckStatus");
        String uploadTimeStart = MapUtils.getString(param, "uploadTimeStart");
        String uploadTimeEnd = MapUtils.getString(param, "uploadTimeEnd");
        String checkTimeStart = MapUtils.getString(param, "checkTimeStart");
        String checkTimeEnd = MapUtils.getString(param, "checkTimeEnd");
        String shareTimeStart = MapUtils.getString(param, "shareTimeStart");
        String shareTimeEnd = MapUtils.getString(param, "shareTimeEnd");
        String resName = MapUtils.getString(param, "resName");
        Integer resStatus = MapUtils.getInteger(param, "resStatus");
        Integer shareLevel = MapUtils.getInteger(param, "shareLevel");
        Integer kpTagStatus = MapUtils.getInteger(param, "kpTagStatus");
        Integer tbcTagStatus = MapUtils.getInteger(param, "tbcTagStatus");
        String resTypeL2 = MapUtils.getString(param, "resTypeL2");
        String userName = MapUtils.getString(param, "userName");
        String orgName = MapUtils.getString(param, "orgName");
        String orgCode = MapUtils.getString(param, "orgCode");
        Integer shareCheckLevel = MapUtils.getInteger(param, "shareCheckLevel");
        String orderType = MapUtils.getString(param, "orderType");
        Integer resStatusSuccess = MapUtils.getInteger(param, "resStatusSuccess");
        String sectionCodes = MapUtils.getString(param, "sectionCodes");
        String gradeCodes = MapUtils.getString(param, "gradeCodes");
        String subjectCodes = MapUtils.getString(param, "subjectCodes");
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append("select b.* from ( ");
        sql.append(" select rm.*,so.orgName, scsec.name as sectionName,scs.name as subjectName,scg.name as gradeName,srtl.name as resTypeL2Name, ");
        sql.append(" (select count (*)   from rel_res_tbc  rrt where rrt.resId=rm.id and rrt.resTypeL1=rm.resTypeL1) as tbcCount ,");
        sql.append(" (select count (*)  from rel_res_kp  rrk where rrk.resId=rm.id and rrk.resTypeL1=rm.resTypeL1) as kpCount  ");
        sql.append(" FROM res_media rm ");
        sql.append(" LEFT JOIN rel_res_section rrsec ON rrsec.resTypeL1 = rm.resTypeL1 AND rrsec.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_section scsec ON scsec.code = rrsec.sectionCode and scsec.flagDelete=0 ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rm.resTypeL1 AND rrs.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode  and scs.flagDelete=0 ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rm.resTypeL1 AND rrg.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode and scg.flagDelete=0 ");
        sql.append(" LEFT JOIN share_org so ON so.orgCode = rm.makerOrgCode ");
        sql.append(" LEFT JOIN share_res_type_l2  srtl  ON srtl.code = rm.resTypeL2 and srtl.codeL1=rm.resTypeL1 ");
        sql.append(" WHERE 1=1 and rm.flagDelete=0 ");
        // 查询条件参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        // 教研员登录
        if (StringUtils.isNotEmpty(sectionCodes)) {
            if (sectionCodes.contains(",")) {
                sectionCodes = "'" + sectionCodes.replace(",", "','") + "'";
            } else {
                sectionCodes = "'" + sectionCodes + "'";
            }
            sql.append(" AND rrsec.sectionCode IN (" + sectionCodes + ") ");
        }
        if (StringUtils.isNotEmpty(subjectCodes)) {
            if (subjectCodes.contains(",")) {
                subjectCodes = "'" + subjectCodes.replace(",", "','") + "'";
            } else {
                subjectCodes = "'" + subjectCodes + "'";
            }
            sql.append(" AND rrs.subjectCode IN (" + subjectCodes + ") ");
        }
        if (StringUtils.isNotEmpty(gradeCodes)) {
            if (gradeCodes.contains(",")) {
                gradeCodes = "'" + gradeCodes.replace(",", "','") + "'";
            } else {
                gradeCodes = "'" + gradeCodes + "'";
            }
            sql.append(" AND rrg.gradeCode IN (" + gradeCodes + ") ");
        }
        if (StringUtils.isNotEmpty(gradeCode) && !"-1".equals(gradeCode)) {
            sql.append(" and  rrg.gradeCode=:gradeCode ");
            sqlParam.put("gradeCode", gradeCode);
        }
        if (StringUtils.isNotEmpty(subjectCode) && !"-1".equals(subjectCode)) {
            sql.append(" and rrs.subjectCode=:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (StringUtils.isNotEmpty(sectionCode) && !"-1".equals(sectionCode)) {
            sql.append(" and rrsec.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and so.orgName like :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" and rm.makerName like :userName ");
            sqlParam.put("userName", "%" + userName.trim() + "%");
        }
        if (!StringUtils.isEmpty(resTypeL2) && !"-1".equals(resTypeL2)) {
            sql.append(" and rm.resTypeL2=:resTypeL2 ");
            sqlParam.put("resTypeL2", resTypeL2);
        }
        if (shareCheckStatus != null && shareCheckStatus.intValue() != -1) {
            if (shareCheckStatus == 60) {
                sql.append(" and (rm.shareCheckStatus is null or rm.shareCheckStatus='') ");
            } else {
                sql.append(" and rm.shareCheckStatus=:shareCheckStatus ");
            }
            sqlParam.put("shareCheckStatus", shareCheckStatus);
        }
        if (shareCheckLevel != null) {
            sql.append(" and rm.shareCheckLevel=:shareCheckLevel ");
            sqlParam.put("shareCheckLevel", shareCheckLevel);
        }
        if (!StringUtils.isEmpty(resName)) {
            sql.append(" and rm.resName like '%" + resName.trim() + "%'");
        }
        if (!StringUtils.isEmpty(uploadTimeStart)) {
            sql.append(" and rm.makeTime  >=:uploadTimeStart ");
            sqlParam.put("uploadTimeStart", uploadTimeStart);
        }
        if (!StringUtils.isEmpty(uploadTimeEnd)) {
            sql.append(" and rm.makeTime <=:uploadTimeEnd ");
            sqlParam.put("uploadTimeEnd", uploadTimeEnd + " 23:59:59");
        }
        if (!StringUtils.isEmpty(checkTimeStart)) {
            sql.append(" and rm.shareCheckTime >=:checkTimeStart ");
            sqlParam.put("checkTimeStart", checkTimeStart);
        }
        if (!StringUtils.isEmpty(checkTimeEnd)) {
            sql.append(" and rm.shareCheckTime<=:checkTimeEnd ");
            sqlParam.put("checkTimeEnd", checkTimeEnd + " 23:59:59");
        }
        if (!StringUtils.isEmpty(shareTimeStart)) {
            sql.append(" and rm.shareTime >=:shareTimeStart ");
            sqlParam.put("shareTimeStart", shareTimeStart);
        }
        if (!StringUtils.isEmpty(shareTimeEnd)) {
            sql.append(" and rm.shareTime<=:shareTimeEnd ");
            sqlParam.put("shareTimeEnd", shareTimeEnd + " 23:59:59");
        }
        if (resStatus != null && resStatus != -1) {
            sql.append(" and rm.resStatus =:resStatus ");
            sqlParam.put("resStatus", resStatus);
        }
        if (resStatusSuccess != null) {
            sql.append(" and rm.resStatus = :resStatusSuccess ");
            sqlParam.put("resStatusSuccess", resStatusSuccess);
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and rm.makerOrgCode=:orgCode ");
            sqlParam.put("orgCode", orgCode);
        }
        if (shareLevel != null && shareLevel != -1) {
            sql.append(" and rm.shareLevel =:shareLevel ");
            sqlParam.put("shareLevel", shareLevel);
        }
        sql.append(" )b where 1=1  ");
        if (kpTagStatus != null) {
            if (kpTagStatus == 1) {
                sql.append(" and b.kpCount >0 ");
            } else if (kpTagStatus == 2) {
                sql.append(" and b.kpCount =0 ");
            }
        }
        if (tbcTagStatus != null) {
            if (tbcTagStatus == 1) {
                sql.append(" and b.tbcCount >0 ");
            } else if (tbcTagStatus == 2) {
                sql.append(" and b.tbcCount =0 ");
            }
        }
        if (StringUtils.isNotEmpty(orderType)) {
            sql.append("  order by b.makeTime desc");
        } else if (shareCheckStatus == 10) {
            sql.append("  order by b.shareTime desc");
        } else {
            sql.append("  order by b.shareCheckTime desc ");
        }

        return super.queryDistinctPage(sql.toString(), sqlParam, pageNo, pageSize);
    }

    /**
     * 
     * (查询删除的资源)<br>
     * 
     * @param param
     * @param pageNo
     * @param pageSize
     * @return
     */
    @SuppressWarnings("unchecked")
    public Page<Map<String, Object>> getGarbageMediaInfo(Map<String, Object> param, Integer pageNo, Integer pageSize) {
        // 获取查询参数
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String resName = MapUtils.getString(param, "resName");
        String orgName = MapUtils.getString(param, "orgName");
        Integer resTypeL1 = MapUtils.getInteger(param, "resTypeL1");
        // 拼接SQL语句
        StringBuilder sql = new StringBuilder();
        // 查询条件
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        b.*");
        sql.append("        ,so.orgName ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            rd.id");
        sql.append("            ,rd.resName");
        sql.append("            ,scg.name AS gradeName");
        sql.append("            ,scs.name AS sectionName");
        sql.append("            ,rd.makerOrgCode");
        sql.append("            ,scs2.name AS subjectName");
        sql.append("            ,rd.makerName");
        sql.append("            ,rd.resTypeL1");
        sql.append("            ,rd.resCode ");
        sql.append("            ,rd.shareLevel ");
        sql.append("            ,rrg.gradeCode ");
        sql.append("            ,rrs.sectionCode ");
        sql.append("            ,rrs2.subjectCode ");
        sql.append("            ,rd.makeTime ");
        sql.append("            ,rd.modifyTime ");
        sql.append("        FROM");
        sql.append("            res_doc rd ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_grade rrg ");
        sql.append("                ON rd.id = rrg.resId ");
        sql.append("                AND rd.resTypeL1 = rrg.resTypeL1 ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_grade scg ");
        sql.append("                ON rrg.gradeCode = scg.code ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section rrs ");
        sql.append("                ON rd.id = rrs.resId ");
        sql.append("                AND rd.resTypeL1 = rrs.resTypeL1 ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_section scs ");
        sql.append("                ON rrs.sectionCode = scs.code ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_subject rrs2 ");
        sql.append("                ON rd.id = rrs2.resId ");
        sql.append("                AND rd.resTypeL1 = rrs2.resTypeL1 ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_subject scs2 ");
        sql.append("                ON rrs2.subjectCode = scs2.code ");
        sql.append("        WHERE");
        sql.append("            rd.flagDelete = :flagDeleteYes ");
        sql.append("            AND rd.resTypeL1 = :resTypeDoc ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            rm.id");
        sql.append("            ,rm.resName");
        sql.append("            ,scg.name AS gradeName");
        sql.append("            ,scs.name AS sectionName");
        sql.append("            ,rm.makerOrgCode");
        sql.append("            ,scs2.name AS subjectName");
        sql.append("            ,rm.makerName");
        sql.append("            ,rm.resTypeL1");
        sql.append("            ,rm.resCode ");
        sql.append("            ,rm.shareLevel ");
        sql.append("            ,rrg.gradeCode ");
        sql.append("            ,rrs.sectionCode ");
        sql.append("            ,rrs2.subjectCode ");
        sql.append("            ,rm.makeTime ");
        sql.append("            ,rm.modifyTime ");
        sql.append("        FROM");
        sql.append("            res_media rm ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_grade rrg ");
        sql.append("                ON rm.id = rrg.resId ");
        sql.append("                AND rm.resTypeL1 = rrg.resTypeL1 ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_grade scg ");
        sql.append("                ON rrg.gradeCode = scg.code ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section rrs ");
        sql.append("                ON rm.id = rrs.resId ");
        sql.append("                AND rm.resTypeL1 = rrs.resTypeL1 ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_section scs ");
        sql.append("                ON rrs.sectionCode = scs.code ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_subject rrs2 ");
        sql.append("                ON rm.id = rrs2.resId ");
        sql.append("                AND rm.resTypeL1 = rrs2.resTypeL1 ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_subject scs2 ");
        sql.append("                ON rrs2.subjectCode = scs2.code ");
        sql.append("        WHERE");
        sql.append("            rm.flagDelete = :flagDeleteYes ");
        sql.append("            AND rm.resTypeL1 = :resTypeMedia ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            rf.id");
        sql.append("            ,rf.resName");
        sql.append("            ,scg.name AS gradeName");
        sql.append("            ,scs.name AS sectionName");
        sql.append("            ,rf.makerOrgCode");
        sql.append("            ,scs2.name AS subjectName");
        sql.append("            ,rf.makerName");
        sql.append("            ,rf.resTypeL1");
        sql.append("            ,rf.resCode ");
        sql.append("            ,rf.shareLevel ");
        sql.append("            ,rrg.gradeCode ");
        sql.append("            ,rrs.sectionCode ");
        sql.append("            ,rrs2.subjectCode ");
        sql.append("            ,rf.makeTime ");
        sql.append("            ,rf.modifyTime ");
        sql.append("        FROM");
        sql.append("            res_flash rf ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_grade rrg ");
        sql.append("                ON rf.id = rrg.resId ");
        sql.append("                AND rf.resTypeL1 = rrg.resTypeL1 ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_grade scg ");
        sql.append("                ON rrg.gradeCode = scg.code ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section rrs ");
        sql.append("                ON rf.id = rrs.resId ");
        sql.append("                AND rf.resTypeL1 = rrs.resTypeL1 ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_section scs ");
        sql.append("                ON rrs.sectionCode = scs.code ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_subject rrs2 ");
        sql.append("                ON rf.id = rrs2.resId ");
        sql.append("                AND rf.resTypeL1 = rrs2.resTypeL1 ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_subject scs2 ");
        sql.append("                ON rrs2.subjectCode = scs2.code ");
        sql.append("        WHERE");
        sql.append("            rf.flagDelete = :flagDeleteYes ");
        sql.append("            AND rf.resTypeL1 = :resTypeFlash ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            rms.id");
        sql.append("            ,rms.resName");
        sql.append("            ,'' AS gradeName");
        sql.append("            ,'' AS sectionName");
        sql.append("            ,rms.makerOrgCode");
        sql.append("            ,'' AS subjectName");
        sql.append("            ,rms.makerName");
        sql.append("            ,rms.resTypeL1");
        sql.append("            ,rms.resCode ");
        sql.append("            ,rms.shareLevel ");
        sql.append("            ,'' AS gradeCode ");
        sql.append("            ,'' AS sectionCode ");
        sql.append("            ,'' AS subjectCode ");
        sql.append("            ,rms.makeTime ");
        sql.append("            ,rms.modifyTime ");
        sql.append("        FROM");
        sql.append("            res_media_special rms ");
        sql.append("        WHERE");
        sql.append("            rms.flagDelete = :flagDeleteYes ");
        sql.append("            AND rms.resTypeL1 = :resTypeSpecialMedia ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            e.id");
        sql.append("            ,e.testName AS resName");
        sql.append("            ,scg.name AS gradeName");
        sql.append("            ,scs.name AS sectionName");
        sql.append("            ,e.orgCode AS makerOrgCode");
        sql.append("            ,scs2.name AS subjectName");
        sql.append("            ,e.userName AS makerName");
        sql.append("            ,e.resTypeL1");
        sql.append("            ,e.testCode AS resCode ");
        sql.append("            ,e.shareLevel ");
        sql.append("            ,reg.gradeCode ");
        sql.append("            ,se.sectionCode ");
        sql.append("            ,e.subjectCode ");
        sql.append("            ,e.makeTime ");
        sql.append("            ,e.modifyTime ");
        sql.append("        FROM");
        sql.append("            exercise e ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_exercise_section se ");
        sql.append("                ON e.testCode = se.testCode ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_section scs ");
        sql.append("                ON se.sectionCode = scs.code ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_exercise_grade reg ");
        sql.append("                ON e.testCode = reg.testCode ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_grade scg ");
        sql.append("                ON reg.gradeCode = scg.code ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_subject scs2 ");
        sql.append("                ON e.subjectCode = scs2.code ");
        sql.append("        WHERE");
        sql.append("            e.flagDelete = :flagDeleteYes ");
        sql.append("            AND e.resTypeL1 = :resTypeExercise ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            q.id");
        sql.append("            ,q.questionCode AS resName");
        sql.append("            ,scg.name AS gradeName");
        sql.append("            ,scs.name AS sectionName");
        sql.append("            ,q.makerOrgCode");
        sql.append("            ,scs2.name AS subjectName");
        sql.append("            ,q.makerName");
        sql.append("            ,:resTypeQuestion AS resTypeL1");
        sql.append("            ,q.questionCode AS resCode ");
        sql.append("            ,q.shareLevel ");
        sql.append("            ,rqg.gradeCode ");
        sql.append("            ,se.sectionCode ");
        sql.append("            ,q.subjectCode ");
        sql.append("            ,q.makeTime ");
        sql.append("            ,q.modifyTime ");
        sql.append("        FROM");
        sql.append("            question q ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_question_section se ");
        sql.append("                ON q.questionCode = se.questionCode ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_section scs ");
        sql.append("                ON se.sectionCode = scs.code ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_question_grade rqg ");
        sql.append("                ON q.questionCode = rqg.questionCode ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_grade scg ");
        sql.append("                ON rqg.gradeCode = scg.code ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_subject scs2 ");
        sql.append("                ON q.subjectCode = scs2.code ");
        sql.append("        WHERE");
        sql.append("            q.flagDelete = :flagDeleteYes ");
        sql.append("    ) b ");
        sql.append("LEFT JOIN");
        sql.append("    share_org so ");
        sql.append("        ON so.orgCode = b.makerOrgCode ");
        sql.append("WHERE");
        sql.append("    1 = 1");
        sqlParam.put("flagDeleteYes", CoreConstants.FLAG_DELETE_YES);
        sqlParam.put("resTypeDoc", CoreConstants.RES_TYPE_DOC);
        sqlParam.put("resTypeMedia", CoreConstants.RES_TYPE_MEDIA);
        sqlParam.put("resTypeSpecialMedia", CoreConstants.RES_TYPE_SPECIAL_MEDIA);
        sqlParam.put("resTypeExercise", CoreConstants.RES_TYPE_EXERCISE);
        sqlParam.put("resTypeQuestion", CoreConstants.RES_TYPE_QUESTION);
        sqlParam.put("resTypeFlash", CoreConstants.RES_TYPE_FLASH);
        // sqlParam.put("resTypeLesson", CoreConstants.RES_TYPE_COURSE); 
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and b.sectionCode = :sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(gradeCode)) {
            sql.append(" and b.gradeCode = :gradeCode ");
            sqlParam.put("gradeCode", gradeCode);
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append(" and b.subjectCode = :subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (StringUtils.isNotEmpty(resName)) {
            sql.append(" and b.resName like :resName ");
            sqlParam.put("resName", "%" + resName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and so.orgName like :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (resTypeL1 != null) {
            sql.append(" and b.resTypeL1 = :resTypeL1 ");
            sqlParam.put("resTypeL1", resTypeL1);
        }
        sql.append(" order by b.modifyTime desc ");
        // 执行查询
        return super.queryDistinctPage(sql.toString(), new RowMapperUtils(), sqlParam, pageNo, pageSize);
    }

    /**
     * 根据资源id 查询视频信息
     * 
     * @param id 视频资源id
     * @return
     * @author gaow
     * @date:2015年12月19日 下午7:11:52
     */
    public Map<String, Object> getResMediaById(Integer id) {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("        rm.*");
        sql.append("        ,scg.name AS gradeName ");
        sql.append("        ,scs.name AS sectionName ");
        sql.append("        ,scs2.name AS subjectName ");
        sql.append("        ,org.orgName ");
        sql.append("        ,org.orgCode ");
        sql.append("        ,srtl2.name AS resTypeL2Name ");
        sql.append("    FROM");
        sql.append("        res_media rm ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        share_org org ");
        sql.append("            ON rm.makerOrgCode = org.orgCode ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_res_grade rrg ");
        sql.append("            ON rm.id = rrg.resId ");
        sql.append("            AND rm.resTypeL1 = rrg.resTypeL1 ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade scg ");
        sql.append("            ON rrg.gradeCode = scg.code ");
        sql.append("            AND scg.flagDelete = 0 ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_res_section rrs ");
        sql.append("            ON rm.id = rrs.resId ");
        sql.append("            AND rm.resTypeL1 = rrs.resTypeL1 ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scs ");
        sql.append("            ON rrs.sectionCode = scs.code ");
        sql.append("            AND scs.flagDelete = 0 ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_res_subject rrs2 ");
        sql.append("            ON rm.id = rrs2.resId ");
        sql.append("            AND rm.resTypeL1 = rrs2.resTypeL1 ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scs2 ");
        sql.append("            ON rrs2.subjectCode = scs2.code ");
        sql.append("            AND scs2.flagDelete = 0 ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_res_type_l2 srtl2 ");
        sql.append("            ON srtl2.code = rm.resTypeL2 ");
        sql.append("            AND srtl2.codeL1 = rm.resTypeL1 ");
        sql.append("    WHERE 1=1 ");
        /* sql.append("        rm.flagDelete = 0"); */ // 资源回收站也需要查
        sql.append("       and rm.id=:id  ");
        Map<String, Object> sqlParamMap = new HashMap<String, Object>();
        sqlParamMap.put("id", id);
        List<Map<String, Object>> mapList = super.findBySql(sql.toString(), sqlParamMap);
        if (mapList != null && mapList.size() > 0) {
            return mapList.get(0);
        } else {
            return null;
        }
        /* return super.findUniqueBySql(sql.toString(), sqlParamMap); */
    }

    /**
     * 查询未被推荐的音视频资源信息
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<Map<String, Object>> getCanRecommendInfo(Map<String, Object> param) throws Exception {
        // 获取查询参数
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String uploadStartTime = MapUtils.getString(param, "uploadStartTime");
        String uploadEndTime = MapUtils.getString(param, "uploadEndTime");
        String resName = MapUtils.getString(param, "resName");
        Integer pageNumber = MapUtils.getInteger(param, "pageNumber");
        Integer pageSize = MapUtils.getInteger(param, "pageSize");

        List<Map<String, Object>> mapList = resRecommendDao.getResIdMapList(CoreConstants.RES_TYPE_MEDIA);
        String resIds = "";// 已被推荐的音视频资源id
        if (mapList != null && mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                resIds += MapUtils.getString(map, "resId") + ",";
            }
        }
        if (!StringUtils.isEmpty(resIds)) {
            resIds = resIds.substring(0, resIds.length() - 1);
        }
        // 拼接SQL语句
        StringBuilder sqlStr = new StringBuilder();
        sqlStr.append(" select rm.id,rm.resName,scg.name as gradeName,scs.name as sectionName, ");
        sqlStr.append(" scs2.name as subjectName,rm.makerName,rm.makeTime ,rm.resDesc, ");
        sqlStr.append(" rm.browseCount,rm.downloadCount,rm.goodCount,rm.commentCount,rm.downloadPoints ");
        sqlStr.append(" from res_media rm ");
        sqlStr.append(" LEFT JOIN rel_res_grade rrg on rm.id=rrg.resId and rm.resTypeL1=rrg.resTypeL1 ");
        sqlStr.append(" LEFT JOIN share_code_grade scg on rrg.gradeCode=scg.code ");
        sqlStr.append(" LEFT JOIN rel_res_section rrs on rm.id=rrs.resId and rm.resTypeL1=rrs.resTypeL1 ");
        sqlStr.append(" LEFT JOIN share_code_section scs on rrs.sectionCode=scs.code ");
        sqlStr.append(" LEFT JOIN rel_res_subject rrs2 on rm.id=rrs2.resId and rm.resTypeL1=rrs2.resTypeL1 ");
        sqlStr.append(" LEFT JOIN share_code_subject scs2 on rrs2.subjectCode=scs2.code ");
        sqlStr.append(" where rm.flagDelete=0 and rm.resTypeL1=10 ");
        if (!StringUtils.isEmpty(resIds)) {
            sqlStr.append(" and rm.id not in (" + resIds + ") ");
        }
        sqlStr.append(" and rm.shareCheckStatus=20 ");
        sqlStr.append(" and rm.shareLevel=30 ");

        // 查询条件
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(sectionCode)) {
            sqlStr.append(" and rrs.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (!StringUtils.isEmpty(gradeCode)) {
            sqlStr.append(" and rrg.gradeCode=:gradeCode ");
            sqlParam.put("gradeCode", gradeCode);
        }
        if (!StringUtils.isEmpty(subjectCode)) {
            sqlStr.append(" and rrs2.subjectCode=:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (!StringUtils.isEmpty(resName)) {
            sqlStr.append(" and rm.resName like '%" + resName.trim().trim() + "%' ");
        }
        if (!StringUtils.isEmpty(uploadStartTime)) {
            sqlStr.append(" and rm.makeTime  >=:uploadStartTime ");
            sqlParam.put("uploadStartTime", uploadStartTime);
        }
        if (!StringUtils.isEmpty(uploadEndTime)) {
            sqlStr.append(" and rm.makeTime <=:uploadEndTime ");
            sqlParam.put("uploadEndTime", uploadEndTime);
        }
        sqlStr.append(" order by rm.id asc ");
        // 执行查询
        return super.queryDistinctPage(sqlStr.toString(), new RowMapperUtils(), sqlParam, pageNumber, pageSize);
    }

    /**
     * 资源审核
     * 
     * @param resId 资源id
     * @param status 审核状态
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月23日 下午1:53:19
     */
    public int examine(Integer resId, String status) throws Exception {
        String sqlString = "update res_media set shareCheckStatus=:shareCheckStatus where id=:id";
        Map<String, Object> sqlParamMap = new HashMap<String, Object>();
        sqlParamMap.put("shareCheckStatus", status);
        sqlParamMap.put("id", resId);
        return super.update(sqlString, sqlParamMap);
    }

    /**
     * 删除视频 ()<br>
     * 
     * @param ids 视频ids
     * @return
     */
    public int deleteMedia(String ids, String modifier, String modifierIP) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        Timestamp currentTime = DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        sql.append("UPDATE");
        sql.append("        res_media ");
        sql.append("    SET");
        sql.append("        flagDelete = :flagDelete");
        sql.append("        ,modifier = :modifier");
        sql.append("        ,modifierIP = :modifierIP");
        sql.append("        ,modifyTime = :modifyTime ");
        sql.append("    WHERE");
        sql.append("        id ");
        sql.append("    IN (" + ids + ") ");
        sqlParam.put("flagDelete", CoreConstants.FLAG_DELETE_YES);
        sqlParam.put("modifier", modifier);
        sqlParam.put("modifierIP", modifierIP);
        sqlParam.put("modifyTime", currentTime);
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 物理删除视频 ()<br>
     * 
     * @param ids 视频id
     * @return
     */
    public int physicalDeleteMedia(String ids) {
        String sql = "delete from  res_media where id in (" + ids + ")";
        return super.update(sql, new HashMap<String, Object>());
    }

    /**
     * 恢复视频 ()<br>
     * 
     * @param ids 视频id列表
     */
    public int recoveryMediaList(String ids) {
        String sql = "update res_media set flagDelete=0 where id in (" + ids + ")";
        return super.update(sql, new HashMap<String, Object>());
    }

    /**
     * 
     * (分页查询可以设置在首页的视频资源)<br>
     * 
     * @param param 查询参数
     * @param rows 每页大小
     * @param page 当前页
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<Map<String, Object>> selectPageByResSetting(Map<String, Object> param, Integer rows, Integer page) {
        // 查询参数
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String resName = MapUtils.getString(param, "resName");
        String resTypeL2 = MapUtils.getString(param, "resTypeL2");
        String userName = MapUtils.getString(param, "userName");
        String orgName = MapUtils.getString(param, "orgName");
        Integer shareLevel = MapUtils.getInteger(param, "shareLevel");
        // SQL语句
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        rm.resName");
        sql.append("        ,rm.resCode");
        sql.append("        ,rm.resTypeL1");
        sql.append("        ,rm.resTypeL2");
        sql.append("        ,srtl1.name AS resTypeL1Name");
        sql.append("        ,srtl2.name AS resTypeL2Name");
        sql.append("        ,rm.makerOrgName AS orgName");
        sql.append("        ,rm.makerName AS userName");
        sql.append("        ,scsec.name AS sectionName");
        sql.append("        ,scs.name AS subjectName");
        sql.append("        ,scg.name AS gradeName");
        sql.append("        ,rm.makeTime ");
        sql.append("        ,rrsec.sectionCode ");
        sql.append("        ,rrs.subjectCode ");
        sql.append("        ,rm.shareLevel ");
        sql.append("    FROM");
        sql.append("        res_media rm ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_res_section rrsec ");
        sql.append("            ON rrsec.resTypeL1 = rm.resTypeL1 ");
        sql.append("            AND rrsec.resId = rm.id ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scsec ");
        sql.append("            ON scsec.code = rrsec.sectionCode ");
        sql.append("            AND scsec.flagDelete = 0 ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_res_subject rrs ");
        sql.append("            ON rrs.resTypeL1 = rm.resTypeL1 ");
        sql.append("            AND rrs.resId = rm.id ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scs ");
        sql.append("            ON scs.code = rrs.subjectCode ");
        sql.append("            AND scs.flagDelete = 0 ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_res_grade rrg ");
        sql.append("            ON rrg.resTypeL1 = rm.resTypeL1 ");
        sql.append("            AND rrg.resId = rm.id ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade scg ");
        sql.append("            ON scg.code = rrg.gradeCode ");
        sql.append("            AND scg.flagDelete = 0 ");
        sql.append("    LEFT JOIN");
        sql.append("        share_res_type_l1 srtl1 ");
        sql.append("            ON rm.resTypeL1 = srtl1.code ");
        sql.append("    LEFT JOIN");
        sql.append("        share_res_type_l2 srtl2 ");
        sql.append("            ON srtl2.code = rm.resTypeL2 ");
        sql.append("            AND srtl2.codeL1 = rm.resTypeL1 ");
        sql.append("    WHERE");
        sql.append("        rm.flagDelete = 0 ");
        /*
         * sql.append("        AND rm.resCode NOT "); sql.append("    IN ("); sql.append(
         * "        SELECT"); sql.append("            resCode FROM"); sql.append(
         * "                res_settings_home "); sql.append("        ) ");
         */
        if (ObjectUtils.isNotNull(shareLevel)) {
            sql.append("        AND rm.shareLevel = :shareLevel ");
            sqlParam.put("shareLevel", shareLevel);
        } else {
            sql.append("        AND rm.shareLevel IN (20,30) ");
        }
        sql.append("        AND rm.shareCheckStatus = :shareCheckStatus ");
        sqlParam.put("shareCheckStatus", CoreConstants.CHECK_STATUS_CHECKED);
        sql.append("        AND rm.resStatus = :resStatus ");
        sqlParam.put("resStatus", CoreConstants.RESOURCE_STATE_CONVERT_SUCCESS);
        if (StringUtils.isNotEmpty(gradeCode) && !"-1".equals(gradeCode)) {
            sql.append(" and  rrg.gradeCode = :gradeCode ");
            sqlParam.put("gradeCode", gradeCode);
        }
        if (StringUtils.isNotEmpty(subjectCode) && !"-1".equals(subjectCode)) {
            sql.append(" and rrs.subjectCode = :subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (StringUtils.isNotEmpty(sectionCode) && !"-1".equals(sectionCode)) {
            sql.append(" and rrsec.sectionCode = :sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and rm.makerOrgName like :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" and rm.makerName like :userName ");
            sqlParam.put("userName", "%" + userName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(resTypeL2) && !"-1".equals(resTypeL2)) {
            sql.append(" and rm.resTypeL2 = :resTypeL2 ");
            sqlParam.put("resTypeL2", resTypeL2);
        }
        if (StringUtils.isNotEmpty(resName)) {
            sql.append(" and rm.resName like :resName ");
            sqlParam.put("resName", "%" + resName.trim() + "%");
        }
        sql.append(" order by rm.makeTime desc ");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    /**
     * 
     * (批量处理视频资源，彻底删除或者恢复)<br>
     * 
     * @param ids
     * @param operateType
     * @return
     */
    @Override
    public int updateFlagDeleteBatch(String ids, Integer operateType, String userName, String ip) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("modifier", userName);
        sqlParam.put("modifierIP", ip);
        sqlParam.put("modifyTime", DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
        sql.append("UPDATE");
        sql.append("        res_media ");
        sql.append("    SET");
        sql.append("        flagDelete = :flagDelete");
        sql.append("        ,modifier = :modifier");
        sql.append("        ,modifierIP = :modifierIP");
        sql.append("        ,modifyTime = :modifyTime ");
        sql.append("    WHERE");
        sql.append("        id ");
        sql.append("    IN (" + ids + ") ");
        // 删除
        if (1 == operateType.intValue()) {
            sqlParam.put("flagDelete", CoreConstants.FLAG_DELETE_PACED);
        } else if (2 == operateType.intValue()) {
            // 恢复
            sqlParam.put("flagDelete", CoreConstants.FLAG_DELETE_NO);
        }
        return super.update(sql.toString(), sqlParam);
    }

}
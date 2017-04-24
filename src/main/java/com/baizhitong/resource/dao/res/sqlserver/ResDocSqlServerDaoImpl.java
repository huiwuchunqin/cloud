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
import com.baizhitong.resource.dao.res.ResDocDao;
import com.baizhitong.resource.dao.res.ResRecommendDao;
import com.baizhitong.resource.model.res.ResDoc;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.RowMapperUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 资源_文档数据SqlServer接口实现
 * 
 * @author creator cuidc 2015/12/03
 * @author updater
 */
@Service("resDocSqlServerDao")
public class ResDocSqlServerDaoImpl extends BaseSqlServerDao<ResDoc> implements ResDocDao {
    /** 推荐资源数据接口 */
    private @Autowired ResRecommendDao resRecommendDao;

    /**
     * 获取文档分页集合
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param versionCode 版本编码
     * @param tbcCode 教材章节编码
     * @param kpCode 知识点编码
     * @param docName 文档名称
     * @param resTypeL2 资源分类(二级)
     * @param sort 排序方式(null:默认,10:最热,20:最新)
     * @param pageNo 当前页数
     * @param pageSize 每页条数
     * @return 文档分页集合
     * @throws Exception 异常
     */
    @Override
    @SuppressWarnings("unchecked")
    public Page<Map<String, Object>> selectDocPage(String sectionCode, String gradeCode, String subjectCode,
                    String versionCode, String tbcCode, String kpCode, String docName, String resTypeL2, String sort,
                    Integer pageNo, Integer pageSize) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT rd.id, rd.resName, rd.suffix, rd.coverPath, rd.browseCount, rd.downloadCount, rd.referCount, ");
        sql.append(" rd.favoriteCount, rd.goodCount, rd.badCount, rd.commentCount, rd.resDesc, rd.makeTime as uploadTime, ");
        sql.append(" scse.name AS sectionName, scs.name AS subjectName, scg.name AS gradeName, sctv.name AS textbookVerName ");
        sql.append(" FROM res_doc rd ");
        sql.append(" LEFT JOIN rel_res_section rrse ON rrse.resTypeL1 = rd.resTypeL1 AND rrse.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_section scse ON scse.code = rrse.sectionCode ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rd.resTypeL1 AND rrs.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rd.resTypeL1 AND rrg.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode ");
        sql.append(" LEFT JOIN rel_res_tbv rrt ON rrt.resTypeL1 = rd.resTypeL1 AND rrt.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_textbook_ver sctv ON sctv.code = rrt.tbvCode ");
        // sql.append(" LEFT JOIN rel_res_kp rrk ON rrk.resTypeL1 = rd.resTypeL1 AND rrk.resId =
        // rd.id ");
        // sql.append(" LEFT JOIN share_knowledge_point_textbook skpt ON skpt.textbookVerCode =
        // sctv.code AND skpt.code = rrk.kpCode ");
        // sql.append(" LEFT JOIN rel_res_tbc rrt2 ON rrt2.resTypeL1 = rd.resTypeL1 AND rrt2.resId =
        // rd.id ");
        // sql.append(" LEFT JOIN share_textbook_chapter stc ON stc.textBookCode = sctv.code AND
        // stc.code = rrt2.tbcCode ");
        sql.append(" WHERE rd.shareLevel = 60 AND　rd.flagDelete = 0 AND rd.flagAllowBrowse = 1 AND rd.shareCheckStatus = 20 AND rd.resStatus = 20 ");
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
            sql.append(" AND rd.resTypeL2 = :resTypeL2 ");
        }
        // 判断文档名称是否为空
        if (!StringUtils.isEmpty(docName)) {
            sql.append(" AND rd.resName LIKE '%" + docName + "%' ");
        }
        // 判断教材章节编码是否为空
        if (!StringUtils.isEmpty(tbcCode)) {
            // sql.append(" AND rrt2.tbcCode LIKE '" + tbcCode + "%' ");
            sql.append(" AND rd.id IN(SELECT resId FROM rel_res_tbc WHERE resTypeL1 = 20 AND tbcCode LIKE '" + tbcCode
                            + "%') ");
        }
        // 判断知识点编码是否为空
        if (!StringUtils.isEmpty(kpCode)) {
            // sql.append(" AND rrk.kpCode LIKE '" + kpCode + "%' ");
            sql.append(" AND rd.id IN(SELECT resId FROM rel_res_kp WHERE resTypeL1 = 20 AND kpCode LIKE '" + kpCode
                            + "%') ");
        }

        // 排序方式(null:默认,10:最热,20:最新)
        if (!StringUtils.isEmpty(sort) && "10".equals(sort)) {
            sql.append(" ORDER BY rd.browseCount DESC, rd.id ");
        } else if (!StringUtils.isEmpty(sort) && "20".equals(sort)) {
            sql.append(" ORDER BY rd.makeTime DESC, rd.id ");
        } else {
            sql.append(" ORDER BY rd.id ");
        }

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sectionCode", sectionCode);
        map.put("gradeCode", gradeCode);
        map.put("subjectCode", subjectCode);
        map.put("versionCode", versionCode);
        map.put("tbcCode", tbcCode);
        map.put("kpCode", kpCode);
        map.put("resName", docName);
        map.put("resTypeL2", resTypeL2);
        map.put("sort", sort);

        // 执行SQL
        return super.queryDistinctPage(sql.toString(), map, pageNo, pageSize);
    }

    /**
     * 获取文档信息
     * 
     * @param docId 文档ID
     * @return 信息
     * @throws Exception 异常
     */
    @Override
    public Map<String, Object> selectDocInfo(Integer docId) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT rd.id, rd.resName, rd.suffix, rd.pages, rd.coverPath, rd.convertedPathImages, rd.makerName, ");
        sql.append(" rd.createTime, rd.browseCount, rd.downloadCount, rd.referCount, rd.favoriteCount, rd.goodCount, rd.badCount, rd.commentCount, ");
        sql.append(" rd.resDesc, rd.shareLevel, rd.downloadPoints ");
        sql.append(" FROM res_doc rd ");
        sql.append(" WHERE rd.id = :docId AND rd.flagDelete = 0 AND rd.resStatus = 20 ");

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("docId", docId);

        // 执行SQL
        return super.findUniqueBySql(sql.toString(), map);
    }

    /**
     * 获取相关文档信息
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param textbookVerCode 教材版本编码
     * @param knowledgePointCode 知识点编码
     * @param textbookChapterCode 教材章节编码
     * @return 相关文档信息
     * @throws Exception 异常
     */
    @Override
    public List<Map<String, Object>> selectRelevantDocInfo(String sectionCode, String gradeCode, String subjectCode,
                    String textbookVerCode, String knowledgePointCode, String textbookChapterCode) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT DISTINCT TOP 5 * FROM ( ");
        sql.append(" SELECT DISTINCT TOP 5 rd.id, rd.resName, rd.browseCount, rd.makeTime as uploadTime ");
        sql.append(" FROM res_doc rd ");
        sql.append(" LEFT JOIN rel_res_section rrse ON rrse.resTypeL1 = rd.resTypeL1 AND rrse.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_section scse ON scse.code = rrse.sectionCode ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rd.resTypeL1 AND rrs.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rd.resTypeL1 AND rrg.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode ");
        sql.append(" LEFT JOIN rel_res_tbv rrt ON rrt.resTypeL1 = rd.resTypeL1 AND rrt.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_textbook_ver sctv ON sctv.code = rrt.tbvCode ");
        sql.append(" LEFT JOIN rel_res_tbc rrt2 ON rrt2.resTypeL1 = rd.resTypeL1 AND rrt2.resId = rd.id ");
        sql.append(" LEFT JOIN share_textbook_chapter stc ON stc.textBookCode = sctv.code AND stc.code = rrt2.tbcCode ");
        sql.append(" WHERE rd.shareLevel = 60 AND　rd.flagDelete = 0 AND rd.flagAllowBrowse = 1 AND rd.shareCheckStatus = 20 AND rd.resStatus = 20 ");
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
        sql.append(" ORDER BY rd.browseCount DESC, rd.makeTime  DESC ");
        sql.append(" UNION ALL ");
        sql.append(" SELECT DISTINCT TOP 5 rd.id, rd.resName, rd.browseCount, rd.makeTime as uploadTime ");
        sql.append(" FROM res_doc rd ");
        sql.append(" LEFT JOIN rel_res_section rrse ON rrse.resTypeL1 = rd.resTypeL1 AND rrse.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_section scse ON scse.code = rrse.sectionCode ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rd.resTypeL1 AND rrs.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rd.resTypeL1 AND rrg.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode ");
        sql.append(" LEFT JOIN rel_res_tbv rrt ON rrt.resTypeL1 = rd.resTypeL1 AND rrt.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_textbook_ver sctv ON sctv.code = rrt.tbvCode ");
        sql.append(" LEFT JOIN rel_res_kp rrk ON rrk.resTypeL1 = rd.resTypeL1 AND rrk.resId = rd.id ");
        sql.append(" LEFT JOIN share_knowledge_point_textbook skpt ON skpt.textbookVerCode = sctv.code AND skpt.code = rrk.kpCode ");
        sql.append(" WHERE rd.shareLevel = 60 AND　rd.flagDelete = 0 AND rd.flagAllowBrowse = 1 AND rd.shareCheckStatus = 20 AND rd.resStatus = 20 ");
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
        sql.append(" ORDER BY rd.browseCount DESC, rd.makeTime DESC ");
        sql.append(" ) rd_relevant ");
        sql.append(" ORDER BY rd_relevant.browseCount DESC, rd_relevant.uploadTime DESC ");

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
     * 根据resID获取资源
     * 
     * @param resID 资源id
     * @return 返回资源对象
     */
    @Override
    public ResDoc selectResDoc(Integer resID) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", resID);
        return super.get(map);

    }

    /**
     * 根据resCode获取资源
     * 
     * @param resCode 资源编码
     * @return 返回资源对象
     */
    public ResDoc selectResDoc(String resCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("resCode", resCode);
        qr.andEqual("flagDelete", CoreConstants.FLAG_DELETE_NO);
        return super.findUnique(qr); 
    }

    /**
     * 更新资源信息
     * 
     * @param resDoc 资源对象
     * @return 是否成功
     */
    @Override
    public boolean updateDocInfo(ResDoc resDoc) {
        int count = 0;
        try {
            count = super.update(resDoc);
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return count > 0;
    }

    /**
     * 保存资源信息
     * 
     * @param resDoc 资源对象
     * @return 是否成功
     */
    @Override
    public boolean saveDocInfo(ResDoc resDoc) {
        boolean success = false;
        try {
            success = super.saveOne(resDoc);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return success;
    }

    /**
     * 获取首页文档资源集合
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param docName 文档名称
     * @param isHotSort 是否最热降序
     * @param isNewSort 是否最新降序
     * @return 首页文档资源集合信息
     * @exception 异常
     */
    @Override
    public List<Map<String, Object>> selectDocResList(String sectionCode, String subjectCode, String docName,
                    Boolean isHotSort, Boolean isNewSort) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT TOP 5 rd.id, rd.resName, rd.browseCount, rd.downloadCount, rd.goodCount, rd.userName, rd.makeTime as uploadTime, ");
        sql.append(" scse.name AS sectionName, scs.name AS subjectName, scg.name AS gradeName ");
        sql.append(" FROM res_doc rd ");
        sql.append(" LEFT JOIN rel_res_section rrse ON rrse.resTypeL1 = rd.resTypeL1 AND rrse.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_section scse ON scse.code = rrse.sectionCode ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rd.resTypeL1 AND rrs.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rd.resTypeL1 AND rrg.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode ");
        sql.append(" WHERE rd.shareLevel = 60 AND　rd.flagDelete = 0 AND rd.flagAllowBrowse = 1 AND rd.shareCheckStatus = 20 AND rd.resStatus = 20 ");

        // 判断学段编码是否为空
        if (!StringUtils.isEmpty(sectionCode)) {
            sql.append(" AND rrse.sectionCode = :sectionCode ");
        }
        // 判断学科编码是否为空
        if (!StringUtils.isEmpty(subjectCode)) {
            sql.append(" AND rrs.subjectCode = :subjectCode ");
        }
        // 判断文档名称是否为空
        if (!StringUtils.isEmpty(docName)) {
            sql.append(" AND rd.resName LIKE '%" + docName + "%' ");
        }

        // 排序方式:最热
        if (isHotSort != null && isHotSort) {
            sql.append(" ORDER BY rd.browseCount DESC, rd.id ");
        }
        // 排序方式:最新
        if (isNewSort != null && isNewSort) {
            sql.append(" ORDER BY rd.makeTime DESC, rd.id ");
        }

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sectionCode", sectionCode);
        map.put("subjectCode", subjectCode);
        map.put("resName", docName);

        // 执行SQL
        return super.findBySql(sql.toString(), map);
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
        sql.append(" SELECT rd.id, rd.resName, rd.suffix, rd.convertedPathImages, rd.browseCount, rd.downloadCount, rd.referCount, ");
        sql.append(" rd.favoriteCount, rd.goodCount, rd.badCount, rd.commentCount, rd.resDesc, rd.makeTime as uploadTime, rd.resStatus, rd.shareCheckStatus, rd.objectId, rd.fileKey, rd.resTypeL1, ");
        sql.append(" scse.name AS sectionName, scs.name AS subjectName, scg.name AS gradeName, sctv.name AS textbookVerName ");
        sql.append(" FROM res_doc rd ");
        sql.append(" LEFT JOIN rel_res_section rrse ON rrse.resTypeL1 = rd.resTypeL1 AND rrse.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_section scse ON scse.code = rrse.sectionCode ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rd.resTypeL1 AND rrs.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rd.resTypeL1 AND rrg.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode ");
        sql.append(" LEFT JOIN rel_res_tbv rrt ON rrt.resTypeL1 = rd.resTypeL1 AND rrt.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_textbook_ver sctv ON sctv.code = rrt.tbvCode ");
        sql.append(" WHERE rd.flagDelete = 0 ");

        // 判断用户编码是否为空
        if (!StringUtils.isEmpty(userCode)) {
            sql.append(" AND rd.makerCode = :userCode ");
        }
        // 判断资源状态是否为空
        if (resStatus != null) {
            sql.append(" AND rd.resStatus = :resStatus ");
        }
        // 判断审核状态是否为空
        if (shareCheckStatus != null) {
            sql.append(" AND rd.shareCheckStatus = :shareCheckStatus ");
        }

        // 排序方式(null:默认,10:最热,20:最新)
        if (!StringUtils.isEmpty(sort) && "10".equals(sort)) {
            sql.append(" ORDER BY rd.browseCount DESC, rd.id ");
        } else if (!StringUtils.isEmpty(sort) && "20".equals(sort)) {
            sql.append(" ORDER BY rd.makeTime DESC, rd.id ");
        } else {
            sql.append(" ORDER BY rd.id ");
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

    /*************************************************** 后台查询方法 ****************************************************/

    @SuppressWarnings("unchecked")
    public Page<Map<String, Object>> getUnUsualDocInfo(Map<String, Object> param, Integer pageNo, Integer pageSize) {
        // 查询参数
        // 查询参数
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String orgName = MapUtils.getString(param, "orgName");
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append("   select rd.*,so.orgName,");
        sql.append(" scs.code AS subjectCode, scs.name AS subjectName, scg.code AS gradeCode, scg.name AS gradeName,scsec.name AS sectionName,");
        sql.append(" (select count (*)   from rel_res_tbc  rrt where rrt.resId=rd.id  and rrt.resTypeL1=rd.resTypeL1 ) as tbcCount ,");
        sql.append(" (select count (*)  from rel_res_kp  rrk where rrk.resId=rd.id and rrk.resTypeL1=rd.resTypeL1 ) as kpCount  ");
        sql.append(" FROM res_doc rd ");
        sql.append(" LEFT JOIN rel_res_section rrsec ON rrsec.resTypeL1 = rd.resTypeL1 AND rrsec.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_section scsec ON scsec.code = rrsec.sectionCode and scsec.flagDelete=0 ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rd.resTypeL1 AND rrs.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode and scs.flagDelete=0 ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rd.resTypeL1 AND rrg.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode and scg.flagDelete=0 ");
        sql.append(" LEFT JOIN share_org so ON so.orgCode = rd.makerOrgCode ");
        sql.append(" WHERE 1=1 and rd.flagDelete=0 ");
        sql.append(" and (  scsec.name is null or scs.name is null or scg.name is null or rd.makerName is null or rd.makerOrgCode = '' or rd.makerOrgCode IS NULL)");
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
        sql.append("  order by rd.makeTime desc ");
        return super.queryDistinctPage(sql.toString(), sqlParam, pageNo, pageSize);
    }

    /**
     * 后台分页查询文档资源信息
     * 
     * @param param 查询参数
     * @param pageNo 页码
     * @param pageSize 每页记录数
     * @return
     * @author gaow
     * @date:2015年12月16日 上午11:39:53
     */
    @SuppressWarnings("unchecked")
    public Page<Map<String, Object>> getAllDocInfo(Map<String, Object> param, Integer pageNo, Integer pageSize) {
        // 查询参数
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
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
        String userName = MapUtils.getString(param, "userName");
        String orgName = MapUtils.getString(param, "orgName");
        String orgCode = MapUtils.getString(param, "orgCode");
        String resTypeL2 = MapUtils.getString(param, "resTypeL2");
        Integer shareCheckLevel = MapUtils.getInteger(param, "shareCheckLevel");
        String orderType = MapUtils.getString(param, "orderType");
        Integer resStatusSuccess = MapUtils.getInteger(param, "resStatusSuccess");
        String sectionCodes = MapUtils.getString(param, "sectionCodes");
        String gradeCodes = MapUtils.getString(param, "gradeCodes");
        String subjectCodes = MapUtils.getString(param, "subjectCodes");
        // SQL语句
        StringBuffer sql = new StringBuffer();

        sql.append("select b.* from ( ");
        sql.append("   select rd.*,so.orgName,");
        sql.append(" scs.code AS subjectCode, scs.name AS subjectName, scg.code AS gradeCode, scg.name AS gradeName,scsec.name AS sectionName,srtl.name AS resTypeL2Name,");
        sql.append(" (select count (*)   from rel_res_tbc  rrt where rrt.resId=rd.id  and rrt.resTypeL1=rd.resTypeL1 ) as tbcCount ,");
        sql.append(" (select count (*)  from rel_res_kp  rrk where rrk.resId=rd.id and rrk.resTypeL1=rd.resTypeL1 ) as kpCount  ");
        sql.append(" FROM res_doc rd ");
        sql.append(" LEFT JOIN rel_res_section rrsec ON rrsec.resTypeL1 = rd.resTypeL1 AND rrsec.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_section scsec ON scsec.code = rrsec.sectionCode  and scsec.flagDelete=0 ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rd.resTypeL1 AND rrs.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode     and scs.flagDelete=0 ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rd.resTypeL1 AND rrg.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode and scg.flagDelete=0");
        sql.append(" LEFT JOIN share_org so ON so.orgCode = rd.makerOrgCode ");
        sql.append(" LEFT JOIN share_res_type_l2  srtl  ON srtl.code = rd.resTypeL2 and srtl.codeL1=rd.resTypeL1 ");
        sql.append(" WHERE rd.flagDelete=0 ");
        // 查询条件参数

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
        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" and rd.makerName like :userName ");
            sqlParam.put("userName", "%" + userName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(resTypeL2) && !"-1".equals(resTypeL2)) {
            sql.append(" and rd.resTypeL2 = :resTypeL2 ");
            sqlParam.put("resTypeL2", resTypeL2);
        }
        if (shareCheckStatus != null && shareCheckStatus.intValue() != -1) {
            if (shareCheckStatus == 60) {
                sql.append(" and rd.shareCheckStatus is null ");
            } else {
                sql.append(" and rd.shareCheckStatus=:shareCheckStatus ");
            }
            sqlParam.put("shareCheckStatus", shareCheckStatus);
        }

        if (shareCheckLevel != null) {
            sql.append(" and rd.shareCheckLevel =:shareCheckLevel ");
            sqlParam.put("shareCheckLevel", shareCheckLevel);
        }
        if (!StringUtils.isEmpty(resName)) {
            sql.append(" and rd.resName like '%" + resName.trim() + "%'");
        }
        if (!StringUtils.isEmpty(orgCode)) {
            sql.append(" and rd.makerOrgCode =:orgCode");
            sqlParam.put("orgCode", orgCode);
        }
        if (!StringUtils.isEmpty(uploadTimeStart)) {
            sql.append(" and rd.makeTime >=:uploadTimeStart ");
            sqlParam.put("uploadTimeStart", uploadTimeStart);
        }
        if (!StringUtils.isEmpty(uploadTimeEnd)) {
            sql.append(" and rd.makeTime<=:uploadTimeEnd ");
            sqlParam.put("uploadTimeEnd", uploadTimeEnd + " 23:59:59");
        }
        if (!StringUtils.isEmpty(shareTimeStart)) {
            sql.append(" and rd.shareTime >=:shareTimeStart ");
            sqlParam.put("shareTimeStart", shareTimeStart);
        }
        if (!StringUtils.isEmpty(shareTimeEnd)) {
            sql.append(" and rd.shareTime<=:shareTimeEnd ");
            sqlParam.put("shareTimeEnd", shareTimeEnd + " 23:59:59");
        }
        if (!StringUtils.isEmpty(checkTimeStart)) {
            sql.append(" and rd.shareCheckTime >=:checkTimeStart ");
            sqlParam.put("checkTimeStart", checkTimeStart);
        }
        if (!StringUtils.isEmpty(checkTimeEnd)) {
            sql.append(" and rd.shareCheckTime<=:checkTimeEnd ");
            sqlParam.put("checkTimeEnd", checkTimeEnd + " 23:59:59");
        }
        if (resStatus != null && resStatus != -1) {
            sql.append(" and rd.resStatus =:resStatus ");
            sqlParam.put("resStatus", resStatus);
        }
        if (resStatusSuccess != null) {
            sql.append(" and rd.resStatus = :resStatusSuccess ");
            sqlParam.put("resStatusSuccess", resStatusSuccess);
        }
        if (shareLevel != null && shareLevel != -1) {
            sql.append(" and rd.shareLevel =:shareLevel ");
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
     * 根据资源id 查询文档信息
     * 
     * @param id 文档资源id
     * @return
     * @author gaow
     * @date:2015年12月19日 下午7:11:52
     */
    public Map<String, Object> getResDocById(Integer id) {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append(" rd.* ");
        sql.append("        ,scg.name AS gradeName");
        sql.append("        ,scs.name AS sectionName");
        sql.append("        ,scs2.name AS subjectName");
        sql.append("        ,org.orgName");
        sql.append("        ,org.orgCode ");
        sql.append("        ,srtl2.name AS resTypeL2Name ");
        sql.append("    FROM");
        sql.append("        res_doc rd ");
        sql.append("    LEFT JOIN");
        sql.append("        share_org org ");
        sql.append("            ON rd.makerOrgCode = org.orgCode ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_res_grade rrg ");
        sql.append("            ON rd.id = rrg.resId ");
        sql.append("            AND rd.resTypeL1 = rrg.resTypeL1 ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade scg ");
        sql.append("            ON rrg.gradeCode = scg.code ");
        sql.append("            AND scg.flagDelete = 0 ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_res_section rrs ");
        sql.append("            ON rd.id = rrs.resId ");
        sql.append("            AND rd.resTypeL1 = rrs.resTypeL1 ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scs ");
        sql.append("            ON rrs.sectionCode = scs.code ");
        sql.append("            AND scs.flagDelete = 0 ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_res_subject rrs2 ");
        sql.append("            ON rd.id = rrs2.resId ");
        sql.append("            AND rd.resTypeL1 = rrs2.resTypeL1 ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scs2 ");
        sql.append("            ON rrs2.subjectCode = scs2.code ");
        sql.append("            AND scs2.flagDelete = 0 ");
        sql.append("    LEFT JOIN");
        sql.append("        share_res_type_l2 srtl2 ");
        sql.append("            ON srtl2.code = rd.resTypeL2 ");
        sql.append("            AND srtl2.codeL1 = rd.resTypeL1 ");
        sql.append("    WHERE  1=1 ");
        /* sql.append("        rd.flagDelete = 0"); */ // 资源回收站也要查询
        sql.append("       and  rd.id = :id");
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
     * 根据crcCode获取文档
     * 
     * @param crcCode 文件唯一码
     * @param creator 上传人
     * @return
     */
    @Override
    public ResDoc selectResDocByCrcCode(String crcCode, String creator) {
        QueryRule queryRule = QueryRule.getInstance();
        queryRule.andEqual("resCode", crcCode);
        queryRule.andEqual("flagDelete", "0");
        if (StringUtils.isEmpty(creator)) {
            List<ResDoc> resDocs = super.find(queryRule);

            if (resDocs != null && resDocs.size() > 0) {
                return resDocs.get(0);
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
    public int updateSameCodeDoc(ResDoc doc) {
        String sqlString = "update res_doc set pages=?,convertedPathImages=?,convertCompletedTime=? ,resStatus=? where resCode=? and resStatus!=20";
        return super.update(sqlString, doc.getPages(), doc.getConvertedPathHTML(), doc.getConvertCompletedTime(),
                        doc.getResStatus(), doc.getResCode());

    }

    /**
     * 保存并返回id
     * 
     * @param doc
     * @return
     * @author gaow
     * @date:2015年12月18日 下午3:51:39
     */
    public Integer saveDocAndReturnId(ResDoc doc) throws Exception {
        Map<String, Object> map = super.saveAndReturnId(doc);
        if (map != null)
            return MapUtils.getInteger(map, "id");
        return null;
    }

    /**
     * 查询所有未被推荐的资源 ()<br>
     * 
     * @param param
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<Map<String, Object>> getAllCanRecommendInfo(Map<String, Object> param) throws Exception {
        // 获取查询参数
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String uploadStartTime = MapUtils.getString(param, "uploadStartTime");
        String uploadEndTime = MapUtils.getString(param, "uploadEndTime");
        String resName = MapUtils.getString(param, "resName");
        Integer resTypeL1 = MapUtils.getInteger(param, "resTypeL1");
        Integer pageNumber = MapUtils.getInteger(param, "pageNumber");
        Integer pageSize = MapUtils.getInteger(param, "pageSize");

        List<Map<String, Object>> docmapList = resRecommendDao.getResIdMapList(CoreConstants.RES_TYPE_DOC);
        String docResIds = "";// 已被推荐的文档资源id
        if (docmapList != null && docmapList.size() > 0) {
            for (Map<String, Object> map : docmapList) {
                docResIds += MapUtils.getString(map, "resId") + ",";
            }
        }
        if (!StringUtils.isEmpty(docResIds)) {
            docResIds = docResIds.substring(0, docResIds.length() - 1);
        }

        List<Map<String, Object>> meidamapList = resRecommendDao.getResIdMapList(CoreConstants.RES_TYPE_MEDIA);
        String mediaResIds = "";// 已被推荐的音视频资源id
        if (meidamapList != null && meidamapList.size() > 0) {
            for (Map<String, Object> map : meidamapList) {
                mediaResIds += MapUtils.getString(map, "resId") + ",";
            }
        }
        if (!StringUtils.isEmpty(mediaResIds)) {
            mediaResIds = mediaResIds.substring(0, mediaResIds.length() - 1);
        }
        // 拼接SQL语句
        StringBuilder sqlStr = new StringBuilder();
        sqlStr.append(" select b.* from ");
        sqlStr.append(" ( ");
        sqlStr.append(" select rd.id,rd.resName,scg.name as gradeName,scs.name as sectionName, ");
        sqlStr.append(" scs2.name as subjectName,rd.makerName,rd.makeTime ,rd.resDesc, ");
        sqlStr.append(" rd.browseCount,rd.downloadCount,rd.goodCount,rd.commentCount,rd.downloadPoints,rd.resTypeL1,rrs.sectionCode,rrg.gradeCode,rrs2.subjectCode ");
        sqlStr.append(" from res_doc rd ");
        sqlStr.append(" LEFT JOIN rel_res_grade rrg on rd.id=rrg.resId and rd.resTypeL1=rrg.resTypeL1 ");
        sqlStr.append(" LEFT JOIN share_code_grade scg on rrg.gradeCode=scg.code ");
        sqlStr.append(" LEFT JOIN rel_res_section rrs on rd.id=rrs.resId and rd.resTypeL1=rrs.resTypeL1 ");
        sqlStr.append(" LEFT JOIN share_code_section scs on rrs.sectionCode=scs.code ");
        sqlStr.append(" LEFT JOIN rel_res_subject rrs2 on rd.id=rrs2.resId and rd.resTypeL1=rrs2.resTypeL1 ");
        sqlStr.append(" LEFT JOIN share_code_subject scs2 on rrs2.subjectCode=scs2.code ");
        sqlStr.append(" where rd.flagDelete=0 and rd.resTypeL1=20 ");
        if (!StringUtils.isEmpty(docResIds)) {
            sqlStr.append(" and rd.id not in (" + docResIds + ") ");
        }
        sqlStr.append(" and rd.shareCheckStatus=20 ");
        sqlStr.append(" and rd.shareLevel=30 ");
        sqlStr.append(" union ALL ");
        sqlStr.append(" select rm.id,rm.resName,scg.name as gradeName,scs.name as sectionName, ");
        sqlStr.append(" scs2.name as subjectName,rm.makerName,rm.makeTime ,rm.resDesc, ");
        sqlStr.append(" rm.browseCount,rm.downloadCount,rm.goodCount,rm.commentCount,rm.downloadPoints,rm.resTypeL1,rrs.sectionCode,rrg.gradeCode,rrs2.subjectCode ");
        sqlStr.append(" from res_media rm ");
        sqlStr.append(" LEFT JOIN rel_res_grade rrg on rm.id=rrg.resId and rm.resTypeL1=rrg.resTypeL1 ");
        sqlStr.append(" LEFT JOIN share_code_grade scg on rrg.gradeCode=scg.code ");
        sqlStr.append(" LEFT JOIN rel_res_section rrs on rm.id=rrs.resId and rm.resTypeL1=rrs.resTypeL1 ");
        sqlStr.append(" LEFT JOIN share_code_section scs on rrs.sectionCode=scs.code ");
        sqlStr.append(" LEFT JOIN rel_res_subject rrs2 on rm.id=rrs2.resId and rm.resTypeL1=rrs2.resTypeL1 ");
        sqlStr.append(" LEFT JOIN share_code_subject scs2 on rrs2.subjectCode=scs2.code ");
        sqlStr.append(" where rm.flagDelete=0 and rm.resTypeL1=10 ");
        if (!StringUtils.isEmpty(mediaResIds)) {
            sqlStr.append(" and rm.id not in (" + mediaResIds + ") ");
        }
        sqlStr.append(" and rm.shareCheckStatus=20 ");
        sqlStr.append(" and rm.shareLevel=30 ");
        sqlStr.append(" ) b");
        sqlStr.append(" where 1=1");
        // 查询条件
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(sectionCode)) {
            sqlStr.append(" and b.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (!StringUtils.isEmpty(gradeCode)) {
            sqlStr.append(" and b.gradeCode=:gradeCode ");
            sqlParam.put("gradeCode", gradeCode);
        }
        if (!StringUtils.isEmpty(subjectCode)) {
            sqlStr.append(" and b.subjectCode=:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (!StringUtils.isEmpty(resName)) {
            sqlStr.append(" and b.resName like '%" + resName.trim() + "%' ");
        }
        if (!StringUtils.isEmpty(uploadStartTime)) {
            sqlStr.append(" and b.makeTime >=:uploadStartTime ");
            sqlParam.put("uploadStartTime", uploadStartTime);
        }
        if (!StringUtils.isEmpty(uploadEndTime)) {
            sqlStr.append(" and b.makeTime<=:uploadEndTime ");
            sqlParam.put("uploadEndTime", uploadEndTime);
        }
        if (resTypeL1 != null) {
            sqlStr.append(" and b.resTypeL1=:resTypeL1 ");
            sqlParam.put("resTypeL1", resTypeL1);
        }
        sqlStr.append(" order by b.id asc ");
        // 执行查询
        return super.queryDistinctPage(sqlStr.toString(), new RowMapperUtils(), sqlParam, pageNumber, pageSize);
    }

    /**
     * 查询未被推荐的文档资源信息
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

        List<Map<String, Object>> mapList = resRecommendDao.getResIdMapList(CoreConstants.RES_TYPE_DOC);
        String resIds = "";// 已被推荐的文档资源id
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
        sqlStr.append(" select rd.id,rd.resName,scg.name as gradeName,scs.name as sectionName, ");
        sqlStr.append(" scs2.name as subjectName,rd.makerName,rd.makeTime ,rd.resDesc, ");
        sqlStr.append(" rd.browseCount,rd.downloadCount,rd.goodCount,rd.commentCount,rd.downloadPoints ");
        sqlStr.append(" from res_doc rd ");
        sqlStr.append(" LEFT JOIN rel_res_grade rrg on rd.id=rrg.resId and rd.resTypeL1=rrg.resTypeL1 ");
        sqlStr.append(" LEFT JOIN share_code_grade scg on rrg.gradeCode=scg.code ");
        sqlStr.append(" LEFT JOIN rel_res_section rrs on rd.id=rrs.resId and rd.resTypeL1=rrs.resTypeL1 ");
        sqlStr.append(" LEFT JOIN share_code_section scs on rrs.sectionCode=scs.code ");
        sqlStr.append(" LEFT JOIN rel_res_subject rrs2 on rd.id=rrs2.resId and rd.resTypeL1=rrs2.resTypeL1 ");
        sqlStr.append(" LEFT JOIN share_code_subject scs2 on rrs2.subjectCode=scs2.code ");
        sqlStr.append(" where rd.flagDelete=0 and rd.resTypeL1=20 ");
        if (!StringUtils.isEmpty(resIds)) {
            sqlStr.append(" and rd.id not in (" + resIds + ") ");
        }
        sqlStr.append(" and rd.shareCheckStatus=20 ");
        sqlStr.append(" and rd.shareLevel=30 ");

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
            sqlStr.append(" and rd.resName like '%" + resName.trim() + "%' ");
        }
        if (!StringUtils.isEmpty(uploadStartTime)) {
            sqlStr.append(" and rd.makeTime >=:uploadStartTime ");
            sqlParam.put("uploadStartTime", uploadStartTime);
        }
        if (!StringUtils.isEmpty(uploadEndTime)) {
            sqlStr.append(" and rd.makeTime<=:uploadEndTime ");
            sqlParam.put("uploadEndTime", uploadEndTime);
        }
        sqlStr.append(" order by rd.id asc ");
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
        String sqlString = "update res_doc set shareCheckStatus=:shareCheckStatus where id=:id";
        Map<String, Object> sqlParamMap = new HashMap<String, Object>();
        sqlParamMap.put("shareCheckStatus", status);
        sqlParamMap.put("id", resId);
        return super.update(sqlString, sqlParamMap);
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
     * @param resTypeL1 资源类别
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
                    String orgCode, Integer page, Integer rows) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("        b.*");
        sql.append("        ,scg.name AS gradeName");
        sql.append("        ,scs.name AS sectionName");
        sql.append("        ,scs2.name AS subjectName ");
        sql.append("        ,org.orgName  ");
        sql.append("        ,srtl2.name AS resTypeL2Name  ");
        sql.append("    FROM");
        sql.append("        ( ( SELECT");
        sql.append("            rd.id");
        sql.append("            ,rd.resName");
        sql.append("            ,rd.resTypeL1");
        sql.append("            ,rd.resTypeL2");
        sql.append("            ,rd.makerName");
        sql.append("            ,rd.makeTime");
        sql.append("            ,rd.flagAllowDownload");
        sql.append("            ,rd.resDesc");
        sql.append("            ,rd.resStatus ");
        sql.append("            ,rd.shareLevel");
        sql.append("            ,rd.shareCheckStatus");
        sql.append("            ,rd.makerOrgCode ");
        sql.append("        FROM");
        sql.append("            res_doc rd ");
        sql.append("        WHERE");
        sql.append("            rd.flagDelete = 0 ");
        sql.append("            AND rd.shareCheckLevel =:shareCheckLevel) ");
        sql.append("    UNION ALL");
        sql.append("    (");
        sql.append("        SELECT");
        sql.append("            rm.id");
        sql.append("            ,rm.resName");
        sql.append("            ,rm.resTypeL1");
        sql.append("            ,rm.resTypeL2");
        sql.append("            ,rm.makerName");
        sql.append("            ,rm.makeTime");
        sql.append("            ,rm.flagAllowDownload");
        sql.append("            ,rm.resDesc");
        sql.append("            ,rm.resStatus ");
        sql.append("            ,rm.shareLevel");
        sql.append("            ,rm.shareCheckStatus");
        sql.append("            ,rm.makerOrgCode ");
        sql.append("        FROM");
        sql.append("            res_media rm ");
        sql.append("        WHERE");
        sql.append("            rm.flagDelete = 0 ");
        sql.append("            AND rm.shareCheckLevel = :shareCheckLevel ");
        sql.append("    ) ");
        sql.append(") b ");
        sql.append("");
        sql.append("  LEFT JOIN  ");
        sql.append(" share_org org ");
        sql.append("    ON b.makerOrgCode = org.orgCode ");
        sql.append("    ");
        sql.append("  LEFT JOIN  ");
        sql.append(" rel_res_grade rrg ");
        sql.append("    ON b.id = rrg.resId ");
        sql.append("    AND b.resTypeL1 = rrg.resTypeL1 ");
        sql.append("    ");
        sql.append(" LEFT JOIN ");
        sql.append(" share_code_grade scg ");
        sql.append("    ON rrg.gradeCode = scg.code ");
        sql.append("    AND scg.flagDelete = 0 ");
        sql.append("    ");
        sql.append(" LEFT JOIN ");
        sql.append(" rel_res_section rrs ");
        sql.append("    ON b.id = rrs.resId ");
        sql.append("    AND b.resTypeL1 = rrs.resTypeL1 ");
        sql.append("    ");
        sql.append(" LEFT JOIN ");
        sql.append(" share_code_section scs ");
        sql.append("    ON rrs.sectionCode = scs.code ");
        sql.append("    AND scs.flagDelete = 0 ");
        sql.append("    ");
        sql.append(" LEFT JOIN ");
        sql.append(" rel_res_subject rrs2 ");
        sql.append("    ON b.id = rrs2.resId ");
        sql.append("    AND b.resTypeL1 = rrs2.resTypeL1 ");
        sql.append("    ");
        sql.append(" LEFT JOIN ");
        sql.append(" share_code_subject scs2 ");
        sql.append("    ON rrs2.subjectCode = scs2.code ");
        sql.append("    AND scs2.flagDelete = 0");
        sql.append(" LEFT JOIN ");
        sql.append("    share_res_type_l2 srtl2 ");
        sql.append("        ON srtl2.code = b.resTypeL2  ");
        sql.append("        AND srtl2.codeL1 = b.resTypeL1 ");
        sql.append("    where 1=1 ");
        // sql参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("shareCheckLevel", shareCheckLevel);
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("  and scs.code=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append(" and scs2.code=:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (StringUtils.isNotEmpty(gradeCode)) {
            sql.append(" and scg.code=:gradeCode ");
            sqlParam.put("gradeCode", gradeCode);
        }
        if (shareCheckStatus != null) {
            sql.append(" and b.shareCheckStatus =:shareCheckStatus ");
            sqlParam.put("shareCheckStatus", shareCheckStatus);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and  org.orgName like:orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (shareCheckLevel != null && shareCheckLevel == 20) {
            sql.append(" and b.makerOrgCode =:orgCode ");
            sqlParam.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" and b.makerName like:userName ");
            sqlParam.put("userName", "%" + userName.trim() + "%");
        }
        if (resTypeL1 != null) {
            sql.append(" and b.resTypeL1=:resTypeL1 ");
            sqlParam.put("resTypeL1", resTypeL1);
        }
        if (StringUtils.isNotEmpty(uploadStartTime)) {
            sql.append(" and b.makeTime>= :uploadStartTime");
            sqlParam.put("uploadStartTime", uploadStartTime);
        }
        if (StringUtils.isNotEmpty(uploadEndTime)) {
            sql.append(" and b.makeTime<= :uploadEndTime");
            sqlParam.put("uploadEndTime", uploadEndTime);
        }
        if (StringUtils.isNotEmpty(resName)) {
            sql.append(" and b.resName like :resName");
            sqlParam.put("resName", "%" + resName.trim() + "%");
        }
        sql.append(" order by b.makeTime desc ");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    /**
     * 恢复文档 ()<br>
     * 
     * @param ids 文档id列表
     */
    public int recoveryDocList(String ids) {
        String sql = "update res_doc set flagDelete=0 where id in (" + ids + ")";
        return super.update(sql, new HashMap<String, Object>());
    }

    /**
     * 物理删除文档 ()<br>
     * 
     * @param ids 文档ids
     * @return
     */
    public int physicalDeleteDoc(String ids) {
        String sql = "delete from  res_Doc where id in (" + ids + ")";
        return super.update(sql, new HashMap<String, Object>());
    }

    /**
     * 删除文档 ()<br>
     * 
     * @param ids 文档ids
     * @return
     */
    public int deleteDoc(String ids, String modifier, String modifierIP) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        Timestamp currentTime = DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        sql.append("UPDATE");
        sql.append("        res_doc ");
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
     * 
     * (批量处理文档资源，彻底删除或者恢复)<br>
     * 
     * @param ids
     * @param operateType
     * @return
     */
    @Override
    public int updateFlagDeleteBatch(String ids, Integer operateType, String userName, String ip) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        Timestamp currentTime = DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        sqlParam.put("modifier", userName);
        sqlParam.put("modifierIP", ip);
        sqlParam.put("modifyTime", currentTime);
        sql.append("UPDATE");
        sql.append("        res_doc ");
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